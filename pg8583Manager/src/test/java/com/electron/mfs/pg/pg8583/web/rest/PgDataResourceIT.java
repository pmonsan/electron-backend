package com.electron.mfs.pg.pg8583.web.rest;

import com.electron.mfs.pg.pg8583.Pg8583ManagerApp;
import com.electron.mfs.pg.pg8583.config.SecurityBeanOverrideConfiguration;
import com.electron.mfs.pg.pg8583.domain.PgData;
import com.electron.mfs.pg.pg8583.repository.PgDataRepository;
import com.electron.mfs.pg.pg8583.service.PgDataService;
import com.electron.mfs.pg.pg8583.service.dto.PgDataDTO;
import com.electron.mfs.pg.pg8583.service.mapper.PgDataMapper;
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
 * Integration tests for the {@Link PgDataResource} REST controller.
 */
@EmbeddedKafka
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, Pg8583ManagerApp.class})
public class PgDataResourceIT {

    private static final String DEFAULT_CODE = "AAAAA";
    private static final String UPDATED_CODE = "BBBBB";

    private static final String DEFAULT_LONG_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LONG_LABEL = "BBBBBBBBBB";

    private static final String DEFAULT_SHORT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_SHORT_LABEL = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private PgDataRepository pgDataRepository;

    @Autowired
    private PgDataMapper pgDataMapper;

    @Autowired
    private PgDataService pgDataService;

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

    private MockMvc restPgDataMockMvc;

