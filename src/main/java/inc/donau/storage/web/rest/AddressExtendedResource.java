package inc.donau.storage.web.rest;

import inc.donau.storage.repository.AddressExtendedRepository;
import inc.donau.storage.repository.AddressRepository;
import inc.donau.storage.service.AddressExtendedService;
import inc.donau.storage.service.AddressService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/xt")
public class AddressExtendedResource extends AddressResource {

    private final AddressExtendedService addressExtendedService;
    private final AddressExtendedRepository addressExtendedRepository;

    public AddressExtendedResource(AddressExtendedService addressService, AddressExtendedRepository addressRepository) {
        super(addressService, addressRepository);
        this.addressExtendedService = addressService;
        this.addressExtendedRepository = addressRepository;
    }
}
