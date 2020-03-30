package com.electron.mfs.pg.iam.web.rest;

import com.electron.mfs.pg.iam.IamManagerApp;
import com.electron.mfs.pg.iam.config.SecurityBeanOverrideConfiguration;
import com.electron.mfs.pg.iam.domain.UserConnection;
import com.electron.mfs.pg.iam.repository.UserConnectionRepository;
import com.electron.mfs.pg.iam.service.UserConnectionService;
import com.electron.mfs.pg.iam.service.dto.UserConnectionDTO;
import com.electron.mfs.pg.iam.service.mapper.UserConnectionMapper;
import com.electron.mfs.pg.iam.web.rest.errors.ExceptionTranslator;

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

import static com.electron.mfs.pg.iam.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link UserConnectionResource} REST controller.
 */
@EmbeddedKafka
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, IamManagerApp.class})
public class UserConnectionResourceIT {

    private static final Instant DEFAULT_LOGIN_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LOGIN_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_LOGOUT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LOGOUT_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private UserConnectionRepository userConnectionRepository;

    @Autowired
    private UserConnectionMapper userConnectionMapper;

    @Autowired
    private UserConnectionService userConnectionService;

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

    private MockMvc restUserConnectionMockMvc;

    private UserConnection userConnection;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UserConnectionResource userConnectionResource = new UserConnectionResource(userConnectionService);
        this.restUserConnectionMockMvc = MockMvcBuilders.standaloneSetup(userConnectionResource)
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
    public static UserConnection createEntity(EntityManager em) {
        UserConnection userConnection = new UserConnection()
            .loginDate(DEFAULT_LOGIN_DATE)
            .logoutDate(DEFAULT_LOGOUT_DATE);
        return userConnection;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserConnection createUpdatedEntity(EntityManager em) {
        UserConnection userConnection = new UserConnection()
            .loginDate(UPDATED_LOGIN_DATE)
            .logoutDate(UPDATED_LOGOUT_DATE);
        return userConnection;
    }

    @BeforeEach
    public void initTest() {
        userConnection = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserConnection() throws Exception {
        int databaseSizeBeforeCreate = userConnectionRepository.findAll().size();

        // Create the UserConnection
        UserConnectionDTO userConnectionDTO = userConnectionMapper.toDto(userConnection);
        restUserConnectionMockMvc.perform(post("/api/user-connections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userConnectionDTO)))
            .andExpect(status().isCreated());

        // Validate the UserConnection in the database
        List<UserConnection> userConnectionList = userConnectionRepository.findAll();
        assertThat(userConnectionList).hasSize(databaseSizeBeforeCreate + 1);
        UserConnection testUserConnection = userConnectionList.get(userConnectionList.size() - 1);
        assertThat(testUserConnection.getLoginDate()).isEqualTo(DEFAULT_LOGIN_DATE);
        assertThat(testUserConnection.getLogoutDate()).isEqualTo(DEFAULT_LOGOUT_DATE);
    }

    @Test
    @Transactional
    public void createUserConnectionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userConnectionRepository.findAll().size();

        // Create the UserConnection with an existing ID
        userConnection.setId(1L);
        UserConnectionDTO userConnectionDTO = userConnectionMapper.toDto(userConnection);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserConnectionMockMvc.perform(post("/api/user-connections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userConnectionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserConnection in the database
        List<UserConnection> userConnectionList = userConnectionRepository.findAll();
        assertThat(userConnectionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkLoginDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = userConnectionRepository.findAll().size();
        // set the field null
        userConnection.setLoginDate(null);

        // Create the UserConnection, which fails.
        UserConnectionDTO userConnectionDTO = userConnectionMapper.toDto(userConnection);

        restUserConnectionMockMvc.perform(post("/api/user-connections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userConnectionDTO)))
            .andExpect(status().isBadRequest());

        List<UserConnection> userConnectionList = userConnectionRepository.findAll();
        assertThat(userConnectionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUserConnections() throws Exception {
        // Initialize the database
        userConnectionRepository.saveAndFlush(userConnection);

        // Get all the userConnectionList
        restUserConnectionMockMvc.perform(get("/api/user-connections?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userConnection.getId().intValue())))
            .andExpect(jsonPath("$.[*].loginDate").value(hasItem(DEFAULT_LOGIN_DATE.toString())))
            .andExpect(jsonPath("$.[*].logoutDate").value(hasItem(DEFAULT_LOGOUT_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getUserConnection() throws Exception {
        // Initialize the database
        userConnectionRepository.saveAndFlush(userConnection);

        // Get the userConnection
        restUserConnectionMockMvc.perform(get("/api/user-connections/{id}", userConnection.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userConnection.getId().intValue()))
            .andExpect(jsonPath("$.loginDate").value(DEFAULT_LOGIN_DATE.toString()))
            .andExpect(jsonPath("$.logoutDate").value(DEFAULT_LOGOUT_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUserConnection() throws Exception {
        // Get the userConnection
        restUserConnectionMockMvc.perform(get("/api/user-connections/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserConnection() throws Exception {
        // Initialize the database
        userConnectionRepository.saveAndFlush(userConnection);

        int databaseSizeBeforeUpdate = userConnectionRepository.findAll().size();

        // Update the userConnection
        UserConnection updatedUserConnection = userConnectionRepository.findById(userConnection.getId()).get();
        // Disconnect from session so that the updates on updatedUserConnection are not directly saved in db
        em.detach(updatedUserConnection);
        updatedUserConnection
            .loginDate(UPDATED_LOGIN_DATE)
            .logoutDate(UPDATED_LOGOUT_DATE);
        UserConnectionDTO userConnectionDTO = userConnectionMapper.toDto(updatedUserConnection);

        restUserConnectionMockMvc.perform(put("/api/user-connections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userConnectionDTO)))
            .andExpect(status().isOk());

        // Validate the UserConnection in the database
        List<UserConnection> userConnectionList = userConnectionRepository.findAll();
        assertThat(userConnectionList).hasSize(databaseSizeBeforeUpdate);
        UserConnection testUserConnection = userConnectionList.get(userConnectionList.size() - 1);
        assertThat(testUserConnection.getLoginDate()).isEqualTo(UPDATED_LOGIN_DATE);
        assertThat(testUserConnection.getLogoutDate()).isEqualTo(UPDATED_LOGOUT_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingUserConnection() throws Exception {
        int databaseSizeBeforeUpdate = userConnectionRepository.findAll().size();

        // Create the UserConnection
        UserConnectionDTO userConnectionDTO = userConnectionMapper.toDto(userConnection);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserConnectionMockMvc.perform(put("/api/user-connections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userConnectionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserConnection in the database
        List<UserConnection> userConnectionList = userConnectionRepository.findAll();
        assertThat(userConnectionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserConnection() throws Exception {
        // Initialize the database
        userConnectionRepository.saveAndFlush(userConnection);

        int databaseSizeBeforeDelete = userConnectionRepository.findAll().size();

        // Delete the userConnection
        restUserConnectionMockMvc.perform(delete("/api/user-connections/{id}", userConnection.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserConnection> userConnectionList = userConnectionRepository.findAll();
        assertThat(userConnectionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserConnection.class);
        UserConnection userConnection1 = new UserConnection();
        userConnection1.setId(1L);
        UserConnection userConnection2 = new UserConnection();
        userConnection2.setId(userConnection1.getId());
        assertThat(userConnection1).isEqualTo(userConnection2);
        userConnection2.setId(2L);
        assertThat(userConnection1).isNotEqualTo(userConnection2);
        userConnection1.setId(null);
        assertThat(userConnection1).isNotEqualTo(userConnection2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserConnectionDTO.class);
        UserConnectionDTO userConnectionDTO1 = new UserConnectionDTO();
        userConnectionDTO1.setId(1L);
        UserConnectionDTO userConnectionDTO2 = new UserConnectionDTO();
        assertThat(userConnectionDTO1).isNotEqualTo(userConnectionDTO2);
        userConnectionDTO2.setId(userConnectionDTO1.getId());
        assertThat(userConnectionDTO1).isEqualTo(userConnectionDTO2);
        userConnectionDTO2.setId(2L);
        assertThat(userConnectionDTO1).isNotEqualTo(userConnectionDTO2);
        userConnectionDTO1.setId(null);
        assertThat(userConnectionDTO1).isNotEqualTo(userConnectionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(userConnectionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(userConnectionMapper.fromId(null)).isNull();
    }
}
