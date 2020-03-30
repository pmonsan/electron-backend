package com.electron.mfs.pg.gateway.web.rest;

import com.electron.mfs.pg.gateway.PgkernelgatewayApp;
import com.electron.mfs.pg.gateway.config.SecurityBeanOverrideConfiguration;
import com.electron.mfs.pg.gateway.domain.PgMessageModel;
import com.electron.mfs.pg.gateway.repository.PgMessageModelRepository;
import com.electron.mfs.pg.gateway.service.PgMessageModelService;
import com.electron.mfs.pg.gateway.service.dto.PgMessageModelDTO;
import com.electron.mfs.pg.gateway.service.mapper.PgMessageModelMapper;
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
 * Integration tests for the {@Link PgMessageModelResource} REST controller.
 */
@EmbeddedKafka
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, PgkernelgatewayApp.class})
public class PgMessageModelResourceIT {

    private static final String DEFAULT_CODE = "AAAAA";
    private static final String UPDATED_CODE = "BBBBB";

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private PgMessageModelRepository pgMessageModelRepository;

    @Autowired
    private PgMessageModelMapper pgMessageModelMapper;

    @Autowired
    private PgMessageModelService pgMessageModelService;

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

    private MockMvc restPgMessageModelMockMvc;

    private PgMessageModel pgMessageModel;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PgMessageModelResource pgMessageModelResource = new PgMessageModelResource(pgMessageModelService);
        this.restPgMessageModelMockMvc = MockMvcBuilders.standaloneSetup(pgMessageModelResource)
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
    public static PgMessageModel createEntity(EntityManager em) {
        PgMessageModel pgMessageModel = new PgMessageModel()
            .code(DEFAULT_CODE)
            .label(DEFAULT_LABEL)
            .comment(DEFAULT_COMMENT)
            .active(DEFAULT_ACTIVE);
        return pgMessageModel;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PgMessageModel createUpdatedEntity(EntityManager em) {
        PgMessageModel pgMessageModel = new PgMessageModel()
            .code(UPDATED_CODE)
            .label(UPDATED_LABEL)
            .comment(UPDATED_COMMENT)
            .active(UPDATED_ACTIVE);
        return pgMessageModel;
    }

    @BeforeEach
    public void initTest() {
        pgMessageModel = createEntity(em);
    }

    @Test
    @Transactional
    public void createPgMessageModel() throws Exception {
        int databaseSizeBeforeCreate = pgMessageModelRepository.findAll().size();

        // Create the PgMessageModel
        PgMessageModelDTO pgMessageModelDTO = pgMessageModelMapper.toDto(pgMessageModel);
        restPgMessageModelMockMvc.perform(post("/api/pg-message-models")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgMessageModelDTO)))
            .andExpect(status().isCreated());

        // Validate the PgMessageModel in the database
        List<PgMessageModel> pgMessageModelList = pgMessageModelRepository.findAll();
        assertThat(pgMessageModelList).hasSize(databaseSizeBeforeCreate + 1);
        PgMessageModel testPgMessageModel = pgMessageModelList.get(pgMessageModelList.size() - 1);
        assertThat(testPgMessageModel.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testPgMessageModel.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testPgMessageModel.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testPgMessageModel.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createPgMessageModelWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pgMessageModelRepository.findAll().size();

        // Create the PgMessageModel with an existing ID
        pgMessageModel.setId(1L);
        PgMessageModelDTO pgMessageModelDTO = pgMessageModelMapper.toDto(pgMessageModel);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPgMessageModelMockMvc.perform(post("/api/pg-message-models")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgMessageModelDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PgMessageModel in the database
        List<PgMessageModel> pgMessageModelList = pgMessageModelRepository.findAll();
        assertThat(pgMessageModelList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgMessageModelRepository.findAll().size();
        // set the field null
        pgMessageModel.setCode(null);

        // Create the PgMessageModel, which fails.
        PgMessageModelDTO pgMessageModelDTO = pgMessageModelMapper.toDto(pgMessageModel);

        restPgMessageModelMockMvc.perform(post("/api/pg-message-models")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgMessageModelDTO)))
            .andExpect(status().isBadRequest());

        List<PgMessageModel> pgMessageModelList = pgMessageModelRepository.findAll();
        assertThat(pgMessageModelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLabelIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgMessageModelRepository.findAll().size();
        // set the field null
        pgMessageModel.setLabel(null);

        // Create the PgMessageModel, which fails.
        PgMessageModelDTO pgMessageModelDTO = pgMessageModelMapper.toDto(pgMessageModel);

        restPgMessageModelMockMvc.perform(post("/api/pg-message-models")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgMessageModelDTO)))
            .andExpect(status().isBadRequest());

