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

    private static final Float DEFAULT_PRICE = 0F;
    private static final Float UPDATED_PRICE = 1F;

    private static final Float DEFAULT_TRAFFIC_VALUE = 1F;
    private static final Float UPDATED_TRAFFIC_VALUE = 2F;

    private static final String DEFAULT_DOCUMENT = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENT = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

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
