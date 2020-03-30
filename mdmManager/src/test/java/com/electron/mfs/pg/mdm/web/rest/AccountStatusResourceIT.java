package com.electron.mfs.pg.mdm.web.rest;

import com.electron.mfs.pg.mdm.MdmManagerApp;
import com.electron.mfs.pg.mdm.config.SecurityBeanOverrideConfiguration;
import com.electron.mfs.pg.mdm.domain.AccountStatus;
import com.electron.mfs.pg.mdm.repository.AccountStatusRepository;
import com.electron.mfs.pg.mdm.service.AccountStatusService;
import com.electron.mfs.pg.mdm.service.dto.AccountStatusDTO;
import com.electron.mfs.pg.mdm.service.mapper.AccountStatusMapper;
import com.electron.mfs.pg.mdm.web.rest.errors.ExceptionTranslator;

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

import static com.electron.mfs.pg.mdm.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link AccountStatusResource} REST controller.
 */
@EmbeddedKafka
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, MdmManagerApp.class})
public class AccountStatusResourceIT {

    private static final String DEFAULT_CODE = "AAAAA";
    private static final String UPDATED_CODE = "BBBBB";

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private AccountStatusRepository accountStatusRepository;

    @Autowired
    private AccountStatusMapper accountStatusMapper;

    @Autowired
    private AccountStatusService accountStatusService;

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

    private MockMvc restAccountStatusMockMvc;

