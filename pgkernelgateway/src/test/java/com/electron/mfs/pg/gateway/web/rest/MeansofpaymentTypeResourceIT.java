package com.electron.mfs.pg.gateway.web.rest;

import com.electron.mfs.pg.gateway.PgkernelgatewayApp;
import com.electron.mfs.pg.gateway.config.SecurityBeanOverrideConfiguration;
import com.electron.mfs.pg.gateway.domain.MeansofpaymentType;
import com.electron.mfs.pg.gateway.repository.MeansofpaymentTypeRepository;
import com.electron.mfs.pg.gateway.service.MeansofpaymentTypeService;
import com.electron.mfs.pg.gateway.service.dto.MeansofpaymentTypeDTO;
import com.electron.mfs.pg.gateway.service.mapper.MeansofpaymentTypeMapper;
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
 * Integration tests for the {@Link MeansofpaymentTypeResource} REST controller.
 */
@EmbeddedKafka
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, PgkernelgatewayApp.class})
public class MeansofpaymentTypeResourceIT {

    private static final String DEFAULT_CODE = "AAAAA";
    private static final String UPDATED_CODE = "BBBBB";

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private MeansofpaymentTypeRepository meansofpaymentTypeRepository;

    @Autowired
    private MeansofpaymentTypeMapper meansofpaymentTypeMapper;

    @Autowired
    private MeansofpaymentTypeService meansofpaymentTypeService;

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

    private MockMvc restMeansofpaymentTypeMockMvc;

