package inc.donau.storage.web.rest;

import inc.donau.storage.repository.BusinessContactExtendedRepository;
import inc.donau.storage.repository.BusinessContactRepository;
import inc.donau.storage.service.BusinessContactExtendedService;
import inc.donau.storage.service.BusinessContactQueryExtendedService;
import inc.donau.storage.service.BusinessContactService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/xt")
public class BusinessContactExtendedResource extends BusinessContactResource {

    private final BusinessContactExtendedService businessContactExtendedService;
    private final BusinessContactExtendedRepository businessContactExtendedRepository;
    private final BusinessContactQueryExtendedService businessContactQueryExtendedService;

    public BusinessContactExtendedResource(
        BusinessContactExtendedService businessContactService,
        BusinessContactExtendedRepository businessContactRepository,
        BusinessContactQueryExtendedService businessContactQueryExtendedService
    ) {
        super(businessContactService, businessContactRepository, businessContactQueryExtendedService);
        this.businessContactExtendedService = businessContactService;
        this.businessContactExtendedRepository = businessContactRepository;
        this.businessContactQueryExtendedService = businessContactQueryExtendedService;
    }
}
