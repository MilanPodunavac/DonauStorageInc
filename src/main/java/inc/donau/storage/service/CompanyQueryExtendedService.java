package inc.donau.storage.service;

import inc.donau.storage.repository.CompanyExtendedRepository;
import inc.donau.storage.repository.CompanyRepository;
import inc.donau.storage.service.mapper.CompanyMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CompanyQueryExtendedService extends CompanyQueryService {

    private final CompanyExtendedRepository companyExtendedRepository;

    public CompanyQueryExtendedService(CompanyExtendedRepository companyRepository, CompanyMapper companyMapper) {
        super(companyRepository, companyMapper);
        this.companyExtendedRepository = companyRepository;
    }
}
