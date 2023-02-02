package inc.donau.storage.service;

import inc.donau.storage.domain.*; // for static metamodels
import inc.donau.storage.domain.MeasurementUnit;
import inc.donau.storage.repository.MeasurementUnitRepository;
import inc.donau.storage.service.criteria.MeasurementUnitCriteria;
import inc.donau.storage.service.dto.MeasurementUnitDTO;
import inc.donau.storage.service.mapper.MeasurementUnitMapper;
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
 * Service for executing complex queries for {@link MeasurementUnit} entities in the database.
 * The main input is a {@link MeasurementUnitCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MeasurementUnitDTO} or a {@link Page} of {@link MeasurementUnitDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MeasurementUnitQueryService extends QueryService<MeasurementUnit> {

    private final Logger log = LoggerFactory.getLogger(MeasurementUnitQueryService.class);

    private final MeasurementUnitRepository measurementUnitRepository;

    private final MeasurementUnitMapper measurementUnitMapper;

    public MeasurementUnitQueryService(MeasurementUnitRepository measurementUnitRepository, MeasurementUnitMapper measurementUnitMapper) {
        this.measurementUnitRepository = measurementUnitRepository;
        this.measurementUnitMapper = measurementUnitMapper;
    }

    /**
     * Return a {@link List} of {@link MeasurementUnitDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MeasurementUnitDTO> findByCriteria(MeasurementUnitCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MeasurementUnit> specification = createSpecification(criteria);
        return measurementUnitMapper.toDto(measurementUnitRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MeasurementUnitDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MeasurementUnitDTO> findByCriteria(MeasurementUnitCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MeasurementUnit> specification = createSpecification(criteria);
        return measurementUnitRepository.findAll(specification, page).map(measurementUnitMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MeasurementUnitCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MeasurementUnit> specification = createSpecification(criteria);
        return measurementUnitRepository.count(specification);
    }

    /**
     * Function to convert {@link MeasurementUnitCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MeasurementUnit> createSpecification(MeasurementUnitCriteria criteria) {
        Specification<MeasurementUnit> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MeasurementUnit_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), MeasurementUnit_.name));
            }
            if (criteria.getAbbreviation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAbbreviation(), MeasurementUnit_.abbreviation));
            }
        }
        return specification;
    }
}
