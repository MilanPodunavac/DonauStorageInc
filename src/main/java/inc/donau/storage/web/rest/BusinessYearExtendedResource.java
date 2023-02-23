package inc.donau.storage.web.rest;

import inc.donau.storage.domain.enumeration.CensusDocumentStatus;
import inc.donau.storage.domain.enumeration.TransferDocumentStatus;
import inc.donau.storage.domain.enumeration.TransferDocumentType;
import inc.donau.storage.repository.BusinessYearExtendedRepository;
import inc.donau.storage.service.*;
import inc.donau.storage.service.criteria.*;
import inc.donau.storage.service.dto.*;
import inc.donau.storage.web.rest.errors.BadRequestAlertException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.web.util.HeaderUtil;

@RestController
@RequestMapping("/api/xt")
public class BusinessYearExtendedResource extends BusinessYearResource {

    private final Logger log = LoggerFactory.getLogger(BusinessYearResource.class);

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

    @PutMapping("/business-years/complete/{id}")
    public ResponseEntity<BusinessYearDTO> completeBusinessYear(@PathVariable(value = "id", required = false) final Long id)
        throws URISyntaxException {
        log.debug("REST request to complete BusinessYear : {}", id);

        if (id == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }

        if (!businessYearExtendedRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BusinessYearDTO businessYearDTO = businessYearExtendedService.findOne(id).get();

        if (businessYearDTO.getCompleted()) throw new BadRequestAlertException(
            "Business year already completed",
            ENTITY_NAME,
            "yearcompleted"
        );

        if (businessYearQueryExtendedService.countByCriteria(createCriteriaForCompletion(businessYearDTO)) > 0) {
            return ResponseEntity
                .badRequest()
                .headers(
                    HeaderUtil.createFailureAlert(
                        applicationName,
                        true,
                        ENTITY_NAME,
                        "businessYearCompletionError",
                        "Business year " +
                        businessYearDTO.getYearCode() +
                        " cannot be completed if there are previous ones that are not. Complete those ones first"
                    )
                )
                .build();
        }

        LongFilter longFilter = new LongFilter();
        longFilter.setEquals(id);

        TransferDocumentCriteria transferDocumentCriteria = new TransferDocumentCriteria();
        transferDocumentCriteria.setBusinessYearId(longFilter);
        TransferDocumentCriteria.TransferDocumentStatusFilter transferDocumentStatusFilter = new TransferDocumentCriteria.TransferDocumentStatusFilter();
        transferDocumentStatusFilter.setEquals(TransferDocumentStatus.IN_PREPARATION);
        transferDocumentCriteria.setStatus(transferDocumentStatusFilter);
        List<TransferDocumentDTO> transferDocuments = transferDocumentQueryExtendedService.findByCriteria(transferDocumentCriteria);
        if (!transferDocuments.isEmpty()) return ResponseEntity
            .badRequest()
            .headers(
                HeaderUtil.createFailureAlert(
                    applicationName,
                    true,
                    ENTITY_NAME,
                    "transferDocumentIncompleteBusinessYear",
                    "A transfer document id: " + transferDocuments.get(0).getId() + " " + "is incomplete, year cannot be completed"
                )
            )
            .build();

        CensusDocumentCriteria censusDocumentCriteria = new CensusDocumentCriteria();
        censusDocumentCriteria.setBusinessYearId(longFilter);
        CensusDocumentCriteria.CensusDocumentStatusFilter censusDocumentStatusFilter = new CensusDocumentCriteria.CensusDocumentStatusFilter();
        censusDocumentStatusFilter.setEquals(CensusDocumentStatus.INCOMPLETE);
        censusDocumentCriteria.setStatus(censusDocumentStatusFilter);
        List<CensusDocumentDTO> censusDocuments = censusDocumentQueryExtendedService.findByCriteria(censusDocumentCriteria);
        if (!censusDocuments.isEmpty()) return ResponseEntity
            .badRequest()
            .headers(
                HeaderUtil.createFailureAlert(
                    applicationName,
                    true,
                    ENTITY_NAME,
                    "censusDocumentIncompleteBusinessYear",
                    "A census document id: " + censusDocuments.get(0).getId() + " " + "is incomplete, year cannot be completed"
                )
            )
            .build();

        BusinessYearDTO result = businessYearExtendedService.complete(businessYearDTO);

        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, businessYearDTO.getId().toString()))
            .body(result);
    }

    private BusinessYearCriteria createCriteriaForCompletion(BusinessYearDTO businessYearDTO) {
        BusinessYearCriteria businessYearCriteria = new BusinessYearCriteria();

        LongFilter companyFilter = new LongFilter();
        companyFilter.setEquals(businessYearDTO.getCompany().getId());
        businessYearCriteria.setCompanyId(companyFilter);

        BooleanFilter completedFilter = new BooleanFilter();
        completedFilter.setEquals(false);
        businessYearCriteria.setCompleted(completedFilter);

        LongFilter idFilter = new LongFilter();
        idFilter.setLessThan(businessYearDTO.getId());
        businessYearCriteria.setId(idFilter);

        return businessYearCriteria;
    }
}
