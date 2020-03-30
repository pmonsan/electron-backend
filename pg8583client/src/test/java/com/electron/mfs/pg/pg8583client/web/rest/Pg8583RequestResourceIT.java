package com.electron.mfs.pg.pg8583client.web.rest;

import com.electron.mfs.pg.pg8583client.Pg8583ClientApp;
import com.electron.mfs.pg.pg8583client.config.SecurityBeanOverrideConfiguration;
import com.electron.mfs.pg.pg8583client.domain.Pg8583Request;
import com.electron.mfs.pg.pg8583client.repository.Pg8583RequestRepository;
import com.electron.mfs.pg.pg8583client.service.Pg8583RequestService;
import com.electron.mfs.pg.pg8583client.service.dto.Pg8583RequestDTO;
import com.electron.mfs.pg.pg8583client.service.mapper.Pg8583RequestMapper;
import com.electron.mfs.pg.pg8583client.web.rest.errors.ExceptionTranslator;

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

import static com.electron.mfs.pg.pg8583client.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link Pg8583RequestResource} REST controller.
 */
@EmbeddedKafka
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, Pg8583ClientApp.class})
public class Pg8583RequestResourceIT {

    private static final String DEFAULT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_API_KEY = "AAAAAAAAAA";
    private static final String UPDATED_API_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_ENCRYPTED_DATA = "AAAAAAAAAA";
    private static final String UPDATED_ENCRYPTED_DATA = "BBBBBBBBBB";

    private static final String DEFAULT_DECRYPTED_DATA = "AAAAAAAAAA";
    private static final String UPDATED_DECRYPTED_DATA = "BBBBBBBBBB";

    private static final Instant DEFAULT_REGISTRATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_REGISTRATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_RESPONSE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_RESPONSE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_REQUEST_RESPONSE = "AAAAAAAAAA";
    private static final String UPDATED_REQUEST_RESPONSE = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_REASON = "AAAAAAAAAA";
    private static final String UPDATED_REASON = "BBBBBBBBBB";

    private static final String DEFAULT_PGAPS_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_PGAPS_MESSAGE = "BBBBBBBBBB";

    private static final String DEFAULT_PGAPS_TRANSACTION_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PGAPS_TRANSACTION_NUMBER = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private Pg8583RequestRepository pg8583RequestRepository;

    @Autowired
    private Pg8583RequestMapper pg8583RequestMapper;

    @Autowired
    private Pg8583RequestService pg8583RequestService;

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

    private MockMvc restPg8583RequestMockMvc;

