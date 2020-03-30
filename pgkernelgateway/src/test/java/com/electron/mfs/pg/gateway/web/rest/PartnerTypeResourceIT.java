package com.electron.mfs.pg.gateway.web.rest;

import com.electron.mfs.pg.gateway.PgkernelgatewayApp;
import com.electron.mfs.pg.gateway.config.SecurityBeanOverrideConfiguration;
import com.electron.mfs.pg.gateway.domain.PartnerType;
import com.electron.mfs.pg.gateway.repository.PartnerTypeRepository;
import com.electron.mfs.pg.gateway.service.PartnerTypeService;
import com.electron.mfs.pg.gateway.service.dto.PartnerTypeDTO;
import com.electron.mfs.pg.gateway.service.mapper.PartnerTypeMapper;
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
 * Integration tests for the {@Link PartnerTypeResource} REST controller.
 */
@EmbeddedKafka
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, PgkernelgatewayApp.class})
public class PartnerTypeResourceIT {

    private static final String DEFAULT_CODE = "AAAAA";
    private static final String UPDATED_CODE = "BBBBB";

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private PartnerTypeRepository partnerTypeRepository;

    @Autowired
    private PartnerTypeMapper partnerTypeMapper;

    @Autowired
    private PartnerTypeService partnerTypeService;

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

    private MockMvc restPartnerTypeMockMvc;

