package inc.donau.storage.service;

import inc.donau.storage.repository.CountryExtendedRepository;
import inc.donau.storage.repository.CountryRepository;
import inc.donau.storage.service.mapper.CountryMapper;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CountryExtendedService extends CountryService {

    private final CountryExtendedRepository countryExtendedRepository;

    public CountryExtendedService(CountryExtendedRepository countryRepository, CountryMapper countryMapper) {
        super(countryRepository, countryMapper);
        this.countryExtendedRepository = countryRepository;
    }
}
