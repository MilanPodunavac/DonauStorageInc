package inc.donau.storage.service;

import inc.donau.storage.repository.TransferDocumentItemExtendedRepository;
import inc.donau.storage.repository.TransferDocumentItemRepository;
import inc.donau.storage.service.mapper.TransferDocumentItemMapper;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class TransferDocumentItemExtendedService extends TransferDocumentItemService {

    private final TransferDocumentItemExtendedRepository transferDocumentItemExtendedRepository;

    public TransferDocumentItemExtendedService(
        TransferDocumentItemExtendedRepository transferDocumentItemRepository,
        TransferDocumentItemMapper transferDocumentItemMapper
    ) {
        super(transferDocumentItemRepository, transferDocumentItemMapper);
        this.transferDocumentItemExtendedRepository = transferDocumentItemRepository;
    }
}
