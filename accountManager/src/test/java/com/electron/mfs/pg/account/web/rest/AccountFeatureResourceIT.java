package com.electron.mfs.pg.account.web.rest;

import com.electron.mfs.pg.account.AccountManagerApp;
import com.electron.mfs.pg.account.config.SecurityBeanOverrideConfiguration;
import com.electron.mfs.pg.account.domain.AccountFeature;
import com.electron.mfs.pg.account.repository.AccountFeatureRepository;
import com.electron.mfs.pg.account.service.AccountFeatureService;
import com.electron.mfs.pg.account.service.dto.AccountFeatureDTO;
import com.electron.mfs.pg.account.service.mapper.AccountFeatureMapper;
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
 * Integration tests for the {@Link AccountFeatureResource} REST controller.
 */
@EmbeddedKafka
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, AccountManagerApp.class})
public class AccountFeatureResourceIT {

    private static final Instant DEFAULT_ACTIVATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ACTIVATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_FEATURE_CODE = "AAAAA";
    private static final String UPDATED_FEATURE_CODE = "BBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private AccountFeatureRepository accountFeatureRepository;

    @Autowired
    private AccountFeatureMapper accountFeatureMapper;

    @Autowired
    private AccountFeatureService accountFeatureService;

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

    private MockMvc restAccountFeatureMockMvc;

    private AccountFeature accountFeature;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AccountFeatureResource accountFeatureResource = new AccountFeatureResource(accountFeatureService);
        this.restAccountFeatureMockMvc = MockMvcBuilders.standaloneSetup(accountFeatureResource)
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
    public static AccountFeature createEntity(EntityManager em) {
        AccountFeature accountFeature = new AccountFeature()
            .activationDate(DEFAULT_ACTIVATION_DATE)
            .featureCode(DEFAULT_FEATURE_CODE)
            .active(DEFAULT_ACTIVE);
        return accountFeature;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AccountFeature createUpdatedEntity(EntityManager em) {
        AccountFeature accountFeature = new AccountFeature()
            .activationDate(UPDATED_ACTIVATION_DATE)
            .featureCode(UPDATED_FEATURE_CODE)
            .active(UPDATED_ACTIVE);
        return accountFeature;
    }

    @BeforeEach
    public void initTest() {
        accountFeature = createEntity(em);
    }

    @Test
    @Transactional
    public void createAccountFeature() throws Exception {
        int databaseSizeBeforeCreate = accountFeatureRepository.findAll().size();

        // Create the AccountFeature
        AccountFeatureDTO accountFeatureDTO = accountFeatureMapper.toDto(accountFeature);
        restAccountFeatureMockMvc.perform(post("/api/account-features")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accountFeatureDTO)))
            .andExpect(status().isCreated());

        // Validate the AccountFeature in the database
        List<AccountFeature> accountFeatureList = accountFeatureRepository.findAll();
        assertThat(accountFeatureList).hasSize(databaseSizeBeforeCreate + 1);
        AccountFeature testAccountFeature = accountFeatureList.get(accountFeatureList.size() - 1);
        assertThat(testAccountFeature.getActivationDate()).isEqualTo(DEFAULT_ACTIVATION_DATE);
        assertThat(testAccountFeature.getFeatureCode()).isEqualTo(DEFAULT_FEATURE_CODE);
        assertThat(testAccountFeature.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createAccountFeatureWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = accountFeatureRepository.findAll().size();

        // Create the AccountFeature with an existing ID
        accountFeature.setId(1L);
        AccountFeatureDTO accountFeatureDTO = accountFeatureMapper.toDto(accountFeature);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAccountFeatureMockMvc.perform(post("/api/account-features")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accountFeatureDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AccountFeature in the database
        List<AccountFeature> accountFeatureList = accountFeatureRepository.findAll();
        assertThat(accountFeatureList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkActivationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = accountFeatureRepository.findAll().size();
        // set the field null
        accountFeature.setActivationDate(null);

        // Create the AccountFeature, which fails.
        AccountFeatureDTO accountFeatureDTO = accountFeatureMapper.toDto(accountFeature);

        restAccountFeatureMockMvc.perform(post("/api/account-features")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accountFeatureDTO)))
            .andExpect(status().isBadRequest());

        List<AccountFeature> accountFeatureList = accountFeatureRepository.findAll();
        assertThat(accountFeatureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFeatureCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = accountFeatureRepository.findAll().size();
        // set the field null
        accountFeature.setFeatureCode(null);

        // Create the AccountFeature, which fails.
        AccountFeatureDTO accountFeatureDTO = accountFeatureMapper.toDto(accountFeature);

        restAccountFeatureMockMvc.perform(post("/api/account-features")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accountFeatureDTO)))
            .andExpect(status().isBadRequest());

