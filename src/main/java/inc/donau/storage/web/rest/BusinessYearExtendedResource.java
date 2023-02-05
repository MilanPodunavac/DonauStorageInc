package inc.donau.storage.web.rest;

import inc.donau.storage.repository.BusinessYearExtendedRepository;
import inc.donau.storage.service.*;
import inc.donau.storage.service.criteria.*;
import inc.donau.storage.service.dto.*;
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
public class BusinessYearExtendedResource extends BusinessYearResource {

    private static final String ENTITY_NAME = "businessYear";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BusinessYearExtendedService businessYearExtendedService;
    private final BusinessYearExtendedRepository businessYearExtendedRepository;
    private final BusinessYearQueryExtendedService businessYearQueryExtendedService;

    private final StorageCardQueryExtendedService storageCardQueryExtendedService;
    private final TransferDocumentQueryExtendedService transferDocumentQueryExtendedService;
    private final CensusDocumentQueryExtendedService censusDocumentQueryExtendedService;

    public BusinessYearExtendedResource(
        BusinessYearExtendedService businessYearService,
        BusinessYearExtendedRepository businessYearRepository,
        BusinessYearQueryExtendedService businessYearQueryService,
        StorageCardQueryExtendedService storageCardQueryExtendedService,
        TransferDocumentQueryExtendedService transferDocumentQueryExtendedService,
        CensusDocumentQueryExtendedService censusDocumentQueryExtendedService
    ) {
        super(businessYearService, businessYearRepository, businessYearQueryService);
        this.businessYearExtendedService = businessYearService;
        this.businessYearExtendedRepository = businessYearRepository;
        this.businessYearQueryExtendedService = businessYearQueryService;
        this.storageCardQueryExtendedService = storageCardQueryExtendedService;
        this.transferDocumentQueryExtendedService = transferDocumentQueryExtendedService;
        this.censusDocumentQueryExtendedService = censusDocumentQueryExtendedService;
    }

    @Override
    public ResponseEntity<Void> deleteBusinessYear(@PathVariable Long id) {
        LongFilter longFilter = new LongFilter();
        longFilter.setEquals(id);
        StorageCardCriteria storageCardCriteria = new StorageCardCriteria();
        storageCardCriteria.setBusinessYearId(longFilter);
        List<StorageCardDTO> cards = storageCardQueryExtendedService.findByCriteria(storageCardCriteria);
        if (!cards.isEmpty()) return ResponseEntity
            .badRequest()
            .headers(
                HeaderUtil.createFailureAlert(
                    applicationName,
                    true,
                    ENTITY_NAME,
                    "storageCardAsssociationBusinessYear",
                    "A storage card id: " + cards.get(0).getId() + " " + "is in this business year, it cannot be deleted"
                )
            )
            .build();

        TransferDocumentCriteria transferDocumentCriteria = new TransferDocumentCriteria();
        transferDocumentCriteria.setBusinessYearId(longFilter);
        List<TransferDocumentDTO> transferDocuments = transferDocumentQueryExtendedService.findByCriteria(transferDocumentCriteria);
        if (!transferDocuments.isEmpty()) return ResponseEntity
            .badRequest()
            .headers(
                HeaderUtil.createFailureAlert(
                    applicationName,
                    true,
                    ENTITY_NAME,
                    "transferDocumentAsssociationBusinessYear",
                    "A transfer document id: " + transferDocuments.get(0).getId() + " " + "is in this business year, it cannot be deleted"
                )
            )
            .build();

        CensusDocumentCriteria censusDocumentCriteria = new CensusDocumentCriteria();
        censusDocumentCriteria.setBusinessYearId(longFilter);
        List<CensusDocumentDTO> censusDocuments = censusDocumentQueryExtendedService.findByCriteria(censusDocumentCriteria);
        if (!censusDocuments.isEmpty()) return ResponseEntity
            .badRequest()
            .headers(
                HeaderUtil.createFailureAlert(
                    applicationName,
                    true,
                    ENTITY_NAME,
                    "censusDocumentAsssociationBusinessYear",
                    "A census document id: " + censusDocuments.get(0).getId() + " " + "is in this business year, it cannot be deleted"
                )
            )
            .build();

        return super.deleteBusinessYear(id);
    }
}
