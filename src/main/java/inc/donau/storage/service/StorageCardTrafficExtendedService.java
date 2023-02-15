package inc.donau.storage.service;

import inc.donau.storage.repository.StorageCardTrafficExtendedRepository;
import inc.donau.storage.service.dto.StorageCardTrafficDTO;
import inc.donau.storage.service.mapper.StorageCardTrafficMapper;
import javax.transaction.Transactional;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class StorageCardTrafficExtendedService extends StorageCardTrafficService {

    private final StorageCardTrafficExtendedRepository storageCardTrafficExtendedRepository;

    private final StorageCardExtendedService storageCardExtendedService;

    public StorageCardTrafficExtendedService(
        StorageCardTrafficExtendedRepository storageCardTrafficRepository,
        StorageCardTrafficMapper storageCardTrafficMapper,
        @Lazy StorageCardExtendedService storageCardExtendedService
    ) {
        super(storageCardTrafficRepository, storageCardTrafficMapper);
        this.storageCardTrafficExtendedRepository = storageCardTrafficRepository;
        this.storageCardExtendedService = storageCardExtendedService;
    }

    @Override
    public StorageCardTrafficDTO save(StorageCardTrafficDTO storageCardTrafficDTO) {
        StorageCardTrafficDTO newStorageCardTrafficDTO = super.save(storageCardTrafficDTO);

        storageCardExtendedService.updateValues(storageCardTrafficDTO);

        return newStorageCardTrafficDTO;
    }
}
