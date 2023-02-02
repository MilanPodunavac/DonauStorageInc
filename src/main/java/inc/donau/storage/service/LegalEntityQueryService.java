package inc.donau.storage.service;

import inc.donau.storage.domain.*; // for static metamodels
import inc.donau.storage.domain.LegalEntity;
import inc.donau.storage.repository.LegalEntityRepository;
import inc.donau.storage.service.criteria.LegalEntityCriteria;
import inc.donau.storage.service.dto.LegalEntityDTO;
import inc.donau.storage.service.mapper.LegalEntityMapper;
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
 * Service for executing complex queries for {@link LegalEntity} entities in the database.
 * The main input is a {@link LegalEntityCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link LegalEntityDTO} or a {@link Page} of {@link LegalEntityDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LegalEntityQueryService extends QueryService<LegalEntity> {

    private final Logger log = LoggerFactory.getLogger(LegalEntityQueryService.class);

    private final LegalEntityRepository legalEntityRepository;

    private final LegalEntityMapper legalEntityMapper;

    public LegalEntityQueryService(LegalEntityRepository legalEntityRepository, LegalEntityMapper legalEntityMapper) {
        this.legalEntityRepository = legalEntityRepository;
        this.legalEntityMapper = legalEntityMapper;
    }

    /**
     * Return a {@link List} of {@link LegalEntityDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<LegalEntityDTO> findByCriteria(LegalEntityCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<LegalEntity> specification = createSpecification(criteria);
        return legalEntityMapper.toDto(legalEntityRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link LegalEntityDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<LegalEntityDTO> findByCriteria(LegalEntityCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<LegalEntity> specification = createSpecification(criteria);
        return legalEntityRepository.findAll(specification, page).map(legalEntityMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LegalEntityCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<LegalEntity> specification = createSpecification(criteria);
        return legalEntityRepository.count(specification);
    }

    /**
     * Function to convert {@link LegalEntityCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<LegalEntity> createSpecification(LegalEntityCriteria criteria) {
        Specification<LegalEntity> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), LegalEntity_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), LegalEntity_.name));
            }
            if (criteria.getTaxIdentificationNumber() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getTaxIdentificationNumber(), LegalEntity_.taxIdentificationNumber)
                    );
            }
            if (criteria.getIdentificationNumber() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getIdentificationNumber(), LegalEntity_.identificationNumber));
            }
            if (criteria.getContactInfoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getContactInfoId(),
                            root -> root.join(LegalEntity_.contactInfo, JoinType.LEFT).get(ContactInfo_.id)
                        )
                    );
            }
            if (criteria.getAddressId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getAddressId(), root -> root.join(LegalEntity_.address, JoinType.LEFT).get(Address_.id))
                    );
            }
        }
        return specification;
    }
}
