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
    TransferDocumentDTO toDtoTransferDocumentId(TransferDocument transferDocument);

    @Named("resourceId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ResourceDTO toDtoResourceId(Resource resource);
}
