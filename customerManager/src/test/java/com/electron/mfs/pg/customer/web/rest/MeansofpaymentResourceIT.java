package com.electron.mfs.pg.customer.web.rest;

import com.electron.mfs.pg.customer.CustomerManagerApp;
import com.electron.mfs.pg.customer.config.SecurityBeanOverrideConfiguration;
import com.electron.mfs.pg.customer.domain.Meansofpayment;
import com.electron.mfs.pg.customer.repository.MeansofpaymentRepository;
import com.electron.mfs.pg.customer.service.MeansofpaymentService;
import com.electron.mfs.pg.customer.service.dto.MeansofpaymentDTO;
import com.electron.mfs.pg.customer.service.mapper.MeansofpaymentMapper;
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
 * Integration tests for the {@Link MeansofpaymentResource} REST controller.
 */
@EmbeddedKafka
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, CustomerManagerApp.class})
public class MeansofpaymentResourceIT {

    private static final String DEFAULT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_NUMBER = "BBBBBBBBBB";

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

    private static final String DEFAULT_MOMO_ACCOUNT = "AAAAAAAAAA";
    private static final String UPDATED_MOMO_ACCOUNT = "BBBBBBBBBB";

    private static final String DEFAULT_MEANSOFPAYMENT_TYPE_CODE = "AAAAA";
    private static final String UPDATED_MEANSOFPAYMENT_TYPE_CODE = "BBBBB";

    private static final String DEFAULT_ISSUER_CODE = "AAAAA";
    private static final String UPDATED_ISSUER_CODE = "BBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private MeansofpaymentRepository meansofpaymentRepository;

    @Autowired
    private MeansofpaymentMapper meansofpaymentMapper;

    @Autowired
    private MeansofpaymentService meansofpaymentService;

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

    private MockMvc restMeansofpaymentMockMvc;

