package com.electron.mfs.pg.gateway.web.rest;

import com.electron.mfs.pg.gateway.PgkernelgatewayApp;
import com.electron.mfs.pg.gateway.config.SecurityBeanOverrideConfiguration;
import com.electron.mfs.pg.gateway.domain.DetailTransaction;
import com.electron.mfs.pg.gateway.repository.DetailTransactionRepository;
import com.electron.mfs.pg.gateway.service.DetailTransactionService;
import com.electron.mfs.pg.gateway.service.dto.DetailTransactionDTO;
import com.electron.mfs.pg.gateway.service.mapper.DetailTransactionMapper;
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
 * Integration tests for the {@Link DetailTransactionResource} REST controller.
 */
@EmbeddedKafka
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, PgkernelgatewayApp.class})
public class DetailTransactionResourceIT {

    private static final String DEFAULT_PG_DATA_CODE = "AAAAA";
    private static final String UPDATED_PG_DATA_CODE = "BBBBB";

    private static final String DEFAULT_DATA_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_DATA_VALUE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private DetailTransactionRepository detailTransactionRepository;

    @Autowired
    private DetailTransactionMapper detailTransactionMapper;

    @Autowired
    private DetailTransactionService detailTransactionService;

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

    private MockMvc restDetailTransactionMockMvc;

