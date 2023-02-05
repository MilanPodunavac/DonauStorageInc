package inc.donau.storage.web.rest;

import inc.donau.storage.repository.ResourceExtendedRepository;
import inc.donau.storage.service.*;
import inc.donau.storage.service.criteria.*;
import inc.donau.storage.service.dto.BusinessContactDTO;
import inc.donau.storage.service.dto.CensusItemDTO;
import inc.donau.storage.service.dto.StorageCardDTO;
import inc.donau.storage.service.dto.TransferDocumentItemDTO;
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
public class ResourceExtendedResource extends ResourceResource {

    private static final String ENTITY_NAME = "resource";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ResourceExtendedService resourceExtendedService;
    private final ResourceExtendedRepository resourceExtendedRepository;
    private final ResourceQueryExtendedService resourceQueryExtendedService;

    private final StorageCardQueryExtendedService storageCardQueryExtendedService;
    private final TransferDocumentItemQueryExtendedService transferDocumentItemQueryExtendedService;
    private final CensusItemQueryExtendedService censusItemQueryExtendedService;

    public ResourceExtendedResource(
        ResourceExtendedService resourceService,
        ResourceExtendedRepository resourceRepository,
        ResourceQueryExtendedService resourceQueryService,
        StorageCardQueryExtendedService storageCardQueryExtendedService,
        TransferDocumentItemQueryExtendedService transferDocumentItemQueryExtendedService,
        CensusItemQueryExtendedService censusItemQueryExtendedService
    ) {
        super(resourceService, resourceRepository, resourceQueryService);
        this.resourceExtendedService = resourceService;
        this.resourceExtendedRepository = resourceRepository;
        this.resourceQueryExtendedService = resourceQueryService;
        this.storageCardQueryExtendedService = storageCardQueryExtendedService;
        this.transferDocumentItemQueryExtendedService = transferDocumentItemQueryExtendedService;
        this.censusItemQueryExtendedService = censusItemQueryExtendedService;
    }

    @Override
    public ResponseEntity<Void> deleteResource(@PathVariable Long id) {
        LongFilter longFilter = new LongFilter();
        longFilter.setEquals(id);
        StorageCardCriteria storageCardCriteria = new StorageCardCriteria();
        storageCardCriteria.setResourceId(longFilter);
        List<StorageCardDTO> cards = storageCardQueryExtendedService.findByCriteria(storageCardCriteria);
        if (!cards.isEmpty()) return ResponseEntity
            .badRequest()
            .headers(
                HeaderUtil.createFailureAlert(
                    applicationName,
                    true,
                    ENTITY_NAME,
                    "storageCardAsssociationResource",
                    "A storage card id: " + cards.get(0).getId() + " " + "is for this resource, it cannot be deleted"
                )
            )
            .build();

        TransferDocumentItemCriteria transferDocumentItemCriteria = new TransferDocumentItemCriteria();
        transferDocumentItemCriteria.setResourceId(longFilter);
        List<TransferDocumentItemDTO> transferItems = transferDocumentItemQueryExtendedService.findByCriteria(transferDocumentItemCriteria);
        if (!transferItems.isEmpty()) return ResponseEntity
            .badRequest()
            .headers(
                HeaderUtil.createFailureAlert(
                    applicationName,
                    true,
                    ENTITY_NAME,
                    "transferDocumentItemAsssociationResource",
                    "A transfer document item id: " + transferItems.get(0).getId() + " " + "is for this resource, it cannot be deleted"
                )
            )
            .build();

        CensusItemCriteria censusItemCriteria = new CensusItemCriteria();
        censusItemCriteria.setResourceId(longFilter);
        List<CensusItemDTO> censusItems = censusItemQueryExtendedService.findByCriteria(censusItemCriteria);
        if (!censusItems.isEmpty()) return ResponseEntity
            .badRequest()
            .headers(
                HeaderUtil.createFailureAlert(
                    applicationName,
                    true,
                    ENTITY_NAME,
                    "censusItemAsssociationResource",
                    "A census item id: " + censusItems.get(0).getId() + " " + "is for this resource, it cannot be deleted"
                )
            )
            .build();

        return super.deleteResource(id);
    }
}
