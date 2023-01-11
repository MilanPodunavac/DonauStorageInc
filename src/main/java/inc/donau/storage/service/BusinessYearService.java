package inc.donau.storage.service;

import inc.donau.storage.domain.BusinessYear;
import inc.donau.storage.repository.BusinessYearRepository;
import inc.donau.storage.service.dto.BusinessYearDTO;
import inc.donau.storage.service.mapper.BusinessYearMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link BusinessYear}.
 */
@Service
@Transactional
public class BusinessYearService {

    private final Logger log = LoggerFactory.getLogger(BusinessYearService.class);

    private final BusinessYearRepository businessYearRepository;

    private final BusinessYearMapper businessYearMapper;

    public BusinessYearService(BusinessYearRepository businessYearRepository, BusinessYearMapper businessYearMapper) {
        this.businessYearRepository = businessYearRepository;
        this.businessYearMapper = businessYearMapper;
    }

    /**
     * Save a businessYear.
     *
     * @param businessYearDTO the entity to save.
     * @return the persisted entity.
     */
    public BusinessYearDTO save(BusinessYearDTO businessYearDTO) {
        log.debug("Request to save BusinessYear : {}", businessYearDTO);
        BusinessYear businessYear = businessYearMapper.toEntity(businessYearDTO);
        businessYear = businessYearRepository.save(businessYear);
        return businessYearMapper.toDto(businessYear);
    }

    /**
     * Update a businessYear.
     *
     * @param businessYearDTO the entity to save.
     * @return the persisted entity.
     */
    public BusinessYearDTO update(BusinessYearDTO businessYearDTO) {
        log.debug("Request to update BusinessYear : {}", businessYearDTO);
        BusinessYear businessYear = businessYearMapper.toEntity(businessYearDTO);
        businessYear = businessYearRepository.save(businessYear);
        return businessYearMapper.toDto(businessYear);
    }

    /**
     * Partially update a businessYear.
     *
     * @param businessYearDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<BusinessYearDTO> partialUpdate(BusinessYearDTO businessYearDTO) {
        log.debug("Request to partially update BusinessYear : {}", businessYearDTO);

        return businessYearRepository
            .findById(businessYearDTO.getId())
            .map(existingBusinessYear -> {
                businessYearMapper.partialUpdate(existingBusinessYear, businessYearDTO);

                return existingBusinessYear;
            })
            .map(businessYearRepository::save)
            .map(businessYearMapper::toDto);
    }

    /**
     * Get all the businessYears.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<BusinessYearDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BusinessYears");
        return businessYearRepository.findAll(pageable).map(businessYearMapper::toDto);
    }

    /**
     * Get one businessYear by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BusinessYearDTO> findOne(Long id) {
        log.debug("Request to get BusinessYear : {}", id);
        return businessYearRepository.findById(id).map(businessYearMapper::toDto);
    }

    /**
     * Delete the businessYear by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete BusinessYear : {}", id);
        businessYearRepository.deleteById(id);
    }
}
