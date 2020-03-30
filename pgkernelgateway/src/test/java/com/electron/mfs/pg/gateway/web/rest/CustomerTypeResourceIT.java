package com.electron.mfs.pg.gateway.web.rest;

import com.electron.mfs.pg.gateway.PgkernelgatewayApp;
import com.electron.mfs.pg.gateway.config.SecurityBeanOverrideConfiguration;
import com.electron.mfs.pg.gateway.domain.CustomerType;
import com.electron.mfs.pg.gateway.repository.CustomerTypeRepository;
import com.electron.mfs.pg.gateway.service.CustomerTypeService;
import com.electron.mfs.pg.gateway.service.dto.CustomerTypeDTO;
import com.electron.mfs.pg.gateway.service.mapper.CustomerTypeMapper;
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
 * Integration tests for the {@Link CustomerTypeResource} REST controller.
 */
@EmbeddedKafka
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, PgkernelgatewayApp.class})
public class CustomerTypeResourceIT {

    private static final String DEFAULT_CODE = "AAAAA";
    private static final String UPDATED_CODE = "BBBBB";

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private CustomerTypeRepository customerTypeRepository;

    @Autowired
    private CustomerTypeMapper customerTypeMapper;

    @Autowired
    private CustomerTypeService customerTypeService;

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

    private MockMvc restCustomerTypeMockMvc;

