package inc.donau.storage.service.mapper;

import inc.donau.storage.domain.BusinessContact;
import inc.donau.storage.domain.BusinessPartner;
import inc.donau.storage.domain.Company;
import inc.donau.storage.domain.LegalEntity;
import inc.donau.storage.service.dto.BusinessContactDTO;
import inc.donau.storage.service.dto.BusinessPartnerDTO;
import inc.donau.storage.service.dto.CompanyDTO;
import inc.donau.storage.service.dto.LegalEntityDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BusinessPartner} and its DTO {@link BusinessPartnerDTO}.
 */
@Mapper(componentModel = "spring")
public interface BusinessPartnerMapper extends EntityMapper<BusinessPartnerDTO, BusinessPartner> {
    @Mapping(target = "businessContact", source = "businessContact", qualifiedByName = "businessContactId")
    @Mapping(target = "legalEntityInfo", source = "legalEntityInfo", qualifiedByName = "legalEntityId")
    @Mapping(target = "company", source = "company", qualifiedByName = "companyId")
    BusinessPartnerDTO toDto(BusinessPartner s);

    @Named("businessContactId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "personalInfo", source = "personalInfo")
    BusinessContactDTO toDtoBusinessContactId(BusinessContact businessContact);

    @Named("legalEntityId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "taxIdentificationNumber", source = "taxIdentificationNumber")
    @Mapping(target = "identificationNumber", source = "identificationNumber")
    @Mapping(target = "contactInfo", source = "contactInfo")
    LegalEntityDTO toDtoLegalEntityId(LegalEntity legalEntity);

    @Named("companyId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "legalEntityInfo", source = "legalEntityInfo")
    CompanyDTO toDtoCompanyId(Company company);
}
