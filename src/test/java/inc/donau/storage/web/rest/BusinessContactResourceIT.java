package inc.donau.storage.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import inc.donau.storage.IntegrationTest;
import inc.donau.storage.domain.BusinessContact;
import inc.donau.storage.domain.Person;
import inc.donau.storage.repository.BusinessContactRepository;
import inc.donau.storage.service.dto.BusinessContactDTO;
import inc.donau.storage.service.mapper.BusinessContactMapper;
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
 * Integration tests for the {@link BusinessContactResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BusinessContactResourceIT {

    private static final String ENTITY_API_URL = "/api/business-contacts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BusinessContactRepository businessContactRepository;

    @Autowired
    private BusinessContactMapper businessContactMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBusinessContactMockMvc;

    private BusinessContact businessContact;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BusinessContact createEntity(EntityManager em) {
        BusinessContact businessContact = new BusinessContact();
        // Add required entity
        Person person;
        if (TestUtil.findAll(em, Person.class).isEmpty()) {
            person = PersonResourceIT.createEntity(em);
            em.persist(person);
            em.flush();
        } else {
            person = TestUtil.findAll(em, Person.class).get(0);
        }
        businessContact.setPersonalInfo(person);
        return businessContact;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BusinessContact createUpdatedEntity(EntityManager em) {
        BusinessContact businessContact = new BusinessContact();
        // Add required entity
        Person person;
        if (TestUtil.findAll(em, Person.class).isEmpty()) {
            person = PersonResourceIT.createUpdatedEntity(em);
            em.persist(person);
            em.flush();
        } else {
            person = TestUtil.findAll(em, Person.class).get(0);
        }
        businessContact.setPersonalInfo(person);
        return businessContact;
    }

    @BeforeEach
    public void initTest() {
        businessContact = createEntity(em);
    }

    @Test
    @Transactional
    void createBusinessContact() throws Exception {
        int databaseSizeBeforeCreate = businessContactRepository.findAll().size();
        // Create the BusinessContact
        BusinessContactDTO businessContactDTO = businessContactMapper.toDto(businessContact);
        restBusinessContactMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(businessContactDTO))
            )
            .andExpect(status().isCreated());

        // Validate the BusinessContact in the database
        List<BusinessContact> businessContactList = businessContactRepository.findAll();
        assertThat(businessContactList).hasSize(databaseSizeBeforeCreate + 1);
        BusinessContact testBusinessContact = businessContactList.get(businessContactList.size() - 1);
    }

    @Test
    @Transactional
    void createBusinessContactWithExistingId() throws Exception {
        // Create the BusinessContact with an existing ID
        businessContact.setId(1L);
        BusinessContactDTO businessContactDTO = businessContactMapper.toDto(businessContact);

        int databaseSizeBeforeCreate = businessContactRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBusinessContactMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(businessContactDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BusinessContact in the database
        List<BusinessContact> businessContactList = businessContactRepository.findAll();
        assertThat(businessContactList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBusinessContacts() throws Exception {
        // Initialize the database
        businessContactRepository.saveAndFlush(businessContact);

        // Get all the businessContactList
        restBusinessContactMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(businessContact.getId().intValue())));
    }

    @Test
    @Transactional
    void getBusinessContact() throws Exception {
        // Initialize the database
        businessContactRepository.saveAndFlush(businessContact);

        // Get the businessContact
        restBusinessContactMockMvc
            .perform(get(ENTITY_API_URL_ID, businessContact.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(businessContact.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingBusinessContact() throws Exception {
        // Get the businessContact
        restBusinessContactMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBusinessContact() throws Exception {
        // Initialize the database
        businessContactRepository.saveAndFlush(businessContact);

        int databaseSizeBeforeUpdate = businessContactRepository.findAll().size();

        // Update the businessContact
        BusinessContact updatedBusinessContact = businessContactRepository.findById(businessContact.getId()).get();
        // Disconnect from session so that the updates on updatedBusinessContact are not directly saved in db
        em.detach(updatedBusinessContact);
        BusinessContactDTO businessContactDTO = businessContactMapper.toDto(updatedBusinessContact);

        restBusinessContactMockMvc
            .perform(
                put(ENTITY_API_URL_ID, businessContactDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(businessContactDTO))
            )
            .andExpect(status().isOk());

        // Validate the BusinessContact in the database
        List<BusinessContact> businessContactList = businessContactRepository.findAll();
        assertThat(businessContactList).hasSize(databaseSizeBeforeUpdate);
        BusinessContact testBusinessContact = businessContactList.get(businessContactList.size() - 1);
    }

    @Test
    @Transactional
    void putNonExistingBusinessContact() throws Exception {
        int databaseSizeBeforeUpdate = businessContactRepository.findAll().size();
        businessContact.setId(count.incrementAndGet());

        // Create the BusinessContact
        BusinessContactDTO businessContactDTO = businessContactMapper.toDto(businessContact);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBusinessContactMockMvc
            .perform(
                put(ENTITY_API_URL_ID, businessContactDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(businessContactDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BusinessContact in the database
        List<BusinessContact> businessContactList = businessContactRepository.findAll();
        assertThat(businessContactList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBusinessContact() throws Exception {
        int databaseSizeBeforeUpdate = businessContactRepository.findAll().size();
        businessContact.setId(count.incrementAndGet());

        // Create the BusinessContact
        BusinessContactDTO businessContactDTO = businessContactMapper.toDto(businessContact);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBusinessContactMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(businessContactDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BusinessContact in the database
        List<BusinessContact> businessContactList = businessContactRepository.findAll();
        assertThat(businessContactList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBusinessContact() throws Exception {
        int databaseSizeBeforeUpdate = businessContactRepository.findAll().size();
        businessContact.setId(count.incrementAndGet());

        // Create the BusinessContact
        BusinessContactDTO businessContactDTO = businessContactMapper.toDto(businessContact);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBusinessContactMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(businessContactDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BusinessContact in the database
        List<BusinessContact> businessContactList = businessContactRepository.findAll();
        assertThat(businessContactList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBusinessContactWithPatch() throws Exception {
        // Initialize the database
        businessContactRepository.saveAndFlush(businessContact);

        int databaseSizeBeforeUpdate = businessContactRepository.findAll().size();

        // Update the businessContact using partial update
        BusinessContact partialUpdatedBusinessContact = new BusinessContact();
        partialUpdatedBusinessContact.setId(businessContact.getId());

        restBusinessContactMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBusinessContact.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBusinessContact))
            )
            .andExpect(status().isOk());

        // Validate the BusinessContact in the database
        List<BusinessContact> businessContactList = businessContactRepository.findAll();
        assertThat(businessContactList).hasSize(databaseSizeBeforeUpdate);
        BusinessContact testBusinessContact = businessContactList.get(businessContactList.size() - 1);
    }

    @Test
    @Transactional
    void fullUpdateBusinessContactWithPatch() throws Exception {
        // Initialize the database
        businessContactRepository.saveAndFlush(businessContact);

        int databaseSizeBeforeUpdate = businessContactRepository.findAll().size();

        // Update the businessContact using partial update
        BusinessContact partialUpdatedBusinessContact = new BusinessContact();
        partialUpdatedBusinessContact.setId(businessContact.getId());

        restBusinessContactMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBusinessContact.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBusinessContact))
            )
            .andExpect(status().isOk());

        // Validate the BusinessContact in the database
        List<BusinessContact> businessContactList = businessContactRepository.findAll();
        assertThat(businessContactList).hasSize(databaseSizeBeforeUpdate);
        BusinessContact testBusinessContact = businessContactList.get(businessContactList.size() - 1);
    }

    @Test
    @Transactional
    void patchNonExistingBusinessContact() throws Exception {
        int databaseSizeBeforeUpdate = businessContactRepository.findAll().size();
        businessContact.setId(count.incrementAndGet());

        // Create the BusinessContact
        BusinessContactDTO businessContactDTO = businessContactMapper.toDto(businessContact);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBusinessContactMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, businessContactDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(businessContactDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BusinessContact in the database
        List<BusinessContact> businessContactList = businessContactRepository.findAll();
        assertThat(businessContactList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBusinessContact() throws Exception {
        int databaseSizeBeforeUpdate = businessContactRepository.findAll().size();
        businessContact.setId(count.incrementAndGet());

        // Create the BusinessContact
        BusinessContactDTO businessContactDTO = businessContactMapper.toDto(businessContact);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBusinessContactMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(businessContactDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BusinessContact in the database
        List<BusinessContact> businessContactList = businessContactRepository.findAll();
        assertThat(businessContactList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBusinessContact() throws Exception {
        int databaseSizeBeforeUpdate = businessContactRepository.findAll().size();
        businessContact.setId(count.incrementAndGet());

        // Create the BusinessContact
        BusinessContactDTO businessContactDTO = businessContactMapper.toDto(businessContact);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBusinessContactMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(businessContactDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BusinessContact in the database
        List<BusinessContact> businessContactList = businessContactRepository.findAll();
        assertThat(businessContactList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBusinessContact() throws Exception {
        // Initialize the database
        businessContactRepository.saveAndFlush(businessContact);

        int databaseSizeBeforeDelete = businessContactRepository.findAll().size();

        // Delete the businessContact
        restBusinessContactMockMvc
            .perform(delete(ENTITY_API_URL_ID, businessContact.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BusinessContact> businessContactList = businessContactRepository.findAll();
        assertThat(businessContactList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
