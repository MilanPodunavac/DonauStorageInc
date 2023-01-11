package inc.donau.storage.service;

import inc.donau.storage.domain.*; // for static metamodels
import inc.donau.storage.domain.TransferDocument;
import inc.donau.storage.repository.TransferDocumentRepository;
import inc.donau.storage.service.criteria.TransferDocumentCriteria;
import inc.donau.storage.service.dto.TransferDocumentDTO;
import inc.donau.storage.service.mapper.TransferDocumentMapper;
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
 * Service for executing complex queries for {@link TransferDocument} entities in the database.
 * The main input is a {@link TransferDocumentCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TransferDocumentDTO} or a {@link Page} of {@link TransferDocumentDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TransferDocumentQueryService extends QueryService<TransferDocument> {

    private final Logger log = LoggerFactory.getLogger(TransferDocumentQueryService.class);

    private final TransferDocumentRepository transferDocumentRepository;

    private final TransferDocumentMapper transferDocumentMapper;

    public TransferDocumentQueryService(
        TransferDocumentRepository transferDocumentRepository,
        TransferDocumentMapper transferDocumentMapper
    ) {
        this.transferDocumentRepository = transferDocumentRepository;
        this.transferDocumentMapper = transferDocumentMapper;
    }

    /**
     * Return a {@link List} of {@link TransferDocumentDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TransferDocumentDTO> findByCriteria(TransferDocumentCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TransferDocument> specification = createSpecification(criteria);
        return transferDocumentMapper.toDto(transferDocumentRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TransferDocumentDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TransferDocumentDTO> findByCriteria(TransferDocumentCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TransferDocument> specification = createSpecification(criteria);
        return transferDocumentRepository.findAll(specification, page).map(transferDocumentMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TransferDocumentCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TransferDocument> specification = createSpecification(criteria);
        return transferDocumentRepository.count(specification);
    }

    /**
     * Function to convert {@link TransferDocumentCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TransferDocument> createSpecification(TransferDocumentCriteria criteria) {
        Specification<TransferDocument> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TransferDocument_.id));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildSpecification(criteria.getType(), TransferDocument_.type));
            }
            if (criteria.getTransferDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTransferDate(), TransferDocument_.transferDate));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), TransferDocument_.status));
            }
            if (criteria.getAccountingDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAccountingDate(), TransferDocument_.accountingDate));
            }
            if (criteria.getReversalDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getReversalDate(), TransferDocument_.reversalDate));
            }
            if (criteria.getTransferDocumentItemId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getTransferDocumentItemId(),
                            root -> root.join(TransferDocument_.transferDocumentItems, JoinType.LEFT).get(TransferDocumentItem_.id)
                        )
                    );
            }
            if (criteria.getBusinessYearId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getBusinessYearId(),
                            root -> root.join(TransferDocument_.businessYear, JoinType.LEFT).get(BusinessYear_.id)
                        )
                    );
            }
            if (criteria.getReceivingStorageId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getReceivingStorageId(),
                            root -> root.join(TransferDocument_.receivingStorage, JoinType.LEFT).get(Storage_.id)
                        )
                    );
            }
            if (criteria.getDispatchingStorageId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDispatchingStorageId(),
                            root -> root.join(TransferDocument_.dispatchingStorage, JoinType.LEFT).get(Storage_.id)
                        )
                    );
            }
            if (criteria.getBusinessPartnerId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getBusinessPartnerId(),
                            root -> root.join(TransferDocument_.businessPartner, JoinType.LEFT).get(BusinessPartner_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
