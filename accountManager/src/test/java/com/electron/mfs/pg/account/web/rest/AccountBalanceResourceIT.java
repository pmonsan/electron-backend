package com.electron.mfs.pg.account.web.rest;

import com.electron.mfs.pg.account.AccountManagerApp;
import com.electron.mfs.pg.account.config.SecurityBeanOverrideConfiguration;
import com.electron.mfs.pg.account.domain.AccountBalance;
import com.electron.mfs.pg.account.repository.AccountBalanceRepository;
import com.electron.mfs.pg.account.service.AccountBalanceService;
import com.electron.mfs.pg.account.service.dto.AccountBalanceDTO;
import com.electron.mfs.pg.account.service.mapper.AccountBalanceMapper;
import com.electron.mfs.pg.account.web.rest.errors.ExceptionTranslator;

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

import static com.electron.mfs.pg.account.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link AccountBalanceResource} REST controller.
 */
@EmbeddedKafka
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, AccountManagerApp.class})
public class AccountBalanceResourceIT {

    private static final Instant DEFAULT_SITUATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SITUATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Double DEFAULT_BALANCE = 0D;
    private static final Double UPDATED_BALANCE = 1D;

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private AccountBalanceRepository accountBalanceRepository;

    @Autowired
    private AccountBalanceMapper accountBalanceMapper;

    @Autowired
    private AccountBalanceService accountBalanceService;

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

    private MockMvc restAccountBalanceMockMvc;

