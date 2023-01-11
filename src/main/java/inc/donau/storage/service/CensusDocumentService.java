package inc.donau.storage.service;

import inc.donau.storage.service.dto.CensusDocumentDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link inc.donau.storage.domain.CensusDocument}.
 */
public interface CensusDocumentService {
    /**
     * Save a censusDocument.
     *
     * @param censusDocumentDTO the entity to save.
     * @return the persisted entity.
     */
    CensusDocumentDTO save(CensusDocumentDTO censusDocumentDTO);

    /**
     * Updates a censusDocument.
     *
     * @param censusDocumentDTO the entity to update.
     * @return the persisted entity.
     */
    CensusDocumentDTO update(CensusDocumentDTO censusDocumentDTO);

    /**
     * Partially updates a censusDocument.
     *
     * @param censusDocumentDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CensusDocumentDTO> partialUpdate(CensusDocumentDTO censusDocumentDTO);

    /**
     * Get all the censusDocuments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CensusDocumentDTO> findAll(Pageable pageable);

    /**
     * Get the "id" censusDocument.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CensusDocumentDTO> findOne(Long id);

    /**
     * Delete the "id" censusDocument.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
