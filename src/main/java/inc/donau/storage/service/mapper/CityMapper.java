package inc.donau.storage.service.mapper;

import inc.donau.storage.domain.City;
import inc.donau.storage.domain.Country;
import inc.donau.storage.service.dto.CityDTO;
import inc.donau.storage.service.dto.CountryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link City} and its DTO {@link CityDTO}.
 */
@Mapper(componentModel = "spring")
public interface CityMapper extends EntityMapper<CityDTO, City> {
    @Mapping(target = "country", source = "country", qualifiedByName = "countryId")
    CityDTO toDto(City s);

    @Named("countryId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    CountryDTO toDtoCountryId(Country country);
}
