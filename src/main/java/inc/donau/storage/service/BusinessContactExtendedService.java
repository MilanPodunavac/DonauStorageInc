package inc.donau.storage.service;

import inc.donau.storage.repository.BusinessContactExtendedRepository;
import inc.donau.storage.repository.BusinessContactRepository;
import inc.donau.storage.service.dto.BusinessContactDTO;
import inc.donau.storage.service.mapper.BusinessContactMapper;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class BusinessContactExtendedService extends BusinessContactService {

    private final BusinessContactExtendedRepository businessContactExtendedRepository;

    private final PersonExtendedService personExtendedService;

    public BusinessContactExtendedService(
        BusinessContactExtendedRepository businessContactRepository,
        BusinessContactMapper businessContactMapper,
        PersonExtendedService personExtendedService
    ) {
        super(businessContactRepository, businessContactMapper);
        this.businessContactExtendedRepository = businessContactRepository;
        this.personExtendedService = personExtendedService;
    }

    @Override
    public BusinessContactDTO save(BusinessContactDTO businessContactDTO) {
        if (businessContactDTO.getPersonalInfo().getId() == null) businessContactDTO.setPersonalInfo(
            personExtendedService.save(businessContactDTO.getPersonalInfo())
        );
        return super.save(businessContactDTO);
    }
}