    private MeansofpaymentType meansofpaymentType;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MeansofpaymentTypeResource meansofpaymentTypeResource = new MeansofpaymentTypeResource(meansofpaymentTypeService);
        this.restMeansofpaymentTypeMockMvc = MockMvcBuilders.standaloneSetup(meansofpaymentTypeResource)
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
    public static MeansofpaymentType createEntity(EntityManager em) {
        MeansofpaymentType meansofpaymentType = new MeansofpaymentType()
            .code(DEFAULT_CODE)
            .label(DEFAULT_LABEL)
            .active(DEFAULT_ACTIVE);
        return meansofpaymentType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MeansofpaymentType createUpdatedEntity(EntityManager em) {
        MeansofpaymentType meansofpaymentType = new MeansofpaymentType()
            .code(UPDATED_CODE)
            .label(UPDATED_LABEL)
            .active(UPDATED_ACTIVE);
        return meansofpaymentType;
    }

    @BeforeEach
    public void initTest() {
        meansofpaymentType = createEntity(em);
    }

    @Test
    @Transactional
    public void createMeansofpaymentType() throws Exception {
        int databaseSizeBeforeCreate = meansofpaymentTypeRepository.findAll().size();

        // Create the MeansofpaymentType
        MeansofpaymentTypeDTO meansofpaymentTypeDTO = meansofpaymentTypeMapper.toDto(meansofpaymentType);
        restMeansofpaymentTypeMockMvc.perform(post("/api/meansofpayment-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meansofpaymentTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the MeansofpaymentType in the database
        List<MeansofpaymentType> meansofpaymentTypeList = meansofpaymentTypeRepository.findAll();
        assertThat(meansofpaymentTypeList).hasSize(databaseSizeBeforeCreate + 1);
        MeansofpaymentType testMeansofpaymentType = meansofpaymentTypeList.get(meansofpaymentTypeList.size() - 1);
        assertThat(testMeansofpaymentType.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testMeansofpaymentType.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testMeansofpaymentType.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createMeansofpaymentTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = meansofpaymentTypeRepository.findAll().size();

        // Create the MeansofpaymentType with an existing ID
        meansofpaymentType.setId(1L);
        MeansofpaymentTypeDTO meansofpaymentTypeDTO = meansofpaymentTypeMapper.toDto(meansofpaymentType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMeansofpaymentTypeMockMvc.perform(post("/api/meansofpayment-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meansofpaymentTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MeansofpaymentType in the database
        List<MeansofpaymentType> meansofpaymentTypeList = meansofpaymentTypeRepository.findAll();
        assertThat(meansofpaymentTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = meansofpaymentTypeRepository.findAll().size();
        // set the field null
        meansofpaymentType.setCode(null);

        // Create the MeansofpaymentType, which fails.
        MeansofpaymentTypeDTO meansofpaymentTypeDTO = meansofpaymentTypeMapper.toDto(meansofpaymentType);

        restMeansofpaymentTypeMockMvc.perform(post("/api/meansofpayment-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meansofpaymentTypeDTO)))
            .andExpect(status().isBadRequest());

        List<MeansofpaymentType> meansofpaymentTypeList = meansofpaymentTypeRepository.findAll();
        assertThat(meansofpaymentTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLabelIsRequired() throws Exception {
        int databaseSizeBeforeTest = meansofpaymentTypeRepository.findAll().size();
        // set the field null
        meansofpaymentType.setLabel(null);

        // Create the MeansofpaymentType, which fails.
        MeansofpaymentTypeDTO meansofpaymentTypeDTO = meansofpaymentTypeMapper.toDto(meansofpaymentType);

        restMeansofpaymentTypeMockMvc.perform(post("/api/meansofpayment-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meansofpaymentTypeDTO)))
            .andExpect(status().isBadRequest());

        List<MeansofpaymentType> meansofpaymentTypeList = meansofpaymentTypeRepository.findAll();
        assertThat(meansofpaymentTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = meansofpaymentTypeRepository.findAll().size();
        // set the field null
        meansofpaymentType.setActive(null);

        // Create the MeansofpaymentType, which fails.
        MeansofpaymentTypeDTO meansofpaymentTypeDTO = meansofpaymentTypeMapper.toDto(meansofpaymentType);

        restMeansofpaymentTypeMockMvc.perform(post("/api/meansofpayment-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meansofpaymentTypeDTO)))
            .andExpect(status().isBadRequest());

        List<MeansofpaymentType> meansofpaymentTypeList = meansofpaymentTypeRepository.findAll();
        assertThat(meansofpaymentTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMeansofpaymentTypes() throws Exception {
        // Initialize the database
        meansofpaymentTypeRepository.saveAndFlush(meansofpaymentType);

        // Get all the meansofpaymentTypeList
        restMeansofpaymentTypeMockMvc.perform(get("/api/meansofpayment-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(meansofpaymentType.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMeansofpaymentType() throws Exception {
        // Initialize the database
        meansofpaymentTypeRepository.saveAndFlush(meansofpaymentType);

        // Get the meansofpaymentType
        restMeansofpaymentTypeMockMvc.perform(get("/api/meansofpayment-types/{id}", meansofpaymentType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(meansofpaymentType.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingMeansofpaymentType() throws Exception {
        // Get the meansofpaymentType
        restMeansofpaymentTypeMockMvc.perform(get("/api/meansofpayment-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMeansofpaymentType() throws Exception {
        // Initialize the database
        meansofpaymentTypeRepository.saveAndFlush(meansofpaymentType);

        int databaseSizeBeforeUpdate = meansofpaymentTypeRepository.findAll().size();

        // Update the meansofpaymentType
        MeansofpaymentType updatedMeansofpaymentType = meansofpaymentTypeRepository.findById(meansofpaymentType.getId()).get();
        // Disconnect from session so that the updates on updatedMeansofpaymentType are not directly saved in db
        em.detach(updatedMeansofpaymentType);
        updatedMeansofpaymentType
            .code(UPDATED_CODE)
            .label(UPDATED_LABEL)
            .active(UPDATED_ACTIVE);
        MeansofpaymentTypeDTO meansofpaymentTypeDTO = meansofpaymentTypeMapper.toDto(updatedMeansofpaymentType);

        restMeansofpaymentTypeMockMvc.perform(put("/api/meansofpayment-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meansofpaymentTypeDTO)))
            .andExpect(status().isOk());

        // Validate the MeansofpaymentType in the database
        List<MeansofpaymentType> meansofpaymentTypeList = meansofpaymentTypeRepository.findAll();
        assertThat(meansofpaymentTypeList).hasSize(databaseSizeBeforeUpdate);
        MeansofpaymentType testMeansofpaymentType = meansofpaymentTypeList.get(meansofpaymentTypeList.size() - 1);
        assertThat(testMeansofpaymentType.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testMeansofpaymentType.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testMeansofpaymentType.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingMeansofpaymentType() throws Exception {
        int databaseSizeBeforeUpdate = meansofpaymentTypeRepository.findAll().size();

        // Create the MeansofpaymentType
        MeansofpaymentTypeDTO meansofpaymentTypeDTO = meansofpaymentTypeMapper.toDto(meansofpaymentType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMeansofpaymentTypeMockMvc.perform(put("/api/meansofpayment-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meansofpaymentTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MeansofpaymentType in the database
        List<MeansofpaymentType> meansofpaymentTypeList = meansofpaymentTypeRepository.findAll();
        assertThat(meansofpaymentTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMeansofpaymentType() throws Exception {
        // Initialize the database
        meansofpaymentTypeRepository.saveAndFlush(meansofpaymentType);

        int databaseSizeBeforeDelete = meansofpaymentTypeRepository.findAll().size();

        // Delete the meansofpaymentType
        restMeansofpaymentTypeMockMvc.perform(delete("/api/meansofpayment-types/{id}", meansofpaymentType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MeansofpaymentType> meansofpaymentTypeList = meansofpaymentTypeRepository.findAll();
        assertThat(meansofpaymentTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MeansofpaymentType.class);
        MeansofpaymentType meansofpaymentType1 = new MeansofpaymentType();
        meansofpaymentType1.setId(1L);
        MeansofpaymentType meansofpaymentType2 = new MeansofpaymentType();
        meansofpaymentType2.setId(meansofpaymentType1.getId());
        assertThat(meansofpaymentType1).isEqualTo(meansofpaymentType2);
        meansofpaymentType2.setId(2L);
        assertThat(meansofpaymentType1).isNotEqualTo(meansofpaymentType2);
        meansofpaymentType1.setId(null);
        assertThat(meansofpaymentType1).isNotEqualTo(meansofpaymentType2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MeansofpaymentTypeDTO.class);
        MeansofpaymentTypeDTO meansofpaymentTypeDTO1 = new MeansofpaymentTypeDTO();
        meansofpaymentTypeDTO1.setId(1L);
        MeansofpaymentTypeDTO meansofpaymentTypeDTO2 = new MeansofpaymentTypeDTO();
        assertThat(meansofpaymentTypeDTO1).isNotEqualTo(meansofpaymentTypeDTO2);
        meansofpaymentTypeDTO2.setId(meansofpaymentTypeDTO1.getId());
        assertThat(meansofpaymentTypeDTO1).isEqualTo(meansofpaymentTypeDTO2);
        meansofpaymentTypeDTO2.setId(2L);
        assertThat(meansofpaymentTypeDTO1).isNotEqualTo(meansofpaymentTypeDTO2);
        meansofpaymentTypeDTO1.setId(null);
        assertThat(meansofpaymentTypeDTO1).isNotEqualTo(meansofpaymentTypeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(meansofpaymentTypeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(meansofpaymentTypeMapper.fromId(null)).isNull();
    }
}
