package inc.donau.storage.web.rest;

import inc.donau.storage.repository.TransferDocumentExtendedRepository;
import inc.donau.storage.repository.TransferDocumentRepository;
import inc.donau.storage.service.TransferDocumentExtendedService;
import inc.donau.storage.service.TransferDocumentQueryExtendedService;
import inc.donau.storage.service.TransferDocumentQueryService;
import inc.donau.storage.service.TransferDocumentService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/xt")
public class TransferDocumentExtendedResource extends TransferDocumentResource {

    private final TransferDocumentExtendedService transferDocumentExtendedService;
    private final TransferDocumentExtendedRepository transferDocumentExtendedRepository;
    private final TransferDocumentQueryExtendedService transferDocumentQueryExtendedService;

    public TransferDocumentExtendedResource(
        TransferDocumentExtendedService transferDocumentService,
        TransferDocumentExtendedRepository transferDocumentRepository,
        TransferDocumentQueryExtendedService transferDocumentQueryService
    ) {
        super(transferDocumentService, transferDocumentRepository, transferDocumentQueryService);
        this.transferDocumentExtendedService = transferDocumentService;
        this.transferDocumentExtendedRepository = transferDocumentRepository;
        this.transferDocumentQueryExtendedService = transferDocumentQueryService;
    }
}
