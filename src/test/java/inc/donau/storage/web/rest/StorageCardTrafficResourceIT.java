package inc.donau.storage.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import inc.donau.storage.IntegrationTest;
import inc.donau.storage.domain.StorageCard;
import inc.donau.storage.domain.StorageCardTraffic;
import inc.donau.storage.domain.enumeration.StorageCardTrafficDirection;
import inc.donau.storage.domain.enumeration.StorageCardTrafficType;
import inc.donau.storage.repository.StorageCardTrafficRepository;
import inc.donau.storage.service.criteria.StorageCardTrafficCriteria;
import inc.donau.storage.service.dto.StorageCardTrafficDTO;
import inc.donau.storage.service.mapper.StorageCardTrafficMapper;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link StorageCardTrafficResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class StorageCardTrafficResourceIT {

    private static final StorageCardTrafficType DEFAULT_TYPE = StorageCardTrafficType.STARTING_BALANCE;
    private static final StorageCardTrafficType UPDATED_TYPE = StorageCardTrafficType.TRANSFER;

    private static final StorageCardTrafficDirection DEFAULT_DIRECTION = StorageCardTrafficDirection.IN;
    private static final StorageCardTrafficDirection UPDATED_DIRECTION = StorageCardTrafficDirection.OUT;

    private static final Float DEFAULT_AMOUNT = 1F;
    private static final Float UPDATED_AMOUNT = 2F;
    private static final Float SMALLER_AMOUNT = 1F - 1F;

    private static final Float DEFAULT_PRICE = 0F;
    private static final Float UPDATED_PRICE = 1F;
    private static final Float SMALLER_PRICE = 0F - 1F;

    private static final Float DEFAULT_TRAFFIC_VALUE = 1F;
    private static final Float UPDATED_TRAFFIC_VALUE = 2F;
    private static final Float SMALLER_TRAFFIC_VALUE = 1F - 1F;

    private static final String DEFAULT_DOCUMENT = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENT = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE = LocalDate.ofEpochDay(-1L);

    private static final String ENTITY_API_URL = "/api/storage-card-traffics";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private StorageCardTrafficRepository storageCardTrafficRepository;

    @Autowired
    private StorageCardTrafficMapper storageCardTrafficMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStorageCardTrafficMockMvc;

    private StorageCardTraffic storageCardTraffic;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StorageCardTraffic createEntity(EntityManager em) {
        StorageCardTraffic storageCardTraffic = new StorageCardTraffic()
            .type(DEFAULT_TYPE)
            .direction(DEFAULT_DIRECTION)
            .amount(DEFAULT_AMOUNT)
            .price(DEFAULT_PRICE)
            .trafficValue(DEFAULT_TRAFFIC_VALUE)
            .document(DEFAULT_DOCUMENT)
            .date(DEFAULT_DATE);
        // Add required entity
        StorageCard storageCard;
        if (TestUtil.findAll(em, StorageCard.class).isEmpty()) {
            storageCard = StorageCardResourceIT.createEntity(em);
            em.persist(storageCard);
            em.flush();
        } else {
            storageCard = TestUtil.findAll(em, StorageCard.class).get(0);
        }
        storageCardTraffic.setStorageCard(storageCard);
        return storageCardTraffic;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StorageCardTraffic createUpdatedEntity(EntityManager em) {
        StorageCardTraffic storageCardTraffic = new StorageCardTraffic()
            .type(UPDATED_TYPE)
            .direction(UPDATED_DIRECTION)
            .amount(UPDATED_AMOUNT)
            .price(UPDATED_PRICE)
            .trafficValue(UPDATED_TRAFFIC_VALUE)
            .document(UPDATED_DOCUMENT)
            .date(UPDATED_DATE);
        // Add required entity
        StorageCard storageCard;
        if (TestUtil.findAll(em, StorageCard.class).isEmpty()) {
            storageCard = StorageCardResourceIT.createUpdatedEntity(em);
            em.persist(storageCard);
            em.flush();
        } else {
            storageCard = TestUtil.findAll(em, StorageCard.class).get(0);
        }
        storageCardTraffic.setStorageCard(storageCard);
        return storageCardTraffic;
    }

    @BeforeEach
    public void initTest() {
        storageCardTraffic = createEntity(em);
    }

    @Test
    @Transactional
    void createStorageCardTraffic() throws Exception {
        int databaseSizeBeforeCreate = storageCardTrafficRepository.findAll().size();
        // Create the StorageCardTraffic
        StorageCardTrafficDTO storageCardTrafficDTO = storageCardTrafficMapper.toDto(storageCardTraffic);
        restStorageCardTrafficMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(storageCardTrafficDTO))
            )
            .andExpect(status().isCreated());

        // Validate the StorageCardTraffic in the database
        List<StorageCardTraffic> storageCardTrafficList = storageCardTrafficRepository.findAll();
        assertThat(storageCardTrafficList).hasSize(databaseSizeBeforeCreate + 1);
        StorageCardTraffic testStorageCardTraffic = storageCardTrafficList.get(storageCardTrafficList.size() - 1);
        assertThat(testStorageCardTraffic.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testStorageCardTraffic.getDirection()).isEqualTo(DEFAULT_DIRECTION);
        assertThat(testStorageCardTraffic.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testStorageCardTraffic.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testStorageCardTraffic.getTrafficValue()).isEqualTo(DEFAULT_TRAFFIC_VALUE);
        assertThat(testStorageCardTraffic.getDocument()).isEqualTo(DEFAULT_DOCUMENT);
        assertThat(testStorageCardTraffic.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    void createStorageCardTrafficWithExistingId() throws Exception {
        // Create the StorageCardTraffic with an existing ID
        storageCardTraffic.setId(1L);
        StorageCardTrafficDTO storageCardTrafficDTO = storageCardTrafficMapper.toDto(storageCardTraffic);

        int databaseSizeBeforeCreate = storageCardTrafficRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStorageCardTrafficMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(storageCardTrafficDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StorageCardTraffic in the database
        List<StorageCardTraffic> storageCardTrafficList = storageCardTrafficRepository.findAll();
        assertThat(storageCardTrafficList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = storageCardTrafficRepository.findAll().size();
        // set the field null
        storageCardTraffic.setType(null);

        // Create the StorageCardTraffic, which fails.
        StorageCardTrafficDTO storageCardTrafficDTO = storageCardTrafficMapper.toDto(storageCardTraffic);

        restStorageCardTrafficMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(storageCardTrafficDTO))
            )
            .andExpect(status().isBadRequest());

        List<StorageCardTraffic> storageCardTrafficList = storageCardTrafficRepository.findAll();
        assertThat(storageCardTrafficList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDirectionIsRequired() throws Exception {
        int databaseSizeBeforeTest = storageCardTrafficRepository.findAll().size();
        // set the field null
        storageCardTraffic.setDirection(null);

        // Create the StorageCardTraffic, which fails.
        StorageCardTrafficDTO storageCardTrafficDTO = storageCardTrafficMapper.toDto(storageCardTraffic);

        restStorageCardTrafficMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(storageCardTrafficDTO))
            )
            .andExpect(status().isBadRequest());

        List<StorageCardTraffic> storageCardTrafficList = storageCardTrafficRepository.findAll();
        assertThat(storageCardTrafficList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = storageCardTrafficRepository.findAll().size();
        // set the field null
        storageCardTraffic.setAmount(null);

        // Create the StorageCardTraffic, which fails.
        StorageCardTrafficDTO storageCardTrafficDTO = storageCardTrafficMapper.toDto(storageCardTraffic);

        restStorageCardTrafficMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(storageCardTrafficDTO))
            )
            .andExpect(status().isBadRequest());

        List<StorageCardTraffic> storageCardTrafficList = storageCardTrafficRepository.findAll();
        assertThat(storageCardTrafficList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = storageCardTrafficRepository.findAll().size();
        // set the field null
        storageCardTraffic.setPrice(null);

        // Create the StorageCardTraffic, which fails.
        StorageCardTrafficDTO storageCardTrafficDTO = storageCardTrafficMapper.toDto(storageCardTraffic);

        restStorageCardTrafficMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(storageCardTrafficDTO))
            )
            .andExpect(status().isBadRequest());

        List<StorageCardTraffic> storageCardTrafficList = storageCardTrafficRepository.findAll();
        assertThat(storageCardTrafficList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTrafficValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = storageCardTrafficRepository.findAll().size();
        // set the field null
        storageCardTraffic.setTrafficValue(null);

        // Create the StorageCardTraffic, which fails.
        StorageCardTrafficDTO storageCardTrafficDTO = storageCardTrafficMapper.toDto(storageCardTraffic);

        restStorageCardTrafficMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(storageCardTrafficDTO))
            )
            .andExpect(status().isBadRequest());

        List<StorageCardTraffic> storageCardTrafficList = storageCardTrafficRepository.findAll();
        assertThat(storageCardTrafficList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllStorageCardTraffics() throws Exception {
        // Initialize the database
        storageCardTrafficRepository.saveAndFlush(storageCardTraffic);

        // Get all the storageCardTrafficList
        restStorageCardTrafficMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(storageCardTraffic.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].direction").value(hasItem(DEFAULT_DIRECTION.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].trafficValue").value(hasItem(DEFAULT_TRAFFIC_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].document").value(hasItem(DEFAULT_DOCUMENT)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }

    @Test
    @Transactional
    void getStorageCardTraffic() throws Exception {
        // Initialize the database
        storageCardTrafficRepository.saveAndFlush(storageCardTraffic);

        // Get the storageCardTraffic
        restStorageCardTrafficMockMvc
            .perform(get(ENTITY_API_URL_ID, storageCardTraffic.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(storageCardTraffic.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.direction").value(DEFAULT_DIRECTION.toString()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.trafficValue").value(DEFAULT_TRAFFIC_VALUE.doubleValue()))
            .andExpect(jsonPath("$.document").value(DEFAULT_DOCUMENT))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }

    @Test
    @Transactional
    void getStorageCardTrafficsByIdFiltering() throws Exception {
        // Initialize the database
        storageCardTrafficRepository.saveAndFlush(storageCardTraffic);

        Long id = storageCardTraffic.getId();

        defaultStorageCardTrafficShouldBeFound("id.equals=" + id);
        defaultStorageCardTrafficShouldNotBeFound("id.notEquals=" + id);

        defaultStorageCardTrafficShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultStorageCardTrafficShouldNotBeFound("id.greaterThan=" + id);

        defaultStorageCardTrafficShouldBeFound("id.lessThanOrEqual=" + id);
        defaultStorageCardTrafficShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllStorageCardTrafficsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        storageCardTrafficRepository.saveAndFlush(storageCardTraffic);

        // Get all the storageCardTrafficList where type equals to DEFAULT_TYPE
        defaultStorageCardTrafficShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the storageCardTrafficList where type equals to UPDATED_TYPE
        defaultStorageCardTrafficShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllStorageCardTrafficsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        storageCardTrafficRepository.saveAndFlush(storageCardTraffic);

        // Get all the storageCardTrafficList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultStorageCardTrafficShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the storageCardTrafficList where type equals to UPDATED_TYPE
        defaultStorageCardTrafficShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllStorageCardTrafficsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        storageCardTrafficRepository.saveAndFlush(storageCardTraffic);

        // Get all the storageCardTrafficList where type is not null
        defaultStorageCardTrafficShouldBeFound("type.specified=true");

        // Get all the storageCardTrafficList where type is null
        defaultStorageCardTrafficShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    void getAllStorageCardTrafficsByDirectionIsEqualToSomething() throws Exception {
        // Initialize the database
        storageCardTrafficRepository.saveAndFlush(storageCardTraffic);

        // Get all the storageCardTrafficList where direction equals to DEFAULT_DIRECTION
        defaultStorageCardTrafficShouldBeFound("direction.equals=" + DEFAULT_DIRECTION);

        // Get all the storageCardTrafficList where direction equals to UPDATED_DIRECTION
        defaultStorageCardTrafficShouldNotBeFound("direction.equals=" + UPDATED_DIRECTION);
    }

    @Test
    @Transactional
    void getAllStorageCardTrafficsByDirectionIsInShouldWork() throws Exception {
        // Initialize the database
        storageCardTrafficRepository.saveAndFlush(storageCardTraffic);

        // Get all the storageCardTrafficList where direction in DEFAULT_DIRECTION or UPDATED_DIRECTION
        defaultStorageCardTrafficShouldBeFound("direction.in=" + DEFAULT_DIRECTION + "," + UPDATED_DIRECTION);

        // Get all the storageCardTrafficList where direction equals to UPDATED_DIRECTION
        defaultStorageCardTrafficShouldNotBeFound("direction.in=" + UPDATED_DIRECTION);
    }

    @Test
    @Transactional
    void getAllStorageCardTrafficsByDirectionIsNullOrNotNull() throws Exception {
        // Initialize the database
        storageCardTrafficRepository.saveAndFlush(storageCardTraffic);

        // Get all the storageCardTrafficList where direction is not null
        defaultStorageCardTrafficShouldBeFound("direction.specified=true");

        // Get all the storageCardTrafficList where direction is null
        defaultStorageCardTrafficShouldNotBeFound("direction.specified=false");
    }

    @Test
    @Transactional
    void getAllStorageCardTrafficsByAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        storageCardTrafficRepository.saveAndFlush(storageCardTraffic);

        // Get all the storageCardTrafficList where amount equals to DEFAULT_AMOUNT
        defaultStorageCardTrafficShouldBeFound("amount.equals=" + DEFAULT_AMOUNT);

        // Get all the storageCardTrafficList where amount equals to UPDATED_AMOUNT
        defaultStorageCardTrafficShouldNotBeFound("amount.equals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllStorageCardTrafficsByAmountIsInShouldWork() throws Exception {
        // Initialize the database
        storageCardTrafficRepository.saveAndFlush(storageCardTraffic);

        // Get all the storageCardTrafficList where amount in DEFAULT_AMOUNT or UPDATED_AMOUNT
        defaultStorageCardTrafficShouldBeFound("amount.in=" + DEFAULT_AMOUNT + "," + UPDATED_AMOUNT);

        // Get all the storageCardTrafficList where amount equals to UPDATED_AMOUNT
        defaultStorageCardTrafficShouldNotBeFound("amount.in=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllStorageCardTrafficsByAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        storageCardTrafficRepository.saveAndFlush(storageCardTraffic);

        // Get all the storageCardTrafficList where amount is not null
        defaultStorageCardTrafficShouldBeFound("amount.specified=true");

        // Get all the storageCardTrafficList where amount is null
        defaultStorageCardTrafficShouldNotBeFound("amount.specified=false");
    }

    @Test
    @Transactional
    void getAllStorageCardTrafficsByAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        storageCardTrafficRepository.saveAndFlush(storageCardTraffic);

        // Get all the storageCardTrafficList where amount is greater than or equal to DEFAULT_AMOUNT
        defaultStorageCardTrafficShouldBeFound("amount.greaterThanOrEqual=" + DEFAULT_AMOUNT);

        // Get all the storageCardTrafficList where amount is greater than or equal to UPDATED_AMOUNT
        defaultStorageCardTrafficShouldNotBeFound("amount.greaterThanOrEqual=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllStorageCardTrafficsByAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        storageCardTrafficRepository.saveAndFlush(storageCardTraffic);

        // Get all the storageCardTrafficList where amount is less than or equal to DEFAULT_AMOUNT
        defaultStorageCardTrafficShouldBeFound("amount.lessThanOrEqual=" + DEFAULT_AMOUNT);

        // Get all the storageCardTrafficList where amount is less than or equal to SMALLER_AMOUNT
        defaultStorageCardTrafficShouldNotBeFound("amount.lessThanOrEqual=" + SMALLER_AMOUNT);
    }

    @Test
    @Transactional
    void getAllStorageCardTrafficsByAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        storageCardTrafficRepository.saveAndFlush(storageCardTraffic);

        // Get all the storageCardTrafficList where amount is less than DEFAULT_AMOUNT
        defaultStorageCardTrafficShouldNotBeFound("amount.lessThan=" + DEFAULT_AMOUNT);

        // Get all the storageCardTrafficList where amount is less than UPDATED_AMOUNT
        defaultStorageCardTrafficShouldBeFound("amount.lessThan=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllStorageCardTrafficsByAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        storageCardTrafficRepository.saveAndFlush(storageCardTraffic);

        // Get all the storageCardTrafficList where amount is greater than DEFAULT_AMOUNT
        defaultStorageCardTrafficShouldNotBeFound("amount.greaterThan=" + DEFAULT_AMOUNT);

        // Get all the storageCardTrafficList where amount is greater than SMALLER_AMOUNT
        defaultStorageCardTrafficShouldBeFound("amount.greaterThan=" + SMALLER_AMOUNT);
    }

    @Test
    @Transactional
    void getAllStorageCardTrafficsByPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        storageCardTrafficRepository.saveAndFlush(storageCardTraffic);

        // Get all the storageCardTrafficList where price equals to DEFAULT_PRICE
        defaultStorageCardTrafficShouldBeFound("price.equals=" + DEFAULT_PRICE);

        // Get all the storageCardTrafficList where price equals to UPDATED_PRICE
        defaultStorageCardTrafficShouldNotBeFound("price.equals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllStorageCardTrafficsByPriceIsInShouldWork() throws Exception {
        // Initialize the database
        storageCardTrafficRepository.saveAndFlush(storageCardTraffic);

        // Get all the storageCardTrafficList where price in DEFAULT_PRICE or UPDATED_PRICE
        defaultStorageCardTrafficShouldBeFound("price.in=" + DEFAULT_PRICE + "," + UPDATED_PRICE);

        // Get all the storageCardTrafficList where price equals to UPDATED_PRICE
        defaultStorageCardTrafficShouldNotBeFound("price.in=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllStorageCardTrafficsByPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        storageCardTrafficRepository.saveAndFlush(storageCardTraffic);

        // Get all the storageCardTrafficList where price is not null
        defaultStorageCardTrafficShouldBeFound("price.specified=true");

        // Get all the storageCardTrafficList where price is null
        defaultStorageCardTrafficShouldNotBeFound("price.specified=false");
    }

    @Test
    @Transactional
    void getAllStorageCardTrafficsByPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        storageCardTrafficRepository.saveAndFlush(storageCardTraffic);

        // Get all the storageCardTrafficList where price is greater than or equal to DEFAULT_PRICE
        defaultStorageCardTrafficShouldBeFound("price.greaterThanOrEqual=" + DEFAULT_PRICE);

        // Get all the storageCardTrafficList where price is greater than or equal to UPDATED_PRICE
        defaultStorageCardTrafficShouldNotBeFound("price.greaterThanOrEqual=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllStorageCardTrafficsByPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        storageCardTrafficRepository.saveAndFlush(storageCardTraffic);

        // Get all the storageCardTrafficList where price is less than or equal to DEFAULT_PRICE
        defaultStorageCardTrafficShouldBeFound("price.lessThanOrEqual=" + DEFAULT_PRICE);

        // Get all the storageCardTrafficList where price is less than or equal to SMALLER_PRICE
        defaultStorageCardTrafficShouldNotBeFound("price.lessThanOrEqual=" + SMALLER_PRICE);
    }

    @Test
    @Transactional
    void getAllStorageCardTrafficsByPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        storageCardTrafficRepository.saveAndFlush(storageCardTraffic);

        // Get all the storageCardTrafficList where price is less than DEFAULT_PRICE
        defaultStorageCardTrafficShouldNotBeFound("price.lessThan=" + DEFAULT_PRICE);

        // Get all the storageCardTrafficList where price is less than UPDATED_PRICE
        defaultStorageCardTrafficShouldBeFound("price.lessThan=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllStorageCardTrafficsByPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        storageCardTrafficRepository.saveAndFlush(storageCardTraffic);

        // Get all the storageCardTrafficList where price is greater than DEFAULT_PRICE
        defaultStorageCardTrafficShouldNotBeFound("price.greaterThan=" + DEFAULT_PRICE);

        // Get all the storageCardTrafficList where price is greater than SMALLER_PRICE
        defaultStorageCardTrafficShouldBeFound("price.greaterThan=" + SMALLER_PRICE);
    }

    @Test
    @Transactional
    void getAllStorageCardTrafficsByTrafficValueIsEqualToSomething() throws Exception {
        // Initialize the database
        storageCardTrafficRepository.saveAndFlush(storageCardTraffic);

        // Get all the storageCardTrafficList where trafficValue equals to DEFAULT_TRAFFIC_VALUE
        defaultStorageCardTrafficShouldBeFound("trafficValue.equals=" + DEFAULT_TRAFFIC_VALUE);

        // Get all the storageCardTrafficList where trafficValue equals to UPDATED_TRAFFIC_VALUE
        defaultStorageCardTrafficShouldNotBeFound("trafficValue.equals=" + UPDATED_TRAFFIC_VALUE);
    }

    @Test
    @Transactional
    void getAllStorageCardTrafficsByTrafficValueIsInShouldWork() throws Exception {
        // Initialize the database
        storageCardTrafficRepository.saveAndFlush(storageCardTraffic);

        // Get all the storageCardTrafficList where trafficValue in DEFAULT_TRAFFIC_VALUE or UPDATED_TRAFFIC_VALUE
        defaultStorageCardTrafficShouldBeFound("trafficValue.in=" + DEFAULT_TRAFFIC_VALUE + "," + UPDATED_TRAFFIC_VALUE);

        // Get all the storageCardTrafficList where trafficValue equals to UPDATED_TRAFFIC_VALUE
        defaultStorageCardTrafficShouldNotBeFound("trafficValue.in=" + UPDATED_TRAFFIC_VALUE);
    }

    @Test
    @Transactional
    void getAllStorageCardTrafficsByTrafficValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        storageCardTrafficRepository.saveAndFlush(storageCardTraffic);

        // Get all the storageCardTrafficList where trafficValue is not null
        defaultStorageCardTrafficShouldBeFound("trafficValue.specified=true");

        // Get all the storageCardTrafficList where trafficValue is null
        defaultStorageCardTrafficShouldNotBeFound("trafficValue.specified=false");
    }

    @Test
    @Transactional
    void getAllStorageCardTrafficsByTrafficValueIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        storageCardTrafficRepository.saveAndFlush(storageCardTraffic);

        // Get all the storageCardTrafficList where trafficValue is greater than or equal to DEFAULT_TRAFFIC_VALUE
        defaultStorageCardTrafficShouldBeFound("trafficValue.greaterThanOrEqual=" + DEFAULT_TRAFFIC_VALUE);

        // Get all the storageCardTrafficList where trafficValue is greater than or equal to UPDATED_TRAFFIC_VALUE
        defaultStorageCardTrafficShouldNotBeFound("trafficValue.greaterThanOrEqual=" + UPDATED_TRAFFIC_VALUE);
    }

    @Test
    @Transactional
    void getAllStorageCardTrafficsByTrafficValueIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        storageCardTrafficRepository.saveAndFlush(storageCardTraffic);

        // Get all the storageCardTrafficList where trafficValue is less than or equal to DEFAULT_TRAFFIC_VALUE
        defaultStorageCardTrafficShouldBeFound("trafficValue.lessThanOrEqual=" + DEFAULT_TRAFFIC_VALUE);

        // Get all the storageCardTrafficList where trafficValue is less than or equal to SMALLER_TRAFFIC_VALUE
        defaultStorageCardTrafficShouldNotBeFound("trafficValue.lessThanOrEqual=" + SMALLER_TRAFFIC_VALUE);
    }

    @Test
    @Transactional
    void getAllStorageCardTrafficsByTrafficValueIsLessThanSomething() throws Exception {
        // Initialize the database
        storageCardTrafficRepository.saveAndFlush(storageCardTraffic);

        // Get all the storageCardTrafficList where trafficValue is less than DEFAULT_TRAFFIC_VALUE
        defaultStorageCardTrafficShouldNotBeFound("trafficValue.lessThan=" + DEFAULT_TRAFFIC_VALUE);

        // Get all the storageCardTrafficList where trafficValue is less than UPDATED_TRAFFIC_VALUE
        defaultStorageCardTrafficShouldBeFound("trafficValue.lessThan=" + UPDATED_TRAFFIC_VALUE);
    }

    @Test
    @Transactional
    void getAllStorageCardTrafficsByTrafficValueIsGreaterThanSomething() throws Exception {
        // Initialize the database
        storageCardTrafficRepository.saveAndFlush(storageCardTraffic);

        // Get all the storageCardTrafficList where trafficValue is greater than DEFAULT_TRAFFIC_VALUE
        defaultStorageCardTrafficShouldNotBeFound("trafficValue.greaterThan=" + DEFAULT_TRAFFIC_VALUE);

        // Get all the storageCardTrafficList where trafficValue is greater than SMALLER_TRAFFIC_VALUE
        defaultStorageCardTrafficShouldBeFound("trafficValue.greaterThan=" + SMALLER_TRAFFIC_VALUE);
    }

    @Test
    @Transactional
    void getAllStorageCardTrafficsByDocumentIsEqualToSomething() throws Exception {
        // Initialize the database
        storageCardTrafficRepository.saveAndFlush(storageCardTraffic);

        // Get all the storageCardTrafficList where document equals to DEFAULT_DOCUMENT
        defaultStorageCardTrafficShouldBeFound("document.equals=" + DEFAULT_DOCUMENT);

        // Get all the storageCardTrafficList where document equals to UPDATED_DOCUMENT
        defaultStorageCardTrafficShouldNotBeFound("document.equals=" + UPDATED_DOCUMENT);
    }

    @Test
    @Transactional
    void getAllStorageCardTrafficsByDocumentIsInShouldWork() throws Exception {
        // Initialize the database
        storageCardTrafficRepository.saveAndFlush(storageCardTraffic);

        // Get all the storageCardTrafficList where document in DEFAULT_DOCUMENT or UPDATED_DOCUMENT
        defaultStorageCardTrafficShouldBeFound("document.in=" + DEFAULT_DOCUMENT + "," + UPDATED_DOCUMENT);

        // Get all the storageCardTrafficList where document equals to UPDATED_DOCUMENT
        defaultStorageCardTrafficShouldNotBeFound("document.in=" + UPDATED_DOCUMENT);
    }

    @Test
    @Transactional
    void getAllStorageCardTrafficsByDocumentIsNullOrNotNull() throws Exception {
        // Initialize the database
        storageCardTrafficRepository.saveAndFlush(storageCardTraffic);

        // Get all the storageCardTrafficList where document is not null
        defaultStorageCardTrafficShouldBeFound("document.specified=true");

        // Get all the storageCardTrafficList where document is null
        defaultStorageCardTrafficShouldNotBeFound("document.specified=false");
    }

    @Test
    @Transactional
    void getAllStorageCardTrafficsByDocumentContainsSomething() throws Exception {
        // Initialize the database
        storageCardTrafficRepository.saveAndFlush(storageCardTraffic);

        // Get all the storageCardTrafficList where document contains DEFAULT_DOCUMENT
        defaultStorageCardTrafficShouldBeFound("document.contains=" + DEFAULT_DOCUMENT);

        // Get all the storageCardTrafficList where document contains UPDATED_DOCUMENT
        defaultStorageCardTrafficShouldNotBeFound("document.contains=" + UPDATED_DOCUMENT);
    }

    @Test
    @Transactional
    void getAllStorageCardTrafficsByDocumentNotContainsSomething() throws Exception {
        // Initialize the database
        storageCardTrafficRepository.saveAndFlush(storageCardTraffic);

        // Get all the storageCardTrafficList where document does not contain DEFAULT_DOCUMENT
        defaultStorageCardTrafficShouldNotBeFound("document.doesNotContain=" + DEFAULT_DOCUMENT);

        // Get all the storageCardTrafficList where document does not contain UPDATED_DOCUMENT
        defaultStorageCardTrafficShouldBeFound("document.doesNotContain=" + UPDATED_DOCUMENT);
    }

    @Test
    @Transactional
    void getAllStorageCardTrafficsByDateIsEqualToSomething() throws Exception {
        // Initialize the database
        storageCardTrafficRepository.saveAndFlush(storageCardTraffic);

        // Get all the storageCardTrafficList where date equals to DEFAULT_DATE
        defaultStorageCardTrafficShouldBeFound("date.equals=" + DEFAULT_DATE);

        // Get all the storageCardTrafficList where date equals to UPDATED_DATE
        defaultStorageCardTrafficShouldNotBeFound("date.equals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllStorageCardTrafficsByDateIsInShouldWork() throws Exception {
        // Initialize the database
        storageCardTrafficRepository.saveAndFlush(storageCardTraffic);

        // Get all the storageCardTrafficList where date in DEFAULT_DATE or UPDATED_DATE
        defaultStorageCardTrafficShouldBeFound("date.in=" + DEFAULT_DATE + "," + UPDATED_DATE);

        // Get all the storageCardTrafficList where date equals to UPDATED_DATE
        defaultStorageCardTrafficShouldNotBeFound("date.in=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllStorageCardTrafficsByDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        storageCardTrafficRepository.saveAndFlush(storageCardTraffic);

        // Get all the storageCardTrafficList where date is not null
        defaultStorageCardTrafficShouldBeFound("date.specified=true");

        // Get all the storageCardTrafficList where date is null
        defaultStorageCardTrafficShouldNotBeFound("date.specified=false");
    }

    @Test
    @Transactional
    void getAllStorageCardTrafficsByDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        storageCardTrafficRepository.saveAndFlush(storageCardTraffic);

        // Get all the storageCardTrafficList where date is greater than or equal to DEFAULT_DATE
        defaultStorageCardTrafficShouldBeFound("date.greaterThanOrEqual=" + DEFAULT_DATE);

        // Get all the storageCardTrafficList where date is greater than or equal to UPDATED_DATE
        defaultStorageCardTrafficShouldNotBeFound("date.greaterThanOrEqual=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllStorageCardTrafficsByDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        storageCardTrafficRepository.saveAndFlush(storageCardTraffic);

        // Get all the storageCardTrafficList where date is less than or equal to DEFAULT_DATE
        defaultStorageCardTrafficShouldBeFound("date.lessThanOrEqual=" + DEFAULT_DATE);

        // Get all the storageCardTrafficList where date is less than or equal to SMALLER_DATE
        defaultStorageCardTrafficShouldNotBeFound("date.lessThanOrEqual=" + SMALLER_DATE);
    }

    @Test
    @Transactional
    void getAllStorageCardTrafficsByDateIsLessThanSomething() throws Exception {
        // Initialize the database
        storageCardTrafficRepository.saveAndFlush(storageCardTraffic);

        // Get all the storageCardTrafficList where date is less than DEFAULT_DATE
        defaultStorageCardTrafficShouldNotBeFound("date.lessThan=" + DEFAULT_DATE);

        // Get all the storageCardTrafficList where date is less than UPDATED_DATE
        defaultStorageCardTrafficShouldBeFound("date.lessThan=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllStorageCardTrafficsByDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        storageCardTrafficRepository.saveAndFlush(storageCardTraffic);

        // Get all the storageCardTrafficList where date is greater than DEFAULT_DATE
        defaultStorageCardTrafficShouldNotBeFound("date.greaterThan=" + DEFAULT_DATE);

        // Get all the storageCardTrafficList where date is greater than SMALLER_DATE
        defaultStorageCardTrafficShouldBeFound("date.greaterThan=" + SMALLER_DATE);
    }

    @Test
    @Transactional
    void getAllStorageCardTrafficsByStorageCardIsEqualToSomething() throws Exception {
        StorageCard storageCard;
        if (TestUtil.findAll(em, StorageCard.class).isEmpty()) {
            storageCardTrafficRepository.saveAndFlush(storageCardTraffic);
            storageCard = StorageCardResourceIT.createEntity(em);
        } else {
            storageCard = TestUtil.findAll(em, StorageCard.class).get(0);
        }
        em.persist(storageCard);
        em.flush();
        storageCardTraffic.setStorageCard(storageCard);
        storageCardTrafficRepository.saveAndFlush(storageCardTraffic);
        String storageCardId = storageCard.getId();

        // Get all the storageCardTrafficList where storageCard equals to storageCardId
        defaultStorageCardTrafficShouldBeFound("storageCardId.equals=" + storageCardId);

        // Get all the storageCardTrafficList where storageCard equals to "invalid-id"
        defaultStorageCardTrafficShouldNotBeFound("storageCardId.equals=" + "invalid-id");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultStorageCardTrafficShouldBeFound(String filter) throws Exception {
        restStorageCardTrafficMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(storageCardTraffic.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].direction").value(hasItem(DEFAULT_DIRECTION.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].trafficValue").value(hasItem(DEFAULT_TRAFFIC_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].document").value(hasItem(DEFAULT_DOCUMENT)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));

        // Check, that the count call also returns 1
        restStorageCardTrafficMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultStorageCardTrafficShouldNotBeFound(String filter) throws Exception {
        restStorageCardTrafficMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restStorageCardTrafficMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingStorageCardTraffic() throws Exception {
        // Get the storageCardTraffic
        restStorageCardTrafficMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingStorageCardTraffic() throws Exception {
        // Initialize the database
        storageCardTrafficRepository.saveAndFlush(storageCardTraffic);

        int databaseSizeBeforeUpdate = storageCardTrafficRepository.findAll().size();

        // Update the storageCardTraffic
        StorageCardTraffic updatedStorageCardTraffic = storageCardTrafficRepository.findById(storageCardTraffic.getId()).get();
        // Disconnect from session so that the updates on updatedStorageCardTraffic are not directly saved in db
        em.detach(updatedStorageCardTraffic);
        updatedStorageCardTraffic
            .type(UPDATED_TYPE)
            .direction(UPDATED_DIRECTION)
            .amount(UPDATED_AMOUNT)
            .price(UPDATED_PRICE)
            .trafficValue(UPDATED_TRAFFIC_VALUE)
            .document(UPDATED_DOCUMENT)
            .date(UPDATED_DATE);
        StorageCardTrafficDTO storageCardTrafficDTO = storageCardTrafficMapper.toDto(updatedStorageCardTraffic);

        restStorageCardTrafficMockMvc
            .perform(
                put(ENTITY_API_URL_ID, storageCardTrafficDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(storageCardTrafficDTO))
            )
            .andExpect(status().isOk());

        // Validate the StorageCardTraffic in the database
        List<StorageCardTraffic> storageCardTrafficList = storageCardTrafficRepository.findAll();
        assertThat(storageCardTrafficList).hasSize(databaseSizeBeforeUpdate);
        StorageCardTraffic testStorageCardTraffic = storageCardTrafficList.get(storageCardTrafficList.size() - 1);
        assertThat(testStorageCardTraffic.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testStorageCardTraffic.getDirection()).isEqualTo(UPDATED_DIRECTION);
        assertThat(testStorageCardTraffic.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testStorageCardTraffic.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testStorageCardTraffic.getTrafficValue()).isEqualTo(UPDATED_TRAFFIC_VALUE);
        assertThat(testStorageCardTraffic.getDocument()).isEqualTo(UPDATED_DOCUMENT);
        assertThat(testStorageCardTraffic.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingStorageCardTraffic() throws Exception {
        int databaseSizeBeforeUpdate = storageCardTrafficRepository.findAll().size();
        storageCardTraffic.setId(count.incrementAndGet());

        // Create the StorageCardTraffic
        StorageCardTrafficDTO storageCardTrafficDTO = storageCardTrafficMapper.toDto(storageCardTraffic);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStorageCardTrafficMockMvc
            .perform(
                put(ENTITY_API_URL_ID, storageCardTrafficDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(storageCardTrafficDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StorageCardTraffic in the database
        List<StorageCardTraffic> storageCardTrafficList = storageCardTrafficRepository.findAll();
        assertThat(storageCardTrafficList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStorageCardTraffic() throws Exception {
        int databaseSizeBeforeUpdate = storageCardTrafficRepository.findAll().size();
        storageCardTraffic.setId(count.incrementAndGet());

        // Create the StorageCardTraffic
        StorageCardTrafficDTO storageCardTrafficDTO = storageCardTrafficMapper.toDto(storageCardTraffic);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStorageCardTrafficMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(storageCardTrafficDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StorageCardTraffic in the database
        List<StorageCardTraffic> storageCardTrafficList = storageCardTrafficRepository.findAll();
        assertThat(storageCardTrafficList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStorageCardTraffic() throws Exception {
        int databaseSizeBeforeUpdate = storageCardTrafficRepository.findAll().size();
        storageCardTraffic.setId(count.incrementAndGet());

        // Create the StorageCardTraffic
        StorageCardTrafficDTO storageCardTrafficDTO = storageCardTrafficMapper.toDto(storageCardTraffic);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStorageCardTrafficMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(storageCardTrafficDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the StorageCardTraffic in the database
        List<StorageCardTraffic> storageCardTrafficList = storageCardTrafficRepository.findAll();
        assertThat(storageCardTrafficList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStorageCardTrafficWithPatch() throws Exception {
        // Initialize the database
        storageCardTrafficRepository.saveAndFlush(storageCardTraffic);

        int databaseSizeBeforeUpdate = storageCardTrafficRepository.findAll().size();

        // Update the storageCardTraffic using partial update
        StorageCardTraffic partialUpdatedStorageCardTraffic = new StorageCardTraffic();
        partialUpdatedStorageCardTraffic.setId(storageCardTraffic.getId());

        partialUpdatedStorageCardTraffic
            .type(UPDATED_TYPE)
            .direction(UPDATED_DIRECTION)
            .price(UPDATED_PRICE)
            .trafficValue(UPDATED_TRAFFIC_VALUE)
            .document(UPDATED_DOCUMENT)
            .date(UPDATED_DATE);

        restStorageCardTrafficMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStorageCardTraffic.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStorageCardTraffic))
            )
            .andExpect(status().isOk());

        // Validate the StorageCardTraffic in the database
        List<StorageCardTraffic> storageCardTrafficList = storageCardTrafficRepository.findAll();
        assertThat(storageCardTrafficList).hasSize(databaseSizeBeforeUpdate);
        StorageCardTraffic testStorageCardTraffic = storageCardTrafficList.get(storageCardTrafficList.size() - 1);
        assertThat(testStorageCardTraffic.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testStorageCardTraffic.getDirection()).isEqualTo(UPDATED_DIRECTION);
        assertThat(testStorageCardTraffic.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testStorageCardTraffic.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testStorageCardTraffic.getTrafficValue()).isEqualTo(UPDATED_TRAFFIC_VALUE);
        assertThat(testStorageCardTraffic.getDocument()).isEqualTo(UPDATED_DOCUMENT);
        assertThat(testStorageCardTraffic.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateStorageCardTrafficWithPatch() throws Exception {
        // Initialize the database
        storageCardTrafficRepository.saveAndFlush(storageCardTraffic);

        int databaseSizeBeforeUpdate = storageCardTrafficRepository.findAll().size();

        // Update the storageCardTraffic using partial update
        StorageCardTraffic partialUpdatedStorageCardTraffic = new StorageCardTraffic();
        partialUpdatedStorageCardTraffic.setId(storageCardTraffic.getId());

        partialUpdatedStorageCardTraffic
            .type(UPDATED_TYPE)
            .direction(UPDATED_DIRECTION)
            .amount(UPDATED_AMOUNT)
            .price(UPDATED_PRICE)
            .trafficValue(UPDATED_TRAFFIC_VALUE)
            .document(UPDATED_DOCUMENT)
            .date(UPDATED_DATE);

        restStorageCardTrafficMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStorageCardTraffic.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStorageCardTraffic))
            )
            .andExpect(status().isOk());

        // Validate the StorageCardTraffic in the database
        List<StorageCardTraffic> storageCardTrafficList = storageCardTrafficRepository.findAll();
        assertThat(storageCardTrafficList).hasSize(databaseSizeBeforeUpdate);
        StorageCardTraffic testStorageCardTraffic = storageCardTrafficList.get(storageCardTrafficList.size() - 1);
        assertThat(testStorageCardTraffic.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testStorageCardTraffic.getDirection()).isEqualTo(UPDATED_DIRECTION);
        assertThat(testStorageCardTraffic.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testStorageCardTraffic.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testStorageCardTraffic.getTrafficValue()).isEqualTo(UPDATED_TRAFFIC_VALUE);
        assertThat(testStorageCardTraffic.getDocument()).isEqualTo(UPDATED_DOCUMENT);
        assertThat(testStorageCardTraffic.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingStorageCardTraffic() throws Exception {
        int databaseSizeBeforeUpdate = storageCardTrafficRepository.findAll().size();
        storageCardTraffic.setId(count.incrementAndGet());

        // Create the StorageCardTraffic
        StorageCardTrafficDTO storageCardTrafficDTO = storageCardTrafficMapper.toDto(storageCardTraffic);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStorageCardTrafficMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, storageCardTrafficDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(storageCardTrafficDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StorageCardTraffic in the database
        List<StorageCardTraffic> storageCardTrafficList = storageCardTrafficRepository.findAll();
        assertThat(storageCardTrafficList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStorageCardTraffic() throws Exception {
        int databaseSizeBeforeUpdate = storageCardTrafficRepository.findAll().size();
        storageCardTraffic.setId(count.incrementAndGet());

        // Create the StorageCardTraffic
        StorageCardTrafficDTO storageCardTrafficDTO = storageCardTrafficMapper.toDto(storageCardTraffic);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStorageCardTrafficMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(storageCardTrafficDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StorageCardTraffic in the database
        List<StorageCardTraffic> storageCardTrafficList = storageCardTrafficRepository.findAll();
        assertThat(storageCardTrafficList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStorageCardTraffic() throws Exception {
        int databaseSizeBeforeUpdate = storageCardTrafficRepository.findAll().size();
        storageCardTraffic.setId(count.incrementAndGet());

        // Create the StorageCardTraffic
        StorageCardTrafficDTO storageCardTrafficDTO = storageCardTrafficMapper.toDto(storageCardTraffic);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStorageCardTrafficMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(storageCardTrafficDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the StorageCardTraffic in the database
        List<StorageCardTraffic> storageCardTrafficList = storageCardTrafficRepository.findAll();
        assertThat(storageCardTrafficList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStorageCardTraffic() throws Exception {
        // Initialize the database
        storageCardTrafficRepository.saveAndFlush(storageCardTraffic);

        int databaseSizeBeforeDelete = storageCardTrafficRepository.findAll().size();

        // Delete the storageCardTraffic
        restStorageCardTrafficMockMvc
            .perform(delete(ENTITY_API_URL_ID, storageCardTraffic.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StorageCardTraffic> storageCardTrafficList = storageCardTrafficRepository.findAll();
        assertThat(storageCardTrafficList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
