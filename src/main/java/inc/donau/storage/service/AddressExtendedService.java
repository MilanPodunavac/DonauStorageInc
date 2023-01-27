package inc.donau.storage.service;

import inc.donau.storage.repository.AddressExtendedRepository;
import inc.donau.storage.repository.AddressRepository;
import inc.donau.storage.service.mapper.AddressMapper;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AddressExtendedService extends AddressService {

    private final AddressExtendedRepository addressExtendedRepository;

    public AddressExtendedService(AddressExtendedRepository addressRepository, AddressMapper addressMapper) {
        super(addressRepository, addressMapper);
        this.addressExtendedRepository = addressRepository;
    }
}
