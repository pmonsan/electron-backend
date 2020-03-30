package com.electron.mfs.pg.gateway.web.rest;

import com.electron.mfs.pg.gateway.PgkernelgatewayApp;
import com.electron.mfs.pg.gateway.config.SecurityBeanOverrideConfiguration;
import com.electron.mfs.pg.gateway.domain.ServiceChannel;
import com.electron.mfs.pg.gateway.repository.ServiceChannelRepository;
import com.electron.mfs.pg.gateway.service.ServiceChannelService;
import com.electron.mfs.pg.gateway.service.dto.ServiceChannelDTO;
import com.electron.mfs.pg.gateway.service.mapper.ServiceChannelMapper;
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
 * Integration tests for the {@Link ServiceChannelResource} REST controller.
 */
@EmbeddedKafka
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, PgkernelgatewayApp.class})
public class ServiceChannelResourceIT {

    private static final String DEFAULT_CHANNEL_CODE = "AAAAA";
    private static final String UPDATED_CHANNEL_CODE = "BBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private ServiceChannelRepository serviceChannelRepository;

    @Autowired
    private ServiceChannelMapper serviceChannelMapper;

    @Autowired
    private ServiceChannelService serviceChannelService;

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

    private MockMvc restServiceChannelMockMvc;

    private ServiceChannel serviceChannel;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ServiceChannelResource serviceChannelResource = new ServiceChannelResource(serviceChannelService);
        this.restServiceChannelMockMvc = MockMvcBuilders.standaloneSetup(serviceChannelResource)
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
    public static ServiceChannel createEntity(EntityManager em) {
        ServiceChannel serviceChannel = new ServiceChannel()
            .channelCode(DEFAULT_CHANNEL_CODE)
            .active(DEFAULT_ACTIVE);
        return serviceChannel;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServiceChannel createUpdatedEntity(EntityManager em) {
        ServiceChannel serviceChannel = new ServiceChannel()
            .channelCode(UPDATED_CHANNEL_CODE)
            .active(UPDATED_ACTIVE);
        return serviceChannel;
    }

    @BeforeEach
    public void initTest() {
        serviceChannel = createEntity(em);
    }

    @Test
    @Transactional
    public void createServiceChannel() throws Exception {
        int databaseSizeBeforeCreate = serviceChannelRepository.findAll().size();

        // Create the ServiceChannel
        ServiceChannelDTO serviceChannelDTO = serviceChannelMapper.toDto(serviceChannel);
        restServiceChannelMockMvc.perform(post("/api/service-channels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceChannelDTO)))
            .andExpect(status().isCreated());

        // Validate the ServiceChannel in the database
        List<ServiceChannel> serviceChannelList = serviceChannelRepository.findAll();
        assertThat(serviceChannelList).hasSize(databaseSizeBeforeCreate + 1);
        ServiceChannel testServiceChannel = serviceChannelList.get(serviceChannelList.size() - 1);
        assertThat(testServiceChannel.getChannelCode()).isEqualTo(DEFAULT_CHANNEL_CODE);
        assertThat(testServiceChannel.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createServiceChannelWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = serviceChannelRepository.findAll().size();

        // Create the ServiceChannel with an existing ID
        serviceChannel.setId(1L);
        ServiceChannelDTO serviceChannelDTO = serviceChannelMapper.toDto(serviceChannel);

        // An entity with an existing ID cannot be created, so this API call must fail
        restServiceChannelMockMvc.perform(post("/api/service-channels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceChannelDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceChannel in the database
        List<ServiceChannel> serviceChannelList = serviceChannelRepository.findAll();
        assertThat(serviceChannelList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkChannelCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceChannelRepository.findAll().size();
        // set the field null
        serviceChannel.setChannelCode(null);

        // Create the ServiceChannel, which fails.
        ServiceChannelDTO serviceChannelDTO = serviceChannelMapper.toDto(serviceChannel);

        restServiceChannelMockMvc.perform(post("/api/service-channels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceChannelDTO)))
            .andExpect(status().isBadRequest());

        List<ServiceChannel> serviceChannelList = serviceChannelRepository.findAll();
        assertThat(serviceChannelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceChannelRepository.findAll().size();
        // set the field null
        serviceChannel.setActive(null);

        // Create the ServiceChannel, which fails.
        ServiceChannelDTO serviceChannelDTO = serviceChannelMapper.toDto(serviceChannel);

        restServiceChannelMockMvc.perform(post("/api/service-channels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceChannelDTO)))
            .andExpect(status().isBadRequest());

        List<ServiceChannel> serviceChannelList = serviceChannelRepository.findAll();
        assertThat(serviceChannelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllServiceChannels() throws Exception {
        // Initialize the database
        serviceChannelRepository.saveAndFlush(serviceChannel);

        // Get all the serviceChannelList
        restServiceChannelMockMvc.perform(get("/api/service-channels?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceChannel.getId().intValue())))
            .andExpect(jsonPath("$.[*].channelCode").value(hasItem(DEFAULT_CHANNEL_CODE.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getServiceChannel() throws Exception {
        // Initialize the database
        serviceChannelRepository.saveAndFlush(serviceChannel);

        // Get the serviceChannel
        restServiceChannelMockMvc.perform(get("/api/service-channels/{id}", serviceChannel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(serviceChannel.getId().intValue()))
            .andExpect(jsonPath("$.channelCode").value(DEFAULT_CHANNEL_CODE.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingServiceChannel() throws Exception {
        // Get the serviceChannel
        restServiceChannelMockMvc.perform(get("/api/service-channels/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateServiceChannel() throws Exception {
        // Initialize the database
        serviceChannelRepository.saveAndFlush(serviceChannel);

        int databaseSizeBeforeUpdate = serviceChannelRepository.findAll().size();

        // Update the serviceChannel
        ServiceChannel updatedServiceChannel = serviceChannelRepository.findById(serviceChannel.getId()).get();
        // Disconnect from session so that the updates on updatedServiceChannel are not directly saved in db
        em.detach(updatedServiceChannel);
        updatedServiceChannel
            .channelCode(UPDATED_CHANNEL_CODE)
            .active(UPDATED_ACTIVE);
        ServiceChannelDTO serviceChannelDTO = serviceChannelMapper.toDto(updatedServiceChannel);

        restServiceChannelMockMvc.perform(put("/api/service-channels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceChannelDTO)))
            .andExpect(status().isOk());

        // Validate the ServiceChannel in the database
        List<ServiceChannel> serviceChannelList = serviceChannelRepository.findAll();
        assertThat(serviceChannelList).hasSize(databaseSizeBeforeUpdate);
        ServiceChannel testServiceChannel = serviceChannelList.get(serviceChannelList.size() - 1);
        assertThat(testServiceChannel.getChannelCode()).isEqualTo(UPDATED_CHANNEL_CODE);
        assertThat(testServiceChannel.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingServiceChannel() throws Exception {
        int databaseSizeBeforeUpdate = serviceChannelRepository.findAll().size();

        // Create the ServiceChannel
        ServiceChannelDTO serviceChannelDTO = serviceChannelMapper.toDto(serviceChannel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceChannelMockMvc.perform(put("/api/service-channels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceChannelDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceChannel in the database
        List<ServiceChannel> serviceChannelList = serviceChannelRepository.findAll();
        assertThat(serviceChannelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteServiceChannel() throws Exception {
        // Initialize the database
        serviceChannelRepository.saveAndFlush(serviceChannel);

        int databaseSizeBeforeDelete = serviceChannelRepository.findAll().size();

        // Delete the serviceChannel
        restServiceChannelMockMvc.perform(delete("/api/service-channels/{id}", serviceChannel.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ServiceChannel> serviceChannelList = serviceChannelRepository.findAll();
        assertThat(serviceChannelList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceChannel.class);
        ServiceChannel serviceChannel1 = new ServiceChannel();
        serviceChannel1.setId(1L);
        ServiceChannel serviceChannel2 = new ServiceChannel();
        serviceChannel2.setId(serviceChannel1.getId());
        assertThat(serviceChannel1).isEqualTo(serviceChannel2);
        serviceChannel2.setId(2L);
        assertThat(serviceChannel1).isNotEqualTo(serviceChannel2);
        serviceChannel1.setId(null);
        assertThat(serviceChannel1).isNotEqualTo(serviceChannel2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceChannelDTO.class);
        ServiceChannelDTO serviceChannelDTO1 = new ServiceChannelDTO();
        serviceChannelDTO1.setId(1L);
        ServiceChannelDTO serviceChannelDTO2 = new ServiceChannelDTO();
        assertThat(serviceChannelDTO1).isNotEqualTo(serviceChannelDTO2);
        serviceChannelDTO2.setId(serviceChannelDTO1.getId());
        assertThat(serviceChannelDTO1).isEqualTo(serviceChannelDTO2);
        serviceChannelDTO2.setId(2L);
        assertThat(serviceChannelDTO1).isNotEqualTo(serviceChannelDTO2);
        serviceChannelDTO1.setId(null);
        assertThat(serviceChannelDTO1).isNotEqualTo(serviceChannelDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(serviceChannelMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(serviceChannelMapper.fromId(null)).isNull();
    }
}
