package com.electron.mfs.pg.gateway.web.rest;

import com.electron.mfs.pg.gateway.PgkernelgatewayApp;
import com.electron.mfs.pg.gateway.config.SecurityBeanOverrideConfiguration;
import com.electron.mfs.pg.gateway.domain.AccountType;
import com.electron.mfs.pg.gateway.repository.AccountTypeRepository;
import com.electron.mfs.pg.gateway.service.AccountTypeService;
import com.electron.mfs.pg.gateway.service.dto.AccountTypeDTO;
import com.electron.mfs.pg.gateway.service.mapper.AccountTypeMapper;
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
 * Integration tests for the {@Link AccountTypeResource} REST controller.
 */
@EmbeddedKafka
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, PgkernelgatewayApp.class})
public class AccountTypeResourceIT {

    private static final String DEFAULT_CODE = "AAAAA";
    private static final String UPDATED_CODE = "BBBBB";

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private AccountTypeRepository accountTypeRepository;

    @Autowired
    private AccountTypeMapper accountTypeMapper;

    @Autowired
    private AccountTypeService accountTypeService;

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

    private MockMvc restAccountTypeMockMvc;

    private AccountType accountType;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AccountTypeResource accountTypeResource = new AccountTypeResource(accountTypeService);
        this.restAccountTypeMockMvc = MockMvcBuilders.standaloneSetup(accountTypeResource)
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
    public static AccountType createEntity(EntityManager em) {
        AccountType accountType = new AccountType()
            .code(DEFAULT_CODE)
            .label(DEFAULT_LABEL)
            .active(DEFAULT_ACTIVE);
        return accountType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AccountType createUpdatedEntity(EntityManager em) {
        AccountType accountType = new AccountType()
            .code(UPDATED_CODE)
            .label(UPDATED_LABEL)
            .active(UPDATED_ACTIVE);
        return accountType;
    }

    @BeforeEach
    public void initTest() {
        accountType = createEntity(em);
    }

    @Test
    @Transactional
    public void createAccountType() throws Exception {
        int databaseSizeBeforeCreate = accountTypeRepository.findAll().size();

        // Create the AccountType
        AccountTypeDTO accountTypeDTO = accountTypeMapper.toDto(accountType);
        restAccountTypeMockMvc.perform(post("/api/account-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accountTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the AccountType in the database
        List<AccountType> accountTypeList = accountTypeRepository.findAll();
        assertThat(accountTypeList).hasSize(databaseSizeBeforeCreate + 1);
        AccountType testAccountType = accountTypeList.get(accountTypeList.size() - 1);
        assertThat(testAccountType.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testAccountType.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testAccountType.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createAccountTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = accountTypeRepository.findAll().size();

        // Create the AccountType with an existing ID
        accountType.setId(1L);
        AccountTypeDTO accountTypeDTO = accountTypeMapper.toDto(accountType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAccountTypeMockMvc.perform(post("/api/account-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accountTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AccountType in the database
        List<AccountType> accountTypeList = accountTypeRepository.findAll();
        assertThat(accountTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = accountTypeRepository.findAll().size();
        // set the field null
        accountType.setCode(null);

        // Create the AccountType, which fails.
        AccountTypeDTO accountTypeDTO = accountTypeMapper.toDto(accountType);

        restAccountTypeMockMvc.perform(post("/api/account-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accountTypeDTO)))
            .andExpect(status().isBadRequest());

        List<AccountType> accountTypeList = accountTypeRepository.findAll();
        assertThat(accountTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLabelIsRequired() throws Exception {
        int databaseSizeBeforeTest = accountTypeRepository.findAll().size();
        // set the field null
        accountType.setLabel(null);

        // Create the AccountType, which fails.
        AccountTypeDTO accountTypeDTO = accountTypeMapper.toDto(accountType);

        restAccountTypeMockMvc.perform(post("/api/account-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accountTypeDTO)))
            .andExpect(status().isBadRequest());

        List<AccountType> accountTypeList = accountTypeRepository.findAll();
        assertThat(accountTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = accountTypeRepository.findAll().size();
        // set the field null
        accountType.setActive(null);

        // Create the AccountType, which fails.
        AccountTypeDTO accountTypeDTO = accountTypeMapper.toDto(accountType);

        restAccountTypeMockMvc.perform(post("/api/account-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accountTypeDTO)))
            .andExpect(status().isBadRequest());

        List<AccountType> accountTypeList = accountTypeRepository.findAll();
        assertThat(accountTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAccountTypes() throws Exception {
        // Initialize the database
        accountTypeRepository.saveAndFlush(accountType);

        // Get all the accountTypeList
        restAccountTypeMockMvc.perform(get("/api/account-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(accountType.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getAccountType() throws Exception {
        // Initialize the database
        accountTypeRepository.saveAndFlush(accountType);

        // Get the accountType
        restAccountTypeMockMvc.perform(get("/api/account-types/{id}", accountType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(accountType.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAccountType() throws Exception {
        // Get the accountType
        restAccountTypeMockMvc.perform(get("/api/account-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAccountType() throws Exception {
        // Initialize the database
        accountTypeRepository.saveAndFlush(accountType);

        int databaseSizeBeforeUpdate = accountTypeRepository.findAll().size();

        // Update the accountType
        AccountType updatedAccountType = accountTypeRepository.findById(accountType.getId()).get();
        // Disconnect from session so that the updates on updatedAccountType are not directly saved in db
        em.detach(updatedAccountType);
        updatedAccountType
            .code(UPDATED_CODE)
            .label(UPDATED_LABEL)
            .active(UPDATED_ACTIVE);
        AccountTypeDTO accountTypeDTO = accountTypeMapper.toDto(updatedAccountType);

        restAccountTypeMockMvc.perform(put("/api/account-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accountTypeDTO)))
            .andExpect(status().isOk());

        // Validate the AccountType in the database
        List<AccountType> accountTypeList = accountTypeRepository.findAll();
        assertThat(accountTypeList).hasSize(databaseSizeBeforeUpdate);
        AccountType testAccountType = accountTypeList.get(accountTypeList.size() - 1);
        assertThat(testAccountType.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testAccountType.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testAccountType.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingAccountType() throws Exception {
        int databaseSizeBeforeUpdate = accountTypeRepository.findAll().size();

        // Create the AccountType
        AccountTypeDTO accountTypeDTO = accountTypeMapper.toDto(accountType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAccountTypeMockMvc.perform(put("/api/account-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accountTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AccountType in the database
        List<AccountType> accountTypeList = accountTypeRepository.findAll();
        assertThat(accountTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAccountType() throws Exception {
        // Initialize the database
        accountTypeRepository.saveAndFlush(accountType);

        int databaseSizeBeforeDelete = accountTypeRepository.findAll().size();

        // Delete the accountType
        restAccountTypeMockMvc.perform(delete("/api/account-types/{id}", accountType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AccountType> accountTypeList = accountTypeRepository.findAll();
        assertThat(accountTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AccountType.class);
        AccountType accountType1 = new AccountType();
        accountType1.setId(1L);
        AccountType accountType2 = new AccountType();
        accountType2.setId(accountType1.getId());
        assertThat(accountType1).isEqualTo(accountType2);
        accountType2.setId(2L);
        assertThat(accountType1).isNotEqualTo(accountType2);
        accountType1.setId(null);
        assertThat(accountType1).isNotEqualTo(accountType2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AccountTypeDTO.class);
        AccountTypeDTO accountTypeDTO1 = new AccountTypeDTO();
        accountTypeDTO1.setId(1L);
        AccountTypeDTO accountTypeDTO2 = new AccountTypeDTO();
        assertThat(accountTypeDTO1).isNotEqualTo(accountTypeDTO2);
        accountTypeDTO2.setId(accountTypeDTO1.getId());
        assertThat(accountTypeDTO1).isEqualTo(accountTypeDTO2);
        accountTypeDTO2.setId(2L);
        assertThat(accountTypeDTO1).isNotEqualTo(accountTypeDTO2);
        accountTypeDTO1.setId(null);
        assertThat(accountTypeDTO1).isNotEqualTo(accountTypeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(accountTypeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(accountTypeMapper.fromId(null)).isNull();
    }
}
