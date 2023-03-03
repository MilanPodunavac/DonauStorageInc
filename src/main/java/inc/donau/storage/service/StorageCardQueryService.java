package inc.donau.storage.service;

import inc.donau.storage.domain.*; // for static metamodels
import inc.donau.storage.domain.StorageCard;
import inc.donau.storage.repository.StorageCardRepository;
import inc.donau.storage.service.criteria.StorageCardCriteria;
import inc.donau.storage.service.dto.StorageCardDTO;
import inc.donau.storage.service.mapper.StorageCardMapper;
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
 * Service for executing complex queries for {@link StorageCard} entities in the database.
 * The main input is a {@link StorageCardCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link StorageCardDTO} or a {@link Page} of {@link StorageCardDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class StorageCardQueryService extends QueryService<StorageCard> {

    private final Logger log = LoggerFactory.getLogger(StorageCardQueryService.class);

    private final StorageCardRepository storageCardRepository;

    private final StorageCardMapper storageCardMapper;

    public StorageCardQueryService(StorageCardRepository storageCardRepository, StorageCardMapper storageCardMapper) {
        this.storageCardRepository = storageCardRepository;
        this.storageCardMapper = storageCardMapper;
    }

    /**
     * Return a {@link List} of {@link StorageCardDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<StorageCardDTO> findByCriteria(StorageCardCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<StorageCard> specification = createSpecification(criteria);
        return storageCardMapper.toDto(storageCardRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link StorageCardDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<StorageCardDTO> findByCriteria(StorageCardCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<StorageCard> specification = createSpecification(criteria);
        return storageCardRepository.findAll(specification, page).map(storageCardMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(StorageCardCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<StorageCard> specification = createSpecification(criteria);
        return storageCardRepository.count(specification);
    }

    /**
     * Function to convert {@link StorageCardCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<StorageCard> createSpecification(StorageCardCriteria criteria) {
        Specification<StorageCard> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getId(), StorageCard_.id));
            }
            if (criteria.getStartingAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartingAmount(), StorageCard_.startingAmount));
            }
            if (criteria.getReceivedAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getReceivedAmount(), StorageCard_.receivedAmount));
            }
            if (criteria.getDispatchedAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDispatchedAmount(), StorageCard_.dispatchedAmount));
            }
            if (criteria.getTotalAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalAmount(), StorageCard_.totalAmount));
            }
            if (criteria.getStartingValue() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartingValue(), StorageCard_.startingValue));
            }
            if (criteria.getReceivedValue() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getReceivedValue(), StorageCard_.receivedValue));
            }
            if (criteria.getDispatchedValue() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDispatchedValue(), StorageCard_.dispatchedValue));
            }
            if (criteria.getTotalValue() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalValue(), StorageCard_.totalValue));
            }
            if (criteria.getPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrice(), StorageCard_.price));
            }
            if (criteria.getTrafficId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getTrafficId(),
                            root -> root.join(StorageCard_.traffic, JoinType.LEFT).get(StorageCardTraffic_.id)
                        )
                    );
            }
            if (criteria.getBusinessYearId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getBusinessYearId(),
                            root -> root.join(StorageCard_.businessYear, JoinType.LEFT).get(BusinessYear_.id)
                        )
                    );
            }
            if (criteria.getResourceId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getResourceId(),
                            root -> root.join(StorageCard_.resource, JoinType.LEFT).get(Resource_.id)
                        )
                    );
            }
            if (criteria.getStorageId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getStorageId(), root -> root.join(StorageCard_.storage, JoinType.LEFT).get(Storage_.id))
                    );
            }
        }
        return specification;
    }
}
