package inc.donau.storage.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import inc.donau.storage.IntegrationTest;
import inc.donau.storage.domain.ContactInfo;
import inc.donau.storage.domain.LegalEntity;
import inc.donau.storage.domain.Person;
import inc.donau.storage.repository.ContactInfoRepository;
import inc.donau.storage.service.criteria.ContactInfoCriteria;
import inc.donau.storage.service.dto.ContactInfoDTO;
import inc.donau.storage.service.mapper.ContactInfoMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ContactInfoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ContactInfoResourceIT {

    private static final String DEFAULT_EMAIL = "`G<\\uV@7.U%j?p";
    private static final String UPDATED_EMAIL = "Yf5yv@N.GrbL";

    private static final String DEFAULT_PHONE_NUMBER = "+523003494";
    private static final String UPDATED_PHONE_NUMBER = "05273047092";

    private static final String ENTITY_API_URL = "/api/contact-infos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ContactInfoRepository contactInfoRepository;

    @Autowired
    private ContactInfoMapper contactInfoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restContactInfoMockMvc;

    private ContactInfo contactInfo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContactInfo createEntity(EntityManager em) {
        ContactInfo contactInfo = new ContactInfo().email(DEFAULT_EMAIL).phoneNumber(DEFAULT_PHONE_NUMBER);
        return contactInfo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContactInfo createUpdatedEntity(EntityManager em) {
        ContactInfo contactInfo = new ContactInfo().email(UPDATED_EMAIL).phoneNumber(UPDATED_PHONE_NUMBER);
        return contactInfo;
    }

    @BeforeEach
    public void initTest() {
        contactInfo = createEntity(em);
    }

    @Test
    @Transactional
    void createContactInfo() throws Exception {
        int databaseSizeBeforeCreate = contactInfoRepository.findAll().size();
        // Create the ContactInfo
        ContactInfoDTO contactInfoDTO = contactInfoMapper.toDto(contactInfo);
        restContactInfoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contactInfoDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ContactInfo in the database
        List<ContactInfo> contactInfoList = contactInfoRepository.findAll();
        assertThat(contactInfoList).hasSize(databaseSizeBeforeCreate + 1);
        ContactInfo testContactInfo = contactInfoList.get(contactInfoList.size() - 1);
        assertThat(testContactInfo.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testContactInfo.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void createContactInfoWithExistingId() throws Exception {
        // Create the ContactInfo with an existing ID
        contactInfo.setId(1L);
        ContactInfoDTO contactInfoDTO = contactInfoMapper.toDto(contactInfo);

        int databaseSizeBeforeCreate = contactInfoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restContactInfoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contactInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContactInfo in the database
        List<ContactInfo> contactInfoList = contactInfoRepository.findAll();
        assertThat(contactInfoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = contactInfoRepository.findAll().size();
        // set the field null
        contactInfo.setEmail(null);

        // Create the ContactInfo, which fails.
        ContactInfoDTO contactInfoDTO = contactInfoMapper.toDto(contactInfo);

        restContactInfoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contactInfoDTO))
            )
            .andExpect(status().isBadRequest());

        List<ContactInfo> contactInfoList = contactInfoRepository.findAll();
        assertThat(contactInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllContactInfos() throws Exception {
        // Initialize the database
        contactInfoRepository.saveAndFlush(contactInfo);

        // Get all the contactInfoList
        restContactInfoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contactInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)));
    }

    @Test
    @Transactional
    void getContactInfo() throws Exception {
        // Initialize the database
        contactInfoRepository.saveAndFlush(contactInfo);

        // Get the contactInfo
        restContactInfoMockMvc
            .perform(get(ENTITY_API_URL_ID, contactInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(contactInfo.getId().intValue()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER));
    }

    @Test
    @Transactional
    void getContactInfosByIdFiltering() throws Exception {
        // Initialize the database
        contactInfoRepository.saveAndFlush(contactInfo);

        Long id = contactInfo.getId();

        defaultContactInfoShouldBeFound("id.equals=" + id);
        defaultContactInfoShouldNotBeFound("id.notEquals=" + id);

        defaultContactInfoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultContactInfoShouldNotBeFound("id.greaterThan=" + id);

        defaultContactInfoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultContactInfoShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllContactInfosByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        contactInfoRepository.saveAndFlush(contactInfo);

        // Get all the contactInfoList where email equals to DEFAULT_EMAIL
        defaultContactInfoShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the contactInfoList where email equals to UPDATED_EMAIL
        defaultContactInfoShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllContactInfosByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        contactInfoRepository.saveAndFlush(contactInfo);

        // Get all the contactInfoList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultContactInfoShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the contactInfoList where email equals to UPDATED_EMAIL
        defaultContactInfoShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllContactInfosByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        contactInfoRepository.saveAndFlush(contactInfo);

        // Get all the contactInfoList where email is not null
        defaultContactInfoShouldBeFound("email.specified=true");

        // Get all the contactInfoList where email is null
        defaultContactInfoShouldNotBeFound("email.specified=false");
    }

    @Test
    @Transactional
    void getAllContactInfosByEmailContainsSomething() throws Exception {
        // Initialize the database
        contactInfoRepository.saveAndFlush(contactInfo);

        // Get all the contactInfoList where email contains DEFAULT_EMAIL
        defaultContactInfoShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the contactInfoList where email contains UPDATED_EMAIL
        defaultContactInfoShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllContactInfosByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        contactInfoRepository.saveAndFlush(contactInfo);

        // Get all the contactInfoList where email does not contain DEFAULT_EMAIL
        defaultContactInfoShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the contactInfoList where email does not contain UPDATED_EMAIL
        defaultContactInfoShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllContactInfosByPhoneNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        contactInfoRepository.saveAndFlush(contactInfo);

        // Get all the contactInfoList where phoneNumber equals to DEFAULT_PHONE_NUMBER
        defaultContactInfoShouldBeFound("phoneNumber.equals=" + DEFAULT_PHONE_NUMBER);

        // Get all the contactInfoList where phoneNumber equals to UPDATED_PHONE_NUMBER
        defaultContactInfoShouldNotBeFound("phoneNumber.equals=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllContactInfosByPhoneNumberIsInShouldWork() throws Exception {
        // Initialize the database
        contactInfoRepository.saveAndFlush(contactInfo);

        // Get all the contactInfoList where phoneNumber in DEFAULT_PHONE_NUMBER or UPDATED_PHONE_NUMBER
        defaultContactInfoShouldBeFound("phoneNumber.in=" + DEFAULT_PHONE_NUMBER + "," + UPDATED_PHONE_NUMBER);

        // Get all the contactInfoList where phoneNumber equals to UPDATED_PHONE_NUMBER
        defaultContactInfoShouldNotBeFound("phoneNumber.in=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllContactInfosByPhoneNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        contactInfoRepository.saveAndFlush(contactInfo);

        // Get all the contactInfoList where phoneNumber is not null
        defaultContactInfoShouldBeFound("phoneNumber.specified=true");

        // Get all the contactInfoList where phoneNumber is null
        defaultContactInfoShouldNotBeFound("phoneNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllContactInfosByPhoneNumberContainsSomething() throws Exception {
        // Initialize the database
        contactInfoRepository.saveAndFlush(contactInfo);

        // Get all the contactInfoList where phoneNumber contains DEFAULT_PHONE_NUMBER
        defaultContactInfoShouldBeFound("phoneNumber.contains=" + DEFAULT_PHONE_NUMBER);

        // Get all the contactInfoList where phoneNumber contains UPDATED_PHONE_NUMBER
        defaultContactInfoShouldNotBeFound("phoneNumber.contains=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllContactInfosByPhoneNumberNotContainsSomething() throws Exception {
        // Initialize the database
        contactInfoRepository.saveAndFlush(contactInfo);

        // Get all the contactInfoList where phoneNumber does not contain DEFAULT_PHONE_NUMBER
        defaultContactInfoShouldNotBeFound("phoneNumber.doesNotContain=" + DEFAULT_PHONE_NUMBER);

        // Get all the contactInfoList where phoneNumber does not contain UPDATED_PHONE_NUMBER
        defaultContactInfoShouldBeFound("phoneNumber.doesNotContain=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllContactInfosByPersonIsEqualToSomething() throws Exception {
        Person person;
        if (TestUtil.findAll(em, Person.class).isEmpty()) {
            contactInfoRepository.saveAndFlush(contactInfo);
            person = PersonResourceIT.createEntity(em);
        } else {
            person = TestUtil.findAll(em, Person.class).get(0);
        }
        em.persist(person);
        em.flush();
        contactInfo.setPerson(person);
        person.setContactInfo(contactInfo);
        contactInfoRepository.saveAndFlush(contactInfo);
        Long personId = person.getId();

        // Get all the contactInfoList where person equals to personId
        defaultContactInfoShouldBeFound("personId.equals=" + personId);

        // Get all the contactInfoList where person equals to (personId + 1)
        defaultContactInfoShouldNotBeFound("personId.equals=" + (personId + 1));
    }

    @Test
    @Transactional
    void getAllContactInfosByLegalEntityIsEqualToSomething() throws Exception {
        LegalEntity legalEntity;
        if (TestUtil.findAll(em, LegalEntity.class).isEmpty()) {
            contactInfoRepository.saveAndFlush(contactInfo);
            legalEntity = LegalEntityResourceIT.createEntity(em);
        } else {
            legalEntity = TestUtil.findAll(em, LegalEntity.class).get(0);
        }
        em.persist(legalEntity);
        em.flush();
        contactInfo.setLegalEntity(legalEntity);
        legalEntity.setContactInfo(contactInfo);
        contactInfoRepository.saveAndFlush(contactInfo);
        Long legalEntityId = legalEntity.getId();

        // Get all the contactInfoList where legalEntity equals to legalEntityId
        defaultContactInfoShouldBeFound("legalEntityId.equals=" + legalEntityId);

        // Get all the contactInfoList where legalEntity equals to (legalEntityId + 1)
        defaultContactInfoShouldNotBeFound("legalEntityId.equals=" + (legalEntityId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultContactInfoShouldBeFound(String filter) throws Exception {
        restContactInfoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contactInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)));

        // Check, that the count call also returns 1
        restContactInfoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultContactInfoShouldNotBeFound(String filter) throws Exception {
        restContactInfoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restContactInfoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingContactInfo() throws Exception {
        // Get the contactInfo
        restContactInfoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingContactInfo() throws Exception {
        // Initialize the database
        contactInfoRepository.saveAndFlush(contactInfo);

        int databaseSizeBeforeUpdate = contactInfoRepository.findAll().size();

        // Update the contactInfo
        ContactInfo updatedContactInfo = contactInfoRepository.findById(contactInfo.getId()).get();
        // Disconnect from session so that the updates on updatedContactInfo are not directly saved in db
        em.detach(updatedContactInfo);
        updatedContactInfo.email(UPDATED_EMAIL).phoneNumber(UPDATED_PHONE_NUMBER);
        ContactInfoDTO contactInfoDTO = contactInfoMapper.toDto(updatedContactInfo);

        restContactInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, contactInfoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contactInfoDTO))
            )
            .andExpect(status().isOk());

        // Validate the ContactInfo in the database
        List<ContactInfo> contactInfoList = contactInfoRepository.findAll();
        assertThat(contactInfoList).hasSize(databaseSizeBeforeUpdate);
        ContactInfo testContactInfo = contactInfoList.get(contactInfoList.size() - 1);
        assertThat(testContactInfo.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testContactInfo.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void putNonExistingContactInfo() throws Exception {
        int databaseSizeBeforeUpdate = contactInfoRepository.findAll().size();
        contactInfo.setId(count.incrementAndGet());

        // Create the ContactInfo
        ContactInfoDTO contactInfoDTO = contactInfoMapper.toDto(contactInfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContactInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, contactInfoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contactInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContactInfo in the database
        List<ContactInfo> contactInfoList = contactInfoRepository.findAll();
        assertThat(contactInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchContactInfo() throws Exception {
        int databaseSizeBeforeUpdate = contactInfoRepository.findAll().size();
        contactInfo.setId(count.incrementAndGet());

        // Create the ContactInfo
        ContactInfoDTO contactInfoDTO = contactInfoMapper.toDto(contactInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContactInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contactInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContactInfo in the database
        List<ContactInfo> contactInfoList = contactInfoRepository.findAll();
        assertThat(contactInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamContactInfo() throws Exception {
        int databaseSizeBeforeUpdate = contactInfoRepository.findAll().size();
        contactInfo.setId(count.incrementAndGet());

        // Create the ContactInfo
        ContactInfoDTO contactInfoDTO = contactInfoMapper.toDto(contactInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContactInfoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contactInfoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ContactInfo in the database
        List<ContactInfo> contactInfoList = contactInfoRepository.findAll();
        assertThat(contactInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateContactInfoWithPatch() throws Exception {
        // Initialize the database
        contactInfoRepository.saveAndFlush(contactInfo);

        int databaseSizeBeforeUpdate = contactInfoRepository.findAll().size();

        // Update the contactInfo using partial update
        ContactInfo partialUpdatedContactInfo = new ContactInfo();
        partialUpdatedContactInfo.setId(contactInfo.getId());

        partialUpdatedContactInfo.email(UPDATED_EMAIL);

        restContactInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContactInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedContactInfo))
            )
            .andExpect(status().isOk());

        // Validate the ContactInfo in the database
        List<ContactInfo> contactInfoList = contactInfoRepository.findAll();
        assertThat(contactInfoList).hasSize(databaseSizeBeforeUpdate);
        ContactInfo testContactInfo = contactInfoList.get(contactInfoList.size() - 1);
        assertThat(testContactInfo.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testContactInfo.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void fullUpdateContactInfoWithPatch() throws Exception {
        // Initialize the database
        contactInfoRepository.saveAndFlush(contactInfo);

        int databaseSizeBeforeUpdate = contactInfoRepository.findAll().size();

        // Update the contactInfo using partial update
        ContactInfo partialUpdatedContactInfo = new ContactInfo();
        partialUpdatedContactInfo.setId(contactInfo.getId());

        partialUpdatedContactInfo.email(UPDATED_EMAIL).phoneNumber(UPDATED_PHONE_NUMBER);

        restContactInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContactInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedContactInfo))
            )
            .andExpect(status().isOk());

        // Validate the ContactInfo in the database
        List<ContactInfo> contactInfoList = contactInfoRepository.findAll();
        assertThat(contactInfoList).hasSize(databaseSizeBeforeUpdate);
        ContactInfo testContactInfo = contactInfoList.get(contactInfoList.size() - 1);
        assertThat(testContactInfo.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testContactInfo.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void patchNonExistingContactInfo() throws Exception {
        int databaseSizeBeforeUpdate = contactInfoRepository.findAll().size();
        contactInfo.setId(count.incrementAndGet());

        // Create the ContactInfo
        ContactInfoDTO contactInfoDTO = contactInfoMapper.toDto(contactInfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContactInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, contactInfoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(contactInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContactInfo in the database
        List<ContactInfo> contactInfoList = contactInfoRepository.findAll();
        assertThat(contactInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchContactInfo() throws Exception {
        int databaseSizeBeforeUpdate = contactInfoRepository.findAll().size();
        contactInfo.setId(count.incrementAndGet());

        // Create the ContactInfo
        ContactInfoDTO contactInfoDTO = contactInfoMapper.toDto(contactInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContactInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(contactInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContactInfo in the database
        List<ContactInfo> contactInfoList = contactInfoRepository.findAll();
        assertThat(contactInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamContactInfo() throws Exception {
        int databaseSizeBeforeUpdate = contactInfoRepository.findAll().size();
        contactInfo.setId(count.incrementAndGet());

        // Create the ContactInfo
        ContactInfoDTO contactInfoDTO = contactInfoMapper.toDto(contactInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContactInfoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(contactInfoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ContactInfo in the database
        List<ContactInfo> contactInfoList = contactInfoRepository.findAll();
        assertThat(contactInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteContactInfo() throws Exception {
        // Initialize the database
        contactInfoRepository.saveAndFlush(contactInfo);

        int databaseSizeBeforeDelete = contactInfoRepository.findAll().size();

        // Delete the contactInfo
        restContactInfoMockMvc
            .perform(delete(ENTITY_API_URL_ID, contactInfo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ContactInfo> contactInfoList = contactInfoRepository.findAll();
        assertThat(contactInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
