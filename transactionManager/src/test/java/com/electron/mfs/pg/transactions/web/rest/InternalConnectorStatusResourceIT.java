package com.electron.mfs.pg.transactions.web.rest;

import com.electron.mfs.pg.transactions.TransactionManagerApp;
import com.electron.mfs.pg.transactions.config.SecurityBeanOverrideConfiguration;
import com.electron.mfs.pg.transactions.domain.InternalConnectorStatus;
import com.electron.mfs.pg.transactions.repository.InternalConnectorStatusRepository;
import com.electron.mfs.pg.transactions.service.InternalConnectorStatusService;
import com.electron.mfs.pg.transactions.service.dto.InternalConnectorStatusDTO;
import com.electron.mfs.pg.transactions.service.mapper.InternalConnectorStatusMapper;
import com.electron.mfs.pg.transactions.web.rest.errors.ExceptionTranslator;

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

import static com.electron.mfs.pg.transactions.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link InternalConnectorStatusResource} REST controller.
 */
@EmbeddedKafka
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, TransactionManagerApp.class})
public class InternalConnectorStatusResourceIT {

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_DEFAULT_REASON = "AAAAAAAAAA";
    private static final String UPDATED_DEFAULT_REASON = "BBBBBBBBBB";

    @Autowired
    private InternalConnectorStatusRepository internalConnectorStatusRepository;

    @Autowired
    private InternalConnectorStatusMapper internalConnectorStatusMapper;

    @Autowired
    private InternalConnectorStatusService internalConnectorStatusService;

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

    private MockMvc restInternalConnectorStatusMockMvc;

