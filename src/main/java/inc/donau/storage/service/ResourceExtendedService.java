package inc.donau.storage.service;

import inc.donau.storage.domain.enumeration.StorageCardTrafficDirection;
import inc.donau.storage.domain.enumeration.StorageCardTrafficType;
import inc.donau.storage.repository.ResourceExtendedRepository;
import inc.donau.storage.repository.ResourceRepository;
import inc.donau.storage.service.criteria.BusinessYearCriteria;
import inc.donau.storage.service.criteria.ResourceCriteria;
import inc.donau.storage.service.criteria.StorageCardCriteria;
import inc.donau.storage.service.criteria.StorageCriteria;
import inc.donau.storage.service.dto.*;
import inc.donau.storage.service.mapper.ResourceMapper;
import java.time.LocalDate;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;
import tech.jhipster.service.filter.LongFilter;

@Service
@Transactional
public class ResourceExtendedService extends ResourceService {

    private final ResourceExtendedRepository resourceExtendedRepository;
    private final ResourceQueryExtendedService resourceQueryExtendedService;

    private final StorageQueryExtendedService storageQueryExtendedService;
    private final BusinessYearQueryExtendedService businessYearQueryExtendedService;

    private final StorageCardExtendedService storageCardExtendedService;
    private final StorageCardTrafficExtendedService storageCardTrafficExtendedService;

    public ResourceExtendedService(
        ResourceExtendedRepository resourceRepository,
        ResourceMapper resourceMapper,
        ResourceQueryExtendedService resourceQueryExtendedService,
        StorageQueryExtendedService storageQueryExtendedService,
        BusinessYearQueryExtendedService businessYearQueryExtendedService,
        StorageCardExtendedService storageCardExtendedService,
        StorageCardTrafficExtendedService storageCardTrafficExtendedService
    ) {
        super(resourceRepository, resourceMapper);
        this.resourceExtendedRepository = resourceRepository;
        this.resourceQueryExtendedService = resourceQueryExtendedService;
        this.storageQueryExtendedService = storageQueryExtendedService;
        this.businessYearQueryExtendedService = businessYearQueryExtendedService;
        this.storageCardExtendedService = storageCardExtendedService;
        this.storageCardTrafficExtendedService = storageCardTrafficExtendedService;
    }

    @Override
    public ResourceDTO save(ResourceDTO resourceDTO) {
        ResourceDTO newResourceDTO = super.save(resourceDTO);

        LongFilter companyFilter = new LongFilter();
        companyFilter.setEquals(resourceDTO.getCompany().getId());
        StorageCriteria storageCriteria = new StorageCriteria();
        storageCriteria.setCompanyId(companyFilter);
        List<StorageDTO> storageList = storageQueryExtendedService.findByCriteria(storageCriteria);
        BusinessYearCriteria businessYearCriteria = new BusinessYearCriteria();
        businessYearCriteria.setCompanyId(companyFilter);
        List<BusinessYearDTO> businessYearList = businessYearQueryExtendedService.findByCriteria(businessYearCriteria);

        for (StorageDTO storage : storageList) {
            for (BusinessYearDTO businessYear : businessYearList) {
                storageCardExtendedService.save(new StorageCardDTO(businessYear, newResourceDTO, storage, 0.0f, 0.0f, 0.0f));
            }
        }
        return newResourceDTO;
    }
}
