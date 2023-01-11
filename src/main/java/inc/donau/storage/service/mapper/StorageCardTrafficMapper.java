package inc.donau.storage.service.mapper;

import inc.donau.storage.domain.StorageCard;
import inc.donau.storage.domain.StorageCardTraffic;
import inc.donau.storage.service.dto.StorageCardDTO;
import inc.donau.storage.service.dto.StorageCardTrafficDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link StorageCardTraffic} and its DTO {@link StorageCardTrafficDTO}.
 */
@Mapper(componentModel = "spring")
public interface StorageCardTrafficMapper extends EntityMapper<StorageCardTrafficDTO, StorageCardTraffic> {
    @Mapping(target = "storageCard", source = "storageCard", qualifiedByName = "storageCardId")
    StorageCardTrafficDTO toDto(StorageCardTraffic s);

    @Named("storageCardId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    StorageCardDTO toDtoStorageCardId(StorageCard storageCard);
}
