package com.electron.mfs.pg.pg8583.web.rest;

import com.electron.mfs.pg.pg8583.Pg8583ManagerApp;
import com.electron.mfs.pg.pg8583.config.SecurityBeanOverrideConfiguration;
import com.electron.mfs.pg.pg8583.domain.PgDetailMessage;
import com.electron.mfs.pg.pg8583.repository.PgDetailMessageRepository;
import com.electron.mfs.pg.pg8583.service.PgDetailMessageService;
import com.electron.mfs.pg.pg8583.service.dto.PgDetailMessageDTO;
import com.electron.mfs.pg.pg8583.service.mapper.PgDetailMessageMapper;
import com.electron.mfs.pg.pg8583.web.rest.errors.ExceptionTranslator;

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

import static com.electron.mfs.pg.pg8583.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link PgDetailMessageResource} REST controller.
 */
@EmbeddedKafka
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, Pg8583ManagerApp.class})
public class PgDetailMessageResourceIT {

    private static final String DEFAULT_DATA_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_DATA_VALUE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private PgDetailMessageRepository pgDetailMessageRepository;

    @Autowired
    private PgDetailMessageMapper pgDetailMessageMapper;

    @Autowired
    private PgDetailMessageService pgDetailMessageService;

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

    private MockMvc restPgDetailMessageMockMvc;

