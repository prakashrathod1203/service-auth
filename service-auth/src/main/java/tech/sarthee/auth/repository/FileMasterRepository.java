package tech.sarthee.auth.repository;

import tech.sarthee.auth.library.enums.EntityNameEnum;
import tech.sarthee.auth.model.entity.FileMasterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FileMasterRepository extends JpaRepository<FileMasterEntity, Long> {
    Optional<FileMasterEntity> findByEntityNameAndEntityId(EntityNameEnum entityName, String entityId);

    @Query("SELECT f FROM FileMasterEntity f WHERE f.entityId = :entityId AND f.entityName = :entityName")
    List<FileMasterEntity> findByEntityIdAndEntityName(@Param("entityId") String entityId,
                                                       @Param("entityName") EntityNameEnum entityName);
    @Query("SELECT f FROM FileMasterEntity f WHERE f.entityId = :entityId AND f.entityName IN (:entityNames)")
    List<FileMasterEntity> findByEntityIdAndEntityNames(@Param("entityId") String entityId,
                                                  @Param("entityNames") List<EntityNameEnum> entityNames);
    @Query("SELECT f FROM FileMasterEntity f WHERE f.entityId = :entityId")
    List<FileMasterEntity> findByEntityId(@Param("entityId") String entityId);
}
