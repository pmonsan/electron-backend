package com.electron.mfs.pg.subscription.web.rest;

import com.electron.mfs.pg.subscription.SubscriptionManagerApp;
import com.electron.mfs.pg.subscription.config.SecurityBeanOverrideConfiguration;
import com.electron.mfs.pg.subscription.domain.CustomerSubscription;
import com.electron.mfs.pg.subscription.repository.CustomerSubscriptionRepository;
import com.electron.mfs.pg.subscription.service.CustomerSubscriptionService;
import com.electron.mfs.pg.subscription.service.dto.CustomerSubscriptionDTO;
import com.electron.mfs.pg.subscription.service.mapper.CustomerSubscriptionMapper;
import com.electron.mfs.pg.subscription.web.rest.errors.ExceptionTranslator;

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

import static com.electron.mfs.pg.subscription.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link CustomerSubscriptionResource} REST controller.
 */
@EmbeddedKafka
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, SubscriptionManagerApp.class})
public class CustomerSubscriptionResourceIT {

    private static final String DEFAULT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_NUMBER = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_IS_MERCHANT_SUBSCRIPTION = false;
    private static final Boolean UPDATED_IS_MERCHANT_SUBSCRIPTION = true;

    private static final Instant DEFAULT_MODIFICATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MODIFICATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_VALIDATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALIDATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_FILENAME = "AAAAAAAAAA";
    private static final String UPDATED_FILENAME = "BBBBBBBBBB";

    private static final String DEFAULT_CUSTOMER_CODE = "AAAAA";
    private static final String UPDATED_CUSTOMER_CODE = "BBBBB";

    private static final String DEFAULT_SERVICE_CODE = "AAAAA";
    private static final String UPDATED_SERVICE_CODE = "BBBBB";

    private static final String DEFAULT_ACCOUNT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_NUMBER = "BBBBBBBBBB";

    private static final Instant DEFAULT_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private CustomerSubscriptionRepository customerSubscriptionRepository;

    @Autowired
    private CustomerSubscriptionMapper customerSubscriptionMapper;

    @Autowired
    private CustomerSubscriptionService customerSubscriptionService;

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

    private MockMvc restCustomerSubscriptionMockMvc;

