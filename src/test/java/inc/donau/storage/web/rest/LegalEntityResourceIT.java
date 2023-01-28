package inc.donau.storage.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import inc.donau.storage.IntegrationTest;
import inc.donau.storage.domain.Address;
import inc.donau.storage.domain.ContactInfo;
import inc.donau.storage.domain.LegalEntity;
import inc.donau.storage.repository.LegalEntityRepository;
import inc.donau.storage.service.dto.LegalEntityDTO;
import inc.donau.storage.service.mapper.LegalEntityMapper;
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
 * Integration tests for the {@link LegalEntityResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LegalEntityResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TAX_IDENTIFICATION_NUMBER = "6654163373";
    private static final String UPDATED_TAX_IDENTIFICATION_NUMBER = "9548082697";

    private static final String DEFAULT_IDENTIFICATION_NUMBER = "77354511";
    private static final String UPDATED_IDENTIFICATION_NUMBER = "64491748";

    private static final String ENTITY_API_URL = "/api/legal-entities";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LegalEntityRepository legalEntityRepository;

    @Autowired
    private LegalEntityMapper legalEntityMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLegalEntityMockMvc;

    private LegalEntity legalEntity;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LegalEntity createEntity(EntityManager em) {
        LegalEntity legalEntity = new LegalEntity()
            .name(DEFAULT_NAME)
            .taxIdentificationNumber(DEFAULT_TAX_IDENTIFICATION_NUMBER)
            .identificationNumber(DEFAULT_IDENTIFICATION_NUMBER);
        // Add required entity
        ContactInfo contactInfo;
        if (TestUtil.findAll(em, ContactInfo.class).isEmpty()) {
            contactInfo = ContactInfoResourceIT.createEntity(em);
            em.persist(contactInfo);
            em.flush();
        } else {
            contactInfo = TestUtil.findAll(em, ContactInfo.class).get(0);
        }
        legalEntity.setContactInfo(contactInfo);
        // Add required entity
        Address address;
        if (TestUtil.findAll(em, Address.class).isEmpty()) {
            address = AddressResourceIT.createEntity(em);
            em.persist(address);
            em.flush();
        } else {
            address = TestUtil.findAll(em, Address.class).get(0);
        }
        legalEntity.setAddress(address);
        return legalEntity;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LegalEntity createUpdatedEntity(EntityManager em) {
        LegalEntity legalEntity = new LegalEntity()
            .name(UPDATED_NAME)
            .taxIdentificationNumber(UPDATED_TAX_IDENTIFICATION_NUMBER)
            .identificationNumber(UPDATED_IDENTIFICATION_NUMBER);
        // Add required entity
        ContactInfo contactInfo;
        if (TestUtil.findAll(em, ContactInfo.class).isEmpty()) {
            contactInfo = ContactInfoResourceIT.createUpdatedEntity(em);
            em.persist(contactInfo);
            em.flush();
        } else {
            contactInfo = TestUtil.findAll(em, ContactInfo.class).get(0);
        }
        legalEntity.setContactInfo(contactInfo);
        // Add required entity
        Address address;
        if (TestUtil.findAll(em, Address.class).isEmpty()) {
            address = AddressResourceIT.createUpdatedEntity(em);
            em.persist(address);
            em.flush();
        } else {
            address = TestUtil.findAll(em, Address.class).get(0);
        }
        legalEntity.setAddress(address);
        return legalEntity;
    }

    @BeforeEach
    public void initTest() {
        legalEntity = createEntity(em);
    }

    @Test
    @Transactional
    void createLegalEntity() throws Exception {
        int databaseSizeBeforeCreate = legalEntityRepository.findAll().size();
        // Create the LegalEntity
        LegalEntityDTO legalEntityDTO = legalEntityMapper.toDto(legalEntity);
        restLegalEntityMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(legalEntityDTO))
            )
            .andExpect(status().isCreated());

        // Validate the LegalEntity in the database
        List<LegalEntity> legalEntityList = legalEntityRepository.findAll();
        assertThat(legalEntityList).hasSize(databaseSizeBeforeCreate + 1);
        LegalEntity testLegalEntity = legalEntityList.get(legalEntityList.size() - 1);
        assertThat(testLegalEntity.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testLegalEntity.getTaxIdentificationNumber()).isEqualTo(DEFAULT_TAX_IDENTIFICATION_NUMBER);
        assertThat(testLegalEntity.getIdentificationNumber()).isEqualTo(DEFAULT_IDENTIFICATION_NUMBER);
    }

    @Test
    @Transactional
    void createLegalEntityWithExistingId() throws Exception {
        // Create the LegalEntity with an existing ID
        legalEntity.setId(1L);
        LegalEntityDTO legalEntityDTO = legalEntityMapper.toDto(legalEntity);

        int databaseSizeBeforeCreate = legalEntityRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLegalEntityMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(legalEntityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LegalEntity in the database
        List<LegalEntity> legalEntityList = legalEntityRepository.findAll();
        assertThat(legalEntityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = legalEntityRepository.findAll().size();
        // set the field null
        legalEntity.setName(null);

        // Create the LegalEntity, which fails.
        LegalEntityDTO legalEntityDTO = legalEntityMapper.toDto(legalEntity);

        restLegalEntityMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(legalEntityDTO))
            )
            .andExpect(status().isBadRequest());

        List<LegalEntity> legalEntityList = legalEntityRepository.findAll();
        assertThat(legalEntityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTaxIdentificationNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = legalEntityRepository.findAll().size();
        // set the field null
        legalEntity.setTaxIdentificationNumber(null);

        // Create the LegalEntity, which fails.
        LegalEntityDTO legalEntityDTO = legalEntityMapper.toDto(legalEntity);

        restLegalEntityMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(legalEntityDTO))
            )
            .andExpect(status().isBadRequest());

        List<LegalEntity> legalEntityList = legalEntityRepository.findAll();
        assertThat(legalEntityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIdentificationNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = legalEntityRepository.findAll().size();
        // set the field null
        legalEntity.setIdentificationNumber(null);

        // Create the LegalEntity, which fails.
        LegalEntityDTO legalEntityDTO = legalEntityMapper.toDto(legalEntity);

        restLegalEntityMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(legalEntityDTO))
            )
            .andExpect(status().isBadRequest());

        List<LegalEntity> legalEntityList = legalEntityRepository.findAll();
        assertThat(legalEntityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllLegalEntities() throws Exception {
        // Initialize the database
        legalEntityRepository.saveAndFlush(legalEntity);

        // Get all the legalEntityList
        restLegalEntityMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(legalEntity.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].taxIdentificationNumber").value(hasItem(DEFAULT_TAX_IDENTIFICATION_NUMBER)))
            .andExpect(jsonPath("$.[*].identificationNumber").value(hasItem(DEFAULT_IDENTIFICATION_NUMBER)));
    }

    @Test
    @Transactional
    void getLegalEntity() throws Exception {
        // Initialize the database
        legalEntityRepository.saveAndFlush(legalEntity);

        // Get the legalEntity
        restLegalEntityMockMvc
            .perform(get(ENTITY_API_URL_ID, legalEntity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(legalEntity.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.taxIdentificationNumber").value(DEFAULT_TAX_IDENTIFICATION_NUMBER))
            .andExpect(jsonPath("$.identificationNumber").value(DEFAULT_IDENTIFICATION_NUMBER));
    }

    @Test
    @Transactional
    void getNonExistingLegalEntity() throws Exception {
        // Get the legalEntity
        restLegalEntityMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLegalEntity() throws Exception {
        // Initialize the database
        legalEntityRepository.saveAndFlush(legalEntity);

        int databaseSizeBeforeUpdate = legalEntityRepository.findAll().size();

        // Update the legalEntity
        LegalEntity updatedLegalEntity = legalEntityRepository.findById(legalEntity.getId()).get();
        // Disconnect from session so that the updates on updatedLegalEntity are not directly saved in db
        em.detach(updatedLegalEntity);
        updatedLegalEntity
            .name(UPDATED_NAME)
            .taxIdentificationNumber(UPDATED_TAX_IDENTIFICATION_NUMBER)
            .identificationNumber(UPDATED_IDENTIFICATION_NUMBER);
        LegalEntityDTO legalEntityDTO = legalEntityMapper.toDto(updatedLegalEntity);

        restLegalEntityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, legalEntityDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(legalEntityDTO))
            )
            .andExpect(status().isOk());

        // Validate the LegalEntity in the database
        List<LegalEntity> legalEntityList = legalEntityRepository.findAll();
        assertThat(legalEntityList).hasSize(databaseSizeBeforeUpdate);
        LegalEntity testLegalEntity = legalEntityList.get(legalEntityList.size() - 1);
        assertThat(testLegalEntity.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLegalEntity.getTaxIdentificationNumber()).isEqualTo(UPDATED_TAX_IDENTIFICATION_NUMBER);
        assertThat(testLegalEntity.getIdentificationNumber()).isEqualTo(UPDATED_IDENTIFICATION_NUMBER);
    }

    @Test
    @Transactional
    void putNonExistingLegalEntity() throws Exception {
        int databaseSizeBeforeUpdate = legalEntityRepository.findAll().size();
        legalEntity.setId(count.incrementAndGet());

        // Create the LegalEntity
        LegalEntityDTO legalEntityDTO = legalEntityMapper.toDto(legalEntity);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLegalEntityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, legalEntityDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(legalEntityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LegalEntity in the database
        List<LegalEntity> legalEntityList = legalEntityRepository.findAll();
        assertThat(legalEntityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLegalEntity() throws Exception {
        int databaseSizeBeforeUpdate = legalEntityRepository.findAll().size();
        legalEntity.setId(count.incrementAndGet());

        // Create the LegalEntity
        LegalEntityDTO legalEntityDTO = legalEntityMapper.toDto(legalEntity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLegalEntityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(legalEntityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LegalEntity in the database
        List<LegalEntity> legalEntityList = legalEntityRepository.findAll();
        assertThat(legalEntityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLegalEntity() throws Exception {
        int databaseSizeBeforeUpdate = legalEntityRepository.findAll().size();
        legalEntity.setId(count.incrementAndGet());

        // Create the LegalEntity
        LegalEntityDTO legalEntityDTO = legalEntityMapper.toDto(legalEntity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLegalEntityMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(legalEntityDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the LegalEntity in the database
        List<LegalEntity> legalEntityList = legalEntityRepository.findAll();
        assertThat(legalEntityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLegalEntityWithPatch() throws Exception {
        // Initialize the database
        legalEntityRepository.saveAndFlush(legalEntity);

        int databaseSizeBeforeUpdate = legalEntityRepository.findAll().size();

        // Update the legalEntity using partial update
        LegalEntity partialUpdatedLegalEntity = new LegalEntity();
        partialUpdatedLegalEntity.setId(legalEntity.getId());

        partialUpdatedLegalEntity
            .taxIdentificationNumber(UPDATED_TAX_IDENTIFICATION_NUMBER)
            .identificationNumber(UPDATED_IDENTIFICATION_NUMBER);

        restLegalEntityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLegalEntity.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLegalEntity))
            )
            .andExpect(status().isOk());

        // Validate the LegalEntity in the database
        List<LegalEntity> legalEntityList = legalEntityRepository.findAll();
        assertThat(legalEntityList).hasSize(databaseSizeBeforeUpdate);
        LegalEntity testLegalEntity = legalEntityList.get(legalEntityList.size() - 1);
        assertThat(testLegalEntity.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testLegalEntity.getTaxIdentificationNumber()).isEqualTo(UPDATED_TAX_IDENTIFICATION_NUMBER);
        assertThat(testLegalEntity.getIdentificationNumber()).isEqualTo(UPDATED_IDENTIFICATION_NUMBER);
    }

    @Test
    @Transactional
    void fullUpdateLegalEntityWithPatch() throws Exception {
        // Initialize the database
        legalEntityRepository.saveAndFlush(legalEntity);

        int databaseSizeBeforeUpdate = legalEntityRepository.findAll().size();

        // Update the legalEntity using partial update
        LegalEntity partialUpdatedLegalEntity = new LegalEntity();
        partialUpdatedLegalEntity.setId(legalEntity.getId());

        partialUpdatedLegalEntity
            .name(UPDATED_NAME)
            .taxIdentificationNumber(UPDATED_TAX_IDENTIFICATION_NUMBER)
            .identificationNumber(UPDATED_IDENTIFICATION_NUMBER);

        restLegalEntityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLegalEntity.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLegalEntity))
            )
            .andExpect(status().isOk());

        // Validate the LegalEntity in the database
        List<LegalEntity> legalEntityList = legalEntityRepository.findAll();
        assertThat(legalEntityList).hasSize(databaseSizeBeforeUpdate);
        LegalEntity testLegalEntity = legalEntityList.get(legalEntityList.size() - 1);
        assertThat(testLegalEntity.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLegalEntity.getTaxIdentificationNumber()).isEqualTo(UPDATED_TAX_IDENTIFICATION_NUMBER);
        assertThat(testLegalEntity.getIdentificationNumber()).isEqualTo(UPDATED_IDENTIFICATION_NUMBER);
    }

    @Test
    @Transactional
    void patchNonExistingLegalEntity() throws Exception {
        int databaseSizeBeforeUpdate = legalEntityRepository.findAll().size();
        legalEntity.setId(count.incrementAndGet());

        // Create the LegalEntity
        LegalEntityDTO legalEntityDTO = legalEntityMapper.toDto(legalEntity);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLegalEntityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, legalEntityDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(legalEntityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LegalEntity in the database
        List<LegalEntity> legalEntityList = legalEntityRepository.findAll();
        assertThat(legalEntityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLegalEntity() throws Exception {
        int databaseSizeBeforeUpdate = legalEntityRepository.findAll().size();
        legalEntity.setId(count.incrementAndGet());

        // Create the LegalEntity
        LegalEntityDTO legalEntityDTO = legalEntityMapper.toDto(legalEntity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLegalEntityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(legalEntityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LegalEntity in the database
        List<LegalEntity> legalEntityList = legalEntityRepository.findAll();
        assertThat(legalEntityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLegalEntity() throws Exception {
        int databaseSizeBeforeUpdate = legalEntityRepository.findAll().size();
        legalEntity.setId(count.incrementAndGet());

        // Create the LegalEntity
        LegalEntityDTO legalEntityDTO = legalEntityMapper.toDto(legalEntity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLegalEntityMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(legalEntityDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LegalEntity in the database
        List<LegalEntity> legalEntityList = legalEntityRepository.findAll();
        assertThat(legalEntityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLegalEntity() throws Exception {
        // Initialize the database
        legalEntityRepository.saveAndFlush(legalEntity);

        int databaseSizeBeforeDelete = legalEntityRepository.findAll().size();

        // Delete the legalEntity
        restLegalEntityMockMvc
            .perform(delete(ENTITY_API_URL_ID, legalEntity.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LegalEntity> legalEntityList = legalEntityRepository.findAll();
        assertThat(legalEntityList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
