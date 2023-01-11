package inc.donau.storage.repository;

import inc.donau.storage.domain.BusinessYear;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the BusinessYear entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BusinessYearRepository extends JpaRepository<BusinessYear, Long>, JpaSpecificationExecutor<BusinessYear> {}
