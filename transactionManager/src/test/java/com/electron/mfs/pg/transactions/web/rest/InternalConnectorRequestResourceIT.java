package com.electron.mfs.pg.transactions.web.rest;

import com.electron.mfs.pg.transactions.TransactionManagerApp;
import com.electron.mfs.pg.transactions.config.SecurityBeanOverrideConfiguration;
import com.electron.mfs.pg.transactions.domain.InternalConnectorRequest;
import com.electron.mfs.pg.transactions.repository.InternalConnectorRequestRepository;
import com.electron.mfs.pg.transactions.service.InternalConnectorRequestService;
import com.electron.mfs.pg.transactions.service.dto.InternalConnectorRequestDTO;
import com.electron.mfs.pg.transactions.service.mapper.InternalConnectorRequestMapper;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.electron.mfs.pg.transactions.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link InternalConnectorRequestResource} REST controller.
 */
@EmbeddedKafka
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, TransactionManagerApp.class})
public class InternalConnectorRequestResourceIT {

    private static final String DEFAULT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_MODULE = "AAAAAAAAAA";
    private static final String UPDATED_MODULE = "BBBBBBBBBB";

    private static final String DEFAULT_CONNECTOR = "AAAAAAAAAA";
    private static final String UPDATED_CONNECTOR = "BBBBBBBBBB";

    private static final String DEFAULT_CONNECTOR_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_CONNECTOR_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_REQUEST_DATA = "AAAAAAAAAA";
    private static final String UPDATED_REQUEST_DATA = "BBBBBBBBBB";

    private static final Instant DEFAULT_REGISTRATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_REGISTRATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_PGAPS_TRANSACTION_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PGAPS_TRANSACTION_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_SERVICE_ID = "AAAAAAAAAA";
    private static final String UPDATED_SERVICE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_ACCOUNT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_NUMBER = "BBBBBBBBBB";

    private static final Double DEFAULT_AMOUNT = 0D;
    private static final Double UPDATED_AMOUNT = 1D;

    private static final Double DEFAULT_BALANCE = 0D;
    private static final Double UPDATED_BALANCE = 1D;

    private static final Boolean DEFAULT_ACCOUNT_VALIDATION = false;
    private static final Boolean UPDATED_ACCOUNT_VALIDATION = true;

    private static final Integer DEFAULT_NUMBER_OF_TRANSACTIONS = 1;
    private static final Integer UPDATED_NUMBER_OF_TRANSACTIONS = 2;

    private static final String DEFAULT_LAST_TRANSACTIONS = "AAAAAAAAAA";
    private static final String UPDATED_LAST_TRANSACTIONS = "BBBBBBBBBB";

    private static final String DEFAULT_ACTION = "AAAAAAAAAA";
    private static final String UPDATED_ACTION = "BBBBBBBBBB";

    private static final Instant DEFAULT_RESPONSE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_RESPONSE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_REASON = "AAAAAAAAAA";
    private static final String UPDATED_REASON = "BBBBBBBBBB";

    private static final String DEFAULT_PARTNER_TRANSACTION_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PARTNER_TRANSACTION_NUMBER = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private InternalConnectorRequestRepository internalConnectorRequestRepository;

    @Autowired
    private InternalConnectorRequestMapper internalConnectorRequestMapper;

    @Autowired
    private InternalConnectorRequestService internalConnectorRequestService;

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

    private MockMvc restInternalConnectorRequestMockMvc;

