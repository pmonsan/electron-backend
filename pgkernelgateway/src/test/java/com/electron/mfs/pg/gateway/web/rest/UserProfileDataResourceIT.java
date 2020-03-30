package com.electron.mfs.pg.gateway.web.rest;

import com.electron.mfs.pg.gateway.PgkernelgatewayApp;
import com.electron.mfs.pg.gateway.config.SecurityBeanOverrideConfiguration;
import com.electron.mfs.pg.gateway.domain.UserProfileData;
import com.electron.mfs.pg.gateway.repository.UserProfileDataRepository;
import com.electron.mfs.pg.gateway.service.UserProfileDataService;
import com.electron.mfs.pg.gateway.service.dto.UserProfileDataDTO;
import com.electron.mfs.pg.gateway.service.mapper.UserProfileDataMapper;
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
 * Integration tests for the {@Link UserProfileDataResource} REST controller.
 */
@EmbeddedKafka
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, PgkernelgatewayApp.class})
public class UserProfileDataResourceIT {

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final String DEFAULT_PG_DATA_CODE = "AAAAA";
    private static final String UPDATED_PG_DATA_CODE = "BBBBB";

    @Autowired
    private UserProfileDataRepository userProfileDataRepository;

    @Autowired
    private UserProfileDataMapper userProfileDataMapper;

    @Autowired
    private UserProfileDataService userProfileDataService;

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

    private MockMvc restUserProfileDataMockMvc;

