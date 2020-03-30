package com.electron.mfs.pg.mdm.web.rest;

import com.electron.mfs.pg.mdm.MdmManagerApp;
import com.electron.mfs.pg.mdm.config.SecurityBeanOverrideConfiguration;
import com.electron.mfs.pg.mdm.domain.ActivityArea;
import com.electron.mfs.pg.mdm.repository.ActivityAreaRepository;
import com.electron.mfs.pg.mdm.service.ActivityAreaService;
import com.electron.mfs.pg.mdm.service.dto.ActivityAreaDTO;
import com.electron.mfs.pg.mdm.service.mapper.ActivityAreaMapper;
import com.electron.mfs.pg.mdm.web.rest.errors.ExceptionTranslator;

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

import static com.electron.mfs.pg.mdm.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link ActivityAreaResource} REST controller.
 */
@EmbeddedKafka
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, MdmManagerApp.class})
public class ActivityAreaResourceIT {

    private static final String DEFAULT_CODE = "AAAAA";
    private static final String UPDATED_CODE = "BBBBB";

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private ActivityAreaRepository activityAreaRepository;

    @Autowired
    private ActivityAreaMapper activityAreaMapper;

    @Autowired
    private ActivityAreaService activityAreaService;

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

    private MockMvc restActivityAreaMockMvc;

