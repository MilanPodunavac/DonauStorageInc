package inc.donau.storage.service;

import inc.donau.storage.repository.MeasurementUnitExtendedRepository;
import inc.donau.storage.repository.MeasurementUnitRepository;
import inc.donau.storage.service.mapper.MeasurementUnitMapper;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class MeasurementUnitExtendedService extends MeasurementUnitService {

    private final MeasurementUnitExtendedRepository measurementUnitExtendedRepository;

    public MeasurementUnitExtendedService(
        MeasurementUnitExtendedRepository measurementUnitRepository,
        MeasurementUnitMapper measurementUnitMapper
    ) {
        super(measurementUnitRepository, measurementUnitMapper);
        this.measurementUnitExtendedRepository = measurementUnitRepository;
    }
}
