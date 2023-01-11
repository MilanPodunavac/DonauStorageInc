package inc.donau.storage.service;

import inc.donau.storage.domain.Storage;
import inc.donau.storage.repository.StorageRepository;
import inc.donau.storage.service.dto.StorageDTO;
import inc.donau.storage.service.mapper.StorageMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Storage}.
 */
@Service
@Transactional
public class StorageService {

    private final Logger log = LoggerFactory.getLogger(StorageService.class);

    private final StorageRepository storageRepository;

    private final StorageMapper storageMapper;

    public StorageService(StorageRepository storageRepository, StorageMapper storageMapper) {
        this.storageRepository = storageRepository;
        this.storageMapper = storageMapper;
    }

    /**
     * Save a storage.
     *
     * @param storageDTO the entity to save.
     * @return the persisted entity.
     */
    public StorageDTO save(StorageDTO storageDTO) {
        log.debug("Request to save Storage : {}", storageDTO);
        Storage storage = storageMapper.toEntity(storageDTO);
        storage = storageRepository.save(storage);
        return storageMapper.toDto(storage);
    }

    /**
     * Update a storage.
     *
     * @param storageDTO the entity to save.
     * @return the persisted entity.
     */
    public StorageDTO update(StorageDTO storageDTO) {
        log.debug("Request to update Storage : {}", storageDTO);
        Storage storage = storageMapper.toEntity(storageDTO);
        storage = storageRepository.save(storage);
        return storageMapper.toDto(storage);
    }

    /**
     * Partially update a storage.
     *
     * @param storageDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<StorageDTO> partialUpdate(StorageDTO storageDTO) {
        log.debug("Request to partially update Storage : {}", storageDTO);

        return storageRepository
            .findById(storageDTO.getId())
            .map(existingStorage -> {
                storageMapper.partialUpdate(existingStorage, storageDTO);

                return existingStorage;
            })
            .map(storageRepository::save)
            .map(storageMapper::toDto);
    }

    /**
     * Get all the storages.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<StorageDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Storages");
        return storageRepository.findAll(pageable).map(storageMapper::toDto);
    }

    /**
     * Get one storage by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<StorageDTO> findOne(Long id) {
        log.debug("Request to get Storage : {}", id);
        return storageRepository.findById(id).map(storageMapper::toDto);
    }

    /**
     * Delete the storage by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Storage : {}", id);
        storageRepository.deleteById(id);
    }
}
