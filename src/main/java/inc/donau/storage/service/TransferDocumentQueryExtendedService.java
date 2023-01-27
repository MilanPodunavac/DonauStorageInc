package inc.donau.storage.service;

import inc.donau.storage.repository.TransferDocumentExtendedRepository;
import inc.donau.storage.repository.TransferDocumentRepository;
import inc.donau.storage.service.mapper.TransferDocumentMapper;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class TransferDocumentQueryExtendedService extends TransferDocumentQueryService {

    private final TransferDocumentExtendedRepository transferDocumentExtendedRepository;

    public TransferDocumentQueryExtendedService(
        TransferDocumentExtendedRepository transferDocumentRepository,
        TransferDocumentMapper transferDocumentMapper
    ) {
        super(transferDocumentRepository, transferDocumentMapper);
        this.transferDocumentExtendedRepository = transferDocumentRepository;
    }
}
