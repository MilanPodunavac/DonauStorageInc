package inc.donau.storage.web.rest;

import inc.donau.storage.repository.StorageExtendedRepository;
import inc.donau.storage.repository.StorageRepository;
import inc.donau.storage.service.StorageExtendedService;
import inc.donau.storage.service.StorageQueryExtendedService;
import inc.donau.storage.service.StorageQueryService;
import inc.donau.storage.service.StorageService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/xt")
public class StorageExtendedResource extends StorageResource {

    private final StorageExtendedService storageExtendedService;
    private final StorageExtendedRepository storageExtendedRepository;
    private final StorageQueryExtendedService storageQueryExtendedService;

    public StorageExtendedResource(
        StorageExtendedService storageService,
        StorageExtendedRepository storageRepository,
        StorageQueryExtendedService storageQueryService
    ) {
        super(storageService, storageRepository, storageQueryService);
        this.storageExtendedService = storageService;
        this.storageExtendedRepository = storageRepository;
        this.storageQueryExtendedService = storageQueryService;
    }
}
