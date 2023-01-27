package inc.donau.storage.service;

import inc.donau.storage.repository.ResourceExtendedRepository;
import inc.donau.storage.repository.ResourceRepository;
import inc.donau.storage.service.mapper.ResourceMapper;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ResourceExtendedService extends ResourceService {

    private final ResourceExtendedRepository resourceExtendedRepository;

    public ResourceExtendedService(ResourceExtendedRepository resourceRepository, ResourceMapper resourceMapper) {
        super(resourceRepository, resourceMapper);
        this.resourceExtendedRepository = resourceRepository;
    }
}
