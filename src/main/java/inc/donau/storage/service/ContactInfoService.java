package inc.donau.storage.service;

import inc.donau.storage.domain.ContactInfo;
import inc.donau.storage.repository.ContactInfoRepository;
import inc.donau.storage.service.dto.ContactInfoDTO;
import inc.donau.storage.service.mapper.ContactInfoMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ContactInfo}.
 */
@Service
@Transactional
public class ContactInfoService {

    private final Logger log = LoggerFactory.getLogger(ContactInfoService.class);

    private final ContactInfoRepository contactInfoRepository;

    private final ContactInfoMapper contactInfoMapper;

    public ContactInfoService(ContactInfoRepository contactInfoRepository, ContactInfoMapper contactInfoMapper) {
        this.contactInfoRepository = contactInfoRepository;
        this.contactInfoMapper = contactInfoMapper;
    }

    /**
     * Save a contactInfo.
     *
     * @param contactInfoDTO the entity to save.
     * @return the persisted entity.
     */
    public ContactInfoDTO save(ContactInfoDTO contactInfoDTO) {
        log.debug("Request to save ContactInfo : {}", contactInfoDTO);
        ContactInfo contactInfo = contactInfoMapper.toEntity(contactInfoDTO);
        contactInfo = contactInfoRepository.save(contactInfo);
        return contactInfoMapper.toDto(contactInfo);
    }

    /**
     * Update a contactInfo.
     *
     * @param contactInfoDTO the entity to save.
     * @return the persisted entity.
     */
    public ContactInfoDTO update(ContactInfoDTO contactInfoDTO) {
        log.debug("Request to update ContactInfo : {}", contactInfoDTO);
        ContactInfo contactInfo = contactInfoMapper.toEntity(contactInfoDTO);
        contactInfo = contactInfoRepository.save(contactInfo);
        return contactInfoMapper.toDto(contactInfo);
    }

    /**
     * Partially update a contactInfo.
     *
     * @param contactInfoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ContactInfoDTO> partialUpdate(ContactInfoDTO contactInfoDTO) {
        log.debug("Request to partially update ContactInfo : {}", contactInfoDTO);

        return contactInfoRepository
            .findById(contactInfoDTO.getId())
            .map(existingContactInfo -> {
                contactInfoMapper.partialUpdate(existingContactInfo, contactInfoDTO);

                return existingContactInfo;
            })
            .map(contactInfoRepository::save)
            .map(contactInfoMapper::toDto);
    }

    /**
     * Get all the contactInfos.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ContactInfoDTO> findAll() {
        log.debug("Request to get all ContactInfos");
        return contactInfoRepository.findAll().stream().map(contactInfoMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the contactInfos where Person is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ContactInfoDTO> findAllWherePersonIsNull() {
        log.debug("Request to get all contactInfos where Person is null");
        return StreamSupport
            .stream(contactInfoRepository.findAll().spliterator(), false)
            .filter(contactInfo -> contactInfo.getPerson() == null)
            .map(contactInfoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the contactInfos where LegalEntity is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ContactInfoDTO> findAllWhereLegalEntityIsNull() {
        log.debug("Request to get all contactInfos where LegalEntity is null");
        return StreamSupport
            .stream(contactInfoRepository.findAll().spliterator(), false)
            .filter(contactInfo -> contactInfo.getLegalEntity() == null)
            .map(contactInfoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one contactInfo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ContactInfoDTO> findOne(Long id) {
        log.debug("Request to get ContactInfo : {}", id);
        return contactInfoRepository.findById(id).map(contactInfoMapper::toDto);
    }

    /**
     * Delete the contactInfo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ContactInfo : {}", id);
        contactInfoRepository.deleteById(id);
    }
}
