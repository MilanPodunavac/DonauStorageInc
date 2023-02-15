package inc.donau.storage.web.rest;

import inc.donau.storage.domain.enumeration.CensusDocumentStatus;
import inc.donau.storage.repository.CensusDocumentExtendedRepository;
import inc.donau.storage.repository.CensusDocumentRepository;
import inc.donau.storage.service.*;
import inc.donau.storage.service.criteria.BusinessYearCriteria;
import inc.donau.storage.service.dto.BusinessYearDTO;
import inc.donau.storage.service.dto.CensusDocumentDTO;
import inc.donau.storage.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.web.util.HeaderUtil;

@RestController
@RequestMapping("/api/xt")
public class CensusDocumentExtendedResource extends CensusDocumentResource {

    private final Logger log = LoggerFactory.getLogger(CensusDocumentResource.class);

    private static final String ENTITY_NAME = "censusDocument";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CensusDocumentExtendedService censusDocumentExtendedService;
    private final CensusDocumentExtendedRepository censusDocumentExtendedRepository;
    private final CensusDocumentQueryExtendedService censusDocumentQueryExtendedService;

    private final BusinessYearQueryExtendedService businessYearQueryExtendedService;

    public CensusDocumentExtendedResource(
        CensusDocumentExtendedService censusDocumentService,
        CensusDocumentExtendedRepository censusDocumentRepository,
        CensusDocumentQueryExtendedService censusDocumentQueryService,
        BusinessYearQueryExtendedService businessYearQueryExtendedService
    ) {
        super(censusDocumentService, censusDocumentRepository, censusDocumentQueryService);
        this.censusDocumentExtendedService = censusDocumentService;
        this.censusDocumentExtendedRepository = censusDocumentRepository;
        this.censusDocumentQueryExtendedService = censusDocumentQueryService;
        this.businessYearQueryExtendedService = businessYearQueryExtendedService;
    }

    @Override
    public ResponseEntity<CensusDocumentDTO> createCensusDocument(@Valid @RequestBody CensusDocumentDTO censusDocumentDTO)
        throws URISyntaxException {
        log.debug("REST request to save CensusDocument : {}", censusDocumentDTO);
        if (censusDocumentDTO.getId() != null) {
            throw new BadRequestAlertException("A new censusDocument cannot already have an ID", ENTITY_NAME, "idexists");
        }

        checkEmployeesFromCensus(censusDocumentDTO);

        CensusDocumentDTO result = censusDocumentExtendedService.save(censusDocumentDTO);
        return ResponseEntity
            .created(new URI("/api/census-documents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @Override
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

        if (!censusDocumentExtendedRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        checkEmployeesFromCensus(censusDocumentDTO);

        CensusDocumentDTO result = censusDocumentExtendedService.update(censusDocumentDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, censusDocumentDTO.getId().toString()))
            .body(result);
    }

    private void checkEmployeesFromCensus(@RequestBody @Valid CensusDocumentDTO censusDocumentDTO) {
        if (censusDocumentDTO.getPresident().getId() == censusDocumentDTO.getDeputy().getId()) {
            throw new BadRequestAlertException("President and deputy cannot be the same", ENTITY_NAME, "presidentDeputySameError");
        }

        if (censusDocumentDTO.getPresident().getId() == censusDocumentDTO.getCensusTaker().getId()) {
            throw new BadRequestAlertException(
                "President and census taker cannot be the same",
                ENTITY_NAME,
                "presidentCensusTakerSameError"
            );
        }

        if (censusDocumentDTO.getDeputy().getId() == censusDocumentDTO.getCensusTaker().getId()) {
            throw new BadRequestAlertException("Deputy and census taker cannot be the same", ENTITY_NAME, "deputyCensusTakerSameError");
        }
    }

    @PutMapping("/census-documents/account/{id}")
    public ResponseEntity<CensusDocumentDTO> accountCensusDocument(@PathVariable(value = "id", required = false) final Long id)
        throws URISyntaxException {
        log.debug("REST request to update CensusDocument : {}", id);
        if (id == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }

        if (!censusDocumentExtendedRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CensusDocumentDTO censusDocumentDTO = censusDocumentExtendedService.findOne(id).get();

        if (censusDocumentDTO.getStatus() != CensusDocumentStatus.INCOMPLETE) {
            throw new BadRequestAlertException("Census is already completed, it cannot be accounted", ENTITY_NAME, "censusComplete");
        }

        BusinessYearCriteria businessYearCriteria = new BusinessYearCriteria();
        LongFilter companyFilter = new LongFilter();
        companyFilter.setEquals(censusDocumentDTO.getBusinessYear().getCompany().getId());
        businessYearCriteria.setCompanyId(companyFilter);
        LongFilter idFilter = new LongFilter();
        idFilter.setGreaterThan(censusDocumentDTO.getBusinessYear().getId());
        businessYearCriteria.setId(idFilter);
        List<BusinessYearDTO> businessYearDTOList = businessYearQueryExtendedService.findByCriteria(businessYearCriteria);
        if (businessYearDTOList.isEmpty()) {
            throw new BadRequestAlertException("There is no business year after this one", ENTITY_NAME, "noNextBusinessYear");
        }

        CensusDocumentDTO result = censusDocumentExtendedService.account(censusDocumentDTO, businessYearDTOList.get(0));
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, censusDocumentDTO.getId().toString()))
            .body(result);
    }
}
