package com.electron.mfs.pg.gateway.web.rest;

import com.electron.mfs.pg.gateway.PgkernelgatewayApp;
import com.electron.mfs.pg.gateway.config.SecurityBeanOverrideConfiguration;
import com.electron.mfs.pg.gateway.domain.LoanInstalment;
import com.electron.mfs.pg.gateway.repository.LoanInstalmentRepository;
import com.electron.mfs.pg.gateway.service.LoanInstalmentService;
import com.electron.mfs.pg.gateway.service.dto.LoanInstalmentDTO;
import com.electron.mfs.pg.gateway.service.mapper.LoanInstalmentMapper;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.electron.mfs.pg.gateway.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link LoanInstalmentResource} REST controller.
 */
@EmbeddedKafka
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, PgkernelgatewayApp.class})
public class LoanInstalmentResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Instant DEFAULT_EXPECTED_PAYMENT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_EXPECTED_PAYMENT_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_REAL_PAYMENT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_REAL_PAYMENT_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Double DEFAULT_AMOUNT_TO_PAY = 1D;
    private static final Double UPDATED_AMOUNT_TO_PAY = 2D;

    private static final Double DEFAULT_PENALITY_FEE = 1D;
    private static final Double UPDATED_PENALITY_FEE = 2D;

    private static final Instant DEFAULT_STATUS_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_STATUS_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LOAN_INSTALMENT_STATUS_CODE = "AAAAA";
    private static final String UPDATED_LOAN_INSTALMENT_STATUS_CODE = "BBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private LoanInstalmentRepository loanInstalmentRepository;

    @Autowired
    private LoanInstalmentMapper loanInstalmentMapper;

    @Autowired
    private LoanInstalmentService loanInstalmentService;

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

    private MockMvc restLoanInstalmentMockMvc;

    private LoanInstalment loanInstalment;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LoanInstalmentResource loanInstalmentResource = new LoanInstalmentResource(loanInstalmentService);
        this.restLoanInstalmentMockMvc = MockMvcBuilders.standaloneSetup(loanInstalmentResource)
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
    public static LoanInstalment createEntity(EntityManager em) {
        LoanInstalment loanInstalment = new LoanInstalment()
            .code(DEFAULT_CODE)
            .expectedPaymentDate(DEFAULT_EXPECTED_PAYMENT_DATE)
            .realPaymentDate(DEFAULT_REAL_PAYMENT_DATE)
            .amountToPay(DEFAULT_AMOUNT_TO_PAY)
            .penalityFee(DEFAULT_PENALITY_FEE)
            .statusDate(DEFAULT_STATUS_DATE)
            .loanInstalmentStatusCode(DEFAULT_LOAN_INSTALMENT_STATUS_CODE)
            .active(DEFAULT_ACTIVE);
        return loanInstalment;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LoanInstalment createUpdatedEntity(EntityManager em) {
        LoanInstalment loanInstalment = new LoanInstalment()
            .code(UPDATED_CODE)
            .expectedPaymentDate(UPDATED_EXPECTED_PAYMENT_DATE)
            .realPaymentDate(UPDATED_REAL_PAYMENT_DATE)
            .amountToPay(UPDATED_AMOUNT_TO_PAY)
            .penalityFee(UPDATED_PENALITY_FEE)
            .statusDate(UPDATED_STATUS_DATE)
            .loanInstalmentStatusCode(UPDATED_LOAN_INSTALMENT_STATUS_CODE)
            .active(UPDATED_ACTIVE);
        return loanInstalment;
    }

    @BeforeEach
    public void initTest() {
        loanInstalment = createEntity(em);
    }

    @Test
    @Transactional
    public void createLoanInstalment() throws Exception {
        int databaseSizeBeforeCreate = loanInstalmentRepository.findAll().size();

        // Create the LoanInstalment
        LoanInstalmentDTO loanInstalmentDTO = loanInstalmentMapper.toDto(loanInstalment);
        restLoanInstalmentMockMvc.perform(post("/api/loan-instalments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loanInstalmentDTO)))
            .andExpect(status().isCreated());

        // Validate the LoanInstalment in the database
        List<LoanInstalment> loanInstalmentList = loanInstalmentRepository.findAll();
        assertThat(loanInstalmentList).hasSize(databaseSizeBeforeCreate + 1);
        LoanInstalment testLoanInstalment = loanInstalmentList.get(loanInstalmentList.size() - 1);
        assertThat(testLoanInstalment.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testLoanInstalment.getExpectedPaymentDate()).isEqualTo(DEFAULT_EXPECTED_PAYMENT_DATE);
        assertThat(testLoanInstalment.getRealPaymentDate()).isEqualTo(DEFAULT_REAL_PAYMENT_DATE);
        assertThat(testLoanInstalment.getAmountToPay()).isEqualTo(DEFAULT_AMOUNT_TO_PAY);
        assertThat(testLoanInstalment.getPenalityFee()).isEqualTo(DEFAULT_PENALITY_FEE);
        assertThat(testLoanInstalment.getStatusDate()).isEqualTo(DEFAULT_STATUS_DATE);
        assertThat(testLoanInstalment.getLoanInstalmentStatusCode()).isEqualTo(DEFAULT_LOAN_INSTALMENT_STATUS_CODE);
        assertThat(testLoanInstalment.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createLoanInstalmentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = loanInstalmentRepository.findAll().size();

        // Create the LoanInstalment with an existing ID
        loanInstalment.setId(1L);
        LoanInstalmentDTO loanInstalmentDTO = loanInstalmentMapper.toDto(loanInstalment);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLoanInstalmentMockMvc.perform(post("/api/loan-instalments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loanInstalmentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LoanInstalment in the database
        List<LoanInstalment> loanInstalmentList = loanInstalmentRepository.findAll();
        assertThat(loanInstalmentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = loanInstalmentRepository.findAll().size();
        // set the field null
        loanInstalment.setCode(null);

        // Create the LoanInstalment, which fails.
        LoanInstalmentDTO loanInstalmentDTO = loanInstalmentMapper.toDto(loanInstalment);

        restLoanInstalmentMockMvc.perform(post("/api/loan-instalments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loanInstalmentDTO)))
            .andExpect(status().isBadRequest());

        List<LoanInstalment> loanInstalmentList = loanInstalmentRepository.findAll();
        assertThat(loanInstalmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkExpectedPaymentDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = loanInstalmentRepository.findAll().size();
        // set the field null
        loanInstalment.setExpectedPaymentDate(null);

        // Create the LoanInstalment, which fails.
        LoanInstalmentDTO loanInstalmentDTO = loanInstalmentMapper.toDto(loanInstalment);

        restLoanInstalmentMockMvc.perform(post("/api/loan-instalments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loanInstalmentDTO)))
            .andExpect(status().isBadRequest());

        List<LoanInstalment> loanInstalmentList = loanInstalmentRepository.findAll();
        assertThat(loanInstalmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAmountToPayIsRequired() throws Exception {
        int databaseSizeBeforeTest = loanInstalmentRepository.findAll().size();
        // set the field null
        loanInstalment.setAmountToPay(null);

        // Create the LoanInstalment, which fails.
        LoanInstalmentDTO loanInstalmentDTO = loanInstalmentMapper.toDto(loanInstalment);

        restLoanInstalmentMockMvc.perform(post("/api/loan-instalments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loanInstalmentDTO)))
            .andExpect(status().isBadRequest());

        List<LoanInstalment> loanInstalmentList = loanInstalmentRepository.findAll();
        assertThat(loanInstalmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLoanInstalmentStatusCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = loanInstalmentRepository.findAll().size();
        // set the field null
        loanInstalment.setLoanInstalmentStatusCode(null);

        // Create the LoanInstalment, which fails.
        LoanInstalmentDTO loanInstalmentDTO = loanInstalmentMapper.toDto(loanInstalment);

        restLoanInstalmentMockMvc.perform(post("/api/loan-instalments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loanInstalmentDTO)))
            .andExpect(status().isBadRequest());

        List<LoanInstalment> loanInstalmentList = loanInstalmentRepository.findAll();
        assertThat(loanInstalmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = loanInstalmentRepository.findAll().size();
        // set the field null
        loanInstalment.setActive(null);

        // Create the LoanInstalment, which fails.
        LoanInstalmentDTO loanInstalmentDTO = loanInstalmentMapper.toDto(loanInstalment);

        restLoanInstalmentMockMvc.perform(post("/api/loan-instalments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loanInstalmentDTO)))
            .andExpect(status().isBadRequest());

        List<LoanInstalment> loanInstalmentList = loanInstalmentRepository.findAll();
        assertThat(loanInstalmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLoanInstalments() throws Exception {
        // Initialize the database
        loanInstalmentRepository.saveAndFlush(loanInstalment);

        // Get all the loanInstalmentList
        restLoanInstalmentMockMvc.perform(get("/api/loan-instalments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(loanInstalment.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].expectedPaymentDate").value(hasItem(DEFAULT_EXPECTED_PAYMENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].realPaymentDate").value(hasItem(DEFAULT_REAL_PAYMENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].amountToPay").value(hasItem(DEFAULT_AMOUNT_TO_PAY.doubleValue())))
            .andExpect(jsonPath("$.[*].penalityFee").value(hasItem(DEFAULT_PENALITY_FEE.doubleValue())))
            .andExpect(jsonPath("$.[*].statusDate").value(hasItem(DEFAULT_STATUS_DATE.toString())))
            .andExpect(jsonPath("$.[*].loanInstalmentStatusCode").value(hasItem(DEFAULT_LOAN_INSTALMENT_STATUS_CODE.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getLoanInstalment() throws Exception {
        // Initialize the database
        loanInstalmentRepository.saveAndFlush(loanInstalment);

        // Get the loanInstalment
        restLoanInstalmentMockMvc.perform(get("/api/loan-instalments/{id}", loanInstalment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(loanInstalment.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.expectedPaymentDate").value(DEFAULT_EXPECTED_PAYMENT_DATE.toString()))
            .andExpect(jsonPath("$.realPaymentDate").value(DEFAULT_REAL_PAYMENT_DATE.toString()))
            .andExpect(jsonPath("$.amountToPay").value(DEFAULT_AMOUNT_TO_PAY.doubleValue()))
            .andExpect(jsonPath("$.penalityFee").value(DEFAULT_PENALITY_FEE.doubleValue()))
            .andExpect(jsonPath("$.statusDate").value(DEFAULT_STATUS_DATE.toString()))
            .andExpect(jsonPath("$.loanInstalmentStatusCode").value(DEFAULT_LOAN_INSTALMENT_STATUS_CODE.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingLoanInstalment() throws Exception {
        // Get the loanInstalment
        restLoanInstalmentMockMvc.perform(get("/api/loan-instalments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLoanInstalment() throws Exception {
        // Initialize the database
        loanInstalmentRepository.saveAndFlush(loanInstalment);

        int databaseSizeBeforeUpdate = loanInstalmentRepository.findAll().size();

        // Update the loanInstalment
        LoanInstalment updatedLoanInstalment = loanInstalmentRepository.findById(loanInstalment.getId()).get();
        // Disconnect from session so that the updates on updatedLoanInstalment are not directly saved in db
        em.detach(updatedLoanInstalment);
        updatedLoanInstalment
            .code(UPDATED_CODE)
            .expectedPaymentDate(UPDATED_EXPECTED_PAYMENT_DATE)
            .realPaymentDate(UPDATED_REAL_PAYMENT_DATE)
            .amountToPay(UPDATED_AMOUNT_TO_PAY)
            .penalityFee(UPDATED_PENALITY_FEE)
            .statusDate(UPDATED_STATUS_DATE)
            .loanInstalmentStatusCode(UPDATED_LOAN_INSTALMENT_STATUS_CODE)
            .active(UPDATED_ACTIVE);
        LoanInstalmentDTO loanInstalmentDTO = loanInstalmentMapper.toDto(updatedLoanInstalment);

        restLoanInstalmentMockMvc.perform(put("/api/loan-instalments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loanInstalmentDTO)))
            .andExpect(status().isOk());

        // Validate the LoanInstalment in the database
        List<LoanInstalment> loanInstalmentList = loanInstalmentRepository.findAll();
        assertThat(loanInstalmentList).hasSize(databaseSizeBeforeUpdate);
        LoanInstalment testLoanInstalment = loanInstalmentList.get(loanInstalmentList.size() - 1);
        assertThat(testLoanInstalment.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testLoanInstalment.getExpectedPaymentDate()).isEqualTo(UPDATED_EXPECTED_PAYMENT_DATE);
        assertThat(testLoanInstalment.getRealPaymentDate()).isEqualTo(UPDATED_REAL_PAYMENT_DATE);
        assertThat(testLoanInstalment.getAmountToPay()).isEqualTo(UPDATED_AMOUNT_TO_PAY);
        assertThat(testLoanInstalment.getPenalityFee()).isEqualTo(UPDATED_PENALITY_FEE);
        assertThat(testLoanInstalment.getStatusDate()).isEqualTo(UPDATED_STATUS_DATE);
        assertThat(testLoanInstalment.getLoanInstalmentStatusCode()).isEqualTo(UPDATED_LOAN_INSTALMENT_STATUS_CODE);
        assertThat(testLoanInstalment.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingLoanInstalment() throws Exception {
        int databaseSizeBeforeUpdate = loanInstalmentRepository.findAll().size();

        // Create the LoanInstalment
        LoanInstalmentDTO loanInstalmentDTO = loanInstalmentMapper.toDto(loanInstalment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLoanInstalmentMockMvc.perform(put("/api/loan-instalments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loanInstalmentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LoanInstalment in the database
        List<LoanInstalment> loanInstalmentList = loanInstalmentRepository.findAll();
        assertThat(loanInstalmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLoanInstalment() throws Exception {
        // Initialize the database
        loanInstalmentRepository.saveAndFlush(loanInstalment);

        int databaseSizeBeforeDelete = loanInstalmentRepository.findAll().size();

        // Delete the loanInstalment
        restLoanInstalmentMockMvc.perform(delete("/api/loan-instalments/{id}", loanInstalment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LoanInstalment> loanInstalmentList = loanInstalmentRepository.findAll();
        assertThat(loanInstalmentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LoanInstalment.class);
        LoanInstalment loanInstalment1 = new LoanInstalment();
        loanInstalment1.setId(1L);
        LoanInstalment loanInstalment2 = new LoanInstalment();
        loanInstalment2.setId(loanInstalment1.getId());
        assertThat(loanInstalment1).isEqualTo(loanInstalment2);
        loanInstalment2.setId(2L);
        assertThat(loanInstalment1).isNotEqualTo(loanInstalment2);
        loanInstalment1.setId(null);
        assertThat(loanInstalment1).isNotEqualTo(loanInstalment2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LoanInstalmentDTO.class);
        LoanInstalmentDTO loanInstalmentDTO1 = new LoanInstalmentDTO();
        loanInstalmentDTO1.setId(1L);
        LoanInstalmentDTO loanInstalmentDTO2 = new LoanInstalmentDTO();
        assertThat(loanInstalmentDTO1).isNotEqualTo(loanInstalmentDTO2);
        loanInstalmentDTO2.setId(loanInstalmentDTO1.getId());
        assertThat(loanInstalmentDTO1).isEqualTo(loanInstalmentDTO2);
        loanInstalmentDTO2.setId(2L);
        assertThat(loanInstalmentDTO1).isNotEqualTo(loanInstalmentDTO2);
        loanInstalmentDTO1.setId(null);
        assertThat(loanInstalmentDTO1).isNotEqualTo(loanInstalmentDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(loanInstalmentMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(loanInstalmentMapper.fromId(null)).isNull();
    }
}
