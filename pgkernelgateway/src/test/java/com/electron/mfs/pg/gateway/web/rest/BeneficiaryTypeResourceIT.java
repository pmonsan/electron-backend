package com.electron.mfs.pg.gateway.web.rest;

import com.electron.mfs.pg.gateway.PgkernelgatewayApp;
import com.electron.mfs.pg.gateway.config.SecurityBeanOverrideConfiguration;
import com.electron.mfs.pg.gateway.domain.BeneficiaryType;
import com.electron.mfs.pg.gateway.repository.BeneficiaryTypeRepository;
import com.electron.mfs.pg.gateway.service.BeneficiaryTypeService;
import com.electron.mfs.pg.gateway.service.dto.BeneficiaryTypeDTO;
import com.electron.mfs.pg.gateway.service.mapper.BeneficiaryTypeMapper;
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
 * Integration tests for the {@Link BeneficiaryTypeResource} REST controller.
 */
@EmbeddedKafka
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, PgkernelgatewayApp.class})
public class BeneficiaryTypeResourceIT {

    private static final String DEFAULT_CODE = "AAAAA";
    private static final String UPDATED_CODE = "BBBBB";

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private BeneficiaryTypeRepository beneficiaryTypeRepository;

    @Autowired
    private BeneficiaryTypeMapper beneficiaryTypeMapper;

    @Autowired
    private BeneficiaryTypeService beneficiaryTypeService;

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

    private MockMvc restBeneficiaryTypeMockMvc;

