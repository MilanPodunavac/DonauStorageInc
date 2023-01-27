package inc.donau.storage.service.mapper;

import inc.donau.storage.domain.BusinessYear;
import inc.donau.storage.domain.CensusDocument;
import inc.donau.storage.domain.Employee;
import inc.donau.storage.domain.Storage;
import inc.donau.storage.service.dto.BusinessYearDTO;
import inc.donau.storage.service.dto.CensusDocumentDTO;
import inc.donau.storage.service.dto.EmployeeDTO;
import inc.donau.storage.service.dto.StorageDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CensusDocument} and its DTO {@link CensusDocumentDTO}.
 */
@Mapper(componentModel = "spring")
public interface CensusDocumentMapper extends EntityMapper<CensusDocumentDTO, CensusDocument> {
    @Mapping(target = "businessYear", source = "businessYear", qualifiedByName = "businessYearId")
    @Mapping(target = "president", source = "president", qualifiedByName = "employeeId")
    @Mapping(target = "deputy", source = "deputy", qualifiedByName = "employeeId")
    @Mapping(target = "censusTaker", source = "censusTaker", qualifiedByName = "employeeId")
    @Mapping(target = "storage", source = "storage", qualifiedByName = "storageId")
    CensusDocumentDTO toDto(CensusDocument s);

    @Named("businessYearId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "yearCode", source = "yearCode")
    @Mapping(target = "completed", source = "completed")
    @Mapping(target = "company", source = "company")
    BusinessYearDTO toDtoBusinessYearId(BusinessYear businessYear);

    @Named("employeeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "uniqueIdentificationNumber", source = "uniqueIdentificationNumber")
    @Mapping(target = "birthDate", source = "birthDate")
    @Mapping(target = "disability", source = "disability")
    @Mapping(target = "employment", source = "employment")
    @Mapping(target = "address", source = "address")
    @Mapping(target = "personalInfo", source = "personalInfo")
    EmployeeDTO toDtoEmployeeId(Employee employee);

    @Named("storageId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "address", source = "address")
    StorageDTO toDtoStorageId(Storage storage);
}
