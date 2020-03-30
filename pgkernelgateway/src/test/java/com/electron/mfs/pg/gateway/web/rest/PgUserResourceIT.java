package com.electron.mfs.pg.gateway.web.rest;

import com.electron.mfs.pg.gateway.PgkernelgatewayApp;
import com.electron.mfs.pg.gateway.config.SecurityBeanOverrideConfiguration;
import com.electron.mfs.pg.gateway.domain.PgUser;
import com.electron.mfs.pg.gateway.repository.PgUserRepository;
import com.electron.mfs.pg.gateway.service.PgUserService;
import com.electron.mfs.pg.gateway.service.dto.PgUserDTO;
import com.electron.mfs.pg.gateway.service.mapper.PgUserMapper;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.electron.mfs.pg.gateway.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link PgUserResource} REST controller.
 */
@EmbeddedKafka
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, PgkernelgatewayApp.class})
public class PgUserResourceIT {

    private static final String DEFAULT_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_USERNAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "7@*.!v";
    private static final String UPDATED_EMAIL = "P?@r}.3";

    private static final String DEFAULT_FIRSTNAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRSTNAME = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MSISDN = "AAAAAAAAAA";
    private static final String UPDATED_MSISDN = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private PgUserRepository pgUserRepository;

    @Autowired
    private PgUserMapper pgUserMapper;

    @Autowired
    private PgUserService pgUserService;

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

    private MockMvc restPgUserMockMvc;

