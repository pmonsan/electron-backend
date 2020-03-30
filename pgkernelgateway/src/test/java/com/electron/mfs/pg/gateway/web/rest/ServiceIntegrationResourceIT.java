package com.electron.mfs.pg.gateway.web.rest;

import com.electron.mfs.pg.gateway.PgkernelgatewayApp;
import com.electron.mfs.pg.gateway.config.SecurityBeanOverrideConfiguration;
import com.electron.mfs.pg.gateway.domain.ServiceIntegration;
import com.electron.mfs.pg.gateway.repository.ServiceIntegrationRepository;
import com.electron.mfs.pg.gateway.service.ServiceIntegrationService;
import com.electron.mfs.pg.gateway.service.dto.ServiceIntegrationDTO;
import com.electron.mfs.pg.gateway.service.mapper.ServiceIntegrationMapper;
import com.electron.mfs.pg.gateway.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.electron.mfs.pg.gateway.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link ServiceIntegrationResource} REST controller.
 */
@EmbeddedKafka
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, PgkernelgatewayApp.class})
public class ServiceIntegrationResourceIT {

    private static final String DEFAULT_CUSTOMER_REF = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_REF = "BBBBBBBBBB";

    private static final String DEFAULT_SERVICE_CODE = "AAAAA";
    private static final String UPDATED_SERVICE_CODE = "BBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private ServiceIntegrationRepository serviceIntegrationRepository;

    @Autowired
    private ServiceIntegrationMapper serviceIntegrationMapper;

    @Autowired
    private ServiceIntegrationService serviceIntegrationService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restServiceIntegrationMockMvc;

