package inc.donau.storage.service;

import inc.donau.storage.service.dto.*;

public interface StorageCardExtendedService extends StorageCardService {
    void level(StorageCardDTO storageCardDTO, CensusDocumentDTO censusDocumentDTO);

    void correct(StorageCardDTO storageCardDTO, CensusItemDTO censusItem);

    void transfer(TransferDocumentDTO transferDocumentDTO, TransferDocumentItemDTO transferDocumentItemDTO);

    void updateValues(StorageCardTrafficDTO storageCardTrafficDTO);
}
