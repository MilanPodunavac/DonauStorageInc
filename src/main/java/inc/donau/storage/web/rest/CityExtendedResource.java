package inc.donau.storage.web.rest;

import inc.donau.storage.repository.AddressExtendedRepository;
import inc.donau.storage.repository.CityExtendedRepository;
import inc.donau.storage.repository.CityRepository;
import inc.donau.storage.service.AddressQueryExtendedService;
import inc.donau.storage.service.CityExtendedService;
import inc.donau.storage.service.CityQueryExtendedService;
import inc.donau.storage.service.CityService;
import inc.donau.storage.service.criteria.AddressCriteria;
import inc.donau.storage.service.criteria.BusinessPartnerCriteria;
import inc.donau.storage.service.dto.AddressDTO;
import inc.donau.storage.service.dto.BusinessPartnerDTO;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.web.util.HeaderUtil;

@RestController
@RequestMapping("/api/xt")
public class CityExtendedResource extends CityResource {

    private static final String ENTITY_NAME = "city";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CityExtendedService cityExtendedService;
    private final CityExtendedRepository cityExtendedRepository;
    private final CityQueryExtendedService cityQueryExtendedService;

    private final AddressQueryExtendedService addressQueryExtendedService;

    public CityExtendedResource(
        CityExtendedService cityService,
        CityExtendedRepository cityRepository,
        CityQueryExtendedService cityQueryExtendedService,
        AddressQueryExtendedService addressQueryExtendedService
    ) {
        super(cityService, cityRepository, cityQueryExtendedService);
        this.cityExtendedService = cityService;
        this.cityExtendedRepository = cityRepository;
        this.cityQueryExtendedService = cityQueryExtendedService;
        this.addressQueryExtendedService = addressQueryExtendedService;
    }

    @Override
    public ResponseEntity<Void> deleteCity(@PathVariable Long id) {
        LongFilter longFilter = new LongFilter();
        longFilter.setEquals(id);
        AddressCriteria addressCriteria = new AddressCriteria();
        addressCriteria.setCityId(longFilter);
        List<AddressDTO> addresses = addressQueryExtendedService.findByCriteria(addressCriteria);
        if (!addresses.isEmpty()) return ResponseEntity
            .badRequest()
            .headers(
                HeaderUtil.createFailureAlert(
                    applicationName,
                    true,
                    ENTITY_NAME,
                    "cityAsssociationAddress",
                    "An address " +
                    addresses.get(0).getStreetName() +
                    " " +
                    addresses.get(0).getStreetCode() +
                    ", " +
                    addresses.get(0).getCity().getName() +
                    " (id: " +
                    addresses.get(0).getId() +
                    ") " +
                    "is in this city, it cannot be deleted"
                )
            )
            .build();

        return super.deleteCity(id);
    }
}
