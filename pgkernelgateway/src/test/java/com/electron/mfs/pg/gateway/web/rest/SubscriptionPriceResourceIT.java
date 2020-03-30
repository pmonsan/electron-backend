package com.electron.mfs.pg.gateway.web.rest;

import com.electron.mfs.pg.gateway.PgkernelgatewayApp;
import com.electron.mfs.pg.gateway.config.SecurityBeanOverrideConfiguration;
import com.electron.mfs.pg.gateway.domain.SubscriptionPrice;
import com.electron.mfs.pg.gateway.repository.SubscriptionPriceRepository;
import com.electron.mfs.pg.gateway.service.SubscriptionPriceService;
import com.electron.mfs.pg.gateway.service.dto.SubscriptionPriceDTO;
import com.electron.mfs.pg.gateway.service.mapper.SubscriptionPriceMapper;
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
 * Integration tests for the {@Link SubscriptionPriceResource} REST controller.
 */
@EmbeddedKafka
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, PgkernelgatewayApp.class})
public class SubscriptionPriceResourceIT {

    private static final Double DEFAULT_AMOUNT = 0D;
    private static final Double UPDATED_AMOUNT = 1D;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final Instant DEFAULT_MODIFICATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MODIFICATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_SERVICE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_SERVICE_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_ACCOUNT_TYPE_CODE = "AAAAA";
    private static final String UPDATED_ACCOUNT_TYPE_CODE = "BBBBB";

    private static final String DEFAULT_CURRENCY_CODE = "AAAAA";
    private static final String UPDATED_CURRENCY_CODE = "BBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private SubscriptionPriceRepository subscriptionPriceRepository;

    @Autowired
    private SubscriptionPriceMapper subscriptionPriceMapper;

    @Autowired
    private SubscriptionPriceService subscriptionPriceService;

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

    private MockMvc restSubscriptionPriceMockMvc;