    private AccountStatus accountStatus;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AccountStatusResource accountStatusResource = new AccountStatusResource(accountStatusService);
        this.restAccountStatusMockMvc = MockMvcBuilders.standaloneSetup(accountStatusResource)
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
    public static AccountStatus createEntity(EntityManager em) {
        AccountStatus accountStatus = new AccountStatus()
            .code(DEFAULT_CODE)
            .label(DEFAULT_LABEL)
            .active(DEFAULT_ACTIVE);
        return accountStatus;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AccountStatus createUpdatedEntity(EntityManager em) {
        AccountStatus accountStatus = new AccountStatus()
            .code(UPDATED_CODE)
            .label(UPDATED_LABEL)
            .active(UPDATED_ACTIVE);
        return accountStatus;
    }

    @BeforeEach
    public void initTest() {
        accountStatus = createEntity(em);
    }

    @Test
    @Transactional
    public void createAccountStatus() throws Exception {
        int databaseSizeBeforeCreate = accountStatusRepository.findAll().size();

        // Create the AccountStatus
        AccountStatusDTO accountStatusDTO = accountStatusMapper.toDto(accountStatus);
        restAccountStatusMockMvc.perform(post("/api/account-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accountStatusDTO)))
            .andExpect(status().isCreated());

        // Validate the AccountStatus in the database
        List<AccountStatus> accountStatusList = accountStatusRepository.findAll();
        assertThat(accountStatusList).hasSize(databaseSizeBeforeCreate + 1);
        AccountStatus testAccountStatus = accountStatusList.get(accountStatusList.size() - 1);
        assertThat(testAccountStatus.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testAccountStatus.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testAccountStatus.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createAccountStatusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = accountStatusRepository.findAll().size();

        // Create the AccountStatus with an existing ID
        accountStatus.setId(1L);
        AccountStatusDTO accountStatusDTO = accountStatusMapper.toDto(accountStatus);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAccountStatusMockMvc.perform(post("/api/account-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accountStatusDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AccountStatus in the database
        List<AccountStatus> accountStatusList = accountStatusRepository.findAll();
        assertThat(accountStatusList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = accountStatusRepository.findAll().size();
        // set the field null
        accountStatus.setCode(null);

        // Create the AccountStatus, which fails.
        AccountStatusDTO accountStatusDTO = accountStatusMapper.toDto(accountStatus);

        restAccountStatusMockMvc.perform(post("/api/account-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accountStatusDTO)))
            .andExpect(status().isBadRequest());

        List<AccountStatus> accountStatusList = accountStatusRepository.findAll();
        assertThat(accountStatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLabelIsRequired() throws Exception {
        int databaseSizeBeforeTest = accountStatusRepository.findAll().size();
        // set the field null
        accountStatus.setLabel(null);

        // Create the AccountStatus, which fails.
        AccountStatusDTO accountStatusDTO = accountStatusMapper.toDto(accountStatus);

        restAccountStatusMockMvc.perform(post("/api/account-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accountStatusDTO)))
            .andExpect(status().isBadRequest());

        List<AccountStatus> accountStatusList = accountStatusRepository.findAll();
        assertThat(accountStatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = accountStatusRepository.findAll().size();
        // set the field null
        accountStatus.setActive(null);

        // Create the AccountStatus, which fails.
        AccountStatusDTO accountStatusDTO = accountStatusMapper.toDto(accountStatus);

        restAccountStatusMockMvc.perform(post("/api/account-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accountStatusDTO)))
            .andExpect(status().isBadRequest());

        List<AccountStatus> accountStatusList = accountStatusRepository.findAll();
        assertThat(accountStatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAccountStatuses() throws Exception {
        // Initialize the database
        accountStatusRepository.saveAndFlush(accountStatus);

        // Get all the accountStatusList
        restAccountStatusMockMvc.perform(get("/api/account-statuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(accountStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getAccountStatus() throws Exception {
        // Initialize the database
        accountStatusRepository.saveAndFlush(accountStatus);

        // Get the accountStatus
        restAccountStatusMockMvc.perform(get("/api/account-statuses/{id}", accountStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(accountStatus.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAccountStatus() throws Exception {
        // Get the accountStatus
        restAccountStatusMockMvc.perform(get("/api/account-statuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAccountStatus() throws Exception {
        // Initialize the database
        accountStatusRepository.saveAndFlush(accountStatus);

        int databaseSizeBeforeUpdate = accountStatusRepository.findAll().size();

        // Update the accountStatus
        AccountStatus updatedAccountStatus = accountStatusRepository.findById(accountStatus.getId()).get();
        // Disconnect from session so that the updates on updatedAccountStatus are not directly saved in db
        em.detach(updatedAccountStatus);
        updatedAccountStatus
            .code(UPDATED_CODE)
            .label(UPDATED_LABEL)
            .active(UPDATED_ACTIVE);
        AccountStatusDTO accountStatusDTO = accountStatusMapper.toDto(updatedAccountStatus);

        restAccountStatusMockMvc.perform(put("/api/account-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accountStatusDTO)))
            .andExpect(status().isOk());

        // Validate the AccountStatus in the database
        List<AccountStatus> accountStatusList = accountStatusRepository.findAll();
        assertThat(accountStatusList).hasSize(databaseSizeBeforeUpdate);
        AccountStatus testAccountStatus = accountStatusList.get(accountStatusList.size() - 1);
        assertThat(testAccountStatus.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testAccountStatus.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testAccountStatus.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingAccountStatus() throws Exception {
        int databaseSizeBeforeUpdate = accountStatusRepository.findAll().size();

        // Create the AccountStatus
        AccountStatusDTO accountStatusDTO = accountStatusMapper.toDto(accountStatus);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAccountStatusMockMvc.perform(put("/api/account-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accountStatusDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AccountStatus in the database
        List<AccountStatus> accountStatusList = accountStatusRepository.findAll();
        assertThat(accountStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAccountStatus() throws Exception {
        // Initialize the database
        accountStatusRepository.saveAndFlush(accountStatus);

        int databaseSizeBeforeDelete = accountStatusRepository.findAll().size();

        // Delete the accountStatus
        restAccountStatusMockMvc.perform(delete("/api/account-statuses/{id}", accountStatus.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AccountStatus> accountStatusList = accountStatusRepository.findAll();
        assertThat(accountStatusList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AccountStatus.class);
        AccountStatus accountStatus1 = new AccountStatus();
        accountStatus1.setId(1L);
        AccountStatus accountStatus2 = new AccountStatus();
        accountStatus2.setId(accountStatus1.getId());
        assertThat(accountStatus1).isEqualTo(accountStatus2);
        accountStatus2.setId(2L);
        assertThat(accountStatus1).isNotEqualTo(accountStatus2);
        accountStatus1.setId(null);
        assertThat(accountStatus1).isNotEqualTo(accountStatus2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AccountStatusDTO.class);
        AccountStatusDTO accountStatusDTO1 = new AccountStatusDTO();
        accountStatusDTO1.setId(1L);
        AccountStatusDTO accountStatusDTO2 = new AccountStatusDTO();
        assertThat(accountStatusDTO1).isNotEqualTo(accountStatusDTO2);
        accountStatusDTO2.setId(accountStatusDTO1.getId());
        assertThat(accountStatusDTO1).isEqualTo(accountStatusDTO2);
        accountStatusDTO2.setId(2L);
        assertThat(accountStatusDTO1).isNotEqualTo(accountStatusDTO2);
        accountStatusDTO1.setId(null);
        assertThat(accountStatusDTO1).isNotEqualTo(accountStatusDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(accountStatusMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(accountStatusMapper.fromId(null)).isNull();
    }
}
