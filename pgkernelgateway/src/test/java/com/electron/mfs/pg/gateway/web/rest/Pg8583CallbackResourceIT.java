package com.electron.mfs.pg.gateway.web.rest;

import com.electron.mfs.pg.gateway.PgkernelgatewayApp;
import com.electron.mfs.pg.gateway.config.SecurityBeanOverrideConfiguration;
import com.electron.mfs.pg.gateway.domain.Pg8583Callback;
import com.electron.mfs.pg.gateway.repository.Pg8583CallbackRepository;
import com.electron.mfs.pg.gateway.service.Pg8583CallbackService;
import com.electron.mfs.pg.gateway.service.dto.Pg8583CallbackDTO;
import com.electron.mfs.pg.gateway.service.mapper.Pg8583CallbackMapper;
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
 * Integration tests for the {@Link Pg8583CallbackResource} REST controller.
 */
@EmbeddedKafka
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, PgkernelgatewayApp.class})
public class Pg8583CallbackResourceIT {

    private static final String DEFAULT_PARTNER_CODE = "AAAAA";
    private static final String UPDATED_PARTNER_CODE = "BBBBB";

    private static final String DEFAULT_CALLBACK_URI = "AAAAAAAAAA";
    private static final String UPDATED_CALLBACK_URI = "BBBBBBBBBB";

    private static final String DEFAULT_HTTP_METHOD = "AAAAAAAAAA";
    private static final String UPDATED_HTTP_METHOD = "BBBBBBBBBB";

    private static final String DEFAULT_MANAGER_CLASS = "AAAAAAAAAA";
    private static final String UPDATED_MANAGER_CLASS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private Pg8583CallbackRepository pg8583CallbackRepository;

    @Autowired
    private Pg8583CallbackMapper pg8583CallbackMapper;

    @Autowired
    private Pg8583CallbackService pg8583CallbackService;

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

    private MockMvc restPg8583CallbackMockMvc;