    private SubscriptionPrice subscriptionPrice;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SubscriptionPriceResource subscriptionPriceResource = new SubscriptionPriceResource(subscriptionPriceService);
        this.restSubscriptionPriceMockMvc = MockMvcBuilders.standaloneSetup(subscriptionPriceResource)
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
    public static SubscriptionPrice createEntity(EntityManager em) {
        SubscriptionPrice subscriptionPrice = new SubscriptionPrice()
            .amount(DEFAULT_AMOUNT)
            .description(DEFAULT_DESCRIPTION)
            .label(DEFAULT_LABEL)
            .modificationDate(DEFAULT_MODIFICATION_DATE)
            .serviceCode(DEFAULT_SERVICE_CODE)
            .accountTypeCode(DEFAULT_ACCOUNT_TYPE_CODE)
            .currencyCode(DEFAULT_CURRENCY_CODE)
            .active(DEFAULT_ACTIVE);
        return subscriptionPrice;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SubscriptionPrice createUpdatedEntity(EntityManager em) {
        SubscriptionPrice subscriptionPrice = new SubscriptionPrice()
            .amount(UPDATED_AMOUNT)
            .description(UPDATED_DESCRIPTION)
            .label(UPDATED_LABEL)
            .modificationDate(UPDATED_MODIFICATION_DATE)
            .serviceCode(UPDATED_SERVICE_CODE)
            .accountTypeCode(UPDATED_ACCOUNT_TYPE_CODE)
            .currencyCode(UPDATED_CURRENCY_CODE)
            .active(UPDATED_ACTIVE);
        return subscriptionPrice;
    }

    @BeforeEach
    public void initTest() {
        subscriptionPrice = createEntity(em);
    }

    @Test
    @Transactional
    public void createSubscriptionPrice() throws Exception {
        int databaseSizeBeforeCreate = subscriptionPriceRepository.findAll().size();

        // Create the SubscriptionPrice
        SubscriptionPriceDTO subscriptionPriceDTO = subscriptionPriceMapper.toDto(subscriptionPrice);
        restSubscriptionPriceMockMvc.perform(post("/api/subscription-prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subscriptionPriceDTO)))
            .andExpect(status().isCreated());

        // Validate the SubscriptionPrice in the database
        List<SubscriptionPrice> subscriptionPriceList = subscriptionPriceRepository.findAll();
        assertThat(subscriptionPriceList).hasSize(databaseSizeBeforeCreate + 1);
        SubscriptionPrice testSubscriptionPrice = subscriptionPriceList.get(subscriptionPriceList.size() - 1);
        assertThat(testSubscriptionPrice.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testSubscriptionPrice.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testSubscriptionPrice.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testSubscriptionPrice.getModificationDate()).isEqualTo(DEFAULT_MODIFICATION_DATE);
        assertThat(testSubscriptionPrice.getServiceCode()).isEqualTo(DEFAULT_SERVICE_CODE);
        assertThat(testSubscriptionPrice.getAccountTypeCode()).isEqualTo(DEFAULT_ACCOUNT_TYPE_CODE);
        assertThat(testSubscriptionPrice.getCurrencyCode()).isEqualTo(DEFAULT_CURRENCY_CODE);
        assertThat(testSubscriptionPrice.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createSubscriptionPriceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = subscriptionPriceRepository.findAll().size();

        // Create the SubscriptionPrice with an existing ID
        subscriptionPrice.setId(1L);
        SubscriptionPriceDTO subscriptionPriceDTO = subscriptionPriceMapper.toDto(subscriptionPrice);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSubscriptionPriceMockMvc.perform(post("/api/subscription-prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subscriptionPriceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SubscriptionPrice in the database
        List<SubscriptionPrice> subscriptionPriceList = subscriptionPriceRepository.findAll();
        assertThat(subscriptionPriceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkServiceCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = subscriptionPriceRepository.findAll().size();
        // set the field null
        subscriptionPrice.setServiceCode(null);

        // Create the SubscriptionPrice, which fails.
        SubscriptionPriceDTO subscriptionPriceDTO = subscriptionPriceMapper.toDto(subscriptionPrice);

        restSubscriptionPriceMockMvc.perform(post("/api/subscription-prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subscriptionPriceDTO)))
            .andExpect(status().isBadRequest());

        List<SubscriptionPrice> subscriptionPriceList = subscriptionPriceRepository.findAll();
        assertThat(subscriptionPriceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAccountTypeCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = subscriptionPriceRepository.findAll().size();
        // set the field null
        subscriptionPrice.setAccountTypeCode(null);

        // Create the SubscriptionPrice, which fails.
        SubscriptionPriceDTO subscriptionPriceDTO = subscriptionPriceMapper.toDto(subscriptionPrice);

        restSubscriptionPriceMockMvc.perform(post("/api/subscription-prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subscriptionPriceDTO)))
            .andExpect(status().isBadRequest());

        List<SubscriptionPrice> subscriptionPriceList = subscriptionPriceRepository.findAll();
        assertThat(subscriptionPriceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCurrencyCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = subscriptionPriceRepository.findAll().size();
        // set the field null
        subscriptionPrice.setCurrencyCode(null);

        // Create the SubscriptionPrice, which fails.
        SubscriptionPriceDTO subscriptionPriceDTO = subscriptionPriceMapper.toDto(subscriptionPrice);

        restSubscriptionPriceMockMvc.perform(post("/api/subscription-prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subscriptionPriceDTO)))
            .andExpect(status().isBadRequest());

        List<SubscriptionPrice> subscriptionPriceList = subscriptionPriceRepository.findAll();
        assertThat(subscriptionPriceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = subscriptionPriceRepository.findAll().size();
        // set the field null
        subscriptionPrice.setActive(null);

        // Create the SubscriptionPrice, which fails.
        SubscriptionPriceDTO subscriptionPriceDTO = subscriptionPriceMapper.toDto(subscriptionPrice);

        restSubscriptionPriceMockMvc.perform(post("/api/subscription-prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subscriptionPriceDTO)))
            .andExpect(status().isBadRequest());

        List<SubscriptionPrice> subscriptionPriceList = subscriptionPriceRepository.findAll();
        assertThat(subscriptionPriceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSubscriptionPrices() throws Exception {
        // Initialize the database
        subscriptionPriceRepository.saveAndFlush(subscriptionPrice);

        // Get all the subscriptionPriceList
        restSubscriptionPriceMockMvc.perform(get("/api/subscription-prices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subscriptionPrice.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())))
            .andExpect(jsonPath("$.[*].modificationDate").value(hasItem(DEFAULT_MODIFICATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].serviceCode").value(hasItem(DEFAULT_SERVICE_CODE.toString())))
            .andExpect(jsonPath("$.[*].accountTypeCode").value(hasItem(DEFAULT_ACCOUNT_TYPE_CODE.toString())))
            .andExpect(jsonPath("$.[*].currencyCode").value(hasItem(DEFAULT_CURRENCY_CODE.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getSubscriptionPrice() throws Exception {
        // Initialize the database
        subscriptionPriceRepository.saveAndFlush(subscriptionPrice);

        // Get the subscriptionPrice
        restSubscriptionPriceMockMvc.perform(get("/api/subscription-prices/{id}", subscriptionPrice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(subscriptionPrice.getId().intValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()))
            .andExpect(jsonPath("$.modificationDate").value(DEFAULT_MODIFICATION_DATE.toString()))
            .andExpect(jsonPath("$.serviceCode").value(DEFAULT_SERVICE_CODE.toString()))
            .andExpect(jsonPath("$.accountTypeCode").value(DEFAULT_ACCOUNT_TYPE_CODE.toString()))
            .andExpect(jsonPath("$.currencyCode").value(DEFAULT_CURRENCY_CODE.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingSubscriptionPrice() throws Exception {
        // Get the subscriptionPrice
        restSubscriptionPriceMockMvc.perform(get("/api/subscription-prices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSubscriptionPrice() throws Exception {
        // Initialize the database
        subscriptionPriceRepository.saveAndFlush(subscriptionPrice);

        int databaseSizeBeforeUpdate = subscriptionPriceRepository.findAll().size();

        // Update the subscriptionPrice
        SubscriptionPrice updatedSubscriptionPrice = subscriptionPriceRepository.findById(subscriptionPrice.getId()).get();
        // Disconnect from session so that the updates on updatedSubscriptionPrice are not directly saved in db
        em.detach(updatedSubscriptionPrice);
        updatedSubscriptionPrice
            .amount(UPDATED_AMOUNT)
            .description(UPDATED_DESCRIPTION)
            .label(UPDATED_LABEL)
            .modificationDate(UPDATED_MODIFICATION_DATE)
            .serviceCode(UPDATED_SERVICE_CODE)
            .accountTypeCode(UPDATED_ACCOUNT_TYPE_CODE)
            .currencyCode(UPDATED_CURRENCY_CODE)
            .active(UPDATED_ACTIVE);
        SubscriptionPriceDTO subscriptionPriceDTO = subscriptionPriceMapper.toDto(updatedSubscriptionPrice);

        restSubscriptionPriceMockMvc.perform(put("/api/subscription-prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subscriptionPriceDTO)))
            .andExpect(status().isOk());

        // Validate the SubscriptionPrice in the database
        List<SubscriptionPrice> subscriptionPriceList = subscriptionPriceRepository.findAll();
        assertThat(subscriptionPriceList).hasSize(databaseSizeBeforeUpdate);
        SubscriptionPrice testSubscriptionPrice = subscriptionPriceList.get(subscriptionPriceList.size() - 1);
        assertThat(testSubscriptionPrice.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testSubscriptionPrice.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSubscriptionPrice.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testSubscriptionPrice.getModificationDate()).isEqualTo(UPDATED_MODIFICATION_DATE);
        assertThat(testSubscriptionPrice.getServiceCode()).isEqualTo(UPDATED_SERVICE_CODE);
        assertThat(testSubscriptionPrice.getAccountTypeCode()).isEqualTo(UPDATED_ACCOUNT_TYPE_CODE);
        assertThat(testSubscriptionPrice.getCurrencyCode()).isEqualTo(UPDATED_CURRENCY_CODE);
        assertThat(testSubscriptionPrice.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingSubscriptionPrice() throws Exception {
        int databaseSizeBeforeUpdate = subscriptionPriceRepository.findAll().size();

        // Create the SubscriptionPrice
        SubscriptionPriceDTO subscriptionPriceDTO = subscriptionPriceMapper.toDto(subscriptionPrice);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubscriptionPriceMockMvc.perform(put("/api/subscription-prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subscriptionPriceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SubscriptionPrice in the database
        List<SubscriptionPrice> subscriptionPriceList = subscriptionPriceRepository.findAll();
        assertThat(subscriptionPriceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSubscriptionPrice() throws Exception {
        // Initialize the database
        subscriptionPriceRepository.saveAndFlush(subscriptionPrice);

        int databaseSizeBeforeDelete = subscriptionPriceRepository.findAll().size();

        // Delete the subscriptionPrice
        restSubscriptionPriceMockMvc.perform(delete("/api/subscription-prices/{id}", subscriptionPrice.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SubscriptionPrice> subscriptionPriceList = subscriptionPriceRepository.findAll();
        assertThat(subscriptionPriceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SubscriptionPrice.class);
        SubscriptionPrice subscriptionPrice1 = new SubscriptionPrice();
        subscriptionPrice1.setId(1L);
        SubscriptionPrice subscriptionPrice2 = new SubscriptionPrice();
        subscriptionPrice2.setId(subscriptionPrice1.getId());
        assertThat(subscriptionPrice1).isEqualTo(subscriptionPrice2);
        subscriptionPrice2.setId(2L);
        assertThat(subscriptionPrice1).isNotEqualTo(subscriptionPrice2);
        subscriptionPrice1.setId(null);
        assertThat(subscriptionPrice1).isNotEqualTo(subscriptionPrice2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SubscriptionPriceDTO.class);
        SubscriptionPriceDTO subscriptionPriceDTO1 = new SubscriptionPriceDTO();
        subscriptionPriceDTO1.setId(1L);
        SubscriptionPriceDTO subscriptionPriceDTO2 = new SubscriptionPriceDTO();
        assertThat(subscriptionPriceDTO1).isNotEqualTo(subscriptionPriceDTO2);
        subscriptionPriceDTO2.setId(subscriptionPriceDTO1.getId());
        assertThat(subscriptionPriceDTO1).isEqualTo(subscriptionPriceDTO2);
        subscriptionPriceDTO2.setId(2L);
        assertThat(subscriptionPriceDTO1).isNotEqualTo(subscriptionPriceDTO2);
        subscriptionPriceDTO1.setId(null);
        assertThat(subscriptionPriceDTO1).isNotEqualTo(subscriptionPriceDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(subscriptionPriceMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(subscriptionPriceMapper.fromId(null)).isNull();
    }
}
