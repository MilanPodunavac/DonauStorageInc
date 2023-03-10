package inc.donau.storage.service.mapper;

import inc.donau.storage.domain.Address;
import inc.donau.storage.domain.ContactInfo;
import inc.donau.storage.domain.LegalEntity;
import inc.donau.storage.service.dto.AddressDTO;
import inc.donau.storage.service.dto.ContactInfoDTO;
import inc.donau.storage.service.dto.LegalEntityDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link LegalEntity} and its DTO {@link LegalEntityDTO}.
 */
@Mapper(componentModel = "spring")
public interface LegalEntityMapper extends EntityMapper<LegalEntityDTO, LegalEntity> {
    @Mapping(target = "contactInfo", source = "contactInfo", qualifiedByName = "contactInfoEmail")
    @Mapping(target = "address", source = "address", qualifiedByName = "addressId")
    LegalEntityDTO toDto(LegalEntity s);

    @Named("contactInfoEmail")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "phoneNumber", source = "phoneNumber")
    ContactInfoDTO toDtoContactInfoEmail(ContactInfo contactInfo);

    @Named("addressId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "streetName", source = "streetName")
    @Mapping(target = "streetCode", source = "streetCode")
    @Mapping(target = "postalCode", source = "postalCode")
    @Mapping(target = "city", source = "city")
    AddressDTO toDtoAddressId(Address address);
}