    private UserProfileData userProfileData;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UserProfileDataResource userProfileDataResource = new UserProfileDataResource(userProfileDataService);
        this.restUserProfileDataMockMvc = MockMvcBuilders.standaloneSetup(userProfileDataResource)
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
    public static UserProfileData createEntity(EntityManager em) {
        UserProfileData userProfileData = new UserProfileData()
            .active(DEFAULT_ACTIVE)
            .pgDataCode(DEFAULT_PG_DATA_CODE);
        return userProfileData;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserProfileData createUpdatedEntity(EntityManager em) {
        UserProfileData userProfileData = new UserProfileData()
            .active(UPDATED_ACTIVE)
            .pgDataCode(UPDATED_PG_DATA_CODE);
        return userProfileData;
    }

    @BeforeEach
    public void initTest() {
        userProfileData = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserProfileData() throws Exception {
        int databaseSizeBeforeCreate = userProfileDataRepository.findAll().size();

        // Create the UserProfileData
        UserProfileDataDTO userProfileDataDTO = userProfileDataMapper.toDto(userProfileData);
        restUserProfileDataMockMvc.perform(post("/api/user-profile-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userProfileDataDTO)))
            .andExpect(status().isCreated());

        // Validate the UserProfileData in the database
        List<UserProfileData> userProfileDataList = userProfileDataRepository.findAll();
        assertThat(userProfileDataList).hasSize(databaseSizeBeforeCreate + 1);
        UserProfileData testUserProfileData = userProfileDataList.get(userProfileDataList.size() - 1);
        assertThat(testUserProfileData.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testUserProfileData.getPgDataCode()).isEqualTo(DEFAULT_PG_DATA_CODE);
    }

    @Test
    @Transactional
    public void createUserProfileDataWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userProfileDataRepository.findAll().size();

        // Create the UserProfileData with an existing ID
        userProfileData.setId(1L);
        UserProfileDataDTO userProfileDataDTO = userProfileDataMapper.toDto(userProfileData);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserProfileDataMockMvc.perform(post("/api/user-profile-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userProfileDataDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserProfileData in the database
        List<UserProfileData> userProfileDataList = userProfileDataRepository.findAll();
        assertThat(userProfileDataList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = userProfileDataRepository.findAll().size();
        // set the field null
        userProfileData.setActive(null);

        // Create the UserProfileData, which fails.
        UserProfileDataDTO userProfileDataDTO = userProfileDataMapper.toDto(userProfileData);

        restUserProfileDataMockMvc.perform(post("/api/user-profile-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userProfileDataDTO)))
            .andExpect(status().isBadRequest());

        List<UserProfileData> userProfileDataList = userProfileDataRepository.findAll();
        assertThat(userProfileDataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUserProfileData() throws Exception {
        // Initialize the database
        userProfileDataRepository.saveAndFlush(userProfileData);

        // Get all the userProfileDataList
        restUserProfileDataMockMvc.perform(get("/api/user-profile-data?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userProfileData.getId().intValue())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].pgDataCode").value(hasItem(DEFAULT_PG_DATA_CODE.toString())));
    }
    
    @Test
    @Transactional
    public void getUserProfileData() throws Exception {
        // Initialize the database
        userProfileDataRepository.saveAndFlush(userProfileData);

        // Get the userProfileData
        restUserProfileDataMockMvc.perform(get("/api/user-profile-data/{id}", userProfileData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userProfileData.getId().intValue()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.pgDataCode").value(DEFAULT_PG_DATA_CODE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUserProfileData() throws Exception {
        // Get the userProfileData
        restUserProfileDataMockMvc.perform(get("/api/user-profile-data/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserProfileData() throws Exception {
        // Initialize the database
        userProfileDataRepository.saveAndFlush(userProfileData);

        int databaseSizeBeforeUpdate = userProfileDataRepository.findAll().size();

        // Update the userProfileData
        UserProfileData updatedUserProfileData = userProfileDataRepository.findById(userProfileData.getId()).get();
        // Disconnect from session so that the updates on updatedUserProfileData are not directly saved in db
        em.detach(updatedUserProfileData);
        updatedUserProfileData
            .active(UPDATED_ACTIVE)
            .pgDataCode(UPDATED_PG_DATA_CODE);
        UserProfileDataDTO userProfileDataDTO = userProfileDataMapper.toDto(updatedUserProfileData);

        restUserProfileDataMockMvc.perform(put("/api/user-profile-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userProfileDataDTO)))
            .andExpect(status().isOk());

        // Validate the UserProfileData in the database
        List<UserProfileData> userProfileDataList = userProfileDataRepository.findAll();
        assertThat(userProfileDataList).hasSize(databaseSizeBeforeUpdate);
        UserProfileData testUserProfileData = userProfileDataList.get(userProfileDataList.size() - 1);
        assertThat(testUserProfileData.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testUserProfileData.getPgDataCode()).isEqualTo(UPDATED_PG_DATA_CODE);
    }

    @Test
    @Transactional
    public void updateNonExistingUserProfileData() throws Exception {
        int databaseSizeBeforeUpdate = userProfileDataRepository.findAll().size();

        // Create the UserProfileData
        UserProfileDataDTO userProfileDataDTO = userProfileDataMapper.toDto(userProfileData);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserProfileDataMockMvc.perform(put("/api/user-profile-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userProfileDataDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserProfileData in the database
        List<UserProfileData> userProfileDataList = userProfileDataRepository.findAll();
        assertThat(userProfileDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserProfileData() throws Exception {
        // Initialize the database
        userProfileDataRepository.saveAndFlush(userProfileData);

        int databaseSizeBeforeDelete = userProfileDataRepository.findAll().size();

        // Delete the userProfileData
        restUserProfileDataMockMvc.perform(delete("/api/user-profile-data/{id}", userProfileData.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserProfileData> userProfileDataList = userProfileDataRepository.findAll();
        assertThat(userProfileDataList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserProfileData.class);
        UserProfileData userProfileData1 = new UserProfileData();
        userProfileData1.setId(1L);
        UserProfileData userProfileData2 = new UserProfileData();
        userProfileData2.setId(userProfileData1.getId());
        assertThat(userProfileData1).isEqualTo(userProfileData2);
        userProfileData2.setId(2L);
        assertThat(userProfileData1).isNotEqualTo(userProfileData2);
        userProfileData1.setId(null);
        assertThat(userProfileData1).isNotEqualTo(userProfileData2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserProfileDataDTO.class);
        UserProfileDataDTO userProfileDataDTO1 = new UserProfileDataDTO();
        userProfileDataDTO1.setId(1L);
        UserProfileDataDTO userProfileDataDTO2 = new UserProfileDataDTO();
        assertThat(userProfileDataDTO1).isNotEqualTo(userProfileDataDTO2);
        userProfileDataDTO2.setId(userProfileDataDTO1.getId());
        assertThat(userProfileDataDTO1).isEqualTo(userProfileDataDTO2);
        userProfileDataDTO2.setId(2L);
        assertThat(userProfileDataDTO1).isNotEqualTo(userProfileDataDTO2);
        userProfileDataDTO1.setId(null);
        assertThat(userProfileDataDTO1).isNotEqualTo(userProfileDataDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(userProfileDataMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(userProfileDataMapper.fromId(null)).isNull();
    }
}