    private InternalConnectorStatus internalConnectorStatus;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InternalConnectorStatusResource internalConnectorStatusResource = new InternalConnectorStatusResource(internalConnectorStatusService);
        this.restInternalConnectorStatusMockMvc = MockMvcBuilders.standaloneSetup(internalConnectorStatusResource)
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
    public static InternalConnectorStatus createEntity(EntityManager em) {
        InternalConnectorStatus internalConnectorStatus = new InternalConnectorStatus()
            .status(DEFAULT_STATUS)
            .defaultReason(DEFAULT_DEFAULT_REASON);
        return internalConnectorStatus;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InternalConnectorStatus createUpdatedEntity(EntityManager em) {
        InternalConnectorStatus internalConnectorStatus = new InternalConnectorStatus()
            .status(UPDATED_STATUS)
            .defaultReason(UPDATED_DEFAULT_REASON);
        return internalConnectorStatus;
    }

    @BeforeEach
    public void initTest() {
        internalConnectorStatus = createEntity(em);
    }

    @Test
    @Transactional
    public void createInternalConnectorStatus() throws Exception {
        int databaseSizeBeforeCreate = internalConnectorStatusRepository.findAll().size();

        // Create the InternalConnectorStatus
        InternalConnectorStatusDTO internalConnectorStatusDTO = internalConnectorStatusMapper.toDto(internalConnectorStatus);
        restInternalConnectorStatusMockMvc.perform(post("/api/internal-connector-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(internalConnectorStatusDTO)))
            .andExpect(status().isCreated());

        // Validate the InternalConnectorStatus in the database
        List<InternalConnectorStatus> internalConnectorStatusList = internalConnectorStatusRepository.findAll();
        assertThat(internalConnectorStatusList).hasSize(databaseSizeBeforeCreate + 1);
        InternalConnectorStatus testInternalConnectorStatus = internalConnectorStatusList.get(internalConnectorStatusList.size() - 1);
        assertThat(testInternalConnectorStatus.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testInternalConnectorStatus.getDefaultReason()).isEqualTo(DEFAULT_DEFAULT_REASON);
    }

    @Test
    @Transactional
    public void createInternalConnectorStatusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = internalConnectorStatusRepository.findAll().size();

        // Create the InternalConnectorStatus with an existing ID
        internalConnectorStatus.setId(1L);
        InternalConnectorStatusDTO internalConnectorStatusDTO = internalConnectorStatusMapper.toDto(internalConnectorStatus);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInternalConnectorStatusMockMvc.perform(post("/api/internal-connector-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(internalConnectorStatusDTO)))
            .andExpect(status().isBadRequest());

        // Validate the InternalConnectorStatus in the database
        List<InternalConnectorStatus> internalConnectorStatusList = internalConnectorStatusRepository.findAll();
        assertThat(internalConnectorStatusList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = internalConnectorStatusRepository.findAll().size();
        // set the field null
        internalConnectorStatus.setStatus(null);

        // Create the InternalConnectorStatus, which fails.
        InternalConnectorStatusDTO internalConnectorStatusDTO = internalConnectorStatusMapper.toDto(internalConnectorStatus);

        restInternalConnectorStatusMockMvc.perform(post("/api/internal-connector-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(internalConnectorStatusDTO)))
            .andExpect(status().isBadRequest());

        List<InternalConnectorStatus> internalConnectorStatusList = internalConnectorStatusRepository.findAll();
        assertThat(internalConnectorStatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllInternalConnectorStatuses() throws Exception {
        // Initialize the database
        internalConnectorStatusRepository.saveAndFlush(internalConnectorStatus);

        // Get all the internalConnectorStatusList
        restInternalConnectorStatusMockMvc.perform(get("/api/internal-connector-statuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(internalConnectorStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].defaultReason").value(hasItem(DEFAULT_DEFAULT_REASON.toString())));
    }
    
    @Test
    @Transactional
    public void getInternalConnectorStatus() throws Exception {
        // Initialize the database
        internalConnectorStatusRepository.saveAndFlush(internalConnectorStatus);

        // Get the internalConnectorStatus
        restInternalConnectorStatusMockMvc.perform(get("/api/internal-connector-statuses/{id}", internalConnectorStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(internalConnectorStatus.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.defaultReason").value(DEFAULT_DEFAULT_REASON.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingInternalConnectorStatus() throws Exception {
        // Get the internalConnectorStatus
        restInternalConnectorStatusMockMvc.perform(get("/api/internal-connector-statuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInternalConnectorStatus() throws Exception {
        // Initialize the database
        internalConnectorStatusRepository.saveAndFlush(internalConnectorStatus);

        int databaseSizeBeforeUpdate = internalConnectorStatusRepository.findAll().size();

        // Update the internalConnectorStatus
        InternalConnectorStatus updatedInternalConnectorStatus = internalConnectorStatusRepository.findById(internalConnectorStatus.getId()).get();
        // Disconnect from session so that the updates on updatedInternalConnectorStatus are not directly saved in db
        em.detach(updatedInternalConnectorStatus);
        updatedInternalConnectorStatus
            .status(UPDATED_STATUS)
            .defaultReason(UPDATED_DEFAULT_REASON);
        InternalConnectorStatusDTO internalConnectorStatusDTO = internalConnectorStatusMapper.toDto(updatedInternalConnectorStatus);

        restInternalConnectorStatusMockMvc.perform(put("/api/internal-connector-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(internalConnectorStatusDTO)))
            .andExpect(status().isOk());

        // Validate the InternalConnectorStatus in the database
        List<InternalConnectorStatus> internalConnectorStatusList = internalConnectorStatusRepository.findAll();
        assertThat(internalConnectorStatusList).hasSize(databaseSizeBeforeUpdate);
        InternalConnectorStatus testInternalConnectorStatus = internalConnectorStatusList.get(internalConnectorStatusList.size() - 1);
        assertThat(testInternalConnectorStatus.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testInternalConnectorStatus.getDefaultReason()).isEqualTo(UPDATED_DEFAULT_REASON);
    }

    @Test
    @Transactional
    public void updateNonExistingInternalConnectorStatus() throws Exception {
        int databaseSizeBeforeUpdate = internalConnectorStatusRepository.findAll().size();

        // Create the InternalConnectorStatus
        InternalConnectorStatusDTO internalConnectorStatusDTO = internalConnectorStatusMapper.toDto(internalConnectorStatus);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInternalConnectorStatusMockMvc.perform(put("/api/internal-connector-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(internalConnectorStatusDTO)))
            .andExpect(status().isBadRequest());

        // Validate the InternalConnectorStatus in the database
        List<InternalConnectorStatus> internalConnectorStatusList = internalConnectorStatusRepository.findAll();
        assertThat(internalConnectorStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInternalConnectorStatus() throws Exception {
        // Initialize the database
        internalConnectorStatusRepository.saveAndFlush(internalConnectorStatus);

        int databaseSizeBeforeDelete = internalConnectorStatusRepository.findAll().size();

        // Delete the internalConnectorStatus
        restInternalConnectorStatusMockMvc.perform(delete("/api/internal-connector-statuses/{id}", internalConnectorStatus.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<InternalConnectorStatus> internalConnectorStatusList = internalConnectorStatusRepository.findAll();
        assertThat(internalConnectorStatusList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InternalConnectorStatus.class);
        InternalConnectorStatus internalConnectorStatus1 = new InternalConnectorStatus();
        internalConnectorStatus1.setId(1L);
        InternalConnectorStatus internalConnectorStatus2 = new InternalConnectorStatus();
        internalConnectorStatus2.setId(internalConnectorStatus1.getId());
        assertThat(internalConnectorStatus1).isEqualTo(internalConnectorStatus2);
        internalConnectorStatus2.setId(2L);
        assertThat(internalConnectorStatus1).isNotEqualTo(internalConnectorStatus2);
        internalConnectorStatus1.setId(null);
        assertThat(internalConnectorStatus1).isNotEqualTo(internalConnectorStatus2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InternalConnectorStatusDTO.class);
        InternalConnectorStatusDTO internalConnectorStatusDTO1 = new InternalConnectorStatusDTO();
        internalConnectorStatusDTO1.setId(1L);
        InternalConnectorStatusDTO internalConnectorStatusDTO2 = new InternalConnectorStatusDTO();
        assertThat(internalConnectorStatusDTO1).isNotEqualTo(internalConnectorStatusDTO2);
        internalConnectorStatusDTO2.setId(internalConnectorStatusDTO1.getId());
        assertThat(internalConnectorStatusDTO1).isEqualTo(internalConnectorStatusDTO2);
        internalConnectorStatusDTO2.setId(2L);
        assertThat(internalConnectorStatusDTO1).isNotEqualTo(internalConnectorStatusDTO2);
        internalConnectorStatusDTO1.setId(null);
        assertThat(internalConnectorStatusDTO1).isNotEqualTo(internalConnectorStatusDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(internalConnectorStatusMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(internalConnectorStatusMapper.fromId(null)).isNull();
    }
}
