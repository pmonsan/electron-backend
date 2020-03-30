package com.electron.mfs.pg.mdm.web.rest;

import com.electron.mfs.pg.mdm.MdmManagerApp;
import com.electron.mfs.pg.mdm.config.SecurityBeanOverrideConfiguration;
import com.electron.mfs.pg.mdm.domain.LoanInstalmentStatus;
import com.electron.mfs.pg.mdm.repository.LoanInstalmentStatusRepository;
import com.electron.mfs.pg.mdm.service.LoanInstalmentStatusService;
import com.electron.mfs.pg.mdm.service.dto.LoanInstalmentStatusDTO;
import com.electron.mfs.pg.mdm.service.mapper.LoanInstalmentStatusMapper;
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
 * Integration tests for the {@Link LoanInstalmentStatusResource} REST controller.
 */
@EmbeddedKafka
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, MdmManagerApp.class})
public class LoanInstalmentStatusResourceIT {

    private static final String DEFAULT_CODE = "AAAAA";
    private static final String UPDATED_CODE = "BBBBB";

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private LoanInstalmentStatusRepository loanInstalmentStatusRepository;

    @Autowired
    private LoanInstalmentStatusMapper loanInstalmentStatusMapper;

    @Autowired
    private LoanInstalmentStatusService loanInstalmentStatusService;

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

    private MockMvc restLoanInstalmentStatusMockMvc;

