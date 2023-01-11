package inc.donau.storage.service;

import inc.donau.storage.domain.MeasurementUnit;
import inc.donau.storage.repository.MeasurementUnitRepository;
import inc.donau.storage.service.dto.MeasurementUnitDTO;
import inc.donau.storage.service.mapper.MeasurementUnitMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MeasurementUnit}.
 */
@Service
@Transactional
public class MeasurementUnitService {

    private final Logger log = LoggerFactory.getLogger(MeasurementUnitService.class);

    private final MeasurementUnitRepository measurementUnitRepository;

    private final MeasurementUnitMapper measurementUnitMapper;

    public MeasurementUnitService(MeasurementUnitRepository measurementUnitRepository, MeasurementUnitMapper measurementUnitMapper) {
        this.measurementUnitRepository = measurementUnitRepository;
        this.measurementUnitMapper = measurementUnitMapper;
    }

    /**
     * Save a measurementUnit.
     *
     * @param measurementUnitDTO the entity to save.
     * @return the persisted entity.
     */
    public MeasurementUnitDTO save(MeasurementUnitDTO measurementUnitDTO) {
        log.debug("Request to save MeasurementUnit : {}", measurementUnitDTO);
        MeasurementUnit measurementUnit = measurementUnitMapper.toEntity(measurementUnitDTO);
        measurementUnit = measurementUnitRepository.save(measurementUnit);
        return measurementUnitMapper.toDto(measurementUnit);
    }

    /**
     * Update a measurementUnit.
     *
     * @param measurementUnitDTO the entity to save.
     * @return the persisted entity.
     */
    public MeasurementUnitDTO update(MeasurementUnitDTO measurementUnitDTO) {
        log.debug("Request to update MeasurementUnit : {}", measurementUnitDTO);
        MeasurementUnit measurementUnit = measurementUnitMapper.toEntity(measurementUnitDTO);
        measurementUnit = measurementUnitRepository.save(measurementUnit);
        return measurementUnitMapper.toDto(measurementUnit);
    }

    /**
     * Partially update a measurementUnit.
     *
     * @param measurementUnitDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<MeasurementUnitDTO> partialUpdate(MeasurementUnitDTO measurementUnitDTO) {
        log.debug("Request to partially update MeasurementUnit : {}", measurementUnitDTO);

        return measurementUnitRepository
            .findById(measurementUnitDTO.getId())
            .map(existingMeasurementUnit -> {
                measurementUnitMapper.partialUpdate(existingMeasurementUnit, measurementUnitDTO);

                return existingMeasurementUnit;
            })
            .map(measurementUnitRepository::save)
            .map(measurementUnitMapper::toDto);
    }

    /**
     * Get all the measurementUnits.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<MeasurementUnitDTO> findAll() {
        log.debug("Request to get all MeasurementUnits");
        return measurementUnitRepository
            .findAll()
            .stream()
            .map(measurementUnitMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one measurementUnit by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MeasurementUnitDTO> findOne(Long id) {
        log.debug("Request to get MeasurementUnit : {}", id);
        return measurementUnitRepository.findById(id).map(measurementUnitMapper::toDto);
    }

    /**
     * Delete the measurementUnit by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MeasurementUnit : {}", id);
        measurementUnitRepository.deleteById(id);
    }
}
