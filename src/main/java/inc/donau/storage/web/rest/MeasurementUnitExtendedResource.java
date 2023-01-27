package inc.donau.storage.web.rest;

import inc.donau.storage.repository.MeasurementUnitExtendedRepository;
import inc.donau.storage.repository.MeasurementUnitRepository;
import inc.donau.storage.service.MeasurementUnitExtendedService;
import inc.donau.storage.service.MeasurementUnitService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/xt")
public class MeasurementUnitExtendedResource extends MeasurementUnitResource {

    private final MeasurementUnitExtendedService measurementUnitExtendedService;
    private final MeasurementUnitExtendedRepository measurementUnitExtendedRepository;

    public MeasurementUnitExtendedResource(
        MeasurementUnitExtendedService measurementUnitService,
        MeasurementUnitExtendedRepository measurementUnitRepository
    ) {
        super(measurementUnitService, measurementUnitRepository);
        this.measurementUnitExtendedService = measurementUnitService;
        this.measurementUnitExtendedRepository = measurementUnitRepository;
    }
}
