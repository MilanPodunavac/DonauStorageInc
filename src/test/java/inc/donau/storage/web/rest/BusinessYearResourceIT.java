package inc.donau.storage.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import inc.donau.storage.IntegrationTest;
import inc.donau.storage.domain.BusinessYear;
import inc.donau.storage.domain.Company;
import inc.donau.storage.repository.BusinessYearRepository;
import inc.donau.storage.service.criteria.BusinessYearCriteria;
import inc.donau.storage.service.dto.BusinessYearDTO;
import inc.donau.storage.service.mapper.BusinessYearMapper;
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
 * Integration tests for the {@link BusinessYearResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BusinessYearResourceIT {

    private static final String DEFAULT_YEAR_CODE = "AAAAAAAAAA";
    private static final String UPDATED_YEAR_CODE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_COMPLETED = false;
    private static final Boolean UPDATED_COMPLETED = true;

    private static final String ENTITY_API_URL = "/api/business-years";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BusinessYearRepository businessYearRepository;

    @Autowired
    private BusinessYearMapper businessYearMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBusinessYearMockMvc;

    private BusinessYear businessYear;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BusinessYear createEntity(EntityManager em) {
        BusinessYear businessYear = new BusinessYear().yearCode(DEFAULT_YEAR_CODE).completed(DEFAULT_COMPLETED);
        // Add required entity
        Company company;
        if (TestUtil.findAll(em, Company.class).isEmpty()) {
            company = CompanyResourceIT.createEntity(em);
            em.persist(company);
            em.flush();
        } else {
            company = TestUtil.findAll(em, Company.class).get(0);
        }
        businessYear.setCompany(company);
        return businessYear;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BusinessYear createUpdatedEntity(EntityManager em) {
        BusinessYear businessYear = new BusinessYear().yearCode(UPDATED_YEAR_CODE).completed(UPDATED_COMPLETED);
        // Add required entity
        Company company;
        if (TestUtil.findAll(em, Company.class).isEmpty()) {
            company = CompanyResourceIT.createUpdatedEntity(em);
            em.persist(company);
            em.flush();
        } else {
            company = TestUtil.findAll(em, Company.class).get(0);
        }
        businessYear.setCompany(company);
        return businessYear;
    }

    @BeforeEach
    public void initTest() {
        businessYear = createEntity(em);
    }

    @Test
    @Transactional
    void createBusinessYear() throws Exception {
        int databaseSizeBeforeCreate = businessYearRepository.findAll().size();
        // Create the BusinessYear
        BusinessYearDTO businessYearDTO = businessYearMapper.toDto(businessYear);
        restBusinessYearMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(businessYearDTO))
            )
            .andExpect(status().isCreated());

        // Validate the BusinessYear in the database
        List<BusinessYear> businessYearList = businessYearRepository.findAll();
        assertThat(businessYearList).hasSize(databaseSizeBeforeCreate + 1);
        BusinessYear testBusinessYear = businessYearList.get(businessYearList.size() - 1);
        assertThat(testBusinessYear.getYearCode()).isEqualTo(DEFAULT_YEAR_CODE);
        assertThat(testBusinessYear.getCompleted()).isEqualTo(DEFAULT_COMPLETED);
    }

    @Test
    @Transactional
    void createBusinessYearWithExistingId() throws Exception {
        // Create the BusinessYear with an existing ID
        businessYear.setId(1L);
        BusinessYearDTO businessYearDTO = businessYearMapper.toDto(businessYear);

        int databaseSizeBeforeCreate = businessYearRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBusinessYearMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(businessYearDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BusinessYear in the database
        List<BusinessYear> businessYearList = businessYearRepository.findAll();
        assertThat(businessYearList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkYearCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = businessYearRepository.findAll().size();
        // set the field null
        businessYear.setYearCode(null);

        // Create the BusinessYear, which fails.
        BusinessYearDTO businessYearDTO = businessYearMapper.toDto(businessYear);

        restBusinessYearMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(businessYearDTO))
            )
            .andExpect(status().isBadRequest());

        List<BusinessYear> businessYearList = businessYearRepository.findAll();
        assertThat(businessYearList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCompletedIsRequired() throws Exception {
        int databaseSizeBeforeTest = businessYearRepository.findAll().size();
        // set the field null
        businessYear.setCompleted(null);

        // Create the BusinessYear, which fails.
        BusinessYearDTO businessYearDTO = businessYearMapper.toDto(businessYear);

        restBusinessYearMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(businessYearDTO))
            )
            .andExpect(status().isBadRequest());

        List<BusinessYear> businessYearList = businessYearRepository.findAll();
        assertThat(businessYearList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllBusinessYears() throws Exception {
        // Initialize the database
        businessYearRepository.saveAndFlush(businessYear);

        // Get all the businessYearList
        restBusinessYearMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(businessYear.getId().intValue())))
            .andExpect(jsonPath("$.[*].yearCode").value(hasItem(DEFAULT_YEAR_CODE)))
            .andExpect(jsonPath("$.[*].completed").value(hasItem(DEFAULT_COMPLETED.booleanValue())));
    }

    @Test
    @Transactional
    void getBusinessYear() throws Exception {
        // Initialize the database
        businessYearRepository.saveAndFlush(businessYear);

        // Get the businessYear
        restBusinessYearMockMvc
            .perform(get(ENTITY_API_URL_ID, businessYear.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(businessYear.getId().intValue()))
            .andExpect(jsonPath("$.yearCode").value(DEFAULT_YEAR_CODE))
            .andExpect(jsonPath("$.completed").value(DEFAULT_COMPLETED.booleanValue()));
    }

    @Test
    @Transactional
    void getBusinessYearsByIdFiltering() throws Exception {
        // Initialize the database
        businessYearRepository.saveAndFlush(businessYear);

        Long id = businessYear.getId();

        defaultBusinessYearShouldBeFound("id.equals=" + id);
        defaultBusinessYearShouldNotBeFound("id.notEquals=" + id);

        defaultBusinessYearShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultBusinessYearShouldNotBeFound("id.greaterThan=" + id);

        defaultBusinessYearShouldBeFound("id.lessThanOrEqual=" + id);
        defaultBusinessYearShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllBusinessYearsByYearCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        businessYearRepository.saveAndFlush(businessYear);

        // Get all the businessYearList where yearCode equals to DEFAULT_YEAR_CODE
        defaultBusinessYearShouldBeFound("yearCode.equals=" + DEFAULT_YEAR_CODE);

        // Get all the businessYearList where yearCode equals to UPDATED_YEAR_CODE
        defaultBusinessYearShouldNotBeFound("yearCode.equals=" + UPDATED_YEAR_CODE);
    }

    @Test
    @Transactional
    void getAllBusinessYearsByYearCodeIsInShouldWork() throws Exception {
        // Initialize the database
        businessYearRepository.saveAndFlush(businessYear);

        // Get all the businessYearList where yearCode in DEFAULT_YEAR_CODE or UPDATED_YEAR_CODE
        defaultBusinessYearShouldBeFound("yearCode.in=" + DEFAULT_YEAR_CODE + "," + UPDATED_YEAR_CODE);

        // Get all the businessYearList where yearCode equals to UPDATED_YEAR_CODE
        defaultBusinessYearShouldNotBeFound("yearCode.in=" + UPDATED_YEAR_CODE);
    }

    @Test
    @Transactional
    void getAllBusinessYearsByYearCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        businessYearRepository.saveAndFlush(businessYear);

        // Get all the businessYearList where yearCode is not null
        defaultBusinessYearShouldBeFound("yearCode.specified=true");

        // Get all the businessYearList where yearCode is null
        defaultBusinessYearShouldNotBeFound("yearCode.specified=false");
    }

    @Test
    @Transactional
    void getAllBusinessYearsByYearCodeContainsSomething() throws Exception {
        // Initialize the database
        businessYearRepository.saveAndFlush(businessYear);

        // Get all the businessYearList where yearCode contains DEFAULT_YEAR_CODE
        defaultBusinessYearShouldBeFound("yearCode.contains=" + DEFAULT_YEAR_CODE);

        // Get all the businessYearList where yearCode contains UPDATED_YEAR_CODE
        defaultBusinessYearShouldNotBeFound("yearCode.contains=" + UPDATED_YEAR_CODE);
    }

    @Test
    @Transactional
    void getAllBusinessYearsByYearCodeNotContainsSomething() throws Exception {
        // Initialize the database
        businessYearRepository.saveAndFlush(businessYear);

        // Get all the businessYearList where yearCode does not contain DEFAULT_YEAR_CODE
        defaultBusinessYearShouldNotBeFound("yearCode.doesNotContain=" + DEFAULT_YEAR_CODE);

        // Get all the businessYearList where yearCode does not contain UPDATED_YEAR_CODE
        defaultBusinessYearShouldBeFound("yearCode.doesNotContain=" + UPDATED_YEAR_CODE);
    }

    @Test
    @Transactional
    void getAllBusinessYearsByCompletedIsEqualToSomething() throws Exception {
        // Initialize the database
        businessYearRepository.saveAndFlush(businessYear);

        // Get all the businessYearList where completed equals to DEFAULT_COMPLETED
        defaultBusinessYearShouldBeFound("completed.equals=" + DEFAULT_COMPLETED);

        // Get all the businessYearList where completed equals to UPDATED_COMPLETED
        defaultBusinessYearShouldNotBeFound("completed.equals=" + UPDATED_COMPLETED);
    }

    @Test
    @Transactional
    void getAllBusinessYearsByCompletedIsInShouldWork() throws Exception {
        // Initialize the database
        businessYearRepository.saveAndFlush(businessYear);

        // Get all the businessYearList where completed in DEFAULT_COMPLETED or UPDATED_COMPLETED
        defaultBusinessYearShouldBeFound("completed.in=" + DEFAULT_COMPLETED + "," + UPDATED_COMPLETED);

        // Get all the businessYearList where completed equals to UPDATED_COMPLETED
        defaultBusinessYearShouldNotBeFound("completed.in=" + UPDATED_COMPLETED);
    }

    @Test
    @Transactional
    void getAllBusinessYearsByCompletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        businessYearRepository.saveAndFlush(businessYear);

        // Get all the businessYearList where completed is not null
        defaultBusinessYearShouldBeFound("completed.specified=true");

        // Get all the businessYearList where completed is null
        defaultBusinessYearShouldNotBeFound("completed.specified=false");
    }

    @Test
    @Transactional
    void getAllBusinessYearsByCompanyIsEqualToSomething() throws Exception {
        Company company;
        if (TestUtil.findAll(em, Company.class).isEmpty()) {
            businessYearRepository.saveAndFlush(businessYear);
            company = CompanyResourceIT.createEntity(em);
        } else {
            company = TestUtil.findAll(em, Company.class).get(0);
        }
        em.persist(company);
        em.flush();
        businessYear.setCompany(company);
        businessYearRepository.saveAndFlush(businessYear);
        Long companyId = company.getId();

        // Get all the businessYearList where company equals to companyId
        defaultBusinessYearShouldBeFound("companyId.equals=" + companyId);

        // Get all the businessYearList where company equals to (companyId + 1)
        defaultBusinessYearShouldNotBeFound("companyId.equals=" + (companyId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultBusinessYearShouldBeFound(String filter) throws Exception {
        restBusinessYearMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(businessYear.getId().intValue())))
            .andExpect(jsonPath("$.[*].yearCode").value(hasItem(DEFAULT_YEAR_CODE)))
            .andExpect(jsonPath("$.[*].completed").value(hasItem(DEFAULT_COMPLETED.booleanValue())));

        // Check, that the count call also returns 1
        restBusinessYearMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultBusinessYearShouldNotBeFound(String filter) throws Exception {
        restBusinessYearMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBusinessYearMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingBusinessYear() throws Exception {
        // Get the businessYear
        restBusinessYearMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBusinessYear() throws Exception {
        // Initialize the database
        businessYearRepository.saveAndFlush(businessYear);

        int databaseSizeBeforeUpdate = businessYearRepository.findAll().size();

        // Update the businessYear
        BusinessYear updatedBusinessYear = businessYearRepository.findById(businessYear.getId()).get();
        // Disconnect from session so that the updates on updatedBusinessYear are not directly saved in db
        em.detach(updatedBusinessYear);
        updatedBusinessYear.yearCode(UPDATED_YEAR_CODE).completed(UPDATED_COMPLETED);
        BusinessYearDTO businessYearDTO = businessYearMapper.toDto(updatedBusinessYear);

        restBusinessYearMockMvc
            .perform(
                put(ENTITY_API_URL_ID, businessYearDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(businessYearDTO))
            )
            .andExpect(status().isOk());

        // Validate the BusinessYear in the database
        List<BusinessYear> businessYearList = businessYearRepository.findAll();
        assertThat(businessYearList).hasSize(databaseSizeBeforeUpdate);
        BusinessYear testBusinessYear = businessYearList.get(businessYearList.size() - 1);
        assertThat(testBusinessYear.getYearCode()).isEqualTo(UPDATED_YEAR_CODE);
        assertThat(testBusinessYear.getCompleted()).isEqualTo(UPDATED_COMPLETED);
    }

    @Test
    @Transactional
    void putNonExistingBusinessYear() throws Exception {
        int databaseSizeBeforeUpdate = businessYearRepository.findAll().size();
        businessYear.setId(count.incrementAndGet());

        // Create the BusinessYear
        BusinessYearDTO businessYearDTO = businessYearMapper.toDto(businessYear);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBusinessYearMockMvc
            .perform(
                put(ENTITY_API_URL_ID, businessYearDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(businessYearDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BusinessYear in the database
        List<BusinessYear> businessYearList = businessYearRepository.findAll();
        assertThat(businessYearList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBusinessYear() throws Exception {
        int databaseSizeBeforeUpdate = businessYearRepository.findAll().size();
        businessYear.setId(count.incrementAndGet());

        // Create the BusinessYear
        BusinessYearDTO businessYearDTO = businessYearMapper.toDto(businessYear);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBusinessYearMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(businessYearDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BusinessYear in the database
        List<BusinessYear> businessYearList = businessYearRepository.findAll();
        assertThat(businessYearList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBusinessYear() throws Exception {
        int databaseSizeBeforeUpdate = businessYearRepository.findAll().size();
        businessYear.setId(count.incrementAndGet());

        // Create the BusinessYear
        BusinessYearDTO businessYearDTO = businessYearMapper.toDto(businessYear);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBusinessYearMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(businessYearDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BusinessYear in the database
        List<BusinessYear> businessYearList = businessYearRepository.findAll();
        assertThat(businessYearList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBusinessYearWithPatch() throws Exception {
        // Initialize the database
        businessYearRepository.saveAndFlush(businessYear);

        int databaseSizeBeforeUpdate = businessYearRepository.findAll().size();

        // Update the businessYear using partial update
        BusinessYear partialUpdatedBusinessYear = new BusinessYear();
        partialUpdatedBusinessYear.setId(businessYear.getId());

        partialUpdatedBusinessYear.completed(UPDATED_COMPLETED);

        restBusinessYearMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBusinessYear.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBusinessYear))
            )
            .andExpect(status().isOk());

        // Validate the BusinessYear in the database
        List<BusinessYear> businessYearList = businessYearRepository.findAll();
        assertThat(businessYearList).hasSize(databaseSizeBeforeUpdate);
        BusinessYear testBusinessYear = businessYearList.get(businessYearList.size() - 1);
        assertThat(testBusinessYear.getYearCode()).isEqualTo(DEFAULT_YEAR_CODE);
        assertThat(testBusinessYear.getCompleted()).isEqualTo(UPDATED_COMPLETED);
    }

    @Test
    @Transactional
    void fullUpdateBusinessYearWithPatch() throws Exception {
        // Initialize the database
        businessYearRepository.saveAndFlush(businessYear);

        int databaseSizeBeforeUpdate = businessYearRepository.findAll().size();

        // Update the businessYear using partial update
        BusinessYear partialUpdatedBusinessYear = new BusinessYear();
        partialUpdatedBusinessYear.setId(businessYear.getId());

        partialUpdatedBusinessYear.yearCode(UPDATED_YEAR_CODE).completed(UPDATED_COMPLETED);

        restBusinessYearMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBusinessYear.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBusinessYear))
            )
            .andExpect(status().isOk());

        // Validate the BusinessYear in the database
        List<BusinessYear> businessYearList = businessYearRepository.findAll();
        assertThat(businessYearList).hasSize(databaseSizeBeforeUpdate);
        BusinessYear testBusinessYear = businessYearList.get(businessYearList.size() - 1);
        assertThat(testBusinessYear.getYearCode()).isEqualTo(UPDATED_YEAR_CODE);
        assertThat(testBusinessYear.getCompleted()).isEqualTo(UPDATED_COMPLETED);
    }

    @Test
    @Transactional
    void patchNonExistingBusinessYear() throws Exception {
        int databaseSizeBeforeUpdate = businessYearRepository.findAll().size();
        businessYear.setId(count.incrementAndGet());

        // Create the BusinessYear
        BusinessYearDTO businessYearDTO = businessYearMapper.toDto(businessYear);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBusinessYearMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, businessYearDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(businessYearDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BusinessYear in the database
        List<BusinessYear> businessYearList = businessYearRepository.findAll();
        assertThat(businessYearList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBusinessYear() throws Exception {
        int databaseSizeBeforeUpdate = businessYearRepository.findAll().size();
        businessYear.setId(count.incrementAndGet());

        // Create the BusinessYear
        BusinessYearDTO businessYearDTO = businessYearMapper.toDto(businessYear);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBusinessYearMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(businessYearDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BusinessYear in the database
        List<BusinessYear> businessYearList = businessYearRepository.findAll();
        assertThat(businessYearList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBusinessYear() throws Exception {
        int databaseSizeBeforeUpdate = businessYearRepository.findAll().size();
        businessYear.setId(count.incrementAndGet());

        // Create the BusinessYear
        BusinessYearDTO businessYearDTO = businessYearMapper.toDto(businessYear);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBusinessYearMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(businessYearDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BusinessYear in the database
        List<BusinessYear> businessYearList = businessYearRepository.findAll();
        assertThat(businessYearList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBusinessYear() throws Exception {
        // Initialize the database
        businessYearRepository.saveAndFlush(businessYear);

        int databaseSizeBeforeDelete = businessYearRepository.findAll().size();

        // Delete the businessYear
        restBusinessYearMockMvc
            .perform(delete(ENTITY_API_URL_ID, businessYear.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BusinessYear> businessYearList = businessYearRepository.findAll();
        assertThat(businessYearList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
