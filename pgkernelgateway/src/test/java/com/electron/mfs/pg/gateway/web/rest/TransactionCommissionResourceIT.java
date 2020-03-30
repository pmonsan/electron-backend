package com.electron.mfs.pg.gateway.web.rest;

import com.electron.mfs.pg.gateway.PgkernelgatewayApp;
import com.electron.mfs.pg.gateway.config.SecurityBeanOverrideConfiguration;
import com.electron.mfs.pg.gateway.domain.TransactionCommission;
import com.electron.mfs.pg.gateway.repository.TransactionCommissionRepository;
import com.electron.mfs.pg.gateway.service.TransactionCommissionService;
import com.electron.mfs.pg.gateway.service.dto.TransactionCommissionDTO;
import com.electron.mfs.pg.gateway.service.mapper.TransactionCommissionMapper;
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
 * Integration tests for the {@Link TransactionCommissionResource} REST controller.
 */
@EmbeddedKafka
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, PgkernelgatewayApp.class})
public class TransactionCommissionResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final Double DEFAULT_AMOUNT = 0D;
    private static final Double UPDATED_AMOUNT = 1D;

    private static final Boolean DEFAULT_AMOUNT_IN_PERCENT = false;
    private static final Boolean UPDATED_AMOUNT_IN_PERCENT = true;

    private static final Instant DEFAULT_DATE_CREATION = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_CREATION = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Double DEFAULT_PERCENT = 0D;
    private static final Double UPDATED_PERCENT = 1D;

    private static final String DEFAULT_CURRENCY_CODE = "AAAAA";
    private static final String UPDATED_CURRENCY_CODE = "BBBBB";

    private static final String DEFAULT_PARTNER_CODE = "AAAAA";
    private static final String UPDATED_PARTNER_CODE = "BBBBB";

    private static final String DEFAULT_SERVICE_CODE = "AAAAA";
    private static final String UPDATED_SERVICE_CODE = "BBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Double DEFAULT_COMMISSION = 0D;
    private static final Double UPDATED_COMMISSION = 1D;

    @Autowired
    private TransactionCommissionRepository transactionCommissionRepository;

    @Autowired
    private TransactionCommissionMapper transactionCommissionMapper;

    @Autowired
    private TransactionCommissionService transactionCommissionService;

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

    private MockMvc restTransactionCommissionMockMvc;

    private TransactionCommission transactionCommission;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TransactionCommissionResource transactionCommissionResource = new TransactionCommissionResource(transactionCommissionService);
        this.restTransactionCommissionMockMvc = MockMvcBuilders.standaloneSetup(transactionCommissionResource)
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
    public static TransactionCommission createEntity(EntityManager em) {
        TransactionCommission transactionCommission = new TransactionCommission()
            .code(DEFAULT_CODE)
            .label(DEFAULT_LABEL)
            .amount(DEFAULT_AMOUNT)
            .amountInPercent(DEFAULT_AMOUNT_IN_PERCENT)
            .dateCreation(DEFAULT_DATE_CREATION)
            .percent(DEFAULT_PERCENT)
            .currencyCode(DEFAULT_CURRENCY_CODE)
            .partnerCode(DEFAULT_PARTNER_CODE)
            .serviceCode(DEFAULT_SERVICE_CODE)
            .description(DEFAULT_DESCRIPTION)
            .commission(DEFAULT_COMMISSION);
        return transactionCommission;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TransactionCommission createUpdatedEntity(EntityManager em) {
        TransactionCommission transactionCommission = new TransactionCommission()
            .code(UPDATED_CODE)
            .label(UPDATED_LABEL)
            .amount(UPDATED_AMOUNT)
            .amountInPercent(UPDATED_AMOUNT_IN_PERCENT)
            .dateCreation(UPDATED_DATE_CREATION)
            .percent(UPDATED_PERCENT)
            .currencyCode(UPDATED_CURRENCY_CODE)
            .partnerCode(UPDATED_PARTNER_CODE)
            .serviceCode(UPDATED_SERVICE_CODE)
            .description(UPDATED_DESCRIPTION)
            .commission(UPDATED_COMMISSION);
        return transactionCommission;
    }

    @BeforeEach
    public void initTest() {
        transactionCommission = createEntity(em);
    }

    @Test
    @Transactional
    public void createTransactionCommission() throws Exception {
        int databaseSizeBeforeCreate = transactionCommissionRepository.findAll().size();

        // Create the TransactionCommission
        TransactionCommissionDTO transactionCommissionDTO = transactionCommissionMapper.toDto(transactionCommission);
        restTransactionCommissionMockMvc.perform(post("/api/transaction-commissions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionCommissionDTO)))
            .andExpect(status().isCreated());

        // Validate the TransactionCommission in the database
        List<TransactionCommission> transactionCommissionList = transactionCommissionRepository.findAll();
        assertThat(transactionCommissionList).hasSize(databaseSizeBeforeCreate + 1);
        TransactionCommission testTransactionCommission = transactionCommissionList.get(transactionCommissionList.size() - 1);
        assertThat(testTransactionCommission.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testTransactionCommission.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testTransactionCommission.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testTransactionCommission.isAmountInPercent()).isEqualTo(DEFAULT_AMOUNT_IN_PERCENT);
        assertThat(testTransactionCommission.getDateCreation()).isEqualTo(DEFAULT_DATE_CREATION);
        assertThat(testTransactionCommission.getPercent()).isEqualTo(DEFAULT_PERCENT);
        assertThat(testTransactionCommission.getCurrencyCode()).isEqualTo(DEFAULT_CURRENCY_CODE);
        assertThat(testTransactionCommission.getPartnerCode()).isEqualTo(DEFAULT_PARTNER_CODE);
        assertThat(testTransactionCommission.getServiceCode()).isEqualTo(DEFAULT_SERVICE_CODE);
        assertThat(testTransactionCommission.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTransactionCommission.getCommission()).isEqualTo(DEFAULT_COMMISSION);
    }

    @Test
    @Transactional
    public void createTransactionCommissionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = transactionCommissionRepository.findAll().size();

        // Create the TransactionCommission with an existing ID
        transactionCommission.setId(1L);
        TransactionCommissionDTO transactionCommissionDTO = transactionCommissionMapper.toDto(transactionCommission);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransactionCommissionMockMvc.perform(post("/api/transaction-commissions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionCommissionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TransactionCommission in the database
        List<TransactionCommission> transactionCommissionList = transactionCommissionRepository.findAll();
        assertThat(transactionCommissionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionCommissionRepository.findAll().size();
        // set the field null
        transactionCommission.setCode(null);

        // Create the TransactionCommission, which fails.
        TransactionCommissionDTO transactionCommissionDTO = transactionCommissionMapper.toDto(transactionCommission);

        restTransactionCommissionMockMvc.perform(post("/api/transaction-commissions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionCommissionDTO)))
            .andExpect(status().isBadRequest());

        List<TransactionCommission> transactionCommissionList = transactionCommissionRepository.findAll();
        assertThat(transactionCommissionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLabelIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionCommissionRepository.findAll().size();
        // set the field null
        transactionCommission.setLabel(null);

        // Create the TransactionCommission, which fails.
        TransactionCommissionDTO transactionCommissionDTO = transactionCommissionMapper.toDto(transactionCommission);

        restTransactionCommissionMockMvc.perform(post("/api/transaction-commissions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionCommissionDTO)))
            .andExpect(status().isBadRequest());

        List<TransactionCommission> transactionCommissionList = transactionCommissionRepository.findAll();
        assertThat(transactionCommissionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAmountInPercentIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionCommissionRepository.findAll().size();
        // set the field null
        transactionCommission.setAmountInPercent(null);

        // Create the TransactionCommission, which fails.
        TransactionCommissionDTO transactionCommissionDTO = transactionCommissionMapper.toDto(transactionCommission);

        restTransactionCommissionMockMvc.perform(post("/api/transaction-commissions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionCommissionDTO)))
            .andExpect(status().isBadRequest());

        List<TransactionCommission> transactionCommissionList = transactionCommissionRepository.findAll();
        assertThat(transactionCommissionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateCreationIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionCommissionRepository.findAll().size();
        // set the field null
        transactionCommission.setDateCreation(null);

        // Create the TransactionCommission, which fails.
        TransactionCommissionDTO transactionCommissionDTO = transactionCommissionMapper.toDto(transactionCommission);

        restTransactionCommissionMockMvc.perform(post("/api/transaction-commissions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionCommissionDTO)))
            .andExpect(status().isBadRequest());

        List<TransactionCommission> transactionCommissionList = transactionCommissionRepository.findAll();
        assertThat(transactionCommissionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCurrencyCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionCommissionRepository.findAll().size();
        // set the field null
        transactionCommission.setCurrencyCode(null);

        // Create the TransactionCommission, which fails.
        TransactionCommissionDTO transactionCommissionDTO = transactionCommissionMapper.toDto(transactionCommission);

        restTransactionCommissionMockMvc.perform(post("/api/transaction-commissions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionCommissionDTO)))
            .andExpect(status().isBadRequest());

        List<TransactionCommission> transactionCommissionList = transactionCommissionRepository.findAll();
        assertThat(transactionCommissionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPartnerCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionCommissionRepository.findAll().size();
        // set the field null
        transactionCommission.setPartnerCode(null);

        // Create the TransactionCommission, which fails.
        TransactionCommissionDTO transactionCommissionDTO = transactionCommissionMapper.toDto(transactionCommission);

        restTransactionCommissionMockMvc.perform(post("/api/transaction-commissions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionCommissionDTO)))
            .andExpect(status().isBadRequest());

        List<TransactionCommission> transactionCommissionList = transactionCommissionRepository.findAll();
        assertThat(transactionCommissionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkServiceCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionCommissionRepository.findAll().size();
        // set the field null
        transactionCommission.setServiceCode(null);

        // Create the TransactionCommission, which fails.
        TransactionCommissionDTO transactionCommissionDTO = transactionCommissionMapper.toDto(transactionCommission);

        restTransactionCommissionMockMvc.perform(post("/api/transaction-commissions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionCommissionDTO)))
            .andExpect(status().isBadRequest());

        List<TransactionCommission> transactionCommissionList = transactionCommissionRepository.findAll();
        assertThat(transactionCommissionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTransactionCommissions() throws Exception {
        // Initialize the database
        transactionCommissionRepository.saveAndFlush(transactionCommission);

        // Get all the transactionCommissionList
        restTransactionCommissionMockMvc.perform(get("/api/transaction-commissions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transactionCommission.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].amountInPercent").value(hasItem(DEFAULT_AMOUNT_IN_PERCENT.booleanValue())))
            .andExpect(jsonPath("$.[*].dateCreation").value(hasItem(DEFAULT_DATE_CREATION.toString())))
            .andExpect(jsonPath("$.[*].percent").value(hasItem(DEFAULT_PERCENT.doubleValue())))
            .andExpect(jsonPath("$.[*].currencyCode").value(hasItem(DEFAULT_CURRENCY_CODE.toString())))
            .andExpect(jsonPath("$.[*].partnerCode").value(hasItem(DEFAULT_PARTNER_CODE.toString())))
            .andExpect(jsonPath("$.[*].serviceCode").value(hasItem(DEFAULT_SERVICE_CODE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].commission").value(hasItem(DEFAULT_COMMISSION.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getTransactionCommission() throws Exception {
        // Initialize the database
        transactionCommissionRepository.saveAndFlush(transactionCommission);

        // Get the transactionCommission
        restTransactionCommissionMockMvc.perform(get("/api/transaction-commissions/{id}", transactionCommission.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(transactionCommission.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.amountInPercent").value(DEFAULT_AMOUNT_IN_PERCENT.booleanValue()))
            .andExpect(jsonPath("$.dateCreation").value(DEFAULT_DATE_CREATION.toString()))
            .andExpect(jsonPath("$.percent").value(DEFAULT_PERCENT.doubleValue()))
            .andExpect(jsonPath("$.currencyCode").value(DEFAULT_CURRENCY_CODE.toString()))
            .andExpect(jsonPath("$.partnerCode").value(DEFAULT_PARTNER_CODE.toString()))
            .andExpect(jsonPath("$.serviceCode").value(DEFAULT_SERVICE_CODE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.commission").value(DEFAULT_COMMISSION.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTransactionCommission() throws Exception {
        // Get the transactionCommission
        restTransactionCommissionMockMvc.perform(get("/api/transaction-commissions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTransactionCommission() throws Exception {
        // Initialize the database
        transactionCommissionRepository.saveAndFlush(transactionCommission);

        int databaseSizeBeforeUpdate = transactionCommissionRepository.findAll().size();

        // Update the transactionCommission
        TransactionCommission updatedTransactionCommission = transactionCommissionRepository.findById(transactionCommission.getId()).get();
        // Disconnect from session so that the updates on updatedTransactionCommission are not directly saved in db
        em.detach(updatedTransactionCommission);
        updatedTransactionCommission
            .code(UPDATED_CODE)
            .label(UPDATED_LABEL)
            .amount(UPDATED_AMOUNT)
            .amountInPercent(UPDATED_AMOUNT_IN_PERCENT)
            .dateCreation(UPDATED_DATE_CREATION)
            .percent(UPDATED_PERCENT)
            .currencyCode(UPDATED_CURRENCY_CODE)
            .partnerCode(UPDATED_PARTNER_CODE)
            .serviceCode(UPDATED_SERVICE_CODE)
            .description(UPDATED_DESCRIPTION)
            .commission(UPDATED_COMMISSION);
        TransactionCommissionDTO transactionCommissionDTO = transactionCommissionMapper.toDto(updatedTransactionCommission);

        restTransactionCommissionMockMvc.perform(put("/api/transaction-commissions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionCommissionDTO)))
            .andExpect(status().isOk());

        // Validate the TransactionCommission in the database
        List<TransactionCommission> transactionCommissionList = transactionCommissionRepository.findAll();
        assertThat(transactionCommissionList).hasSize(databaseSizeBeforeUpdate);
        TransactionCommission testTransactionCommission = transactionCommissionList.get(transactionCommissionList.size() - 1);
        assertThat(testTransactionCommission.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTransactionCommission.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testTransactionCommission.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testTransactionCommission.isAmountInPercent()).isEqualTo(UPDATED_AMOUNT_IN_PERCENT);
        assertThat(testTransactionCommission.getDateCreation()).isEqualTo(UPDATED_DATE_CREATION);
        assertThat(testTransactionCommission.getPercent()).isEqualTo(UPDATED_PERCENT);
        assertThat(testTransactionCommission.getCurrencyCode()).isEqualTo(UPDATED_CURRENCY_CODE);
        assertThat(testTransactionCommission.getPartnerCode()).isEqualTo(UPDATED_PARTNER_CODE);
        assertThat(testTransactionCommission.getServiceCode()).isEqualTo(UPDATED_SERVICE_CODE);
        assertThat(testTransactionCommission.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTransactionCommission.getCommission()).isEqualTo(UPDATED_COMMISSION);
    }

    @Test
    @Transactional
    public void updateNonExistingTransactionCommission() throws Exception {
        int databaseSizeBeforeUpdate = transactionCommissionRepository.findAll().size();

        // Create the TransactionCommission
        TransactionCommissionDTO transactionCommissionDTO = transactionCommissionMapper.toDto(transactionCommission);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransactionCommissionMockMvc.perform(put("/api/transaction-commissions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionCommissionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TransactionCommission in the database
        List<TransactionCommission> transactionCommissionList = transactionCommissionRepository.findAll();
        assertThat(transactionCommissionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTransactionCommission() throws Exception {
        // Initialize the database
        transactionCommissionRepository.saveAndFlush(transactionCommission);

        int databaseSizeBeforeDelete = transactionCommissionRepository.findAll().size();

        // Delete the transactionCommission
        restTransactionCommissionMockMvc.perform(delete("/api/transaction-commissions/{id}", transactionCommission.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TransactionCommission> transactionCommissionList = transactionCommissionRepository.findAll();
        assertThat(transactionCommissionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TransactionCommission.class);
        TransactionCommission transactionCommission1 = new TransactionCommission();
        transactionCommission1.setId(1L);
        TransactionCommission transactionCommission2 = new TransactionCommission();
        transactionCommission2.setId(transactionCommission1.getId());
        assertThat(transactionCommission1).isEqualTo(transactionCommission2);
        transactionCommission2.setId(2L);
        assertThat(transactionCommission1).isNotEqualTo(transactionCommission2);
        transactionCommission1.setId(null);
        assertThat(transactionCommission1).isNotEqualTo(transactionCommission2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TransactionCommissionDTO.class);
        TransactionCommissionDTO transactionCommissionDTO1 = new TransactionCommissionDTO();
        transactionCommissionDTO1.setId(1L);
        TransactionCommissionDTO transactionCommissionDTO2 = new TransactionCommissionDTO();
        assertThat(transactionCommissionDTO1).isNotEqualTo(transactionCommissionDTO2);
        transactionCommissionDTO2.setId(transactionCommissionDTO1.getId());
        assertThat(transactionCommissionDTO1).isEqualTo(transactionCommissionDTO2);
        transactionCommissionDTO2.setId(2L);
        assertThat(transactionCommissionDTO1).isNotEqualTo(transactionCommissionDTO2);
        transactionCommissionDTO1.setId(null);
        assertThat(transactionCommissionDTO1).isNotEqualTo(transactionCommissionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(transactionCommissionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(transactionCommissionMapper.fromId(null)).isNull();
    }
}
