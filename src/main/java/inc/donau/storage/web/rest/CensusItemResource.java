package inc.donau.storage.web.rest;

import inc.donau.storage.repository.CensusItemRepository;
import inc.donau.storage.service.CensusItemQueryService;
import inc.donau.storage.service.CensusItemService;
import inc.donau.storage.service.criteria.CensusItemCriteria;
import inc.donau.storage.service.dto.CensusItemDTO;
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
 * REST controller for managing {@link inc.donau.storage.domain.CensusItem}.
 */
@RestController
@RequestMapping("/api")
public class CensusItemResource {

    private final Logger log = LoggerFactory.getLogger(CensusItemResource.class);

    private static final String ENTITY_NAME = "censusItem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CensusItemService censusItemService;

    private final CensusItemRepository censusItemRepository;

    private final CensusItemQueryService censusItemQueryService;

    public CensusItemResource(
        CensusItemService censusItemService,
        CensusItemRepository censusItemRepository,
        CensusItemQueryService censusItemQueryService
    ) {
        this.censusItemService = censusItemService;
        this.censusItemRepository = censusItemRepository;
        this.censusItemQueryService = censusItemQueryService;
    }

    /**
     * {@code POST  /census-items} : Create a new censusItem.
     *
     * @param censusItemDTO the censusItemDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new censusItemDTO, or with status {@code 400 (Bad Request)} if the censusItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/census-items")
    public ResponseEntity<CensusItemDTO> createCensusItem(@Valid @RequestBody CensusItemDTO censusItemDTO) throws URISyntaxException {
        log.debug("REST request to save CensusItem : {}", censusItemDTO);
        if (censusItemDTO.getId() != null) {
            throw new BadRequestAlertException("A new censusItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CensusItemDTO result = censusItemService.save(censusItemDTO);
        return ResponseEntity
            .created(new URI("/api/census-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /census-items/:id} : Updates an existing censusItem.
     *
     * @param id the id of the censusItemDTO to save.
     * @param censusItemDTO the censusItemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated censusItemDTO,
     * or with status {@code 400 (Bad Request)} if the censusItemDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the censusItemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/census-items/{id}")
    public ResponseEntity<CensusItemDTO> updateCensusItem(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CensusItemDTO censusItemDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CensusItem : {}, {}", id, censusItemDTO);
        if (censusItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, censusItemDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!censusItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CensusItemDTO result = censusItemService.update(censusItemDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, censusItemDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /census-items/:id} : Partial updates given fields of an existing censusItem, field will ignore if it is null
     *
     * @param id the id of the censusItemDTO to save.
     * @param censusItemDTO the censusItemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated censusItemDTO,
     * or with status {@code 400 (Bad Request)} if the censusItemDTO is not valid,
     * or with status {@code 404 (Not Found)} if the censusItemDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the censusItemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/census-items/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CensusItemDTO> partialUpdateCensusItem(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CensusItemDTO censusItemDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CensusItem partially : {}, {}", id, censusItemDTO);
        if (censusItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, censusItemDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!censusItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CensusItemDTO> result = censusItemService.partialUpdate(censusItemDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, censusItemDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /census-items} : get all the censusItems.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of censusItems in body.
     */
    @GetMapping("/census-items")
    public ResponseEntity<List<CensusItemDTO>> getAllCensusItems(
        CensusItemCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get CensusItems by criteria: {}", criteria);
        Page<CensusItemDTO> page = censusItemQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /census-items/count} : count all the censusItems.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/census-items/count")
    public ResponseEntity<Long> countCensusItems(CensusItemCriteria criteria) {
        log.debug("REST request to count CensusItems by criteria: {}", criteria);
        return ResponseEntity.ok().body(censusItemQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /census-items/:id} : get the "id" censusItem.
     *
     * @param id the id of the censusItemDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the censusItemDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/census-items/{id}")
    public ResponseEntity<CensusItemDTO> getCensusItem(@PathVariable Long id) {
        log.debug("REST request to get CensusItem : {}", id);
        Optional<CensusItemDTO> censusItemDTO = censusItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(censusItemDTO);
    }

    /**
     * {@code DELETE  /census-items/:id} : delete the "id" censusItem.
     *
     * @param id the id of the censusItemDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/census-items/{id}")
    public ResponseEntity<Void> deleteCensusItem(@PathVariable Long id) {
        log.debug("REST request to delete CensusItem : {}", id);
        censusItemService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