        List<PgMessageModel> pgMessageModelList = pgMessageModelRepository.findAll();
        assertThat(pgMessageModelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgMessageModelRepository.findAll().size();
        // set the field null
        pgMessageModel.setActive(null);

        // Create the PgMessageModel, which fails.
        PgMessageModelDTO pgMessageModelDTO = pgMessageModelMapper.toDto(pgMessageModel);

        restPgMessageModelMockMvc.perform(post("/api/pg-message-models")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgMessageModelDTO)))
            .andExpect(status().isBadRequest());

        List<PgMessageModel> pgMessageModelList = pgMessageModelRepository.findAll();
        assertThat(pgMessageModelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPgMessageModels() throws Exception {
        // Initialize the database
        pgMessageModelRepository.saveAndFlush(pgMessageModel);

        // Get all the pgMessageModelList
        restPgMessageModelMockMvc.perform(get("/api/pg-message-models?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pgMessageModel.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getPgMessageModel() throws Exception {
        // Initialize the database
        pgMessageModelRepository.saveAndFlush(pgMessageModel);

        // Get the pgMessageModel
        restPgMessageModelMockMvc.perform(get("/api/pg-message-models/{id}", pgMessageModel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pgMessageModel.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPgMessageModel() throws Exception {
        // Get the pgMessageModel
        restPgMessageModelMockMvc.perform(get("/api/pg-message-models/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePgMessageModel() throws Exception {
        // Initialize the database
        pgMessageModelRepository.saveAndFlush(pgMessageModel);

        int databaseSizeBeforeUpdate = pgMessageModelRepository.findAll().size();

        // Update the pgMessageModel
        PgMessageModel updatedPgMessageModel = pgMessageModelRepository.findById(pgMessageModel.getId()).get();
        // Disconnect from session so that the updates on updatedPgMessageModel are not directly saved in db
        em.detach(updatedPgMessageModel);
        updatedPgMessageModel
            .code(UPDATED_CODE)
            .label(UPDATED_LABEL)
            .comment(UPDATED_COMMENT)
            .active(UPDATED_ACTIVE);
        PgMessageModelDTO pgMessageModelDTO = pgMessageModelMapper.toDto(updatedPgMessageModel);

        restPgMessageModelMockMvc.perform(put("/api/pg-message-models")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgMessageModelDTO)))
            .andExpect(status().isOk());

        // Validate the PgMessageModel in the database
        List<PgMessageModel> pgMessageModelList = pgMessageModelRepository.findAll();
        assertThat(pgMessageModelList).hasSize(databaseSizeBeforeUpdate);
        PgMessageModel testPgMessageModel = pgMessageModelList.get(pgMessageModelList.size() - 1);
        assertThat(testPgMessageModel.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testPgMessageModel.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testPgMessageModel.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testPgMessageModel.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingPgMessageModel() throws Exception {
        int databaseSizeBeforeUpdate = pgMessageModelRepository.findAll().size();

        // Create the PgMessageModel
        PgMessageModelDTO pgMessageModelDTO = pgMessageModelMapper.toDto(pgMessageModel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPgMessageModelMockMvc.perform(put("/api/pg-message-models")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgMessageModelDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PgMessageModel in the database
        List<PgMessageModel> pgMessageModelList = pgMessageModelRepository.findAll();
        assertThat(pgMessageModelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePgMessageModel() throws Exception {
        // Initialize the database
        pgMessageModelRepository.saveAndFlush(pgMessageModel);

        int databaseSizeBeforeDelete = pgMessageModelRepository.findAll().size();

        // Delete the pgMessageModel
        restPgMessageModelMockMvc.perform(delete("/api/pg-message-models/{id}", pgMessageModel.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PgMessageModel> pgMessageModelList = pgMessageModelRepository.findAll();
        assertThat(pgMessageModelList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PgMessageModel.class);
        PgMessageModel pgMessageModel1 = new PgMessageModel();
        pgMessageModel1.setId(1L);
        PgMessageModel pgMessageModel2 = new PgMessageModel();
        pgMessageModel2.setId(pgMessageModel1.getId());
        assertThat(pgMessageModel1).isEqualTo(pgMessageModel2);
        pgMessageModel2.setId(2L);
        assertThat(pgMessageModel1).isNotEqualTo(pgMessageModel2);
        pgMessageModel1.setId(null);
        assertThat(pgMessageModel1).isNotEqualTo(pgMessageModel2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PgMessageModelDTO.class);
        PgMessageModelDTO pgMessageModelDTO1 = new PgMessageModelDTO();
        pgMessageModelDTO1.setId(1L);
        PgMessageModelDTO pgMessageModelDTO2 = new PgMessageModelDTO();
        assertThat(pgMessageModelDTO1).isNotEqualTo(pgMessageModelDTO2);
        pgMessageModelDTO2.setId(pgMessageModelDTO1.getId());
        assertThat(pgMessageModelDTO1).isEqualTo(pgMessageModelDTO2);
        pgMessageModelDTO2.setId(2L);
        assertThat(pgMessageModelDTO1).isNotEqualTo(pgMessageModelDTO2);
        pgMessageModelDTO1.setId(null);
        assertThat(pgMessageModelDTO1).isNotEqualTo(pgMessageModelDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(pgMessageModelMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(pgMessageModelMapper.fromId(null)).isNull();
    }
}
