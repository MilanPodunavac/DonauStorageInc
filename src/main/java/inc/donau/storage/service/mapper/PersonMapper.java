package inc.donau.storage.service.mapper;

import inc.donau.storage.domain.ContactInfo;
import inc.donau.storage.domain.Person;
import inc.donau.storage.service.dto.ContactInfoDTO;
import inc.donau.storage.service.dto.PersonDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Person} and its DTO {@link PersonDTO}.
 */
@Mapper(componentModel = "spring")
public interface PersonMapper extends EntityMapper<PersonDTO, Person> {
    @Mapping(target = "contactInfo", source = "contactInfo", qualifiedByName = "contactInfoId")
    PersonDTO toDto(Person s);

    @Named("contactInfoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "phoneNumber", source = "phoneNumber")
    ContactInfoDTO toDtoContactInfoId(ContactInfo contactInfo);
}
