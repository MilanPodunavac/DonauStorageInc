package inc.donau.storage.service;

import inc.donau.storage.domain.TransferDocumentItem;
import inc.donau.storage.repository.TransferDocumentItemRepository;
import inc.donau.storage.service.dto.TransferDocumentItemDTO;
import inc.donau.storage.service.mapper.TransferDocumentItemMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TransferDocumentItem}.
 */
@Service
@Transactional
public class TransferDocumentItemService {

    private final Logger log = LoggerFactory.getLogger(TransferDocumentItemService.class);

    private final TransferDocumentItemRepository transferDocumentItemRepository;

    private final TransferDocumentItemMapper transferDocumentItemMapper;

    public TransferDocumentItemService(
        TransferDocumentItemRepository transferDocumentItemRepository,
        TransferDocumentItemMapper transferDocumentItemMapper
    ) {
        this.transferDocumentItemRepository = transferDocumentItemRepository;
        this.transferDocumentItemMapper = transferDocumentItemMapper;
    }

    /**
     * Save a transferDocumentItem.
     *
     * @param transferDocumentItemDTO the entity to save.
     * @return the persisted entity.
     */
    public TransferDocumentItemDTO save(TransferDocumentItemDTO transferDocumentItemDTO) {
        log.debug("Request to save TransferDocumentItem : {}", transferDocumentItemDTO);
        TransferDocumentItem transferDocumentItem = transferDocumentItemMapper.toEntity(transferDocumentItemDTO);
        transferDocumentItem = transferDocumentItemRepository.save(transferDocumentItem);
        return transferDocumentItemMapper.toDto(transferDocumentItem);
    }

    /**
     * Update a transferDocumentItem.
     *
     * @param transferDocumentItemDTO the entity to save.
     * @return the persisted entity.
     */
    public TransferDocumentItemDTO update(TransferDocumentItemDTO transferDocumentItemDTO) {
        log.debug("Request to update TransferDocumentItem : {}", transferDocumentItemDTO);
        TransferDocumentItem transferDocumentItem = transferDocumentItemMapper.toEntity(transferDocumentItemDTO);
        transferDocumentItem = transferDocumentItemRepository.save(transferDocumentItem);
        return transferDocumentItemMapper.toDto(transferDocumentItem);
    }

    /**
     * Partially update a transferDocumentItem.
     *
     * @param transferDocumentItemDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TransferDocumentItemDTO> partialUpdate(TransferDocumentItemDTO transferDocumentItemDTO) {
        log.debug("Request to partially update TransferDocumentItem : {}", transferDocumentItemDTO);

        return transferDocumentItemRepository
            .findById(transferDocumentItemDTO.getId())
            .map(existingTransferDocumentItem -> {
                transferDocumentItemMapper.partialUpdate(existingTransferDocumentItem, transferDocumentItemDTO);

                return existingTransferDocumentItem;
            })
            .map(transferDocumentItemRepository::save)
            .map(transferDocumentItemMapper::toDto);
    }

    /**
     * Get all the transferDocumentItems.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TransferDocumentItemDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TransferDocumentItems");
        return transferDocumentItemRepository.findAll(pageable).map(transferDocumentItemMapper::toDto);
    }

    /**
     * Get one transferDocumentItem by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TransferDocumentItemDTO> findOne(Long id) {
        log.debug("Request to get TransferDocumentItem : {}", id);
        return transferDocumentItemRepository.findById(id).map(transferDocumentItemMapper::toDto);
    }

    /**
     * Delete the transferDocumentItem by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TransferDocumentItem : {}", id);
        transferDocumentItemRepository.deleteById(id);
    }
}
