package com.electron.mfs.pg.gateway.web.rest;

import com.electron.mfs.pg.gateway.PgkernelgatewayApp;
import com.electron.mfs.pg.gateway.config.SecurityBeanOverrideConfiguration;
import com.electron.mfs.pg.gateway.domain.PersonGender;
import com.electron.mfs.pg.gateway.repository.PersonGenderRepository;
import com.electron.mfs.pg.gateway.service.PersonGenderService;
import com.electron.mfs.pg.gateway.service.dto.PersonGenderDTO;
import com.electron.mfs.pg.gateway.service.mapper.PersonGenderMapper;
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
 * Integration tests for the {@Link PersonGenderResource} REST controller.
 */
@EmbeddedKafka
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, PgkernelgatewayApp.class})
public class PersonGenderResourceIT {

    private static final String DEFAULT_CODE = "AAAAA";
    private static final String UPDATED_CODE = "BBBBB";

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private PersonGenderRepository personGenderRepository;

    @Autowired
    private PersonGenderMapper personGenderMapper;

    @Autowired
    private PersonGenderService personGenderService;

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

    private MockMvc restPersonGenderMockMvc;

    private PersonGender personGender;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PersonGenderResource personGenderResource = new PersonGenderResource(personGenderService);
        this.restPersonGenderMockMvc = MockMvcBuilders.standaloneSetup(personGenderResource)
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
    public static PersonGender createEntity(EntityManager em) {
        PersonGender personGender = new PersonGender()
            .code(DEFAULT_CODE)
            .label(DEFAULT_LABEL)
            .active(DEFAULT_ACTIVE);
        return personGender;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PersonGender createUpdatedEntity(EntityManager em) {
        PersonGender personGender = new PersonGender()
            .code(UPDATED_CODE)
            .label(UPDATED_LABEL)
            .active(UPDATED_ACTIVE);
        return personGender;
    }

    @BeforeEach
    public void initTest() {
        personGender = createEntity(em);
    }

    @Test
    @Transactional
    public void createPersonGender() throws Exception {
        int databaseSizeBeforeCreate = personGenderRepository.findAll().size();

        // Create the PersonGender
        PersonGenderDTO personGenderDTO = personGenderMapper.toDto(personGender);
        restPersonGenderMockMvc.perform(post("/api/person-genders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personGenderDTO)))
            .andExpect(status().isCreated());

        // Validate the PersonGender in the database
        List<PersonGender> personGenderList = personGenderRepository.findAll();
        assertThat(personGenderList).hasSize(databaseSizeBeforeCreate + 1);
        PersonGender testPersonGender = personGenderList.get(personGenderList.size() - 1);
        assertThat(testPersonGender.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testPersonGender.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testPersonGender.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createPersonGenderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personGenderRepository.findAll().size();

        // Create the PersonGender with an existing ID
        personGender.setId(1L);
        PersonGenderDTO personGenderDTO = personGenderMapper.toDto(personGender);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonGenderMockMvc.perform(post("/api/person-genders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personGenderDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PersonGender in the database
        List<PersonGender> personGenderList = personGenderRepository.findAll();
        assertThat(personGenderList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = personGenderRepository.findAll().size();
        // set the field null
        personGender.setCode(null);

        // Create the PersonGender, which fails.
        PersonGenderDTO personGenderDTO = personGenderMapper.toDto(personGender);

        restPersonGenderMockMvc.perform(post("/api/person-genders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personGenderDTO)))
            .andExpect(status().isBadRequest());

        List<PersonGender> personGenderList = personGenderRepository.findAll();
        assertThat(personGenderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLabelIsRequired() throws Exception {
        int databaseSizeBeforeTest = personGenderRepository.findAll().size();
        // set the field null
        personGender.setLabel(null);

        // Create the PersonGender, which fails.
        PersonGenderDTO personGenderDTO = personGenderMapper.toDto(personGender);

        restPersonGenderMockMvc.perform(post("/api/person-genders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personGenderDTO)))
            .andExpect(status().isBadRequest());

        List<PersonGender> personGenderList = personGenderRepository.findAll();
        assertThat(personGenderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = personGenderRepository.findAll().size();
        // set the field null
        personGender.setActive(null);

        // Create the PersonGender, which fails.
        PersonGenderDTO personGenderDTO = personGenderMapper.toDto(personGender);

        restPersonGenderMockMvc.perform(post("/api/person-genders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personGenderDTO)))
            .andExpect(status().isBadRequest());

        List<PersonGender> personGenderList = personGenderRepository.findAll();
        assertThat(personGenderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPersonGenders() throws Exception {
        // Initialize the database
        personGenderRepository.saveAndFlush(personGender);

        // Get all the personGenderList
        restPersonGenderMockMvc.perform(get("/api/person-genders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personGender.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getPersonGender() throws Exception {
        // Initialize the database
        personGenderRepository.saveAndFlush(personGender);

        // Get the personGender
        restPersonGenderMockMvc.perform(get("/api/person-genders/{id}", personGender.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(personGender.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPersonGender() throws Exception {
        // Get the personGender
        restPersonGenderMockMvc.perform(get("/api/person-genders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersonGender() throws Exception {
        // Initialize the database
        personGenderRepository.saveAndFlush(personGender);

        int databaseSizeBeforeUpdate = personGenderRepository.findAll().size();

        // Update the personGender
        PersonGender updatedPersonGender = personGenderRepository.findById(personGender.getId()).get();
        // Disconnect from session so that the updates on updatedPersonGender are not directly saved in db
        em.detach(updatedPersonGender);
        updatedPersonGender
            .code(UPDATED_CODE)
            .label(UPDATED_LABEL)
            .active(UPDATED_ACTIVE);
        PersonGenderDTO personGenderDTO = personGenderMapper.toDto(updatedPersonGender);

        restPersonGenderMockMvc.perform(put("/api/person-genders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personGenderDTO)))
            .andExpect(status().isOk());

        // Validate the PersonGender in the database
        List<PersonGender> personGenderList = personGenderRepository.findAll();
        assertThat(personGenderList).hasSize(databaseSizeBeforeUpdate);
        PersonGender testPersonGender = personGenderList.get(personGenderList.size() - 1);
        assertThat(testPersonGender.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testPersonGender.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testPersonGender.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingPersonGender() throws Exception {
        int databaseSizeBeforeUpdate = personGenderRepository.findAll().size();

        // Create the PersonGender
        PersonGenderDTO personGenderDTO = personGenderMapper.toDto(personGender);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonGenderMockMvc.perform(put("/api/person-genders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personGenderDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PersonGender in the database
        List<PersonGender> personGenderList = personGenderRepository.findAll();
        assertThat(personGenderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePersonGender() throws Exception {
        // Initialize the database
        personGenderRepository.saveAndFlush(personGender);

        int databaseSizeBeforeDelete = personGenderRepository.findAll().size();

        // Delete the personGender
        restPersonGenderMockMvc.perform(delete("/api/person-genders/{id}", personGender.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PersonGender> personGenderList = personGenderRepository.findAll();
        assertThat(personGenderList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PersonGender.class);
        PersonGender personGender1 = new PersonGender();
        personGender1.setId(1L);
        PersonGender personGender2 = new PersonGender();
        personGender2.setId(personGender1.getId());
        assertThat(personGender1).isEqualTo(personGender2);
        personGender2.setId(2L);
        assertThat(personGender1).isNotEqualTo(personGender2);
        personGender1.setId(null);
        assertThat(personGender1).isNotEqualTo(personGender2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PersonGenderDTO.class);
        PersonGenderDTO personGenderDTO1 = new PersonGenderDTO();
        personGenderDTO1.setId(1L);
        PersonGenderDTO personGenderDTO2 = new PersonGenderDTO();
        assertThat(personGenderDTO1).isNotEqualTo(personGenderDTO2);
        personGenderDTO2.setId(personGenderDTO1.getId());
        assertThat(personGenderDTO1).isEqualTo(personGenderDTO2);
        personGenderDTO2.setId(2L);
        assertThat(personGenderDTO1).isNotEqualTo(personGenderDTO2);
        personGenderDTO1.setId(null);
        assertThat(personGenderDTO1).isNotEqualTo(personGenderDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(personGenderMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(personGenderMapper.fromId(null)).isNull();
    }
}
