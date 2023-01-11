package inc.donau.storage.repository;

import inc.donau.storage.domain.BusinessPartner;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the BusinessPartner entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BusinessPartnerRepository extends JpaRepository<BusinessPartner, Long> {}
