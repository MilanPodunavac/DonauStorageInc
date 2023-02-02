package inc.donau.storage.service;

import inc.donau.storage.domain.*; // for static metamodels
import inc.donau.storage.domain.BusinessContact;
import inc.donau.storage.repository.BusinessContactRepository;
import inc.donau.storage.service.criteria.BusinessContactCriteria;
import inc.donau.storage.service.dto.BusinessContactDTO;
import inc.donau.storage.service.mapper.BusinessContactMapper;
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
 * Service for executing complex queries for {@link BusinessContact} entities in the database.
 * The main input is a {@link BusinessContactCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link BusinessContactDTO} or a {@link Page} of {@link BusinessContactDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BusinessContactQueryService extends QueryService<BusinessContact> {

    private final Logger log = LoggerFactory.getLogger(BusinessContactQueryService.class);

    private final BusinessContactRepository businessContactRepository;

    private final BusinessContactMapper businessContactMapper;

    public BusinessContactQueryService(BusinessContactRepository businessContactRepository, BusinessContactMapper businessContactMapper) {
        this.businessContactRepository = businessContactRepository;
        this.businessContactMapper = businessContactMapper;
    }

    /**
     * Return a {@link List} of {@link BusinessContactDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<BusinessContactDTO> findByCriteria(BusinessContactCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<BusinessContact> specification = createSpecification(criteria);
        return businessContactMapper.toDto(businessContactRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link BusinessContactDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<BusinessContactDTO> findByCriteria(BusinessContactCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<BusinessContact> specification = createSpecification(criteria);
        return businessContactRepository.findAll(specification, page).map(businessContactMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(BusinessContactCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<BusinessContact> specification = createSpecification(criteria);
        return businessContactRepository.count(specification);
    }

    /**
     * Function to convert {@link BusinessContactCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<BusinessContact> createSpecification(BusinessContactCriteria criteria) {
        Specification<BusinessContact> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), BusinessContact_.id));
            }
            if (criteria.getPersonalInfoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPersonalInfoId(),
                            root -> root.join(BusinessContact_.personalInfo, JoinType.LEFT).get(Person_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
