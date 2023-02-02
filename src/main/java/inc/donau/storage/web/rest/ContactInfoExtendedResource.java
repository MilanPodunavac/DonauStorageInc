package inc.donau.storage.web.rest;

import inc.donau.storage.repository.ContactInfoExtendedRepository;
import inc.donau.storage.repository.ContactInfoRepository;
import inc.donau.storage.service.ContactInfoExtendedService;
import inc.donau.storage.service.ContactInfoQueryExtendedService;
import inc.donau.storage.service.ContactInfoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/xt")
public class ContactInfoExtendedResource extends ContactInfoResource {

    private final ContactInfoExtendedService contactInfoExtendedService;
    private final ContactInfoExtendedRepository contactInfoExtendedRepository;
    private final ContactInfoQueryExtendedService contactInfoQueryExtendedService;

    public ContactInfoExtendedResource(
        ContactInfoExtendedService contactInfoService,
        ContactInfoExtendedRepository contactInfoRepository,
        ContactInfoQueryExtendedService contactInfoQueryExtendedService
    ) {
        super(contactInfoService, contactInfoRepository, contactInfoQueryExtendedService);
        this.contactInfoExtendedService = contactInfoService;
        this.contactInfoExtendedRepository = contactInfoRepository;
        this.contactInfoQueryExtendedService = contactInfoQueryExtendedService;
    }
}
