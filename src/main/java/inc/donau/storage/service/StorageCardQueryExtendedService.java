package inc.donau.storage.service;

import inc.donau.storage.repository.StorageCardExtendedRepository;
import inc.donau.storage.repository.StorageCardRepository;
import inc.donau.storage.service.mapper.StorageCardMapper;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class StorageCardQueryExtendedService extends StorageCardQueryService {

    private final StorageCardExtendedRepository storageCardExtendedRepository;

    public StorageCardQueryExtendedService(StorageCardExtendedRepository storageCardRepository, StorageCardMapper storageCardMapper) {
        super(storageCardRepository, storageCardMapper);
        this.storageCardExtendedRepository = storageCardRepository;
    }
}
