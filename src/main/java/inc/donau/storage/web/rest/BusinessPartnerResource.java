package inc.donau.storage.web.rest;

import inc.donau.storage.repository.BusinessPartnerRepository;
import inc.donau.storage.service.BusinessPartnerService;
import inc.donau.storage.service.dto.BusinessPartnerDTO;
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
 * REST controller for managing {@link inc.donau.storage.domain.BusinessPartner}.
 */
@RestController
@RequestMapping("/api")
public class BusinessPartnerResource {

    private final Logger log = LoggerFactory.getLogger(BusinessPartnerResource.class);

    private static final String ENTITY_NAME = "businessPartner";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BusinessPartnerService businessPartnerService;

    private final BusinessPartnerRepository businessPartnerRepository;

    public BusinessPartnerResource(BusinessPartnerService businessPartnerService, BusinessPartnerRepository businessPartnerRepository) {
        this.businessPartnerService = businessPartnerService;
        this.businessPartnerRepository = businessPartnerRepository;
    }

    /**
     * {@code POST  /business-partners} : Create a new businessPartner.
     *
     * @param businessPartnerDTO the businessPartnerDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new businessPartnerDTO, or with status {@code 400 (Bad Request)} if the businessPartner has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/business-partners")
    public ResponseEntity<BusinessPartnerDTO> createBusinessPartner(@Valid @RequestBody BusinessPartnerDTO businessPartnerDTO)
        throws URISyntaxException {
        log.debug("REST request to save BusinessPartner : {}", businessPartnerDTO);
        if (businessPartnerDTO.getId() != null) {
            throw new BadRequestAlertException("A new businessPartner cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BusinessPartnerDTO result = businessPartnerService.save(businessPartnerDTO);
        return ResponseEntity
            .created(new URI("/api/business-partners/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /business-partners/:id} : Updates an existing businessPartner.
     *
     * @param id the id of the businessPartnerDTO to save.
     * @param businessPartnerDTO the businessPartnerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated businessPartnerDTO,
     * or with status {@code 400 (Bad Request)} if the businessPartnerDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the businessPartnerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/business-partners/{id}")
    public ResponseEntity<BusinessPartnerDTO> updateBusinessPartner(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody BusinessPartnerDTO businessPartnerDTO
    ) throws URISyntaxException {
        log.debug("REST request to update BusinessPartner : {}, {}", id, businessPartnerDTO);
        if (businessPartnerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, businessPartnerDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!businessPartnerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BusinessPartnerDTO result = businessPartnerService.update(businessPartnerDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, businessPartnerDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /business-partners/:id} : Partial updates given fields of an existing businessPartner, field will ignore if it is null
     *
     * @param id the id of the businessPartnerDTO to save.
     * @param businessPartnerDTO the businessPartnerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated businessPartnerDTO,
     * or with status {@code 400 (Bad Request)} if the businessPartnerDTO is not valid,
     * or with status {@code 404 (Not Found)} if the businessPartnerDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the businessPartnerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/business-partners/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BusinessPartnerDTO> partialUpdateBusinessPartner(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody BusinessPartnerDTO businessPartnerDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update BusinessPartner partially : {}, {}", id, businessPartnerDTO);
        if (businessPartnerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, businessPartnerDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!businessPartnerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BusinessPartnerDTO> result = businessPartnerService.partialUpdate(businessPartnerDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, businessPartnerDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /business-partners} : get all the businessPartners.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of businessPartners in body.
     */
    @GetMapping("/business-partners")
    public ResponseEntity<List<BusinessPartnerDTO>> getAllBusinessPartners(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of BusinessPartners");
        Page<BusinessPartnerDTO> page = businessPartnerService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /business-partners/:id} : get the "id" businessPartner.
     *
     * @param id the id of the businessPartnerDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the businessPartnerDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/business-partners/{id}")
    public ResponseEntity<BusinessPartnerDTO> getBusinessPartner(@PathVariable Long id) {
        log.debug("REST request to get BusinessPartner : {}", id);
        Optional<BusinessPartnerDTO> businessPartnerDTO = businessPartnerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(businessPartnerDTO);
    }

    /**
     * {@code DELETE  /business-partners/:id} : delete the "id" businessPartner.
     *
     * @param id the id of the businessPartnerDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/business-partners/{id}")
    public ResponseEntity<Void> deleteBusinessPartner(@PathVariable Long id) {
        log.debug("REST request to delete BusinessPartner : {}", id);
        businessPartnerService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
