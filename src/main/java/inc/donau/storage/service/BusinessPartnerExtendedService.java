package inc.donau.storage.service;

import inc.donau.storage.repository.BusinessPartnerExtendedRepository;
import inc.donau.storage.repository.BusinessPartnerRepository;
import inc.donau.storage.service.mapper.BusinessPartnerMapper;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class BusinessPartnerExtendedService extends BusinessPartnerService {

    private final BusinessPartnerExtendedRepository businessPartnerExtendedRepository;

    public BusinessPartnerExtendedService(
        BusinessPartnerExtendedRepository businessPartnerRepository,
        BusinessPartnerMapper businessPartnerMapper
    ) {
        super(businessPartnerRepository, businessPartnerMapper);
        this.businessPartnerExtendedRepository = businessPartnerRepository;
    }
}
