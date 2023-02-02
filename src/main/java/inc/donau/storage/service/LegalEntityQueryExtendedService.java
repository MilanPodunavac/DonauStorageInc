package inc.donau.storage.service;

import inc.donau.storage.repository.LegalEntityExtendedRepository;
import inc.donau.storage.repository.LegalEntityRepository;
import inc.donau.storage.service.mapper.LegalEntityMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LegalEntityQueryExtendedService extends LegalEntityQueryService {

    private final LegalEntityExtendedRepository legalEntityExtendedRepository;

    public LegalEntityQueryExtendedService(LegalEntityExtendedRepository legalEntityRepository, LegalEntityMapper legalEntityMapper) {
        super(legalEntityRepository, legalEntityMapper);
        this.legalEntityExtendedRepository = legalEntityRepository;
    }
}
