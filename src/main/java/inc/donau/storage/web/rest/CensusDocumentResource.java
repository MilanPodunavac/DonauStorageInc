package inc.donau.storage.web.rest;

import inc.donau.storage.repository.CensusDocumentRepository;
import inc.donau.storage.service.CensusDocumentQueryService;
import inc.donau.storage.service.CensusDocumentService;
import inc.donau.storage.service.criteria.CensusDocumentCriteria;
import inc.donau.storage.service.dto.CensusDocumentDTO;
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
 * REST controller for managing {@link inc.donau.storage.domain.CensusDocument}.
 */
@RestController
@RequestMapping("/api")
public class CensusDocumentResource {

    private final Logger log = LoggerFactory.getLogger(CensusDocumentResource.class);

    private static final String ENTITY_NAME = "censusDocument";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CensusDocumentService censusDocumentService;

    private final CensusDocumentRepository censusDocumentRepository;

    private final CensusDocumentQueryService censusDocumentQueryService;

    public CensusDocumentResource(
        CensusDocumentService censusDocumentService,
        CensusDocumentRepository censusDocumentRepository,
        CensusDocumentQueryService censusDocumentQueryService
    ) {
        this.censusDocumentService = censusDocumentService;
        this.censusDocumentRepository = censusDocumentRepository;
        this.censusDocumentQueryService = censusDocumentQueryService;
    }

    /**
     * {@code POST  /census-documents} : Create a new censusDocument.
     *
     * @param censusDocumentDTO the censusDocumentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new censusDocumentDTO, or with status {@code 400 (Bad Request)} if the censusDocument has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/census-documents")
    public ResponseEntity<CensusDocumentDTO> createCensusDocument(@Valid @RequestBody CensusDocumentDTO censusDocumentDTO)
        throws URISyntaxException {
        log.debug("REST request to save CensusDocument : {}", censusDocumentDTO);
        if (censusDocumentDTO.getId() != null) {
            throw new BadRequestAlertException("A new censusDocument cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CensusDocumentDTO result = censusDocumentService.save(censusDocumentDTO);
        return ResponseEntity
            .created(new URI("/api/census-documents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /census-documents/:id} : Updates an existing censusDocument.
     *
     * @param id the id of the censusDocumentDTO to save.
     * @param censusDocumentDTO the censusDocumentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated censusDocumentDTO,
     * or with status {@code 400 (Bad Request)} if the censusDocumentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the censusDocumentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/census-documents/{id}")
    public ResponseEntity<CensusDocumentDTO> updateCensusDocument(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CensusDocumentDTO censusDocumentDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CensusDocument : {}, {}", id, censusDocumentDTO);
        if (censusDocumentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, censusDocumentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!censusDocumentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CensusDocumentDTO result = censusDocumentService.update(censusDocumentDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, censusDocumentDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /census-documents/:id} : Partial updates given fields of an existing censusDocument, field will ignore if it is null
     *
     * @param id the id of the censusDocumentDTO to save.
     * @param censusDocumentDTO the censusDocumentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated censusDocumentDTO,
     * or with status {@code 400 (Bad Request)} if the censusDocumentDTO is not valid,
     * or with status {@code 404 (Not Found)} if the censusDocumentDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the censusDocumentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/census-documents/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CensusDocumentDTO> partialUpdateCensusDocument(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CensusDocumentDTO censusDocumentDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CensusDocument partially : {}, {}", id, censusDocumentDTO);
        if (censusDocumentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, censusDocumentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!censusDocumentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CensusDocumentDTO> result = censusDocumentService.partialUpdate(censusDocumentDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, censusDocumentDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /census-documents} : get all the censusDocuments.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of censusDocuments in body.
     */
    @GetMapping("/census-documents")
    public ResponseEntity<List<CensusDocumentDTO>> getAllCensusDocuments(
        CensusDocumentCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get CensusDocuments by criteria: {}", criteria);
        Page<CensusDocumentDTO> page = censusDocumentQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /census-documents/count} : count all the censusDocuments.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/census-documents/count")
    public ResponseEntity<Long> countCensusDocuments(CensusDocumentCriteria criteria) {
        log.debug("REST request to count CensusDocuments by criteria: {}", criteria);
        return ResponseEntity.ok().body(censusDocumentQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /census-documents/:id} : get the "id" censusDocument.
     *
     * @param id the id of the censusDocumentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the censusDocumentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/census-documents/{id}")
    public ResponseEntity<CensusDocumentDTO> getCensusDocument(@PathVariable Long id) {
        log.debug("REST request to get CensusDocument : {}", id);
        Optional<CensusDocumentDTO> censusDocumentDTO = censusDocumentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(censusDocumentDTO);
    }

    /**
     * {@code DELETE  /census-documents/:id} : delete the "id" censusDocument.
     *
     * @param id the id of the censusDocumentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/census-documents/{id}")
    public ResponseEntity<Void> deleteCensusDocument(@PathVariable Long id) {
        log.debug("REST request to delete CensusDocument : {}", id);
        censusDocumentService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
