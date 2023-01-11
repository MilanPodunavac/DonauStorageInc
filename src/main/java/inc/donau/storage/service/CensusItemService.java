package inc.donau.storage.service;

import inc.donau.storage.domain.CensusItem;
import inc.donau.storage.repository.CensusItemRepository;
import inc.donau.storage.service.dto.CensusItemDTO;
import inc.donau.storage.service.mapper.CensusItemMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CensusItem}.
 */
@Service
@Transactional
public class CensusItemService {

    private final Logger log = LoggerFactory.getLogger(CensusItemService.class);

    private final CensusItemRepository censusItemRepository;

    private final CensusItemMapper censusItemMapper;

    public CensusItemService(CensusItemRepository censusItemRepository, CensusItemMapper censusItemMapper) {
        this.censusItemRepository = censusItemRepository;
        this.censusItemMapper = censusItemMapper;
    }

    /**
     * Save a censusItem.
     *
     * @param censusItemDTO the entity to save.
     * @return the persisted entity.
     */
    public CensusItemDTO save(CensusItemDTO censusItemDTO) {
        log.debug("Request to save CensusItem : {}", censusItemDTO);
        CensusItem censusItem = censusItemMapper.toEntity(censusItemDTO);
        censusItem = censusItemRepository.save(censusItem);
        return censusItemMapper.toDto(censusItem);
    }

    /**
     * Update a censusItem.
     *
     * @param censusItemDTO the entity to save.
     * @return the persisted entity.
     */
    public CensusItemDTO update(CensusItemDTO censusItemDTO) {
        log.debug("Request to update CensusItem : {}", censusItemDTO);
        CensusItem censusItem = censusItemMapper.toEntity(censusItemDTO);
        censusItem = censusItemRepository.save(censusItem);
        return censusItemMapper.toDto(censusItem);
    }

    /**
     * Partially update a censusItem.
     *
     * @param censusItemDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CensusItemDTO> partialUpdate(CensusItemDTO censusItemDTO) {
        log.debug("Request to partially update CensusItem : {}", censusItemDTO);

        return censusItemRepository
            .findById(censusItemDTO.getId())
            .map(existingCensusItem -> {
                censusItemMapper.partialUpdate(existingCensusItem, censusItemDTO);

                return existingCensusItem;
            })
            .map(censusItemRepository::save)
            .map(censusItemMapper::toDto);
    }

    /**
     * Get all the censusItems.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CensusItemDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CensusItems");
        return censusItemRepository.findAll(pageable).map(censusItemMapper::toDto);
    }

    /**
     * Get one censusItem by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CensusItemDTO> findOne(Long id) {
        log.debug("Request to get CensusItem : {}", id);
        return censusItemRepository.findById(id).map(censusItemMapper::toDto);
    }

    /**
     * Delete the censusItem by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CensusItem : {}", id);
        censusItemRepository.deleteById(id);
    }
}
