package inc.donau.storage.web.rest;

import inc.donau.storage.repository.StorageCardRepository;
import inc.donau.storage.service.StorageCardQueryService;
import inc.donau.storage.service.StorageCardService;
import inc.donau.storage.service.criteria.StorageCardCriteria;
import inc.donau.storage.service.dto.StorageCardDTO;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link inc.donau.storage.domain.StorageCard}.
 */
@RestController
@RequestMapping("/api")
public class StorageCardResource {

    private final Logger log = LoggerFactory.getLogger(StorageCardResource.class);

    private static final String ENTITY_NAME = "storageCard";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StorageCardService storageCardService;

    private final StorageCardRepository storageCardRepository;

    private final StorageCardQueryService storageCardQueryService;

    public StorageCardResource(
        StorageCardService storageCardService,
        StorageCardRepository storageCardRepository,
        StorageCardQueryService storageCardQueryService
    ) {
        this.storageCardService = storageCardService;
        this.storageCardRepository = storageCardRepository;
        this.storageCardQueryService = storageCardQueryService;
    }

    /**
     * {@code POST  /storage-cards} : Create a new storageCard.
     *
     * @param storageCardDTO the storageCardDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new storageCardDTO, or with status {@code 400 (Bad Request)} if the storageCard has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/storage-cards")
    public ResponseEntity<StorageCardDTO> createStorageCard(@Valid @RequestBody StorageCardDTO storageCardDTO) throws URISyntaxException {
        log.debug("REST request to save StorageCard : {}", storageCardDTO);
        if (storageCardDTO.getId() != null) {
            throw new BadRequestAlertException("A new storageCard cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StorageCardDTO result = storageCardService.save(storageCardDTO);
        return ResponseEntity
            .created(new URI("/api/storage-cards/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /storage-cards/:id} : Updates an existing storageCard.
     *
     * @param id the id of the storageCardDTO to save.
     * @param storageCardDTO the storageCardDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated storageCardDTO,
     * or with status {@code 400 (Bad Request)} if the storageCardDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the storageCardDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/storage-cards/{id}")
    public ResponseEntity<StorageCardDTO> updateStorageCard(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody StorageCardDTO storageCardDTO
    ) throws URISyntaxException {
        log.debug("REST request to update StorageCard : {}, {}", id, storageCardDTO);
        if (storageCardDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, storageCardDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!storageCardRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        StorageCardDTO result = storageCardService.update(storageCardDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, storageCardDTO.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /storage-cards/:id} : Partial updates given fields of an existing storageCard, field will ignore if it is null
     *
     * @param id the id of the storageCardDTO to save.
     * @param storageCardDTO the storageCardDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated storageCardDTO,
     * or with status {@code 400 (Bad Request)} if the storageCardDTO is not valid,
     * or with status {@code 404 (Not Found)} if the storageCardDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the storageCardDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/storage-cards/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<StorageCardDTO> partialUpdateStorageCard(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody StorageCardDTO storageCardDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update StorageCard partially : {}, {}", id, storageCardDTO);
        if (storageCardDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, storageCardDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!storageCardRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<StorageCardDTO> result = storageCardService.partialUpdate(storageCardDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, storageCardDTO.getId())
        );
    }

    /**
     * {@code GET  /storage-cards} : get all the storageCards.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of storageCards in body.
     */
    @GetMapping("/storage-cards")
    public ResponseEntity<List<StorageCardDTO>> getAllStorageCards(
        StorageCardCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get StorageCards by criteria: {}", criteria);
        Page<StorageCardDTO> page = storageCardQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /storage-cards/count} : count all the storageCards.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/storage-cards/count")
    public ResponseEntity<Long> countStorageCards(StorageCardCriteria criteria) {
        log.debug("REST request to count StorageCards by criteria: {}", criteria);
        return ResponseEntity.ok().body(storageCardQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /storage-cards/:id} : get the "id" storageCard.
     *
     * @param id the id of the storageCardDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the storageCardDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/storage-cards/{id}")
    public ResponseEntity<StorageCardDTO> getStorageCard(@PathVariable String id) {
        log.debug("REST request to get StorageCard : {}", id);
        Optional<StorageCardDTO> storageCardDTO = storageCardService.findOne(id);
        return ResponseUtil.wrapOrNotFound(storageCardDTO);
    }

    /**
     * {@code DELETE  /storage-cards/:id} : delete the "id" storageCard.
     *
     * @param id the id of the storageCardDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/storage-cards/{id}")
    public ResponseEntity<Void> deleteStorageCard(@PathVariable String id) {
        log.debug("REST request to delete StorageCard : {}", id);
        storageCardService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
