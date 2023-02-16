package inc.donau.storage.service;

import inc.donau.storage.repository.LegalEntityExtendedRepository;
import inc.donau.storage.repository.LegalEntityRepository;
import inc.donau.storage.service.dto.LegalEntityDTO;
import inc.donau.storage.service.mapper.LegalEntityMapper;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class LegalEntityExtendedService extends LegalEntityService {

    private final LegalEntityExtendedRepository legalEntityExtendedRepository;

    private final ContactInfoExtendedService contactInfoExtendedService;
    private final AddressExtendedService addressExtendedService;

    public LegalEntityExtendedService(
        LegalEntityExtendedRepository legalEntityRepository,
        LegalEntityMapper legalEntityMapper,
        ContactInfoExtendedService contactInfoExtendedService,
        AddressExtendedService addressExtendedService
    ) {
        super(legalEntityRepository, legalEntityMapper);
        this.legalEntityExtendedRepository = legalEntityRepository;
        this.contactInfoExtendedService = contactInfoExtendedService;
        this.addressExtendedService = addressExtendedService;
    }

    @Override
    public LegalEntityDTO save(LegalEntityDTO legalEntityDTO) {
        if (legalEntityDTO.getContactInfo().getId() == null) legalEntityDTO.setContactInfo(
            contactInfoExtendedService.save(legalEntityDTO.getContactInfo())
        );
        if (legalEntityDTO.getAddress().getId() == null) legalEntityDTO.setAddress(
            addressExtendedService.save(legalEntityDTO.getAddress())
        );
        return super.save(legalEntityDTO);
    }
}
