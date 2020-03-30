package com.electron.mfs.pg.gateway.web.rest;

import com.electron.mfs.pg.gateway.PgkernelgatewayApp;
import com.electron.mfs.pg.gateway.config.SecurityBeanOverrideConfiguration;
import com.electron.mfs.pg.gateway.domain.TransactionStatus;
import com.electron.mfs.pg.gateway.repository.TransactionStatusRepository;
import com.electron.mfs.pg.gateway.service.TransactionStatusService;
import com.electron.mfs.pg.gateway.service.dto.TransactionStatusDTO;
import com.electron.mfs.pg.gateway.service.mapper.TransactionStatusMapper;
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
 * Integration tests for the {@Link TransactionStatusResource} REST controller.
 */
@EmbeddedKafka
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, PgkernelgatewayApp.class})
public class TransactionStatusResourceIT {

    private static final String DEFAULT_CODE = "AAAAA";
    private static final String UPDATED_CODE = "BBBBB";

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private TransactionStatusRepository transactionStatusRepository;

    @Autowired
    private TransactionStatusMapper transactionStatusMapper;

    @Autowired
    private TransactionStatusService transactionStatusService;

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

    private MockMvc restTransactionStatusMockMvc;

    private TransactionStatus transactionStatus;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TransactionStatusResource transactionStatusResource = new TransactionStatusResource(transactionStatusService);
        this.restTransactionStatusMockMvc = MockMvcBuilders.standaloneSetup(transactionStatusResource)
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
    public static TransactionStatus createEntity(EntityManager em) {
        TransactionStatus transactionStatus = new TransactionStatus()
            .code(DEFAULT_CODE)
            .label(DEFAULT_LABEL)
            .active(DEFAULT_ACTIVE);
        return transactionStatus;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TransactionStatus createUpdatedEntity(EntityManager em) {
        TransactionStatus transactionStatus = new TransactionStatus()
            .code(UPDATED_CODE)
            .label(UPDATED_LABEL)
            .active(UPDATED_ACTIVE);
        return transactionStatus;
    }

    @BeforeEach
    public void initTest() {
        transactionStatus = createEntity(em);
    }

    @Test
    @Transactional
    public void createTransactionStatus() throws Exception {
        int databaseSizeBeforeCreate = transactionStatusRepository.findAll().size();

        // Create the TransactionStatus
        TransactionStatusDTO transactionStatusDTO = transactionStatusMapper.toDto(transactionStatus);
        restTransactionStatusMockMvc.perform(post("/api/transaction-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionStatusDTO)))
            .andExpect(status().isCreated());

        // Validate the TransactionStatus in the database
        List<TransactionStatus> transactionStatusList = transactionStatusRepository.findAll();
        assertThat(transactionStatusList).hasSize(databaseSizeBeforeCreate + 1);
        TransactionStatus testTransactionStatus = transactionStatusList.get(transactionStatusList.size() - 1);
        assertThat(testTransactionStatus.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testTransactionStatus.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testTransactionStatus.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createTransactionStatusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = transactionStatusRepository.findAll().size();

        // Create the TransactionStatus with an existing ID
        transactionStatus.setId(1L);
        TransactionStatusDTO transactionStatusDTO = transactionStatusMapper.toDto(transactionStatus);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransactionStatusMockMvc.perform(post("/api/transaction-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionStatusDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TransactionStatus in the database
        List<TransactionStatus> transactionStatusList = transactionStatusRepository.findAll();
        assertThat(transactionStatusList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionStatusRepository.findAll().size();
        // set the field null
        transactionStatus.setCode(null);

        // Create the TransactionStatus, which fails.
        TransactionStatusDTO transactionStatusDTO = transactionStatusMapper.toDto(transactionStatus);

        restTransactionStatusMockMvc.perform(post("/api/transaction-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionStatusDTO)))
            .andExpect(status().isBadRequest());

        List<TransactionStatus> transactionStatusList = transactionStatusRepository.findAll();
        assertThat(transactionStatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLabelIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionStatusRepository.findAll().size();
        // set the field null
        transactionStatus.setLabel(null);

        // Create the TransactionStatus, which fails.
        TransactionStatusDTO transactionStatusDTO = transactionStatusMapper.toDto(transactionStatus);

        restTransactionStatusMockMvc.perform(post("/api/transaction-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionStatusDTO)))
            .andExpect(status().isBadRequest());

        List<TransactionStatus> transactionStatusList = transactionStatusRepository.findAll();
        assertThat(transactionStatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionStatusRepository.findAll().size();
        // set the field null
        transactionStatus.setActive(null);

        // Create the TransactionStatus, which fails.
        TransactionStatusDTO transactionStatusDTO = transactionStatusMapper.toDto(transactionStatus);

        restTransactionStatusMockMvc.perform(post("/api/transaction-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionStatusDTO)))
            .andExpect(status().isBadRequest());

        List<TransactionStatus> transactionStatusList = transactionStatusRepository.findAll();
        assertThat(transactionStatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTransactionStatuses() throws Exception {
        // Initialize the database
        transactionStatusRepository.saveAndFlush(transactionStatus);

        // Get all the transactionStatusList
        restTransactionStatusMockMvc.perform(get("/api/transaction-statuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transactionStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getTransactionStatus() throws Exception {
        // Initialize the database
        transactionStatusRepository.saveAndFlush(transactionStatus);

        // Get the transactionStatus
        restTransactionStatusMockMvc.perform(get("/api/transaction-statuses/{id}", transactionStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(transactionStatus.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTransactionStatus() throws Exception {
        // Get the transactionStatus
        restTransactionStatusMockMvc.perform(get("/api/transaction-statuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTransactionStatus() throws Exception {
        // Initialize the database
        transactionStatusRepository.saveAndFlush(transactionStatus);

        int databaseSizeBeforeUpdate = transactionStatusRepository.findAll().size();

        // Update the transactionStatus
        TransactionStatus updatedTransactionStatus = transactionStatusRepository.findById(transactionStatus.getId()).get();
        // Disconnect from session so that the updates on updatedTransactionStatus are not directly saved in db
        em.detach(updatedTransactionStatus);
        updatedTransactionStatus
            .code(UPDATED_CODE)
            .label(UPDATED_LABEL)
            .active(UPDATED_ACTIVE);
        TransactionStatusDTO transactionStatusDTO = transactionStatusMapper.toDto(updatedTransactionStatus);

        restTransactionStatusMockMvc.perform(put("/api/transaction-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionStatusDTO)))
            .andExpect(status().isOk());

        // Validate the TransactionStatus in the database
        List<TransactionStatus> transactionStatusList = transactionStatusRepository.findAll();
        assertThat(transactionStatusList).hasSize(databaseSizeBeforeUpdate);
        TransactionStatus testTransactionStatus = transactionStatusList.get(transactionStatusList.size() - 1);
        assertThat(testTransactionStatus.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTransactionStatus.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testTransactionStatus.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingTransactionStatus() throws Exception {
        int databaseSizeBeforeUpdate = transactionStatusRepository.findAll().size();

        // Create the TransactionStatus
        TransactionStatusDTO transactionStatusDTO = transactionStatusMapper.toDto(transactionStatus);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransactionStatusMockMvc.perform(put("/api/transaction-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionStatusDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TransactionStatus in the database
        List<TransactionStatus> transactionStatusList = transactionStatusRepository.findAll();
        assertThat(transactionStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTransactionStatus() throws Exception {
        // Initialize the database
        transactionStatusRepository.saveAndFlush(transactionStatus);

        int databaseSizeBeforeDelete = transactionStatusRepository.findAll().size();

        // Delete the transactionStatus
        restTransactionStatusMockMvc.perform(delete("/api/transaction-statuses/{id}", transactionStatus.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TransactionStatus> transactionStatusList = transactionStatusRepository.findAll();
        assertThat(transactionStatusList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TransactionStatus.class);
        TransactionStatus transactionStatus1 = new TransactionStatus();
        transactionStatus1.setId(1L);
        TransactionStatus transactionStatus2 = new TransactionStatus();
        transactionStatus2.setId(transactionStatus1.getId());
        assertThat(transactionStatus1).isEqualTo(transactionStatus2);
        transactionStatus2.setId(2L);
        assertThat(transactionStatus1).isNotEqualTo(transactionStatus2);
        transactionStatus1.setId(null);
        assertThat(transactionStatus1).isNotEqualTo(transactionStatus2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TransactionStatusDTO.class);
        TransactionStatusDTO transactionStatusDTO1 = new TransactionStatusDTO();
        transactionStatusDTO1.setId(1L);
        TransactionStatusDTO transactionStatusDTO2 = new TransactionStatusDTO();
        assertThat(transactionStatusDTO1).isNotEqualTo(transactionStatusDTO2);
        transactionStatusDTO2.setId(transactionStatusDTO1.getId());
        assertThat(transactionStatusDTO1).isEqualTo(transactionStatusDTO2);
        transactionStatusDTO2.setId(2L);
        assertThat(transactionStatusDTO1).isNotEqualTo(transactionStatusDTO2);
        transactionStatusDTO1.setId(null);
        assertThat(transactionStatusDTO1).isNotEqualTo(transactionStatusDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(transactionStatusMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(transactionStatusMapper.fromId(null)).isNull();
    }
}
