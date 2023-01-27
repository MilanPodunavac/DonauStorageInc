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
    @Mapping(target = "censusDate", source = "censusDate")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "accountingDate", source = "accountingDate")
    @Mapping(target = "leveling", source = "leveling")
    @Mapping(target = "businessYear", source = "businessYear")
    @Mapping(target = "president", source = "president")
    @Mapping(target = "deputy", source = "deputy")
    @Mapping(target = "censusTaker", source = "censusTaker")
    @Mapping(target = "storage", source = "storage")
    CensusDocumentDTO toDtoCensusDocumentId(CensusDocument censusDocument);

    @Named("resourceId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "type", source = "type")
    @Mapping(target = "unit", source = "unit")
    @Mapping(target = "company", source = "company")
    ResourceDTO toDtoResourceId(Resource resource);
}
