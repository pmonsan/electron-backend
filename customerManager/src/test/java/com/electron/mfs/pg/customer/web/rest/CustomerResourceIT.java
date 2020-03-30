package com.electron.mfs.pg.customer.web.rest;

import com.electron.mfs.pg.customer.CustomerManagerApp;
import com.electron.mfs.pg.customer.config.SecurityBeanOverrideConfiguration;
import com.electron.mfs.pg.customer.domain.Customer;
import com.electron.mfs.pg.customer.repository.CustomerRepository;
import com.electron.mfs.pg.customer.service.CustomerService;
import com.electron.mfs.pg.customer.service.dto.CustomerDTO;
import com.electron.mfs.pg.customer.service.mapper.CustomerMapper;
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
 * Integration tests for the {@Link CustomerResource} REST controller.
 */
@EmbeddedKafka
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, CustomerManagerApp.class})
public class CustomerResourceIT {

    private static final String DEFAULT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_CORPORATE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CORPORATE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FIRSTNAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRSTNAME = "BBBBBBBBBB";

    private static final String DEFAULT_LASTNAME = "AAAAAAAAAA";
    private static final String UPDATED_LASTNAME = "BBBBBBBBBB";

    private static final Double DEFAULT_GPS_LATITUDE = 0D;
    private static final Double UPDATED_GPS_LATITUDE = 1D;

    private static final Double DEFAULT_GPS_LONGITUDE = 0D;
    private static final Double UPDATED_GPS_LONGITUDE = 1D;

    private static final String DEFAULT_HOME_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_HOME_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_MOBILE_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_MOBILE_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_WORK_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_WORK_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_OTHER_QUESTION = "AAAAAAAAAA";
    private static final String UPDATED_OTHER_QUESTION = "BBBBBBBBBB";

    private static final String DEFAULT_RESPONSE_OF_QUESTION = "AAAAAAAAAA";
    private static final String UPDATED_RESPONSE_OF_QUESTION = "BBBBBBBBBB";

    private static final String DEFAULT_TRADE_REGISTER = "AAAAAAAAAA";
    private static final String UPDATED_TRADE_REGISTER = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_POSTAL_CODE = "AAAAAAAAAA";
    private static final String UPDATED_POSTAL_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "u@tx.(X";
    private static final String UPDATED_EMAIL = "*8@'.H";

    private static final String DEFAULT_COUNTRY_CODE = "AAAAA";
    private static final String UPDATED_COUNTRY_CODE = "BBBBB";

    private static final String DEFAULT_PARTNER_CODE = "AAAAAAAAAA";
    private static final String UPDATED_PARTNER_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_ACTIVITY_AREA_CODE = "AAAAA";
    private static final String UPDATED_ACTIVITY_AREA_CODE = "BBBBB";

    private static final String DEFAULT_CUSTOMER_TYPE_CODE = "AAAAA";
    private static final String UPDATED_CUSTOMER_TYPE_CODE = "BBBBB";

    private static final String DEFAULT_QUESTION_CODE = "AAAAA";
    private static final String UPDATED_QUESTION_CODE = "BBBBB";

    private static final String DEFAULT_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_USERNAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private CustomerService customerService;

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

    private MockMvc restCustomerMockMvc;

