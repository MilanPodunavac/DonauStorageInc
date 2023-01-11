package inc.donau.storage.web.rest;

import inc.donau.storage.repository.TransferDocumentRepository;
import inc.donau.storage.service.TransferDocumentQueryService;
import inc.donau.storage.service.TransferDocumentService;
import inc.donau.storage.service.criteria.TransferDocumentCriteria;
import inc.donau.storage.service.dto.TransferDocumentDTO;
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
 * REST controller for managing {@link inc.donau.storage.domain.TransferDocument}.
 */
@RestController
@RequestMapping("/api")
public class TransferDocumentResource {

    private final Logger log = LoggerFactory.getLogger(TransferDocumentResource.class);

    private static final String ENTITY_NAME = "transferDocument";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TransferDocumentService transferDocumentService;

    private final TransferDocumentRepository transferDocumentRepository;

    private final TransferDocumentQueryService transferDocumentQueryService;

    public TransferDocumentResource(
        TransferDocumentService transferDocumentService,
        TransferDocumentRepository transferDocumentRepository,
        TransferDocumentQueryService transferDocumentQueryService
    ) {
        this.transferDocumentService = transferDocumentService;
        this.transferDocumentRepository = transferDocumentRepository;
        this.transferDocumentQueryService = transferDocumentQueryService;
    }

    /**
     * {@code POST  /transfer-documents} : Create a new transferDocument.
     *
     * @param transferDocumentDTO the transferDocumentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new transferDocumentDTO, or with status {@code 400 (Bad Request)} if the transferDocument has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/transfer-documents")
    public ResponseEntity<TransferDocumentDTO> createTransferDocument(@Valid @RequestBody TransferDocumentDTO transferDocumentDTO)
        throws URISyntaxException {
        log.debug("REST request to save TransferDocument : {}", transferDocumentDTO);
        if (transferDocumentDTO.getId() != null) {
            throw new BadRequestAlertException("A new transferDocument cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TransferDocumentDTO result = transferDocumentService.save(transferDocumentDTO);
        return ResponseEntity
            .created(new URI("/api/transfer-documents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /transfer-documents/:id} : Updates an existing transferDocument.
     *
     * @param id the id of the transferDocumentDTO to save.
     * @param transferDocumentDTO the transferDocumentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transferDocumentDTO,
     * or with status {@code 400 (Bad Request)} if the transferDocumentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the transferDocumentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/transfer-documents/{id}")
    public ResponseEntity<TransferDocumentDTO> updateTransferDocument(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TransferDocumentDTO transferDocumentDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TransferDocument : {}, {}", id, transferDocumentDTO);
        if (transferDocumentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, transferDocumentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!transferDocumentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TransferDocumentDTO result = transferDocumentService.update(transferDocumentDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, transferDocumentDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /transfer-documents/:id} : Partial updates given fields of an existing transferDocument, field will ignore if it is null
     *
     * @param id the id of the transferDocumentDTO to save.
     * @param transferDocumentDTO the transferDocumentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transferDocumentDTO,
     * or with status {@code 400 (Bad Request)} if the transferDocumentDTO is not valid,
     * or with status {@code 404 (Not Found)} if the transferDocumentDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the transferDocumentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/transfer-documents/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TransferDocumentDTO> partialUpdateTransferDocument(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TransferDocumentDTO transferDocumentDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TransferDocument partially : {}, {}", id, transferDocumentDTO);
        if (transferDocumentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, transferDocumentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!transferDocumentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TransferDocumentDTO> result = transferDocumentService.partialUpdate(transferDocumentDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, transferDocumentDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /transfer-documents} : get all the transferDocuments.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of transferDocuments in body.
     */
    @GetMapping("/transfer-documents")
    public ResponseEntity<List<TransferDocumentDTO>> getAllTransferDocuments(
        TransferDocumentCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get TransferDocuments by criteria: {}", criteria);
        Page<TransferDocumentDTO> page = transferDocumentQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /transfer-documents/count} : count all the transferDocuments.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/transfer-documents/count")
    public ResponseEntity<Long> countTransferDocuments(TransferDocumentCriteria criteria) {
        log.debug("REST request to count TransferDocuments by criteria: {}", criteria);
        return ResponseEntity.ok().body(transferDocumentQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /transfer-documents/:id} : get the "id" transferDocument.
     *
     * @param id the id of the transferDocumentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the transferDocumentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/transfer-documents/{id}")
    public ResponseEntity<TransferDocumentDTO> getTransferDocument(@PathVariable Long id) {
        log.debug("REST request to get TransferDocument : {}", id);
        Optional<TransferDocumentDTO> transferDocumentDTO = transferDocumentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(transferDocumentDTO);
    }

    /**
     * {@code DELETE  /transfer-documents/:id} : delete the "id" transferDocument.
     *
     * @param id the id of the transferDocumentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/transfer-documents/{id}")
    public ResponseEntity<Void> deleteTransferDocument(@PathVariable Long id) {
        log.debug("REST request to delete TransferDocument : {}", id);
        transferDocumentService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
