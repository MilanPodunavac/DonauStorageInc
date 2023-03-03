package inc.donau.storage.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import inc.donau.storage.IntegrationTest;
import inc.donau.storage.domain.BusinessContact;
import inc.donau.storage.domain.BusinessPartner;
import inc.donau.storage.domain.Company;
import inc.donau.storage.domain.LegalEntity;
import inc.donau.storage.domain.TransferDocument;
import inc.donau.storage.repository.BusinessPartnerRepository;
import inc.donau.storage.service.criteria.BusinessPartnerCriteria;
import inc.donau.storage.service.dto.BusinessPartnerDTO;
import inc.donau.storage.service.mapper.BusinessPartnerMapper;
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
 * Integration tests for the {@link BusinessPartnerResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BusinessPartnerResourceIT {

    private static final String ENTITY_API_URL = "/api/business-partners";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BusinessPartnerRepository businessPartnerRepository;

    @Autowired
    private BusinessPartnerMapper businessPartnerMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBusinessPartnerMockMvc;

    private BusinessPartner businessPartner;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BusinessPartner createEntity(EntityManager em) {
        BusinessPartner businessPartner = new BusinessPartner();
        // Add required entity
        BusinessContact businessContact;
        if (TestUtil.findAll(em, BusinessContact.class).isEmpty()) {
            businessContact = BusinessContactResourceIT.createEntity(em);
            em.persist(businessContact);
            em.flush();
        } else {
            businessContact = TestUtil.findAll(em, BusinessContact.class).get(0);
        }
        businessPartner.setBusinessContact(businessContact);
        // Add required entity
        LegalEntity legalEntity;
        if (TestUtil.findAll(em, LegalEntity.class).isEmpty()) {
            legalEntity = LegalEntityResourceIT.createEntity(em);
            em.persist(legalEntity);
            em.flush();
        } else {
            legalEntity = TestUtil.findAll(em, LegalEntity.class).get(0);
        }
        businessPartner.setLegalEntityInfo(legalEntity);
        // Add required entity
        Company company;
        if (TestUtil.findAll(em, Company.class).isEmpty()) {
            company = CompanyResourceIT.createEntity(em);
            em.persist(company);
            em.flush();
        } else {
            company = TestUtil.findAll(em, Company.class).get(0);
        }
        businessPartner.setCompany(company);
        return businessPartner;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BusinessPartner createUpdatedEntity(EntityManager em) {
        BusinessPartner businessPartner = new BusinessPartner();
        // Add required entity
        BusinessContact businessContact;
        if (TestUtil.findAll(em, BusinessContact.class).isEmpty()) {
            businessContact = BusinessContactResourceIT.createUpdatedEntity(em);
            em.persist(businessContact);
            em.flush();
        } else {
            businessContact = TestUtil.findAll(em, BusinessContact.class).get(0);
        }
        businessPartner.setBusinessContact(businessContact);
        // Add required entity
        LegalEntity legalEntity;
        if (TestUtil.findAll(em, LegalEntity.class).isEmpty()) {
            legalEntity = LegalEntityResourceIT.createUpdatedEntity(em);
            em.persist(legalEntity);
            em.flush();
        } else {
            legalEntity = TestUtil.findAll(em, LegalEntity.class).get(0);
        }
        businessPartner.setLegalEntityInfo(legalEntity);
        // Add required entity
        Company company;
        if (TestUtil.findAll(em, Company.class).isEmpty()) {
            company = CompanyResourceIT.createUpdatedEntity(em);
            em.persist(company);
            em.flush();
        } else {
            company = TestUtil.findAll(em, Company.class).get(0);
        }
        businessPartner.setCompany(company);
        return businessPartner;
    }

    @BeforeEach
    public void initTest() {
        businessPartner = createEntity(em);
    }

    @Test
    @Transactional
    void createBusinessPartner() throws Exception {
        int databaseSizeBeforeCreate = businessPartnerRepository.findAll().size();
        // Create the BusinessPartner
        BusinessPartnerDTO businessPartnerDTO = businessPartnerMapper.toDto(businessPartner);
        restBusinessPartnerMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(businessPartnerDTO))
            )
            .andExpect(status().isCreated());

        // Validate the BusinessPartner in the database
        List<BusinessPartner> businessPartnerList = businessPartnerRepository.findAll();
        assertThat(businessPartnerList).hasSize(databaseSizeBeforeCreate + 1);
        BusinessPartner testBusinessPartner = businessPartnerList.get(businessPartnerList.size() - 1);
    }

    @Test
    @Transactional
    void createBusinessPartnerWithExistingId() throws Exception {
        // Create the BusinessPartner with an existing ID
        businessPartner.setId(1L);
        BusinessPartnerDTO businessPartnerDTO = businessPartnerMapper.toDto(businessPartner);

        int databaseSizeBeforeCreate = businessPartnerRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBusinessPartnerMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(businessPartnerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BusinessPartner in the database
        List<BusinessPartner> businessPartnerList = businessPartnerRepository.findAll();
        assertThat(businessPartnerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBusinessPartners() throws Exception {
        // Initialize the database
        businessPartnerRepository.saveAndFlush(businessPartner);

        // Get all the businessPartnerList
        restBusinessPartnerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(businessPartner.getId().intValue())));
    }

    @Test
    @Transactional
    void getBusinessPartner() throws Exception {
        // Initialize the database
        businessPartnerRepository.saveAndFlush(businessPartner);

        // Get the businessPartner
        restBusinessPartnerMockMvc
            .perform(get(ENTITY_API_URL_ID, businessPartner.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(businessPartner.getId().intValue()));
    }

    @Test
    @Transactional
    void getBusinessPartnersByIdFiltering() throws Exception {
        // Initialize the database
        businessPartnerRepository.saveAndFlush(businessPartner);

        Long id = businessPartner.getId();

        defaultBusinessPartnerShouldBeFound("id.equals=" + id);
        defaultBusinessPartnerShouldNotBeFound("id.notEquals=" + id);

        defaultBusinessPartnerShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultBusinessPartnerShouldNotBeFound("id.greaterThan=" + id);

        defaultBusinessPartnerShouldBeFound("id.lessThanOrEqual=" + id);
        defaultBusinessPartnerShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllBusinessPartnersByBusinessContactIsEqualToSomething() throws Exception {
        // Get already existing entity
        BusinessContact businessContact = businessPartner.getBusinessContact();
        businessPartnerRepository.saveAndFlush(businessPartner);
        Long businessContactId = businessContact.getId();

        // Get all the businessPartnerList where businessContact equals to businessContactId
        defaultBusinessPartnerShouldBeFound("businessContactId.equals=" + businessContactId);

        // Get all the businessPartnerList where businessContact equals to (businessContactId + 1)
        defaultBusinessPartnerShouldNotBeFound("businessContactId.equals=" + (businessContactId + 1));
    }

    @Test
    @Transactional
    void getAllBusinessPartnersByLegalEntityInfoIsEqualToSomething() throws Exception {
        // Get already existing entity
        LegalEntity legalEntityInfo = businessPartner.getLegalEntityInfo();
        businessPartnerRepository.saveAndFlush(businessPartner);
        Long legalEntityInfoId = legalEntityInfo.getId();

        // Get all the businessPartnerList where legalEntityInfo equals to legalEntityInfoId
        defaultBusinessPartnerShouldBeFound("legalEntityInfoId.equals=" + legalEntityInfoId);

        // Get all the businessPartnerList where legalEntityInfo equals to (legalEntityInfoId + 1)
        defaultBusinessPartnerShouldNotBeFound("legalEntityInfoId.equals=" + (legalEntityInfoId + 1));
    }

    @Test
    @Transactional
    void getAllBusinessPartnersByTransfersIsEqualToSomething() throws Exception {
        TransferDocument transfers;
        if (TestUtil.findAll(em, TransferDocument.class).isEmpty()) {
            businessPartnerRepository.saveAndFlush(businessPartner);
            transfers = TransferDocumentResourceIT.createEntity(em);
        } else {
            transfers = TestUtil.findAll(em, TransferDocument.class).get(0);
        }
        em.persist(transfers);
        em.flush();
        businessPartner.addTransfers(transfers);
        businessPartnerRepository.saveAndFlush(businessPartner);
        Long transfersId = transfers.getId();

        // Get all the businessPartnerList where transfers equals to transfersId
        defaultBusinessPartnerShouldBeFound("transfersId.equals=" + transfersId);

        // Get all the businessPartnerList where transfers equals to (transfersId + 1)
        defaultBusinessPartnerShouldNotBeFound("transfersId.equals=" + (transfersId + 1));
    }

    @Test
    @Transactional
    void getAllBusinessPartnersByCompanyIsEqualToSomething() throws Exception {
        Company company;
        if (TestUtil.findAll(em, Company.class).isEmpty()) {
            businessPartnerRepository.saveAndFlush(businessPartner);
            company = CompanyResourceIT.createEntity(em);
        } else {
            company = TestUtil.findAll(em, Company.class).get(0);
        }
        em.persist(company);
        em.flush();
        businessPartner.setCompany(company);
        businessPartnerRepository.saveAndFlush(businessPartner);
        Long companyId = company.getId();

        // Get all the businessPartnerList where company equals to companyId
        defaultBusinessPartnerShouldBeFound("companyId.equals=" + companyId);

        // Get all the businessPartnerList where company equals to (companyId + 1)
        defaultBusinessPartnerShouldNotBeFound("companyId.equals=" + (companyId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultBusinessPartnerShouldBeFound(String filter) throws Exception {
        restBusinessPartnerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(businessPartner.getId().intValue())));

        // Check, that the count call also returns 1
        restBusinessPartnerMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultBusinessPartnerShouldNotBeFound(String filter) throws Exception {
        restBusinessPartnerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBusinessPartnerMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingBusinessPartner() throws Exception {
        // Get the businessPartner
        restBusinessPartnerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBusinessPartner() throws Exception {
        // Initialize the database
        businessPartnerRepository.saveAndFlush(businessPartner);

        int databaseSizeBeforeUpdate = businessPartnerRepository.findAll().size();

        // Update the businessPartner
        BusinessPartner updatedBusinessPartner = businessPartnerRepository.findById(businessPartner.getId()).get();
        // Disconnect from session so that the updates on updatedBusinessPartner are not directly saved in db
        em.detach(updatedBusinessPartner);
        BusinessPartnerDTO businessPartnerDTO = businessPartnerMapper.toDto(updatedBusinessPartner);

        restBusinessPartnerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, businessPartnerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(businessPartnerDTO))
            )
            .andExpect(status().isOk());

        // Validate the BusinessPartner in the database
        List<BusinessPartner> businessPartnerList = businessPartnerRepository.findAll();
        assertThat(businessPartnerList).hasSize(databaseSizeBeforeUpdate);
        BusinessPartner testBusinessPartner = businessPartnerList.get(businessPartnerList.size() - 1);
    }

    @Test
    @Transactional
    void putNonExistingBusinessPartner() throws Exception {
        int databaseSizeBeforeUpdate = businessPartnerRepository.findAll().size();
        businessPartner.setId(count.incrementAndGet());

        // Create the BusinessPartner
        BusinessPartnerDTO businessPartnerDTO = businessPartnerMapper.toDto(businessPartner);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBusinessPartnerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, businessPartnerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(businessPartnerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BusinessPartner in the database
        List<BusinessPartner> businessPartnerList = businessPartnerRepository.findAll();
        assertThat(businessPartnerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBusinessPartner() throws Exception {
        int databaseSizeBeforeUpdate = businessPartnerRepository.findAll().size();
        businessPartner.setId(count.incrementAndGet());

        // Create the BusinessPartner
        BusinessPartnerDTO businessPartnerDTO = businessPartnerMapper.toDto(businessPartner);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBusinessPartnerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(businessPartnerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BusinessPartner in the database
        List<BusinessPartner> businessPartnerList = businessPartnerRepository.findAll();
        assertThat(businessPartnerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBusinessPartner() throws Exception {
        int databaseSizeBeforeUpdate = businessPartnerRepository.findAll().size();
        businessPartner.setId(count.incrementAndGet());

        // Create the BusinessPartner
        BusinessPartnerDTO businessPartnerDTO = businessPartnerMapper.toDto(businessPartner);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBusinessPartnerMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(businessPartnerDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BusinessPartner in the database
        List<BusinessPartner> businessPartnerList = businessPartnerRepository.findAll();
        assertThat(businessPartnerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBusinessPartnerWithPatch() throws Exception {
        // Initialize the database
        businessPartnerRepository.saveAndFlush(businessPartner);

        int databaseSizeBeforeUpdate = businessPartnerRepository.findAll().size();

        // Update the businessPartner using partial update
        BusinessPartner partialUpdatedBusinessPartner = new BusinessPartner();
        partialUpdatedBusinessPartner.setId(businessPartner.getId());

        restBusinessPartnerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBusinessPartner.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBusinessPartner))
            )
            .andExpect(status().isOk());

        // Validate the BusinessPartner in the database
        List<BusinessPartner> businessPartnerList = businessPartnerRepository.findAll();
        assertThat(businessPartnerList).hasSize(databaseSizeBeforeUpdate);
        BusinessPartner testBusinessPartner = businessPartnerList.get(businessPartnerList.size() - 1);
    }

    @Test
    @Transactional
    void fullUpdateBusinessPartnerWithPatch() throws Exception {
        // Initialize the database
        businessPartnerRepository.saveAndFlush(businessPartner);

        int databaseSizeBeforeUpdate = businessPartnerRepository.findAll().size();

        // Update the businessPartner using partial update
        BusinessPartner partialUpdatedBusinessPartner = new BusinessPartner();
        partialUpdatedBusinessPartner.setId(businessPartner.getId());

        restBusinessPartnerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBusinessPartner.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBusinessPartner))
            )
            .andExpect(status().isOk());

        // Validate the BusinessPartner in the database
        List<BusinessPartner> businessPartnerList = businessPartnerRepository.findAll();
        assertThat(businessPartnerList).hasSize(databaseSizeBeforeUpdate);
        BusinessPartner testBusinessPartner = businessPartnerList.get(businessPartnerList.size() - 1);
    }

    @Test
    @Transactional
    void patchNonExistingBusinessPartner() throws Exception {
        int databaseSizeBeforeUpdate = businessPartnerRepository.findAll().size();
        businessPartner.setId(count.incrementAndGet());

        // Create the BusinessPartner
        BusinessPartnerDTO businessPartnerDTO = businessPartnerMapper.toDto(businessPartner);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBusinessPartnerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, businessPartnerDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(businessPartnerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BusinessPartner in the database
        List<BusinessPartner> businessPartnerList = businessPartnerRepository.findAll();
        assertThat(businessPartnerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBusinessPartner() throws Exception {
        int databaseSizeBeforeUpdate = businessPartnerRepository.findAll().size();
        businessPartner.setId(count.incrementAndGet());

        // Create the BusinessPartner
        BusinessPartnerDTO businessPartnerDTO = businessPartnerMapper.toDto(businessPartner);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBusinessPartnerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(businessPartnerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BusinessPartner in the database
        List<BusinessPartner> businessPartnerList = businessPartnerRepository.findAll();
        assertThat(businessPartnerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBusinessPartner() throws Exception {
        int databaseSizeBeforeUpdate = businessPartnerRepository.findAll().size();
        businessPartner.setId(count.incrementAndGet());

        // Create the BusinessPartner
        BusinessPartnerDTO businessPartnerDTO = businessPartnerMapper.toDto(businessPartner);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBusinessPartnerMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(businessPartnerDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BusinessPartner in the database
        List<BusinessPartner> businessPartnerList = businessPartnerRepository.findAll();
        assertThat(businessPartnerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBusinessPartner() throws Exception {
        // Initialize the database
        businessPartnerRepository.saveAndFlush(businessPartner);

        int databaseSizeBeforeDelete = businessPartnerRepository.findAll().size();

        // Delete the businessPartner
        restBusinessPartnerMockMvc
            .perform(delete(ENTITY_API_URL_ID, businessPartner.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BusinessPartner> businessPartnerList = businessPartnerRepository.findAll();
        assertThat(businessPartnerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
