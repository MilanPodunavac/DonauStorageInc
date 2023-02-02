package inc.donau.storage.service;

import inc.donau.storage.domain.*; // for static metamodels
import inc.donau.storage.domain.StorageCardTraffic;
import inc.donau.storage.repository.StorageCardTrafficRepository;
import inc.donau.storage.service.criteria.StorageCardTrafficCriteria;
import inc.donau.storage.service.dto.StorageCardTrafficDTO;
import inc.donau.storage.service.mapper.StorageCardTrafficMapper;
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
 * Service for executing complex queries for {@link StorageCardTraffic} entities in the database.
 * The main input is a {@link StorageCardTrafficCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link StorageCardTrafficDTO} or a {@link Page} of {@link StorageCardTrafficDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class StorageCardTrafficQueryService extends QueryService<StorageCardTraffic> {

    private final Logger log = LoggerFactory.getLogger(StorageCardTrafficQueryService.class);

    private final StorageCardTrafficRepository storageCardTrafficRepository;

    private final StorageCardTrafficMapper storageCardTrafficMapper;

    public StorageCardTrafficQueryService(
        StorageCardTrafficRepository storageCardTrafficRepository,
        StorageCardTrafficMapper storageCardTrafficMapper
    ) {
        this.storageCardTrafficRepository = storageCardTrafficRepository;
        this.storageCardTrafficMapper = storageCardTrafficMapper;
    }

    /**
     * Return a {@link List} of {@link StorageCardTrafficDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<StorageCardTrafficDTO> findByCriteria(StorageCardTrafficCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<StorageCardTraffic> specification = createSpecification(criteria);
        return storageCardTrafficMapper.toDto(storageCardTrafficRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link StorageCardTrafficDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<StorageCardTrafficDTO> findByCriteria(StorageCardTrafficCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<StorageCardTraffic> specification = createSpecification(criteria);
        return storageCardTrafficRepository.findAll(specification, page).map(storageCardTrafficMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(StorageCardTrafficCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<StorageCardTraffic> specification = createSpecification(criteria);
        return storageCardTrafficRepository.count(specification);
    }

    /**
     * Function to convert {@link StorageCardTrafficCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<StorageCardTraffic> createSpecification(StorageCardTrafficCriteria criteria) {
        Specification<StorageCardTraffic> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), StorageCardTraffic_.id));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildSpecification(criteria.getType(), StorageCardTraffic_.type));
            }
            if (criteria.getDirection() != null) {
                specification = specification.and(buildSpecification(criteria.getDirection(), StorageCardTraffic_.direction));
            }
            if (criteria.getAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmount(), StorageCardTraffic_.amount));
            }
            if (criteria.getPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrice(), StorageCardTraffic_.price));
            }
            if (criteria.getTrafficValue() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTrafficValue(), StorageCardTraffic_.trafficValue));
            }
            if (criteria.getDocument() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocument(), StorageCardTraffic_.document));
            }
            if (criteria.getDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDate(), StorageCardTraffic_.date));
            }
            if (criteria.getStorageCardId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getStorageCardId(),
                            root -> root.join(StorageCardTraffic_.storageCard, JoinType.LEFT).get(StorageCard_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
