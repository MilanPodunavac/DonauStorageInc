package inc.donau.storage.service;

import inc.donau.storage.domain.*; // for static metamodels
import inc.donau.storage.domain.TransferDocumentItem;
import inc.donau.storage.repository.TransferDocumentItemRepository;
import inc.donau.storage.service.criteria.TransferDocumentItemCriteria;
import inc.donau.storage.service.dto.TransferDocumentItemDTO;
import inc.donau.storage.service.mapper.TransferDocumentItemMapper;
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
 * Service for executing complex queries for {@link TransferDocumentItem} entities in the database.
 * The main input is a {@link TransferDocumentItemCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TransferDocumentItemDTO} or a {@link Page} of {@link TransferDocumentItemDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TransferDocumentItemQueryService extends QueryService<TransferDocumentItem> {

    private final Logger log = LoggerFactory.getLogger(TransferDocumentItemQueryService.class);

    private final TransferDocumentItemRepository transferDocumentItemRepository;

    private final TransferDocumentItemMapper transferDocumentItemMapper;

    public TransferDocumentItemQueryService(
        TransferDocumentItemRepository transferDocumentItemRepository,
        TransferDocumentItemMapper transferDocumentItemMapper
    ) {
        this.transferDocumentItemRepository = transferDocumentItemRepository;
        this.transferDocumentItemMapper = transferDocumentItemMapper;
    }

    /**
     * Return a {@link List} of {@link TransferDocumentItemDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TransferDocumentItemDTO> findByCriteria(TransferDocumentItemCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TransferDocumentItem> specification = createSpecification(criteria);
        return transferDocumentItemMapper.toDto(transferDocumentItemRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TransferDocumentItemDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TransferDocumentItemDTO> findByCriteria(TransferDocumentItemCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TransferDocumentItem> specification = createSpecification(criteria);
        return transferDocumentItemRepository.findAll(specification, page).map(transferDocumentItemMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TransferDocumentItemCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TransferDocumentItem> specification = createSpecification(criteria);
        return transferDocumentItemRepository.count(specification);
    }

    /**
     * Function to convert {@link TransferDocumentItemCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TransferDocumentItem> createSpecification(TransferDocumentItemCriteria criteria) {
        Specification<TransferDocumentItem> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TransferDocumentItem_.id));
            }
            if (criteria.getAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmount(), TransferDocumentItem_.amount));
            }
            if (criteria.getPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrice(), TransferDocumentItem_.price));
            }
            if (criteria.getTransferValue() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getTransferValue(), TransferDocumentItem_.transferValue));
            }
            if (criteria.getTransferDocumentId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getTransferDocumentId(),
                            root -> root.join(TransferDocumentItem_.transferDocument, JoinType.LEFT).get(TransferDocument_.id)
                        )
                    );
            }
            if (criteria.getResourceId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getResourceId(),
                            root -> root.join(TransferDocumentItem_.resource, JoinType.LEFT).get(Resource_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
