package inc.donau.storage.web.rest;

import inc.donau.storage.domain.TransferDocumentItem;
import inc.donau.storage.domain.enumeration.CensusDocumentStatus;
import inc.donau.storage.domain.enumeration.TransferDocumentStatus;
import inc.donau.storage.domain.enumeration.TransferDocumentType;
import inc.donau.storage.repository.TransferDocumentExtendedRepository;
import inc.donau.storage.repository.TransferDocumentRepository;
import inc.donau.storage.service.*;
import inc.donau.storage.service.criteria.TransferDocumentItemCriteria;
import inc.donau.storage.service.dto.TransferDocumentDTO;
import inc.donau.storage.service.dto.TransferDocumentItemDTO;
import inc.donau.storage.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.web.util.HeaderUtil;

@RestController
@RequestMapping("/api/xt")
public class TransferDocumentExtendedResource extends TransferDocumentResource {

    private final Logger log = LoggerFactory.getLogger(TransferDocumentResource.class);

    private static final String ENTITY_NAME = "transferDocument";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TransferDocumentExtendedService transferDocumentExtendedService;
    private final TransferDocumentExtendedRepository transferDocumentExtendedRepository;
    private final TransferDocumentQueryExtendedService transferDocumentQueryExtendedService;

    private final TransferDocumentItemQueryExtendedService transferDocumentItemQueryExtendedService;

    public TransferDocumentExtendedResource(
        TransferDocumentExtendedService transferDocumentService,
        TransferDocumentExtendedRepository transferDocumentRepository,
        TransferDocumentQueryExtendedService transferDocumentQueryService,
        TransferDocumentItemQueryExtendedService transferDocumentItemQueryExtendedService
    ) {
        super(transferDocumentService, transferDocumentRepository, transferDocumentQueryService);
        this.transferDocumentExtendedService = transferDocumentService;
        this.transferDocumentExtendedRepository = transferDocumentRepository;
        this.transferDocumentQueryExtendedService = transferDocumentQueryService;
        this.transferDocumentItemQueryExtendedService = transferDocumentItemQueryExtendedService;
    }

