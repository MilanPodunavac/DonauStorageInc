package inc.donau.storage.service.mapper;

import inc.donau.storage.domain.BusinessYear;
import inc.donau.storage.domain.Resource;
import inc.donau.storage.domain.Storage;
import inc.donau.storage.domain.StorageCard;
import inc.donau.storage.service.dto.BusinessYearDTO;
import inc.donau.storage.service.dto.ResourceDTO;
import inc.donau.storage.service.dto.StorageCardDTO;
import inc.donau.storage.service.dto.StorageDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link StorageCard} and its DTO {@link StorageCardDTO}.
 */
@Mapper(componentModel = "spring")
public interface StorageCardMapper extends EntityMapper<StorageCardDTO, StorageCard> {
    @Mapping(target = "businessYear", source = "businessYear", qualifiedByName = "businessYearId")
    @Mapping(target = "resource", source = "resource", qualifiedByName = "resourceId")
    @Mapping(target = "storage", source = "storage", qualifiedByName = "storageId")
    StorageCardDTO toDto(StorageCard s);

    @Named("businessYearId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    BusinessYearDTO toDtoBusinessYearId(BusinessYear businessYear);

    @Named("resourceId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ResourceDTO toDtoResourceId(Resource resource);

    @Named("storageId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    StorageDTO toDtoStorageId(Storage storage);
}
