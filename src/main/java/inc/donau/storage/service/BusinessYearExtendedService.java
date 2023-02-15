package inc.donau.storage.service;

import inc.donau.storage.domain.Storage;
import inc.donau.storage.domain.enumeration.StorageCardTrafficDirection;
import inc.donau.storage.domain.enumeration.StorageCardTrafficType;
import inc.donau.storage.repository.BusinessYearExtendedRepository;
import inc.donau.storage.service.criteria.BusinessYearCriteria;
import inc.donau.storage.service.criteria.ResourceCriteria;
import inc.donau.storage.service.criteria.StorageCardCriteria;
import inc.donau.storage.service.criteria.StorageCriteria;
import inc.donau.storage.service.dto.*;
import inc.donau.storage.service.mapper.BusinessYearMapper;
import java.time.LocalDate;
import java.util.List;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.LongFilter;

@Service
@Transactional
public class BusinessYearExtendedService extends BusinessYearService {

    private final Logger log = LoggerFactory.getLogger(BusinessYearService.class);

    private final BusinessYearExtendedRepository businessYearExtendedRepository;
    private final BusinessYearQueryExtendedService businessYearQueryExtendedService;

    private final StorageQueryExtendedService storageQueryExtendedService;
    private final ResourceQueryExtendedService resourceQueryExtendedService;
    private final StorageCardQueryExtendedService storageCardQueryExtendedService;

    private final StorageCardExtendedService storageCardExtendedService;
    private final StorageCardTrafficExtendedService storageCardTrafficExtendedService;

    public BusinessYearExtendedService(
        BusinessYearExtendedRepository businessYearRepository,
        BusinessYearMapper businessYearMapper,
        BusinessYearQueryExtendedService businessYearQueryExtendedService,
        StorageQueryExtendedService storageQueryExtendedService,
        ResourceQueryExtendedService resourceQueryExtendedService,
        StorageCardQueryExtendedService storageCardQueryExtendedService,
        StorageCardExtendedService storageCardExtendedService,
        StorageCardTrafficExtendedService storageCardTrafficExtendedService
    ) {
        super(businessYearRepository, businessYearMapper);
        this.businessYearExtendedRepository = businessYearRepository;
        this.businessYearQueryExtendedService = businessYearQueryExtendedService;
        this.storageQueryExtendedService = storageQueryExtendedService;
        this.resourceQueryExtendedService = resourceQueryExtendedService;
        this.storageCardQueryExtendedService = storageCardQueryExtendedService;
        this.storageCardExtendedService = storageCardExtendedService;
        this.storageCardTrafficExtendedService = storageCardTrafficExtendedService;
    }

    public BusinessYearDTO complete(BusinessYearDTO businessYearDTO) {
        log.debug("Request to complete BusinessYear : {}", businessYearDTO);

        businessYearDTO.setCompleted(true);
        return update(businessYearDTO);
    }

    @Override
    public BusinessYearDTO save(BusinessYearDTO businessYearDTO) {
        BusinessYearDTO newBusinessYearDTO = super.save(businessYearDTO);

        LongFilter companyFilter = new LongFilter();
        companyFilter.setEquals(businessYearDTO.getCompany().getId());
        StorageCriteria storageCriteria = new StorageCriteria();
        storageCriteria.setCompanyId(companyFilter);
        List<StorageDTO> storageList = storageQueryExtendedService.findByCriteria(storageCriteria);
        ResourceCriteria resourceCriteria = new ResourceCriteria();
        resourceCriteria.setCompanyId(companyFilter);
        List<ResourceDTO> resourceList = resourceQueryExtendedService.findByCriteria(resourceCriteria);

        for (StorageDTO storage : storageList) {
            for (ResourceDTO resource : resourceList) {
                StorageCardCriteria storageCardCriteria = new StorageCardCriteria();
                LongFilter storageFilter = new LongFilter();
                storageFilter.setEquals(storage.getId());
                storageCardCriteria.setStorageId(storageFilter);
                LongFilter resourceFilter = new LongFilter();
                resourceFilter.setEquals(resource.getId());
                storageCardCriteria.setResourceId(resourceFilter);

                if (storageCardQueryExtendedService.countByCriteria(storageCardCriteria) == 0) {
                    storageCardExtendedService.save(new StorageCardDTO(newBusinessYearDTO, resource, storage, 0.0f, 0.0f, 0.0f));
                }
            }
        }
        return newBusinessYearDTO;
    }
}
