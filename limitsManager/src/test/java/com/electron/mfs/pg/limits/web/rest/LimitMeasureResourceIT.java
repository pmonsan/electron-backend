package com.electron.mfs.pg.limits.web.rest;

import com.electron.mfs.pg.limits.LimitsManagerApp;
import com.electron.mfs.pg.limits.config.SecurityBeanOverrideConfiguration;
import com.electron.mfs.pg.limits.domain.LimitMeasure;
import com.electron.mfs.pg.limits.repository.LimitMeasureRepository;
import com.electron.mfs.pg.limits.service.LimitMeasureService;
import com.electron.mfs.pg.limits.service.dto.LimitMeasureDTO;
import com.electron.mfs.pg.limits.service.mapper.LimitMeasureMapper;
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
 * Integration tests for the {@Link LimitMeasureResource} REST controller.
 */
@EmbeddedKafka
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, LimitsManagerApp.class})
public class LimitMeasureResourceIT {

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private LimitMeasureRepository limitMeasureRepository;

    @Autowired
    private LimitMeasureMapper limitMeasureMapper;

    @Autowired
    private LimitMeasureService limitMeasureService;

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

    private MockMvc restLimitMeasureMockMvc;

    private LimitMeasure limitMeasure;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LimitMeasureResource limitMeasureResource = new LimitMeasureResource(limitMeasureService);
        this.restLimitMeasureMockMvc = MockMvcBuilders.standaloneSetup(limitMeasureResource)
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
    public static LimitMeasure createEntity(EntityManager em) {
        LimitMeasure limitMeasure = new LimitMeasure()
            .label(DEFAULT_LABEL)
            .active(DEFAULT_ACTIVE);
        return limitMeasure;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LimitMeasure createUpdatedEntity(EntityManager em) {
        LimitMeasure limitMeasure = new LimitMeasure()
            .label(UPDATED_LABEL)
            .active(UPDATED_ACTIVE);
        return limitMeasure;
    }

    @BeforeEach
    public void initTest() {
        limitMeasure = createEntity(em);
    }

    @Test
    @Transactional
    public void createLimitMeasure() throws Exception {
        int databaseSizeBeforeCreate = limitMeasureRepository.findAll().size();

        // Create the LimitMeasure
        LimitMeasureDTO limitMeasureDTO = limitMeasureMapper.toDto(limitMeasure);
        restLimitMeasureMockMvc.perform(post("/api/limit-measures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(limitMeasureDTO)))
            .andExpect(status().isCreated());

        // Validate the LimitMeasure in the database
        List<LimitMeasure> limitMeasureList = limitMeasureRepository.findAll();
        assertThat(limitMeasureList).hasSize(databaseSizeBeforeCreate + 1);
        LimitMeasure testLimitMeasure = limitMeasureList.get(limitMeasureList.size() - 1);
        assertThat(testLimitMeasure.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testLimitMeasure.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createLimitMeasureWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = limitMeasureRepository.findAll().size();

        // Create the LimitMeasure with an existing ID
        limitMeasure.setId(1L);
        LimitMeasureDTO limitMeasureDTO = limitMeasureMapper.toDto(limitMeasure);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLimitMeasureMockMvc.perform(post("/api/limit-measures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(limitMeasureDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LimitMeasure in the database
        List<LimitMeasure> limitMeasureList = limitMeasureRepository.findAll();
        assertThat(limitMeasureList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkLabelIsRequired() throws Exception {
        int databaseSizeBeforeTest = limitMeasureRepository.findAll().size();
        // set the field null
        limitMeasure.setLabel(null);

        // Create the LimitMeasure, which fails.
        LimitMeasureDTO limitMeasureDTO = limitMeasureMapper.toDto(limitMeasure);

        restLimitMeasureMockMvc.perform(post("/api/limit-measures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(limitMeasureDTO)))
            .andExpect(status().isBadRequest());

        List<LimitMeasure> limitMeasureList = limitMeasureRepository.findAll();
        assertThat(limitMeasureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = limitMeasureRepository.findAll().size();
        // set the field null
        limitMeasure.setActive(null);

        // Create the LimitMeasure, which fails.
        LimitMeasureDTO limitMeasureDTO = limitMeasureMapper.toDto(limitMeasure);

        restLimitMeasureMockMvc.perform(post("/api/limit-measures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(limitMeasureDTO)))
            .andExpect(status().isBadRequest());

        List<LimitMeasure> limitMeasureList = limitMeasureRepository.findAll();
        assertThat(limitMeasureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLimitMeasures() throws Exception {
        // Initialize the database
        limitMeasureRepository.saveAndFlush(limitMeasure);

        // Get all the limitMeasureList
        restLimitMeasureMockMvc.perform(get("/api/limit-measures?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(limitMeasure.getId().intValue())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getLimitMeasure() throws Exception {
        // Initialize the database
        limitMeasureRepository.saveAndFlush(limitMeasure);

        // Get the limitMeasure
        restLimitMeasureMockMvc.perform(get("/api/limit-measures/{id}", limitMeasure.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(limitMeasure.getId().intValue()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingLimitMeasure() throws Exception {
        // Get the limitMeasure
        restLimitMeasureMockMvc.perform(get("/api/limit-measures/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLimitMeasure() throws Exception {
        // Initialize the database
        limitMeasureRepository.saveAndFlush(limitMeasure);

        int databaseSizeBeforeUpdate = limitMeasureRepository.findAll().size();

        // Update the limitMeasure
        LimitMeasure updatedLimitMeasure = limitMeasureRepository.findById(limitMeasure.getId()).get();
        // Disconnect from session so that the updates on updatedLimitMeasure are not directly saved in db
        em.detach(updatedLimitMeasure);
        updatedLimitMeasure
            .label(UPDATED_LABEL)
            .active(UPDATED_ACTIVE);
        LimitMeasureDTO limitMeasureDTO = limitMeasureMapper.toDto(updatedLimitMeasure);

        restLimitMeasureMockMvc.perform(put("/api/limit-measures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(limitMeasureDTO)))
            .andExpect(status().isOk());

        // Validate the LimitMeasure in the database
        List<LimitMeasure> limitMeasureList = limitMeasureRepository.findAll();
        assertThat(limitMeasureList).hasSize(databaseSizeBeforeUpdate);
        LimitMeasure testLimitMeasure = limitMeasureList.get(limitMeasureList.size() - 1);
        assertThat(testLimitMeasure.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testLimitMeasure.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingLimitMeasure() throws Exception {
        int databaseSizeBeforeUpdate = limitMeasureRepository.findAll().size();

        // Create the LimitMeasure
        LimitMeasureDTO limitMeasureDTO = limitMeasureMapper.toDto(limitMeasure);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLimitMeasureMockMvc.perform(put("/api/limit-measures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(limitMeasureDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LimitMeasure in the database
        List<LimitMeasure> limitMeasureList = limitMeasureRepository.findAll();
        assertThat(limitMeasureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLimitMeasure() throws Exception {
        // Initialize the database
        limitMeasureRepository.saveAndFlush(limitMeasure);

        int databaseSizeBeforeDelete = limitMeasureRepository.findAll().size();

        // Delete the limitMeasure
        restLimitMeasureMockMvc.perform(delete("/api/limit-measures/{id}", limitMeasure.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LimitMeasure> limitMeasureList = limitMeasureRepository.findAll();
        assertThat(limitMeasureList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LimitMeasure.class);
        LimitMeasure limitMeasure1 = new LimitMeasure();
        limitMeasure1.setId(1L);
        LimitMeasure limitMeasure2 = new LimitMeasure();
        limitMeasure2.setId(limitMeasure1.getId());
        assertThat(limitMeasure1).isEqualTo(limitMeasure2);
        limitMeasure2.setId(2L);
        assertThat(limitMeasure1).isNotEqualTo(limitMeasure2);
        limitMeasure1.setId(null);
        assertThat(limitMeasure1).isNotEqualTo(limitMeasure2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LimitMeasureDTO.class);
        LimitMeasureDTO limitMeasureDTO1 = new LimitMeasureDTO();
        limitMeasureDTO1.setId(1L);
        LimitMeasureDTO limitMeasureDTO2 = new LimitMeasureDTO();
        assertThat(limitMeasureDTO1).isNotEqualTo(limitMeasureDTO2);
        limitMeasureDTO2.setId(limitMeasureDTO1.getId());
        assertThat(limitMeasureDTO1).isEqualTo(limitMeasureDTO2);
        limitMeasureDTO2.setId(2L);
        assertThat(limitMeasureDTO1).isNotEqualTo(limitMeasureDTO2);
        limitMeasureDTO1.setId(null);
        assertThat(limitMeasureDTO1).isNotEqualTo(limitMeasureDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(limitMeasureMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(limitMeasureMapper.fromId(null)).isNull();
    }
}
