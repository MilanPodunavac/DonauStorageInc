package inc.donau.storage.web.rest;

import inc.donau.storage.repository.BusinessYearRepository;
import inc.donau.storage.service.BusinessYearQueryService;
import inc.donau.storage.service.BusinessYearService;
import inc.donau.storage.service.criteria.BusinessYearCriteria;
import inc.donau.storage.service.dto.BusinessYearDTO;
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
 * REST controller for managing {@link inc.donau.storage.domain.BusinessYear}.
 */
@RestController
@RequestMapping("/api")
public class BusinessYearResource {

    private final Logger log = LoggerFactory.getLogger(BusinessYearResource.class);

    private static final String ENTITY_NAME = "businessYear";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BusinessYearService businessYearService;

    private final BusinessYearRepository businessYearRepository;

    private final BusinessYearQueryService businessYearQueryService;

    public BusinessYearResource(
        BusinessYearService businessYearService,
        BusinessYearRepository businessYearRepository,
        BusinessYearQueryService businessYearQueryService
    ) {
        this.businessYearService = businessYearService;
        this.businessYearRepository = businessYearRepository;
        this.businessYearQueryService = businessYearQueryService;
    }

    /**
     * {@code POST  /business-years} : Create a new businessYear.
     *
     * @param businessYearDTO the businessYearDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new businessYearDTO, or with status {@code 400 (Bad Request)} if the businessYear has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/business-years")
    public ResponseEntity<BusinessYearDTO> createBusinessYear(@Valid @RequestBody BusinessYearDTO businessYearDTO)
        throws URISyntaxException {
        log.debug("REST request to save BusinessYear : {}", businessYearDTO);
        if (businessYearDTO.getId() != null) {
            throw new BadRequestAlertException("A new businessYear cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BusinessYearDTO result = businessYearService.save(businessYearDTO);
        return ResponseEntity
            .created(new URI("/api/business-years/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /business-years/:id} : Updates an existing businessYear.
     *
     * @param id the id of the businessYearDTO to save.
     * @param businessYearDTO the businessYearDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated businessYearDTO,
     * or with status {@code 400 (Bad Request)} if the businessYearDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the businessYearDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/business-years/{id}")
    public ResponseEntity<BusinessYearDTO> updateBusinessYear(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody BusinessYearDTO businessYearDTO
    ) throws URISyntaxException {
        log.debug("REST request to update BusinessYear : {}, {}", id, businessYearDTO);
        if (businessYearDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, businessYearDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!businessYearRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BusinessYearDTO result = businessYearService.update(businessYearDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, businessYearDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /business-years/:id} : Partial updates given fields of an existing businessYear, field will ignore if it is null
     *
     * @param id the id of the businessYearDTO to save.
     * @param businessYearDTO the businessYearDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated businessYearDTO,
     * or with status {@code 400 (Bad Request)} if the businessYearDTO is not valid,
     * or with status {@code 404 (Not Found)} if the businessYearDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the businessYearDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/business-years/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BusinessYearDTO> partialUpdateBusinessYear(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody BusinessYearDTO businessYearDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update BusinessYear partially : {}, {}", id, businessYearDTO);
        if (businessYearDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, businessYearDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!businessYearRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BusinessYearDTO> result = businessYearService.partialUpdate(businessYearDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, businessYearDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /business-years} : get all the businessYears.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of businessYears in body.
     */
    @GetMapping("/business-years")
    public ResponseEntity<List<BusinessYearDTO>> getAllBusinessYears(
        BusinessYearCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get BusinessYears by criteria: {}", criteria);
        Page<BusinessYearDTO> page = businessYearQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /business-years/count} : count all the businessYears.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/business-years/count")
    public ResponseEntity<Long> countBusinessYears(BusinessYearCriteria criteria) {
        log.debug("REST request to count BusinessYears by criteria: {}", criteria);
        return ResponseEntity.ok().body(businessYearQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /business-years/:id} : get the "id" businessYear.
     *
     * @param id the id of the businessYearDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the businessYearDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/business-years/{id}")
    public ResponseEntity<BusinessYearDTO> getBusinessYear(@PathVariable Long id) {
        log.debug("REST request to get BusinessYear : {}", id);
        Optional<BusinessYearDTO> businessYearDTO = businessYearService.findOne(id);
        return ResponseUtil.wrapOrNotFound(businessYearDTO);
    }

    /**
     * {@code DELETE  /business-years/:id} : delete the "id" businessYear.
     *
     * @param id the id of the businessYearDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/business-years/{id}")
    public ResponseEntity<Void> deleteBusinessYear(@PathVariable Long id) {
        log.debug("REST request to delete BusinessYear : {}", id);
        businessYearService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
