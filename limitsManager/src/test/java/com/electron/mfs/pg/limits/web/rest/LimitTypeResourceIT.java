package com.electron.mfs.pg.limits.web.rest;

import com.electron.mfs.pg.limits.LimitsManagerApp;
import com.electron.mfs.pg.limits.config.SecurityBeanOverrideConfiguration;
import com.electron.mfs.pg.limits.domain.LimitType;
import com.electron.mfs.pg.limits.repository.LimitTypeRepository;
import com.electron.mfs.pg.limits.service.LimitTypeService;
import com.electron.mfs.pg.limits.service.dto.LimitTypeDTO;
import com.electron.mfs.pg.limits.service.mapper.LimitTypeMapper;
import com.electron.mfs.pg.limits.web.rest.errors.ExceptionTranslator;

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

import static com.electron.mfs.pg.limits.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.electron.mfs.pg.limits.domain.enumeration.LimitValueType;
/**
 * Integration tests for the {@Link LimitTypeResource} REST controller.
 */
@EmbeddedKafka
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, LimitsManagerApp.class})
public class LimitTypeResourceIT {

    private static final String DEFAULT_CODE = "AAAAA";
    private static final String UPDATED_CODE = "BBBBB";

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final LimitValueType DEFAULT_LIMIT_VALUE_TYPE = LimitValueType.STRING;
    private static final LimitValueType UPDATED_LIMIT_VALUE_TYPE = LimitValueType.INTEGER;

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private LimitTypeRepository limitTypeRepository;

    @Autowired
    private LimitTypeMapper limitTypeMapper;

    @Autowired
    private LimitTypeService limitTypeService;

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

    private MockMvc restLimitTypeMockMvc;

