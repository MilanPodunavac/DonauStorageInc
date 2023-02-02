package inc.donau.storage.web.rest;

import inc.donau.storage.repository.StorageCardTrafficExtendedRepository;
import inc.donau.storage.repository.StorageCardTrafficRepository;
import inc.donau.storage.service.StorageCardTrafficExtendedService;
import inc.donau.storage.service.StorageCardTrafficQueryExtendedService;
import inc.donau.storage.service.StorageCardTrafficService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/xt")
public class StorageCardTrafficExtendedResource extends StorageCardTrafficResource {

    private final StorageCardTrafficExtendedService storageCardTrafficExtendedService;
    private final StorageCardTrafficExtendedRepository storageCardTrafficExtendedRepository;
    private final StorageCardTrafficQueryExtendedService storageCardTrafficQueryExtendedService;

    public StorageCardTrafficExtendedResource(
        StorageCardTrafficExtendedService storageCardTrafficService,
        StorageCardTrafficExtendedRepository storageCardTrafficRepository,
        StorageCardTrafficQueryExtendedService storageCardTrafficQueryExtendedService
    ) {
        super(storageCardTrafficService, storageCardTrafficRepository, storageCardTrafficQueryExtendedService);
        this.storageCardTrafficExtendedService = storageCardTrafficService;
        this.storageCardTrafficExtendedRepository = storageCardTrafficRepository;
        this.storageCardTrafficQueryExtendedService = storageCardTrafficQueryExtendedService;
    }
}
