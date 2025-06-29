package om.auth.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import om.auth.model.entity.RoleEntity;

@Repository
public interface RoleRepository
                extends JpaRepository<RoleEntity, Long>, JpaSpecificationExecutor<RoleEntity> {
        Optional<RoleEntity> findByNameAndOrganizationId(String name, Integer organizationId);
}
