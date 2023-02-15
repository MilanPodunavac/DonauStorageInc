package inc.donau.storage.service.impl;

import inc.donau.storage.domain.enumeration.TransferDocumentStatus;
import inc.donau.storage.repository.TransferDocumentExtendedRepository;
import inc.donau.storage.repository.TransferDocumentRepository;
import inc.donau.storage.service.StorageCardExtendedService;
import inc.donau.storage.service.TransferDocumentExtendedService;
import inc.donau.storage.service.TransferDocumentItemQueryExtendedService;
import inc.donau.storage.service.criteria.CensusItemCriteria;
import inc.donau.storage.service.criteria.StorageCardCriteria;
import inc.donau.storage.service.criteria.TransferDocumentItemCriteria;
import inc.donau.storage.service.dto.CensusItemDTO;
import inc.donau.storage.service.dto.StorageCardDTO;
import inc.donau.storage.service.dto.TransferDocumentDTO;
import inc.donau.storage.service.dto.TransferDocumentItemDTO;
import inc.donau.storage.service.mapper.TransferDocumentMapper;
import java.time.LocalDate;
import java.util.List;
import org.h2.value.Transfer;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.filter.LongFilter;

@Service
@Transactional
@Primary
public class TransferDocumentExtendedServiceImpl extends TransferDocumentServiceImpl implements TransferDocumentExtendedService {

    private final TransferDocumentExtendedRepository transferDocumentExtendedRepository;

    private final TransferDocumentItemQueryExtendedService transferDocumentItemQueryExtendedService;

    private final StorageCardExtendedService storageCardExtendedService;

    public TransferDocumentExtendedServiceImpl(
        TransferDocumentExtendedRepository transferDocumentRepository,
        TransferDocumentMapper transferDocumentMapper,
        TransferDocumentItemQueryExtendedService transferDocumentItemQueryExtendedService,
        StorageCardExtendedService storageCardExtendedService
    ) {
        super(transferDocumentRepository, transferDocumentMapper);
        this.transferDocumentExtendedRepository = transferDocumentRepository;
        this.transferDocumentItemQueryExtendedService = transferDocumentItemQueryExtendedService;
        this.storageCardExtendedService = storageCardExtendedService;
    }

    @Override
    public TransferDocumentDTO account(TransferDocumentDTO transferDocumentDTO, List<TransferDocumentItemDTO> transferDocumentItemDTOList) {
        transferDocumentDTO.setStatus(TransferDocumentStatus.ACCOUNTED);
        transferDocumentDTO.setAccountingDate(LocalDate.now());
        TransferDocumentDTO updatedTransferDocumentDTO = super.update(transferDocumentDTO);

        for (TransferDocumentItemDTO transferDocumentItemDTO : transferDocumentItemDTOList) {
            storageCardExtendedService.transfer(transferDocumentDTO, transferDocumentItemDTO);
        }
        return updatedTransferDocumentDTO;
    }

    @Override
    public TransferDocumentDTO reverse(TransferDocumentDTO transferDocumentDTO, List<TransferDocumentItemDTO> transferDocumentItemDTOList) {
        transferDocumentDTO.setStatus(TransferDocumentStatus.REVERSED);
        transferDocumentDTO.setReversalDate(LocalDate.now());
        TransferDocumentDTO updatedTransferDocumentDTO = super.update(transferDocumentDTO);

        for (TransferDocumentItemDTO transferDocumentItemDTO : transferDocumentItemDTOList) {
            storageCardExtendedService.transfer(transferDocumentDTO, transferDocumentItemDTO);
        }
        return updatedTransferDocumentDTO;
    }
}
