package inc.donau.storage.service.mapper;

import inc.donau.storage.domain.Resource;
import inc.donau.storage.domain.TransferDocument;
import inc.donau.storage.domain.TransferDocumentItem;
import inc.donau.storage.service.dto.ResourceDTO;
import inc.donau.storage.service.dto.TransferDocumentDTO;
import inc.donau.storage.service.dto.TransferDocumentItemDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TransferDocumentItem} and its DTO {@link TransferDocumentItemDTO}.
 */
@Mapper(componentModel = "spring")
public interface TransferDocumentItemMapper extends EntityMapper<TransferDocumentItemDTO, TransferDocumentItem> {
    @Mapping(target = "transferDocument", source = "transferDocument", qualifiedByName = "transferDocumentId")
    @Mapping(target = "resource", source = "resource", qualifiedByName = "resourceId")
    TransferDocumentItemDTO toDto(TransferDocumentItem s);

    @Named("transferDocumentId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "type", source = "type")
    @Mapping(target = "transferDate", source = "transferDate")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "accountingDate", source = "accountingDate")
    @Mapping(target = "reversalDate", source = "reversalDate")
    @Mapping(target = "businessYear", source = "businessYear")
    @Mapping(target = "receivingStorage", source = "receivingStorage")
    @Mapping(target = "dispatchingStorage", source = "dispatchingStorage")
    @Mapping(target = "businessPartner", source = "businessPartner")
    TransferDocumentDTO toDtoTransferDocumentId(TransferDocument transferDocument);

    @Named("resourceId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "type", source = "type")
    @Mapping(target = "unit", source = "unit")
    ResourceDTO toDtoResourceId(Resource resource);
}
