package com.electron.mfs.pg.gateway.web.rest;

import com.electron.mfs.pg.gateway.PgkernelgatewayApp;
import com.electron.mfs.pg.gateway.config.SecurityBeanOverrideConfiguration;
import com.electron.mfs.pg.gateway.domain.TransactionPrice;
import com.electron.mfs.pg.gateway.repository.TransactionPriceRepository;
import com.electron.mfs.pg.gateway.service.TransactionPriceService;
import com.electron.mfs.pg.gateway.service.dto.TransactionPriceDTO;
import com.electron.mfs.pg.gateway.service.mapper.TransactionPriceMapper;
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
 * Integration tests for the {@Link TransactionPriceResource} REST controller.
 */
@EmbeddedKafka
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, PgkernelgatewayApp.class})
public class TransactionPriceResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

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

    private static final String DEFAULT_PRICE_CODE = "AAAAA";
    private static final String UPDATED_PRICE_CODE = "BBBBB";

    private static final String DEFAULT_SERVICE_CODE = "AAAAA";
    private static final String UPDATED_SERVICE_CODE = "BBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Instant DEFAULT_MODIFICATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MODIFICATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private TransactionPriceRepository transactionPriceRepository;

    @Autowired
    private TransactionPriceMapper transactionPriceMapper;

    @Autowired
    private TransactionPriceService transactionPriceService;

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

    private MockMvc restTransactionPriceMockMvc;

    private TransactionPrice transactionPrice;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TransactionPriceResource transactionPriceResource = new TransactionPriceResource(transactionPriceService);
        this.restTransactionPriceMockMvc = MockMvcBuilders.standaloneSetup(transactionPriceResource)
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
    public static TransactionPrice createEntity(EntityManager em) {
        TransactionPrice transactionPrice = new TransactionPrice()
            .code(DEFAULT_CODE)
            .label(DEFAULT_LABEL)
            .amount(DEFAULT_AMOUNT)
            .percent(DEFAULT_PERCENT)
            .amountInPercent(DEFAULT_AMOUNT_IN_PERCENT)
            .amountTransactionMax(DEFAULT_AMOUNT_TRANSACTION_MAX)
            .amountTransactionMin(DEFAULT_AMOUNT_TRANSACTION_MIN)
            .priceCode(DEFAULT_PRICE_CODE)
            .serviceCode(DEFAULT_SERVICE_CODE)
            .description(DEFAULT_DESCRIPTION)
            .modificationDate(DEFAULT_MODIFICATION_DATE);
        return transactionPrice;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TransactionPrice createUpdatedEntity(EntityManager em) {
        TransactionPrice transactionPrice = new TransactionPrice()
            .code(UPDATED_CODE)
            .label(UPDATED_LABEL)
            .amount(UPDATED_AMOUNT)
            .percent(UPDATED_PERCENT)
            .amountInPercent(UPDATED_AMOUNT_IN_PERCENT)
            .amountTransactionMax(UPDATED_AMOUNT_TRANSACTION_MAX)
            .amountTransactionMin(UPDATED_AMOUNT_TRANSACTION_MIN)
            .priceCode(UPDATED_PRICE_CODE)
            .serviceCode(UPDATED_SERVICE_CODE)
            .description(UPDATED_DESCRIPTION)
            .modificationDate(UPDATED_MODIFICATION_DATE);
        return transactionPrice;
    }

    @BeforeEach
    public void initTest() {
        transactionPrice = createEntity(em);
    }

    @Test
    @Transactional
    public void createTransactionPrice() throws Exception {
        int databaseSizeBeforeCreate = transactionPriceRepository.findAll().size();

        // Create the TransactionPrice
        TransactionPriceDTO transactionPriceDTO = transactionPriceMapper.toDto(transactionPrice);
        restTransactionPriceMockMvc.perform(post("/api/transaction-prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionPriceDTO)))
            .andExpect(status().isCreated());

        // Validate the TransactionPrice in the database
        List<TransactionPrice> transactionPriceList = transactionPriceRepository.findAll();
        assertThat(transactionPriceList).hasSize(databaseSizeBeforeCreate + 1);
        TransactionPrice testTransactionPrice = transactionPriceList.get(transactionPriceList.size() - 1);
        assertThat(testTransactionPrice.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testTransactionPrice.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testTransactionPrice.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testTransactionPrice.getPercent()).isEqualTo(DEFAULT_PERCENT);
        assertThat(testTransactionPrice.isAmountInPercent()).isEqualTo(DEFAULT_AMOUNT_IN_PERCENT);
        assertThat(testTransactionPrice.getAmountTransactionMax()).isEqualTo(DEFAULT_AMOUNT_TRANSACTION_MAX);
        assertThat(testTransactionPrice.getAmountTransactionMin()).isEqualTo(DEFAULT_AMOUNT_TRANSACTION_MIN);
        assertThat(testTransactionPrice.getPriceCode()).isEqualTo(DEFAULT_PRICE_CODE);
        assertThat(testTransactionPrice.getServiceCode()).isEqualTo(DEFAULT_SERVICE_CODE);
        assertThat(testTransactionPrice.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTransactionPrice.getModificationDate()).isEqualTo(DEFAULT_MODIFICATION_DATE);
    }

    @Test
    @Transactional
    public void createTransactionPriceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = transactionPriceRepository.findAll().size();

        // Create the TransactionPrice with an existing ID
        transactionPrice.setId(1L);
        TransactionPriceDTO transactionPriceDTO = transactionPriceMapper.toDto(transactionPrice);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransactionPriceMockMvc.perform(post("/api/transaction-prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionPriceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TransactionPrice in the database
        List<TransactionPrice> transactionPriceList = transactionPriceRepository.findAll();
        assertThat(transactionPriceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionPriceRepository.findAll().size();
        // set the field null
        transactionPrice.setCode(null);

        // Create the TransactionPrice, which fails.
        TransactionPriceDTO transactionPriceDTO = transactionPriceMapper.toDto(transactionPrice);

        restTransactionPriceMockMvc.perform(post("/api/transaction-prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionPriceDTO)))
            .andExpect(status().isBadRequest());

        List<TransactionPrice> transactionPriceList = transactionPriceRepository.findAll();
        assertThat(transactionPriceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLabelIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionPriceRepository.findAll().size();
        // set the field null
        transactionPrice.setLabel(null);

        // Create the TransactionPrice, which fails.
        TransactionPriceDTO transactionPriceDTO = transactionPriceMapper.toDto(transactionPrice);

        restTransactionPriceMockMvc.perform(post("/api/transaction-prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionPriceDTO)))
            .andExpect(status().isBadRequest());

        List<TransactionPrice> transactionPriceList = transactionPriceRepository.findAll();
        assertThat(transactionPriceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAmountInPercentIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionPriceRepository.findAll().size();
        // set the field null
        transactionPrice.setAmountInPercent(null);

        // Create the TransactionPrice, which fails.
        TransactionPriceDTO transactionPriceDTO = transactionPriceMapper.toDto(transactionPrice);

        restTransactionPriceMockMvc.perform(post("/api/transaction-prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionPriceDTO)))
            .andExpect(status().isBadRequest());

        List<TransactionPrice> transactionPriceList = transactionPriceRepository.findAll();
        assertThat(transactionPriceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPriceCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionPriceRepository.findAll().size();
        // set the field null
        transactionPrice.setPriceCode(null);

        // Create the TransactionPrice, which fails.
        TransactionPriceDTO transactionPriceDTO = transactionPriceMapper.toDto(transactionPrice);

        restTransactionPriceMockMvc.perform(post("/api/transaction-prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionPriceDTO)))
            .andExpect(status().isBadRequest());

        List<TransactionPrice> transactionPriceList = transactionPriceRepository.findAll();
        assertThat(transactionPriceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkServiceCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionPriceRepository.findAll().size();
        // set the field null
        transactionPrice.setServiceCode(null);

        // Create the TransactionPrice, which fails.
        TransactionPriceDTO transactionPriceDTO = transactionPriceMapper.toDto(transactionPrice);

        restTransactionPriceMockMvc.perform(post("/api/transaction-prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionPriceDTO)))
            .andExpect(status().isBadRequest());

        List<TransactionPrice> transactionPriceList = transactionPriceRepository.findAll();
        assertThat(transactionPriceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTransactionPrices() throws Exception {
        // Initialize the database
        transactionPriceRepository.saveAndFlush(transactionPrice);

        // Get all the transactionPriceList
        restTransactionPriceMockMvc.perform(get("/api/transaction-prices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transactionPrice.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].percent").value(hasItem(DEFAULT_PERCENT.doubleValue())))
            .andExpect(jsonPath("$.[*].amountInPercent").value(hasItem(DEFAULT_AMOUNT_IN_PERCENT.booleanValue())))
            .andExpect(jsonPath("$.[*].amountTransactionMax").value(hasItem(DEFAULT_AMOUNT_TRANSACTION_MAX.doubleValue())))
            .andExpect(jsonPath("$.[*].amountTransactionMin").value(hasItem(DEFAULT_AMOUNT_TRANSACTION_MIN.doubleValue())))
            .andExpect(jsonPath("$.[*].priceCode").value(hasItem(DEFAULT_PRICE_CODE.toString())))
            .andExpect(jsonPath("$.[*].serviceCode").value(hasItem(DEFAULT_SERVICE_CODE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].modificationDate").value(hasItem(DEFAULT_MODIFICATION_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getTransactionPrice() throws Exception {
        // Initialize the database
        transactionPriceRepository.saveAndFlush(transactionPrice);

        // Get the transactionPrice
        restTransactionPriceMockMvc.perform(get("/api/transaction-prices/{id}", transactionPrice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(transactionPrice.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.percent").value(DEFAULT_PERCENT.doubleValue()))
            .andExpect(jsonPath("$.amountInPercent").value(DEFAULT_AMOUNT_IN_PERCENT.booleanValue()))
            .andExpect(jsonPath("$.amountTransactionMax").value(DEFAULT_AMOUNT_TRANSACTION_MAX.doubleValue()))
            .andExpect(jsonPath("$.amountTransactionMin").value(DEFAULT_AMOUNT_TRANSACTION_MIN.doubleValue()))
            .andExpect(jsonPath("$.priceCode").value(DEFAULT_PRICE_CODE.toString()))
            .andExpect(jsonPath("$.serviceCode").value(DEFAULT_SERVICE_CODE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.modificationDate").value(DEFAULT_MODIFICATION_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTransactionPrice() throws Exception {
        // Get the transactionPrice
        restTransactionPriceMockMvc.perform(get("/api/transaction-prices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTransactionPrice() throws Exception {
        // Initialize the database
        transactionPriceRepository.saveAndFlush(transactionPrice);

        int databaseSizeBeforeUpdate = transactionPriceRepository.findAll().size();

        // Update the transactionPrice
        TransactionPrice updatedTransactionPrice = transactionPriceRepository.findById(transactionPrice.getId()).get();
        // Disconnect from session so that the updates on updatedTransactionPrice are not directly saved in db
        em.detach(updatedTransactionPrice);
        updatedTransactionPrice
            .code(UPDATED_CODE)
            .label(UPDATED_LABEL)
            .amount(UPDATED_AMOUNT)
            .percent(UPDATED_PERCENT)
            .amountInPercent(UPDATED_AMOUNT_IN_PERCENT)
            .amountTransactionMax(UPDATED_AMOUNT_TRANSACTION_MAX)
            .amountTransactionMin(UPDATED_AMOUNT_TRANSACTION_MIN)
            .priceCode(UPDATED_PRICE_CODE)
            .serviceCode(UPDATED_SERVICE_CODE)
            .description(UPDATED_DESCRIPTION)
            .modificationDate(UPDATED_MODIFICATION_DATE);
        TransactionPriceDTO transactionPriceDTO = transactionPriceMapper.toDto(updatedTransactionPrice);

        restTransactionPriceMockMvc.perform(put("/api/transaction-prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionPriceDTO)))
            .andExpect(status().isOk());

        // Validate the TransactionPrice in the database
        List<TransactionPrice> transactionPriceList = transactionPriceRepository.findAll();
        assertThat(transactionPriceList).hasSize(databaseSizeBeforeUpdate);
        TransactionPrice testTransactionPrice = transactionPriceList.get(transactionPriceList.size() - 1);
        assertThat(testTransactionPrice.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTransactionPrice.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testTransactionPrice.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testTransactionPrice.getPercent()).isEqualTo(UPDATED_PERCENT);
        assertThat(testTransactionPrice.isAmountInPercent()).isEqualTo(UPDATED_AMOUNT_IN_PERCENT);
        assertThat(testTransactionPrice.getAmountTransactionMax()).isEqualTo(UPDATED_AMOUNT_TRANSACTION_MAX);
        assertThat(testTransactionPrice.getAmountTransactionMin()).isEqualTo(UPDATED_AMOUNT_TRANSACTION_MIN);
        assertThat(testTransactionPrice.getPriceCode()).isEqualTo(UPDATED_PRICE_CODE);
        assertThat(testTransactionPrice.getServiceCode()).isEqualTo(UPDATED_SERVICE_CODE);
        assertThat(testTransactionPrice.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTransactionPrice.getModificationDate()).isEqualTo(UPDATED_MODIFICATION_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingTransactionPrice() throws Exception {
        int databaseSizeBeforeUpdate = transactionPriceRepository.findAll().size();

        // Create the TransactionPrice
        TransactionPriceDTO transactionPriceDTO = transactionPriceMapper.toDto(transactionPrice);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransactionPriceMockMvc.perform(put("/api/transaction-prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionPriceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TransactionPrice in the database
        List<TransactionPrice> transactionPriceList = transactionPriceRepository.findAll();
        assertThat(transactionPriceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTransactionPrice() throws Exception {
        // Initialize the database
        transactionPriceRepository.saveAndFlush(transactionPrice);

        int databaseSizeBeforeDelete = transactionPriceRepository.findAll().size();

        // Delete the transactionPrice
        restTransactionPriceMockMvc.perform(delete("/api/transaction-prices/{id}", transactionPrice.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TransactionPrice> transactionPriceList = transactionPriceRepository.findAll();
        assertThat(transactionPriceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TransactionPrice.class);
        TransactionPrice transactionPrice1 = new TransactionPrice();
        transactionPrice1.setId(1L);
        TransactionPrice transactionPrice2 = new TransactionPrice();
        transactionPrice2.setId(transactionPrice1.getId());
        assertThat(transactionPrice1).isEqualTo(transactionPrice2);
        transactionPrice2.setId(2L);
        assertThat(transactionPrice1).isNotEqualTo(transactionPrice2);
        transactionPrice1.setId(null);
        assertThat(transactionPrice1).isNotEqualTo(transactionPrice2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TransactionPriceDTO.class);
        TransactionPriceDTO transactionPriceDTO1 = new TransactionPriceDTO();
        transactionPriceDTO1.setId(1L);
        TransactionPriceDTO transactionPriceDTO2 = new TransactionPriceDTO();
        assertThat(transactionPriceDTO1).isNotEqualTo(transactionPriceDTO2);
        transactionPriceDTO2.setId(transactionPriceDTO1.getId());
        assertThat(transactionPriceDTO1).isEqualTo(transactionPriceDTO2);
        transactionPriceDTO2.setId(2L);
        assertThat(transactionPriceDTO1).isNotEqualTo(transactionPriceDTO2);
        transactionPriceDTO1.setId(null);
        assertThat(transactionPriceDTO1).isNotEqualTo(transactionPriceDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(transactionPriceMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(transactionPriceMapper.fromId(null)).isNull();
    }
}