    private LimitType limitType;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LimitTypeResource limitTypeResource = new LimitTypeResource(limitTypeService);
        this.restLimitTypeMockMvc = MockMvcBuilders.standaloneSetup(limitTypeResource)
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
    public static LimitType createEntity(EntityManager em) {
        LimitType limitType = new LimitType()
            .code(DEFAULT_CODE)
            .label(DEFAULT_LABEL)
            .limitValueType(DEFAULT_LIMIT_VALUE_TYPE)
            .active(DEFAULT_ACTIVE);
        return limitType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LimitType createUpdatedEntity(EntityManager em) {
        LimitType limitType = new LimitType()
            .code(UPDATED_CODE)
            .label(UPDATED_LABEL)
            .limitValueType(UPDATED_LIMIT_VALUE_TYPE)
            .active(UPDATED_ACTIVE);
        return limitType;
    }

    @BeforeEach
    public void initTest() {
        limitType = createEntity(em);
    }

    @Test
    @Transactional
    public void createLimitType() throws Exception {
        int databaseSizeBeforeCreate = limitTypeRepository.findAll().size();

        // Create the LimitType
        LimitTypeDTO limitTypeDTO = limitTypeMapper.toDto(limitType);
        restLimitTypeMockMvc.perform(post("/api/limit-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(limitTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the LimitType in the database
        List<LimitType> limitTypeList = limitTypeRepository.findAll();
        assertThat(limitTypeList).hasSize(databaseSizeBeforeCreate + 1);
        LimitType testLimitType = limitTypeList.get(limitTypeList.size() - 1);
        assertThat(testLimitType.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testLimitType.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testLimitType.getLimitValueType()).isEqualTo(DEFAULT_LIMIT_VALUE_TYPE);
        assertThat(testLimitType.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createLimitTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = limitTypeRepository.findAll().size();

        // Create the LimitType with an existing ID
        limitType.setId(1L);
        LimitTypeDTO limitTypeDTO = limitTypeMapper.toDto(limitType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLimitTypeMockMvc.perform(post("/api/limit-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(limitTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LimitType in the database
        List<LimitType> limitTypeList = limitTypeRepository.findAll();
        assertThat(limitTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = limitTypeRepository.findAll().size();
        // set the field null
        limitType.setCode(null);

        // Create the LimitType, which fails.
        LimitTypeDTO limitTypeDTO = limitTypeMapper.toDto(limitType);

        restLimitTypeMockMvc.perform(post("/api/limit-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(limitTypeDTO)))
            .andExpect(status().isBadRequest());

        List<LimitType> limitTypeList = limitTypeRepository.findAll();
        assertThat(limitTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLabelIsRequired() throws Exception {
        int databaseSizeBeforeTest = limitTypeRepository.findAll().size();
        // set the field null
        limitType.setLabel(null);

        // Create the LimitType, which fails.
        LimitTypeDTO limitTypeDTO = limitTypeMapper.toDto(limitType);

        restLimitTypeMockMvc.perform(post("/api/limit-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(limitTypeDTO)))
            .andExpect(status().isBadRequest());

        List<LimitType> limitTypeList = limitTypeRepository.findAll();
        assertThat(limitTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLimitValueTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = limitTypeRepository.findAll().size();
        // set the field null
        limitType.setLimitValueType(null);

        // Create the LimitType, which fails.
        LimitTypeDTO limitTypeDTO = limitTypeMapper.toDto(limitType);

        restLimitTypeMockMvc.perform(post("/api/limit-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(limitTypeDTO)))
            .andExpect(status().isBadRequest());

        List<LimitType> limitTypeList = limitTypeRepository.findAll();
        assertThat(limitTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = limitTypeRepository.findAll().size();
        // set the field null
        limitType.setActive(null);

        // Create the LimitType, which fails.
        LimitTypeDTO limitTypeDTO = limitTypeMapper.toDto(limitType);

        restLimitTypeMockMvc.perform(post("/api/limit-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(limitTypeDTO)))
            .andExpect(status().isBadRequest());

        List<LimitType> limitTypeList = limitTypeRepository.findAll();
        assertThat(limitTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLimitTypes() throws Exception {
        // Initialize the database
        limitTypeRepository.saveAndFlush(limitType);

        // Get all the limitTypeList
        restLimitTypeMockMvc.perform(get("/api/limit-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(limitType.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())))
            .andExpect(jsonPath("$.[*].limitValueType").value(hasItem(DEFAULT_LIMIT_VALUE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getLimitType() throws Exception {
        // Initialize the database
        limitTypeRepository.saveAndFlush(limitType);

        // Get the limitType
        restLimitTypeMockMvc.perform(get("/api/limit-types/{id}", limitType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(limitType.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()))
            .andExpect(jsonPath("$.limitValueType").value(DEFAULT_LIMIT_VALUE_TYPE.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingLimitType() throws Exception {
        // Get the limitType
        restLimitTypeMockMvc.perform(get("/api/limit-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLimitType() throws Exception {
        // Initialize the database
        limitTypeRepository.saveAndFlush(limitType);

        int databaseSizeBeforeUpdate = limitTypeRepository.findAll().size();

        // Update the limitType
        LimitType updatedLimitType = limitTypeRepository.findById(limitType.getId()).get();
        // Disconnect from session so that the updates on updatedLimitType are not directly saved in db
        em.detach(updatedLimitType);
        updatedLimitType
            .code(UPDATED_CODE)
            .label(UPDATED_LABEL)
            .limitValueType(UPDATED_LIMIT_VALUE_TYPE)
            .active(UPDATED_ACTIVE);
        LimitTypeDTO limitTypeDTO = limitTypeMapper.toDto(updatedLimitType);

        restLimitTypeMockMvc.perform(put("/api/limit-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(limitTypeDTO)))
            .andExpect(status().isOk());

        // Validate the LimitType in the database
        List<LimitType> limitTypeList = limitTypeRepository.findAll();
        assertThat(limitTypeList).hasSize(databaseSizeBeforeUpdate);
        LimitType testLimitType = limitTypeList.get(limitTypeList.size() - 1);
        assertThat(testLimitType.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testLimitType.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testLimitType.getLimitValueType()).isEqualTo(UPDATED_LIMIT_VALUE_TYPE);
        assertThat(testLimitType.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingLimitType() throws Exception {
        int databaseSizeBeforeUpdate = limitTypeRepository.findAll().size();

        // Create the LimitType
        LimitTypeDTO limitTypeDTO = limitTypeMapper.toDto(limitType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLimitTypeMockMvc.perform(put("/api/limit-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(limitTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LimitType in the database
        List<LimitType> limitTypeList = limitTypeRepository.findAll();
        assertThat(limitTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLimitType() throws Exception {
        // Initialize the database
        limitTypeRepository.saveAndFlush(limitType);

        int databaseSizeBeforeDelete = limitTypeRepository.findAll().size();

        // Delete the limitType
        restLimitTypeMockMvc.perform(delete("/api/limit-types/{id}", limitType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LimitType> limitTypeList = limitTypeRepository.findAll();
        assertThat(limitTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LimitType.class);
        LimitType limitType1 = new LimitType();
        limitType1.setId(1L);
        LimitType limitType2 = new LimitType();
        limitType2.setId(limitType1.getId());
        assertThat(limitType1).isEqualTo(limitType2);
        limitType2.setId(2L);
        assertThat(limitType1).isNotEqualTo(limitType2);
        limitType1.setId(null);
        assertThat(limitType1).isNotEqualTo(limitType2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LimitTypeDTO.class);
        LimitTypeDTO limitTypeDTO1 = new LimitTypeDTO();
        limitTypeDTO1.setId(1L);
        LimitTypeDTO limitTypeDTO2 = new LimitTypeDTO();
        assertThat(limitTypeDTO1).isNotEqualTo(limitTypeDTO2);
        limitTypeDTO2.setId(limitTypeDTO1.getId());
        assertThat(limitTypeDTO1).isEqualTo(limitTypeDTO2);
        limitTypeDTO2.setId(2L);
        assertThat(limitTypeDTO1).isNotEqualTo(limitTypeDTO2);
        limitTypeDTO1.setId(null);
        assertThat(limitTypeDTO1).isNotEqualTo(limitTypeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(limitTypeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(limitTypeMapper.fromId(null)).isNull();
    }
}
