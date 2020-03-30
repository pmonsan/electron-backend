package com.electron.mfs.pg.customer.web.rest;

import com.electron.mfs.pg.customer.CustomerManagerApp;
import com.electron.mfs.pg.customer.config.SecurityBeanOverrideConfiguration;
import com.electron.mfs.pg.customer.domain.CustomerBlacklist;
import com.electron.mfs.pg.customer.repository.CustomerBlacklistRepository;
import com.electron.mfs.pg.customer.service.CustomerBlacklistService;
import com.electron.mfs.pg.customer.service.dto.CustomerBlacklistDTO;
import com.electron.mfs.pg.customer.service.mapper.CustomerBlacklistMapper;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.electron.mfs.pg.customer.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.electron.mfs.pg.customer.domain.enumeration.CustomerBlacklistStatus;
/**
 * Integration tests for the {@Link CustomerBlacklistResource} REST controller.
 */
@EmbeddedKafka
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, CustomerManagerApp.class})
public class CustomerBlacklistResourceIT {

    private static final CustomerBlacklistStatus DEFAULT_CUSTOMER_BLACKLIST_STATUS = CustomerBlacklistStatus.INITIATED;
    private static final CustomerBlacklistStatus UPDATED_CUSTOMER_BLACKLIST_STATUS = CustomerBlacklistStatus.BLACKLIST;

    private static final Instant DEFAULT_INSERTION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_INSERTION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_MODIFICATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MODIFICATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private CustomerBlacklistRepository customerBlacklistRepository;

    @Autowired
    private CustomerBlacklistMapper customerBlacklistMapper;

    @Autowired
    private CustomerBlacklistService customerBlacklistService;

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

    private MockMvc restCustomerBlacklistMockMvc;

