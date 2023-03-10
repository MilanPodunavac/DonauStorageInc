package inc.donau.storage.service.mapper;

import inc.donau.storage.domain.Company;
import inc.donau.storage.domain.MeasurementUnit;
import inc.donau.storage.domain.Resource;
import inc.donau.storage.service.dto.CompanyDTO;
import inc.donau.storage.service.dto.MeasurementUnitDTO;
import inc.donau.storage.service.dto.ResourceDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Resource} and its DTO {@link ResourceDTO}.
 */
@Mapper(componentModel = "spring")
public interface ResourceMapper extends EntityMapper<ResourceDTO, Resource> {
    @Mapping(target = "unit", source = "unit", qualifiedByName = "measurementUnitName")
    @Mapping(target = "company", source = "company", qualifiedByName = "companyId")
    ResourceDTO toDto(Resource s);

    @Named("measurementUnitName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "abbreviation", source = "abbreviation")
    MeasurementUnitDTO toDtoMeasurementUnitName(MeasurementUnit measurementUnit);

    @Named("companyId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "legalEntityInfo", source = "legalEntityInfo")
    CompanyDTO toDtoCompanyId(Company company);
}
