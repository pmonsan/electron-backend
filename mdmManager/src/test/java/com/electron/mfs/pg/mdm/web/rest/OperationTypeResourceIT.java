package com.electron.mfs.pg.mdm.web.rest;

import com.electron.mfs.pg.mdm.MdmManagerApp;
import com.electron.mfs.pg.mdm.config.SecurityBeanOverrideConfiguration;
import com.electron.mfs.pg.mdm.domain.OperationType;
import com.electron.mfs.pg.mdm.repository.OperationTypeRepository;
import com.electron.mfs.pg.mdm.service.OperationTypeService;
import com.electron.mfs.pg.mdm.service.dto.OperationTypeDTO;
import com.electron.mfs.pg.mdm.service.mapper.OperationTypeMapper;
import com.electron.mfs.pg.mdm.web.rest.errors.ExceptionTranslator;

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

import static com.electron.mfs.pg.mdm.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link OperationTypeResource} REST controller.
 */
@EmbeddedKafka
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, MdmManagerApp.class})
public class OperationTypeResourceIT {

    private static final String DEFAULT_CODE = "AAAAA";
    private static final String UPDATED_CODE = "BBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    @Autowired
    private OperationTypeRepository operationTypeRepository;

    @Autowired
    private OperationTypeMapper operationTypeMapper;

    @Autowired
    private OperationTypeService operationTypeService;

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

    private MockMvc restOperationTypeMockMvc;