    private ActivityArea activityArea;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ActivityAreaResource activityAreaResource = new ActivityAreaResource(activityAreaService);
        this.restActivityAreaMockMvc = MockMvcBuilders.standaloneSetup(activityAreaResource)
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
    public static ActivityArea createEntity(EntityManager em) {
        ActivityArea activityArea = new ActivityArea()
            .code(DEFAULT_CODE)
            .label(DEFAULT_LABEL)
            .active(DEFAULT_ACTIVE);
        return activityArea;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ActivityArea createUpdatedEntity(EntityManager em) {
        ActivityArea activityArea = new ActivityArea()
            .code(UPDATED_CODE)
            .label(UPDATED_LABEL)
            .active(UPDATED_ACTIVE);
        return activityArea;
    }

    @BeforeEach
    public void initTest() {
        activityArea = createEntity(em);
    }

    @Test
    @Transactional
    public void createActivityArea() throws Exception {
        int databaseSizeBeforeCreate = activityAreaRepository.findAll().size();

        // Create the ActivityArea
        ActivityAreaDTO activityAreaDTO = activityAreaMapper.toDto(activityArea);
        restActivityAreaMockMvc.perform(post("/api/activity-areas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(activityAreaDTO)))
            .andExpect(status().isCreated());

        // Validate the ActivityArea in the database
        List<ActivityArea> activityAreaList = activityAreaRepository.findAll();
        assertThat(activityAreaList).hasSize(databaseSizeBeforeCreate + 1);
        ActivityArea testActivityArea = activityAreaList.get(activityAreaList.size() - 1);
        assertThat(testActivityArea.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testActivityArea.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testActivityArea.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createActivityAreaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = activityAreaRepository.findAll().size();

        // Create the ActivityArea with an existing ID
        activityArea.setId(1L);
        ActivityAreaDTO activityAreaDTO = activityAreaMapper.toDto(activityArea);

        // An entity with an existing ID cannot be created, so this API call must fail
        restActivityAreaMockMvc.perform(post("/api/activity-areas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(activityAreaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ActivityArea in the database
        List<ActivityArea> activityAreaList = activityAreaRepository.findAll();
        assertThat(activityAreaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = activityAreaRepository.findAll().size();
        // set the field null
        activityArea.setCode(null);

        // Create the ActivityArea, which fails.
        ActivityAreaDTO activityAreaDTO = activityAreaMapper.toDto(activityArea);

        restActivityAreaMockMvc.perform(post("/api/activity-areas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(activityAreaDTO)))
            .andExpect(status().isBadRequest());

        List<ActivityArea> activityAreaList = activityAreaRepository.findAll();
        assertThat(activityAreaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLabelIsRequired() throws Exception {
        int databaseSizeBeforeTest = activityAreaRepository.findAll().size();
        // set the field null
        activityArea.setLabel(null);

        // Create the ActivityArea, which fails.
        ActivityAreaDTO activityAreaDTO = activityAreaMapper.toDto(activityArea);

        restActivityAreaMockMvc.perform(post("/api/activity-areas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(activityAreaDTO)))
            .andExpect(status().isBadRequest());

        List<ActivityArea> activityAreaList = activityAreaRepository.findAll();
        assertThat(activityAreaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = activityAreaRepository.findAll().size();
        // set the field null
        activityArea.setActive(null);

        // Create the ActivityArea, which fails.
        ActivityAreaDTO activityAreaDTO = activityAreaMapper.toDto(activityArea);

        restActivityAreaMockMvc.perform(post("/api/activity-areas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(activityAreaDTO)))
            .andExpect(status().isBadRequest());

        List<ActivityArea> activityAreaList = activityAreaRepository.findAll();
        assertThat(activityAreaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllActivityAreas() throws Exception {
        // Initialize the database
        activityAreaRepository.saveAndFlush(activityArea);

        // Get all the activityAreaList
        restActivityAreaMockMvc.perform(get("/api/activity-areas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(activityArea.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getActivityArea() throws Exception {
        // Initialize the database
        activityAreaRepository.saveAndFlush(activityArea);

        // Get the activityArea
        restActivityAreaMockMvc.perform(get("/api/activity-areas/{id}", activityArea.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(activityArea.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingActivityArea() throws Exception {
        // Get the activityArea
        restActivityAreaMockMvc.perform(get("/api/activity-areas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateActivityArea() throws Exception {
        // Initialize the database
        activityAreaRepository.saveAndFlush(activityArea);

        int databaseSizeBeforeUpdate = activityAreaRepository.findAll().size();

        // Update the activityArea
        ActivityArea updatedActivityArea = activityAreaRepository.findById(activityArea.getId()).get();
        // Disconnect from session so that the updates on updatedActivityArea are not directly saved in db
        em.detach(updatedActivityArea);
        updatedActivityArea
            .code(UPDATED_CODE)
            .label(UPDATED_LABEL)
            .active(UPDATED_ACTIVE);
        ActivityAreaDTO activityAreaDTO = activityAreaMapper.toDto(updatedActivityArea);

        restActivityAreaMockMvc.perform(put("/api/activity-areas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(activityAreaDTO)))
            .andExpect(status().isOk());

        // Validate the ActivityArea in the database
        List<ActivityArea> activityAreaList = activityAreaRepository.findAll();
        assertThat(activityAreaList).hasSize(databaseSizeBeforeUpdate);
        ActivityArea testActivityArea = activityAreaList.get(activityAreaList.size() - 1);
        assertThat(testActivityArea.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testActivityArea.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testActivityArea.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingActivityArea() throws Exception {
        int databaseSizeBeforeUpdate = activityAreaRepository.findAll().size();

        // Create the ActivityArea
        ActivityAreaDTO activityAreaDTO = activityAreaMapper.toDto(activityArea);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restActivityAreaMockMvc.perform(put("/api/activity-areas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(activityAreaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ActivityArea in the database
        List<ActivityArea> activityAreaList = activityAreaRepository.findAll();
        assertThat(activityAreaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteActivityArea() throws Exception {
        // Initialize the database
        activityAreaRepository.saveAndFlush(activityArea);

        int databaseSizeBeforeDelete = activityAreaRepository.findAll().size();

        // Delete the activityArea
        restActivityAreaMockMvc.perform(delete("/api/activity-areas/{id}", activityArea.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ActivityArea> activityAreaList = activityAreaRepository.findAll();
        assertThat(activityAreaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ActivityArea.class);
        ActivityArea activityArea1 = new ActivityArea();
        activityArea1.setId(1L);
        ActivityArea activityArea2 = new ActivityArea();
        activityArea2.setId(activityArea1.getId());
        assertThat(activityArea1).isEqualTo(activityArea2);
        activityArea2.setId(2L);
        assertThat(activityArea1).isNotEqualTo(activityArea2);
        activityArea1.setId(null);
        assertThat(activityArea1).isNotEqualTo(activityArea2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ActivityAreaDTO.class);
        ActivityAreaDTO activityAreaDTO1 = new ActivityAreaDTO();
        activityAreaDTO1.setId(1L);
        ActivityAreaDTO activityAreaDTO2 = new ActivityAreaDTO();
        assertThat(activityAreaDTO1).isNotEqualTo(activityAreaDTO2);
        activityAreaDTO2.setId(activityAreaDTO1.getId());
        assertThat(activityAreaDTO1).isEqualTo(activityAreaDTO2);
        activityAreaDTO2.setId(2L);
        assertThat(activityAreaDTO1).isNotEqualTo(activityAreaDTO2);
        activityAreaDTO1.setId(null);
        assertThat(activityAreaDTO1).isNotEqualTo(activityAreaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(activityAreaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(activityAreaMapper.fromId(null)).isNull();
    }
}
