package inc.donau.storage.service;

import inc.donau.storage.service.dto.BusinessYearDTO;
import inc.donau.storage.service.dto.CensusDocumentDTO;

public interface CensusDocumentExtendedService extends CensusDocumentService {
    CensusDocumentDTO account(CensusDocumentDTO censusDocumentDTO, BusinessYearDTO businessYearDTO);
}
