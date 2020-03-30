package com.electron.mfs.pg.gateway.web.rest;

import com.electron.mfs.pg.gateway.PgkernelgatewayApp;
import com.electron.mfs.pg.gateway.config.SecurityBeanOverrideConfiguration;
import com.electron.mfs.pg.gateway.domain.PgChannelAuthorized;
import com.electron.mfs.pg.gateway.repository.PgChannelAuthorizedRepository;
import com.electron.mfs.pg.gateway.service.PgChannelAuthorizedService;
import com.electron.mfs.pg.gateway.service.dto.PgChannelAuthorizedDTO;
import com.electron.mfs.pg.gateway.service.mapper.PgChannelAuthorizedMapper;
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
 * Integration tests for the {@Link PgChannelAuthorizedResource} REST controller.
 */
@EmbeddedKafka
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, PgkernelgatewayApp.class})
public class PgChannelAuthorizedResourceIT {

    private static final String DEFAULT_TRANSACTION_TYPE_CODE = "AAAAA";
    private static final String UPDATED_TRANSACTION_TYPE_CODE = "BBBBB";

    private static final Instant DEFAULT_REGISTRATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_REGISTRATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private PgChannelAuthorizedRepository pgChannelAuthorizedRepository;

    @Autowired
    private PgChannelAuthorizedMapper pgChannelAuthorizedMapper;

    @Autowired
    private PgChannelAuthorizedService pgChannelAuthorizedService;

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

    private MockMvc restPgChannelAuthorizedMockMvc;

