package com.electron.mfs.pg.pg8583.web.rest;

import com.electron.mfs.pg.pg8583.Pg8583ManagerApp;
import com.electron.mfs.pg.pg8583.config.SecurityBeanOverrideConfiguration;
import com.electron.mfs.pg.pg8583.domain.PgTransactionType2;
import com.electron.mfs.pg.pg8583.repository.PgTransactionType2Repository;
import com.electron.mfs.pg.pg8583.service.PgTransactionType2Service;
import com.electron.mfs.pg.pg8583.service.dto.PgTransactionType2DTO;
import com.electron.mfs.pg.pg8583.service.mapper.PgTransactionType2Mapper;
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
 * Integration tests for the {@Link PgTransactionType2Resource} REST controller.
 */
@EmbeddedKafka
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, Pg8583ManagerApp.class})
public class PgTransactionType2ResourceIT {

    private static final String DEFAULT_CODE = "AAAAA";
    private static final String UPDATED_CODE = "BBBBB";

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private PgTransactionType2Repository pgTransactionType2Repository;

    @Autowired
    private PgTransactionType2Mapper pgTransactionType2Mapper;

    @Autowired
    private PgTransactionType2Service pgTransactionType2Service;

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

    private MockMvc restPgTransactionType2MockMvc;

    private PgTransactionType2 pgTransactionType2;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PgTransactionType2Resource pgTransactionType2Resource = new PgTransactionType2Resource(pgTransactionType2Service);
        this.restPgTransactionType2MockMvc = MockMvcBuilders.standaloneSetup(pgTransactionType2Resource)
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
    public static PgTransactionType2 createEntity(EntityManager em) {
        PgTransactionType2 pgTransactionType2 = new PgTransactionType2()
            .code(DEFAULT_CODE)
            .label(DEFAULT_LABEL)
            .description(DEFAULT_DESCRIPTION)
            .active(DEFAULT_ACTIVE);
        return pgTransactionType2;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PgTransactionType2 createUpdatedEntity(EntityManager em) {
        PgTransactionType2 pgTransactionType2 = new PgTransactionType2()
            .code(UPDATED_CODE)
            .label(UPDATED_LABEL)
            .description(UPDATED_DESCRIPTION)
            .active(UPDATED_ACTIVE);
        return pgTransactionType2;
    }

    @BeforeEach
    public void initTest() {
        pgTransactionType2 = createEntity(em);
    }

    @Test
    @Transactional
    public void createPgTransactionType2() throws Exception {
        int databaseSizeBeforeCreate = pgTransactionType2Repository.findAll().size();

        // Create the PgTransactionType2
        PgTransactionType2DTO pgTransactionType2DTO = pgTransactionType2Mapper.toDto(pgTransactionType2);
        restPgTransactionType2MockMvc.perform(post("/api/pg-transaction-type-2-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgTransactionType2DTO)))
            .andExpect(status().isCreated());

        // Validate the PgTransactionType2 in the database
        List<PgTransactionType2> pgTransactionType2List = pgTransactionType2Repository.findAll();
        assertThat(pgTransactionType2List).hasSize(databaseSizeBeforeCreate + 1);
        PgTransactionType2 testPgTransactionType2 = pgTransactionType2List.get(pgTransactionType2List.size() - 1);
        assertThat(testPgTransactionType2.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testPgTransactionType2.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testPgTransactionType2.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPgTransactionType2.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createPgTransactionType2WithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pgTransactionType2Repository.findAll().size();

        // Create the PgTransactionType2 with an existing ID
        pgTransactionType2.setId(1L);
        PgTransactionType2DTO pgTransactionType2DTO = pgTransactionType2Mapper.toDto(pgTransactionType2);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPgTransactionType2MockMvc.perform(post("/api/pg-transaction-type-2-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgTransactionType2DTO)))
            .andExpect(status().isBadRequest());

        // Validate the PgTransactionType2 in the database
        List<PgTransactionType2> pgTransactionType2List = pgTransactionType2Repository.findAll();
        assertThat(pgTransactionType2List).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgTransactionType2Repository.findAll().size();
        // set the field null
        pgTransactionType2.setCode(null);

        // Create the PgTransactionType2, which fails.
        PgTransactionType2DTO pgTransactionType2DTO = pgTransactionType2Mapper.toDto(pgTransactionType2);

        restPgTransactionType2MockMvc.perform(post("/api/pg-transaction-type-2-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgTransactionType2DTO)))
            .andExpect(status().isBadRequest());

        List<PgTransactionType2> pgTransactionType2List = pgTransactionType2Repository.findAll();
        assertThat(pgTransactionType2List).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLabelIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgTransactionType2Repository.findAll().size();
        // set the field null
        pgTransactionType2.setLabel(null);

        // Create the PgTransactionType2, which fails.
        PgTransactionType2DTO pgTransactionType2DTO = pgTransactionType2Mapper.toDto(pgTransactionType2);

        restPgTransactionType2MockMvc.perform(post("/api/pg-transaction-type-2-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgTransactionType2DTO)))
            .andExpect(status().isBadRequest());

        List<PgTransactionType2> pgTransactionType2List = pgTransactionType2Repository.findAll();
        assertThat(pgTransactionType2List).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgTransactionType2Repository.findAll().size();
        // set the field null
        pgTransactionType2.setDescription(null);

        // Create the PgTransactionType2, which fails.
        PgTransactionType2DTO pgTransactionType2DTO = pgTransactionType2Mapper.toDto(pgTransactionType2);

        restPgTransactionType2MockMvc.perform(post("/api/pg-transaction-type-2-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgTransactionType2DTO)))
            .andExpect(status().isBadRequest());

        List<PgTransactionType2> pgTransactionType2List = pgTransactionType2Repository.findAll();
        assertThat(pgTransactionType2List).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgTransactionType2Repository.findAll().size();
        // set the field null
        pgTransactionType2.setActive(null);

        // Create the PgTransactionType2, which fails.
        PgTransactionType2DTO pgTransactionType2DTO = pgTransactionType2Mapper.toDto(pgTransactionType2);

        restPgTransactionType2MockMvc.perform(post("/api/pg-transaction-type-2-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgTransactionType2DTO)))
            .andExpect(status().isBadRequest());

        List<PgTransactionType2> pgTransactionType2List = pgTransactionType2Repository.findAll();
        assertThat(pgTransactionType2List).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPgTransactionType2S() throws Exception {
        // Initialize the database
        pgTransactionType2Repository.saveAndFlush(pgTransactionType2);

        // Get all the pgTransactionType2List
        restPgTransactionType2MockMvc.perform(get("/api/pg-transaction-type-2-s?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pgTransactionType2.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getPgTransactionType2() throws Exception {
        // Initialize the database
        pgTransactionType2Repository.saveAndFlush(pgTransactionType2);

        // Get the pgTransactionType2
        restPgTransactionType2MockMvc.perform(get("/api/pg-transaction-type-2-s/{id}", pgTransactionType2.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pgTransactionType2.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPgTransactionType2() throws Exception {
        // Get the pgTransactionType2
        restPgTransactionType2MockMvc.perform(get("/api/pg-transaction-type-2-s/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePgTransactionType2() throws Exception {
        // Initialize the database
        pgTransactionType2Repository.saveAndFlush(pgTransactionType2);

        int databaseSizeBeforeUpdate = pgTransactionType2Repository.findAll().size();

        // Update the pgTransactionType2
        PgTransactionType2 updatedPgTransactionType2 = pgTransactionType2Repository.findById(pgTransactionType2.getId()).get();
        // Disconnect from session so that the updates on updatedPgTransactionType2 are not directly saved in db
        em.detach(updatedPgTransactionType2);
        updatedPgTransactionType2
            .code(UPDATED_CODE)
            .label(UPDATED_LABEL)
            .description(UPDATED_DESCRIPTION)
            .active(UPDATED_ACTIVE);
        PgTransactionType2DTO pgTransactionType2DTO = pgTransactionType2Mapper.toDto(updatedPgTransactionType2);

        restPgTransactionType2MockMvc.perform(put("/api/pg-transaction-type-2-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgTransactionType2DTO)))
            .andExpect(status().isOk());

        // Validate the PgTransactionType2 in the database
        List<PgTransactionType2> pgTransactionType2List = pgTransactionType2Repository.findAll();
        assertThat(pgTransactionType2List).hasSize(databaseSizeBeforeUpdate);
        PgTransactionType2 testPgTransactionType2 = pgTransactionType2List.get(pgTransactionType2List.size() - 1);
        assertThat(testPgTransactionType2.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testPgTransactionType2.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testPgTransactionType2.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPgTransactionType2.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingPgTransactionType2() throws Exception {
        int databaseSizeBeforeUpdate = pgTransactionType2Repository.findAll().size();

        // Create the PgTransactionType2
        PgTransactionType2DTO pgTransactionType2DTO = pgTransactionType2Mapper.toDto(pgTransactionType2);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPgTransactionType2MockMvc.perform(put("/api/pg-transaction-type-2-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgTransactionType2DTO)))
            .andExpect(status().isBadRequest());

        // Validate the PgTransactionType2 in the database
        List<PgTransactionType2> pgTransactionType2List = pgTransactionType2Repository.findAll();
        assertThat(pgTransactionType2List).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePgTransactionType2() throws Exception {
        // Initialize the database
        pgTransactionType2Repository.saveAndFlush(pgTransactionType2);

        int databaseSizeBeforeDelete = pgTransactionType2Repository.findAll().size();

        // Delete the pgTransactionType2
        restPgTransactionType2MockMvc.perform(delete("/api/pg-transaction-type-2-s/{id}", pgTransactionType2.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PgTransactionType2> pgTransactionType2List = pgTransactionType2Repository.findAll();
        assertThat(pgTransactionType2List).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PgTransactionType2.class);
        PgTransactionType2 pgTransactionType21 = new PgTransactionType2();
        pgTransactionType21.setId(1L);
        PgTransactionType2 pgTransactionType22 = new PgTransactionType2();
        pgTransactionType22.setId(pgTransactionType21.getId());
        assertThat(pgTransactionType21).isEqualTo(pgTransactionType22);
        pgTransactionType22.setId(2L);
        assertThat(pgTransactionType21).isNotEqualTo(pgTransactionType22);
        pgTransactionType21.setId(null);
        assertThat(pgTransactionType21).isNotEqualTo(pgTransactionType22);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PgTransactionType2DTO.class);
        PgTransactionType2DTO pgTransactionType2DTO1 = new PgTransactionType2DTO();
        pgTransactionType2DTO1.setId(1L);
        PgTransactionType2DTO pgTransactionType2DTO2 = new PgTransactionType2DTO();
        assertThat(pgTransactionType2DTO1).isNotEqualTo(pgTransactionType2DTO2);
        pgTransactionType2DTO2.setId(pgTransactionType2DTO1.getId());
        assertThat(pgTransactionType2DTO1).isEqualTo(pgTransactionType2DTO2);
        pgTransactionType2DTO2.setId(2L);
        assertThat(pgTransactionType2DTO1).isNotEqualTo(pgTransactionType2DTO2);
        pgTransactionType2DTO1.setId(null);
        assertThat(pgTransactionType2DTO1).isNotEqualTo(pgTransactionType2DTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(pgTransactionType2Mapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(pgTransactionType2Mapper.fromId(null)).isNull();
    }
}
