package inc.donau.storage.web.rest;

import inc.donau.storage.repository.LegalEntityExtendedRepository;
import inc.donau.storage.repository.LegalEntityRepository;
import inc.donau.storage.service.LegalEntityExtendedService;
import inc.donau.storage.service.LegalEntityQueryExtendedService;
import inc.donau.storage.service.LegalEntityService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/xt")
public class LegalEntityExtendedResource extends LegalEntityResource {

    private final LegalEntityExtendedService legalEntityExtendedService;
    private final LegalEntityExtendedRepository legalEntityExtendedRepository;
    private final LegalEntityQueryExtendedService legalEntityQueryExtendedService;

    public LegalEntityExtendedResource(
        LegalEntityExtendedService legalEntityService,
        LegalEntityExtendedRepository legalEntityRepository,
        LegalEntityQueryExtendedService legalEntityQueryExtendedService
    ) {
        super(legalEntityService, legalEntityRepository, legalEntityQueryExtendedService);
        this.legalEntityExtendedService = legalEntityService;
        this.legalEntityExtendedRepository = legalEntityRepository;
        this.legalEntityQueryExtendedService = legalEntityQueryExtendedService;
    }
}
