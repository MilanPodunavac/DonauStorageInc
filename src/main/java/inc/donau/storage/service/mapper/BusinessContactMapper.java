package inc.donau.storage.service.mapper;

import inc.donau.storage.domain.BusinessContact;
import inc.donau.storage.domain.Person;
import inc.donau.storage.service.dto.BusinessContactDTO;
import inc.donau.storage.service.dto.PersonDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BusinessContact} and its DTO {@link BusinessContactDTO}.
 */
@Mapper(componentModel = "spring")
public interface BusinessContactMapper extends EntityMapper<BusinessContactDTO, BusinessContact> {
    @Mapping(target = "personalInfo", source = "personalInfo", qualifiedByName = "personId")
    BusinessContactDTO toDto(BusinessContact s);

    @Named("personId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PersonDTO toDtoPersonId(Person person);
}
