package inc.donau.storage.service;

import inc.donau.storage.repository.StorageCardTrafficExtendedRepository;
import inc.donau.storage.service.mapper.StorageCardTrafficMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class StorageCardTrafficQueryExtendedService extends StorageCardTrafficQueryService {

    private final StorageCardTrafficExtendedRepository storageCardTrafficExtendedRepository;

    public StorageCardTrafficQueryExtendedService(
        StorageCardTrafficExtendedRepository storageCardTrafficRepository,
        StorageCardTrafficMapper storageCardTrafficMapper
    ) {
        super(storageCardTrafficRepository, storageCardTrafficMapper);
        this.storageCardTrafficExtendedRepository = storageCardTrafficRepository;
    }
}
