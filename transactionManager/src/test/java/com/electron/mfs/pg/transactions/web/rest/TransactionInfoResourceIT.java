package com.electron.mfs.pg.transactions.web.rest;

import com.electron.mfs.pg.transactions.TransactionManagerApp;
import com.electron.mfs.pg.transactions.config.SecurityBeanOverrideConfiguration;
import com.electron.mfs.pg.transactions.domain.TransactionInfo;
import com.electron.mfs.pg.transactions.repository.TransactionInfoRepository;
import com.electron.mfs.pg.transactions.service.TransactionInfoService;
import com.electron.mfs.pg.transactions.service.dto.TransactionInfoDTO;
import com.electron.mfs.pg.transactions.service.mapper.TransactionInfoMapper;
import com.electron.mfs.pg.transactions.web.rest.errors.ExceptionTranslator;

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

import static com.electron.mfs.pg.transactions.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link TransactionInfoResource} REST controller.
 */
@EmbeddedKafka
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, TransactionManagerApp.class})
public class TransactionInfoResourceIT {

    private static final String DEFAULT_TRANSACTION_PROPERTY_CODE = "AAAAA";
    private static final String UPDATED_TRANSACTION_PROPERTY_CODE = "BBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private TransactionInfoRepository transactionInfoRepository;

    @Autowired
    private TransactionInfoMapper transactionInfoMapper;

    @Autowired
    private TransactionInfoService transactionInfoService;

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

    private MockMvc restTransactionInfoMockMvc;

