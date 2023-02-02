package inc.donau.storage.service;

import inc.donau.storage.repository.CensusItemExtendedRepository;
import inc.donau.storage.repository.CensusItemRepository;
import inc.donau.storage.service.mapper.CensusItemMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CensusItemQueryExtendedService extends CensusItemQueryService {

    private final CensusItemExtendedRepository censusItemExtendedRepository;

    public CensusItemQueryExtendedService(CensusItemExtendedRepository censusItemRepository, CensusItemMapper censusItemMapper) {
        super(censusItemRepository, censusItemMapper);
        this.censusItemExtendedRepository = censusItemRepository;
    }
}
