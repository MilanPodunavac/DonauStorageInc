package inc.donau.storage.service;

import inc.donau.storage.domain.StorageCardTraffic;
import inc.donau.storage.repository.StorageCardTrafficRepository;
import inc.donau.storage.service.dto.StorageCardTrafficDTO;
import inc.donau.storage.service.mapper.StorageCardTrafficMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link StorageCardTraffic}.
 */
@Service
@Transactional
public class StorageCardTrafficService {

    private final Logger log = LoggerFactory.getLogger(StorageCardTrafficService.class);

    private final StorageCardTrafficRepository storageCardTrafficRepository;

    private final StorageCardTrafficMapper storageCardTrafficMapper;

    public StorageCardTrafficService(
        StorageCardTrafficRepository storageCardTrafficRepository,
        StorageCardTrafficMapper storageCardTrafficMapper
    ) {
        this.storageCardTrafficRepository = storageCardTrafficRepository;
        this.storageCardTrafficMapper = storageCardTrafficMapper;
    }

    /**
     * Save a storageCardTraffic.
     *
     * @param storageCardTrafficDTO the entity to save.
     * @return the persisted entity.
     */
    public StorageCardTrafficDTO save(StorageCardTrafficDTO storageCardTrafficDTO) {
        log.debug("Request to save StorageCardTraffic : {}", storageCardTrafficDTO);
        StorageCardTraffic storageCardTraffic = storageCardTrafficMapper.toEntity(storageCardTrafficDTO);
        storageCardTraffic = storageCardTrafficRepository.save(storageCardTraffic);
        return storageCardTrafficMapper.toDto(storageCardTraffic);
    }

    /**
     * Update a storageCardTraffic.
     *
     * @param storageCardTrafficDTO the entity to save.
     * @return the persisted entity.
     */
    public StorageCardTrafficDTO update(StorageCardTrafficDTO storageCardTrafficDTO) {
        log.debug("Request to update StorageCardTraffic : {}", storageCardTrafficDTO);
        StorageCardTraffic storageCardTraffic = storageCardTrafficMapper.toEntity(storageCardTrafficDTO);
        storageCardTraffic = storageCardTrafficRepository.save(storageCardTraffic);
        return storageCardTrafficMapper.toDto(storageCardTraffic);
    }

    /**
     * Partially update a storageCardTraffic.
     *
     * @param storageCardTrafficDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<StorageCardTrafficDTO> partialUpdate(StorageCardTrafficDTO storageCardTrafficDTO) {
        log.debug("Request to partially update StorageCardTraffic : {}", storageCardTrafficDTO);

        return storageCardTrafficRepository
            .findById(storageCardTrafficDTO.getId())
            .map(existingStorageCardTraffic -> {
                storageCardTrafficMapper.partialUpdate(existingStorageCardTraffic, storageCardTrafficDTO);

                return existingStorageCardTraffic;
            })
            .map(storageCardTrafficRepository::save)
            .map(storageCardTrafficMapper::toDto);
    }

    /**
     * Get all the storageCardTraffics.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<StorageCardTrafficDTO> findAll(Pageable pageable) {
        log.debug("Request to get all StorageCardTraffics");
        return storageCardTrafficRepository.findAll(pageable).map(storageCardTrafficMapper::toDto);
    }

    /**
     * Get one storageCardTraffic by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<StorageCardTrafficDTO> findOne(Long id) {
        log.debug("Request to get StorageCardTraffic : {}", id);
        return storageCardTrafficRepository.findById(id).map(storageCardTrafficMapper::toDto);
    }

    /**
     * Delete the storageCardTraffic by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete StorageCardTraffic : {}", id);
        storageCardTrafficRepository.deleteById(id);
    }
}
