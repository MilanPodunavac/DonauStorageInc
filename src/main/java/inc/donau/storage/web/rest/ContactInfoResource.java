package inc.donau.storage.web.rest;

import inc.donau.storage.repository.ContactInfoRepository;
import inc.donau.storage.service.ContactInfoQueryService;
import inc.donau.storage.service.ContactInfoService;
import inc.donau.storage.service.criteria.ContactInfoCriteria;
import inc.donau.storage.service.dto.ContactInfoDTO;
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
 * REST controller for managing {@link inc.donau.storage.domain.ContactInfo}.
 */
@RestController
@RequestMapping("/api")
public class ContactInfoResource {

    private final Logger log = LoggerFactory.getLogger(ContactInfoResource.class);

    private static final String ENTITY_NAME = "contactInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ContactInfoService contactInfoService;

    private final ContactInfoRepository contactInfoRepository;

    private final ContactInfoQueryService contactInfoQueryService;

    public ContactInfoResource(
        ContactInfoService contactInfoService,
        ContactInfoRepository contactInfoRepository,
        ContactInfoQueryService contactInfoQueryService
    ) {
        this.contactInfoService = contactInfoService;
        this.contactInfoRepository = contactInfoRepository;
        this.contactInfoQueryService = contactInfoQueryService;
    }

    /**
     * {@code POST  /contact-infos} : Create a new contactInfo.
     *
     * @param contactInfoDTO the contactInfoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new contactInfoDTO, or with status {@code 400 (Bad Request)} if the contactInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/contact-infos")
    public ResponseEntity<ContactInfoDTO> createContactInfo(@Valid @RequestBody ContactInfoDTO contactInfoDTO) throws URISyntaxException {
        log.debug("REST request to save ContactInfo : {}", contactInfoDTO);
        if (contactInfoDTO.getId() != null) {
            throw new BadRequestAlertException("A new contactInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ContactInfoDTO result = contactInfoService.save(contactInfoDTO);
        return ResponseEntity
            .created(new URI("/api/contact-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /contact-infos/:id} : Updates an existing contactInfo.
     *
     * @param id the id of the contactInfoDTO to save.
     * @param contactInfoDTO the contactInfoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contactInfoDTO,
     * or with status {@code 400 (Bad Request)} if the contactInfoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the contactInfoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/contact-infos/{id}")
    public ResponseEntity<ContactInfoDTO> updateContactInfo(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ContactInfoDTO contactInfoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ContactInfo : {}, {}", id, contactInfoDTO);
        if (contactInfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, contactInfoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!contactInfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ContactInfoDTO result = contactInfoService.update(contactInfoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contactInfoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /contact-infos/:id} : Partial updates given fields of an existing contactInfo, field will ignore if it is null
     *
     * @param id the id of the contactInfoDTO to save.
     * @param contactInfoDTO the contactInfoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contactInfoDTO,
     * or with status {@code 400 (Bad Request)} if the contactInfoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the contactInfoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the contactInfoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/contact-infos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ContactInfoDTO> partialUpdateContactInfo(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ContactInfoDTO contactInfoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ContactInfo partially : {}, {}", id, contactInfoDTO);
        if (contactInfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, contactInfoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!contactInfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ContactInfoDTO> result = contactInfoService.partialUpdate(contactInfoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contactInfoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /contact-infos} : get all the contactInfos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of contactInfos in body.
     */
    @GetMapping("/contact-infos")
    public ResponseEntity<List<ContactInfoDTO>> getAllContactInfos(ContactInfoCriteria criteria) {
        log.debug("REST request to get ContactInfos by criteria: {}", criteria);
        List<ContactInfoDTO> entityList = contactInfoQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /contact-infos/count} : count all the contactInfos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/contact-infos/count")
    public ResponseEntity<Long> countContactInfos(ContactInfoCriteria criteria) {
        log.debug("REST request to count ContactInfos by criteria: {}", criteria);
        return ResponseEntity.ok().body(contactInfoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /contact-infos/:id} : get the "id" contactInfo.
     *
     * @param id the id of the contactInfoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the contactInfoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/contact-infos/{id}")
    public ResponseEntity<ContactInfoDTO> getContactInfo(@PathVariable Long id) {
        log.debug("REST request to get ContactInfo : {}", id);
        Optional<ContactInfoDTO> contactInfoDTO = contactInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(contactInfoDTO);
    }

    /**
     * {@code DELETE  /contact-infos/:id} : delete the "id" contactInfo.
     *
     * @param id the id of the contactInfoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/contact-infos/{id}")
    public ResponseEntity<Void> deleteContactInfo(@PathVariable Long id) {
        log.debug("REST request to delete ContactInfo : {}", id);
        contactInfoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
