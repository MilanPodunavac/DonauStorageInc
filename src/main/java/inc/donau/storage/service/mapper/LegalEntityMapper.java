package inc.donau.storage.service.mapper;

import inc.donau.storage.domain.ContactInfo;
import inc.donau.storage.domain.LegalEntity;
import inc.donau.storage.service.dto.ContactInfoDTO;
import inc.donau.storage.service.dto.LegalEntityDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link LegalEntity} and its DTO {@link LegalEntityDTO}.
 */
@Mapper(componentModel = "spring")
public interface LegalEntityMapper extends EntityMapper<LegalEntityDTO, LegalEntity> {
    @Mapping(target = "contactInfo", source = "contactInfo", qualifiedByName = "contactInfoId")
    LegalEntityDTO toDto(LegalEntity s);

    @Named("contactInfoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ContactInfoDTO toDtoContactInfoId(ContactInfo contactInfo);
}
