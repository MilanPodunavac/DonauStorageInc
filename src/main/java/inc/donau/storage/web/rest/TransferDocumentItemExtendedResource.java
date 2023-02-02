package inc.donau.storage.web.rest;

import inc.donau.storage.repository.TransferDocumentItemExtendedRepository;
import inc.donau.storage.repository.TransferDocumentItemRepository;
import inc.donau.storage.service.TransferDocumentItemExtendedService;
import inc.donau.storage.service.TransferDocumentItemQueryExtendedService;
import inc.donau.storage.service.TransferDocumentItemService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/xt")
public class TransferDocumentItemExtendedResource extends TransferDocumentItemResource {

    private final TransferDocumentItemExtendedService transferDocumentItemExtendedService;
    private final TransferDocumentItemExtendedRepository transferDocumentItemExtendedRepository;
    private final TransferDocumentItemQueryExtendedService transferDocumentItemQueryExtendedService;

    public TransferDocumentItemExtendedResource(
        TransferDocumentItemExtendedService transferDocumentItemService,
        TransferDocumentItemExtendedRepository transferDocumentItemRepository,
        TransferDocumentItemQueryExtendedService transferDocumentItemQueryExtendedService
    ) {
        super(transferDocumentItemService, transferDocumentItemRepository, transferDocumentItemQueryExtendedService);
        this.transferDocumentItemExtendedService = transferDocumentItemService;
        this.transferDocumentItemExtendedRepository = transferDocumentItemRepository;
        this.transferDocumentItemQueryExtendedService = transferDocumentItemQueryExtendedService;
    }
}
