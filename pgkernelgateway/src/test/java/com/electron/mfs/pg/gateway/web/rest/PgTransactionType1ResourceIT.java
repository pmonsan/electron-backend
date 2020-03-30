package com.electron.mfs.pg.gateway.web.rest;

import com.electron.mfs.pg.gateway.PgkernelgatewayApp;
import com.electron.mfs.pg.gateway.config.SecurityBeanOverrideConfiguration;
import com.electron.mfs.pg.gateway.domain.PgTransactionType1;
import com.electron.mfs.pg.gateway.repository.PgTransactionType1Repository;
import com.electron.mfs.pg.gateway.service.PgTransactionType1Service;
import com.electron.mfs.pg.gateway.service.dto.PgTransactionType1DTO;
import com.electron.mfs.pg.gateway.service.mapper.PgTransactionType1Mapper;
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
 * Integration tests for the {@Link PgTransactionType1Resource} REST controller.
 */
@EmbeddedKafka
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, PgkernelgatewayApp.class})
public class PgTransactionType1ResourceIT {

    private static final String DEFAULT_CODE = "AAAAA";
    private static final String UPDATED_CODE = "BBBBB";

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private PgTransactionType1Repository pgTransactionType1Repository;

    @Autowired
    private PgTransactionType1Mapper pgTransactionType1Mapper;

    @Autowired
    private PgTransactionType1Service pgTransactionType1Service;

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

    private MockMvc restPgTransactionType1MockMvc;

