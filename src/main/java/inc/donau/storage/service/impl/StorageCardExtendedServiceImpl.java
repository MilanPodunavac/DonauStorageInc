package inc.donau.storage.service.impl;

import inc.donau.storage.domain.StorageCard;
import inc.donau.storage.repository.StorageCardExtendedRepository;
import inc.donau.storage.repository.StorageCardRepository;
import inc.donau.storage.service.BusinessYearService;
import inc.donau.storage.service.StorageCardExtendedService;
import inc.donau.storage.service.StorageCardQueryExtendedService;
import inc.donau.storage.service.StorageCardTrafficExtendedService;
import inc.donau.storage.service.criteria.ResourceCriteria;
import inc.donau.storage.service.criteria.StorageCardCriteria;
import inc.donau.storage.service.criteria.StorageCriteria;
import inc.donau.storage.service.dto.*;
import inc.donau.storage.service.mapper.StorageCardMapper;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.filter.LongFilter;

@Service
@Transactional
@Primary
public class StorageCardExtendedServiceImpl extends StorageCardServiceImpl implements StorageCardExtendedService {

    private final Logger log = LoggerFactory.getLogger(BusinessYearService.class);

    private final StorageCardExtendedRepository storageCardExtendedRepository;

    private final StorageCardQueryExtendedService storageCardQueryExtendedService;
    private final StorageCardTrafficExtendedService storageCardTrafficExtendedService;

    public StorageCardExtendedServiceImpl(
        StorageCardExtendedRepository storageCardRepository,
        StorageCardMapper storageCardMapper,
        StorageCardQueryExtendedService storageCardQueryExtendedService,
        StorageCardTrafficExtendedService storageCardTrafficExtendedService
    ) {
        super(storageCardRepository, storageCardMapper);
        this.storageCardExtendedRepository = storageCardRepository;
        this.storageCardQueryExtendedService = storageCardQueryExtendedService;
        this.storageCardTrafficExtendedService = storageCardTrafficExtendedService;
    }

    @Override
    public StorageCardDTO save(StorageCardDTO storageCardDTO) {
        StorageCardDTO newStorageCardDTO = super.save(storageCardDTO);

        List<StorageCardDTO> storageCardDTOList = storageCardQueryExtendedService.findByCriteria(new StorageCardCriteria());

        storageCardTrafficExtendedService.save(new StorageCardTrafficDTO(newStorageCardDTO, storageCardDTO));

        return newStorageCardDTO;
    }

    @Override
    public void level(StorageCardDTO storageCardDTO, CensusDocumentDTO censusDocumentDTO) {
        float leveling = storageCardDTO.getTotalAmount() * storageCardDTO.getPrice() - storageCardDTO.getTotalValue();
        if (leveling != 0) {
            storageCardTrafficExtendedService.save(new StorageCardTrafficDTO(storageCardDTO, censusDocumentDTO, leveling));
        }
    }

    @Override
    public void correct(StorageCardDTO storageCardDTO, CensusItemDTO censusItem) {
        float correction = censusItem.getAmount() - storageCardDTO.getTotalAmount();
        if (correction != 0) {
            storageCardTrafficExtendedService.save(new StorageCardTrafficDTO(storageCardDTO, censusItem, correction));
        }
    }

    @Override
    public void transfer(TransferDocumentDTO transferDocumentDTO, TransferDocumentItemDTO transferDocumentItemDTO) {
        switch (transferDocumentDTO.getType()) {
            case RECEIVING:
                StorageCardDTO receivingStorageCardDTO = getReceivingStorageCardDTO(transferDocumentDTO, transferDocumentItemDTO);
                storageCardTrafficExtendedService.save(
                    new StorageCardTrafficDTO(receivingStorageCardDTO, transferDocumentDTO, transferDocumentItemDTO, true)
                );
                break;
            case DISPATCHING:
                StorageCardDTO dispatchingStorageCardDTO = getDispatchingStorageCardDTO(transferDocumentDTO, transferDocumentItemDTO);
                storageCardTrafficExtendedService.save(
                    new StorageCardTrafficDTO(dispatchingStorageCardDTO, transferDocumentDTO, transferDocumentItemDTO, false)
                );
                break;
            case INTERSTORAGE:
                StorageCardDTO receivingInterstorageCardDTO = getReceivingStorageCardDTO(transferDocumentDTO, transferDocumentItemDTO);
                storageCardTrafficExtendedService.save(
                    new StorageCardTrafficDTO(receivingInterstorageCardDTO, transferDocumentDTO, transferDocumentItemDTO, true)
                );
                StorageCardDTO dispatchingInterstorageCardDTO = getDispatchingStorageCardDTO(transferDocumentDTO, transferDocumentItemDTO);
                storageCardTrafficExtendedService.save(
                    new StorageCardTrafficDTO(dispatchingInterstorageCardDTO, transferDocumentDTO, transferDocumentItemDTO, false)
                );
                break;
            default:
                break;
        }
    }

