package com.electron.mfs.pg.pg8583.web.rest;

import com.electron.mfs.pg.pg8583.Pg8583ManagerApp;
import com.electron.mfs.pg.pg8583.config.SecurityBeanOverrideConfiguration;
import com.electron.mfs.pg.pg8583.domain.PgMessageModelData;
import com.electron.mfs.pg.pg8583.repository.PgMessageModelDataRepository;
import com.electron.mfs.pg.pg8583.service.PgMessageModelDataService;
import com.electron.mfs.pg.pg8583.service.dto.PgMessageModelDataDTO;
import com.electron.mfs.pg.pg8583.service.mapper.PgMessageModelDataMapper;
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
 * Integration tests for the {@Link PgMessageModelDataResource} REST controller.
 */
@EmbeddedKafka
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, Pg8583ManagerApp.class})
public class PgMessageModelDataResourceIT {

    private static final Boolean DEFAULT_MANDATORY = false;
    private static final Boolean UPDATED_MANDATORY = true;

    private static final Boolean DEFAULT_HIDDEN = false;
    private static final Boolean UPDATED_HIDDEN = true;

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private PgMessageModelDataRepository pgMessageModelDataRepository;

    @Autowired
    private PgMessageModelDataMapper pgMessageModelDataMapper;

    @Autowired
    private PgMessageModelDataService pgMessageModelDataService;

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

    private MockMvc restPgMessageModelDataMockMvc;

