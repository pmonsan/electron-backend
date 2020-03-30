package com.electron.mfs.pg.mdm.web.rest;

import com.electron.mfs.pg.mdm.MdmManagerApp;
import com.electron.mfs.pg.mdm.config.SecurityBeanOverrideConfiguration;
import com.electron.mfs.pg.mdm.domain.ServiceAuthentication;
import com.electron.mfs.pg.mdm.repository.ServiceAuthenticationRepository;
import com.electron.mfs.pg.mdm.service.ServiceAuthenticationService;
import com.electron.mfs.pg.mdm.service.dto.ServiceAuthenticationDTO;
import com.electron.mfs.pg.mdm.service.mapper.ServiceAuthenticationMapper;
import com.electron.mfs.pg.mdm.web.rest.errors.ExceptionTranslator;

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

import static com.electron.mfs.pg.mdm.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link ServiceAuthenticationResource} REST controller.
 */
@EmbeddedKafka
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, MdmManagerApp.class})
public class ServiceAuthenticationResourceIT {

    private static final String DEFAULT_CODE = "AAAAA";
    private static final String UPDATED_CODE = "BBBBB";

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private ServiceAuthenticationRepository serviceAuthenticationRepository;

    @Autowired
    private ServiceAuthenticationMapper serviceAuthenticationMapper;

    @Autowired
    private ServiceAuthenticationService serviceAuthenticationService;

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

    private MockMvc restServiceAuthenticationMockMvc;

