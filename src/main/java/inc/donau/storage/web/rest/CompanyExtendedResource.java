package inc.donau.storage.web.rest;

import inc.donau.storage.repository.CompanyExtendedRepository;
import inc.donau.storage.repository.CompanyRepository;
import inc.donau.storage.service.CompanyExtendedService;
import inc.donau.storage.service.CompanyService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/xt")
public class CompanyExtendedResource extends CompanyResource {

    private final CompanyExtendedService companyExtendedService;
    private final CompanyExtendedRepository companyExtendedRepository;

    public CompanyExtendedResource(CompanyExtendedService companyService, CompanyExtendedRepository companyRepository) {
        super(companyService, companyRepository);
        this.companyExtendedService = companyService;
        this.companyExtendedRepository = companyRepository;
    }
}
