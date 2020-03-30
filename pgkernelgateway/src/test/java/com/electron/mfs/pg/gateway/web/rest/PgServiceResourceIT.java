package com.electron.mfs.pg.gateway.web.rest;

import com.electron.mfs.pg.gateway.PgkernelgatewayApp;
import com.electron.mfs.pg.gateway.config.SecurityBeanOverrideConfiguration;
import com.electron.mfs.pg.gateway.domain.PgService;
import com.electron.mfs.pg.gateway.repository.PgServiceRepository;
import com.electron.mfs.pg.gateway.service.PgServiceService;
import com.electron.mfs.pg.gateway.service.dto.PgServiceDTO;
import com.electron.mfs.pg.gateway.service.mapper.PgServiceMapper;
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
 * Integration tests for the {@Link PgServiceResource} REST controller.
 */
@EmbeddedKafka
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, PgkernelgatewayApp.class})
public class PgServiceResourceIT {

    private static final String DEFAULT_CODE = "AAAAA";
    private static final String UPDATED_CODE = "BBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_NATIVE = false;
    private static final Boolean UPDATED_IS_NATIVE = true;

    private static final Boolean DEFAULT_IS_SOURCE_INTERNAL = false;
    private static final Boolean UPDATED_IS_SOURCE_INTERNAL = true;

    private static final Boolean DEFAULT_IS_DESTINATION_INTERNAL = false;
    private static final Boolean UPDATED_IS_DESTINATION_INTERNAL = true;

    private static final Boolean DEFAULT_NEED_SUBSCRIPTION = false;
    private static final Boolean UPDATED_NEED_SUBSCRIPTION = true;

    private static final String DEFAULT_CURRENCY_CODE = "AAAAA";
    private static final String UPDATED_CURRENCY_CODE = "BBBBB";

    private static final Boolean DEFAULT_USE_TRANSACTION_TYPE = false;
    private static final Boolean UPDATED_USE_TRANSACTION_TYPE = true;

    private static final Boolean DEFAULT_CHECK_SUBSCRIPTION = false;
    private static final Boolean UPDATED_CHECK_SUBSCRIPTION = true;

    private static final Boolean DEFAULT_IGNORE_FEES = false;
    private static final Boolean UPDATED_IGNORE_FEES = true;

    private static final Boolean DEFAULT_IGNORE_LIMIT = false;
    private static final Boolean UPDATED_IGNORE_LIMIT = true;

    private static final Boolean DEFAULT_IGNORE_COMMISSION = false;
    private static final Boolean UPDATED_IGNORE_COMMISSION = true;

    private static final Boolean DEFAULT_CHECK_OTP = false;
    private static final Boolean UPDATED_CHECK_OTP = true;

    private static final String DEFAULT_PG_TRANSACTION_TYPE_1_CODE = "AAAAA";
    private static final String UPDATED_PG_TRANSACTION_TYPE_1_CODE = "BBBBB";

    private static final String DEFAULT_PG_TRANSACTION_TYPE_2_CODE = "AAAAA";
    private static final String UPDATED_PG_TRANSACTION_TYPE_2_CODE = "BBBBB";

    private static final String DEFAULT_PARTNER_OWNER_CODE = "AAAAA";
    private static final String UPDATED_PARTNER_OWNER_CODE = "BBBBB";

    private static final String DEFAULT_TRANSACTION_TYPE_CODE = "AAAAA";
    private static final String UPDATED_TRANSACTION_TYPE_CODE = "BBBBB";

    private static final String DEFAULT_SERVICE_AUTHENTICATION_CODE = "AAAAA";
    private static final String UPDATED_SERVICE_AUTHENTICATION_CODE = "BBBBB";

    private static final String DEFAULT_CONTRACT_PATH = "AAAAAAAAAA";
    private static final String UPDATED_CONTRACT_PATH = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_LOGIC = "AAAAAAAAAA";
    private static final String UPDATED_LOGIC = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private PgServiceRepository pgServiceRepository;

