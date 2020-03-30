package com.electron.mfs.pg.gateway.web.rest;

import com.electron.mfs.pg.gateway.PgkernelgatewayApp;
import com.electron.mfs.pg.gateway.config.SecurityBeanOverrideConfiguration;
import com.electron.mfs.pg.gateway.domain.TransactionProperty;
import com.electron.mfs.pg.gateway.repository.TransactionPropertyRepository;
import com.electron.mfs.pg.gateway.service.TransactionPropertyService;
import com.electron.mfs.pg.gateway.service.dto.TransactionPropertyDTO;
import com.electron.mfs.pg.gateway.service.mapper.TransactionPropertyMapper;
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

import com.electron.mfs.pg.gateway.domain.enumeration.PropertyType;
/**
 * Integration tests for the {@Link TransactionPropertyResource} REST controller.
 */
@EmbeddedKafka
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, PgkernelgatewayApp.class})
public class TransactionPropertyResourceIT {

    private static final String DEFAULT_CODE = "AAAAA";
    private static final String UPDATED_CODE = "BBBBB";

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final PropertyType DEFAULT_PROPERTY_TYPE = PropertyType.STRING;
    private static final PropertyType UPDATED_PROPERTY_TYPE = PropertyType.INTEGER;

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private TransactionPropertyRepository transactionPropertyRepository;

    @Autowired
    private TransactionPropertyMapper transactionPropertyMapper;

    @Autowired
    private TransactionPropertyService transactionPropertyService;

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

    private MockMvc restTransactionPropertyMockMvc;

