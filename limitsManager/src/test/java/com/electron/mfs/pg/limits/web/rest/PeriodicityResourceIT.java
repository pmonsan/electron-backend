package com.electron.mfs.pg.limits.web.rest;

import com.electron.mfs.pg.limits.LimitsManagerApp;
import com.electron.mfs.pg.limits.config.SecurityBeanOverrideConfiguration;
import com.electron.mfs.pg.limits.domain.Periodicity;
import com.electron.mfs.pg.limits.repository.PeriodicityRepository;
import com.electron.mfs.pg.limits.service.PeriodicityService;
import com.electron.mfs.pg.limits.service.dto.PeriodicityDTO;
import com.electron.mfs.pg.limits.service.mapper.PeriodicityMapper;
import com.electron.mfs.pg.limits.web.rest.errors.ExceptionTranslator;

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

import static com.electron.mfs.pg.limits.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link PeriodicityResource} REST controller.
 */
@EmbeddedKafka
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, LimitsManagerApp.class})
public class PeriodicityResourceIT {

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private PeriodicityRepository periodicityRepository;

    @Autowired
    private PeriodicityMapper periodicityMapper;

    @Autowired
    private PeriodicityService periodicityService;

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

    private MockMvc restPeriodicityMockMvc;

    private Periodicity periodicity;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PeriodicityResource periodicityResource = new PeriodicityResource(periodicityService);
        this.restPeriodicityMockMvc = MockMvcBuilders.standaloneSetup(periodicityResource)
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
    public static Periodicity createEntity(EntityManager em) {
        Periodicity periodicity = new Periodicity()
            .label(DEFAULT_LABEL)
            .active(DEFAULT_ACTIVE);
        return periodicity;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Periodicity createUpdatedEntity(EntityManager em) {
        Periodicity periodicity = new Periodicity()
            .label(UPDATED_LABEL)
            .active(UPDATED_ACTIVE);
        return periodicity;
    }

    @BeforeEach
    public void initTest() {
        periodicity = createEntity(em);
    }

    @Test
    @Transactional
    public void createPeriodicity() throws Exception {
        int databaseSizeBeforeCreate = periodicityRepository.findAll().size();

        // Create the Periodicity
        PeriodicityDTO periodicityDTO = periodicityMapper.toDto(periodicity);
        restPeriodicityMockMvc.perform(post("/api/periodicities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(periodicityDTO)))
            .andExpect(status().isCreated());

        // Validate the Periodicity in the database
        List<Periodicity> periodicityList = periodicityRepository.findAll();
        assertThat(periodicityList).hasSize(databaseSizeBeforeCreate + 1);
        Periodicity testPeriodicity = periodicityList.get(periodicityList.size() - 1);
        assertThat(testPeriodicity.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testPeriodicity.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createPeriodicityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = periodicityRepository.findAll().size();

        // Create the Periodicity with an existing ID
        periodicity.setId(1L);
        PeriodicityDTO periodicityDTO = periodicityMapper.toDto(periodicity);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPeriodicityMockMvc.perform(post("/api/periodicities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(periodicityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Periodicity in the database
        List<Periodicity> periodicityList = periodicityRepository.findAll();
        assertThat(periodicityList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkLabelIsRequired() throws Exception {
        int databaseSizeBeforeTest = periodicityRepository.findAll().size();
        // set the field null
        periodicity.setLabel(null);

        // Create the Periodicity, which fails.
        PeriodicityDTO periodicityDTO = periodicityMapper.toDto(periodicity);

        restPeriodicityMockMvc.perform(post("/api/periodicities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(periodicityDTO)))
            .andExpect(status().isBadRequest());

        List<Periodicity> periodicityList = periodicityRepository.findAll();
        assertThat(periodicityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = periodicityRepository.findAll().size();
        // set the field null
        periodicity.setActive(null);

        // Create the Periodicity, which fails.
        PeriodicityDTO periodicityDTO = periodicityMapper.toDto(periodicity);

        restPeriodicityMockMvc.perform(post("/api/periodicities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(periodicityDTO)))
            .andExpect(status().isBadRequest());

        List<Periodicity> periodicityList = periodicityRepository.findAll();
        assertThat(periodicityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPeriodicities() throws Exception {
        // Initialize the database
        periodicityRepository.saveAndFlush(periodicity);

        // Get all the periodicityList
        restPeriodicityMockMvc.perform(get("/api/periodicities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(periodicity.getId().intValue())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getPeriodicity() throws Exception {
        // Initialize the database
        periodicityRepository.saveAndFlush(periodicity);

        // Get the periodicity
        restPeriodicityMockMvc.perform(get("/api/periodicities/{id}", periodicity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(periodicity.getId().intValue()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPeriodicity() throws Exception {
        // Get the periodicity
        restPeriodicityMockMvc.perform(get("/api/periodicities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePeriodicity() throws Exception {
        // Initialize the database
        periodicityRepository.saveAndFlush(periodicity);

        int databaseSizeBeforeUpdate = periodicityRepository.findAll().size();

        // Update the periodicity
        Periodicity updatedPeriodicity = periodicityRepository.findById(periodicity.getId()).get();
        // Disconnect from session so that the updates on updatedPeriodicity are not directly saved in db
        em.detach(updatedPeriodicity);
        updatedPeriodicity
            .label(UPDATED_LABEL)
            .active(UPDATED_ACTIVE);
        PeriodicityDTO periodicityDTO = periodicityMapper.toDto(updatedPeriodicity);

        restPeriodicityMockMvc.perform(put("/api/periodicities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(periodicityDTO)))
            .andExpect(status().isOk());

        // Validate the Periodicity in the database
        List<Periodicity> periodicityList = periodicityRepository.findAll();
        assertThat(periodicityList).hasSize(databaseSizeBeforeUpdate);
        Periodicity testPeriodicity = periodicityList.get(periodicityList.size() - 1);
        assertThat(testPeriodicity.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testPeriodicity.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingPeriodicity() throws Exception {
        int databaseSizeBeforeUpdate = periodicityRepository.findAll().size();

        // Create the Periodicity
        PeriodicityDTO periodicityDTO = periodicityMapper.toDto(periodicity);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPeriodicityMockMvc.perform(put("/api/periodicities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(periodicityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Periodicity in the database
        List<Periodicity> periodicityList = periodicityRepository.findAll();
        assertThat(periodicityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePeriodicity() throws Exception {
        // Initialize the database
        periodicityRepository.saveAndFlush(periodicity);

        int databaseSizeBeforeDelete = periodicityRepository.findAll().size();

        // Delete the periodicity
        restPeriodicityMockMvc.perform(delete("/api/periodicities/{id}", periodicity.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Periodicity> periodicityList = periodicityRepository.findAll();
        assertThat(periodicityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Periodicity.class);
        Periodicity periodicity1 = new Periodicity();
        periodicity1.setId(1L);
        Periodicity periodicity2 = new Periodicity();
        periodicity2.setId(periodicity1.getId());
        assertThat(periodicity1).isEqualTo(periodicity2);
        periodicity2.setId(2L);
        assertThat(periodicity1).isNotEqualTo(periodicity2);
        periodicity1.setId(null);
        assertThat(periodicity1).isNotEqualTo(periodicity2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PeriodicityDTO.class);
        PeriodicityDTO periodicityDTO1 = new PeriodicityDTO();
        periodicityDTO1.setId(1L);
        PeriodicityDTO periodicityDTO2 = new PeriodicityDTO();
        assertThat(periodicityDTO1).isNotEqualTo(periodicityDTO2);
        periodicityDTO2.setId(periodicityDTO1.getId());
        assertThat(periodicityDTO1).isEqualTo(periodicityDTO2);
        periodicityDTO2.setId(2L);
        assertThat(periodicityDTO1).isNotEqualTo(periodicityDTO2);
        periodicityDTO1.setId(null);
        assertThat(periodicityDTO1).isNotEqualTo(periodicityDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(periodicityMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(periodicityMapper.fromId(null)).isNull();
    }
}