    private Pg8583Callback pg8583Callback;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final Pg8583CallbackResource pg8583CallbackResource = new Pg8583CallbackResource(pg8583CallbackService);
        this.restPg8583CallbackMockMvc = MockMvcBuilders.standaloneSetup(pg8583CallbackResource)
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
    public static Pg8583Callback createEntity(EntityManager em) {
        Pg8583Callback pg8583Callback = new Pg8583Callback()
            .partnerCode(DEFAULT_PARTNER_CODE)
            .callbackUri(DEFAULT_CALLBACK_URI)
            .httpMethod(DEFAULT_HTTP_METHOD)
            .managerClass(DEFAULT_MANAGER_CLASS)
            .active(DEFAULT_ACTIVE);
        return pg8583Callback;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pg8583Callback createUpdatedEntity(EntityManager em) {
        Pg8583Callback pg8583Callback = new Pg8583Callback()
            .partnerCode(UPDATED_PARTNER_CODE)
            .callbackUri(UPDATED_CALLBACK_URI)
            .httpMethod(UPDATED_HTTP_METHOD)
            .managerClass(UPDATED_MANAGER_CLASS)
            .active(UPDATED_ACTIVE);
        return pg8583Callback;
    }

    @BeforeEach
    public void initTest() {
        pg8583Callback = createEntity(em);
    }

    @Test
    @Transactional
    public void createPg8583Callback() throws Exception {
        int databaseSizeBeforeCreate = pg8583CallbackRepository.findAll().size();

        // Create the Pg8583Callback
        Pg8583CallbackDTO pg8583CallbackDTO = pg8583CallbackMapper.toDto(pg8583Callback);
        restPg8583CallbackMockMvc.perform(post("/api/pg-8583-callbacks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pg8583CallbackDTO)))
            .andExpect(status().isCreated());

        // Validate the Pg8583Callback in the database
        List<Pg8583Callback> pg8583CallbackList = pg8583CallbackRepository.findAll();
        assertThat(pg8583CallbackList).hasSize(databaseSizeBeforeCreate + 1);
        Pg8583Callback testPg8583Callback = pg8583CallbackList.get(pg8583CallbackList.size() - 1);
        assertThat(testPg8583Callback.getPartnerCode()).isEqualTo(DEFAULT_PARTNER_CODE);
        assertThat(testPg8583Callback.getCallbackUri()).isEqualTo(DEFAULT_CALLBACK_URI);
        assertThat(testPg8583Callback.getHttpMethod()).isEqualTo(DEFAULT_HTTP_METHOD);
        assertThat(testPg8583Callback.getManagerClass()).isEqualTo(DEFAULT_MANAGER_CLASS);
        assertThat(testPg8583Callback.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createPg8583CallbackWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pg8583CallbackRepository.findAll().size();

        // Create the Pg8583Callback with an existing ID
        pg8583Callback.setId(1L);
        Pg8583CallbackDTO pg8583CallbackDTO = pg8583CallbackMapper.toDto(pg8583Callback);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPg8583CallbackMockMvc.perform(post("/api/pg-8583-callbacks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pg8583CallbackDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Pg8583Callback in the database
        List<Pg8583Callback> pg8583CallbackList = pg8583CallbackRepository.findAll();
        assertThat(pg8583CallbackList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCallbackUriIsRequired() throws Exception {
        int databaseSizeBeforeTest = pg8583CallbackRepository.findAll().size();
        // set the field null
        pg8583Callback.setCallbackUri(null);

        // Create the Pg8583Callback, which fails.
        Pg8583CallbackDTO pg8583CallbackDTO = pg8583CallbackMapper.toDto(pg8583Callback);

        restPg8583CallbackMockMvc.perform(post("/api/pg-8583-callbacks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pg8583CallbackDTO)))
            .andExpect(status().isBadRequest());

        List<Pg8583Callback> pg8583CallbackList = pg8583CallbackRepository.findAll();
        assertThat(pg8583CallbackList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHttpMethodIsRequired() throws Exception {
        int databaseSizeBeforeTest = pg8583CallbackRepository.findAll().size();
        // set the field null
        pg8583Callback.setHttpMethod(null);

        // Create the Pg8583Callback, which fails.
        Pg8583CallbackDTO pg8583CallbackDTO = pg8583CallbackMapper.toDto(pg8583Callback);

        restPg8583CallbackMockMvc.perform(post("/api/pg-8583-callbacks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pg8583CallbackDTO)))
            .andExpect(status().isBadRequest());

        List<Pg8583Callback> pg8583CallbackList = pg8583CallbackRepository.findAll();
        assertThat(pg8583CallbackList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkManagerClassIsRequired() throws Exception {
        int databaseSizeBeforeTest = pg8583CallbackRepository.findAll().size();
        // set the field null
        pg8583Callback.setManagerClass(null);

        // Create the Pg8583Callback, which fails.
        Pg8583CallbackDTO pg8583CallbackDTO = pg8583CallbackMapper.toDto(pg8583Callback);

        restPg8583CallbackMockMvc.perform(post("/api/pg-8583-callbacks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pg8583CallbackDTO)))
            .andExpect(status().isBadRequest());

        List<Pg8583Callback> pg8583CallbackList = pg8583CallbackRepository.findAll();
        assertThat(pg8583CallbackList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = pg8583CallbackRepository.findAll().size();
        // set the field null
        pg8583Callback.setActive(null);

        // Create the Pg8583Callback, which fails.
        Pg8583CallbackDTO pg8583CallbackDTO = pg8583CallbackMapper.toDto(pg8583Callback);

        restPg8583CallbackMockMvc.perform(post("/api/pg-8583-callbacks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pg8583CallbackDTO)))
            .andExpect(status().isBadRequest());

        List<Pg8583Callback> pg8583CallbackList = pg8583CallbackRepository.findAll();
        assertThat(pg8583CallbackList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPg8583Callbacks() throws Exception {
        // Initialize the database
        pg8583CallbackRepository.saveAndFlush(pg8583Callback);

        // Get all the pg8583CallbackList
        restPg8583CallbackMockMvc.perform(get("/api/pg-8583-callbacks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pg8583Callback.getId().intValue())))
            .andExpect(jsonPath("$.[*].partnerCode").value(hasItem(DEFAULT_PARTNER_CODE.toString())))
            .andExpect(jsonPath("$.[*].callbackUri").value(hasItem(DEFAULT_CALLBACK_URI.toString())))
            .andExpect(jsonPath("$.[*].httpMethod").value(hasItem(DEFAULT_HTTP_METHOD.toString())))
            .andExpect(jsonPath("$.[*].managerClass").value(hasItem(DEFAULT_MANAGER_CLASS.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getPg8583Callback() throws Exception {
        // Initialize the database
        pg8583CallbackRepository.saveAndFlush(pg8583Callback);

        // Get the pg8583Callback
        restPg8583CallbackMockMvc.perform(get("/api/pg-8583-callbacks/{id}", pg8583Callback.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pg8583Callback.getId().intValue()))
            .andExpect(jsonPath("$.partnerCode").value(DEFAULT_PARTNER_CODE.toString()))
            .andExpect(jsonPath("$.callbackUri").value(DEFAULT_CALLBACK_URI.toString()))
            .andExpect(jsonPath("$.httpMethod").value(DEFAULT_HTTP_METHOD.toString()))
            .andExpect(jsonPath("$.managerClass").value(DEFAULT_MANAGER_CLASS.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPg8583Callback() throws Exception {
        // Get the pg8583Callback
        restPg8583CallbackMockMvc.perform(get("/api/pg-8583-callbacks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePg8583Callback() throws Exception {
        // Initialize the database
        pg8583CallbackRepository.saveAndFlush(pg8583Callback);

        int databaseSizeBeforeUpdate = pg8583CallbackRepository.findAll().size();

        // Update the pg8583Callback
        Pg8583Callback updatedPg8583Callback = pg8583CallbackRepository.findById(pg8583Callback.getId()).get();
        // Disconnect from session so that the updates on updatedPg8583Callback are not directly saved in db
        em.detach(updatedPg8583Callback);
        updatedPg8583Callback
            .partnerCode(UPDATED_PARTNER_CODE)
            .callbackUri(UPDATED_CALLBACK_URI)
            .httpMethod(UPDATED_HTTP_METHOD)
            .managerClass(UPDATED_MANAGER_CLASS)
            .active(UPDATED_ACTIVE);
        Pg8583CallbackDTO pg8583CallbackDTO = pg8583CallbackMapper.toDto(updatedPg8583Callback);

        restPg8583CallbackMockMvc.perform(put("/api/pg-8583-callbacks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pg8583CallbackDTO)))
            .andExpect(status().isOk());

        // Validate the Pg8583Callback in the database
        List<Pg8583Callback> pg8583CallbackList = pg8583CallbackRepository.findAll();
        assertThat(pg8583CallbackList).hasSize(databaseSizeBeforeUpdate);
        Pg8583Callback testPg8583Callback = pg8583CallbackList.get(pg8583CallbackList.size() - 1);
        assertThat(testPg8583Callback.getPartnerCode()).isEqualTo(UPDATED_PARTNER_CODE);
        assertThat(testPg8583Callback.getCallbackUri()).isEqualTo(UPDATED_CALLBACK_URI);
        assertThat(testPg8583Callback.getHttpMethod()).isEqualTo(UPDATED_HTTP_METHOD);
        assertThat(testPg8583Callback.getManagerClass()).isEqualTo(UPDATED_MANAGER_CLASS);
        assertThat(testPg8583Callback.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingPg8583Callback() throws Exception {
        int databaseSizeBeforeUpdate = pg8583CallbackRepository.findAll().size();

        // Create the Pg8583Callback
        Pg8583CallbackDTO pg8583CallbackDTO = pg8583CallbackMapper.toDto(pg8583Callback);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPg8583CallbackMockMvc.perform(put("/api/pg-8583-callbacks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pg8583CallbackDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Pg8583Callback in the database
        List<Pg8583Callback> pg8583CallbackList = pg8583CallbackRepository.findAll();
        assertThat(pg8583CallbackList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePg8583Callback() throws Exception {
        // Initialize the database
        pg8583CallbackRepository.saveAndFlush(pg8583Callback);

        int databaseSizeBeforeDelete = pg8583CallbackRepository.findAll().size();

        // Delete the pg8583Callback
        restPg8583CallbackMockMvc.perform(delete("/api/pg-8583-callbacks/{id}", pg8583Callback.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Pg8583Callback> pg8583CallbackList = pg8583CallbackRepository.findAll();
        assertThat(pg8583CallbackList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pg8583Callback.class);
        Pg8583Callback pg8583Callback1 = new Pg8583Callback();
        pg8583Callback1.setId(1L);
        Pg8583Callback pg8583Callback2 = new Pg8583Callback();
        pg8583Callback2.setId(pg8583Callback1.getId());
        assertThat(pg8583Callback1).isEqualTo(pg8583Callback2);
        pg8583Callback2.setId(2L);
        assertThat(pg8583Callback1).isNotEqualTo(pg8583Callback2);
        pg8583Callback1.setId(null);
        assertThat(pg8583Callback1).isNotEqualTo(pg8583Callback2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pg8583CallbackDTO.class);
        Pg8583CallbackDTO pg8583CallbackDTO1 = new Pg8583CallbackDTO();
        pg8583CallbackDTO1.setId(1L);
        Pg8583CallbackDTO pg8583CallbackDTO2 = new Pg8583CallbackDTO();
        assertThat(pg8583CallbackDTO1).isNotEqualTo(pg8583CallbackDTO2);
        pg8583CallbackDTO2.setId(pg8583CallbackDTO1.getId());
        assertThat(pg8583CallbackDTO1).isEqualTo(pg8583CallbackDTO2);
        pg8583CallbackDTO2.setId(2L);
        assertThat(pg8583CallbackDTO1).isNotEqualTo(pg8583CallbackDTO2);
        pg8583CallbackDTO1.setId(null);
        assertThat(pg8583CallbackDTO1).isNotEqualTo(pg8583CallbackDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(pg8583CallbackMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(pg8583CallbackMapper.fromId(null)).isNull();
    }
}
