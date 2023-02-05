package inc.donau.storage.web.rest;

import inc.donau.storage.repository.MeasurementUnitExtendedRepository;
import inc.donau.storage.repository.MeasurementUnitRepository;
import inc.donau.storage.service.MeasurementUnitExtendedService;
import inc.donau.storage.service.MeasurementUnitQueryExtendedService;
import inc.donau.storage.service.MeasurementUnitService;
import inc.donau.storage.service.ResourceQueryExtendedService;
import inc.donau.storage.service.criteria.CompanyCriteria;
import inc.donau.storage.service.criteria.ResourceCriteria;
import inc.donau.storage.service.dto.CompanyDTO;
import inc.donau.storage.service.dto.ResourceDTO;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.web.util.HeaderUtil;

@RestController
@RequestMapping("/api/xt")
public class MeasurementUnitExtendedResource extends MeasurementUnitResource {

    private static final String ENTITY_NAME = "measurementUnit";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MeasurementUnitExtendedService measurementUnitExtendedService;
    private final MeasurementUnitExtendedRepository measurementUnitExtendedRepository;
    private final MeasurementUnitQueryExtendedService measurementUnitQueryExtendedService;

    private final ResourceQueryExtendedService resourceQueryExtendedService;

    public MeasurementUnitExtendedResource(
        MeasurementUnitExtendedService measurementUnitService,
        MeasurementUnitExtendedRepository measurementUnitRepository,
        MeasurementUnitQueryExtendedService measurementUnitQueryExtendedService,
        ResourceQueryExtendedService resourceQueryExtendedService
    ) {
        super(measurementUnitService, measurementUnitRepository, measurementUnitQueryExtendedService);
        this.measurementUnitExtendedService = measurementUnitService;
        this.measurementUnitExtendedRepository = measurementUnitRepository;
        this.measurementUnitQueryExtendedService = measurementUnitQueryExtendedService;
        this.resourceQueryExtendedService = resourceQueryExtendedService;
    }

    @Override
    public ResponseEntity<Void> deleteMeasurementUnit(@PathVariable Long id) {
        LongFilter longFilter = new LongFilter();
        longFilter.setEquals(id);
        ResourceCriteria resourceCriteria = new ResourceCriteria();
        resourceCriteria.setUnitId(longFilter);
        List<ResourceDTO> resources = resourceQueryExtendedService.findByCriteria(resourceCriteria);
        if (!resources.isEmpty()) return ResponseEntity
            .badRequest()
            .headers(
                HeaderUtil.createFailureAlert(
                    applicationName,
                    true,
                    ENTITY_NAME,
                    "resourceAsssociationMeasurementUnit",
                    "A resource " +
                    resources.get(0).getName() +
                    " (id: " +
                    resources.get(0).getId() +
                    ") " +
                    "is using this measurement unit, it cannot be deleted"
                )
            )
            .build();

        return super.deleteMeasurementUnit(id);
    }
}
