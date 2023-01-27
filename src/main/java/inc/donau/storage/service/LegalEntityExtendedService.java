package inc.donau.storage.service;

import inc.donau.storage.repository.LegalEntityExtendedRepository;
import inc.donau.storage.repository.LegalEntityRepository;
import inc.donau.storage.service.mapper.LegalEntityMapper;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class LegalEntityExtendedService extends LegalEntityService {

    private final LegalEntityExtendedRepository legalEntityExtendedRepository;

    public LegalEntityExtendedService(LegalEntityExtendedRepository legalEntityRepository, LegalEntityMapper legalEntityMapper) {
        super(legalEntityRepository, legalEntityMapper);
        this.legalEntityExtendedRepository = legalEntityRepository;
    }
}
