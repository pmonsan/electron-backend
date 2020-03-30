package com.electron.mfs.pg.subscription.web.rest;

import com.electron.mfs.pg.subscription.SubscriptionManagerApp;
import com.electron.mfs.pg.subscription.config.SecurityBeanOverrideConfiguration;
import com.electron.mfs.pg.subscription.domain.Partner;
import com.electron.mfs.pg.subscription.repository.PartnerRepository;
import com.electron.mfs.pg.subscription.service.PartnerService;
import com.electron.mfs.pg.subscription.service.dto.PartnerDTO;
import com.electron.mfs.pg.subscription.service.mapper.PartnerMapper;
import com.electron.mfs.pg.subscription.web.rest.errors.ExceptionTranslator;

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

import static com.electron.mfs.pg.subscription.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link PartnerResource} REST controller.
 */
@EmbeddedKafka
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, SubscriptionManagerApp.class})
public class PartnerResourceIT {

    private static final String DEFAULT_CODE = "AAAAA";
    private static final String UPDATED_CODE = "BBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_API_KEY = "AAAAAAAAAA";
    private static final String UPDATED_API_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_POSTAL_CODE = "AAAAAAAAAA";
    private static final String UPDATED_POSTAL_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY_CODE = "AAAAA";
    private static final String UPDATED_COUNTRY_CODE = "BBBBB";

    private static final String DEFAULT_RSA_PUBLIC_KEY_PATH = "AAAAAAAAAA";
    private static final String UPDATED_RSA_PUBLIC_KEY_PATH = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT_FIRSTNAME = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_FIRSTNAME = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT_LASTNAME = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_LASTNAME = "BBBBBBBBBB";

    private static final String DEFAULT_BUSINESS_EMAIL = "Z@p.v2";
    private static final String UPDATED_BUSINESS_EMAIL = "D1@'.[N";

    private static final String DEFAULT_BUSINESS_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_BUSINESS_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_PARTNER_TYPE_CODE = "AAAAA";
    private static final String UPDATED_PARTNER_TYPE_CODE = "BBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private PartnerRepository partnerRepository;

    @Autowired
    private PartnerMapper partnerMapper;

    @Autowired
    private PartnerService partnerService;

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

    private MockMvc restPartnerMockMvc;

    private Partner partner;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PartnerResource partnerResource = new PartnerResource(partnerService);
        this.restPartnerMockMvc = MockMvcBuilders.standaloneSetup(partnerResource)
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
    public static Partner createEntity(EntityManager em) {
        Partner partner = new Partner()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .apiKey(DEFAULT_API_KEY)
            .address(DEFAULT_ADDRESS)
            .city(DEFAULT_CITY)
            .postalCode(DEFAULT_POSTAL_CODE)
            .countryCode(DEFAULT_COUNTRY_CODE)
            .rsaPublicKeyPath(DEFAULT_RSA_PUBLIC_KEY_PATH)
            .contactFirstname(DEFAULT_CONTACT_FIRSTNAME)
            .contactLastname(DEFAULT_CONTACT_LASTNAME)
            .businessEmail(DEFAULT_BUSINESS_EMAIL)
            .businessPhone(DEFAULT_BUSINESS_PHONE)
            .partnerTypeCode(DEFAULT_PARTNER_TYPE_CODE)
            .active(DEFAULT_ACTIVE);
        return partner;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Partner createUpdatedEntity(EntityManager em) {
        Partner partner = new Partner()
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .apiKey(UPDATED_API_KEY)
            .address(UPDATED_ADDRESS)
            .city(UPDATED_CITY)
            .postalCode(UPDATED_POSTAL_CODE)
            .countryCode(UPDATED_COUNTRY_CODE)
            .rsaPublicKeyPath(UPDATED_RSA_PUBLIC_KEY_PATH)
            .contactFirstname(UPDATED_CONTACT_FIRSTNAME)
            .contactLastname(UPDATED_CONTACT_LASTNAME)
            .businessEmail(UPDATED_BUSINESS_EMAIL)
            .businessPhone(UPDATED_BUSINESS_PHONE)
            .partnerTypeCode(UPDATED_PARTNER_TYPE_CODE)
            .active(UPDATED_ACTIVE);
        return partner;
    }

    @BeforeEach
    public void initTest() {
        partner = createEntity(em);
    }

    @Test
    @Transactional
    public void createPartner() throws Exception {
        int databaseSizeBeforeCreate = partnerRepository.findAll().size();

        // Create the Partner
        PartnerDTO partnerDTO = partnerMapper.toDto(partner);
        restPartnerMockMvc.perform(post("/api/partners")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partnerDTO)))
            .andExpect(status().isCreated());