    private BeneficiaryType beneficiaryType;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BeneficiaryTypeResource beneficiaryTypeResource = new BeneficiaryTypeResource(beneficiaryTypeService);
        this.restBeneficiaryTypeMockMvc = MockMvcBuilders.standaloneSetup(beneficiaryTypeResource)
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
    public static BeneficiaryType createEntity(EntityManager em) {
        BeneficiaryType beneficiaryType = new BeneficiaryType()
            .code(DEFAULT_CODE)
            .label(DEFAULT_LABEL)
            .active(DEFAULT_ACTIVE);
        return beneficiaryType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BeneficiaryType createUpdatedEntity(EntityManager em) {
        BeneficiaryType beneficiaryType = new BeneficiaryType()
            .code(UPDATED_CODE)
            .label(UPDATED_LABEL)
            .active(UPDATED_ACTIVE);
        return beneficiaryType;
    }

    @BeforeEach
    public void initTest() {
        beneficiaryType = createEntity(em);
    }

    @Test
    @Transactional
    public void createBeneficiaryType() throws Exception {
        int databaseSizeBeforeCreate = beneficiaryTypeRepository.findAll().size();

        // Create the BeneficiaryType
        BeneficiaryTypeDTO beneficiaryTypeDTO = beneficiaryTypeMapper.toDto(beneficiaryType);
        restBeneficiaryTypeMockMvc.perform(post("/api/beneficiary-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(beneficiaryTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the BeneficiaryType in the database
        List<BeneficiaryType> beneficiaryTypeList = beneficiaryTypeRepository.findAll();
        assertThat(beneficiaryTypeList).hasSize(databaseSizeBeforeCreate + 1);
        BeneficiaryType testBeneficiaryType = beneficiaryTypeList.get(beneficiaryTypeList.size() - 1);
        assertThat(testBeneficiaryType.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testBeneficiaryType.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testBeneficiaryType.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createBeneficiaryTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = beneficiaryTypeRepository.findAll().size();

        // Create the BeneficiaryType with an existing ID
        beneficiaryType.setId(1L);
        BeneficiaryTypeDTO beneficiaryTypeDTO = beneficiaryTypeMapper.toDto(beneficiaryType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBeneficiaryTypeMockMvc.perform(post("/api/beneficiary-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(beneficiaryTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BeneficiaryType in the database
        List<BeneficiaryType> beneficiaryTypeList = beneficiaryTypeRepository.findAll();
        assertThat(beneficiaryTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = beneficiaryTypeRepository.findAll().size();
        // set the field null
        beneficiaryType.setCode(null);

        // Create the BeneficiaryType, which fails.
        BeneficiaryTypeDTO beneficiaryTypeDTO = beneficiaryTypeMapper.toDto(beneficiaryType);

        restBeneficiaryTypeMockMvc.perform(post("/api/beneficiary-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(beneficiaryTypeDTO)))
            .andExpect(status().isBadRequest());

        List<BeneficiaryType> beneficiaryTypeList = beneficiaryTypeRepository.findAll();
        assertThat(beneficiaryTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLabelIsRequired() throws Exception {
        int databaseSizeBeforeTest = beneficiaryTypeRepository.findAll().size();
        // set the field null
        beneficiaryType.setLabel(null);

        // Create the BeneficiaryType, which fails.
        BeneficiaryTypeDTO beneficiaryTypeDTO = beneficiaryTypeMapper.toDto(beneficiaryType);

        restBeneficiaryTypeMockMvc.perform(post("/api/beneficiary-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(beneficiaryTypeDTO)))
            .andExpect(status().isBadRequest());

        List<BeneficiaryType> beneficiaryTypeList = beneficiaryTypeRepository.findAll();
        assertThat(beneficiaryTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = beneficiaryTypeRepository.findAll().size();
        // set the field null
        beneficiaryType.setActive(null);

        // Create the BeneficiaryType, which fails.
        BeneficiaryTypeDTO beneficiaryTypeDTO = beneficiaryTypeMapper.toDto(beneficiaryType);

        restBeneficiaryTypeMockMvc.perform(post("/api/beneficiary-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(beneficiaryTypeDTO)))
            .andExpect(status().isBadRequest());

        List<BeneficiaryType> beneficiaryTypeList = beneficiaryTypeRepository.findAll();
        assertThat(beneficiaryTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBeneficiaryTypes() throws Exception {
        // Initialize the database
        beneficiaryTypeRepository.saveAndFlush(beneficiaryType);

        // Get all the beneficiaryTypeList
        restBeneficiaryTypeMockMvc.perform(get("/api/beneficiary-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(beneficiaryType.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getBeneficiaryType() throws Exception {
        // Initialize the database
        beneficiaryTypeRepository.saveAndFlush(beneficiaryType);

        // Get the beneficiaryType
        restBeneficiaryTypeMockMvc.perform(get("/api/beneficiary-types/{id}", beneficiaryType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(beneficiaryType.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingBeneficiaryType() throws Exception {
        // Get the beneficiaryType
        restBeneficiaryTypeMockMvc.perform(get("/api/beneficiary-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBeneficiaryType() throws Exception {
        // Initialize the database
        beneficiaryTypeRepository.saveAndFlush(beneficiaryType);

        int databaseSizeBeforeUpdate = beneficiaryTypeRepository.findAll().size();

        // Update the beneficiaryType
        BeneficiaryType updatedBeneficiaryType = beneficiaryTypeRepository.findById(beneficiaryType.getId()).get();
        // Disconnect from session so that the updates on updatedBeneficiaryType are not directly saved in db
        em.detach(updatedBeneficiaryType);
        updatedBeneficiaryType
            .code(UPDATED_CODE)
            .label(UPDATED_LABEL)
            .active(UPDATED_ACTIVE);
        BeneficiaryTypeDTO beneficiaryTypeDTO = beneficiaryTypeMapper.toDto(updatedBeneficiaryType);

        restBeneficiaryTypeMockMvc.perform(put("/api/beneficiary-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(beneficiaryTypeDTO)))
            .andExpect(status().isOk());

        // Validate the BeneficiaryType in the database
        List<BeneficiaryType> beneficiaryTypeList = beneficiaryTypeRepository.findAll();
        assertThat(beneficiaryTypeList).hasSize(databaseSizeBeforeUpdate);
        BeneficiaryType testBeneficiaryType = beneficiaryTypeList.get(beneficiaryTypeList.size() - 1);
        assertThat(testBeneficiaryType.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testBeneficiaryType.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testBeneficiaryType.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingBeneficiaryType() throws Exception {
        int databaseSizeBeforeUpdate = beneficiaryTypeRepository.findAll().size();

        // Create the BeneficiaryType
        BeneficiaryTypeDTO beneficiaryTypeDTO = beneficiaryTypeMapper.toDto(beneficiaryType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBeneficiaryTypeMockMvc.perform(put("/api/beneficiary-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(beneficiaryTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BeneficiaryType in the database
        List<BeneficiaryType> beneficiaryTypeList = beneficiaryTypeRepository.findAll();
        assertThat(beneficiaryTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBeneficiaryType() throws Exception {
        // Initialize the database
        beneficiaryTypeRepository.saveAndFlush(beneficiaryType);

        int databaseSizeBeforeDelete = beneficiaryTypeRepository.findAll().size();

        // Delete the beneficiaryType
        restBeneficiaryTypeMockMvc.perform(delete("/api/beneficiary-types/{id}", beneficiaryType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BeneficiaryType> beneficiaryTypeList = beneficiaryTypeRepository.findAll();
        assertThat(beneficiaryTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BeneficiaryType.class);
        BeneficiaryType beneficiaryType1 = new BeneficiaryType();
        beneficiaryType1.setId(1L);
        BeneficiaryType beneficiaryType2 = new BeneficiaryType();
        beneficiaryType2.setId(beneficiaryType1.getId());
        assertThat(beneficiaryType1).isEqualTo(beneficiaryType2);
        beneficiaryType2.setId(2L);
        assertThat(beneficiaryType1).isNotEqualTo(beneficiaryType2);
        beneficiaryType1.setId(null);
        assertThat(beneficiaryType1).isNotEqualTo(beneficiaryType2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BeneficiaryTypeDTO.class);
        BeneficiaryTypeDTO beneficiaryTypeDTO1 = new BeneficiaryTypeDTO();
        beneficiaryTypeDTO1.setId(1L);
        BeneficiaryTypeDTO beneficiaryTypeDTO2 = new BeneficiaryTypeDTO();
        assertThat(beneficiaryTypeDTO1).isNotEqualTo(beneficiaryTypeDTO2);
        beneficiaryTypeDTO2.setId(beneficiaryTypeDTO1.getId());
        assertThat(beneficiaryTypeDTO1).isEqualTo(beneficiaryTypeDTO2);
        beneficiaryTypeDTO2.setId(2L);
        assertThat(beneficiaryTypeDTO1).isNotEqualTo(beneficiaryTypeDTO2);
        beneficiaryTypeDTO1.setId(null);
        assertThat(beneficiaryTypeDTO1).isNotEqualTo(beneficiaryTypeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(beneficiaryTypeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(beneficiaryTypeMapper.fromId(null)).isNull();
    }
}
