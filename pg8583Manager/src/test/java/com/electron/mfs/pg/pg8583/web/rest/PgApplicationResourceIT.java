package com.electron.mfs.pg.pg8583.web.rest;

import com.electron.mfs.pg.pg8583.Pg8583ManagerApp;
import com.electron.mfs.pg.pg8583.config.SecurityBeanOverrideConfiguration;
import com.electron.mfs.pg.pg8583.domain.PgApplication;
import com.electron.mfs.pg.pg8583.repository.PgApplicationRepository;
import com.electron.mfs.pg.pg8583.service.PgApplicationService;
import com.electron.mfs.pg.pg8583.service.dto.PgApplicationDTO;
import com.electron.mfs.pg.pg8583.service.mapper.PgApplicationMapper;
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
 * Integration tests for the {@Link PgApplicationResource} REST controller.
 */
@EmbeddedKafka
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, Pg8583ManagerApp.class})
public class PgApplicationResourceIT {

    private static final String DEFAULT_CODE = "AAAAA";
    private static final String UPDATED_CODE = "BBBBB";

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final String DEFAULT_PARTNER_CODE = "AAAAA";
    private static final String UPDATED_PARTNER_CODE = "BBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private PgApplicationRepository pgApplicationRepository;

    @Autowired
    private PgApplicationMapper pgApplicationMapper;

    @Autowired
    private PgApplicationService pgApplicationService;

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

    private MockMvc restPgApplicationMockMvc;

