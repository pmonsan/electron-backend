package com.electron.mfs.pg.customer.web.rest;

import com.electron.mfs.pg.customer.CustomerManagerApp;
import com.electron.mfs.pg.customer.config.SecurityBeanOverrideConfiguration;
import com.electron.mfs.pg.customer.domain.PersonDocument;
import com.electron.mfs.pg.customer.repository.PersonDocumentRepository;
import com.electron.mfs.pg.customer.service.PersonDocumentService;
import com.electron.mfs.pg.customer.service.dto.PersonDocumentDTO;
import com.electron.mfs.pg.customer.service.mapper.PersonDocumentMapper;
import com.electron.mfs.pg.customer.web.rest.errors.ExceptionTranslator;

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

import static com.electron.mfs.pg.customer.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link PersonDocumentResource} REST controller.
 */
@EmbeddedKafka
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, CustomerManagerApp.class})
public class PersonDocumentResourceIT {

    private static final String DEFAULT_DOCUMENT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENT_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_EXPIRATION_DATE = "AAAAAAAAAA";
    private static final String UPDATED_EXPIRATION_DATE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_VALID = false;
    private static final Boolean UPDATED_IS_VALID = true;

    private static final String DEFAULT_DOCUMENT_TYPE_CODE = "AAAAA";
    private static final String UPDATED_DOCUMENT_TYPE_CODE = "BBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private PersonDocumentRepository personDocumentRepository;

    @Autowired
    private PersonDocumentMapper personDocumentMapper;

    @Autowired
    private PersonDocumentService personDocumentService;

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

    private MockMvc restPersonDocumentMockMvc;

