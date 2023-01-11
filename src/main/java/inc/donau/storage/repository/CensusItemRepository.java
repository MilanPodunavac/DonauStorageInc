package inc.donau.storage.repository;

import inc.donau.storage.domain.CensusItem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CensusItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CensusItemRepository extends JpaRepository<CensusItem, Long> {}
