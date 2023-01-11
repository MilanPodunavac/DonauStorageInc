package inc.donau.storage.service.mapper;

import inc.donau.storage.domain.Address;
import inc.donau.storage.domain.Company;
import inc.donau.storage.domain.Employee;
import inc.donau.storage.domain.Person;
import inc.donau.storage.service.dto.AddressDTO;
import inc.donau.storage.service.dto.CompanyDTO;
import inc.donau.storage.service.dto.EmployeeDTO;
import inc.donau.storage.service.dto.PersonDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Employee} and its DTO {@link EmployeeDTO}.
 */
@Mapper(componentModel = "spring")
public interface EmployeeMapper extends EntityMapper<EmployeeDTO, Employee> {
    @Mapping(target = "address", source = "address", qualifiedByName = "addressId")
    @Mapping(target = "personalInfo", source = "personalInfo", qualifiedByName = "personId")
    @Mapping(target = "company", source = "company", qualifiedByName = "companyId")
    EmployeeDTO toDto(Employee s);

    @Named("addressId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AddressDTO toDtoAddressId(Address address);

    @Named("personId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PersonDTO toDtoPersonId(Person person);

    @Named("companyId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CompanyDTO toDtoCompanyId(Company company);
}
