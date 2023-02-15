package inc.donau.storage.service;

import inc.donau.storage.service.dto.TransferDocumentDTO;
import inc.donau.storage.service.dto.TransferDocumentItemDTO;
import java.util.List;

public interface TransferDocumentExtendedService extends TransferDocumentService {
    TransferDocumentDTO account(TransferDocumentDTO transferDocumentDTO, List<TransferDocumentItemDTO> transferDocumentItemDTOList);

    TransferDocumentDTO reverse(TransferDocumentDTO transferDocumentDTO, List<TransferDocumentItemDTO> transferDocumentItemDTOList);
}
