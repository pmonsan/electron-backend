package com.electron.mfs.pg.pg8583.web.rest;

import com.electron.mfs.pg.pg8583.Pg8583ManagerApp;
import com.electron.mfs.pg.pg8583.config.SecurityBeanOverrideConfiguration;
import com.electron.mfs.pg.pg8583.domain.PgChannel;
import com.electron.mfs.pg.pg8583.repository.PgChannelRepository;
import com.electron.mfs.pg.pg8583.service.PgChannelService;
import com.electron.mfs.pg.pg8583.service.dto.PgChannelDTO;
import com.electron.mfs.pg.pg8583.service.mapper.PgChannelMapper;
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
 * Integration tests for the {@Link PgChannelResource} REST controller.
 */
@EmbeddedKafka
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, Pg8583ManagerApp.class})
public class PgChannelResourceIT {

    private static final String DEFAULT_CODE = "AAAAA";
    private static final String UPDATED_CODE = "BBBBB";

    private static final String DEFAULT_LONG_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LONG_LABEL = "BBBBBBBBBB";

    private static final String DEFAULT_SHORT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_SHORT_LABEL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private PgChannelRepository pgChannelRepository;

    @Autowired
    private PgChannelMapper pgChannelMapper;

    @Autowired
    private PgChannelService pgChannelService;

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

    private MockMvc restPgChannelMockMvc;

