package inc.donau.storage.service.impl;

import inc.donau.storage.domain.CensusDocument;
import inc.donau.storage.domain.Storage;
import inc.donau.storage.domain.enumeration.CensusDocumentStatus;
import inc.donau.storage.repository.CensusDocumentExtendedRepository;
import inc.donau.storage.repository.CensusDocumentRepository;
import inc.donau.storage.repository.CensusItemExtendedRepository;
import inc.donau.storage.service.*;
import inc.donau.storage.service.criteria.CensusItemCriteria;
import inc.donau.storage.service.criteria.ResourceCriteria;
import inc.donau.storage.service.criteria.StorageCardCriteria;
import inc.donau.storage.service.dto.*;
import inc.donau.storage.service.mapper.CensusDocumentMapper;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.filter.LongFilter;

@Service
@Transactional
@Primary
public class CensusDocumentExtendedServiceImpl extends CensusDocumentServiceImpl implements CensusDocumentExtendedService {

    private final CensusDocumentExtendedRepository censusDocumentExtendedRepository;

    private final CensusItemQueryExtendedService censusItemQueryExtendedService;
    private final StorageCardQueryExtendedService storageCardQueryExtendedService;
    private final ResourceQueryExtendedService resourceQueryExtendedService;

    private final StorageCardExtendedService storageCardExtendedService;
    private final CensusItemExtendedService censusItemExtendedService;

    public CensusDocumentExtendedServiceImpl(
        CensusDocumentExtendedRepository censusDocumentRepository,
        CensusDocumentMapper censusDocumentMapper,
        CensusItemQueryExtendedService censusItemQueryExtendedService,
        StorageCardQueryExtendedService storageCardQueryExtendedService,
        ResourceQueryExtendedService resourceQueryExtendedService,
        StorageCardExtendedService storageCardExtendedService,
        CensusItemExtendedService censusItemExtendedService
    ) {
        super(censusDocumentRepository, censusDocumentMapper);
        this.censusDocumentExtendedRepository = censusDocumentRepository;
        this.censusItemQueryExtendedService = censusItemQueryExtendedService;
        this.storageCardQueryExtendedService = storageCardQueryExtendedService;
        this.storageCardExtendedService = storageCardExtendedService;
        this.resourceQueryExtendedService = resourceQueryExtendedService;
        this.censusItemExtendedService = censusItemExtendedService;
    }

    @Override
    public CensusDocumentDTO save(CensusDocumentDTO censusDocumentDTO) {
        CensusDocumentDTO newCensusDocument = super.save(censusDocumentDTO);

        ResourceCriteria resourceCriteria = new ResourceCriteria();
        LongFilter companyFilter = new LongFilter();
        companyFilter.setEquals(censusDocumentDTO.getBusinessYear().getCompany().getId());
        resourceCriteria.setCompanyId(companyFilter);
        List<ResourceDTO> resourceDTOList = resourceQueryExtendedService.findByCriteria(resourceCriteria);

        for (ResourceDTO resourceDTO : resourceDTOList) {
            censusItemExtendedService.save(new CensusItemDTO(resourceDTO, newCensusDocument));
        }

        return newCensusDocument;
    }

    @Override
    public CensusDocumentDTO account(CensusDocumentDTO censusDocumentDTO, BusinessYearDTO businessYearDTO) {
        censusDocumentDTO.setAccountingDate(LocalDate.now());
        censusDocumentDTO.setStatus(CensusDocumentStatus.ACCOUNTED);
        CensusDocumentDTO updatedCensusDocumentDTO = super.update(censusDocumentDTO);

        CensusItemCriteria censusItemCriteria = new CensusItemCriteria();
        LongFilter censusDocumentFilter = new LongFilter();
        censusDocumentFilter.setEquals(censusDocumentDTO.getId());
        censusItemCriteria.setCensusDocumentId(censusDocumentFilter);
        List<CensusItemDTO> censusItemDTOList = censusItemQueryExtendedService.findByCriteria(censusItemCriteria);
        for (CensusItemDTO censusItem : censusItemDTOList) {
            StorageCardCriteria storageCardCriteria = new StorageCardCriteria();
            LongFilter resourceFilter = new LongFilter();
            resourceFilter.setEquals(censusItem.getResource().getId());
            storageCardCriteria.setResourceId(resourceFilter);
            LongFilter storageFilter = new LongFilter();
            storageFilter.setEquals(censusDocumentDTO.getStorage().getId());
            storageCardCriteria.setStorageId(storageFilter);
            LongFilter businessYearFilter = new LongFilter();
            businessYearFilter.setEquals(censusDocumentDTO.getBusinessYear().getId());
            storageCardCriteria.setBusinessYearId(businessYearFilter);
            StorageCardDTO storageCardDTO = storageCardQueryExtendedService.findByCriteria(storageCardCriteria).get(0);

            if (censusDocumentDTO.getLeveling()) {
                storageCardExtendedService.level(storageCardDTO, censusDocumentDTO);
            }

            storageCardExtendedService.correct(storageCardDTO, censusItem);

            storageCardExtendedService.save(
                new StorageCardDTO(
                    businessYearDTO,
                    censusItem.getResource(),
                    censusDocumentDTO.getStorage(),
                    storageCardDTO.getTotalAmount(),
                    storageCardDTO.getTotalValue(),
                    storageCardDTO.getPrice()
                )
            );
        }
        return updatedCensusDocumentDTO;
    }
}
