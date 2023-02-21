package inc.donau.storage.service;

import inc.donau.storage.domain.*; // for static metamodels
import inc.donau.storage.domain.Storage;
import inc.donau.storage.repository.StorageRepository;
import inc.donau.storage.service.criteria.StorageCriteria;
import inc.donau.storage.service.dto.StorageDTO;
import inc.donau.storage.service.mapper.StorageMapper;
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
 * Service for executing complex queries for {@link Storage} entities in the database.
 * The main input is a {@link StorageCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link StorageDTO} or a {@link Page} of {@link StorageDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class StorageQueryService extends QueryService<Storage> {

    private final Logger log = LoggerFactory.getLogger(StorageQueryService.class);

    private final StorageRepository storageRepository;

    private final StorageMapper storageMapper;

    public StorageQueryService(StorageRepository storageRepository, StorageMapper storageMapper) {
        this.storageRepository = storageRepository;
        this.storageMapper = storageMapper;
    }

    /**
     * Return a {@link List} of {@link StorageDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<StorageDTO> findByCriteria(StorageCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Storage> specification = createSpecification(criteria);
        return storageMapper.toDto(storageRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link StorageDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<StorageDTO> findByCriteria(StorageCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Storage> specification = createSpecification(criteria);
        return storageRepository.findAll(specification, page).map(storageMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(StorageCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Storage> specification = createSpecification(criteria);
        return storageRepository.count(specification);
    }

    /**
     * Function to convert {@link StorageCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Storage> createSpecification(StorageCriteria criteria) {
        Specification<Storage> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Storage_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Storage_.name));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), Storage_.code));
            }
            if (criteria.getAddressId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getAddressId(), root -> root.join(Storage_.address, JoinType.LEFT).get(Address_.id))
                    );
            }
            if (criteria.getStorageCardId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getStorageCardId(),
                            root -> root.join(Storage_.storageCards, JoinType.LEFT).get(StorageCard_.id)
                        )
                    );
            }
            if (criteria.getReceivedId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getReceivedId(),
                            root -> root.join(Storage_.receiveds, JoinType.LEFT).get(TransferDocument_.id)
                        )
                    );
            }
            if (criteria.getDispatchedId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDispatchedId(),
                            root -> root.join(Storage_.dispatcheds, JoinType.LEFT).get(TransferDocument_.id)
                        )
                    );
            }
            if (criteria.getCensusDocumentId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCensusDocumentId(),
                            root -> root.join(Storage_.censusDocuments, JoinType.LEFT).get(CensusDocument_.id)
                        )
                    );
            }
            if (criteria.getCompanyId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getCompanyId(), root -> root.join(Storage_.company, JoinType.LEFT).get(Company_.id))
                    );
            }
        }
        return specification;
    }
}
