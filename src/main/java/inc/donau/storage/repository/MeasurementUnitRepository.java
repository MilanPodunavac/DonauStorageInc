package inc.donau.storage.repository;

import inc.donau.storage.domain.MeasurementUnit;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MeasurementUnit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MeasurementUnitRepository extends JpaRepository<MeasurementUnit, Long> {}