    private PgUser pgUser;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PgUserResource pgUserResource = new PgUserResource(pgUserService);
        this.restPgUserMockMvc = MockMvcBuilders.standaloneSetup(pgUserResource)
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
    public static PgUser createEntity(EntityManager em) {
        PgUser pgUser = new PgUser()
            .username(DEFAULT_USERNAME)
            .email(DEFAULT_EMAIL)
            .firstname(DEFAULT_FIRSTNAME)
            .name(DEFAULT_NAME)
            .msisdn(DEFAULT_MSISDN)
            .creationDate(DEFAULT_CREATION_DATE)
            .updateDate(DEFAULT_UPDATE_DATE);
        return pgUser;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PgUser createUpdatedEntity(EntityManager em) {
        PgUser pgUser = new PgUser()
            .username(UPDATED_USERNAME)
            .email(UPDATED_EMAIL)
            .firstname(UPDATED_FIRSTNAME)
            .name(UPDATED_NAME)
            .msisdn(UPDATED_MSISDN)
            .creationDate(UPDATED_CREATION_DATE)
            .updateDate(UPDATED_UPDATE_DATE);
        return pgUser;
    }

    @BeforeEach
    public void initTest() {
        pgUser = createEntity(em);
    }

    @Test
    @Transactional
    public void createPgUser() throws Exception {
        int databaseSizeBeforeCreate = pgUserRepository.findAll().size();

        // Create the PgUser
        PgUserDTO pgUserDTO = pgUserMapper.toDto(pgUser);
        restPgUserMockMvc.perform(post("/api/pg-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgUserDTO)))
            .andExpect(status().isCreated());

        // Validate the PgUser in the database
        List<PgUser> pgUserList = pgUserRepository.findAll();
        assertThat(pgUserList).hasSize(databaseSizeBeforeCreate + 1);
        PgUser testPgUser = pgUserList.get(pgUserList.size() - 1);
        assertThat(testPgUser.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testPgUser.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testPgUser.getFirstname()).isEqualTo(DEFAULT_FIRSTNAME);
        assertThat(testPgUser.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPgUser.getMsisdn()).isEqualTo(DEFAULT_MSISDN);
        assertThat(testPgUser.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testPgUser.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void createPgUserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pgUserRepository.findAll().size();

        // Create the PgUser with an existing ID
        pgUser.setId(1L);
        PgUserDTO pgUserDTO = pgUserMapper.toDto(pgUser);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPgUserMockMvc.perform(post("/api/pg-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgUserDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PgUser in the database
        List<PgUser> pgUserList = pgUserRepository.findAll();
        assertThat(pgUserList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUsernameIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgUserRepository.findAll().size();
        // set the field null
        pgUser.setUsername(null);

        // Create the PgUser, which fails.
        PgUserDTO pgUserDTO = pgUserMapper.toDto(pgUser);

        restPgUserMockMvc.perform(post("/api/pg-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgUserDTO)))
            .andExpect(status().isBadRequest());

        List<PgUser> pgUserList = pgUserRepository.findAll();
        assertThat(pgUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgUserRepository.findAll().size();
        // set the field null
        pgUser.setEmail(null);

        // Create the PgUser, which fails.
        PgUserDTO pgUserDTO = pgUserMapper.toDto(pgUser);

        restPgUserMockMvc.perform(post("/api/pg-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgUserDTO)))
            .andExpect(status().isBadRequest());

        List<PgUser> pgUserList = pgUserRepository.findAll();
        assertThat(pgUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = pgUserRepository.findAll().size();
        // set the field null
        pgUser.setCreationDate(null);

        // Create the PgUser, which fails.
        PgUserDTO pgUserDTO = pgUserMapper.toDto(pgUser);

        restPgUserMockMvc.perform(post("/api/pg-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgUserDTO)))
            .andExpect(status().isBadRequest());

        List<PgUser> pgUserList = pgUserRepository.findAll();
        assertThat(pgUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPgUsers() throws Exception {
        // Initialize the database
        pgUserRepository.saveAndFlush(pgUser);

        // Get all the pgUserList
        restPgUserMockMvc.perform(get("/api/pg-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pgUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].firstname").value(hasItem(DEFAULT_FIRSTNAME.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].msisdn").value(hasItem(DEFAULT_MSISDN.toString())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getPgUser() throws Exception {
        // Initialize the database
        pgUserRepository.saveAndFlush(pgUser);

        // Get the pgUser
        restPgUserMockMvc.perform(get("/api/pg-users/{id}", pgUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pgUser.getId().intValue()))
            .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.firstname").value(DEFAULT_FIRSTNAME.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.msisdn").value(DEFAULT_MSISDN.toString()))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPgUser() throws Exception {
        // Get the pgUser
        restPgUserMockMvc.perform(get("/api/pg-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePgUser() throws Exception {
        // Initialize the database
        pgUserRepository.saveAndFlush(pgUser);

        int databaseSizeBeforeUpdate = pgUserRepository.findAll().size();

        // Update the pgUser
        PgUser updatedPgUser = pgUserRepository.findById(pgUser.getId()).get();
        // Disconnect from session so that the updates on updatedPgUser are not directly saved in db
        em.detach(updatedPgUser);
        updatedPgUser
            .username(UPDATED_USERNAME)
            .email(UPDATED_EMAIL)
            .firstname(UPDATED_FIRSTNAME)
            .name(UPDATED_NAME)
            .msisdn(UPDATED_MSISDN)
            .creationDate(UPDATED_CREATION_DATE)
            .updateDate(UPDATED_UPDATE_DATE);
        PgUserDTO pgUserDTO = pgUserMapper.toDto(updatedPgUser);

        restPgUserMockMvc.perform(put("/api/pg-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgUserDTO)))
            .andExpect(status().isOk());

        // Validate the PgUser in the database
        List<PgUser> pgUserList = pgUserRepository.findAll();
        assertThat(pgUserList).hasSize(databaseSizeBeforeUpdate);
        PgUser testPgUser = pgUserList.get(pgUserList.size() - 1);
        assertThat(testPgUser.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testPgUser.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testPgUser.getFirstname()).isEqualTo(UPDATED_FIRSTNAME);
        assertThat(testPgUser.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPgUser.getMsisdn()).isEqualTo(UPDATED_MSISDN);
        assertThat(testPgUser.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testPgUser.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingPgUser() throws Exception {
        int databaseSizeBeforeUpdate = pgUserRepository.findAll().size();

        // Create the PgUser
        PgUserDTO pgUserDTO = pgUserMapper.toDto(pgUser);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPgUserMockMvc.perform(put("/api/pg-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pgUserDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PgUser in the database
        List<PgUser> pgUserList = pgUserRepository.findAll();
        assertThat(pgUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePgUser() throws Exception {
        // Initialize the database
        pgUserRepository.saveAndFlush(pgUser);

        int databaseSizeBeforeDelete = pgUserRepository.findAll().size();

        // Delete the pgUser
        restPgUserMockMvc.perform(delete("/api/pg-users/{id}", pgUser.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PgUser> pgUserList = pgUserRepository.findAll();
        assertThat(pgUserList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PgUser.class);
        PgUser pgUser1 = new PgUser();
        pgUser1.setId(1L);
        PgUser pgUser2 = new PgUser();
        pgUser2.setId(pgUser1.getId());
        assertThat(pgUser1).isEqualTo(pgUser2);
        pgUser2.setId(2L);
        assertThat(pgUser1).isNotEqualTo(pgUser2);
        pgUser1.setId(null);
        assertThat(pgUser1).isNotEqualTo(pgUser2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PgUserDTO.class);
        PgUserDTO pgUserDTO1 = new PgUserDTO();
        pgUserDTO1.setId(1L);
        PgUserDTO pgUserDTO2 = new PgUserDTO();
        assertThat(pgUserDTO1).isNotEqualTo(pgUserDTO2);
        pgUserDTO2.setId(pgUserDTO1.getId());
        assertThat(pgUserDTO1).isEqualTo(pgUserDTO2);
        pgUserDTO2.setId(2L);
        assertThat(pgUserDTO1).isNotEqualTo(pgUserDTO2);
        pgUserDTO1.setId(null);
        assertThat(pgUserDTO1).isNotEqualTo(pgUserDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(pgUserMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(pgUserMapper.fromId(null)).isNull();
    }
}