    private PgMessageModelData pgMessageModelData;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PgMessageModelDataResource pgMessageModelDataResource = new PgMessageModelDataResource(pgMessageModelDataService);
        this.restPgMessageModelDataMockMvc = MockMvcBuilders.standaloneSetup(pgMessageModelDataResource)
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
    public static PgMessageModelData createEntity(EntityManager em) {
        PgMessageModelData pgMessageModelData = new PgMessageModelData()
            .mandatory(DEFAULT_MANDATORY)
            .hidden(DEFAULT_HIDDEN)
            .active(DEFAULT_ACTIVE);
        return pgMessageModelData;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PgMessageModelData createUpdatedEntity(EntityManager em) {
        PgMessageModelData pgMessageModelData = new PgMessageModelData()
            .mandatory(UPDATED_MANDATORY)
            .hidden(UPDATED_HIDDEN)
            .active(UPDATED_ACTIVE);
        return pgMessageModelData;
    }

    @BeforeEach
    public void initTest() {
        pgMessageModelData = createEntity(em);
    }

    @Test
    @Transactional
    public void createPgMessageModelData() throws Exception {
        int databaseSizeBeforeCreate = pgMessageModelDataRepository.findAll().size();

        // Create the PgMessageModelData
        PgMessageModelDataDTO pgMessageModelDataDTO = pgMessageModelDataMapper.toDto(pgMessageModelData);
        restPgMessageModelDataMockMvc.perform(post("/api/pg-message-model-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgMessageModelDataDTO)))
            .andExpect(status().isCreated());

        // Validate the PgMessageModelData in the database
        List<PgMessageModelData> pgMessageModelDataList = pgMessageModelDataRepository.findAll();
        assertThat(pgMessageModelDataList).hasSize(databaseSizeBeforeCreate + 1);
        PgMessageModelData testPgMessageModelData = pgMessageModelDataList.get(pgMessageModelDataList.size() - 1);
        assertThat(testPgMessageModelData.isMandatory()).isEqualTo(DEFAULT_MANDATORY);
        assertThat(testPgMessageModelData.isHidden()).isEqualTo(DEFAULT_HIDDEN);
        assertThat(testPgMessageModelData.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createPgMessageModelDataWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pgMessageModelDataRepository.findAll().size();

        // Create the PgMessageModelData with an existing ID
        pgMessageModelData.setId(1L);
        PgMessageModelDataDTO pgMessageModelDataDTO = pgMessageModelDataMapper.toDto(pgMessageModelData);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPgMessageModelDataMockMvc.perform(post("/api/pg-message-model-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgMessageModelDataDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PgMessageModelData in the database
        List<PgMessageModelData> pgMessageModelDataList = pgMessageModelDataRepository.findAll();
        assertThat(pgMessageModelDataList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkMandatoryIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgMessageModelDataRepository.findAll().size();
        // set the field null
        pgMessageModelData.setMandatory(null);

        // Create the PgMessageModelData, which fails.
        PgMessageModelDataDTO pgMessageModelDataDTO = pgMessageModelDataMapper.toDto(pgMessageModelData);

        restPgMessageModelDataMockMvc.perform(post("/api/pg-message-model-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgMessageModelDataDTO)))
            .andExpect(status().isBadRequest());

        List<PgMessageModelData> pgMessageModelDataList = pgMessageModelDataRepository.findAll();
        assertThat(pgMessageModelDataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHiddenIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgMessageModelDataRepository.findAll().size();
        // set the field null
        pgMessageModelData.setHidden(null);

        // Create the PgMessageModelData, which fails.
        PgMessageModelDataDTO pgMessageModelDataDTO = pgMessageModelDataMapper.toDto(pgMessageModelData);

        restPgMessageModelDataMockMvc.perform(post("/api/pg-message-model-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgMessageModelDataDTO)))
            .andExpect(status().isBadRequest());

        List<PgMessageModelData> pgMessageModelDataList = pgMessageModelDataRepository.findAll();
        assertThat(pgMessageModelDataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgMessageModelDataRepository.findAll().size();
        // set the field null
        pgMessageModelData.setActive(null);

        // Create the PgMessageModelData, which fails.
        PgMessageModelDataDTO pgMessageModelDataDTO = pgMessageModelDataMapper.toDto(pgMessageModelData);

        restPgMessageModelDataMockMvc.perform(post("/api/pg-message-model-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgMessageModelDataDTO)))
            .andExpect(status().isBadRequest());

        List<PgMessageModelData> pgMessageModelDataList = pgMessageModelDataRepository.findAll();
        assertThat(pgMessageModelDataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPgMessageModelData() throws Exception {
        // Initialize the database
        pgMessageModelDataRepository.saveAndFlush(pgMessageModelData);

        // Get all the pgMessageModelDataList
        restPgMessageModelDataMockMvc.perform(get("/api/pg-message-model-data?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pgMessageModelData.getId().intValue())))
            .andExpect(jsonPath("$.[*].mandatory").value(hasItem(DEFAULT_MANDATORY.booleanValue())))
            .andExpect(jsonPath("$.[*].hidden").value(hasItem(DEFAULT_HIDDEN.booleanValue())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getPgMessageModelData() throws Exception {
        // Initialize the database
        pgMessageModelDataRepository.saveAndFlush(pgMessageModelData);

        // Get the pgMessageModelData
        restPgMessageModelDataMockMvc.perform(get("/api/pg-message-model-data/{id}", pgMessageModelData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pgMessageModelData.getId().intValue()))
            .andExpect(jsonPath("$.mandatory").value(DEFAULT_MANDATORY.booleanValue()))
            .andExpect(jsonPath("$.hidden").value(DEFAULT_HIDDEN.booleanValue()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPgMessageModelData() throws Exception {
        // Get the pgMessageModelData
        restPgMessageModelDataMockMvc.perform(get("/api/pg-message-model-data/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePgMessageModelData() throws Exception {
        // Initialize the database
        pgMessageModelDataRepository.saveAndFlush(pgMessageModelData);

        int databaseSizeBeforeUpdate = pgMessageModelDataRepository.findAll().size();

        // Update the pgMessageModelData
        PgMessageModelData updatedPgMessageModelData = pgMessageModelDataRepository.findById(pgMessageModelData.getId()).get();
        // Disconnect from session so that the updates on updatedPgMessageModelData are not directly saved in db
        em.detach(updatedPgMessageModelData);
        updatedPgMessageModelData
            .mandatory(UPDATED_MANDATORY)
            .hidden(UPDATED_HIDDEN)
            .active(UPDATED_ACTIVE);
        PgMessageModelDataDTO pgMessageModelDataDTO = pgMessageModelDataMapper.toDto(updatedPgMessageModelData);

        restPgMessageModelDataMockMvc.perform(put("/api/pg-message-model-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgMessageModelDataDTO)))
            .andExpect(status().isOk());

        // Validate the PgMessageModelData in the database
        List<PgMessageModelData> pgMessageModelDataList = pgMessageModelDataRepository.findAll();
        assertThat(pgMessageModelDataList).hasSize(databaseSizeBeforeUpdate);
        PgMessageModelData testPgMessageModelData = pgMessageModelDataList.get(pgMessageModelDataList.size() - 1);
        assertThat(testPgMessageModelData.isMandatory()).isEqualTo(UPDATED_MANDATORY);
        assertThat(testPgMessageModelData.isHidden()).isEqualTo(UPDATED_HIDDEN);
        assertThat(testPgMessageModelData.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingPgMessageModelData() throws Exception {
        int databaseSizeBeforeUpdate = pgMessageModelDataRepository.findAll().size();

        // Create the PgMessageModelData
        PgMessageModelDataDTO pgMessageModelDataDTO = pgMessageModelDataMapper.toDto(pgMessageModelData);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPgMessageModelDataMockMvc.perform(put("/api/pg-message-model-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgMessageModelDataDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PgMessageModelData in the database
        List<PgMessageModelData> pgMessageModelDataList = pgMessageModelDataRepository.findAll();
        assertThat(pgMessageModelDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePgMessageModelData() throws Exception {
        // Initialize the database
        pgMessageModelDataRepository.saveAndFlush(pgMessageModelData);

        int databaseSizeBeforeDelete = pgMessageModelDataRepository.findAll().size();

        // Delete the pgMessageModelData
        restPgMessageModelDataMockMvc.perform(delete("/api/pg-message-model-data/{id}", pgMessageModelData.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PgMessageModelData> pgMessageModelDataList = pgMessageModelDataRepository.findAll();
        assertThat(pgMessageModelDataList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PgMessageModelData.class);
        PgMessageModelData pgMessageModelData1 = new PgMessageModelData();
        pgMessageModelData1.setId(1L);
        PgMessageModelData pgMessageModelData2 = new PgMessageModelData();
        pgMessageModelData2.setId(pgMessageModelData1.getId());
        assertThat(pgMessageModelData1).isEqualTo(pgMessageModelData2);
        pgMessageModelData2.setId(2L);
        assertThat(pgMessageModelData1).isNotEqualTo(pgMessageModelData2);
        pgMessageModelData1.setId(null);
        assertThat(pgMessageModelData1).isNotEqualTo(pgMessageModelData2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PgMessageModelDataDTO.class);
        PgMessageModelDataDTO pgMessageModelDataDTO1 = new PgMessageModelDataDTO();
        pgMessageModelDataDTO1.setId(1L);
        PgMessageModelDataDTO pgMessageModelDataDTO2 = new PgMessageModelDataDTO();
        assertThat(pgMessageModelDataDTO1).isNotEqualTo(pgMessageModelDataDTO2);
        pgMessageModelDataDTO2.setId(pgMessageModelDataDTO1.getId());
        assertThat(pgMessageModelDataDTO1).isEqualTo(pgMessageModelDataDTO2);
        pgMessageModelDataDTO2.setId(2L);
        assertThat(pgMessageModelDataDTO1).isNotEqualTo(pgMessageModelDataDTO2);
        pgMessageModelDataDTO1.setId(null);
        assertThat(pgMessageModelDataDTO1).isNotEqualTo(pgMessageModelDataDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(pgMessageModelDataMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(pgMessageModelDataMapper.fromId(null)).isNull();
    }
}
