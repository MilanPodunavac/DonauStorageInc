package inc.donau.storage.service.impl;

import inc.donau.storage.domain.StorageCard;
import inc.donau.storage.repository.StorageCardRepository;
import inc.donau.storage.service.StorageCardService;
import inc.donau.storage.service.dto.StorageCardDTO;
import inc.donau.storage.service.mapper.StorageCardMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link StorageCard}.
 */
@Service
@Transactional
public class StorageCardServiceImpl implements StorageCardService {

    private final Logger log = LoggerFactory.getLogger(StorageCardServiceImpl.class);

    private final StorageCardRepository storageCardRepository;

    private final StorageCardMapper storageCardMapper;

    public StorageCardServiceImpl(StorageCardRepository storageCardRepository, StorageCardMapper storageCardMapper) {
        this.storageCardRepository = storageCardRepository;
        this.storageCardMapper = storageCardMapper;
    }

    @Override
    public StorageCardDTO save(StorageCardDTO storageCardDTO) {
        log.debug("Request to save StorageCard : {}", storageCardDTO);
        StorageCard storageCard = storageCardMapper.toEntity(storageCardDTO);
        storageCard = storageCardRepository.save(storageCard);
        return storageCardMapper.toDto(storageCard);
    }

    @Override
    public StorageCardDTO update(StorageCardDTO storageCardDTO) {
        log.debug("Request to update StorageCard : {}", storageCardDTO);
        StorageCard storageCard = storageCardMapper.toEntity(storageCardDTO);
        storageCard.setIsPersisted();
        storageCard = storageCardRepository.save(storageCard);
        return storageCardMapper.toDto(storageCard);
    }

    @Override
    public Optional<StorageCardDTO> partialUpdate(StorageCardDTO storageCardDTO) {
        log.debug("Request to partially update StorageCard : {}", storageCardDTO);

        return storageCardRepository
            .findById(storageCardDTO.getId())
            .map(existingStorageCard -> {
                storageCardMapper.partialUpdate(existingStorageCard, storageCardDTO);

                return existingStorageCard;
            })
            .map(storageCardRepository::save)
            .map(storageCardMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StorageCardDTO> findAll(Pageable pageable) {
        log.debug("Request to get all StorageCards");
        return storageCardRepository.findAll(pageable).map(storageCardMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<StorageCardDTO> findOne(String id) {
        log.debug("Request to get StorageCard : {}", id);
        return storageCardRepository.findById(id).map(storageCardMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete StorageCard : {}", id);
        storageCardRepository.deleteById(id);
    }
}
