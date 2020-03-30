package com.electron.mfs.pg.customer.web.rest;

import com.electron.mfs.pg.customer.CustomerManagerApp;
import com.electron.mfs.pg.customer.config.SecurityBeanOverrideConfiguration;
import com.electron.mfs.pg.customer.domain.Beneficiary;
import com.electron.mfs.pg.customer.repository.BeneficiaryRepository;
import com.electron.mfs.pg.customer.service.BeneficiaryService;
import com.electron.mfs.pg.customer.service.dto.BeneficiaryDTO;
import com.electron.mfs.pg.customer.service.mapper.BeneficiaryMapper;
import com.electron.mfs.pg.customer.web.rest.errors.ExceptionTranslator;

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

import static com.electron.mfs.pg.customer.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link BeneficiaryResource} REST controller.
 */
@EmbeddedKafka
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, CustomerManagerApp.class})
public class BeneficiaryResourceIT {

    private static final String DEFAULT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_NUMBER = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_COMPANY = false;
    private static final Boolean UPDATED_IS_COMPANY = true;

    private static final String DEFAULT_FIRSTNAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRSTNAME = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ALIAS_ACCOUNT = "AAAAAAAAAA";
    private static final String UPDATED_ALIAS_ACCOUNT = "BBBBBBBBBB";

    private static final String DEFAULT_BACC_BANK_CODE = "AAAAAAAAAA";
    private static final String UPDATED_BACC_BANK_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_BACC_BRANCH_CODE = "AAAAAAAAAA";
    private static final String UPDATED_BACC_BRANCH_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_BACC_ACCOUNT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_BACC_ACCOUNT_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_BACC_RIB_KEY = "AAAAA";
    private static final String UPDATED_BACC_RIB_KEY = "BBBBB";

    private static final String DEFAULT_CARD_CVV_2 = "AAAAA";
    private static final String UPDATED_CARD_CVV_2 = "BBBBB";

    private static final String DEFAULT_CARD_PAN = "AAAAAAAAAA";
    private static final String UPDATED_CARD_PAN = "BBBBBBBBBB";

    private static final String DEFAULT_CARD_VALIDITY_DATE = "AAAAAAAA";
    private static final String UPDATED_CARD_VALIDITY_DATE = "BBBBBBBB";

    private static final Boolean DEFAULT_IS_DM_ACCOUNT = false;
    private static final Boolean UPDATED_IS_DM_ACCOUNT = true;

    private static final String DEFAULT_MOMO_ACCOUNT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_MOMO_ACCOUNT_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_BENEFICIARY_RELATIONSHIP_CODE = "AAAAA";
    private static final String UPDATED_BENEFICIARY_RELATIONSHIP_CODE = "BBBBB";

    private static final String DEFAULT_BENEFICIARY_TYPE_CODE = "AAAAA";
    private static final String UPDATED_BENEFICIARY_TYPE_CODE = "BBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private BeneficiaryRepository beneficiaryRepository;

    @Autowired
    private BeneficiaryMapper beneficiaryMapper;

    @Autowired
    private BeneficiaryService beneficiaryService;

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

    private MockMvc restBeneficiaryMockMvc;

