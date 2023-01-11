package inc.donau.storage.service.mapper;

import inc.donau.storage.domain.Company;
import inc.donau.storage.domain.LegalEntity;
import inc.donau.storage.service.dto.CompanyDTO;
import inc.donau.storage.service.dto.LegalEntityDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Company} and its DTO {@link CompanyDTO}.
 */
@Mapper(componentModel = "spring")
public interface CompanyMapper extends EntityMapper<CompanyDTO, Company> {
    @Mapping(target = "legalEntityInfo", source = "legalEntityInfo", qualifiedByName = "legalEntityId")
    CompanyDTO toDto(Company s);

    @Named("legalEntityId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    LegalEntityDTO toDtoLegalEntityId(LegalEntity legalEntity);
}
