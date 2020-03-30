package com.electron.mfs.pg.gateway.web.rest;

import com.electron.mfs.pg.gateway.PgkernelgatewayApp;
import com.electron.mfs.pg.gateway.config.SecurityBeanOverrideConfiguration;
import com.electron.mfs.pg.gateway.domain.TransactionGroup;
import com.electron.mfs.pg.gateway.repository.TransactionGroupRepository;
import com.electron.mfs.pg.gateway.service.TransactionGroupService;
import com.electron.mfs.pg.gateway.service.dto.TransactionGroupDTO;
import com.electron.mfs.pg.gateway.service.mapper.TransactionGroupMapper;
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
 * Integration tests for the {@Link TransactionGroupResource} REST controller.
 */
@EmbeddedKafka
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, PgkernelgatewayApp.class})
public class TransactionGroupResourceIT {

    private static final String DEFAULT_CODE = "AAAAA";
    private static final String UPDATED_CODE = "BBBBB";

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_CHECK_SUBSCRIPTION = false;
    private static final Boolean UPDATED_CHECK_SUBSCRIPTION = true;

    private static final Boolean DEFAULT_IGNORE_FEES = false;
    private static final Boolean UPDATED_IGNORE_FEES = true;

    private static final Boolean DEFAULT_IGNORE_LIMIT = false;
    private static final Boolean UPDATED_IGNORE_LIMIT = true;

    private static final Boolean DEFAULT_IGNORE_COMMISSION = false;
    private static final Boolean UPDATED_IGNORE_COMMISSION = true;

    private static final Boolean DEFAULT_CHECK_OTP = false;
    private static final Boolean UPDATED_CHECK_OTP = true;

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private TransactionGroupRepository transactionGroupRepository;

    @Autowired
    private TransactionGroupMapper transactionGroupMapper;

    @Autowired
    private TransactionGroupService transactionGroupService;

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

    private MockMvc restTransactionGroupMockMvc;

