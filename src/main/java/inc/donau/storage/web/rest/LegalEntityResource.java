package inc.donau.storage.web.rest;

import inc.donau.storage.repository.LegalEntityRepository;
import inc.donau.storage.service.LegalEntityService;
import inc.donau.storage.service.dto.LegalEntityDTO;
import inc.donau.storage.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link inc.donau.storage.domain.LegalEntity}.
 */
@RestController
@RequestMapping("/api")
public class LegalEntityResource {

    private final Logger log = LoggerFactory.getLogger(LegalEntityResource.class);

    private static final String ENTITY_NAME = "legalEntity";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LegalEntityService legalEntityService;

    private final LegalEntityRepository legalEntityRepository;

    public LegalEntityResource(LegalEntityService legalEntityService, LegalEntityRepository legalEntityRepository) {
        this.legalEntityService = legalEntityService;
        this.legalEntityRepository = legalEntityRepository;
    }

    /**
     * {@code POST  /legal-entities} : Create a new legalEntity.
     *
     * @param legalEntityDTO the legalEntityDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new legalEntityDTO, or with status {@code 400 (Bad Request)} if the legalEntity has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/legal-entities")
    public ResponseEntity<LegalEntityDTO> createLegalEntity(@Valid @RequestBody LegalEntityDTO legalEntityDTO) throws URISyntaxException {
        log.debug("REST request to save LegalEntity : {}", legalEntityDTO);
        if (legalEntityDTO.getId() != null) {
            throw new BadRequestAlertException("A new legalEntity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LegalEntityDTO result = legalEntityService.save(legalEntityDTO);
        return ResponseEntity
            .created(new URI("/api/legal-entities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /legal-entities/:id} : Updates an existing legalEntity.
     *
     * @param id the id of the legalEntityDTO to save.
     * @param legalEntityDTO the legalEntityDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated legalEntityDTO,
     * or with status {@code 400 (Bad Request)} if the legalEntityDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the legalEntityDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/legal-entities/{id}")
    public ResponseEntity<LegalEntityDTO> updateLegalEntity(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody LegalEntityDTO legalEntityDTO
    ) throws URISyntaxException {
        log.debug("REST request to update LegalEntity : {}, {}", id, legalEntityDTO);
        if (legalEntityDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, legalEntityDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!legalEntityRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LegalEntityDTO result = legalEntityService.update(legalEntityDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, legalEntityDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /legal-entities/:id} : Partial updates given fields of an existing legalEntity, field will ignore if it is null
     *
     * @param id the id of the legalEntityDTO to save.
     * @param legalEntityDTO the legalEntityDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated legalEntityDTO,
     * or with status {@code 400 (Bad Request)} if the legalEntityDTO is not valid,
     * or with status {@code 404 (Not Found)} if the legalEntityDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the legalEntityDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/legal-entities/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LegalEntityDTO> partialUpdateLegalEntity(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody LegalEntityDTO legalEntityDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update LegalEntity partially : {}, {}", id, legalEntityDTO);
        if (legalEntityDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, legalEntityDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!legalEntityRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LegalEntityDTO> result = legalEntityService.partialUpdate(legalEntityDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, legalEntityDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /legal-entities} : get all the legalEntities.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of legalEntities in body.
     */
    @GetMapping("/legal-entities")
    public List<LegalEntityDTO> getAllLegalEntities() {
        log.debug("REST request to get all LegalEntities");
        return legalEntityService.findAll();
    }

    /**
     * {@code GET  /legal-entities/:id} : get the "id" legalEntity.
     *
     * @param id the id of the legalEntityDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the legalEntityDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/legal-entities/{id}")
    public ResponseEntity<LegalEntityDTO> getLegalEntity(@PathVariable Long id) {
        log.debug("REST request to get LegalEntity : {}", id);
        Optional<LegalEntityDTO> legalEntityDTO = legalEntityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(legalEntityDTO);
    }

    /**
     * {@code DELETE  /legal-entities/:id} : delete the "id" legalEntity.
     *
     * @param id the id of the legalEntityDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/legal-entities/{id}")
    public ResponseEntity<Void> deleteLegalEntity(@PathVariable Long id) {
        log.debug("REST request to delete LegalEntity : {}", id);
        legalEntityService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
