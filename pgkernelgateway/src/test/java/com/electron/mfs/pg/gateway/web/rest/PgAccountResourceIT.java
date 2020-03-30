package com.electron.mfs.pg.gateway.web.rest;

import com.electron.mfs.pg.gateway.PgkernelgatewayApp;
import com.electron.mfs.pg.gateway.config.SecurityBeanOverrideConfiguration;
import com.electron.mfs.pg.gateway.domain.PgAccount;
import com.electron.mfs.pg.gateway.repository.PgAccountRepository;
import com.electron.mfs.pg.gateway.service.PgAccountService;
import com.electron.mfs.pg.gateway.service.dto.PgAccountDTO;
import com.electron.mfs.pg.gateway.service.mapper.PgAccountMapper;
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
 * Integration tests for the {@Link PgAccountResource} REST controller.
 */
@EmbeddedKafka
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, PgkernelgatewayApp.class})
public class PgAccountResourceIT {

    private static final String DEFAULT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_NUMBER = "BBBBBBBBBB";

    private static final Instant DEFAULT_OPENING_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_OPENING_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_TEMPORARY = false;
    private static final Boolean UPDATED_TEMPORARY = true;

    private static final Instant DEFAULT_CLOSING_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CLOSING_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_IMSI = "AAAAAAAAAA";
    private static final String UPDATED_IMSI = "BBBBBBBBBB";

    private static final String DEFAULT_TRANSACTION_CODE = "JJfT";
    private static final String UPDATED_TRANSACTION_CODE = "bbee";

    private static final Instant DEFAULT_VALIDATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALIDATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_ACCOUNT_STATUS_CODE = "AAAAA";
    private static final String UPDATED_ACCOUNT_STATUS_CODE = "BBBBB";

    private static final String DEFAULT_ACCOUNT_TYPE_CODE = "AAAAA";
    private static final String UPDATED_ACCOUNT_TYPE_CODE = "BBBBB";

    private static final String DEFAULT_CUSTOMER_CODE = "AAAAA";
    private static final String UPDATED_CUSTOMER_CODE = "BBBBB";

    private static final String DEFAULT_CURRENCY_CODE = "AAAAA";
    private static final String UPDATED_CURRENCY_CODE = "BBBBB";

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private PgAccountRepository pgAccountRepository;

    @Autowired
    private PgAccountMapper pgAccountMapper;

    @Autowired
    private PgAccountService pgAccountService;

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

    private MockMvc restPgAccountMockMvc;

