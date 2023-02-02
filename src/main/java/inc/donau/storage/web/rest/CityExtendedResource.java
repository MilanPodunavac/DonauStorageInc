package inc.donau.storage.web.rest;

import inc.donau.storage.repository.CityExtendedRepository;
import inc.donau.storage.repository.CityRepository;
import inc.donau.storage.service.CityExtendedService;
import inc.donau.storage.service.CityQueryExtendedService;
import inc.donau.storage.service.CityService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/xt")
public class CityExtendedResource extends CityResource {

    private final CityExtendedService cityExtendedService;
    private final CityExtendedRepository cityExtendedRepository;
    private final CityQueryExtendedService cityQueryExtendedService;

    public CityExtendedResource(
        CityExtendedService cityService,
        CityExtendedRepository cityRepository,
        CityQueryExtendedService cityQueryExtendedService
    ) {
        super(cityService, cityRepository, cityQueryExtendedService);
        this.cityExtendedService = cityService;
        this.cityExtendedRepository = cityRepository;
        this.cityQueryExtendedService = cityQueryExtendedService;
    }
}
