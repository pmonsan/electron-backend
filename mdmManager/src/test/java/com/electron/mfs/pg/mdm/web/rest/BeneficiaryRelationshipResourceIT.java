package com.electron.mfs.pg.mdm.web.rest;

import com.electron.mfs.pg.mdm.MdmManagerApp;
import com.electron.mfs.pg.mdm.config.SecurityBeanOverrideConfiguration;
import com.electron.mfs.pg.mdm.domain.BeneficiaryRelationship;
import com.electron.mfs.pg.mdm.repository.BeneficiaryRelationshipRepository;
import com.electron.mfs.pg.mdm.service.BeneficiaryRelationshipService;
import com.electron.mfs.pg.mdm.service.dto.BeneficiaryRelationshipDTO;
import com.electron.mfs.pg.mdm.service.mapper.BeneficiaryRelationshipMapper;
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
 * Integration tests for the {@Link BeneficiaryRelationshipResource} REST controller.
 */
@EmbeddedKafka
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, MdmManagerApp.class})
public class BeneficiaryRelationshipResourceIT {

    private static final String DEFAULT_CODE = "AAAAA";
    private static final String UPDATED_CODE = "BBBBB";

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private BeneficiaryRelationshipRepository beneficiaryRelationshipRepository;

    @Autowired
    private BeneficiaryRelationshipMapper beneficiaryRelationshipMapper;

    @Autowired
    private BeneficiaryRelationshipService beneficiaryRelationshipService;

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

    private MockMvc restBeneficiaryRelationshipMockMvc;