    private Pg8583Request pg8583Request;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final Pg8583RequestResource pg8583RequestResource = new Pg8583RequestResource(pg8583RequestService);
        this.restPg8583RequestMockMvc = MockMvcBuilders.standaloneSetup(pg8583RequestResource)
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
    public static Pg8583Request createEntity(EntityManager em) {
        Pg8583Request pg8583Request = new Pg8583Request()
            .number(DEFAULT_NUMBER)
            .apiKey(DEFAULT_API_KEY)
            .encryptedData(DEFAULT_ENCRYPTED_DATA)
            .decryptedData(DEFAULT_DECRYPTED_DATA)
            .registrationDate(DEFAULT_REGISTRATION_DATE)
            .responseDate(DEFAULT_RESPONSE_DATE)
            .requestResponse(DEFAULT_REQUEST_RESPONSE)
            .status(DEFAULT_STATUS)
            .reason(DEFAULT_REASON)
            .pgapsMessage(DEFAULT_PGAPS_MESSAGE)
            .pgapsTransactionNumber(DEFAULT_PGAPS_TRANSACTION_NUMBER)
            .active(DEFAULT_ACTIVE);
        return pg8583Request;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pg8583Request createUpdatedEntity(EntityManager em) {
        Pg8583Request pg8583Request = new Pg8583Request()
            .number(UPDATED_NUMBER)
            .apiKey(UPDATED_API_KEY)
            .encryptedData(UPDATED_ENCRYPTED_DATA)
            .decryptedData(UPDATED_DECRYPTED_DATA)
            .registrationDate(UPDATED_REGISTRATION_DATE)
            .responseDate(UPDATED_RESPONSE_DATE)
            .requestResponse(UPDATED_REQUEST_RESPONSE)
            .status(UPDATED_STATUS)
            .reason(UPDATED_REASON)
            .pgapsMessage(UPDATED_PGAPS_MESSAGE)
            .pgapsTransactionNumber(UPDATED_PGAPS_TRANSACTION_NUMBER)
            .active(UPDATED_ACTIVE);
        return pg8583Request;
    }

    @BeforeEach
    public void initTest() {
        pg8583Request = createEntity(em);
    }

    @Test
    @Transactional
    public void createPg8583Request() throws Exception {
        int databaseSizeBeforeCreate = pg8583RequestRepository.findAll().size();

        // Create the Pg8583Request
        Pg8583RequestDTO pg8583RequestDTO = pg8583RequestMapper.toDto(pg8583Request);
        restPg8583RequestMockMvc.perform(post("/api/pg-8583-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pg8583RequestDTO)))
            .andExpect(status().isCreated());

        // Validate the Pg8583Request in the database
        List<Pg8583Request> pg8583RequestList = pg8583RequestRepository.findAll();
        assertThat(pg8583RequestList).hasSize(databaseSizeBeforeCreate + 1);
        Pg8583Request testPg8583Request = pg8583RequestList.get(pg8583RequestList.size() - 1);
        assertThat(testPg8583Request.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testPg8583Request.getApiKey()).isEqualTo(DEFAULT_API_KEY);
        assertThat(testPg8583Request.getEncryptedData()).isEqualTo(DEFAULT_ENCRYPTED_DATA);
        assertThat(testPg8583Request.getDecryptedData()).isEqualTo(DEFAULT_DECRYPTED_DATA);
        assertThat(testPg8583Request.getRegistrationDate()).isEqualTo(DEFAULT_REGISTRATION_DATE);
        assertThat(testPg8583Request.getResponseDate()).isEqualTo(DEFAULT_RESPONSE_DATE);
        assertThat(testPg8583Request.getRequestResponse()).isEqualTo(DEFAULT_REQUEST_RESPONSE);
        assertThat(testPg8583Request.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testPg8583Request.getReason()).isEqualTo(DEFAULT_REASON);
        assertThat(testPg8583Request.getPgapsMessage()).isEqualTo(DEFAULT_PGAPS_MESSAGE);
        assertThat(testPg8583Request.getPgapsTransactionNumber()).isEqualTo(DEFAULT_PGAPS_TRANSACTION_NUMBER);
        assertThat(testPg8583Request.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createPg8583RequestWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pg8583RequestRepository.findAll().size();

        // Create the Pg8583Request with an existing ID
        pg8583Request.setId(1L);
        Pg8583RequestDTO pg8583RequestDTO = pg8583RequestMapper.toDto(pg8583Request);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPg8583RequestMockMvc.perform(post("/api/pg-8583-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pg8583RequestDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Pg8583Request in the database
        List<Pg8583Request> pg8583RequestList = pg8583RequestRepository.findAll();
        assertThat(pg8583RequestList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = pg8583RequestRepository.findAll().size();
        // set the field null
        pg8583Request.setNumber(null);

        // Create the Pg8583Request, which fails.
        Pg8583RequestDTO pg8583RequestDTO = pg8583RequestMapper.toDto(pg8583Request);

        restPg8583RequestMockMvc.perform(post("/api/pg-8583-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pg8583RequestDTO)))
            .andExpect(status().isBadRequest());

        List<Pg8583Request> pg8583RequestList = pg8583RequestRepository.findAll();
        assertThat(pg8583RequestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkApiKeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = pg8583RequestRepository.findAll().size();
        // set the field null
        pg8583Request.setApiKey(null);

        // Create the Pg8583Request, which fails.
        Pg8583RequestDTO pg8583RequestDTO = pg8583RequestMapper.toDto(pg8583Request);

        restPg8583RequestMockMvc.perform(post("/api/pg-8583-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pg8583RequestDTO)))
            .andExpect(status().isBadRequest());

        List<Pg8583Request> pg8583RequestList = pg8583RequestRepository.findAll();
        assertThat(pg8583RequestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEncryptedDataIsRequired() throws Exception {
        int databaseSizeBeforeTest = pg8583RequestRepository.findAll().size();
        // set the field null
        pg8583Request.setEncryptedData(null);

        // Create the Pg8583Request, which fails.
        Pg8583RequestDTO pg8583RequestDTO = pg8583RequestMapper.toDto(pg8583Request);

        restPg8583RequestMockMvc.perform(post("/api/pg-8583-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pg8583RequestDTO)))
            .andExpect(status().isBadRequest());

        List<Pg8583Request> pg8583RequestList = pg8583RequestRepository.findAll();
        assertThat(pg8583RequestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRegistrationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = pg8583RequestRepository.findAll().size();
        // set the field null
        pg8583Request.setRegistrationDate(null);

        // Create the Pg8583Request, which fails.
        Pg8583RequestDTO pg8583RequestDTO = pg8583RequestMapper.toDto(pg8583Request);

        restPg8583RequestMockMvc.perform(post("/api/pg-8583-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pg8583RequestDTO)))
            .andExpect(status().isBadRequest());

        List<Pg8583Request> pg8583RequestList = pg8583RequestRepository.findAll();
        assertThat(pg8583RequestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = pg8583RequestRepository.findAll().size();
        // set the field null
        pg8583Request.setActive(null);

        // Create the Pg8583Request, which fails.
        Pg8583RequestDTO pg8583RequestDTO = pg8583RequestMapper.toDto(pg8583Request);

        restPg8583RequestMockMvc.perform(post("/api/pg-8583-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pg8583RequestDTO)))
            .andExpect(status().isBadRequest());

        List<Pg8583Request> pg8583RequestList = pg8583RequestRepository.findAll();
        assertThat(pg8583RequestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPg8583Requests() throws Exception {
        // Initialize the database
        pg8583RequestRepository.saveAndFlush(pg8583Request);

        // Get all the pg8583RequestList
        restPg8583RequestMockMvc.perform(get("/api/pg-8583-requests?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pg8583Request.getId().intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].apiKey").value(hasItem(DEFAULT_API_KEY.toString())))
            .andExpect(jsonPath("$.[*].encryptedData").value(hasItem(DEFAULT_ENCRYPTED_DATA.toString())))
            .andExpect(jsonPath("$.[*].decryptedData").value(hasItem(DEFAULT_DECRYPTED_DATA.toString())))
            .andExpect(jsonPath("$.[*].registrationDate").value(hasItem(DEFAULT_REGISTRATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].responseDate").value(hasItem(DEFAULT_RESPONSE_DATE.toString())))
            .andExpect(jsonPath("$.[*].requestResponse").value(hasItem(DEFAULT_REQUEST_RESPONSE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON.toString())))
            .andExpect(jsonPath("$.[*].pgapsMessage").value(hasItem(DEFAULT_PGAPS_MESSAGE.toString())))
            .andExpect(jsonPath("$.[*].pgapsTransactionNumber").value(hasItem(DEFAULT_PGAPS_TRANSACTION_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getPg8583Request() throws Exception {
        // Initialize the database
        pg8583RequestRepository.saveAndFlush(pg8583Request);

        // Get the pg8583Request
        restPg8583RequestMockMvc.perform(get("/api/pg-8583-requests/{id}", pg8583Request.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pg8583Request.getId().intValue()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER.toString()))
            .andExpect(jsonPath("$.apiKey").value(DEFAULT_API_KEY.toString()))
            .andExpect(jsonPath("$.encryptedData").value(DEFAULT_ENCRYPTED_DATA.toString()))
            .andExpect(jsonPath("$.decryptedData").value(DEFAULT_DECRYPTED_DATA.toString()))
            .andExpect(jsonPath("$.registrationDate").value(DEFAULT_REGISTRATION_DATE.toString()))
            .andExpect(jsonPath("$.responseDate").value(DEFAULT_RESPONSE_DATE.toString()))
            .andExpect(jsonPath("$.requestResponse").value(DEFAULT_REQUEST_RESPONSE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.reason").value(DEFAULT_REASON.toString()))
            .andExpect(jsonPath("$.pgapsMessage").value(DEFAULT_PGAPS_MESSAGE.toString()))
            .andExpect(jsonPath("$.pgapsTransactionNumber").value(DEFAULT_PGAPS_TRANSACTION_NUMBER.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPg8583Request() throws Exception {
        // Get the pg8583Request
        restPg8583RequestMockMvc.perform(get("/api/pg-8583-requests/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePg8583Request() throws Exception {
        // Initialize the database
        pg8583RequestRepository.saveAndFlush(pg8583Request);

        int databaseSizeBeforeUpdate = pg8583RequestRepository.findAll().size();

        // Update the pg8583Request
        Pg8583Request updatedPg8583Request = pg8583RequestRepository.findById(pg8583Request.getId()).get();
        // Disconnect from session so that the updates on updatedPg8583Request are not directly saved in db
        em.detach(updatedPg8583Request);
        updatedPg8583Request
            .number(UPDATED_NUMBER)
            .apiKey(UPDATED_API_KEY)
            .encryptedData(UPDATED_ENCRYPTED_DATA)
            .decryptedData(UPDATED_DECRYPTED_DATA)
            .registrationDate(UPDATED_REGISTRATION_DATE)
            .responseDate(UPDATED_RESPONSE_DATE)
            .requestResponse(UPDATED_REQUEST_RESPONSE)
            .status(UPDATED_STATUS)
            .reason(UPDATED_REASON)
            .pgapsMessage(UPDATED_PGAPS_MESSAGE)
            .pgapsTransactionNumber(UPDATED_PGAPS_TRANSACTION_NUMBER)
            .active(UPDATED_ACTIVE);
        Pg8583RequestDTO pg8583RequestDTO = pg8583RequestMapper.toDto(updatedPg8583Request);

        restPg8583RequestMockMvc.perform(put("/api/pg-8583-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pg8583RequestDTO)))
            .andExpect(status().isOk());

        // Validate the Pg8583Request in the database
        List<Pg8583Request> pg8583RequestList = pg8583RequestRepository.findAll();
        assertThat(pg8583RequestList).hasSize(databaseSizeBeforeUpdate);
        Pg8583Request testPg8583Request = pg8583RequestList.get(pg8583RequestList.size() - 1);
        assertThat(testPg8583Request.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testPg8583Request.getApiKey()).isEqualTo(UPDATED_API_KEY);
        assertThat(testPg8583Request.getEncryptedData()).isEqualTo(UPDATED_ENCRYPTED_DATA);
        assertThat(testPg8583Request.getDecryptedData()).isEqualTo(UPDATED_DECRYPTED_DATA);
        assertThat(testPg8583Request.getRegistrationDate()).isEqualTo(UPDATED_REGISTRATION_DATE);
        assertThat(testPg8583Request.getResponseDate()).isEqualTo(UPDATED_RESPONSE_DATE);
        assertThat(testPg8583Request.getRequestResponse()).isEqualTo(UPDATED_REQUEST_RESPONSE);
        assertThat(testPg8583Request.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testPg8583Request.getReason()).isEqualTo(UPDATED_REASON);
        assertThat(testPg8583Request.getPgapsMessage()).isEqualTo(UPDATED_PGAPS_MESSAGE);
        assertThat(testPg8583Request.getPgapsTransactionNumber()).isEqualTo(UPDATED_PGAPS_TRANSACTION_NUMBER);
        assertThat(testPg8583Request.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingPg8583Request() throws Exception {
        int databaseSizeBeforeUpdate = pg8583RequestRepository.findAll().size();

        // Create the Pg8583Request
        Pg8583RequestDTO pg8583RequestDTO = pg8583RequestMapper.toDto(pg8583Request);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPg8583RequestMockMvc.perform(put("/api/pg-8583-requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pg8583RequestDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Pg8583Request in the database
        List<Pg8583Request> pg8583RequestList = pg8583RequestRepository.findAll();
        assertThat(pg8583RequestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePg8583Request() throws Exception {
        // Initialize the database
        pg8583RequestRepository.saveAndFlush(pg8583Request);

        int databaseSizeBeforeDelete = pg8583RequestRepository.findAll().size();

        // Delete the pg8583Request
        restPg8583RequestMockMvc.perform(delete("/api/pg-8583-requests/{id}", pg8583Request.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Pg8583Request> pg8583RequestList = pg8583RequestRepository.findAll();
        assertThat(pg8583RequestList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pg8583Request.class);
        Pg8583Request pg8583Request1 = new Pg8583Request();
        pg8583Request1.setId(1L);
        Pg8583Request pg8583Request2 = new Pg8583Request();
        pg8583Request2.setId(pg8583Request1.getId());
        assertThat(pg8583Request1).isEqualTo(pg8583Request2);
        pg8583Request2.setId(2L);
        assertThat(pg8583Request1).isNotEqualTo(pg8583Request2);
        pg8583Request1.setId(null);
        assertThat(pg8583Request1).isNotEqualTo(pg8583Request2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pg8583RequestDTO.class);
        Pg8583RequestDTO pg8583RequestDTO1 = new Pg8583RequestDTO();
        pg8583RequestDTO1.setId(1L);
        Pg8583RequestDTO pg8583RequestDTO2 = new Pg8583RequestDTO();
        assertThat(pg8583RequestDTO1).isNotEqualTo(pg8583RequestDTO2);
        pg8583RequestDTO2.setId(pg8583RequestDTO1.getId());
        assertThat(pg8583RequestDTO1).isEqualTo(pg8583RequestDTO2);
        pg8583RequestDTO2.setId(2L);
        assertThat(pg8583RequestDTO1).isNotEqualTo(pg8583RequestDTO2);
        pg8583RequestDTO1.setId(null);
        assertThat(pg8583RequestDTO1).isNotEqualTo(pg8583RequestDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(pg8583RequestMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(pg8583RequestMapper.fromId(null)).isNull();
    }
}
