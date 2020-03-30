package com.electron.mfs.pg.services.web.rest;

import com.electron.mfs.pg.services.ServicesManagerApp;
import com.electron.mfs.pg.services.config.SecurityBeanOverrideConfiguration;
import com.electron.mfs.pg.services.domain.PgModule;
import com.electron.mfs.pg.services.repository.PgModuleRepository;
import com.electron.mfs.pg.services.service.PgModuleService;
import com.electron.mfs.pg.services.service.dto.PgModuleDTO;
import com.electron.mfs.pg.services.service.mapper.PgModuleMapper;
import com.electron.mfs.pg.services.web.rest.errors.ExceptionTranslator;

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

import static com.electron.mfs.pg.services.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link PgModuleResource} REST controller.
 */
@EmbeddedKafka
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, ServicesManagerApp.class})
public class PgModuleResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private PgModuleRepository pgModuleRepository;

    @Autowired
    private PgModuleMapper pgModuleMapper;

    @Autowired
    private PgModuleService pgModuleService;

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

    private MockMvc restPgModuleMockMvc;

    private PgModule pgModule;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PgModuleResource pgModuleResource = new PgModuleResource(pgModuleService);
        this.restPgModuleMockMvc = MockMvcBuilders.standaloneSetup(pgModuleResource)
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
    public static PgModule createEntity(EntityManager em) {
        PgModule pgModule = new PgModule()
            .code(DEFAULT_CODE)
            .label(DEFAULT_LABEL)
            .active(DEFAULT_ACTIVE);
        return pgModule;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PgModule createUpdatedEntity(EntityManager em) {
        PgModule pgModule = new PgModule()
            .code(UPDATED_CODE)
            .label(UPDATED_LABEL)
            .active(UPDATED_ACTIVE);
        return pgModule;
    }

    @BeforeEach
    public void initTest() {
        pgModule = createEntity(em);
    }

    @Test
    @Transactional
    public void createPgModule() throws Exception {
        int databaseSizeBeforeCreate = pgModuleRepository.findAll().size();

        // Create the PgModule
        PgModuleDTO pgModuleDTO = pgModuleMapper.toDto(pgModule);
        restPgModuleMockMvc.perform(post("/api/pg-modules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgModuleDTO)))
            .andExpect(status().isCreated());

        // Validate the PgModule in the database
        List<PgModule> pgModuleList = pgModuleRepository.findAll();
        assertThat(pgModuleList).hasSize(databaseSizeBeforeCreate + 1);
        PgModule testPgModule = pgModuleList.get(pgModuleList.size() - 1);
        assertThat(testPgModule.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testPgModule.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testPgModule.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createPgModuleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pgModuleRepository.findAll().size();

        // Create the PgModule with an existing ID
        pgModule.setId(1L);
        PgModuleDTO pgModuleDTO = pgModuleMapper.toDto(pgModule);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPgModuleMockMvc.perform(post("/api/pg-modules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgModuleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PgModule in the database
        List<PgModule> pgModuleList = pgModuleRepository.findAll();
        assertThat(pgModuleList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgModuleRepository.findAll().size();
        // set the field null
        pgModule.setCode(null);

        // Create the PgModule, which fails.
        PgModuleDTO pgModuleDTO = pgModuleMapper.toDto(pgModule);

        restPgModuleMockMvc.perform(post("/api/pg-modules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgModuleDTO)))
            .andExpect(status().isBadRequest());

        List<PgModule> pgModuleList = pgModuleRepository.findAll();
        assertThat(pgModuleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLabelIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgModuleRepository.findAll().size();
        // set the field null
        pgModule.setLabel(null);

        // Create the PgModule, which fails.
        PgModuleDTO pgModuleDTO = pgModuleMapper.toDto(pgModule);

        restPgModuleMockMvc.perform(post("/api/pg-modules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgModuleDTO)))
            .andExpect(status().isBadRequest());

        List<PgModule> pgModuleList = pgModuleRepository.findAll();
        assertThat(pgModuleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgModuleRepository.findAll().size();
        // set the field null
        pgModule.setActive(null);

        // Create the PgModule, which fails.
        PgModuleDTO pgModuleDTO = pgModuleMapper.toDto(pgModule);

        restPgModuleMockMvc.perform(post("/api/pg-modules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgModuleDTO)))
            .andExpect(status().isBadRequest());

        List<PgModule> pgModuleList = pgModuleRepository.findAll();
        assertThat(pgModuleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPgModules() throws Exception {
        // Initialize the database
        pgModuleRepository.saveAndFlush(pgModule);

        // Get all the pgModuleList
        restPgModuleMockMvc.perform(get("/api/pg-modules?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pgModule.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getPgModule() throws Exception {
        // Initialize the database
        pgModuleRepository.saveAndFlush(pgModule);

        // Get the pgModule
        restPgModuleMockMvc.perform(get("/api/pg-modules/{id}", pgModule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pgModule.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPgModule() throws Exception {
        // Get the pgModule
        restPgModuleMockMvc.perform(get("/api/pg-modules/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePgModule() throws Exception {
        // Initialize the database
        pgModuleRepository.saveAndFlush(pgModule);

        int databaseSizeBeforeUpdate = pgModuleRepository.findAll().size();

        // Update the pgModule
        PgModule updatedPgModule = pgModuleRepository.findById(pgModule.getId()).get();
        // Disconnect from session so that the updates on updatedPgModule are not directly saved in db
        em.detach(updatedPgModule);
        updatedPgModule
            .code(UPDATED_CODE)
            .label(UPDATED_LABEL)
            .active(UPDATED_ACTIVE);
        PgModuleDTO pgModuleDTO = pgModuleMapper.toDto(updatedPgModule);

        restPgModuleMockMvc.perform(put("/api/pg-modules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgModuleDTO)))
            .andExpect(status().isOk());

        // Validate the PgModule in the database
        List<PgModule> pgModuleList = pgModuleRepository.findAll();
        assertThat(pgModuleList).hasSize(databaseSizeBeforeUpdate);
        PgModule testPgModule = pgModuleList.get(pgModuleList.size() - 1);
        assertThat(testPgModule.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testPgModule.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testPgModule.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingPgModule() throws Exception {
        int databaseSizeBeforeUpdate = pgModuleRepository.findAll().size();

        // Create the PgModule
        PgModuleDTO pgModuleDTO = pgModuleMapper.toDto(pgModule);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPgModuleMockMvc.perform(put("/api/pg-modules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgModuleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PgModule in the database
        List<PgModule> pgModuleList = pgModuleRepository.findAll();
        assertThat(pgModuleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePgModule() throws Exception {
        // Initialize the database
        pgModuleRepository.saveAndFlush(pgModule);

        int databaseSizeBeforeDelete = pgModuleRepository.findAll().size();

        // Delete the pgModule
        restPgModuleMockMvc.perform(delete("/api/pg-modules/{id}", pgModule.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PgModule> pgModuleList = pgModuleRepository.findAll();
        assertThat(pgModuleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PgModule.class);
        PgModule pgModule1 = new PgModule();
        pgModule1.setId(1L);
        PgModule pgModule2 = new PgModule();
        pgModule2.setId(pgModule1.getId());
        assertThat(pgModule1).isEqualTo(pgModule2);
        pgModule2.setId(2L);
        assertThat(pgModule1).isNotEqualTo(pgModule2);
        pgModule1.setId(null);
        assertThat(pgModule1).isNotEqualTo(pgModule2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PgModuleDTO.class);
        PgModuleDTO pgModuleDTO1 = new PgModuleDTO();
        pgModuleDTO1.setId(1L);
        PgModuleDTO pgModuleDTO2 = new PgModuleDTO();
        assertThat(pgModuleDTO1).isNotEqualTo(pgModuleDTO2);
        pgModuleDTO2.setId(pgModuleDTO1.getId());
        assertThat(pgModuleDTO1).isEqualTo(pgModuleDTO2);
        pgModuleDTO2.setId(2L);
        assertThat(pgModuleDTO1).isNotEqualTo(pgModuleDTO2);
        pgModuleDTO1.setId(null);
        assertThat(pgModuleDTO1).isNotEqualTo(pgModuleDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(pgModuleMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(pgModuleMapper.fromId(null)).isNull();
    }
}
