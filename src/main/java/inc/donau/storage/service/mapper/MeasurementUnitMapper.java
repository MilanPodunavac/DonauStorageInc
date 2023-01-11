package inc.donau.storage.service.mapper;

import inc.donau.storage.domain.MeasurementUnit;
import inc.donau.storage.service.dto.MeasurementUnitDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link MeasurementUnit} and its DTO {@link MeasurementUnitDTO}.
 */
@Mapper(componentModel = "spring")
public interface MeasurementUnitMapper extends EntityMapper<MeasurementUnitDTO, MeasurementUnit> {}
