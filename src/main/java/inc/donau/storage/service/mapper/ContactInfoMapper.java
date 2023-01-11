package inc.donau.storage.service.mapper;

import inc.donau.storage.domain.ContactInfo;
import inc.donau.storage.service.dto.ContactInfoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ContactInfo} and its DTO {@link ContactInfoDTO}.
 */
@Mapper(componentModel = "spring")
public interface ContactInfoMapper extends EntityMapper<ContactInfoDTO, ContactInfo> {}