    private PgTransactionType1 pgTransactionType1;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PgTransactionType1Resource pgTransactionType1Resource = new PgTransactionType1Resource(pgTransactionType1Service);
        this.restPgTransactionType1MockMvc = MockMvcBuilders.standaloneSetup(pgTransactionType1Resource)
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
    public static PgTransactionType1 createEntity(EntityManager em) {
        PgTransactionType1 pgTransactionType1 = new PgTransactionType1()
            .code(DEFAULT_CODE)
            .label(DEFAULT_LABEL)
            .description(DEFAULT_DESCRIPTION)
            .active(DEFAULT_ACTIVE);
        return pgTransactionType1;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PgTransactionType1 createUpdatedEntity(EntityManager em) {
        PgTransactionType1 pgTransactionType1 = new PgTransactionType1()
            .code(UPDATED_CODE)
            .label(UPDATED_LABEL)
            .description(UPDATED_DESCRIPTION)
            .active(UPDATED_ACTIVE);
        return pgTransactionType1;
    }

    @BeforeEach
    public void initTest() {
        pgTransactionType1 = createEntity(em);
    }

    @Test
    @Transactional
    public void createPgTransactionType1() throws Exception {
        int databaseSizeBeforeCreate = pgTransactionType1Repository.findAll().size();

        // Create the PgTransactionType1
        PgTransactionType1DTO pgTransactionType1DTO = pgTransactionType1Mapper.toDto(pgTransactionType1);
        restPgTransactionType1MockMvc.perform(post("/api/pg-transaction-type-1-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgTransactionType1DTO)))
            .andExpect(status().isCreated());

        // Validate the PgTransactionType1 in the database
        List<PgTransactionType1> pgTransactionType1List = pgTransactionType1Repository.findAll();
        assertThat(pgTransactionType1List).hasSize(databaseSizeBeforeCreate + 1);
        PgTransactionType1 testPgTransactionType1 = pgTransactionType1List.get(pgTransactionType1List.size() - 1);
        assertThat(testPgTransactionType1.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testPgTransactionType1.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testPgTransactionType1.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPgTransactionType1.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createPgTransactionType1WithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pgTransactionType1Repository.findAll().size();

        // Create the PgTransactionType1 with an existing ID
        pgTransactionType1.setId(1L);
        PgTransactionType1DTO pgTransactionType1DTO = pgTransactionType1Mapper.toDto(pgTransactionType1);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPgTransactionType1MockMvc.perform(post("/api/pg-transaction-type-1-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgTransactionType1DTO)))
            .andExpect(status().isBadRequest());

        // Validate the PgTransactionType1 in the database
        List<PgTransactionType1> pgTransactionType1List = pgTransactionType1Repository.findAll();
        assertThat(pgTransactionType1List).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgTransactionType1Repository.findAll().size();
        // set the field null
        pgTransactionType1.setCode(null);

        // Create the PgTransactionType1, which fails.
        PgTransactionType1DTO pgTransactionType1DTO = pgTransactionType1Mapper.toDto(pgTransactionType1);

        restPgTransactionType1MockMvc.perform(post("/api/pg-transaction-type-1-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgTransactionType1DTO)))
            .andExpect(status().isBadRequest());

        List<PgTransactionType1> pgTransactionType1List = pgTransactionType1Repository.findAll();
        assertThat(pgTransactionType1List).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgTransactionType1Repository.findAll().size();
        // set the field null
        pgTransactionType1.setActive(null);

        // Create the PgTransactionType1, which fails.
        PgTransactionType1DTO pgTransactionType1DTO = pgTransactionType1Mapper.toDto(pgTransactionType1);

        restPgTransactionType1MockMvc.perform(post("/api/pg-transaction-type-1-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgTransactionType1DTO)))
            .andExpect(status().isBadRequest());

        List<PgTransactionType1> pgTransactionType1List = pgTransactionType1Repository.findAll();
        assertThat(pgTransactionType1List).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPgTransactionType1S() throws Exception {
        // Initialize the database
        pgTransactionType1Repository.saveAndFlush(pgTransactionType1);

        // Get all the pgTransactionType1List
        restPgTransactionType1MockMvc.perform(get("/api/pg-transaction-type-1-s?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pgTransactionType1.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getPgTransactionType1() throws Exception {
        // Initialize the database
        pgTransactionType1Repository.saveAndFlush(pgTransactionType1);

        // Get the pgTransactionType1
        restPgTransactionType1MockMvc.perform(get("/api/pg-transaction-type-1-s/{id}", pgTransactionType1.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pgTransactionType1.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPgTransactionType1() throws Exception {
        // Get the pgTransactionType1
        restPgTransactionType1MockMvc.perform(get("/api/pg-transaction-type-1-s/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePgTransactionType1() throws Exception {
        // Initialize the database
        pgTransactionType1Repository.saveAndFlush(pgTransactionType1);

        int databaseSizeBeforeUpdate = pgTransactionType1Repository.findAll().size();

        // Update the pgTransactionType1
        PgTransactionType1 updatedPgTransactionType1 = pgTransactionType1Repository.findById(pgTransactionType1.getId()).get();
        // Disconnect from session so that the updates on updatedPgTransactionType1 are not directly saved in db
        em.detach(updatedPgTransactionType1);
        updatedPgTransactionType1
            .code(UPDATED_CODE)
            .label(UPDATED_LABEL)
            .description(UPDATED_DESCRIPTION)
            .active(UPDATED_ACTIVE);
        PgTransactionType1DTO pgTransactionType1DTO = pgTransactionType1Mapper.toDto(updatedPgTransactionType1);

        restPgTransactionType1MockMvc.perform(put("/api/pg-transaction-type-1-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgTransactionType1DTO)))
            .andExpect(status().isOk());

        // Validate the PgTransactionType1 in the database
        List<PgTransactionType1> pgTransactionType1List = pgTransactionType1Repository.findAll();
        assertThat(pgTransactionType1List).hasSize(databaseSizeBeforeUpdate);
        PgTransactionType1 testPgTransactionType1 = pgTransactionType1List.get(pgTransactionType1List.size() - 1);
        assertThat(testPgTransactionType1.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testPgTransactionType1.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testPgTransactionType1.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPgTransactionType1.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingPgTransactionType1() throws Exception {
        int databaseSizeBeforeUpdate = pgTransactionType1Repository.findAll().size();

        // Create the PgTransactionType1
        PgTransactionType1DTO pgTransactionType1DTO = pgTransactionType1Mapper.toDto(pgTransactionType1);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPgTransactionType1MockMvc.perform(put("/api/pg-transaction-type-1-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgTransactionType1DTO)))
            .andExpect(status().isBadRequest());

        // Validate the PgTransactionType1 in the database
        List<PgTransactionType1> pgTransactionType1List = pgTransactionType1Repository.findAll();
        assertThat(pgTransactionType1List).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePgTransactionType1() throws Exception {
        // Initialize the database
        pgTransactionType1Repository.saveAndFlush(pgTransactionType1);

        int databaseSizeBeforeDelete = pgTransactionType1Repository.findAll().size();

        // Delete the pgTransactionType1
        restPgTransactionType1MockMvc.perform(delete("/api/pg-transaction-type-1-s/{id}", pgTransactionType1.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PgTransactionType1> pgTransactionType1List = pgTransactionType1Repository.findAll();
        assertThat(pgTransactionType1List).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PgTransactionType1.class);
        PgTransactionType1 pgTransactionType11 = new PgTransactionType1();
        pgTransactionType11.setId(1L);
        PgTransactionType1 pgTransactionType12 = new PgTransactionType1();
        pgTransactionType12.setId(pgTransactionType11.getId());
        assertThat(pgTransactionType11).isEqualTo(pgTransactionType12);
        pgTransactionType12.setId(2L);
        assertThat(pgTransactionType11).isNotEqualTo(pgTransactionType12);
        pgTransactionType11.setId(null);
        assertThat(pgTransactionType11).isNotEqualTo(pgTransactionType12);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PgTransactionType1DTO.class);
        PgTransactionType1DTO pgTransactionType1DTO1 = new PgTransactionType1DTO();
        pgTransactionType1DTO1.setId(1L);
        PgTransactionType1DTO pgTransactionType1DTO2 = new PgTransactionType1DTO();
        assertThat(pgTransactionType1DTO1).isNotEqualTo(pgTransactionType1DTO2);
        pgTransactionType1DTO2.setId(pgTransactionType1DTO1.getId());
        assertThat(pgTransactionType1DTO1).isEqualTo(pgTransactionType1DTO2);
        pgTransactionType1DTO2.setId(2L);
        assertThat(pgTransactionType1DTO1).isNotEqualTo(pgTransactionType1DTO2);
        pgTransactionType1DTO1.setId(null);
        assertThat(pgTransactionType1DTO1).isNotEqualTo(pgTransactionType1DTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(pgTransactionType1Mapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(pgTransactionType1Mapper.fromId(null)).isNull();
    }
}
