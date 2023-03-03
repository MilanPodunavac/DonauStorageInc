package inc.donau.storage.repository;

import inc.donau.storage.domain.LegalEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the LegalEntity entity.
 */
@Repository
public interface LegalEntityRepository extends JpaRepository<LegalEntity, Long>, JpaSpecificationExecutor<LegalEntity> {
    default Optional<LegalEntity> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<LegalEntity> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<LegalEntity> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct legalEntity from LegalEntity legalEntity left join fetch legalEntity.contactInfo",
        countQuery = "select count(distinct legalEntity) from LegalEntity legalEntity"
    )
    Page<LegalEntity> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct legalEntity from LegalEntity legalEntity left join fetch legalEntity.contactInfo")
    List<LegalEntity> findAllWithToOneRelationships();

    @Query("select legalEntity from LegalEntity legalEntity left join fetch legalEntity.contactInfo where legalEntity.id =:id")
    Optional<LegalEntity> findOneWithToOneRelationships(@Param("id") Long id);
}