        // Validate the Partner in the database
        List<Partner> partnerList = partnerRepository.findAll();
        assertThat(partnerList).hasSize(databaseSizeBeforeCreate + 1);
        Partner testPartner = partnerList.get(partnerList.size() - 1);
        assertThat(testPartner.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testPartner.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPartner.getApiKey()).isEqualTo(DEFAULT_API_KEY);
        assertThat(testPartner.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testPartner.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testPartner.getPostalCode()).isEqualTo(DEFAULT_POSTAL_CODE);
        assertThat(testPartner.getCountryCode()).isEqualTo(DEFAULT_COUNTRY_CODE);
        assertThat(testPartner.getRsaPublicKeyPath()).isEqualTo(DEFAULT_RSA_PUBLIC_KEY_PATH);
        assertThat(testPartner.getContactFirstname()).isEqualTo(DEFAULT_CONTACT_FIRSTNAME);
        assertThat(testPartner.getContactLastname()).isEqualTo(DEFAULT_CONTACT_LASTNAME);
        assertThat(testPartner.getBusinessEmail()).isEqualTo(DEFAULT_BUSINESS_EMAIL);
        assertThat(testPartner.getBusinessPhone()).isEqualTo(DEFAULT_BUSINESS_PHONE);
        assertThat(testPartner.getPartnerTypeCode()).isEqualTo(DEFAULT_PARTNER_TYPE_CODE);
        assertThat(testPartner.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createPartnerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = partnerRepository.findAll().size();

        // Create the Partner with an existing ID
        partner.setId(1L);
        PartnerDTO partnerDTO = partnerMapper.toDto(partner);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPartnerMockMvc.perform(post("/api/partners")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partnerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Partner in the database
        List<Partner> partnerList = partnerRepository.findAll();
        assertThat(partnerList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = partnerRepository.findAll().size();
        // set the field null
        partner.setCode(null);

        // Create the Partner, which fails.
        PartnerDTO partnerDTO = partnerMapper.toDto(partner);

        restPartnerMockMvc.perform(post("/api/partners")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partnerDTO)))
            .andExpect(status().isBadRequest());

        List<Partner> partnerList = partnerRepository.findAll();
        assertThat(partnerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = partnerRepository.findAll().size();
        // set the field null
        partner.setName(null);

        // Create the Partner, which fails.
        PartnerDTO partnerDTO = partnerMapper.toDto(partner);

        restPartnerMockMvc.perform(post("/api/partners")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partnerDTO)))
            .andExpect(status().isBadRequest());

