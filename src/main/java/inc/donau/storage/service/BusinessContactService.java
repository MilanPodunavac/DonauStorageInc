package inc.donau.storage.service;

import inc.donau.storage.domain.BusinessContact;
import inc.donau.storage.repository.BusinessContactRepository;
import inc.donau.storage.service.dto.BusinessContactDTO;
import inc.donau.storage.service.mapper.BusinessContactMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link BusinessContact}.
 */
@Service
@Transactional
public class BusinessContactService {

    private final Logger log = LoggerFactory.getLogger(BusinessContactService.class);

    private final BusinessContactRepository businessContactRepository;

    private final BusinessContactMapper businessContactMapper;

    public BusinessContactService(BusinessContactRepository businessContactRepository, BusinessContactMapper businessContactMapper) {
        this.businessContactRepository = businessContactRepository;
        this.businessContactMapper = businessContactMapper;
    }

    /**
     * Save a businessContact.
     *
     * @param businessContactDTO the entity to save.
     * @return the persisted entity.
     */
    public BusinessContactDTO save(BusinessContactDTO businessContactDTO) {
        log.debug("Request to save BusinessContact : {}", businessContactDTO);
        BusinessContact businessContact = businessContactMapper.toEntity(businessContactDTO);
        businessContact = businessContactRepository.save(businessContact);
        return businessContactMapper.toDto(businessContact);
    }

    /**
     * Update a businessContact.
     *
     * @param businessContactDTO the entity to save.
     * @return the persisted entity.
     */
    public BusinessContactDTO update(BusinessContactDTO businessContactDTO) {
        log.debug("Request to update BusinessContact : {}", businessContactDTO);
        BusinessContact businessContact = businessContactMapper.toEntity(businessContactDTO);
        businessContact = businessContactRepository.save(businessContact);
        return businessContactMapper.toDto(businessContact);
    }

    /**
     * Partially update a businessContact.
     *
     * @param businessContactDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<BusinessContactDTO> partialUpdate(BusinessContactDTO businessContactDTO) {
        log.debug("Request to partially update BusinessContact : {}", businessContactDTO);

        return businessContactRepository
            .findById(businessContactDTO.getId())
            .map(existingBusinessContact -> {
                businessContactMapper.partialUpdate(existingBusinessContact, businessContactDTO);

                return existingBusinessContact;
            })
            .map(businessContactRepository::save)
            .map(businessContactMapper::toDto);
    }

    /**
     * Get all the businessContacts.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<BusinessContactDTO> findAll() {
        log.debug("Request to get all BusinessContacts");
        return businessContactRepository
            .findAll()
            .stream()
            .map(businessContactMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one businessContact by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BusinessContactDTO> findOne(Long id) {
        log.debug("Request to get BusinessContact : {}", id);
        return businessContactRepository.findById(id).map(businessContactMapper::toDto);
    }

    /**
     * Delete the businessContact by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete BusinessContact : {}", id);
        businessContactRepository.deleteById(id);
    }
}
