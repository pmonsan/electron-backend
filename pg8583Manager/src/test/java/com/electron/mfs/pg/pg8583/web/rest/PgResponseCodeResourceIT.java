package com.electron.mfs.pg.pg8583.web.rest;

import com.electron.mfs.pg.pg8583.Pg8583ManagerApp;
import com.electron.mfs.pg.pg8583.config.SecurityBeanOverrideConfiguration;
import com.electron.mfs.pg.pg8583.domain.PgResponseCode;
import com.electron.mfs.pg.pg8583.repository.PgResponseCodeRepository;
import com.electron.mfs.pg.pg8583.service.PgResponseCodeService;
import com.electron.mfs.pg.pg8583.service.dto.PgResponseCodeDTO;
import com.electron.mfs.pg.pg8583.service.mapper.PgResponseCodeMapper;
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
 * Integration tests for the {@Link PgResponseCodeResource} REST controller.
 */
@EmbeddedKafka
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, Pg8583ManagerApp.class})
public class PgResponseCodeResourceIT {

    private static final String DEFAULT_CODE = "AAAAA";
    private static final String UPDATED_CODE = "BBBBB";

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private PgResponseCodeRepository pgResponseCodeRepository;

    @Autowired
    private PgResponseCodeMapper pgResponseCodeMapper;

    @Autowired
    private PgResponseCodeService pgResponseCodeService;

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

    private MockMvc restPgResponseCodeMockMvc;