    private TransactionInfo transactionInfo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TransactionInfoResource transactionInfoResource = new TransactionInfoResource(transactionInfoService);
        this.restTransactionInfoMockMvc = MockMvcBuilders.standaloneSetup(transactionInfoResource)
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
    public static TransactionInfo createEntity(EntityManager em) {
        TransactionInfo transactionInfo = new TransactionInfo()
            .transactionPropertyCode(DEFAULT_TRANSACTION_PROPERTY_CODE)
            .value(DEFAULT_VALUE)
            .active(DEFAULT_ACTIVE);
        return transactionInfo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TransactionInfo createUpdatedEntity(EntityManager em) {
        TransactionInfo transactionInfo = new TransactionInfo()
            .transactionPropertyCode(UPDATED_TRANSACTION_PROPERTY_CODE)
            .value(UPDATED_VALUE)
            .active(UPDATED_ACTIVE);
        return transactionInfo;
    }

    @BeforeEach
    public void initTest() {
        transactionInfo = createEntity(em);
    }

    @Test
    @Transactional
    public void createTransactionInfo() throws Exception {
        int databaseSizeBeforeCreate = transactionInfoRepository.findAll().size();

        // Create the TransactionInfo
        TransactionInfoDTO transactionInfoDTO = transactionInfoMapper.toDto(transactionInfo);
        restTransactionInfoMockMvc.perform(post("/api/transaction-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionInfoDTO)))
            .andExpect(status().isCreated());

        // Validate the TransactionInfo in the database
        List<TransactionInfo> transactionInfoList = transactionInfoRepository.findAll();
        assertThat(transactionInfoList).hasSize(databaseSizeBeforeCreate + 1);
        TransactionInfo testTransactionInfo = transactionInfoList.get(transactionInfoList.size() - 1);
        assertThat(testTransactionInfo.getTransactionPropertyCode()).isEqualTo(DEFAULT_TRANSACTION_PROPERTY_CODE);
        assertThat(testTransactionInfo.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testTransactionInfo.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createTransactionInfoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = transactionInfoRepository.findAll().size();

        // Create the TransactionInfo with an existing ID
        transactionInfo.setId(1L);
        TransactionInfoDTO transactionInfoDTO = transactionInfoMapper.toDto(transactionInfo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransactionInfoMockMvc.perform(post("/api/transaction-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionInfoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TransactionInfo in the database
        List<TransactionInfo> transactionInfoList = transactionInfoRepository.findAll();
        assertThat(transactionInfoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTransactionPropertyCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionInfoRepository.findAll().size();
        // set the field null
        transactionInfo.setTransactionPropertyCode(null);

        // Create the TransactionInfo, which fails.
        TransactionInfoDTO transactionInfoDTO = transactionInfoMapper.toDto(transactionInfo);

        restTransactionInfoMockMvc.perform(post("/api/transaction-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionInfoDTO)))
            .andExpect(status().isBadRequest());

        List<TransactionInfo> transactionInfoList = transactionInfoRepository.findAll();
        assertThat(transactionInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionInfoRepository.findAll().size();
        // set the field null
        transactionInfo.setValue(null);

        // Create the TransactionInfo, which fails.
        TransactionInfoDTO transactionInfoDTO = transactionInfoMapper.toDto(transactionInfo);

        restTransactionInfoMockMvc.perform(post("/api/transaction-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionInfoDTO)))
            .andExpect(status().isBadRequest());

        List<TransactionInfo> transactionInfoList = transactionInfoRepository.findAll();
        assertThat(transactionInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionInfoRepository.findAll().size();
        // set the field null
        transactionInfo.setActive(null);

        // Create the TransactionInfo, which fails.
        TransactionInfoDTO transactionInfoDTO = transactionInfoMapper.toDto(transactionInfo);

        restTransactionInfoMockMvc.perform(post("/api/transaction-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionInfoDTO)))
            .andExpect(status().isBadRequest());

        List<TransactionInfo> transactionInfoList = transactionInfoRepository.findAll();
        assertThat(transactionInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTransactionInfos() throws Exception {
        // Initialize the database
        transactionInfoRepository.saveAndFlush(transactionInfo);

        // Get all the transactionInfoList
        restTransactionInfoMockMvc.perform(get("/api/transaction-infos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transactionInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].transactionPropertyCode").value(hasItem(DEFAULT_TRANSACTION_PROPERTY_CODE.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getTransactionInfo() throws Exception {
        // Initialize the database
        transactionInfoRepository.saveAndFlush(transactionInfo);

        // Get the transactionInfo
        restTransactionInfoMockMvc.perform(get("/api/transaction-infos/{id}", transactionInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(transactionInfo.getId().intValue()))
            .andExpect(jsonPath("$.transactionPropertyCode").value(DEFAULT_TRANSACTION_PROPERTY_CODE.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTransactionInfo() throws Exception {
        // Get the transactionInfo
        restTransactionInfoMockMvc.perform(get("/api/transaction-infos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTransactionInfo() throws Exception {
        // Initialize the database
        transactionInfoRepository.saveAndFlush(transactionInfo);

        int databaseSizeBeforeUpdate = transactionInfoRepository.findAll().size();

        // Update the transactionInfo
        TransactionInfo updatedTransactionInfo = transactionInfoRepository.findById(transactionInfo.getId()).get();
        // Disconnect from session so that the updates on updatedTransactionInfo are not directly saved in db
        em.detach(updatedTransactionInfo);
        updatedTransactionInfo
            .transactionPropertyCode(UPDATED_TRANSACTION_PROPERTY_CODE)
            .value(UPDATED_VALUE)
            .active(UPDATED_ACTIVE);
        TransactionInfoDTO transactionInfoDTO = transactionInfoMapper.toDto(updatedTransactionInfo);

        restTransactionInfoMockMvc.perform(put("/api/transaction-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionInfoDTO)))
            .andExpect(status().isOk());

        // Validate the TransactionInfo in the database
        List<TransactionInfo> transactionInfoList = transactionInfoRepository.findAll();
        assertThat(transactionInfoList).hasSize(databaseSizeBeforeUpdate);
        TransactionInfo testTransactionInfo = transactionInfoList.get(transactionInfoList.size() - 1);
        assertThat(testTransactionInfo.getTransactionPropertyCode()).isEqualTo(UPDATED_TRANSACTION_PROPERTY_CODE);
        assertThat(testTransactionInfo.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testTransactionInfo.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingTransactionInfo() throws Exception {
        int databaseSizeBeforeUpdate = transactionInfoRepository.findAll().size();

        // Create the TransactionInfo
        TransactionInfoDTO transactionInfoDTO = transactionInfoMapper.toDto(transactionInfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransactionInfoMockMvc.perform(put("/api/transaction-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionInfoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TransactionInfo in the database
        List<TransactionInfo> transactionInfoList = transactionInfoRepository.findAll();
        assertThat(transactionInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTransactionInfo() throws Exception {
        // Initialize the database
        transactionInfoRepository.saveAndFlush(transactionInfo);

        int databaseSizeBeforeDelete = transactionInfoRepository.findAll().size();

        // Delete the transactionInfo
        restTransactionInfoMockMvc.perform(delete("/api/transaction-infos/{id}", transactionInfo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TransactionInfo> transactionInfoList = transactionInfoRepository.findAll();
        assertThat(transactionInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TransactionInfo.class);
        TransactionInfo transactionInfo1 = new TransactionInfo();
        transactionInfo1.setId(1L);
        TransactionInfo transactionInfo2 = new TransactionInfo();
        transactionInfo2.setId(transactionInfo1.getId());
        assertThat(transactionInfo1).isEqualTo(transactionInfo2);
        transactionInfo2.setId(2L);
        assertThat(transactionInfo1).isNotEqualTo(transactionInfo2);
        transactionInfo1.setId(null);
        assertThat(transactionInfo1).isNotEqualTo(transactionInfo2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TransactionInfoDTO.class);
        TransactionInfoDTO transactionInfoDTO1 = new TransactionInfoDTO();
        transactionInfoDTO1.setId(1L);
        TransactionInfoDTO transactionInfoDTO2 = new TransactionInfoDTO();
        assertThat(transactionInfoDTO1).isNotEqualTo(transactionInfoDTO2);
        transactionInfoDTO2.setId(transactionInfoDTO1.getId());
        assertThat(transactionInfoDTO1).isEqualTo(transactionInfoDTO2);
        transactionInfoDTO2.setId(2L);
        assertThat(transactionInfoDTO1).isNotEqualTo(transactionInfoDTO2);
        transactionInfoDTO1.setId(null);
        assertThat(transactionInfoDTO1).isNotEqualTo(transactionInfoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(transactionInfoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(transactionInfoMapper.fromId(null)).isNull();
    }
}