        List<AccountFeature> accountFeatureList = accountFeatureRepository.findAll();
        assertThat(accountFeatureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = accountFeatureRepository.findAll().size();
        // set the field null
        accountFeature.setActive(null);

        // Create the AccountFeature, which fails.
        AccountFeatureDTO accountFeatureDTO = accountFeatureMapper.toDto(accountFeature);

        restAccountFeatureMockMvc.perform(post("/api/account-features")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accountFeatureDTO)))
            .andExpect(status().isBadRequest());

        List<AccountFeature> accountFeatureList = accountFeatureRepository.findAll();
        assertThat(accountFeatureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAccountFeatures() throws Exception {
        // Initialize the database
        accountFeatureRepository.saveAndFlush(accountFeature);

        // Get all the accountFeatureList
        restAccountFeatureMockMvc.perform(get("/api/account-features?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(accountFeature.getId().intValue())))
            .andExpect(jsonPath("$.[*].activationDate").value(hasItem(DEFAULT_ACTIVATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].featureCode").value(hasItem(DEFAULT_FEATURE_CODE.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getAccountFeature() throws Exception {
        // Initialize the database
        accountFeatureRepository.saveAndFlush(accountFeature);

        // Get the accountFeature
        restAccountFeatureMockMvc.perform(get("/api/account-features/{id}", accountFeature.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(accountFeature.getId().intValue()))
            .andExpect(jsonPath("$.activationDate").value(DEFAULT_ACTIVATION_DATE.toString()))
            .andExpect(jsonPath("$.featureCode").value(DEFAULT_FEATURE_CODE.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAccountFeature() throws Exception {
        // Get the accountFeature
        restAccountFeatureMockMvc.perform(get("/api/account-features/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAccountFeature() throws Exception {
        // Initialize the database
        accountFeatureRepository.saveAndFlush(accountFeature);

        int databaseSizeBeforeUpdate = accountFeatureRepository.findAll().size();

        // Update the accountFeature
        AccountFeature updatedAccountFeature = accountFeatureRepository.findById(accountFeature.getId()).get();
        // Disconnect from session so that the updates on updatedAccountFeature are not directly saved in db
        em.detach(updatedAccountFeature);
        updatedAccountFeature
            .activationDate(UPDATED_ACTIVATION_DATE)
            .featureCode(UPDATED_FEATURE_CODE)
            .active(UPDATED_ACTIVE);
        AccountFeatureDTO accountFeatureDTO = accountFeatureMapper.toDto(updatedAccountFeature);

        restAccountFeatureMockMvc.perform(put("/api/account-features")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accountFeatureDTO)))
            .andExpect(status().isOk());

        // Validate the AccountFeature in the database
        List<AccountFeature> accountFeatureList = accountFeatureRepository.findAll();
        assertThat(accountFeatureList).hasSize(databaseSizeBeforeUpdate);
        AccountFeature testAccountFeature = accountFeatureList.get(accountFeatureList.size() - 1);
        assertThat(testAccountFeature.getActivationDate()).isEqualTo(UPDATED_ACTIVATION_DATE);
        assertThat(testAccountFeature.getFeatureCode()).isEqualTo(UPDATED_FEATURE_CODE);
        assertThat(testAccountFeature.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingAccountFeature() throws Exception {
        int databaseSizeBeforeUpdate = accountFeatureRepository.findAll().size();

        // Create the AccountFeature
        AccountFeatureDTO accountFeatureDTO = accountFeatureMapper.toDto(accountFeature);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAccountFeatureMockMvc.perform(put("/api/account-features")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accountFeatureDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AccountFeature in the database
        List<AccountFeature> accountFeatureList = accountFeatureRepository.findAll();
        assertThat(accountFeatureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAccountFeature() throws Exception {
        // Initialize the database
        accountFeatureRepository.saveAndFlush(accountFeature);

        int databaseSizeBeforeDelete = accountFeatureRepository.findAll().size();

        // Delete the accountFeature
        restAccountFeatureMockMvc.perform(delete("/api/account-features/{id}", accountFeature.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AccountFeature> accountFeatureList = accountFeatureRepository.findAll();
        assertThat(accountFeatureList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AccountFeature.class);
        AccountFeature accountFeature1 = new AccountFeature();
        accountFeature1.setId(1L);
        AccountFeature accountFeature2 = new AccountFeature();
        accountFeature2.setId(accountFeature1.getId());
        assertThat(accountFeature1).isEqualTo(accountFeature2);
        accountFeature2.setId(2L);
        assertThat(accountFeature1).isNotEqualTo(accountFeature2);
        accountFeature1.setId(null);
        assertThat(accountFeature1).isNotEqualTo(accountFeature2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AccountFeatureDTO.class);
        AccountFeatureDTO accountFeatureDTO1 = new AccountFeatureDTO();
        accountFeatureDTO1.setId(1L);
        AccountFeatureDTO accountFeatureDTO2 = new AccountFeatureDTO();
        assertThat(accountFeatureDTO1).isNotEqualTo(accountFeatureDTO2);
        accountFeatureDTO2.setId(accountFeatureDTO1.getId());
        assertThat(accountFeatureDTO1).isEqualTo(accountFeatureDTO2);
        accountFeatureDTO2.setId(2L);
        assertThat(accountFeatureDTO1).isNotEqualTo(accountFeatureDTO2);
        accountFeatureDTO1.setId(null);
        assertThat(accountFeatureDTO1).isNotEqualTo(accountFeatureDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(accountFeatureMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(accountFeatureMapper.fromId(null)).isNull();
    }
}
