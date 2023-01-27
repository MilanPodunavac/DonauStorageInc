package inc.donau.storage.service;

import inc.donau.storage.repository.BusinessYearExtendedRepository;
import inc.donau.storage.repository.BusinessYearRepository;
import inc.donau.storage.service.mapper.BusinessYearMapper;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class BusinessYearExtendedService extends BusinessYearService {

    private final BusinessYearExtendedRepository businessYearExtendedRepository;

    public BusinessYearExtendedService(BusinessYearExtendedRepository businessYearRepository, BusinessYearMapper businessYearMapper) {
        super(businessYearRepository, businessYearMapper);
        this.businessYearExtendedRepository = businessYearRepository;
    }
}