    @Autowired
    private PgServiceMapper pgServiceMapper;

    @Autowired
    private PgServiceService pgServiceService;

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

    private MockMvc restPgServiceMockMvc;

    private PgService pgService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PgServiceResource pgServiceResource = new PgServiceResource(pgServiceService);
        this.restPgServiceMockMvc = MockMvcBuilders.standaloneSetup(pgServiceResource)
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
    public static PgService createEntity(EntityManager em) {
        PgService pgService = new PgService()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .isNative(DEFAULT_IS_NATIVE)
            .isSourceInternal(DEFAULT_IS_SOURCE_INTERNAL)
            .isDestinationInternal(DEFAULT_IS_DESTINATION_INTERNAL)
            .needSubscription(DEFAULT_NEED_SUBSCRIPTION)
            .currencyCode(DEFAULT_CURRENCY_CODE)
            .useTransactionType(DEFAULT_USE_TRANSACTION_TYPE)
            .checkSubscription(DEFAULT_CHECK_SUBSCRIPTION)
            .ignoreFees(DEFAULT_IGNORE_FEES)
            .ignoreLimit(DEFAULT_IGNORE_LIMIT)
            .ignoreCommission(DEFAULT_IGNORE_COMMISSION)
            .checkOtp(DEFAULT_CHECK_OTP)
            .pgTransactionType1Code(DEFAULT_PG_TRANSACTION_TYPE_1_CODE)
            .pgTransactionType2Code(DEFAULT_PG_TRANSACTION_TYPE_2_CODE)
            .partnerOwnerCode(DEFAULT_PARTNER_OWNER_CODE)
            .transactionTypeCode(DEFAULT_TRANSACTION_TYPE_CODE)
            .serviceAuthenticationCode(DEFAULT_SERVICE_AUTHENTICATION_CODE)
            .contractPath(DEFAULT_CONTRACT_PATH)
            .description(DEFAULT_DESCRIPTION)
            .logic(DEFAULT_LOGIC)
            .active(DEFAULT_ACTIVE);
        return pgService;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PgService createUpdatedEntity(EntityManager em) {
        PgService pgService = new PgService()
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .isNative(UPDATED_IS_NATIVE)
            .isSourceInternal(UPDATED_IS_SOURCE_INTERNAL)
            .isDestinationInternal(UPDATED_IS_DESTINATION_INTERNAL)
            .needSubscription(UPDATED_NEED_SUBSCRIPTION)
            .currencyCode(UPDATED_CURRENCY_CODE)
            .useTransactionType(UPDATED_USE_TRANSACTION_TYPE)
            .checkSubscription(UPDATED_CHECK_SUBSCRIPTION)
            .ignoreFees(UPDATED_IGNORE_FEES)
            .ignoreLimit(UPDATED_IGNORE_LIMIT)
            .ignoreCommission(UPDATED_IGNORE_COMMISSION)
            .checkOtp(UPDATED_CHECK_OTP)
            .pgTransactionType1Code(UPDATED_PG_TRANSACTION_TYPE_1_CODE)
            .pgTransactionType2Code(UPDATED_PG_TRANSACTION_TYPE_2_CODE)
            .partnerOwnerCode(UPDATED_PARTNER_OWNER_CODE)
            .transactionTypeCode(UPDATED_TRANSACTION_TYPE_CODE)
            .serviceAuthenticationCode(UPDATED_SERVICE_AUTHENTICATION_CODE)
            .contractPath(UPDATED_CONTRACT_PATH)
            .description(UPDATED_DESCRIPTION)
            .logic(UPDATED_LOGIC)
            .active(UPDATED_ACTIVE);
        return pgService;
    }

    @BeforeEach
    public void initTest() {
        pgService = createEntity(em);
    }

    @Test
    @Transactional
    public void createPgService() throws Exception {
        int databaseSizeBeforeCreate = pgServiceRepository.findAll().size();

        // Create the PgService
        PgServiceDTO pgServiceDTO = pgServiceMapper.toDto(pgService);
        restPgServiceMockMvc.perform(post("/api/pg-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgServiceDTO)))
            .andExpect(status().isCreated());

        // Validate the PgService in the database
        List<PgService> pgServiceList = pgServiceRepository.findAll();
        assertThat(pgServiceList).hasSize(databaseSizeBeforeCreate + 1);
        PgService testPgService = pgServiceList.get(pgServiceList.size() - 1);
        assertThat(testPgService.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testPgService.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPgService.isIsNative()).isEqualTo(DEFAULT_IS_NATIVE);
        assertThat(testPgService.isIsSourceInternal()).isEqualTo(DEFAULT_IS_SOURCE_INTERNAL);
        assertThat(testPgService.isIsDestinationInternal()).isEqualTo(DEFAULT_IS_DESTINATION_INTERNAL);
        assertThat(testPgService.isNeedSubscription()).isEqualTo(DEFAULT_NEED_SUBSCRIPTION);
        assertThat(testPgService.getCurrencyCode()).isEqualTo(DEFAULT_CURRENCY_CODE);
        assertThat(testPgService.isUseTransactionType()).isEqualTo(DEFAULT_USE_TRANSACTION_TYPE);
        assertThat(testPgService.isCheckSubscription()).isEqualTo(DEFAULT_CHECK_SUBSCRIPTION);
        assertThat(testPgService.isIgnoreFees()).isEqualTo(DEFAULT_IGNORE_FEES);
        assertThat(testPgService.isIgnoreLimit()).isEqualTo(DEFAULT_IGNORE_LIMIT);
        assertThat(testPgService.isIgnoreCommission()).isEqualTo(DEFAULT_IGNORE_COMMISSION);
        assertThat(testPgService.isCheckOtp()).isEqualTo(DEFAULT_CHECK_OTP);
        assertThat(testPgService.getPgTransactionType1Code()).isEqualTo(DEFAULT_PG_TRANSACTION_TYPE_1_CODE);
        assertThat(testPgService.getPgTransactionType2Code()).isEqualTo(DEFAULT_PG_TRANSACTION_TYPE_2_CODE);
        assertThat(testPgService.getPartnerOwnerCode()).isEqualTo(DEFAULT_PARTNER_OWNER_CODE);
        assertThat(testPgService.getTransactionTypeCode()).isEqualTo(DEFAULT_TRANSACTION_TYPE_CODE);
        assertThat(testPgService.getServiceAuthenticationCode()).isEqualTo(DEFAULT_SERVICE_AUTHENTICATION_CODE);
        assertThat(testPgService.getContractPath()).isEqualTo(DEFAULT_CONTRACT_PATH);
        assertThat(testPgService.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPgService.getLogic()).isEqualTo(DEFAULT_LOGIC);
        assertThat(testPgService.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createPgServiceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pgServiceRepository.findAll().size();

        // Create the PgService with an existing ID
        pgService.setId(1L);
        PgServiceDTO pgServiceDTO = pgServiceMapper.toDto(pgService);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPgServiceMockMvc.perform(post("/api/pg-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgServiceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PgService in the database
        List<PgService> pgServiceList = pgServiceRepository.findAll();
        assertThat(pgServiceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgServiceRepository.findAll().size();
        // set the field null
        pgService.setCode(null);

        // Create the PgService, which fails.
        PgServiceDTO pgServiceDTO = pgServiceMapper.toDto(pgService);

        restPgServiceMockMvc.perform(post("/api/pg-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgServiceDTO)))
            .andExpect(status().isBadRequest());

        List<PgService> pgServiceList = pgServiceRepository.findAll();
        assertThat(pgServiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgServiceRepository.findAll().size();
        // set the field null
        pgService.setName(null);

        // Create the PgService, which fails.
        PgServiceDTO pgServiceDTO = pgServiceMapper.toDto(pgService);

        restPgServiceMockMvc.perform(post("/api/pg-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgServiceDTO)))
            .andExpect(status().isBadRequest());

        List<PgService> pgServiceList = pgServiceRepository.findAll();
        assertThat(pgServiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsNativeIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgServiceRepository.findAll().size();
        // set the field null
        pgService.setIsNative(null);

        // Create the PgService, which fails.
        PgServiceDTO pgServiceDTO = pgServiceMapper.toDto(pgService);

        restPgServiceMockMvc.perform(post("/api/pg-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgServiceDTO)))
            .andExpect(status().isBadRequest());

        List<PgService> pgServiceList = pgServiceRepository.findAll();
        assertThat(pgServiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsSourceInternalIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgServiceRepository.findAll().size();
        // set the field null
        pgService.setIsSourceInternal(null);

        // Create the PgService, which fails.
        PgServiceDTO pgServiceDTO = pgServiceMapper.toDto(pgService);

        restPgServiceMockMvc.perform(post("/api/pg-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgServiceDTO)))
            .andExpect(status().isBadRequest());

        List<PgService> pgServiceList = pgServiceRepository.findAll();
        assertThat(pgServiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsDestinationInternalIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgServiceRepository.findAll().size();
        // set the field null
        pgService.setIsDestinationInternal(null);

        // Create the PgService, which fails.
        PgServiceDTO pgServiceDTO = pgServiceMapper.toDto(pgService);

        restPgServiceMockMvc.perform(post("/api/pg-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgServiceDTO)))
            .andExpect(status().isBadRequest());

        List<PgService> pgServiceList = pgServiceRepository.findAll();
        assertThat(pgServiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNeedSubscriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgServiceRepository.findAll().size();
        // set the field null
        pgService.setNeedSubscription(null);

        // Create the PgService, which fails.
        PgServiceDTO pgServiceDTO = pgServiceMapper.toDto(pgService);

        restPgServiceMockMvc.perform(post("/api/pg-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgServiceDTO)))
            .andExpect(status().isBadRequest());

        List<PgService> pgServiceList = pgServiceRepository.findAll();
        assertThat(pgServiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCurrencyCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgServiceRepository.findAll().size();
        // set the field null
        pgService.setCurrencyCode(null);

        // Create the PgService, which fails.
        PgServiceDTO pgServiceDTO = pgServiceMapper.toDto(pgService);

        restPgServiceMockMvc.perform(post("/api/pg-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgServiceDTO)))
            .andExpect(status().isBadRequest());

        List<PgService> pgServiceList = pgServiceRepository.findAll();
        assertThat(pgServiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUseTransactionTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgServiceRepository.findAll().size();
        // set the field null
        pgService.setUseTransactionType(null);

        // Create the PgService, which fails.
        PgServiceDTO pgServiceDTO = pgServiceMapper.toDto(pgService);

        restPgServiceMockMvc.perform(post("/api/pg-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgServiceDTO)))
            .andExpect(status().isBadRequest());

        List<PgService> pgServiceList = pgServiceRepository.findAll();
        assertThat(pgServiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCheckSubscriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgServiceRepository.findAll().size();
        // set the field null
        pgService.setCheckSubscription(null);

        // Create the PgService, which fails.
        PgServiceDTO pgServiceDTO = pgServiceMapper.toDto(pgService);

        restPgServiceMockMvc.perform(post("/api/pg-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgServiceDTO)))
            .andExpect(status().isBadRequest());

        List<PgService> pgServiceList = pgServiceRepository.findAll();
        assertThat(pgServiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIgnoreFeesIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgServiceRepository.findAll().size();
        // set the field null
        pgService.setIgnoreFees(null);

        // Create the PgService, which fails.
        PgServiceDTO pgServiceDTO = pgServiceMapper.toDto(pgService);

        restPgServiceMockMvc.perform(post("/api/pg-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgServiceDTO)))
            .andExpect(status().isBadRequest());

        List<PgService> pgServiceList = pgServiceRepository.findAll();
        assertThat(pgServiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIgnoreLimitIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgServiceRepository.findAll().size();
        // set the field null
        pgService.setIgnoreLimit(null);

        // Create the PgService, which fails.
        PgServiceDTO pgServiceDTO = pgServiceMapper.toDto(pgService);

        restPgServiceMockMvc.perform(post("/api/pg-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgServiceDTO)))
            .andExpect(status().isBadRequest());

        List<PgService> pgServiceList = pgServiceRepository.findAll();
        assertThat(pgServiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIgnoreCommissionIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgServiceRepository.findAll().size();
        // set the field null
        pgService.setIgnoreCommission(null);

        // Create the PgService, which fails.
        PgServiceDTO pgServiceDTO = pgServiceMapper.toDto(pgService);

        restPgServiceMockMvc.perform(post("/api/pg-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgServiceDTO)))
            .andExpect(status().isBadRequest());

        List<PgService> pgServiceList = pgServiceRepository.findAll();
        assertThat(pgServiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCheckOtpIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgServiceRepository.findAll().size();
        // set the field null
        pgService.setCheckOtp(null);

        // Create the PgService, which fails.
        PgServiceDTO pgServiceDTO = pgServiceMapper.toDto(pgService);

        restPgServiceMockMvc.perform(post("/api/pg-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgServiceDTO)))
            .andExpect(status().isBadRequest());

        List<PgService> pgServiceList = pgServiceRepository.findAll();
        assertThat(pgServiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPgTransactionType1CodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgServiceRepository.findAll().size();
        // set the field null
        pgService.setPgTransactionType1Code(null);

        // Create the PgService, which fails.
        PgServiceDTO pgServiceDTO = pgServiceMapper.toDto(pgService);

        restPgServiceMockMvc.perform(post("/api/pg-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgServiceDTO)))
            .andExpect(status().isBadRequest());

        List<PgService> pgServiceList = pgServiceRepository.findAll();
        assertThat(pgServiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPgTransactionType2CodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgServiceRepository.findAll().size();
        // set the field null
        pgService.setPgTransactionType2Code(null);

        // Create the PgService, which fails.
        PgServiceDTO pgServiceDTO = pgServiceMapper.toDto(pgService);

        restPgServiceMockMvc.perform(post("/api/pg-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgServiceDTO)))
            .andExpect(status().isBadRequest());

        List<PgService> pgServiceList = pgServiceRepository.findAll();
        assertThat(pgServiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTransactionTypeCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgServiceRepository.findAll().size();
        // set the field null
        pgService.setTransactionTypeCode(null);

        // Create the PgService, which fails.
        PgServiceDTO pgServiceDTO = pgServiceMapper.toDto(pgService);

        restPgServiceMockMvc.perform(post("/api/pg-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgServiceDTO)))
            .andExpect(status().isBadRequest());

        List<PgService> pgServiceList = pgServiceRepository.findAll();
        assertThat(pgServiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkServiceAuthenticationCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgServiceRepository.findAll().size();
        // set the field null
        pgService.setServiceAuthenticationCode(null);

        // Create the PgService, which fails.
        PgServiceDTO pgServiceDTO = pgServiceMapper.toDto(pgService);

        restPgServiceMockMvc.perform(post("/api/pg-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgServiceDTO)))
            .andExpect(status().isBadRequest());

        List<PgService> pgServiceList = pgServiceRepository.findAll();
        assertThat(pgServiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgServiceRepository.findAll().size();
        // set the field null
        pgService.setActive(null);

        // Create the PgService, which fails.
        PgServiceDTO pgServiceDTO = pgServiceMapper.toDto(pgService);

        restPgServiceMockMvc.perform(post("/api/pg-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgServiceDTO)))
            .andExpect(status().isBadRequest());

        List<PgService> pgServiceList = pgServiceRepository.findAll();
        assertThat(pgServiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPgServices() throws Exception {
        // Initialize the database
        pgServiceRepository.saveAndFlush(pgService);

        // Get all the pgServiceList
        restPgServiceMockMvc.perform(get("/api/pg-services?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pgService.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].isNative").value(hasItem(DEFAULT_IS_NATIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].isSourceInternal").value(hasItem(DEFAULT_IS_SOURCE_INTERNAL.booleanValue())))
            .andExpect(jsonPath("$.[*].isDestinationInternal").value(hasItem(DEFAULT_IS_DESTINATION_INTERNAL.booleanValue())))
            .andExpect(jsonPath("$.[*].needSubscription").value(hasItem(DEFAULT_NEED_SUBSCRIPTION.booleanValue())))
            .andExpect(jsonPath("$.[*].currencyCode").value(hasItem(DEFAULT_CURRENCY_CODE.toString())))
            .andExpect(jsonPath("$.[*].useTransactionType").value(hasItem(DEFAULT_USE_TRANSACTION_TYPE.booleanValue())))
            .andExpect(jsonPath("$.[*].checkSubscription").value(hasItem(DEFAULT_CHECK_SUBSCRIPTION.booleanValue())))
            .andExpect(jsonPath("$.[*].ignoreFees").value(hasItem(DEFAULT_IGNORE_FEES.booleanValue())))
            .andExpect(jsonPath("$.[*].ignoreLimit").value(hasItem(DEFAULT_IGNORE_LIMIT.booleanValue())))
            .andExpect(jsonPath("$.[*].ignoreCommission").value(hasItem(DEFAULT_IGNORE_COMMISSION.booleanValue())))
            .andExpect(jsonPath("$.[*].checkOtp").value(hasItem(DEFAULT_CHECK_OTP.booleanValue())))
            .andExpect(jsonPath("$.[*].pgTransactionType1Code").value(hasItem(DEFAULT_PG_TRANSACTION_TYPE_1_CODE.toString())))
            .andExpect(jsonPath("$.[*].pgTransactionType2Code").value(hasItem(DEFAULT_PG_TRANSACTION_TYPE_2_CODE.toString())))
            .andExpect(jsonPath("$.[*].partnerOwnerCode").value(hasItem(DEFAULT_PARTNER_OWNER_CODE.toString())))
            .andExpect(jsonPath("$.[*].transactionTypeCode").value(hasItem(DEFAULT_TRANSACTION_TYPE_CODE.toString())))
            .andExpect(jsonPath("$.[*].serviceAuthenticationCode").value(hasItem(DEFAULT_SERVICE_AUTHENTICATION_CODE.toString())))
            .andExpect(jsonPath("$.[*].contractPath").value(hasItem(DEFAULT_CONTRACT_PATH.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].logic").value(hasItem(DEFAULT_LOGIC.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getPgService() throws Exception {
        // Initialize the database
        pgServiceRepository.saveAndFlush(pgService);

        // Get the pgService
        restPgServiceMockMvc.perform(get("/api/pg-services/{id}", pgService.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pgService.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.isNative").value(DEFAULT_IS_NATIVE.booleanValue()))
            .andExpect(jsonPath("$.isSourceInternal").value(DEFAULT_IS_SOURCE_INTERNAL.booleanValue()))
            .andExpect(jsonPath("$.isDestinationInternal").value(DEFAULT_IS_DESTINATION_INTERNAL.booleanValue()))
            .andExpect(jsonPath("$.needSubscription").value(DEFAULT_NEED_SUBSCRIPTION.booleanValue()))
            .andExpect(jsonPath("$.currencyCode").value(DEFAULT_CURRENCY_CODE.toString()))
            .andExpect(jsonPath("$.useTransactionType").value(DEFAULT_USE_TRANSACTION_TYPE.booleanValue()))
            .andExpect(jsonPath("$.checkSubscription").value(DEFAULT_CHECK_SUBSCRIPTION.booleanValue()))
            .andExpect(jsonPath("$.ignoreFees").value(DEFAULT_IGNORE_FEES.booleanValue()))
            .andExpect(jsonPath("$.ignoreLimit").value(DEFAULT_IGNORE_LIMIT.booleanValue()))
            .andExpect(jsonPath("$.ignoreCommission").value(DEFAULT_IGNORE_COMMISSION.booleanValue()))
            .andExpect(jsonPath("$.checkOtp").value(DEFAULT_CHECK_OTP.booleanValue()))
            .andExpect(jsonPath("$.pgTransactionType1Code").value(DEFAULT_PG_TRANSACTION_TYPE_1_CODE.toString()))
            .andExpect(jsonPath("$.pgTransactionType2Code").value(DEFAULT_PG_TRANSACTION_TYPE_2_CODE.toString()))
            .andExpect(jsonPath("$.partnerOwnerCode").value(DEFAULT_PARTNER_OWNER_CODE.toString()))
            .andExpect(jsonPath("$.transactionTypeCode").value(DEFAULT_TRANSACTION_TYPE_CODE.toString()))
            .andExpect(jsonPath("$.serviceAuthenticationCode").value(DEFAULT_SERVICE_AUTHENTICATION_CODE.toString()))
            .andExpect(jsonPath("$.contractPath").value(DEFAULT_CONTRACT_PATH.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.logic").value(DEFAULT_LOGIC.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPgService() throws Exception {
        // Get the pgService
        restPgServiceMockMvc.perform(get("/api/pg-services/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePgService() throws Exception {
        // Initialize the database
        pgServiceRepository.saveAndFlush(pgService);

        int databaseSizeBeforeUpdate = pgServiceRepository.findAll().size();

        // Update the pgService
        PgService updatedPgService = pgServiceRepository.findById(pgService.getId()).get();
        // Disconnect from session so that the updates on updatedPgService are not directly saved in db
        em.detach(updatedPgService);
        updatedPgService
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .isNative(UPDATED_IS_NATIVE)
            .isSourceInternal(UPDATED_IS_SOURCE_INTERNAL)
            .isDestinationInternal(UPDATED_IS_DESTINATION_INTERNAL)
            .needSubscription(UPDATED_NEED_SUBSCRIPTION)
            .currencyCode(UPDATED_CURRENCY_CODE)
            .useTransactionType(UPDATED_USE_TRANSACTION_TYPE)
            .checkSubscription(UPDATED_CHECK_SUBSCRIPTION)
            .ignoreFees(UPDATED_IGNORE_FEES)
            .ignoreLimit(UPDATED_IGNORE_LIMIT)
            .ignoreCommission(UPDATED_IGNORE_COMMISSION)
            .checkOtp(UPDATED_CHECK_OTP)
            .pgTransactionType1Code(UPDATED_PG_TRANSACTION_TYPE_1_CODE)
            .pgTransactionType2Code(UPDATED_PG_TRANSACTION_TYPE_2_CODE)
            .partnerOwnerCode(UPDATED_PARTNER_OWNER_CODE)
            .transactionTypeCode(UPDATED_TRANSACTION_TYPE_CODE)
            .serviceAuthenticationCode(UPDATED_SERVICE_AUTHENTICATION_CODE)
            .contractPath(UPDATED_CONTRACT_PATH)
            .description(UPDATED_DESCRIPTION)
            .logic(UPDATED_LOGIC)
            .active(UPDATED_ACTIVE);
        PgServiceDTO pgServiceDTO = pgServiceMapper.toDto(updatedPgService);

        restPgServiceMockMvc.perform(put("/api/pg-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgServiceDTO)))
            .andExpect(status().isOk());

        // Validate the PgService in the database
        List<PgService> pgServiceList = pgServiceRepository.findAll();
        assertThat(pgServiceList).hasSize(databaseSizeBeforeUpdate);
        PgService testPgService = pgServiceList.get(pgServiceList.size() - 1);
        assertThat(testPgService.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testPgService.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPgService.isIsNative()).isEqualTo(UPDATED_IS_NATIVE);
        assertThat(testPgService.isIsSourceInternal()).isEqualTo(UPDATED_IS_SOURCE_INTERNAL);
        assertThat(testPgService.isIsDestinationInternal()).isEqualTo(UPDATED_IS_DESTINATION_INTERNAL);
        assertThat(testPgService.isNeedSubscription()).isEqualTo(UPDATED_NEED_SUBSCRIPTION);
        assertThat(testPgService.getCurrencyCode()).isEqualTo(UPDATED_CURRENCY_CODE);
        assertThat(testPgService.isUseTransactionType()).isEqualTo(UPDATED_USE_TRANSACTION_TYPE);
        assertThat(testPgService.isCheckSubscription()).isEqualTo(UPDATED_CHECK_SUBSCRIPTION);
        assertThat(testPgService.isIgnoreFees()).isEqualTo(UPDATED_IGNORE_FEES);
        assertThat(testPgService.isIgnoreLimit()).isEqualTo(UPDATED_IGNORE_LIMIT);
        assertThat(testPgService.isIgnoreCommission()).isEqualTo(UPDATED_IGNORE_COMMISSION);
        assertThat(testPgService.isCheckOtp()).isEqualTo(UPDATED_CHECK_OTP);
        assertThat(testPgService.getPgTransactionType1Code()).isEqualTo(UPDATED_PG_TRANSACTION_TYPE_1_CODE);
        assertThat(testPgService.getPgTransactionType2Code()).isEqualTo(UPDATED_PG_TRANSACTION_TYPE_2_CODE);
        assertThat(testPgService.getPartnerOwnerCode()).isEqualTo(UPDATED_PARTNER_OWNER_CODE);
        assertThat(testPgService.getTransactionTypeCode()).isEqualTo(UPDATED_TRANSACTION_TYPE_CODE);
        assertThat(testPgService.getServiceAuthenticationCode()).isEqualTo(UPDATED_SERVICE_AUTHENTICATION_CODE);
        assertThat(testPgService.getContractPath()).isEqualTo(UPDATED_CONTRACT_PATH);
        assertThat(testPgService.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPgService.getLogic()).isEqualTo(UPDATED_LOGIC);
        assertThat(testPgService.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingPgService() throws Exception {
        int databaseSizeBeforeUpdate = pgServiceRepository.findAll().size();

        // Create the PgService
        PgServiceDTO pgServiceDTO = pgServiceMapper.toDto(pgService);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPgServiceMockMvc.perform(put("/api/pg-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgServiceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PgService in the database
        List<PgService> pgServiceList = pgServiceRepository.findAll();
        assertThat(pgServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePgService() throws Exception {
        // Initialize the database
        pgServiceRepository.saveAndFlush(pgService);

        int databaseSizeBeforeDelete = pgServiceRepository.findAll().size();

        // Delete the pgService
        restPgServiceMockMvc.perform(delete("/api/pg-services/{id}", pgService.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PgService> pgServiceList = pgServiceRepository.findAll();
        assertThat(pgServiceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PgService.class);
        PgService pgService1 = new PgService();
        pgService1.setId(1L);
        PgService pgService2 = new PgService();
        pgService2.setId(pgService1.getId());
        assertThat(pgService1).isEqualTo(pgService2);
        pgService2.setId(2L);
        assertThat(pgService1).isNotEqualTo(pgService2);
        pgService1.setId(null);
        assertThat(pgService1).isNotEqualTo(pgService2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PgServiceDTO.class);
        PgServiceDTO pgServiceDTO1 = new PgServiceDTO();
        pgServiceDTO1.setId(1L);
        PgServiceDTO pgServiceDTO2 = new PgServiceDTO();
        assertThat(pgServiceDTO1).isNotEqualTo(pgServiceDTO2);
        pgServiceDTO2.setId(pgServiceDTO1.getId());
        assertThat(pgServiceDTO1).isEqualTo(pgServiceDTO2);
        pgServiceDTO2.setId(2L);
        assertThat(pgServiceDTO1).isNotEqualTo(pgServiceDTO2);
        pgServiceDTO1.setId(null);
        assertThat(pgServiceDTO1).isNotEqualTo(pgServiceDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(pgServiceMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(pgServiceMapper.fromId(null)).isNull();
    }
}
