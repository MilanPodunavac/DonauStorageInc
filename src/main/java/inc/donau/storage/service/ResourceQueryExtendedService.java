package inc.donau.storage.service;

import inc.donau.storage.repository.ResourceExtendedRepository;
import inc.donau.storage.repository.ResourceRepository;
import inc.donau.storage.service.mapper.ResourceMapper;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ResourceQueryExtendedService extends ResourceQueryService {

    private final ResourceExtendedRepository resourceExtendedRepository;

    public ResourceQueryExtendedService(ResourceExtendedRepository resourceRepository, ResourceMapper resourceMapper) {
        super(resourceRepository, resourceMapper);
        this.resourceExtendedRepository = resourceRepository;
    }
}
