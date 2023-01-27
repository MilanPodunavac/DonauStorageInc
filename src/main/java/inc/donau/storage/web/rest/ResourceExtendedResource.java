package inc.donau.storage.web.rest;

import inc.donau.storage.repository.ResourceExtendedRepository;
import inc.donau.storage.repository.ResourceRepository;
import inc.donau.storage.service.ResourceExtendedService;
import inc.donau.storage.service.ResourceQueryExtendedService;
import inc.donau.storage.service.ResourceQueryService;
import inc.donau.storage.service.ResourceService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/xt")
public class ResourceExtendedResource extends ResourceResource {

    private final ResourceExtendedService resourceExtendedService;
    private final ResourceExtendedRepository resourceExtendedRepository;
    private final ResourceQueryExtendedService resourceQueryExtendedService;

    public ResourceExtendedResource(
        ResourceExtendedService resourceService,
        ResourceExtendedRepository resourceRepository,
        ResourceQueryExtendedService resourceQueryService
    ) {
        super(resourceService, resourceRepository, resourceQueryService);
        this.resourceExtendedService = resourceService;
        this.resourceExtendedRepository = resourceRepository;
        this.resourceQueryExtendedService = resourceQueryService;
    }
}
