package com.electron.mfs.pg.mdm.web.rest;

import com.electron.mfs.pg.mdm.MdmManagerApp;
import com.electron.mfs.pg.mdm.config.SecurityBeanOverrideConfiguration;
import com.electron.mfs.pg.mdm.domain.Forex;
import com.electron.mfs.pg.mdm.repository.ForexRepository;
import com.electron.mfs.pg.mdm.service.ForexService;
import com.electron.mfs.pg.mdm.service.dto.ForexDTO;
import com.electron.mfs.pg.mdm.service.mapper.ForexMapper;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.electron.mfs.pg.mdm.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link ForexResource} REST controller.
 */
@EmbeddedKafka
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, MdmManagerApp.class})
public class ForexResourceIT {

    private static final String DEFAULT_CODE = "AAAAA";
    private static final String UPDATED_CODE = "BBBBB";

    private static final Double DEFAULT_RATE = 1D;
    private static final Double UPDATED_RATE = 2D;

    private static final Instant DEFAULT_CREATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private ForexRepository forexRepository;

    @Autowired
    private ForexMapper forexMapper;

    @Autowired
    private ForexService forexService;

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

    private MockMvc restForexMockMvc;

    private Forex forex;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ForexResource forexResource = new ForexResource(forexService);
        this.restForexMockMvc = MockMvcBuilders.standaloneSetup(forexResource)
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
    public static Forex createEntity(EntityManager em) {
        Forex forex = new Forex()
            .code(DEFAULT_CODE)
            .rate(DEFAULT_RATE)
            .creationDate(DEFAULT_CREATION_DATE)
            .active(DEFAULT_ACTIVE);
        return forex;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Forex createUpdatedEntity(EntityManager em) {
        Forex forex = new Forex()
            .code(UPDATED_CODE)
            .rate(UPDATED_RATE)
            .creationDate(UPDATED_CREATION_DATE)
            .active(UPDATED_ACTIVE);
        return forex;
    }

    @BeforeEach
    public void initTest() {
        forex = createEntity(em);
    }

    @Test
    @Transactional
    public void createForex() throws Exception {
        int databaseSizeBeforeCreate = forexRepository.findAll().size();

        // Create the Forex
        ForexDTO forexDTO = forexMapper.toDto(forex);
        restForexMockMvc.perform(post("/api/forexes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(forexDTO)))
            .andExpect(status().isCreated());

        // Validate the Forex in the database
        List<Forex> forexList = forexRepository.findAll();
        assertThat(forexList).hasSize(databaseSizeBeforeCreate + 1);
        Forex testForex = forexList.get(forexList.size() - 1);
        assertThat(testForex.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testForex.getRate()).isEqualTo(DEFAULT_RATE);
        assertThat(testForex.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testForex.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createForexWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = forexRepository.findAll().size();

        // Create the Forex with an existing ID
        forex.setId(1L);
        ForexDTO forexDTO = forexMapper.toDto(forex);

        // An entity with an existing ID cannot be created, so this API call must fail
        restForexMockMvc.perform(post("/api/forexes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(forexDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Forex in the database
        List<Forex> forexList = forexRepository.findAll();
        assertThat(forexList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = forexRepository.findAll().size();
        // set the field null
        forex.setCode(null);

        // Create the Forex, which fails.
        ForexDTO forexDTO = forexMapper.toDto(forex);

        restForexMockMvc.perform(post("/api/forexes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(forexDTO)))
            .andExpect(status().isBadRequest());

        List<Forex> forexList = forexRepository.findAll();
        assertThat(forexList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = forexRepository.findAll().size();
        // set the field null
        forex.setActive(null);

        // Create the Forex, which fails.
        ForexDTO forexDTO = forexMapper.toDto(forex);

        restForexMockMvc.perform(post("/api/forexes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(forexDTO)))
            .andExpect(status().isBadRequest());

        List<Forex> forexList = forexRepository.findAll();
        assertThat(forexList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllForexes() throws Exception {
        // Initialize the database
        forexRepository.saveAndFlush(forex);

        // Get all the forexList
        restForexMockMvc.perform(get("/api/forexes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(forex.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].rate").value(hasItem(DEFAULT_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getForex() throws Exception {
        // Initialize the database
        forexRepository.saveAndFlush(forex);

        // Get the forex
        restForexMockMvc.perform(get("/api/forexes/{id}", forex.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(forex.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.rate").value(DEFAULT_RATE.doubleValue()))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingForex() throws Exception {
        // Get the forex
        restForexMockMvc.perform(get("/api/forexes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateForex() throws Exception {
        // Initialize the database
        forexRepository.saveAndFlush(forex);

        int databaseSizeBeforeUpdate = forexRepository.findAll().size();

        // Update the forex
        Forex updatedForex = forexRepository.findById(forex.getId()).get();
        // Disconnect from session so that the updates on updatedForex are not directly saved in db
        em.detach(updatedForex);
        updatedForex
            .code(UPDATED_CODE)
            .rate(UPDATED_RATE)
            .creationDate(UPDATED_CREATION_DATE)
            .active(UPDATED_ACTIVE);
        ForexDTO forexDTO = forexMapper.toDto(updatedForex);

        restForexMockMvc.perform(put("/api/forexes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(forexDTO)))
            .andExpect(status().isOk());

        // Validate the Forex in the database
        List<Forex> forexList = forexRepository.findAll();
        assertThat(forexList).hasSize(databaseSizeBeforeUpdate);
        Forex testForex = forexList.get(forexList.size() - 1);
        assertThat(testForex.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testForex.getRate()).isEqualTo(UPDATED_RATE);
        assertThat(testForex.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testForex.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingForex() throws Exception {
        int databaseSizeBeforeUpdate = forexRepository.findAll().size();

        // Create the Forex
        ForexDTO forexDTO = forexMapper.toDto(forex);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restForexMockMvc.perform(put("/api/forexes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(forexDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Forex in the database
        List<Forex> forexList = forexRepository.findAll();
        assertThat(forexList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteForex() throws Exception {
        // Initialize the database
        forexRepository.saveAndFlush(forex);

        int databaseSizeBeforeDelete = forexRepository.findAll().size();

        // Delete the forex
        restForexMockMvc.perform(delete("/api/forexes/{id}", forex.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Forex> forexList = forexRepository.findAll();
        assertThat(forexList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Forex.class);
        Forex forex1 = new Forex();
        forex1.setId(1L);
        Forex forex2 = new Forex();
        forex2.setId(forex1.getId());
        assertThat(forex1).isEqualTo(forex2);
        forex2.setId(2L);
        assertThat(forex1).isNotEqualTo(forex2);
        forex1.setId(null);
        assertThat(forex1).isNotEqualTo(forex2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ForexDTO.class);
        ForexDTO forexDTO1 = new ForexDTO();
        forexDTO1.setId(1L);
        ForexDTO forexDTO2 = new ForexDTO();
        assertThat(forexDTO1).isNotEqualTo(forexDTO2);
        forexDTO2.setId(forexDTO1.getId());
        assertThat(forexDTO1).isEqualTo(forexDTO2);
        forexDTO2.setId(2L);
        assertThat(forexDTO1).isNotEqualTo(forexDTO2);
        forexDTO1.setId(null);
        assertThat(forexDTO1).isNotEqualTo(forexDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(forexMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(forexMapper.fromId(null)).isNull();
    }
}
