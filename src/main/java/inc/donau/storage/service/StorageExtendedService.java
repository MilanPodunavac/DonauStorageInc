package inc.donau.storage.service;

import inc.donau.storage.repository.StorageExtendedRepository;
import inc.donau.storage.repository.StorageRepository;
import inc.donau.storage.service.dto.StorageDTO;
import inc.donau.storage.service.mapper.StorageMapper;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class StorageExtendedService extends StorageService {

    private final StorageExtendedRepository storageExtendedRepository;

    private final AddressExtendedService addressExtendedService;

    public StorageExtendedService(
        StorageExtendedRepository storageRepository,
        StorageMapper storageMapper,
        AddressExtendedService addressExtendedService
    ) {
        super(storageRepository, storageMapper);
        this.storageExtendedRepository = storageRepository;
        this.addressExtendedService = addressExtendedService;
    }

    @Override
    public StorageDTO save(StorageDTO storageDTO) {
        if (storageDTO.getAddress().getId() == null) storageDTO.setAddress(addressExtendedService.save(storageDTO.getAddress()));
        return super.save(storageDTO);
    }
}