    private OperationType operationType;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OperationTypeResource operationTypeResource = new OperationTypeResource(operationTypeService);
        this.restOperationTypeMockMvc = MockMvcBuilders.standaloneSetup(operationTypeResource)
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
    public static OperationType createEntity(EntityManager em) {
        OperationType operationType = new OperationType()
            .code(DEFAULT_CODE)
            .active(DEFAULT_ACTIVE)
            .label(DEFAULT_LABEL);
        return operationType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OperationType createUpdatedEntity(EntityManager em) {
        OperationType operationType = new OperationType()
            .code(UPDATED_CODE)
            .active(UPDATED_ACTIVE)
            .label(UPDATED_LABEL);
        return operationType;
    }

    @BeforeEach
    public void initTest() {
        operationType = createEntity(em);
    }

    @Test
    @Transactional
    public void createOperationType() throws Exception {
        int databaseSizeBeforeCreate = operationTypeRepository.findAll().size();

        // Create the OperationType
        OperationTypeDTO operationTypeDTO = operationTypeMapper.toDto(operationType);
        restOperationTypeMockMvc.perform(post("/api/operation-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(operationTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the OperationType in the database
        List<OperationType> operationTypeList = operationTypeRepository.findAll();
        assertThat(operationTypeList).hasSize(databaseSizeBeforeCreate + 1);
        OperationType testOperationType = operationTypeList.get(operationTypeList.size() - 1);
        assertThat(testOperationType.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testOperationType.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testOperationType.getLabel()).isEqualTo(DEFAULT_LABEL);
    }

    @Test
    @Transactional
    public void createOperationTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = operationTypeRepository.findAll().size();

        // Create the OperationType with an existing ID
        operationType.setId(1L);
        OperationTypeDTO operationTypeDTO = operationTypeMapper.toDto(operationType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOperationTypeMockMvc.perform(post("/api/operation-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(operationTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OperationType in the database
        List<OperationType> operationTypeList = operationTypeRepository.findAll();
        assertThat(operationTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = operationTypeRepository.findAll().size();
        // set the field null
        operationType.setCode(null);

        // Create the OperationType, which fails.
        OperationTypeDTO operationTypeDTO = operationTypeMapper.toDto(operationType);

        restOperationTypeMockMvc.perform(post("/api/operation-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(operationTypeDTO)))
            .andExpect(status().isBadRequest());

        List<OperationType> operationTypeList = operationTypeRepository.findAll();
        assertThat(operationTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = operationTypeRepository.findAll().size();
        // set the field null
        operationType.setActive(null);

        // Create the OperationType, which fails.
        OperationTypeDTO operationTypeDTO = operationTypeMapper.toDto(operationType);

        restOperationTypeMockMvc.perform(post("/api/operation-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(operationTypeDTO)))
            .andExpect(status().isBadRequest());

        List<OperationType> operationTypeList = operationTypeRepository.findAll();
        assertThat(operationTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLabelIsRequired() throws Exception {
        int databaseSizeBeforeTest = operationTypeRepository.findAll().size();
        // set the field null
        operationType.setLabel(null);

        // Create the OperationType, which fails.
        OperationTypeDTO operationTypeDTO = operationTypeMapper.toDto(operationType);

        restOperationTypeMockMvc.perform(post("/api/operation-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(operationTypeDTO)))
            .andExpect(status().isBadRequest());

        List<OperationType> operationTypeList = operationTypeRepository.findAll();
        assertThat(operationTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOperationTypes() throws Exception {
        // Initialize the database
        operationTypeRepository.saveAndFlush(operationType);

        // Get all the operationTypeList
        restOperationTypeMockMvc.perform(get("/api/operation-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(operationType.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())));
    }
    
    @Test
    @Transactional
    public void getOperationType() throws Exception {
        // Initialize the database
        operationTypeRepository.saveAndFlush(operationType);

        // Get the operationType
        restOperationTypeMockMvc.perform(get("/api/operation-types/{id}", operationType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(operationType.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOperationType() throws Exception {
        // Get the operationType
        restOperationTypeMockMvc.perform(get("/api/operation-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOperationType() throws Exception {
        // Initialize the database
        operationTypeRepository.saveAndFlush(operationType);

        int databaseSizeBeforeUpdate = operationTypeRepository.findAll().size();

        // Update the operationType
        OperationType updatedOperationType = operationTypeRepository.findById(operationType.getId()).get();
        // Disconnect from session so that the updates on updatedOperationType are not directly saved in db
        em.detach(updatedOperationType);
        updatedOperationType
            .code(UPDATED_CODE)
            .active(UPDATED_ACTIVE)
            .label(UPDATED_LABEL);
        OperationTypeDTO operationTypeDTO = operationTypeMapper.toDto(updatedOperationType);

        restOperationTypeMockMvc.perform(put("/api/operation-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(operationTypeDTO)))
            .andExpect(status().isOk());

        // Validate the OperationType in the database
        List<OperationType> operationTypeList = operationTypeRepository.findAll();
        assertThat(operationTypeList).hasSize(databaseSizeBeforeUpdate);
        OperationType testOperationType = operationTypeList.get(operationTypeList.size() - 1);
        assertThat(testOperationType.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testOperationType.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testOperationType.getLabel()).isEqualTo(UPDATED_LABEL);
    }

    @Test
    @Transactional
    public void updateNonExistingOperationType() throws Exception {
        int databaseSizeBeforeUpdate = operationTypeRepository.findAll().size();

        // Create the OperationType
        OperationTypeDTO operationTypeDTO = operationTypeMapper.toDto(operationType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOperationTypeMockMvc.perform(put("/api/operation-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(operationTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OperationType in the database
        List<OperationType> operationTypeList = operationTypeRepository.findAll();
        assertThat(operationTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOperationType() throws Exception {
        // Initialize the database
        operationTypeRepository.saveAndFlush(operationType);

        int databaseSizeBeforeDelete = operationTypeRepository.findAll().size();

        // Delete the operationType
        restOperationTypeMockMvc.perform(delete("/api/operation-types/{id}", operationType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OperationType> operationTypeList = operationTypeRepository.findAll();
        assertThat(operationTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OperationType.class);
        OperationType operationType1 = new OperationType();
        operationType1.setId(1L);
        OperationType operationType2 = new OperationType();
        operationType2.setId(operationType1.getId());
        assertThat(operationType1).isEqualTo(operationType2);
        operationType2.setId(2L);
        assertThat(operationType1).isNotEqualTo(operationType2);
        operationType1.setId(null);
        assertThat(operationType1).isNotEqualTo(operationType2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OperationTypeDTO.class);
        OperationTypeDTO operationTypeDTO1 = new OperationTypeDTO();
        operationTypeDTO1.setId(1L);
        OperationTypeDTO operationTypeDTO2 = new OperationTypeDTO();
        assertThat(operationTypeDTO1).isNotEqualTo(operationTypeDTO2);
        operationTypeDTO2.setId(operationTypeDTO1.getId());
        assertThat(operationTypeDTO1).isEqualTo(operationTypeDTO2);
        operationTypeDTO2.setId(2L);
        assertThat(operationTypeDTO1).isNotEqualTo(operationTypeDTO2);
        operationTypeDTO1.setId(null);
        assertThat(operationTypeDTO1).isNotEqualTo(operationTypeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(operationTypeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(operationTypeMapper.fromId(null)).isNull();
    }
}
