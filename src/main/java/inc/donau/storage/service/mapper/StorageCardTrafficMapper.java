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
    @Mapping(target = "startingAmount", source = "startingAmount")
    @Mapping(target = "receivedAmount", source = "receivedAmount")
    @Mapping(target = "dispatchedAmount", source = "dispatchedAmount")
    @Mapping(target = "totalAmount", source = "totalAmount")
    @Mapping(target = "startingValue", source = "startingValue")
    @Mapping(target = "receivedValue", source = "receivedValue")
    @Mapping(target = "dispatchedValue", source = "dispatchedValue")
    @Mapping(target = "totalValue", source = "totalValue")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "businessYear", source = "businessYear")
    @Mapping(target = "resource", source = "resource")
    @Mapping(target = "storage", source = "storage")
    StorageCardDTO toDtoStorageCardId(StorageCard storageCard);
}
