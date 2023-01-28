package inc.donau.storage.service;

import inc.donau.storage.domain.Company;
import inc.donau.storage.repository.CompanyExtendedRepository;
import inc.donau.storage.repository.CompanyRepository;
import inc.donau.storage.service.dto.CompanyDTO;
import inc.donau.storage.service.mapper.CompanyMapper;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CompanyExtendedService extends CompanyService {

    private final CompanyExtendedRepository companyExtendedRepository;

    private final LegalEntityExtendedService legalEntityExtendedService;

    public CompanyExtendedService(
        CompanyExtendedRepository companyRepository,
        CompanyMapper companyMapper,
        LegalEntityExtendedService legalEntityExtendedService
    ) {
        super(companyRepository, companyMapper);
        this.companyExtendedRepository = companyRepository;
        this.legalEntityExtendedService = legalEntityExtendedService;
    }

    @Override
    public CompanyDTO save(CompanyDTO companyDTO) {
        if (companyDTO.getLegalEntityInfo().getId() == null) companyDTO.setLegalEntityInfo(
            legalEntityExtendedService.save(companyDTO.getLegalEntityInfo())
        );
        return super.save(companyDTO);
    }
}
