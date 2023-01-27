package inc.donau.storage.web.rest;

import inc.donau.storage.repository.CityExtendedRepository;
import inc.donau.storage.repository.CityRepository;
import inc.donau.storage.service.CityExtendedService;
import inc.donau.storage.service.CityService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/xt")
public class CityExtendedResource extends CityResource {

    private final CityExtendedService cityExtendedService;
    private final CityExtendedRepository cityExtendedRepository;

    public CityExtendedResource(CityExtendedService cityService, CityExtendedRepository cityRepository) {
        super(cityService, cityRepository);
        this.cityExtendedService = cityService;
        this.cityExtendedRepository = cityRepository;
    }
}
