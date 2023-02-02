package inc.donau.storage.service;

import inc.donau.storage.repository.MeasurementUnitExtendedRepository;
import inc.donau.storage.repository.MeasurementUnitRepository;
import inc.donau.storage.service.mapper.MeasurementUnitMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MeasurementUnitQueryExtendedService extends MeasurementUnitQueryService {

    private final MeasurementUnitExtendedRepository measurementUnitExtendedRepository;

    public MeasurementUnitQueryExtendedService(
        MeasurementUnitExtendedRepository measurementUnitRepository,
        MeasurementUnitMapper measurementUnitMapper
    ) {
        super(measurementUnitRepository, measurementUnitMapper);
        this.measurementUnitExtendedRepository = measurementUnitRepository;
    }
}