    private PgDetailMessage pgDetailMessage;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PgDetailMessageResource pgDetailMessageResource = new PgDetailMessageResource(pgDetailMessageService);
        this.restPgDetailMessageMockMvc = MockMvcBuilders.standaloneSetup(pgDetailMessageResource)
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
    public static PgDetailMessage createEntity(EntityManager em) {
        PgDetailMessage pgDetailMessage = new PgDetailMessage()
            .dataValue(DEFAULT_DATA_VALUE)
            .active(DEFAULT_ACTIVE);
        return pgDetailMessage;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PgDetailMessage createUpdatedEntity(EntityManager em) {
        PgDetailMessage pgDetailMessage = new PgDetailMessage()
            .dataValue(UPDATED_DATA_VALUE)
            .active(UPDATED_ACTIVE);
        return pgDetailMessage;
    }

    @BeforeEach
    public void initTest() {
        pgDetailMessage = createEntity(em);
    }

    @Test
    @Transactional
    public void createPgDetailMessage() throws Exception {
        int databaseSizeBeforeCreate = pgDetailMessageRepository.findAll().size();

        // Create the PgDetailMessage
        PgDetailMessageDTO pgDetailMessageDTO = pgDetailMessageMapper.toDto(pgDetailMessage);
        restPgDetailMessageMockMvc.perform(post("/api/pg-detail-messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgDetailMessageDTO)))
            .andExpect(status().isCreated());

        // Validate the PgDetailMessage in the database
        List<PgDetailMessage> pgDetailMessageList = pgDetailMessageRepository.findAll();
        assertThat(pgDetailMessageList).hasSize(databaseSizeBeforeCreate + 1);
        PgDetailMessage testPgDetailMessage = pgDetailMessageList.get(pgDetailMessageList.size() - 1);
        assertThat(testPgDetailMessage.getDataValue()).isEqualTo(DEFAULT_DATA_VALUE);
        assertThat(testPgDetailMessage.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createPgDetailMessageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pgDetailMessageRepository.findAll().size();

        // Create the PgDetailMessage with an existing ID
        pgDetailMessage.setId(1L);
        PgDetailMessageDTO pgDetailMessageDTO = pgDetailMessageMapper.toDto(pgDetailMessage);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPgDetailMessageMockMvc.perform(post("/api/pg-detail-messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgDetailMessageDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PgDetailMessage in the database
        List<PgDetailMessage> pgDetailMessageList = pgDetailMessageRepository.findAll();
        assertThat(pgDetailMessageList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDataValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgDetailMessageRepository.findAll().size();
        // set the field null
        pgDetailMessage.setDataValue(null);

        // Create the PgDetailMessage, which fails.
        PgDetailMessageDTO pgDetailMessageDTO = pgDetailMessageMapper.toDto(pgDetailMessage);

        restPgDetailMessageMockMvc.perform(post("/api/pg-detail-messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgDetailMessageDTO)))
            .andExpect(status().isBadRequest());

        List<PgDetailMessage> pgDetailMessageList = pgDetailMessageRepository.findAll();
        assertThat(pgDetailMessageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgDetailMessageRepository.findAll().size();
        // set the field null
        pgDetailMessage.setActive(null);

        // Create the PgDetailMessage, which fails.
        PgDetailMessageDTO pgDetailMessageDTO = pgDetailMessageMapper.toDto(pgDetailMessage);

        restPgDetailMessageMockMvc.perform(post("/api/pg-detail-messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgDetailMessageDTO)))
            .andExpect(status().isBadRequest());

        List<PgDetailMessage> pgDetailMessageList = pgDetailMessageRepository.findAll();
        assertThat(pgDetailMessageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPgDetailMessages() throws Exception {
        // Initialize the database
        pgDetailMessageRepository.saveAndFlush(pgDetailMessage);

        // Get all the pgDetailMessageList
        restPgDetailMessageMockMvc.perform(get("/api/pg-detail-messages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pgDetailMessage.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataValue").value(hasItem(DEFAULT_DATA_VALUE.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getPgDetailMessage() throws Exception {
        // Initialize the database
        pgDetailMessageRepository.saveAndFlush(pgDetailMessage);

        // Get the pgDetailMessage
        restPgDetailMessageMockMvc.perform(get("/api/pg-detail-messages/{id}", pgDetailMessage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pgDetailMessage.getId().intValue()))
            .andExpect(jsonPath("$.dataValue").value(DEFAULT_DATA_VALUE.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPgDetailMessage() throws Exception {
        // Get the pgDetailMessage
        restPgDetailMessageMockMvc.perform(get("/api/pg-detail-messages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePgDetailMessage() throws Exception {
        // Initialize the database
        pgDetailMessageRepository.saveAndFlush(pgDetailMessage);

        int databaseSizeBeforeUpdate = pgDetailMessageRepository.findAll().size();

        // Update the pgDetailMessage
        PgDetailMessage updatedPgDetailMessage = pgDetailMessageRepository.findById(pgDetailMessage.getId()).get();
        // Disconnect from session so that the updates on updatedPgDetailMessage are not directly saved in db
        em.detach(updatedPgDetailMessage);
        updatedPgDetailMessage
            .dataValue(UPDATED_DATA_VALUE)
            .active(UPDATED_ACTIVE);
        PgDetailMessageDTO pgDetailMessageDTO = pgDetailMessageMapper.toDto(updatedPgDetailMessage);

        restPgDetailMessageMockMvc.perform(put("/api/pg-detail-messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgDetailMessageDTO)))
            .andExpect(status().isOk());

        // Validate the PgDetailMessage in the database
        List<PgDetailMessage> pgDetailMessageList = pgDetailMessageRepository.findAll();
        assertThat(pgDetailMessageList).hasSize(databaseSizeBeforeUpdate);
        PgDetailMessage testPgDetailMessage = pgDetailMessageList.get(pgDetailMessageList.size() - 1);
        assertThat(testPgDetailMessage.getDataValue()).isEqualTo(UPDATED_DATA_VALUE);
        assertThat(testPgDetailMessage.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingPgDetailMessage() throws Exception {
        int databaseSizeBeforeUpdate = pgDetailMessageRepository.findAll().size();

        // Create the PgDetailMessage
        PgDetailMessageDTO pgDetailMessageDTO = pgDetailMessageMapper.toDto(pgDetailMessage);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPgDetailMessageMockMvc.perform(put("/api/pg-detail-messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgDetailMessageDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PgDetailMessage in the database
        List<PgDetailMessage> pgDetailMessageList = pgDetailMessageRepository.findAll();
        assertThat(pgDetailMessageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePgDetailMessage() throws Exception {
        // Initialize the database
        pgDetailMessageRepository.saveAndFlush(pgDetailMessage);

        int databaseSizeBeforeDelete = pgDetailMessageRepository.findAll().size();

        // Delete the pgDetailMessage
        restPgDetailMessageMockMvc.perform(delete("/api/pg-detail-messages/{id}", pgDetailMessage.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PgDetailMessage> pgDetailMessageList = pgDetailMessageRepository.findAll();
        assertThat(pgDetailMessageList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PgDetailMessage.class);
        PgDetailMessage pgDetailMessage1 = new PgDetailMessage();
        pgDetailMessage1.setId(1L);
        PgDetailMessage pgDetailMessage2 = new PgDetailMessage();
        pgDetailMessage2.setId(pgDetailMessage1.getId());
        assertThat(pgDetailMessage1).isEqualTo(pgDetailMessage2);
        pgDetailMessage2.setId(2L);
        assertThat(pgDetailMessage1).isNotEqualTo(pgDetailMessage2);
        pgDetailMessage1.setId(null);
        assertThat(pgDetailMessage1).isNotEqualTo(pgDetailMessage2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PgDetailMessageDTO.class);
        PgDetailMessageDTO pgDetailMessageDTO1 = new PgDetailMessageDTO();
        pgDetailMessageDTO1.setId(1L);
        PgDetailMessageDTO pgDetailMessageDTO2 = new PgDetailMessageDTO();
        assertThat(pgDetailMessageDTO1).isNotEqualTo(pgDetailMessageDTO2);
        pgDetailMessageDTO2.setId(pgDetailMessageDTO1.getId());
        assertThat(pgDetailMessageDTO1).isEqualTo(pgDetailMessageDTO2);
        pgDetailMessageDTO2.setId(2L);
        assertThat(pgDetailMessageDTO1).isNotEqualTo(pgDetailMessageDTO2);
        pgDetailMessageDTO1.setId(null);
        assertThat(pgDetailMessageDTO1).isNotEqualTo(pgDetailMessageDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(pgDetailMessageMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(pgDetailMessageMapper.fromId(null)).isNull();
    }
}