    private StorageCardDTO getReceivingStorageCardDTO(
        TransferDocumentDTO transferDocumentDTO,
        TransferDocumentItemDTO transferDocumentItemDTO
    ) {
        StorageCardCriteria storageCardCriteria = new StorageCardCriteria();
        LongFilter resourceFilter = new LongFilter();
        resourceFilter.setEquals(transferDocumentItemDTO.getResource().getId());
        storageCardCriteria.setResourceId(resourceFilter);
        LongFilter storageFilter = new LongFilter();
        storageFilter.setEquals(transferDocumentDTO.getReceivingStorage().getId());
        storageCardCriteria.setStorageId(storageFilter);
        LongFilter businessYearFilter = new LongFilter();
        businessYearFilter.setEquals(transferDocumentDTO.getBusinessYear().getId());
        storageCardCriteria.setBusinessYearId(businessYearFilter);
        StorageCardDTO receivingStorageCardDTO = storageCardQueryExtendedService.findByCriteria(storageCardCriteria).get(0);
        return receivingStorageCardDTO;
    }

    private StorageCardDTO getDispatchingStorageCardDTO(
        TransferDocumentDTO transferDocumentDTO,
        TransferDocumentItemDTO transferDocumentItemDTO
    ) {
        StorageCardCriteria storageCardCriteria = new StorageCardCriteria();
        LongFilter resourceFilter = new LongFilter();
        resourceFilter.setEquals(transferDocumentItemDTO.getResource().getId());
        storageCardCriteria.setResourceId(resourceFilter);
        LongFilter storageFilter = new LongFilter();
        storageFilter.setEquals(transferDocumentDTO.getDispatchingStorage().getId());
        storageCardCriteria.setStorageId(storageFilter);
        LongFilter businessYearFilter = new LongFilter();
        businessYearFilter.setEquals(transferDocumentDTO.getBusinessYear().getId());
        storageCardCriteria.setBusinessYearId(businessYearFilter);
        StorageCardDTO dispatchingStorageCardDTO = storageCardQueryExtendedService.findByCriteria(storageCardCriteria).get(0);
        return dispatchingStorageCardDTO;
    }

    @Override
    public void updateValues(StorageCardTrafficDTO storageCardTrafficDTO) {
        StorageCardDTO storageCardDTO = storageCardTrafficDTO.getStorageCard();
        switch (storageCardTrafficDTO.getType()) {
            case STARTING_BALANCE:
                storageCardDTO.setStartingAmount(storageCardTrafficDTO.getAmount());
                storageCardDTO.setStartingValue(storageCardTrafficDTO.getTrafficValue());
                storageCardDTO.setPrice(storageCardTrafficDTO.getPrice());
                break;
            case TRANSFER:
                switch (storageCardTrafficDTO.getDirection()) {
                    case IN:
                        storageCardDTO.setReceivedAmount(storageCardDTO.getReceivedAmount() + storageCardTrafficDTO.getAmount());
                        storageCardDTO.setReceivedValue(storageCardDTO.getReceivedValue() + storageCardTrafficDTO.getTrafficValue());
                        storageCardDTO.setPrice(
                            (storageCardDTO.getTotalValue() + storageCardTrafficDTO.getTrafficValue()) /
                            (storageCardDTO.getTotalAmount() + storageCardTrafficDTO.getAmount())
                        );
                        break;
                    case OUT:
                        storageCardDTO.setDispatchedAmount(storageCardDTO.getDispatchedAmount() + storageCardTrafficDTO.getAmount());
                        storageCardDTO.setDispatchedValue(storageCardDTO.getDispatchedAmount() + storageCardTrafficDTO.getTrafficValue());
                        break;
                }
                break;
            case REVERSAL:
                switch (storageCardTrafficDTO.getDirection()) {
                    case IN:
                        storageCardDTO.setDispatchedAmount(storageCardDTO.getDispatchedAmount() - storageCardTrafficDTO.getAmount());
                        storageCardDTO.setDispatchedValue(storageCardDTO.getDispatchedValue() - storageCardTrafficDTO.getTrafficValue());
                        break;
                    case OUT:
                        storageCardDTO.setReceivedAmount(storageCardDTO.getReceivedAmount() - storageCardTrafficDTO.getAmount());
                        storageCardDTO.setReceivedValue(storageCardDTO.getReceivedValue() - storageCardTrafficDTO.getTrafficValue());
                        break;
                }
                break;
            case CORRECTION:
                switch (storageCardTrafficDTO.getDirection()) {
                    case IN:
                        storageCardDTO.setReceivedAmount(storageCardDTO.getReceivedAmount() + storageCardTrafficDTO.getAmount());
                        storageCardDTO.setReceivedValue(storageCardDTO.getReceivedValue() + storageCardTrafficDTO.getTrafficValue());
                        break;
                    case OUT:
                        storageCardDTO.setDispatchedAmount(storageCardDTO.getDispatchedAmount() + storageCardTrafficDTO.getAmount());
                        storageCardDTO.setDispatchedValue(storageCardDTO.getDispatchedAmount() + storageCardTrafficDTO.getTrafficValue());
                        break;
                }
                break;
            case LEVELING:
                storageCardDTO.setReceivedValue(storageCardDTO.getReceivedValue() + storageCardTrafficDTO.getTrafficValue());
                break;
        }
        storageCardDTO.setTotalAmount(
            storageCardDTO.getStartingAmount() + storageCardDTO.getReceivedAmount() - storageCardDTO.getDispatchedAmount()
        );
        storageCardDTO.setTotalValue(
            storageCardDTO.getStartingValue() + storageCardDTO.getReceivedValue() - storageCardDTO.getDispatchedValue()
        );

        super.update(storageCardDTO);
    }
}
