package inc.donau.storage.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import inc.donau.storage.IntegrationTest;
import inc.donau.storage.domain.Address;
import inc.donau.storage.domain.CensusDocument;
import inc.donau.storage.domain.Company;
import inc.donau.storage.domain.Storage;
import inc.donau.storage.domain.StorageCard;
import inc.donau.storage.domain.TransferDocument;
import inc.donau.storage.repository.StorageRepository;
import inc.donau.storage.service.criteria.StorageCriteria;
import inc.donau.storage.service.dto.StorageDTO;
import inc.donau.storage.service.mapper.StorageMapper;
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
 * Integration tests for the {@link StorageResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class StorageResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/storages";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private StorageRepository storageRepository;

    @Autowired
    private StorageMapper storageMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStorageMockMvc;

    private Storage storage;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Storage createEntity(EntityManager em) {
        Storage storage = new Storage().code(DEFAULT_CODE).name(DEFAULT_NAME);
        // Add required entity
        Address address;
        if (TestUtil.findAll(em, Address.class).isEmpty()) {
            address = AddressResourceIT.createEntity(em);
            em.persist(address);
            em.flush();
        } else {
            address = TestUtil.findAll(em, Address.class).get(0);
        }
        storage.setAddress(address);
        // Add required entity
        Company company;
        if (TestUtil.findAll(em, Company.class).isEmpty()) {
            company = CompanyResourceIT.createEntity(em);
            em.persist(company);
            em.flush();
        } else {
            company = TestUtil.findAll(em, Company.class).get(0);
        }
        storage.setCompany(company);
        return storage;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Storage createUpdatedEntity(EntityManager em) {
        Storage storage = new Storage().code(UPDATED_CODE).name(UPDATED_NAME);
        // Add required entity
        Address address;
        if (TestUtil.findAll(em, Address.class).isEmpty()) {
            address = AddressResourceIT.createUpdatedEntity(em);
            em.persist(address);
            em.flush();
        } else {
            address = TestUtil.findAll(em, Address.class).get(0);
        }
        storage.setAddress(address);
        // Add required entity
        Company company;
        if (TestUtil.findAll(em, Company.class).isEmpty()) {
            company = CompanyResourceIT.createUpdatedEntity(em);
            em.persist(company);
            em.flush();
        } else {
            company = TestUtil.findAll(em, Company.class).get(0);
        }
        storage.setCompany(company);
        return storage;
    }

    @BeforeEach
    public void initTest() {
        storage = createEntity(em);
    }

    @Test
    @Transactional
    void createStorage() throws Exception {
        int databaseSizeBeforeCreate = storageRepository.findAll().size();
        // Create the Storage
        StorageDTO storageDTO = storageMapper.toDto(storage);
        restStorageMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(storageDTO)))
            .andExpect(status().isCreated());

        // Validate the Storage in the database
        List<Storage> storageList = storageRepository.findAll();
        assertThat(storageList).hasSize(databaseSizeBeforeCreate + 1);
        Storage testStorage = storageList.get(storageList.size() - 1);
        assertThat(testStorage.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testStorage.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createStorageWithExistingId() throws Exception {
        // Create the Storage with an existing ID
        storage.setId(1L);
        StorageDTO storageDTO = storageMapper.toDto(storage);

        int databaseSizeBeforeCreate = storageRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStorageMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(storageDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Storage in the database
        List<Storage> storageList = storageRepository.findAll();
        assertThat(storageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = storageRepository.findAll().size();
        // set the field null
        storage.setCode(null);

        // Create the Storage, which fails.
        StorageDTO storageDTO = storageMapper.toDto(storage);

        restStorageMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(storageDTO)))
            .andExpect(status().isBadRequest());

        List<Storage> storageList = storageRepository.findAll();
        assertThat(storageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = storageRepository.findAll().size();
        // set the field null
        storage.setName(null);

        // Create the Storage, which fails.
        StorageDTO storageDTO = storageMapper.toDto(storage);

        restStorageMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(storageDTO)))
            .andExpect(status().isBadRequest());

        List<Storage> storageList = storageRepository.findAll();
        assertThat(storageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllStorages() throws Exception {
        // Initialize the database
        storageRepository.saveAndFlush(storage);

        // Get all the storageList
        restStorageMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(storage.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getStorage() throws Exception {
        // Initialize the database
        storageRepository.saveAndFlush(storage);

        // Get the storage
        restStorageMockMvc
            .perform(get(ENTITY_API_URL_ID, storage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(storage.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getStoragesByIdFiltering() throws Exception {
        // Initialize the database
        storageRepository.saveAndFlush(storage);

        Long id = storage.getId();

        defaultStorageShouldBeFound("id.equals=" + id);
        defaultStorageShouldNotBeFound("id.notEquals=" + id);

        defaultStorageShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultStorageShouldNotBeFound("id.greaterThan=" + id);

        defaultStorageShouldBeFound("id.lessThanOrEqual=" + id);
        defaultStorageShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllStoragesByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        storageRepository.saveAndFlush(storage);

        // Get all the storageList where code equals to DEFAULT_CODE
        defaultStorageShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the storageList where code equals to UPDATED_CODE
        defaultStorageShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllStoragesByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        storageRepository.saveAndFlush(storage);

        // Get all the storageList where code in DEFAULT_CODE or UPDATED_CODE
        defaultStorageShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the storageList where code equals to UPDATED_CODE
        defaultStorageShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllStoragesByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        storageRepository.saveAndFlush(storage);

        // Get all the storageList where code is not null
        defaultStorageShouldBeFound("code.specified=true");

        // Get all the storageList where code is null
        defaultStorageShouldNotBeFound("code.specified=false");
    }

    @Test
    @Transactional
    void getAllStoragesByCodeContainsSomething() throws Exception {
        // Initialize the database
        storageRepository.saveAndFlush(storage);

        // Get all the storageList where code contains DEFAULT_CODE
        defaultStorageShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the storageList where code contains UPDATED_CODE
        defaultStorageShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllStoragesByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        storageRepository.saveAndFlush(storage);

        // Get all the storageList where code does not contain DEFAULT_CODE
        defaultStorageShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the storageList where code does not contain UPDATED_CODE
        defaultStorageShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllStoragesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        storageRepository.saveAndFlush(storage);

        // Get all the storageList where name equals to DEFAULT_NAME
        defaultStorageShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the storageList where name equals to UPDATED_NAME
        defaultStorageShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllStoragesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        storageRepository.saveAndFlush(storage);

        // Get all the storageList where name in DEFAULT_NAME or UPDATED_NAME
        defaultStorageShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the storageList where name equals to UPDATED_NAME
        defaultStorageShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllStoragesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        storageRepository.saveAndFlush(storage);

        // Get all the storageList where name is not null
        defaultStorageShouldBeFound("name.specified=true");

        // Get all the storageList where name is null
        defaultStorageShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllStoragesByNameContainsSomething() throws Exception {
        // Initialize the database
        storageRepository.saveAndFlush(storage);

        // Get all the storageList where name contains DEFAULT_NAME
        defaultStorageShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the storageList where name contains UPDATED_NAME
        defaultStorageShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllStoragesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        storageRepository.saveAndFlush(storage);

        // Get all the storageList where name does not contain DEFAULT_NAME
        defaultStorageShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the storageList where name does not contain UPDATED_NAME
        defaultStorageShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllStoragesByAddressIsEqualToSomething() throws Exception {
        // Get already existing entity
        Address address = storage.getAddress();
        storageRepository.saveAndFlush(storage);
        Long addressId = address.getId();

        // Get all the storageList where address equals to addressId
        defaultStorageShouldBeFound("addressId.equals=" + addressId);

        // Get all the storageList where address equals to (addressId + 1)
        defaultStorageShouldNotBeFound("addressId.equals=" + (addressId + 1));
    }

    @Test
    @Transactional
    void getAllStoragesByStorageCardsIsEqualToSomething() throws Exception {
        StorageCard storageCards;
        if (TestUtil.findAll(em, StorageCard.class).isEmpty()) {
            storageRepository.saveAndFlush(storage);
            storageCards = StorageCardResourceIT.createEntity(em);
        } else {
            storageCards = TestUtil.findAll(em, StorageCard.class).get(0);
        }
        em.persist(storageCards);
        em.flush();
        storage.addStorageCards(storageCards);
        storageRepository.saveAndFlush(storage);
        String storageCardsId = storageCards.getId();

        // Get all the storageList where storageCards equals to storageCardsId
        defaultStorageShouldBeFound("storageCardsId.equals=" + storageCardsId);

        // Get all the storageList where storageCards equals to "invalid-id"
        defaultStorageShouldNotBeFound("storageCardsId.equals=" + "invalid-id");
    }

    @Test
    @Transactional
    void getAllStoragesByReceivedIsEqualToSomething() throws Exception {
        TransferDocument received;
        if (TestUtil.findAll(em, TransferDocument.class).isEmpty()) {
            storageRepository.saveAndFlush(storage);
            received = TransferDocumentResourceIT.createEntity(em);
        } else {
            received = TestUtil.findAll(em, TransferDocument.class).get(0);
        }
        em.persist(received);
        em.flush();
        storage.addReceived(received);
        storageRepository.saveAndFlush(storage);
        Long receivedId = received.getId();

        // Get all the storageList where received equals to receivedId
        defaultStorageShouldBeFound("receivedId.equals=" + receivedId);

        // Get all the storageList where received equals to (receivedId + 1)
        defaultStorageShouldNotBeFound("receivedId.equals=" + (receivedId + 1));
    }

    @Test
    @Transactional
    void getAllStoragesByDispatchedIsEqualToSomething() throws Exception {
        TransferDocument dispatched;
        if (TestUtil.findAll(em, TransferDocument.class).isEmpty()) {
            storageRepository.saveAndFlush(storage);
            dispatched = TransferDocumentResourceIT.createEntity(em);
        } else {
            dispatched = TestUtil.findAll(em, TransferDocument.class).get(0);
        }
        em.persist(dispatched);
        em.flush();
        storage.addDispatched(dispatched);
        storageRepository.saveAndFlush(storage);
        Long dispatchedId = dispatched.getId();

        // Get all the storageList where dispatched equals to dispatchedId
        defaultStorageShouldBeFound("dispatchedId.equals=" + dispatchedId);

        // Get all the storageList where dispatched equals to (dispatchedId + 1)
        defaultStorageShouldNotBeFound("dispatchedId.equals=" + (dispatchedId + 1));
    }

    @Test
    @Transactional
    void getAllStoragesByCensusDocumentIsEqualToSomething() throws Exception {
        CensusDocument censusDocument;
        if (TestUtil.findAll(em, CensusDocument.class).isEmpty()) {
            storageRepository.saveAndFlush(storage);
            censusDocument = CensusDocumentResourceIT.createEntity(em);
        } else {
            censusDocument = TestUtil.findAll(em, CensusDocument.class).get(0);
        }
        em.persist(censusDocument);
        em.flush();
        storage.addCensusDocument(censusDocument);
        storageRepository.saveAndFlush(storage);
        Long censusDocumentId = censusDocument.getId();

        // Get all the storageList where censusDocument equals to censusDocumentId
        defaultStorageShouldBeFound("censusDocumentId.equals=" + censusDocumentId);

        // Get all the storageList where censusDocument equals to (censusDocumentId + 1)
        defaultStorageShouldNotBeFound("censusDocumentId.equals=" + (censusDocumentId + 1));
    }

    @Test
    @Transactional
    void getAllStoragesByCompanyIsEqualToSomething() throws Exception {
        Company company;
        if (TestUtil.findAll(em, Company.class).isEmpty()) {
            storageRepository.saveAndFlush(storage);
            company = CompanyResourceIT.createEntity(em);
        } else {
            company = TestUtil.findAll(em, Company.class).get(0);
        }
        em.persist(company);
        em.flush();
        storage.setCompany(company);
        storageRepository.saveAndFlush(storage);
        Long companyId = company.getId();

        // Get all the storageList where company equals to companyId
        defaultStorageShouldBeFound("companyId.equals=" + companyId);

        // Get all the storageList where company equals to (companyId + 1)
        defaultStorageShouldNotBeFound("companyId.equals=" + (companyId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultStorageShouldBeFound(String filter) throws Exception {
        restStorageMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(storage.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restStorageMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultStorageShouldNotBeFound(String filter) throws Exception {
        restStorageMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restStorageMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingStorage() throws Exception {
        // Get the storage
        restStorageMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingStorage() throws Exception {
        // Initialize the database
        storageRepository.saveAndFlush(storage);

        int databaseSizeBeforeUpdate = storageRepository.findAll().size();

        // Update the storage
        Storage updatedStorage = storageRepository.findById(storage.getId()).get();
        // Disconnect from session so that the updates on updatedStorage are not directly saved in db
        em.detach(updatedStorage);
        updatedStorage.code(UPDATED_CODE).name(UPDATED_NAME);
        StorageDTO storageDTO = storageMapper.toDto(updatedStorage);

        restStorageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, storageDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(storageDTO))
            )
            .andExpect(status().isOk());

        // Validate the Storage in the database
        List<Storage> storageList = storageRepository.findAll();
        assertThat(storageList).hasSize(databaseSizeBeforeUpdate);
        Storage testStorage = storageList.get(storageList.size() - 1);
        assertThat(testStorage.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testStorage.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingStorage() throws Exception {
        int databaseSizeBeforeUpdate = storageRepository.findAll().size();
        storage.setId(count.incrementAndGet());

        // Create the Storage
        StorageDTO storageDTO = storageMapper.toDto(storage);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStorageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, storageDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(storageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Storage in the database
        List<Storage> storageList = storageRepository.findAll();
        assertThat(storageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStorage() throws Exception {
        int databaseSizeBeforeUpdate = storageRepository.findAll().size();
        storage.setId(count.incrementAndGet());

        // Create the Storage
        StorageDTO storageDTO = storageMapper.toDto(storage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStorageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(storageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Storage in the database
        List<Storage> storageList = storageRepository.findAll();
        assertThat(storageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStorage() throws Exception {
        int databaseSizeBeforeUpdate = storageRepository.findAll().size();
        storage.setId(count.incrementAndGet());

        // Create the Storage
        StorageDTO storageDTO = storageMapper.toDto(storage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStorageMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(storageDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Storage in the database
        List<Storage> storageList = storageRepository.findAll();
        assertThat(storageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStorageWithPatch() throws Exception {
        // Initialize the database
        storageRepository.saveAndFlush(storage);

        int databaseSizeBeforeUpdate = storageRepository.findAll().size();

        // Update the storage using partial update
        Storage partialUpdatedStorage = new Storage();
        partialUpdatedStorage.setId(storage.getId());

        partialUpdatedStorage.name(UPDATED_NAME);

        restStorageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStorage.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStorage))
            )
            .andExpect(status().isOk());

        // Validate the Storage in the database
        List<Storage> storageList = storageRepository.findAll();
        assertThat(storageList).hasSize(databaseSizeBeforeUpdate);
        Storage testStorage = storageList.get(storageList.size() - 1);
        assertThat(testStorage.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testStorage.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void fullUpdateStorageWithPatch() throws Exception {
        // Initialize the database
        storageRepository.saveAndFlush(storage);

        int databaseSizeBeforeUpdate = storageRepository.findAll().size();

        // Update the storage using partial update
        Storage partialUpdatedStorage = new Storage();
        partialUpdatedStorage.setId(storage.getId());

        partialUpdatedStorage.code(UPDATED_CODE).name(UPDATED_NAME);

        restStorageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStorage.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStorage))
            )
            .andExpect(status().isOk());

        // Validate the Storage in the database
        List<Storage> storageList = storageRepository.findAll();
        assertThat(storageList).hasSize(databaseSizeBeforeUpdate);
        Storage testStorage = storageList.get(storageList.size() - 1);
        assertThat(testStorage.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testStorage.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingStorage() throws Exception {
        int databaseSizeBeforeUpdate = storageRepository.findAll().size();
        storage.setId(count.incrementAndGet());

        // Create the Storage
        StorageDTO storageDTO = storageMapper.toDto(storage);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStorageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, storageDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(storageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Storage in the database
        List<Storage> storageList = storageRepository.findAll();
        assertThat(storageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStorage() throws Exception {
        int databaseSizeBeforeUpdate = storageRepository.findAll().size();
        storage.setId(count.incrementAndGet());

        // Create the Storage
        StorageDTO storageDTO = storageMapper.toDto(storage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStorageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(storageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Storage in the database
        List<Storage> storageList = storageRepository.findAll();
        assertThat(storageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStorage() throws Exception {
        int databaseSizeBeforeUpdate = storageRepository.findAll().size();
        storage.setId(count.incrementAndGet());

        // Create the Storage
        StorageDTO storageDTO = storageMapper.toDto(storage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStorageMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(storageDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Storage in the database
        List<Storage> storageList = storageRepository.findAll();
        assertThat(storageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStorage() throws Exception {
        // Initialize the database
        storageRepository.saveAndFlush(storage);

        int databaseSizeBeforeDelete = storageRepository.findAll().size();

        // Delete the storage
        restStorageMockMvc
            .perform(delete(ENTITY_API_URL_ID, storage.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Storage> storageList = storageRepository.findAll();
        assertThat(storageList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
