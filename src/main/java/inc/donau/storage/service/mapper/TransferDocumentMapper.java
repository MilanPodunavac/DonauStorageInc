package inc.donau.storage.service.mapper;

import inc.donau.storage.domain.BusinessPartner;
import inc.donau.storage.domain.BusinessYear;
import inc.donau.storage.domain.Storage;
import inc.donau.storage.domain.TransferDocument;
import inc.donau.storage.service.dto.BusinessPartnerDTO;
import inc.donau.storage.service.dto.BusinessYearDTO;
import inc.donau.storage.service.dto.StorageDTO;
import inc.donau.storage.service.dto.TransferDocumentDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TransferDocument} and its DTO {@link TransferDocumentDTO}.
 */
@Mapper(componentModel = "spring")
public interface TransferDocumentMapper extends EntityMapper<TransferDocumentDTO, TransferDocument> {
    @Mapping(target = "businessYear", source = "businessYear", qualifiedByName = "businessYearId")
    @Mapping(target = "receivingStorage", source = "receivingStorage", qualifiedByName = "storageId")
    @Mapping(target = "dispatchingStorage", source = "dispatchingStorage", qualifiedByName = "storageId")
    @Mapping(target = "businessPartner", source = "businessPartner", qualifiedByName = "businessPartnerId")
    TransferDocumentDTO toDto(TransferDocument s);

    @Named("businessYearId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "yearCode", source = "yearCode")
    @Mapping(target = "completed", source = "completed")
    @Mapping(target = "company", source = "company")
    BusinessYearDTO toDtoBusinessYearId(BusinessYear businessYear);

    @Named("storageId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "address", source = "address")
    StorageDTO toDtoStorageId(Storage storage);

    @Named("businessPartnerId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "businessContact", source = "businessContact")
    @Mapping(target = "legalEntityInfo", source = "legalEntityInfo")
    BusinessPartnerDTO toDtoBusinessPartnerId(BusinessPartner businessPartner);
}
