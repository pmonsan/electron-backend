package com.electron.mfs.pg.feescommission.web.rest;

import com.electron.mfs.pg.feescommission.FeesCommissionManagerApp;
import com.electron.mfs.pg.feescommission.config.SecurityBeanOverrideConfiguration;
import com.electron.mfs.pg.feescommission.domain.Price;
import com.electron.mfs.pg.feescommission.repository.PriceRepository;
import com.electron.mfs.pg.feescommission.service.PriceService;
import com.electron.mfs.pg.feescommission.service.dto.PriceDTO;
import com.electron.mfs.pg.feescommission.service.mapper.PriceMapper;
import com.electron.mfs.pg.feescommission.web.rest.errors.ExceptionTranslator;

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

import static com.electron.mfs.pg.feescommission.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link PriceResource} REST controller.
 */
@EmbeddedKafka
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, FeesCommissionManagerApp.class})
public class PriceResourceIT {

    private static final String DEFAULT_CODE = "AAAAA";
    private static final String UPDATED_CODE = "BBBBB";

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final Double DEFAULT_AMOUNT = 0D;
    private static final Double UPDATED_AMOUNT = 1D;

    private static final Double DEFAULT_PERCENT = 0D;
    private static final Double UPDATED_PERCENT = 1D;

    private static final Boolean DEFAULT_AMOUNT_IN_PERCENT = false;
    private static final Boolean UPDATED_AMOUNT_IN_PERCENT = true;

    private static final Double DEFAULT_AMOUNT_TRANSACTION_MAX = 0D;
    private static final Double UPDATED_AMOUNT_TRANSACTION_MAX = 1D;

    private static final Double DEFAULT_AMOUNT_TRANSACTION_MIN = 0D;
    private static final Double UPDATED_AMOUNT_TRANSACTION_MIN = 1D;

    private static final String DEFAULT_CURRENCY_CODE = "AAAAA";
    private static final String UPDATED_CURRENCY_CODE = "BBBBB";

    private static final String DEFAULT_SERVICE_CODE = "AAAAA";
    private static final String UPDATED_SERVICE_CODE = "BBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Instant DEFAULT_MODIFICATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MODIFICATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private PriceRepository priceRepository;

    @Autowired
    private PriceMapper priceMapper;

    @Autowired
    private PriceService priceService;

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

    private MockMvc restPriceMockMvc;