    private PgAccount pgAccount;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PgAccountResource pgAccountResource = new PgAccountResource(pgAccountService);
        this.restPgAccountMockMvc = MockMvcBuilders.standaloneSetup(pgAccountResource)
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
    public static PgAccount createEntity(EntityManager em) {
        PgAccount pgAccount = new PgAccount()
            .number(DEFAULT_NUMBER)
            .openingDate(DEFAULT_OPENING_DATE)
            .temporary(DEFAULT_TEMPORARY)
            .closingDate(DEFAULT_CLOSING_DATE)
            .imsi(DEFAULT_IMSI)
            .transactionCode(DEFAULT_TRANSACTION_CODE)
            .validationDate(DEFAULT_VALIDATION_DATE)
            .accountStatusCode(DEFAULT_ACCOUNT_STATUS_CODE)
            .accountTypeCode(DEFAULT_ACCOUNT_TYPE_CODE)
            .customerCode(DEFAULT_CUSTOMER_CODE)
            .currencyCode(DEFAULT_CURRENCY_CODE)
            .comment(DEFAULT_COMMENT)
            .active(DEFAULT_ACTIVE);
        return pgAccount;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PgAccount createUpdatedEntity(EntityManager em) {
        PgAccount pgAccount = new PgAccount()
            .number(UPDATED_NUMBER)
            .openingDate(UPDATED_OPENING_DATE)
            .temporary(UPDATED_TEMPORARY)
            .closingDate(UPDATED_CLOSING_DATE)
            .imsi(UPDATED_IMSI)
            .transactionCode(UPDATED_TRANSACTION_CODE)
            .validationDate(UPDATED_VALIDATION_DATE)
            .accountStatusCode(UPDATED_ACCOUNT_STATUS_CODE)
            .accountTypeCode(UPDATED_ACCOUNT_TYPE_CODE)
            .customerCode(UPDATED_CUSTOMER_CODE)
            .currencyCode(UPDATED_CURRENCY_CODE)
            .comment(UPDATED_COMMENT)
            .active(UPDATED_ACTIVE);
        return pgAccount;
    }

    @BeforeEach
    public void initTest() {
        pgAccount = createEntity(em);
    }

    @Test
    @Transactional
    public void createPgAccount() throws Exception {
        int databaseSizeBeforeCreate = pgAccountRepository.findAll().size();

        // Create the PgAccount
        PgAccountDTO pgAccountDTO = pgAccountMapper.toDto(pgAccount);
        restPgAccountMockMvc.perform(post("/api/pg-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgAccountDTO)))
            .andExpect(status().isCreated());

        // Validate the PgAccount in the database
        List<PgAccount> pgAccountList = pgAccountRepository.findAll();
        assertThat(pgAccountList).hasSize(databaseSizeBeforeCreate + 1);
        PgAccount testPgAccount = pgAccountList.get(pgAccountList.size() - 1);
        assertThat(testPgAccount.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testPgAccount.getOpeningDate()).isEqualTo(DEFAULT_OPENING_DATE);
        assertThat(testPgAccount.isTemporary()).isEqualTo(DEFAULT_TEMPORARY);
        assertThat(testPgAccount.getClosingDate()).isEqualTo(DEFAULT_CLOSING_DATE);
        assertThat(testPgAccount.getImsi()).isEqualTo(DEFAULT_IMSI);
        assertThat(testPgAccount.getTransactionCode()).isEqualTo(DEFAULT_TRANSACTION_CODE);
        assertThat(testPgAccount.getValidationDate()).isEqualTo(DEFAULT_VALIDATION_DATE);
        assertThat(testPgAccount.getAccountStatusCode()).isEqualTo(DEFAULT_ACCOUNT_STATUS_CODE);
        assertThat(testPgAccount.getAccountTypeCode()).isEqualTo(DEFAULT_ACCOUNT_TYPE_CODE);
        assertThat(testPgAccount.getCustomerCode()).isEqualTo(DEFAULT_CUSTOMER_CODE);
        assertThat(testPgAccount.getCurrencyCode()).isEqualTo(DEFAULT_CURRENCY_CODE);
        assertThat(testPgAccount.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testPgAccount.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createPgAccountWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pgAccountRepository.findAll().size();

        // Create the PgAccount with an existing ID
        pgAccount.setId(1L);
        PgAccountDTO pgAccountDTO = pgAccountMapper.toDto(pgAccount);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPgAccountMockMvc.perform(post("/api/pg-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgAccountDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PgAccount in the database
        List<PgAccount> pgAccountList = pgAccountRepository.findAll();
        assertThat(pgAccountList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgAccountRepository.findAll().size();
        // set the field null
        pgAccount.setNumber(null);

        // Create the PgAccount, which fails.
        PgAccountDTO pgAccountDTO = pgAccountMapper.toDto(pgAccount);

        restPgAccountMockMvc.perform(post("/api/pg-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgAccountDTO)))
            .andExpect(status().isBadRequest());

        List<PgAccount> pgAccountList = pgAccountRepository.findAll();
        assertThat(pgAccountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOpeningDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgAccountRepository.findAll().size();
        // set the field null
        pgAccount.setOpeningDate(null);

        // Create the PgAccount, which fails.
        PgAccountDTO pgAccountDTO = pgAccountMapper.toDto(pgAccount);

        restPgAccountMockMvc.perform(post("/api/pg-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgAccountDTO)))
            .andExpect(status().isBadRequest());

        List<PgAccount> pgAccountList = pgAccountRepository.findAll();
        assertThat(pgAccountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTemporaryIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgAccountRepository.findAll().size();
        // set the field null
        pgAccount.setTemporary(null);

        // Create the PgAccount, which fails.
        PgAccountDTO pgAccountDTO = pgAccountMapper.toDto(pgAccount);

        restPgAccountMockMvc.perform(post("/api/pg-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgAccountDTO)))
            .andExpect(status().isBadRequest());

        List<PgAccount> pgAccountList = pgAccountRepository.findAll();
        assertThat(pgAccountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTransactionCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgAccountRepository.findAll().size();
        // set the field null
        pgAccount.setTransactionCode(null);

        // Create the PgAccount, which fails.
        PgAccountDTO pgAccountDTO = pgAccountMapper.toDto(pgAccount);

        restPgAccountMockMvc.perform(post("/api/pg-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgAccountDTO)))
            .andExpect(status().isBadRequest());

        List<PgAccount> pgAccountList = pgAccountRepository.findAll();
        assertThat(pgAccountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAccountStatusCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgAccountRepository.findAll().size();
        // set the field null
        pgAccount.setAccountStatusCode(null);

        // Create the PgAccount, which fails.
        PgAccountDTO pgAccountDTO = pgAccountMapper.toDto(pgAccount);

        restPgAccountMockMvc.perform(post("/api/pg-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgAccountDTO)))
            .andExpect(status().isBadRequest());

        List<PgAccount> pgAccountList = pgAccountRepository.findAll();
        assertThat(pgAccountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAccountTypeCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgAccountRepository.findAll().size();
        // set the field null
        pgAccount.setAccountTypeCode(null);

        // Create the PgAccount, which fails.
        PgAccountDTO pgAccountDTO = pgAccountMapper.toDto(pgAccount);

        restPgAccountMockMvc.perform(post("/api/pg-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgAccountDTO)))
            .andExpect(status().isBadRequest());

        List<PgAccount> pgAccountList = pgAccountRepository.findAll();
        assertThat(pgAccountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCustomerCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgAccountRepository.findAll().size();
        // set the field null
        pgAccount.setCustomerCode(null);

        // Create the PgAccount, which fails.
        PgAccountDTO pgAccountDTO = pgAccountMapper.toDto(pgAccount);

        restPgAccountMockMvc.perform(post("/api/pg-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgAccountDTO)))
            .andExpect(status().isBadRequest());

        List<PgAccount> pgAccountList = pgAccountRepository.findAll();
        assertThat(pgAccountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCurrencyCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgAccountRepository.findAll().size();
        // set the field null
        pgAccount.setCurrencyCode(null);

        // Create the PgAccount, which fails.
        PgAccountDTO pgAccountDTO = pgAccountMapper.toDto(pgAccount);

        restPgAccountMockMvc.perform(post("/api/pg-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgAccountDTO)))
            .andExpect(status().isBadRequest());

        List<PgAccount> pgAccountList = pgAccountRepository.findAll();
        assertThat(pgAccountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgAccountRepository.findAll().size();
        // set the field null
        pgAccount.setActive(null);

        // Create the PgAccount, which fails.
        PgAccountDTO pgAccountDTO = pgAccountMapper.toDto(pgAccount);

        restPgAccountMockMvc.perform(post("/api/pg-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgAccountDTO)))
            .andExpect(status().isBadRequest());

        List<PgAccount> pgAccountList = pgAccountRepository.findAll();
        assertThat(pgAccountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPgAccounts() throws Exception {
        // Initialize the database
        pgAccountRepository.saveAndFlush(pgAccount);

        // Get all the pgAccountList
        restPgAccountMockMvc.perform(get("/api/pg-accounts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pgAccount.getId().intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].openingDate").value(hasItem(DEFAULT_OPENING_DATE.toString())))
            .andExpect(jsonPath("$.[*].temporary").value(hasItem(DEFAULT_TEMPORARY.booleanValue())))
            .andExpect(jsonPath("$.[*].closingDate").value(hasItem(DEFAULT_CLOSING_DATE.toString())))
            .andExpect(jsonPath("$.[*].imsi").value(hasItem(DEFAULT_IMSI.toString())))
            .andExpect(jsonPath("$.[*].transactionCode").value(hasItem(DEFAULT_TRANSACTION_CODE.toString())))
            .andExpect(jsonPath("$.[*].validationDate").value(hasItem(DEFAULT_VALIDATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].accountStatusCode").value(hasItem(DEFAULT_ACCOUNT_STATUS_CODE.toString())))
            .andExpect(jsonPath("$.[*].accountTypeCode").value(hasItem(DEFAULT_ACCOUNT_TYPE_CODE.toString())))
            .andExpect(jsonPath("$.[*].customerCode").value(hasItem(DEFAULT_CUSTOMER_CODE.toString())))
            .andExpect(jsonPath("$.[*].currencyCode").value(hasItem(DEFAULT_CURRENCY_CODE.toString())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getPgAccount() throws Exception {
        // Initialize the database
        pgAccountRepository.saveAndFlush(pgAccount);

        // Get the pgAccount
        restPgAccountMockMvc.perform(get("/api/pg-accounts/{id}", pgAccount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pgAccount.getId().intValue()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER.toString()))
            .andExpect(jsonPath("$.openingDate").value(DEFAULT_OPENING_DATE.toString()))
            .andExpect(jsonPath("$.temporary").value(DEFAULT_TEMPORARY.booleanValue()))
            .andExpect(jsonPath("$.closingDate").value(DEFAULT_CLOSING_DATE.toString()))
            .andExpect(jsonPath("$.imsi").value(DEFAULT_IMSI.toString()))
            .andExpect(jsonPath("$.transactionCode").value(DEFAULT_TRANSACTION_CODE.toString()))
            .andExpect(jsonPath("$.validationDate").value(DEFAULT_VALIDATION_DATE.toString()))
            .andExpect(jsonPath("$.accountStatusCode").value(DEFAULT_ACCOUNT_STATUS_CODE.toString()))
            .andExpect(jsonPath("$.accountTypeCode").value(DEFAULT_ACCOUNT_TYPE_CODE.toString()))
            .andExpect(jsonPath("$.customerCode").value(DEFAULT_CUSTOMER_CODE.toString()))
            .andExpect(jsonPath("$.currencyCode").value(DEFAULT_CURRENCY_CODE.toString()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPgAccount() throws Exception {
        // Get the pgAccount
        restPgAccountMockMvc.perform(get("/api/pg-accounts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePgAccount() throws Exception {
        // Initialize the database
        pgAccountRepository.saveAndFlush(pgAccount);

        int databaseSizeBeforeUpdate = pgAccountRepository.findAll().size();

        // Update the pgAccount
        PgAccount updatedPgAccount = pgAccountRepository.findById(pgAccount.getId()).get();
        // Disconnect from session so that the updates on updatedPgAccount are not directly saved in db
        em.detach(updatedPgAccount);
        updatedPgAccount
            .number(UPDATED_NUMBER)
            .openingDate(UPDATED_OPENING_DATE)
            .temporary(UPDATED_TEMPORARY)
            .closingDate(UPDATED_CLOSING_DATE)
            .imsi(UPDATED_IMSI)
            .transactionCode(UPDATED_TRANSACTION_CODE)
            .validationDate(UPDATED_VALIDATION_DATE)
            .accountStatusCode(UPDATED_ACCOUNT_STATUS_CODE)
            .accountTypeCode(UPDATED_ACCOUNT_TYPE_CODE)
            .customerCode(UPDATED_CUSTOMER_CODE)
            .currencyCode(UPDATED_CURRENCY_CODE)
            .comment(UPDATED_COMMENT)
            .active(UPDATED_ACTIVE);
        PgAccountDTO pgAccountDTO = pgAccountMapper.toDto(updatedPgAccount);

        restPgAccountMockMvc.perform(put("/api/pg-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgAccountDTO)))
            .andExpect(status().isOk());

        // Validate the PgAccount in the database
        List<PgAccount> pgAccountList = pgAccountRepository.findAll();
        assertThat(pgAccountList).hasSize(databaseSizeBeforeUpdate);
        PgAccount testPgAccount = pgAccountList.get(pgAccountList.size() - 1);
        assertThat(testPgAccount.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testPgAccount.getOpeningDate()).isEqualTo(UPDATED_OPENING_DATE);
        assertThat(testPgAccount.isTemporary()).isEqualTo(UPDATED_TEMPORARY);
        assertThat(testPgAccount.getClosingDate()).isEqualTo(UPDATED_CLOSING_DATE);
        assertThat(testPgAccount.getImsi()).isEqualTo(UPDATED_IMSI);
        assertThat(testPgAccount.getTransactionCode()).isEqualTo(UPDATED_TRANSACTION_CODE);
        assertThat(testPgAccount.getValidationDate()).isEqualTo(UPDATED_VALIDATION_DATE);
        assertThat(testPgAccount.getAccountStatusCode()).isEqualTo(UPDATED_ACCOUNT_STATUS_CODE);
        assertThat(testPgAccount.getAccountTypeCode()).isEqualTo(UPDATED_ACCOUNT_TYPE_CODE);
        assertThat(testPgAccount.getCustomerCode()).isEqualTo(UPDATED_CUSTOMER_CODE);
        assertThat(testPgAccount.getCurrencyCode()).isEqualTo(UPDATED_CURRENCY_CODE);
        assertThat(testPgAccount.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testPgAccount.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingPgAccount() throws Exception {
        int databaseSizeBeforeUpdate = pgAccountRepository.findAll().size();

        // Create the PgAccount
        PgAccountDTO pgAccountDTO = pgAccountMapper.toDto(pgAccount);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPgAccountMockMvc.perform(put("/api/pg-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgAccountDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PgAccount in the database
        List<PgAccount> pgAccountList = pgAccountRepository.findAll();
        assertThat(pgAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePgAccount() throws Exception {
        // Initialize the database
        pgAccountRepository.saveAndFlush(pgAccount);

        int databaseSizeBeforeDelete = pgAccountRepository.findAll().size();

        // Delete the pgAccount
        restPgAccountMockMvc.perform(delete("/api/pg-accounts/{id}", pgAccount.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PgAccount> pgAccountList = pgAccountRepository.findAll();
        assertThat(pgAccountList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PgAccount.class);
        PgAccount pgAccount1 = new PgAccount();
        pgAccount1.setId(1L);
        PgAccount pgAccount2 = new PgAccount();
        pgAccount2.setId(pgAccount1.getId());
        assertThat(pgAccount1).isEqualTo(pgAccount2);
        pgAccount2.setId(2L);
        assertThat(pgAccount1).isNotEqualTo(pgAccount2);
        pgAccount1.setId(null);
        assertThat(pgAccount1).isNotEqualTo(pgAccount2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PgAccountDTO.class);
        PgAccountDTO pgAccountDTO1 = new PgAccountDTO();
        pgAccountDTO1.setId(1L);
        PgAccountDTO pgAccountDTO2 = new PgAccountDTO();
        assertThat(pgAccountDTO1).isNotEqualTo(pgAccountDTO2);
        pgAccountDTO2.setId(pgAccountDTO1.getId());
        assertThat(pgAccountDTO1).isEqualTo(pgAccountDTO2);
        pgAccountDTO2.setId(2L);
        assertThat(pgAccountDTO1).isNotEqualTo(pgAccountDTO2);
        pgAccountDTO1.setId(null);
        assertThat(pgAccountDTO1).isNotEqualTo(pgAccountDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(pgAccountMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(pgAccountMapper.fromId(null)).isNull();
    }
}