    private Customer customer;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CustomerResource customerResource = new CustomerResource(customerService);
        this.restCustomerMockMvc = MockMvcBuilders.standaloneSetup(customerResource)
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
    public static Customer createEntity(EntityManager em) {
        Customer customer = new Customer()
            .number(DEFAULT_NUMBER)
            .corporateName(DEFAULT_CORPORATE_NAME)
            .firstname(DEFAULT_FIRSTNAME)
            .lastname(DEFAULT_LASTNAME)
            .gpsLatitude(DEFAULT_GPS_LATITUDE)
            .gpsLongitude(DEFAULT_GPS_LONGITUDE)
            .homePhone(DEFAULT_HOME_PHONE)
            .mobilePhone(DEFAULT_MOBILE_PHONE)
            .workPhone(DEFAULT_WORK_PHONE)
            .otherQuestion(DEFAULT_OTHER_QUESTION)
            .responseOfQuestion(DEFAULT_RESPONSE_OF_QUESTION)
            .tradeRegister(DEFAULT_TRADE_REGISTER)
            .address(DEFAULT_ADDRESS)
            .postalCode(DEFAULT_POSTAL_CODE)
            .city(DEFAULT_CITY)
            .email(DEFAULT_EMAIL)
            .countryCode(DEFAULT_COUNTRY_CODE)
            .partnerCode(DEFAULT_PARTNER_CODE)
            .activityAreaCode(DEFAULT_ACTIVITY_AREA_CODE)
            .customerTypeCode(DEFAULT_CUSTOMER_TYPE_CODE)
            .questionCode(DEFAULT_QUESTION_CODE)
            .username(DEFAULT_USERNAME)
            .active(DEFAULT_ACTIVE);
        return customer;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Customer createUpdatedEntity(EntityManager em) {
        Customer customer = new Customer()
            .number(UPDATED_NUMBER)
            .corporateName(UPDATED_CORPORATE_NAME)
            .firstname(UPDATED_FIRSTNAME)
            .lastname(UPDATED_LASTNAME)
            .gpsLatitude(UPDATED_GPS_LATITUDE)
            .gpsLongitude(UPDATED_GPS_LONGITUDE)
            .homePhone(UPDATED_HOME_PHONE)
            .mobilePhone(UPDATED_MOBILE_PHONE)
            .workPhone(UPDATED_WORK_PHONE)
            .otherQuestion(UPDATED_OTHER_QUESTION)
            .responseOfQuestion(UPDATED_RESPONSE_OF_QUESTION)
            .tradeRegister(UPDATED_TRADE_REGISTER)
            .address(UPDATED_ADDRESS)
            .postalCode(UPDATED_POSTAL_CODE)
            .city(UPDATED_CITY)
            .email(UPDATED_EMAIL)
            .countryCode(UPDATED_COUNTRY_CODE)
            .partnerCode(UPDATED_PARTNER_CODE)
            .activityAreaCode(UPDATED_ACTIVITY_AREA_CODE)
            .customerTypeCode(UPDATED_CUSTOMER_TYPE_CODE)
            .questionCode(UPDATED_QUESTION_CODE)
            .username(UPDATED_USERNAME)
            .active(UPDATED_ACTIVE);
        return customer;
    }

    @BeforeEach
    public void initTest() {
        customer = createEntity(em);
    }

    @Test
    @Transactional
    public void createCustomer() throws Exception {
        int databaseSizeBeforeCreate = customerRepository.findAll().size();

        // Create the Customer
        CustomerDTO customerDTO = customerMapper.toDto(customer);
        restCustomerMockMvc.perform(post("/api/customers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerDTO)))
            .andExpect(status().isCreated());

        // Validate the Customer in the database
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeCreate + 1);
        Customer testCustomer = customerList.get(customerList.size() - 1);
        assertThat(testCustomer.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testCustomer.getCorporateName()).isEqualTo(DEFAULT_CORPORATE_NAME);
        assertThat(testCustomer.getFirstname()).isEqualTo(DEFAULT_FIRSTNAME);
        assertThat(testCustomer.getLastname()).isEqualTo(DEFAULT_LASTNAME);
        assertThat(testCustomer.getGpsLatitude()).isEqualTo(DEFAULT_GPS_LATITUDE);
        assertThat(testCustomer.getGpsLongitude()).isEqualTo(DEFAULT_GPS_LONGITUDE);
        assertThat(testCustomer.getHomePhone()).isEqualTo(DEFAULT_HOME_PHONE);
        assertThat(testCustomer.getMobilePhone()).isEqualTo(DEFAULT_MOBILE_PHONE);
        assertThat(testCustomer.getWorkPhone()).isEqualTo(DEFAULT_WORK_PHONE);
        assertThat(testCustomer.getOtherQuestion()).isEqualTo(DEFAULT_OTHER_QUESTION);
        assertThat(testCustomer.getResponseOfQuestion()).isEqualTo(DEFAULT_RESPONSE_OF_QUESTION);
        assertThat(testCustomer.getTradeRegister()).isEqualTo(DEFAULT_TRADE_REGISTER);
        assertThat(testCustomer.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testCustomer.getPostalCode()).isEqualTo(DEFAULT_POSTAL_CODE);
        assertThat(testCustomer.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testCustomer.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testCustomer.getCountryCode()).isEqualTo(DEFAULT_COUNTRY_CODE);
        assertThat(testCustomer.getPartnerCode()).isEqualTo(DEFAULT_PARTNER_CODE);
        assertThat(testCustomer.getActivityAreaCode()).isEqualTo(DEFAULT_ACTIVITY_AREA_CODE);
        assertThat(testCustomer.getCustomerTypeCode()).isEqualTo(DEFAULT_CUSTOMER_TYPE_CODE);
        assertThat(testCustomer.getQuestionCode()).isEqualTo(DEFAULT_QUESTION_CODE);
        assertThat(testCustomer.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testCustomer.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createCustomerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = customerRepository.findAll().size();

        // Create the Customer with an existing ID
        customer.setId(1L);
        CustomerDTO customerDTO = customerMapper.toDto(customer);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomerMockMvc.perform(post("/api/customers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Customer in the database
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCountryCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerRepository.findAll().size();
        // set the field null
        customer.setCountryCode(null);

        // Create the Customer, which fails.
        CustomerDTO customerDTO = customerMapper.toDto(customer);

        restCustomerMockMvc.perform(post("/api/customers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerDTO)))
            .andExpect(status().isBadRequest());

        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPartnerCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerRepository.findAll().size();
        // set the field null
        customer.setPartnerCode(null);

        // Create the Customer, which fails.
        CustomerDTO customerDTO = customerMapper.toDto(customer);

        restCustomerMockMvc.perform(post("/api/customers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerDTO)))
            .andExpect(status().isBadRequest());

        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActivityAreaCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerRepository.findAll().size();
        // set the field null
        customer.setActivityAreaCode(null);

        // Create the Customer, which fails.
        CustomerDTO customerDTO = customerMapper.toDto(customer);

        restCustomerMockMvc.perform(post("/api/customers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerDTO)))
            .andExpect(status().isBadRequest());

        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCustomerTypeCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerRepository.findAll().size();
        // set the field null
        customer.setCustomerTypeCode(null);

        // Create the Customer, which fails.
        CustomerDTO customerDTO = customerMapper.toDto(customer);

        restCustomerMockMvc.perform(post("/api/customers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerDTO)))
            .andExpect(status().isBadRequest());

        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQuestionCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerRepository.findAll().size();
        // set the field null
        customer.setQuestionCode(null);

        // Create the Customer, which fails.
        CustomerDTO customerDTO = customerMapper.toDto(customer);

        restCustomerMockMvc.perform(post("/api/customers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerDTO)))
            .andExpect(status().isBadRequest());

        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerRepository.findAll().size();
        // set the field null
        customer.setActive(null);

        // Create the Customer, which fails.
        CustomerDTO customerDTO = customerMapper.toDto(customer);

        restCustomerMockMvc.perform(post("/api/customers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerDTO)))
            .andExpect(status().isBadRequest());

        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCustomers() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList
        restCustomerMockMvc.perform(get("/api/customers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customer.getId().intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].corporateName").value(hasItem(DEFAULT_CORPORATE_NAME.toString())))
            .andExpect(jsonPath("$.[*].firstname").value(hasItem(DEFAULT_FIRSTNAME.toString())))
            .andExpect(jsonPath("$.[*].lastname").value(hasItem(DEFAULT_LASTNAME.toString())))
            .andExpect(jsonPath("$.[*].gpsLatitude").value(hasItem(DEFAULT_GPS_LATITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].gpsLongitude").value(hasItem(DEFAULT_GPS_LONGITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].homePhone").value(hasItem(DEFAULT_HOME_PHONE.toString())))
            .andExpect(jsonPath("$.[*].mobilePhone").value(hasItem(DEFAULT_MOBILE_PHONE.toString())))
            .andExpect(jsonPath("$.[*].workPhone").value(hasItem(DEFAULT_WORK_PHONE.toString())))
            .andExpect(jsonPath("$.[*].otherQuestion").value(hasItem(DEFAULT_OTHER_QUESTION.toString())))
            .andExpect(jsonPath("$.[*].responseOfQuestion").value(hasItem(DEFAULT_RESPONSE_OF_QUESTION.toString())))
            .andExpect(jsonPath("$.[*].tradeRegister").value(hasItem(DEFAULT_TRADE_REGISTER.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].postalCode").value(hasItem(DEFAULT_POSTAL_CODE.toString())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].countryCode").value(hasItem(DEFAULT_COUNTRY_CODE.toString())))
            .andExpect(jsonPath("$.[*].partnerCode").value(hasItem(DEFAULT_PARTNER_CODE.toString())))
            .andExpect(jsonPath("$.[*].activityAreaCode").value(hasItem(DEFAULT_ACTIVITY_AREA_CODE.toString())))
            .andExpect(jsonPath("$.[*].customerTypeCode").value(hasItem(DEFAULT_CUSTOMER_TYPE_CODE.toString())))
            .andExpect(jsonPath("$.[*].questionCode").value(hasItem(DEFAULT_QUESTION_CODE.toString())))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCustomer() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get the customer
        restCustomerMockMvc.perform(get("/api/customers/{id}", customer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(customer.getId().intValue()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER.toString()))
            .andExpect(jsonPath("$.corporateName").value(DEFAULT_CORPORATE_NAME.toString()))
            .andExpect(jsonPath("$.firstname").value(DEFAULT_FIRSTNAME.toString()))
            .andExpect(jsonPath("$.lastname").value(DEFAULT_LASTNAME.toString()))
            .andExpect(jsonPath("$.gpsLatitude").value(DEFAULT_GPS_LATITUDE.doubleValue()))
            .andExpect(jsonPath("$.gpsLongitude").value(DEFAULT_GPS_LONGITUDE.doubleValue()))
            .andExpect(jsonPath("$.homePhone").value(DEFAULT_HOME_PHONE.toString()))
            .andExpect(jsonPath("$.mobilePhone").value(DEFAULT_MOBILE_PHONE.toString()))
            .andExpect(jsonPath("$.workPhone").value(DEFAULT_WORK_PHONE.toString()))
            .andExpect(jsonPath("$.otherQuestion").value(DEFAULT_OTHER_QUESTION.toString()))
            .andExpect(jsonPath("$.responseOfQuestion").value(DEFAULT_RESPONSE_OF_QUESTION.toString()))
            .andExpect(jsonPath("$.tradeRegister").value(DEFAULT_TRADE_REGISTER.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.postalCode").value(DEFAULT_POSTAL_CODE.toString()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.countryCode").value(DEFAULT_COUNTRY_CODE.toString()))
            .andExpect(jsonPath("$.partnerCode").value(DEFAULT_PARTNER_CODE.toString()))
            .andExpect(jsonPath("$.activityAreaCode").value(DEFAULT_ACTIVITY_AREA_CODE.toString()))
            .andExpect(jsonPath("$.customerTypeCode").value(DEFAULT_CUSTOMER_TYPE_CODE.toString()))
            .andExpect(jsonPath("$.questionCode").value(DEFAULT_QUESTION_CODE.toString()))
            .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCustomer() throws Exception {
        // Get the customer
        restCustomerMockMvc.perform(get("/api/customers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomer() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        int databaseSizeBeforeUpdate = customerRepository.findAll().size();

        // Update the customer
        Customer updatedCustomer = customerRepository.findById(customer.getId()).get();
        // Disconnect from session so that the updates on updatedCustomer are not directly saved in db
        em.detach(updatedCustomer);
        updatedCustomer
            .number(UPDATED_NUMBER)
            .corporateName(UPDATED_CORPORATE_NAME)
            .firstname(UPDATED_FIRSTNAME)
            .lastname(UPDATED_LASTNAME)
            .gpsLatitude(UPDATED_GPS_LATITUDE)
            .gpsLongitude(UPDATED_GPS_LONGITUDE)
            .homePhone(UPDATED_HOME_PHONE)
            .mobilePhone(UPDATED_MOBILE_PHONE)
            .workPhone(UPDATED_WORK_PHONE)
            .otherQuestion(UPDATED_OTHER_QUESTION)
            .responseOfQuestion(UPDATED_RESPONSE_OF_QUESTION)
            .tradeRegister(UPDATED_TRADE_REGISTER)
            .address(UPDATED_ADDRESS)
            .postalCode(UPDATED_POSTAL_CODE)
            .city(UPDATED_CITY)
            .email(UPDATED_EMAIL)
            .countryCode(UPDATED_COUNTRY_CODE)
            .partnerCode(UPDATED_PARTNER_CODE)
            .activityAreaCode(UPDATED_ACTIVITY_AREA_CODE)
            .customerTypeCode(UPDATED_CUSTOMER_TYPE_CODE)
            .questionCode(UPDATED_QUESTION_CODE)
            .username(UPDATED_USERNAME)
            .active(UPDATED_ACTIVE);
        CustomerDTO customerDTO = customerMapper.toDto(updatedCustomer);

        restCustomerMockMvc.perform(put("/api/customers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerDTO)))
            .andExpect(status().isOk());

        // Validate the Customer in the database
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeUpdate);
        Customer testCustomer = customerList.get(customerList.size() - 1);
        assertThat(testCustomer.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testCustomer.getCorporateName()).isEqualTo(UPDATED_CORPORATE_NAME);
        assertThat(testCustomer.getFirstname()).isEqualTo(UPDATED_FIRSTNAME);
        assertThat(testCustomer.getLastname()).isEqualTo(UPDATED_LASTNAME);
        assertThat(testCustomer.getGpsLatitude()).isEqualTo(UPDATED_GPS_LATITUDE);
        assertThat(testCustomer.getGpsLongitude()).isEqualTo(UPDATED_GPS_LONGITUDE);
        assertThat(testCustomer.getHomePhone()).isEqualTo(UPDATED_HOME_PHONE);
        assertThat(testCustomer.getMobilePhone()).isEqualTo(UPDATED_MOBILE_PHONE);
        assertThat(testCustomer.getWorkPhone()).isEqualTo(UPDATED_WORK_PHONE);
        assertThat(testCustomer.getOtherQuestion()).isEqualTo(UPDATED_OTHER_QUESTION);
        assertThat(testCustomer.getResponseOfQuestion()).isEqualTo(UPDATED_RESPONSE_OF_QUESTION);
        assertThat(testCustomer.getTradeRegister()).isEqualTo(UPDATED_TRADE_REGISTER);
        assertThat(testCustomer.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testCustomer.getPostalCode()).isEqualTo(UPDATED_POSTAL_CODE);
        assertThat(testCustomer.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testCustomer.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testCustomer.getCountryCode()).isEqualTo(UPDATED_COUNTRY_CODE);
        assertThat(testCustomer.getPartnerCode()).isEqualTo(UPDATED_PARTNER_CODE);
        assertThat(testCustomer.getActivityAreaCode()).isEqualTo(UPDATED_ACTIVITY_AREA_CODE);
        assertThat(testCustomer.getCustomerTypeCode()).isEqualTo(UPDATED_CUSTOMER_TYPE_CODE);
        assertThat(testCustomer.getQuestionCode()).isEqualTo(UPDATED_QUESTION_CODE);
        assertThat(testCustomer.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testCustomer.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingCustomer() throws Exception {
        int databaseSizeBeforeUpdate = customerRepository.findAll().size();

        // Create the Customer
        CustomerDTO customerDTO = customerMapper.toDto(customer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomerMockMvc.perform(put("/api/customers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Customer in the database
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCustomer() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        int databaseSizeBeforeDelete = customerRepository.findAll().size();

        // Delete the customer
        restCustomerMockMvc.perform(delete("/api/customers/{id}", customer.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Customer.class);
        Customer customer1 = new Customer();
        customer1.setId(1L);
        Customer customer2 = new Customer();
        customer2.setId(customer1.getId());
        assertThat(customer1).isEqualTo(customer2);
        customer2.setId(2L);
        assertThat(customer1).isNotEqualTo(customer2);
        customer1.setId(null);
        assertThat(customer1).isNotEqualTo(customer2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerDTO.class);
        CustomerDTO customerDTO1 = new CustomerDTO();
        customerDTO1.setId(1L);
        CustomerDTO customerDTO2 = new CustomerDTO();
        assertThat(customerDTO1).isNotEqualTo(customerDTO2);
        customerDTO2.setId(customerDTO1.getId());
        assertThat(customerDTO1).isEqualTo(customerDTO2);
        customerDTO2.setId(2L);
        assertThat(customerDTO1).isNotEqualTo(customerDTO2);
        customerDTO1.setId(null);
        assertThat(customerDTO1).isNotEqualTo(customerDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(customerMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(customerMapper.fromId(null)).isNull();
    }
}
