package com.electron.mfs.pg.gateway.web.rest;

import com.electron.mfs.pg.gateway.PgkernelgatewayApp;
import com.electron.mfs.pg.gateway.config.SecurityBeanOverrideConfiguration;
import com.electron.mfs.pg.gateway.domain.CustomerLimit;
import com.electron.mfs.pg.gateway.repository.CustomerLimitRepository;
import com.electron.mfs.pg.gateway.service.CustomerLimitService;
import com.electron.mfs.pg.gateway.service.dto.CustomerLimitDTO;
import com.electron.mfs.pg.gateway.service.mapper.CustomerLimitMapper;
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
 * Integration tests for the {@Link CustomerLimitResource} REST controller.
 */
@EmbeddedKafka
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, PgkernelgatewayApp.class})
public class CustomerLimitResourceIT {

    private static final String DEFAULT_LIMIT_TYPE_CODE = "AAAAA";
    private static final String UPDATED_LIMIT_TYPE_CODE = "BBBBB";

    private static final String DEFAULT_ACCOUNT_TYPE_CODE = "AAAAA";
    private static final String UPDATED_ACCOUNT_TYPE_CODE = "BBBBB";

    private static final String DEFAULT_CUSTOMER_TYPE_CODE = "AAAAA";
    private static final String UPDATED_CUSTOMER_TYPE_CODE = "BBBBB";

    private static final Double DEFAULT_VALUE = 1D;
    private static final Double UPDATED_VALUE = 2D;

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private CustomerLimitRepository customerLimitRepository;

    @Autowired
    private CustomerLimitMapper customerLimitMapper;

    @Autowired
    private CustomerLimitService customerLimitService;

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

    private MockMvc restCustomerLimitMockMvc;

