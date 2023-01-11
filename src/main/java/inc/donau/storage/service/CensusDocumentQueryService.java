package inc.donau.storage.service;

import inc.donau.storage.domain.*; // for static metamodels
import inc.donau.storage.domain.CensusDocument;
import inc.donau.storage.repository.CensusDocumentRepository;
import inc.donau.storage.service.criteria.CensusDocumentCriteria;
import inc.donau.storage.service.dto.CensusDocumentDTO;
import inc.donau.storage.service.mapper.CensusDocumentMapper;
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
 * Service for executing complex queries for {@link CensusDocument} entities in the database.
 * The main input is a {@link CensusDocumentCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CensusDocumentDTO} or a {@link Page} of {@link CensusDocumentDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CensusDocumentQueryService extends QueryService<CensusDocument> {

    private final Logger log = LoggerFactory.getLogger(CensusDocumentQueryService.class);

    private final CensusDocumentRepository censusDocumentRepository;

    private final CensusDocumentMapper censusDocumentMapper;

    public CensusDocumentQueryService(CensusDocumentRepository censusDocumentRepository, CensusDocumentMapper censusDocumentMapper) {
        this.censusDocumentRepository = censusDocumentRepository;
        this.censusDocumentMapper = censusDocumentMapper;
    }

    /**
     * Return a {@link List} of {@link CensusDocumentDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CensusDocumentDTO> findByCriteria(CensusDocumentCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CensusDocument> specification = createSpecification(criteria);
        return censusDocumentMapper.toDto(censusDocumentRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CensusDocumentDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CensusDocumentDTO> findByCriteria(CensusDocumentCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CensusDocument> specification = createSpecification(criteria);
        return censusDocumentRepository.findAll(specification, page).map(censusDocumentMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CensusDocumentCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CensusDocument> specification = createSpecification(criteria);
        return censusDocumentRepository.count(specification);
    }

    /**
     * Function to convert {@link CensusDocumentCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CensusDocument> createSpecification(CensusDocumentCriteria criteria) {
        Specification<CensusDocument> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CensusDocument_.id));
            }
            if (criteria.getCensusDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCensusDate(), CensusDocument_.censusDate));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), CensusDocument_.status));
            }
            if (criteria.getAccountingDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAccountingDate(), CensusDocument_.accountingDate));
            }
            if (criteria.getLeveling() != null) {
                specification = specification.and(buildSpecification(criteria.getLeveling(), CensusDocument_.leveling));
            }
            if (criteria.getCensusItemId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCensusItemId(),
                            root -> root.join(CensusDocument_.censusItems, JoinType.LEFT).get(CensusItem_.id)
                        )
                    );
            }
            if (criteria.getBusinessYearId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getBusinessYearId(),
                            root -> root.join(CensusDocument_.businessYear, JoinType.LEFT).get(BusinessYear_.id)
                        )
                    );
            }
            if (criteria.getPresidentId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPresidentId(),
                            root -> root.join(CensusDocument_.president, JoinType.LEFT).get(Employee_.id)
                        )
                    );
            }
            if (criteria.getDeputyId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDeputyId(),
                            root -> root.join(CensusDocument_.deputy, JoinType.LEFT).get(Employee_.id)
                        )
                    );
            }
            if (criteria.getCensusTakerId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCensusTakerId(),
                            root -> root.join(CensusDocument_.censusTaker, JoinType.LEFT).get(Employee_.id)
                        )
                    );
            }
            if (criteria.getStorageId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getStorageId(),
                            root -> root.join(CensusDocument_.storage, JoinType.LEFT).get(Storage_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
