package inc.donau.storage.service;

import inc.donau.storage.repository.CompanyExtendedRepository;
import inc.donau.storage.repository.CompanyRepository;
import inc.donau.storage.service.mapper.CompanyMapper;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CompanyExtendedService extends CompanyService {

    private final CompanyExtendedRepository companyExtendedRepository;

    public CompanyExtendedService(CompanyExtendedRepository companyRepository, CompanyMapper companyMapper) {
        super(companyRepository, companyMapper);
        this.companyExtendedRepository = companyRepository;
    }
}
