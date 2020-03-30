package com.electron.mfs.pg.gateway.web.rest;

import com.electron.mfs.pg.gateway.PgkernelgatewayApp;
import com.electron.mfs.pg.gateway.config.SecurityBeanOverrideConfiguration;
import com.electron.mfs.pg.gateway.domain.TransactionChannel;
import com.electron.mfs.pg.gateway.repository.TransactionChannelRepository;
import com.electron.mfs.pg.gateway.service.TransactionChannelService;
import com.electron.mfs.pg.gateway.service.dto.TransactionChannelDTO;
import com.electron.mfs.pg.gateway.service.mapper.TransactionChannelMapper;
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
 * Integration tests for the {@Link TransactionChannelResource} REST controller.
 */
@EmbeddedKafka
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, PgkernelgatewayApp.class})
public class TransactionChannelResourceIT {

    private static final String DEFAULT_CODE = "AAAAA";
    private static final String UPDATED_CODE = "BBBBB";

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private TransactionChannelRepository transactionChannelRepository;

    @Autowired
    private TransactionChannelMapper transactionChannelMapper;

    @Autowired
    private TransactionChannelService transactionChannelService;

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

    private MockMvc restTransactionChannelMockMvc;

    private TransactionChannel transactionChannel;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TransactionChannelResource transactionChannelResource = new TransactionChannelResource(transactionChannelService);
        this.restTransactionChannelMockMvc = MockMvcBuilders.standaloneSetup(transactionChannelResource)
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
    public static TransactionChannel createEntity(EntityManager em) {
        TransactionChannel transactionChannel = new TransactionChannel()
            .code(DEFAULT_CODE)
            .label(DEFAULT_LABEL)
            .active(DEFAULT_ACTIVE);
        return transactionChannel;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TransactionChannel createUpdatedEntity(EntityManager em) {
        TransactionChannel transactionChannel = new TransactionChannel()
            .code(UPDATED_CODE)
            .label(UPDATED_LABEL)
            .active(UPDATED_ACTIVE);
        return transactionChannel;
    }

    @BeforeEach
    public void initTest() {
        transactionChannel = createEntity(em);
    }

    @Test
    @Transactional
    public void createTransactionChannel() throws Exception {
        int databaseSizeBeforeCreate = transactionChannelRepository.findAll().size();

        // Create the TransactionChannel
        TransactionChannelDTO transactionChannelDTO = transactionChannelMapper.toDto(transactionChannel);
        restTransactionChannelMockMvc.perform(post("/api/transaction-channels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionChannelDTO)))
            .andExpect(status().isCreated());

        // Validate the TransactionChannel in the database
        List<TransactionChannel> transactionChannelList = transactionChannelRepository.findAll();
        assertThat(transactionChannelList).hasSize(databaseSizeBeforeCreate + 1);
        TransactionChannel testTransactionChannel = transactionChannelList.get(transactionChannelList.size() - 1);
        assertThat(testTransactionChannel.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testTransactionChannel.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testTransactionChannel.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createTransactionChannelWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = transactionChannelRepository.findAll().size();

        // Create the TransactionChannel with an existing ID
        transactionChannel.setId(1L);
        TransactionChannelDTO transactionChannelDTO = transactionChannelMapper.toDto(transactionChannel);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransactionChannelMockMvc.perform(post("/api/transaction-channels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionChannelDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TransactionChannel in the database
        List<TransactionChannel> transactionChannelList = transactionChannelRepository.findAll();
        assertThat(transactionChannelList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionChannelRepository.findAll().size();
        // set the field null
        transactionChannel.setCode(null);

        // Create the TransactionChannel, which fails.
        TransactionChannelDTO transactionChannelDTO = transactionChannelMapper.toDto(transactionChannel);

        restTransactionChannelMockMvc.perform(post("/api/transaction-channels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionChannelDTO)))
            .andExpect(status().isBadRequest());

        List<TransactionChannel> transactionChannelList = transactionChannelRepository.findAll();
        assertThat(transactionChannelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLabelIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionChannelRepository.findAll().size();
        // set the field null
        transactionChannel.setLabel(null);

        // Create the TransactionChannel, which fails.
        TransactionChannelDTO transactionChannelDTO = transactionChannelMapper.toDto(transactionChannel);

        restTransactionChannelMockMvc.perform(post("/api/transaction-channels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionChannelDTO)))
            .andExpect(status().isBadRequest());

        List<TransactionChannel> transactionChannelList = transactionChannelRepository.findAll();
        assertThat(transactionChannelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionChannelRepository.findAll().size();
        // set the field null
        transactionChannel.setActive(null);

        // Create the TransactionChannel, which fails.
        TransactionChannelDTO transactionChannelDTO = transactionChannelMapper.toDto(transactionChannel);

        restTransactionChannelMockMvc.perform(post("/api/transaction-channels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionChannelDTO)))
            .andExpect(status().isBadRequest());

        List<TransactionChannel> transactionChannelList = transactionChannelRepository.findAll();
        assertThat(transactionChannelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTransactionChannels() throws Exception {
        // Initialize the database
        transactionChannelRepository.saveAndFlush(transactionChannel);

        // Get all the transactionChannelList
        restTransactionChannelMockMvc.perform(get("/api/transaction-channels?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transactionChannel.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getTransactionChannel() throws Exception {
        // Initialize the database
        transactionChannelRepository.saveAndFlush(transactionChannel);

        // Get the transactionChannel
        restTransactionChannelMockMvc.perform(get("/api/transaction-channels/{id}", transactionChannel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(transactionChannel.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTransactionChannel() throws Exception {
        // Get the transactionChannel
        restTransactionChannelMockMvc.perform(get("/api/transaction-channels/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTransactionChannel() throws Exception {
        // Initialize the database
        transactionChannelRepository.saveAndFlush(transactionChannel);

        int databaseSizeBeforeUpdate = transactionChannelRepository.findAll().size();

        // Update the transactionChannel
        TransactionChannel updatedTransactionChannel = transactionChannelRepository.findById(transactionChannel.getId()).get();
        // Disconnect from session so that the updates on updatedTransactionChannel are not directly saved in db
        em.detach(updatedTransactionChannel);
        updatedTransactionChannel
            .code(UPDATED_CODE)
            .label(UPDATED_LABEL)
            .active(UPDATED_ACTIVE);
        TransactionChannelDTO transactionChannelDTO = transactionChannelMapper.toDto(updatedTransactionChannel);

        restTransactionChannelMockMvc.perform(put("/api/transaction-channels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionChannelDTO)))
            .andExpect(status().isOk());

        // Validate the TransactionChannel in the database
        List<TransactionChannel> transactionChannelList = transactionChannelRepository.findAll();
        assertThat(transactionChannelList).hasSize(databaseSizeBeforeUpdate);
        TransactionChannel testTransactionChannel = transactionChannelList.get(transactionChannelList.size() - 1);
        assertThat(testTransactionChannel.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTransactionChannel.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testTransactionChannel.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingTransactionChannel() throws Exception {
        int databaseSizeBeforeUpdate = transactionChannelRepository.findAll().size();

        // Create the TransactionChannel
        TransactionChannelDTO transactionChannelDTO = transactionChannelMapper.toDto(transactionChannel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransactionChannelMockMvc.perform(put("/api/transaction-channels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionChannelDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TransactionChannel in the database
        List<TransactionChannel> transactionChannelList = transactionChannelRepository.findAll();
        assertThat(transactionChannelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTransactionChannel() throws Exception {
        // Initialize the database
        transactionChannelRepository.saveAndFlush(transactionChannel);

        int databaseSizeBeforeDelete = transactionChannelRepository.findAll().size();

        // Delete the transactionChannel
        restTransactionChannelMockMvc.perform(delete("/api/transaction-channels/{id}", transactionChannel.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TransactionChannel> transactionChannelList = transactionChannelRepository.findAll();
        assertThat(transactionChannelList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TransactionChannel.class);
        TransactionChannel transactionChannel1 = new TransactionChannel();
        transactionChannel1.setId(1L);
        TransactionChannel transactionChannel2 = new TransactionChannel();
        transactionChannel2.setId(transactionChannel1.getId());
        assertThat(transactionChannel1).isEqualTo(transactionChannel2);
        transactionChannel2.setId(2L);
        assertThat(transactionChannel1).isNotEqualTo(transactionChannel2);
        transactionChannel1.setId(null);
        assertThat(transactionChannel1).isNotEqualTo(transactionChannel2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TransactionChannelDTO.class);
        TransactionChannelDTO transactionChannelDTO1 = new TransactionChannelDTO();
        transactionChannelDTO1.setId(1L);
        TransactionChannelDTO transactionChannelDTO2 = new TransactionChannelDTO();
        assertThat(transactionChannelDTO1).isNotEqualTo(transactionChannelDTO2);
        transactionChannelDTO2.setId(transactionChannelDTO1.getId());
        assertThat(transactionChannelDTO1).isEqualTo(transactionChannelDTO2);
        transactionChannelDTO2.setId(2L);
        assertThat(transactionChannelDTO1).isNotEqualTo(transactionChannelDTO2);
        transactionChannelDTO1.setId(null);
        assertThat(transactionChannelDTO1).isNotEqualTo(transactionChannelDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(transactionChannelMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(transactionChannelMapper.fromId(null)).isNull();
    }
}