    private PgData pgData;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PgDataResource pgDataResource = new PgDataResource(pgDataService);
        this.restPgDataMockMvc = MockMvcBuilders.standaloneSetup(pgDataResource)
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
    public static PgData createEntity(EntityManager em) {
        PgData pgData = new PgData()
            .code(DEFAULT_CODE)
            .longLabel(DEFAULT_LONG_LABEL)
            .shortLabel(DEFAULT_SHORT_LABEL)
            .comment(DEFAULT_COMMENT)
            .active(DEFAULT_ACTIVE);
        return pgData;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PgData createUpdatedEntity(EntityManager em) {
        PgData pgData = new PgData()
            .code(UPDATED_CODE)
            .longLabel(UPDATED_LONG_LABEL)
            .shortLabel(UPDATED_SHORT_LABEL)
            .comment(UPDATED_COMMENT)
            .active(UPDATED_ACTIVE);
        return pgData;
    }

    @BeforeEach
    public void initTest() {
        pgData = createEntity(em);
    }

    @Test
    @Transactional
    public void createPgData() throws Exception {
        int databaseSizeBeforeCreate = pgDataRepository.findAll().size();

        // Create the PgData
        PgDataDTO pgDataDTO = pgDataMapper.toDto(pgData);
        restPgDataMockMvc.perform(post("/api/pg-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgDataDTO)))
            .andExpect(status().isCreated());

        // Validate the PgData in the database
        List<PgData> pgDataList = pgDataRepository.findAll();
        assertThat(pgDataList).hasSize(databaseSizeBeforeCreate + 1);
        PgData testPgData = pgDataList.get(pgDataList.size() - 1);
        assertThat(testPgData.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testPgData.getLongLabel()).isEqualTo(DEFAULT_LONG_LABEL);
        assertThat(testPgData.getShortLabel()).isEqualTo(DEFAULT_SHORT_LABEL);
        assertThat(testPgData.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testPgData.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createPgDataWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pgDataRepository.findAll().size();

        // Create the PgData with an existing ID
        pgData.setId(1L);
        PgDataDTO pgDataDTO = pgDataMapper.toDto(pgData);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPgDataMockMvc.perform(post("/api/pg-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgDataDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PgData in the database
        List<PgData> pgDataList = pgDataRepository.findAll();
        assertThat(pgDataList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgDataRepository.findAll().size();
        // set the field null
        pgData.setCode(null);

        // Create the PgData, which fails.
        PgDataDTO pgDataDTO = pgDataMapper.toDto(pgData);

        restPgDataMockMvc.perform(post("/api/pg-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgDataDTO)))
            .andExpect(status().isBadRequest());

        List<PgData> pgDataList = pgDataRepository.findAll();
        assertThat(pgDataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgDataRepository.findAll().size();
        // set the field null
        pgData.setActive(null);

        // Create the PgData, which fails.
        PgDataDTO pgDataDTO = pgDataMapper.toDto(pgData);

        restPgDataMockMvc.perform(post("/api/pg-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgDataDTO)))
            .andExpect(status().isBadRequest());

        List<PgData> pgDataList = pgDataRepository.findAll();
        assertThat(pgDataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPgData() throws Exception {
        // Initialize the database
        pgDataRepository.saveAndFlush(pgData);

        // Get all the pgDataList
        restPgDataMockMvc.perform(get("/api/pg-data?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pgData.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].longLabel").value(hasItem(DEFAULT_LONG_LABEL.toString())))
            .andExpect(jsonPath("$.[*].shortLabel").value(hasItem(DEFAULT_SHORT_LABEL.toString())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getPgData() throws Exception {
        // Initialize the database
        pgDataRepository.saveAndFlush(pgData);

        // Get the pgData
        restPgDataMockMvc.perform(get("/api/pg-data/{id}", pgData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pgData.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.longLabel").value(DEFAULT_LONG_LABEL.toString()))
            .andExpect(jsonPath("$.shortLabel").value(DEFAULT_SHORT_LABEL.toString()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPgData() throws Exception {
        // Get the pgData
        restPgDataMockMvc.perform(get("/api/pg-data/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePgData() throws Exception {
        // Initialize the database
        pgDataRepository.saveAndFlush(pgData);

        int databaseSizeBeforeUpdate = pgDataRepository.findAll().size();

        // Update the pgData
        PgData updatedPgData = pgDataRepository.findById(pgData.getId()).get();
        // Disconnect from session so that the updates on updatedPgData are not directly saved in db
        em.detach(updatedPgData);
        updatedPgData
            .code(UPDATED_CODE)
            .longLabel(UPDATED_LONG_LABEL)
            .shortLabel(UPDATED_SHORT_LABEL)
            .comment(UPDATED_COMMENT)
            .active(UPDATED_ACTIVE);
        PgDataDTO pgDataDTO = pgDataMapper.toDto(updatedPgData);

        restPgDataMockMvc.perform(put("/api/pg-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgDataDTO)))
            .andExpect(status().isOk());

        // Validate the PgData in the database
        List<PgData> pgDataList = pgDataRepository.findAll();
        assertThat(pgDataList).hasSize(databaseSizeBeforeUpdate);
        PgData testPgData = pgDataList.get(pgDataList.size() - 1);
        assertThat(testPgData.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testPgData.getLongLabel()).isEqualTo(UPDATED_LONG_LABEL);
        assertThat(testPgData.getShortLabel()).isEqualTo(UPDATED_SHORT_LABEL);
        assertThat(testPgData.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testPgData.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingPgData() throws Exception {
        int databaseSizeBeforeUpdate = pgDataRepository.findAll().size();

        // Create the PgData
        PgDataDTO pgDataDTO = pgDataMapper.toDto(pgData);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPgDataMockMvc.perform(put("/api/pg-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgDataDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PgData in the database
        List<PgData> pgDataList = pgDataRepository.findAll();
        assertThat(pgDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePgData() throws Exception {
        // Initialize the database
        pgDataRepository.saveAndFlush(pgData);

        int databaseSizeBeforeDelete = pgDataRepository.findAll().size();

        // Delete the pgData
        restPgDataMockMvc.perform(delete("/api/pg-data/{id}", pgData.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PgData> pgDataList = pgDataRepository.findAll();
        assertThat(pgDataList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PgData.class);
        PgData pgData1 = new PgData();
        pgData1.setId(1L);
        PgData pgData2 = new PgData();
        pgData2.setId(pgData1.getId());
        assertThat(pgData1).isEqualTo(pgData2);
        pgData2.setId(2L);
        assertThat(pgData1).isNotEqualTo(pgData2);
        pgData1.setId(null);
        assertThat(pgData1).isNotEqualTo(pgData2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PgDataDTO.class);
        PgDataDTO pgDataDTO1 = new PgDataDTO();
        pgDataDTO1.setId(1L);
        PgDataDTO pgDataDTO2 = new PgDataDTO();
        assertThat(pgDataDTO1).isNotEqualTo(pgDataDTO2);
        pgDataDTO2.setId(pgDataDTO1.getId());
        assertThat(pgDataDTO1).isEqualTo(pgDataDTO2);
        pgDataDTO2.setId(2L);
        assertThat(pgDataDTO1).isNotEqualTo(pgDataDTO2);
        pgDataDTO1.setId(null);
        assertThat(pgDataDTO1).isNotEqualTo(pgDataDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(pgDataMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(pgDataMapper.fromId(null)).isNull();
    }
}
