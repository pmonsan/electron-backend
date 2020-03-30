package com.electron.mfs.pg.gateway.web.rest;

import com.electron.mfs.pg.gateway.PgkernelgatewayApp;
import com.electron.mfs.pg.gateway.config.SecurityBeanOverrideConfiguration;
import com.electron.mfs.pg.gateway.domain.PersonType;
import com.electron.mfs.pg.gateway.repository.PersonTypeRepository;
import com.electron.mfs.pg.gateway.service.PersonTypeService;
import com.electron.mfs.pg.gateway.service.dto.PersonTypeDTO;
import com.electron.mfs.pg.gateway.service.mapper.PersonTypeMapper;
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
 * Integration tests for the {@Link PersonTypeResource} REST controller.
 */
@EmbeddedKafka
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, PgkernelgatewayApp.class})
public class PersonTypeResourceIT {

    private static final String DEFAULT_CODE = "AAAAA";
    private static final String UPDATED_CODE = "BBBBB";

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private PersonTypeRepository personTypeRepository;

    @Autowired
    private PersonTypeMapper personTypeMapper;

    @Autowired
    private PersonTypeService personTypeService;

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

    private MockMvc restPersonTypeMockMvc;

    private PersonType personType;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PersonTypeResource personTypeResource = new PersonTypeResource(personTypeService);
        this.restPersonTypeMockMvc = MockMvcBuilders.standaloneSetup(personTypeResource)
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
    public static PersonType createEntity(EntityManager em) {
        PersonType personType = new PersonType()
            .code(DEFAULT_CODE)
            .label(DEFAULT_LABEL)
            .active(DEFAULT_ACTIVE);
        return personType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PersonType createUpdatedEntity(EntityManager em) {
        PersonType personType = new PersonType()
            .code(UPDATED_CODE)
            .label(UPDATED_LABEL)
            .active(UPDATED_ACTIVE);
        return personType;
    }

    @BeforeEach
    public void initTest() {
        personType = createEntity(em);
    }

    @Test
    @Transactional
    public void createPersonType() throws Exception {
        int databaseSizeBeforeCreate = personTypeRepository.findAll().size();

        // Create the PersonType
        PersonTypeDTO personTypeDTO = personTypeMapper.toDto(personType);
        restPersonTypeMockMvc.perform(post("/api/person-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the PersonType in the database
        List<PersonType> personTypeList = personTypeRepository.findAll();
        assertThat(personTypeList).hasSize(databaseSizeBeforeCreate + 1);
        PersonType testPersonType = personTypeList.get(personTypeList.size() - 1);
        assertThat(testPersonType.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testPersonType.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testPersonType.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createPersonTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personTypeRepository.findAll().size();

        // Create the PersonType with an existing ID
        personType.setId(1L);
        PersonTypeDTO personTypeDTO = personTypeMapper.toDto(personType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonTypeMockMvc.perform(post("/api/person-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PersonType in the database
        List<PersonType> personTypeList = personTypeRepository.findAll();
        assertThat(personTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = personTypeRepository.findAll().size();
        // set the field null
        personType.setCode(null);

        // Create the PersonType, which fails.
        PersonTypeDTO personTypeDTO = personTypeMapper.toDto(personType);

        restPersonTypeMockMvc.perform(post("/api/person-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personTypeDTO)))
            .andExpect(status().isBadRequest());

        List<PersonType> personTypeList = personTypeRepository.findAll();
        assertThat(personTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLabelIsRequired() throws Exception {
        int databaseSizeBeforeTest = personTypeRepository.findAll().size();
        // set the field null
        personType.setLabel(null);

        // Create the PersonType, which fails.
        PersonTypeDTO personTypeDTO = personTypeMapper.toDto(personType);

        restPersonTypeMockMvc.perform(post("/api/person-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personTypeDTO)))
            .andExpect(status().isBadRequest());

        List<PersonType> personTypeList = personTypeRepository.findAll();
        assertThat(personTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = personTypeRepository.findAll().size();
        // set the field null
        personType.setActive(null);

        // Create the PersonType, which fails.
        PersonTypeDTO personTypeDTO = personTypeMapper.toDto(personType);

        restPersonTypeMockMvc.perform(post("/api/person-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personTypeDTO)))
            .andExpect(status().isBadRequest());

        List<PersonType> personTypeList = personTypeRepository.findAll();
        assertThat(personTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPersonTypes() throws Exception {
        // Initialize the database
        personTypeRepository.saveAndFlush(personType);

        // Get all the personTypeList
        restPersonTypeMockMvc.perform(get("/api/person-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personType.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getPersonType() throws Exception {
        // Initialize the database
        personTypeRepository.saveAndFlush(personType);

        // Get the personType
        restPersonTypeMockMvc.perform(get("/api/person-types/{id}", personType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(personType.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPersonType() throws Exception {
        // Get the personType
        restPersonTypeMockMvc.perform(get("/api/person-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersonType() throws Exception {
        // Initialize the database
        personTypeRepository.saveAndFlush(personType);

        int databaseSizeBeforeUpdate = personTypeRepository.findAll().size();

        // Update the personType
        PersonType updatedPersonType = personTypeRepository.findById(personType.getId()).get();
        // Disconnect from session so that the updates on updatedPersonType are not directly saved in db
        em.detach(updatedPersonType);
        updatedPersonType
            .code(UPDATED_CODE)
            .label(UPDATED_LABEL)
            .active(UPDATED_ACTIVE);
        PersonTypeDTO personTypeDTO = personTypeMapper.toDto(updatedPersonType);

        restPersonTypeMockMvc.perform(put("/api/person-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personTypeDTO)))
            .andExpect(status().isOk());

        // Validate the PersonType in the database
        List<PersonType> personTypeList = personTypeRepository.findAll();
        assertThat(personTypeList).hasSize(databaseSizeBeforeUpdate);
        PersonType testPersonType = personTypeList.get(personTypeList.size() - 1);
        assertThat(testPersonType.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testPersonType.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testPersonType.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingPersonType() throws Exception {
        int databaseSizeBeforeUpdate = personTypeRepository.findAll().size();

        // Create the PersonType
        PersonTypeDTO personTypeDTO = personTypeMapper.toDto(personType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonTypeMockMvc.perform(put("/api/person-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PersonType in the database
        List<PersonType> personTypeList = personTypeRepository.findAll();
        assertThat(personTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePersonType() throws Exception {
        // Initialize the database
        personTypeRepository.saveAndFlush(personType);

        int databaseSizeBeforeDelete = personTypeRepository.findAll().size();

        // Delete the personType
        restPersonTypeMockMvc.perform(delete("/api/person-types/{id}", personType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PersonType> personTypeList = personTypeRepository.findAll();
        assertThat(personTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PersonType.class);
        PersonType personType1 = new PersonType();
        personType1.setId(1L);
        PersonType personType2 = new PersonType();
        personType2.setId(personType1.getId());
        assertThat(personType1).isEqualTo(personType2);
        personType2.setId(2L);
        assertThat(personType1).isNotEqualTo(personType2);
        personType1.setId(null);
        assertThat(personType1).isNotEqualTo(personType2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PersonTypeDTO.class);
        PersonTypeDTO personTypeDTO1 = new PersonTypeDTO();
        personTypeDTO1.setId(1L);
        PersonTypeDTO personTypeDTO2 = new PersonTypeDTO();
        assertThat(personTypeDTO1).isNotEqualTo(personTypeDTO2);
        personTypeDTO2.setId(personTypeDTO1.getId());
        assertThat(personTypeDTO1).isEqualTo(personTypeDTO2);
        personTypeDTO2.setId(2L);
        assertThat(personTypeDTO1).isNotEqualTo(personTypeDTO2);
        personTypeDTO1.setId(null);
        assertThat(personTypeDTO1).isNotEqualTo(personTypeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(personTypeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(personTypeMapper.fromId(null)).isNull();
    }
}
