package com.electron.mfs.pg.gateway.web.rest;

import com.electron.mfs.pg.gateway.PgkernelgatewayApp;
import com.electron.mfs.pg.gateway.config.SecurityBeanOverrideConfiguration;
import com.electron.mfs.pg.gateway.domain.PgMessageStatus;
import com.electron.mfs.pg.gateway.repository.PgMessageStatusRepository;
import com.electron.mfs.pg.gateway.service.PgMessageStatusService;
import com.electron.mfs.pg.gateway.service.dto.PgMessageStatusDTO;
import com.electron.mfs.pg.gateway.service.mapper.PgMessageStatusMapper;
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
 * Integration tests for the {@Link PgMessageStatusResource} REST controller.
 */
@EmbeddedKafka
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, PgkernelgatewayApp.class})
public class PgMessageStatusResourceIT {

    private static final String DEFAULT_LABEL = "AAAAA";
    private static final String UPDATED_LABEL = "BBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private PgMessageStatusRepository pgMessageStatusRepository;

    @Autowired
    private PgMessageStatusMapper pgMessageStatusMapper;

    @Autowired
    private PgMessageStatusService pgMessageStatusService;

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

    private MockMvc restPgMessageStatusMockMvc;

    private PgMessageStatus pgMessageStatus;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PgMessageStatusResource pgMessageStatusResource = new PgMessageStatusResource(pgMessageStatusService);
        this.restPgMessageStatusMockMvc = MockMvcBuilders.standaloneSetup(pgMessageStatusResource)
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
    public static PgMessageStatus createEntity(EntityManager em) {
        PgMessageStatus pgMessageStatus = new PgMessageStatus()
            .label(DEFAULT_LABEL)
            .active(DEFAULT_ACTIVE);
        return pgMessageStatus;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PgMessageStatus createUpdatedEntity(EntityManager em) {
        PgMessageStatus pgMessageStatus = new PgMessageStatus()
            .label(UPDATED_LABEL)
            .active(UPDATED_ACTIVE);
        return pgMessageStatus;
    }

    @BeforeEach
    public void initTest() {
        pgMessageStatus = createEntity(em);
    }

    @Test
    @Transactional
    public void createPgMessageStatus() throws Exception {
        int databaseSizeBeforeCreate = pgMessageStatusRepository.findAll().size();

        // Create the PgMessageStatus
        PgMessageStatusDTO pgMessageStatusDTO = pgMessageStatusMapper.toDto(pgMessageStatus);
        restPgMessageStatusMockMvc.perform(post("/api/pg-message-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgMessageStatusDTO)))
            .andExpect(status().isCreated());

        // Validate the PgMessageStatus in the database
        List<PgMessageStatus> pgMessageStatusList = pgMessageStatusRepository.findAll();
        assertThat(pgMessageStatusList).hasSize(databaseSizeBeforeCreate + 1);
        PgMessageStatus testPgMessageStatus = pgMessageStatusList.get(pgMessageStatusList.size() - 1);
        assertThat(testPgMessageStatus.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testPgMessageStatus.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createPgMessageStatusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pgMessageStatusRepository.findAll().size();

        // Create the PgMessageStatus with an existing ID
        pgMessageStatus.setId(1L);
        PgMessageStatusDTO pgMessageStatusDTO = pgMessageStatusMapper.toDto(pgMessageStatus);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPgMessageStatusMockMvc.perform(post("/api/pg-message-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgMessageStatusDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PgMessageStatus in the database
        List<PgMessageStatus> pgMessageStatusList = pgMessageStatusRepository.findAll();
        assertThat(pgMessageStatusList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkLabelIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgMessageStatusRepository.findAll().size();
        // set the field null
        pgMessageStatus.setLabel(null);

        // Create the PgMessageStatus, which fails.
        PgMessageStatusDTO pgMessageStatusDTO = pgMessageStatusMapper.toDto(pgMessageStatus);

        restPgMessageStatusMockMvc.perform(post("/api/pg-message-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgMessageStatusDTO)))
            .andExpect(status().isBadRequest());

        List<PgMessageStatus> pgMessageStatusList = pgMessageStatusRepository.findAll();
        assertThat(pgMessageStatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgMessageStatusRepository.findAll().size();
        // set the field null
        pgMessageStatus.setActive(null);

        // Create the PgMessageStatus, which fails.
        PgMessageStatusDTO pgMessageStatusDTO = pgMessageStatusMapper.toDto(pgMessageStatus);

        restPgMessageStatusMockMvc.perform(post("/api/pg-message-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgMessageStatusDTO)))
            .andExpect(status().isBadRequest());

        List<PgMessageStatus> pgMessageStatusList = pgMessageStatusRepository.findAll();
        assertThat(pgMessageStatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPgMessageStatuses() throws Exception {
        // Initialize the database
        pgMessageStatusRepository.saveAndFlush(pgMessageStatus);

        // Get all the pgMessageStatusList
        restPgMessageStatusMockMvc.perform(get("/api/pg-message-statuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pgMessageStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getPgMessageStatus() throws Exception {
        // Initialize the database
        pgMessageStatusRepository.saveAndFlush(pgMessageStatus);

        // Get the pgMessageStatus
        restPgMessageStatusMockMvc.perform(get("/api/pg-message-statuses/{id}", pgMessageStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pgMessageStatus.getId().intValue()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPgMessageStatus() throws Exception {
        // Get the pgMessageStatus
        restPgMessageStatusMockMvc.perform(get("/api/pg-message-statuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePgMessageStatus() throws Exception {
        // Initialize the database
        pgMessageStatusRepository.saveAndFlush(pgMessageStatus);

        int databaseSizeBeforeUpdate = pgMessageStatusRepository.findAll().size();

        // Update the pgMessageStatus
        PgMessageStatus updatedPgMessageStatus = pgMessageStatusRepository.findById(pgMessageStatus.getId()).get();
        // Disconnect from session so that the updates on updatedPgMessageStatus are not directly saved in db
        em.detach(updatedPgMessageStatus);
        updatedPgMessageStatus
            .label(UPDATED_LABEL)
            .active(UPDATED_ACTIVE);
        PgMessageStatusDTO pgMessageStatusDTO = pgMessageStatusMapper.toDto(updatedPgMessageStatus);

        restPgMessageStatusMockMvc.perform(put("/api/pg-message-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgMessageStatusDTO)))
            .andExpect(status().isOk());

        // Validate the PgMessageStatus in the database
        List<PgMessageStatus> pgMessageStatusList = pgMessageStatusRepository.findAll();
        assertThat(pgMessageStatusList).hasSize(databaseSizeBeforeUpdate);
        PgMessageStatus testPgMessageStatus = pgMessageStatusList.get(pgMessageStatusList.size() - 1);
        assertThat(testPgMessageStatus.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testPgMessageStatus.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingPgMessageStatus() throws Exception {
        int databaseSizeBeforeUpdate = pgMessageStatusRepository.findAll().size();

        // Create the PgMessageStatus
        PgMessageStatusDTO pgMessageStatusDTO = pgMessageStatusMapper.toDto(pgMessageStatus);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPgMessageStatusMockMvc.perform(put("/api/pg-message-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgMessageStatusDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PgMessageStatus in the database
        List<PgMessageStatus> pgMessageStatusList = pgMessageStatusRepository.findAll();
        assertThat(pgMessageStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePgMessageStatus() throws Exception {
        // Initialize the database
        pgMessageStatusRepository.saveAndFlush(pgMessageStatus);

        int databaseSizeBeforeDelete = pgMessageStatusRepository.findAll().size();

        // Delete the pgMessageStatus
        restPgMessageStatusMockMvc.perform(delete("/api/pg-message-statuses/{id}", pgMessageStatus.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PgMessageStatus> pgMessageStatusList = pgMessageStatusRepository.findAll();
        assertThat(pgMessageStatusList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PgMessageStatus.class);
        PgMessageStatus pgMessageStatus1 = new PgMessageStatus();
        pgMessageStatus1.setId(1L);
        PgMessageStatus pgMessageStatus2 = new PgMessageStatus();
        pgMessageStatus2.setId(pgMessageStatus1.getId());
        assertThat(pgMessageStatus1).isEqualTo(pgMessageStatus2);
        pgMessageStatus2.setId(2L);
        assertThat(pgMessageStatus1).isNotEqualTo(pgMessageStatus2);
        pgMessageStatus1.setId(null);
        assertThat(pgMessageStatus1).isNotEqualTo(pgMessageStatus2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PgMessageStatusDTO.class);
        PgMessageStatusDTO pgMessageStatusDTO1 = new PgMessageStatusDTO();
        pgMessageStatusDTO1.setId(1L);
        PgMessageStatusDTO pgMessageStatusDTO2 = new PgMessageStatusDTO();
        assertThat(pgMessageStatusDTO1).isNotEqualTo(pgMessageStatusDTO2);
        pgMessageStatusDTO2.setId(pgMessageStatusDTO1.getId());
        assertThat(pgMessageStatusDTO1).isEqualTo(pgMessageStatusDTO2);
        pgMessageStatusDTO2.setId(2L);
        assertThat(pgMessageStatusDTO1).isNotEqualTo(pgMessageStatusDTO2);
        pgMessageStatusDTO1.setId(null);
        assertThat(pgMessageStatusDTO1).isNotEqualTo(pgMessageStatusDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(pgMessageStatusMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(pgMessageStatusMapper.fromId(null)).isNull();
    }
}
