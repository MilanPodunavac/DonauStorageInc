package inc.donau.storage.service.mapper;

import inc.donau.storage.domain.CensusDocument;
import inc.donau.storage.domain.CensusItem;
import inc.donau.storage.domain.Resource;
import inc.donau.storage.service.dto.CensusDocumentDTO;
import inc.donau.storage.service.dto.CensusItemDTO;
import inc.donau.storage.service.dto.ResourceDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CensusItem} and its DTO {@link CensusItemDTO}.
 */
@Mapper(componentModel = "spring")
public interface CensusItemMapper extends EntityMapper<CensusItemDTO, CensusItem> {
    @Mapping(target = "censusDocument", source = "censusDocument", qualifiedByName = "censusDocumentId")
    @Mapping(target = "resource", source = "resource", qualifiedByName = "resourceId")
    CensusItemDTO toDto(CensusItem s);

    @Named("censusDocumentId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CensusDocumentDTO toDtoCensusDocumentId(CensusDocument censusDocument);

    @Named("resourceId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ResourceDTO toDtoResourceId(Resource resource);
}