    private TransactionGroup transactionGroup;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TransactionGroupResource transactionGroupResource = new TransactionGroupResource(transactionGroupService);
        this.restTransactionGroupMockMvc = MockMvcBuilders.standaloneSetup(transactionGroupResource)
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
    public static TransactionGroup createEntity(EntityManager em) {
        TransactionGroup transactionGroup = new TransactionGroup()
            .code(DEFAULT_CODE)
            .label(DEFAULT_LABEL)
            .checkSubscription(DEFAULT_CHECK_SUBSCRIPTION)
            .ignoreFees(DEFAULT_IGNORE_FEES)
            .ignoreLimit(DEFAULT_IGNORE_LIMIT)
            .ignoreCommission(DEFAULT_IGNORE_COMMISSION)
            .checkOtp(DEFAULT_CHECK_OTP)
            .active(DEFAULT_ACTIVE);
        return transactionGroup;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TransactionGroup createUpdatedEntity(EntityManager em) {
        TransactionGroup transactionGroup = new TransactionGroup()
            .code(UPDATED_CODE)
            .label(UPDATED_LABEL)
            .checkSubscription(UPDATED_CHECK_SUBSCRIPTION)
            .ignoreFees(UPDATED_IGNORE_FEES)
            .ignoreLimit(UPDATED_IGNORE_LIMIT)
            .ignoreCommission(UPDATED_IGNORE_COMMISSION)
            .checkOtp(UPDATED_CHECK_OTP)
            .active(UPDATED_ACTIVE);
        return transactionGroup;
    }

    @BeforeEach
    public void initTest() {
        transactionGroup = createEntity(em);
    }

    @Test
    @Transactional
    public void createTransactionGroup() throws Exception {
        int databaseSizeBeforeCreate = transactionGroupRepository.findAll().size();

        // Create the TransactionGroup
        TransactionGroupDTO transactionGroupDTO = transactionGroupMapper.toDto(transactionGroup);
        restTransactionGroupMockMvc.perform(post("/api/transaction-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionGroupDTO)))
            .andExpect(status().isCreated());

        // Validate the TransactionGroup in the database
        List<TransactionGroup> transactionGroupList = transactionGroupRepository.findAll();
        assertThat(transactionGroupList).hasSize(databaseSizeBeforeCreate + 1);
        TransactionGroup testTransactionGroup = transactionGroupList.get(transactionGroupList.size() - 1);
        assertThat(testTransactionGroup.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testTransactionGroup.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testTransactionGroup.isCheckSubscription()).isEqualTo(DEFAULT_CHECK_SUBSCRIPTION);
        assertThat(testTransactionGroup.isIgnoreFees()).isEqualTo(DEFAULT_IGNORE_FEES);
        assertThat(testTransactionGroup.isIgnoreLimit()).isEqualTo(DEFAULT_IGNORE_LIMIT);
        assertThat(testTransactionGroup.isIgnoreCommission()).isEqualTo(DEFAULT_IGNORE_COMMISSION);
        assertThat(testTransactionGroup.isCheckOtp()).isEqualTo(DEFAULT_CHECK_OTP);
        assertThat(testTransactionGroup.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createTransactionGroupWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = transactionGroupRepository.findAll().size();

        // Create the TransactionGroup with an existing ID
        transactionGroup.setId(1L);
        TransactionGroupDTO transactionGroupDTO = transactionGroupMapper.toDto(transactionGroup);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransactionGroupMockMvc.perform(post("/api/transaction-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionGroupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TransactionGroup in the database
        List<TransactionGroup> transactionGroupList = transactionGroupRepository.findAll();
        assertThat(transactionGroupList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionGroupRepository.findAll().size();
        // set the field null
        transactionGroup.setCode(null);

        // Create the TransactionGroup, which fails.
        TransactionGroupDTO transactionGroupDTO = transactionGroupMapper.toDto(transactionGroup);

        restTransactionGroupMockMvc.perform(post("/api/transaction-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionGroupDTO)))
            .andExpect(status().isBadRequest());

        List<TransactionGroup> transactionGroupList = transactionGroupRepository.findAll();
        assertThat(transactionGroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLabelIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionGroupRepository.findAll().size();
        // set the field null
        transactionGroup.setLabel(null);

        // Create the TransactionGroup, which fails.
        TransactionGroupDTO transactionGroupDTO = transactionGroupMapper.toDto(transactionGroup);

        restTransactionGroupMockMvc.perform(post("/api/transaction-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionGroupDTO)))
            .andExpect(status().isBadRequest());

        List<TransactionGroup> transactionGroupList = transactionGroupRepository.findAll();
        assertThat(transactionGroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCheckSubscriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionGroupRepository.findAll().size();
        // set the field null
        transactionGroup.setCheckSubscription(null);

        // Create the TransactionGroup, which fails.
        TransactionGroupDTO transactionGroupDTO = transactionGroupMapper.toDto(transactionGroup);

        restTransactionGroupMockMvc.perform(post("/api/transaction-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionGroupDTO)))
            .andExpect(status().isBadRequest());

        List<TransactionGroup> transactionGroupList = transactionGroupRepository.findAll();
        assertThat(transactionGroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIgnoreFeesIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionGroupRepository.findAll().size();
        // set the field null
        transactionGroup.setIgnoreFees(null);

        // Create the TransactionGroup, which fails.
        TransactionGroupDTO transactionGroupDTO = transactionGroupMapper.toDto(transactionGroup);

        restTransactionGroupMockMvc.perform(post("/api/transaction-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionGroupDTO)))
            .andExpect(status().isBadRequest());

        List<TransactionGroup> transactionGroupList = transactionGroupRepository.findAll();
        assertThat(transactionGroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIgnoreLimitIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionGroupRepository.findAll().size();
        // set the field null
        transactionGroup.setIgnoreLimit(null);

        // Create the TransactionGroup, which fails.
        TransactionGroupDTO transactionGroupDTO = transactionGroupMapper.toDto(transactionGroup);

        restTransactionGroupMockMvc.perform(post("/api/transaction-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionGroupDTO)))
            .andExpect(status().isBadRequest());

        List<TransactionGroup> transactionGroupList = transactionGroupRepository.findAll();
        assertThat(transactionGroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIgnoreCommissionIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionGroupRepository.findAll().size();
        // set the field null
        transactionGroup.setIgnoreCommission(null);

        // Create the TransactionGroup, which fails.
        TransactionGroupDTO transactionGroupDTO = transactionGroupMapper.toDto(transactionGroup);

        restTransactionGroupMockMvc.perform(post("/api/transaction-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionGroupDTO)))
            .andExpect(status().isBadRequest());

        List<TransactionGroup> transactionGroupList = transactionGroupRepository.findAll();
        assertThat(transactionGroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCheckOtpIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionGroupRepository.findAll().size();
        // set the field null
        transactionGroup.setCheckOtp(null);

        // Create the TransactionGroup, which fails.
        TransactionGroupDTO transactionGroupDTO = transactionGroupMapper.toDto(transactionGroup);

        restTransactionGroupMockMvc.perform(post("/api/transaction-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionGroupDTO)))
            .andExpect(status().isBadRequest());

        List<TransactionGroup> transactionGroupList = transactionGroupRepository.findAll();
        assertThat(transactionGroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionGroupRepository.findAll().size();
        // set the field null
        transactionGroup.setActive(null);

        // Create the TransactionGroup, which fails.
        TransactionGroupDTO transactionGroupDTO = transactionGroupMapper.toDto(transactionGroup);

        restTransactionGroupMockMvc.perform(post("/api/transaction-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionGroupDTO)))
            .andExpect(status().isBadRequest());

        List<TransactionGroup> transactionGroupList = transactionGroupRepository.findAll();
        assertThat(transactionGroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTransactionGroups() throws Exception {
        // Initialize the database
        transactionGroupRepository.saveAndFlush(transactionGroup);

        // Get all the transactionGroupList
        restTransactionGroupMockMvc.perform(get("/api/transaction-groups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transactionGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())))
            .andExpect(jsonPath("$.[*].checkSubscription").value(hasItem(DEFAULT_CHECK_SUBSCRIPTION.booleanValue())))
            .andExpect(jsonPath("$.[*].ignoreFees").value(hasItem(DEFAULT_IGNORE_FEES.booleanValue())))
            .andExpect(jsonPath("$.[*].ignoreLimit").value(hasItem(DEFAULT_IGNORE_LIMIT.booleanValue())))
            .andExpect(jsonPath("$.[*].ignoreCommission").value(hasItem(DEFAULT_IGNORE_COMMISSION.booleanValue())))
            .andExpect(jsonPath("$.[*].checkOtp").value(hasItem(DEFAULT_CHECK_OTP.booleanValue())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getTransactionGroup() throws Exception {
        // Initialize the database
        transactionGroupRepository.saveAndFlush(transactionGroup);

        // Get the transactionGroup
        restTransactionGroupMockMvc.perform(get("/api/transaction-groups/{id}", transactionGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(transactionGroup.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()))
            .andExpect(jsonPath("$.checkSubscription").value(DEFAULT_CHECK_SUBSCRIPTION.booleanValue()))
            .andExpect(jsonPath("$.ignoreFees").value(DEFAULT_IGNORE_FEES.booleanValue()))
            .andExpect(jsonPath("$.ignoreLimit").value(DEFAULT_IGNORE_LIMIT.booleanValue()))
            .andExpect(jsonPath("$.ignoreCommission").value(DEFAULT_IGNORE_COMMISSION.booleanValue()))
            .andExpect(jsonPath("$.checkOtp").value(DEFAULT_CHECK_OTP.booleanValue()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTransactionGroup() throws Exception {
        // Get the transactionGroup
        restTransactionGroupMockMvc.perform(get("/api/transaction-groups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTransactionGroup() throws Exception {
        // Initialize the database
        transactionGroupRepository.saveAndFlush(transactionGroup);

        int databaseSizeBeforeUpdate = transactionGroupRepository.findAll().size();

        // Update the transactionGroup
        TransactionGroup updatedTransactionGroup = transactionGroupRepository.findById(transactionGroup.getId()).get();
        // Disconnect from session so that the updates on updatedTransactionGroup are not directly saved in db
        em.detach(updatedTransactionGroup);
        updatedTransactionGroup
            .code(UPDATED_CODE)
            .label(UPDATED_LABEL)
            .checkSubscription(UPDATED_CHECK_SUBSCRIPTION)
            .ignoreFees(UPDATED_IGNORE_FEES)
            .ignoreLimit(UPDATED_IGNORE_LIMIT)
            .ignoreCommission(UPDATED_IGNORE_COMMISSION)
            .checkOtp(UPDATED_CHECK_OTP)
            .active(UPDATED_ACTIVE);
        TransactionGroupDTO transactionGroupDTO = transactionGroupMapper.toDto(updatedTransactionGroup);

        restTransactionGroupMockMvc.perform(put("/api/transaction-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionGroupDTO)))
            .andExpect(status().isOk());

        // Validate the TransactionGroup in the database
        List<TransactionGroup> transactionGroupList = transactionGroupRepository.findAll();
        assertThat(transactionGroupList).hasSize(databaseSizeBeforeUpdate);
        TransactionGroup testTransactionGroup = transactionGroupList.get(transactionGroupList.size() - 1);
        assertThat(testTransactionGroup.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTransactionGroup.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testTransactionGroup.isCheckSubscription()).isEqualTo(UPDATED_CHECK_SUBSCRIPTION);
        assertThat(testTransactionGroup.isIgnoreFees()).isEqualTo(UPDATED_IGNORE_FEES);
        assertThat(testTransactionGroup.isIgnoreLimit()).isEqualTo(UPDATED_IGNORE_LIMIT);
        assertThat(testTransactionGroup.isIgnoreCommission()).isEqualTo(UPDATED_IGNORE_COMMISSION);
        assertThat(testTransactionGroup.isCheckOtp()).isEqualTo(UPDATED_CHECK_OTP);
        assertThat(testTransactionGroup.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingTransactionGroup() throws Exception {
        int databaseSizeBeforeUpdate = transactionGroupRepository.findAll().size();

        // Create the TransactionGroup
        TransactionGroupDTO transactionGroupDTO = transactionGroupMapper.toDto(transactionGroup);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransactionGroupMockMvc.perform(put("/api/transaction-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionGroupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TransactionGroup in the database
        List<TransactionGroup> transactionGroupList = transactionGroupRepository.findAll();
        assertThat(transactionGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTransactionGroup() throws Exception {
        // Initialize the database
        transactionGroupRepository.saveAndFlush(transactionGroup);

        int databaseSizeBeforeDelete = transactionGroupRepository.findAll().size();

        // Delete the transactionGroup
        restTransactionGroupMockMvc.perform(delete("/api/transaction-groups/{id}", transactionGroup.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TransactionGroup> transactionGroupList = transactionGroupRepository.findAll();
        assertThat(transactionGroupList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TransactionGroup.class);
        TransactionGroup transactionGroup1 = new TransactionGroup();
        transactionGroup1.setId(1L);
        TransactionGroup transactionGroup2 = new TransactionGroup();
        transactionGroup2.setId(transactionGroup1.getId());
        assertThat(transactionGroup1).isEqualTo(transactionGroup2);
        transactionGroup2.setId(2L);
        assertThat(transactionGroup1).isNotEqualTo(transactionGroup2);
        transactionGroup1.setId(null);
        assertThat(transactionGroup1).isNotEqualTo(transactionGroup2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TransactionGroupDTO.class);
        TransactionGroupDTO transactionGroupDTO1 = new TransactionGroupDTO();
        transactionGroupDTO1.setId(1L);
        TransactionGroupDTO transactionGroupDTO2 = new TransactionGroupDTO();
        assertThat(transactionGroupDTO1).isNotEqualTo(transactionGroupDTO2);
        transactionGroupDTO2.setId(transactionGroupDTO1.getId());
        assertThat(transactionGroupDTO1).isEqualTo(transactionGroupDTO2);
        transactionGroupDTO2.setId(2L);
        assertThat(transactionGroupDTO1).isNotEqualTo(transactionGroupDTO2);
        transactionGroupDTO1.setId(null);
        assertThat(transactionGroupDTO1).isNotEqualTo(transactionGroupDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(transactionGroupMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(transactionGroupMapper.fromId(null)).isNull();
    }
}
