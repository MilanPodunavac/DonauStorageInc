package inc.donau.storage.web.rest;

import inc.donau.storage.repository.BusinessContactRepository;
import inc.donau.storage.service.BusinessContactQueryService;
import inc.donau.storage.service.BusinessContactService;
import inc.donau.storage.service.criteria.BusinessContactCriteria;
import inc.donau.storage.service.dto.BusinessContactDTO;
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
 * REST controller for managing {@link inc.donau.storage.domain.BusinessContact}.
 */
@RestController
@RequestMapping("/api")
public class BusinessContactResource {

    private final Logger log = LoggerFactory.getLogger(BusinessContactResource.class);

    private static final String ENTITY_NAME = "businessContact";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BusinessContactService businessContactService;

    private final BusinessContactRepository businessContactRepository;

    private final BusinessContactQueryService businessContactQueryService;

    public BusinessContactResource(
        BusinessContactService businessContactService,
        BusinessContactRepository businessContactRepository,
        BusinessContactQueryService businessContactQueryService
    ) {
        this.businessContactService = businessContactService;
        this.businessContactRepository = businessContactRepository;
        this.businessContactQueryService = businessContactQueryService;
    }

    /**
     * {@code POST  /business-contacts} : Create a new businessContact.
     *
     * @param businessContactDTO the businessContactDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new businessContactDTO, or with status {@code 400 (Bad Request)} if the businessContact has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/business-contacts")
    public ResponseEntity<BusinessContactDTO> createBusinessContact(@Valid @RequestBody BusinessContactDTO businessContactDTO)
        throws URISyntaxException {
        log.debug("REST request to save BusinessContact : {}", businessContactDTO);
        if (businessContactDTO.getId() != null) {
            throw new BadRequestAlertException("A new businessContact cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BusinessContactDTO result = businessContactService.save(businessContactDTO);
        return ResponseEntity
            .created(new URI("/api/business-contacts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /business-contacts/:id} : Updates an existing businessContact.
     *
     * @param id the id of the businessContactDTO to save.
     * @param businessContactDTO the businessContactDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated businessContactDTO,
     * or with status {@code 400 (Bad Request)} if the businessContactDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the businessContactDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/business-contacts/{id}")
    public ResponseEntity<BusinessContactDTO> updateBusinessContact(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody BusinessContactDTO businessContactDTO
    ) throws URISyntaxException {
        log.debug("REST request to update BusinessContact : {}, {}", id, businessContactDTO);
        if (businessContactDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, businessContactDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!businessContactRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BusinessContactDTO result = businessContactService.update(businessContactDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, businessContactDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /business-contacts/:id} : Partial updates given fields of an existing businessContact, field will ignore if it is null
     *
     * @param id the id of the businessContactDTO to save.
     * @param businessContactDTO the businessContactDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated businessContactDTO,
     * or with status {@code 400 (Bad Request)} if the businessContactDTO is not valid,
     * or with status {@code 404 (Not Found)} if the businessContactDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the businessContactDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/business-contacts/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BusinessContactDTO> partialUpdateBusinessContact(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody BusinessContactDTO businessContactDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update BusinessContact partially : {}, {}", id, businessContactDTO);
        if (businessContactDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, businessContactDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!businessContactRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BusinessContactDTO> result = businessContactService.partialUpdate(businessContactDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, businessContactDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /business-contacts} : get all the businessContacts.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of businessContacts in body.
     */
    @GetMapping("/business-contacts")
    public ResponseEntity<List<BusinessContactDTO>> getAllBusinessContacts(BusinessContactCriteria criteria) {
        log.debug("REST request to get BusinessContacts by criteria: {}", criteria);
        List<BusinessContactDTO> entityList = businessContactQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /business-contacts/count} : count all the businessContacts.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/business-contacts/count")
    public ResponseEntity<Long> countBusinessContacts(BusinessContactCriteria criteria) {
        log.debug("REST request to count BusinessContacts by criteria: {}", criteria);
        return ResponseEntity.ok().body(businessContactQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /business-contacts/:id} : get the "id" businessContact.
     *
     * @param id the id of the businessContactDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the businessContactDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/business-contacts/{id}")
    public ResponseEntity<BusinessContactDTO> getBusinessContact(@PathVariable Long id) {
        log.debug("REST request to get BusinessContact : {}", id);
        Optional<BusinessContactDTO> businessContactDTO = businessContactService.findOne(id);
        return ResponseUtil.wrapOrNotFound(businessContactDTO);
    }

    /**
     * {@code DELETE  /business-contacts/:id} : delete the "id" businessContact.
     *
     * @param id the id of the businessContactDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/business-contacts/{id}")
    public ResponseEntity<Void> deleteBusinessContact(@PathVariable Long id) {
        log.debug("REST request to delete BusinessContact : {}", id);
        businessContactService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
