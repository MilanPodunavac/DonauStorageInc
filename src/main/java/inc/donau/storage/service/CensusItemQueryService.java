package inc.donau.storage.service;

import inc.donau.storage.domain.*; // for static metamodels
import inc.donau.storage.domain.CensusItem;
import inc.donau.storage.repository.CensusItemRepository;
import inc.donau.storage.service.criteria.CensusItemCriteria;
import inc.donau.storage.service.dto.CensusItemDTO;
import inc.donau.storage.service.mapper.CensusItemMapper;
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
 * Service for executing complex queries for {@link CensusItem} entities in the database.
 * The main input is a {@link CensusItemCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CensusItemDTO} or a {@link Page} of {@link CensusItemDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CensusItemQueryService extends QueryService<CensusItem> {

    private final Logger log = LoggerFactory.getLogger(CensusItemQueryService.class);

    private final CensusItemRepository censusItemRepository;

    private final CensusItemMapper censusItemMapper;

    public CensusItemQueryService(CensusItemRepository censusItemRepository, CensusItemMapper censusItemMapper) {
        this.censusItemRepository = censusItemRepository;
        this.censusItemMapper = censusItemMapper;
    }

    /**
     * Return a {@link List} of {@link CensusItemDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CensusItemDTO> findByCriteria(CensusItemCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CensusItem> specification = createSpecification(criteria);
        return censusItemMapper.toDto(censusItemRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CensusItemDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CensusItemDTO> findByCriteria(CensusItemCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CensusItem> specification = createSpecification(criteria);
        return censusItemRepository.findAll(specification, page).map(censusItemMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CensusItemCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CensusItem> specification = createSpecification(criteria);
        return censusItemRepository.count(specification);
    }

    /**
     * Function to convert {@link CensusItemCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CensusItem> createSpecification(CensusItemCriteria criteria) {
        Specification<CensusItem> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CensusItem_.id));
            }
            if (criteria.getAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmount(), CensusItem_.amount));
            }
            if (criteria.getCensusDocumentId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCensusDocumentId(),
                            root -> root.join(CensusItem_.censusDocument, JoinType.LEFT).get(CensusDocument_.id)
                        )
                    );
            }
            if (criteria.getResourceId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getResourceId(),
                            root -> root.join(CensusItem_.resource, JoinType.LEFT).get(Resource_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