    private AccountBalance accountBalance;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AccountBalanceResource accountBalanceResource = new AccountBalanceResource(accountBalanceService);
        this.restAccountBalanceMockMvc = MockMvcBuilders.standaloneSetup(accountBalanceResource)
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
    public static AccountBalance createEntity(EntityManager em) {
        AccountBalance accountBalance = new AccountBalance()
            .situationDate(DEFAULT_SITUATION_DATE)
            .balance(DEFAULT_BALANCE)
            .active(DEFAULT_ACTIVE);
        return accountBalance;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AccountBalance createUpdatedEntity(EntityManager em) {
        AccountBalance accountBalance = new AccountBalance()
            .situationDate(UPDATED_SITUATION_DATE)
            .balance(UPDATED_BALANCE)
            .active(UPDATED_ACTIVE);
        return accountBalance;
    }

    @BeforeEach
    public void initTest() {
        accountBalance = createEntity(em);
    }

    @Test
    @Transactional
    public void createAccountBalance() throws Exception {
        int databaseSizeBeforeCreate = accountBalanceRepository.findAll().size();

        // Create the AccountBalance
        AccountBalanceDTO accountBalanceDTO = accountBalanceMapper.toDto(accountBalance);
        restAccountBalanceMockMvc.perform(post("/api/account-balances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accountBalanceDTO)))
            .andExpect(status().isCreated());

        // Validate the AccountBalance in the database
        List<AccountBalance> accountBalanceList = accountBalanceRepository.findAll();
        assertThat(accountBalanceList).hasSize(databaseSizeBeforeCreate + 1);
        AccountBalance testAccountBalance = accountBalanceList.get(accountBalanceList.size() - 1);
        assertThat(testAccountBalance.getSituationDate()).isEqualTo(DEFAULT_SITUATION_DATE);
        assertThat(testAccountBalance.getBalance()).isEqualTo(DEFAULT_BALANCE);
        assertThat(testAccountBalance.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createAccountBalanceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = accountBalanceRepository.findAll().size();

        // Create the AccountBalance with an existing ID
        accountBalance.setId(1L);
        AccountBalanceDTO accountBalanceDTO = accountBalanceMapper.toDto(accountBalance);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAccountBalanceMockMvc.perform(post("/api/account-balances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accountBalanceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AccountBalance in the database
        List<AccountBalance> accountBalanceList = accountBalanceRepository.findAll();
        assertThat(accountBalanceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkSituationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = accountBalanceRepository.findAll().size();
        // set the field null
        accountBalance.setSituationDate(null);

        // Create the AccountBalance, which fails.
        AccountBalanceDTO accountBalanceDTO = accountBalanceMapper.toDto(accountBalance);

        restAccountBalanceMockMvc.perform(post("/api/account-balances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accountBalanceDTO)))
            .andExpect(status().isBadRequest());

        List<AccountBalance> accountBalanceList = accountBalanceRepository.findAll();
        assertThat(accountBalanceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = accountBalanceRepository.findAll().size();
        // set the field null
        accountBalance.setActive(null);

        // Create the AccountBalance, which fails.
        AccountBalanceDTO accountBalanceDTO = accountBalanceMapper.toDto(accountBalance);

        restAccountBalanceMockMvc.perform(post("/api/account-balances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accountBalanceDTO)))
            .andExpect(status().isBadRequest());

        List<AccountBalance> accountBalanceList = accountBalanceRepository.findAll();
        assertThat(accountBalanceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAccountBalances() throws Exception {
        // Initialize the database
        accountBalanceRepository.saveAndFlush(accountBalance);

        // Get all the accountBalanceList
        restAccountBalanceMockMvc.perform(get("/api/account-balances?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(accountBalance.getId().intValue())))
            .andExpect(jsonPath("$.[*].situationDate").value(hasItem(DEFAULT_SITUATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].balance").value(hasItem(DEFAULT_BALANCE.doubleValue())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getAccountBalance() throws Exception {
        // Initialize the database
        accountBalanceRepository.saveAndFlush(accountBalance);

        // Get the accountBalance
        restAccountBalanceMockMvc.perform(get("/api/account-balances/{id}", accountBalance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(accountBalance.getId().intValue()))
            .andExpect(jsonPath("$.situationDate").value(DEFAULT_SITUATION_DATE.toString()))
            .andExpect(jsonPath("$.balance").value(DEFAULT_BALANCE.doubleValue()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAccountBalance() throws Exception {
        // Get the accountBalance
        restAccountBalanceMockMvc.perform(get("/api/account-balances/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAccountBalance() throws Exception {
        // Initialize the database
        accountBalanceRepository.saveAndFlush(accountBalance);

        int databaseSizeBeforeUpdate = accountBalanceRepository.findAll().size();

        // Update the accountBalance
        AccountBalance updatedAccountBalance = accountBalanceRepository.findById(accountBalance.getId()).get();
        // Disconnect from session so that the updates on updatedAccountBalance are not directly saved in db
        em.detach(updatedAccountBalance);
        updatedAccountBalance
            .situationDate(UPDATED_SITUATION_DATE)
            .balance(UPDATED_BALANCE)
            .active(UPDATED_ACTIVE);
        AccountBalanceDTO accountBalanceDTO = accountBalanceMapper.toDto(updatedAccountBalance);

        restAccountBalanceMockMvc.perform(put("/api/account-balances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accountBalanceDTO)))
            .andExpect(status().isOk());

        // Validate the AccountBalance in the database
        List<AccountBalance> accountBalanceList = accountBalanceRepository.findAll();
        assertThat(accountBalanceList).hasSize(databaseSizeBeforeUpdate);
        AccountBalance testAccountBalance = accountBalanceList.get(accountBalanceList.size() - 1);
        assertThat(testAccountBalance.getSituationDate()).isEqualTo(UPDATED_SITUATION_DATE);
        assertThat(testAccountBalance.getBalance()).isEqualTo(UPDATED_BALANCE);
        assertThat(testAccountBalance.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingAccountBalance() throws Exception {
        int databaseSizeBeforeUpdate = accountBalanceRepository.findAll().size();

        // Create the AccountBalance
        AccountBalanceDTO accountBalanceDTO = accountBalanceMapper.toDto(accountBalance);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAccountBalanceMockMvc.perform(put("/api/account-balances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accountBalanceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AccountBalance in the database
        List<AccountBalance> accountBalanceList = accountBalanceRepository.findAll();
        assertThat(accountBalanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAccountBalance() throws Exception {
        // Initialize the database
        accountBalanceRepository.saveAndFlush(accountBalance);

        int databaseSizeBeforeDelete = accountBalanceRepository.findAll().size();

        // Delete the accountBalance
        restAccountBalanceMockMvc.perform(delete("/api/account-balances/{id}", accountBalance.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AccountBalance> accountBalanceList = accountBalanceRepository.findAll();
        assertThat(accountBalanceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AccountBalance.class);
        AccountBalance accountBalance1 = new AccountBalance();
        accountBalance1.setId(1L);
        AccountBalance accountBalance2 = new AccountBalance();
        accountBalance2.setId(accountBalance1.getId());
        assertThat(accountBalance1).isEqualTo(accountBalance2);
        accountBalance2.setId(2L);
        assertThat(accountBalance1).isNotEqualTo(accountBalance2);
        accountBalance1.setId(null);
        assertThat(accountBalance1).isNotEqualTo(accountBalance2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AccountBalanceDTO.class);
        AccountBalanceDTO accountBalanceDTO1 = new AccountBalanceDTO();
        accountBalanceDTO1.setId(1L);
        AccountBalanceDTO accountBalanceDTO2 = new AccountBalanceDTO();
        assertThat(accountBalanceDTO1).isNotEqualTo(accountBalanceDTO2);
        accountBalanceDTO2.setId(accountBalanceDTO1.getId());
        assertThat(accountBalanceDTO1).isEqualTo(accountBalanceDTO2);
        accountBalanceDTO2.setId(2L);
        assertThat(accountBalanceDTO1).isNotEqualTo(accountBalanceDTO2);
        accountBalanceDTO1.setId(null);
        assertThat(accountBalanceDTO1).isNotEqualTo(accountBalanceDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(accountBalanceMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(accountBalanceMapper.fromId(null)).isNull();
    }
}
