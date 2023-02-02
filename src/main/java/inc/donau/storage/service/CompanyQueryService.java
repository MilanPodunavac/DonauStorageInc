package inc.donau.storage.service;

import inc.donau.storage.domain.*; // for static metamodels
import inc.donau.storage.domain.Company;
import inc.donau.storage.repository.CompanyRepository;
import inc.donau.storage.service.criteria.CompanyCriteria;
import inc.donau.storage.service.dto.CompanyDTO;
import inc.donau.storage.service.mapper.CompanyMapper;
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
 * Service for executing complex queries for {@link Company} entities in the database.
 * The main input is a {@link CompanyCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CompanyDTO} or a {@link Page} of {@link CompanyDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CompanyQueryService extends QueryService<Company> {

    private final Logger log = LoggerFactory.getLogger(CompanyQueryService.class);

    private final CompanyRepository companyRepository;

    private final CompanyMapper companyMapper;

    public CompanyQueryService(CompanyRepository companyRepository, CompanyMapper companyMapper) {
        this.companyRepository = companyRepository;
        this.companyMapper = companyMapper;
    }

    /**
     * Return a {@link List} of {@link CompanyDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CompanyDTO> findByCriteria(CompanyCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Company> specification = createSpecification(criteria);
        return companyMapper.toDto(companyRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CompanyDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CompanyDTO> findByCriteria(CompanyCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Company> specification = createSpecification(criteria);
        return companyRepository.findAll(specification, page).map(companyMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CompanyCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Company> specification = createSpecification(criteria);
        return companyRepository.count(specification);
    }

    /**
     * Function to convert {@link CompanyCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Company> createSpecification(CompanyCriteria criteria) {
        Specification<Company> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Company_.id));
            }
            if (criteria.getLegalEntityInfoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getLegalEntityInfoId(),
                            root -> root.join(Company_.legalEntityInfo, JoinType.LEFT).get(LegalEntity_.id)
                        )
                    );
            }
            if (criteria.getResourceId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getResourceId(), root -> root.join(Company_.resources, JoinType.LEFT).get(Resource_.id))
                    );
            }
            if (criteria.getBusinessPartnerId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getBusinessPartnerId(),
                            root -> root.join(Company_.businessPartners, JoinType.LEFT).get(BusinessPartner_.id)
                        )
                    );
            }
            if (criteria.getBusinessYearId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getBusinessYearId(),
                            root -> root.join(Company_.businessYears, JoinType.LEFT).get(BusinessYear_.id)
                        )
                    );
            }
            if (criteria.getEmployeeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getEmployeeId(), root -> root.join(Company_.employees, JoinType.LEFT).get(Employee_.id))
                    );
            }
        }
        return specification;
    }
}
