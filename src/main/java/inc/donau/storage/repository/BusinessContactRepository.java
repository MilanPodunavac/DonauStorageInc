package inc.donau.storage.repository;

import inc.donau.storage.domain.BusinessContact;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the BusinessContact entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BusinessContactRepository extends JpaRepository<BusinessContact, Long> {}
