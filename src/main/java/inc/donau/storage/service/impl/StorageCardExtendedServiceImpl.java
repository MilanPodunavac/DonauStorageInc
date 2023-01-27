package inc.donau.storage.service.impl;

import inc.donau.storage.repository.StorageCardExtendedRepository;
import inc.donau.storage.repository.StorageCardRepository;
import inc.donau.storage.service.StorageCardExtendedService;
import inc.donau.storage.service.mapper.StorageCardMapper;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Primary
public class StorageCardExtendedServiceImpl extends StorageCardServiceImpl implements StorageCardExtendedService {

    private final StorageCardExtendedRepository storageCardExtendedRepository;

    public StorageCardExtendedServiceImpl(StorageCardExtendedRepository storageCardRepository, StorageCardMapper storageCardMapper) {
        super(storageCardRepository, storageCardMapper);
        this.storageCardExtendedRepository = storageCardRepository;
    }
}
