package inc.donau.storage.service;

import inc.donau.storage.domain.BusinessPartner;
import inc.donau.storage.repository.BusinessPartnerRepository;
import inc.donau.storage.service.dto.BusinessPartnerDTO;
import inc.donau.storage.service.mapper.BusinessPartnerMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link BusinessPartner}.
 */
@Service
@Transactional
public class BusinessPartnerService {

    private final Logger log = LoggerFactory.getLogger(BusinessPartnerService.class);

    private final BusinessPartnerRepository businessPartnerRepository;

    private final BusinessPartnerMapper businessPartnerMapper;

    public BusinessPartnerService(BusinessPartnerRepository businessPartnerRepository, BusinessPartnerMapper businessPartnerMapper) {
        this.businessPartnerRepository = businessPartnerRepository;
        this.businessPartnerMapper = businessPartnerMapper;
    }

    /**
     * Save a businessPartner.
     *
     * @param businessPartnerDTO the entity to save.
     * @return the persisted entity.
     */
    public BusinessPartnerDTO save(BusinessPartnerDTO businessPartnerDTO) {
        log.debug("Request to save BusinessPartner : {}", businessPartnerDTO);
        BusinessPartner businessPartner = businessPartnerMapper.toEntity(businessPartnerDTO);
        businessPartner = businessPartnerRepository.save(businessPartner);
        return businessPartnerMapper.toDto(businessPartner);
    }

    /**
     * Update a businessPartner.
     *
     * @param businessPartnerDTO the entity to save.
     * @return the persisted entity.
     */
    public BusinessPartnerDTO update(BusinessPartnerDTO businessPartnerDTO) {
        log.debug("Request to update BusinessPartner : {}", businessPartnerDTO);
        BusinessPartner businessPartner = businessPartnerMapper.toEntity(businessPartnerDTO);
        businessPartner = businessPartnerRepository.save(businessPartner);
        return businessPartnerMapper.toDto(businessPartner);
    }

    /**
     * Partially update a businessPartner.
     *
     * @param businessPartnerDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<BusinessPartnerDTO> partialUpdate(BusinessPartnerDTO businessPartnerDTO) {
        log.debug("Request to partially update BusinessPartner : {}", businessPartnerDTO);

        return businessPartnerRepository
            .findById(businessPartnerDTO.getId())
            .map(existingBusinessPartner -> {
                businessPartnerMapper.partialUpdate(existingBusinessPartner, businessPartnerDTO);

                return existingBusinessPartner;
            })
            .map(businessPartnerRepository::save)
            .map(businessPartnerMapper::toDto);
    }

    /**
     * Get all the businessPartners.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<BusinessPartnerDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BusinessPartners");
        return businessPartnerRepository.findAll(pageable).map(businessPartnerMapper::toDto);
    }

    /**
     * Get one businessPartner by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BusinessPartnerDTO> findOne(Long id) {
        log.debug("Request to get BusinessPartner : {}", id);
        return businessPartnerRepository.findById(id).map(businessPartnerMapper::toDto);
    }

    /**
     * Delete the businessPartner by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete BusinessPartner : {}", id);
        businessPartnerRepository.deleteById(id);
    }
}
