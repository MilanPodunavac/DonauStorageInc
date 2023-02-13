package inc.donau.storage.web.rest;

import inc.donau.storage.repository.CensusDocumentExtendedRepository;
import inc.donau.storage.repository.CensusDocumentRepository;
import inc.donau.storage.service.CensusDocumentExtendedService;
import inc.donau.storage.service.CensusDocumentQueryExtendedService;
import inc.donau.storage.service.CensusDocumentQueryService;
import inc.donau.storage.service.CensusDocumentService;
import inc.donau.storage.service.dto.CensusDocumentDTO;
import inc.donau.storage.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    public CensusDocumentExtendedResource(
        CensusDocumentExtendedService censusDocumentService,
        CensusDocumentExtendedRepository censusDocumentRepository,
        CensusDocumentQueryExtendedService censusDocumentQueryService
    ) {
        super(censusDocumentService, censusDocumentRepository, censusDocumentQueryService);
        this.censusDocumentExtendedService = censusDocumentService;
        this.censusDocumentExtendedRepository = censusDocumentRepository;
        this.censusDocumentQueryExtendedService = censusDocumentQueryService;
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
}
