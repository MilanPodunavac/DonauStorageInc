package inc.donau.storage.web.rest;

import inc.donau.storage.repository.CensusItemExtendedRepository;
import inc.donau.storage.repository.CensusItemRepository;
import inc.donau.storage.service.CensusItemExtendedService;
import inc.donau.storage.service.CensusItemQueryExtendedService;
import inc.donau.storage.service.CensusItemService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/xt")
public class CensusItemExtendedResource extends CensusItemResource {

    private final CensusItemExtendedService censusItemExtendedService;
    private final CensusItemExtendedRepository censusItemExtendedRepository;
    private final CensusItemQueryExtendedService censusItemQueryExtendedService;

    public CensusItemExtendedResource(
        CensusItemExtendedService censusItemService,
        CensusItemExtendedRepository censusItemRepository,
        CensusItemQueryExtendedService censusItemQueryExtendedService
    ) {
        super(censusItemService, censusItemRepository, censusItemQueryExtendedService);
        this.censusItemExtendedService = censusItemService;
        this.censusItemExtendedRepository = censusItemRepository;
        this.censusItemQueryExtendedService = censusItemQueryExtendedService;
    }
}
