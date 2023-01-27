package inc.donau.storage.service.impl;

import inc.donau.storage.repository.TransferDocumentExtendedRepository;
import inc.donau.storage.repository.TransferDocumentRepository;
import inc.donau.storage.service.TransferDocumentExtendedService;
import inc.donau.storage.service.mapper.TransferDocumentMapper;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Primary
public class TransferDocumentExtendedServiceImpl extends TransferDocumentServiceImpl implements TransferDocumentExtendedService {

    private final TransferDocumentExtendedRepository transferDocumentExtendedRepository;

    public TransferDocumentExtendedServiceImpl(
        TransferDocumentExtendedRepository transferDocumentRepository,
        TransferDocumentMapper transferDocumentMapper
    ) {
        super(transferDocumentRepository, transferDocumentMapper);
        this.transferDocumentExtendedRepository = transferDocumentRepository;
    }
}
