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
import inc.donau.storage.service.criteria.TransferDocumentItemCriteria;
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
    private static final Float SMALLER_AMOUNT = 0F - 1F;

    private static final Float DEFAULT_PRICE = 0F;
    private static final Float UPDATED_PRICE = 1F;
    private static final Float SMALLER_PRICE = 0F - 1F;

    private static final Float DEFAULT_TRANSFER_VALUE = 0F;
    private static final Float UPDATED_TRANSFER_VALUE = 1F;
    private static final Float SMALLER_TRANSFER_VALUE = 0F - 1F;

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
            .transferValue(DEFAULT_TRANSFER_VALUE);
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
            .transferValue(UPDATED_TRANSFER_VALUE);
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
        assertThat(testTransferDocumentItem.getTransferValue()).isEqualTo(DEFAULT_TRANSFER_VALUE);
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
            .andExpect(jsonPath("$.[*].transferValue").value(hasItem(DEFAULT_TRANSFER_VALUE.doubleValue())));
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
            .andExpect(jsonPath("$.transferValue").value(DEFAULT_TRANSFER_VALUE.doubleValue()));
    }

    @Test
    @Transactional
    void getTransferDocumentItemsByIdFiltering() throws Exception {
        // Initialize the database
        transferDocumentItemRepository.saveAndFlush(transferDocumentItem);

        Long id = transferDocumentItem.getId();

        defaultTransferDocumentItemShouldBeFound("id.equals=" + id);
        defaultTransferDocumentItemShouldNotBeFound("id.notEquals=" + id);

        defaultTransferDocumentItemShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTransferDocumentItemShouldNotBeFound("id.greaterThan=" + id);

        defaultTransferDocumentItemShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTransferDocumentItemShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllTransferDocumentItemsByAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        transferDocumentItemRepository.saveAndFlush(transferDocumentItem);

        // Get all the transferDocumentItemList where amount equals to DEFAULT_AMOUNT
        defaultTransferDocumentItemShouldBeFound("amount.equals=" + DEFAULT_AMOUNT);

        // Get all the transferDocumentItemList where amount equals to UPDATED_AMOUNT
        defaultTransferDocumentItemShouldNotBeFound("amount.equals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllTransferDocumentItemsByAmountIsInShouldWork() throws Exception {
        // Initialize the database
        transferDocumentItemRepository.saveAndFlush(transferDocumentItem);

        // Get all the transferDocumentItemList where amount in DEFAULT_AMOUNT or UPDATED_AMOUNT
        defaultTransferDocumentItemShouldBeFound("amount.in=" + DEFAULT_AMOUNT + "," + UPDATED_AMOUNT);

        // Get all the transferDocumentItemList where amount equals to UPDATED_AMOUNT
        defaultTransferDocumentItemShouldNotBeFound("amount.in=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllTransferDocumentItemsByAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        transferDocumentItemRepository.saveAndFlush(transferDocumentItem);

        // Get all the transferDocumentItemList where amount is not null
        defaultTransferDocumentItemShouldBeFound("amount.specified=true");

        // Get all the transferDocumentItemList where amount is null
        defaultTransferDocumentItemShouldNotBeFound("amount.specified=false");
    }

    @Test
    @Transactional
    void getAllTransferDocumentItemsByAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transferDocumentItemRepository.saveAndFlush(transferDocumentItem);

        // Get all the transferDocumentItemList where amount is greater than or equal to DEFAULT_AMOUNT
        defaultTransferDocumentItemShouldBeFound("amount.greaterThanOrEqual=" + DEFAULT_AMOUNT);

        // Get all the transferDocumentItemList where amount is greater than or equal to UPDATED_AMOUNT
        defaultTransferDocumentItemShouldNotBeFound("amount.greaterThanOrEqual=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllTransferDocumentItemsByAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transferDocumentItemRepository.saveAndFlush(transferDocumentItem);

        // Get all the transferDocumentItemList where amount is less than or equal to DEFAULT_AMOUNT
        defaultTransferDocumentItemShouldBeFound("amount.lessThanOrEqual=" + DEFAULT_AMOUNT);

        // Get all the transferDocumentItemList where amount is less than or equal to SMALLER_AMOUNT
        defaultTransferDocumentItemShouldNotBeFound("amount.lessThanOrEqual=" + SMALLER_AMOUNT);
    }

    @Test
    @Transactional
    void getAllTransferDocumentItemsByAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        transferDocumentItemRepository.saveAndFlush(transferDocumentItem);

        // Get all the transferDocumentItemList where amount is less than DEFAULT_AMOUNT
        defaultTransferDocumentItemShouldNotBeFound("amount.lessThan=" + DEFAULT_AMOUNT);

        // Get all the transferDocumentItemList where amount is less than UPDATED_AMOUNT
        defaultTransferDocumentItemShouldBeFound("amount.lessThan=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllTransferDocumentItemsByAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        transferDocumentItemRepository.saveAndFlush(transferDocumentItem);

        // Get all the transferDocumentItemList where amount is greater than DEFAULT_AMOUNT
        defaultTransferDocumentItemShouldNotBeFound("amount.greaterThan=" + DEFAULT_AMOUNT);

        // Get all the transferDocumentItemList where amount is greater than SMALLER_AMOUNT
        defaultTransferDocumentItemShouldBeFound("amount.greaterThan=" + SMALLER_AMOUNT);
    }

    @Test
    @Transactional
    void getAllTransferDocumentItemsByPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        transferDocumentItemRepository.saveAndFlush(transferDocumentItem);

        // Get all the transferDocumentItemList where price equals to DEFAULT_PRICE
        defaultTransferDocumentItemShouldBeFound("price.equals=" + DEFAULT_PRICE);

        // Get all the transferDocumentItemList where price equals to UPDATED_PRICE
        defaultTransferDocumentItemShouldNotBeFound("price.equals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllTransferDocumentItemsByPriceIsInShouldWork() throws Exception {
        // Initialize the database
        transferDocumentItemRepository.saveAndFlush(transferDocumentItem);

        // Get all the transferDocumentItemList where price in DEFAULT_PRICE or UPDATED_PRICE
        defaultTransferDocumentItemShouldBeFound("price.in=" + DEFAULT_PRICE + "," + UPDATED_PRICE);

        // Get all the transferDocumentItemList where price equals to UPDATED_PRICE
        defaultTransferDocumentItemShouldNotBeFound("price.in=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllTransferDocumentItemsByPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        transferDocumentItemRepository.saveAndFlush(transferDocumentItem);

        // Get all the transferDocumentItemList where price is not null
        defaultTransferDocumentItemShouldBeFound("price.specified=true");

        // Get all the transferDocumentItemList where price is null
        defaultTransferDocumentItemShouldNotBeFound("price.specified=false");
    }

    @Test
    @Transactional
    void getAllTransferDocumentItemsByPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transferDocumentItemRepository.saveAndFlush(transferDocumentItem);

        // Get all the transferDocumentItemList where price is greater than or equal to DEFAULT_PRICE
        defaultTransferDocumentItemShouldBeFound("price.greaterThanOrEqual=" + DEFAULT_PRICE);

        // Get all the transferDocumentItemList where price is greater than or equal to UPDATED_PRICE
        defaultTransferDocumentItemShouldNotBeFound("price.greaterThanOrEqual=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllTransferDocumentItemsByPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transferDocumentItemRepository.saveAndFlush(transferDocumentItem);

        // Get all the transferDocumentItemList where price is less than or equal to DEFAULT_PRICE
        defaultTransferDocumentItemShouldBeFound("price.lessThanOrEqual=" + DEFAULT_PRICE);

        // Get all the transferDocumentItemList where price is less than or equal to SMALLER_PRICE
        defaultTransferDocumentItemShouldNotBeFound("price.lessThanOrEqual=" + SMALLER_PRICE);
    }

    @Test
    @Transactional
    void getAllTransferDocumentItemsByPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        transferDocumentItemRepository.saveAndFlush(transferDocumentItem);

        // Get all the transferDocumentItemList where price is less than DEFAULT_PRICE
        defaultTransferDocumentItemShouldNotBeFound("price.lessThan=" + DEFAULT_PRICE);

        // Get all the transferDocumentItemList where price is less than UPDATED_PRICE
        defaultTransferDocumentItemShouldBeFound("price.lessThan=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllTransferDocumentItemsByPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        transferDocumentItemRepository.saveAndFlush(transferDocumentItem);

        // Get all the transferDocumentItemList where price is greater than DEFAULT_PRICE
        defaultTransferDocumentItemShouldNotBeFound("price.greaterThan=" + DEFAULT_PRICE);

        // Get all the transferDocumentItemList where price is greater than SMALLER_PRICE
        defaultTransferDocumentItemShouldBeFound("price.greaterThan=" + SMALLER_PRICE);
    }

    @Test
    @Transactional
    void getAllTransferDocumentItemsByTransferValueIsEqualToSomething() throws Exception {
        // Initialize the database
        transferDocumentItemRepository.saveAndFlush(transferDocumentItem);

        // Get all the transferDocumentItemList where transferValue equals to DEFAULT_TRANSFER_VALUE
        defaultTransferDocumentItemShouldBeFound("transferValue.equals=" + DEFAULT_TRANSFER_VALUE);

        // Get all the transferDocumentItemList where transferValue equals to UPDATED_TRANSFER_VALUE
        defaultTransferDocumentItemShouldNotBeFound("transferValue.equals=" + UPDATED_TRANSFER_VALUE);
    }

    @Test
    @Transactional
    void getAllTransferDocumentItemsByTransferValueIsInShouldWork() throws Exception {
        // Initialize the database
        transferDocumentItemRepository.saveAndFlush(transferDocumentItem);

        // Get all the transferDocumentItemList where transferValue in DEFAULT_TRANSFER_VALUE or UPDATED_TRANSFER_VALUE
        defaultTransferDocumentItemShouldBeFound("transferValue.in=" + DEFAULT_TRANSFER_VALUE + "," + UPDATED_TRANSFER_VALUE);

        // Get all the transferDocumentItemList where transferValue equals to UPDATED_TRANSFER_VALUE
        defaultTransferDocumentItemShouldNotBeFound("transferValue.in=" + UPDATED_TRANSFER_VALUE);
    }

    @Test
    @Transactional
    void getAllTransferDocumentItemsByTransferValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        transferDocumentItemRepository.saveAndFlush(transferDocumentItem);

        // Get all the transferDocumentItemList where transferValue is not null
        defaultTransferDocumentItemShouldBeFound("transferValue.specified=true");

        // Get all the transferDocumentItemList where transferValue is null
        defaultTransferDocumentItemShouldNotBeFound("transferValue.specified=false");
    }

    @Test
    @Transactional
    void getAllTransferDocumentItemsByTransferValueIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transferDocumentItemRepository.saveAndFlush(transferDocumentItem);

        // Get all the transferDocumentItemList where transferValue is greater than or equal to DEFAULT_TRANSFER_VALUE
        defaultTransferDocumentItemShouldBeFound("transferValue.greaterThanOrEqual=" + DEFAULT_TRANSFER_VALUE);

        // Get all the transferDocumentItemList where transferValue is greater than or equal to UPDATED_TRANSFER_VALUE
        defaultTransferDocumentItemShouldNotBeFound("transferValue.greaterThanOrEqual=" + UPDATED_TRANSFER_VALUE);
    }

    @Test
    @Transactional
    void getAllTransferDocumentItemsByTransferValueIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transferDocumentItemRepository.saveAndFlush(transferDocumentItem);

        // Get all the transferDocumentItemList where transferValue is less than or equal to DEFAULT_TRANSFER_VALUE
        defaultTransferDocumentItemShouldBeFound("transferValue.lessThanOrEqual=" + DEFAULT_TRANSFER_VALUE);

        // Get all the transferDocumentItemList where transferValue is less than or equal to SMALLER_TRANSFER_VALUE
        defaultTransferDocumentItemShouldNotBeFound("transferValue.lessThanOrEqual=" + SMALLER_TRANSFER_VALUE);
    }

    @Test
    @Transactional
    void getAllTransferDocumentItemsByTransferValueIsLessThanSomething() throws Exception {
        // Initialize the database
        transferDocumentItemRepository.saveAndFlush(transferDocumentItem);

        // Get all the transferDocumentItemList where transferValue is less than DEFAULT_TRANSFER_VALUE
        defaultTransferDocumentItemShouldNotBeFound("transferValue.lessThan=" + DEFAULT_TRANSFER_VALUE);

        // Get all the transferDocumentItemList where transferValue is less than UPDATED_TRANSFER_VALUE
        defaultTransferDocumentItemShouldBeFound("transferValue.lessThan=" + UPDATED_TRANSFER_VALUE);
    }

    @Test
    @Transactional
    void getAllTransferDocumentItemsByTransferValueIsGreaterThanSomething() throws Exception {
        // Initialize the database
        transferDocumentItemRepository.saveAndFlush(transferDocumentItem);

        // Get all the transferDocumentItemList where transferValue is greater than DEFAULT_TRANSFER_VALUE
        defaultTransferDocumentItemShouldNotBeFound("transferValue.greaterThan=" + DEFAULT_TRANSFER_VALUE);

        // Get all the transferDocumentItemList where transferValue is greater than SMALLER_TRANSFER_VALUE
        defaultTransferDocumentItemShouldBeFound("transferValue.greaterThan=" + SMALLER_TRANSFER_VALUE);
    }

    @Test
    @Transactional
    void getAllTransferDocumentItemsByTransferDocumentIsEqualToSomething() throws Exception {
        TransferDocument transferDocument;
        if (TestUtil.findAll(em, TransferDocument.class).isEmpty()) {
            transferDocumentItemRepository.saveAndFlush(transferDocumentItem);
            transferDocument = TransferDocumentResourceIT.createEntity(em);
        } else {
            transferDocument = TestUtil.findAll(em, TransferDocument.class).get(0);
        }
        em.persist(transferDocument);
        em.flush();
        transferDocumentItem.setTransferDocument(transferDocument);
        transferDocumentItemRepository.saveAndFlush(transferDocumentItem);
        Long transferDocumentId = transferDocument.getId();

        // Get all the transferDocumentItemList where transferDocument equals to transferDocumentId
        defaultTransferDocumentItemShouldBeFound("transferDocumentId.equals=" + transferDocumentId);

        // Get all the transferDocumentItemList where transferDocument equals to (transferDocumentId + 1)
        defaultTransferDocumentItemShouldNotBeFound("transferDocumentId.equals=" + (transferDocumentId + 1));
    }

    @Test
    @Transactional
    void getAllTransferDocumentItemsByResourceIsEqualToSomething() throws Exception {
        Resource resource;
        if (TestUtil.findAll(em, Resource.class).isEmpty()) {
            transferDocumentItemRepository.saveAndFlush(transferDocumentItem);
            resource = ResourceResourceIT.createEntity(em);
        } else {
            resource = TestUtil.findAll(em, Resource.class).get(0);
        }
        em.persist(resource);
        em.flush();
        transferDocumentItem.setResource(resource);
        transferDocumentItemRepository.saveAndFlush(transferDocumentItem);
        Long resourceId = resource.getId();

        // Get all the transferDocumentItemList where resource equals to resourceId
        defaultTransferDocumentItemShouldBeFound("resourceId.equals=" + resourceId);

        // Get all the transferDocumentItemList where resource equals to (resourceId + 1)
        defaultTransferDocumentItemShouldNotBeFound("resourceId.equals=" + (resourceId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTransferDocumentItemShouldBeFound(String filter) throws Exception {
        restTransferDocumentItemMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transferDocumentItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].transferValue").value(hasItem(DEFAULT_TRANSFER_VALUE.doubleValue())));

        // Check, that the count call also returns 1
        restTransferDocumentItemMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTransferDocumentItemShouldNotBeFound(String filter) throws Exception {
        restTransferDocumentItemMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTransferDocumentItemMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
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
        updatedTransferDocumentItem.amount(UPDATED_AMOUNT).price(UPDATED_PRICE).transferValue(UPDATED_TRANSFER_VALUE);
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
        assertThat(testTransferDocumentItem.getTransferValue()).isEqualTo(UPDATED_TRANSFER_VALUE);
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
        assertThat(testTransferDocumentItem.getTransferValue()).isEqualTo(DEFAULT_TRANSFER_VALUE);
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

        partialUpdatedTransferDocumentItem.amount(UPDATED_AMOUNT).price(UPDATED_PRICE).transferValue(UPDATED_TRANSFER_VALUE);

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
        assertThat(testTransferDocumentItem.getTransferValue()).isEqualTo(UPDATED_TRANSFER_VALUE);
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
