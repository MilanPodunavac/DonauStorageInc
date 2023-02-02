package inc.donau.storage.service;

import inc.donau.storage.repository.CityExtendedRepository;
import inc.donau.storage.repository.CityRepository;
import inc.donau.storage.service.mapper.CityMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CityQueryExtendedService extends CityQueryService {

    private final CityExtendedRepository cityExtendedRepository;

    public CityQueryExtendedService(CityExtendedRepository cityRepository, CityMapper cityMapper) {
        super(cityRepository, cityMapper);
        this.cityExtendedRepository = cityRepository;
    }
}