        List<Partner> partnerList = partnerRepository.findAll();
        assertThat(partnerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkApiKeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = partnerRepository.findAll().size();
        // set the field null
        partner.setApiKey(null);

        // Create the Partner, which fails.
        PartnerDTO partnerDTO = partnerMapper.toDto(partner);

        restPartnerMockMvc.perform(post("/api/partners")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partnerDTO)))
            .andExpect(status().isBadRequest());

        List<Partner> partnerList = partnerRepository.findAll();
        assertThat(partnerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkContactFirstnameIsRequired() throws Exception {
        int databaseSizeBeforeTest = partnerRepository.findAll().size();
        // set the field null
        partner.setContactFirstname(null);

        // Create the Partner, which fails.
        PartnerDTO partnerDTO = partnerMapper.toDto(partner);

        restPartnerMockMvc.perform(post("/api/partners")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partnerDTO)))
            .andExpect(status().isBadRequest());

        List<Partner> partnerList = partnerRepository.findAll();
        assertThat(partnerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkContactLastnameIsRequired() throws Exception {
        int databaseSizeBeforeTest = partnerRepository.findAll().size();
        // set the field null
        partner.setContactLastname(null);

        // Create the Partner, which fails.
        PartnerDTO partnerDTO = partnerMapper.toDto(partner);

        restPartnerMockMvc.perform(post("/api/partners")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partnerDTO)))
            .andExpect(status().isBadRequest());

        List<Partner> partnerList = partnerRepository.findAll();
        assertThat(partnerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBusinessEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = partnerRepository.findAll().size();
        // set the field null
        partner.setBusinessEmail(null);

        // Create the Partner, which fails.
        PartnerDTO partnerDTO = partnerMapper.toDto(partner);

        restPartnerMockMvc.perform(post("/api/partners")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partnerDTO)))
            .andExpect(status().isBadRequest());

        List<Partner> partnerList = partnerRepository.findAll();
        assertThat(partnerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBusinessPhoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = partnerRepository.findAll().size();
        // set the field null
        partner.setBusinessPhone(null);

        // Create the Partner, which fails.
        PartnerDTO partnerDTO = partnerMapper.toDto(partner);

        restPartnerMockMvc.perform(post("/api/partners")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partnerDTO)))
            .andExpect(status().isBadRequest());

        List<Partner> partnerList = partnerRepository.findAll();
        assertThat(partnerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = partnerRepository.findAll().size();
        // set the field null
        partner.setActive(null);

        // Create the Partner, which fails.
        PartnerDTO partnerDTO = partnerMapper.toDto(partner);

        restPartnerMockMvc.perform(post("/api/partners")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partnerDTO)))
            .andExpect(status().isBadRequest());

        List<Partner> partnerList = partnerRepository.findAll();
        assertThat(partnerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPartners() throws Exception {
        // Initialize the database
        partnerRepository.saveAndFlush(partner);

        // Get all the partnerList
        restPartnerMockMvc.perform(get("/api/partners?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(partner.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].apiKey").value(hasItem(DEFAULT_API_KEY.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
            .andExpect(jsonPath("$.[*].postalCode").value(hasItem(DEFAULT_POSTAL_CODE.toString())))
            .andExpect(jsonPath("$.[*].countryCode").value(hasItem(DEFAULT_COUNTRY_CODE.toString())))
            .andExpect(jsonPath("$.[*].rsaPublicKeyPath").value(hasItem(DEFAULT_RSA_PUBLIC_KEY_PATH.toString())))
            .andExpect(jsonPath("$.[*].contactFirstname").value(hasItem(DEFAULT_CONTACT_FIRSTNAME.toString())))
            .andExpect(jsonPath("$.[*].contactLastname").value(hasItem(DEFAULT_CONTACT_LASTNAME.toString())))
            .andExpect(jsonPath("$.[*].businessEmail").value(hasItem(DEFAULT_BUSINESS_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].businessPhone").value(hasItem(DEFAULT_BUSINESS_PHONE.toString())))
            .andExpect(jsonPath("$.[*].partnerTypeCode").value(hasItem(DEFAULT_PARTNER_TYPE_CODE.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getPartner() throws Exception {
        // Initialize the database
        partnerRepository.saveAndFlush(partner);

        // Get the partner
        restPartnerMockMvc.perform(get("/api/partners/{id}", partner.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(partner.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.apiKey").value(DEFAULT_API_KEY.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()))
            .andExpect(jsonPath("$.postalCode").value(DEFAULT_POSTAL_CODE.toString()))
            .andExpect(jsonPath("$.countryCode").value(DEFAULT_COUNTRY_CODE.toString()))
            .andExpect(jsonPath("$.rsaPublicKeyPath").value(DEFAULT_RSA_PUBLIC_KEY_PATH.toString()))
            .andExpect(jsonPath("$.contactFirstname").value(DEFAULT_CONTACT_FIRSTNAME.toString()))
            .andExpect(jsonPath("$.contactLastname").value(DEFAULT_CONTACT_LASTNAME.toString()))
            .andExpect(jsonPath("$.businessEmail").value(DEFAULT_BUSINESS_EMAIL.toString()))
            .andExpect(jsonPath("$.businessPhone").value(DEFAULT_BUSINESS_PHONE.toString()))
            .andExpect(jsonPath("$.partnerTypeCode").value(DEFAULT_PARTNER_TYPE_CODE.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPartner() throws Exception {
        // Get the partner
        restPartnerMockMvc.perform(get("/api/partners/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePartner() throws Exception {
        // Initialize the database
        partnerRepository.saveAndFlush(partner);

        int databaseSizeBeforeUpdate = partnerRepository.findAll().size();

        // Update the partner
        Partner updatedPartner = partnerRepository.findById(partner.getId()).get();
        // Disconnect from session so that the updates on updatedPartner are not directly saved in db
        em.detach(updatedPartner);
        updatedPartner
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .apiKey(UPDATED_API_KEY)
            .address(UPDATED_ADDRESS)
            .city(UPDATED_CITY)
            .postalCode(UPDATED_POSTAL_CODE)
            .countryCode(UPDATED_COUNTRY_CODE)
            .rsaPublicKeyPath(UPDATED_RSA_PUBLIC_KEY_PATH)
            .contactFirstname(UPDATED_CONTACT_FIRSTNAME)
            .contactLastname(UPDATED_CONTACT_LASTNAME)
            .businessEmail(UPDATED_BUSINESS_EMAIL)
            .businessPhone(UPDATED_BUSINESS_PHONE)
            .partnerTypeCode(UPDATED_PARTNER_TYPE_CODE)
            .active(UPDATED_ACTIVE);
        PartnerDTO partnerDTO = partnerMapper.toDto(updatedPartner);

        restPartnerMockMvc.perform(put("/api/partners")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partnerDTO)))
            .andExpect(status().isOk());

        // Validate the Partner in the database
        List<Partner> partnerList = partnerRepository.findAll();
        assertThat(partnerList).hasSize(databaseSizeBeforeUpdate);
        Partner testPartner = partnerList.get(partnerList.size() - 1);
        assertThat(testPartner.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testPartner.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPartner.getApiKey()).isEqualTo(UPDATED_API_KEY);
        assertThat(testPartner.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testPartner.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testPartner.getPostalCode()).isEqualTo(UPDATED_POSTAL_CODE);
        assertThat(testPartner.getCountryCode()).isEqualTo(UPDATED_COUNTRY_CODE);
        assertThat(testPartner.getRsaPublicKeyPath()).isEqualTo(UPDATED_RSA_PUBLIC_KEY_PATH);
        assertThat(testPartner.getContactFirstname()).isEqualTo(UPDATED_CONTACT_FIRSTNAME);
        assertThat(testPartner.getContactLastname()).isEqualTo(UPDATED_CONTACT_LASTNAME);
        assertThat(testPartner.getBusinessEmail()).isEqualTo(UPDATED_BUSINESS_EMAIL);
        assertThat(testPartner.getBusinessPhone()).isEqualTo(UPDATED_BUSINESS_PHONE);
        assertThat(testPartner.getPartnerTypeCode()).isEqualTo(UPDATED_PARTNER_TYPE_CODE);
        assertThat(testPartner.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingPartner() throws Exception {
        int databaseSizeBeforeUpdate = partnerRepository.findAll().size();

        // Create the Partner
        PartnerDTO partnerDTO = partnerMapper.toDto(partner);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPartnerMockMvc.perform(put("/api/partners")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(partnerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Partner in the database
        List<Partner> partnerList = partnerRepository.findAll();
        assertThat(partnerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePartner() throws Exception {
        // Initialize the database
        partnerRepository.saveAndFlush(partner);

        int databaseSizeBeforeDelete = partnerRepository.findAll().size();

        // Delete the partner
        restPartnerMockMvc.perform(delete("/api/partners/{id}", partner.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Partner> partnerList = partnerRepository.findAll();
        assertThat(partnerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Partner.class);
        Partner partner1 = new Partner();
        partner1.setId(1L);
        Partner partner2 = new Partner();
        partner2.setId(partner1.getId());
        assertThat(partner1).isEqualTo(partner2);
        partner2.setId(2L);
        assertThat(partner1).isNotEqualTo(partner2);
        partner1.setId(null);
        assertThat(partner1).isNotEqualTo(partner2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PartnerDTO.class);
        PartnerDTO partnerDTO1 = new PartnerDTO();
        partnerDTO1.setId(1L);
        PartnerDTO partnerDTO2 = new PartnerDTO();
        assertThat(partnerDTO1).isNotEqualTo(partnerDTO2);
        partnerDTO2.setId(partnerDTO1.getId());
        assertThat(partnerDTO1).isEqualTo(partnerDTO2);
        partnerDTO2.setId(2L);
        assertThat(partnerDTO1).isNotEqualTo(partnerDTO2);
        partnerDTO1.setId(null);
        assertThat(partnerDTO1).isNotEqualTo(partnerDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(partnerMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(partnerMapper.fromId(null)).isNull();
    }
}