    private CustomerSubscription customerSubscription;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CustomerSubscriptionResource customerSubscriptionResource = new CustomerSubscriptionResource(customerSubscriptionService);
        this.restCustomerSubscriptionMockMvc = MockMvcBuilders.standaloneSetup(customerSubscriptionResource)
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
    public static CustomerSubscription createEntity(EntityManager em) {
        CustomerSubscription customerSubscription = new CustomerSubscription()
            .number(DEFAULT_NUMBER)
            .creationDate(DEFAULT_CREATION_DATE)
            .isMerchantSubscription(DEFAULT_IS_MERCHANT_SUBSCRIPTION)
            .modificationDate(DEFAULT_MODIFICATION_DATE)
            .validationDate(DEFAULT_VALIDATION_DATE)
            .filename(DEFAULT_FILENAME)
            .customerCode(DEFAULT_CUSTOMER_CODE)
            .serviceCode(DEFAULT_SERVICE_CODE)
            .accountNumber(DEFAULT_ACCOUNT_NUMBER)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .active(DEFAULT_ACTIVE);
        return customerSubscription;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomerSubscription createUpdatedEntity(EntityManager em) {
        CustomerSubscription customerSubscription = new CustomerSubscription()
            .number(UPDATED_NUMBER)
            .creationDate(UPDATED_CREATION_DATE)
            .isMerchantSubscription(UPDATED_IS_MERCHANT_SUBSCRIPTION)
            .modificationDate(UPDATED_MODIFICATION_DATE)
            .validationDate(UPDATED_VALIDATION_DATE)
            .filename(UPDATED_FILENAME)
            .customerCode(UPDATED_CUSTOMER_CODE)
            .serviceCode(UPDATED_SERVICE_CODE)
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .active(UPDATED_ACTIVE);
        return customerSubscription;
    }

    @BeforeEach
    public void initTest() {
        customerSubscription = createEntity(em);
    }

    @Test
    @Transactional
    public void createCustomerSubscription() throws Exception {
        int databaseSizeBeforeCreate = customerSubscriptionRepository.findAll().size();

        // Create the CustomerSubscription
        CustomerSubscriptionDTO customerSubscriptionDTO = customerSubscriptionMapper.toDto(customerSubscription);
        restCustomerSubscriptionMockMvc.perform(post("/api/customer-subscriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerSubscriptionDTO)))
            .andExpect(status().isCreated());

        // Validate the CustomerSubscription in the database
        List<CustomerSubscription> customerSubscriptionList = customerSubscriptionRepository.findAll();
        assertThat(customerSubscriptionList).hasSize(databaseSizeBeforeCreate + 1);
        CustomerSubscription testCustomerSubscription = customerSubscriptionList.get(customerSubscriptionList.size() - 1);
        assertThat(testCustomerSubscription.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testCustomerSubscription.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testCustomerSubscription.isIsMerchantSubscription()).isEqualTo(DEFAULT_IS_MERCHANT_SUBSCRIPTION);
        assertThat(testCustomerSubscription.getModificationDate()).isEqualTo(DEFAULT_MODIFICATION_DATE);
        assertThat(testCustomerSubscription.getValidationDate()).isEqualTo(DEFAULT_VALIDATION_DATE);
        assertThat(testCustomerSubscription.getFilename()).isEqualTo(DEFAULT_FILENAME);
        assertThat(testCustomerSubscription.getCustomerCode()).isEqualTo(DEFAULT_CUSTOMER_CODE);
        assertThat(testCustomerSubscription.getServiceCode()).isEqualTo(DEFAULT_SERVICE_CODE);
        assertThat(testCustomerSubscription.getAccountNumber()).isEqualTo(DEFAULT_ACCOUNT_NUMBER);
        assertThat(testCustomerSubscription.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testCustomerSubscription.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testCustomerSubscription.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createCustomerSubscriptionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = customerSubscriptionRepository.findAll().size();

        // Create the CustomerSubscription with an existing ID
        customerSubscription.setId(1L);
        CustomerSubscriptionDTO customerSubscriptionDTO = customerSubscriptionMapper.toDto(customerSubscription);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomerSubscriptionMockMvc.perform(post("/api/customer-subscriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerSubscriptionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CustomerSubscription in the database
        List<CustomerSubscription> customerSubscriptionList = customerSubscriptionRepository.findAll();
        assertThat(customerSubscriptionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCreationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerSubscriptionRepository.findAll().size();
        // set the field null
        customerSubscription.setCreationDate(null);

        // Create the CustomerSubscription, which fails.
        CustomerSubscriptionDTO customerSubscriptionDTO = customerSubscriptionMapper.toDto(customerSubscription);

        restCustomerSubscriptionMockMvc.perform(post("/api/customer-subscriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerSubscriptionDTO)))
            .andExpect(status().isBadRequest());

        List<CustomerSubscription> customerSubscriptionList = customerSubscriptionRepository.findAll();
        assertThat(customerSubscriptionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsMerchantSubscriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerSubscriptionRepository.findAll().size();
        // set the field null
        customerSubscription.setIsMerchantSubscription(null);

        // Create the CustomerSubscription, which fails.
        CustomerSubscriptionDTO customerSubscriptionDTO = customerSubscriptionMapper.toDto(customerSubscription);

        restCustomerSubscriptionMockMvc.perform(post("/api/customer-subscriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerSubscriptionDTO)))
            .andExpect(status().isBadRequest());

        List<CustomerSubscription> customerSubscriptionList = customerSubscriptionRepository.findAll();
        assertThat(customerSubscriptionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModificationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerSubscriptionRepository.findAll().size();
        // set the field null
        customerSubscription.setModificationDate(null);

        // Create the CustomerSubscription, which fails.
        CustomerSubscriptionDTO customerSubscriptionDTO = customerSubscriptionMapper.toDto(customerSubscription);

        restCustomerSubscriptionMockMvc.perform(post("/api/customer-subscriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerSubscriptionDTO)))
            .andExpect(status().isBadRequest());

        List<CustomerSubscription> customerSubscriptionList = customerSubscriptionRepository.findAll();
        assertThat(customerSubscriptionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFilenameIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerSubscriptionRepository.findAll().size();
        // set the field null
        customerSubscription.setFilename(null);

        // Create the CustomerSubscription, which fails.
        CustomerSubscriptionDTO customerSubscriptionDTO = customerSubscriptionMapper.toDto(customerSubscription);

        restCustomerSubscriptionMockMvc.perform(post("/api/customer-subscriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerSubscriptionDTO)))
            .andExpect(status().isBadRequest());

        List<CustomerSubscription> customerSubscriptionList = customerSubscriptionRepository.findAll();
        assertThat(customerSubscriptionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCustomerCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerSubscriptionRepository.findAll().size();
        // set the field null
        customerSubscription.setCustomerCode(null);

        // Create the CustomerSubscription, which fails.
        CustomerSubscriptionDTO customerSubscriptionDTO = customerSubscriptionMapper.toDto(customerSubscription);

        restCustomerSubscriptionMockMvc.perform(post("/api/customer-subscriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerSubscriptionDTO)))
            .andExpect(status().isBadRequest());

        List<CustomerSubscription> customerSubscriptionList = customerSubscriptionRepository.findAll();
        assertThat(customerSubscriptionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkServiceCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerSubscriptionRepository.findAll().size();
        // set the field null
        customerSubscription.setServiceCode(null);

        // Create the CustomerSubscription, which fails.
        CustomerSubscriptionDTO customerSubscriptionDTO = customerSubscriptionMapper.toDto(customerSubscription);

        restCustomerSubscriptionMockMvc.perform(post("/api/customer-subscriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerSubscriptionDTO)))
            .andExpect(status().isBadRequest());

        List<CustomerSubscription> customerSubscriptionList = customerSubscriptionRepository.findAll();
        assertThat(customerSubscriptionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerSubscriptionRepository.findAll().size();
        // set the field null
        customerSubscription.setStartDate(null);

        // Create the CustomerSubscription, which fails.
        CustomerSubscriptionDTO customerSubscriptionDTO = customerSubscriptionMapper.toDto(customerSubscription);

        restCustomerSubscriptionMockMvc.perform(post("/api/customer-subscriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerSubscriptionDTO)))
            .andExpect(status().isBadRequest());

        List<CustomerSubscription> customerSubscriptionList = customerSubscriptionRepository.findAll();
        assertThat(customerSubscriptionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEndDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerSubscriptionRepository.findAll().size();
        // set the field null
        customerSubscription.setEndDate(null);

        // Create the CustomerSubscription, which fails.
        CustomerSubscriptionDTO customerSubscriptionDTO = customerSubscriptionMapper.toDto(customerSubscription);

        restCustomerSubscriptionMockMvc.perform(post("/api/customer-subscriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerSubscriptionDTO)))
            .andExpect(status().isBadRequest());

        List<CustomerSubscription> customerSubscriptionList = customerSubscriptionRepository.findAll();
        assertThat(customerSubscriptionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerSubscriptionRepository.findAll().size();
        // set the field null
        customerSubscription.setActive(null);

        // Create the CustomerSubscription, which fails.
        CustomerSubscriptionDTO customerSubscriptionDTO = customerSubscriptionMapper.toDto(customerSubscription);

        restCustomerSubscriptionMockMvc.perform(post("/api/customer-subscriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerSubscriptionDTO)))
            .andExpect(status().isBadRequest());

        List<CustomerSubscription> customerSubscriptionList = customerSubscriptionRepository.findAll();
        assertThat(customerSubscriptionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCustomerSubscriptions() throws Exception {
        // Initialize the database
        customerSubscriptionRepository.saveAndFlush(customerSubscription);

        // Get all the customerSubscriptionList
        restCustomerSubscriptionMockMvc.perform(get("/api/customer-subscriptions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerSubscription.getId().intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].isMerchantSubscription").value(hasItem(DEFAULT_IS_MERCHANT_SUBSCRIPTION.booleanValue())))
            .andExpect(jsonPath("$.[*].modificationDate").value(hasItem(DEFAULT_MODIFICATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].validationDate").value(hasItem(DEFAULT_VALIDATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].filename").value(hasItem(DEFAULT_FILENAME.toString())))
            .andExpect(jsonPath("$.[*].customerCode").value(hasItem(DEFAULT_CUSTOMER_CODE.toString())))
            .andExpect(jsonPath("$.[*].serviceCode").value(hasItem(DEFAULT_SERVICE_CODE.toString())))
            .andExpect(jsonPath("$.[*].accountNumber").value(hasItem(DEFAULT_ACCOUNT_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCustomerSubscription() throws Exception {
        // Initialize the database
        customerSubscriptionRepository.saveAndFlush(customerSubscription);

        // Get the customerSubscription
        restCustomerSubscriptionMockMvc.perform(get("/api/customer-subscriptions/{id}", customerSubscription.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(customerSubscription.getId().intValue()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER.toString()))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.isMerchantSubscription").value(DEFAULT_IS_MERCHANT_SUBSCRIPTION.booleanValue()))
            .andExpect(jsonPath("$.modificationDate").value(DEFAULT_MODIFICATION_DATE.toString()))
            .andExpect(jsonPath("$.validationDate").value(DEFAULT_VALIDATION_DATE.toString()))
            .andExpect(jsonPath("$.filename").value(DEFAULT_FILENAME.toString()))
            .andExpect(jsonPath("$.customerCode").value(DEFAULT_CUSTOMER_CODE.toString()))
            .andExpect(jsonPath("$.serviceCode").value(DEFAULT_SERVICE_CODE.toString()))
            .andExpect(jsonPath("$.accountNumber").value(DEFAULT_ACCOUNT_NUMBER.toString()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCustomerSubscription() throws Exception {
        // Get the customerSubscription
        restCustomerSubscriptionMockMvc.perform(get("/api/customer-subscriptions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomerSubscription() throws Exception {
        // Initialize the database
        customerSubscriptionRepository.saveAndFlush(customerSubscription);

        int databaseSizeBeforeUpdate = customerSubscriptionRepository.findAll().size();

        // Update the customerSubscription
        CustomerSubscription updatedCustomerSubscription = customerSubscriptionRepository.findById(customerSubscription.getId()).get();
        // Disconnect from session so that the updates on updatedCustomerSubscription are not directly saved in db
        em.detach(updatedCustomerSubscription);
        updatedCustomerSubscription
            .number(UPDATED_NUMBER)
            .creationDate(UPDATED_CREATION_DATE)
            .isMerchantSubscription(UPDATED_IS_MERCHANT_SUBSCRIPTION)
            .modificationDate(UPDATED_MODIFICATION_DATE)
            .validationDate(UPDATED_VALIDATION_DATE)
            .filename(UPDATED_FILENAME)
            .customerCode(UPDATED_CUSTOMER_CODE)
            .serviceCode(UPDATED_SERVICE_CODE)
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .active(UPDATED_ACTIVE);
        CustomerSubscriptionDTO customerSubscriptionDTO = customerSubscriptionMapper.toDto(updatedCustomerSubscription);

        restCustomerSubscriptionMockMvc.perform(put("/api/customer-subscriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerSubscriptionDTO)))
            .andExpect(status().isOk());

        // Validate the CustomerSubscription in the database
        List<CustomerSubscription> customerSubscriptionList = customerSubscriptionRepository.findAll();
        assertThat(customerSubscriptionList).hasSize(databaseSizeBeforeUpdate);
        CustomerSubscription testCustomerSubscription = customerSubscriptionList.get(customerSubscriptionList.size() - 1);
        assertThat(testCustomerSubscription.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testCustomerSubscription.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testCustomerSubscription.isIsMerchantSubscription()).isEqualTo(UPDATED_IS_MERCHANT_SUBSCRIPTION);
        assertThat(testCustomerSubscription.getModificationDate()).isEqualTo(UPDATED_MODIFICATION_DATE);
        assertThat(testCustomerSubscription.getValidationDate()).isEqualTo(UPDATED_VALIDATION_DATE);
        assertThat(testCustomerSubscription.getFilename()).isEqualTo(UPDATED_FILENAME);
        assertThat(testCustomerSubscription.getCustomerCode()).isEqualTo(UPDATED_CUSTOMER_CODE);
        assertThat(testCustomerSubscription.getServiceCode()).isEqualTo(UPDATED_SERVICE_CODE);
        assertThat(testCustomerSubscription.getAccountNumber()).isEqualTo(UPDATED_ACCOUNT_NUMBER);
        assertThat(testCustomerSubscription.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testCustomerSubscription.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testCustomerSubscription.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingCustomerSubscription() throws Exception {
        int databaseSizeBeforeUpdate = customerSubscriptionRepository.findAll().size();

        // Create the CustomerSubscription
        CustomerSubscriptionDTO customerSubscriptionDTO = customerSubscriptionMapper.toDto(customerSubscription);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomerSubscriptionMockMvc.perform(put("/api/customer-subscriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerSubscriptionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CustomerSubscription in the database
        List<CustomerSubscription> customerSubscriptionList = customerSubscriptionRepository.findAll();
        assertThat(customerSubscriptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCustomerSubscription() throws Exception {
        // Initialize the database
        customerSubscriptionRepository.saveAndFlush(customerSubscription);

        int databaseSizeBeforeDelete = customerSubscriptionRepository.findAll().size();

        // Delete the customerSubscription
        restCustomerSubscriptionMockMvc.perform(delete("/api/customer-subscriptions/{id}", customerSubscription.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CustomerSubscription> customerSubscriptionList = customerSubscriptionRepository.findAll();
        assertThat(customerSubscriptionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerSubscription.class);
        CustomerSubscription customerSubscription1 = new CustomerSubscription();
        customerSubscription1.setId(1L);
        CustomerSubscription customerSubscription2 = new CustomerSubscription();
        customerSubscription2.setId(customerSubscription1.getId());
        assertThat(customerSubscription1).isEqualTo(customerSubscription2);
        customerSubscription2.setId(2L);
        assertThat(customerSubscription1).isNotEqualTo(customerSubscription2);
        customerSubscription1.setId(null);
        assertThat(customerSubscription1).isNotEqualTo(customerSubscription2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerSubscriptionDTO.class);
        CustomerSubscriptionDTO customerSubscriptionDTO1 = new CustomerSubscriptionDTO();
        customerSubscriptionDTO1.setId(1L);
        CustomerSubscriptionDTO customerSubscriptionDTO2 = new CustomerSubscriptionDTO();
        assertThat(customerSubscriptionDTO1).isNotEqualTo(customerSubscriptionDTO2);
        customerSubscriptionDTO2.setId(customerSubscriptionDTO1.getId());
        assertThat(customerSubscriptionDTO1).isEqualTo(customerSubscriptionDTO2);
        customerSubscriptionDTO2.setId(2L);
        assertThat(customerSubscriptionDTO1).isNotEqualTo(customerSubscriptionDTO2);
        customerSubscriptionDTO1.setId(null);
        assertThat(customerSubscriptionDTO1).isNotEqualTo(customerSubscriptionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(customerSubscriptionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(customerSubscriptionMapper.fromId(null)).isNull();
    }
}
