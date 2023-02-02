package inc.donau.storage.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import inc.donau.storage.IntegrationTest;
import inc.donau.storage.domain.CensusDocument;
import inc.donau.storage.domain.CensusItem;
import inc.donau.storage.domain.Resource;
import inc.donau.storage.repository.CensusItemRepository;
import inc.donau.storage.service.criteria.CensusItemCriteria;
import inc.donau.storage.service.dto.CensusItemDTO;
import inc.donau.storage.service.mapper.CensusItemMapper;
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
 * Integration tests for the {@link CensusItemResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CensusItemResourceIT {

    private static final Float DEFAULT_AMOUNT = 0F;
    private static final Float UPDATED_AMOUNT = 1F;
    private static final Float SMALLER_AMOUNT = 0F - 1F;

    private static final String ENTITY_API_URL = "/api/census-items";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CensusItemRepository censusItemRepository;

    @Autowired
    private CensusItemMapper censusItemMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCensusItemMockMvc;

    private CensusItem censusItem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CensusItem createEntity(EntityManager em) {
        CensusItem censusItem = new CensusItem().amount(DEFAULT_AMOUNT);
        // Add required entity
        CensusDocument censusDocument;
        if (TestUtil.findAll(em, CensusDocument.class).isEmpty()) {
            censusDocument = CensusDocumentResourceIT.createEntity(em);
            em.persist(censusDocument);
            em.flush();
        } else {
            censusDocument = TestUtil.findAll(em, CensusDocument.class).get(0);
        }
        censusItem.setCensusDocument(censusDocument);
        // Add required entity
        Resource resource;
        if (TestUtil.findAll(em, Resource.class).isEmpty()) {
            resource = ResourceResourceIT.createEntity(em);
            em.persist(resource);
            em.flush();
        } else {
            resource = TestUtil.findAll(em, Resource.class).get(0);
        }
        censusItem.setResource(resource);
        return censusItem;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CensusItem createUpdatedEntity(EntityManager em) {
        CensusItem censusItem = new CensusItem().amount(UPDATED_AMOUNT);
        // Add required entity
        CensusDocument censusDocument;
        if (TestUtil.findAll(em, CensusDocument.class).isEmpty()) {
            censusDocument = CensusDocumentResourceIT.createUpdatedEntity(em);
            em.persist(censusDocument);
            em.flush();
        } else {
            censusDocument = TestUtil.findAll(em, CensusDocument.class).get(0);
        }
        censusItem.setCensusDocument(censusDocument);
        // Add required entity
        Resource resource;
        if (TestUtil.findAll(em, Resource.class).isEmpty()) {
            resource = ResourceResourceIT.createUpdatedEntity(em);
            em.persist(resource);
            em.flush();
        } else {
            resource = TestUtil.findAll(em, Resource.class).get(0);
        }
        censusItem.setResource(resource);
        return censusItem;
    }

    @BeforeEach
    public void initTest() {
        censusItem = createEntity(em);
    }

    @Test
    @Transactional
    void createCensusItem() throws Exception {
        int databaseSizeBeforeCreate = censusItemRepository.findAll().size();
        // Create the CensusItem
        CensusItemDTO censusItemDTO = censusItemMapper.toDto(censusItem);
        restCensusItemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(censusItemDTO)))
            .andExpect(status().isCreated());

        // Validate the CensusItem in the database
        List<CensusItem> censusItemList = censusItemRepository.findAll();
        assertThat(censusItemList).hasSize(databaseSizeBeforeCreate + 1);
        CensusItem testCensusItem = censusItemList.get(censusItemList.size() - 1);
        assertThat(testCensusItem.getAmount()).isEqualTo(DEFAULT_AMOUNT);
    }

    @Test
    @Transactional
    void createCensusItemWithExistingId() throws Exception {
        // Create the CensusItem with an existing ID
        censusItem.setId(1L);
        CensusItemDTO censusItemDTO = censusItemMapper.toDto(censusItem);

        int databaseSizeBeforeCreate = censusItemRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCensusItemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(censusItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CensusItem in the database
        List<CensusItem> censusItemList = censusItemRepository.findAll();
        assertThat(censusItemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = censusItemRepository.findAll().size();
        // set the field null
        censusItem.setAmount(null);

        // Create the CensusItem, which fails.
        CensusItemDTO censusItemDTO = censusItemMapper.toDto(censusItem);

        restCensusItemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(censusItemDTO)))
            .andExpect(status().isBadRequest());

        List<CensusItem> censusItemList = censusItemRepository.findAll();
        assertThat(censusItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCensusItems() throws Exception {
        // Initialize the database
        censusItemRepository.saveAndFlush(censusItem);

        // Get all the censusItemList
        restCensusItemMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(censusItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())));
    }

    @Test
    @Transactional
    void getCensusItem() throws Exception {
        // Initialize the database
        censusItemRepository.saveAndFlush(censusItem);

        // Get the censusItem
        restCensusItemMockMvc
            .perform(get(ENTITY_API_URL_ID, censusItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(censusItem.getId().intValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()));
    }

    @Test
    @Transactional
    void getCensusItemsByIdFiltering() throws Exception {
        // Initialize the database
        censusItemRepository.saveAndFlush(censusItem);

        Long id = censusItem.getId();

        defaultCensusItemShouldBeFound("id.equals=" + id);
        defaultCensusItemShouldNotBeFound("id.notEquals=" + id);

        defaultCensusItemShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCensusItemShouldNotBeFound("id.greaterThan=" + id);

        defaultCensusItemShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCensusItemShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCensusItemsByAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        censusItemRepository.saveAndFlush(censusItem);

        // Get all the censusItemList where amount equals to DEFAULT_AMOUNT
        defaultCensusItemShouldBeFound("amount.equals=" + DEFAULT_AMOUNT);

        // Get all the censusItemList where amount equals to UPDATED_AMOUNT
        defaultCensusItemShouldNotBeFound("amount.equals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllCensusItemsByAmountIsInShouldWork() throws Exception {
        // Initialize the database
        censusItemRepository.saveAndFlush(censusItem);

        // Get all the censusItemList where amount in DEFAULT_AMOUNT or UPDATED_AMOUNT
        defaultCensusItemShouldBeFound("amount.in=" + DEFAULT_AMOUNT + "," + UPDATED_AMOUNT);

        // Get all the censusItemList where amount equals to UPDATED_AMOUNT
        defaultCensusItemShouldNotBeFound("amount.in=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllCensusItemsByAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        censusItemRepository.saveAndFlush(censusItem);

        // Get all the censusItemList where amount is not null
        defaultCensusItemShouldBeFound("amount.specified=true");

        // Get all the censusItemList where amount is null
        defaultCensusItemShouldNotBeFound("amount.specified=false");
    }

    @Test
    @Transactional
    void getAllCensusItemsByAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        censusItemRepository.saveAndFlush(censusItem);

        // Get all the censusItemList where amount is greater than or equal to DEFAULT_AMOUNT
        defaultCensusItemShouldBeFound("amount.greaterThanOrEqual=" + DEFAULT_AMOUNT);

        // Get all the censusItemList where amount is greater than or equal to UPDATED_AMOUNT
        defaultCensusItemShouldNotBeFound("amount.greaterThanOrEqual=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllCensusItemsByAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        censusItemRepository.saveAndFlush(censusItem);

        // Get all the censusItemList where amount is less than or equal to DEFAULT_AMOUNT
        defaultCensusItemShouldBeFound("amount.lessThanOrEqual=" + DEFAULT_AMOUNT);

        // Get all the censusItemList where amount is less than or equal to SMALLER_AMOUNT
        defaultCensusItemShouldNotBeFound("amount.lessThanOrEqual=" + SMALLER_AMOUNT);
    }

    @Test
    @Transactional
    void getAllCensusItemsByAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        censusItemRepository.saveAndFlush(censusItem);

        // Get all the censusItemList where amount is less than DEFAULT_AMOUNT
        defaultCensusItemShouldNotBeFound("amount.lessThan=" + DEFAULT_AMOUNT);

        // Get all the censusItemList where amount is less than UPDATED_AMOUNT
        defaultCensusItemShouldBeFound("amount.lessThan=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllCensusItemsByAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        censusItemRepository.saveAndFlush(censusItem);

        // Get all the censusItemList where amount is greater than DEFAULT_AMOUNT
        defaultCensusItemShouldNotBeFound("amount.greaterThan=" + DEFAULT_AMOUNT);

        // Get all the censusItemList where amount is greater than SMALLER_AMOUNT
        defaultCensusItemShouldBeFound("amount.greaterThan=" + SMALLER_AMOUNT);
    }

    @Test
    @Transactional
    void getAllCensusItemsByCensusDocumentIsEqualToSomething() throws Exception {
        CensusDocument censusDocument;
        if (TestUtil.findAll(em, CensusDocument.class).isEmpty()) {
            censusItemRepository.saveAndFlush(censusItem);
            censusDocument = CensusDocumentResourceIT.createEntity(em);
        } else {
            censusDocument = TestUtil.findAll(em, CensusDocument.class).get(0);
        }
        em.persist(censusDocument);
        em.flush();
        censusItem.setCensusDocument(censusDocument);
        censusItemRepository.saveAndFlush(censusItem);
        Long censusDocumentId = censusDocument.getId();

        // Get all the censusItemList where censusDocument equals to censusDocumentId
        defaultCensusItemShouldBeFound("censusDocumentId.equals=" + censusDocumentId);

        // Get all the censusItemList where censusDocument equals to (censusDocumentId + 1)
        defaultCensusItemShouldNotBeFound("censusDocumentId.equals=" + (censusDocumentId + 1));
    }

    @Test
    @Transactional
    void getAllCensusItemsByResourceIsEqualToSomething() throws Exception {
        Resource resource;
        if (TestUtil.findAll(em, Resource.class).isEmpty()) {
            censusItemRepository.saveAndFlush(censusItem);
            resource = ResourceResourceIT.createEntity(em);
        } else {
            resource = TestUtil.findAll(em, Resource.class).get(0);
        }
        em.persist(resource);
        em.flush();
        censusItem.setResource(resource);
        censusItemRepository.saveAndFlush(censusItem);
        Long resourceId = resource.getId();

        // Get all the censusItemList where resource equals to resourceId
        defaultCensusItemShouldBeFound("resourceId.equals=" + resourceId);

        // Get all the censusItemList where resource equals to (resourceId + 1)
        defaultCensusItemShouldNotBeFound("resourceId.equals=" + (resourceId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCensusItemShouldBeFound(String filter) throws Exception {
        restCensusItemMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(censusItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())));

        // Check, that the count call also returns 1
        restCensusItemMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCensusItemShouldNotBeFound(String filter) throws Exception {
        restCensusItemMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCensusItemMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCensusItem() throws Exception {
        // Get the censusItem
        restCensusItemMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCensusItem() throws Exception {
        // Initialize the database
        censusItemRepository.saveAndFlush(censusItem);

        int databaseSizeBeforeUpdate = censusItemRepository.findAll().size();

        // Update the censusItem
        CensusItem updatedCensusItem = censusItemRepository.findById(censusItem.getId()).get();
        // Disconnect from session so that the updates on updatedCensusItem are not directly saved in db
        em.detach(updatedCensusItem);
        updatedCensusItem.amount(UPDATED_AMOUNT);
        CensusItemDTO censusItemDTO = censusItemMapper.toDto(updatedCensusItem);

        restCensusItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, censusItemDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(censusItemDTO))
            )
            .andExpect(status().isOk());

        // Validate the CensusItem in the database
        List<CensusItem> censusItemList = censusItemRepository.findAll();
        assertThat(censusItemList).hasSize(databaseSizeBeforeUpdate);
        CensusItem testCensusItem = censusItemList.get(censusItemList.size() - 1);
        assertThat(testCensusItem.getAmount()).isEqualTo(UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void putNonExistingCensusItem() throws Exception {
        int databaseSizeBeforeUpdate = censusItemRepository.findAll().size();
        censusItem.setId(count.incrementAndGet());

        // Create the CensusItem
        CensusItemDTO censusItemDTO = censusItemMapper.toDto(censusItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCensusItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, censusItemDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(censusItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CensusItem in the database
        List<CensusItem> censusItemList = censusItemRepository.findAll();
        assertThat(censusItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCensusItem() throws Exception {
        int databaseSizeBeforeUpdate = censusItemRepository.findAll().size();
        censusItem.setId(count.incrementAndGet());

        // Create the CensusItem
        CensusItemDTO censusItemDTO = censusItemMapper.toDto(censusItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCensusItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(censusItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CensusItem in the database
        List<CensusItem> censusItemList = censusItemRepository.findAll();
        assertThat(censusItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCensusItem() throws Exception {
        int databaseSizeBeforeUpdate = censusItemRepository.findAll().size();
        censusItem.setId(count.incrementAndGet());

        // Create the CensusItem
        CensusItemDTO censusItemDTO = censusItemMapper.toDto(censusItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCensusItemMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(censusItemDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CensusItem in the database
        List<CensusItem> censusItemList = censusItemRepository.findAll();
        assertThat(censusItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCensusItemWithPatch() throws Exception {
        // Initialize the database
        censusItemRepository.saveAndFlush(censusItem);

        int databaseSizeBeforeUpdate = censusItemRepository.findAll().size();

        // Update the censusItem using partial update
        CensusItem partialUpdatedCensusItem = new CensusItem();
        partialUpdatedCensusItem.setId(censusItem.getId());

        partialUpdatedCensusItem.amount(UPDATED_AMOUNT);

        restCensusItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCensusItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCensusItem))
            )
            .andExpect(status().isOk());

        // Validate the CensusItem in the database
        List<CensusItem> censusItemList = censusItemRepository.findAll();
        assertThat(censusItemList).hasSize(databaseSizeBeforeUpdate);
        CensusItem testCensusItem = censusItemList.get(censusItemList.size() - 1);
        assertThat(testCensusItem.getAmount()).isEqualTo(UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void fullUpdateCensusItemWithPatch() throws Exception {
        // Initialize the database
        censusItemRepository.saveAndFlush(censusItem);

        int databaseSizeBeforeUpdate = censusItemRepository.findAll().size();

        // Update the censusItem using partial update
        CensusItem partialUpdatedCensusItem = new CensusItem();
        partialUpdatedCensusItem.setId(censusItem.getId());

        partialUpdatedCensusItem.amount(UPDATED_AMOUNT);

        restCensusItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCensusItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCensusItem))
            )
            .andExpect(status().isOk());

        // Validate the CensusItem in the database
        List<CensusItem> censusItemList = censusItemRepository.findAll();
        assertThat(censusItemList).hasSize(databaseSizeBeforeUpdate);
        CensusItem testCensusItem = censusItemList.get(censusItemList.size() - 1);
        assertThat(testCensusItem.getAmount()).isEqualTo(UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void patchNonExistingCensusItem() throws Exception {
        int databaseSizeBeforeUpdate = censusItemRepository.findAll().size();
        censusItem.setId(count.incrementAndGet());

        // Create the CensusItem
        CensusItemDTO censusItemDTO = censusItemMapper.toDto(censusItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCensusItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, censusItemDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(censusItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CensusItem in the database
        List<CensusItem> censusItemList = censusItemRepository.findAll();
        assertThat(censusItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCensusItem() throws Exception {
        int databaseSizeBeforeUpdate = censusItemRepository.findAll().size();
        censusItem.setId(count.incrementAndGet());

        // Create the CensusItem
        CensusItemDTO censusItemDTO = censusItemMapper.toDto(censusItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCensusItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(censusItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CensusItem in the database
        List<CensusItem> censusItemList = censusItemRepository.findAll();
        assertThat(censusItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCensusItem() throws Exception {
        int databaseSizeBeforeUpdate = censusItemRepository.findAll().size();
        censusItem.setId(count.incrementAndGet());

        // Create the CensusItem
        CensusItemDTO censusItemDTO = censusItemMapper.toDto(censusItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCensusItemMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(censusItemDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CensusItem in the database
        List<CensusItem> censusItemList = censusItemRepository.findAll();
        assertThat(censusItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCensusItem() throws Exception {
        // Initialize the database
        censusItemRepository.saveAndFlush(censusItem);

        int databaseSizeBeforeDelete = censusItemRepository.findAll().size();

        // Delete the censusItem
        restCensusItemMockMvc
            .perform(delete(ENTITY_API_URL_ID, censusItem.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CensusItem> censusItemList = censusItemRepository.findAll();
        assertThat(censusItemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
