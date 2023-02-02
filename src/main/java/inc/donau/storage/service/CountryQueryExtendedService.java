package inc.donau.storage.service;

import inc.donau.storage.repository.CountryExtendedRepository;
import inc.donau.storage.repository.CountryRepository;
import inc.donau.storage.service.mapper.CountryMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CountryQueryExtendedService extends CountryQueryService {

    private final CountryExtendedRepository countryExtendedRepository;

    public CountryQueryExtendedService(CountryExtendedRepository countryRepository, CountryMapper countryMapper) {
        super(countryRepository, countryMapper);
        this.countryExtendedRepository = countryRepository;
    }
}
