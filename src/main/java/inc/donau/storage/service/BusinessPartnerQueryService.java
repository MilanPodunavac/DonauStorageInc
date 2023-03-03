package inc.donau.storage.service;

import inc.donau.storage.domain.*; // for static metamodels
import inc.donau.storage.domain.BusinessPartner;
import inc.donau.storage.repository.BusinessPartnerRepository;
import inc.donau.storage.service.criteria.BusinessPartnerCriteria;
import inc.donau.storage.service.dto.BusinessPartnerDTO;
import inc.donau.storage.service.mapper.BusinessPartnerMapper;
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
 * Service for executing complex queries for {@link BusinessPartner} entities in the database.
 * The main input is a {@link BusinessPartnerCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link BusinessPartnerDTO} or a {@link Page} of {@link BusinessPartnerDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BusinessPartnerQueryService extends QueryService<BusinessPartner> {

    private final Logger log = LoggerFactory.getLogger(BusinessPartnerQueryService.class);

    private final BusinessPartnerRepository businessPartnerRepository;

    private final BusinessPartnerMapper businessPartnerMapper;

    public BusinessPartnerQueryService(BusinessPartnerRepository businessPartnerRepository, BusinessPartnerMapper businessPartnerMapper) {
        this.businessPartnerRepository = businessPartnerRepository;
        this.businessPartnerMapper = businessPartnerMapper;
    }

    /**
     * Return a {@link List} of {@link BusinessPartnerDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<BusinessPartnerDTO> findByCriteria(BusinessPartnerCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<BusinessPartner> specification = createSpecification(criteria);
        return businessPartnerMapper.toDto(businessPartnerRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link BusinessPartnerDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<BusinessPartnerDTO> findByCriteria(BusinessPartnerCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<BusinessPartner> specification = createSpecification(criteria);
        return businessPartnerRepository.findAll(specification, page).map(businessPartnerMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(BusinessPartnerCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<BusinessPartner> specification = createSpecification(criteria);
        return businessPartnerRepository.count(specification);
    }

    /**
     * Function to convert {@link BusinessPartnerCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<BusinessPartner> createSpecification(BusinessPartnerCriteria criteria) {
        Specification<BusinessPartner> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), BusinessPartner_.id));
            }
            if (criteria.getBusinessContactId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getBusinessContactId(),
                            root -> root.join(BusinessPartner_.businessContact, JoinType.LEFT).get(BusinessContact_.id)
                        )
                    );
            }
            if (criteria.getLegalEntityInfoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getLegalEntityInfoId(),
                            root -> root.join(BusinessPartner_.legalEntityInfo, JoinType.LEFT).get(LegalEntity_.id)
                        )
                    );
            }
            if (criteria.getTransfersId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getTransfersId(),
                            root -> root.join(BusinessPartner_.transfers, JoinType.LEFT).get(TransferDocument_.id)
                        )
                    );
            }
            if (criteria.getCompanyId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCompanyId(),
                            root -> root.join(BusinessPartner_.company, JoinType.LEFT).get(Company_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
