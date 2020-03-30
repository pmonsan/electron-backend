package com.electron.mfs.pg.gateway.web.rest;

import com.electron.mfs.pg.gateway.PgkernelgatewayApp;
import com.electron.mfs.pg.gateway.config.SecurityBeanOverrideConfiguration;
import com.electron.mfs.pg.gateway.domain.PriceCommission;
import com.electron.mfs.pg.gateway.repository.PriceCommissionRepository;
import com.electron.mfs.pg.gateway.service.PriceCommissionService;
import com.electron.mfs.pg.gateway.service.dto.PriceCommissionDTO;
import com.electron.mfs.pg.gateway.service.mapper.PriceCommissionMapper;
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
 * Integration tests for the {@Link PriceCommissionResource} REST controller.
 */
@EmbeddedKafka
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, PgkernelgatewayApp.class})
public class PriceCommissionResourceIT {

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

    private static final String DEFAULT_CURRENCY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CURRENCY_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_PARTNER_CODE = "AAAAAAAAAA";
    private static final String UPDATED_PARTNER_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_SERVICE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_SERVICE_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private PriceCommissionRepository priceCommissionRepository;

    @Autowired
    private PriceCommissionMapper priceCommissionMapper;

    @Autowired
    private PriceCommissionService priceCommissionService;

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

    private MockMvc restPriceCommissionMockMvc;

