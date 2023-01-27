package inc.donau.storage.web.rest;

import inc.donau.storage.repository.CensusDocumentExtendedRepository;
import inc.donau.storage.repository.CensusDocumentRepository;
import inc.donau.storage.service.CensusDocumentExtendedService;
import inc.donau.storage.service.CensusDocumentQueryExtendedService;
import inc.donau.storage.service.CensusDocumentQueryService;
import inc.donau.storage.service.CensusDocumentService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/xt")
public class CensusDocumentExtendedResource extends CensusDocumentResource {

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
}
