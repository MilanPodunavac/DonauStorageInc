package inc.donau.storage.service.mapper;

import inc.donau.storage.domain.BusinessYear;
import inc.donau.storage.domain.Company;
import inc.donau.storage.service.dto.BusinessYearDTO;
import inc.donau.storage.service.dto.CompanyDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BusinessYear} and its DTO {@link BusinessYearDTO}.
 */
@Mapper(componentModel = "spring")
public interface BusinessYearMapper extends EntityMapper<BusinessYearDTO, BusinessYear> {
    @Mapping(target = "company", source = "company", qualifiedByName = "companyId")
    BusinessYearDTO toDto(BusinessYear s);

    @Named("companyId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "legalEntityInfo", source = "legalEntityInfo")
    CompanyDTO toDtoCompanyId(Company company);
}
