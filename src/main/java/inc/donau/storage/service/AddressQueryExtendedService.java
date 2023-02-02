package inc.donau.storage.service;

import inc.donau.storage.repository.AddressExtendedRepository;
import inc.donau.storage.repository.AddressRepository;
import inc.donau.storage.service.mapper.AddressMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AddressQueryExtendedService extends AddressQueryService {

    private final AddressExtendedRepository addressExtendedRepository;

    public AddressQueryExtendedService(AddressExtendedRepository addressRepository, AddressMapper addressMapper) {
        super(addressRepository, addressMapper);
        this.addressExtendedRepository = addressRepository;
    }
}
