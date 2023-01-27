package inc.donau.storage.service;

import inc.donau.storage.repository.CityExtendedRepository;
import inc.donau.storage.repository.CityRepository;
import inc.donau.storage.service.mapper.CityMapper;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CityExtendedService extends CityService {

    private final CityExtendedRepository cityExtendedRepository;

    public CityExtendedService(CityExtendedRepository cityRepository, CityMapper cityMapper) {
        super(cityRepository, cityMapper);
        this.cityExtendedRepository = cityRepository;
    }
}