    private ServiceAuthentication serviceAuthentication;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ServiceAuthenticationResource serviceAuthenticationResource = new ServiceAuthenticationResource(serviceAuthenticationService);
        this.restServiceAuthenticationMockMvc = MockMvcBuilders.standaloneSetup(serviceAuthenticationResource)
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
    public static ServiceAuthentication createEntity(EntityManager em) {
        ServiceAuthentication serviceAuthentication = new ServiceAuthentication()
            .code(DEFAULT_CODE)
            .label(DEFAULT_LABEL)
            .active(DEFAULT_ACTIVE);
        return serviceAuthentication;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServiceAuthentication createUpdatedEntity(EntityManager em) {
        ServiceAuthentication serviceAuthentication = new ServiceAuthentication()
            .code(UPDATED_CODE)
            .label(UPDATED_LABEL)
            .active(UPDATED_ACTIVE);
        return serviceAuthentication;
    }

    @BeforeEach
    public void initTest() {
        serviceAuthentication = createEntity(em);
    }

    @Test
    @Transactional
    public void createServiceAuthentication() throws Exception {
        int databaseSizeBeforeCreate = serviceAuthenticationRepository.findAll().size();

        // Create the ServiceAuthentication
        ServiceAuthenticationDTO serviceAuthenticationDTO = serviceAuthenticationMapper.toDto(serviceAuthentication);
        restServiceAuthenticationMockMvc.perform(post("/api/service-authentications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceAuthenticationDTO)))
            .andExpect(status().isCreated());

        // Validate the ServiceAuthentication in the database
        List<ServiceAuthentication> serviceAuthenticationList = serviceAuthenticationRepository.findAll();
        assertThat(serviceAuthenticationList).hasSize(databaseSizeBeforeCreate + 1);
        ServiceAuthentication testServiceAuthentication = serviceAuthenticationList.get(serviceAuthenticationList.size() - 1);
        assertThat(testServiceAuthentication.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testServiceAuthentication.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testServiceAuthentication.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createServiceAuthenticationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = serviceAuthenticationRepository.findAll().size();

        // Create the ServiceAuthentication with an existing ID
        serviceAuthentication.setId(1L);
        ServiceAuthenticationDTO serviceAuthenticationDTO = serviceAuthenticationMapper.toDto(serviceAuthentication);

        // An entity with an existing ID cannot be created, so this API call must fail
        restServiceAuthenticationMockMvc.perform(post("/api/service-authentications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceAuthenticationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceAuthentication in the database
        List<ServiceAuthentication> serviceAuthenticationList = serviceAuthenticationRepository.findAll();
        assertThat(serviceAuthenticationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceAuthenticationRepository.findAll().size();
        // set the field null
        serviceAuthentication.setCode(null);

        // Create the ServiceAuthentication, which fails.
        ServiceAuthenticationDTO serviceAuthenticationDTO = serviceAuthenticationMapper.toDto(serviceAuthentication);

        restServiceAuthenticationMockMvc.perform(post("/api/service-authentications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceAuthenticationDTO)))
            .andExpect(status().isBadRequest());

        List<ServiceAuthentication> serviceAuthenticationList = serviceAuthenticationRepository.findAll();
        assertThat(serviceAuthenticationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLabelIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceAuthenticationRepository.findAll().size();
        // set the field null
        serviceAuthentication.setLabel(null);

        // Create the ServiceAuthentication, which fails.
        ServiceAuthenticationDTO serviceAuthenticationDTO = serviceAuthenticationMapper.toDto(serviceAuthentication);

        restServiceAuthenticationMockMvc.perform(post("/api/service-authentications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceAuthenticationDTO)))
            .andExpect(status().isBadRequest());

        List<ServiceAuthentication> serviceAuthenticationList = serviceAuthenticationRepository.findAll();
        assertThat(serviceAuthenticationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceAuthenticationRepository.findAll().size();
        // set the field null
        serviceAuthentication.setActive(null);

        // Create the ServiceAuthentication, which fails.
        ServiceAuthenticationDTO serviceAuthenticationDTO = serviceAuthenticationMapper.toDto(serviceAuthentication);

        restServiceAuthenticationMockMvc.perform(post("/api/service-authentications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceAuthenticationDTO)))
            .andExpect(status().isBadRequest());

        List<ServiceAuthentication> serviceAuthenticationList = serviceAuthenticationRepository.findAll();
        assertThat(serviceAuthenticationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllServiceAuthentications() throws Exception {
        // Initialize the database
        serviceAuthenticationRepository.saveAndFlush(serviceAuthentication);

        // Get all the serviceAuthenticationList
        restServiceAuthenticationMockMvc.perform(get("/api/service-authentications?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceAuthentication.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getServiceAuthentication() throws Exception {
        // Initialize the database
        serviceAuthenticationRepository.saveAndFlush(serviceAuthentication);

        // Get the serviceAuthentication
        restServiceAuthenticationMockMvc.perform(get("/api/service-authentications/{id}", serviceAuthentication.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(serviceAuthentication.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingServiceAuthentication() throws Exception {
        // Get the serviceAuthentication
        restServiceAuthenticationMockMvc.perform(get("/api/service-authentications/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateServiceAuthentication() throws Exception {
        // Initialize the database
        serviceAuthenticationRepository.saveAndFlush(serviceAuthentication);

        int databaseSizeBeforeUpdate = serviceAuthenticationRepository.findAll().size();

        // Update the serviceAuthentication
        ServiceAuthentication updatedServiceAuthentication = serviceAuthenticationRepository.findById(serviceAuthentication.getId()).get();
        // Disconnect from session so that the updates on updatedServiceAuthentication are not directly saved in db
        em.detach(updatedServiceAuthentication);
        updatedServiceAuthentication
            .code(UPDATED_CODE)
            .label(UPDATED_LABEL)
            .active(UPDATED_ACTIVE);
        ServiceAuthenticationDTO serviceAuthenticationDTO = serviceAuthenticationMapper.toDto(updatedServiceAuthentication);

        restServiceAuthenticationMockMvc.perform(put("/api/service-authentications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceAuthenticationDTO)))
            .andExpect(status().isOk());

        // Validate the ServiceAuthentication in the database
        List<ServiceAuthentication> serviceAuthenticationList = serviceAuthenticationRepository.findAll();
        assertThat(serviceAuthenticationList).hasSize(databaseSizeBeforeUpdate);
        ServiceAuthentication testServiceAuthentication = serviceAuthenticationList.get(serviceAuthenticationList.size() - 1);
        assertThat(testServiceAuthentication.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testServiceAuthentication.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testServiceAuthentication.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingServiceAuthentication() throws Exception {
        int databaseSizeBeforeUpdate = serviceAuthenticationRepository.findAll().size();

        // Create the ServiceAuthentication
        ServiceAuthenticationDTO serviceAuthenticationDTO = serviceAuthenticationMapper.toDto(serviceAuthentication);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceAuthenticationMockMvc.perform(put("/api/service-authentications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceAuthenticationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceAuthentication in the database
        List<ServiceAuthentication> serviceAuthenticationList = serviceAuthenticationRepository.findAll();
        assertThat(serviceAuthenticationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteServiceAuthentication() throws Exception {
        // Initialize the database
        serviceAuthenticationRepository.saveAndFlush(serviceAuthentication);

        int databaseSizeBeforeDelete = serviceAuthenticationRepository.findAll().size();

        // Delete the serviceAuthentication
        restServiceAuthenticationMockMvc.perform(delete("/api/service-authentications/{id}", serviceAuthentication.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ServiceAuthentication> serviceAuthenticationList = serviceAuthenticationRepository.findAll();
        assertThat(serviceAuthenticationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceAuthentication.class);
        ServiceAuthentication serviceAuthentication1 = new ServiceAuthentication();
        serviceAuthentication1.setId(1L);
        ServiceAuthentication serviceAuthentication2 = new ServiceAuthentication();
        serviceAuthentication2.setId(serviceAuthentication1.getId());
        assertThat(serviceAuthentication1).isEqualTo(serviceAuthentication2);
        serviceAuthentication2.setId(2L);
        assertThat(serviceAuthentication1).isNotEqualTo(serviceAuthentication2);
        serviceAuthentication1.setId(null);
        assertThat(serviceAuthentication1).isNotEqualTo(serviceAuthentication2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceAuthenticationDTO.class);
        ServiceAuthenticationDTO serviceAuthenticationDTO1 = new ServiceAuthenticationDTO();
        serviceAuthenticationDTO1.setId(1L);
        ServiceAuthenticationDTO serviceAuthenticationDTO2 = new ServiceAuthenticationDTO();
        assertThat(serviceAuthenticationDTO1).isNotEqualTo(serviceAuthenticationDTO2);
        serviceAuthenticationDTO2.setId(serviceAuthenticationDTO1.getId());
        assertThat(serviceAuthenticationDTO1).isEqualTo(serviceAuthenticationDTO2);
        serviceAuthenticationDTO2.setId(2L);
        assertThat(serviceAuthenticationDTO1).isNotEqualTo(serviceAuthenticationDTO2);
        serviceAuthenticationDTO1.setId(null);
        assertThat(serviceAuthenticationDTO1).isNotEqualTo(serviceAuthenticationDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(serviceAuthenticationMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(serviceAuthenticationMapper.fromId(null)).isNull();
    }
}