    private PriceCommission priceCommission;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PriceCommissionResource priceCommissionResource = new PriceCommissionResource(priceCommissionService);
        this.restPriceCommissionMockMvc = MockMvcBuilders.standaloneSetup(priceCommissionResource)
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
    public static PriceCommission createEntity(EntityManager em) {
        PriceCommission priceCommission = new PriceCommission()
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
            .active(DEFAULT_ACTIVE);
        return priceCommission;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PriceCommission createUpdatedEntity(EntityManager em) {
        PriceCommission priceCommission = new PriceCommission()
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
            .active(UPDATED_ACTIVE);
        return priceCommission;
    }

    @BeforeEach
    public void initTest() {
        priceCommission = createEntity(em);
    }

    @Test
    @Transactional
    public void createPriceCommission() throws Exception {
        int databaseSizeBeforeCreate = priceCommissionRepository.findAll().size();

        // Create the PriceCommission
        PriceCommissionDTO priceCommissionDTO = priceCommissionMapper.toDto(priceCommission);
        restPriceCommissionMockMvc.perform(post("/api/price-commissions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(priceCommissionDTO)))
            .andExpect(status().isCreated());

        // Validate the PriceCommission in the database
        List<PriceCommission> priceCommissionList = priceCommissionRepository.findAll();
        assertThat(priceCommissionList).hasSize(databaseSizeBeforeCreate + 1);
        PriceCommission testPriceCommission = priceCommissionList.get(priceCommissionList.size() - 1);
        assertThat(testPriceCommission.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testPriceCommission.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testPriceCommission.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testPriceCommission.isAmountInPercent()).isEqualTo(DEFAULT_AMOUNT_IN_PERCENT);
        assertThat(testPriceCommission.getDateCreation()).isEqualTo(DEFAULT_DATE_CREATION);
        assertThat(testPriceCommission.getPercent()).isEqualTo(DEFAULT_PERCENT);
        assertThat(testPriceCommission.getCurrencyCode()).isEqualTo(DEFAULT_CURRENCY_CODE);
        assertThat(testPriceCommission.getPartnerCode()).isEqualTo(DEFAULT_PARTNER_CODE);
        assertThat(testPriceCommission.getServiceCode()).isEqualTo(DEFAULT_SERVICE_CODE);
        assertThat(testPriceCommission.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPriceCommission.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createPriceCommissionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = priceCommissionRepository.findAll().size();

        // Create the PriceCommission with an existing ID
        priceCommission.setId(1L);
        PriceCommissionDTO priceCommissionDTO = priceCommissionMapper.toDto(priceCommission);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPriceCommissionMockMvc.perform(post("/api/price-commissions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(priceCommissionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PriceCommission in the database
        List<PriceCommission> priceCommissionList = priceCommissionRepository.findAll();
        assertThat(priceCommissionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = priceCommissionRepository.findAll().size();
        // set the field null
        priceCommission.setCode(null);

        // Create the PriceCommission, which fails.
        PriceCommissionDTO priceCommissionDTO = priceCommissionMapper.toDto(priceCommission);

        restPriceCommissionMockMvc.perform(post("/api/price-commissions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(priceCommissionDTO)))
            .andExpect(status().isBadRequest());

        List<PriceCommission> priceCommissionList = priceCommissionRepository.findAll();
        assertThat(priceCommissionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLabelIsRequired() throws Exception {
        int databaseSizeBeforeTest = priceCommissionRepository.findAll().size();
        // set the field null
        priceCommission.setLabel(null);

        // Create the PriceCommission, which fails.
        PriceCommissionDTO priceCommissionDTO = priceCommissionMapper.toDto(priceCommission);

        restPriceCommissionMockMvc.perform(post("/api/price-commissions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(priceCommissionDTO)))
            .andExpect(status().isBadRequest());

        List<PriceCommission> priceCommissionList = priceCommissionRepository.findAll();
        assertThat(priceCommissionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAmountInPercentIsRequired() throws Exception {
        int databaseSizeBeforeTest = priceCommissionRepository.findAll().size();
        // set the field null
        priceCommission.setAmountInPercent(null);

        // Create the PriceCommission, which fails.
        PriceCommissionDTO priceCommissionDTO = priceCommissionMapper.toDto(priceCommission);

        restPriceCommissionMockMvc.perform(post("/api/price-commissions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(priceCommissionDTO)))
            .andExpect(status().isBadRequest());

        List<PriceCommission> priceCommissionList = priceCommissionRepository.findAll();
        assertThat(priceCommissionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateCreationIsRequired() throws Exception {
        int databaseSizeBeforeTest = priceCommissionRepository.findAll().size();
        // set the field null
        priceCommission.setDateCreation(null);

        // Create the PriceCommission, which fails.
        PriceCommissionDTO priceCommissionDTO = priceCommissionMapper.toDto(priceCommission);

        restPriceCommissionMockMvc.perform(post("/api/price-commissions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(priceCommissionDTO)))
            .andExpect(status().isBadRequest());

        List<PriceCommission> priceCommissionList = priceCommissionRepository.findAll();
        assertThat(priceCommissionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCurrencyCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = priceCommissionRepository.findAll().size();
        // set the field null
        priceCommission.setCurrencyCode(null);

        // Create the PriceCommission, which fails.
        PriceCommissionDTO priceCommissionDTO = priceCommissionMapper.toDto(priceCommission);

        restPriceCommissionMockMvc.perform(post("/api/price-commissions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(priceCommissionDTO)))
            .andExpect(status().isBadRequest());

        List<PriceCommission> priceCommissionList = priceCommissionRepository.findAll();
        assertThat(priceCommissionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPartnerCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = priceCommissionRepository.findAll().size();
        // set the field null
        priceCommission.setPartnerCode(null);

        // Create the PriceCommission, which fails.
        PriceCommissionDTO priceCommissionDTO = priceCommissionMapper.toDto(priceCommission);

        restPriceCommissionMockMvc.perform(post("/api/price-commissions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(priceCommissionDTO)))
            .andExpect(status().isBadRequest());

        List<PriceCommission> priceCommissionList = priceCommissionRepository.findAll();
        assertThat(priceCommissionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkServiceCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = priceCommissionRepository.findAll().size();
        // set the field null
        priceCommission.setServiceCode(null);

        // Create the PriceCommission, which fails.
        PriceCommissionDTO priceCommissionDTO = priceCommissionMapper.toDto(priceCommission);

        restPriceCommissionMockMvc.perform(post("/api/price-commissions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(priceCommissionDTO)))
            .andExpect(status().isBadRequest());

        List<PriceCommission> priceCommissionList = priceCommissionRepository.findAll();
        assertThat(priceCommissionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = priceCommissionRepository.findAll().size();
        // set the field null
        priceCommission.setActive(null);

        // Create the PriceCommission, which fails.
        PriceCommissionDTO priceCommissionDTO = priceCommissionMapper.toDto(priceCommission);

        restPriceCommissionMockMvc.perform(post("/api/price-commissions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(priceCommissionDTO)))
            .andExpect(status().isBadRequest());

        List<PriceCommission> priceCommissionList = priceCommissionRepository.findAll();
        assertThat(priceCommissionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPriceCommissions() throws Exception {
        // Initialize the database
        priceCommissionRepository.saveAndFlush(priceCommission);

        // Get all the priceCommissionList
        restPriceCommissionMockMvc.perform(get("/api/price-commissions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(priceCommission.getId().intValue())))
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
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getPriceCommission() throws Exception {
        // Initialize the database
        priceCommissionRepository.saveAndFlush(priceCommission);

        // Get the priceCommission
        restPriceCommissionMockMvc.perform(get("/api/price-commissions/{id}", priceCommission.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(priceCommission.getId().intValue()))
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
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPriceCommission() throws Exception {
        // Get the priceCommission
        restPriceCommissionMockMvc.perform(get("/api/price-commissions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePriceCommission() throws Exception {
        // Initialize the database
        priceCommissionRepository.saveAndFlush(priceCommission);

        int databaseSizeBeforeUpdate = priceCommissionRepository.findAll().size();

        // Update the priceCommission
        PriceCommission updatedPriceCommission = priceCommissionRepository.findById(priceCommission.getId()).get();
        // Disconnect from session so that the updates on updatedPriceCommission are not directly saved in db
        em.detach(updatedPriceCommission);
        updatedPriceCommission
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
            .active(UPDATED_ACTIVE);
        PriceCommissionDTO priceCommissionDTO = priceCommissionMapper.toDto(updatedPriceCommission);

        restPriceCommissionMockMvc.perform(put("/api/price-commissions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(priceCommissionDTO)))
            .andExpect(status().isOk());

        // Validate the PriceCommission in the database
        List<PriceCommission> priceCommissionList = priceCommissionRepository.findAll();
        assertThat(priceCommissionList).hasSize(databaseSizeBeforeUpdate);
        PriceCommission testPriceCommission = priceCommissionList.get(priceCommissionList.size() - 1);
        assertThat(testPriceCommission.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testPriceCommission.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testPriceCommission.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testPriceCommission.isAmountInPercent()).isEqualTo(UPDATED_AMOUNT_IN_PERCENT);
        assertThat(testPriceCommission.getDateCreation()).isEqualTo(UPDATED_DATE_CREATION);
        assertThat(testPriceCommission.getPercent()).isEqualTo(UPDATED_PERCENT);
        assertThat(testPriceCommission.getCurrencyCode()).isEqualTo(UPDATED_CURRENCY_CODE);
        assertThat(testPriceCommission.getPartnerCode()).isEqualTo(UPDATED_PARTNER_CODE);
        assertThat(testPriceCommission.getServiceCode()).isEqualTo(UPDATED_SERVICE_CODE);
        assertThat(testPriceCommission.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPriceCommission.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingPriceCommission() throws Exception {
        int databaseSizeBeforeUpdate = priceCommissionRepository.findAll().size();

        // Create the PriceCommission
        PriceCommissionDTO priceCommissionDTO = priceCommissionMapper.toDto(priceCommission);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPriceCommissionMockMvc.perform(put("/api/price-commissions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(priceCommissionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PriceCommission in the database
        List<PriceCommission> priceCommissionList = priceCommissionRepository.findAll();
        assertThat(priceCommissionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePriceCommission() throws Exception {
        // Initialize the database
        priceCommissionRepository.saveAndFlush(priceCommission);

        int databaseSizeBeforeDelete = priceCommissionRepository.findAll().size();

        // Delete the priceCommission
        restPriceCommissionMockMvc.perform(delete("/api/price-commissions/{id}", priceCommission.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PriceCommission> priceCommissionList = priceCommissionRepository.findAll();
        assertThat(priceCommissionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PriceCommission.class);
        PriceCommission priceCommission1 = new PriceCommission();
        priceCommission1.setId(1L);
        PriceCommission priceCommission2 = new PriceCommission();
        priceCommission2.setId(priceCommission1.getId());
        assertThat(priceCommission1).isEqualTo(priceCommission2);
        priceCommission2.setId(2L);
        assertThat(priceCommission1).isNotEqualTo(priceCommission2);
        priceCommission1.setId(null);
        assertThat(priceCommission1).isNotEqualTo(priceCommission2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PriceCommissionDTO.class);
        PriceCommissionDTO priceCommissionDTO1 = new PriceCommissionDTO();
        priceCommissionDTO1.setId(1L);
        PriceCommissionDTO priceCommissionDTO2 = new PriceCommissionDTO();
        assertThat(priceCommissionDTO1).isNotEqualTo(priceCommissionDTO2);
        priceCommissionDTO2.setId(priceCommissionDTO1.getId());
        assertThat(priceCommissionDTO1).isEqualTo(priceCommissionDTO2);
        priceCommissionDTO2.setId(2L);
        assertThat(priceCommissionDTO1).isNotEqualTo(priceCommissionDTO2);
        priceCommissionDTO1.setId(null);
        assertThat(priceCommissionDTO1).isNotEqualTo(priceCommissionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(priceCommissionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(priceCommissionMapper.fromId(null)).isNull();
    }
}
