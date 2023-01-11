package inc.donau.storage.service.mapper;

import inc.donau.storage.domain.Address;
import inc.donau.storage.domain.Company;
import inc.donau.storage.domain.Storage;
import inc.donau.storage.service.dto.AddressDTO;
import inc.donau.storage.service.dto.CompanyDTO;
import inc.donau.storage.service.dto.StorageDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Storage} and its DTO {@link StorageDTO}.
 */
@Mapper(componentModel = "spring")
public interface StorageMapper extends EntityMapper<StorageDTO, Storage> {
    @Mapping(target = "address", source = "address", qualifiedByName = "addressId")
    @Mapping(target = "company", source = "company", qualifiedByName = "companyId")
    StorageDTO toDto(Storage s);

    @Named("addressId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AddressDTO toDtoAddressId(Address address);

    @Named("companyId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CompanyDTO toDtoCompanyId(Company company);
}
