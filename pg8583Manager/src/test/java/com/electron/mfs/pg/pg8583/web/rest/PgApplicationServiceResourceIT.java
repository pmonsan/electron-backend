package com.electron.mfs.pg.pg8583.web.rest;

import com.electron.mfs.pg.pg8583.Pg8583ManagerApp;
import com.electron.mfs.pg.pg8583.config.SecurityBeanOverrideConfiguration;
import com.electron.mfs.pg.pg8583.domain.PgApplicationService;
import com.electron.mfs.pg.pg8583.repository.PgApplicationServiceRepository;
import com.electron.mfs.pg.pg8583.service.PgApplicationServiceService;
import com.electron.mfs.pg.pg8583.service.dto.PgApplicationServiceDTO;
import com.electron.mfs.pg.pg8583.service.mapper.PgApplicationServiceMapper;
import com.electron.mfs.pg.pg8583.web.rest.errors.ExceptionTranslator;

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

import static com.electron.mfs.pg.pg8583.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link PgApplicationServiceResource} REST controller.
 */
@EmbeddedKafka
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, Pg8583ManagerApp.class})
public class PgApplicationServiceResourceIT {

    private static final String DEFAULT_SERVICE_CODE = "AAAAA";
    private static final String UPDATED_SERVICE_CODE = "BBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private PgApplicationServiceRepository pgApplicationServiceRepository;

    @Autowired
    private PgApplicationServiceMapper pgApplicationServiceMapper;

    @Autowired
    private PgApplicationServiceService pgApplicationServiceService;

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

    private MockMvc restPgApplicationServiceMockMvc;

