package com.electron.mfs.pg.agent.web.rest;

import com.electron.mfs.pg.agent.AgentManagerApp;
import com.electron.mfs.pg.agent.config.SecurityBeanOverrideConfiguration;
import com.electron.mfs.pg.agent.domain.AgentType;
import com.electron.mfs.pg.agent.repository.AgentTypeRepository;
import com.electron.mfs.pg.agent.service.AgentTypeService;
import com.electron.mfs.pg.agent.service.dto.AgentTypeDTO;
import com.electron.mfs.pg.agent.service.mapper.AgentTypeMapper;
import com.electron.mfs.pg.agent.web.rest.errors.ExceptionTranslator;

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

import static com.electron.mfs.pg.agent.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link AgentTypeResource} REST controller.
 */
@EmbeddedKafka
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, AgentManagerApp.class})
public class AgentTypeResourceIT {

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private AgentTypeRepository agentTypeRepository;

    @Autowired
    private AgentTypeMapper agentTypeMapper;

    @Autowired
    private AgentTypeService agentTypeService;

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

    private MockMvc restAgentTypeMockMvc;

    private AgentType agentType;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AgentTypeResource agentTypeResource = new AgentTypeResource(agentTypeService);
        this.restAgentTypeMockMvc = MockMvcBuilders.standaloneSetup(agentTypeResource)
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
    public static AgentType createEntity(EntityManager em) {
        AgentType agentType = new AgentType()
            .label(DEFAULT_LABEL)
            .active(DEFAULT_ACTIVE);
        return agentType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AgentType createUpdatedEntity(EntityManager em) {
        AgentType agentType = new AgentType()
            .label(UPDATED_LABEL)
            .active(UPDATED_ACTIVE);
        return agentType;
    }

    @BeforeEach
    public void initTest() {
        agentType = createEntity(em);
    }

    @Test
    @Transactional
    public void createAgentType() throws Exception {
        int databaseSizeBeforeCreate = agentTypeRepository.findAll().size();

        // Create the AgentType
        AgentTypeDTO agentTypeDTO = agentTypeMapper.toDto(agentType);
        restAgentTypeMockMvc.perform(post("/api/agent-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agentTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the AgentType in the database
        List<AgentType> agentTypeList = agentTypeRepository.findAll();
        assertThat(agentTypeList).hasSize(databaseSizeBeforeCreate + 1);
        AgentType testAgentType = agentTypeList.get(agentTypeList.size() - 1);
        assertThat(testAgentType.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testAgentType.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createAgentTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = agentTypeRepository.findAll().size();

        // Create the AgentType with an existing ID
        agentType.setId(1L);
        AgentTypeDTO agentTypeDTO = agentTypeMapper.toDto(agentType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAgentTypeMockMvc.perform(post("/api/agent-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agentTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AgentType in the database
        List<AgentType> agentTypeList = agentTypeRepository.findAll();
        assertThat(agentTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkLabelIsRequired() throws Exception {
        int databaseSizeBeforeTest = agentTypeRepository.findAll().size();
        // set the field null
        agentType.setLabel(null);

        // Create the AgentType, which fails.
        AgentTypeDTO agentTypeDTO = agentTypeMapper.toDto(agentType);

        restAgentTypeMockMvc.perform(post("/api/agent-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agentTypeDTO)))
            .andExpect(status().isBadRequest());

        List<AgentType> agentTypeList = agentTypeRepository.findAll();
        assertThat(agentTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = agentTypeRepository.findAll().size();
        // set the field null
        agentType.setActive(null);

        // Create the AgentType, which fails.
        AgentTypeDTO agentTypeDTO = agentTypeMapper.toDto(agentType);

        restAgentTypeMockMvc.perform(post("/api/agent-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agentTypeDTO)))
            .andExpect(status().isBadRequest());

        List<AgentType> agentTypeList = agentTypeRepository.findAll();
        assertThat(agentTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAgentTypes() throws Exception {
        // Initialize the database
        agentTypeRepository.saveAndFlush(agentType);

        // Get all the agentTypeList
        restAgentTypeMockMvc.perform(get("/api/agent-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(agentType.getId().intValue())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getAgentType() throws Exception {
        // Initialize the database
        agentTypeRepository.saveAndFlush(agentType);

        // Get the agentType
        restAgentTypeMockMvc.perform(get("/api/agent-types/{id}", agentType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(agentType.getId().intValue()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAgentType() throws Exception {
        // Get the agentType
        restAgentTypeMockMvc.perform(get("/api/agent-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAgentType() throws Exception {
        // Initialize the database
        agentTypeRepository.saveAndFlush(agentType);

        int databaseSizeBeforeUpdate = agentTypeRepository.findAll().size();

        // Update the agentType
        AgentType updatedAgentType = agentTypeRepository.findById(agentType.getId()).get();
        // Disconnect from session so that the updates on updatedAgentType are not directly saved in db
        em.detach(updatedAgentType);
        updatedAgentType
            .label(UPDATED_LABEL)
            .active(UPDATED_ACTIVE);
        AgentTypeDTO agentTypeDTO = agentTypeMapper.toDto(updatedAgentType);

        restAgentTypeMockMvc.perform(put("/api/agent-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agentTypeDTO)))
            .andExpect(status().isOk());

        // Validate the AgentType in the database
        List<AgentType> agentTypeList = agentTypeRepository.findAll();
        assertThat(agentTypeList).hasSize(databaseSizeBeforeUpdate);
        AgentType testAgentType = agentTypeList.get(agentTypeList.size() - 1);
        assertThat(testAgentType.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testAgentType.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingAgentType() throws Exception {
        int databaseSizeBeforeUpdate = agentTypeRepository.findAll().size();

        // Create the AgentType
        AgentTypeDTO agentTypeDTO = agentTypeMapper.toDto(agentType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAgentTypeMockMvc.perform(put("/api/agent-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agentTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AgentType in the database
        List<AgentType> agentTypeList = agentTypeRepository.findAll();
        assertThat(agentTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAgentType() throws Exception {
        // Initialize the database
        agentTypeRepository.saveAndFlush(agentType);

        int databaseSizeBeforeDelete = agentTypeRepository.findAll().size();

        // Delete the agentType
        restAgentTypeMockMvc.perform(delete("/api/agent-types/{id}", agentType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AgentType> agentTypeList = agentTypeRepository.findAll();
        assertThat(agentTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AgentType.class);
        AgentType agentType1 = new AgentType();
        agentType1.setId(1L);
        AgentType agentType2 = new AgentType();
        agentType2.setId(agentType1.getId());
        assertThat(agentType1).isEqualTo(agentType2);
        agentType2.setId(2L);
        assertThat(agentType1).isNotEqualTo(agentType2);
        agentType1.setId(null);
        assertThat(agentType1).isNotEqualTo(agentType2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AgentTypeDTO.class);
        AgentTypeDTO agentTypeDTO1 = new AgentTypeDTO();
        agentTypeDTO1.setId(1L);
        AgentTypeDTO agentTypeDTO2 = new AgentTypeDTO();
        assertThat(agentTypeDTO1).isNotEqualTo(agentTypeDTO2);
        agentTypeDTO2.setId(agentTypeDTO1.getId());
        assertThat(agentTypeDTO1).isEqualTo(agentTypeDTO2);
        agentTypeDTO2.setId(2L);
        assertThat(agentTypeDTO1).isNotEqualTo(agentTypeDTO2);
        agentTypeDTO1.setId(null);
        assertThat(agentTypeDTO1).isNotEqualTo(agentTypeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(agentTypeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(agentTypeMapper.fromId(null)).isNull();
    }
}
