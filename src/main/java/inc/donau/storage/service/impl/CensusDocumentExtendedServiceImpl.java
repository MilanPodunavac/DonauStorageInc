package inc.donau.storage.service.impl;

import inc.donau.storage.domain.CensusDocument;
import inc.donau.storage.repository.CensusDocumentExtendedRepository;
import inc.donau.storage.repository.CensusDocumentRepository;
import inc.donau.storage.service.CensusDocumentExtendedService;
import inc.donau.storage.service.dto.CensusDocumentDTO;
import inc.donau.storage.service.mapper.CensusDocumentMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Primary
public class CensusDocumentExtendedServiceImpl extends CensusDocumentServiceImpl implements CensusDocumentExtendedService {

    private final CensusDocumentExtendedRepository censusDocumentExtendedRepository;

    public CensusDocumentExtendedServiceImpl(
        CensusDocumentExtendedRepository censusDocumentRepository,
        CensusDocumentMapper censusDocumentMapper
    ) {
        super(censusDocumentRepository, censusDocumentMapper);
        this.censusDocumentExtendedRepository = censusDocumentRepository;
    }
}
