package inc.donau.storage.service;

import inc.donau.storage.domain.BusinessPartner;
import inc.donau.storage.repository.BusinessPartnerExtendedRepository;
import inc.donau.storage.repository.BusinessPartnerRepository;
import inc.donau.storage.service.dto.BusinessPartnerDTO;
import inc.donau.storage.service.mapper.BusinessPartnerMapper;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class BusinessPartnerExtendedService extends BusinessPartnerService {

    private final BusinessPartnerExtendedRepository businessPartnerExtendedRepository;

    private final BusinessContactExtendedService businessContactExtendedService;
    private final LegalEntityExtendedService legalEntityExtendedService;

    public BusinessPartnerExtendedService(
        BusinessPartnerExtendedRepository businessPartnerRepository,
        BusinessPartnerMapper businessPartnerMapper,
        BusinessContactExtendedService businessContactExtendedService,
        LegalEntityExtendedService legalEntityExtendedService
    ) {
        super(businessPartnerRepository, businessPartnerMapper);
        this.businessPartnerExtendedRepository = businessPartnerRepository;
        this.businessContactExtendedService = businessContactExtendedService;
        this.legalEntityExtendedService = legalEntityExtendedService;
    }

    @Override
    public BusinessPartnerDTO save(BusinessPartnerDTO businessPartnerDTO) {
        if (businessPartnerDTO.getLegalEntityInfo().getId() == null) businessPartnerDTO.setLegalEntityInfo(
            legalEntityExtendedService.save(businessPartnerDTO.getLegalEntityInfo())
        );
        if (businessPartnerDTO.getBusinessContact().getId() == null) businessPartnerDTO.setBusinessContact(
            businessContactExtendedService.save(businessPartnerDTO.getBusinessContact())
        );
        return super.save(businessPartnerDTO);
    }
}
