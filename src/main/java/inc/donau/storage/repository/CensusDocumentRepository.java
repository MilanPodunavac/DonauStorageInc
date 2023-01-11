package inc.donau.storage.repository;

import inc.donau.storage.domain.CensusDocument;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CensusDocument entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CensusDocumentRepository extends JpaRepository<CensusDocument, Long>, JpaSpecificationExecutor<CensusDocument> {}
