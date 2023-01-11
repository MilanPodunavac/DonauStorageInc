package inc.donau.storage.service;

import inc.donau.storage.domain.*; // for static metamodels
import inc.donau.storage.domain.BusinessYear;
import inc.donau.storage.repository.BusinessYearRepository;
import inc.donau.storage.service.criteria.BusinessYearCriteria;
import inc.donau.storage.service.dto.BusinessYearDTO;
import inc.donau.storage.service.mapper.BusinessYearMapper;
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
 * Service for executing complex queries for {@link BusinessYear} entities in the database.
 * The main input is a {@link BusinessYearCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link BusinessYearDTO} or a {@link Page} of {@link BusinessYearDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BusinessYearQueryService extends QueryService<BusinessYear> {

    private final Logger log = LoggerFactory.getLogger(BusinessYearQueryService.class);

    private final BusinessYearRepository businessYearRepository;

    private final BusinessYearMapper businessYearMapper;

    public BusinessYearQueryService(BusinessYearRepository businessYearRepository, BusinessYearMapper businessYearMapper) {
        this.businessYearRepository = businessYearRepository;
        this.businessYearMapper = businessYearMapper;
    }

    /**
     * Return a {@link List} of {@link BusinessYearDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<BusinessYearDTO> findByCriteria(BusinessYearCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<BusinessYear> specification = createSpecification(criteria);
        return businessYearMapper.toDto(businessYearRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link BusinessYearDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<BusinessYearDTO> findByCriteria(BusinessYearCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<BusinessYear> specification = createSpecification(criteria);
        return businessYearRepository.findAll(specification, page).map(businessYearMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(BusinessYearCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<BusinessYear> specification = createSpecification(criteria);
        return businessYearRepository.count(specification);
    }

    /**
     * Function to convert {@link BusinessYearCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<BusinessYear> createSpecification(BusinessYearCriteria criteria) {
        Specification<BusinessYear> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), BusinessYear_.id));
            }
            if (criteria.getYearCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getYearCode(), BusinessYear_.yearCode));
            }
            if (criteria.getCompleted() != null) {
                specification = specification.and(buildSpecification(criteria.getCompleted(), BusinessYear_.completed));
            }
            if (criteria.getCompanyId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCompanyId(),
                            root -> root.join(BusinessYear_.company, JoinType.LEFT).get(Company_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
