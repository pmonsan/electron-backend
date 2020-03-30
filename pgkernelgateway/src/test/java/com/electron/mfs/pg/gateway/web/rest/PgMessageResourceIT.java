package com.electron.mfs.pg.gateway.web.rest;

import com.electron.mfs.pg.gateway.PgkernelgatewayApp;
import com.electron.mfs.pg.gateway.config.SecurityBeanOverrideConfiguration;
import com.electron.mfs.pg.gateway.domain.PgMessage;
import com.electron.mfs.pg.gateway.repository.PgMessageRepository;
import com.electron.mfs.pg.gateway.service.PgMessageService;
import com.electron.mfs.pg.gateway.service.dto.PgMessageDTO;
import com.electron.mfs.pg.gateway.service.mapper.PgMessageMapper;
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
 * Integration tests for the {@Link PgMessageResource} REST controller.
 */
@EmbeddedKafka
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, PgkernelgatewayApp.class})
public class PgMessageResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final Instant DEFAULT_MESSAGE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MESSAGE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private PgMessageRepository pgMessageRepository;

    @Autowired
    private PgMessageMapper pgMessageMapper;

    @Autowired
    private PgMessageService pgMessageService;

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

    private MockMvc restPgMessageMockMvc;

    private PgMessage pgMessage;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PgMessageResource pgMessageResource = new PgMessageResource(pgMessageService);
        this.restPgMessageMockMvc = MockMvcBuilders.standaloneSetup(pgMessageResource)
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
    public static PgMessage createEntity(EntityManager em) {
        PgMessage pgMessage = new PgMessage()
            .code(DEFAULT_CODE)
            .label(DEFAULT_LABEL)
            .messageDate(DEFAULT_MESSAGE_DATE)
            .comment(DEFAULT_COMMENT)
            .active(DEFAULT_ACTIVE);
        return pgMessage;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PgMessage createUpdatedEntity(EntityManager em) {
        PgMessage pgMessage = new PgMessage()
            .code(UPDATED_CODE)
            .label(UPDATED_LABEL)
            .messageDate(UPDATED_MESSAGE_DATE)
            .comment(UPDATED_COMMENT)
            .active(UPDATED_ACTIVE);
        return pgMessage;
    }

    @BeforeEach
    public void initTest() {
        pgMessage = createEntity(em);
    }

    @Test
    @Transactional
    public void createPgMessage() throws Exception {
        int databaseSizeBeforeCreate = pgMessageRepository.findAll().size();

        // Create the PgMessage
        PgMessageDTO pgMessageDTO = pgMessageMapper.toDto(pgMessage);
        restPgMessageMockMvc.perform(post("/api/pg-messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgMessageDTO)))
            .andExpect(status().isCreated());

        // Validate the PgMessage in the database
        List<PgMessage> pgMessageList = pgMessageRepository.findAll();
        assertThat(pgMessageList).hasSize(databaseSizeBeforeCreate + 1);
        PgMessage testPgMessage = pgMessageList.get(pgMessageList.size() - 1);
        assertThat(testPgMessage.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testPgMessage.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testPgMessage.getMessageDate()).isEqualTo(DEFAULT_MESSAGE_DATE);
        assertThat(testPgMessage.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testPgMessage.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createPgMessageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pgMessageRepository.findAll().size();

        // Create the PgMessage with an existing ID
        pgMessage.setId(1L);
        PgMessageDTO pgMessageDTO = pgMessageMapper.toDto(pgMessage);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPgMessageMockMvc.perform(post("/api/pg-messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgMessageDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PgMessage in the database
        List<PgMessage> pgMessageList = pgMessageRepository.findAll();
        assertThat(pgMessageList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgMessageRepository.findAll().size();
        // set the field null
        pgMessage.setCode(null);

        // Create the PgMessage, which fails.
        PgMessageDTO pgMessageDTO = pgMessageMapper.toDto(pgMessage);

        restPgMessageMockMvc.perform(post("/api/pg-messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgMessageDTO)))
            .andExpect(status().isBadRequest());

        List<PgMessage> pgMessageList = pgMessageRepository.findAll();
        assertThat(pgMessageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLabelIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgMessageRepository.findAll().size();
        // set the field null
        pgMessage.setLabel(null);

        // Create the PgMessage, which fails.
        PgMessageDTO pgMessageDTO = pgMessageMapper.toDto(pgMessage);

        restPgMessageMockMvc.perform(post("/api/pg-messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgMessageDTO)))
            .andExpect(status().isBadRequest());

        List<PgMessage> pgMessageList = pgMessageRepository.findAll();
        assertThat(pgMessageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMessageDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgMessageRepository.findAll().size();
        // set the field null
        pgMessage.setMessageDate(null);

        // Create the PgMessage, which fails.
        PgMessageDTO pgMessageDTO = pgMessageMapper.toDto(pgMessage);

        restPgMessageMockMvc.perform(post("/api/pg-messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgMessageDTO)))
            .andExpect(status().isBadRequest());

        List<PgMessage> pgMessageList = pgMessageRepository.findAll();
        assertThat(pgMessageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgMessageRepository.findAll().size();
        // set the field null
        pgMessage.setActive(null);

        // Create the PgMessage, which fails.
        PgMessageDTO pgMessageDTO = pgMessageMapper.toDto(pgMessage);

        restPgMessageMockMvc.perform(post("/api/pg-messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgMessageDTO)))
            .andExpect(status().isBadRequest());

        List<PgMessage> pgMessageList = pgMessageRepository.findAll();
        assertThat(pgMessageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPgMessages() throws Exception {
        // Initialize the database
        pgMessageRepository.saveAndFlush(pgMessage);

        // Get all the pgMessageList
        restPgMessageMockMvc.perform(get("/api/pg-messages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pgMessage.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())))
            .andExpect(jsonPath("$.[*].messageDate").value(hasItem(DEFAULT_MESSAGE_DATE.toString())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getPgMessage() throws Exception {
        // Initialize the database
        pgMessageRepository.saveAndFlush(pgMessage);

        // Get the pgMessage
        restPgMessageMockMvc.perform(get("/api/pg-messages/{id}", pgMessage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pgMessage.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()))
            .andExpect(jsonPath("$.messageDate").value(DEFAULT_MESSAGE_DATE.toString()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPgMessage() throws Exception {
        // Get the pgMessage
        restPgMessageMockMvc.perform(get("/api/pg-messages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePgMessage() throws Exception {
        // Initialize the database
        pgMessageRepository.saveAndFlush(pgMessage);

        int databaseSizeBeforeUpdate = pgMessageRepository.findAll().size();

        // Update the pgMessage
        PgMessage updatedPgMessage = pgMessageRepository.findById(pgMessage.getId()).get();
        // Disconnect from session so that the updates on updatedPgMessage are not directly saved in db
        em.detach(updatedPgMessage);
        updatedPgMessage
            .code(UPDATED_CODE)
            .label(UPDATED_LABEL)
            .messageDate(UPDATED_MESSAGE_DATE)
            .comment(UPDATED_COMMENT)
            .active(UPDATED_ACTIVE);
        PgMessageDTO pgMessageDTO = pgMessageMapper.toDto(updatedPgMessage);

        restPgMessageMockMvc.perform(put("/api/pg-messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgMessageDTO)))
            .andExpect(status().isOk());

        // Validate the PgMessage in the database
        List<PgMessage> pgMessageList = pgMessageRepository.findAll();
        assertThat(pgMessageList).hasSize(databaseSizeBeforeUpdate);
        PgMessage testPgMessage = pgMessageList.get(pgMessageList.size() - 1);
        assertThat(testPgMessage.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testPgMessage.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testPgMessage.getMessageDate()).isEqualTo(UPDATED_MESSAGE_DATE);
        assertThat(testPgMessage.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testPgMessage.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingPgMessage() throws Exception {
        int databaseSizeBeforeUpdate = pgMessageRepository.findAll().size();

        // Create the PgMessage
        PgMessageDTO pgMessageDTO = pgMessageMapper.toDto(pgMessage);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPgMessageMockMvc.perform(put("/api/pg-messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgMessageDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PgMessage in the database
        List<PgMessage> pgMessageList = pgMessageRepository.findAll();
        assertThat(pgMessageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePgMessage() throws Exception {
        // Initialize the database
        pgMessageRepository.saveAndFlush(pgMessage);

        int databaseSizeBeforeDelete = pgMessageRepository.findAll().size();

        // Delete the pgMessage
        restPgMessageMockMvc.perform(delete("/api/pg-messages/{id}", pgMessage.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PgMessage> pgMessageList = pgMessageRepository.findAll();
        assertThat(pgMessageList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PgMessage.class);
        PgMessage pgMessage1 = new PgMessage();
        pgMessage1.setId(1L);
        PgMessage pgMessage2 = new PgMessage();
        pgMessage2.setId(pgMessage1.getId());
        assertThat(pgMessage1).isEqualTo(pgMessage2);
        pgMessage2.setId(2L);
        assertThat(pgMessage1).isNotEqualTo(pgMessage2);
        pgMessage1.setId(null);
        assertThat(pgMessage1).isNotEqualTo(pgMessage2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PgMessageDTO.class);
        PgMessageDTO pgMessageDTO1 = new PgMessageDTO();
        pgMessageDTO1.setId(1L);
        PgMessageDTO pgMessageDTO2 = new PgMessageDTO();
        assertThat(pgMessageDTO1).isNotEqualTo(pgMessageDTO2);
        pgMessageDTO2.setId(pgMessageDTO1.getId());
        assertThat(pgMessageDTO1).isEqualTo(pgMessageDTO2);
        pgMessageDTO2.setId(2L);
        assertThat(pgMessageDTO1).isNotEqualTo(pgMessageDTO2);
        pgMessageDTO1.setId(null);
        assertThat(pgMessageDTO1).isNotEqualTo(pgMessageDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(pgMessageMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(pgMessageMapper.fromId(null)).isNull();
    }
}
