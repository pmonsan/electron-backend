package com.electron.mfs.pg.gateway.web.rest;

import com.electron.mfs.pg.gateway.PgkernelgatewayApp;
import com.electron.mfs.pg.gateway.config.SecurityBeanOverrideConfiguration;
import com.electron.mfs.pg.gateway.domain.TransactionType;
import com.electron.mfs.pg.gateway.repository.TransactionTypeRepository;
import com.electron.mfs.pg.gateway.service.TransactionTypeService;
import com.electron.mfs.pg.gateway.service.dto.TransactionTypeDTO;
import com.electron.mfs.pg.gateway.service.mapper.TransactionTypeMapper;
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
 * Integration tests for the {@Link TransactionTypeResource} REST controller.
 */
@EmbeddedKafka
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, PgkernelgatewayApp.class})
public class TransactionTypeResourceIT {

    private static final String DEFAULT_CODE = "AAAAA";
    private static final String UPDATED_CODE = "BBBBB";

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_USE_TRANSACTION_GROUP = false;
    private static final Boolean UPDATED_USE_TRANSACTION_GROUP = true;

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

    private static final String DEFAULT_PG_MESSAGE_MODEL_CODE = "AAAAA";
    private static final String UPDATED_PG_MESSAGE_MODEL_CODE = "BBBBB";

    private static final String DEFAULT_TRANSACTION_GROUP_CODE = "AAAAA";
    private static final String UPDATED_TRANSACTION_GROUP_CODE = "BBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private TransactionTypeRepository transactionTypeRepository;

    @Autowired
    private TransactionTypeMapper transactionTypeMapper;

    @Autowired
    private TransactionTypeService transactionTypeService;

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

    private MockMvc restTransactionTypeMockMvc;

