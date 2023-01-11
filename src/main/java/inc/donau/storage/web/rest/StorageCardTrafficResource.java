package inc.donau.storage.web.rest;

import inc.donau.storage.repository.StorageCardTrafficRepository;
import inc.donau.storage.service.StorageCardTrafficService;
import inc.donau.storage.service.dto.StorageCardTrafficDTO;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link inc.donau.storage.domain.StorageCardTraffic}.
 */
@RestController
@RequestMapping("/api")
public class StorageCardTrafficResource {

    private final Logger log = LoggerFactory.getLogger(StorageCardTrafficResource.class);

    private static final String ENTITY_NAME = "storageCardTraffic";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StorageCardTrafficService storageCardTrafficService;

    private final StorageCardTrafficRepository storageCardTrafficRepository;

    public StorageCardTrafficResource(
        StorageCardTrafficService storageCardTrafficService,
        StorageCardTrafficRepository storageCardTrafficRepository
    ) {
        this.storageCardTrafficService = storageCardTrafficService;
        this.storageCardTrafficRepository = storageCardTrafficRepository;
    }

    /**
     * {@code POST  /storage-card-traffics} : Create a new storageCardTraffic.
     *
     * @param storageCardTrafficDTO the storageCardTrafficDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new storageCardTrafficDTO, or with status {@code 400 (Bad Request)} if the storageCardTraffic has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/storage-card-traffics")
    public ResponseEntity<StorageCardTrafficDTO> createStorageCardTraffic(@Valid @RequestBody StorageCardTrafficDTO storageCardTrafficDTO)
        throws URISyntaxException {
        log.debug("REST request to save StorageCardTraffic : {}", storageCardTrafficDTO);
        if (storageCardTrafficDTO.getId() != null) {
            throw new BadRequestAlertException("A new storageCardTraffic cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StorageCardTrafficDTO result = storageCardTrafficService.save(storageCardTrafficDTO);
        return ResponseEntity
            .created(new URI("/api/storage-card-traffics/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /storage-card-traffics/:id} : Updates an existing storageCardTraffic.
     *
     * @param id the id of the storageCardTrafficDTO to save.
     * @param storageCardTrafficDTO the storageCardTrafficDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated storageCardTrafficDTO,
     * or with status {@code 400 (Bad Request)} if the storageCardTrafficDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the storageCardTrafficDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/storage-card-traffics/{id}")
    public ResponseEntity<StorageCardTrafficDTO> updateStorageCardTraffic(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody StorageCardTrafficDTO storageCardTrafficDTO
    ) throws URISyntaxException {
        log.debug("REST request to update StorageCardTraffic : {}, {}", id, storageCardTrafficDTO);
        if (storageCardTrafficDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, storageCardTrafficDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!storageCardTrafficRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        StorageCardTrafficDTO result = storageCardTrafficService.update(storageCardTrafficDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, storageCardTrafficDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /storage-card-traffics/:id} : Partial updates given fields of an existing storageCardTraffic, field will ignore if it is null
     *
     * @param id the id of the storageCardTrafficDTO to save.
     * @param storageCardTrafficDTO the storageCardTrafficDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated storageCardTrafficDTO,
     * or with status {@code 400 (Bad Request)} if the storageCardTrafficDTO is not valid,
     * or with status {@code 404 (Not Found)} if the storageCardTrafficDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the storageCardTrafficDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/storage-card-traffics/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<StorageCardTrafficDTO> partialUpdateStorageCardTraffic(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody StorageCardTrafficDTO storageCardTrafficDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update StorageCardTraffic partially : {}, {}", id, storageCardTrafficDTO);
        if (storageCardTrafficDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, storageCardTrafficDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!storageCardTrafficRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<StorageCardTrafficDTO> result = storageCardTrafficService.partialUpdate(storageCardTrafficDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, storageCardTrafficDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /storage-card-traffics} : get all the storageCardTraffics.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of storageCardTraffics in body.
     */
    @GetMapping("/storage-card-traffics")
    public ResponseEntity<List<StorageCardTrafficDTO>> getAllStorageCardTraffics(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of StorageCardTraffics");
        Page<StorageCardTrafficDTO> page = storageCardTrafficService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /storage-card-traffics/:id} : get the "id" storageCardTraffic.
     *
     * @param id the id of the storageCardTrafficDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the storageCardTrafficDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/storage-card-traffics/{id}")
    public ResponseEntity<StorageCardTrafficDTO> getStorageCardTraffic(@PathVariable Long id) {
        log.debug("REST request to get StorageCardTraffic : {}", id);
        Optional<StorageCardTrafficDTO> storageCardTrafficDTO = storageCardTrafficService.findOne(id);
        return ResponseUtil.wrapOrNotFound(storageCardTrafficDTO);
    }

    /**
     * {@code DELETE  /storage-card-traffics/:id} : delete the "id" storageCardTraffic.
     *
     * @param id the id of the storageCardTrafficDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/storage-card-traffics/{id}")
    public ResponseEntity<Void> deleteStorageCardTraffic(@PathVariable Long id) {
        log.debug("REST request to delete StorageCardTraffic : {}", id);
        storageCardTrafficService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
