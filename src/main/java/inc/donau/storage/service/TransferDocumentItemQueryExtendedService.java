package inc.donau.storage.service;

import inc.donau.storage.repository.TransferDocumentItemExtendedRepository;
import inc.donau.storage.repository.TransferDocumentItemRepository;
import inc.donau.storage.service.mapper.TransferDocumentItemMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TransferDocumentItemQueryExtendedService extends TransferDocumentItemQueryService {

    private final TransferDocumentItemExtendedRepository transferDocumentItemExtendedRepository;

    public TransferDocumentItemQueryExtendedService(
        TransferDocumentItemExtendedRepository transferDocumentItemRepository,
        TransferDocumentItemMapper transferDocumentItemMapper
    ) {
        super(transferDocumentItemRepository, transferDocumentItemMapper);
        this.transferDocumentItemExtendedRepository = transferDocumentItemRepository;
    }
}
