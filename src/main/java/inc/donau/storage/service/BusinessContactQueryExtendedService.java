package inc.donau.storage.service;

import inc.donau.storage.repository.BusinessContactExtendedRepository;
import inc.donau.storage.repository.BusinessContactRepository;
import inc.donau.storage.service.mapper.BusinessContactMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BusinessContactQueryExtendedService extends BusinessContactQueryService {

    private final BusinessContactExtendedRepository businessContactExtendedRepository;

    public BusinessContactQueryExtendedService(
        BusinessContactExtendedRepository businessContactRepository,
        BusinessContactMapper businessContactMapper
    ) {
        super(businessContactRepository, businessContactMapper);
        this.businessContactExtendedRepository = businessContactRepository;
    }
}
