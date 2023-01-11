package inc.donau.storage.service;

import inc.donau.storage.service.dto.StorageCardDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link inc.donau.storage.domain.StorageCard}.
 */
public interface StorageCardService {
    /**
     * Save a storageCard.
     *
     * @param storageCardDTO the entity to save.
     * @return the persisted entity.
     */
    StorageCardDTO save(StorageCardDTO storageCardDTO);

    /**
     * Updates a storageCard.
     *
     * @param storageCardDTO the entity to update.
     * @return the persisted entity.
     */
    StorageCardDTO update(StorageCardDTO storageCardDTO);

    /**
     * Partially updates a storageCard.
     *
     * @param storageCardDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<StorageCardDTO> partialUpdate(StorageCardDTO storageCardDTO);

    /**
     * Get all the storageCards.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<StorageCardDTO> findAll(Pageable pageable);

    /**
     * Get the "id" storageCard.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<StorageCardDTO> findOne(String id);

    /**
     * Delete the "id" storageCard.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