    private PgChannel pgChannel;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PgChannelResource pgChannelResource = new PgChannelResource(pgChannelService);
        this.restPgChannelMockMvc = MockMvcBuilders.standaloneSetup(pgChannelResource)
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
    public static PgChannel createEntity(EntityManager em) {
        PgChannel pgChannel = new PgChannel()
            .code(DEFAULT_CODE)
            .longLabel(DEFAULT_LONG_LABEL)
            .shortLabel(DEFAULT_SHORT_LABEL)
            .active(DEFAULT_ACTIVE);
        return pgChannel;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PgChannel createUpdatedEntity(EntityManager em) {
        PgChannel pgChannel = new PgChannel()
            .code(UPDATED_CODE)
            .longLabel(UPDATED_LONG_LABEL)
            .shortLabel(UPDATED_SHORT_LABEL)
            .active(UPDATED_ACTIVE);
        return pgChannel;
    }

    @BeforeEach
    public void initTest() {
        pgChannel = createEntity(em);
    }

    @Test
    @Transactional
    public void createPgChannel() throws Exception {
        int databaseSizeBeforeCreate = pgChannelRepository.findAll().size();

        // Create the PgChannel
        PgChannelDTO pgChannelDTO = pgChannelMapper.toDto(pgChannel);
        restPgChannelMockMvc.perform(post("/api/pg-channels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgChannelDTO)))
            .andExpect(status().isCreated());

        // Validate the PgChannel in the database
        List<PgChannel> pgChannelList = pgChannelRepository.findAll();
        assertThat(pgChannelList).hasSize(databaseSizeBeforeCreate + 1);
        PgChannel testPgChannel = pgChannelList.get(pgChannelList.size() - 1);
        assertThat(testPgChannel.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testPgChannel.getLongLabel()).isEqualTo(DEFAULT_LONG_LABEL);
        assertThat(testPgChannel.getShortLabel()).isEqualTo(DEFAULT_SHORT_LABEL);
        assertThat(testPgChannel.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createPgChannelWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pgChannelRepository.findAll().size();

        // Create the PgChannel with an existing ID
        pgChannel.setId(1L);
        PgChannelDTO pgChannelDTO = pgChannelMapper.toDto(pgChannel);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPgChannelMockMvc.perform(post("/api/pg-channels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgChannelDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PgChannel in the database
        List<PgChannel> pgChannelList = pgChannelRepository.findAll();
        assertThat(pgChannelList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgChannelRepository.findAll().size();
        // set the field null
        pgChannel.setCode(null);

        // Create the PgChannel, which fails.
        PgChannelDTO pgChannelDTO = pgChannelMapper.toDto(pgChannel);

        restPgChannelMockMvc.perform(post("/api/pg-channels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgChannelDTO)))
            .andExpect(status().isBadRequest());

        List<PgChannel> pgChannelList = pgChannelRepository.findAll();
        assertThat(pgChannelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLongLabelIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgChannelRepository.findAll().size();
        // set the field null
        pgChannel.setLongLabel(null);

        // Create the PgChannel, which fails.
        PgChannelDTO pgChannelDTO = pgChannelMapper.toDto(pgChannel);

        restPgChannelMockMvc.perform(post("/api/pg-channels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgChannelDTO)))
            .andExpect(status().isBadRequest());

        List<PgChannel> pgChannelList = pgChannelRepository.findAll();
        assertThat(pgChannelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkShortLabelIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgChannelRepository.findAll().size();
        // set the field null
        pgChannel.setShortLabel(null);

        // Create the PgChannel, which fails.
        PgChannelDTO pgChannelDTO = pgChannelMapper.toDto(pgChannel);

        restPgChannelMockMvc.perform(post("/api/pg-channels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgChannelDTO)))
            .andExpect(status().isBadRequest());

        List<PgChannel> pgChannelList = pgChannelRepository.findAll();
        assertThat(pgChannelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgChannelRepository.findAll().size();
        // set the field null
        pgChannel.setActive(null);

        // Create the PgChannel, which fails.
        PgChannelDTO pgChannelDTO = pgChannelMapper.toDto(pgChannel);

        restPgChannelMockMvc.perform(post("/api/pg-channels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgChannelDTO)))
            .andExpect(status().isBadRequest());

        List<PgChannel> pgChannelList = pgChannelRepository.findAll();
        assertThat(pgChannelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPgChannels() throws Exception {
        // Initialize the database
        pgChannelRepository.saveAndFlush(pgChannel);

        // Get all the pgChannelList
        restPgChannelMockMvc.perform(get("/api/pg-channels?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pgChannel.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].longLabel").value(hasItem(DEFAULT_LONG_LABEL.toString())))
            .andExpect(jsonPath("$.[*].shortLabel").value(hasItem(DEFAULT_SHORT_LABEL.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getPgChannel() throws Exception {
        // Initialize the database
        pgChannelRepository.saveAndFlush(pgChannel);

        // Get the pgChannel
        restPgChannelMockMvc.perform(get("/api/pg-channels/{id}", pgChannel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pgChannel.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.longLabel").value(DEFAULT_LONG_LABEL.toString()))
            .andExpect(jsonPath("$.shortLabel").value(DEFAULT_SHORT_LABEL.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPgChannel() throws Exception {
        // Get the pgChannel
        restPgChannelMockMvc.perform(get("/api/pg-channels/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePgChannel() throws Exception {
        // Initialize the database
        pgChannelRepository.saveAndFlush(pgChannel);

        int databaseSizeBeforeUpdate = pgChannelRepository.findAll().size();

        // Update the pgChannel
        PgChannel updatedPgChannel = pgChannelRepository.findById(pgChannel.getId()).get();
        // Disconnect from session so that the updates on updatedPgChannel are not directly saved in db
        em.detach(updatedPgChannel);
        updatedPgChannel
            .code(UPDATED_CODE)
            .longLabel(UPDATED_LONG_LABEL)
            .shortLabel(UPDATED_SHORT_LABEL)
            .active(UPDATED_ACTIVE);
        PgChannelDTO pgChannelDTO = pgChannelMapper.toDto(updatedPgChannel);

        restPgChannelMockMvc.perform(put("/api/pg-channels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgChannelDTO)))
            .andExpect(status().isOk());

        // Validate the PgChannel in the database
        List<PgChannel> pgChannelList = pgChannelRepository.findAll();
        assertThat(pgChannelList).hasSize(databaseSizeBeforeUpdate);
        PgChannel testPgChannel = pgChannelList.get(pgChannelList.size() - 1);
        assertThat(testPgChannel.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testPgChannel.getLongLabel()).isEqualTo(UPDATED_LONG_LABEL);
        assertThat(testPgChannel.getShortLabel()).isEqualTo(UPDATED_SHORT_LABEL);
        assertThat(testPgChannel.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingPgChannel() throws Exception {
        int databaseSizeBeforeUpdate = pgChannelRepository.findAll().size();

        // Create the PgChannel
        PgChannelDTO pgChannelDTO = pgChannelMapper.toDto(pgChannel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPgChannelMockMvc.perform(put("/api/pg-channels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgChannelDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PgChannel in the database
        List<PgChannel> pgChannelList = pgChannelRepository.findAll();
        assertThat(pgChannelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePgChannel() throws Exception {
        // Initialize the database
        pgChannelRepository.saveAndFlush(pgChannel);

        int databaseSizeBeforeDelete = pgChannelRepository.findAll().size();

        // Delete the pgChannel
        restPgChannelMockMvc.perform(delete("/api/pg-channels/{id}", pgChannel.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PgChannel> pgChannelList = pgChannelRepository.findAll();
        assertThat(pgChannelList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PgChannel.class);
        PgChannel pgChannel1 = new PgChannel();
        pgChannel1.setId(1L);
        PgChannel pgChannel2 = new PgChannel();
        pgChannel2.setId(pgChannel1.getId());
        assertThat(pgChannel1).isEqualTo(pgChannel2);
        pgChannel2.setId(2L);
        assertThat(pgChannel1).isNotEqualTo(pgChannel2);
        pgChannel1.setId(null);
        assertThat(pgChannel1).isNotEqualTo(pgChannel2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PgChannelDTO.class);
        PgChannelDTO pgChannelDTO1 = new PgChannelDTO();
        pgChannelDTO1.setId(1L);
        PgChannelDTO pgChannelDTO2 = new PgChannelDTO();
        assertThat(pgChannelDTO1).isNotEqualTo(pgChannelDTO2);
        pgChannelDTO2.setId(pgChannelDTO1.getId());
        assertThat(pgChannelDTO1).isEqualTo(pgChannelDTO2);
        pgChannelDTO2.setId(2L);
        assertThat(pgChannelDTO1).isNotEqualTo(pgChannelDTO2);
        pgChannelDTO1.setId(null);
        assertThat(pgChannelDTO1).isNotEqualTo(pgChannelDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(pgChannelMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(pgChannelMapper.fromId(null)).isNull();
    }
}
