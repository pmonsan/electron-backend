package com.electron.mfs.pg.account.web.rest;

import com.electron.mfs.pg.account.AccountManagerApp;
import com.electron.mfs.pg.account.config.SecurityBeanOverrideConfiguration;
import com.electron.mfs.pg.account.domain.ContractOpposition;
import com.electron.mfs.pg.account.repository.ContractOppositionRepository;
import com.electron.mfs.pg.account.service.ContractOppositionService;
import com.electron.mfs.pg.account.service.dto.ContractOppositionDTO;
import com.electron.mfs.pg.account.service.mapper.ContractOppositionMapper;
import com.electron.mfs.pg.account.web.rest.errors.ExceptionTranslator;

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

import static com.electron.mfs.pg.account.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link ContractOppositionResource} REST controller.
 */
@EmbeddedKafka
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, AccountManagerApp.class})
public class ContractOppositionResourceIT {

    private static final String DEFAULT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_NUMBER = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_CUSTOMER_INITIATIVE = false;
    private static final Boolean UPDATED_IS_CUSTOMER_INITIATIVE = true;

    private static final Instant DEFAULT_OPPOSITION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_OPPOSITION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_OPPOSITION_REASON = "AAAAAAAAAA";
    private static final String UPDATED_OPPOSITION_REASON = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private ContractOppositionRepository contractOppositionRepository;

    @Autowired
    private ContractOppositionMapper contractOppositionMapper;

    @Autowired
    private ContractOppositionService contractOppositionService;

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

    private MockMvc restContractOppositionMockMvc;