    private PgApplicationService pgApplicationService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PgApplicationServiceResource pgApplicationServiceResource = new PgApplicationServiceResource(pgApplicationServiceService);
        this.restPgApplicationServiceMockMvc = MockMvcBuilders.standaloneSetup(pgApplicationServiceResource)
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
    public static PgApplicationService createEntity(EntityManager em) {
        PgApplicationService pgApplicationService = new PgApplicationService()
            .serviceCode(DEFAULT_SERVICE_CODE)
            .active(DEFAULT_ACTIVE);
        return pgApplicationService;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PgApplicationService createUpdatedEntity(EntityManager em) {
        PgApplicationService pgApplicationService = new PgApplicationService()
            .serviceCode(UPDATED_SERVICE_CODE)
            .active(UPDATED_ACTIVE);
        return pgApplicationService;
    }

    @BeforeEach
    public void initTest() {
        pgApplicationService = createEntity(em);
    }

    @Test
    @Transactional
    public void createPgApplicationService() throws Exception {
        int databaseSizeBeforeCreate = pgApplicationServiceRepository.findAll().size();

        // Create the PgApplicationService
        PgApplicationServiceDTO pgApplicationServiceDTO = pgApplicationServiceMapper.toDto(pgApplicationService);
        restPgApplicationServiceMockMvc.perform(post("/api/pg-application-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgApplicationServiceDTO)))
            .andExpect(status().isCreated());

        // Validate the PgApplicationService in the database
        List<PgApplicationService> pgApplicationServiceList = pgApplicationServiceRepository.findAll();
        assertThat(pgApplicationServiceList).hasSize(databaseSizeBeforeCreate + 1);
        PgApplicationService testPgApplicationService = pgApplicationServiceList.get(pgApplicationServiceList.size() - 1);
        assertThat(testPgApplicationService.getServiceCode()).isEqualTo(DEFAULT_SERVICE_CODE);
        assertThat(testPgApplicationService.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createPgApplicationServiceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pgApplicationServiceRepository.findAll().size();

        // Create the PgApplicationService with an existing ID
        pgApplicationService.setId(1L);
        PgApplicationServiceDTO pgApplicationServiceDTO = pgApplicationServiceMapper.toDto(pgApplicationService);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPgApplicationServiceMockMvc.perform(post("/api/pg-application-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgApplicationServiceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PgApplicationService in the database
        List<PgApplicationService> pgApplicationServiceList = pgApplicationServiceRepository.findAll();
        assertThat(pgApplicationServiceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkServiceCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgApplicationServiceRepository.findAll().size();
        // set the field null
        pgApplicationService.setServiceCode(null);

        // Create the PgApplicationService, which fails.
        PgApplicationServiceDTO pgApplicationServiceDTO = pgApplicationServiceMapper.toDto(pgApplicationService);

        restPgApplicationServiceMockMvc.perform(post("/api/pg-application-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgApplicationServiceDTO)))
            .andExpect(status().isBadRequest());

        List<PgApplicationService> pgApplicationServiceList = pgApplicationServiceRepository.findAll();
        assertThat(pgApplicationServiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgApplicationServiceRepository.findAll().size();
        // set the field null
        pgApplicationService.setActive(null);

        // Create the PgApplicationService, which fails.
        PgApplicationServiceDTO pgApplicationServiceDTO = pgApplicationServiceMapper.toDto(pgApplicationService);

        restPgApplicationServiceMockMvc.perform(post("/api/pg-application-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgApplicationServiceDTO)))
            .andExpect(status().isBadRequest());

        List<PgApplicationService> pgApplicationServiceList = pgApplicationServiceRepository.findAll();
        assertThat(pgApplicationServiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPgApplicationServices() throws Exception {
        // Initialize the database
        pgApplicationServiceRepository.saveAndFlush(pgApplicationService);

        // Get all the pgApplicationServiceList
        restPgApplicationServiceMockMvc.perform(get("/api/pg-application-services?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pgApplicationService.getId().intValue())))
            .andExpect(jsonPath("$.[*].serviceCode").value(hasItem(DEFAULT_SERVICE_CODE.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getPgApplicationService() throws Exception {
        // Initialize the database
        pgApplicationServiceRepository.saveAndFlush(pgApplicationService);

        // Get the pgApplicationService
        restPgApplicationServiceMockMvc.perform(get("/api/pg-application-services/{id}", pgApplicationService.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pgApplicationService.getId().intValue()))
            .andExpect(jsonPath("$.serviceCode").value(DEFAULT_SERVICE_CODE.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPgApplicationService() throws Exception {
        // Get the pgApplicationService
        restPgApplicationServiceMockMvc.perform(get("/api/pg-application-services/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePgApplicationService() throws Exception {
        // Initialize the database
        pgApplicationServiceRepository.saveAndFlush(pgApplicationService);

        int databaseSizeBeforeUpdate = pgApplicationServiceRepository.findAll().size();

        // Update the pgApplicationService
        PgApplicationService updatedPgApplicationService = pgApplicationServiceRepository.findById(pgApplicationService.getId()).get();
        // Disconnect from session so that the updates on updatedPgApplicationService are not directly saved in db
        em.detach(updatedPgApplicationService);
        updatedPgApplicationService
            .serviceCode(UPDATED_SERVICE_CODE)
            .active(UPDATED_ACTIVE);
        PgApplicationServiceDTO pgApplicationServiceDTO = pgApplicationServiceMapper.toDto(updatedPgApplicationService);

        restPgApplicationServiceMockMvc.perform(put("/api/pg-application-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgApplicationServiceDTO)))
            .andExpect(status().isOk());

        // Validate the PgApplicationService in the database
        List<PgApplicationService> pgApplicationServiceList = pgApplicationServiceRepository.findAll();
        assertThat(pgApplicationServiceList).hasSize(databaseSizeBeforeUpdate);
        PgApplicationService testPgApplicationService = pgApplicationServiceList.get(pgApplicationServiceList.size() - 1);
        assertThat(testPgApplicationService.getServiceCode()).isEqualTo(UPDATED_SERVICE_CODE);
        assertThat(testPgApplicationService.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingPgApplicationService() throws Exception {
        int databaseSizeBeforeUpdate = pgApplicationServiceRepository.findAll().size();

        // Create the PgApplicationService
        PgApplicationServiceDTO pgApplicationServiceDTO = pgApplicationServiceMapper.toDto(pgApplicationService);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPgApplicationServiceMockMvc.perform(put("/api/pg-application-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgApplicationServiceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PgApplicationService in the database
        List<PgApplicationService> pgApplicationServiceList = pgApplicationServiceRepository.findAll();
        assertThat(pgApplicationServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePgApplicationService() throws Exception {
        // Initialize the database
        pgApplicationServiceRepository.saveAndFlush(pgApplicationService);

        int databaseSizeBeforeDelete = pgApplicationServiceRepository.findAll().size();

        // Delete the pgApplicationService
        restPgApplicationServiceMockMvc.perform(delete("/api/pg-application-services/{id}", pgApplicationService.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PgApplicationService> pgApplicationServiceList = pgApplicationServiceRepository.findAll();
        assertThat(pgApplicationServiceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PgApplicationService.class);
        PgApplicationService pgApplicationService1 = new PgApplicationService();
        pgApplicationService1.setId(1L);
        PgApplicationService pgApplicationService2 = new PgApplicationService();
        pgApplicationService2.setId(pgApplicationService1.getId());
        assertThat(pgApplicationService1).isEqualTo(pgApplicationService2);
        pgApplicationService2.setId(2L);
        assertThat(pgApplicationService1).isNotEqualTo(pgApplicationService2);
        pgApplicationService1.setId(null);
        assertThat(pgApplicationService1).isNotEqualTo(pgApplicationService2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PgApplicationServiceDTO.class);
        PgApplicationServiceDTO pgApplicationServiceDTO1 = new PgApplicationServiceDTO();
        pgApplicationServiceDTO1.setId(1L);
        PgApplicationServiceDTO pgApplicationServiceDTO2 = new PgApplicationServiceDTO();
        assertThat(pgApplicationServiceDTO1).isNotEqualTo(pgApplicationServiceDTO2);
        pgApplicationServiceDTO2.setId(pgApplicationServiceDTO1.getId());
        assertThat(pgApplicationServiceDTO1).isEqualTo(pgApplicationServiceDTO2);
        pgApplicationServiceDTO2.setId(2L);
        assertThat(pgApplicationServiceDTO1).isNotEqualTo(pgApplicationServiceDTO2);
        pgApplicationServiceDTO1.setId(null);
        assertThat(pgApplicationServiceDTO1).isNotEqualTo(pgApplicationServiceDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(pgApplicationServiceMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(pgApplicationServiceMapper.fromId(null)).isNull();
    }
}
