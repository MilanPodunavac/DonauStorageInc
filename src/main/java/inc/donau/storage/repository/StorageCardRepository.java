package inc.donau.storage.repository;

import inc.donau.storage.domain.StorageCard;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the StorageCard entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StorageCardRepository extends JpaRepository<StorageCard, String>, JpaSpecificationExecutor<StorageCard> {}