    private Beneficiary beneficiary;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BeneficiaryResource beneficiaryResource = new BeneficiaryResource(beneficiaryService);
        this.restBeneficiaryMockMvc = MockMvcBuilders.standaloneSetup(beneficiaryResource)
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
    public static Beneficiary createEntity(EntityManager em) {
        Beneficiary beneficiary = new Beneficiary()
            .number(DEFAULT_NUMBER)
            .isCompany(DEFAULT_IS_COMPANY)
            .firstname(DEFAULT_FIRSTNAME)
            .name(DEFAULT_NAME)
            .aliasAccount(DEFAULT_ALIAS_ACCOUNT)
            .baccBankCode(DEFAULT_BACC_BANK_CODE)
            .baccBranchCode(DEFAULT_BACC_BRANCH_CODE)
            .baccAccountNumber(DEFAULT_BACC_ACCOUNT_NUMBER)
            .baccRibKey(DEFAULT_BACC_RIB_KEY)
            .cardCvv2(DEFAULT_CARD_CVV_2)
            .cardPan(DEFAULT_CARD_PAN)
            .cardValidityDate(DEFAULT_CARD_VALIDITY_DATE)
            .isDmAccount(DEFAULT_IS_DM_ACCOUNT)
            .momoAccountNumber(DEFAULT_MOMO_ACCOUNT_NUMBER)
            .beneficiaryRelationshipCode(DEFAULT_BENEFICIARY_RELATIONSHIP_CODE)
            .beneficiaryTypeCode(DEFAULT_BENEFICIARY_TYPE_CODE)
            .active(DEFAULT_ACTIVE);
        return beneficiary;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Beneficiary createUpdatedEntity(EntityManager em) {
        Beneficiary beneficiary = new Beneficiary()
            .number(UPDATED_NUMBER)
            .isCompany(UPDATED_IS_COMPANY)
            .firstname(UPDATED_FIRSTNAME)
            .name(UPDATED_NAME)
            .aliasAccount(UPDATED_ALIAS_ACCOUNT)
            .baccBankCode(UPDATED_BACC_BANK_CODE)
            .baccBranchCode(UPDATED_BACC_BRANCH_CODE)
            .baccAccountNumber(UPDATED_BACC_ACCOUNT_NUMBER)
            .baccRibKey(UPDATED_BACC_RIB_KEY)
            .cardCvv2(UPDATED_CARD_CVV_2)
            .cardPan(UPDATED_CARD_PAN)
            .cardValidityDate(UPDATED_CARD_VALIDITY_DATE)
            .isDmAccount(UPDATED_IS_DM_ACCOUNT)
            .momoAccountNumber(UPDATED_MOMO_ACCOUNT_NUMBER)
            .beneficiaryRelationshipCode(UPDATED_BENEFICIARY_RELATIONSHIP_CODE)
            .beneficiaryTypeCode(UPDATED_BENEFICIARY_TYPE_CODE)
            .active(UPDATED_ACTIVE);
        return beneficiary;
    }

    @BeforeEach
    public void initTest() {
        beneficiary = createEntity(em);
    }

    @Test
    @Transactional
    public void createBeneficiary() throws Exception {
        int databaseSizeBeforeCreate = beneficiaryRepository.findAll().size();

        // Create the Beneficiary
        BeneficiaryDTO beneficiaryDTO = beneficiaryMapper.toDto(beneficiary);
        restBeneficiaryMockMvc.perform(post("/api/beneficiaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(beneficiaryDTO)))
            .andExpect(status().isCreated());

        // Validate the Beneficiary in the database
        List<Beneficiary> beneficiaryList = beneficiaryRepository.findAll();
        assertThat(beneficiaryList).hasSize(databaseSizeBeforeCreate + 1);
        Beneficiary testBeneficiary = beneficiaryList.get(beneficiaryList.size() - 1);
        assertThat(testBeneficiary.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testBeneficiary.isIsCompany()).isEqualTo(DEFAULT_IS_COMPANY);
        assertThat(testBeneficiary.getFirstname()).isEqualTo(DEFAULT_FIRSTNAME);
        assertThat(testBeneficiary.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBeneficiary.getAliasAccount()).isEqualTo(DEFAULT_ALIAS_ACCOUNT);
        assertThat(testBeneficiary.getBaccBankCode()).isEqualTo(DEFAULT_BACC_BANK_CODE);
        assertThat(testBeneficiary.getBaccBranchCode()).isEqualTo(DEFAULT_BACC_BRANCH_CODE);
        assertThat(testBeneficiary.getBaccAccountNumber()).isEqualTo(DEFAULT_BACC_ACCOUNT_NUMBER);
        assertThat(testBeneficiary.getBaccRibKey()).isEqualTo(DEFAULT_BACC_RIB_KEY);
        assertThat(testBeneficiary.getCardCvv2()).isEqualTo(DEFAULT_CARD_CVV_2);
        assertThat(testBeneficiary.getCardPan()).isEqualTo(DEFAULT_CARD_PAN);
        assertThat(testBeneficiary.getCardValidityDate()).isEqualTo(DEFAULT_CARD_VALIDITY_DATE);
        assertThat(testBeneficiary.isIsDmAccount()).isEqualTo(DEFAULT_IS_DM_ACCOUNT);
        assertThat(testBeneficiary.getMomoAccountNumber()).isEqualTo(DEFAULT_MOMO_ACCOUNT_NUMBER);
        assertThat(testBeneficiary.getBeneficiaryRelationshipCode()).isEqualTo(DEFAULT_BENEFICIARY_RELATIONSHIP_CODE);
        assertThat(testBeneficiary.getBeneficiaryTypeCode()).isEqualTo(DEFAULT_BENEFICIARY_TYPE_CODE);
        assertThat(testBeneficiary.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createBeneficiaryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = beneficiaryRepository.findAll().size();

        // Create the Beneficiary with an existing ID
        beneficiary.setId(1L);
        BeneficiaryDTO beneficiaryDTO = beneficiaryMapper.toDto(beneficiary);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBeneficiaryMockMvc.perform(post("/api/beneficiaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(beneficiaryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Beneficiary in the database
        List<Beneficiary> beneficiaryList = beneficiaryRepository.findAll();
        assertThat(beneficiaryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = beneficiaryRepository.findAll().size();
        // set the field null
        beneficiary.setNumber(null);

        // Create the Beneficiary, which fails.
        BeneficiaryDTO beneficiaryDTO = beneficiaryMapper.toDto(beneficiary);

        restBeneficiaryMockMvc.perform(post("/api/beneficiaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(beneficiaryDTO)))
            .andExpect(status().isBadRequest());

        List<Beneficiary> beneficiaryList = beneficiaryRepository.findAll();
        assertThat(beneficiaryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsCompanyIsRequired() throws Exception {
        int databaseSizeBeforeTest = beneficiaryRepository.findAll().size();
        // set the field null
        beneficiary.setIsCompany(null);

        // Create the Beneficiary, which fails.
        BeneficiaryDTO beneficiaryDTO = beneficiaryMapper.toDto(beneficiary);

        restBeneficiaryMockMvc.perform(post("/api/beneficiaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(beneficiaryDTO)))
            .andExpect(status().isBadRequest());

        List<Beneficiary> beneficiaryList = beneficiaryRepository.findAll();
        assertThat(beneficiaryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAliasAccountIsRequired() throws Exception {
        int databaseSizeBeforeTest = beneficiaryRepository.findAll().size();
        // set the field null
        beneficiary.setAliasAccount(null);

        // Create the Beneficiary, which fails.
        BeneficiaryDTO beneficiaryDTO = beneficiaryMapper.toDto(beneficiary);

        restBeneficiaryMockMvc.perform(post("/api/beneficiaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(beneficiaryDTO)))
            .andExpect(status().isBadRequest());

        List<Beneficiary> beneficiaryList = beneficiaryRepository.findAll();
        assertThat(beneficiaryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsDmAccountIsRequired() throws Exception {
        int databaseSizeBeforeTest = beneficiaryRepository.findAll().size();
        // set the field null
        beneficiary.setIsDmAccount(null);

        // Create the Beneficiary, which fails.
        BeneficiaryDTO beneficiaryDTO = beneficiaryMapper.toDto(beneficiary);

        restBeneficiaryMockMvc.perform(post("/api/beneficiaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(beneficiaryDTO)))
            .andExpect(status().isBadRequest());

        List<Beneficiary> beneficiaryList = beneficiaryRepository.findAll();
        assertThat(beneficiaryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBeneficiaryRelationshipCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = beneficiaryRepository.findAll().size();
        // set the field null
        beneficiary.setBeneficiaryRelationshipCode(null);

        // Create the Beneficiary, which fails.
        BeneficiaryDTO beneficiaryDTO = beneficiaryMapper.toDto(beneficiary);

        restBeneficiaryMockMvc.perform(post("/api/beneficiaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(beneficiaryDTO)))
            .andExpect(status().isBadRequest());

        List<Beneficiary> beneficiaryList = beneficiaryRepository.findAll();
        assertThat(beneficiaryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBeneficiaryTypeCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = beneficiaryRepository.findAll().size();
        // set the field null
        beneficiary.setBeneficiaryTypeCode(null);

        // Create the Beneficiary, which fails.
        BeneficiaryDTO beneficiaryDTO = beneficiaryMapper.toDto(beneficiary);

        restBeneficiaryMockMvc.perform(post("/api/beneficiaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(beneficiaryDTO)))
            .andExpect(status().isBadRequest());

        List<Beneficiary> beneficiaryList = beneficiaryRepository.findAll();
        assertThat(beneficiaryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = beneficiaryRepository.findAll().size();
        // set the field null
        beneficiary.setActive(null);

        // Create the Beneficiary, which fails.
        BeneficiaryDTO beneficiaryDTO = beneficiaryMapper.toDto(beneficiary);

        restBeneficiaryMockMvc.perform(post("/api/beneficiaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(beneficiaryDTO)))
            .andExpect(status().isBadRequest());

        List<Beneficiary> beneficiaryList = beneficiaryRepository.findAll();
        assertThat(beneficiaryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBeneficiaries() throws Exception {
        // Initialize the database
        beneficiaryRepository.saveAndFlush(beneficiary);

        // Get all the beneficiaryList
        restBeneficiaryMockMvc.perform(get("/api/beneficiaries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(beneficiary.getId().intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].isCompany").value(hasItem(DEFAULT_IS_COMPANY.booleanValue())))
            .andExpect(jsonPath("$.[*].firstname").value(hasItem(DEFAULT_FIRSTNAME.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].aliasAccount").value(hasItem(DEFAULT_ALIAS_ACCOUNT.toString())))
            .andExpect(jsonPath("$.[*].baccBankCode").value(hasItem(DEFAULT_BACC_BANK_CODE.toString())))
            .andExpect(jsonPath("$.[*].baccBranchCode").value(hasItem(DEFAULT_BACC_BRANCH_CODE.toString())))
            .andExpect(jsonPath("$.[*].baccAccountNumber").value(hasItem(DEFAULT_BACC_ACCOUNT_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].baccRibKey").value(hasItem(DEFAULT_BACC_RIB_KEY.toString())))
            .andExpect(jsonPath("$.[*].cardCvv2").value(hasItem(DEFAULT_CARD_CVV_2.toString())))
            .andExpect(jsonPath("$.[*].cardPan").value(hasItem(DEFAULT_CARD_PAN.toString())))
            .andExpect(jsonPath("$.[*].cardValidityDate").value(hasItem(DEFAULT_CARD_VALIDITY_DATE.toString())))
            .andExpect(jsonPath("$.[*].isDmAccount").value(hasItem(DEFAULT_IS_DM_ACCOUNT.booleanValue())))
            .andExpect(jsonPath("$.[*].momoAccountNumber").value(hasItem(DEFAULT_MOMO_ACCOUNT_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].beneficiaryRelationshipCode").value(hasItem(DEFAULT_BENEFICIARY_RELATIONSHIP_CODE.toString())))
            .andExpect(jsonPath("$.[*].beneficiaryTypeCode").value(hasItem(DEFAULT_BENEFICIARY_TYPE_CODE.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getBeneficiary() throws Exception {
        // Initialize the database
        beneficiaryRepository.saveAndFlush(beneficiary);

        // Get the beneficiary
        restBeneficiaryMockMvc.perform(get("/api/beneficiaries/{id}", beneficiary.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(beneficiary.getId().intValue()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER.toString()))
            .andExpect(jsonPath("$.isCompany").value(DEFAULT_IS_COMPANY.booleanValue()))
            .andExpect(jsonPath("$.firstname").value(DEFAULT_FIRSTNAME.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.aliasAccount").value(DEFAULT_ALIAS_ACCOUNT.toString()))
            .andExpect(jsonPath("$.baccBankCode").value(DEFAULT_BACC_BANK_CODE.toString()))
            .andExpect(jsonPath("$.baccBranchCode").value(DEFAULT_BACC_BRANCH_CODE.toString()))
            .andExpect(jsonPath("$.baccAccountNumber").value(DEFAULT_BACC_ACCOUNT_NUMBER.toString()))
            .andExpect(jsonPath("$.baccRibKey").value(DEFAULT_BACC_RIB_KEY.toString()))
            .andExpect(jsonPath("$.cardCvv2").value(DEFAULT_CARD_CVV_2.toString()))
            .andExpect(jsonPath("$.cardPan").value(DEFAULT_CARD_PAN.toString()))
            .andExpect(jsonPath("$.cardValidityDate").value(DEFAULT_CARD_VALIDITY_DATE.toString()))
            .andExpect(jsonPath("$.isDmAccount").value(DEFAULT_IS_DM_ACCOUNT.booleanValue()))
            .andExpect(jsonPath("$.momoAccountNumber").value(DEFAULT_MOMO_ACCOUNT_NUMBER.toString()))
            .andExpect(jsonPath("$.beneficiaryRelationshipCode").value(DEFAULT_BENEFICIARY_RELATIONSHIP_CODE.toString()))
            .andExpect(jsonPath("$.beneficiaryTypeCode").value(DEFAULT_BENEFICIARY_TYPE_CODE.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingBeneficiary() throws Exception {
        // Get the beneficiary
        restBeneficiaryMockMvc.perform(get("/api/beneficiaries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBeneficiary() throws Exception {
        // Initialize the database
        beneficiaryRepository.saveAndFlush(beneficiary);

        int databaseSizeBeforeUpdate = beneficiaryRepository.findAll().size();

        // Update the beneficiary
        Beneficiary updatedBeneficiary = beneficiaryRepository.findById(beneficiary.getId()).get();
        // Disconnect from session so that the updates on updatedBeneficiary are not directly saved in db
        em.detach(updatedBeneficiary);
        updatedBeneficiary
            .number(UPDATED_NUMBER)
            .isCompany(UPDATED_IS_COMPANY)
            .firstname(UPDATED_FIRSTNAME)
            .name(UPDATED_NAME)
            .aliasAccount(UPDATED_ALIAS_ACCOUNT)
            .baccBankCode(UPDATED_BACC_BANK_CODE)
            .baccBranchCode(UPDATED_BACC_BRANCH_CODE)
            .baccAccountNumber(UPDATED_BACC_ACCOUNT_NUMBER)
            .baccRibKey(UPDATED_BACC_RIB_KEY)
            .cardCvv2(UPDATED_CARD_CVV_2)
            .cardPan(UPDATED_CARD_PAN)
            .cardValidityDate(UPDATED_CARD_VALIDITY_DATE)
            .isDmAccount(UPDATED_IS_DM_ACCOUNT)
            .momoAccountNumber(UPDATED_MOMO_ACCOUNT_NUMBER)
            .beneficiaryRelationshipCode(UPDATED_BENEFICIARY_RELATIONSHIP_CODE)
            .beneficiaryTypeCode(UPDATED_BENEFICIARY_TYPE_CODE)
            .active(UPDATED_ACTIVE);
        BeneficiaryDTO beneficiaryDTO = beneficiaryMapper.toDto(updatedBeneficiary);

        restBeneficiaryMockMvc.perform(put("/api/beneficiaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(beneficiaryDTO)))
            .andExpect(status().isOk());

        // Validate the Beneficiary in the database
        List<Beneficiary> beneficiaryList = beneficiaryRepository.findAll();
        assertThat(beneficiaryList).hasSize(databaseSizeBeforeUpdate);
        Beneficiary testBeneficiary = beneficiaryList.get(beneficiaryList.size() - 1);
        assertThat(testBeneficiary.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testBeneficiary.isIsCompany()).isEqualTo(UPDATED_IS_COMPANY);
        assertThat(testBeneficiary.getFirstname()).isEqualTo(UPDATED_FIRSTNAME);
        assertThat(testBeneficiary.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBeneficiary.getAliasAccount()).isEqualTo(UPDATED_ALIAS_ACCOUNT);
        assertThat(testBeneficiary.getBaccBankCode()).isEqualTo(UPDATED_BACC_BANK_CODE);
        assertThat(testBeneficiary.getBaccBranchCode()).isEqualTo(UPDATED_BACC_BRANCH_CODE);
        assertThat(testBeneficiary.getBaccAccountNumber()).isEqualTo(UPDATED_BACC_ACCOUNT_NUMBER);
        assertThat(testBeneficiary.getBaccRibKey()).isEqualTo(UPDATED_BACC_RIB_KEY);
        assertThat(testBeneficiary.getCardCvv2()).isEqualTo(UPDATED_CARD_CVV_2);
        assertThat(testBeneficiary.getCardPan()).isEqualTo(UPDATED_CARD_PAN);
        assertThat(testBeneficiary.getCardValidityDate()).isEqualTo(UPDATED_CARD_VALIDITY_DATE);
        assertThat(testBeneficiary.isIsDmAccount()).isEqualTo(UPDATED_IS_DM_ACCOUNT);
        assertThat(testBeneficiary.getMomoAccountNumber()).isEqualTo(UPDATED_MOMO_ACCOUNT_NUMBER);
        assertThat(testBeneficiary.getBeneficiaryRelationshipCode()).isEqualTo(UPDATED_BENEFICIARY_RELATIONSHIP_CODE);
        assertThat(testBeneficiary.getBeneficiaryTypeCode()).isEqualTo(UPDATED_BENEFICIARY_TYPE_CODE);
        assertThat(testBeneficiary.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingBeneficiary() throws Exception {
        int databaseSizeBeforeUpdate = beneficiaryRepository.findAll().size();

        // Create the Beneficiary
        BeneficiaryDTO beneficiaryDTO = beneficiaryMapper.toDto(beneficiary);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBeneficiaryMockMvc.perform(put("/api/beneficiaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(beneficiaryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Beneficiary in the database
        List<Beneficiary> beneficiaryList = beneficiaryRepository.findAll();
        assertThat(beneficiaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBeneficiary() throws Exception {
        // Initialize the database
        beneficiaryRepository.saveAndFlush(beneficiary);

        int databaseSizeBeforeDelete = beneficiaryRepository.findAll().size();

        // Delete the beneficiary
        restBeneficiaryMockMvc.perform(delete("/api/beneficiaries/{id}", beneficiary.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Beneficiary> beneficiaryList = beneficiaryRepository.findAll();
        assertThat(beneficiaryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Beneficiary.class);
        Beneficiary beneficiary1 = new Beneficiary();
        beneficiary1.setId(1L);
        Beneficiary beneficiary2 = new Beneficiary();
        beneficiary2.setId(beneficiary1.getId());
        assertThat(beneficiary1).isEqualTo(beneficiary2);
        beneficiary2.setId(2L);
        assertThat(beneficiary1).isNotEqualTo(beneficiary2);
        beneficiary1.setId(null);
        assertThat(beneficiary1).isNotEqualTo(beneficiary2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BeneficiaryDTO.class);
        BeneficiaryDTO beneficiaryDTO1 = new BeneficiaryDTO();
        beneficiaryDTO1.setId(1L);
        BeneficiaryDTO beneficiaryDTO2 = new BeneficiaryDTO();
        assertThat(beneficiaryDTO1).isNotEqualTo(beneficiaryDTO2);
        beneficiaryDTO2.setId(beneficiaryDTO1.getId());
        assertThat(beneficiaryDTO1).isEqualTo(beneficiaryDTO2);
        beneficiaryDTO2.setId(2L);
        assertThat(beneficiaryDTO1).isNotEqualTo(beneficiaryDTO2);
        beneficiaryDTO1.setId(null);
        assertThat(beneficiaryDTO1).isNotEqualTo(beneficiaryDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(beneficiaryMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(beneficiaryMapper.fromId(null)).isNull();
    }
}
