package inc.donau.storage.service;

import inc.donau.storage.service.dto.TransferDocumentDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link inc.donau.storage.domain.TransferDocument}.
 */
public interface TransferDocumentService {
    /**
     * Save a transferDocument.
     *
     * @param transferDocumentDTO the entity to save.
     * @return the persisted entity.
     */
    TransferDocumentDTO save(TransferDocumentDTO transferDocumentDTO);

    /**
     * Updates a transferDocument.
     *
     * @param transferDocumentDTO the entity to update.
     * @return the persisted entity.
     */
    TransferDocumentDTO update(TransferDocumentDTO transferDocumentDTO);

    /**
     * Partially updates a transferDocument.
     *
     * @param transferDocumentDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TransferDocumentDTO> partialUpdate(TransferDocumentDTO transferDocumentDTO);

    /**
     * Get all the transferDocuments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TransferDocumentDTO> findAll(Pageable pageable);

    /**
     * Get the "id" transferDocument.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TransferDocumentDTO> findOne(Long id);

    /**
     * Delete the "id" transferDocument.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
