package inc.donau.storage.service;

import inc.donau.storage.repository.CensusDocumentExtendedRepository;
import inc.donau.storage.repository.CensusDocumentRepository;
import inc.donau.storage.service.mapper.CensusDocumentMapper;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CensusDocumentQueryExtendedService extends CensusDocumentQueryService {

    private final CensusDocumentExtendedRepository censusDocumentExtendedRepository;

    public CensusDocumentQueryExtendedService(
        CensusDocumentExtendedRepository censusDocumentRepository,
        CensusDocumentMapper censusDocumentMapper
    ) {
        super(censusDocumentRepository, censusDocumentMapper);
        this.censusDocumentExtendedRepository = censusDocumentRepository;
    }
}