    private LoanInstalmentStatus loanInstalmentStatus;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LoanInstalmentStatusResource loanInstalmentStatusResource = new LoanInstalmentStatusResource(loanInstalmentStatusService);
        this.restLoanInstalmentStatusMockMvc = MockMvcBuilders.standaloneSetup(loanInstalmentStatusResource)
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
    public static LoanInstalmentStatus createEntity(EntityManager em) {
        LoanInstalmentStatus loanInstalmentStatus = new LoanInstalmentStatus()
            .code(DEFAULT_CODE)
            .label(DEFAULT_LABEL)
            .active(DEFAULT_ACTIVE);
        return loanInstalmentStatus;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LoanInstalmentStatus createUpdatedEntity(EntityManager em) {
        LoanInstalmentStatus loanInstalmentStatus = new LoanInstalmentStatus()
            .code(UPDATED_CODE)
            .label(UPDATED_LABEL)
            .active(UPDATED_ACTIVE);
        return loanInstalmentStatus;
    }

    @BeforeEach
    public void initTest() {
        loanInstalmentStatus = createEntity(em);
    }

    @Test
    @Transactional
    public void createLoanInstalmentStatus() throws Exception {
        int databaseSizeBeforeCreate = loanInstalmentStatusRepository.findAll().size();

        // Create the LoanInstalmentStatus
        LoanInstalmentStatusDTO loanInstalmentStatusDTO = loanInstalmentStatusMapper.toDto(loanInstalmentStatus);
        restLoanInstalmentStatusMockMvc.perform(post("/api/loan-instalment-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loanInstalmentStatusDTO)))
            .andExpect(status().isCreated());

        // Validate the LoanInstalmentStatus in the database
        List<LoanInstalmentStatus> loanInstalmentStatusList = loanInstalmentStatusRepository.findAll();
        assertThat(loanInstalmentStatusList).hasSize(databaseSizeBeforeCreate + 1);
        LoanInstalmentStatus testLoanInstalmentStatus = loanInstalmentStatusList.get(loanInstalmentStatusList.size() - 1);
        assertThat(testLoanInstalmentStatus.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testLoanInstalmentStatus.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testLoanInstalmentStatus.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createLoanInstalmentStatusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = loanInstalmentStatusRepository.findAll().size();

        // Create the LoanInstalmentStatus with an existing ID
        loanInstalmentStatus.setId(1L);
        LoanInstalmentStatusDTO loanInstalmentStatusDTO = loanInstalmentStatusMapper.toDto(loanInstalmentStatus);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLoanInstalmentStatusMockMvc.perform(post("/api/loan-instalment-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loanInstalmentStatusDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LoanInstalmentStatus in the database
        List<LoanInstalmentStatus> loanInstalmentStatusList = loanInstalmentStatusRepository.findAll();
        assertThat(loanInstalmentStatusList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = loanInstalmentStatusRepository.findAll().size();
        // set the field null
        loanInstalmentStatus.setCode(null);

        // Create the LoanInstalmentStatus, which fails.
        LoanInstalmentStatusDTO loanInstalmentStatusDTO = loanInstalmentStatusMapper.toDto(loanInstalmentStatus);

        restLoanInstalmentStatusMockMvc.perform(post("/api/loan-instalment-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loanInstalmentStatusDTO)))
            .andExpect(status().isBadRequest());

        List<LoanInstalmentStatus> loanInstalmentStatusList = loanInstalmentStatusRepository.findAll();
        assertThat(loanInstalmentStatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLabelIsRequired() throws Exception {
        int databaseSizeBeforeTest = loanInstalmentStatusRepository.findAll().size();
        // set the field null
        loanInstalmentStatus.setLabel(null);

        // Create the LoanInstalmentStatus, which fails.
        LoanInstalmentStatusDTO loanInstalmentStatusDTO = loanInstalmentStatusMapper.toDto(loanInstalmentStatus);

        restLoanInstalmentStatusMockMvc.perform(post("/api/loan-instalment-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loanInstalmentStatusDTO)))
            .andExpect(status().isBadRequest());

        List<LoanInstalmentStatus> loanInstalmentStatusList = loanInstalmentStatusRepository.findAll();
        assertThat(loanInstalmentStatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = loanInstalmentStatusRepository.findAll().size();
        // set the field null
        loanInstalmentStatus.setActive(null);

        // Create the LoanInstalmentStatus, which fails.
        LoanInstalmentStatusDTO loanInstalmentStatusDTO = loanInstalmentStatusMapper.toDto(loanInstalmentStatus);

        restLoanInstalmentStatusMockMvc.perform(post("/api/loan-instalment-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loanInstalmentStatusDTO)))
            .andExpect(status().isBadRequest());

        List<LoanInstalmentStatus> loanInstalmentStatusList = loanInstalmentStatusRepository.findAll();
        assertThat(loanInstalmentStatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLoanInstalmentStatuses() throws Exception {
        // Initialize the database
        loanInstalmentStatusRepository.saveAndFlush(loanInstalmentStatus);

        // Get all the loanInstalmentStatusList
        restLoanInstalmentStatusMockMvc.perform(get("/api/loan-instalment-statuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(loanInstalmentStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getLoanInstalmentStatus() throws Exception {
        // Initialize the database
        loanInstalmentStatusRepository.saveAndFlush(loanInstalmentStatus);

        // Get the loanInstalmentStatus
        restLoanInstalmentStatusMockMvc.perform(get("/api/loan-instalment-statuses/{id}", loanInstalmentStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(loanInstalmentStatus.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingLoanInstalmentStatus() throws Exception {
        // Get the loanInstalmentStatus
        restLoanInstalmentStatusMockMvc.perform(get("/api/loan-instalment-statuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLoanInstalmentStatus() throws Exception {
        // Initialize the database
        loanInstalmentStatusRepository.saveAndFlush(loanInstalmentStatus);

        int databaseSizeBeforeUpdate = loanInstalmentStatusRepository.findAll().size();

        // Update the loanInstalmentStatus
        LoanInstalmentStatus updatedLoanInstalmentStatus = loanInstalmentStatusRepository.findById(loanInstalmentStatus.getId()).get();
        // Disconnect from session so that the updates on updatedLoanInstalmentStatus are not directly saved in db
        em.detach(updatedLoanInstalmentStatus);
        updatedLoanInstalmentStatus
            .code(UPDATED_CODE)
            .label(UPDATED_LABEL)
            .active(UPDATED_ACTIVE);
        LoanInstalmentStatusDTO loanInstalmentStatusDTO = loanInstalmentStatusMapper.toDto(updatedLoanInstalmentStatus);

        restLoanInstalmentStatusMockMvc.perform(put("/api/loan-instalment-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loanInstalmentStatusDTO)))
            .andExpect(status().isOk());

        // Validate the LoanInstalmentStatus in the database
        List<LoanInstalmentStatus> loanInstalmentStatusList = loanInstalmentStatusRepository.findAll();
        assertThat(loanInstalmentStatusList).hasSize(databaseSizeBeforeUpdate);
        LoanInstalmentStatus testLoanInstalmentStatus = loanInstalmentStatusList.get(loanInstalmentStatusList.size() - 1);
        assertThat(testLoanInstalmentStatus.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testLoanInstalmentStatus.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testLoanInstalmentStatus.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingLoanInstalmentStatus() throws Exception {
        int databaseSizeBeforeUpdate = loanInstalmentStatusRepository.findAll().size();

        // Create the LoanInstalmentStatus
        LoanInstalmentStatusDTO loanInstalmentStatusDTO = loanInstalmentStatusMapper.toDto(loanInstalmentStatus);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLoanInstalmentStatusMockMvc.perform(put("/api/loan-instalment-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loanInstalmentStatusDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LoanInstalmentStatus in the database
        List<LoanInstalmentStatus> loanInstalmentStatusList = loanInstalmentStatusRepository.findAll();
        assertThat(loanInstalmentStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLoanInstalmentStatus() throws Exception {
        // Initialize the database
        loanInstalmentStatusRepository.saveAndFlush(loanInstalmentStatus);

        int databaseSizeBeforeDelete = loanInstalmentStatusRepository.findAll().size();

        // Delete the loanInstalmentStatus
        restLoanInstalmentStatusMockMvc.perform(delete("/api/loan-instalment-statuses/{id}", loanInstalmentStatus.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LoanInstalmentStatus> loanInstalmentStatusList = loanInstalmentStatusRepository.findAll();
        assertThat(loanInstalmentStatusList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LoanInstalmentStatus.class);
        LoanInstalmentStatus loanInstalmentStatus1 = new LoanInstalmentStatus();
        loanInstalmentStatus1.setId(1L);
        LoanInstalmentStatus loanInstalmentStatus2 = new LoanInstalmentStatus();
        loanInstalmentStatus2.setId(loanInstalmentStatus1.getId());
        assertThat(loanInstalmentStatus1).isEqualTo(loanInstalmentStatus2);
        loanInstalmentStatus2.setId(2L);
        assertThat(loanInstalmentStatus1).isNotEqualTo(loanInstalmentStatus2);
        loanInstalmentStatus1.setId(null);
        assertThat(loanInstalmentStatus1).isNotEqualTo(loanInstalmentStatus2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LoanInstalmentStatusDTO.class);
        LoanInstalmentStatusDTO loanInstalmentStatusDTO1 = new LoanInstalmentStatusDTO();
        loanInstalmentStatusDTO1.setId(1L);
        LoanInstalmentStatusDTO loanInstalmentStatusDTO2 = new LoanInstalmentStatusDTO();
        assertThat(loanInstalmentStatusDTO1).isNotEqualTo(loanInstalmentStatusDTO2);
        loanInstalmentStatusDTO2.setId(loanInstalmentStatusDTO1.getId());
        assertThat(loanInstalmentStatusDTO1).isEqualTo(loanInstalmentStatusDTO2);
        loanInstalmentStatusDTO2.setId(2L);
        assertThat(loanInstalmentStatusDTO1).isNotEqualTo(loanInstalmentStatusDTO2);
        loanInstalmentStatusDTO1.setId(null);
        assertThat(loanInstalmentStatusDTO1).isNotEqualTo(loanInstalmentStatusDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(loanInstalmentStatusMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(loanInstalmentStatusMapper.fromId(null)).isNull();
    }
}
