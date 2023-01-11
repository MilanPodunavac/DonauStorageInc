package inc.donau.storage.service.mapper;

import inc.donau.storage.domain.Address;
import inc.donau.storage.domain.City;
import inc.donau.storage.service.dto.AddressDTO;
import inc.donau.storage.service.dto.CityDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Address} and its DTO {@link AddressDTO}.
 */
@Mapper(componentModel = "spring")
public interface AddressMapper extends EntityMapper<AddressDTO, Address> {
    @Mapping(target = "city", source = "city", qualifiedByName = "cityId")
    AddressDTO toDto(Address s);

    @Named("cityId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CityDTO toDtoCityId(City city);
}