    private TransactionProperty transactionProperty;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TransactionPropertyResource transactionPropertyResource = new TransactionPropertyResource(transactionPropertyService);
        this.restTransactionPropertyMockMvc = MockMvcBuilders.standaloneSetup(transactionPropertyResource)
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
    public static TransactionProperty createEntity(EntityManager em) {
        TransactionProperty transactionProperty = new TransactionProperty()
            .code(DEFAULT_CODE)
            .label(DEFAULT_LABEL)
            .propertyType(DEFAULT_PROPERTY_TYPE)
            .active(DEFAULT_ACTIVE);
        return transactionProperty;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TransactionProperty createUpdatedEntity(EntityManager em) {
        TransactionProperty transactionProperty = new TransactionProperty()
            .code(UPDATED_CODE)
            .label(UPDATED_LABEL)
            .propertyType(UPDATED_PROPERTY_TYPE)
            .active(UPDATED_ACTIVE);
        return transactionProperty;
    }

    @BeforeEach
    public void initTest() {
        transactionProperty = createEntity(em);
    }

    @Test
    @Transactional
    public void createTransactionProperty() throws Exception {
        int databaseSizeBeforeCreate = transactionPropertyRepository.findAll().size();

        // Create the TransactionProperty
        TransactionPropertyDTO transactionPropertyDTO = transactionPropertyMapper.toDto(transactionProperty);
        restTransactionPropertyMockMvc.perform(post("/api/transaction-properties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionPropertyDTO)))
            .andExpect(status().isCreated());

        // Validate the TransactionProperty in the database
        List<TransactionProperty> transactionPropertyList = transactionPropertyRepository.findAll();
        assertThat(transactionPropertyList).hasSize(databaseSizeBeforeCreate + 1);
        TransactionProperty testTransactionProperty = transactionPropertyList.get(transactionPropertyList.size() - 1);
        assertThat(testTransactionProperty.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testTransactionProperty.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testTransactionProperty.getPropertyType()).isEqualTo(DEFAULT_PROPERTY_TYPE);
        assertThat(testTransactionProperty.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createTransactionPropertyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = transactionPropertyRepository.findAll().size();

        // Create the TransactionProperty with an existing ID
        transactionProperty.setId(1L);
        TransactionPropertyDTO transactionPropertyDTO = transactionPropertyMapper.toDto(transactionProperty);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransactionPropertyMockMvc.perform(post("/api/transaction-properties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionPropertyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TransactionProperty in the database
        List<TransactionProperty> transactionPropertyList = transactionPropertyRepository.findAll();
        assertThat(transactionPropertyList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionPropertyRepository.findAll().size();
        // set the field null
        transactionProperty.setCode(null);

        // Create the TransactionProperty, which fails.
        TransactionPropertyDTO transactionPropertyDTO = transactionPropertyMapper.toDto(transactionProperty);

        restTransactionPropertyMockMvc.perform(post("/api/transaction-properties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionPropertyDTO)))
            .andExpect(status().isBadRequest());

        List<TransactionProperty> transactionPropertyList = transactionPropertyRepository.findAll();
        assertThat(transactionPropertyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLabelIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionPropertyRepository.findAll().size();
        // set the field null
        transactionProperty.setLabel(null);

        // Create the TransactionProperty, which fails.
        TransactionPropertyDTO transactionPropertyDTO = transactionPropertyMapper.toDto(transactionProperty);

        restTransactionPropertyMockMvc.perform(post("/api/transaction-properties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionPropertyDTO)))
            .andExpect(status().isBadRequest());

        List<TransactionProperty> transactionPropertyList = transactionPropertyRepository.findAll();
        assertThat(transactionPropertyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPropertyTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionPropertyRepository.findAll().size();
        // set the field null
        transactionProperty.setPropertyType(null);

        // Create the TransactionProperty, which fails.
        TransactionPropertyDTO transactionPropertyDTO = transactionPropertyMapper.toDto(transactionProperty);

        restTransactionPropertyMockMvc.perform(post("/api/transaction-properties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionPropertyDTO)))
            .andExpect(status().isBadRequest());

        List<TransactionProperty> transactionPropertyList = transactionPropertyRepository.findAll();
        assertThat(transactionPropertyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionPropertyRepository.findAll().size();
        // set the field null
        transactionProperty.setActive(null);

        // Create the TransactionProperty, which fails.
        TransactionPropertyDTO transactionPropertyDTO = transactionPropertyMapper.toDto(transactionProperty);

        restTransactionPropertyMockMvc.perform(post("/api/transaction-properties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionPropertyDTO)))
            .andExpect(status().isBadRequest());

        List<TransactionProperty> transactionPropertyList = transactionPropertyRepository.findAll();
        assertThat(transactionPropertyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTransactionProperties() throws Exception {
        // Initialize the database
        transactionPropertyRepository.saveAndFlush(transactionProperty);

        // Get all the transactionPropertyList
        restTransactionPropertyMockMvc.perform(get("/api/transaction-properties?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transactionProperty.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())))
            .andExpect(jsonPath("$.[*].propertyType").value(hasItem(DEFAULT_PROPERTY_TYPE.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getTransactionProperty() throws Exception {
        // Initialize the database
        transactionPropertyRepository.saveAndFlush(transactionProperty);

        // Get the transactionProperty
        restTransactionPropertyMockMvc.perform(get("/api/transaction-properties/{id}", transactionProperty.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(transactionProperty.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()))
            .andExpect(jsonPath("$.propertyType").value(DEFAULT_PROPERTY_TYPE.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTransactionProperty() throws Exception {
        // Get the transactionProperty
        restTransactionPropertyMockMvc.perform(get("/api/transaction-properties/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTransactionProperty() throws Exception {
        // Initialize the database
        transactionPropertyRepository.saveAndFlush(transactionProperty);

        int databaseSizeBeforeUpdate = transactionPropertyRepository.findAll().size();

        // Update the transactionProperty
        TransactionProperty updatedTransactionProperty = transactionPropertyRepository.findById(transactionProperty.getId()).get();
        // Disconnect from session so that the updates on updatedTransactionProperty are not directly saved in db
        em.detach(updatedTransactionProperty);
        updatedTransactionProperty
            .code(UPDATED_CODE)
            .label(UPDATED_LABEL)
            .propertyType(UPDATED_PROPERTY_TYPE)
            .active(UPDATED_ACTIVE);
        TransactionPropertyDTO transactionPropertyDTO = transactionPropertyMapper.toDto(updatedTransactionProperty);

        restTransactionPropertyMockMvc.perform(put("/api/transaction-properties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionPropertyDTO)))
            .andExpect(status().isOk());

        // Validate the TransactionProperty in the database
        List<TransactionProperty> transactionPropertyList = transactionPropertyRepository.findAll();
        assertThat(transactionPropertyList).hasSize(databaseSizeBeforeUpdate);
        TransactionProperty testTransactionProperty = transactionPropertyList.get(transactionPropertyList.size() - 1);
        assertThat(testTransactionProperty.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTransactionProperty.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testTransactionProperty.getPropertyType()).isEqualTo(UPDATED_PROPERTY_TYPE);
        assertThat(testTransactionProperty.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingTransactionProperty() throws Exception {
        int databaseSizeBeforeUpdate = transactionPropertyRepository.findAll().size();

        // Create the TransactionProperty
        TransactionPropertyDTO transactionPropertyDTO = transactionPropertyMapper.toDto(transactionProperty);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransactionPropertyMockMvc.perform(put("/api/transaction-properties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionPropertyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TransactionProperty in the database
        List<TransactionProperty> transactionPropertyList = transactionPropertyRepository.findAll();
        assertThat(transactionPropertyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTransactionProperty() throws Exception {
        // Initialize the database
        transactionPropertyRepository.saveAndFlush(transactionProperty);

        int databaseSizeBeforeDelete = transactionPropertyRepository.findAll().size();

        // Delete the transactionProperty
        restTransactionPropertyMockMvc.perform(delete("/api/transaction-properties/{id}", transactionProperty.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TransactionProperty> transactionPropertyList = transactionPropertyRepository.findAll();
        assertThat(transactionPropertyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TransactionProperty.class);
        TransactionProperty transactionProperty1 = new TransactionProperty();
        transactionProperty1.setId(1L);
        TransactionProperty transactionProperty2 = new TransactionProperty();
        transactionProperty2.setId(transactionProperty1.getId());
        assertThat(transactionProperty1).isEqualTo(transactionProperty2);
        transactionProperty2.setId(2L);
        assertThat(transactionProperty1).isNotEqualTo(transactionProperty2);
        transactionProperty1.setId(null);
        assertThat(transactionProperty1).isNotEqualTo(transactionProperty2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TransactionPropertyDTO.class);
        TransactionPropertyDTO transactionPropertyDTO1 = new TransactionPropertyDTO();
        transactionPropertyDTO1.setId(1L);
        TransactionPropertyDTO transactionPropertyDTO2 = new TransactionPropertyDTO();
        assertThat(transactionPropertyDTO1).isNotEqualTo(transactionPropertyDTO2);
        transactionPropertyDTO2.setId(transactionPropertyDTO1.getId());
        assertThat(transactionPropertyDTO1).isEqualTo(transactionPropertyDTO2);
        transactionPropertyDTO2.setId(2L);
        assertThat(transactionPropertyDTO1).isNotEqualTo(transactionPropertyDTO2);
        transactionPropertyDTO1.setId(null);
        assertThat(transactionPropertyDTO1).isNotEqualTo(transactionPropertyDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(transactionPropertyMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(transactionPropertyMapper.fromId(null)).isNull();
    }
}
