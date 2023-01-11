package inc.donau.storage.repository;

import inc.donau.storage.domain.StorageCardTraffic;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the StorageCardTraffic entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StorageCardTrafficRepository extends JpaRepository<StorageCardTraffic, Long> {}
