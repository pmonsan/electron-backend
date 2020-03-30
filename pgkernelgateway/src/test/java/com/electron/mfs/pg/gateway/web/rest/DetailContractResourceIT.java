package com.electron.mfs.pg.gateway.web.rest;

import com.electron.mfs.pg.gateway.PgkernelgatewayApp;
import com.electron.mfs.pg.gateway.config.SecurityBeanOverrideConfiguration;
import com.electron.mfs.pg.gateway.domain.DetailContract;
import com.electron.mfs.pg.gateway.repository.DetailContractRepository;
import com.electron.mfs.pg.gateway.service.DetailContractService;
import com.electron.mfs.pg.gateway.service.dto.DetailContractDTO;
import com.electron.mfs.pg.gateway.service.mapper.DetailContractMapper;
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
 * Integration tests for the {@Link DetailContractResource} REST controller.
 */
@EmbeddedKafka
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, PgkernelgatewayApp.class})
public class DetailContractResourceIT {

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private DetailContractRepository detailContractRepository;

    @Autowired
    private DetailContractMapper detailContractMapper;

    @Autowired
    private DetailContractService detailContractService;

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

    private MockMvc restDetailContractMockMvc;

    private DetailContract detailContract;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DetailContractResource detailContractResource = new DetailContractResource(detailContractService);
        this.restDetailContractMockMvc = MockMvcBuilders.standaloneSetup(detailContractResource)
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
    public static DetailContract createEntity(EntityManager em) {
        DetailContract detailContract = new DetailContract()
            .comment(DEFAULT_COMMENT)
            .active(DEFAULT_ACTIVE);
        return detailContract;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DetailContract createUpdatedEntity(EntityManager em) {
        DetailContract detailContract = new DetailContract()
            .comment(UPDATED_COMMENT)
            .active(UPDATED_ACTIVE);
        return detailContract;
    }

    @BeforeEach
    public void initTest() {
        detailContract = createEntity(em);
    }

    @Test
    @Transactional
    public void createDetailContract() throws Exception {
        int databaseSizeBeforeCreate = detailContractRepository.findAll().size();

        // Create the DetailContract
        DetailContractDTO detailContractDTO = detailContractMapper.toDto(detailContract);
        restDetailContractMockMvc.perform(post("/api/detail-contracts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detailContractDTO)))
            .andExpect(status().isCreated());

        // Validate the DetailContract in the database
        List<DetailContract> detailContractList = detailContractRepository.findAll();
        assertThat(detailContractList).hasSize(databaseSizeBeforeCreate + 1);
        DetailContract testDetailContract = detailContractList.get(detailContractList.size() - 1);
        assertThat(testDetailContract.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testDetailContract.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createDetailContractWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = detailContractRepository.findAll().size();

        // Create the DetailContract with an existing ID
        detailContract.setId(1L);
        DetailContractDTO detailContractDTO = detailContractMapper.toDto(detailContract);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDetailContractMockMvc.perform(post("/api/detail-contracts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detailContractDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DetailContract in the database
        List<DetailContract> detailContractList = detailContractRepository.findAll();
        assertThat(detailContractList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = detailContractRepository.findAll().size();
        // set the field null
        detailContract.setActive(null);

        // Create the DetailContract, which fails.
        DetailContractDTO detailContractDTO = detailContractMapper.toDto(detailContract);

        restDetailContractMockMvc.perform(post("/api/detail-contracts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detailContractDTO)))
            .andExpect(status().isBadRequest());

        List<DetailContract> detailContractList = detailContractRepository.findAll();
        assertThat(detailContractList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDetailContracts() throws Exception {
        // Initialize the database
        detailContractRepository.saveAndFlush(detailContract);

        // Get all the detailContractList
        restDetailContractMockMvc.perform(get("/api/detail-contracts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(detailContract.getId().intValue())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getDetailContract() throws Exception {
        // Initialize the database
        detailContractRepository.saveAndFlush(detailContract);

        // Get the detailContract
        restDetailContractMockMvc.perform(get("/api/detail-contracts/{id}", detailContract.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(detailContract.getId().intValue()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingDetailContract() throws Exception {
        // Get the detailContract
        restDetailContractMockMvc.perform(get("/api/detail-contracts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDetailContract() throws Exception {
        // Initialize the database
        detailContractRepository.saveAndFlush(detailContract);

        int databaseSizeBeforeUpdate = detailContractRepository.findAll().size();

        // Update the detailContract
        DetailContract updatedDetailContract = detailContractRepository.findById(detailContract.getId()).get();
        // Disconnect from session so that the updates on updatedDetailContract are not directly saved in db
        em.detach(updatedDetailContract);
        updatedDetailContract
            .comment(UPDATED_COMMENT)
            .active(UPDATED_ACTIVE);
        DetailContractDTO detailContractDTO = detailContractMapper.toDto(updatedDetailContract);

        restDetailContractMockMvc.perform(put("/api/detail-contracts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detailContractDTO)))
            .andExpect(status().isOk());

        // Validate the DetailContract in the database
        List<DetailContract> detailContractList = detailContractRepository.findAll();
        assertThat(detailContractList).hasSize(databaseSizeBeforeUpdate);
        DetailContract testDetailContract = detailContractList.get(detailContractList.size() - 1);
        assertThat(testDetailContract.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testDetailContract.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingDetailContract() throws Exception {
        int databaseSizeBeforeUpdate = detailContractRepository.findAll().size();

        // Create the DetailContract
        DetailContractDTO detailContractDTO = detailContractMapper.toDto(detailContract);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDetailContractMockMvc.perform(put("/api/detail-contracts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(detailContractDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DetailContract in the database
        List<DetailContract> detailContractList = detailContractRepository.findAll();
        assertThat(detailContractList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDetailContract() throws Exception {
        // Initialize the database
        detailContractRepository.saveAndFlush(detailContract);

        int databaseSizeBeforeDelete = detailContractRepository.findAll().size();

        // Delete the detailContract
        restDetailContractMockMvc.perform(delete("/api/detail-contracts/{id}", detailContract.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DetailContract> detailContractList = detailContractRepository.findAll();
        assertThat(detailContractList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DetailContract.class);
        DetailContract detailContract1 = new DetailContract();
        detailContract1.setId(1L);
        DetailContract detailContract2 = new DetailContract();
        detailContract2.setId(detailContract1.getId());
        assertThat(detailContract1).isEqualTo(detailContract2);
        detailContract2.setId(2L);
        assertThat(detailContract1).isNotEqualTo(detailContract2);
        detailContract1.setId(null);
        assertThat(detailContract1).isNotEqualTo(detailContract2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DetailContractDTO.class);
        DetailContractDTO detailContractDTO1 = new DetailContractDTO();
        detailContractDTO1.setId(1L);
        DetailContractDTO detailContractDTO2 = new DetailContractDTO();
        assertThat(detailContractDTO1).isNotEqualTo(detailContractDTO2);
        detailContractDTO2.setId(detailContractDTO1.getId());
        assertThat(detailContractDTO1).isEqualTo(detailContractDTO2);
        detailContractDTO2.setId(2L);
        assertThat(detailContractDTO1).isNotEqualTo(detailContractDTO2);
        detailContractDTO1.setId(null);
        assertThat(detailContractDTO1).isNotEqualTo(detailContractDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(detailContractMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(detailContractMapper.fromId(null)).isNull();
    }
}
