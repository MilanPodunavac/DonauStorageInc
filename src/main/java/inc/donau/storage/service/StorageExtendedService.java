package inc.donau.storage.service;

import inc.donau.storage.repository.StorageExtendedRepository;
import inc.donau.storage.repository.StorageRepository;
import inc.donau.storage.service.criteria.BusinessYearCriteria;
import inc.donau.storage.service.criteria.ResourceCriteria;
import inc.donau.storage.service.criteria.StorageCriteria;
import inc.donau.storage.service.dto.*;
import inc.donau.storage.service.mapper.StorageMapper;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;
import tech.jhipster.service.filter.LongFilter;

@Service
@Transactional
public class StorageExtendedService extends StorageService {

    private final StorageExtendedRepository storageExtendedRepository;
    private final StorageQueryExtendedService storageQueryExtendedService;

    private final AddressExtendedService addressExtendedService;

    private final BusinessYearQueryExtendedService businessYearQueryExtendedService;
    private final ResourceQueryExtendedService resourceQueryExtendedService;

    private final StorageCardExtendedService storageCardExtendedService;
    private final StorageCardTrafficExtendedService storageCardTrafficExtendedService;

    public StorageExtendedService(
        StorageExtendedRepository storageRepository,
        StorageMapper storageMapper,
        StorageQueryExtendedService storageQueryExtendedService,
        AddressExtendedService addressExtendedService,
        BusinessYearQueryExtendedService businessYearQueryExtendedService,
        ResourceQueryExtendedService resourceQueryExtendedService,
        StorageCardExtendedService storageCardExtendedService,
        StorageCardTrafficExtendedService storageCardTrafficExtendedService
    ) {
        super(storageRepository, storageMapper);
        this.storageExtendedRepository = storageRepository;
        this.storageQueryExtendedService = storageQueryExtendedService;
        this.addressExtendedService = addressExtendedService;
        this.businessYearQueryExtendedService = businessYearQueryExtendedService;
        this.resourceQueryExtendedService = resourceQueryExtendedService;
        this.storageCardExtendedService = storageCardExtendedService;
        this.storageCardTrafficExtendedService = storageCardTrafficExtendedService;
    }

    @Override
    public StorageDTO save(StorageDTO storageDTO) {
        if (storageDTO.getAddress().getId() == null) storageDTO.setAddress(addressExtendedService.save(storageDTO.getAddress()));
        StorageDTO newStorageDTO = super.save(storageDTO);

        LongFilter companyFilter = new LongFilter();
        companyFilter.setEquals(storageDTO.getCompany().getId());
        ResourceCriteria resourceCriteria = new ResourceCriteria();
        resourceCriteria.setCompanyId(companyFilter);
        List<ResourceDTO> resourceList = resourceQueryExtendedService.findByCriteria(resourceCriteria);
        BusinessYearCriteria businessYearCriteria = new BusinessYearCriteria();
        businessYearCriteria.setCompanyId(companyFilter);
        List<BusinessYearDTO> businessYearList = businessYearQueryExtendedService.findByCriteria(businessYearCriteria);

        for (ResourceDTO resource : resourceList) {
            for (BusinessYearDTO businessYear : businessYearList) {
                storageCardExtendedService.save(new StorageCardDTO(businessYear, resource, newStorageDTO));
            }
        }
        return newStorageDTO;
    }
}
