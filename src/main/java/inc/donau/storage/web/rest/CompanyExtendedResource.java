package inc.donau.storage.web.rest;

import inc.donau.storage.repository.CompanyExtendedRepository;
import inc.donau.storage.repository.CompanyRepository;
import inc.donau.storage.service.CompanyExtendedService;
import inc.donau.storage.service.CompanyQueryExtendedService;
import inc.donau.storage.service.CompanyService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/xt")
public class CompanyExtendedResource extends CompanyResource {

    private final CompanyExtendedService companyExtendedService;
    private final CompanyExtendedRepository companyExtendedRepository;
    private final CompanyQueryExtendedService companyQueryExtendedService;

    public CompanyExtendedResource(
        CompanyExtendedService companyService,
        CompanyExtendedRepository companyRepository,
        CompanyQueryExtendedService companyQueryExtendedService
    ) {
        super(companyService, companyRepository, companyQueryExtendedService);
        this.companyExtendedService = companyService;
        this.companyExtendedRepository = companyRepository;
        this.companyQueryExtendedService = companyQueryExtendedService;
    }
}