    private BeneficiaryRelationship beneficiaryRelationship;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BeneficiaryRelationshipResource beneficiaryRelationshipResource = new BeneficiaryRelationshipResource(beneficiaryRelationshipService);
        this.restBeneficiaryRelationshipMockMvc = MockMvcBuilders.standaloneSetup(beneficiaryRelationshipResource)
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
    public static BeneficiaryRelationship createEntity(EntityManager em) {
        BeneficiaryRelationship beneficiaryRelationship = new BeneficiaryRelationship()
            .code(DEFAULT_CODE)
            .label(DEFAULT_LABEL)
            .active(DEFAULT_ACTIVE);
        return beneficiaryRelationship;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BeneficiaryRelationship createUpdatedEntity(EntityManager em) {
        BeneficiaryRelationship beneficiaryRelationship = new BeneficiaryRelationship()
            .code(UPDATED_CODE)
            .label(UPDATED_LABEL)
            .active(UPDATED_ACTIVE);
        return beneficiaryRelationship;
    }

    @BeforeEach
    public void initTest() {
        beneficiaryRelationship = createEntity(em);
    }

    @Test
    @Transactional
    public void createBeneficiaryRelationship() throws Exception {
        int databaseSizeBeforeCreate = beneficiaryRelationshipRepository.findAll().size();

        // Create the BeneficiaryRelationship
        BeneficiaryRelationshipDTO beneficiaryRelationshipDTO = beneficiaryRelationshipMapper.toDto(beneficiaryRelationship);
        restBeneficiaryRelationshipMockMvc.perform(post("/api/beneficiary-relationships")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(beneficiaryRelationshipDTO)))
            .andExpect(status().isCreated());

        // Validate the BeneficiaryRelationship in the database
        List<BeneficiaryRelationship> beneficiaryRelationshipList = beneficiaryRelationshipRepository.findAll();
        assertThat(beneficiaryRelationshipList).hasSize(databaseSizeBeforeCreate + 1);
        BeneficiaryRelationship testBeneficiaryRelationship = beneficiaryRelationshipList.get(beneficiaryRelationshipList.size() - 1);
        assertThat(testBeneficiaryRelationship.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testBeneficiaryRelationship.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testBeneficiaryRelationship.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createBeneficiaryRelationshipWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = beneficiaryRelationshipRepository.findAll().size();

        // Create the BeneficiaryRelationship with an existing ID
        beneficiaryRelationship.setId(1L);
        BeneficiaryRelationshipDTO beneficiaryRelationshipDTO = beneficiaryRelationshipMapper.toDto(beneficiaryRelationship);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBeneficiaryRelationshipMockMvc.perform(post("/api/beneficiary-relationships")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(beneficiaryRelationshipDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BeneficiaryRelationship in the database
        List<BeneficiaryRelationship> beneficiaryRelationshipList = beneficiaryRelationshipRepository.findAll();
        assertThat(beneficiaryRelationshipList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = beneficiaryRelationshipRepository.findAll().size();
        // set the field null
        beneficiaryRelationship.setCode(null);

        // Create the BeneficiaryRelationship, which fails.
        BeneficiaryRelationshipDTO beneficiaryRelationshipDTO = beneficiaryRelationshipMapper.toDto(beneficiaryRelationship);

        restBeneficiaryRelationshipMockMvc.perform(post("/api/beneficiary-relationships")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(beneficiaryRelationshipDTO)))
            .andExpect(status().isBadRequest());

        List<BeneficiaryRelationship> beneficiaryRelationshipList = beneficiaryRelationshipRepository.findAll();
        assertThat(beneficiaryRelationshipList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLabelIsRequired() throws Exception {
        int databaseSizeBeforeTest = beneficiaryRelationshipRepository.findAll().size();
        // set the field null
        beneficiaryRelationship.setLabel(null);

        // Create the BeneficiaryRelationship, which fails.
        BeneficiaryRelationshipDTO beneficiaryRelationshipDTO = beneficiaryRelationshipMapper.toDto(beneficiaryRelationship);

        restBeneficiaryRelationshipMockMvc.perform(post("/api/beneficiary-relationships")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(beneficiaryRelationshipDTO)))
            .andExpect(status().isBadRequest());

        List<BeneficiaryRelationship> beneficiaryRelationshipList = beneficiaryRelationshipRepository.findAll();
        assertThat(beneficiaryRelationshipList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = beneficiaryRelationshipRepository.findAll().size();
        // set the field null
        beneficiaryRelationship.setActive(null);

        // Create the BeneficiaryRelationship, which fails.
        BeneficiaryRelationshipDTO beneficiaryRelationshipDTO = beneficiaryRelationshipMapper.toDto(beneficiaryRelationship);

        restBeneficiaryRelationshipMockMvc.perform(post("/api/beneficiary-relationships")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(beneficiaryRelationshipDTO)))
            .andExpect(status().isBadRequest());

        List<BeneficiaryRelationship> beneficiaryRelationshipList = beneficiaryRelationshipRepository.findAll();
        assertThat(beneficiaryRelationshipList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBeneficiaryRelationships() throws Exception {
        // Initialize the database
        beneficiaryRelationshipRepository.saveAndFlush(beneficiaryRelationship);

        // Get all the beneficiaryRelationshipList
        restBeneficiaryRelationshipMockMvc.perform(get("/api/beneficiary-relationships?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(beneficiaryRelationship.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getBeneficiaryRelationship() throws Exception {
        // Initialize the database
        beneficiaryRelationshipRepository.saveAndFlush(beneficiaryRelationship);

        // Get the beneficiaryRelationship
        restBeneficiaryRelationshipMockMvc.perform(get("/api/beneficiary-relationships/{id}", beneficiaryRelationship.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(beneficiaryRelationship.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingBeneficiaryRelationship() throws Exception {
        // Get the beneficiaryRelationship
        restBeneficiaryRelationshipMockMvc.perform(get("/api/beneficiary-relationships/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBeneficiaryRelationship() throws Exception {
        // Initialize the database
        beneficiaryRelationshipRepository.saveAndFlush(beneficiaryRelationship);

        int databaseSizeBeforeUpdate = beneficiaryRelationshipRepository.findAll().size();

        // Update the beneficiaryRelationship
        BeneficiaryRelationship updatedBeneficiaryRelationship = beneficiaryRelationshipRepository.findById(beneficiaryRelationship.getId()).get();
        // Disconnect from session so that the updates on updatedBeneficiaryRelationship are not directly saved in db
        em.detach(updatedBeneficiaryRelationship);
        updatedBeneficiaryRelationship
            .code(UPDATED_CODE)
            .label(UPDATED_LABEL)
            .active(UPDATED_ACTIVE);
        BeneficiaryRelationshipDTO beneficiaryRelationshipDTO = beneficiaryRelationshipMapper.toDto(updatedBeneficiaryRelationship);

        restBeneficiaryRelationshipMockMvc.perform(put("/api/beneficiary-relationships")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(beneficiaryRelationshipDTO)))
            .andExpect(status().isOk());

        // Validate the BeneficiaryRelationship in the database
        List<BeneficiaryRelationship> beneficiaryRelationshipList = beneficiaryRelationshipRepository.findAll();
        assertThat(beneficiaryRelationshipList).hasSize(databaseSizeBeforeUpdate);
        BeneficiaryRelationship testBeneficiaryRelationship = beneficiaryRelationshipList.get(beneficiaryRelationshipList.size() - 1);
        assertThat(testBeneficiaryRelationship.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testBeneficiaryRelationship.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testBeneficiaryRelationship.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingBeneficiaryRelationship() throws Exception {
        int databaseSizeBeforeUpdate = beneficiaryRelationshipRepository.findAll().size();

        // Create the BeneficiaryRelationship
        BeneficiaryRelationshipDTO beneficiaryRelationshipDTO = beneficiaryRelationshipMapper.toDto(beneficiaryRelationship);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBeneficiaryRelationshipMockMvc.perform(put("/api/beneficiary-relationships")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(beneficiaryRelationshipDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BeneficiaryRelationship in the database
        List<BeneficiaryRelationship> beneficiaryRelationshipList = beneficiaryRelationshipRepository.findAll();
        assertThat(beneficiaryRelationshipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBeneficiaryRelationship() throws Exception {
        // Initialize the database
        beneficiaryRelationshipRepository.saveAndFlush(beneficiaryRelationship);

        int databaseSizeBeforeDelete = beneficiaryRelationshipRepository.findAll().size();

        // Delete the beneficiaryRelationship
        restBeneficiaryRelationshipMockMvc.perform(delete("/api/beneficiary-relationships/{id}", beneficiaryRelationship.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BeneficiaryRelationship> beneficiaryRelationshipList = beneficiaryRelationshipRepository.findAll();
        assertThat(beneficiaryRelationshipList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BeneficiaryRelationship.class);
        BeneficiaryRelationship beneficiaryRelationship1 = new BeneficiaryRelationship();
        beneficiaryRelationship1.setId(1L);
        BeneficiaryRelationship beneficiaryRelationship2 = new BeneficiaryRelationship();
        beneficiaryRelationship2.setId(beneficiaryRelationship1.getId());
        assertThat(beneficiaryRelationship1).isEqualTo(beneficiaryRelationship2);
        beneficiaryRelationship2.setId(2L);
        assertThat(beneficiaryRelationship1).isNotEqualTo(beneficiaryRelationship2);
        beneficiaryRelationship1.setId(null);
        assertThat(beneficiaryRelationship1).isNotEqualTo(beneficiaryRelationship2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BeneficiaryRelationshipDTO.class);
        BeneficiaryRelationshipDTO beneficiaryRelationshipDTO1 = new BeneficiaryRelationshipDTO();
        beneficiaryRelationshipDTO1.setId(1L);
        BeneficiaryRelationshipDTO beneficiaryRelationshipDTO2 = new BeneficiaryRelationshipDTO();
        assertThat(beneficiaryRelationshipDTO1).isNotEqualTo(beneficiaryRelationshipDTO2);
        beneficiaryRelationshipDTO2.setId(beneficiaryRelationshipDTO1.getId());
        assertThat(beneficiaryRelationshipDTO1).isEqualTo(beneficiaryRelationshipDTO2);
        beneficiaryRelationshipDTO2.setId(2L);
        assertThat(beneficiaryRelationshipDTO1).isNotEqualTo(beneficiaryRelationshipDTO2);
        beneficiaryRelationshipDTO1.setId(null);
        assertThat(beneficiaryRelationshipDTO1).isNotEqualTo(beneficiaryRelationshipDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(beneficiaryRelationshipMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(beneficiaryRelationshipMapper.fromId(null)).isNull();
    }
}