    private ContractOpposition contractOpposition;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ContractOppositionResource contractOppositionResource = new ContractOppositionResource(contractOppositionService);
        this.restContractOppositionMockMvc = MockMvcBuilders.standaloneSetup(contractOppositionResource)
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
    public static ContractOpposition createEntity(EntityManager em) {
        ContractOpposition contractOpposition = new ContractOpposition()
            .number(DEFAULT_NUMBER)
            .isCustomerInitiative(DEFAULT_IS_CUSTOMER_INITIATIVE)
            .oppositionDate(DEFAULT_OPPOSITION_DATE)
            .oppositionReason(DEFAULT_OPPOSITION_REASON)
            .comment(DEFAULT_COMMENT)
            .active(DEFAULT_ACTIVE);
        return contractOpposition;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContractOpposition createUpdatedEntity(EntityManager em) {
        ContractOpposition contractOpposition = new ContractOpposition()
            .number(UPDATED_NUMBER)
            .isCustomerInitiative(UPDATED_IS_CUSTOMER_INITIATIVE)
            .oppositionDate(UPDATED_OPPOSITION_DATE)
            .oppositionReason(UPDATED_OPPOSITION_REASON)
            .comment(UPDATED_COMMENT)
            .active(UPDATED_ACTIVE);
        return contractOpposition;
    }

    @BeforeEach
    public void initTest() {
        contractOpposition = createEntity(em);
    }

    @Test
    @Transactional
    public void createContractOpposition() throws Exception {
        int databaseSizeBeforeCreate = contractOppositionRepository.findAll().size();

        // Create the ContractOpposition
        ContractOppositionDTO contractOppositionDTO = contractOppositionMapper.toDto(contractOpposition);
        restContractOppositionMockMvc.perform(post("/api/contract-oppositions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contractOppositionDTO)))
            .andExpect(status().isCreated());

        // Validate the ContractOpposition in the database
        List<ContractOpposition> contractOppositionList = contractOppositionRepository.findAll();
        assertThat(contractOppositionList).hasSize(databaseSizeBeforeCreate + 1);
        ContractOpposition testContractOpposition = contractOppositionList.get(contractOppositionList.size() - 1);
        assertThat(testContractOpposition.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testContractOpposition.isIsCustomerInitiative()).isEqualTo(DEFAULT_IS_CUSTOMER_INITIATIVE);
        assertThat(testContractOpposition.getOppositionDate()).isEqualTo(DEFAULT_OPPOSITION_DATE);
        assertThat(testContractOpposition.getOppositionReason()).isEqualTo(DEFAULT_OPPOSITION_REASON);
        assertThat(testContractOpposition.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testContractOpposition.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createContractOppositionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contractOppositionRepository.findAll().size();

        // Create the ContractOpposition with an existing ID
        contractOpposition.setId(1L);
        ContractOppositionDTO contractOppositionDTO = contractOppositionMapper.toDto(contractOpposition);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContractOppositionMockMvc.perform(post("/api/contract-oppositions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contractOppositionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ContractOpposition in the database
        List<ContractOpposition> contractOppositionList = contractOppositionRepository.findAll();
        assertThat(contractOppositionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkIsCustomerInitiativeIsRequired() throws Exception {
        int databaseSizeBeforeTest = contractOppositionRepository.findAll().size();
        // set the field null
        contractOpposition.setIsCustomerInitiative(null);

        // Create the ContractOpposition, which fails.
        ContractOppositionDTO contractOppositionDTO = contractOppositionMapper.toDto(contractOpposition);

        restContractOppositionMockMvc.perform(post("/api/contract-oppositions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contractOppositionDTO)))
            .andExpect(status().isBadRequest());

        List<ContractOpposition> contractOppositionList = contractOppositionRepository.findAll();
        assertThat(contractOppositionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOppositionDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = contractOppositionRepository.findAll().size();
        // set the field null
        contractOpposition.setOppositionDate(null);

        // Create the ContractOpposition, which fails.
        ContractOppositionDTO contractOppositionDTO = contractOppositionMapper.toDto(contractOpposition);

        restContractOppositionMockMvc.perform(post("/api/contract-oppositions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contractOppositionDTO)))
            .andExpect(status().isBadRequest());

        List<ContractOpposition> contractOppositionList = contractOppositionRepository.findAll();
        assertThat(contractOppositionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = contractOppositionRepository.findAll().size();
        // set the field null
        contractOpposition.setActive(null);

        // Create the ContractOpposition, which fails.
        ContractOppositionDTO contractOppositionDTO = contractOppositionMapper.toDto(contractOpposition);

        restContractOppositionMockMvc.perform(post("/api/contract-oppositions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contractOppositionDTO)))
            .andExpect(status().isBadRequest());

        List<ContractOpposition> contractOppositionList = contractOppositionRepository.findAll();
        assertThat(contractOppositionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllContractOppositions() throws Exception {
        // Initialize the database
        contractOppositionRepository.saveAndFlush(contractOpposition);

        // Get all the contractOppositionList
        restContractOppositionMockMvc.perform(get("/api/contract-oppositions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contractOpposition.getId().intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].isCustomerInitiative").value(hasItem(DEFAULT_IS_CUSTOMER_INITIATIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].oppositionDate").value(hasItem(DEFAULT_OPPOSITION_DATE.toString())))
            .andExpect(jsonPath("$.[*].oppositionReason").value(hasItem(DEFAULT_OPPOSITION_REASON.toString())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getContractOpposition() throws Exception {
        // Initialize the database
        contractOppositionRepository.saveAndFlush(contractOpposition);

        // Get the contractOpposition
        restContractOppositionMockMvc.perform(get("/api/contract-oppositions/{id}", contractOpposition.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(contractOpposition.getId().intValue()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER.toString()))
            .andExpect(jsonPath("$.isCustomerInitiative").value(DEFAULT_IS_CUSTOMER_INITIATIVE.booleanValue()))
            .andExpect(jsonPath("$.oppositionDate").value(DEFAULT_OPPOSITION_DATE.toString()))
            .andExpect(jsonPath("$.oppositionReason").value(DEFAULT_OPPOSITION_REASON.toString()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingContractOpposition() throws Exception {
        // Get the contractOpposition
        restContractOppositionMockMvc.perform(get("/api/contract-oppositions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContractOpposition() throws Exception {
        // Initialize the database
        contractOppositionRepository.saveAndFlush(contractOpposition);

        int databaseSizeBeforeUpdate = contractOppositionRepository.findAll().size();

        // Update the contractOpposition
        ContractOpposition updatedContractOpposition = contractOppositionRepository.findById(contractOpposition.getId()).get();
        // Disconnect from session so that the updates on updatedContractOpposition are not directly saved in db
        em.detach(updatedContractOpposition);
        updatedContractOpposition
            .number(UPDATED_NUMBER)
            .isCustomerInitiative(UPDATED_IS_CUSTOMER_INITIATIVE)
            .oppositionDate(UPDATED_OPPOSITION_DATE)
            .oppositionReason(UPDATED_OPPOSITION_REASON)
            .comment(UPDATED_COMMENT)
            .active(UPDATED_ACTIVE);
        ContractOppositionDTO contractOppositionDTO = contractOppositionMapper.toDto(updatedContractOpposition);

        restContractOppositionMockMvc.perform(put("/api/contract-oppositions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contractOppositionDTO)))
            .andExpect(status().isOk());

        // Validate the ContractOpposition in the database
        List<ContractOpposition> contractOppositionList = contractOppositionRepository.findAll();
        assertThat(contractOppositionList).hasSize(databaseSizeBeforeUpdate);
        ContractOpposition testContractOpposition = contractOppositionList.get(contractOppositionList.size() - 1);
        assertThat(testContractOpposition.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testContractOpposition.isIsCustomerInitiative()).isEqualTo(UPDATED_IS_CUSTOMER_INITIATIVE);
        assertThat(testContractOpposition.getOppositionDate()).isEqualTo(UPDATED_OPPOSITION_DATE);
        assertThat(testContractOpposition.getOppositionReason()).isEqualTo(UPDATED_OPPOSITION_REASON);
        assertThat(testContractOpposition.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testContractOpposition.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingContractOpposition() throws Exception {
        int databaseSizeBeforeUpdate = contractOppositionRepository.findAll().size();

        // Create the ContractOpposition
        ContractOppositionDTO contractOppositionDTO = contractOppositionMapper.toDto(contractOpposition);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContractOppositionMockMvc.perform(put("/api/contract-oppositions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contractOppositionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ContractOpposition in the database
        List<ContractOpposition> contractOppositionList = contractOppositionRepository.findAll();
        assertThat(contractOppositionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteContractOpposition() throws Exception {
        // Initialize the database
        contractOppositionRepository.saveAndFlush(contractOpposition);

        int databaseSizeBeforeDelete = contractOppositionRepository.findAll().size();

        // Delete the contractOpposition
        restContractOppositionMockMvc.perform(delete("/api/contract-oppositions/{id}", contractOpposition.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ContractOpposition> contractOppositionList = contractOppositionRepository.findAll();
        assertThat(contractOppositionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContractOpposition.class);
        ContractOpposition contractOpposition1 = new ContractOpposition();
        contractOpposition1.setId(1L);
        ContractOpposition contractOpposition2 = new ContractOpposition();
        contractOpposition2.setId(contractOpposition1.getId());
        assertThat(contractOpposition1).isEqualTo(contractOpposition2);
        contractOpposition2.setId(2L);
        assertThat(contractOpposition1).isNotEqualTo(contractOpposition2);
        contractOpposition1.setId(null);
        assertThat(contractOpposition1).isNotEqualTo(contractOpposition2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContractOppositionDTO.class);
        ContractOppositionDTO contractOppositionDTO1 = new ContractOppositionDTO();
        contractOppositionDTO1.setId(1L);
        ContractOppositionDTO contractOppositionDTO2 = new ContractOppositionDTO();
        assertThat(contractOppositionDTO1).isNotEqualTo(contractOppositionDTO2);
        contractOppositionDTO2.setId(contractOppositionDTO1.getId());
        assertThat(contractOppositionDTO1).isEqualTo(contractOppositionDTO2);
        contractOppositionDTO2.setId(2L);
        assertThat(contractOppositionDTO1).isNotEqualTo(contractOppositionDTO2);
        contractOppositionDTO1.setId(null);
        assertThat(contractOppositionDTO1).isNotEqualTo(contractOppositionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(contractOppositionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(contractOppositionMapper.fromId(null)).isNull();
    }
}
