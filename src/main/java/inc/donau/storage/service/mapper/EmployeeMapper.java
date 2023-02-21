package inc.donau.storage.service.mapper;

import inc.donau.storage.domain.Address;
import inc.donau.storage.domain.Company;
import inc.donau.storage.domain.Employee;
import inc.donau.storage.domain.Person;
import inc.donau.storage.domain.User;
import inc.donau.storage.service.dto.AddressDTO;
import inc.donau.storage.service.dto.CompanyDTO;
import inc.donau.storage.service.dto.EmployeeDTO;
import inc.donau.storage.service.dto.PersonDTO;
import inc.donau.storage.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Employee} and its DTO {@link EmployeeDTO}.
 */
@Mapper(componentModel = "spring")
public interface EmployeeMapper extends EntityMapper<EmployeeDTO, Employee> {
    @Mapping(target = "address", source = "address", qualifiedByName = "addressId")
    @Mapping(target = "personalInfo", source = "personalInfo", qualifiedByName = "personId")
    @Mapping(target = "company", source = "company", qualifiedByName = "companyId")
    @Mapping(target = "user", source = "user", qualifiedByName = "userLogin")
    EmployeeDTO toDto(Employee s);

    @Named("addressId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "streetName", source = "streetName")
    @Mapping(target = "streetCode", source = "streetCode")
    @Mapping(target = "postalCode", source = "postalCode")
    @Mapping(target = "city", source = "city")
    AddressDTO toDtoAddressId(Address address);

    @Named("personId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "middleName", source = "middleName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "maidenName", source = "maidenName")
    @Mapping(target = "gender", source = "gender")
    @Mapping(target = "contactInfo", source = "contactInfo")
    PersonDTO toDtoPersonId(Person person);

    @Named("companyId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "legalEntityInfo", source = "legalEntityInfo")
    CompanyDTO toDtoCompanyId(Company company);

    @Named("userLogin")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserLogin(User user);
}
