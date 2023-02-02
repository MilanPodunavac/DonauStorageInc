package inc.donau.storage.service;

import inc.donau.storage.repository.BusinessPartnerExtendedRepository;
import inc.donau.storage.repository.BusinessPartnerRepository;
import inc.donau.storage.service.mapper.BusinessPartnerMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BusinessPartnerQueryExtendedService extends BusinessPartnerQueryService {

    private final BusinessPartnerExtendedRepository businessPartnerExtendedRepository;

    public BusinessPartnerQueryExtendedService(
        BusinessPartnerExtendedRepository businessPartnerRepository,
        BusinessPartnerMapper businessPartnerMapper
    ) {
        super(businessPartnerRepository, businessPartnerMapper);
        this.businessPartnerExtendedRepository = businessPartnerRepository;
    }
}