    private ServiceIntegration serviceIntegration;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ServiceIntegrationResource serviceIntegrationResource = new ServiceIntegrationResource(serviceIntegrationService);
        this.restServiceIntegrationMockMvc = MockMvcBuilders.standaloneSetup(serviceIntegrationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServiceIntegration createEntity(EntityManager em) {
        ServiceIntegration serviceIntegration = new ServiceIntegration()
            .customerRef(DEFAULT_CUSTOMER_REF)
            .serviceCode(DEFAULT_SERVICE_CODE)
            .active(DEFAULT_ACTIVE);
        return serviceIntegration;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServiceIntegration createUpdatedEntity(EntityManager em) {
        ServiceIntegration serviceIntegration = new ServiceIntegration()
            .customerRef(UPDATED_CUSTOMER_REF)
            .serviceCode(UPDATED_SERVICE_CODE)
            .active(UPDATED_ACTIVE);
        return serviceIntegration;
    }

    @BeforeEach
    public void initTest() {
        serviceIntegration = createEntity(em);
    }

    @Test
    @Transactional
    public void createServiceIntegration() throws Exception {
        int databaseSizeBeforeCreate = serviceIntegrationRepository.findAll().size();

        // Create the ServiceIntegration
        ServiceIntegrationDTO serviceIntegrationDTO = serviceIntegrationMapper.toDto(serviceIntegration);
        restServiceIntegrationMockMvc.perform(post("/api/service-integrations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceIntegrationDTO)))
            .andExpect(status().isCreated());

        // Validate the ServiceIntegration in the database
        List<ServiceIntegration> serviceIntegrationList = serviceIntegrationRepository.findAll();
        assertThat(serviceIntegrationList).hasSize(databaseSizeBeforeCreate + 1);
        ServiceIntegration testServiceIntegration = serviceIntegrationList.get(serviceIntegrationList.size() - 1);
        assertThat(testServiceIntegration.getCustomerRef()).isEqualTo(DEFAULT_CUSTOMER_REF);
        assertThat(testServiceIntegration.getServiceCode()).isEqualTo(DEFAULT_SERVICE_CODE);
        assertThat(testServiceIntegration.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createServiceIntegrationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = serviceIntegrationRepository.findAll().size();

        // Create the ServiceIntegration with an existing ID
        serviceIntegration.setId(1L);
        ServiceIntegrationDTO serviceIntegrationDTO = serviceIntegrationMapper.toDto(serviceIntegration);

        // An entity with an existing ID cannot be created, so this API call must fail
        restServiceIntegrationMockMvc.perform(post("/api/service-integrations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceIntegrationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceIntegration in the database
        List<ServiceIntegration> serviceIntegrationList = serviceIntegrationRepository.findAll();
        assertThat(serviceIntegrationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCustomerRefIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceIntegrationRepository.findAll().size();
        // set the field null
        serviceIntegration.setCustomerRef(null);

        // Create the ServiceIntegration, which fails.
        ServiceIntegrationDTO serviceIntegrationDTO = serviceIntegrationMapper.toDto(serviceIntegration);

        restServiceIntegrationMockMvc.perform(post("/api/service-integrations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceIntegrationDTO)))
            .andExpect(status().isBadRequest());

        List<ServiceIntegration> serviceIntegrationList = serviceIntegrationRepository.findAll();
        assertThat(serviceIntegrationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceIntegrationRepository.findAll().size();
        // set the field null
        serviceIntegration.setActive(null);

        // Create the ServiceIntegration, which fails.
        ServiceIntegrationDTO serviceIntegrationDTO = serviceIntegrationMapper.toDto(serviceIntegration);

        restServiceIntegrationMockMvc.perform(post("/api/service-integrations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceIntegrationDTO)))
            .andExpect(status().isBadRequest());

        List<ServiceIntegration> serviceIntegrationList = serviceIntegrationRepository.findAll();
        assertThat(serviceIntegrationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllServiceIntegrations() throws Exception {
        // Initialize the database
        serviceIntegrationRepository.saveAndFlush(serviceIntegration);

        // Get all the serviceIntegrationList
        restServiceIntegrationMockMvc.perform(get("/api/service-integrations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceIntegration.getId().intValue())))
            .andExpect(jsonPath("$.[*].customerRef").value(hasItem(DEFAULT_CUSTOMER_REF.toString())))
            .andExpect(jsonPath("$.[*].serviceCode").value(hasItem(DEFAULT_SERVICE_CODE.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getServiceIntegration() throws Exception {
        // Initialize the database
        serviceIntegrationRepository.saveAndFlush(serviceIntegration);

        // Get the serviceIntegration
        restServiceIntegrationMockMvc.perform(get("/api/service-integrations/{id}", serviceIntegration.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(serviceIntegration.getId().intValue()))
            .andExpect(jsonPath("$.customerRef").value(DEFAULT_CUSTOMER_REF.toString()))
            .andExpect(jsonPath("$.serviceCode").value(DEFAULT_SERVICE_CODE.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingServiceIntegration() throws Exception {
        // Get the serviceIntegration
        restServiceIntegrationMockMvc.perform(get("/api/service-integrations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateServiceIntegration() throws Exception {
        // Initialize the database
        serviceIntegrationRepository.saveAndFlush(serviceIntegration);

        int databaseSizeBeforeUpdate = serviceIntegrationRepository.findAll().size();

        // Update the serviceIntegration
        ServiceIntegration updatedServiceIntegration = serviceIntegrationRepository.findById(serviceIntegration.getId()).get();
        // Disconnect from session so that the updates on updatedServiceIntegration are not directly saved in db
        em.detach(updatedServiceIntegration);
        updatedServiceIntegration
            .customerRef(UPDATED_CUSTOMER_REF)
            .serviceCode(UPDATED_SERVICE_CODE)
            .active(UPDATED_ACTIVE);
        ServiceIntegrationDTO serviceIntegrationDTO = serviceIntegrationMapper.toDto(updatedServiceIntegration);

        restServiceIntegrationMockMvc.perform(put("/api/service-integrations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceIntegrationDTO)))
            .andExpect(status().isOk());

        // Validate the ServiceIntegration in the database
        List<ServiceIntegration> serviceIntegrationList = serviceIntegrationRepository.findAll();
        assertThat(serviceIntegrationList).hasSize(databaseSizeBeforeUpdate);
        ServiceIntegration testServiceIntegration = serviceIntegrationList.get(serviceIntegrationList.size() - 1);
        assertThat(testServiceIntegration.getCustomerRef()).isEqualTo(UPDATED_CUSTOMER_REF);
        assertThat(testServiceIntegration.getServiceCode()).isEqualTo(UPDATED_SERVICE_CODE);
        assertThat(testServiceIntegration.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingServiceIntegration() throws Exception {
        int databaseSizeBeforeUpdate = serviceIntegrationRepository.findAll().size();

        // Create the ServiceIntegration
        ServiceIntegrationDTO serviceIntegrationDTO = serviceIntegrationMapper.toDto(serviceIntegration);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceIntegrationMockMvc.perform(put("/api/service-integrations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceIntegrationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceIntegration in the database
        List<ServiceIntegration> serviceIntegrationList = serviceIntegrationRepository.findAll();
        assertThat(serviceIntegrationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteServiceIntegration() throws Exception {
        // Initialize the database
        serviceIntegrationRepository.saveAndFlush(serviceIntegration);

        int databaseSizeBeforeDelete = serviceIntegrationRepository.findAll().size();

        // Delete the serviceIntegration
        restServiceIntegrationMockMvc.perform(delete("/api/service-integrations/{id}", serviceIntegration.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ServiceIntegration> serviceIntegrationList = serviceIntegrationRepository.findAll();
        assertThat(serviceIntegrationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceIntegration.class);
        ServiceIntegration serviceIntegration1 = new ServiceIntegration();
        serviceIntegration1.setId(1L);
        ServiceIntegration serviceIntegration2 = new ServiceIntegration();
        serviceIntegration2.setId(serviceIntegration1.getId());
        assertThat(serviceIntegration1).isEqualTo(serviceIntegration2);
        serviceIntegration2.setId(2L);
        assertThat(serviceIntegration1).isNotEqualTo(serviceIntegration2);
        serviceIntegration1.setId(null);
        assertThat(serviceIntegration1).isNotEqualTo(serviceIntegration2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceIntegrationDTO.class);
        ServiceIntegrationDTO serviceIntegrationDTO1 = new ServiceIntegrationDTO();
        serviceIntegrationDTO1.setId(1L);
        ServiceIntegrationDTO serviceIntegrationDTO2 = new ServiceIntegrationDTO();
        assertThat(serviceIntegrationDTO1).isNotEqualTo(serviceIntegrationDTO2);
        serviceIntegrationDTO2.setId(serviceIntegrationDTO1.getId());
        assertThat(serviceIntegrationDTO1).isEqualTo(serviceIntegrationDTO2);
        serviceIntegrationDTO2.setId(2L);
        assertThat(serviceIntegrationDTO1).isNotEqualTo(serviceIntegrationDTO2);
        serviceIntegrationDTO1.setId(null);
        assertThat(serviceIntegrationDTO1).isNotEqualTo(serviceIntegrationDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(serviceIntegrationMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(serviceIntegrationMapper.fromId(null)).isNull();
    }
}
