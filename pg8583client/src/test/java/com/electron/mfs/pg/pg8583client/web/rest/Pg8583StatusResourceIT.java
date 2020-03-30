package com.electron.mfs.pg.pg8583client.web.rest;

import com.electron.mfs.pg.pg8583client.Pg8583ClientApp;
import com.electron.mfs.pg.pg8583client.config.SecurityBeanOverrideConfiguration;
import com.electron.mfs.pg.pg8583client.domain.Pg8583Status;
import com.electron.mfs.pg.pg8583client.repository.Pg8583StatusRepository;
import com.electron.mfs.pg.pg8583client.service.Pg8583StatusService;
import com.electron.mfs.pg.pg8583client.service.dto.Pg8583StatusDTO;
import com.electron.mfs.pg.pg8583client.service.mapper.Pg8583StatusMapper;
import com.electron.mfs.pg.pg8583client.web.rest.errors.ExceptionTranslator;

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

import static com.electron.mfs.pg.pg8583client.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link Pg8583StatusResource} REST controller.
 */
@EmbeddedKafka
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, Pg8583ClientApp.class})
public class Pg8583StatusResourceIT {

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_DEFAULT_REASON = "AAAAAAAAAA";
    private static final String UPDATED_DEFAULT_REASON = "BBBBBBBBBB";

    @Autowired
    private Pg8583StatusRepository pg8583StatusRepository;

    @Autowired
    private Pg8583StatusMapper pg8583StatusMapper;

    @Autowired
    private Pg8583StatusService pg8583StatusService;

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

    private MockMvc restPg8583StatusMockMvc;

