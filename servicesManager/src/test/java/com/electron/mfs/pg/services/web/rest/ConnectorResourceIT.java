package com.electron.mfs.pg.services.web.rest;

import com.electron.mfs.pg.services.ServicesManagerApp;
import com.electron.mfs.pg.services.config.SecurityBeanOverrideConfiguration;
import com.electron.mfs.pg.services.domain.Connector;
import com.electron.mfs.pg.services.repository.ConnectorRepository;
import com.electron.mfs.pg.services.service.ConnectorService;
import com.electron.mfs.pg.services.service.dto.ConnectorDTO;
import com.electron.mfs.pg.services.service.mapper.ConnectorMapper;
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
 * Integration tests for the {@Link ConnectorResource} REST controller.
 */
@EmbeddedKafka
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, ServicesManagerApp.class})
public class ConnectorResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final String DEFAULT_LOGIC = "AAAAAAAAAA";
    private static final String UPDATED_LOGIC = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    private static final String DEFAULT_PARTNER_CODE = "AAAAA";
    private static final String UPDATED_PARTNER_CODE = "BBBBB";

    private static final String DEFAULT_MEANSOFPAYMENT_TYPE_CODE = "AAAAA";
    private static final String UPDATED_MEANSOFPAYMENT_TYPE_CODE = "BBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private ConnectorRepository connectorRepository;

    @Autowired
    private ConnectorMapper connectorMapper;

    @Autowired
    private ConnectorService connectorService;

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

    private MockMvc restConnectorMockMvc;

    private Connector connector;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ConnectorResource connectorResource = new ConnectorResource(connectorService);
        this.restConnectorMockMvc = MockMvcBuilders.standaloneSetup(connectorResource)
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
    public static Connector createEntity(EntityManager em) {
        Connector connector = new Connector()
            .code(DEFAULT_CODE)
            .label(DEFAULT_LABEL)
            .logic(DEFAULT_LOGIC)
            .comment(DEFAULT_COMMENT)
            .partnerCode(DEFAULT_PARTNER_CODE)
            .meansofpaymentTypeCode(DEFAULT_MEANSOFPAYMENT_TYPE_CODE)
            .active(DEFAULT_ACTIVE);
        return connector;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Connector createUpdatedEntity(EntityManager em) {
        Connector connector = new Connector()
            .code(UPDATED_CODE)
            .label(UPDATED_LABEL)
            .logic(UPDATED_LOGIC)
            .comment(UPDATED_COMMENT)
            .partnerCode(UPDATED_PARTNER_CODE)
            .meansofpaymentTypeCode(UPDATED_MEANSOFPAYMENT_TYPE_CODE)
            .active(UPDATED_ACTIVE);
        return connector;
    }

    @BeforeEach
    public void initTest() {
        connector = createEntity(em);
    }

    @Test
    @Transactional
    public void createConnector() throws Exception {
        int databaseSizeBeforeCreate = connectorRepository.findAll().size();

        // Create the Connector
        ConnectorDTO connectorDTO = connectorMapper.toDto(connector);
        restConnectorMockMvc.perform(post("/api/connectors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(connectorDTO)))
            .andExpect(status().isCreated());

        // Validate the Connector in the database
        List<Connector> connectorList = connectorRepository.findAll();
        assertThat(connectorList).hasSize(databaseSizeBeforeCreate + 1);
        Connector testConnector = connectorList.get(connectorList.size() - 1);
        assertThat(testConnector.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testConnector.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testConnector.getLogic()).isEqualTo(DEFAULT_LOGIC);
        assertThat(testConnector.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testConnector.getPartnerCode()).isEqualTo(DEFAULT_PARTNER_CODE);
        assertThat(testConnector.getMeansofpaymentTypeCode()).isEqualTo(DEFAULT_MEANSOFPAYMENT_TYPE_CODE);
        assertThat(testConnector.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createConnectorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = connectorRepository.findAll().size();

        // Create the Connector with an existing ID
        connector.setId(1L);
        ConnectorDTO connectorDTO = connectorMapper.toDto(connector);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConnectorMockMvc.perform(post("/api/connectors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(connectorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Connector in the database
        List<Connector> connectorList = connectorRepository.findAll();
        assertThat(connectorList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = connectorRepository.findAll().size();
        // set the field null
        connector.setCode(null);

        // Create the Connector, which fails.
        ConnectorDTO connectorDTO = connectorMapper.toDto(connector);

        restConnectorMockMvc.perform(post("/api/connectors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(connectorDTO)))
            .andExpect(status().isBadRequest());

        List<Connector> connectorList = connectorRepository.findAll();
        assertThat(connectorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLabelIsRequired() throws Exception {
        int databaseSizeBeforeTest = connectorRepository.findAll().size();
        // set the field null
        connector.setLabel(null);

        // Create the Connector, which fails.
        ConnectorDTO connectorDTO = connectorMapper.toDto(connector);

        restConnectorMockMvc.perform(post("/api/connectors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(connectorDTO)))
            .andExpect(status().isBadRequest());

        List<Connector> connectorList = connectorRepository.findAll();
        assertThat(connectorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMeansofpaymentTypeCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = connectorRepository.findAll().size();
        // set the field null
        connector.setMeansofpaymentTypeCode(null);

        // Create the Connector, which fails.
        ConnectorDTO connectorDTO = connectorMapper.toDto(connector);

        restConnectorMockMvc.perform(post("/api/connectors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(connectorDTO)))
            .andExpect(status().isBadRequest());

        List<Connector> connectorList = connectorRepository.findAll();
        assertThat(connectorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = connectorRepository.findAll().size();
        // set the field null
        connector.setActive(null);

        // Create the Connector, which fails.
        ConnectorDTO connectorDTO = connectorMapper.toDto(connector);

        restConnectorMockMvc.perform(post("/api/connectors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(connectorDTO)))
            .andExpect(status().isBadRequest());

        List<Connector> connectorList = connectorRepository.findAll();
        assertThat(connectorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllConnectors() throws Exception {
        // Initialize the database
        connectorRepository.saveAndFlush(connector);

        // Get all the connectorList
        restConnectorMockMvc.perform(get("/api/connectors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(connector.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())))
            .andExpect(jsonPath("$.[*].logic").value(hasItem(DEFAULT_LOGIC.toString())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())))
            .andExpect(jsonPath("$.[*].partnerCode").value(hasItem(DEFAULT_PARTNER_CODE.toString())))
            .andExpect(jsonPath("$.[*].meansofpaymentTypeCode").value(hasItem(DEFAULT_MEANSOFPAYMENT_TYPE_CODE.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getConnector() throws Exception {
        // Initialize the database
        connectorRepository.saveAndFlush(connector);

        // Get the connector
        restConnectorMockMvc.perform(get("/api/connectors/{id}", connector.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(connector.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()))
            .andExpect(jsonPath("$.logic").value(DEFAULT_LOGIC.toString()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()))
            .andExpect(jsonPath("$.partnerCode").value(DEFAULT_PARTNER_CODE.toString()))
            .andExpect(jsonPath("$.meansofpaymentTypeCode").value(DEFAULT_MEANSOFPAYMENT_TYPE_CODE.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingConnector() throws Exception {
        // Get the connector
        restConnectorMockMvc.perform(get("/api/connectors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConnector() throws Exception {
        // Initialize the database
        connectorRepository.saveAndFlush(connector);

        int databaseSizeBeforeUpdate = connectorRepository.findAll().size();

        // Update the connector
        Connector updatedConnector = connectorRepository.findById(connector.getId()).get();
        // Disconnect from session so that the updates on updatedConnector are not directly saved in db
        em.detach(updatedConnector);
        updatedConnector
            .code(UPDATED_CODE)
            .label(UPDATED_LABEL)
            .logic(UPDATED_LOGIC)
            .comment(UPDATED_COMMENT)
            .partnerCode(UPDATED_PARTNER_CODE)
            .meansofpaymentTypeCode(UPDATED_MEANSOFPAYMENT_TYPE_CODE)
            .active(UPDATED_ACTIVE);
        ConnectorDTO connectorDTO = connectorMapper.toDto(updatedConnector);

        restConnectorMockMvc.perform(put("/api/connectors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(connectorDTO)))
            .andExpect(status().isOk());

        // Validate the Connector in the database
        List<Connector> connectorList = connectorRepository.findAll();
        assertThat(connectorList).hasSize(databaseSizeBeforeUpdate);
        Connector testConnector = connectorList.get(connectorList.size() - 1);
        assertThat(testConnector.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testConnector.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testConnector.getLogic()).isEqualTo(UPDATED_LOGIC);
        assertThat(testConnector.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testConnector.getPartnerCode()).isEqualTo(UPDATED_PARTNER_CODE);
        assertThat(testConnector.getMeansofpaymentTypeCode()).isEqualTo(UPDATED_MEANSOFPAYMENT_TYPE_CODE);
        assertThat(testConnector.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingConnector() throws Exception {
        int databaseSizeBeforeUpdate = connectorRepository.findAll().size();

        // Create the Connector
        ConnectorDTO connectorDTO = connectorMapper.toDto(connector);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConnectorMockMvc.perform(put("/api/connectors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(connectorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Connector in the database
        List<Connector> connectorList = connectorRepository.findAll();
        assertThat(connectorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteConnector() throws Exception {
        // Initialize the database
        connectorRepository.saveAndFlush(connector);

        int databaseSizeBeforeDelete = connectorRepository.findAll().size();

        // Delete the connector
        restConnectorMockMvc.perform(delete("/api/connectors/{id}", connector.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Connector> connectorList = connectorRepository.findAll();
        assertThat(connectorList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Connector.class);
        Connector connector1 = new Connector();
        connector1.setId(1L);
        Connector connector2 = new Connector();
        connector2.setId(connector1.getId());
        assertThat(connector1).isEqualTo(connector2);
        connector2.setId(2L);
        assertThat(connector1).isNotEqualTo(connector2);
        connector1.setId(null);
        assertThat(connector1).isNotEqualTo(connector2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConnectorDTO.class);
        ConnectorDTO connectorDTO1 = new ConnectorDTO();
        connectorDTO1.setId(1L);
        ConnectorDTO connectorDTO2 = new ConnectorDTO();
        assertThat(connectorDTO1).isNotEqualTo(connectorDTO2);
        connectorDTO2.setId(connectorDTO1.getId());
        assertThat(connectorDTO1).isEqualTo(connectorDTO2);
        connectorDTO2.setId(2L);
        assertThat(connectorDTO1).isNotEqualTo(connectorDTO2);
        connectorDTO1.setId(null);
        assertThat(connectorDTO1).isNotEqualTo(connectorDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(connectorMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(connectorMapper.fromId(null)).isNull();
    }
}
