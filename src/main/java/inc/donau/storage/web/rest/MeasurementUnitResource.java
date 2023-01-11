package inc.donau.storage.web.rest;

import inc.donau.storage.repository.MeasurementUnitRepository;
import inc.donau.storage.service.MeasurementUnitService;
import inc.donau.storage.service.dto.MeasurementUnitDTO;
import inc.donau.storage.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link inc.donau.storage.domain.MeasurementUnit}.
 */
@RestController
@RequestMapping("/api")
public class MeasurementUnitResource {

    private final Logger log = LoggerFactory.getLogger(MeasurementUnitResource.class);

    private static final String ENTITY_NAME = "measurementUnit";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MeasurementUnitService measurementUnitService;

    private final MeasurementUnitRepository measurementUnitRepository;

    public MeasurementUnitResource(MeasurementUnitService measurementUnitService, MeasurementUnitRepository measurementUnitRepository) {
        this.measurementUnitService = measurementUnitService;
        this.measurementUnitRepository = measurementUnitRepository;
    }

    /**
     * {@code POST  /measurement-units} : Create a new measurementUnit.
     *
     * @param measurementUnitDTO the measurementUnitDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new measurementUnitDTO, or with status {@code 400 (Bad Request)} if the measurementUnit has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/measurement-units")
    public ResponseEntity<MeasurementUnitDTO> createMeasurementUnit(@RequestBody MeasurementUnitDTO measurementUnitDTO)
        throws URISyntaxException {
        log.debug("REST request to save MeasurementUnit : {}", measurementUnitDTO);
        if (measurementUnitDTO.getId() != null) {
            throw new BadRequestAlertException("A new measurementUnit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MeasurementUnitDTO result = measurementUnitService.save(measurementUnitDTO);
        return ResponseEntity
            .created(new URI("/api/measurement-units/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /measurement-units/:id} : Updates an existing measurementUnit.
     *
     * @param id the id of the measurementUnitDTO to save.
     * @param measurementUnitDTO the measurementUnitDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated measurementUnitDTO,
     * or with status {@code 400 (Bad Request)} if the measurementUnitDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the measurementUnitDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/measurement-units/{id}")
    public ResponseEntity<MeasurementUnitDTO> updateMeasurementUnit(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MeasurementUnitDTO measurementUnitDTO
    ) throws URISyntaxException {
        log.debug("REST request to update MeasurementUnit : {}, {}", id, measurementUnitDTO);
        if (measurementUnitDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, measurementUnitDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!measurementUnitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MeasurementUnitDTO result = measurementUnitService.update(measurementUnitDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, measurementUnitDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /measurement-units/:id} : Partial updates given fields of an existing measurementUnit, field will ignore if it is null
     *
     * @param id the id of the measurementUnitDTO to save.
     * @param measurementUnitDTO the measurementUnitDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated measurementUnitDTO,
     * or with status {@code 400 (Bad Request)} if the measurementUnitDTO is not valid,
     * or with status {@code 404 (Not Found)} if the measurementUnitDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the measurementUnitDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/measurement-units/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MeasurementUnitDTO> partialUpdateMeasurementUnit(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MeasurementUnitDTO measurementUnitDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update MeasurementUnit partially : {}, {}", id, measurementUnitDTO);
        if (measurementUnitDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, measurementUnitDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!measurementUnitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MeasurementUnitDTO> result = measurementUnitService.partialUpdate(measurementUnitDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, measurementUnitDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /measurement-units} : get all the measurementUnits.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of measurementUnits in body.
     */
    @GetMapping("/measurement-units")
    public List<MeasurementUnitDTO> getAllMeasurementUnits() {
        log.debug("REST request to get all MeasurementUnits");
        return measurementUnitService.findAll();
    }

    /**
     * {@code GET  /measurement-units/:id} : get the "id" measurementUnit.
     *
     * @param id the id of the measurementUnitDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the measurementUnitDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/measurement-units/{id}")
    public ResponseEntity<MeasurementUnitDTO> getMeasurementUnit(@PathVariable Long id) {
        log.debug("REST request to get MeasurementUnit : {}", id);
        Optional<MeasurementUnitDTO> measurementUnitDTO = measurementUnitService.findOne(id);
        return ResponseUtil.wrapOrNotFound(measurementUnitDTO);
    }

    /**
     * {@code DELETE  /measurement-units/:id} : delete the "id" measurementUnit.
     *
     * @param id the id of the measurementUnitDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/measurement-units/{id}")
    public ResponseEntity<Void> deleteMeasurementUnit(@PathVariable Long id) {
        log.debug("REST request to delete MeasurementUnit : {}", id);
        measurementUnitService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