    private PgChannelAuthorized pgChannelAuthorized;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PgChannelAuthorizedResource pgChannelAuthorizedResource = new PgChannelAuthorizedResource(pgChannelAuthorizedService);
        this.restPgChannelAuthorizedMockMvc = MockMvcBuilders.standaloneSetup(pgChannelAuthorizedResource)
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
    public static PgChannelAuthorized createEntity(EntityManager em) {
        PgChannelAuthorized pgChannelAuthorized = new PgChannelAuthorized()
            .transactionTypeCode(DEFAULT_TRANSACTION_TYPE_CODE)
            .registrationDate(DEFAULT_REGISTRATION_DATE)
            .active(DEFAULT_ACTIVE);
        return pgChannelAuthorized;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PgChannelAuthorized createUpdatedEntity(EntityManager em) {
        PgChannelAuthorized pgChannelAuthorized = new PgChannelAuthorized()
            .transactionTypeCode(UPDATED_TRANSACTION_TYPE_CODE)
            .registrationDate(UPDATED_REGISTRATION_DATE)
            .active(UPDATED_ACTIVE);
        return pgChannelAuthorized;
    }

    @BeforeEach
    public void initTest() {
        pgChannelAuthorized = createEntity(em);
    }

    @Test
    @Transactional
    public void createPgChannelAuthorized() throws Exception {
        int databaseSizeBeforeCreate = pgChannelAuthorizedRepository.findAll().size();

        // Create the PgChannelAuthorized
        PgChannelAuthorizedDTO pgChannelAuthorizedDTO = pgChannelAuthorizedMapper.toDto(pgChannelAuthorized);
        restPgChannelAuthorizedMockMvc.perform(post("/api/pg-channel-authorizeds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgChannelAuthorizedDTO)))
            .andExpect(status().isCreated());

        // Validate the PgChannelAuthorized in the database
        List<PgChannelAuthorized> pgChannelAuthorizedList = pgChannelAuthorizedRepository.findAll();
        assertThat(pgChannelAuthorizedList).hasSize(databaseSizeBeforeCreate + 1);
        PgChannelAuthorized testPgChannelAuthorized = pgChannelAuthorizedList.get(pgChannelAuthorizedList.size() - 1);
        assertThat(testPgChannelAuthorized.getTransactionTypeCode()).isEqualTo(DEFAULT_TRANSACTION_TYPE_CODE);
        assertThat(testPgChannelAuthorized.getRegistrationDate()).isEqualTo(DEFAULT_REGISTRATION_DATE);
        assertThat(testPgChannelAuthorized.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createPgChannelAuthorizedWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pgChannelAuthorizedRepository.findAll().size();

        // Create the PgChannelAuthorized with an existing ID
        pgChannelAuthorized.setId(1L);
        PgChannelAuthorizedDTO pgChannelAuthorizedDTO = pgChannelAuthorizedMapper.toDto(pgChannelAuthorized);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPgChannelAuthorizedMockMvc.perform(post("/api/pg-channel-authorizeds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgChannelAuthorizedDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PgChannelAuthorized in the database
        List<PgChannelAuthorized> pgChannelAuthorizedList = pgChannelAuthorizedRepository.findAll();
        assertThat(pgChannelAuthorizedList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTransactionTypeCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgChannelAuthorizedRepository.findAll().size();
        // set the field null
        pgChannelAuthorized.setTransactionTypeCode(null);

        // Create the PgChannelAuthorized, which fails.
        PgChannelAuthorizedDTO pgChannelAuthorizedDTO = pgChannelAuthorizedMapper.toDto(pgChannelAuthorized);

        restPgChannelAuthorizedMockMvc.perform(post("/api/pg-channel-authorizeds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgChannelAuthorizedDTO)))
            .andExpect(status().isBadRequest());

        List<PgChannelAuthorized> pgChannelAuthorizedList = pgChannelAuthorizedRepository.findAll();
        assertThat(pgChannelAuthorizedList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRegistrationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgChannelAuthorizedRepository.findAll().size();
        // set the field null
        pgChannelAuthorized.setRegistrationDate(null);

        // Create the PgChannelAuthorized, which fails.
        PgChannelAuthorizedDTO pgChannelAuthorizedDTO = pgChannelAuthorizedMapper.toDto(pgChannelAuthorized);

        restPgChannelAuthorizedMockMvc.perform(post("/api/pg-channel-authorizeds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgChannelAuthorizedDTO)))
            .andExpect(status().isBadRequest());

        List<PgChannelAuthorized> pgChannelAuthorizedList = pgChannelAuthorizedRepository.findAll();
        assertThat(pgChannelAuthorizedList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgChannelAuthorizedRepository.findAll().size();
        // set the field null
        pgChannelAuthorized.setActive(null);

        // Create the PgChannelAuthorized, which fails.
        PgChannelAuthorizedDTO pgChannelAuthorizedDTO = pgChannelAuthorizedMapper.toDto(pgChannelAuthorized);

        restPgChannelAuthorizedMockMvc.perform(post("/api/pg-channel-authorizeds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgChannelAuthorizedDTO)))
            .andExpect(status().isBadRequest());

        List<PgChannelAuthorized> pgChannelAuthorizedList = pgChannelAuthorizedRepository.findAll();
        assertThat(pgChannelAuthorizedList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPgChannelAuthorizeds() throws Exception {
        // Initialize the database
        pgChannelAuthorizedRepository.saveAndFlush(pgChannelAuthorized);

        // Get all the pgChannelAuthorizedList
        restPgChannelAuthorizedMockMvc.perform(get("/api/pg-channel-authorizeds?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pgChannelAuthorized.getId().intValue())))
            .andExpect(jsonPath("$.[*].transactionTypeCode").value(hasItem(DEFAULT_TRANSACTION_TYPE_CODE.toString())))
            .andExpect(jsonPath("$.[*].registrationDate").value(hasItem(DEFAULT_REGISTRATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getPgChannelAuthorized() throws Exception {
        // Initialize the database
        pgChannelAuthorizedRepository.saveAndFlush(pgChannelAuthorized);

        // Get the pgChannelAuthorized
        restPgChannelAuthorizedMockMvc.perform(get("/api/pg-channel-authorizeds/{id}", pgChannelAuthorized.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pgChannelAuthorized.getId().intValue()))
            .andExpect(jsonPath("$.transactionTypeCode").value(DEFAULT_TRANSACTION_TYPE_CODE.toString()))
            .andExpect(jsonPath("$.registrationDate").value(DEFAULT_REGISTRATION_DATE.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPgChannelAuthorized() throws Exception {
        // Get the pgChannelAuthorized
        restPgChannelAuthorizedMockMvc.perform(get("/api/pg-channel-authorizeds/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePgChannelAuthorized() throws Exception {
        // Initialize the database
        pgChannelAuthorizedRepository.saveAndFlush(pgChannelAuthorized);

        int databaseSizeBeforeUpdate = pgChannelAuthorizedRepository.findAll().size();

        // Update the pgChannelAuthorized
        PgChannelAuthorized updatedPgChannelAuthorized = pgChannelAuthorizedRepository.findById(pgChannelAuthorized.getId()).get();
        // Disconnect from session so that the updates on updatedPgChannelAuthorized are not directly saved in db
        em.detach(updatedPgChannelAuthorized);
        updatedPgChannelAuthorized
            .transactionTypeCode(UPDATED_TRANSACTION_TYPE_CODE)
            .registrationDate(UPDATED_REGISTRATION_DATE)
            .active(UPDATED_ACTIVE);
        PgChannelAuthorizedDTO pgChannelAuthorizedDTO = pgChannelAuthorizedMapper.toDto(updatedPgChannelAuthorized);

        restPgChannelAuthorizedMockMvc.perform(put("/api/pg-channel-authorizeds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgChannelAuthorizedDTO)))
            .andExpect(status().isOk());

        // Validate the PgChannelAuthorized in the database
        List<PgChannelAuthorized> pgChannelAuthorizedList = pgChannelAuthorizedRepository.findAll();
        assertThat(pgChannelAuthorizedList).hasSize(databaseSizeBeforeUpdate);
        PgChannelAuthorized testPgChannelAuthorized = pgChannelAuthorizedList.get(pgChannelAuthorizedList.size() - 1);
        assertThat(testPgChannelAuthorized.getTransactionTypeCode()).isEqualTo(UPDATED_TRANSACTION_TYPE_CODE);
        assertThat(testPgChannelAuthorized.getRegistrationDate()).isEqualTo(UPDATED_REGISTRATION_DATE);
        assertThat(testPgChannelAuthorized.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingPgChannelAuthorized() throws Exception {
        int databaseSizeBeforeUpdate = pgChannelAuthorizedRepository.findAll().size();

        // Create the PgChannelAuthorized
        PgChannelAuthorizedDTO pgChannelAuthorizedDTO = pgChannelAuthorizedMapper.toDto(pgChannelAuthorized);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPgChannelAuthorizedMockMvc.perform(put("/api/pg-channel-authorizeds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgChannelAuthorizedDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PgChannelAuthorized in the database
        List<PgChannelAuthorized> pgChannelAuthorizedList = pgChannelAuthorizedRepository.findAll();
        assertThat(pgChannelAuthorizedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePgChannelAuthorized() throws Exception {
        // Initialize the database
        pgChannelAuthorizedRepository.saveAndFlush(pgChannelAuthorized);

        int databaseSizeBeforeDelete = pgChannelAuthorizedRepository.findAll().size();

        // Delete the pgChannelAuthorized
        restPgChannelAuthorizedMockMvc.perform(delete("/api/pg-channel-authorizeds/{id}", pgChannelAuthorized.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PgChannelAuthorized> pgChannelAuthorizedList = pgChannelAuthorizedRepository.findAll();
        assertThat(pgChannelAuthorizedList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PgChannelAuthorized.class);
        PgChannelAuthorized pgChannelAuthorized1 = new PgChannelAuthorized();
        pgChannelAuthorized1.setId(1L);
        PgChannelAuthorized pgChannelAuthorized2 = new PgChannelAuthorized();
        pgChannelAuthorized2.setId(pgChannelAuthorized1.getId());
        assertThat(pgChannelAuthorized1).isEqualTo(pgChannelAuthorized2);
        pgChannelAuthorized2.setId(2L);
        assertThat(pgChannelAuthorized1).isNotEqualTo(pgChannelAuthorized2);
        pgChannelAuthorized1.setId(null);
        assertThat(pgChannelAuthorized1).isNotEqualTo(pgChannelAuthorized2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PgChannelAuthorizedDTO.class);
        PgChannelAuthorizedDTO pgChannelAuthorizedDTO1 = new PgChannelAuthorizedDTO();
        pgChannelAuthorizedDTO1.setId(1L);
        PgChannelAuthorizedDTO pgChannelAuthorizedDTO2 = new PgChannelAuthorizedDTO();
        assertThat(pgChannelAuthorizedDTO1).isNotEqualTo(pgChannelAuthorizedDTO2);
        pgChannelAuthorizedDTO2.setId(pgChannelAuthorizedDTO1.getId());
        assertThat(pgChannelAuthorizedDTO1).isEqualTo(pgChannelAuthorizedDTO2);
        pgChannelAuthorizedDTO2.setId(2L);
        assertThat(pgChannelAuthorizedDTO1).isNotEqualTo(pgChannelAuthorizedDTO2);
        pgChannelAuthorizedDTO1.setId(null);
        assertThat(pgChannelAuthorizedDTO1).isNotEqualTo(pgChannelAuthorizedDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(pgChannelAuthorizedMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(pgChannelAuthorizedMapper.fromId(null)).isNull();
    }
}
