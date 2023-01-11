package inc.donau.storage.service.impl;

import inc.donau.storage.domain.CensusDocument;
import inc.donau.storage.repository.CensusDocumentRepository;
import inc.donau.storage.service.CensusDocumentService;
import inc.donau.storage.service.dto.CensusDocumentDTO;
import inc.donau.storage.service.mapper.CensusDocumentMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CensusDocument}.
 */
@Service
@Transactional
public class CensusDocumentServiceImpl implements CensusDocumentService {

    private final Logger log = LoggerFactory.getLogger(CensusDocumentServiceImpl.class);

    private final CensusDocumentRepository censusDocumentRepository;

    private final CensusDocumentMapper censusDocumentMapper;

    public CensusDocumentServiceImpl(CensusDocumentRepository censusDocumentRepository, CensusDocumentMapper censusDocumentMapper) {
        this.censusDocumentRepository = censusDocumentRepository;
        this.censusDocumentMapper = censusDocumentMapper;
    }

    @Override
    public CensusDocumentDTO save(CensusDocumentDTO censusDocumentDTO) {
        log.debug("Request to save CensusDocument : {}", censusDocumentDTO);
        CensusDocument censusDocument = censusDocumentMapper.toEntity(censusDocumentDTO);
        censusDocument = censusDocumentRepository.save(censusDocument);
        return censusDocumentMapper.toDto(censusDocument);
    }

    @Override
    public CensusDocumentDTO update(CensusDocumentDTO censusDocumentDTO) {
        log.debug("Request to update CensusDocument : {}", censusDocumentDTO);
        CensusDocument censusDocument = censusDocumentMapper.toEntity(censusDocumentDTO);
        censusDocument = censusDocumentRepository.save(censusDocument);
        return censusDocumentMapper.toDto(censusDocument);
    }

    @Override
    public Optional<CensusDocumentDTO> partialUpdate(CensusDocumentDTO censusDocumentDTO) {
        log.debug("Request to partially update CensusDocument : {}", censusDocumentDTO);

        return censusDocumentRepository
            .findById(censusDocumentDTO.getId())
            .map(existingCensusDocument -> {
                censusDocumentMapper.partialUpdate(existingCensusDocument, censusDocumentDTO);

                return existingCensusDocument;
            })
            .map(censusDocumentRepository::save)
            .map(censusDocumentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CensusDocumentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CensusDocuments");
        return censusDocumentRepository.findAll(pageable).map(censusDocumentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CensusDocumentDTO> findOne(Long id) {
        log.debug("Request to get CensusDocument : {}", id);
        return censusDocumentRepository.findById(id).map(censusDocumentMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CensusDocument : {}", id);
        censusDocumentRepository.deleteById(id);
    }
}
