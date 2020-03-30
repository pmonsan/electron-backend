package com.electron.mfs.pg.mdm.web.rest;

import com.electron.mfs.pg.mdm.MdmManagerApp;
import com.electron.mfs.pg.mdm.config.SecurityBeanOverrideConfiguration;
import com.electron.mfs.pg.mdm.domain.OperationStatus;
import com.electron.mfs.pg.mdm.repository.OperationStatusRepository;
import com.electron.mfs.pg.mdm.service.OperationStatusService;
import com.electron.mfs.pg.mdm.service.dto.OperationStatusDTO;
import com.electron.mfs.pg.mdm.service.mapper.OperationStatusMapper;
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
 * Integration tests for the {@Link OperationStatusResource} REST controller.
 */
@EmbeddedKafka
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, MdmManagerApp.class})
public class OperationStatusResourceIT {

    private static final String DEFAULT_CODE = "AAAAA";
    private static final String UPDATED_CODE = "BBBBB";

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private OperationStatusRepository operationStatusRepository;

    @Autowired
    private OperationStatusMapper operationStatusMapper;

    @Autowired
    private OperationStatusService operationStatusService;

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

    private MockMvc restOperationStatusMockMvc;

    private OperationStatus operationStatus;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OperationStatusResource operationStatusResource = new OperationStatusResource(operationStatusService);
        this.restOperationStatusMockMvc = MockMvcBuilders.standaloneSetup(operationStatusResource)
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
    public static OperationStatus createEntity(EntityManager em) {
        OperationStatus operationStatus = new OperationStatus()
            .code(DEFAULT_CODE)
            .label(DEFAULT_LABEL)
            .active(DEFAULT_ACTIVE);
        return operationStatus;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OperationStatus createUpdatedEntity(EntityManager em) {
        OperationStatus operationStatus = new OperationStatus()
            .code(UPDATED_CODE)
            .label(UPDATED_LABEL)
            .active(UPDATED_ACTIVE);
        return operationStatus;
    }

    @BeforeEach
    public void initTest() {
        operationStatus = createEntity(em);
    }

    @Test
    @Transactional
    public void createOperationStatus() throws Exception {
        int databaseSizeBeforeCreate = operationStatusRepository.findAll().size();

        // Create the OperationStatus
        OperationStatusDTO operationStatusDTO = operationStatusMapper.toDto(operationStatus);
        restOperationStatusMockMvc.perform(post("/api/operation-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(operationStatusDTO)))
            .andExpect(status().isCreated());

        // Validate the OperationStatus in the database
        List<OperationStatus> operationStatusList = operationStatusRepository.findAll();
        assertThat(operationStatusList).hasSize(databaseSizeBeforeCreate + 1);
        OperationStatus testOperationStatus = operationStatusList.get(operationStatusList.size() - 1);
        assertThat(testOperationStatus.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testOperationStatus.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testOperationStatus.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createOperationStatusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = operationStatusRepository.findAll().size();

        // Create the OperationStatus with an existing ID
        operationStatus.setId(1L);
        OperationStatusDTO operationStatusDTO = operationStatusMapper.toDto(operationStatus);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOperationStatusMockMvc.perform(post("/api/operation-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(operationStatusDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OperationStatus in the database
        List<OperationStatus> operationStatusList = operationStatusRepository.findAll();
        assertThat(operationStatusList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = operationStatusRepository.findAll().size();
        // set the field null
        operationStatus.setCode(null);

        // Create the OperationStatus, which fails.
        OperationStatusDTO operationStatusDTO = operationStatusMapper.toDto(operationStatus);

        restOperationStatusMockMvc.perform(post("/api/operation-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(operationStatusDTO)))
            .andExpect(status().isBadRequest());

        List<OperationStatus> operationStatusList = operationStatusRepository.findAll();
        assertThat(operationStatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLabelIsRequired() throws Exception {
        int databaseSizeBeforeTest = operationStatusRepository.findAll().size();
        // set the field null
        operationStatus.setLabel(null);

        // Create the OperationStatus, which fails.
        OperationStatusDTO operationStatusDTO = operationStatusMapper.toDto(operationStatus);

        restOperationStatusMockMvc.perform(post("/api/operation-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(operationStatusDTO)))
            .andExpect(status().isBadRequest());

        List<OperationStatus> operationStatusList = operationStatusRepository.findAll();
        assertThat(operationStatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = operationStatusRepository.findAll().size();
        // set the field null
        operationStatus.setActive(null);

        // Create the OperationStatus, which fails.
        OperationStatusDTO operationStatusDTO = operationStatusMapper.toDto(operationStatus);

        restOperationStatusMockMvc.perform(post("/api/operation-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(operationStatusDTO)))
            .andExpect(status().isBadRequest());

        List<OperationStatus> operationStatusList = operationStatusRepository.findAll();
        assertThat(operationStatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOperationStatuses() throws Exception {
        // Initialize the database
        operationStatusRepository.saveAndFlush(operationStatus);

        // Get all the operationStatusList
        restOperationStatusMockMvc.perform(get("/api/operation-statuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(operationStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getOperationStatus() throws Exception {
        // Initialize the database
        operationStatusRepository.saveAndFlush(operationStatus);

        // Get the operationStatus
        restOperationStatusMockMvc.perform(get("/api/operation-statuses/{id}", operationStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(operationStatus.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingOperationStatus() throws Exception {
        // Get the operationStatus
        restOperationStatusMockMvc.perform(get("/api/operation-statuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOperationStatus() throws Exception {
        // Initialize the database
        operationStatusRepository.saveAndFlush(operationStatus);

        int databaseSizeBeforeUpdate = operationStatusRepository.findAll().size();

        // Update the operationStatus
        OperationStatus updatedOperationStatus = operationStatusRepository.findById(operationStatus.getId()).get();
        // Disconnect from session so that the updates on updatedOperationStatus are not directly saved in db
        em.detach(updatedOperationStatus);
        updatedOperationStatus
            .code(UPDATED_CODE)
            .label(UPDATED_LABEL)
            .active(UPDATED_ACTIVE);
        OperationStatusDTO operationStatusDTO = operationStatusMapper.toDto(updatedOperationStatus);

        restOperationStatusMockMvc.perform(put("/api/operation-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(operationStatusDTO)))
            .andExpect(status().isOk());

        // Validate the OperationStatus in the database
        List<OperationStatus> operationStatusList = operationStatusRepository.findAll();
        assertThat(operationStatusList).hasSize(databaseSizeBeforeUpdate);
        OperationStatus testOperationStatus = operationStatusList.get(operationStatusList.size() - 1);
        assertThat(testOperationStatus.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testOperationStatus.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testOperationStatus.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingOperationStatus() throws Exception {
        int databaseSizeBeforeUpdate = operationStatusRepository.findAll().size();

        // Create the OperationStatus
        OperationStatusDTO operationStatusDTO = operationStatusMapper.toDto(operationStatus);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOperationStatusMockMvc.perform(put("/api/operation-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(operationStatusDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OperationStatus in the database
        List<OperationStatus> operationStatusList = operationStatusRepository.findAll();
        assertThat(operationStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOperationStatus() throws Exception {
        // Initialize the database
        operationStatusRepository.saveAndFlush(operationStatus);

        int databaseSizeBeforeDelete = operationStatusRepository.findAll().size();

        // Delete the operationStatus
        restOperationStatusMockMvc.perform(delete("/api/operation-statuses/{id}", operationStatus.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OperationStatus> operationStatusList = operationStatusRepository.findAll();
        assertThat(operationStatusList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OperationStatus.class);
        OperationStatus operationStatus1 = new OperationStatus();
        operationStatus1.setId(1L);
        OperationStatus operationStatus2 = new OperationStatus();
        operationStatus2.setId(operationStatus1.getId());
        assertThat(operationStatus1).isEqualTo(operationStatus2);
        operationStatus2.setId(2L);
        assertThat(operationStatus1).isNotEqualTo(operationStatus2);
        operationStatus1.setId(null);
        assertThat(operationStatus1).isNotEqualTo(operationStatus2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OperationStatusDTO.class);
        OperationStatusDTO operationStatusDTO1 = new OperationStatusDTO();
        operationStatusDTO1.setId(1L);
        OperationStatusDTO operationStatusDTO2 = new OperationStatusDTO();
        assertThat(operationStatusDTO1).isNotEqualTo(operationStatusDTO2);
        operationStatusDTO2.setId(operationStatusDTO1.getId());
        assertThat(operationStatusDTO1).isEqualTo(operationStatusDTO2);
        operationStatusDTO2.setId(2L);
        assertThat(operationStatusDTO1).isNotEqualTo(operationStatusDTO2);
        operationStatusDTO1.setId(null);
        assertThat(operationStatusDTO1).isNotEqualTo(operationStatusDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(operationStatusMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(operationStatusMapper.fromId(null)).isNull();
    }
}
