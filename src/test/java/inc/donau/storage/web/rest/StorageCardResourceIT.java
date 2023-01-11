package inc.donau.storage.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import inc.donau.storage.IntegrationTest;
import inc.donau.storage.domain.BusinessYear;
import inc.donau.storage.domain.Resource;
import inc.donau.storage.domain.Storage;
import inc.donau.storage.domain.StorageCard;
import inc.donau.storage.domain.StorageCardTraffic;
import inc.donau.storage.repository.StorageCardRepository;
import inc.donau.storage.service.criteria.StorageCardCriteria;
import inc.donau.storage.service.dto.StorageCardDTO;
import inc.donau.storage.service.mapper.StorageCardMapper;
import java.util.List;
import java.util.UUID;
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
 * Integration tests for the {@link StorageCardResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class StorageCardResourceIT {

    private static final Float DEFAULT_STARTING_AMOUNT = 1F;
    private static final Float UPDATED_STARTING_AMOUNT = 2F;
    private static final Float SMALLER_STARTING_AMOUNT = 1F - 1F;

    private static final Float DEFAULT_RECEIVED_AMOUNT = 1F;
    private static final Float UPDATED_RECEIVED_AMOUNT = 2F;
    private static final Float SMALLER_RECEIVED_AMOUNT = 1F - 1F;

    private static final Float DEFAULT_DISPATCHED_AMOUNT = 1F;
    private static final Float UPDATED_DISPATCHED_AMOUNT = 2F;
    private static final Float SMALLER_DISPATCHED_AMOUNT = 1F - 1F;

    private static final Float DEFAULT_TOTAL_AMOUNT = 1F;
    private static final Float UPDATED_TOTAL_AMOUNT = 2F;
    private static final Float SMALLER_TOTAL_AMOUNT = 1F - 1F;

    private static final Float DEFAULT_STARTING_VALUE = 1F;
    private static final Float UPDATED_STARTING_VALUE = 2F;
    private static final Float SMALLER_STARTING_VALUE = 1F - 1F;

    private static final Float DEFAULT_RECEIVED_VALUE = 1F;
    private static final Float UPDATED_RECEIVED_VALUE = 2F;
    private static final Float SMALLER_RECEIVED_VALUE = 1F - 1F;

    private static final Float DEFAULT_DISPATCHED_VALUE = 1F;
    private static final Float UPDATED_DISPATCHED_VALUE = 2F;
    private static final Float SMALLER_DISPATCHED_VALUE = 1F - 1F;

    private static final Float DEFAULT_TOTAL_VALUE = 1F;
    private static final Float UPDATED_TOTAL_VALUE = 2F;
    private static final Float SMALLER_TOTAL_VALUE = 1F - 1F;

    private static final Float DEFAULT_PRICE = 1F;
    private static final Float UPDATED_PRICE = 2F;
    private static final Float SMALLER_PRICE = 1F - 1F;

    private static final String ENTITY_API_URL = "/api/storage-cards";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private StorageCardRepository storageCardRepository;

    @Autowired
    private StorageCardMapper storageCardMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStorageCardMockMvc;

    private StorageCard storageCard;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StorageCard createEntity(EntityManager em) {
        StorageCard storageCard = new StorageCard()
            .startingAmount(DEFAULT_STARTING_AMOUNT)
            .receivedAmount(DEFAULT_RECEIVED_AMOUNT)
            .dispatchedAmount(DEFAULT_DISPATCHED_AMOUNT)
            .totalAmount(DEFAULT_TOTAL_AMOUNT)
            .startingValue(DEFAULT_STARTING_VALUE)
            .receivedValue(DEFAULT_RECEIVED_VALUE)
            .dispatchedValue(DEFAULT_DISPATCHED_VALUE)
            .totalValue(DEFAULT_TOTAL_VALUE)
            .price(DEFAULT_PRICE);
        // Add required entity
        BusinessYear businessYear;
        if (TestUtil.findAll(em, BusinessYear.class).isEmpty()) {
            businessYear = BusinessYearResourceIT.createEntity(em);
            em.persist(businessYear);
            em.flush();
        } else {
            businessYear = TestUtil.findAll(em, BusinessYear.class).get(0);
        }
        storageCard.setBusinessYear(businessYear);
        // Add required entity
        Resource resource;
        if (TestUtil.findAll(em, Resource.class).isEmpty()) {
            resource = ResourceResourceIT.createEntity(em);
            em.persist(resource);
            em.flush();
        } else {
            resource = TestUtil.findAll(em, Resource.class).get(0);
        }
        storageCard.setResource(resource);
        // Add required entity
        Storage storage;
        if (TestUtil.findAll(em, Storage.class).isEmpty()) {
            storage = StorageResourceIT.createEntity(em);
            em.persist(storage);
            em.flush();
        } else {
            storage = TestUtil.findAll(em, Storage.class).get(0);
        }
        storageCard.setStorage(storage);
        return storageCard;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StorageCard createUpdatedEntity(EntityManager em) {
        StorageCard storageCard = new StorageCard()
            .startingAmount(UPDATED_STARTING_AMOUNT)
            .receivedAmount(UPDATED_RECEIVED_AMOUNT)
            .dispatchedAmount(UPDATED_DISPATCHED_AMOUNT)
            .totalAmount(UPDATED_TOTAL_AMOUNT)
            .startingValue(UPDATED_STARTING_VALUE)
            .receivedValue(UPDATED_RECEIVED_VALUE)
            .dispatchedValue(UPDATED_DISPATCHED_VALUE)
            .totalValue(UPDATED_TOTAL_VALUE)
            .price(UPDATED_PRICE);
        // Add required entity
        BusinessYear businessYear;
        if (TestUtil.findAll(em, BusinessYear.class).isEmpty()) {
            businessYear = BusinessYearResourceIT.createUpdatedEntity(em);
            em.persist(businessYear);
            em.flush();
        } else {
            businessYear = TestUtil.findAll(em, BusinessYear.class).get(0);
        }
        storageCard.setBusinessYear(businessYear);
        // Add required entity
        Resource resource;
        if (TestUtil.findAll(em, Resource.class).isEmpty()) {
            resource = ResourceResourceIT.createUpdatedEntity(em);
            em.persist(resource);
            em.flush();
        } else {
            resource = TestUtil.findAll(em, Resource.class).get(0);
        }
        storageCard.setResource(resource);
        // Add required entity
        Storage storage;
        if (TestUtil.findAll(em, Storage.class).isEmpty()) {
            storage = StorageResourceIT.createUpdatedEntity(em);
            em.persist(storage);
            em.flush();
        } else {
            storage = TestUtil.findAll(em, Storage.class).get(0);
        }
        storageCard.setStorage(storage);
        return storageCard;
    }

    @BeforeEach
    public void initTest() {
        storageCard = createEntity(em);
    }

    @Test
    @Transactional
    void createStorageCard() throws Exception {
        int databaseSizeBeforeCreate = storageCardRepository.findAll().size();
        // Create the StorageCard
        StorageCardDTO storageCardDTO = storageCardMapper.toDto(storageCard);
        restStorageCardMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(storageCardDTO))
            )
            .andExpect(status().isCreated());

        // Validate the StorageCard in the database
        List<StorageCard> storageCardList = storageCardRepository.findAll();
        assertThat(storageCardList).hasSize(databaseSizeBeforeCreate + 1);
        StorageCard testStorageCard = storageCardList.get(storageCardList.size() - 1);
        assertThat(testStorageCard.getStartingAmount()).isEqualTo(DEFAULT_STARTING_AMOUNT);
        assertThat(testStorageCard.getReceivedAmount()).isEqualTo(DEFAULT_RECEIVED_AMOUNT);
        assertThat(testStorageCard.getDispatchedAmount()).isEqualTo(DEFAULT_DISPATCHED_AMOUNT);
        assertThat(testStorageCard.getTotalAmount()).isEqualTo(DEFAULT_TOTAL_AMOUNT);
        assertThat(testStorageCard.getStartingValue()).isEqualTo(DEFAULT_STARTING_VALUE);
        assertThat(testStorageCard.getReceivedValue()).isEqualTo(DEFAULT_RECEIVED_VALUE);
        assertThat(testStorageCard.getDispatchedValue()).isEqualTo(DEFAULT_DISPATCHED_VALUE);
        assertThat(testStorageCard.getTotalValue()).isEqualTo(DEFAULT_TOTAL_VALUE);
        assertThat(testStorageCard.getPrice()).isEqualTo(DEFAULT_PRICE);
    }

    @Test
    @Transactional
    void createStorageCardWithExistingId() throws Exception {
        // Create the StorageCard with an existing ID
        storageCard.setId("existing_id");
        StorageCardDTO storageCardDTO = storageCardMapper.toDto(storageCard);

        int databaseSizeBeforeCreate = storageCardRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStorageCardMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(storageCardDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StorageCard in the database
        List<StorageCard> storageCardList = storageCardRepository.findAll();
        assertThat(storageCardList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkStartingAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = storageCardRepository.findAll().size();
        // set the field null
        storageCard.setStartingAmount(null);

        // Create the StorageCard, which fails.
        StorageCardDTO storageCardDTO = storageCardMapper.toDto(storageCard);

        restStorageCardMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(storageCardDTO))
            )
            .andExpect(status().isBadRequest());

        List<StorageCard> storageCardList = storageCardRepository.findAll();
        assertThat(storageCardList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkReceivedAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = storageCardRepository.findAll().size();
        // set the field null
        storageCard.setReceivedAmount(null);

        // Create the StorageCard, which fails.
        StorageCardDTO storageCardDTO = storageCardMapper.toDto(storageCard);

        restStorageCardMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(storageCardDTO))
            )
            .andExpect(status().isBadRequest());

        List<StorageCard> storageCardList = storageCardRepository.findAll();
        assertThat(storageCardList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDispatchedAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = storageCardRepository.findAll().size();
        // set the field null
        storageCard.setDispatchedAmount(null);

        // Create the StorageCard, which fails.
        StorageCardDTO storageCardDTO = storageCardMapper.toDto(storageCard);

        restStorageCardMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(storageCardDTO))
            )
            .andExpect(status().isBadRequest());

        List<StorageCard> storageCardList = storageCardRepository.findAll();
        assertThat(storageCardList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStartingValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = storageCardRepository.findAll().size();
        // set the field null
        storageCard.setStartingValue(null);

        // Create the StorageCard, which fails.
        StorageCardDTO storageCardDTO = storageCardMapper.toDto(storageCard);

        restStorageCardMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(storageCardDTO))
            )
            .andExpect(status().isBadRequest());

        List<StorageCard> storageCardList = storageCardRepository.findAll();
        assertThat(storageCardList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkReceivedValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = storageCardRepository.findAll().size();
        // set the field null
        storageCard.setReceivedValue(null);

        // Create the StorageCard, which fails.
        StorageCardDTO storageCardDTO = storageCardMapper.toDto(storageCard);

        restStorageCardMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(storageCardDTO))
            )
            .andExpect(status().isBadRequest());

        List<StorageCard> storageCardList = storageCardRepository.findAll();
        assertThat(storageCardList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDispatchedValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = storageCardRepository.findAll().size();
        // set the field null
        storageCard.setDispatchedValue(null);

        // Create the StorageCard, which fails.
        StorageCardDTO storageCardDTO = storageCardMapper.toDto(storageCard);

        restStorageCardMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(storageCardDTO))
            )
            .andExpect(status().isBadRequest());

        List<StorageCard> storageCardList = storageCardRepository.findAll();
        assertThat(storageCardList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = storageCardRepository.findAll().size();
        // set the field null
        storageCard.setPrice(null);

        // Create the StorageCard, which fails.
        StorageCardDTO storageCardDTO = storageCardMapper.toDto(storageCard);

        restStorageCardMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(storageCardDTO))
            )
            .andExpect(status().isBadRequest());

        List<StorageCard> storageCardList = storageCardRepository.findAll();
        assertThat(storageCardList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllStorageCards() throws Exception {
        // Initialize the database
        storageCard.setId(UUID.randomUUID().toString());
        storageCardRepository.saveAndFlush(storageCard);

        // Get all the storageCardList
        restStorageCardMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(storageCard.getId())))
            .andExpect(jsonPath("$.[*].startingAmount").value(hasItem(DEFAULT_STARTING_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].receivedAmount").value(hasItem(DEFAULT_RECEIVED_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].dispatchedAmount").value(hasItem(DEFAULT_DISPATCHED_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].totalAmount").value(hasItem(DEFAULT_TOTAL_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].startingValue").value(hasItem(DEFAULT_STARTING_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].receivedValue").value(hasItem(DEFAULT_RECEIVED_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].dispatchedValue").value(hasItem(DEFAULT_DISPATCHED_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].totalValue").value(hasItem(DEFAULT_TOTAL_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())));
    }

    @Test
    @Transactional
    void getStorageCard() throws Exception {
        // Initialize the database
        storageCard.setId(UUID.randomUUID().toString());
        storageCardRepository.saveAndFlush(storageCard);

        // Get the storageCard
        restStorageCardMockMvc
            .perform(get(ENTITY_API_URL_ID, storageCard.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(storageCard.getId()))
            .andExpect(jsonPath("$.startingAmount").value(DEFAULT_STARTING_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.receivedAmount").value(DEFAULT_RECEIVED_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.dispatchedAmount").value(DEFAULT_DISPATCHED_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.totalAmount").value(DEFAULT_TOTAL_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.startingValue").value(DEFAULT_STARTING_VALUE.doubleValue()))
            .andExpect(jsonPath("$.receivedValue").value(DEFAULT_RECEIVED_VALUE.doubleValue()))
            .andExpect(jsonPath("$.dispatchedValue").value(DEFAULT_DISPATCHED_VALUE.doubleValue()))
            .andExpect(jsonPath("$.totalValue").value(DEFAULT_TOTAL_VALUE.doubleValue()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()));
    }

    @Test
    @Transactional
    void getStorageCardsByIdFiltering() throws Exception {
        // Initialize the database
        storageCardRepository.saveAndFlush(storageCard);

        String id = storageCard.getId();

        defaultStorageCardShouldBeFound("id.equals=" + id);
        defaultStorageCardShouldNotBeFound("id.notEquals=" + id);
    }

    @Test
    @Transactional
    void getAllStorageCardsByStartingAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        storageCardRepository.saveAndFlush(storageCard);

        // Get all the storageCardList where startingAmount equals to DEFAULT_STARTING_AMOUNT
        defaultStorageCardShouldBeFound("startingAmount.equals=" + DEFAULT_STARTING_AMOUNT);

        // Get all the storageCardList where startingAmount equals to UPDATED_STARTING_AMOUNT
        defaultStorageCardShouldNotBeFound("startingAmount.equals=" + UPDATED_STARTING_AMOUNT);
    }

    @Test
    @Transactional
    void getAllStorageCardsByStartingAmountIsInShouldWork() throws Exception {
        // Initialize the database
        storageCardRepository.saveAndFlush(storageCard);

        // Get all the storageCardList where startingAmount in DEFAULT_STARTING_AMOUNT or UPDATED_STARTING_AMOUNT
        defaultStorageCardShouldBeFound("startingAmount.in=" + DEFAULT_STARTING_AMOUNT + "," + UPDATED_STARTING_AMOUNT);

        // Get all the storageCardList where startingAmount equals to UPDATED_STARTING_AMOUNT
        defaultStorageCardShouldNotBeFound("startingAmount.in=" + UPDATED_STARTING_AMOUNT);
    }

    @Test
    @Transactional
    void getAllStorageCardsByStartingAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        storageCardRepository.saveAndFlush(storageCard);

        // Get all the storageCardList where startingAmount is not null
        defaultStorageCardShouldBeFound("startingAmount.specified=true");

        // Get all the storageCardList where startingAmount is null
        defaultStorageCardShouldNotBeFound("startingAmount.specified=false");
    }

    @Test
    @Transactional
    void getAllStorageCardsByStartingAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        storageCardRepository.saveAndFlush(storageCard);

        // Get all the storageCardList where startingAmount is greater than or equal to DEFAULT_STARTING_AMOUNT
        defaultStorageCardShouldBeFound("startingAmount.greaterThanOrEqual=" + DEFAULT_STARTING_AMOUNT);

        // Get all the storageCardList where startingAmount is greater than or equal to UPDATED_STARTING_AMOUNT
        defaultStorageCardShouldNotBeFound("startingAmount.greaterThanOrEqual=" + UPDATED_STARTING_AMOUNT);
    }

    @Test
    @Transactional
    void getAllStorageCardsByStartingAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        storageCardRepository.saveAndFlush(storageCard);

        // Get all the storageCardList where startingAmount is less than or equal to DEFAULT_STARTING_AMOUNT
        defaultStorageCardShouldBeFound("startingAmount.lessThanOrEqual=" + DEFAULT_STARTING_AMOUNT);

        // Get all the storageCardList where startingAmount is less than or equal to SMALLER_STARTING_AMOUNT
        defaultStorageCardShouldNotBeFound("startingAmount.lessThanOrEqual=" + SMALLER_STARTING_AMOUNT);
    }

    @Test
    @Transactional
    void getAllStorageCardsByStartingAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        storageCardRepository.saveAndFlush(storageCard);

        // Get all the storageCardList where startingAmount is less than DEFAULT_STARTING_AMOUNT
        defaultStorageCardShouldNotBeFound("startingAmount.lessThan=" + DEFAULT_STARTING_AMOUNT);

        // Get all the storageCardList where startingAmount is less than UPDATED_STARTING_AMOUNT
        defaultStorageCardShouldBeFound("startingAmount.lessThan=" + UPDATED_STARTING_AMOUNT);
    }

    @Test
    @Transactional
    void getAllStorageCardsByStartingAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        storageCardRepository.saveAndFlush(storageCard);

        // Get all the storageCardList where startingAmount is greater than DEFAULT_STARTING_AMOUNT
        defaultStorageCardShouldNotBeFound("startingAmount.greaterThan=" + DEFAULT_STARTING_AMOUNT);

        // Get all the storageCardList where startingAmount is greater than SMALLER_STARTING_AMOUNT
        defaultStorageCardShouldBeFound("startingAmount.greaterThan=" + SMALLER_STARTING_AMOUNT);
    }

    @Test
    @Transactional
    void getAllStorageCardsByReceivedAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        storageCardRepository.saveAndFlush(storageCard);

        // Get all the storageCardList where receivedAmount equals to DEFAULT_RECEIVED_AMOUNT
        defaultStorageCardShouldBeFound("receivedAmount.equals=" + DEFAULT_RECEIVED_AMOUNT);

        // Get all the storageCardList where receivedAmount equals to UPDATED_RECEIVED_AMOUNT
        defaultStorageCardShouldNotBeFound("receivedAmount.equals=" + UPDATED_RECEIVED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllStorageCardsByReceivedAmountIsInShouldWork() throws Exception {
        // Initialize the database
        storageCardRepository.saveAndFlush(storageCard);

        // Get all the storageCardList where receivedAmount in DEFAULT_RECEIVED_AMOUNT or UPDATED_RECEIVED_AMOUNT
        defaultStorageCardShouldBeFound("receivedAmount.in=" + DEFAULT_RECEIVED_AMOUNT + "," + UPDATED_RECEIVED_AMOUNT);

        // Get all the storageCardList where receivedAmount equals to UPDATED_RECEIVED_AMOUNT
        defaultStorageCardShouldNotBeFound("receivedAmount.in=" + UPDATED_RECEIVED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllStorageCardsByReceivedAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        storageCardRepository.saveAndFlush(storageCard);

        // Get all the storageCardList where receivedAmount is not null
        defaultStorageCardShouldBeFound("receivedAmount.specified=true");

        // Get all the storageCardList where receivedAmount is null
        defaultStorageCardShouldNotBeFound("receivedAmount.specified=false");
    }

    @Test
    @Transactional
    void getAllStorageCardsByReceivedAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        storageCardRepository.saveAndFlush(storageCard);

        // Get all the storageCardList where receivedAmount is greater than or equal to DEFAULT_RECEIVED_AMOUNT
        defaultStorageCardShouldBeFound("receivedAmount.greaterThanOrEqual=" + DEFAULT_RECEIVED_AMOUNT);

        // Get all the storageCardList where receivedAmount is greater than or equal to UPDATED_RECEIVED_AMOUNT
        defaultStorageCardShouldNotBeFound("receivedAmount.greaterThanOrEqual=" + UPDATED_RECEIVED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllStorageCardsByReceivedAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        storageCardRepository.saveAndFlush(storageCard);

        // Get all the storageCardList where receivedAmount is less than or equal to DEFAULT_RECEIVED_AMOUNT
        defaultStorageCardShouldBeFound("receivedAmount.lessThanOrEqual=" + DEFAULT_RECEIVED_AMOUNT);

        // Get all the storageCardList where receivedAmount is less than or equal to SMALLER_RECEIVED_AMOUNT
        defaultStorageCardShouldNotBeFound("receivedAmount.lessThanOrEqual=" + SMALLER_RECEIVED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllStorageCardsByReceivedAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        storageCardRepository.saveAndFlush(storageCard);

        // Get all the storageCardList where receivedAmount is less than DEFAULT_RECEIVED_AMOUNT
        defaultStorageCardShouldNotBeFound("receivedAmount.lessThan=" + DEFAULT_RECEIVED_AMOUNT);

        // Get all the storageCardList where receivedAmount is less than UPDATED_RECEIVED_AMOUNT
        defaultStorageCardShouldBeFound("receivedAmount.lessThan=" + UPDATED_RECEIVED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllStorageCardsByReceivedAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        storageCardRepository.saveAndFlush(storageCard);

        // Get all the storageCardList where receivedAmount is greater than DEFAULT_RECEIVED_AMOUNT
        defaultStorageCardShouldNotBeFound("receivedAmount.greaterThan=" + DEFAULT_RECEIVED_AMOUNT);

        // Get all the storageCardList where receivedAmount is greater than SMALLER_RECEIVED_AMOUNT
        defaultStorageCardShouldBeFound("receivedAmount.greaterThan=" + SMALLER_RECEIVED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllStorageCardsByDispatchedAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        storageCardRepository.saveAndFlush(storageCard);

        // Get all the storageCardList where dispatchedAmount equals to DEFAULT_DISPATCHED_AMOUNT
        defaultStorageCardShouldBeFound("dispatchedAmount.equals=" + DEFAULT_DISPATCHED_AMOUNT);

        // Get all the storageCardList where dispatchedAmount equals to UPDATED_DISPATCHED_AMOUNT
        defaultStorageCardShouldNotBeFound("dispatchedAmount.equals=" + UPDATED_DISPATCHED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllStorageCardsByDispatchedAmountIsInShouldWork() throws Exception {
        // Initialize the database
        storageCardRepository.saveAndFlush(storageCard);

        // Get all the storageCardList where dispatchedAmount in DEFAULT_DISPATCHED_AMOUNT or UPDATED_DISPATCHED_AMOUNT
        defaultStorageCardShouldBeFound("dispatchedAmount.in=" + DEFAULT_DISPATCHED_AMOUNT + "," + UPDATED_DISPATCHED_AMOUNT);

        // Get all the storageCardList where dispatchedAmount equals to UPDATED_DISPATCHED_AMOUNT
        defaultStorageCardShouldNotBeFound("dispatchedAmount.in=" + UPDATED_DISPATCHED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllStorageCardsByDispatchedAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        storageCardRepository.saveAndFlush(storageCard);

        // Get all the storageCardList where dispatchedAmount is not null
        defaultStorageCardShouldBeFound("dispatchedAmount.specified=true");

        // Get all the storageCardList where dispatchedAmount is null
        defaultStorageCardShouldNotBeFound("dispatchedAmount.specified=false");
    }

    @Test
    @Transactional
    void getAllStorageCardsByDispatchedAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        storageCardRepository.saveAndFlush(storageCard);

        // Get all the storageCardList where dispatchedAmount is greater than or equal to DEFAULT_DISPATCHED_AMOUNT
        defaultStorageCardShouldBeFound("dispatchedAmount.greaterThanOrEqual=" + DEFAULT_DISPATCHED_AMOUNT);

        // Get all the storageCardList where dispatchedAmount is greater than or equal to UPDATED_DISPATCHED_AMOUNT
        defaultStorageCardShouldNotBeFound("dispatchedAmount.greaterThanOrEqual=" + UPDATED_DISPATCHED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllStorageCardsByDispatchedAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        storageCardRepository.saveAndFlush(storageCard);

        // Get all the storageCardList where dispatchedAmount is less than or equal to DEFAULT_DISPATCHED_AMOUNT
        defaultStorageCardShouldBeFound("dispatchedAmount.lessThanOrEqual=" + DEFAULT_DISPATCHED_AMOUNT);

        // Get all the storageCardList where dispatchedAmount is less than or equal to SMALLER_DISPATCHED_AMOUNT
        defaultStorageCardShouldNotBeFound("dispatchedAmount.lessThanOrEqual=" + SMALLER_DISPATCHED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllStorageCardsByDispatchedAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        storageCardRepository.saveAndFlush(storageCard);

        // Get all the storageCardList where dispatchedAmount is less than DEFAULT_DISPATCHED_AMOUNT
        defaultStorageCardShouldNotBeFound("dispatchedAmount.lessThan=" + DEFAULT_DISPATCHED_AMOUNT);

        // Get all the storageCardList where dispatchedAmount is less than UPDATED_DISPATCHED_AMOUNT
        defaultStorageCardShouldBeFound("dispatchedAmount.lessThan=" + UPDATED_DISPATCHED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllStorageCardsByDispatchedAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        storageCardRepository.saveAndFlush(storageCard);

        // Get all the storageCardList where dispatchedAmount is greater than DEFAULT_DISPATCHED_AMOUNT
        defaultStorageCardShouldNotBeFound("dispatchedAmount.greaterThan=" + DEFAULT_DISPATCHED_AMOUNT);

        // Get all the storageCardList where dispatchedAmount is greater than SMALLER_DISPATCHED_AMOUNT
        defaultStorageCardShouldBeFound("dispatchedAmount.greaterThan=" + SMALLER_DISPATCHED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllStorageCardsByTotalAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        storageCardRepository.saveAndFlush(storageCard);

        // Get all the storageCardList where totalAmount equals to DEFAULT_TOTAL_AMOUNT
        defaultStorageCardShouldBeFound("totalAmount.equals=" + DEFAULT_TOTAL_AMOUNT);

        // Get all the storageCardList where totalAmount equals to UPDATED_TOTAL_AMOUNT
        defaultStorageCardShouldNotBeFound("totalAmount.equals=" + UPDATED_TOTAL_AMOUNT);
    }

    @Test
    @Transactional
    void getAllStorageCardsByTotalAmountIsInShouldWork() throws Exception {
        // Initialize the database
        storageCardRepository.saveAndFlush(storageCard);

        // Get all the storageCardList where totalAmount in DEFAULT_TOTAL_AMOUNT or UPDATED_TOTAL_AMOUNT
        defaultStorageCardShouldBeFound("totalAmount.in=" + DEFAULT_TOTAL_AMOUNT + "," + UPDATED_TOTAL_AMOUNT);

        // Get all the storageCardList where totalAmount equals to UPDATED_TOTAL_AMOUNT
        defaultStorageCardShouldNotBeFound("totalAmount.in=" + UPDATED_TOTAL_AMOUNT);
    }

    @Test
    @Transactional
    void getAllStorageCardsByTotalAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        storageCardRepository.saveAndFlush(storageCard);

        // Get all the storageCardList where totalAmount is not null
        defaultStorageCardShouldBeFound("totalAmount.specified=true");

        // Get all the storageCardList where totalAmount is null
        defaultStorageCardShouldNotBeFound("totalAmount.specified=false");
    }

    @Test
    @Transactional
    void getAllStorageCardsByTotalAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        storageCardRepository.saveAndFlush(storageCard);

        // Get all the storageCardList where totalAmount is greater than or equal to DEFAULT_TOTAL_AMOUNT
        defaultStorageCardShouldBeFound("totalAmount.greaterThanOrEqual=" + DEFAULT_TOTAL_AMOUNT);

        // Get all the storageCardList where totalAmount is greater than or equal to UPDATED_TOTAL_AMOUNT
        defaultStorageCardShouldNotBeFound("totalAmount.greaterThanOrEqual=" + UPDATED_TOTAL_AMOUNT);
    }

    @Test
    @Transactional
    void getAllStorageCardsByTotalAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        storageCardRepository.saveAndFlush(storageCard);

        // Get all the storageCardList where totalAmount is less than or equal to DEFAULT_TOTAL_AMOUNT
        defaultStorageCardShouldBeFound("totalAmount.lessThanOrEqual=" + DEFAULT_TOTAL_AMOUNT);

        // Get all the storageCardList where totalAmount is less than or equal to SMALLER_TOTAL_AMOUNT
        defaultStorageCardShouldNotBeFound("totalAmount.lessThanOrEqual=" + SMALLER_TOTAL_AMOUNT);
    }

    @Test
    @Transactional
    void getAllStorageCardsByTotalAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        storageCardRepository.saveAndFlush(storageCard);

        // Get all the storageCardList where totalAmount is less than DEFAULT_TOTAL_AMOUNT
        defaultStorageCardShouldNotBeFound("totalAmount.lessThan=" + DEFAULT_TOTAL_AMOUNT);

        // Get all the storageCardList where totalAmount is less than UPDATED_TOTAL_AMOUNT
        defaultStorageCardShouldBeFound("totalAmount.lessThan=" + UPDATED_TOTAL_AMOUNT);
    }

    @Test
    @Transactional
    void getAllStorageCardsByTotalAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        storageCardRepository.saveAndFlush(storageCard);

        // Get all the storageCardList where totalAmount is greater than DEFAULT_TOTAL_AMOUNT
        defaultStorageCardShouldNotBeFound("totalAmount.greaterThan=" + DEFAULT_TOTAL_AMOUNT);

        // Get all the storageCardList where totalAmount is greater than SMALLER_TOTAL_AMOUNT
        defaultStorageCardShouldBeFound("totalAmount.greaterThan=" + SMALLER_TOTAL_AMOUNT);
    }

    @Test
    @Transactional
    void getAllStorageCardsByStartingValueIsEqualToSomething() throws Exception {
        // Initialize the database
        storageCardRepository.saveAndFlush(storageCard);

        // Get all the storageCardList where startingValue equals to DEFAULT_STARTING_VALUE
        defaultStorageCardShouldBeFound("startingValue.equals=" + DEFAULT_STARTING_VALUE);

        // Get all the storageCardList where startingValue equals to UPDATED_STARTING_VALUE
        defaultStorageCardShouldNotBeFound("startingValue.equals=" + UPDATED_STARTING_VALUE);
    }

    @Test
    @Transactional
    void getAllStorageCardsByStartingValueIsInShouldWork() throws Exception {
        // Initialize the database
        storageCardRepository.saveAndFlush(storageCard);

        // Get all the storageCardList where startingValue in DEFAULT_STARTING_VALUE or UPDATED_STARTING_VALUE
        defaultStorageCardShouldBeFound("startingValue.in=" + DEFAULT_STARTING_VALUE + "," + UPDATED_STARTING_VALUE);

        // Get all the storageCardList where startingValue equals to UPDATED_STARTING_VALUE
        defaultStorageCardShouldNotBeFound("startingValue.in=" + UPDATED_STARTING_VALUE);
    }

    @Test
    @Transactional
    void getAllStorageCardsByStartingValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        storageCardRepository.saveAndFlush(storageCard);

        // Get all the storageCardList where startingValue is not null
        defaultStorageCardShouldBeFound("startingValue.specified=true");

        // Get all the storageCardList where startingValue is null
        defaultStorageCardShouldNotBeFound("startingValue.specified=false");
    }

    @Test
    @Transactional
    void getAllStorageCardsByStartingValueIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        storageCardRepository.saveAndFlush(storageCard);

        // Get all the storageCardList where startingValue is greater than or equal to DEFAULT_STARTING_VALUE
        defaultStorageCardShouldBeFound("startingValue.greaterThanOrEqual=" + DEFAULT_STARTING_VALUE);

        // Get all the storageCardList where startingValue is greater than or equal to UPDATED_STARTING_VALUE
        defaultStorageCardShouldNotBeFound("startingValue.greaterThanOrEqual=" + UPDATED_STARTING_VALUE);
    }

    @Test
    @Transactional
    void getAllStorageCardsByStartingValueIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        storageCardRepository.saveAndFlush(storageCard);

        // Get all the storageCardList where startingValue is less than or equal to DEFAULT_STARTING_VALUE
        defaultStorageCardShouldBeFound("startingValue.lessThanOrEqual=" + DEFAULT_STARTING_VALUE);

        // Get all the storageCardList where startingValue is less than or equal to SMALLER_STARTING_VALUE
        defaultStorageCardShouldNotBeFound("startingValue.lessThanOrEqual=" + SMALLER_STARTING_VALUE);
    }

    @Test
    @Transactional
    void getAllStorageCardsByStartingValueIsLessThanSomething() throws Exception {
        // Initialize the database
        storageCardRepository.saveAndFlush(storageCard);

        // Get all the storageCardList where startingValue is less than DEFAULT_STARTING_VALUE
        defaultStorageCardShouldNotBeFound("startingValue.lessThan=" + DEFAULT_STARTING_VALUE);

        // Get all the storageCardList where startingValue is less than UPDATED_STARTING_VALUE
        defaultStorageCardShouldBeFound("startingValue.lessThan=" + UPDATED_STARTING_VALUE);
    }

    @Test
    @Transactional
    void getAllStorageCardsByStartingValueIsGreaterThanSomething() throws Exception {
        // Initialize the database
        storageCardRepository.saveAndFlush(storageCard);

        // Get all the storageCardList where startingValue is greater than DEFAULT_STARTING_VALUE
        defaultStorageCardShouldNotBeFound("startingValue.greaterThan=" + DEFAULT_STARTING_VALUE);

        // Get all the storageCardList where startingValue is greater than SMALLER_STARTING_VALUE
        defaultStorageCardShouldBeFound("startingValue.greaterThan=" + SMALLER_STARTING_VALUE);
    }

    @Test
    @Transactional
    void getAllStorageCardsByReceivedValueIsEqualToSomething() throws Exception {
        // Initialize the database
        storageCardRepository.saveAndFlush(storageCard);

        // Get all the storageCardList where receivedValue equals to DEFAULT_RECEIVED_VALUE
        defaultStorageCardShouldBeFound("receivedValue.equals=" + DEFAULT_RECEIVED_VALUE);

        // Get all the storageCardList where receivedValue equals to UPDATED_RECEIVED_VALUE
        defaultStorageCardShouldNotBeFound("receivedValue.equals=" + UPDATED_RECEIVED_VALUE);
    }

    @Test
    @Transactional
    void getAllStorageCardsByReceivedValueIsInShouldWork() throws Exception {
        // Initialize the database
        storageCardRepository.saveAndFlush(storageCard);

        // Get all the storageCardList where receivedValue in DEFAULT_RECEIVED_VALUE or UPDATED_RECEIVED_VALUE
        defaultStorageCardShouldBeFound("receivedValue.in=" + DEFAULT_RECEIVED_VALUE + "," + UPDATED_RECEIVED_VALUE);

        // Get all the storageCardList where receivedValue equals to UPDATED_RECEIVED_VALUE
        defaultStorageCardShouldNotBeFound("receivedValue.in=" + UPDATED_RECEIVED_VALUE);
    }

    @Test
    @Transactional
    void getAllStorageCardsByReceivedValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        storageCardRepository.saveAndFlush(storageCard);

        // Get all the storageCardList where receivedValue is not null
        defaultStorageCardShouldBeFound("receivedValue.specified=true");

        // Get all the storageCardList where receivedValue is null
        defaultStorageCardShouldNotBeFound("receivedValue.specified=false");
    }

    @Test
    @Transactional
    void getAllStorageCardsByReceivedValueIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        storageCardRepository.saveAndFlush(storageCard);

        // Get all the storageCardList where receivedValue is greater than or equal to DEFAULT_RECEIVED_VALUE
        defaultStorageCardShouldBeFound("receivedValue.greaterThanOrEqual=" + DEFAULT_RECEIVED_VALUE);

        // Get all the storageCardList where receivedValue is greater than or equal to UPDATED_RECEIVED_VALUE
        defaultStorageCardShouldNotBeFound("receivedValue.greaterThanOrEqual=" + UPDATED_RECEIVED_VALUE);
    }

    @Test
    @Transactional
    void getAllStorageCardsByReceivedValueIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        storageCardRepository.saveAndFlush(storageCard);

        // Get all the storageCardList where receivedValue is less than or equal to DEFAULT_RECEIVED_VALUE
        defaultStorageCardShouldBeFound("receivedValue.lessThanOrEqual=" + DEFAULT_RECEIVED_VALUE);

        // Get all the storageCardList where receivedValue is less than or equal to SMALLER_RECEIVED_VALUE
        defaultStorageCardShouldNotBeFound("receivedValue.lessThanOrEqual=" + SMALLER_RECEIVED_VALUE);
    }

    @Test
    @Transactional
    void getAllStorageCardsByReceivedValueIsLessThanSomething() throws Exception {
        // Initialize the database
        storageCardRepository.saveAndFlush(storageCard);

        // Get all the storageCardList where receivedValue is less than DEFAULT_RECEIVED_VALUE
        defaultStorageCardShouldNotBeFound("receivedValue.lessThan=" + DEFAULT_RECEIVED_VALUE);

        // Get all the storageCardList where receivedValue is less than UPDATED_RECEIVED_VALUE
        defaultStorageCardShouldBeFound("receivedValue.lessThan=" + UPDATED_RECEIVED_VALUE);
    }

    @Test
    @Transactional
    void getAllStorageCardsByReceivedValueIsGreaterThanSomething() throws Exception {
        // Initialize the database
        storageCardRepository.saveAndFlush(storageCard);

        // Get all the storageCardList where receivedValue is greater than DEFAULT_RECEIVED_VALUE
        defaultStorageCardShouldNotBeFound("receivedValue.greaterThan=" + DEFAULT_RECEIVED_VALUE);

        // Get all the storageCardList where receivedValue is greater than SMALLER_RECEIVED_VALUE
        defaultStorageCardShouldBeFound("receivedValue.greaterThan=" + SMALLER_RECEIVED_VALUE);
    }

    @Test
    @Transactional
    void getAllStorageCardsByDispatchedValueIsEqualToSomething() throws Exception {
        // Initialize the database
        storageCardRepository.saveAndFlush(storageCard);

        // Get all the storageCardList where dispatchedValue equals to DEFAULT_DISPATCHED_VALUE
        defaultStorageCardShouldBeFound("dispatchedValue.equals=" + DEFAULT_DISPATCHED_VALUE);

        // Get all the storageCardList where dispatchedValue equals to UPDATED_DISPATCHED_VALUE
        defaultStorageCardShouldNotBeFound("dispatchedValue.equals=" + UPDATED_DISPATCHED_VALUE);
    }

    @Test
    @Transactional
    void getAllStorageCardsByDispatchedValueIsInShouldWork() throws Exception {
        // Initialize the database
        storageCardRepository.saveAndFlush(storageCard);

        // Get all the storageCardList where dispatchedValue in DEFAULT_DISPATCHED_VALUE or UPDATED_DISPATCHED_VALUE
        defaultStorageCardShouldBeFound("dispatchedValue.in=" + DEFAULT_DISPATCHED_VALUE + "," + UPDATED_DISPATCHED_VALUE);

        // Get all the storageCardList where dispatchedValue equals to UPDATED_DISPATCHED_VALUE
        defaultStorageCardShouldNotBeFound("dispatchedValue.in=" + UPDATED_DISPATCHED_VALUE);
    }

    @Test
    @Transactional
    void getAllStorageCardsByDispatchedValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        storageCardRepository.saveAndFlush(storageCard);

        // Get all the storageCardList where dispatchedValue is not null
        defaultStorageCardShouldBeFound("dispatchedValue.specified=true");

        // Get all the storageCardList where dispatchedValue is null
        defaultStorageCardShouldNotBeFound("dispatchedValue.specified=false");
    }

    @Test
    @Transactional
    void getAllStorageCardsByDispatchedValueIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        storageCardRepository.saveAndFlush(storageCard);

        // Get all the storageCardList where dispatchedValue is greater than or equal to DEFAULT_DISPATCHED_VALUE
        defaultStorageCardShouldBeFound("dispatchedValue.greaterThanOrEqual=" + DEFAULT_DISPATCHED_VALUE);

        // Get all the storageCardList where dispatchedValue is greater than or equal to UPDATED_DISPATCHED_VALUE
        defaultStorageCardShouldNotBeFound("dispatchedValue.greaterThanOrEqual=" + UPDATED_DISPATCHED_VALUE);
    }

    @Test
    @Transactional
    void getAllStorageCardsByDispatchedValueIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        storageCardRepository.saveAndFlush(storageCard);

        // Get all the storageCardList where dispatchedValue is less than or equal to DEFAULT_DISPATCHED_VALUE
        defaultStorageCardShouldBeFound("dispatchedValue.lessThanOrEqual=" + DEFAULT_DISPATCHED_VALUE);

        // Get all the storageCardList where dispatchedValue is less than or equal to SMALLER_DISPATCHED_VALUE
        defaultStorageCardShouldNotBeFound("dispatchedValue.lessThanOrEqual=" + SMALLER_DISPATCHED_VALUE);
    }

    @Test
    @Transactional
    void getAllStorageCardsByDispatchedValueIsLessThanSomething() throws Exception {
        // Initialize the database
        storageCardRepository.saveAndFlush(storageCard);

        // Get all the storageCardList where dispatchedValue is less than DEFAULT_DISPATCHED_VALUE
        defaultStorageCardShouldNotBeFound("dispatchedValue.lessThan=" + DEFAULT_DISPATCHED_VALUE);

        // Get all the storageCardList where dispatchedValue is less than UPDATED_DISPATCHED_VALUE
        defaultStorageCardShouldBeFound("dispatchedValue.lessThan=" + UPDATED_DISPATCHED_VALUE);
    }

    @Test
    @Transactional
    void getAllStorageCardsByDispatchedValueIsGreaterThanSomething() throws Exception {
        // Initialize the database
        storageCardRepository.saveAndFlush(storageCard);

        // Get all the storageCardList where dispatchedValue is greater than DEFAULT_DISPATCHED_VALUE
        defaultStorageCardShouldNotBeFound("dispatchedValue.greaterThan=" + DEFAULT_DISPATCHED_VALUE);

        // Get all the storageCardList where dispatchedValue is greater than SMALLER_DISPATCHED_VALUE
        defaultStorageCardShouldBeFound("dispatchedValue.greaterThan=" + SMALLER_DISPATCHED_VALUE);
    }

    @Test
    @Transactional
    void getAllStorageCardsByTotalValueIsEqualToSomething() throws Exception {
        // Initialize the database
        storageCardRepository.saveAndFlush(storageCard);

        // Get all the storageCardList where totalValue equals to DEFAULT_TOTAL_VALUE
        defaultStorageCardShouldBeFound("totalValue.equals=" + DEFAULT_TOTAL_VALUE);

        // Get all the storageCardList where totalValue equals to UPDATED_TOTAL_VALUE
        defaultStorageCardShouldNotBeFound("totalValue.equals=" + UPDATED_TOTAL_VALUE);
    }

    @Test
    @Transactional
    void getAllStorageCardsByTotalValueIsInShouldWork() throws Exception {
        // Initialize the database
        storageCardRepository.saveAndFlush(storageCard);

        // Get all the storageCardList where totalValue in DEFAULT_TOTAL_VALUE or UPDATED_TOTAL_VALUE
        defaultStorageCardShouldBeFound("totalValue.in=" + DEFAULT_TOTAL_VALUE + "," + UPDATED_TOTAL_VALUE);

        // Get all the storageCardList where totalValue equals to UPDATED_TOTAL_VALUE
        defaultStorageCardShouldNotBeFound("totalValue.in=" + UPDATED_TOTAL_VALUE);
    }

    @Test
    @Transactional
    void getAllStorageCardsByTotalValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        storageCardRepository.saveAndFlush(storageCard);

        // Get all the storageCardList where totalValue is not null
        defaultStorageCardShouldBeFound("totalValue.specified=true");

        // Get all the storageCardList where totalValue is null
        defaultStorageCardShouldNotBeFound("totalValue.specified=false");
    }

    @Test
    @Transactional
    void getAllStorageCardsByTotalValueIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        storageCardRepository.saveAndFlush(storageCard);

        // Get all the storageCardList where totalValue is greater than or equal to DEFAULT_TOTAL_VALUE
        defaultStorageCardShouldBeFound("totalValue.greaterThanOrEqual=" + DEFAULT_TOTAL_VALUE);

        // Get all the storageCardList where totalValue is greater than or equal to UPDATED_TOTAL_VALUE
        defaultStorageCardShouldNotBeFound("totalValue.greaterThanOrEqual=" + UPDATED_TOTAL_VALUE);
    }

    @Test
    @Transactional
    void getAllStorageCardsByTotalValueIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        storageCardRepository.saveAndFlush(storageCard);

        // Get all the storageCardList where totalValue is less than or equal to DEFAULT_TOTAL_VALUE
        defaultStorageCardShouldBeFound("totalValue.lessThanOrEqual=" + DEFAULT_TOTAL_VALUE);

        // Get all the storageCardList where totalValue is less than or equal to SMALLER_TOTAL_VALUE
        defaultStorageCardShouldNotBeFound("totalValue.lessThanOrEqual=" + SMALLER_TOTAL_VALUE);
    }

    @Test
    @Transactional
    void getAllStorageCardsByTotalValueIsLessThanSomething() throws Exception {
        // Initialize the database
        storageCardRepository.saveAndFlush(storageCard);

        // Get all the storageCardList where totalValue is less than DEFAULT_TOTAL_VALUE
        defaultStorageCardShouldNotBeFound("totalValue.lessThan=" + DEFAULT_TOTAL_VALUE);

        // Get all the storageCardList where totalValue is less than UPDATED_TOTAL_VALUE
        defaultStorageCardShouldBeFound("totalValue.lessThan=" + UPDATED_TOTAL_VALUE);
    }

    @Test
    @Transactional
    void getAllStorageCardsByTotalValueIsGreaterThanSomething() throws Exception {
        // Initialize the database
        storageCardRepository.saveAndFlush(storageCard);

        // Get all the storageCardList where totalValue is greater than DEFAULT_TOTAL_VALUE
        defaultStorageCardShouldNotBeFound("totalValue.greaterThan=" + DEFAULT_TOTAL_VALUE);

        // Get all the storageCardList where totalValue is greater than SMALLER_TOTAL_VALUE
        defaultStorageCardShouldBeFound("totalValue.greaterThan=" + SMALLER_TOTAL_VALUE);
    }

    @Test
    @Transactional
    void getAllStorageCardsByPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        storageCardRepository.saveAndFlush(storageCard);

        // Get all the storageCardList where price equals to DEFAULT_PRICE
        defaultStorageCardShouldBeFound("price.equals=" + DEFAULT_PRICE);

        // Get all the storageCardList where price equals to UPDATED_PRICE
        defaultStorageCardShouldNotBeFound("price.equals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllStorageCardsByPriceIsInShouldWork() throws Exception {
        // Initialize the database
        storageCardRepository.saveAndFlush(storageCard);

        // Get all the storageCardList where price in DEFAULT_PRICE or UPDATED_PRICE
        defaultStorageCardShouldBeFound("price.in=" + DEFAULT_PRICE + "," + UPDATED_PRICE);

        // Get all the storageCardList where price equals to UPDATED_PRICE
        defaultStorageCardShouldNotBeFound("price.in=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllStorageCardsByPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        storageCardRepository.saveAndFlush(storageCard);

        // Get all the storageCardList where price is not null
        defaultStorageCardShouldBeFound("price.specified=true");

        // Get all the storageCardList where price is null
        defaultStorageCardShouldNotBeFound("price.specified=false");
    }

    @Test
    @Transactional
    void getAllStorageCardsByPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        storageCardRepository.saveAndFlush(storageCard);

        // Get all the storageCardList where price is greater than or equal to DEFAULT_PRICE
        defaultStorageCardShouldBeFound("price.greaterThanOrEqual=" + DEFAULT_PRICE);

        // Get all the storageCardList where price is greater than or equal to UPDATED_PRICE
        defaultStorageCardShouldNotBeFound("price.greaterThanOrEqual=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllStorageCardsByPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        storageCardRepository.saveAndFlush(storageCard);

        // Get all the storageCardList where price is less than or equal to DEFAULT_PRICE
        defaultStorageCardShouldBeFound("price.lessThanOrEqual=" + DEFAULT_PRICE);

        // Get all the storageCardList where price is less than or equal to SMALLER_PRICE
        defaultStorageCardShouldNotBeFound("price.lessThanOrEqual=" + SMALLER_PRICE);
    }

    @Test
    @Transactional
    void getAllStorageCardsByPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        storageCardRepository.saveAndFlush(storageCard);

        // Get all the storageCardList where price is less than DEFAULT_PRICE
        defaultStorageCardShouldNotBeFound("price.lessThan=" + DEFAULT_PRICE);

        // Get all the storageCardList where price is less than UPDATED_PRICE
        defaultStorageCardShouldBeFound("price.lessThan=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllStorageCardsByPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        storageCardRepository.saveAndFlush(storageCard);

        // Get all the storageCardList where price is greater than DEFAULT_PRICE
        defaultStorageCardShouldNotBeFound("price.greaterThan=" + DEFAULT_PRICE);

        // Get all the storageCardList where price is greater than SMALLER_PRICE
        defaultStorageCardShouldBeFound("price.greaterThan=" + SMALLER_PRICE);
    }

    @Test
    @Transactional
    void getAllStorageCardsByStorageCardTrafficIsEqualToSomething() throws Exception {
        StorageCardTraffic storageCardTraffic;
        if (TestUtil.findAll(em, StorageCardTraffic.class).isEmpty()) {
            storageCardRepository.saveAndFlush(storageCard);
            storageCardTraffic = StorageCardTrafficResourceIT.createEntity(em);
        } else {
            storageCardTraffic = TestUtil.findAll(em, StorageCardTraffic.class).get(0);
        }
        em.persist(storageCardTraffic);
        em.flush();
        storageCard.addStorageCardTraffic(storageCardTraffic);
        storageCardRepository.saveAndFlush(storageCard);
        Long storageCardTrafficId = storageCardTraffic.getId();

        // Get all the storageCardList where storageCardTraffic equals to storageCardTrafficId
        defaultStorageCardShouldBeFound("storageCardTrafficId.equals=" + storageCardTrafficId);

        // Get all the storageCardList where storageCardTraffic equals to (storageCardTrafficId + 1)
        defaultStorageCardShouldNotBeFound("storageCardTrafficId.equals=" + (storageCardTrafficId + 1));
    }

    @Test
    @Transactional
    void getAllStorageCardsByBusinessYearIsEqualToSomething() throws Exception {
        BusinessYear businessYear;
        if (TestUtil.findAll(em, BusinessYear.class).isEmpty()) {
            storageCardRepository.saveAndFlush(storageCard);
            businessYear = BusinessYearResourceIT.createEntity(em);
        } else {
            businessYear = TestUtil.findAll(em, BusinessYear.class).get(0);
        }
        em.persist(businessYear);
        em.flush();
        storageCard.setBusinessYear(businessYear);
        storageCardRepository.saveAndFlush(storageCard);
        Long businessYearId = businessYear.getId();

        // Get all the storageCardList where businessYear equals to businessYearId
        defaultStorageCardShouldBeFound("businessYearId.equals=" + businessYearId);

        // Get all the storageCardList where businessYear equals to (businessYearId + 1)
        defaultStorageCardShouldNotBeFound("businessYearId.equals=" + (businessYearId + 1));
    }

    @Test
    @Transactional
    void getAllStorageCardsByResourceIsEqualToSomething() throws Exception {
        Resource resource;
        if (TestUtil.findAll(em, Resource.class).isEmpty()) {
            storageCardRepository.saveAndFlush(storageCard);
            resource = ResourceResourceIT.createEntity(em);
        } else {
            resource = TestUtil.findAll(em, Resource.class).get(0);
        }
        em.persist(resource);
        em.flush();
        storageCard.setResource(resource);
        storageCardRepository.saveAndFlush(storageCard);
        Long resourceId = resource.getId();

        // Get all the storageCardList where resource equals to resourceId
        defaultStorageCardShouldBeFound("resourceId.equals=" + resourceId);

        // Get all the storageCardList where resource equals to (resourceId + 1)
        defaultStorageCardShouldNotBeFound("resourceId.equals=" + (resourceId + 1));
    }

    @Test
    @Transactional
    void getAllStorageCardsByStorageIsEqualToSomething() throws Exception {
        Storage storage;
        if (TestUtil.findAll(em, Storage.class).isEmpty()) {
            storageCardRepository.saveAndFlush(storageCard);
            storage = StorageResourceIT.createEntity(em);
        } else {
            storage = TestUtil.findAll(em, Storage.class).get(0);
        }
        em.persist(storage);
        em.flush();
        storageCard.setStorage(storage);
        storageCardRepository.saveAndFlush(storageCard);
        Long storageId = storage.getId();

        // Get all the storageCardList where storage equals to storageId
        defaultStorageCardShouldBeFound("storageId.equals=" + storageId);

        // Get all the storageCardList where storage equals to (storageId + 1)
        defaultStorageCardShouldNotBeFound("storageId.equals=" + (storageId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultStorageCardShouldBeFound(String filter) throws Exception {
        restStorageCardMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(storageCard.getId())))
            .andExpect(jsonPath("$.[*].startingAmount").value(hasItem(DEFAULT_STARTING_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].receivedAmount").value(hasItem(DEFAULT_RECEIVED_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].dispatchedAmount").value(hasItem(DEFAULT_DISPATCHED_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].totalAmount").value(hasItem(DEFAULT_TOTAL_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].startingValue").value(hasItem(DEFAULT_STARTING_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].receivedValue").value(hasItem(DEFAULT_RECEIVED_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].dispatchedValue").value(hasItem(DEFAULT_DISPATCHED_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].totalValue").value(hasItem(DEFAULT_TOTAL_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())));

        // Check, that the count call also returns 1
        restStorageCardMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultStorageCardShouldNotBeFound(String filter) throws Exception {
        restStorageCardMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restStorageCardMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingStorageCard() throws Exception {
        // Get the storageCard
        restStorageCardMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingStorageCard() throws Exception {
        // Initialize the database
        storageCard.setId(UUID.randomUUID().toString());
        storageCardRepository.saveAndFlush(storageCard);

        int databaseSizeBeforeUpdate = storageCardRepository.findAll().size();

        // Update the storageCard
        StorageCard updatedStorageCard = storageCardRepository.findById(storageCard.getId()).get();
        // Disconnect from session so that the updates on updatedStorageCard are not directly saved in db
        em.detach(updatedStorageCard);
        updatedStorageCard
            .startingAmount(UPDATED_STARTING_AMOUNT)
            .receivedAmount(UPDATED_RECEIVED_AMOUNT)
            .dispatchedAmount(UPDATED_DISPATCHED_AMOUNT)
            .totalAmount(UPDATED_TOTAL_AMOUNT)
            .startingValue(UPDATED_STARTING_VALUE)
            .receivedValue(UPDATED_RECEIVED_VALUE)
            .dispatchedValue(UPDATED_DISPATCHED_VALUE)
            .totalValue(UPDATED_TOTAL_VALUE)
            .price(UPDATED_PRICE);
        StorageCardDTO storageCardDTO = storageCardMapper.toDto(updatedStorageCard);

        restStorageCardMockMvc
            .perform(
                put(ENTITY_API_URL_ID, storageCardDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(storageCardDTO))
            )
            .andExpect(status().isOk());

        // Validate the StorageCard in the database
        List<StorageCard> storageCardList = storageCardRepository.findAll();
        assertThat(storageCardList).hasSize(databaseSizeBeforeUpdate);
        StorageCard testStorageCard = storageCardList.get(storageCardList.size() - 1);
        assertThat(testStorageCard.getStartingAmount()).isEqualTo(UPDATED_STARTING_AMOUNT);
        assertThat(testStorageCard.getReceivedAmount()).isEqualTo(UPDATED_RECEIVED_AMOUNT);
        assertThat(testStorageCard.getDispatchedAmount()).isEqualTo(UPDATED_DISPATCHED_AMOUNT);
        assertThat(testStorageCard.getTotalAmount()).isEqualTo(UPDATED_TOTAL_AMOUNT);
        assertThat(testStorageCard.getStartingValue()).isEqualTo(UPDATED_STARTING_VALUE);
        assertThat(testStorageCard.getReceivedValue()).isEqualTo(UPDATED_RECEIVED_VALUE);
        assertThat(testStorageCard.getDispatchedValue()).isEqualTo(UPDATED_DISPATCHED_VALUE);
        assertThat(testStorageCard.getTotalValue()).isEqualTo(UPDATED_TOTAL_VALUE);
        assertThat(testStorageCard.getPrice()).isEqualTo(UPDATED_PRICE);
    }

    @Test
    @Transactional
    void putNonExistingStorageCard() throws Exception {
        int databaseSizeBeforeUpdate = storageCardRepository.findAll().size();
        storageCard.setId(UUID.randomUUID().toString());

        // Create the StorageCard
        StorageCardDTO storageCardDTO = storageCardMapper.toDto(storageCard);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStorageCardMockMvc
            .perform(
                put(ENTITY_API_URL_ID, storageCardDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(storageCardDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StorageCard in the database
        List<StorageCard> storageCardList = storageCardRepository.findAll();
        assertThat(storageCardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStorageCard() throws Exception {
        int databaseSizeBeforeUpdate = storageCardRepository.findAll().size();
        storageCard.setId(UUID.randomUUID().toString());

        // Create the StorageCard
        StorageCardDTO storageCardDTO = storageCardMapper.toDto(storageCard);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStorageCardMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(storageCardDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StorageCard in the database
        List<StorageCard> storageCardList = storageCardRepository.findAll();
        assertThat(storageCardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStorageCard() throws Exception {
        int databaseSizeBeforeUpdate = storageCardRepository.findAll().size();
        storageCard.setId(UUID.randomUUID().toString());

        // Create the StorageCard
        StorageCardDTO storageCardDTO = storageCardMapper.toDto(storageCard);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStorageCardMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(storageCardDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the StorageCard in the database
        List<StorageCard> storageCardList = storageCardRepository.findAll();
        assertThat(storageCardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStorageCardWithPatch() throws Exception {
        // Initialize the database
        storageCard.setId(UUID.randomUUID().toString());
        storageCardRepository.saveAndFlush(storageCard);

        int databaseSizeBeforeUpdate = storageCardRepository.findAll().size();

        // Update the storageCard using partial update
        StorageCard partialUpdatedStorageCard = new StorageCard();
        partialUpdatedStorageCard.setId(storageCard.getId());

        partialUpdatedStorageCard
            .startingAmount(UPDATED_STARTING_AMOUNT)
            .receivedAmount(UPDATED_RECEIVED_AMOUNT)
            .totalAmount(UPDATED_TOTAL_AMOUNT)
            .dispatchedValue(UPDATED_DISPATCHED_VALUE);

        restStorageCardMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStorageCard.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStorageCard))
            )
            .andExpect(status().isOk());

        // Validate the StorageCard in the database
        List<StorageCard> storageCardList = storageCardRepository.findAll();
        assertThat(storageCardList).hasSize(databaseSizeBeforeUpdate);
        StorageCard testStorageCard = storageCardList.get(storageCardList.size() - 1);
        assertThat(testStorageCard.getStartingAmount()).isEqualTo(UPDATED_STARTING_AMOUNT);
        assertThat(testStorageCard.getReceivedAmount()).isEqualTo(UPDATED_RECEIVED_AMOUNT);
        assertThat(testStorageCard.getDispatchedAmount()).isEqualTo(DEFAULT_DISPATCHED_AMOUNT);
        assertThat(testStorageCard.getTotalAmount()).isEqualTo(UPDATED_TOTAL_AMOUNT);
        assertThat(testStorageCard.getStartingValue()).isEqualTo(DEFAULT_STARTING_VALUE);
        assertThat(testStorageCard.getReceivedValue()).isEqualTo(DEFAULT_RECEIVED_VALUE);
        assertThat(testStorageCard.getDispatchedValue()).isEqualTo(UPDATED_DISPATCHED_VALUE);
        assertThat(testStorageCard.getTotalValue()).isEqualTo(DEFAULT_TOTAL_VALUE);
        assertThat(testStorageCard.getPrice()).isEqualTo(DEFAULT_PRICE);
    }

    @Test
    @Transactional
    void fullUpdateStorageCardWithPatch() throws Exception {
        // Initialize the database
        storageCard.setId(UUID.randomUUID().toString());
        storageCardRepository.saveAndFlush(storageCard);

        int databaseSizeBeforeUpdate = storageCardRepository.findAll().size();

        // Update the storageCard using partial update
        StorageCard partialUpdatedStorageCard = new StorageCard();
        partialUpdatedStorageCard.setId(storageCard.getId());

        partialUpdatedStorageCard
            .startingAmount(UPDATED_STARTING_AMOUNT)
            .receivedAmount(UPDATED_RECEIVED_AMOUNT)
            .dispatchedAmount(UPDATED_DISPATCHED_AMOUNT)
            .totalAmount(UPDATED_TOTAL_AMOUNT)
            .startingValue(UPDATED_STARTING_VALUE)
            .receivedValue(UPDATED_RECEIVED_VALUE)
            .dispatchedValue(UPDATED_DISPATCHED_VALUE)
            .totalValue(UPDATED_TOTAL_VALUE)
            .price(UPDATED_PRICE);

        restStorageCardMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStorageCard.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStorageCard))
            )
            .andExpect(status().isOk());

        // Validate the StorageCard in the database
        List<StorageCard> storageCardList = storageCardRepository.findAll();
        assertThat(storageCardList).hasSize(databaseSizeBeforeUpdate);
        StorageCard testStorageCard = storageCardList.get(storageCardList.size() - 1);
        assertThat(testStorageCard.getStartingAmount()).isEqualTo(UPDATED_STARTING_AMOUNT);
        assertThat(testStorageCard.getReceivedAmount()).isEqualTo(UPDATED_RECEIVED_AMOUNT);
        assertThat(testStorageCard.getDispatchedAmount()).isEqualTo(UPDATED_DISPATCHED_AMOUNT);
        assertThat(testStorageCard.getTotalAmount()).isEqualTo(UPDATED_TOTAL_AMOUNT);
        assertThat(testStorageCard.getStartingValue()).isEqualTo(UPDATED_STARTING_VALUE);
        assertThat(testStorageCard.getReceivedValue()).isEqualTo(UPDATED_RECEIVED_VALUE);
        assertThat(testStorageCard.getDispatchedValue()).isEqualTo(UPDATED_DISPATCHED_VALUE);
        assertThat(testStorageCard.getTotalValue()).isEqualTo(UPDATED_TOTAL_VALUE);
        assertThat(testStorageCard.getPrice()).isEqualTo(UPDATED_PRICE);
    }

    @Test
    @Transactional
    void patchNonExistingStorageCard() throws Exception {
        int databaseSizeBeforeUpdate = storageCardRepository.findAll().size();
        storageCard.setId(UUID.randomUUID().toString());

        // Create the StorageCard
        StorageCardDTO storageCardDTO = storageCardMapper.toDto(storageCard);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStorageCardMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, storageCardDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(storageCardDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StorageCard in the database
        List<StorageCard> storageCardList = storageCardRepository.findAll();
        assertThat(storageCardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStorageCard() throws Exception {
        int databaseSizeBeforeUpdate = storageCardRepository.findAll().size();
        storageCard.setId(UUID.randomUUID().toString());

        // Create the StorageCard
        StorageCardDTO storageCardDTO = storageCardMapper.toDto(storageCard);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStorageCardMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(storageCardDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StorageCard in the database
        List<StorageCard> storageCardList = storageCardRepository.findAll();
        assertThat(storageCardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStorageCard() throws Exception {
        int databaseSizeBeforeUpdate = storageCardRepository.findAll().size();
        storageCard.setId(UUID.randomUUID().toString());

        // Create the StorageCard
        StorageCardDTO storageCardDTO = storageCardMapper.toDto(storageCard);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStorageCardMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(storageCardDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the StorageCard in the database
        List<StorageCard> storageCardList = storageCardRepository.findAll();
        assertThat(storageCardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStorageCard() throws Exception {
        // Initialize the database
        storageCard.setId(UUID.randomUUID().toString());
        storageCardRepository.saveAndFlush(storageCard);

        int databaseSizeBeforeDelete = storageCardRepository.findAll().size();

        // Delete the storageCard
        restStorageCardMockMvc
            .perform(delete(ENTITY_API_URL_ID, storageCard.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StorageCard> storageCardList = storageCardRepository.findAll();
        assertThat(storageCardList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