    private CustomerLimit customerLimit;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CustomerLimitResource customerLimitResource = new CustomerLimitResource(customerLimitService);
        this.restCustomerLimitMockMvc = MockMvcBuilders.standaloneSetup(customerLimitResource)
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
    public static CustomerLimit createEntity(EntityManager em) {
        CustomerLimit customerLimit = new CustomerLimit()
            .limitTypeCode(DEFAULT_LIMIT_TYPE_CODE)
            .accountTypeCode(DEFAULT_ACCOUNT_TYPE_CODE)
            .customerTypeCode(DEFAULT_CUSTOMER_TYPE_CODE)
            .value(DEFAULT_VALUE)
            .comment(DEFAULT_COMMENT)
            .active(DEFAULT_ACTIVE);
        return customerLimit;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomerLimit createUpdatedEntity(EntityManager em) {
        CustomerLimit customerLimit = new CustomerLimit()
            .limitTypeCode(UPDATED_LIMIT_TYPE_CODE)
            .accountTypeCode(UPDATED_ACCOUNT_TYPE_CODE)
            .customerTypeCode(UPDATED_CUSTOMER_TYPE_CODE)
            .value(UPDATED_VALUE)
            .comment(UPDATED_COMMENT)
            .active(UPDATED_ACTIVE);
        return customerLimit;
    }

    @BeforeEach
    public void initTest() {
        customerLimit = createEntity(em);
    }

    @Test
    @Transactional
    public void createCustomerLimit() throws Exception {
        int databaseSizeBeforeCreate = customerLimitRepository.findAll().size();

        // Create the CustomerLimit
        CustomerLimitDTO customerLimitDTO = customerLimitMapper.toDto(customerLimit);
        restCustomerLimitMockMvc.perform(post("/api/customer-limits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerLimitDTO)))
            .andExpect(status().isCreated());

        // Validate the CustomerLimit in the database
        List<CustomerLimit> customerLimitList = customerLimitRepository.findAll();
        assertThat(customerLimitList).hasSize(databaseSizeBeforeCreate + 1);
        CustomerLimit testCustomerLimit = customerLimitList.get(customerLimitList.size() - 1);
        assertThat(testCustomerLimit.getLimitTypeCode()).isEqualTo(DEFAULT_LIMIT_TYPE_CODE);
        assertThat(testCustomerLimit.getAccountTypeCode()).isEqualTo(DEFAULT_ACCOUNT_TYPE_CODE);
        assertThat(testCustomerLimit.getCustomerTypeCode()).isEqualTo(DEFAULT_CUSTOMER_TYPE_CODE);
        assertThat(testCustomerLimit.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testCustomerLimit.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testCustomerLimit.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createCustomerLimitWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = customerLimitRepository.findAll().size();

        // Create the CustomerLimit with an existing ID
        customerLimit.setId(1L);
        CustomerLimitDTO customerLimitDTO = customerLimitMapper.toDto(customerLimit);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomerLimitMockMvc.perform(post("/api/customer-limits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerLimitDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CustomerLimit in the database
        List<CustomerLimit> customerLimitList = customerLimitRepository.findAll();
        assertThat(customerLimitList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkLimitTypeCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerLimitRepository.findAll().size();
        // set the field null
        customerLimit.setLimitTypeCode(null);

        // Create the CustomerLimit, which fails.
        CustomerLimitDTO customerLimitDTO = customerLimitMapper.toDto(customerLimit);

        restCustomerLimitMockMvc.perform(post("/api/customer-limits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerLimitDTO)))
            .andExpect(status().isBadRequest());

        List<CustomerLimit> customerLimitList = customerLimitRepository.findAll();
        assertThat(customerLimitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAccountTypeCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerLimitRepository.findAll().size();
        // set the field null
        customerLimit.setAccountTypeCode(null);

        // Create the CustomerLimit, which fails.
        CustomerLimitDTO customerLimitDTO = customerLimitMapper.toDto(customerLimit);

        restCustomerLimitMockMvc.perform(post("/api/customer-limits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerLimitDTO)))
            .andExpect(status().isBadRequest());

        List<CustomerLimit> customerLimitList = customerLimitRepository.findAll();
        assertThat(customerLimitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCustomerTypeCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerLimitRepository.findAll().size();
        // set the field null
        customerLimit.setCustomerTypeCode(null);

        // Create the CustomerLimit, which fails.
        CustomerLimitDTO customerLimitDTO = customerLimitMapper.toDto(customerLimit);

        restCustomerLimitMockMvc.perform(post("/api/customer-limits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerLimitDTO)))
            .andExpect(status().isBadRequest());

        List<CustomerLimit> customerLimitList = customerLimitRepository.findAll();
        assertThat(customerLimitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerLimitRepository.findAll().size();
        // set the field null
        customerLimit.setActive(null);

        // Create the CustomerLimit, which fails.
        CustomerLimitDTO customerLimitDTO = customerLimitMapper.toDto(customerLimit);

        restCustomerLimitMockMvc.perform(post("/api/customer-limits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerLimitDTO)))
            .andExpect(status().isBadRequest());

        List<CustomerLimit> customerLimitList = customerLimitRepository.findAll();
        assertThat(customerLimitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCustomerLimits() throws Exception {
        // Initialize the database
        customerLimitRepository.saveAndFlush(customerLimit);

        // Get all the customerLimitList
        restCustomerLimitMockMvc.perform(get("/api/customer-limits?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerLimit.getId().intValue())))
            .andExpect(jsonPath("$.[*].limitTypeCode").value(hasItem(DEFAULT_LIMIT_TYPE_CODE.toString())))
            .andExpect(jsonPath("$.[*].accountTypeCode").value(hasItem(DEFAULT_ACCOUNT_TYPE_CODE.toString())))
            .andExpect(jsonPath("$.[*].customerTypeCode").value(hasItem(DEFAULT_CUSTOMER_TYPE_CODE.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCustomerLimit() throws Exception {
        // Initialize the database
        customerLimitRepository.saveAndFlush(customerLimit);

        // Get the customerLimit
        restCustomerLimitMockMvc.perform(get("/api/customer-limits/{id}", customerLimit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(customerLimit.getId().intValue()))
            .andExpect(jsonPath("$.limitTypeCode").value(DEFAULT_LIMIT_TYPE_CODE.toString()))
            .andExpect(jsonPath("$.accountTypeCode").value(DEFAULT_ACCOUNT_TYPE_CODE.toString()))
            .andExpect(jsonPath("$.customerTypeCode").value(DEFAULT_CUSTOMER_TYPE_CODE.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.doubleValue()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCustomerLimit() throws Exception {
        // Get the customerLimit
        restCustomerLimitMockMvc.perform(get("/api/customer-limits/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomerLimit() throws Exception {
        // Initialize the database
        customerLimitRepository.saveAndFlush(customerLimit);

        int databaseSizeBeforeUpdate = customerLimitRepository.findAll().size();

        // Update the customerLimit
        CustomerLimit updatedCustomerLimit = customerLimitRepository.findById(customerLimit.getId()).get();
        // Disconnect from session so that the updates on updatedCustomerLimit are not directly saved in db
        em.detach(updatedCustomerLimit);
        updatedCustomerLimit
            .limitTypeCode(UPDATED_LIMIT_TYPE_CODE)
            .accountTypeCode(UPDATED_ACCOUNT_TYPE_CODE)
            .customerTypeCode(UPDATED_CUSTOMER_TYPE_CODE)
            .value(UPDATED_VALUE)
            .comment(UPDATED_COMMENT)
            .active(UPDATED_ACTIVE);
        CustomerLimitDTO customerLimitDTO = customerLimitMapper.toDto(updatedCustomerLimit);

        restCustomerLimitMockMvc.perform(put("/api/customer-limits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerLimitDTO)))
            .andExpect(status().isOk());

        // Validate the CustomerLimit in the database
        List<CustomerLimit> customerLimitList = customerLimitRepository.findAll();
        assertThat(customerLimitList).hasSize(databaseSizeBeforeUpdate);
        CustomerLimit testCustomerLimit = customerLimitList.get(customerLimitList.size() - 1);
        assertThat(testCustomerLimit.getLimitTypeCode()).isEqualTo(UPDATED_LIMIT_TYPE_CODE);
        assertThat(testCustomerLimit.getAccountTypeCode()).isEqualTo(UPDATED_ACCOUNT_TYPE_CODE);
        assertThat(testCustomerLimit.getCustomerTypeCode()).isEqualTo(UPDATED_CUSTOMER_TYPE_CODE);
        assertThat(testCustomerLimit.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testCustomerLimit.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testCustomerLimit.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingCustomerLimit() throws Exception {
        int databaseSizeBeforeUpdate = customerLimitRepository.findAll().size();

        // Create the CustomerLimit
        CustomerLimitDTO customerLimitDTO = customerLimitMapper.toDto(customerLimit);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomerLimitMockMvc.perform(put("/api/customer-limits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerLimitDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CustomerLimit in the database
        List<CustomerLimit> customerLimitList = customerLimitRepository.findAll();
        assertThat(customerLimitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCustomerLimit() throws Exception {
        // Initialize the database
        customerLimitRepository.saveAndFlush(customerLimit);

        int databaseSizeBeforeDelete = customerLimitRepository.findAll().size();

        // Delete the customerLimit
        restCustomerLimitMockMvc.perform(delete("/api/customer-limits/{id}", customerLimit.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CustomerLimit> customerLimitList = customerLimitRepository.findAll();
        assertThat(customerLimitList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerLimit.class);
        CustomerLimit customerLimit1 = new CustomerLimit();
        customerLimit1.setId(1L);
        CustomerLimit customerLimit2 = new CustomerLimit();
        customerLimit2.setId(customerLimit1.getId());
        assertThat(customerLimit1).isEqualTo(customerLimit2);
        customerLimit2.setId(2L);
        assertThat(customerLimit1).isNotEqualTo(customerLimit2);
        customerLimit1.setId(null);
        assertThat(customerLimit1).isNotEqualTo(customerLimit2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerLimitDTO.class);
        CustomerLimitDTO customerLimitDTO1 = new CustomerLimitDTO();
        customerLimitDTO1.setId(1L);
        CustomerLimitDTO customerLimitDTO2 = new CustomerLimitDTO();
        assertThat(customerLimitDTO1).isNotEqualTo(customerLimitDTO2);
        customerLimitDTO2.setId(customerLimitDTO1.getId());
        assertThat(customerLimitDTO1).isEqualTo(customerLimitDTO2);
        customerLimitDTO2.setId(2L);
        assertThat(customerLimitDTO1).isNotEqualTo(customerLimitDTO2);
        customerLimitDTO1.setId(null);
        assertThat(customerLimitDTO1).isNotEqualTo(customerLimitDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(customerLimitMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(customerLimitMapper.fromId(null)).isNull();
    }
}
