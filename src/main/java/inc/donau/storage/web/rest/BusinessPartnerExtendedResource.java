package inc.donau.storage.web.rest;

import inc.donau.storage.repository.BusinessPartnerExtendedRepository;
import inc.donau.storage.repository.BusinessPartnerRepository;
import inc.donau.storage.service.BusinessPartnerExtendedService;
import inc.donau.storage.service.BusinessPartnerQueryExtendedService;
import inc.donau.storage.service.BusinessPartnerService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/xt")
public class BusinessPartnerExtendedResource extends BusinessPartnerResource {

    private final BusinessPartnerExtendedService businessPartnerExtendedService;
    private final BusinessPartnerExtendedRepository businessPartnerExtendedRepository;
    private final BusinessPartnerQueryExtendedService businessPartnerQueryExtendedService;

    public BusinessPartnerExtendedResource(
        BusinessPartnerExtendedService businessPartnerService,
        BusinessPartnerExtendedRepository businessPartnerRepository,
        BusinessPartnerQueryExtendedService businessPartnerQueryExtendedService
    ) {
        super(businessPartnerService, businessPartnerRepository, businessPartnerQueryExtendedService);
        this.businessPartnerExtendedService = businessPartnerService;
        this.businessPartnerExtendedRepository = businessPartnerRepository;
        this.businessPartnerQueryExtendedService = businessPartnerQueryExtendedService;
    }
}
