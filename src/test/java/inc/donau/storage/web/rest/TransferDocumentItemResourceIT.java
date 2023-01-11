package inc.donau.storage.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import inc.donau.storage.IntegrationTest;
import inc.donau.storage.domain.Resource;
import inc.donau.storage.domain.TransferDocument;
import inc.donau.storage.domain.TransferDocumentItem;
import inc.donau.storage.repository.TransferDocumentItemRepository;
import inc.donau.storage.service.dto.TransferDocumentItemDTO;
import inc.donau.storage.service.mapper.TransferDocumentItemMapper;
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
 * Integration tests for the {@link TransferDocumentItemResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TransferDocumentItemResourceIT {

    private static final Float DEFAULT_AMOUNT = 0F;
    private static final Float UPDATED_AMOUNT = 1F;

    private static final Float DEFAULT_PRICE = 0F;
    private static final Float UPDATED_PRICE = 1F;

    private static final Float DEFAULT_VALUE = 0F;
    private static final Float UPDATED_VALUE = 1F;

    private static final String ENTITY_API_URL = "/api/transfer-document-items";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TransferDocumentItemRepository transferDocumentItemRepository;

    @Autowired
    private TransferDocumentItemMapper transferDocumentItemMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTransferDocumentItemMockMvc;

    private TransferDocumentItem transferDocumentItem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TransferDocumentItem createEntity(EntityManager em) {
        TransferDocumentItem transferDocumentItem = new TransferDocumentItem()
            .amount(DEFAULT_AMOUNT)
            .price(DEFAULT_PRICE)
            .value(DEFAULT_VALUE);
        // Add required entity
        TransferDocument transferDocument;
        if (TestUtil.findAll(em, TransferDocument.class).isEmpty()) {
            transferDocument = TransferDocumentResourceIT.createEntity(em);
            em.persist(transferDocument);
            em.flush();
        } else {
            transferDocument = TestUtil.findAll(em, TransferDocument.class).get(0);
        }
        transferDocumentItem.setTransferDocument(transferDocument);
        // Add required entity
        Resource resource;
        if (TestUtil.findAll(em, Resource.class).isEmpty()) {
            resource = ResourceResourceIT.createEntity(em);
            em.persist(resource);
            em.flush();
        } else {
            resource = TestUtil.findAll(em, Resource.class).get(0);
        }
        transferDocumentItem.setResource(resource);
        return transferDocumentItem;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TransferDocumentItem createUpdatedEntity(EntityManager em) {
        TransferDocumentItem transferDocumentItem = new TransferDocumentItem()
            .amount(UPDATED_AMOUNT)
            .price(UPDATED_PRICE)
            .value(UPDATED_VALUE);
        // Add required entity
        TransferDocument transferDocument;
        if (TestUtil.findAll(em, TransferDocument.class).isEmpty()) {
            transferDocument = TransferDocumentResourceIT.createUpdatedEntity(em);
            em.persist(transferDocument);
            em.flush();
        } else {
            transferDocument = TestUtil.findAll(em, TransferDocument.class).get(0);
        }
        transferDocumentItem.setTransferDocument(transferDocument);
        // Add required entity
        Resource resource;
        if (TestUtil.findAll(em, Resource.class).isEmpty()) {
            resource = ResourceResourceIT.createUpdatedEntity(em);
            em.persist(resource);
            em.flush();
        } else {
            resource = TestUtil.findAll(em, Resource.class).get(0);
        }
        transferDocumentItem.setResource(resource);
        return transferDocumentItem;
    }

    @BeforeEach
    public void initTest() {
        transferDocumentItem = createEntity(em);
    }

    @Test
    @Transactional
    void createTransferDocumentItem() throws Exception {
        int databaseSizeBeforeCreate = transferDocumentItemRepository.findAll().size();
        // Create the TransferDocumentItem
        TransferDocumentItemDTO transferDocumentItemDTO = transferDocumentItemMapper.toDto(transferDocumentItem);
        restTransferDocumentItemMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transferDocumentItemDTO))
            )
            .andExpect(status().isCreated());

        // Validate the TransferDocumentItem in the database
        List<TransferDocumentItem> transferDocumentItemList = transferDocumentItemRepository.findAll();
        assertThat(transferDocumentItemList).hasSize(databaseSizeBeforeCreate + 1);
        TransferDocumentItem testTransferDocumentItem = transferDocumentItemList.get(transferDocumentItemList.size() - 1);
        assertThat(testTransferDocumentItem.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testTransferDocumentItem.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testTransferDocumentItem.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    void createTransferDocumentItemWithExistingId() throws Exception {
        // Create the TransferDocumentItem with an existing ID
        transferDocumentItem.setId(1L);
        TransferDocumentItemDTO transferDocumentItemDTO = transferDocumentItemMapper.toDto(transferDocumentItem);

        int databaseSizeBeforeCreate = transferDocumentItemRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransferDocumentItemMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transferDocumentItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TransferDocumentItem in the database
        List<TransferDocumentItem> transferDocumentItemList = transferDocumentItemRepository.findAll();
        assertThat(transferDocumentItemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = transferDocumentItemRepository.findAll().size();
        // set the field null
        transferDocumentItem.setAmount(null);

        // Create the TransferDocumentItem, which fails.
        TransferDocumentItemDTO transferDocumentItemDTO = transferDocumentItemMapper.toDto(transferDocumentItem);

        restTransferDocumentItemMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transferDocumentItemDTO))
            )
            .andExpect(status().isBadRequest());

        List<TransferDocumentItem> transferDocumentItemList = transferDocumentItemRepository.findAll();
        assertThat(transferDocumentItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = transferDocumentItemRepository.findAll().size();
        // set the field null
        transferDocumentItem.setPrice(null);

        // Create the TransferDocumentItem, which fails.
        TransferDocumentItemDTO transferDocumentItemDTO = transferDocumentItemMapper.toDto(transferDocumentItem);

        restTransferDocumentItemMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transferDocumentItemDTO))
            )
            .andExpect(status().isBadRequest());

        List<TransferDocumentItem> transferDocumentItemList = transferDocumentItemRepository.findAll();
        assertThat(transferDocumentItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTransferDocumentItems() throws Exception {
        // Initialize the database
        transferDocumentItemRepository.saveAndFlush(transferDocumentItem);

        // Get all the transferDocumentItemList
        restTransferDocumentItemMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transferDocumentItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.doubleValue())));
    }

    @Test
    @Transactional
    void getTransferDocumentItem() throws Exception {
        // Initialize the database
        transferDocumentItemRepository.saveAndFlush(transferDocumentItem);

        // Get the transferDocumentItem
        restTransferDocumentItemMockMvc
            .perform(get(ENTITY_API_URL_ID, transferDocumentItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(transferDocumentItem.getId().intValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingTransferDocumentItem() throws Exception {
        // Get the transferDocumentItem
        restTransferDocumentItemMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTransferDocumentItem() throws Exception {
        // Initialize the database
        transferDocumentItemRepository.saveAndFlush(transferDocumentItem);

        int databaseSizeBeforeUpdate = transferDocumentItemRepository.findAll().size();

        // Update the transferDocumentItem
        TransferDocumentItem updatedTransferDocumentItem = transferDocumentItemRepository.findById(transferDocumentItem.getId()).get();
        // Disconnect from session so that the updates on updatedTransferDocumentItem are not directly saved in db
        em.detach(updatedTransferDocumentItem);
        updatedTransferDocumentItem.amount(UPDATED_AMOUNT).price(UPDATED_PRICE).value(UPDATED_VALUE);
        TransferDocumentItemDTO transferDocumentItemDTO = transferDocumentItemMapper.toDto(updatedTransferDocumentItem);

        restTransferDocumentItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, transferDocumentItemDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transferDocumentItemDTO))
            )
            .andExpect(status().isOk());

        // Validate the TransferDocumentItem in the database
        List<TransferDocumentItem> transferDocumentItemList = transferDocumentItemRepository.findAll();
        assertThat(transferDocumentItemList).hasSize(databaseSizeBeforeUpdate);
        TransferDocumentItem testTransferDocumentItem = transferDocumentItemList.get(transferDocumentItemList.size() - 1);
        assertThat(testTransferDocumentItem.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testTransferDocumentItem.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testTransferDocumentItem.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    void putNonExistingTransferDocumentItem() throws Exception {
        int databaseSizeBeforeUpdate = transferDocumentItemRepository.findAll().size();
        transferDocumentItem.setId(count.incrementAndGet());

        // Create the TransferDocumentItem
        TransferDocumentItemDTO transferDocumentItemDTO = transferDocumentItemMapper.toDto(transferDocumentItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransferDocumentItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, transferDocumentItemDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transferDocumentItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TransferDocumentItem in the database
        List<TransferDocumentItem> transferDocumentItemList = transferDocumentItemRepository.findAll();
        assertThat(transferDocumentItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTransferDocumentItem() throws Exception {
        int databaseSizeBeforeUpdate = transferDocumentItemRepository.findAll().size();
        transferDocumentItem.setId(count.incrementAndGet());

        // Create the TransferDocumentItem
        TransferDocumentItemDTO transferDocumentItemDTO = transferDocumentItemMapper.toDto(transferDocumentItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransferDocumentItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transferDocumentItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TransferDocumentItem in the database
        List<TransferDocumentItem> transferDocumentItemList = transferDocumentItemRepository.findAll();
        assertThat(transferDocumentItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTransferDocumentItem() throws Exception {
        int databaseSizeBeforeUpdate = transferDocumentItemRepository.findAll().size();
        transferDocumentItem.setId(count.incrementAndGet());

        // Create the TransferDocumentItem
        TransferDocumentItemDTO transferDocumentItemDTO = transferDocumentItemMapper.toDto(transferDocumentItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransferDocumentItemMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transferDocumentItemDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TransferDocumentItem in the database
        List<TransferDocumentItem> transferDocumentItemList = transferDocumentItemRepository.findAll();
        assertThat(transferDocumentItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTransferDocumentItemWithPatch() throws Exception {
        // Initialize the database
        transferDocumentItemRepository.saveAndFlush(transferDocumentItem);

        int databaseSizeBeforeUpdate = transferDocumentItemRepository.findAll().size();

        // Update the transferDocumentItem using partial update
        TransferDocumentItem partialUpdatedTransferDocumentItem = new TransferDocumentItem();
        partialUpdatedTransferDocumentItem.setId(transferDocumentItem.getId());

        restTransferDocumentItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTransferDocumentItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTransferDocumentItem))
            )
            .andExpect(status().isOk());

        // Validate the TransferDocumentItem in the database
        List<TransferDocumentItem> transferDocumentItemList = transferDocumentItemRepository.findAll();
        assertThat(transferDocumentItemList).hasSize(databaseSizeBeforeUpdate);
        TransferDocumentItem testTransferDocumentItem = transferDocumentItemList.get(transferDocumentItemList.size() - 1);
        assertThat(testTransferDocumentItem.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testTransferDocumentItem.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testTransferDocumentItem.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    void fullUpdateTransferDocumentItemWithPatch() throws Exception {
        // Initialize the database
        transferDocumentItemRepository.saveAndFlush(transferDocumentItem);

        int databaseSizeBeforeUpdate = transferDocumentItemRepository.findAll().size();

        // Update the transferDocumentItem using partial update
        TransferDocumentItem partialUpdatedTransferDocumentItem = new TransferDocumentItem();
        partialUpdatedTransferDocumentItem.setId(transferDocumentItem.getId());

        partialUpdatedTransferDocumentItem.amount(UPDATED_AMOUNT).price(UPDATED_PRICE).value(UPDATED_VALUE);

        restTransferDocumentItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTransferDocumentItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTransferDocumentItem))
            )
            .andExpect(status().isOk());

        // Validate the TransferDocumentItem in the database
        List<TransferDocumentItem> transferDocumentItemList = transferDocumentItemRepository.findAll();
        assertThat(transferDocumentItemList).hasSize(databaseSizeBeforeUpdate);
        TransferDocumentItem testTransferDocumentItem = transferDocumentItemList.get(transferDocumentItemList.size() - 1);
        assertThat(testTransferDocumentItem.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testTransferDocumentItem.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testTransferDocumentItem.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    void patchNonExistingTransferDocumentItem() throws Exception {
        int databaseSizeBeforeUpdate = transferDocumentItemRepository.findAll().size();
        transferDocumentItem.setId(count.incrementAndGet());

        // Create the TransferDocumentItem
        TransferDocumentItemDTO transferDocumentItemDTO = transferDocumentItemMapper.toDto(transferDocumentItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransferDocumentItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, transferDocumentItemDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(transferDocumentItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TransferDocumentItem in the database
        List<TransferDocumentItem> transferDocumentItemList = transferDocumentItemRepository.findAll();
        assertThat(transferDocumentItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTransferDocumentItem() throws Exception {
        int databaseSizeBeforeUpdate = transferDocumentItemRepository.findAll().size();
        transferDocumentItem.setId(count.incrementAndGet());

        // Create the TransferDocumentItem
        TransferDocumentItemDTO transferDocumentItemDTO = transferDocumentItemMapper.toDto(transferDocumentItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransferDocumentItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(transferDocumentItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TransferDocumentItem in the database
        List<TransferDocumentItem> transferDocumentItemList = transferDocumentItemRepository.findAll();
        assertThat(transferDocumentItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTransferDocumentItem() throws Exception {
        int databaseSizeBeforeUpdate = transferDocumentItemRepository.findAll().size();
        transferDocumentItem.setId(count.incrementAndGet());

        // Create the TransferDocumentItem
        TransferDocumentItemDTO transferDocumentItemDTO = transferDocumentItemMapper.toDto(transferDocumentItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransferDocumentItemMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(transferDocumentItemDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TransferDocumentItem in the database
        List<TransferDocumentItem> transferDocumentItemList = transferDocumentItemRepository.findAll();
        assertThat(transferDocumentItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTransferDocumentItem() throws Exception {
        // Initialize the database
        transferDocumentItemRepository.saveAndFlush(transferDocumentItem);

        int databaseSizeBeforeDelete = transferDocumentItemRepository.findAll().size();

        // Delete the transferDocumentItem
        restTransferDocumentItemMockMvc
            .perform(delete(ENTITY_API_URL_ID, transferDocumentItem.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TransferDocumentItem> transferDocumentItemList = transferDocumentItemRepository.findAll();
        assertThat(transferDocumentItemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
