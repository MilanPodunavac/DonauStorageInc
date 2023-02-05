package inc.donau.storage.web.rest;

import inc.donau.storage.repository.StorageExtendedRepository;
import inc.donau.storage.repository.StorageRepository;
import inc.donau.storage.service.*;
import inc.donau.storage.service.criteria.StorageCardCriteria;
import inc.donau.storage.service.criteria.StorageCardTrafficCriteria;
import inc.donau.storage.service.dto.StorageCardDTO;
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
public class StorageExtendedResource extends StorageResource {

    private static final String ENTITY_NAME = "storage";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StorageExtendedService storageExtendedService;
    private final StorageExtendedRepository storageExtendedRepository;
    private final StorageQueryExtendedService storageQueryExtendedService;

    private final StorageCardQueryExtendedService storageCardQueryExtendedService;

    public StorageExtendedResource(
        StorageExtendedService storageService,
        StorageExtendedRepository storageRepository,
        StorageQueryExtendedService storageQueryService,
        StorageCardQueryExtendedService storageCardQueryExtendedService
    ) {
        super(storageService, storageRepository, storageQueryService);
        this.storageExtendedService = storageService;
        this.storageExtendedRepository = storageRepository;
        this.storageQueryExtendedService = storageQueryService;
        this.storageCardQueryExtendedService = storageCardQueryExtendedService;
    }

    @Override
    public ResponseEntity<Void> deleteStorage(@PathVariable Long id) {
        LongFilter longFilter = new LongFilter();
        longFilter.setEquals(id);
        StorageCardCriteria storageCardCriteria = new StorageCardCriteria();
        storageCardCriteria.setStorageId(longFilter);
        List<StorageCardDTO> cards = storageCardQueryExtendedService.findByCriteria(storageCardCriteria);
        if (!cards.isEmpty()) return ResponseEntity
            .badRequest()
            .headers(
                HeaderUtil.createFailureAlert(
                    applicationName,
                    true,
                    ENTITY_NAME,
                    "storageCardAsssociationStorage",
                    "This storage has cards registered," + " it cannot be deleted"
                )
            )
            .build();

        return super.deleteStorage(id);
    }
}
