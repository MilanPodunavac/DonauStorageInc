package inc.donau.storage.web.rest;

import inc.donau.storage.repository.TransferDocumentItemRepository;
import inc.donau.storage.service.TransferDocumentItemQueryService;
import inc.donau.storage.service.TransferDocumentItemService;
import inc.donau.storage.service.criteria.TransferDocumentItemCriteria;
import inc.donau.storage.service.dto.TransferDocumentItemDTO;
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
 * REST controller for managing {@link inc.donau.storage.domain.TransferDocumentItem}.
 */
@RestController
@RequestMapping("/api")
public class TransferDocumentItemResource {

    private final Logger log = LoggerFactory.getLogger(TransferDocumentItemResource.class);

    private static final String ENTITY_NAME = "transferDocumentItem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TransferDocumentItemService transferDocumentItemService;

    private final TransferDocumentItemRepository transferDocumentItemRepository;

    private final TransferDocumentItemQueryService transferDocumentItemQueryService;

    public TransferDocumentItemResource(
        TransferDocumentItemService transferDocumentItemService,
        TransferDocumentItemRepository transferDocumentItemRepository,
        TransferDocumentItemQueryService transferDocumentItemQueryService
    ) {
        this.transferDocumentItemService = transferDocumentItemService;
        this.transferDocumentItemRepository = transferDocumentItemRepository;
        this.transferDocumentItemQueryService = transferDocumentItemQueryService;
    }

    /**
     * {@code POST  /transfer-document-items} : Create a new transferDocumentItem.
     *
     * @param transferDocumentItemDTO the transferDocumentItemDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new transferDocumentItemDTO, or with status {@code 400 (Bad Request)} if the transferDocumentItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/transfer-document-items")
    public ResponseEntity<TransferDocumentItemDTO> createTransferDocumentItem(
        @Valid @RequestBody TransferDocumentItemDTO transferDocumentItemDTO
    ) throws URISyntaxException {
        log.debug("REST request to save TransferDocumentItem : {}", transferDocumentItemDTO);
        if (transferDocumentItemDTO.getId() != null) {
            throw new BadRequestAlertException("A new transferDocumentItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TransferDocumentItemDTO result = transferDocumentItemService.save(transferDocumentItemDTO);
        return ResponseEntity
            .created(new URI("/api/transfer-document-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /transfer-document-items/:id} : Updates an existing transferDocumentItem.
     *
     * @param id the id of the transferDocumentItemDTO to save.
     * @param transferDocumentItemDTO the transferDocumentItemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transferDocumentItemDTO,
     * or with status {@code 400 (Bad Request)} if the transferDocumentItemDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the transferDocumentItemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/transfer-document-items/{id}")
    public ResponseEntity<TransferDocumentItemDTO> updateTransferDocumentItem(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TransferDocumentItemDTO transferDocumentItemDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TransferDocumentItem : {}, {}", id, transferDocumentItemDTO);
        if (transferDocumentItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, transferDocumentItemDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!transferDocumentItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TransferDocumentItemDTO result = transferDocumentItemService.update(transferDocumentItemDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, transferDocumentItemDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /transfer-document-items/:id} : Partial updates given fields of an existing transferDocumentItem, field will ignore if it is null
     *
     * @param id the id of the transferDocumentItemDTO to save.
     * @param transferDocumentItemDTO the transferDocumentItemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transferDocumentItemDTO,
     * or with status {@code 400 (Bad Request)} if the transferDocumentItemDTO is not valid,
     * or with status {@code 404 (Not Found)} if the transferDocumentItemDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the transferDocumentItemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/transfer-document-items/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TransferDocumentItemDTO> partialUpdateTransferDocumentItem(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TransferDocumentItemDTO transferDocumentItemDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TransferDocumentItem partially : {}, {}", id, transferDocumentItemDTO);
        if (transferDocumentItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, transferDocumentItemDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!transferDocumentItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TransferDocumentItemDTO> result = transferDocumentItemService.partialUpdate(transferDocumentItemDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, transferDocumentItemDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /transfer-document-items} : get all the transferDocumentItems.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of transferDocumentItems in body.
     */
    @GetMapping("/transfer-document-items")
    public ResponseEntity<List<TransferDocumentItemDTO>> getAllTransferDocumentItems(
        TransferDocumentItemCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get TransferDocumentItems by criteria: {}", criteria);
        Page<TransferDocumentItemDTO> page = transferDocumentItemQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /transfer-document-items/count} : count all the transferDocumentItems.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/transfer-document-items/count")
    public ResponseEntity<Long> countTransferDocumentItems(TransferDocumentItemCriteria criteria) {
        log.debug("REST request to count TransferDocumentItems by criteria: {}", criteria);
        return ResponseEntity.ok().body(transferDocumentItemQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /transfer-document-items/:id} : get the "id" transferDocumentItem.
     *
     * @param id the id of the transferDocumentItemDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the transferDocumentItemDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/transfer-document-items/{id}")
    public ResponseEntity<TransferDocumentItemDTO> getTransferDocumentItem(@PathVariable Long id) {
        log.debug("REST request to get TransferDocumentItem : {}", id);
        Optional<TransferDocumentItemDTO> transferDocumentItemDTO = transferDocumentItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(transferDocumentItemDTO);
    }

    /**
     * {@code DELETE  /transfer-document-items/:id} : delete the "id" transferDocumentItem.
     *
     * @param id the id of the transferDocumentItemDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/transfer-document-items/{id}")
    public ResponseEntity<Void> deleteTransferDocumentItem(@PathVariable Long id) {
        log.debug("REST request to delete TransferDocumentItem : {}", id);
        transferDocumentItemService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
