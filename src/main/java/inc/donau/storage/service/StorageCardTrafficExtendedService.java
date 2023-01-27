package inc.donau.storage.service;

import inc.donau.storage.repository.StorageCardTrafficExtendedRepository;
import inc.donau.storage.service.mapper.StorageCardTrafficMapper;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class StorageCardTrafficExtendedService extends StorageCardTrafficService {

    private final StorageCardTrafficExtendedRepository storageCardTrafficExtendedRepository;

    public StorageCardTrafficExtendedService(
        StorageCardTrafficExtendedRepository storageCardTrafficRepository,
        StorageCardTrafficMapper storageCardTrafficMapper
    ) {
        super(storageCardTrafficRepository, storageCardTrafficMapper);
        this.storageCardTrafficExtendedRepository = storageCardTrafficRepository;
    }
}
