package com.electron.mfs.pg.gateway.web.rest;

import com.electron.mfs.pg.gateway.PgkernelgatewayApp;
import com.electron.mfs.pg.gateway.config.SecurityBeanOverrideConfiguration;
import com.electron.mfs.pg.gateway.domain.PartnerSecurity;
import com.electron.mfs.pg.gateway.repository.PartnerSecurityRepository;
import com.electron.mfs.pg.gateway.service.PartnerSecurityService;
import com.electron.mfs.pg.gateway.service.dto.PartnerSecurityDTO;
import com.electron.mfs.pg.gateway.service.mapper.PartnerSecurityMapper;
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
 * Integration tests for the {@Link PartnerSecurityResource} REST controller.
 */
@EmbeddedKafka
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, PgkernelgatewayApp.class})
public class PartnerSecurityResourceIT {

    private static final String DEFAULT_CODE = "AAAAA";
    private static final String UPDATED_CODE = "BBBBB";

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private PartnerSecurityRepository partnerSecurityRepository;

    @Autowired
    private PartnerSecurityMapper partnerSecurityMapper;

    @Autowired
    private PartnerSecurityService partnerSecurityService;

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

    private MockMvc restPartnerSecurityMockMvc;

    private PartnerSecurity partnerSecurity;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PartnerSecurityResource partnerSecurityResource = new PartnerSecurityResource(partnerSecurityService);
        this.restPartnerSecurityMockMvc = MockMvcBuilders.standaloneSetup(partnerSecurityResource)
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
    public static PartnerSecurity createEntity(EntityManager em) {
        PartnerSecurity partnerSecurity = new PartnerSecurity()
            .code(DEFAULT_CODE)
            .label(DEFAULT_LABEL)
            .active(DEFAULT_ACTIVE);
        return partnerSecurity;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PartnerSecurity createUpdatedEntity(EntityManager em) {
        PartnerSecurity partnerSecurity = new PartnerSecurity()
            .code(UPDATED_CODE)
            .label(UPDATED_LABEL)
            .active(UPDATED_ACTIVE);
        return partnerSecurity;
    }

    @BeforeEach
    public void initTest() {
        partnerSecurity = createEntity(em);
    }

    @Test
    @Transactional
    public void createPartnerSecurity() throws Exception {
        int databaseSizeBeforeCreate = partnerSecurityRepository.findAll().size();

        // Create the PartnerSecurity
        PartnerSecurityDTO partnerSecurityDTO = partnerSecurityMapper.toDto(partnerSecurity);
        restPartnerSecurityMockMvc.perform(post("/api/partner-securities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partnerSecurityDTO)))
            .andExpect(status().isCreated());

        // Validate the PartnerSecurity in the database
        List<PartnerSecurity> partnerSecurityList = partnerSecurityRepository.findAll();
        assertThat(partnerSecurityList).hasSize(databaseSizeBeforeCreate + 1);
        PartnerSecurity testPartnerSecurity = partnerSecurityList.get(partnerSecurityList.size() - 1);
        assertThat(testPartnerSecurity.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testPartnerSecurity.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testPartnerSecurity.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createPartnerSecurityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = partnerSecurityRepository.findAll().size();

        // Create the PartnerSecurity with an existing ID
        partnerSecurity.setId(1L);
        PartnerSecurityDTO partnerSecurityDTO = partnerSecurityMapper.toDto(partnerSecurity);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPartnerSecurityMockMvc.perform(post("/api/partner-securities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partnerSecurityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PartnerSecurity in the database
        List<PartnerSecurity> partnerSecurityList = partnerSecurityRepository.findAll();
        assertThat(partnerSecurityList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = partnerSecurityRepository.findAll().size();
        // set the field null
        partnerSecurity.setCode(null);

        // Create the PartnerSecurity, which fails.
        PartnerSecurityDTO partnerSecurityDTO = partnerSecurityMapper.toDto(partnerSecurity);

        restPartnerSecurityMockMvc.perform(post("/api/partner-securities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partnerSecurityDTO)))
            .andExpect(status().isBadRequest());

        List<PartnerSecurity> partnerSecurityList = partnerSecurityRepository.findAll();
        assertThat(partnerSecurityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLabelIsRequired() throws Exception {
        int databaseSizeBeforeTest = partnerSecurityRepository.findAll().size();
        // set the field null
        partnerSecurity.setLabel(null);

        // Create the PartnerSecurity, which fails.
        PartnerSecurityDTO partnerSecurityDTO = partnerSecurityMapper.toDto(partnerSecurity);

        restPartnerSecurityMockMvc.perform(post("/api/partner-securities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partnerSecurityDTO)))
            .andExpect(status().isBadRequest());

        List<PartnerSecurity> partnerSecurityList = partnerSecurityRepository.findAll();
        assertThat(partnerSecurityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = partnerSecurityRepository.findAll().size();
        // set the field null
        partnerSecurity.setActive(null);

        // Create the PartnerSecurity, which fails.
        PartnerSecurityDTO partnerSecurityDTO = partnerSecurityMapper.toDto(partnerSecurity);

        restPartnerSecurityMockMvc.perform(post("/api/partner-securities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partnerSecurityDTO)))
            .andExpect(status().isBadRequest());

        List<PartnerSecurity> partnerSecurityList = partnerSecurityRepository.findAll();
        assertThat(partnerSecurityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPartnerSecurities() throws Exception {
        // Initialize the database
        partnerSecurityRepository.saveAndFlush(partnerSecurity);

        // Get all the partnerSecurityList
        restPartnerSecurityMockMvc.perform(get("/api/partner-securities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(partnerSecurity.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getPartnerSecurity() throws Exception {
        // Initialize the database
        partnerSecurityRepository.saveAndFlush(partnerSecurity);

        // Get the partnerSecurity
        restPartnerSecurityMockMvc.perform(get("/api/partner-securities/{id}", partnerSecurity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(partnerSecurity.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPartnerSecurity() throws Exception {
        // Get the partnerSecurity
        restPartnerSecurityMockMvc.perform(get("/api/partner-securities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePartnerSecurity() throws Exception {
        // Initialize the database
        partnerSecurityRepository.saveAndFlush(partnerSecurity);

        int databaseSizeBeforeUpdate = partnerSecurityRepository.findAll().size();

        // Update the partnerSecurity
        PartnerSecurity updatedPartnerSecurity = partnerSecurityRepository.findById(partnerSecurity.getId()).get();
        // Disconnect from session so that the updates on updatedPartnerSecurity are not directly saved in db
        em.detach(updatedPartnerSecurity);
        updatedPartnerSecurity
            .code(UPDATED_CODE)
            .label(UPDATED_LABEL)
            .active(UPDATED_ACTIVE);
        PartnerSecurityDTO partnerSecurityDTO = partnerSecurityMapper.toDto(updatedPartnerSecurity);

        restPartnerSecurityMockMvc.perform(put("/api/partner-securities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partnerSecurityDTO)))
            .andExpect(status().isOk());

        // Validate the PartnerSecurity in the database
        List<PartnerSecurity> partnerSecurityList = partnerSecurityRepository.findAll();
        assertThat(partnerSecurityList).hasSize(databaseSizeBeforeUpdate);
        PartnerSecurity testPartnerSecurity = partnerSecurityList.get(partnerSecurityList.size() - 1);
        assertThat(testPartnerSecurity.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testPartnerSecurity.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testPartnerSecurity.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingPartnerSecurity() throws Exception {
        int databaseSizeBeforeUpdate = partnerSecurityRepository.findAll().size();

        // Create the PartnerSecurity
        PartnerSecurityDTO partnerSecurityDTO = partnerSecurityMapper.toDto(partnerSecurity);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPartnerSecurityMockMvc.perform(put("/api/partner-securities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partnerSecurityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PartnerSecurity in the database
        List<PartnerSecurity> partnerSecurityList = partnerSecurityRepository.findAll();
        assertThat(partnerSecurityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePartnerSecurity() throws Exception {
        // Initialize the database
        partnerSecurityRepository.saveAndFlush(partnerSecurity);

        int databaseSizeBeforeDelete = partnerSecurityRepository.findAll().size();

        // Delete the partnerSecurity
        restPartnerSecurityMockMvc.perform(delete("/api/partner-securities/{id}", partnerSecurity.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PartnerSecurity> partnerSecurityList = partnerSecurityRepository.findAll();
        assertThat(partnerSecurityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PartnerSecurity.class);
        PartnerSecurity partnerSecurity1 = new PartnerSecurity();
        partnerSecurity1.setId(1L);
        PartnerSecurity partnerSecurity2 = new PartnerSecurity();
        partnerSecurity2.setId(partnerSecurity1.getId());
        assertThat(partnerSecurity1).isEqualTo(partnerSecurity2);
        partnerSecurity2.setId(2L);
        assertThat(partnerSecurity1).isNotEqualTo(partnerSecurity2);
        partnerSecurity1.setId(null);
        assertThat(partnerSecurity1).isNotEqualTo(partnerSecurity2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PartnerSecurityDTO.class);
        PartnerSecurityDTO partnerSecurityDTO1 = new PartnerSecurityDTO();
        partnerSecurityDTO1.setId(1L);
        PartnerSecurityDTO partnerSecurityDTO2 = new PartnerSecurityDTO();
        assertThat(partnerSecurityDTO1).isNotEqualTo(partnerSecurityDTO2);
        partnerSecurityDTO2.setId(partnerSecurityDTO1.getId());
        assertThat(partnerSecurityDTO1).isEqualTo(partnerSecurityDTO2);
        partnerSecurityDTO2.setId(2L);
        assertThat(partnerSecurityDTO1).isNotEqualTo(partnerSecurityDTO2);
        partnerSecurityDTO1.setId(null);
        assertThat(partnerSecurityDTO1).isNotEqualTo(partnerSecurityDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(partnerSecurityMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(partnerSecurityMapper.fromId(null)).isNull();
    }
}