    private TransactionType transactionType;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TransactionTypeResource transactionTypeResource = new TransactionTypeResource(transactionTypeService);
        this.restTransactionTypeMockMvc = MockMvcBuilders.standaloneSetup(transactionTypeResource)
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
    public static TransactionType createEntity(EntityManager em) {
        TransactionType transactionType = new TransactionType()
            .code(DEFAULT_CODE)
            .label(DEFAULT_LABEL)
            .useTransactionGroup(DEFAULT_USE_TRANSACTION_GROUP)
            .checkSubscription(DEFAULT_CHECK_SUBSCRIPTION)
            .ignoreFees(DEFAULT_IGNORE_FEES)
            .ignoreLimit(DEFAULT_IGNORE_LIMIT)
            .ignoreCommission(DEFAULT_IGNORE_COMMISSION)
            .checkOtp(DEFAULT_CHECK_OTP)
            .pgMessageModelCode(DEFAULT_PG_MESSAGE_MODEL_CODE)
            .transactionGroupCode(DEFAULT_TRANSACTION_GROUP_CODE)
            .active(DEFAULT_ACTIVE);
        return transactionType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TransactionType createUpdatedEntity(EntityManager em) {
        TransactionType transactionType = new TransactionType()
            .code(UPDATED_CODE)
            .label(UPDATED_LABEL)
            .useTransactionGroup(UPDATED_USE_TRANSACTION_GROUP)
            .checkSubscription(UPDATED_CHECK_SUBSCRIPTION)
            .ignoreFees(UPDATED_IGNORE_FEES)
            .ignoreLimit(UPDATED_IGNORE_LIMIT)
            .ignoreCommission(UPDATED_IGNORE_COMMISSION)
            .checkOtp(UPDATED_CHECK_OTP)
            .pgMessageModelCode(UPDATED_PG_MESSAGE_MODEL_CODE)
            .transactionGroupCode(UPDATED_TRANSACTION_GROUP_CODE)
            .active(UPDATED_ACTIVE);
        return transactionType;
    }

    @BeforeEach
    public void initTest() {
        transactionType = createEntity(em);
    }

    @Test
    @Transactional
    public void createTransactionType() throws Exception {
        int databaseSizeBeforeCreate = transactionTypeRepository.findAll().size();

        // Create the TransactionType
        TransactionTypeDTO transactionTypeDTO = transactionTypeMapper.toDto(transactionType);
        restTransactionTypeMockMvc.perform(post("/api/transaction-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the TransactionType in the database
        List<TransactionType> transactionTypeList = transactionTypeRepository.findAll();
        assertThat(transactionTypeList).hasSize(databaseSizeBeforeCreate + 1);
        TransactionType testTransactionType = transactionTypeList.get(transactionTypeList.size() - 1);
        assertThat(testTransactionType.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testTransactionType.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testTransactionType.isUseTransactionGroup()).isEqualTo(DEFAULT_USE_TRANSACTION_GROUP);
        assertThat(testTransactionType.isCheckSubscription()).isEqualTo(DEFAULT_CHECK_SUBSCRIPTION);
        assertThat(testTransactionType.isIgnoreFees()).isEqualTo(DEFAULT_IGNORE_FEES);
        assertThat(testTransactionType.isIgnoreLimit()).isEqualTo(DEFAULT_IGNORE_LIMIT);
        assertThat(testTransactionType.isIgnoreCommission()).isEqualTo(DEFAULT_IGNORE_COMMISSION);
        assertThat(testTransactionType.isCheckOtp()).isEqualTo(DEFAULT_CHECK_OTP);
        assertThat(testTransactionType.getPgMessageModelCode()).isEqualTo(DEFAULT_PG_MESSAGE_MODEL_CODE);
        assertThat(testTransactionType.getTransactionGroupCode()).isEqualTo(DEFAULT_TRANSACTION_GROUP_CODE);
        assertThat(testTransactionType.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createTransactionTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = transactionTypeRepository.findAll().size();

        // Create the TransactionType with an existing ID
        transactionType.setId(1L);
        TransactionTypeDTO transactionTypeDTO = transactionTypeMapper.toDto(transactionType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransactionTypeMockMvc.perform(post("/api/transaction-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TransactionType in the database
        List<TransactionType> transactionTypeList = transactionTypeRepository.findAll();
        assertThat(transactionTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionTypeRepository.findAll().size();
        // set the field null
        transactionType.setCode(null);

        // Create the TransactionType, which fails.
        TransactionTypeDTO transactionTypeDTO = transactionTypeMapper.toDto(transactionType);

        restTransactionTypeMockMvc.perform(post("/api/transaction-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionTypeDTO)))
            .andExpect(status().isBadRequest());

        List<TransactionType> transactionTypeList = transactionTypeRepository.findAll();
        assertThat(transactionTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLabelIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionTypeRepository.findAll().size();
        // set the field null
        transactionType.setLabel(null);

        // Create the TransactionType, which fails.
        TransactionTypeDTO transactionTypeDTO = transactionTypeMapper.toDto(transactionType);

        restTransactionTypeMockMvc.perform(post("/api/transaction-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionTypeDTO)))
            .andExpect(status().isBadRequest());

        List<TransactionType> transactionTypeList = transactionTypeRepository.findAll();
        assertThat(transactionTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUseTransactionGroupIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionTypeRepository.findAll().size();
        // set the field null
        transactionType.setUseTransactionGroup(null);

        // Create the TransactionType, which fails.
        TransactionTypeDTO transactionTypeDTO = transactionTypeMapper.toDto(transactionType);

        restTransactionTypeMockMvc.perform(post("/api/transaction-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionTypeDTO)))
            .andExpect(status().isBadRequest());

        List<TransactionType> transactionTypeList = transactionTypeRepository.findAll();
        assertThat(transactionTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCheckSubscriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionTypeRepository.findAll().size();
        // set the field null
        transactionType.setCheckSubscription(null);

        // Create the TransactionType, which fails.
        TransactionTypeDTO transactionTypeDTO = transactionTypeMapper.toDto(transactionType);

        restTransactionTypeMockMvc.perform(post("/api/transaction-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionTypeDTO)))
            .andExpect(status().isBadRequest());

        List<TransactionType> transactionTypeList = transactionTypeRepository.findAll();
        assertThat(transactionTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIgnoreFeesIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionTypeRepository.findAll().size();
        // set the field null
        transactionType.setIgnoreFees(null);

        // Create the TransactionType, which fails.
        TransactionTypeDTO transactionTypeDTO = transactionTypeMapper.toDto(transactionType);

        restTransactionTypeMockMvc.perform(post("/api/transaction-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionTypeDTO)))
            .andExpect(status().isBadRequest());

        List<TransactionType> transactionTypeList = transactionTypeRepository.findAll();
        assertThat(transactionTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIgnoreLimitIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionTypeRepository.findAll().size();
        // set the field null
        transactionType.setIgnoreLimit(null);

        // Create the TransactionType, which fails.
        TransactionTypeDTO transactionTypeDTO = transactionTypeMapper.toDto(transactionType);

        restTransactionTypeMockMvc.perform(post("/api/transaction-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionTypeDTO)))
            .andExpect(status().isBadRequest());

        List<TransactionType> transactionTypeList = transactionTypeRepository.findAll();
        assertThat(transactionTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIgnoreCommissionIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionTypeRepository.findAll().size();
        // set the field null
        transactionType.setIgnoreCommission(null);

        // Create the TransactionType, which fails.
        TransactionTypeDTO transactionTypeDTO = transactionTypeMapper.toDto(transactionType);

        restTransactionTypeMockMvc.perform(post("/api/transaction-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionTypeDTO)))
            .andExpect(status().isBadRequest());

        List<TransactionType> transactionTypeList = transactionTypeRepository.findAll();
        assertThat(transactionTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCheckOtpIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionTypeRepository.findAll().size();
        // set the field null
        transactionType.setCheckOtp(null);

        // Create the TransactionType, which fails.
        TransactionTypeDTO transactionTypeDTO = transactionTypeMapper.toDto(transactionType);

        restTransactionTypeMockMvc.perform(post("/api/transaction-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionTypeDTO)))
            .andExpect(status().isBadRequest());

        List<TransactionType> transactionTypeList = transactionTypeRepository.findAll();
        assertThat(transactionTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPgMessageModelCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionTypeRepository.findAll().size();
        // set the field null
        transactionType.setPgMessageModelCode(null);

        // Create the TransactionType, which fails.
        TransactionTypeDTO transactionTypeDTO = transactionTypeMapper.toDto(transactionType);

        restTransactionTypeMockMvc.perform(post("/api/transaction-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionTypeDTO)))
            .andExpect(status().isBadRequest());

        List<TransactionType> transactionTypeList = transactionTypeRepository.findAll();
        assertThat(transactionTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTransactionGroupCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionTypeRepository.findAll().size();
        // set the field null
        transactionType.setTransactionGroupCode(null);

        // Create the TransactionType, which fails.
        TransactionTypeDTO transactionTypeDTO = transactionTypeMapper.toDto(transactionType);

        restTransactionTypeMockMvc.perform(post("/api/transaction-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionTypeDTO)))
            .andExpect(status().isBadRequest());

        List<TransactionType> transactionTypeList = transactionTypeRepository.findAll();
        assertThat(transactionTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionTypeRepository.findAll().size();
        // set the field null
        transactionType.setActive(null);

        // Create the TransactionType, which fails.
        TransactionTypeDTO transactionTypeDTO = transactionTypeMapper.toDto(transactionType);

        restTransactionTypeMockMvc.perform(post("/api/transaction-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionTypeDTO)))
            .andExpect(status().isBadRequest());

        List<TransactionType> transactionTypeList = transactionTypeRepository.findAll();
        assertThat(transactionTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTransactionTypes() throws Exception {
        // Initialize the database
        transactionTypeRepository.saveAndFlush(transactionType);

        // Get all the transactionTypeList
        restTransactionTypeMockMvc.perform(get("/api/transaction-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transactionType.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())))
            .andExpect(jsonPath("$.[*].useTransactionGroup").value(hasItem(DEFAULT_USE_TRANSACTION_GROUP.booleanValue())))
            .andExpect(jsonPath("$.[*].checkSubscription").value(hasItem(DEFAULT_CHECK_SUBSCRIPTION.booleanValue())))
            .andExpect(jsonPath("$.[*].ignoreFees").value(hasItem(DEFAULT_IGNORE_FEES.booleanValue())))
            .andExpect(jsonPath("$.[*].ignoreLimit").value(hasItem(DEFAULT_IGNORE_LIMIT.booleanValue())))
            .andExpect(jsonPath("$.[*].ignoreCommission").value(hasItem(DEFAULT_IGNORE_COMMISSION.booleanValue())))
            .andExpect(jsonPath("$.[*].checkOtp").value(hasItem(DEFAULT_CHECK_OTP.booleanValue())))
            .andExpect(jsonPath("$.[*].pgMessageModelCode").value(hasItem(DEFAULT_PG_MESSAGE_MODEL_CODE.toString())))
            .andExpect(jsonPath("$.[*].transactionGroupCode").value(hasItem(DEFAULT_TRANSACTION_GROUP_CODE.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getTransactionType() throws Exception {
        // Initialize the database
        transactionTypeRepository.saveAndFlush(transactionType);

        // Get the transactionType
        restTransactionTypeMockMvc.perform(get("/api/transaction-types/{id}", transactionType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(transactionType.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()))
            .andExpect(jsonPath("$.useTransactionGroup").value(DEFAULT_USE_TRANSACTION_GROUP.booleanValue()))
            .andExpect(jsonPath("$.checkSubscription").value(DEFAULT_CHECK_SUBSCRIPTION.booleanValue()))
            .andExpect(jsonPath("$.ignoreFees").value(DEFAULT_IGNORE_FEES.booleanValue()))
            .andExpect(jsonPath("$.ignoreLimit").value(DEFAULT_IGNORE_LIMIT.booleanValue()))
            .andExpect(jsonPath("$.ignoreCommission").value(DEFAULT_IGNORE_COMMISSION.booleanValue()))
            .andExpect(jsonPath("$.checkOtp").value(DEFAULT_CHECK_OTP.booleanValue()))
            .andExpect(jsonPath("$.pgMessageModelCode").value(DEFAULT_PG_MESSAGE_MODEL_CODE.toString()))
            .andExpect(jsonPath("$.transactionGroupCode").value(DEFAULT_TRANSACTION_GROUP_CODE.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTransactionType() throws Exception {
        // Get the transactionType
        restTransactionTypeMockMvc.perform(get("/api/transaction-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTransactionType() throws Exception {
        // Initialize the database
        transactionTypeRepository.saveAndFlush(transactionType);

        int databaseSizeBeforeUpdate = transactionTypeRepository.findAll().size();

        // Update the transactionType
        TransactionType updatedTransactionType = transactionTypeRepository.findById(transactionType.getId()).get();
        // Disconnect from session so that the updates on updatedTransactionType are not directly saved in db
        em.detach(updatedTransactionType);
        updatedTransactionType
            .code(UPDATED_CODE)
            .label(UPDATED_LABEL)
            .useTransactionGroup(UPDATED_USE_TRANSACTION_GROUP)
            .checkSubscription(UPDATED_CHECK_SUBSCRIPTION)
            .ignoreFees(UPDATED_IGNORE_FEES)
            .ignoreLimit(UPDATED_IGNORE_LIMIT)
            .ignoreCommission(UPDATED_IGNORE_COMMISSION)
            .checkOtp(UPDATED_CHECK_OTP)
            .pgMessageModelCode(UPDATED_PG_MESSAGE_MODEL_CODE)
            .transactionGroupCode(UPDATED_TRANSACTION_GROUP_CODE)
            .active(UPDATED_ACTIVE);
        TransactionTypeDTO transactionTypeDTO = transactionTypeMapper.toDto(updatedTransactionType);

        restTransactionTypeMockMvc.perform(put("/api/transaction-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionTypeDTO)))
            .andExpect(status().isOk());

        // Validate the TransactionType in the database
        List<TransactionType> transactionTypeList = transactionTypeRepository.findAll();
        assertThat(transactionTypeList).hasSize(databaseSizeBeforeUpdate);
        TransactionType testTransactionType = transactionTypeList.get(transactionTypeList.size() - 1);
        assertThat(testTransactionType.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTransactionType.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testTransactionType.isUseTransactionGroup()).isEqualTo(UPDATED_USE_TRANSACTION_GROUP);
        assertThat(testTransactionType.isCheckSubscription()).isEqualTo(UPDATED_CHECK_SUBSCRIPTION);
        assertThat(testTransactionType.isIgnoreFees()).isEqualTo(UPDATED_IGNORE_FEES);
        assertThat(testTransactionType.isIgnoreLimit()).isEqualTo(UPDATED_IGNORE_LIMIT);
        assertThat(testTransactionType.isIgnoreCommission()).isEqualTo(UPDATED_IGNORE_COMMISSION);
        assertThat(testTransactionType.isCheckOtp()).isEqualTo(UPDATED_CHECK_OTP);
        assertThat(testTransactionType.getPgMessageModelCode()).isEqualTo(UPDATED_PG_MESSAGE_MODEL_CODE);
        assertThat(testTransactionType.getTransactionGroupCode()).isEqualTo(UPDATED_TRANSACTION_GROUP_CODE);
        assertThat(testTransactionType.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingTransactionType() throws Exception {
        int databaseSizeBeforeUpdate = transactionTypeRepository.findAll().size();

        // Create the TransactionType
        TransactionTypeDTO transactionTypeDTO = transactionTypeMapper.toDto(transactionType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransactionTypeMockMvc.perform(put("/api/transaction-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TransactionType in the database
        List<TransactionType> transactionTypeList = transactionTypeRepository.findAll();
        assertThat(transactionTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTransactionType() throws Exception {
        // Initialize the database
        transactionTypeRepository.saveAndFlush(transactionType);

        int databaseSizeBeforeDelete = transactionTypeRepository.findAll().size();

        // Delete the transactionType
        restTransactionTypeMockMvc.perform(delete("/api/transaction-types/{id}", transactionType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TransactionType> transactionTypeList = transactionTypeRepository.findAll();
        assertThat(transactionTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TransactionType.class);
        TransactionType transactionType1 = new TransactionType();
        transactionType1.setId(1L);
        TransactionType transactionType2 = new TransactionType();
        transactionType2.setId(transactionType1.getId());
        assertThat(transactionType1).isEqualTo(transactionType2);
        transactionType2.setId(2L);
        assertThat(transactionType1).isNotEqualTo(transactionType2);
        transactionType1.setId(null);
        assertThat(transactionType1).isNotEqualTo(transactionType2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TransactionTypeDTO.class);
        TransactionTypeDTO transactionTypeDTO1 = new TransactionTypeDTO();
        transactionTypeDTO1.setId(1L);
        TransactionTypeDTO transactionTypeDTO2 = new TransactionTypeDTO();
        assertThat(transactionTypeDTO1).isNotEqualTo(transactionTypeDTO2);
        transactionTypeDTO2.setId(transactionTypeDTO1.getId());
        assertThat(transactionTypeDTO1).isEqualTo(transactionTypeDTO2);
        transactionTypeDTO2.setId(2L);
        assertThat(transactionTypeDTO1).isNotEqualTo(transactionTypeDTO2);
        transactionTypeDTO1.setId(null);
        assertThat(transactionTypeDTO1).isNotEqualTo(transactionTypeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(transactionTypeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(transactionTypeMapper.fromId(null)).isNull();
    }
}