    private CustomerBlacklist customerBlacklist;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CustomerBlacklistResource customerBlacklistResource = new CustomerBlacklistResource(customerBlacklistService);
        this.restCustomerBlacklistMockMvc = MockMvcBuilders.standaloneSetup(customerBlacklistResource)
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
    public static CustomerBlacklist createEntity(EntityManager em) {
        CustomerBlacklist customerBlacklist = new CustomerBlacklist()
            .customerBlacklistStatus(DEFAULT_CUSTOMER_BLACKLIST_STATUS)
            .insertionDate(DEFAULT_INSERTION_DATE)
            .modificationDate(DEFAULT_MODIFICATION_DATE)
            .comment(DEFAULT_COMMENT)
            .active(DEFAULT_ACTIVE);
        return customerBlacklist;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomerBlacklist createUpdatedEntity(EntityManager em) {
        CustomerBlacklist customerBlacklist = new CustomerBlacklist()
            .customerBlacklistStatus(UPDATED_CUSTOMER_BLACKLIST_STATUS)
            .insertionDate(UPDATED_INSERTION_DATE)
            .modificationDate(UPDATED_MODIFICATION_DATE)
            .comment(UPDATED_COMMENT)
            .active(UPDATED_ACTIVE);
        return customerBlacklist;
    }

    @BeforeEach
    public void initTest() {
        customerBlacklist = createEntity(em);
    }

    @Test
    @Transactional
    public void createCustomerBlacklist() throws Exception {
        int databaseSizeBeforeCreate = customerBlacklistRepository.findAll().size();

        // Create the CustomerBlacklist
        CustomerBlacklistDTO customerBlacklistDTO = customerBlacklistMapper.toDto(customerBlacklist);
        restCustomerBlacklistMockMvc.perform(post("/api/customer-blacklists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerBlacklistDTO)))
            .andExpect(status().isCreated());

        // Validate the CustomerBlacklist in the database
        List<CustomerBlacklist> customerBlacklistList = customerBlacklistRepository.findAll();
        assertThat(customerBlacklistList).hasSize(databaseSizeBeforeCreate + 1);
        CustomerBlacklist testCustomerBlacklist = customerBlacklistList.get(customerBlacklistList.size() - 1);
        assertThat(testCustomerBlacklist.getCustomerBlacklistStatus()).isEqualTo(DEFAULT_CUSTOMER_BLACKLIST_STATUS);
        assertThat(testCustomerBlacklist.getInsertionDate()).isEqualTo(DEFAULT_INSERTION_DATE);
        assertThat(testCustomerBlacklist.getModificationDate()).isEqualTo(DEFAULT_MODIFICATION_DATE);
        assertThat(testCustomerBlacklist.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testCustomerBlacklist.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createCustomerBlacklistWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = customerBlacklistRepository.findAll().size();

        // Create the CustomerBlacklist with an existing ID
        customerBlacklist.setId(1L);
        CustomerBlacklistDTO customerBlacklistDTO = customerBlacklistMapper.toDto(customerBlacklist);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomerBlacklistMockMvc.perform(post("/api/customer-blacklists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerBlacklistDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CustomerBlacklist in the database
        List<CustomerBlacklist> customerBlacklistList = customerBlacklistRepository.findAll();
        assertThat(customerBlacklistList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCustomerBlacklistStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerBlacklistRepository.findAll().size();
        // set the field null
        customerBlacklist.setCustomerBlacklistStatus(null);

        // Create the CustomerBlacklist, which fails.
        CustomerBlacklistDTO customerBlacklistDTO = customerBlacklistMapper.toDto(customerBlacklist);

        restCustomerBlacklistMockMvc.perform(post("/api/customer-blacklists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerBlacklistDTO)))
            .andExpect(status().isBadRequest());

        List<CustomerBlacklist> customerBlacklistList = customerBlacklistRepository.findAll();
        assertThat(customerBlacklistList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkInsertionDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerBlacklistRepository.findAll().size();
        // set the field null
        customerBlacklist.setInsertionDate(null);

        // Create the CustomerBlacklist, which fails.
        CustomerBlacklistDTO customerBlacklistDTO = customerBlacklistMapper.toDto(customerBlacklist);

        restCustomerBlacklistMockMvc.perform(post("/api/customer-blacklists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerBlacklistDTO)))
            .andExpect(status().isBadRequest());

        List<CustomerBlacklist> customerBlacklistList = customerBlacklistRepository.findAll();
        assertThat(customerBlacklistList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerBlacklistRepository.findAll().size();
        // set the field null
        customerBlacklist.setActive(null);

        // Create the CustomerBlacklist, which fails.
        CustomerBlacklistDTO customerBlacklistDTO = customerBlacklistMapper.toDto(customerBlacklist);

        restCustomerBlacklistMockMvc.perform(post("/api/customer-blacklists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerBlacklistDTO)))
            .andExpect(status().isBadRequest());

        List<CustomerBlacklist> customerBlacklistList = customerBlacklistRepository.findAll();
        assertThat(customerBlacklistList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCustomerBlacklists() throws Exception {
        // Initialize the database
        customerBlacklistRepository.saveAndFlush(customerBlacklist);

        // Get all the customerBlacklistList
        restCustomerBlacklistMockMvc.perform(get("/api/customer-blacklists?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerBlacklist.getId().intValue())))
            .andExpect(jsonPath("$.[*].customerBlacklistStatus").value(hasItem(DEFAULT_CUSTOMER_BLACKLIST_STATUS.toString())))
            .andExpect(jsonPath("$.[*].insertionDate").value(hasItem(DEFAULT_INSERTION_DATE.toString())))
            .andExpect(jsonPath("$.[*].modificationDate").value(hasItem(DEFAULT_MODIFICATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCustomerBlacklist() throws Exception {
        // Initialize the database
        customerBlacklistRepository.saveAndFlush(customerBlacklist);

        // Get the customerBlacklist
        restCustomerBlacklistMockMvc.perform(get("/api/customer-blacklists/{id}", customerBlacklist.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(customerBlacklist.getId().intValue()))
            .andExpect(jsonPath("$.customerBlacklistStatus").value(DEFAULT_CUSTOMER_BLACKLIST_STATUS.toString()))
            .andExpect(jsonPath("$.insertionDate").value(DEFAULT_INSERTION_DATE.toString()))
            .andExpect(jsonPath("$.modificationDate").value(DEFAULT_MODIFICATION_DATE.toString()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCustomerBlacklist() throws Exception {
        // Get the customerBlacklist
        restCustomerBlacklistMockMvc.perform(get("/api/customer-blacklists/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomerBlacklist() throws Exception {
        // Initialize the database
        customerBlacklistRepository.saveAndFlush(customerBlacklist);

        int databaseSizeBeforeUpdate = customerBlacklistRepository.findAll().size();

        // Update the customerBlacklist
        CustomerBlacklist updatedCustomerBlacklist = customerBlacklistRepository.findById(customerBlacklist.getId()).get();
        // Disconnect from session so that the updates on updatedCustomerBlacklist are not directly saved in db
        em.detach(updatedCustomerBlacklist);
        updatedCustomerBlacklist
            .customerBlacklistStatus(UPDATED_CUSTOMER_BLACKLIST_STATUS)
            .insertionDate(UPDATED_INSERTION_DATE)
            .modificationDate(UPDATED_MODIFICATION_DATE)
            .comment(UPDATED_COMMENT)
            .active(UPDATED_ACTIVE);
        CustomerBlacklistDTO customerBlacklistDTO = customerBlacklistMapper.toDto(updatedCustomerBlacklist);

        restCustomerBlacklistMockMvc.perform(put("/api/customer-blacklists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerBlacklistDTO)))
            .andExpect(status().isOk());

        // Validate the CustomerBlacklist in the database
        List<CustomerBlacklist> customerBlacklistList = customerBlacklistRepository.findAll();
        assertThat(customerBlacklistList).hasSize(databaseSizeBeforeUpdate);
        CustomerBlacklist testCustomerBlacklist = customerBlacklistList.get(customerBlacklistList.size() - 1);
        assertThat(testCustomerBlacklist.getCustomerBlacklistStatus()).isEqualTo(UPDATED_CUSTOMER_BLACKLIST_STATUS);
        assertThat(testCustomerBlacklist.getInsertionDate()).isEqualTo(UPDATED_INSERTION_DATE);
        assertThat(testCustomerBlacklist.getModificationDate()).isEqualTo(UPDATED_MODIFICATION_DATE);
        assertThat(testCustomerBlacklist.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testCustomerBlacklist.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingCustomerBlacklist() throws Exception {
        int databaseSizeBeforeUpdate = customerBlacklistRepository.findAll().size();

        // Create the CustomerBlacklist
        CustomerBlacklistDTO customerBlacklistDTO = customerBlacklistMapper.toDto(customerBlacklist);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomerBlacklistMockMvc.perform(put("/api/customer-blacklists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerBlacklistDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CustomerBlacklist in the database
        List<CustomerBlacklist> customerBlacklistList = customerBlacklistRepository.findAll();
        assertThat(customerBlacklistList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCustomerBlacklist() throws Exception {
        // Initialize the database
        customerBlacklistRepository.saveAndFlush(customerBlacklist);

        int databaseSizeBeforeDelete = customerBlacklistRepository.findAll().size();

        // Delete the customerBlacklist
        restCustomerBlacklistMockMvc.perform(delete("/api/customer-blacklists/{id}", customerBlacklist.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CustomerBlacklist> customerBlacklistList = customerBlacklistRepository.findAll();
        assertThat(customerBlacklistList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerBlacklist.class);
        CustomerBlacklist customerBlacklist1 = new CustomerBlacklist();
        customerBlacklist1.setId(1L);
        CustomerBlacklist customerBlacklist2 = new CustomerBlacklist();
        customerBlacklist2.setId(customerBlacklist1.getId());
        assertThat(customerBlacklist1).isEqualTo(customerBlacklist2);
        customerBlacklist2.setId(2L);
        assertThat(customerBlacklist1).isNotEqualTo(customerBlacklist2);
        customerBlacklist1.setId(null);
        assertThat(customerBlacklist1).isNotEqualTo(customerBlacklist2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerBlacklistDTO.class);
        CustomerBlacklistDTO customerBlacklistDTO1 = new CustomerBlacklistDTO();
        customerBlacklistDTO1.setId(1L);
        CustomerBlacklistDTO customerBlacklistDTO2 = new CustomerBlacklistDTO();
        assertThat(customerBlacklistDTO1).isNotEqualTo(customerBlacklistDTO2);
        customerBlacklistDTO2.setId(customerBlacklistDTO1.getId());
        assertThat(customerBlacklistDTO1).isEqualTo(customerBlacklistDTO2);
        customerBlacklistDTO2.setId(2L);
        assertThat(customerBlacklistDTO1).isNotEqualTo(customerBlacklistDTO2);
        customerBlacklistDTO1.setId(null);
        assertThat(customerBlacklistDTO1).isNotEqualTo(customerBlacklistDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(customerBlacklistMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(customerBlacklistMapper.fromId(null)).isNull();
    }
}
