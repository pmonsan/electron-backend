package com.electron.mfs.pg.gateway.web.rest;

import com.electron.mfs.pg.gateway.PgkernelgatewayApp;
import com.electron.mfs.pg.gateway.config.SecurityBeanOverrideConfiguration;
import com.electron.mfs.pg.gateway.domain.ServiceLimit;
import com.electron.mfs.pg.gateway.repository.ServiceLimitRepository;
import com.electron.mfs.pg.gateway.service.ServiceLimitService;
import com.electron.mfs.pg.gateway.service.dto.ServiceLimitDTO;
import com.electron.mfs.pg.gateway.service.mapper.ServiceLimitMapper;
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
 * Integration tests for the {@Link ServiceLimitResource} REST controller.
 */
@EmbeddedKafka
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, PgkernelgatewayApp.class})
public class ServiceLimitResourceIT {

    private static final String DEFAULT_LIMIT_TYPE_CODE = "AAAAA";
    private static final String UPDATED_LIMIT_TYPE_CODE = "BBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private ServiceLimitRepository serviceLimitRepository;

    @Autowired
    private ServiceLimitMapper serviceLimitMapper;

    @Autowired
    private ServiceLimitService serviceLimitService;

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

    private MockMvc restServiceLimitMockMvc;

    private ServiceLimit serviceLimit;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ServiceLimitResource serviceLimitResource = new ServiceLimitResource(serviceLimitService);
        this.restServiceLimitMockMvc = MockMvcBuilders.standaloneSetup(serviceLimitResource)
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
    public static ServiceLimit createEntity(EntityManager em) {
        ServiceLimit serviceLimit = new ServiceLimit()
            .limitTypeCode(DEFAULT_LIMIT_TYPE_CODE)
            .value(DEFAULT_VALUE)
            .comment(DEFAULT_COMMENT)
            .active(DEFAULT_ACTIVE);
        return serviceLimit;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServiceLimit createUpdatedEntity(EntityManager em) {
        ServiceLimit serviceLimit = new ServiceLimit()
            .limitTypeCode(UPDATED_LIMIT_TYPE_CODE)
            .value(UPDATED_VALUE)
            .comment(UPDATED_COMMENT)
            .active(UPDATED_ACTIVE);
        return serviceLimit;
    }

    @BeforeEach
    public void initTest() {
        serviceLimit = createEntity(em);
    }

    @Test
    @Transactional
    public void createServiceLimit() throws Exception {
        int databaseSizeBeforeCreate = serviceLimitRepository.findAll().size();

        // Create the ServiceLimit
        ServiceLimitDTO serviceLimitDTO = serviceLimitMapper.toDto(serviceLimit);
        restServiceLimitMockMvc.perform(post("/api/service-limits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceLimitDTO)))
            .andExpect(status().isCreated());

        // Validate the ServiceLimit in the database
        List<ServiceLimit> serviceLimitList = serviceLimitRepository.findAll();
        assertThat(serviceLimitList).hasSize(databaseSizeBeforeCreate + 1);
        ServiceLimit testServiceLimit = serviceLimitList.get(serviceLimitList.size() - 1);
        assertThat(testServiceLimit.getLimitTypeCode()).isEqualTo(DEFAULT_LIMIT_TYPE_CODE);
        assertThat(testServiceLimit.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testServiceLimit.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testServiceLimit.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createServiceLimitWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = serviceLimitRepository.findAll().size();

        // Create the ServiceLimit with an existing ID
        serviceLimit.setId(1L);
        ServiceLimitDTO serviceLimitDTO = serviceLimitMapper.toDto(serviceLimit);

        // An entity with an existing ID cannot be created, so this API call must fail
        restServiceLimitMockMvc.perform(post("/api/service-limits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceLimitDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceLimit in the database
        List<ServiceLimit> serviceLimitList = serviceLimitRepository.findAll();
        assertThat(serviceLimitList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkLimitTypeCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceLimitRepository.findAll().size();
        // set the field null
        serviceLimit.setLimitTypeCode(null);

        // Create the ServiceLimit, which fails.
        ServiceLimitDTO serviceLimitDTO = serviceLimitMapper.toDto(serviceLimit);

        restServiceLimitMockMvc.perform(post("/api/service-limits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceLimitDTO)))
            .andExpect(status().isBadRequest());

        List<ServiceLimit> serviceLimitList = serviceLimitRepository.findAll();
        assertThat(serviceLimitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceLimitRepository.findAll().size();
        // set the field null
        serviceLimit.setValue(null);

        // Create the ServiceLimit, which fails.
        ServiceLimitDTO serviceLimitDTO = serviceLimitMapper.toDto(serviceLimit);

        restServiceLimitMockMvc.perform(post("/api/service-limits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceLimitDTO)))
            .andExpect(status().isBadRequest());

        List<ServiceLimit> serviceLimitList = serviceLimitRepository.findAll();
        assertThat(serviceLimitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceLimitRepository.findAll().size();
        // set the field null
        serviceLimit.setActive(null);

        // Create the ServiceLimit, which fails.
        ServiceLimitDTO serviceLimitDTO = serviceLimitMapper.toDto(serviceLimit);

        restServiceLimitMockMvc.perform(post("/api/service-limits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceLimitDTO)))
            .andExpect(status().isBadRequest());

        List<ServiceLimit> serviceLimitList = serviceLimitRepository.findAll();
        assertThat(serviceLimitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllServiceLimits() throws Exception {
        // Initialize the database
        serviceLimitRepository.saveAndFlush(serviceLimit);

        // Get all the serviceLimitList
        restServiceLimitMockMvc.perform(get("/api/service-limits?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceLimit.getId().intValue())))
            .andExpect(jsonPath("$.[*].limitTypeCode").value(hasItem(DEFAULT_LIMIT_TYPE_CODE.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getServiceLimit() throws Exception {
        // Initialize the database
        serviceLimitRepository.saveAndFlush(serviceLimit);

        // Get the serviceLimit
        restServiceLimitMockMvc.perform(get("/api/service-limits/{id}", serviceLimit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(serviceLimit.getId().intValue()))
            .andExpect(jsonPath("$.limitTypeCode").value(DEFAULT_LIMIT_TYPE_CODE.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingServiceLimit() throws Exception {
        // Get the serviceLimit
        restServiceLimitMockMvc.perform(get("/api/service-limits/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateServiceLimit() throws Exception {
        // Initialize the database
        serviceLimitRepository.saveAndFlush(serviceLimit);

        int databaseSizeBeforeUpdate = serviceLimitRepository.findAll().size();

        // Update the serviceLimit
        ServiceLimit updatedServiceLimit = serviceLimitRepository.findById(serviceLimit.getId()).get();
        // Disconnect from session so that the updates on updatedServiceLimit are not directly saved in db
        em.detach(updatedServiceLimit);
        updatedServiceLimit
            .limitTypeCode(UPDATED_LIMIT_TYPE_CODE)
            .value(UPDATED_VALUE)
            .comment(UPDATED_COMMENT)
            .active(UPDATED_ACTIVE);
        ServiceLimitDTO serviceLimitDTO = serviceLimitMapper.toDto(updatedServiceLimit);

        restServiceLimitMockMvc.perform(put("/api/service-limits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceLimitDTO)))
            .andExpect(status().isOk());

        // Validate the ServiceLimit in the database
        List<ServiceLimit> serviceLimitList = serviceLimitRepository.findAll();
        assertThat(serviceLimitList).hasSize(databaseSizeBeforeUpdate);
        ServiceLimit testServiceLimit = serviceLimitList.get(serviceLimitList.size() - 1);
        assertThat(testServiceLimit.getLimitTypeCode()).isEqualTo(UPDATED_LIMIT_TYPE_CODE);
        assertThat(testServiceLimit.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testServiceLimit.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testServiceLimit.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingServiceLimit() throws Exception {
        int databaseSizeBeforeUpdate = serviceLimitRepository.findAll().size();

        // Create the ServiceLimit
        ServiceLimitDTO serviceLimitDTO = serviceLimitMapper.toDto(serviceLimit);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceLimitMockMvc.perform(put("/api/service-limits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceLimitDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceLimit in the database
        List<ServiceLimit> serviceLimitList = serviceLimitRepository.findAll();
        assertThat(serviceLimitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteServiceLimit() throws Exception {
        // Initialize the database
        serviceLimitRepository.saveAndFlush(serviceLimit);

        int databaseSizeBeforeDelete = serviceLimitRepository.findAll().size();

        // Delete the serviceLimit
        restServiceLimitMockMvc.perform(delete("/api/service-limits/{id}", serviceLimit.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ServiceLimit> serviceLimitList = serviceLimitRepository.findAll();
        assertThat(serviceLimitList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceLimit.class);
        ServiceLimit serviceLimit1 = new ServiceLimit();
        serviceLimit1.setId(1L);
        ServiceLimit serviceLimit2 = new ServiceLimit();
        serviceLimit2.setId(serviceLimit1.getId());
        assertThat(serviceLimit1).isEqualTo(serviceLimit2);
        serviceLimit2.setId(2L);
        assertThat(serviceLimit1).isNotEqualTo(serviceLimit2);
        serviceLimit1.setId(null);
        assertThat(serviceLimit1).isNotEqualTo(serviceLimit2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceLimitDTO.class);
        ServiceLimitDTO serviceLimitDTO1 = new ServiceLimitDTO();
        serviceLimitDTO1.setId(1L);
        ServiceLimitDTO serviceLimitDTO2 = new ServiceLimitDTO();
        assertThat(serviceLimitDTO1).isNotEqualTo(serviceLimitDTO2);
        serviceLimitDTO2.setId(serviceLimitDTO1.getId());
        assertThat(serviceLimitDTO1).isEqualTo(serviceLimitDTO2);
        serviceLimitDTO2.setId(2L);
        assertThat(serviceLimitDTO1).isNotEqualTo(serviceLimitDTO2);
        serviceLimitDTO1.setId(null);
        assertThat(serviceLimitDTO1).isNotEqualTo(serviceLimitDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(serviceLimitMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(serviceLimitMapper.fromId(null)).isNull();
    }
}
