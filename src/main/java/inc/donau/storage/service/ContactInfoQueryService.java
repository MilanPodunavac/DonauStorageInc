package inc.donau.storage.service;

import inc.donau.storage.domain.*; // for static metamodels
import inc.donau.storage.domain.ContactInfo;
import inc.donau.storage.repository.ContactInfoRepository;
import inc.donau.storage.service.criteria.ContactInfoCriteria;
import inc.donau.storage.service.dto.ContactInfoDTO;
import inc.donau.storage.service.mapper.ContactInfoMapper;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link ContactInfo} entities in the database.
 * The main input is a {@link ContactInfoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ContactInfoDTO} or a {@link Page} of {@link ContactInfoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ContactInfoQueryService extends QueryService<ContactInfo> {

    private final Logger log = LoggerFactory.getLogger(ContactInfoQueryService.class);

    private final ContactInfoRepository contactInfoRepository;

    private final ContactInfoMapper contactInfoMapper;

    public ContactInfoQueryService(ContactInfoRepository contactInfoRepository, ContactInfoMapper contactInfoMapper) {
        this.contactInfoRepository = contactInfoRepository;
        this.contactInfoMapper = contactInfoMapper;
    }

    /**
     * Return a {@link List} of {@link ContactInfoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ContactInfoDTO> findByCriteria(ContactInfoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ContactInfo> specification = createSpecification(criteria);
        return contactInfoMapper.toDto(contactInfoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ContactInfoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ContactInfoDTO> findByCriteria(ContactInfoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ContactInfo> specification = createSpecification(criteria);
        return contactInfoRepository.findAll(specification, page).map(contactInfoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ContactInfoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ContactInfo> specification = createSpecification(criteria);
        return contactInfoRepository.count(specification);
    }

    /**
     * Function to convert {@link ContactInfoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ContactInfo> createSpecification(ContactInfoCriteria criteria) {
        Specification<ContactInfo> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ContactInfo_.id));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), ContactInfo_.email));
            }
            if (criteria.getPhoneNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhoneNumber(), ContactInfo_.phoneNumber));
            }
        }
        return specification;
    }
}