    private PersonDocument personDocument;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PersonDocumentResource personDocumentResource = new PersonDocumentResource(personDocumentService);
        this.restPersonDocumentMockMvc = MockMvcBuilders.standaloneSetup(personDocumentResource)
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
    public static PersonDocument createEntity(EntityManager em) {
        PersonDocument personDocument = new PersonDocument()
            .documentNumber(DEFAULT_DOCUMENT_NUMBER)
            .expirationDate(DEFAULT_EXPIRATION_DATE)
            .isValid(DEFAULT_IS_VALID)
            .documentTypeCode(DEFAULT_DOCUMENT_TYPE_CODE)
            .active(DEFAULT_ACTIVE);
        return personDocument;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PersonDocument createUpdatedEntity(EntityManager em) {
        PersonDocument personDocument = new PersonDocument()
            .documentNumber(UPDATED_DOCUMENT_NUMBER)
            .expirationDate(UPDATED_EXPIRATION_DATE)
            .isValid(UPDATED_IS_VALID)
            .documentTypeCode(UPDATED_DOCUMENT_TYPE_CODE)
            .active(UPDATED_ACTIVE);
        return personDocument;
    }

    @BeforeEach
    public void initTest() {
        personDocument = createEntity(em);
    }

    @Test
    @Transactional
    public void createPersonDocument() throws Exception {
        int databaseSizeBeforeCreate = personDocumentRepository.findAll().size();

        // Create the PersonDocument
        PersonDocumentDTO personDocumentDTO = personDocumentMapper.toDto(personDocument);
        restPersonDocumentMockMvc.perform(post("/api/person-documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personDocumentDTO)))
            .andExpect(status().isCreated());

        // Validate the PersonDocument in the database
        List<PersonDocument> personDocumentList = personDocumentRepository.findAll();
        assertThat(personDocumentList).hasSize(databaseSizeBeforeCreate + 1);
        PersonDocument testPersonDocument = personDocumentList.get(personDocumentList.size() - 1);
        assertThat(testPersonDocument.getDocumentNumber()).isEqualTo(DEFAULT_DOCUMENT_NUMBER);
        assertThat(testPersonDocument.getExpirationDate()).isEqualTo(DEFAULT_EXPIRATION_DATE);
        assertThat(testPersonDocument.isIsValid()).isEqualTo(DEFAULT_IS_VALID);
        assertThat(testPersonDocument.getDocumentTypeCode()).isEqualTo(DEFAULT_DOCUMENT_TYPE_CODE);
        assertThat(testPersonDocument.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createPersonDocumentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personDocumentRepository.findAll().size();

        // Create the PersonDocument with an existing ID
        personDocument.setId(1L);
        PersonDocumentDTO personDocumentDTO = personDocumentMapper.toDto(personDocument);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonDocumentMockMvc.perform(post("/api/person-documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personDocumentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PersonDocument in the database
        List<PersonDocument> personDocumentList = personDocumentRepository.findAll();
        assertThat(personDocumentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkIsValidIsRequired() throws Exception {
        int databaseSizeBeforeTest = personDocumentRepository.findAll().size();
        // set the field null
        personDocument.setIsValid(null);

        // Create the PersonDocument, which fails.
        PersonDocumentDTO personDocumentDTO = personDocumentMapper.toDto(personDocument);

        restPersonDocumentMockMvc.perform(post("/api/person-documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personDocumentDTO)))
            .andExpect(status().isBadRequest());

        List<PersonDocument> personDocumentList = personDocumentRepository.findAll();
        assertThat(personDocumentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDocumentTypeCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = personDocumentRepository.findAll().size();
        // set the field null
        personDocument.setDocumentTypeCode(null);

        // Create the PersonDocument, which fails.
        PersonDocumentDTO personDocumentDTO = personDocumentMapper.toDto(personDocument);

        restPersonDocumentMockMvc.perform(post("/api/person-documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personDocumentDTO)))
            .andExpect(status().isBadRequest());

        List<PersonDocument> personDocumentList = personDocumentRepository.findAll();
        assertThat(personDocumentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = personDocumentRepository.findAll().size();
        // set the field null
        personDocument.setActive(null);

        // Create the PersonDocument, which fails.
        PersonDocumentDTO personDocumentDTO = personDocumentMapper.toDto(personDocument);

        restPersonDocumentMockMvc.perform(post("/api/person-documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personDocumentDTO)))
            .andExpect(status().isBadRequest());

        List<PersonDocument> personDocumentList = personDocumentRepository.findAll();
        assertThat(personDocumentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPersonDocuments() throws Exception {
        // Initialize the database
        personDocumentRepository.saveAndFlush(personDocument);

        // Get all the personDocumentList
        restPersonDocumentMockMvc.perform(get("/api/person-documents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personDocument.getId().intValue())))
            .andExpect(jsonPath("$.[*].documentNumber").value(hasItem(DEFAULT_DOCUMENT_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].expirationDate").value(hasItem(DEFAULT_EXPIRATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].isValid").value(hasItem(DEFAULT_IS_VALID.booleanValue())))
            .andExpect(jsonPath("$.[*].documentTypeCode").value(hasItem(DEFAULT_DOCUMENT_TYPE_CODE.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getPersonDocument() throws Exception {
        // Initialize the database
        personDocumentRepository.saveAndFlush(personDocument);

        // Get the personDocument
        restPersonDocumentMockMvc.perform(get("/api/person-documents/{id}", personDocument.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(personDocument.getId().intValue()))
            .andExpect(jsonPath("$.documentNumber").value(DEFAULT_DOCUMENT_NUMBER.toString()))
            .andExpect(jsonPath("$.expirationDate").value(DEFAULT_EXPIRATION_DATE.toString()))
            .andExpect(jsonPath("$.isValid").value(DEFAULT_IS_VALID.booleanValue()))
            .andExpect(jsonPath("$.documentTypeCode").value(DEFAULT_DOCUMENT_TYPE_CODE.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPersonDocument() throws Exception {
        // Get the personDocument
        restPersonDocumentMockMvc.perform(get("/api/person-documents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersonDocument() throws Exception {
        // Initialize the database
        personDocumentRepository.saveAndFlush(personDocument);

        int databaseSizeBeforeUpdate = personDocumentRepository.findAll().size();

        // Update the personDocument
        PersonDocument updatedPersonDocument = personDocumentRepository.findById(personDocument.getId()).get();
        // Disconnect from session so that the updates on updatedPersonDocument are not directly saved in db
        em.detach(updatedPersonDocument);
        updatedPersonDocument
            .documentNumber(UPDATED_DOCUMENT_NUMBER)
            .expirationDate(UPDATED_EXPIRATION_DATE)
            .isValid(UPDATED_IS_VALID)
            .documentTypeCode(UPDATED_DOCUMENT_TYPE_CODE)
            .active(UPDATED_ACTIVE);
        PersonDocumentDTO personDocumentDTO = personDocumentMapper.toDto(updatedPersonDocument);

        restPersonDocumentMockMvc.perform(put("/api/person-documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personDocumentDTO)))
            .andExpect(status().isOk());

        // Validate the PersonDocument in the database
        List<PersonDocument> personDocumentList = personDocumentRepository.findAll();
        assertThat(personDocumentList).hasSize(databaseSizeBeforeUpdate);
        PersonDocument testPersonDocument = personDocumentList.get(personDocumentList.size() - 1);
        assertThat(testPersonDocument.getDocumentNumber()).isEqualTo(UPDATED_DOCUMENT_NUMBER);
        assertThat(testPersonDocument.getExpirationDate()).isEqualTo(UPDATED_EXPIRATION_DATE);
        assertThat(testPersonDocument.isIsValid()).isEqualTo(UPDATED_IS_VALID);
        assertThat(testPersonDocument.getDocumentTypeCode()).isEqualTo(UPDATED_DOCUMENT_TYPE_CODE);
        assertThat(testPersonDocument.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingPersonDocument() throws Exception {
        int databaseSizeBeforeUpdate = personDocumentRepository.findAll().size();

        // Create the PersonDocument
        PersonDocumentDTO personDocumentDTO = personDocumentMapper.toDto(personDocument);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonDocumentMockMvc.perform(put("/api/person-documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personDocumentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PersonDocument in the database
        List<PersonDocument> personDocumentList = personDocumentRepository.findAll();
        assertThat(personDocumentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePersonDocument() throws Exception {
        // Initialize the database
        personDocumentRepository.saveAndFlush(personDocument);

        int databaseSizeBeforeDelete = personDocumentRepository.findAll().size();

        // Delete the personDocument
        restPersonDocumentMockMvc.perform(delete("/api/person-documents/{id}", personDocument.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PersonDocument> personDocumentList = personDocumentRepository.findAll();
        assertThat(personDocumentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PersonDocument.class);
        PersonDocument personDocument1 = new PersonDocument();
        personDocument1.setId(1L);
        PersonDocument personDocument2 = new PersonDocument();
        personDocument2.setId(personDocument1.getId());
        assertThat(personDocument1).isEqualTo(personDocument2);
        personDocument2.setId(2L);
        assertThat(personDocument1).isNotEqualTo(personDocument2);
        personDocument1.setId(null);
        assertThat(personDocument1).isNotEqualTo(personDocument2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PersonDocumentDTO.class);
        PersonDocumentDTO personDocumentDTO1 = new PersonDocumentDTO();
        personDocumentDTO1.setId(1L);
        PersonDocumentDTO personDocumentDTO2 = new PersonDocumentDTO();
        assertThat(personDocumentDTO1).isNotEqualTo(personDocumentDTO2);
        personDocumentDTO2.setId(personDocumentDTO1.getId());
        assertThat(personDocumentDTO1).isEqualTo(personDocumentDTO2);
        personDocumentDTO2.setId(2L);
        assertThat(personDocumentDTO1).isNotEqualTo(personDocumentDTO2);
        personDocumentDTO1.setId(null);
        assertThat(personDocumentDTO1).isNotEqualTo(personDocumentDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(personDocumentMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(personDocumentMapper.fromId(null)).isNull();
    }
}