    private DetailTransaction detailTransaction;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DetailTransactionResource detailTransactionResource = new DetailTransactionResource(detailTransactionService);
        this.restDetailTransactionMockMvc = MockMvcBuilders.standaloneSetup(detailTransactionResource)
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
    public static DetailTransaction createEntity(EntityManager em) {
        DetailTransaction detailTransaction = new DetailTransaction()
            .pgDataCode(DEFAULT_PG_DATA_CODE)
            .dataValue(DEFAULT_DATA_VALUE)
            .active(DEFAULT_ACTIVE);
        return detailTransaction;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DetailTransaction createUpdatedEntity(EntityManager em) {
        DetailTransaction detailTransaction = new DetailTransaction()
            .pgDataCode(UPDATED_PG_DATA_CODE)
            .dataValue(UPDATED_DATA_VALUE)
            .active(UPDATED_ACTIVE);
        return detailTransaction;
    }

    @BeforeEach
    public void initTest() {
        detailTransaction = createEntity(em);
    }

    @Test
    @Transactional
    public void createDetailTransaction() throws Exception {
        int databaseSizeBeforeCreate = detailTransactionRepository.findAll().size();

        // Create the DetailTransaction
        DetailTransactionDTO detailTransactionDTO = detailTransactionMapper.toDto(detailTransaction);
        restDetailTransactionMockMvc.perform(post("/api/detail-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detailTransactionDTO)))
            .andExpect(status().isCreated());

        // Validate the DetailTransaction in the database
        List<DetailTransaction> detailTransactionList = detailTransactionRepository.findAll();
        assertThat(detailTransactionList).hasSize(databaseSizeBeforeCreate + 1);
        DetailTransaction testDetailTransaction = detailTransactionList.get(detailTransactionList.size() - 1);
        assertThat(testDetailTransaction.getPgDataCode()).isEqualTo(DEFAULT_PG_DATA_CODE);
        assertThat(testDetailTransaction.getDataValue()).isEqualTo(DEFAULT_DATA_VALUE);
        assertThat(testDetailTransaction.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createDetailTransactionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = detailTransactionRepository.findAll().size();

        // Create the DetailTransaction with an existing ID
        detailTransaction.setId(1L);
        DetailTransactionDTO detailTransactionDTO = detailTransactionMapper.toDto(detailTransaction);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDetailTransactionMockMvc.perform(post("/api/detail-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detailTransactionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DetailTransaction in the database
        List<DetailTransaction> detailTransactionList = detailTransactionRepository.findAll();
        assertThat(detailTransactionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkPgDataCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = detailTransactionRepository.findAll().size();
        // set the field null
        detailTransaction.setPgDataCode(null);

        // Create the DetailTransaction, which fails.
        DetailTransactionDTO detailTransactionDTO = detailTransactionMapper.toDto(detailTransaction);

        restDetailTransactionMockMvc.perform(post("/api/detail-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detailTransactionDTO)))
            .andExpect(status().isBadRequest());

        List<DetailTransaction> detailTransactionList = detailTransactionRepository.findAll();
        assertThat(detailTransactionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = detailTransactionRepository.findAll().size();
        // set the field null
        detailTransaction.setActive(null);

        // Create the DetailTransaction, which fails.
        DetailTransactionDTO detailTransactionDTO = detailTransactionMapper.toDto(detailTransaction);

        restDetailTransactionMockMvc.perform(post("/api/detail-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detailTransactionDTO)))
            .andExpect(status().isBadRequest());

        List<DetailTransaction> detailTransactionList = detailTransactionRepository.findAll();
        assertThat(detailTransactionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDetailTransactions() throws Exception {
        // Initialize the database
        detailTransactionRepository.saveAndFlush(detailTransaction);

        // Get all the detailTransactionList
        restDetailTransactionMockMvc.perform(get("/api/detail-transactions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(detailTransaction.getId().intValue())))
            .andExpect(jsonPath("$.[*].pgDataCode").value(hasItem(DEFAULT_PG_DATA_CODE.toString())))
            .andExpect(jsonPath("$.[*].dataValue").value(hasItem(DEFAULT_DATA_VALUE.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getDetailTransaction() throws Exception {
        // Initialize the database
        detailTransactionRepository.saveAndFlush(detailTransaction);

        // Get the detailTransaction
        restDetailTransactionMockMvc.perform(get("/api/detail-transactions/{id}", detailTransaction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(detailTransaction.getId().intValue()))
            .andExpect(jsonPath("$.pgDataCode").value(DEFAULT_PG_DATA_CODE.toString()))
            .andExpect(jsonPath("$.dataValue").value(DEFAULT_DATA_VALUE.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingDetailTransaction() throws Exception {
        // Get the detailTransaction
        restDetailTransactionMockMvc.perform(get("/api/detail-transactions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDetailTransaction() throws Exception {
        // Initialize the database
        detailTransactionRepository.saveAndFlush(detailTransaction);

        int databaseSizeBeforeUpdate = detailTransactionRepository.findAll().size();

        // Update the detailTransaction
        DetailTransaction updatedDetailTransaction = detailTransactionRepository.findById(detailTransaction.getId()).get();
        // Disconnect from session so that the updates on updatedDetailTransaction are not directly saved in db
        em.detach(updatedDetailTransaction);
        updatedDetailTransaction
            .pgDataCode(UPDATED_PG_DATA_CODE)
            .dataValue(UPDATED_DATA_VALUE)
            .active(UPDATED_ACTIVE);
        DetailTransactionDTO detailTransactionDTO = detailTransactionMapper.toDto(updatedDetailTransaction);

        restDetailTransactionMockMvc.perform(put("/api/detail-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detailTransactionDTO)))
            .andExpect(status().isOk());

        // Validate the DetailTransaction in the database
        List<DetailTransaction> detailTransactionList = detailTransactionRepository.findAll();
        assertThat(detailTransactionList).hasSize(databaseSizeBeforeUpdate);
        DetailTransaction testDetailTransaction = detailTransactionList.get(detailTransactionList.size() - 1);
        assertThat(testDetailTransaction.getPgDataCode()).isEqualTo(UPDATED_PG_DATA_CODE);
        assertThat(testDetailTransaction.getDataValue()).isEqualTo(UPDATED_DATA_VALUE);
        assertThat(testDetailTransaction.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingDetailTransaction() throws Exception {
        int databaseSizeBeforeUpdate = detailTransactionRepository.findAll().size();

        // Create the DetailTransaction
        DetailTransactionDTO detailTransactionDTO = detailTransactionMapper.toDto(detailTransaction);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDetailTransactionMockMvc.perform(put("/api/detail-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detailTransactionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DetailTransaction in the database
        List<DetailTransaction> detailTransactionList = detailTransactionRepository.findAll();
        assertThat(detailTransactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDetailTransaction() throws Exception {
        // Initialize the database
        detailTransactionRepository.saveAndFlush(detailTransaction);

        int databaseSizeBeforeDelete = detailTransactionRepository.findAll().size();

        // Delete the detailTransaction
        restDetailTransactionMockMvc.perform(delete("/api/detail-transactions/{id}", detailTransaction.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DetailTransaction> detailTransactionList = detailTransactionRepository.findAll();
        assertThat(detailTransactionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DetailTransaction.class);
        DetailTransaction detailTransaction1 = new DetailTransaction();
        detailTransaction1.setId(1L);
        DetailTransaction detailTransaction2 = new DetailTransaction();
        detailTransaction2.setId(detailTransaction1.getId());
        assertThat(detailTransaction1).isEqualTo(detailTransaction2);
        detailTransaction2.setId(2L);
        assertThat(detailTransaction1).isNotEqualTo(detailTransaction2);
        detailTransaction1.setId(null);
        assertThat(detailTransaction1).isNotEqualTo(detailTransaction2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DetailTransactionDTO.class);
        DetailTransactionDTO detailTransactionDTO1 = new DetailTransactionDTO();
        detailTransactionDTO1.setId(1L);
        DetailTransactionDTO detailTransactionDTO2 = new DetailTransactionDTO();
        assertThat(detailTransactionDTO1).isNotEqualTo(detailTransactionDTO2);
        detailTransactionDTO2.setId(detailTransactionDTO1.getId());
        assertThat(detailTransactionDTO1).isEqualTo(detailTransactionDTO2);
        detailTransactionDTO2.setId(2L);
        assertThat(detailTransactionDTO1).isNotEqualTo(detailTransactionDTO2);
        detailTransactionDTO1.setId(null);
        assertThat(detailTransactionDTO1).isNotEqualTo(detailTransactionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(detailTransactionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(detailTransactionMapper.fromId(null)).isNull();
    }
}
