package inc.donau.storage.web.rest;

import inc.donau.storage.repository.StorageCardExtendedRepository;
import inc.donau.storage.repository.StorageCardRepository;
import inc.donau.storage.service.StorageCardExtendedService;
import inc.donau.storage.service.StorageCardQueryExtendedService;
import inc.donau.storage.service.StorageCardQueryService;
import inc.donau.storage.service.StorageCardService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/xt")
public class StorageCardExtendedResource extends StorageCardResource {

    private final StorageCardExtendedService storageCardExtendedService;
    private final StorageCardExtendedRepository storageCardExtendedRepository;
    private final StorageCardQueryExtendedService storageCardQueryExtendedService;

    public StorageCardExtendedResource(
        StorageCardExtendedService storageCardService,
        StorageCardExtendedRepository storageCardRepository,
        StorageCardQueryExtendedService storageCardQueryService
    ) {
        super(storageCardService, storageCardRepository, storageCardQueryService);
        this.storageCardExtendedService = storageCardService;
        this.storageCardExtendedRepository = storageCardRepository;
        this.storageCardQueryExtendedService = storageCardQueryService;
    }
}
