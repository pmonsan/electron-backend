package com.electron.mfs.pg.gateway.web.rest;

import com.electron.mfs.pg.gateway.PgkernelgatewayApp;
import com.electron.mfs.pg.gateway.config.SecurityBeanOverrideConfiguration;
import com.electron.mfs.pg.gateway.domain.PricePlan;
import com.electron.mfs.pg.gateway.repository.PricePlanRepository;
import com.electron.mfs.pg.gateway.service.PricePlanService;
import com.electron.mfs.pg.gateway.service.dto.PricePlanDTO;
import com.electron.mfs.pg.gateway.service.mapper.PricePlanMapper;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.electron.mfs.pg.gateway.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link PricePlanResource} REST controller.
 */
@EmbeddedKafka
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, PgkernelgatewayApp.class})
public class PricePlanResourceIT {

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final Instant DEFAULT_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private PricePlanRepository pricePlanRepository;

    @Autowired
    private PricePlanMapper pricePlanMapper;

    @Autowired
    private PricePlanService pricePlanService;

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

    private MockMvc restPricePlanMockMvc;

    private PricePlan pricePlan;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PricePlanResource pricePlanResource = new PricePlanResource(pricePlanService);
        this.restPricePlanMockMvc = MockMvcBuilders.standaloneSetup(pricePlanResource)
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
    public static PricePlan createEntity(EntityManager em) {
        PricePlan pricePlan = new PricePlan()
            .label(DEFAULT_LABEL)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .active(DEFAULT_ACTIVE);
        return pricePlan;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PricePlan createUpdatedEntity(EntityManager em) {
        PricePlan pricePlan = new PricePlan()
            .label(UPDATED_LABEL)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .active(UPDATED_ACTIVE);
        return pricePlan;
    }

    @BeforeEach
    public void initTest() {
        pricePlan = createEntity(em);
    }

    @Test
    @Transactional
    public void createPricePlan() throws Exception {
        int databaseSizeBeforeCreate = pricePlanRepository.findAll().size();

        // Create the PricePlan
        PricePlanDTO pricePlanDTO = pricePlanMapper.toDto(pricePlan);
        restPricePlanMockMvc.perform(post("/api/price-plans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pricePlanDTO)))
            .andExpect(status().isCreated());

        // Validate the PricePlan in the database
        List<PricePlan> pricePlanList = pricePlanRepository.findAll();
        assertThat(pricePlanList).hasSize(databaseSizeBeforeCreate + 1);
        PricePlan testPricePlan = pricePlanList.get(pricePlanList.size() - 1);
        assertThat(testPricePlan.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testPricePlan.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testPricePlan.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testPricePlan.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createPricePlanWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pricePlanRepository.findAll().size();

        // Create the PricePlan with an existing ID
        pricePlan.setId(1L);
        PricePlanDTO pricePlanDTO = pricePlanMapper.toDto(pricePlan);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPricePlanMockMvc.perform(post("/api/price-plans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pricePlanDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PricePlan in the database
        List<PricePlan> pricePlanList = pricePlanRepository.findAll();
        assertThat(pricePlanList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkLabelIsRequired() throws Exception {
        int databaseSizeBeforeTest = pricePlanRepository.findAll().size();
        // set the field null
        pricePlan.setLabel(null);

        // Create the PricePlan, which fails.
        PricePlanDTO pricePlanDTO = pricePlanMapper.toDto(pricePlan);

        restPricePlanMockMvc.perform(post("/api/price-plans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pricePlanDTO)))
            .andExpect(status().isBadRequest());

        List<PricePlan> pricePlanList = pricePlanRepository.findAll();
        assertThat(pricePlanList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = pricePlanRepository.findAll().size();
        // set the field null
        pricePlan.setStartDate(null);

        // Create the PricePlan, which fails.
        PricePlanDTO pricePlanDTO = pricePlanMapper.toDto(pricePlan);

        restPricePlanMockMvc.perform(post("/api/price-plans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pricePlanDTO)))
            .andExpect(status().isBadRequest());

        List<PricePlan> pricePlanList = pricePlanRepository.findAll();
        assertThat(pricePlanList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEndDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = pricePlanRepository.findAll().size();
        // set the field null
        pricePlan.setEndDate(null);

        // Create the PricePlan, which fails.
        PricePlanDTO pricePlanDTO = pricePlanMapper.toDto(pricePlan);

        restPricePlanMockMvc.perform(post("/api/price-plans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pricePlanDTO)))
            .andExpect(status().isBadRequest());

        List<PricePlan> pricePlanList = pricePlanRepository.findAll();
        assertThat(pricePlanList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = pricePlanRepository.findAll().size();
        // set the field null
        pricePlan.setActive(null);

        // Create the PricePlan, which fails.
        PricePlanDTO pricePlanDTO = pricePlanMapper.toDto(pricePlan);

        restPricePlanMockMvc.perform(post("/api/price-plans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pricePlanDTO)))
            .andExpect(status().isBadRequest());

        List<PricePlan> pricePlanList = pricePlanRepository.findAll();
        assertThat(pricePlanList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPricePlans() throws Exception {
        // Initialize the database
        pricePlanRepository.saveAndFlush(pricePlan);

        // Get all the pricePlanList
        restPricePlanMockMvc.perform(get("/api/price-plans?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pricePlan.getId().intValue())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getPricePlan() throws Exception {
        // Initialize the database
        pricePlanRepository.saveAndFlush(pricePlan);

        // Get the pricePlan
        restPricePlanMockMvc.perform(get("/api/price-plans/{id}", pricePlan.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pricePlan.getId().intValue()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPricePlan() throws Exception {
        // Get the pricePlan
        restPricePlanMockMvc.perform(get("/api/price-plans/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePricePlan() throws Exception {
        // Initialize the database
        pricePlanRepository.saveAndFlush(pricePlan);

        int databaseSizeBeforeUpdate = pricePlanRepository.findAll().size();

        // Update the pricePlan
        PricePlan updatedPricePlan = pricePlanRepository.findById(pricePlan.getId()).get();
        // Disconnect from session so that the updates on updatedPricePlan are not directly saved in db
        em.detach(updatedPricePlan);
        updatedPricePlan
            .label(UPDATED_LABEL)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .active(UPDATED_ACTIVE);
        PricePlanDTO pricePlanDTO = pricePlanMapper.toDto(updatedPricePlan);

        restPricePlanMockMvc.perform(put("/api/price-plans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pricePlanDTO)))
            .andExpect(status().isOk());

        // Validate the PricePlan in the database
        List<PricePlan> pricePlanList = pricePlanRepository.findAll();
        assertThat(pricePlanList).hasSize(databaseSizeBeforeUpdate);
        PricePlan testPricePlan = pricePlanList.get(pricePlanList.size() - 1);
        assertThat(testPricePlan.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testPricePlan.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testPricePlan.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testPricePlan.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingPricePlan() throws Exception {
        int databaseSizeBeforeUpdate = pricePlanRepository.findAll().size();

        // Create the PricePlan
        PricePlanDTO pricePlanDTO = pricePlanMapper.toDto(pricePlan);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPricePlanMockMvc.perform(put("/api/price-plans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pricePlanDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PricePlan in the database
        List<PricePlan> pricePlanList = pricePlanRepository.findAll();
        assertThat(pricePlanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePricePlan() throws Exception {
        // Initialize the database
        pricePlanRepository.saveAndFlush(pricePlan);

        int databaseSizeBeforeDelete = pricePlanRepository.findAll().size();

        // Delete the pricePlan
        restPricePlanMockMvc.perform(delete("/api/price-plans/{id}", pricePlan.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PricePlan> pricePlanList = pricePlanRepository.findAll();
        assertThat(pricePlanList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PricePlan.class);
        PricePlan pricePlan1 = new PricePlan();
        pricePlan1.setId(1L);
        PricePlan pricePlan2 = new PricePlan();
        pricePlan2.setId(pricePlan1.getId());
        assertThat(pricePlan1).isEqualTo(pricePlan2);
        pricePlan2.setId(2L);
        assertThat(pricePlan1).isNotEqualTo(pricePlan2);
        pricePlan1.setId(null);
        assertThat(pricePlan1).isNotEqualTo(pricePlan2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PricePlanDTO.class);
        PricePlanDTO pricePlanDTO1 = new PricePlanDTO();
        pricePlanDTO1.setId(1L);
        PricePlanDTO pricePlanDTO2 = new PricePlanDTO();
        assertThat(pricePlanDTO1).isNotEqualTo(pricePlanDTO2);
        pricePlanDTO2.setId(pricePlanDTO1.getId());
        assertThat(pricePlanDTO1).isEqualTo(pricePlanDTO2);
        pricePlanDTO2.setId(2L);
        assertThat(pricePlanDTO1).isNotEqualTo(pricePlanDTO2);
        pricePlanDTO1.setId(null);
        assertThat(pricePlanDTO1).isNotEqualTo(pricePlanDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(pricePlanMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(pricePlanMapper.fromId(null)).isNull();
    }
}
