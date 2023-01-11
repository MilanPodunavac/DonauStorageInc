package inc.donau.storage.service.impl;

import inc.donau.storage.domain.TransferDocument;
import inc.donau.storage.repository.TransferDocumentRepository;
import inc.donau.storage.service.TransferDocumentService;
import inc.donau.storage.service.dto.TransferDocumentDTO;
import inc.donau.storage.service.mapper.TransferDocumentMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TransferDocument}.
 */
@Service
@Transactional
public class TransferDocumentServiceImpl implements TransferDocumentService {

    private final Logger log = LoggerFactory.getLogger(TransferDocumentServiceImpl.class);

    private final TransferDocumentRepository transferDocumentRepository;

    private final TransferDocumentMapper transferDocumentMapper;

    public TransferDocumentServiceImpl(
        TransferDocumentRepository transferDocumentRepository,
        TransferDocumentMapper transferDocumentMapper
    ) {
        this.transferDocumentRepository = transferDocumentRepository;
        this.transferDocumentMapper = transferDocumentMapper;
    }

    @Override
    public TransferDocumentDTO save(TransferDocumentDTO transferDocumentDTO) {
        log.debug("Request to save TransferDocument : {}", transferDocumentDTO);
        TransferDocument transferDocument = transferDocumentMapper.toEntity(transferDocumentDTO);
        transferDocument = transferDocumentRepository.save(transferDocument);
        return transferDocumentMapper.toDto(transferDocument);
    }

    @Override
    public TransferDocumentDTO update(TransferDocumentDTO transferDocumentDTO) {
        log.debug("Request to update TransferDocument : {}", transferDocumentDTO);
        TransferDocument transferDocument = transferDocumentMapper.toEntity(transferDocumentDTO);
        transferDocument = transferDocumentRepository.save(transferDocument);
        return transferDocumentMapper.toDto(transferDocument);
    }

    @Override
    public Optional<TransferDocumentDTO> partialUpdate(TransferDocumentDTO transferDocumentDTO) {
        log.debug("Request to partially update TransferDocument : {}", transferDocumentDTO);

        return transferDocumentRepository
            .findById(transferDocumentDTO.getId())
            .map(existingTransferDocument -> {
                transferDocumentMapper.partialUpdate(existingTransferDocument, transferDocumentDTO);

                return existingTransferDocument;
            })
            .map(transferDocumentRepository::save)
            .map(transferDocumentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TransferDocumentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TransferDocuments");
        return transferDocumentRepository.findAll(pageable).map(transferDocumentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TransferDocumentDTO> findOne(Long id) {
        log.debug("Request to get TransferDocument : {}", id);
        return transferDocumentRepository.findById(id).map(transferDocumentMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TransferDocument : {}", id);
        transferDocumentRepository.deleteById(id);
    }
}