    private InternalConnectorRequest internalConnectorRequest;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InternalConnectorRequestResource internalConnectorRequestResource = new InternalConnectorRequestResource(internalConnectorRequestService);
        this.restInternalConnectorRequestMockMvc = MockMvcBuilders.standaloneSetup(internalConnectorRequestResource)
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
    public static InternalConnectorRequest createEntity(EntityManager em) {
        InternalConnectorRequest internalConnectorRequest = new InternalConnectorRequest()
            .number(DEFAULT_NUMBER)
            .module(DEFAULT_MODULE)
            .connector(DEFAULT_CONNECTOR)
            .connectorType(DEFAULT_CONNECTOR_TYPE)
            .requestData(DEFAULT_REQUEST_DATA)
            .registrationDate(DEFAULT_REGISTRATION_DATE)
            .pgapsTransactionNumber(DEFAULT_PGAPS_TRANSACTION_NUMBER)
            .serviceId(DEFAULT_SERVICE_ID)
            .accountNumber(DEFAULT_ACCOUNT_NUMBER)
            .amount(DEFAULT_AMOUNT)
            .balance(DEFAULT_BALANCE)
            .accountValidation(DEFAULT_ACCOUNT_VALIDATION)
            .numberOfTransactions(DEFAULT_NUMBER_OF_TRANSACTIONS)
            .lastTransactions(DEFAULT_LAST_TRANSACTIONS)
            .action(DEFAULT_ACTION)
            .responseDate(DEFAULT_RESPONSE_DATE)
            .status(DEFAULT_STATUS)
            .reason(DEFAULT_REASON)
            .partnerTransactionNumber(DEFAULT_PARTNER_TRANSACTION_NUMBER)
            .active(DEFAULT_ACTIVE);
        return internalConnectorRequest;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InternalConnectorRequest createUpdatedEntity(EntityManager em) {
        InternalConnectorRequest internalConnectorRequest = new InternalConnectorRequest()
            .number(UPDATED_NUMBER)
            .module(UPDATED_MODULE)
            .connector(UPDATED_CONNECTOR)
            .connectorType(UPDATED_CONNECTOR_TYPE)
            .requestData(UPDATED_REQUEST_DATA)
            .registrationDate(UPDATED_REGISTRATION_DATE)
            .pgapsTransactionNumber(UPDATED_PGAPS_TRANSACTION_NUMBER)
            .serviceId(UPDATED_SERVICE_ID)
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .amount(UPDATED_AMOUNT)
            .balance(UPDATED_BALANCE)
            .accountValidation(UPDATED_ACCOUNT_VALIDATION)
            .numberOfTransactions(UPDATED_NUMBER_OF_TRANSACTIONS)
            .lastTransactions(UPDATED_LAST_TRANSACTIONS)
            .action(UPDATED_ACTION)
            .responseDate(UPDATED_RESPONSE_DATE)
            .status(UPDATED_STATUS)
            .reason(UPDATED_REASON)
            .partnerTransactionNumber(UPDATED_PARTNER_TRANSACTION_NUMBER)
            .active(UPDATED_ACTIVE);
        return internalConnectorRequest;
    }

    @BeforeEach
    public void initTest() {
        internalConnectorRequest = createEntity(em);
    }

    @Test
    @Transactional
    public void createInternalConnectorRequest() throws Exception {
        int databaseSizeBeforeCreate = internalConnectorRequestRepository.findAll().size();

        // Create the InternalConnectorRequest
        InternalConnectorRequestDTO internalConnectorRequestDTO = internalConnectorRequestMapper.toDto(internalConnectorRequest);
        restInternalConnectorRequestMockMvc.perform(post("/api/internal-connector-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(internalConnectorRequestDTO)))
            .andExpect(status().isCreated());

        // Validate the InternalConnectorRequest in the database
        List<InternalConnectorRequest> internalConnectorRequestList = internalConnectorRequestRepository.findAll();
        assertThat(internalConnectorRequestList).hasSize(databaseSizeBeforeCreate + 1);
        InternalConnectorRequest testInternalConnectorRequest = internalConnectorRequestList.get(internalConnectorRequestList.size() - 1);
        assertThat(testInternalConnectorRequest.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testInternalConnectorRequest.getModule()).isEqualTo(DEFAULT_MODULE);
        assertThat(testInternalConnectorRequest.getConnector()).isEqualTo(DEFAULT_CONNECTOR);
        assertThat(testInternalConnectorRequest.getConnectorType()).isEqualTo(DEFAULT_CONNECTOR_TYPE);
        assertThat(testInternalConnectorRequest.getRequestData()).isEqualTo(DEFAULT_REQUEST_DATA);
        assertThat(testInternalConnectorRequest.getRegistrationDate()).isEqualTo(DEFAULT_REGISTRATION_DATE);
        assertThat(testInternalConnectorRequest.getPgapsTransactionNumber()).isEqualTo(DEFAULT_PGAPS_TRANSACTION_NUMBER);
        assertThat(testInternalConnectorRequest.getServiceId()).isEqualTo(DEFAULT_SERVICE_ID);
        assertThat(testInternalConnectorRequest.getAccountNumber()).isEqualTo(DEFAULT_ACCOUNT_NUMBER);
        assertThat(testInternalConnectorRequest.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testInternalConnectorRequest.getBalance()).isEqualTo(DEFAULT_BALANCE);
        assertThat(testInternalConnectorRequest.isAccountValidation()).isEqualTo(DEFAULT_ACCOUNT_VALIDATION);
        assertThat(testInternalConnectorRequest.getNumberOfTransactions()).isEqualTo(DEFAULT_NUMBER_OF_TRANSACTIONS);
        assertThat(testInternalConnectorRequest.getLastTransactions()).isEqualTo(DEFAULT_LAST_TRANSACTIONS);
        assertThat(testInternalConnectorRequest.getAction()).isEqualTo(DEFAULT_ACTION);
        assertThat(testInternalConnectorRequest.getResponseDate()).isEqualTo(DEFAULT_RESPONSE_DATE);
        assertThat(testInternalConnectorRequest.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testInternalConnectorRequest.getReason()).isEqualTo(DEFAULT_REASON);
        assertThat(testInternalConnectorRequest.getPartnerTransactionNumber()).isEqualTo(DEFAULT_PARTNER_TRANSACTION_NUMBER);
        assertThat(testInternalConnectorRequest.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createInternalConnectorRequestWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = internalConnectorRequestRepository.findAll().size();

        // Create the InternalConnectorRequest with an existing ID
        internalConnectorRequest.setId(1L);
        InternalConnectorRequestDTO internalConnectorRequestDTO = internalConnectorRequestMapper.toDto(internalConnectorRequest);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInternalConnectorRequestMockMvc.perform(post("/api/internal-connector-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(internalConnectorRequestDTO)))
            .andExpect(status().isBadRequest());

        // Validate the InternalConnectorRequest in the database
        List<InternalConnectorRequest> internalConnectorRequestList = internalConnectorRequestRepository.findAll();
        assertThat(internalConnectorRequestList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = internalConnectorRequestRepository.findAll().size();
        // set the field null
        internalConnectorRequest.setNumber(null);

        // Create the InternalConnectorRequest, which fails.
        InternalConnectorRequestDTO internalConnectorRequestDTO = internalConnectorRequestMapper.toDto(internalConnectorRequest);

        restInternalConnectorRequestMockMvc.perform(post("/api/internal-connector-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(internalConnectorRequestDTO)))
            .andExpect(status().isBadRequest());

        List<InternalConnectorRequest> internalConnectorRequestList = internalConnectorRequestRepository.findAll();
        assertThat(internalConnectorRequestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModuleIsRequired() throws Exception {
        int databaseSizeBeforeTest = internalConnectorRequestRepository.findAll().size();
        // set the field null
        internalConnectorRequest.setModule(null);

        // Create the InternalConnectorRequest, which fails.
        InternalConnectorRequestDTO internalConnectorRequestDTO = internalConnectorRequestMapper.toDto(internalConnectorRequest);

        restInternalConnectorRequestMockMvc.perform(post("/api/internal-connector-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(internalConnectorRequestDTO)))
            .andExpect(status().isBadRequest());

        List<InternalConnectorRequest> internalConnectorRequestList = internalConnectorRequestRepository.findAll();
        assertThat(internalConnectorRequestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkConnectorIsRequired() throws Exception {
        int databaseSizeBeforeTest = internalConnectorRequestRepository.findAll().size();
        // set the field null
        internalConnectorRequest.setConnector(null);

        // Create the InternalConnectorRequest, which fails.
        InternalConnectorRequestDTO internalConnectorRequestDTO = internalConnectorRequestMapper.toDto(internalConnectorRequest);

        restInternalConnectorRequestMockMvc.perform(post("/api/internal-connector-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(internalConnectorRequestDTO)))
            .andExpect(status().isBadRequest());

        List<InternalConnectorRequest> internalConnectorRequestList = internalConnectorRequestRepository.findAll();
        assertThat(internalConnectorRequestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkConnectorTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = internalConnectorRequestRepository.findAll().size();
        // set the field null
        internalConnectorRequest.setConnectorType(null);

        // Create the InternalConnectorRequest, which fails.
        InternalConnectorRequestDTO internalConnectorRequestDTO = internalConnectorRequestMapper.toDto(internalConnectorRequest);

        restInternalConnectorRequestMockMvc.perform(post("/api/internal-connector-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(internalConnectorRequestDTO)))
            .andExpect(status().isBadRequest());

        List<InternalConnectorRequest> internalConnectorRequestList = internalConnectorRequestRepository.findAll();
        assertThat(internalConnectorRequestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRegistrationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = internalConnectorRequestRepository.findAll().size();
        // set the field null
        internalConnectorRequest.setRegistrationDate(null);

        // Create the InternalConnectorRequest, which fails.
        InternalConnectorRequestDTO internalConnectorRequestDTO = internalConnectorRequestMapper.toDto(internalConnectorRequest);

        restInternalConnectorRequestMockMvc.perform(post("/api/internal-connector-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(internalConnectorRequestDTO)))
            .andExpect(status().isBadRequest());

        List<InternalConnectorRequest> internalConnectorRequestList = internalConnectorRequestRepository.findAll();
        assertThat(internalConnectorRequestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = internalConnectorRequestRepository.findAll().size();
        // set the field null
        internalConnectorRequest.setActive(null);

        // Create the InternalConnectorRequest, which fails.
        InternalConnectorRequestDTO internalConnectorRequestDTO = internalConnectorRequestMapper.toDto(internalConnectorRequest);

        restInternalConnectorRequestMockMvc.perform(post("/api/internal-connector-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(internalConnectorRequestDTO)))
            .andExpect(status().isBadRequest());

        List<InternalConnectorRequest> internalConnectorRequestList = internalConnectorRequestRepository.findAll();
        assertThat(internalConnectorRequestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllInternalConnectorRequests() throws Exception {
        // Initialize the database
        internalConnectorRequestRepository.saveAndFlush(internalConnectorRequest);

        // Get all the internalConnectorRequestList
        restInternalConnectorRequestMockMvc.perform(get("/api/internal-connector-requests?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(internalConnectorRequest.getId().intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].module").value(hasItem(DEFAULT_MODULE.toString())))
            .andExpect(jsonPath("$.[*].connector").value(hasItem(DEFAULT_CONNECTOR.toString())))
            .andExpect(jsonPath("$.[*].connectorType").value(hasItem(DEFAULT_CONNECTOR_TYPE.toString())))
            .andExpect(jsonPath("$.[*].requestData").value(hasItem(DEFAULT_REQUEST_DATA.toString())))
            .andExpect(jsonPath("$.[*].registrationDate").value(hasItem(DEFAULT_REGISTRATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].pgapsTransactionNumber").value(hasItem(DEFAULT_PGAPS_TRANSACTION_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].serviceId").value(hasItem(DEFAULT_SERVICE_ID.toString())))
            .andExpect(jsonPath("$.[*].accountNumber").value(hasItem(DEFAULT_ACCOUNT_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].balance").value(hasItem(DEFAULT_BALANCE.doubleValue())))
            .andExpect(jsonPath("$.[*].accountValidation").value(hasItem(DEFAULT_ACCOUNT_VALIDATION.booleanValue())))
            .andExpect(jsonPath("$.[*].numberOfTransactions").value(hasItem(DEFAULT_NUMBER_OF_TRANSACTIONS)))
            .andExpect(jsonPath("$.[*].lastTransactions").value(hasItem(DEFAULT_LAST_TRANSACTIONS.toString())))
            .andExpect(jsonPath("$.[*].action").value(hasItem(DEFAULT_ACTION.toString())))
            .andExpect(jsonPath("$.[*].responseDate").value(hasItem(DEFAULT_RESPONSE_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON.toString())))
            .andExpect(jsonPath("$.[*].partnerTransactionNumber").value(hasItem(DEFAULT_PARTNER_TRANSACTION_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getInternalConnectorRequest() throws Exception {
        // Initialize the database
        internalConnectorRequestRepository.saveAndFlush(internalConnectorRequest);

        // Get the internalConnectorRequest
        restInternalConnectorRequestMockMvc.perform(get("/api/internal-connector-requests/{id}", internalConnectorRequest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(internalConnectorRequest.getId().intValue()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER.toString()))
            .andExpect(jsonPath("$.module").value(DEFAULT_MODULE.toString()))
            .andExpect(jsonPath("$.connector").value(DEFAULT_CONNECTOR.toString()))
            .andExpect(jsonPath("$.connectorType").value(DEFAULT_CONNECTOR_TYPE.toString()))
            .andExpect(jsonPath("$.requestData").value(DEFAULT_REQUEST_DATA.toString()))
            .andExpect(jsonPath("$.registrationDate").value(DEFAULT_REGISTRATION_DATE.toString()))
            .andExpect(jsonPath("$.pgapsTransactionNumber").value(DEFAULT_PGAPS_TRANSACTION_NUMBER.toString()))
            .andExpect(jsonPath("$.serviceId").value(DEFAULT_SERVICE_ID.toString()))
            .andExpect(jsonPath("$.accountNumber").value(DEFAULT_ACCOUNT_NUMBER.toString()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.balance").value(DEFAULT_BALANCE.doubleValue()))
            .andExpect(jsonPath("$.accountValidation").value(DEFAULT_ACCOUNT_VALIDATION.booleanValue()))
            .andExpect(jsonPath("$.numberOfTransactions").value(DEFAULT_NUMBER_OF_TRANSACTIONS))
            .andExpect(jsonPath("$.lastTransactions").value(DEFAULT_LAST_TRANSACTIONS.toString()))
            .andExpect(jsonPath("$.action").value(DEFAULT_ACTION.toString()))
            .andExpect(jsonPath("$.responseDate").value(DEFAULT_RESPONSE_DATE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.reason").value(DEFAULT_REASON.toString()))
            .andExpect(jsonPath("$.partnerTransactionNumber").value(DEFAULT_PARTNER_TRANSACTION_NUMBER.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingInternalConnectorRequest() throws Exception {
        // Get the internalConnectorRequest
        restInternalConnectorRequestMockMvc.perform(get("/api/internal-connector-requests/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInternalConnectorRequest() throws Exception {
        // Initialize the database
        internalConnectorRequestRepository.saveAndFlush(internalConnectorRequest);

        int databaseSizeBeforeUpdate = internalConnectorRequestRepository.findAll().size();

        // Update the internalConnectorRequest
        InternalConnectorRequest updatedInternalConnectorRequest = internalConnectorRequestRepository.findById(internalConnectorRequest.getId()).get();
        // Disconnect from session so that the updates on updatedInternalConnectorRequest are not directly saved in db
        em.detach(updatedInternalConnectorRequest);
        updatedInternalConnectorRequest
            .number(UPDATED_NUMBER)
            .module(UPDATED_MODULE)
            .connector(UPDATED_CONNECTOR)
            .connectorType(UPDATED_CONNECTOR_TYPE)
            .requestData(UPDATED_REQUEST_DATA)
            .registrationDate(UPDATED_REGISTRATION_DATE)
            .pgapsTransactionNumber(UPDATED_PGAPS_TRANSACTION_NUMBER)
            .serviceId(UPDATED_SERVICE_ID)
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .amount(UPDATED_AMOUNT)
            .balance(UPDATED_BALANCE)
            .accountValidation(UPDATED_ACCOUNT_VALIDATION)
            .numberOfTransactions(UPDATED_NUMBER_OF_TRANSACTIONS)
            .lastTransactions(UPDATED_LAST_TRANSACTIONS)
            .action(UPDATED_ACTION)
            .responseDate(UPDATED_RESPONSE_DATE)
            .status(UPDATED_STATUS)
            .reason(UPDATED_REASON)
            .partnerTransactionNumber(UPDATED_PARTNER_TRANSACTION_NUMBER)
            .active(UPDATED_ACTIVE);
        InternalConnectorRequestDTO internalConnectorRequestDTO = internalConnectorRequestMapper.toDto(updatedInternalConnectorRequest);

        restInternalConnectorRequestMockMvc.perform(put("/api/internal-connector-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(internalConnectorRequestDTO)))
            .andExpect(status().isOk());

        // Validate the InternalConnectorRequest in the database
        List<InternalConnectorRequest> internalConnectorRequestList = internalConnectorRequestRepository.findAll();
        assertThat(internalConnectorRequestList).hasSize(databaseSizeBeforeUpdate);
        InternalConnectorRequest testInternalConnectorRequest = internalConnectorRequestList.get(internalConnectorRequestList.size() - 1);
        assertThat(testInternalConnectorRequest.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testInternalConnectorRequest.getModule()).isEqualTo(UPDATED_MODULE);
        assertThat(testInternalConnectorRequest.getConnector()).isEqualTo(UPDATED_CONNECTOR);
        assertThat(testInternalConnectorRequest.getConnectorType()).isEqualTo(UPDATED_CONNECTOR_TYPE);
        assertThat(testInternalConnectorRequest.getRequestData()).isEqualTo(UPDATED_REQUEST_DATA);
        assertThat(testInternalConnectorRequest.getRegistrationDate()).isEqualTo(UPDATED_REGISTRATION_DATE);
        assertThat(testInternalConnectorRequest.getPgapsTransactionNumber()).isEqualTo(UPDATED_PGAPS_TRANSACTION_NUMBER);
        assertThat(testInternalConnectorRequest.getServiceId()).isEqualTo(UPDATED_SERVICE_ID);
        assertThat(testInternalConnectorRequest.getAccountNumber()).isEqualTo(UPDATED_ACCOUNT_NUMBER);
        assertThat(testInternalConnectorRequest.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testInternalConnectorRequest.getBalance()).isEqualTo(UPDATED_BALANCE);
        assertThat(testInternalConnectorRequest.isAccountValidation()).isEqualTo(UPDATED_ACCOUNT_VALIDATION);
        assertThat(testInternalConnectorRequest.getNumberOfTransactions()).isEqualTo(UPDATED_NUMBER_OF_TRANSACTIONS);
        assertThat(testInternalConnectorRequest.getLastTransactions()).isEqualTo(UPDATED_LAST_TRANSACTIONS);
        assertThat(testInternalConnectorRequest.getAction()).isEqualTo(UPDATED_ACTION);
        assertThat(testInternalConnectorRequest.getResponseDate()).isEqualTo(UPDATED_RESPONSE_DATE);
        assertThat(testInternalConnectorRequest.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testInternalConnectorRequest.getReason()).isEqualTo(UPDATED_REASON);
        assertThat(testInternalConnectorRequest.getPartnerTransactionNumber()).isEqualTo(UPDATED_PARTNER_TRANSACTION_NUMBER);
        assertThat(testInternalConnectorRequest.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingInternalConnectorRequest() throws Exception {
        int databaseSizeBeforeUpdate = internalConnectorRequestRepository.findAll().size();

        // Create the InternalConnectorRequest
        InternalConnectorRequestDTO internalConnectorRequestDTO = internalConnectorRequestMapper.toDto(internalConnectorRequest);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInternalConnectorRequestMockMvc.perform(put("/api/internal-connector-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(internalConnectorRequestDTO)))
            .andExpect(status().isBadRequest());

        // Validate the InternalConnectorRequest in the database
        List<InternalConnectorRequest> internalConnectorRequestList = internalConnectorRequestRepository.findAll();
        assertThat(internalConnectorRequestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInternalConnectorRequest() throws Exception {
        // Initialize the database
        internalConnectorRequestRepository.saveAndFlush(internalConnectorRequest);

        int databaseSizeBeforeDelete = internalConnectorRequestRepository.findAll().size();

        // Delete the internalConnectorRequest
        restInternalConnectorRequestMockMvc.perform(delete("/api/internal-connector-requests/{id}", internalConnectorRequest.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<InternalConnectorRequest> internalConnectorRequestList = internalConnectorRequestRepository.findAll();
        assertThat(internalConnectorRequestList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InternalConnectorRequest.class);
        InternalConnectorRequest internalConnectorRequest1 = new InternalConnectorRequest();
        internalConnectorRequest1.setId(1L);
        InternalConnectorRequest internalConnectorRequest2 = new InternalConnectorRequest();
        internalConnectorRequest2.setId(internalConnectorRequest1.getId());
        assertThat(internalConnectorRequest1).isEqualTo(internalConnectorRequest2);
        internalConnectorRequest2.setId(2L);
        assertThat(internalConnectorRequest1).isNotEqualTo(internalConnectorRequest2);
        internalConnectorRequest1.setId(null);
        assertThat(internalConnectorRequest1).isNotEqualTo(internalConnectorRequest2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InternalConnectorRequestDTO.class);
        InternalConnectorRequestDTO internalConnectorRequestDTO1 = new InternalConnectorRequestDTO();
        internalConnectorRequestDTO1.setId(1L);
        InternalConnectorRequestDTO internalConnectorRequestDTO2 = new InternalConnectorRequestDTO();
        assertThat(internalConnectorRequestDTO1).isNotEqualTo(internalConnectorRequestDTO2);
        internalConnectorRequestDTO2.setId(internalConnectorRequestDTO1.getId());
        assertThat(internalConnectorRequestDTO1).isEqualTo(internalConnectorRequestDTO2);
        internalConnectorRequestDTO2.setId(2L);
        assertThat(internalConnectorRequestDTO1).isNotEqualTo(internalConnectorRequestDTO2);
        internalConnectorRequestDTO1.setId(null);
        assertThat(internalConnectorRequestDTO1).isNotEqualTo(internalConnectorRequestDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(internalConnectorRequestMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(internalConnectorRequestMapper.fromId(null)).isNull();
    }
}