    private Price price;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PriceResource priceResource = new PriceResource(priceService);
        this.restPriceMockMvc = MockMvcBuilders.standaloneSetup(priceResource)
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
    public static Price createEntity(EntityManager em) {
        Price price = new Price()
            .code(DEFAULT_CODE)
            .label(DEFAULT_LABEL)
            .amount(DEFAULT_AMOUNT)
            .percent(DEFAULT_PERCENT)
            .amountInPercent(DEFAULT_AMOUNT_IN_PERCENT)
            .amountTransactionMax(DEFAULT_AMOUNT_TRANSACTION_MAX)
            .amountTransactionMin(DEFAULT_AMOUNT_TRANSACTION_MIN)
            .currencyCode(DEFAULT_CURRENCY_CODE)
            .serviceCode(DEFAULT_SERVICE_CODE)
            .description(DEFAULT_DESCRIPTION)
            .modificationDate(DEFAULT_MODIFICATION_DATE)
            .active(DEFAULT_ACTIVE);
        return price;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Price createUpdatedEntity(EntityManager em) {
        Price price = new Price()
            .code(UPDATED_CODE)
            .label(UPDATED_LABEL)
            .amount(UPDATED_AMOUNT)
            .percent(UPDATED_PERCENT)
            .amountInPercent(UPDATED_AMOUNT_IN_PERCENT)
            .amountTransactionMax(UPDATED_AMOUNT_TRANSACTION_MAX)
            .amountTransactionMin(UPDATED_AMOUNT_TRANSACTION_MIN)
            .currencyCode(UPDATED_CURRENCY_CODE)
            .serviceCode(UPDATED_SERVICE_CODE)
            .description(UPDATED_DESCRIPTION)
            .modificationDate(UPDATED_MODIFICATION_DATE)
            .active(UPDATED_ACTIVE);
        return price;
    }

    @BeforeEach
    public void initTest() {
        price = createEntity(em);
    }

    @Test
    @Transactional
    public void createPrice() throws Exception {
        int databaseSizeBeforeCreate = priceRepository.findAll().size();

        // Create the Price
        PriceDTO priceDTO = priceMapper.toDto(price);
        restPriceMockMvc.perform(post("/api/prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(priceDTO)))
            .andExpect(status().isCreated());

        // Validate the Price in the database
        List<Price> priceList = priceRepository.findAll();
        assertThat(priceList).hasSize(databaseSizeBeforeCreate + 1);
        Price testPrice = priceList.get(priceList.size() - 1);
        assertThat(testPrice.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testPrice.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testPrice.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testPrice.getPercent()).isEqualTo(DEFAULT_PERCENT);
        assertThat(testPrice.isAmountInPercent()).isEqualTo(DEFAULT_AMOUNT_IN_PERCENT);
        assertThat(testPrice.getAmountTransactionMax()).isEqualTo(DEFAULT_AMOUNT_TRANSACTION_MAX);
        assertThat(testPrice.getAmountTransactionMin()).isEqualTo(DEFAULT_AMOUNT_TRANSACTION_MIN);
        assertThat(testPrice.getCurrencyCode()).isEqualTo(DEFAULT_CURRENCY_CODE);
        assertThat(testPrice.getServiceCode()).isEqualTo(DEFAULT_SERVICE_CODE);
        assertThat(testPrice.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPrice.getModificationDate()).isEqualTo(DEFAULT_MODIFICATION_DATE);
        assertThat(testPrice.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createPriceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = priceRepository.findAll().size();

        // Create the Price with an existing ID
        price.setId(1L);
        PriceDTO priceDTO = priceMapper.toDto(price);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPriceMockMvc.perform(post("/api/prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(priceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Price in the database
        List<Price> priceList = priceRepository.findAll();
        assertThat(priceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = priceRepository.findAll().size();
        // set the field null
        price.setCode(null);

        // Create the Price, which fails.
        PriceDTO priceDTO = priceMapper.toDto(price);

        restPriceMockMvc.perform(post("/api/prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(priceDTO)))
            .andExpect(status().isBadRequest());

        List<Price> priceList = priceRepository.findAll();
        assertThat(priceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLabelIsRequired() throws Exception {
        int databaseSizeBeforeTest = priceRepository.findAll().size();
        // set the field null
        price.setLabel(null);

        // Create the Price, which fails.
        PriceDTO priceDTO = priceMapper.toDto(price);

        restPriceMockMvc.perform(post("/api/prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(priceDTO)))
            .andExpect(status().isBadRequest());

        List<Price> priceList = priceRepository.findAll();
        assertThat(priceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAmountInPercentIsRequired() throws Exception {
        int databaseSizeBeforeTest = priceRepository.findAll().size();
        // set the field null
        price.setAmountInPercent(null);

        // Create the Price, which fails.
        PriceDTO priceDTO = priceMapper.toDto(price);

        restPriceMockMvc.perform(post("/api/prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(priceDTO)))
            .andExpect(status().isBadRequest());

        List<Price> priceList = priceRepository.findAll();
        assertThat(priceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCurrencyCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = priceRepository.findAll().size();
        // set the field null
        price.setCurrencyCode(null);

        // Create the Price, which fails.
        PriceDTO priceDTO = priceMapper.toDto(price);

        restPriceMockMvc.perform(post("/api/prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(priceDTO)))
            .andExpect(status().isBadRequest());

        List<Price> priceList = priceRepository.findAll();
        assertThat(priceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkServiceCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = priceRepository.findAll().size();
        // set the field null
        price.setServiceCode(null);

        // Create the Price, which fails.
        PriceDTO priceDTO = priceMapper.toDto(price);

        restPriceMockMvc.perform(post("/api/prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(priceDTO)))
            .andExpect(status().isBadRequest());

        List<Price> priceList = priceRepository.findAll();
        assertThat(priceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = priceRepository.findAll().size();
        // set the field null
        price.setActive(null);

        // Create the Price, which fails.
        PriceDTO priceDTO = priceMapper.toDto(price);

        restPriceMockMvc.perform(post("/api/prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(priceDTO)))
            .andExpect(status().isBadRequest());

        List<Price> priceList = priceRepository.findAll();
        assertThat(priceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPrices() throws Exception {
        // Initialize the database
        priceRepository.saveAndFlush(price);

        // Get all the priceList
        restPriceMockMvc.perform(get("/api/prices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(price.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].percent").value(hasItem(DEFAULT_PERCENT.doubleValue())))
            .andExpect(jsonPath("$.[*].amountInPercent").value(hasItem(DEFAULT_AMOUNT_IN_PERCENT.booleanValue())))
            .andExpect(jsonPath("$.[*].amountTransactionMax").value(hasItem(DEFAULT_AMOUNT_TRANSACTION_MAX.doubleValue())))
            .andExpect(jsonPath("$.[*].amountTransactionMin").value(hasItem(DEFAULT_AMOUNT_TRANSACTION_MIN.doubleValue())))
            .andExpect(jsonPath("$.[*].currencyCode").value(hasItem(DEFAULT_CURRENCY_CODE.toString())))
            .andExpect(jsonPath("$.[*].serviceCode").value(hasItem(DEFAULT_SERVICE_CODE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].modificationDate").value(hasItem(DEFAULT_MODIFICATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getPrice() throws Exception {
        // Initialize the database
        priceRepository.saveAndFlush(price);

        // Get the price
        restPriceMockMvc.perform(get("/api/prices/{id}", price.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(price.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.percent").value(DEFAULT_PERCENT.doubleValue()))
            .andExpect(jsonPath("$.amountInPercent").value(DEFAULT_AMOUNT_IN_PERCENT.booleanValue()))
            .andExpect(jsonPath("$.amountTransactionMax").value(DEFAULT_AMOUNT_TRANSACTION_MAX.doubleValue()))
            .andExpect(jsonPath("$.amountTransactionMin").value(DEFAULT_AMOUNT_TRANSACTION_MIN.doubleValue()))
            .andExpect(jsonPath("$.currencyCode").value(DEFAULT_CURRENCY_CODE.toString()))
            .andExpect(jsonPath("$.serviceCode").value(DEFAULT_SERVICE_CODE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.modificationDate").value(DEFAULT_MODIFICATION_DATE.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPrice() throws Exception {
        // Get the price
        restPriceMockMvc.perform(get("/api/prices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePrice() throws Exception {
        // Initialize the database
        priceRepository.saveAndFlush(price);

        int databaseSizeBeforeUpdate = priceRepository.findAll().size();

        // Update the price
        Price updatedPrice = priceRepository.findById(price.getId()).get();
        // Disconnect from session so that the updates on updatedPrice are not directly saved in db
        em.detach(updatedPrice);
        updatedPrice
            .code(UPDATED_CODE)
            .label(UPDATED_LABEL)
            .amount(UPDATED_AMOUNT)
            .percent(UPDATED_PERCENT)
            .amountInPercent(UPDATED_AMOUNT_IN_PERCENT)
            .amountTransactionMax(UPDATED_AMOUNT_TRANSACTION_MAX)
            .amountTransactionMin(UPDATED_AMOUNT_TRANSACTION_MIN)
            .currencyCode(UPDATED_CURRENCY_CODE)
            .serviceCode(UPDATED_SERVICE_CODE)
            .description(UPDATED_DESCRIPTION)
            .modificationDate(UPDATED_MODIFICATION_DATE)
            .active(UPDATED_ACTIVE);
        PriceDTO priceDTO = priceMapper.toDto(updatedPrice);

        restPriceMockMvc.perform(put("/api/prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(priceDTO)))
            .andExpect(status().isOk());

        // Validate the Price in the database
        List<Price> priceList = priceRepository.findAll();
        assertThat(priceList).hasSize(databaseSizeBeforeUpdate);
        Price testPrice = priceList.get(priceList.size() - 1);
        assertThat(testPrice.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testPrice.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testPrice.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testPrice.getPercent()).isEqualTo(UPDATED_PERCENT);
        assertThat(testPrice.isAmountInPercent()).isEqualTo(UPDATED_AMOUNT_IN_PERCENT);
        assertThat(testPrice.getAmountTransactionMax()).isEqualTo(UPDATED_AMOUNT_TRANSACTION_MAX);
        assertThat(testPrice.getAmountTransactionMin()).isEqualTo(UPDATED_AMOUNT_TRANSACTION_MIN);
        assertThat(testPrice.getCurrencyCode()).isEqualTo(UPDATED_CURRENCY_CODE);
        assertThat(testPrice.getServiceCode()).isEqualTo(UPDATED_SERVICE_CODE);
        assertThat(testPrice.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPrice.getModificationDate()).isEqualTo(UPDATED_MODIFICATION_DATE);
        assertThat(testPrice.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingPrice() throws Exception {
        int databaseSizeBeforeUpdate = priceRepository.findAll().size();

        // Create the Price
        PriceDTO priceDTO = priceMapper.toDto(price);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPriceMockMvc.perform(put("/api/prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(priceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Price in the database
        List<Price> priceList = priceRepository.findAll();
        assertThat(priceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePrice() throws Exception {
        // Initialize the database
        priceRepository.saveAndFlush(price);

        int databaseSizeBeforeDelete = priceRepository.findAll().size();

        // Delete the price
        restPriceMockMvc.perform(delete("/api/prices/{id}", price.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Price> priceList = priceRepository.findAll();
        assertThat(priceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Price.class);
        Price price1 = new Price();
        price1.setId(1L);
        Price price2 = new Price();
        price2.setId(price1.getId());
        assertThat(price1).isEqualTo(price2);
        price2.setId(2L);
        assertThat(price1).isNotEqualTo(price2);
        price1.setId(null);
        assertThat(price1).isNotEqualTo(price2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PriceDTO.class);
        PriceDTO priceDTO1 = new PriceDTO();
        priceDTO1.setId(1L);
        PriceDTO priceDTO2 = new PriceDTO();
        assertThat(priceDTO1).isNotEqualTo(priceDTO2);
        priceDTO2.setId(priceDTO1.getId());
        assertThat(priceDTO1).isEqualTo(priceDTO2);
        priceDTO2.setId(2L);
        assertThat(priceDTO1).isNotEqualTo(priceDTO2);
        priceDTO1.setId(null);
        assertThat(priceDTO1).isNotEqualTo(priceDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(priceMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(priceMapper.fromId(null)).isNull();
    }
}
