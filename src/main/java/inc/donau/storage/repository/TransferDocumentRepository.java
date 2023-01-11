package inc.donau.storage.repository;

import inc.donau.storage.domain.TransferDocument;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TransferDocument entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TransferDocumentRepository extends JpaRepository<TransferDocument, Long>, JpaSpecificationExecutor<TransferDocument> {}
