package inc.donau.storage.service;

import inc.donau.storage.repository.StorageExtendedRepository;
import inc.donau.storage.repository.StorageRepository;
import inc.donau.storage.service.mapper.StorageMapper;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class StorageQueryExtendedService extends StorageQueryService {

    private final StorageExtendedRepository storageExtendedRepository;

    public StorageQueryExtendedService(StorageExtendedRepository storageRepository, StorageMapper storageMapper) {
        super(storageRepository, storageMapper);
        this.storageExtendedRepository = storageRepository;
    }
}