    private CustomerType customerType;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CustomerTypeResource customerTypeResource = new CustomerTypeResource(customerTypeService);
        this.restCustomerTypeMockMvc = MockMvcBuilders.standaloneSetup(customerTypeResource)
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
    public static CustomerType createEntity(EntityManager em) {
        CustomerType customerType = new CustomerType()
            .code(DEFAULT_CODE)
            .label(DEFAULT_LABEL)
            .active(DEFAULT_ACTIVE);
        return customerType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomerType createUpdatedEntity(EntityManager em) {
        CustomerType customerType = new CustomerType()
            .code(UPDATED_CODE)
            .label(UPDATED_LABEL)
            .active(UPDATED_ACTIVE);
        return customerType;
    }

    @BeforeEach
    public void initTest() {
        customerType = createEntity(em);
    }

    @Test
    @Transactional
    public void createCustomerType() throws Exception {
        int databaseSizeBeforeCreate = customerTypeRepository.findAll().size();

        // Create the CustomerType
        CustomerTypeDTO customerTypeDTO = customerTypeMapper.toDto(customerType);
        restCustomerTypeMockMvc.perform(post("/api/customer-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the CustomerType in the database
        List<CustomerType> customerTypeList = customerTypeRepository.findAll();
        assertThat(customerTypeList).hasSize(databaseSizeBeforeCreate + 1);
        CustomerType testCustomerType = customerTypeList.get(customerTypeList.size() - 1);
        assertThat(testCustomerType.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testCustomerType.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testCustomerType.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createCustomerTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = customerTypeRepository.findAll().size();

        // Create the CustomerType with an existing ID
        customerType.setId(1L);
        CustomerTypeDTO customerTypeDTO = customerTypeMapper.toDto(customerType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomerTypeMockMvc.perform(post("/api/customer-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CustomerType in the database
        List<CustomerType> customerTypeList = customerTypeRepository.findAll();
        assertThat(customerTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerTypeRepository.findAll().size();
        // set the field null
        customerType.setCode(null);

        // Create the CustomerType, which fails.
        CustomerTypeDTO customerTypeDTO = customerTypeMapper.toDto(customerType);

        restCustomerTypeMockMvc.perform(post("/api/customer-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerTypeDTO)))
            .andExpect(status().isBadRequest());

        List<CustomerType> customerTypeList = customerTypeRepository.findAll();
        assertThat(customerTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLabelIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerTypeRepository.findAll().size();
        // set the field null
        customerType.setLabel(null);

        // Create the CustomerType, which fails.
        CustomerTypeDTO customerTypeDTO = customerTypeMapper.toDto(customerType);

        restCustomerTypeMockMvc.perform(post("/api/customer-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerTypeDTO)))
            .andExpect(status().isBadRequest());

        List<CustomerType> customerTypeList = customerTypeRepository.findAll();
        assertThat(customerTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerTypeRepository.findAll().size();
        // set the field null
        customerType.setActive(null);

        // Create the CustomerType, which fails.
        CustomerTypeDTO customerTypeDTO = customerTypeMapper.toDto(customerType);

        restCustomerTypeMockMvc.perform(post("/api/customer-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerTypeDTO)))
            .andExpect(status().isBadRequest());

        List<CustomerType> customerTypeList = customerTypeRepository.findAll();
        assertThat(customerTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCustomerTypes() throws Exception {
        // Initialize the database
        customerTypeRepository.saveAndFlush(customerType);

        // Get all the customerTypeList
        restCustomerTypeMockMvc.perform(get("/api/customer-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerType.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCustomerType() throws Exception {
        // Initialize the database
        customerTypeRepository.saveAndFlush(customerType);

        // Get the customerType
        restCustomerTypeMockMvc.perform(get("/api/customer-types/{id}", customerType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(customerType.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCustomerType() throws Exception {
        // Get the customerType
        restCustomerTypeMockMvc.perform(get("/api/customer-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomerType() throws Exception {
        // Initialize the database
        customerTypeRepository.saveAndFlush(customerType);

        int databaseSizeBeforeUpdate = customerTypeRepository.findAll().size();

        // Update the customerType
        CustomerType updatedCustomerType = customerTypeRepository.findById(customerType.getId()).get();
        // Disconnect from session so that the updates on updatedCustomerType are not directly saved in db
        em.detach(updatedCustomerType);
        updatedCustomerType
            .code(UPDATED_CODE)
            .label(UPDATED_LABEL)
            .active(UPDATED_ACTIVE);
        CustomerTypeDTO customerTypeDTO = customerTypeMapper.toDto(updatedCustomerType);

        restCustomerTypeMockMvc.perform(put("/api/customer-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerTypeDTO)))
            .andExpect(status().isOk());

        // Validate the CustomerType in the database
        List<CustomerType> customerTypeList = customerTypeRepository.findAll();
        assertThat(customerTypeList).hasSize(databaseSizeBeforeUpdate);
        CustomerType testCustomerType = customerTypeList.get(customerTypeList.size() - 1);
        assertThat(testCustomerType.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCustomerType.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testCustomerType.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingCustomerType() throws Exception {
        int databaseSizeBeforeUpdate = customerTypeRepository.findAll().size();

        // Create the CustomerType
        CustomerTypeDTO customerTypeDTO = customerTypeMapper.toDto(customerType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomerTypeMockMvc.perform(put("/api/customer-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CustomerType in the database
        List<CustomerType> customerTypeList = customerTypeRepository.findAll();
        assertThat(customerTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCustomerType() throws Exception {
        // Initialize the database
        customerTypeRepository.saveAndFlush(customerType);

        int databaseSizeBeforeDelete = customerTypeRepository.findAll().size();

        // Delete the customerType
        restCustomerTypeMockMvc.perform(delete("/api/customer-types/{id}", customerType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CustomerType> customerTypeList = customerTypeRepository.findAll();
        assertThat(customerTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerType.class);
        CustomerType customerType1 = new CustomerType();
        customerType1.setId(1L);
        CustomerType customerType2 = new CustomerType();
        customerType2.setId(customerType1.getId());
        assertThat(customerType1).isEqualTo(customerType2);
        customerType2.setId(2L);
        assertThat(customerType1).isNotEqualTo(customerType2);
        customerType1.setId(null);
        assertThat(customerType1).isNotEqualTo(customerType2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerTypeDTO.class);
        CustomerTypeDTO customerTypeDTO1 = new CustomerTypeDTO();
        customerTypeDTO1.setId(1L);
        CustomerTypeDTO customerTypeDTO2 = new CustomerTypeDTO();
        assertThat(customerTypeDTO1).isNotEqualTo(customerTypeDTO2);
        customerTypeDTO2.setId(customerTypeDTO1.getId());
        assertThat(customerTypeDTO1).isEqualTo(customerTypeDTO2);
        customerTypeDTO2.setId(2L);
        assertThat(customerTypeDTO1).isNotEqualTo(customerTypeDTO2);
        customerTypeDTO1.setId(null);
        assertThat(customerTypeDTO1).isNotEqualTo(customerTypeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(customerTypeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(customerTypeMapper.fromId(null)).isNull();
    }
}
