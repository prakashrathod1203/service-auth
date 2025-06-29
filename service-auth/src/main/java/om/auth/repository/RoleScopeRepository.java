package om.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import om.auth.model.entity.RoleScopeEntity;

@Repository
public interface RoleScopeRepository
        extends JpaRepository<RoleScopeEntity, Integer>, JpaSpecificationExecutor<RoleScopeEntity> {
}