    private PgApplication pgApplication;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PgApplicationResource pgApplicationResource = new PgApplicationResource(pgApplicationService);
        this.restPgApplicationMockMvc = MockMvcBuilders.standaloneSetup(pgApplicationResource)
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
    public static PgApplication createEntity(EntityManager em) {
        PgApplication pgApplication = new PgApplication()
            .code(DEFAULT_CODE)
            .label(DEFAULT_LABEL)
            .partnerCode(DEFAULT_PARTNER_CODE)
            .active(DEFAULT_ACTIVE);
        return pgApplication;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PgApplication createUpdatedEntity(EntityManager em) {
        PgApplication pgApplication = new PgApplication()
            .code(UPDATED_CODE)
            .label(UPDATED_LABEL)
            .partnerCode(UPDATED_PARTNER_CODE)
            .active(UPDATED_ACTIVE);
        return pgApplication;
    }

    @BeforeEach
    public void initTest() {
        pgApplication = createEntity(em);
    }

    @Test
    @Transactional
    public void createPgApplication() throws Exception {
        int databaseSizeBeforeCreate = pgApplicationRepository.findAll().size();

        // Create the PgApplication
        PgApplicationDTO pgApplicationDTO = pgApplicationMapper.toDto(pgApplication);
        restPgApplicationMockMvc.perform(post("/api/pg-applications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgApplicationDTO)))
            .andExpect(status().isCreated());

        // Validate the PgApplication in the database
        List<PgApplication> pgApplicationList = pgApplicationRepository.findAll();
        assertThat(pgApplicationList).hasSize(databaseSizeBeforeCreate + 1);
        PgApplication testPgApplication = pgApplicationList.get(pgApplicationList.size() - 1);
        assertThat(testPgApplication.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testPgApplication.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testPgApplication.getPartnerCode()).isEqualTo(DEFAULT_PARTNER_CODE);
        assertThat(testPgApplication.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createPgApplicationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pgApplicationRepository.findAll().size();

        // Create the PgApplication with an existing ID
        pgApplication.setId(1L);
        PgApplicationDTO pgApplicationDTO = pgApplicationMapper.toDto(pgApplication);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPgApplicationMockMvc.perform(post("/api/pg-applications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgApplicationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PgApplication in the database
        List<PgApplication> pgApplicationList = pgApplicationRepository.findAll();
        assertThat(pgApplicationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgApplicationRepository.findAll().size();
        // set the field null
        pgApplication.setCode(null);

        // Create the PgApplication, which fails.
        PgApplicationDTO pgApplicationDTO = pgApplicationMapper.toDto(pgApplication);

        restPgApplicationMockMvc.perform(post("/api/pg-applications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgApplicationDTO)))
            .andExpect(status().isBadRequest());

        List<PgApplication> pgApplicationList = pgApplicationRepository.findAll();
        assertThat(pgApplicationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLabelIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgApplicationRepository.findAll().size();
        // set the field null
        pgApplication.setLabel(null);

        // Create the PgApplication, which fails.
        PgApplicationDTO pgApplicationDTO = pgApplicationMapper.toDto(pgApplication);

        restPgApplicationMockMvc.perform(post("/api/pg-applications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgApplicationDTO)))
            .andExpect(status().isBadRequest());

        List<PgApplication> pgApplicationList = pgApplicationRepository.findAll();
        assertThat(pgApplicationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgApplicationRepository.findAll().size();
        // set the field null
        pgApplication.setActive(null);

        // Create the PgApplication, which fails.
        PgApplicationDTO pgApplicationDTO = pgApplicationMapper.toDto(pgApplication);

        restPgApplicationMockMvc.perform(post("/api/pg-applications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgApplicationDTO)))
            .andExpect(status().isBadRequest());

        List<PgApplication> pgApplicationList = pgApplicationRepository.findAll();
        assertThat(pgApplicationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPgApplications() throws Exception {
        // Initialize the database
        pgApplicationRepository.saveAndFlush(pgApplication);

        // Get all the pgApplicationList
        restPgApplicationMockMvc.perform(get("/api/pg-applications?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pgApplication.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())))
            .andExpect(jsonPath("$.[*].partnerCode").value(hasItem(DEFAULT_PARTNER_CODE.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getPgApplication() throws Exception {
        // Initialize the database
        pgApplicationRepository.saveAndFlush(pgApplication);

        // Get the pgApplication
        restPgApplicationMockMvc.perform(get("/api/pg-applications/{id}", pgApplication.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pgApplication.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()))
            .andExpect(jsonPath("$.partnerCode").value(DEFAULT_PARTNER_CODE.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPgApplication() throws Exception {
        // Get the pgApplication
        restPgApplicationMockMvc.perform(get("/api/pg-applications/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePgApplication() throws Exception {
        // Initialize the database
        pgApplicationRepository.saveAndFlush(pgApplication);

        int databaseSizeBeforeUpdate = pgApplicationRepository.findAll().size();

        // Update the pgApplication
        PgApplication updatedPgApplication = pgApplicationRepository.findById(pgApplication.getId()).get();
        // Disconnect from session so that the updates on updatedPgApplication are not directly saved in db
        em.detach(updatedPgApplication);
        updatedPgApplication
            .code(UPDATED_CODE)
            .label(UPDATED_LABEL)
            .partnerCode(UPDATED_PARTNER_CODE)
            .active(UPDATED_ACTIVE);
        PgApplicationDTO pgApplicationDTO = pgApplicationMapper.toDto(updatedPgApplication);

        restPgApplicationMockMvc.perform(put("/api/pg-applications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgApplicationDTO)))
            .andExpect(status().isOk());

        // Validate the PgApplication in the database
        List<PgApplication> pgApplicationList = pgApplicationRepository.findAll();
        assertThat(pgApplicationList).hasSize(databaseSizeBeforeUpdate);
        PgApplication testPgApplication = pgApplicationList.get(pgApplicationList.size() - 1);
        assertThat(testPgApplication.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testPgApplication.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testPgApplication.getPartnerCode()).isEqualTo(UPDATED_PARTNER_CODE);
        assertThat(testPgApplication.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingPgApplication() throws Exception {
        int databaseSizeBeforeUpdate = pgApplicationRepository.findAll().size();

        // Create the PgApplication
        PgApplicationDTO pgApplicationDTO = pgApplicationMapper.toDto(pgApplication);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPgApplicationMockMvc.perform(put("/api/pg-applications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgApplicationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PgApplication in the database
        List<PgApplication> pgApplicationList = pgApplicationRepository.findAll();
        assertThat(pgApplicationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePgApplication() throws Exception {
        // Initialize the database
        pgApplicationRepository.saveAndFlush(pgApplication);

        int databaseSizeBeforeDelete = pgApplicationRepository.findAll().size();

        // Delete the pgApplication
        restPgApplicationMockMvc.perform(delete("/api/pg-applications/{id}", pgApplication.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PgApplication> pgApplicationList = pgApplicationRepository.findAll();
        assertThat(pgApplicationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PgApplication.class);
        PgApplication pgApplication1 = new PgApplication();
        pgApplication1.setId(1L);
        PgApplication pgApplication2 = new PgApplication();
        pgApplication2.setId(pgApplication1.getId());
        assertThat(pgApplication1).isEqualTo(pgApplication2);
        pgApplication2.setId(2L);
        assertThat(pgApplication1).isNotEqualTo(pgApplication2);
        pgApplication1.setId(null);
        assertThat(pgApplication1).isNotEqualTo(pgApplication2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PgApplicationDTO.class);
        PgApplicationDTO pgApplicationDTO1 = new PgApplicationDTO();
        pgApplicationDTO1.setId(1L);
        PgApplicationDTO pgApplicationDTO2 = new PgApplicationDTO();
        assertThat(pgApplicationDTO1).isNotEqualTo(pgApplicationDTO2);
        pgApplicationDTO2.setId(pgApplicationDTO1.getId());
        assertThat(pgApplicationDTO1).isEqualTo(pgApplicationDTO2);
        pgApplicationDTO2.setId(2L);
        assertThat(pgApplicationDTO1).isNotEqualTo(pgApplicationDTO2);
        pgApplicationDTO1.setId(null);
        assertThat(pgApplicationDTO1).isNotEqualTo(pgApplicationDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(pgApplicationMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(pgApplicationMapper.fromId(null)).isNull();
    }
}
