package inc.donau.storage.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import inc.donau.storage.IntegrationTest;
import inc.donau.storage.domain.CensusItem;
import inc.donau.storage.domain.Company;
import inc.donau.storage.domain.MeasurementUnit;
import inc.donau.storage.domain.Resource;
import inc.donau.storage.domain.TransferDocumentItem;
import inc.donau.storage.domain.enumeration.ResourceType;
import inc.donau.storage.repository.ResourceRepository;
import inc.donau.storage.service.criteria.ResourceCriteria;
import inc.donau.storage.service.dto.ResourceDTO;
import inc.donau.storage.service.mapper.ResourceMapper;
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
 * Integration tests for the {@link ResourceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ResourceResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final ResourceType DEFAULT_TYPE = ResourceType.MATERIAL;
    private static final ResourceType UPDATED_TYPE = ResourceType.PRODUCT;

    private static final String ENTITY_API_URL = "/api/resources";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private ResourceMapper resourceMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restResourceMockMvc;

    private Resource resource;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Resource createEntity(EntityManager em) {
        Resource resource = new Resource().name(DEFAULT_NAME).type(DEFAULT_TYPE);
        // Add required entity
        MeasurementUnit measurementUnit;
        if (TestUtil.findAll(em, MeasurementUnit.class).isEmpty()) {
            measurementUnit = MeasurementUnitResourceIT.createEntity(em);
            em.persist(measurementUnit);
            em.flush();
        } else {
            measurementUnit = TestUtil.findAll(em, MeasurementUnit.class).get(0);
        }
        resource.setUnit(measurementUnit);
        // Add required entity
        Company company;
        if (TestUtil.findAll(em, Company.class).isEmpty()) {
            company = CompanyResourceIT.createEntity(em);
            em.persist(company);
            em.flush();
        } else {
            company = TestUtil.findAll(em, Company.class).get(0);
        }
        resource.setCompany(company);
        return resource;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Resource createUpdatedEntity(EntityManager em) {
        Resource resource = new Resource().name(UPDATED_NAME).type(UPDATED_TYPE);
        // Add required entity
        MeasurementUnit measurementUnit;
        if (TestUtil.findAll(em, MeasurementUnit.class).isEmpty()) {
            measurementUnit = MeasurementUnitResourceIT.createUpdatedEntity(em);
            em.persist(measurementUnit);
            em.flush();
        } else {
            measurementUnit = TestUtil.findAll(em, MeasurementUnit.class).get(0);
        }
        resource.setUnit(measurementUnit);
        // Add required entity
        Company company;
        if (TestUtil.findAll(em, Company.class).isEmpty()) {
            company = CompanyResourceIT.createUpdatedEntity(em);
            em.persist(company);
            em.flush();
        } else {
            company = TestUtil.findAll(em, Company.class).get(0);
        }
        resource.setCompany(company);
        return resource;
    }

    @BeforeEach
    public void initTest() {
        resource = createEntity(em);
    }

    @Test
    @Transactional
    void createResource() throws Exception {
        int databaseSizeBeforeCreate = resourceRepository.findAll().size();
        // Create the Resource
        ResourceDTO resourceDTO = resourceMapper.toDto(resource);
        restResourceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(resourceDTO)))
            .andExpect(status().isCreated());

        // Validate the Resource in the database
        List<Resource> resourceList = resourceRepository.findAll();
        assertThat(resourceList).hasSize(databaseSizeBeforeCreate + 1);
        Resource testResource = resourceList.get(resourceList.size() - 1);
        assertThat(testResource.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testResource.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    void createResourceWithExistingId() throws Exception {
        // Create the Resource with an existing ID
        resource.setId(1L);
        ResourceDTO resourceDTO = resourceMapper.toDto(resource);

        int databaseSizeBeforeCreate = resourceRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restResourceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(resourceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Resource in the database
        List<Resource> resourceList = resourceRepository.findAll();
        assertThat(resourceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = resourceRepository.findAll().size();
        // set the field null
        resource.setName(null);

        // Create the Resource, which fails.
        ResourceDTO resourceDTO = resourceMapper.toDto(resource);

        restResourceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(resourceDTO)))
            .andExpect(status().isBadRequest());

        List<Resource> resourceList = resourceRepository.findAll();
        assertThat(resourceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = resourceRepository.findAll().size();
        // set the field null
        resource.setType(null);

        // Create the Resource, which fails.
        ResourceDTO resourceDTO = resourceMapper.toDto(resource);

        restResourceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(resourceDTO)))
            .andExpect(status().isBadRequest());

        List<Resource> resourceList = resourceRepository.findAll();
        assertThat(resourceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllResources() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList
        restResourceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resource.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }

    @Test
    @Transactional
    void getResource() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get the resource
        restResourceMockMvc
            .perform(get(ENTITY_API_URL_ID, resource.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(resource.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    void getResourcesByIdFiltering() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        Long id = resource.getId();

        defaultResourceShouldBeFound("id.equals=" + id);
        defaultResourceShouldNotBeFound("id.notEquals=" + id);

        defaultResourceShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultResourceShouldNotBeFound("id.greaterThan=" + id);

        defaultResourceShouldBeFound("id.lessThanOrEqual=" + id);
        defaultResourceShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllResourcesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where name equals to DEFAULT_NAME
        defaultResourceShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the resourceList where name equals to UPDATED_NAME
        defaultResourceShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllResourcesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where name in DEFAULT_NAME or UPDATED_NAME
        defaultResourceShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the resourceList where name equals to UPDATED_NAME
        defaultResourceShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllResourcesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where name is not null
        defaultResourceShouldBeFound("name.specified=true");

        // Get all the resourceList where name is null
        defaultResourceShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllResourcesByNameContainsSomething() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where name contains DEFAULT_NAME
        defaultResourceShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the resourceList where name contains UPDATED_NAME
        defaultResourceShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllResourcesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where name does not contain DEFAULT_NAME
        defaultResourceShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the resourceList where name does not contain UPDATED_NAME
        defaultResourceShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllResourcesByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where type equals to DEFAULT_TYPE
        defaultResourceShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the resourceList where type equals to UPDATED_TYPE
        defaultResourceShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllResourcesByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultResourceShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the resourceList where type equals to UPDATED_TYPE
        defaultResourceShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllResourcesByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where type is not null
        defaultResourceShouldBeFound("type.specified=true");

        // Get all the resourceList where type is null
        defaultResourceShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    void getAllResourcesByCensusItemIsEqualToSomething() throws Exception {
        CensusItem censusItem;
        if (TestUtil.findAll(em, CensusItem.class).isEmpty()) {
            resourceRepository.saveAndFlush(resource);
            censusItem = CensusItemResourceIT.createEntity(em);
        } else {
            censusItem = TestUtil.findAll(em, CensusItem.class).get(0);
        }
        em.persist(censusItem);
        em.flush();
        resource.addCensusItem(censusItem);
        resourceRepository.saveAndFlush(resource);
        Long censusItemId = censusItem.getId();

        // Get all the resourceList where censusItem equals to censusItemId
        defaultResourceShouldBeFound("censusItemId.equals=" + censusItemId);

        // Get all the resourceList where censusItem equals to (censusItemId + 1)
        defaultResourceShouldNotBeFound("censusItemId.equals=" + (censusItemId + 1));
    }

    @Test
    @Transactional
    void getAllResourcesByTransferDocumentItemIsEqualToSomething() throws Exception {
        TransferDocumentItem transferDocumentItem;
        if (TestUtil.findAll(em, TransferDocumentItem.class).isEmpty()) {
            resourceRepository.saveAndFlush(resource);
            transferDocumentItem = TransferDocumentItemResourceIT.createEntity(em);
        } else {
            transferDocumentItem = TestUtil.findAll(em, TransferDocumentItem.class).get(0);
        }
        em.persist(transferDocumentItem);
        em.flush();
        resource.addTransferDocumentItem(transferDocumentItem);
        resourceRepository.saveAndFlush(resource);
        Long transferDocumentItemId = transferDocumentItem.getId();

        // Get all the resourceList where transferDocumentItem equals to transferDocumentItemId
        defaultResourceShouldBeFound("transferDocumentItemId.equals=" + transferDocumentItemId);

        // Get all the resourceList where transferDocumentItem equals to (transferDocumentItemId + 1)
        defaultResourceShouldNotBeFound("transferDocumentItemId.equals=" + (transferDocumentItemId + 1));
    }

    @Test
    @Transactional
    void getAllResourcesByUnitIsEqualToSomething() throws Exception {
        MeasurementUnit unit;
        if (TestUtil.findAll(em, MeasurementUnit.class).isEmpty()) {
            resourceRepository.saveAndFlush(resource);
            unit = MeasurementUnitResourceIT.createEntity(em);
        } else {
            unit = TestUtil.findAll(em, MeasurementUnit.class).get(0);
        }
        em.persist(unit);
        em.flush();
        resource.setUnit(unit);
        resourceRepository.saveAndFlush(resource);
        Long unitId = unit.getId();

        // Get all the resourceList where unit equals to unitId
        defaultResourceShouldBeFound("unitId.equals=" + unitId);

        // Get all the resourceList where unit equals to (unitId + 1)
        defaultResourceShouldNotBeFound("unitId.equals=" + (unitId + 1));
    }

    @Test
    @Transactional
    void getAllResourcesByCompanyIsEqualToSomething() throws Exception {
        Company company;
        if (TestUtil.findAll(em, Company.class).isEmpty()) {
            resourceRepository.saveAndFlush(resource);
            company = CompanyResourceIT.createEntity(em);
        } else {
            company = TestUtil.findAll(em, Company.class).get(0);
        }
        em.persist(company);
        em.flush();
        resource.setCompany(company);
        resourceRepository.saveAndFlush(resource);
        Long companyId = company.getId();

        // Get all the resourceList where company equals to companyId
        defaultResourceShouldBeFound("companyId.equals=" + companyId);

        // Get all the resourceList where company equals to (companyId + 1)
        defaultResourceShouldNotBeFound("companyId.equals=" + (companyId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultResourceShouldBeFound(String filter) throws Exception {
        restResourceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resource.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));

        // Check, that the count call also returns 1
        restResourceMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultResourceShouldNotBeFound(String filter) throws Exception {
        restResourceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restResourceMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingResource() throws Exception {
        // Get the resource
        restResourceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingResource() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        int databaseSizeBeforeUpdate = resourceRepository.findAll().size();

        // Update the resource
        Resource updatedResource = resourceRepository.findById(resource.getId()).get();
        // Disconnect from session so that the updates on updatedResource are not directly saved in db
        em.detach(updatedResource);
        updatedResource.name(UPDATED_NAME).type(UPDATED_TYPE);
        ResourceDTO resourceDTO = resourceMapper.toDto(updatedResource);

        restResourceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, resourceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(resourceDTO))
            )
            .andExpect(status().isOk());

        // Validate the Resource in the database
        List<Resource> resourceList = resourceRepository.findAll();
        assertThat(resourceList).hasSize(databaseSizeBeforeUpdate);
        Resource testResource = resourceList.get(resourceList.size() - 1);
        assertThat(testResource.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testResource.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingResource() throws Exception {
        int databaseSizeBeforeUpdate = resourceRepository.findAll().size();
        resource.setId(count.incrementAndGet());

        // Create the Resource
        ResourceDTO resourceDTO = resourceMapper.toDto(resource);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResourceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, resourceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(resourceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Resource in the database
        List<Resource> resourceList = resourceRepository.findAll();
        assertThat(resourceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchResource() throws Exception {
        int databaseSizeBeforeUpdate = resourceRepository.findAll().size();
        resource.setId(count.incrementAndGet());

        // Create the Resource
        ResourceDTO resourceDTO = resourceMapper.toDto(resource);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResourceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(resourceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Resource in the database
        List<Resource> resourceList = resourceRepository.findAll();
        assertThat(resourceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamResource() throws Exception {
        int databaseSizeBeforeUpdate = resourceRepository.findAll().size();
        resource.setId(count.incrementAndGet());

        // Create the Resource
        ResourceDTO resourceDTO = resourceMapper.toDto(resource);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResourceMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(resourceDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Resource in the database
        List<Resource> resourceList = resourceRepository.findAll();
        assertThat(resourceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateResourceWithPatch() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        int databaseSizeBeforeUpdate = resourceRepository.findAll().size();

        // Update the resource using partial update
        Resource partialUpdatedResource = new Resource();
        partialUpdatedResource.setId(resource.getId());

        partialUpdatedResource.type(UPDATED_TYPE);

        restResourceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedResource.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedResource))
            )
            .andExpect(status().isOk());

        // Validate the Resource in the database
        List<Resource> resourceList = resourceRepository.findAll();
        assertThat(resourceList).hasSize(databaseSizeBeforeUpdate);
        Resource testResource = resourceList.get(resourceList.size() - 1);
        assertThat(testResource.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testResource.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateResourceWithPatch() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        int databaseSizeBeforeUpdate = resourceRepository.findAll().size();

        // Update the resource using partial update
        Resource partialUpdatedResource = new Resource();
        partialUpdatedResource.setId(resource.getId());

        partialUpdatedResource.name(UPDATED_NAME).type(UPDATED_TYPE);

        restResourceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedResource.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedResource))
            )
            .andExpect(status().isOk());

        // Validate the Resource in the database
        List<Resource> resourceList = resourceRepository.findAll();
        assertThat(resourceList).hasSize(databaseSizeBeforeUpdate);
        Resource testResource = resourceList.get(resourceList.size() - 1);
        assertThat(testResource.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testResource.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingResource() throws Exception {
        int databaseSizeBeforeUpdate = resourceRepository.findAll().size();
        resource.setId(count.incrementAndGet());

        // Create the Resource
        ResourceDTO resourceDTO = resourceMapper.toDto(resource);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResourceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, resourceDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(resourceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Resource in the database
        List<Resource> resourceList = resourceRepository.findAll();
        assertThat(resourceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchResource() throws Exception {
        int databaseSizeBeforeUpdate = resourceRepository.findAll().size();
        resource.setId(count.incrementAndGet());

        // Create the Resource
        ResourceDTO resourceDTO = resourceMapper.toDto(resource);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResourceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(resourceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Resource in the database
        List<Resource> resourceList = resourceRepository.findAll();
        assertThat(resourceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamResource() throws Exception {
        int databaseSizeBeforeUpdate = resourceRepository.findAll().size();
        resource.setId(count.incrementAndGet());

        // Create the Resource
        ResourceDTO resourceDTO = resourceMapper.toDto(resource);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResourceMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(resourceDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Resource in the database
        List<Resource> resourceList = resourceRepository.findAll();
        assertThat(resourceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteResource() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        int databaseSizeBeforeDelete = resourceRepository.findAll().size();

        // Delete the resource
        restResourceMockMvc
            .perform(delete(ENTITY_API_URL_ID, resource.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Resource> resourceList = resourceRepository.findAll();
        assertThat(resourceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