    private Meansofpayment meansofpayment;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MeansofpaymentResource meansofpaymentResource = new MeansofpaymentResource(meansofpaymentService);
        this.restMeansofpaymentMockMvc = MockMvcBuilders.standaloneSetup(meansofpaymentResource)
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
    public static Meansofpayment createEntity(EntityManager em) {
        Meansofpayment meansofpayment = new Meansofpayment()
            .number(DEFAULT_NUMBER)
            .aliasAccount(DEFAULT_ALIAS_ACCOUNT)
            .baccBankCode(DEFAULT_BACC_BANK_CODE)
            .baccBranchCode(DEFAULT_BACC_BRANCH_CODE)
            .baccAccountNumber(DEFAULT_BACC_ACCOUNT_NUMBER)
            .baccRibKey(DEFAULT_BACC_RIB_KEY)
            .cardCvv2(DEFAULT_CARD_CVV_2)
            .cardPan(DEFAULT_CARD_PAN)
            .cardValidityDate(DEFAULT_CARD_VALIDITY_DATE)
            .momoAccount(DEFAULT_MOMO_ACCOUNT)
            .meansofpaymentTypeCode(DEFAULT_MEANSOFPAYMENT_TYPE_CODE)
            .issuerCode(DEFAULT_ISSUER_CODE)
            .active(DEFAULT_ACTIVE);
        return meansofpayment;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Meansofpayment createUpdatedEntity(EntityManager em) {
        Meansofpayment meansofpayment = new Meansofpayment()
            .number(UPDATED_NUMBER)
            .aliasAccount(UPDATED_ALIAS_ACCOUNT)
            .baccBankCode(UPDATED_BACC_BANK_CODE)
            .baccBranchCode(UPDATED_BACC_BRANCH_CODE)
            .baccAccountNumber(UPDATED_BACC_ACCOUNT_NUMBER)
            .baccRibKey(UPDATED_BACC_RIB_KEY)
            .cardCvv2(UPDATED_CARD_CVV_2)
            .cardPan(UPDATED_CARD_PAN)
            .cardValidityDate(UPDATED_CARD_VALIDITY_DATE)
            .momoAccount(UPDATED_MOMO_ACCOUNT)
            .meansofpaymentTypeCode(UPDATED_MEANSOFPAYMENT_TYPE_CODE)
            .issuerCode(UPDATED_ISSUER_CODE)
            .active(UPDATED_ACTIVE);
        return meansofpayment;
    }

    @BeforeEach
    public void initTest() {
        meansofpayment = createEntity(em);
    }

    @Test
    @Transactional
    public void createMeansofpayment() throws Exception {
        int databaseSizeBeforeCreate = meansofpaymentRepository.findAll().size();

        // Create the Meansofpayment
        MeansofpaymentDTO meansofpaymentDTO = meansofpaymentMapper.toDto(meansofpayment);
        restMeansofpaymentMockMvc.perform(post("/api/meansofpayments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meansofpaymentDTO)))
            .andExpect(status().isCreated());

        // Validate the Meansofpayment in the database
        List<Meansofpayment> meansofpaymentList = meansofpaymentRepository.findAll();
        assertThat(meansofpaymentList).hasSize(databaseSizeBeforeCreate + 1);
        Meansofpayment testMeansofpayment = meansofpaymentList.get(meansofpaymentList.size() - 1);
        assertThat(testMeansofpayment.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testMeansofpayment.getAliasAccount()).isEqualTo(DEFAULT_ALIAS_ACCOUNT);
        assertThat(testMeansofpayment.getBaccBankCode()).isEqualTo(DEFAULT_BACC_BANK_CODE);
        assertThat(testMeansofpayment.getBaccBranchCode()).isEqualTo(DEFAULT_BACC_BRANCH_CODE);
        assertThat(testMeansofpayment.getBaccAccountNumber()).isEqualTo(DEFAULT_BACC_ACCOUNT_NUMBER);
        assertThat(testMeansofpayment.getBaccRibKey()).isEqualTo(DEFAULT_BACC_RIB_KEY);
        assertThat(testMeansofpayment.getCardCvv2()).isEqualTo(DEFAULT_CARD_CVV_2);
        assertThat(testMeansofpayment.getCardPan()).isEqualTo(DEFAULT_CARD_PAN);
        assertThat(testMeansofpayment.getCardValidityDate()).isEqualTo(DEFAULT_CARD_VALIDITY_DATE);
        assertThat(testMeansofpayment.getMomoAccount()).isEqualTo(DEFAULT_MOMO_ACCOUNT);
        assertThat(testMeansofpayment.getMeansofpaymentTypeCode()).isEqualTo(DEFAULT_MEANSOFPAYMENT_TYPE_CODE);
        assertThat(testMeansofpayment.getIssuerCode()).isEqualTo(DEFAULT_ISSUER_CODE);
        assertThat(testMeansofpayment.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createMeansofpaymentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = meansofpaymentRepository.findAll().size();

        // Create the Meansofpayment with an existing ID
        meansofpayment.setId(1L);
        MeansofpaymentDTO meansofpaymentDTO = meansofpaymentMapper.toDto(meansofpayment);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMeansofpaymentMockMvc.perform(post("/api/meansofpayments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meansofpaymentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Meansofpayment in the database
        List<Meansofpayment> meansofpaymentList = meansofpaymentRepository.findAll();
        assertThat(meansofpaymentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = meansofpaymentRepository.findAll().size();
        // set the field null
        meansofpayment.setNumber(null);

        // Create the Meansofpayment, which fails.
        MeansofpaymentDTO meansofpaymentDTO = meansofpaymentMapper.toDto(meansofpayment);

        restMeansofpaymentMockMvc.perform(post("/api/meansofpayments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meansofpaymentDTO)))
            .andExpect(status().isBadRequest());

        List<Meansofpayment> meansofpaymentList = meansofpaymentRepository.findAll();
        assertThat(meansofpaymentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMeansofpaymentTypeCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = meansofpaymentRepository.findAll().size();
        // set the field null
        meansofpayment.setMeansofpaymentTypeCode(null);

        // Create the Meansofpayment, which fails.
        MeansofpaymentDTO meansofpaymentDTO = meansofpaymentMapper.toDto(meansofpayment);

        restMeansofpaymentMockMvc.perform(post("/api/meansofpayments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meansofpaymentDTO)))
            .andExpect(status().isBadRequest());

        List<Meansofpayment> meansofpaymentList = meansofpaymentRepository.findAll();
        assertThat(meansofpaymentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIssuerCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = meansofpaymentRepository.findAll().size();
        // set the field null
        meansofpayment.setIssuerCode(null);

        // Create the Meansofpayment, which fails.
        MeansofpaymentDTO meansofpaymentDTO = meansofpaymentMapper.toDto(meansofpayment);

        restMeansofpaymentMockMvc.perform(post("/api/meansofpayments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meansofpaymentDTO)))
            .andExpect(status().isBadRequest());

        List<Meansofpayment> meansofpaymentList = meansofpaymentRepository.findAll();
        assertThat(meansofpaymentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = meansofpaymentRepository.findAll().size();
        // set the field null
        meansofpayment.setActive(null);

        // Create the Meansofpayment, which fails.
        MeansofpaymentDTO meansofpaymentDTO = meansofpaymentMapper.toDto(meansofpayment);

        restMeansofpaymentMockMvc.perform(post("/api/meansofpayments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meansofpaymentDTO)))
            .andExpect(status().isBadRequest());

        List<Meansofpayment> meansofpaymentList = meansofpaymentRepository.findAll();
        assertThat(meansofpaymentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMeansofpayments() throws Exception {
        // Initialize the database
        meansofpaymentRepository.saveAndFlush(meansofpayment);

        // Get all the meansofpaymentList
        restMeansofpaymentMockMvc.perform(get("/api/meansofpayments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(meansofpayment.getId().intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].aliasAccount").value(hasItem(DEFAULT_ALIAS_ACCOUNT.toString())))
            .andExpect(jsonPath("$.[*].baccBankCode").value(hasItem(DEFAULT_BACC_BANK_CODE.toString())))
            .andExpect(jsonPath("$.[*].baccBranchCode").value(hasItem(DEFAULT_BACC_BRANCH_CODE.toString())))
            .andExpect(jsonPath("$.[*].baccAccountNumber").value(hasItem(DEFAULT_BACC_ACCOUNT_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].baccRibKey").value(hasItem(DEFAULT_BACC_RIB_KEY.toString())))
            .andExpect(jsonPath("$.[*].cardCvv2").value(hasItem(DEFAULT_CARD_CVV_2.toString())))
            .andExpect(jsonPath("$.[*].cardPan").value(hasItem(DEFAULT_CARD_PAN.toString())))
            .andExpect(jsonPath("$.[*].cardValidityDate").value(hasItem(DEFAULT_CARD_VALIDITY_DATE.toString())))
            .andExpect(jsonPath("$.[*].momoAccount").value(hasItem(DEFAULT_MOMO_ACCOUNT.toString())))
            .andExpect(jsonPath("$.[*].meansofpaymentTypeCode").value(hasItem(DEFAULT_MEANSOFPAYMENT_TYPE_CODE.toString())))
            .andExpect(jsonPath("$.[*].issuerCode").value(hasItem(DEFAULT_ISSUER_CODE.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMeansofpayment() throws Exception {
        // Initialize the database
        meansofpaymentRepository.saveAndFlush(meansofpayment);

        // Get the meansofpayment
        restMeansofpaymentMockMvc.perform(get("/api/meansofpayments/{id}", meansofpayment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(meansofpayment.getId().intValue()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER.toString()))
            .andExpect(jsonPath("$.aliasAccount").value(DEFAULT_ALIAS_ACCOUNT.toString()))
            .andExpect(jsonPath("$.baccBankCode").value(DEFAULT_BACC_BANK_CODE.toString()))
            .andExpect(jsonPath("$.baccBranchCode").value(DEFAULT_BACC_BRANCH_CODE.toString()))
            .andExpect(jsonPath("$.baccAccountNumber").value(DEFAULT_BACC_ACCOUNT_NUMBER.toString()))
            .andExpect(jsonPath("$.baccRibKey").value(DEFAULT_BACC_RIB_KEY.toString()))
            .andExpect(jsonPath("$.cardCvv2").value(DEFAULT_CARD_CVV_2.toString()))
            .andExpect(jsonPath("$.cardPan").value(DEFAULT_CARD_PAN.toString()))
            .andExpect(jsonPath("$.cardValidityDate").value(DEFAULT_CARD_VALIDITY_DATE.toString()))
            .andExpect(jsonPath("$.momoAccount").value(DEFAULT_MOMO_ACCOUNT.toString()))
            .andExpect(jsonPath("$.meansofpaymentTypeCode").value(DEFAULT_MEANSOFPAYMENT_TYPE_CODE.toString()))
            .andExpect(jsonPath("$.issuerCode").value(DEFAULT_ISSUER_CODE.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingMeansofpayment() throws Exception {
        // Get the meansofpayment
        restMeansofpaymentMockMvc.perform(get("/api/meansofpayments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMeansofpayment() throws Exception {
        // Initialize the database
        meansofpaymentRepository.saveAndFlush(meansofpayment);

        int databaseSizeBeforeUpdate = meansofpaymentRepository.findAll().size();

        // Update the meansofpayment
        Meansofpayment updatedMeansofpayment = meansofpaymentRepository.findById(meansofpayment.getId()).get();
        // Disconnect from session so that the updates on updatedMeansofpayment are not directly saved in db
        em.detach(updatedMeansofpayment);
        updatedMeansofpayment
            .number(UPDATED_NUMBER)
            .aliasAccount(UPDATED_ALIAS_ACCOUNT)
            .baccBankCode(UPDATED_BACC_BANK_CODE)
            .baccBranchCode(UPDATED_BACC_BRANCH_CODE)
            .baccAccountNumber(UPDATED_BACC_ACCOUNT_NUMBER)
            .baccRibKey(UPDATED_BACC_RIB_KEY)
            .cardCvv2(UPDATED_CARD_CVV_2)
            .cardPan(UPDATED_CARD_PAN)
            .cardValidityDate(UPDATED_CARD_VALIDITY_DATE)
            .momoAccount(UPDATED_MOMO_ACCOUNT)
            .meansofpaymentTypeCode(UPDATED_MEANSOFPAYMENT_TYPE_CODE)
            .issuerCode(UPDATED_ISSUER_CODE)
            .active(UPDATED_ACTIVE);
        MeansofpaymentDTO meansofpaymentDTO = meansofpaymentMapper.toDto(updatedMeansofpayment);

        restMeansofpaymentMockMvc.perform(put("/api/meansofpayments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meansofpaymentDTO)))
            .andExpect(status().isOk());

        // Validate the Meansofpayment in the database
        List<Meansofpayment> meansofpaymentList = meansofpaymentRepository.findAll();
        assertThat(meansofpaymentList).hasSize(databaseSizeBeforeUpdate);
        Meansofpayment testMeansofpayment = meansofpaymentList.get(meansofpaymentList.size() - 1);
        assertThat(testMeansofpayment.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testMeansofpayment.getAliasAccount()).isEqualTo(UPDATED_ALIAS_ACCOUNT);
        assertThat(testMeansofpayment.getBaccBankCode()).isEqualTo(UPDATED_BACC_BANK_CODE);
        assertThat(testMeansofpayment.getBaccBranchCode()).isEqualTo(UPDATED_BACC_BRANCH_CODE);
        assertThat(testMeansofpayment.getBaccAccountNumber()).isEqualTo(UPDATED_BACC_ACCOUNT_NUMBER);
        assertThat(testMeansofpayment.getBaccRibKey()).isEqualTo(UPDATED_BACC_RIB_KEY);
        assertThat(testMeansofpayment.getCardCvv2()).isEqualTo(UPDATED_CARD_CVV_2);
        assertThat(testMeansofpayment.getCardPan()).isEqualTo(UPDATED_CARD_PAN);
        assertThat(testMeansofpayment.getCardValidityDate()).isEqualTo(UPDATED_CARD_VALIDITY_DATE);
        assertThat(testMeansofpayment.getMomoAccount()).isEqualTo(UPDATED_MOMO_ACCOUNT);
        assertThat(testMeansofpayment.getMeansofpaymentTypeCode()).isEqualTo(UPDATED_MEANSOFPAYMENT_TYPE_CODE);
        assertThat(testMeansofpayment.getIssuerCode()).isEqualTo(UPDATED_ISSUER_CODE);
        assertThat(testMeansofpayment.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingMeansofpayment() throws Exception {
        int databaseSizeBeforeUpdate = meansofpaymentRepository.findAll().size();

        // Create the Meansofpayment
        MeansofpaymentDTO meansofpaymentDTO = meansofpaymentMapper.toDto(meansofpayment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMeansofpaymentMockMvc.perform(put("/api/meansofpayments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meansofpaymentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Meansofpayment in the database
        List<Meansofpayment> meansofpaymentList = meansofpaymentRepository.findAll();
        assertThat(meansofpaymentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMeansofpayment() throws Exception {
        // Initialize the database
        meansofpaymentRepository.saveAndFlush(meansofpayment);

        int databaseSizeBeforeDelete = meansofpaymentRepository.findAll().size();

        // Delete the meansofpayment
        restMeansofpaymentMockMvc.perform(delete("/api/meansofpayments/{id}", meansofpayment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Meansofpayment> meansofpaymentList = meansofpaymentRepository.findAll();
        assertThat(meansofpaymentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Meansofpayment.class);
        Meansofpayment meansofpayment1 = new Meansofpayment();
        meansofpayment1.setId(1L);
        Meansofpayment meansofpayment2 = new Meansofpayment();
        meansofpayment2.setId(meansofpayment1.getId());
        assertThat(meansofpayment1).isEqualTo(meansofpayment2);
        meansofpayment2.setId(2L);
        assertThat(meansofpayment1).isNotEqualTo(meansofpayment2);
        meansofpayment1.setId(null);
        assertThat(meansofpayment1).isNotEqualTo(meansofpayment2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MeansofpaymentDTO.class);
        MeansofpaymentDTO meansofpaymentDTO1 = new MeansofpaymentDTO();
        meansofpaymentDTO1.setId(1L);
        MeansofpaymentDTO meansofpaymentDTO2 = new MeansofpaymentDTO();
        assertThat(meansofpaymentDTO1).isNotEqualTo(meansofpaymentDTO2);
        meansofpaymentDTO2.setId(meansofpaymentDTO1.getId());
        assertThat(meansofpaymentDTO1).isEqualTo(meansofpaymentDTO2);
        meansofpaymentDTO2.setId(2L);
        assertThat(meansofpaymentDTO1).isNotEqualTo(meansofpaymentDTO2);
        meansofpaymentDTO1.setId(null);
        assertThat(meansofpaymentDTO1).isNotEqualTo(meansofpaymentDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(meansofpaymentMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(meansofpaymentMapper.fromId(null)).isNull();
    }
}