    private Pg8583Status pg8583Status;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final Pg8583StatusResource pg8583StatusResource = new Pg8583StatusResource(pg8583StatusService);
        this.restPg8583StatusMockMvc = MockMvcBuilders.standaloneSetup(pg8583StatusResource)
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
    public static Pg8583Status createEntity(EntityManager em) {
        Pg8583Status pg8583Status = new Pg8583Status()
            .status(DEFAULT_STATUS)
            .defaultReason(DEFAULT_DEFAULT_REASON);
        return pg8583Status;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pg8583Status createUpdatedEntity(EntityManager em) {
        Pg8583Status pg8583Status = new Pg8583Status()
            .status(UPDATED_STATUS)
            .defaultReason(UPDATED_DEFAULT_REASON);
        return pg8583Status;
    }

    @BeforeEach
    public void initTest() {
        pg8583Status = createEntity(em);
    }

    @Test
    @Transactional
    public void createPg8583Status() throws Exception {
        int databaseSizeBeforeCreate = pg8583StatusRepository.findAll().size();

        // Create the Pg8583Status
        Pg8583StatusDTO pg8583StatusDTO = pg8583StatusMapper.toDto(pg8583Status);
        restPg8583StatusMockMvc.perform(post("/api/pg-8583-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pg8583StatusDTO)))
            .andExpect(status().isCreated());

        // Validate the Pg8583Status in the database
        List<Pg8583Status> pg8583StatusList = pg8583StatusRepository.findAll();
        assertThat(pg8583StatusList).hasSize(databaseSizeBeforeCreate + 1);
        Pg8583Status testPg8583Status = pg8583StatusList.get(pg8583StatusList.size() - 1);
        assertThat(testPg8583Status.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testPg8583Status.getDefaultReason()).isEqualTo(DEFAULT_DEFAULT_REASON);
    }

    @Test
    @Transactional
    public void createPg8583StatusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pg8583StatusRepository.findAll().size();

        // Create the Pg8583Status with an existing ID
        pg8583Status.setId(1L);
        Pg8583StatusDTO pg8583StatusDTO = pg8583StatusMapper.toDto(pg8583Status);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPg8583StatusMockMvc.perform(post("/api/pg-8583-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pg8583StatusDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Pg8583Status in the database
        List<Pg8583Status> pg8583StatusList = pg8583StatusRepository.findAll();
        assertThat(pg8583StatusList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = pg8583StatusRepository.findAll().size();
        // set the field null
        pg8583Status.setStatus(null);

        // Create the Pg8583Status, which fails.
        Pg8583StatusDTO pg8583StatusDTO = pg8583StatusMapper.toDto(pg8583Status);

        restPg8583StatusMockMvc.perform(post("/api/pg-8583-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pg8583StatusDTO)))
            .andExpect(status().isBadRequest());

        List<Pg8583Status> pg8583StatusList = pg8583StatusRepository.findAll();
        assertThat(pg8583StatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPg8583Statuses() throws Exception {
        // Initialize the database
        pg8583StatusRepository.saveAndFlush(pg8583Status);

        // Get all the pg8583StatusList
        restPg8583StatusMockMvc.perform(get("/api/pg-8583-statuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pg8583Status.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].defaultReason").value(hasItem(DEFAULT_DEFAULT_REASON.toString())));
    }
    
    @Test
    @Transactional
    public void getPg8583Status() throws Exception {
        // Initialize the database
        pg8583StatusRepository.saveAndFlush(pg8583Status);

        // Get the pg8583Status
        restPg8583StatusMockMvc.perform(get("/api/pg-8583-statuses/{id}", pg8583Status.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pg8583Status.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.defaultReason").value(DEFAULT_DEFAULT_REASON.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPg8583Status() throws Exception {
        // Get the pg8583Status
        restPg8583StatusMockMvc.perform(get("/api/pg-8583-statuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePg8583Status() throws Exception {
        // Initialize the database
        pg8583StatusRepository.saveAndFlush(pg8583Status);

        int databaseSizeBeforeUpdate = pg8583StatusRepository.findAll().size();

        // Update the pg8583Status
        Pg8583Status updatedPg8583Status = pg8583StatusRepository.findById(pg8583Status.getId()).get();
        // Disconnect from session so that the updates on updatedPg8583Status are not directly saved in db
        em.detach(updatedPg8583Status);
        updatedPg8583Status
            .status(UPDATED_STATUS)
            .defaultReason(UPDATED_DEFAULT_REASON);
        Pg8583StatusDTO pg8583StatusDTO = pg8583StatusMapper.toDto(updatedPg8583Status);

        restPg8583StatusMockMvc.perform(put("/api/pg-8583-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pg8583StatusDTO)))
            .andExpect(status().isOk());

        // Validate the Pg8583Status in the database
        List<Pg8583Status> pg8583StatusList = pg8583StatusRepository.findAll();
        assertThat(pg8583StatusList).hasSize(databaseSizeBeforeUpdate);
        Pg8583Status testPg8583Status = pg8583StatusList.get(pg8583StatusList.size() - 1);
        assertThat(testPg8583Status.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testPg8583Status.getDefaultReason()).isEqualTo(UPDATED_DEFAULT_REASON);
    }

    @Test
    @Transactional
    public void updateNonExistingPg8583Status() throws Exception {
        int databaseSizeBeforeUpdate = pg8583StatusRepository.findAll().size();

        // Create the Pg8583Status
        Pg8583StatusDTO pg8583StatusDTO = pg8583StatusMapper.toDto(pg8583Status);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPg8583StatusMockMvc.perform(put("/api/pg-8583-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pg8583StatusDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Pg8583Status in the database
        List<Pg8583Status> pg8583StatusList = pg8583StatusRepository.findAll();
        assertThat(pg8583StatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePg8583Status() throws Exception {
        // Initialize the database
        pg8583StatusRepository.saveAndFlush(pg8583Status);

        int databaseSizeBeforeDelete = pg8583StatusRepository.findAll().size();

        // Delete the pg8583Status
        restPg8583StatusMockMvc.perform(delete("/api/pg-8583-statuses/{id}", pg8583Status.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Pg8583Status> pg8583StatusList = pg8583StatusRepository.findAll();
        assertThat(pg8583StatusList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pg8583Status.class);
        Pg8583Status pg8583Status1 = new Pg8583Status();
        pg8583Status1.setId(1L);
        Pg8583Status pg8583Status2 = new Pg8583Status();
        pg8583Status2.setId(pg8583Status1.getId());
        assertThat(pg8583Status1).isEqualTo(pg8583Status2);
        pg8583Status2.setId(2L);
        assertThat(pg8583Status1).isNotEqualTo(pg8583Status2);
        pg8583Status1.setId(null);
        assertThat(pg8583Status1).isNotEqualTo(pg8583Status2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pg8583StatusDTO.class);
        Pg8583StatusDTO pg8583StatusDTO1 = new Pg8583StatusDTO();
        pg8583StatusDTO1.setId(1L);
        Pg8583StatusDTO pg8583StatusDTO2 = new Pg8583StatusDTO();
        assertThat(pg8583StatusDTO1).isNotEqualTo(pg8583StatusDTO2);
        pg8583StatusDTO2.setId(pg8583StatusDTO1.getId());
        assertThat(pg8583StatusDTO1).isEqualTo(pg8583StatusDTO2);
        pg8583StatusDTO2.setId(2L);
        assertThat(pg8583StatusDTO1).isNotEqualTo(pg8583StatusDTO2);
        pg8583StatusDTO1.setId(null);
        assertThat(pg8583StatusDTO1).isNotEqualTo(pg8583StatusDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(pg8583StatusMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(pg8583StatusMapper.fromId(null)).isNull();
    }
}
