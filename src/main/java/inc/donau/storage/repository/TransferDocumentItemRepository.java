package inc.donau.storage.repository;

import inc.donau.storage.domain.TransferDocumentItem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TransferDocumentItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TransferDocumentItemRepository extends JpaRepository<TransferDocumentItem, Long> {}
