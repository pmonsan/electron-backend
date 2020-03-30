package com.electron.mfs.pg.gateway.web.rest;

import com.electron.mfs.pg.gateway.PgkernelgatewayApp;
import com.electron.mfs.pg.gateway.config.SecurityBeanOverrideConfiguration;
import com.electron.mfs.pg.gateway.domain.PgBatch;
import com.electron.mfs.pg.gateway.repository.PgBatchRepository;
import com.electron.mfs.pg.gateway.service.PgBatchService;
import com.electron.mfs.pg.gateway.service.dto.PgBatchDTO;
import com.electron.mfs.pg.gateway.service.mapper.PgBatchMapper;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.electron.mfs.pg.gateway.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link PgBatchResource} REST controller.
 */
@EmbeddedKafka
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, PgkernelgatewayApp.class})
public class PgBatchResourceIT {

    private static final String DEFAULT_CODE = "AAAAA";
    private static final String UPDATED_CODE = "BBBBB";

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final Instant DEFAULT_EXPECTED_END_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_EXPECTED_END_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_BATCH_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_BATCH_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private PgBatchRepository pgBatchRepository;

    @Autowired
    private PgBatchMapper pgBatchMapper;

    @Autowired
    private PgBatchService pgBatchService;

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

    private MockMvc restPgBatchMockMvc;

