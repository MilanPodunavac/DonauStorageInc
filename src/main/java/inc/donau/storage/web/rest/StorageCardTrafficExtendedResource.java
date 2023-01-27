package inc.donau.storage.web.rest;

import inc.donau.storage.repository.StorageCardTrafficExtendedRepository;
import inc.donau.storage.repository.StorageCardTrafficRepository;
import inc.donau.storage.service.StorageCardTrafficExtendedService;
import inc.donau.storage.service.StorageCardTrafficService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/xt")
public class StorageCardTrafficExtendedResource extends StorageCardTrafficResource {

    private final StorageCardTrafficExtendedService storageCardTrafficExtendedService;
    private final StorageCardTrafficExtendedRepository storageCardTrafficExtendedRepository;

    public StorageCardTrafficExtendedResource(
        StorageCardTrafficExtendedService storageCardTrafficService,
        StorageCardTrafficExtendedRepository storageCardTrafficRepository
    ) {
        super(storageCardTrafficService, storageCardTrafficRepository);
        this.storageCardTrafficExtendedService = storageCardTrafficService;
        this.storageCardTrafficExtendedRepository = storageCardTrafficRepository;
    }
}