    private PgResponseCode pgResponseCode;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PgResponseCodeResource pgResponseCodeResource = new PgResponseCodeResource(pgResponseCodeService);
        this.restPgResponseCodeMockMvc = MockMvcBuilders.standaloneSetup(pgResponseCodeResource)
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
    public static PgResponseCode createEntity(EntityManager em) {
        PgResponseCode pgResponseCode = new PgResponseCode()
            .code(DEFAULT_CODE)
            .label(DEFAULT_LABEL)
            .description(DEFAULT_DESCRIPTION)
            .active(DEFAULT_ACTIVE);
        return pgResponseCode;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PgResponseCode createUpdatedEntity(EntityManager em) {
        PgResponseCode pgResponseCode = new PgResponseCode()
            .code(UPDATED_CODE)
            .label(UPDATED_LABEL)
            .description(UPDATED_DESCRIPTION)
            .active(UPDATED_ACTIVE);
        return pgResponseCode;
    }

    @BeforeEach
    public void initTest() {
        pgResponseCode = createEntity(em);
    }

    @Test
    @Transactional
    public void createPgResponseCode() throws Exception {
        int databaseSizeBeforeCreate = pgResponseCodeRepository.findAll().size();

        // Create the PgResponseCode
        PgResponseCodeDTO pgResponseCodeDTO = pgResponseCodeMapper.toDto(pgResponseCode);
        restPgResponseCodeMockMvc.perform(post("/api/pg-response-codes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgResponseCodeDTO)))
            .andExpect(status().isCreated());

        // Validate the PgResponseCode in the database
        List<PgResponseCode> pgResponseCodeList = pgResponseCodeRepository.findAll();
        assertThat(pgResponseCodeList).hasSize(databaseSizeBeforeCreate + 1);
        PgResponseCode testPgResponseCode = pgResponseCodeList.get(pgResponseCodeList.size() - 1);
        assertThat(testPgResponseCode.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testPgResponseCode.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testPgResponseCode.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPgResponseCode.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createPgResponseCodeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pgResponseCodeRepository.findAll().size();

        // Create the PgResponseCode with an existing ID
        pgResponseCode.setId(1L);
        PgResponseCodeDTO pgResponseCodeDTO = pgResponseCodeMapper.toDto(pgResponseCode);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPgResponseCodeMockMvc.perform(post("/api/pg-response-codes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgResponseCodeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PgResponseCode in the database
        List<PgResponseCode> pgResponseCodeList = pgResponseCodeRepository.findAll();
        assertThat(pgResponseCodeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgResponseCodeRepository.findAll().size();
        // set the field null
        pgResponseCode.setCode(null);

        // Create the PgResponseCode, which fails.
        PgResponseCodeDTO pgResponseCodeDTO = pgResponseCodeMapper.toDto(pgResponseCode);

        restPgResponseCodeMockMvc.perform(post("/api/pg-response-codes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgResponseCodeDTO)))
            .andExpect(status().isBadRequest());

        List<PgResponseCode> pgResponseCodeList = pgResponseCodeRepository.findAll();
        assertThat(pgResponseCodeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLabelIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgResponseCodeRepository.findAll().size();
        // set the field null
        pgResponseCode.setLabel(null);

        // Create the PgResponseCode, which fails.
        PgResponseCodeDTO pgResponseCodeDTO = pgResponseCodeMapper.toDto(pgResponseCode);

        restPgResponseCodeMockMvc.perform(post("/api/pg-response-codes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgResponseCodeDTO)))
            .andExpect(status().isBadRequest());

        List<PgResponseCode> pgResponseCodeList = pgResponseCodeRepository.findAll();
        assertThat(pgResponseCodeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgResponseCodeRepository.findAll().size();
        // set the field null
        pgResponseCode.setActive(null);

        // Create the PgResponseCode, which fails.
        PgResponseCodeDTO pgResponseCodeDTO = pgResponseCodeMapper.toDto(pgResponseCode);

        restPgResponseCodeMockMvc.perform(post("/api/pg-response-codes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgResponseCodeDTO)))
            .andExpect(status().isBadRequest());

        List<PgResponseCode> pgResponseCodeList = pgResponseCodeRepository.findAll();
        assertThat(pgResponseCodeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPgResponseCodes() throws Exception {
        // Initialize the database
        pgResponseCodeRepository.saveAndFlush(pgResponseCode);

        // Get all the pgResponseCodeList
        restPgResponseCodeMockMvc.perform(get("/api/pg-response-codes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pgResponseCode.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getPgResponseCode() throws Exception {
        // Initialize the database
        pgResponseCodeRepository.saveAndFlush(pgResponseCode);

        // Get the pgResponseCode
        restPgResponseCodeMockMvc.perform(get("/api/pg-response-codes/{id}", pgResponseCode.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pgResponseCode.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPgResponseCode() throws Exception {
        // Get the pgResponseCode
        restPgResponseCodeMockMvc.perform(get("/api/pg-response-codes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePgResponseCode() throws Exception {
        // Initialize the database
        pgResponseCodeRepository.saveAndFlush(pgResponseCode);

        int databaseSizeBeforeUpdate = pgResponseCodeRepository.findAll().size();

        // Update the pgResponseCode
        PgResponseCode updatedPgResponseCode = pgResponseCodeRepository.findById(pgResponseCode.getId()).get();
        // Disconnect from session so that the updates on updatedPgResponseCode are not directly saved in db
        em.detach(updatedPgResponseCode);
        updatedPgResponseCode
            .code(UPDATED_CODE)
            .label(UPDATED_LABEL)
            .description(UPDATED_DESCRIPTION)
            .active(UPDATED_ACTIVE);
        PgResponseCodeDTO pgResponseCodeDTO = pgResponseCodeMapper.toDto(updatedPgResponseCode);

        restPgResponseCodeMockMvc.perform(put("/api/pg-response-codes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgResponseCodeDTO)))
            .andExpect(status().isOk());

        // Validate the PgResponseCode in the database
        List<PgResponseCode> pgResponseCodeList = pgResponseCodeRepository.findAll();
        assertThat(pgResponseCodeList).hasSize(databaseSizeBeforeUpdate);
        PgResponseCode testPgResponseCode = pgResponseCodeList.get(pgResponseCodeList.size() - 1);
        assertThat(testPgResponseCode.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testPgResponseCode.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testPgResponseCode.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPgResponseCode.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingPgResponseCode() throws Exception {
        int databaseSizeBeforeUpdate = pgResponseCodeRepository.findAll().size();

        // Create the PgResponseCode
        PgResponseCodeDTO pgResponseCodeDTO = pgResponseCodeMapper.toDto(pgResponseCode);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPgResponseCodeMockMvc.perform(put("/api/pg-response-codes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgResponseCodeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PgResponseCode in the database
        List<PgResponseCode> pgResponseCodeList = pgResponseCodeRepository.findAll();
        assertThat(pgResponseCodeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePgResponseCode() throws Exception {
        // Initialize the database
        pgResponseCodeRepository.saveAndFlush(pgResponseCode);

        int databaseSizeBeforeDelete = pgResponseCodeRepository.findAll().size();

        // Delete the pgResponseCode
        restPgResponseCodeMockMvc.perform(delete("/api/pg-response-codes/{id}", pgResponseCode.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PgResponseCode> pgResponseCodeList = pgResponseCodeRepository.findAll();
        assertThat(pgResponseCodeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PgResponseCode.class);
        PgResponseCode pgResponseCode1 = new PgResponseCode();
        pgResponseCode1.setId(1L);
        PgResponseCode pgResponseCode2 = new PgResponseCode();
        pgResponseCode2.setId(pgResponseCode1.getId());
        assertThat(pgResponseCode1).isEqualTo(pgResponseCode2);
        pgResponseCode2.setId(2L);
        assertThat(pgResponseCode1).isNotEqualTo(pgResponseCode2);
        pgResponseCode1.setId(null);
        assertThat(pgResponseCode1).isNotEqualTo(pgResponseCode2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PgResponseCodeDTO.class);
        PgResponseCodeDTO pgResponseCodeDTO1 = new PgResponseCodeDTO();
        pgResponseCodeDTO1.setId(1L);
        PgResponseCodeDTO pgResponseCodeDTO2 = new PgResponseCodeDTO();
        assertThat(pgResponseCodeDTO1).isNotEqualTo(pgResponseCodeDTO2);
        pgResponseCodeDTO2.setId(pgResponseCodeDTO1.getId());
        assertThat(pgResponseCodeDTO1).isEqualTo(pgResponseCodeDTO2);
        pgResponseCodeDTO2.setId(2L);
        assertThat(pgResponseCodeDTO1).isNotEqualTo(pgResponseCodeDTO2);
        pgResponseCodeDTO1.setId(null);
        assertThat(pgResponseCodeDTO1).isNotEqualTo(pgResponseCodeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(pgResponseCodeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(pgResponseCodeMapper.fromId(null)).isNull();
    }
}
