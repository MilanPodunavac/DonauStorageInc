package inc.donau.storage.service;

import inc.donau.storage.domain.LegalEntity;
import inc.donau.storage.repository.LegalEntityRepository;
import inc.donau.storage.service.dto.LegalEntityDTO;
import inc.donau.storage.service.mapper.LegalEntityMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link LegalEntity}.
 */
@Service
@Transactional
public class LegalEntityService {

    private final Logger log = LoggerFactory.getLogger(LegalEntityService.class);

    private final LegalEntityRepository legalEntityRepository;

    private final LegalEntityMapper legalEntityMapper;

    public LegalEntityService(LegalEntityRepository legalEntityRepository, LegalEntityMapper legalEntityMapper) {
        this.legalEntityRepository = legalEntityRepository;
        this.legalEntityMapper = legalEntityMapper;
    }

    /**
     * Save a legalEntity.
     *
     * @param legalEntityDTO the entity to save.
     * @return the persisted entity.
     */
    public LegalEntityDTO save(LegalEntityDTO legalEntityDTO) {
        log.debug("Request to save LegalEntity : {}", legalEntityDTO);
        LegalEntity legalEntity = legalEntityMapper.toEntity(legalEntityDTO);
        legalEntity = legalEntityRepository.save(legalEntity);
        return legalEntityMapper.toDto(legalEntity);
    }

    /**
     * Update a legalEntity.
     *
     * @param legalEntityDTO the entity to save.
     * @return the persisted entity.
     */
    public LegalEntityDTO update(LegalEntityDTO legalEntityDTO) {
        log.debug("Request to update LegalEntity : {}", legalEntityDTO);
        LegalEntity legalEntity = legalEntityMapper.toEntity(legalEntityDTO);
        legalEntity = legalEntityRepository.save(legalEntity);
        return legalEntityMapper.toDto(legalEntity);
    }

    /**
     * Partially update a legalEntity.
     *
     * @param legalEntityDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<LegalEntityDTO> partialUpdate(LegalEntityDTO legalEntityDTO) {
        log.debug("Request to partially update LegalEntity : {}", legalEntityDTO);

        return legalEntityRepository
            .findById(legalEntityDTO.getId())
            .map(existingLegalEntity -> {
                legalEntityMapper.partialUpdate(existingLegalEntity, legalEntityDTO);

                return existingLegalEntity;
            })
            .map(legalEntityRepository::save)
            .map(legalEntityMapper::toDto);
    }

    /**
     * Get all the legalEntities.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<LegalEntityDTO> findAll() {
        log.debug("Request to get all LegalEntities");
        return legalEntityRepository.findAll().stream().map(legalEntityMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the legalEntities with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<LegalEntityDTO> findAllWithEagerRelationships(Pageable pageable) {
        return legalEntityRepository.findAllWithEagerRelationships(pageable).map(legalEntityMapper::toDto);
    }

    /**
     *  Get all the legalEntities where BusinessPartner is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<LegalEntityDTO> findAllWhereBusinessPartnerIsNull() {
        log.debug("Request to get all legalEntities where BusinessPartner is null");
        return StreamSupport
            .stream(legalEntityRepository.findAll().spliterator(), false)
            .filter(legalEntity -> legalEntity.getBusinessPartner() == null)
            .map(legalEntityMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the legalEntities where Company is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<LegalEntityDTO> findAllWhereCompanyIsNull() {
        log.debug("Request to get all legalEntities where Company is null");
        return StreamSupport
            .stream(legalEntityRepository.findAll().spliterator(), false)
            .filter(legalEntity -> legalEntity.getCompany() == null)
            .map(legalEntityMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one legalEntity by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LegalEntityDTO> findOne(Long id) {
        log.debug("Request to get LegalEntity : {}", id);
        return legalEntityRepository.findOneWithEagerRelationships(id).map(legalEntityMapper::toDto);
    }

    /**
     * Delete the legalEntity by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete LegalEntity : {}", id);
        legalEntityRepository.deleteById(id);
    }
}
