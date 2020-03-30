package com.electron.mfs.pg.services.web.rest;

import com.electron.mfs.pg.services.ServicesManagerApp;
import com.electron.mfs.pg.services.config.SecurityBeanOverrideConfiguration;
import com.electron.mfs.pg.services.domain.ConnectorType;
import com.electron.mfs.pg.services.repository.ConnectorTypeRepository;
import com.electron.mfs.pg.services.service.ConnectorTypeService;
import com.electron.mfs.pg.services.service.dto.ConnectorTypeDTO;
import com.electron.mfs.pg.services.service.mapper.ConnectorTypeMapper;
import com.electron.mfs.pg.services.web.rest.errors.ExceptionTranslator;

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

import static com.electron.mfs.pg.services.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link ConnectorTypeResource} REST controller.
 */
@EmbeddedKafka
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, ServicesManagerApp.class})
public class ConnectorTypeResourceIT {

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private ConnectorTypeRepository connectorTypeRepository;

    @Autowired
    private ConnectorTypeMapper connectorTypeMapper;

    @Autowired
    private ConnectorTypeService connectorTypeService;

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

    private MockMvc restConnectorTypeMockMvc;

    private ConnectorType connectorType;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ConnectorTypeResource connectorTypeResource = new ConnectorTypeResource(connectorTypeService);
        this.restConnectorTypeMockMvc = MockMvcBuilders.standaloneSetup(connectorTypeResource)
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
    public static ConnectorType createEntity(EntityManager em) {
        ConnectorType connectorType = new ConnectorType()
            .label(DEFAULT_LABEL)
            .description(DEFAULT_DESCRIPTION)
            .active(DEFAULT_ACTIVE);
        return connectorType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConnectorType createUpdatedEntity(EntityManager em) {
        ConnectorType connectorType = new ConnectorType()
            .label(UPDATED_LABEL)
            .description(UPDATED_DESCRIPTION)
            .active(UPDATED_ACTIVE);
        return connectorType;
    }

    @BeforeEach
    public void initTest() {
        connectorType = createEntity(em);
    }

    @Test
    @Transactional
    public void createConnectorType() throws Exception {
        int databaseSizeBeforeCreate = connectorTypeRepository.findAll().size();

        // Create the ConnectorType
        ConnectorTypeDTO connectorTypeDTO = connectorTypeMapper.toDto(connectorType);
        restConnectorTypeMockMvc.perform(post("/api/connector-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(connectorTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the ConnectorType in the database
        List<ConnectorType> connectorTypeList = connectorTypeRepository.findAll();
        assertThat(connectorTypeList).hasSize(databaseSizeBeforeCreate + 1);
        ConnectorType testConnectorType = connectorTypeList.get(connectorTypeList.size() - 1);
        assertThat(testConnectorType.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testConnectorType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testConnectorType.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createConnectorTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = connectorTypeRepository.findAll().size();

        // Create the ConnectorType with an existing ID
        connectorType.setId(1L);
        ConnectorTypeDTO connectorTypeDTO = connectorTypeMapper.toDto(connectorType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConnectorTypeMockMvc.perform(post("/api/connector-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(connectorTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ConnectorType in the database
        List<ConnectorType> connectorTypeList = connectorTypeRepository.findAll();
        assertThat(connectorTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkLabelIsRequired() throws Exception {
        int databaseSizeBeforeTest = connectorTypeRepository.findAll().size();
        // set the field null
        connectorType.setLabel(null);

        // Create the ConnectorType, which fails.
        ConnectorTypeDTO connectorTypeDTO = connectorTypeMapper.toDto(connectorType);

        restConnectorTypeMockMvc.perform(post("/api/connector-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(connectorTypeDTO)))
            .andExpect(status().isBadRequest());

        List<ConnectorType> connectorTypeList = connectorTypeRepository.findAll();
        assertThat(connectorTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = connectorTypeRepository.findAll().size();
        // set the field null
        connectorType.setActive(null);

        // Create the ConnectorType, which fails.
        ConnectorTypeDTO connectorTypeDTO = connectorTypeMapper.toDto(connectorType);

        restConnectorTypeMockMvc.perform(post("/api/connector-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(connectorTypeDTO)))
            .andExpect(status().isBadRequest());

        List<ConnectorType> connectorTypeList = connectorTypeRepository.findAll();
        assertThat(connectorTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllConnectorTypes() throws Exception {
        // Initialize the database
        connectorTypeRepository.saveAndFlush(connectorType);

        // Get all the connectorTypeList
        restConnectorTypeMockMvc.perform(get("/api/connector-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(connectorType.getId().intValue())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getConnectorType() throws Exception {
        // Initialize the database
        connectorTypeRepository.saveAndFlush(connectorType);

        // Get the connectorType
        restConnectorTypeMockMvc.perform(get("/api/connector-types/{id}", connectorType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(connectorType.getId().intValue()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingConnectorType() throws Exception {
        // Get the connectorType
        restConnectorTypeMockMvc.perform(get("/api/connector-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConnectorType() throws Exception {
        // Initialize the database
        connectorTypeRepository.saveAndFlush(connectorType);

        int databaseSizeBeforeUpdate = connectorTypeRepository.findAll().size();

        // Update the connectorType
        ConnectorType updatedConnectorType = connectorTypeRepository.findById(connectorType.getId()).get();
        // Disconnect from session so that the updates on updatedConnectorType are not directly saved in db
        em.detach(updatedConnectorType);
        updatedConnectorType
            .label(UPDATED_LABEL)
            .description(UPDATED_DESCRIPTION)
            .active(UPDATED_ACTIVE);
        ConnectorTypeDTO connectorTypeDTO = connectorTypeMapper.toDto(updatedConnectorType);

        restConnectorTypeMockMvc.perform(put("/api/connector-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(connectorTypeDTO)))
            .andExpect(status().isOk());

        // Validate the ConnectorType in the database
        List<ConnectorType> connectorTypeList = connectorTypeRepository.findAll();
        assertThat(connectorTypeList).hasSize(databaseSizeBeforeUpdate);
        ConnectorType testConnectorType = connectorTypeList.get(connectorTypeList.size() - 1);
        assertThat(testConnectorType.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testConnectorType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testConnectorType.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingConnectorType() throws Exception {
        int databaseSizeBeforeUpdate = connectorTypeRepository.findAll().size();

        // Create the ConnectorType
        ConnectorTypeDTO connectorTypeDTO = connectorTypeMapper.toDto(connectorType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConnectorTypeMockMvc.perform(put("/api/connector-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(connectorTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ConnectorType in the database
        List<ConnectorType> connectorTypeList = connectorTypeRepository.findAll();
        assertThat(connectorTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteConnectorType() throws Exception {
        // Initialize the database
        connectorTypeRepository.saveAndFlush(connectorType);

        int databaseSizeBeforeDelete = connectorTypeRepository.findAll().size();

        // Delete the connectorType
        restConnectorTypeMockMvc.perform(delete("/api/connector-types/{id}", connectorType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ConnectorType> connectorTypeList = connectorTypeRepository.findAll();
        assertThat(connectorTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConnectorType.class);
        ConnectorType connectorType1 = new ConnectorType();
        connectorType1.setId(1L);
        ConnectorType connectorType2 = new ConnectorType();
        connectorType2.setId(connectorType1.getId());
        assertThat(connectorType1).isEqualTo(connectorType2);
        connectorType2.setId(2L);
        assertThat(connectorType1).isNotEqualTo(connectorType2);
        connectorType1.setId(null);
        assertThat(connectorType1).isNotEqualTo(connectorType2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConnectorTypeDTO.class);
        ConnectorTypeDTO connectorTypeDTO1 = new ConnectorTypeDTO();
        connectorTypeDTO1.setId(1L);
        ConnectorTypeDTO connectorTypeDTO2 = new ConnectorTypeDTO();
        assertThat(connectorTypeDTO1).isNotEqualTo(connectorTypeDTO2);
        connectorTypeDTO2.setId(connectorTypeDTO1.getId());
        assertThat(connectorTypeDTO1).isEqualTo(connectorTypeDTO2);
        connectorTypeDTO2.setId(2L);
        assertThat(connectorTypeDTO1).isNotEqualTo(connectorTypeDTO2);
        connectorTypeDTO1.setId(null);
        assertThat(connectorTypeDTO1).isNotEqualTo(connectorTypeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(connectorTypeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(connectorTypeMapper.fromId(null)).isNull();
    }
}
