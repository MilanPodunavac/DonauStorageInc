package inc.donau.storage.service;

import inc.donau.storage.repository.BusinessContactExtendedRepository;
import inc.donau.storage.repository.BusinessContactRepository;
import inc.donau.storage.service.mapper.BusinessContactMapper;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class BusinessContactExtendedService extends BusinessContactService {

    private final BusinessContactExtendedRepository businessContactExtendedRepository;

    public BusinessContactExtendedService(
        BusinessContactExtendedRepository businessContactRepository,
        BusinessContactMapper businessContactMapper
    ) {
        super(businessContactRepository, businessContactMapper);
        this.businessContactExtendedRepository = businessContactRepository;
    }
}