    private PartnerType partnerType;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PartnerTypeResource partnerTypeResource = new PartnerTypeResource(partnerTypeService);
        this.restPartnerTypeMockMvc = MockMvcBuilders.standaloneSetup(partnerTypeResource)
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
    public static PartnerType createEntity(EntityManager em) {
        PartnerType partnerType = new PartnerType()
            .code(DEFAULT_CODE)
            .label(DEFAULT_LABEL)
            .description(DEFAULT_DESCRIPTION)
            .active(DEFAULT_ACTIVE);
        return partnerType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PartnerType createUpdatedEntity(EntityManager em) {
        PartnerType partnerType = new PartnerType()
            .code(UPDATED_CODE)
            .label(UPDATED_LABEL)
            .description(UPDATED_DESCRIPTION)
            .active(UPDATED_ACTIVE);
        return partnerType;
    }

    @BeforeEach
    public void initTest() {
        partnerType = createEntity(em);
    }

    @Test
    @Transactional
    public void createPartnerType() throws Exception {
        int databaseSizeBeforeCreate = partnerTypeRepository.findAll().size();

        // Create the PartnerType
        PartnerTypeDTO partnerTypeDTO = partnerTypeMapper.toDto(partnerType);
        restPartnerTypeMockMvc.perform(post("/api/partner-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partnerTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the PartnerType in the database
        List<PartnerType> partnerTypeList = partnerTypeRepository.findAll();
        assertThat(partnerTypeList).hasSize(databaseSizeBeforeCreate + 1);
        PartnerType testPartnerType = partnerTypeList.get(partnerTypeList.size() - 1);
        assertThat(testPartnerType.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testPartnerType.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testPartnerType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPartnerType.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createPartnerTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = partnerTypeRepository.findAll().size();

        // Create the PartnerType with an existing ID
        partnerType.setId(1L);
        PartnerTypeDTO partnerTypeDTO = partnerTypeMapper.toDto(partnerType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPartnerTypeMockMvc.perform(post("/api/partner-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partnerTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PartnerType in the database
        List<PartnerType> partnerTypeList = partnerTypeRepository.findAll();
        assertThat(partnerTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = partnerTypeRepository.findAll().size();
        // set the field null
        partnerType.setCode(null);

        // Create the PartnerType, which fails.
        PartnerTypeDTO partnerTypeDTO = partnerTypeMapper.toDto(partnerType);

        restPartnerTypeMockMvc.perform(post("/api/partner-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partnerTypeDTO)))
            .andExpect(status().isBadRequest());

        List<PartnerType> partnerTypeList = partnerTypeRepository.findAll();
        assertThat(partnerTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLabelIsRequired() throws Exception {
        int databaseSizeBeforeTest = partnerTypeRepository.findAll().size();
        // set the field null
        partnerType.setLabel(null);

        // Create the PartnerType, which fails.
        PartnerTypeDTO partnerTypeDTO = partnerTypeMapper.toDto(partnerType);

        restPartnerTypeMockMvc.perform(post("/api/partner-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partnerTypeDTO)))
            .andExpect(status().isBadRequest());

        List<PartnerType> partnerTypeList = partnerTypeRepository.findAll();
        assertThat(partnerTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = partnerTypeRepository.findAll().size();
        // set the field null
        partnerType.setActive(null);

        // Create the PartnerType, which fails.
        PartnerTypeDTO partnerTypeDTO = partnerTypeMapper.toDto(partnerType);

        restPartnerTypeMockMvc.perform(post("/api/partner-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partnerTypeDTO)))
            .andExpect(status().isBadRequest());

        List<PartnerType> partnerTypeList = partnerTypeRepository.findAll();
        assertThat(partnerTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPartnerTypes() throws Exception {
        // Initialize the database
        partnerTypeRepository.saveAndFlush(partnerType);

        // Get all the partnerTypeList
        restPartnerTypeMockMvc.perform(get("/api/partner-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(partnerType.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getPartnerType() throws Exception {
        // Initialize the database
        partnerTypeRepository.saveAndFlush(partnerType);

        // Get the partnerType
        restPartnerTypeMockMvc.perform(get("/api/partner-types/{id}", partnerType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(partnerType.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPartnerType() throws Exception {
        // Get the partnerType
        restPartnerTypeMockMvc.perform(get("/api/partner-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePartnerType() throws Exception {
        // Initialize the database
        partnerTypeRepository.saveAndFlush(partnerType);

        int databaseSizeBeforeUpdate = partnerTypeRepository.findAll().size();

        // Update the partnerType
        PartnerType updatedPartnerType = partnerTypeRepository.findById(partnerType.getId()).get();
        // Disconnect from session so that the updates on updatedPartnerType are not directly saved in db
        em.detach(updatedPartnerType);
        updatedPartnerType
            .code(UPDATED_CODE)
            .label(UPDATED_LABEL)
            .description(UPDATED_DESCRIPTION)
            .active(UPDATED_ACTIVE);
        PartnerTypeDTO partnerTypeDTO = partnerTypeMapper.toDto(updatedPartnerType);

        restPartnerTypeMockMvc.perform(put("/api/partner-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partnerTypeDTO)))
            .andExpect(status().isOk());

        // Validate the PartnerType in the database
        List<PartnerType> partnerTypeList = partnerTypeRepository.findAll();
        assertThat(partnerTypeList).hasSize(databaseSizeBeforeUpdate);
        PartnerType testPartnerType = partnerTypeList.get(partnerTypeList.size() - 1);
        assertThat(testPartnerType.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testPartnerType.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testPartnerType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPartnerType.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingPartnerType() throws Exception {
        int databaseSizeBeforeUpdate = partnerTypeRepository.findAll().size();

        // Create the PartnerType
        PartnerTypeDTO partnerTypeDTO = partnerTypeMapper.toDto(partnerType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPartnerTypeMockMvc.perform(put("/api/partner-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partnerTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PartnerType in the database
        List<PartnerType> partnerTypeList = partnerTypeRepository.findAll();
        assertThat(partnerTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePartnerType() throws Exception {
        // Initialize the database
        partnerTypeRepository.saveAndFlush(partnerType);

        int databaseSizeBeforeDelete = partnerTypeRepository.findAll().size();

        // Delete the partnerType
        restPartnerTypeMockMvc.perform(delete("/api/partner-types/{id}", partnerType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PartnerType> partnerTypeList = partnerTypeRepository.findAll();
        assertThat(partnerTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PartnerType.class);
        PartnerType partnerType1 = new PartnerType();
        partnerType1.setId(1L);
        PartnerType partnerType2 = new PartnerType();
        partnerType2.setId(partnerType1.getId());
        assertThat(partnerType1).isEqualTo(partnerType2);
        partnerType2.setId(2L);
        assertThat(partnerType1).isNotEqualTo(partnerType2);
        partnerType1.setId(null);
        assertThat(partnerType1).isNotEqualTo(partnerType2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PartnerTypeDTO.class);
        PartnerTypeDTO partnerTypeDTO1 = new PartnerTypeDTO();
        partnerTypeDTO1.setId(1L);
        PartnerTypeDTO partnerTypeDTO2 = new PartnerTypeDTO();
        assertThat(partnerTypeDTO1).isNotEqualTo(partnerTypeDTO2);
        partnerTypeDTO2.setId(partnerTypeDTO1.getId());
        assertThat(partnerTypeDTO1).isEqualTo(partnerTypeDTO2);
        partnerTypeDTO2.setId(2L);
        assertThat(partnerTypeDTO1).isNotEqualTo(partnerTypeDTO2);
        partnerTypeDTO1.setId(null);
        assertThat(partnerTypeDTO1).isNotEqualTo(partnerTypeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(partnerTypeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(partnerTypeMapper.fromId(null)).isNull();
    }
}
