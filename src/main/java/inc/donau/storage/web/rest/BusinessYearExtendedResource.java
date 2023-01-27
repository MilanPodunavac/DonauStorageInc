package inc.donau.storage.web.rest;

import inc.donau.storage.repository.BusinessYearExtendedRepository;
import inc.donau.storage.repository.BusinessYearRepository;
import inc.donau.storage.service.BusinessYearExtendedService;
import inc.donau.storage.service.BusinessYearQueryExtendedService;
import inc.donau.storage.service.BusinessYearQueryService;
import inc.donau.storage.service.BusinessYearService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/xt")
public class BusinessYearExtendedResource extends BusinessYearResource {

    private final BusinessYearExtendedService businessYearExtendedService;
    private final BusinessYearExtendedRepository businessYearExtendedRepository;
    private final BusinessYearQueryExtendedService businessYearQueryExtendedService;

    public BusinessYearExtendedResource(
        BusinessYearExtendedService businessYearService,
        BusinessYearExtendedRepository businessYearRepository,
        BusinessYearQueryExtendedService businessYearQueryService
    ) {
        super(businessYearService, businessYearRepository, businessYearQueryService);
        this.businessYearExtendedService = businessYearService;
        this.businessYearExtendedRepository = businessYearRepository;
        this.businessYearQueryExtendedService = businessYearQueryService;
    }
}
