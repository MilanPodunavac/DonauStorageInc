package inc.donau.storage.web.rest;

import inc.donau.storage.repository.StorageCardExtendedRepository;
import inc.donau.storage.repository.StorageCardRepository;
import inc.donau.storage.service.*;
import inc.donau.storage.service.criteria.BusinessContactCriteria;
import inc.donau.storage.service.criteria.EmployeeCriteria;
import inc.donau.storage.service.criteria.StorageCardTrafficCriteria;
import inc.donau.storage.service.dto.BusinessContactDTO;
import inc.donau.storage.service.dto.EmployeeDTO;
import inc.donau.storage.service.dto.StorageCardTrafficDTO;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;
import tech.jhipster.web.util.HeaderUtil;

@RestController
@RequestMapping("/api/xt")
public class StorageCardExtendedResource extends StorageCardResource {

    private static final String ENTITY_NAME = "storageCard";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StorageCardExtendedService storageCardExtendedService;
    private final StorageCardExtendedRepository storageCardExtendedRepository;
    private final StorageCardQueryExtendedService storageCardQueryExtendedService;

    private final StorageCardTrafficQueryExtendedService storageCardTrafficQueryExtendedService;

    public StorageCardExtendedResource(
        StorageCardExtendedService storageCardService,
        StorageCardExtendedRepository storageCardRepository,
        StorageCardQueryExtendedService storageCardQueryService,
        StorageCardTrafficQueryExtendedService storageCardTrafficQueryExtendedService
    ) {
        super(storageCardService, storageCardRepository, storageCardQueryService);
        this.storageCardExtendedService = storageCardService;
        this.storageCardExtendedRepository = storageCardRepository;
        this.storageCardQueryExtendedService = storageCardQueryService;
        this.storageCardTrafficQueryExtendedService = storageCardTrafficQueryExtendedService;
    }

    @Override
    public ResponseEntity<Void> deleteStorageCard(@PathVariable String id) {
        StringFilter stringFilter = new StringFilter();
        stringFilter.setEquals(id);
        StorageCardTrafficCriteria storageCardTrafficCriteria = new StorageCardTrafficCriteria();
        storageCardTrafficCriteria.setStorageCardId(stringFilter);
        List<StorageCardTrafficDTO> traffic = storageCardTrafficQueryExtendedService.findByCriteria(storageCardTrafficCriteria);
        if (!traffic.isEmpty()) return ResponseEntity
            .badRequest()
            .headers(
                HeaderUtil.createFailureAlert(
                    applicationName,
                    true,
                    ENTITY_NAME,
                    "storageCardTrafficAsssociationStorageCard",
                    "This storage card has traffic registered," + " it cannot be deleted by law"
                )
            )
            .build();

        return super.deleteStorageCard(id);
    }
}
