package inc.donau.storage.service;

import inc.donau.storage.repository.CensusItemExtendedRepository;
import inc.donau.storage.repository.CensusItemRepository;
import inc.donau.storage.service.mapper.CensusItemMapper;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CensusItemExtendedService extends CensusItemService {

    private final CensusItemExtendedRepository censusItemExtendedRepository;

    public CensusItemExtendedService(CensusItemExtendedRepository censusItemRepository, CensusItemMapper censusItemMapper) {
        super(censusItemRepository, censusItemMapper);
        this.censusItemExtendedRepository = censusItemRepository;
    }
}