    @Override
    public ResponseEntity<TransferDocumentDTO> createTransferDocument(@Valid @RequestBody TransferDocumentDTO transferDocumentDTO)
        throws URISyntaxException {
        log.debug("REST request to save TransferDocument : {}", transferDocumentDTO);
        if (transferDocumentDTO.getId() != null) {
            throw new BadRequestAlertException("A new transferDocument cannot already have an ID", ENTITY_NAME, "idexists");
        }

        checkTransferValidity(transferDocumentDTO);

        TransferDocumentDTO result = transferDocumentExtendedService.save(transferDocumentDTO);
        return ResponseEntity
            .created(new URI("/api/transfer-documents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @Override
    public ResponseEntity<TransferDocumentDTO> updateTransferDocument(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TransferDocumentDTO transferDocumentDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TransferDocument : {}, {}", id, transferDocumentDTO);
        if (transferDocumentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, transferDocumentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!transferDocumentExtendedRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        checkTransferValidity(transferDocumentDTO);

        TransferDocumentDTO result = transferDocumentExtendedService.update(transferDocumentDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, transferDocumentDTO.getId().toString()))
            .body(result);
    }

    private void checkTransferValidity(TransferDocumentDTO transferDocumentDTO) {
        switch (transferDocumentDTO.getType()) {
            case DISPATCHING:
                if (transferDocumentDTO.getDispatchingStorage() == null) {
                    throw new BadRequestAlertException("Dispatching storage missing", ENTITY_NAME, "noDispatchingStorage");
                }
                if (transferDocumentDTO.getBusinessPartner() == null) {
                    throw new BadRequestAlertException("Business partner missing", ENTITY_NAME, "noBusinessPartner");
                }
                transferDocumentDTO.setReceivingStorage(null);
                break;
            case INTERSTORAGE:
                if (transferDocumentDTO.getDispatchingStorage() == null) {
                    throw new BadRequestAlertException("Dispatching storage missing", ENTITY_NAME, "noDispatchingStorage");
                }
                if (transferDocumentDTO.getReceivingStorage() == null) {
                    throw new BadRequestAlertException("Receiving storage missing", ENTITY_NAME, "noReceivingStorage");
                }
                if (transferDocumentDTO.getDispatchingStorage().getId() == transferDocumentDTO.getReceivingStorage().getId()) {
                    throw new BadRequestAlertException("Dispatching and receiving storage cannot be the same", ENTITY_NAME, "sameStorages");
                }
                transferDocumentDTO.setBusinessPartner(null);
                break;
            case RECEIVING:
                if (transferDocumentDTO.getBusinessPartner() == null) {
                    throw new BadRequestAlertException("Business partner missing", ENTITY_NAME, "noBusinessPartner");
                }
                if (transferDocumentDTO.getReceivingStorage() == null) {
                    throw new BadRequestAlertException("Receiving storage missing", ENTITY_NAME, "noReceivingStorage");
                }
                transferDocumentDTO.setDispatchingStorage(null);
                break;
            default:
                throw new BadRequestAlertException("No type specified", ENTITY_NAME, "noTransferType");
        }
    }

    @PutMapping("/transfer-documents/account/{id}")
    public ResponseEntity<TransferDocumentDTO> account(@PathVariable(value = "id", required = false) final Long id)
        throws URISyntaxException {
        log.debug("REST request to account TransferDocument : {}", id);
        if (id == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }

        if (!transferDocumentExtendedRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TransferDocumentDTO transferDocumentDTO = transferDocumentExtendedService.findOne(id).get();

        if (transferDocumentDTO.getStatus() != TransferDocumentStatus.IN_PREPARATION) {
            throw new BadRequestAlertException("Transfer is already completed, it cannot be accounted", ENTITY_NAME, "transferComplete");
        }

        if (transferDocumentDTO.getTransferDate().isAfter(LocalDate.now())) {
            throw new BadRequestAlertException("Transfer is already completed, it cannot be accounted", ENTITY_NAME, "transferFuture");
        }

        TransferDocumentItemCriteria transferDocumentItemCriteria = new TransferDocumentItemCriteria();
        LongFilter transferDocumentFilter = new LongFilter();
        transferDocumentFilter.setEquals(transferDocumentDTO.getId());
        transferDocumentItemCriteria.setTransferDocumentId(transferDocumentFilter);
        List<TransferDocumentItemDTO> transferDocumentItemDTOList = transferDocumentItemQueryExtendedService.findByCriteria(
            transferDocumentItemCriteria
        );

        if (transferDocumentItemDTOList.isEmpty()) {
            throw new BadRequestAlertException("Transfer is empty, it cannot be accounted", ENTITY_NAME, "transferEmpty");
        }

        TransferDocumentDTO result = transferDocumentExtendedService.account(transferDocumentDTO, transferDocumentItemDTOList);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, transferDocumentDTO.getId().toString()))
            .body(result);
    }

    @PutMapping("/transfer-documents/reverse/{id}")
    public ResponseEntity<TransferDocumentDTO> reverse(@PathVariable(value = "id", required = false) final Long id)
        throws URISyntaxException {
        log.debug("REST request to reverse TransferDocument : {}", id);
        if (id == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }

        if (!transferDocumentExtendedRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TransferDocumentDTO transferDocumentDTO = transferDocumentExtendedService.findOne(id).get();

        if (transferDocumentDTO.getStatus() != TransferDocumentStatus.ACCOUNTED) {
            throw new BadRequestAlertException(
                "Transfer is not in the accounted state, it cannot be reversed",
                ENTITY_NAME,
                "transferNotAccounted"
            );
        }

        TransferDocumentItemCriteria transferDocumentItemCriteria = new TransferDocumentItemCriteria();
        LongFilter transferDocumentFilter = new LongFilter();
        transferDocumentFilter.setEquals(transferDocumentDTO.getId());
        transferDocumentItemCriteria.setTransferDocumentId(transferDocumentFilter);
        List<TransferDocumentItemDTO> transferDocumentItemDTOList = transferDocumentItemQueryExtendedService.findByCriteria(
            transferDocumentItemCriteria
        );

        TransferDocumentDTO result = transferDocumentExtendedService.reverse(transferDocumentDTO, transferDocumentItemDTOList);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, transferDocumentDTO.getId().toString()))
            .body(result);
    }
}