    private PgBatch pgBatch;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PgBatchResource pgBatchResource = new PgBatchResource(pgBatchService);
        this.restPgBatchMockMvc = MockMvcBuilders.standaloneSetup(pgBatchResource)
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
    public static PgBatch createEntity(EntityManager em) {
        PgBatch pgBatch = new PgBatch()
            .code(DEFAULT_CODE)
            .label(DEFAULT_LABEL)
            .expectedEndDate(DEFAULT_EXPECTED_END_DATE)
            .batchDate(DEFAULT_BATCH_DATE)
            .active(DEFAULT_ACTIVE);
        return pgBatch;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PgBatch createUpdatedEntity(EntityManager em) {
        PgBatch pgBatch = new PgBatch()
            .code(UPDATED_CODE)
            .label(UPDATED_LABEL)
            .expectedEndDate(UPDATED_EXPECTED_END_DATE)
            .batchDate(UPDATED_BATCH_DATE)
            .active(UPDATED_ACTIVE);
        return pgBatch;
    }

    @BeforeEach
    public void initTest() {
        pgBatch = createEntity(em);
    }

    @Test
    @Transactional
    public void createPgBatch() throws Exception {
        int databaseSizeBeforeCreate = pgBatchRepository.findAll().size();

        // Create the PgBatch
        PgBatchDTO pgBatchDTO = pgBatchMapper.toDto(pgBatch);
        restPgBatchMockMvc.perform(post("/api/pg-batches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgBatchDTO)))
            .andExpect(status().isCreated());

        // Validate the PgBatch in the database
        List<PgBatch> pgBatchList = pgBatchRepository.findAll();
        assertThat(pgBatchList).hasSize(databaseSizeBeforeCreate + 1);
        PgBatch testPgBatch = pgBatchList.get(pgBatchList.size() - 1);
        assertThat(testPgBatch.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testPgBatch.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testPgBatch.getExpectedEndDate()).isEqualTo(DEFAULT_EXPECTED_END_DATE);
        assertThat(testPgBatch.getBatchDate()).isEqualTo(DEFAULT_BATCH_DATE);
        assertThat(testPgBatch.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createPgBatchWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pgBatchRepository.findAll().size();

        // Create the PgBatch with an existing ID
        pgBatch.setId(1L);
        PgBatchDTO pgBatchDTO = pgBatchMapper.toDto(pgBatch);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPgBatchMockMvc.perform(post("/api/pg-batches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgBatchDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PgBatch in the database
        List<PgBatch> pgBatchList = pgBatchRepository.findAll();
        assertThat(pgBatchList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgBatchRepository.findAll().size();
        // set the field null
        pgBatch.setCode(null);

        // Create the PgBatch, which fails.
        PgBatchDTO pgBatchDTO = pgBatchMapper.toDto(pgBatch);

        restPgBatchMockMvc.perform(post("/api/pg-batches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgBatchDTO)))
            .andExpect(status().isBadRequest());

        List<PgBatch> pgBatchList = pgBatchRepository.findAll();
        assertThat(pgBatchList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkExpectedEndDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgBatchRepository.findAll().size();
        // set the field null
        pgBatch.setExpectedEndDate(null);

        // Create the PgBatch, which fails.
        PgBatchDTO pgBatchDTO = pgBatchMapper.toDto(pgBatch);

        restPgBatchMockMvc.perform(post("/api/pg-batches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgBatchDTO)))
            .andExpect(status().isBadRequest());

        List<PgBatch> pgBatchList = pgBatchRepository.findAll();
        assertThat(pgBatchList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBatchDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgBatchRepository.findAll().size();
        // set the field null
        pgBatch.setBatchDate(null);

        // Create the PgBatch, which fails.
        PgBatchDTO pgBatchDTO = pgBatchMapper.toDto(pgBatch);

        restPgBatchMockMvc.perform(post("/api/pg-batches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgBatchDTO)))
            .andExpect(status().isBadRequest());

        List<PgBatch> pgBatchList = pgBatchRepository.findAll();
        assertThat(pgBatchList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgBatchRepository.findAll().size();
        // set the field null
        pgBatch.setActive(null);

        // Create the PgBatch, which fails.
        PgBatchDTO pgBatchDTO = pgBatchMapper.toDto(pgBatch);

        restPgBatchMockMvc.perform(post("/api/pg-batches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgBatchDTO)))
            .andExpect(status().isBadRequest());

        List<PgBatch> pgBatchList = pgBatchRepository.findAll();
        assertThat(pgBatchList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPgBatches() throws Exception {
        // Initialize the database
        pgBatchRepository.saveAndFlush(pgBatch);

        // Get all the pgBatchList
        restPgBatchMockMvc.perform(get("/api/pg-batches?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pgBatch.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())))
            .andExpect(jsonPath("$.[*].expectedEndDate").value(hasItem(DEFAULT_EXPECTED_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].batchDate").value(hasItem(DEFAULT_BATCH_DATE.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getPgBatch() throws Exception {
        // Initialize the database
        pgBatchRepository.saveAndFlush(pgBatch);

        // Get the pgBatch
        restPgBatchMockMvc.perform(get("/api/pg-batches/{id}", pgBatch.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pgBatch.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()))
            .andExpect(jsonPath("$.expectedEndDate").value(DEFAULT_EXPECTED_END_DATE.toString()))
            .andExpect(jsonPath("$.batchDate").value(DEFAULT_BATCH_DATE.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPgBatch() throws Exception {
        // Get the pgBatch
        restPgBatchMockMvc.perform(get("/api/pg-batches/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePgBatch() throws Exception {
        // Initialize the database
        pgBatchRepository.saveAndFlush(pgBatch);

        int databaseSizeBeforeUpdate = pgBatchRepository.findAll().size();

        // Update the pgBatch
        PgBatch updatedPgBatch = pgBatchRepository.findById(pgBatch.getId()).get();
        // Disconnect from session so that the updates on updatedPgBatch are not directly saved in db
        em.detach(updatedPgBatch);
        updatedPgBatch
            .code(UPDATED_CODE)
            .label(UPDATED_LABEL)
            .expectedEndDate(UPDATED_EXPECTED_END_DATE)
            .batchDate(UPDATED_BATCH_DATE)
            .active(UPDATED_ACTIVE);
        PgBatchDTO pgBatchDTO = pgBatchMapper.toDto(updatedPgBatch);

        restPgBatchMockMvc.perform(put("/api/pg-batches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgBatchDTO)))
            .andExpect(status().isOk());

        // Validate the PgBatch in the database
        List<PgBatch> pgBatchList = pgBatchRepository.findAll();
        assertThat(pgBatchList).hasSize(databaseSizeBeforeUpdate);
        PgBatch testPgBatch = pgBatchList.get(pgBatchList.size() - 1);
        assertThat(testPgBatch.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testPgBatch.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testPgBatch.getExpectedEndDate()).isEqualTo(UPDATED_EXPECTED_END_DATE);
        assertThat(testPgBatch.getBatchDate()).isEqualTo(UPDATED_BATCH_DATE);
        assertThat(testPgBatch.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingPgBatch() throws Exception {
        int databaseSizeBeforeUpdate = pgBatchRepository.findAll().size();

        // Create the PgBatch
        PgBatchDTO pgBatchDTO = pgBatchMapper.toDto(pgBatch);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPgBatchMockMvc.perform(put("/api/pg-batches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgBatchDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PgBatch in the database
        List<PgBatch> pgBatchList = pgBatchRepository.findAll();
        assertThat(pgBatchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePgBatch() throws Exception {
        // Initialize the database
        pgBatchRepository.saveAndFlush(pgBatch);

        int databaseSizeBeforeDelete = pgBatchRepository.findAll().size();

        // Delete the pgBatch
        restPgBatchMockMvc.perform(delete("/api/pg-batches/{id}", pgBatch.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PgBatch> pgBatchList = pgBatchRepository.findAll();
        assertThat(pgBatchList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PgBatch.class);
        PgBatch pgBatch1 = new PgBatch();
        pgBatch1.setId(1L);
        PgBatch pgBatch2 = new PgBatch();
        pgBatch2.setId(pgBatch1.getId());
        assertThat(pgBatch1).isEqualTo(pgBatch2);
        pgBatch2.setId(2L);
        assertThat(pgBatch1).isNotEqualTo(pgBatch2);
        pgBatch1.setId(null);
        assertThat(pgBatch1).isNotEqualTo(pgBatch2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PgBatchDTO.class);
        PgBatchDTO pgBatchDTO1 = new PgBatchDTO();
        pgBatchDTO1.setId(1L);
        PgBatchDTO pgBatchDTO2 = new PgBatchDTO();
        assertThat(pgBatchDTO1).isNotEqualTo(pgBatchDTO2);
        pgBatchDTO2.setId(pgBatchDTO1.getId());
        assertThat(pgBatchDTO1).isEqualTo(pgBatchDTO2);
        pgBatchDTO2.setId(2L);
        assertThat(pgBatchDTO1).isNotEqualTo(pgBatchDTO2);
        pgBatchDTO1.setId(null);
        assertThat(pgBatchDTO1).isNotEqualTo(pgBatchDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(pgBatchMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(pgBatchMapper.fromId(null)).isNull();
    }
}
