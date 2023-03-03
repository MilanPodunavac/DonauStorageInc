package inc.donau.storage.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import inc.donau.storage.IntegrationTest;
import inc.donau.storage.domain.MeasurementUnit;
import inc.donau.storage.repository.MeasurementUnitRepository;
import inc.donau.storage.service.criteria.MeasurementUnitCriteria;
import inc.donau.storage.service.dto.MeasurementUnitDTO;
import inc.donau.storage.service.mapper.MeasurementUnitMapper;
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
 * Integration tests for the {@link MeasurementUnitResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MeasurementUnitResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ABBREVIATION = "AAAAAAAAAA";
    private static final String UPDATED_ABBREVIATION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/measurement-units";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MeasurementUnitRepository measurementUnitRepository;

    @Autowired
    private MeasurementUnitMapper measurementUnitMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMeasurementUnitMockMvc;

    private MeasurementUnit measurementUnit;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MeasurementUnit createEntity(EntityManager em) {
        MeasurementUnit measurementUnit = new MeasurementUnit().name(DEFAULT_NAME).abbreviation(DEFAULT_ABBREVIATION);
        return measurementUnit;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MeasurementUnit createUpdatedEntity(EntityManager em) {
        MeasurementUnit measurementUnit = new MeasurementUnit().name(UPDATED_NAME).abbreviation(UPDATED_ABBREVIATION);
        return measurementUnit;
    }

    @BeforeEach
    public void initTest() {
        measurementUnit = createEntity(em);
    }

    @Test
    @Transactional
    void createMeasurementUnit() throws Exception {
        int databaseSizeBeforeCreate = measurementUnitRepository.findAll().size();
        // Create the MeasurementUnit
        MeasurementUnitDTO measurementUnitDTO = measurementUnitMapper.toDto(measurementUnit);
        restMeasurementUnitMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(measurementUnitDTO))
            )
            .andExpect(status().isCreated());

        // Validate the MeasurementUnit in the database
        List<MeasurementUnit> measurementUnitList = measurementUnitRepository.findAll();
        assertThat(measurementUnitList).hasSize(databaseSizeBeforeCreate + 1);
        MeasurementUnit testMeasurementUnit = measurementUnitList.get(measurementUnitList.size() - 1);
        assertThat(testMeasurementUnit.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMeasurementUnit.getAbbreviation()).isEqualTo(DEFAULT_ABBREVIATION);
    }

    @Test
    @Transactional
    void createMeasurementUnitWithExistingId() throws Exception {
        // Create the MeasurementUnit with an existing ID
        measurementUnit.setId(1L);
        MeasurementUnitDTO measurementUnitDTO = measurementUnitMapper.toDto(measurementUnit);

        int databaseSizeBeforeCreate = measurementUnitRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMeasurementUnitMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(measurementUnitDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MeasurementUnit in the database
        List<MeasurementUnit> measurementUnitList = measurementUnitRepository.findAll();
        assertThat(measurementUnitList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = measurementUnitRepository.findAll().size();
        // set the field null
        measurementUnit.setName(null);

        // Create the MeasurementUnit, which fails.
        MeasurementUnitDTO measurementUnitDTO = measurementUnitMapper.toDto(measurementUnit);

        restMeasurementUnitMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(measurementUnitDTO))
            )
            .andExpect(status().isBadRequest());

        List<MeasurementUnit> measurementUnitList = measurementUnitRepository.findAll();
        assertThat(measurementUnitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAbbreviationIsRequired() throws Exception {
        int databaseSizeBeforeTest = measurementUnitRepository.findAll().size();
        // set the field null
        measurementUnit.setAbbreviation(null);

        // Create the MeasurementUnit, which fails.
        MeasurementUnitDTO measurementUnitDTO = measurementUnitMapper.toDto(measurementUnit);

        restMeasurementUnitMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(measurementUnitDTO))
            )
            .andExpect(status().isBadRequest());

        List<MeasurementUnit> measurementUnitList = measurementUnitRepository.findAll();
        assertThat(measurementUnitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMeasurementUnits() throws Exception {
        // Initialize the database
        measurementUnitRepository.saveAndFlush(measurementUnit);

        // Get all the measurementUnitList
        restMeasurementUnitMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(measurementUnit.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].abbreviation").value(hasItem(DEFAULT_ABBREVIATION)));
    }

    @Test
    @Transactional
    void getMeasurementUnit() throws Exception {
        // Initialize the database
        measurementUnitRepository.saveAndFlush(measurementUnit);

        // Get the measurementUnit
        restMeasurementUnitMockMvc
            .perform(get(ENTITY_API_URL_ID, measurementUnit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(measurementUnit.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.abbreviation").value(DEFAULT_ABBREVIATION));
    }

    @Test
    @Transactional
    void getMeasurementUnitsByIdFiltering() throws Exception {
        // Initialize the database
        measurementUnitRepository.saveAndFlush(measurementUnit);

        Long id = measurementUnit.getId();

        defaultMeasurementUnitShouldBeFound("id.equals=" + id);
        defaultMeasurementUnitShouldNotBeFound("id.notEquals=" + id);

        defaultMeasurementUnitShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMeasurementUnitShouldNotBeFound("id.greaterThan=" + id);

        defaultMeasurementUnitShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMeasurementUnitShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllMeasurementUnitsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        measurementUnitRepository.saveAndFlush(measurementUnit);

        // Get all the measurementUnitList where name equals to DEFAULT_NAME
        defaultMeasurementUnitShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the measurementUnitList where name equals to UPDATED_NAME
        defaultMeasurementUnitShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllMeasurementUnitsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        measurementUnitRepository.saveAndFlush(measurementUnit);

        // Get all the measurementUnitList where name in DEFAULT_NAME or UPDATED_NAME
        defaultMeasurementUnitShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the measurementUnitList where name equals to UPDATED_NAME
        defaultMeasurementUnitShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllMeasurementUnitsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        measurementUnitRepository.saveAndFlush(measurementUnit);

        // Get all the measurementUnitList where name is not null
        defaultMeasurementUnitShouldBeFound("name.specified=true");

        // Get all the measurementUnitList where name is null
        defaultMeasurementUnitShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllMeasurementUnitsByNameContainsSomething() throws Exception {
        // Initialize the database
        measurementUnitRepository.saveAndFlush(measurementUnit);

        // Get all the measurementUnitList where name contains DEFAULT_NAME
        defaultMeasurementUnitShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the measurementUnitList where name contains UPDATED_NAME
        defaultMeasurementUnitShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllMeasurementUnitsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        measurementUnitRepository.saveAndFlush(measurementUnit);

        // Get all the measurementUnitList where name does not contain DEFAULT_NAME
        defaultMeasurementUnitShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the measurementUnitList where name does not contain UPDATED_NAME
        defaultMeasurementUnitShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllMeasurementUnitsByAbbreviationIsEqualToSomething() throws Exception {
        // Initialize the database
        measurementUnitRepository.saveAndFlush(measurementUnit);

        // Get all the measurementUnitList where abbreviation equals to DEFAULT_ABBREVIATION
        defaultMeasurementUnitShouldBeFound("abbreviation.equals=" + DEFAULT_ABBREVIATION);

        // Get all the measurementUnitList where abbreviation equals to UPDATED_ABBREVIATION
        defaultMeasurementUnitShouldNotBeFound("abbreviation.equals=" + UPDATED_ABBREVIATION);
    }

    @Test
    @Transactional
    void getAllMeasurementUnitsByAbbreviationIsInShouldWork() throws Exception {
        // Initialize the database
        measurementUnitRepository.saveAndFlush(measurementUnit);

        // Get all the measurementUnitList where abbreviation in DEFAULT_ABBREVIATION or UPDATED_ABBREVIATION
        defaultMeasurementUnitShouldBeFound("abbreviation.in=" + DEFAULT_ABBREVIATION + "," + UPDATED_ABBREVIATION);

        // Get all the measurementUnitList where abbreviation equals to UPDATED_ABBREVIATION
        defaultMeasurementUnitShouldNotBeFound("abbreviation.in=" + UPDATED_ABBREVIATION);
    }

    @Test
    @Transactional
    void getAllMeasurementUnitsByAbbreviationIsNullOrNotNull() throws Exception {
        // Initialize the database
        measurementUnitRepository.saveAndFlush(measurementUnit);

        // Get all the measurementUnitList where abbreviation is not null
        defaultMeasurementUnitShouldBeFound("abbreviation.specified=true");

        // Get all the measurementUnitList where abbreviation is null
        defaultMeasurementUnitShouldNotBeFound("abbreviation.specified=false");
    }

    @Test
    @Transactional
    void getAllMeasurementUnitsByAbbreviationContainsSomething() throws Exception {
        // Initialize the database
        measurementUnitRepository.saveAndFlush(measurementUnit);

        // Get all the measurementUnitList where abbreviation contains DEFAULT_ABBREVIATION
        defaultMeasurementUnitShouldBeFound("abbreviation.contains=" + DEFAULT_ABBREVIATION);

        // Get all the measurementUnitList where abbreviation contains UPDATED_ABBREVIATION
        defaultMeasurementUnitShouldNotBeFound("abbreviation.contains=" + UPDATED_ABBREVIATION);
    }

    @Test
    @Transactional
    void getAllMeasurementUnitsByAbbreviationNotContainsSomething() throws Exception {
        // Initialize the database
        measurementUnitRepository.saveAndFlush(measurementUnit);

        // Get all the measurementUnitList where abbreviation does not contain DEFAULT_ABBREVIATION
        defaultMeasurementUnitShouldNotBeFound("abbreviation.doesNotContain=" + DEFAULT_ABBREVIATION);

        // Get all the measurementUnitList where abbreviation does not contain UPDATED_ABBREVIATION
        defaultMeasurementUnitShouldBeFound("abbreviation.doesNotContain=" + UPDATED_ABBREVIATION);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMeasurementUnitShouldBeFound(String filter) throws Exception {
        restMeasurementUnitMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(measurementUnit.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].abbreviation").value(hasItem(DEFAULT_ABBREVIATION)));

        // Check, that the count call also returns 1
        restMeasurementUnitMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMeasurementUnitShouldNotBeFound(String filter) throws Exception {
        restMeasurementUnitMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMeasurementUnitMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingMeasurementUnit() throws Exception {
        // Get the measurementUnit
        restMeasurementUnitMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMeasurementUnit() throws Exception {
        // Initialize the database
        measurementUnitRepository.saveAndFlush(measurementUnit);

        int databaseSizeBeforeUpdate = measurementUnitRepository.findAll().size();

        // Update the measurementUnit
        MeasurementUnit updatedMeasurementUnit = measurementUnitRepository.findById(measurementUnit.getId()).get();
        // Disconnect from session so that the updates on updatedMeasurementUnit are not directly saved in db
        em.detach(updatedMeasurementUnit);
        updatedMeasurementUnit.name(UPDATED_NAME).abbreviation(UPDATED_ABBREVIATION);
        MeasurementUnitDTO measurementUnitDTO = measurementUnitMapper.toDto(updatedMeasurementUnit);

        restMeasurementUnitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, measurementUnitDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(measurementUnitDTO))
            )
            .andExpect(status().isOk());

        // Validate the MeasurementUnit in the database
        List<MeasurementUnit> measurementUnitList = measurementUnitRepository.findAll();
        assertThat(measurementUnitList).hasSize(databaseSizeBeforeUpdate);
        MeasurementUnit testMeasurementUnit = measurementUnitList.get(measurementUnitList.size() - 1);
        assertThat(testMeasurementUnit.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMeasurementUnit.getAbbreviation()).isEqualTo(UPDATED_ABBREVIATION);
    }

    @Test
    @Transactional
    void putNonExistingMeasurementUnit() throws Exception {
        int databaseSizeBeforeUpdate = measurementUnitRepository.findAll().size();
        measurementUnit.setId(count.incrementAndGet());

        // Create the MeasurementUnit
        MeasurementUnitDTO measurementUnitDTO = measurementUnitMapper.toDto(measurementUnit);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMeasurementUnitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, measurementUnitDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(measurementUnitDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MeasurementUnit in the database
        List<MeasurementUnit> measurementUnitList = measurementUnitRepository.findAll();
        assertThat(measurementUnitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMeasurementUnit() throws Exception {
        int databaseSizeBeforeUpdate = measurementUnitRepository.findAll().size();
        measurementUnit.setId(count.incrementAndGet());

        // Create the MeasurementUnit
        MeasurementUnitDTO measurementUnitDTO = measurementUnitMapper.toDto(measurementUnit);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMeasurementUnitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(measurementUnitDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MeasurementUnit in the database
        List<MeasurementUnit> measurementUnitList = measurementUnitRepository.findAll();
        assertThat(measurementUnitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMeasurementUnit() throws Exception {
        int databaseSizeBeforeUpdate = measurementUnitRepository.findAll().size();
        measurementUnit.setId(count.incrementAndGet());

        // Create the MeasurementUnit
        MeasurementUnitDTO measurementUnitDTO = measurementUnitMapper.toDto(measurementUnit);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMeasurementUnitMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(measurementUnitDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MeasurementUnit in the database
        List<MeasurementUnit> measurementUnitList = measurementUnitRepository.findAll();
        assertThat(measurementUnitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMeasurementUnitWithPatch() throws Exception {
        // Initialize the database
        measurementUnitRepository.saveAndFlush(measurementUnit);

        int databaseSizeBeforeUpdate = measurementUnitRepository.findAll().size();

        // Update the measurementUnit using partial update
        MeasurementUnit partialUpdatedMeasurementUnit = new MeasurementUnit();
        partialUpdatedMeasurementUnit.setId(measurementUnit.getId());

        restMeasurementUnitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMeasurementUnit.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMeasurementUnit))
            )
            .andExpect(status().isOk());

        // Validate the MeasurementUnit in the database
        List<MeasurementUnit> measurementUnitList = measurementUnitRepository.findAll();
        assertThat(measurementUnitList).hasSize(databaseSizeBeforeUpdate);
        MeasurementUnit testMeasurementUnit = measurementUnitList.get(measurementUnitList.size() - 1);
        assertThat(testMeasurementUnit.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMeasurementUnit.getAbbreviation()).isEqualTo(DEFAULT_ABBREVIATION);
    }

    @Test
    @Transactional
    void fullUpdateMeasurementUnitWithPatch() throws Exception {
        // Initialize the database
        measurementUnitRepository.saveAndFlush(measurementUnit);

        int databaseSizeBeforeUpdate = measurementUnitRepository.findAll().size();

        // Update the measurementUnit using partial update
        MeasurementUnit partialUpdatedMeasurementUnit = new MeasurementUnit();
        partialUpdatedMeasurementUnit.setId(measurementUnit.getId());

        partialUpdatedMeasurementUnit.name(UPDATED_NAME).abbreviation(UPDATED_ABBREVIATION);

        restMeasurementUnitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMeasurementUnit.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMeasurementUnit))
            )
            .andExpect(status().isOk());

        // Validate the MeasurementUnit in the database
        List<MeasurementUnit> measurementUnitList = measurementUnitRepository.findAll();
        assertThat(measurementUnitList).hasSize(databaseSizeBeforeUpdate);
        MeasurementUnit testMeasurementUnit = measurementUnitList.get(measurementUnitList.size() - 1);
        assertThat(testMeasurementUnit.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMeasurementUnit.getAbbreviation()).isEqualTo(UPDATED_ABBREVIATION);
    }

    @Test
    @Transactional
    void patchNonExistingMeasurementUnit() throws Exception {
        int databaseSizeBeforeUpdate = measurementUnitRepository.findAll().size();
        measurementUnit.setId(count.incrementAndGet());

        // Create the MeasurementUnit
        MeasurementUnitDTO measurementUnitDTO = measurementUnitMapper.toDto(measurementUnit);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMeasurementUnitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, measurementUnitDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(measurementUnitDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MeasurementUnit in the database
        List<MeasurementUnit> measurementUnitList = measurementUnitRepository.findAll();
        assertThat(measurementUnitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMeasurementUnit() throws Exception {
        int databaseSizeBeforeUpdate = measurementUnitRepository.findAll().size();
        measurementUnit.setId(count.incrementAndGet());

        // Create the MeasurementUnit
        MeasurementUnitDTO measurementUnitDTO = measurementUnitMapper.toDto(measurementUnit);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMeasurementUnitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(measurementUnitDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MeasurementUnit in the database
        List<MeasurementUnit> measurementUnitList = measurementUnitRepository.findAll();
        assertThat(measurementUnitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMeasurementUnit() throws Exception {
        int databaseSizeBeforeUpdate = measurementUnitRepository.findAll().size();
        measurementUnit.setId(count.incrementAndGet());

        // Create the MeasurementUnit
        MeasurementUnitDTO measurementUnitDTO = measurementUnitMapper.toDto(measurementUnit);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMeasurementUnitMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(measurementUnitDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MeasurementUnit in the database
        List<MeasurementUnit> measurementUnitList = measurementUnitRepository.findAll();
        assertThat(measurementUnitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMeasurementUnit() throws Exception {
        // Initialize the database
        measurementUnitRepository.saveAndFlush(measurementUnit);

        int databaseSizeBeforeDelete = measurementUnitRepository.findAll().size();

        // Delete the measurementUnit
        restMeasurementUnitMockMvc
            .perform(delete(ENTITY_API_URL_ID, measurementUnit.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MeasurementUnit> measurementUnitList = measurementUnitRepository.findAll();
        assertThat(measurementUnitList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
