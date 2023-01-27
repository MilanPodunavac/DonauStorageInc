package inc.donau.storage.web.rest;

import inc.donau.storage.repository.CountryExtendedRepository;
import inc.donau.storage.repository.CountryRepository;
import inc.donau.storage.service.CountryExtendedService;
import inc.donau.storage.service.CountryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/xt")
public class CountryExtendedResource extends CountryResource {

    private final CountryExtendedService countryExtendedService;
    private final CountryExtendedRepository countryExtendedRepository;

    public CountryExtendedResource(CountryExtendedService countryService, CountryExtendedRepository countryRepository) {
        super(countryService, countryRepository);
        this.countryExtendedService = countryService;
        this.countryExtendedRepository = countryRepository;
    }
}
