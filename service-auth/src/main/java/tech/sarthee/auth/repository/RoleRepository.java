package tech.sarthee.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.sarthee.auth.model.entity.RoleEntity;
import tech.sarthee.auth.model.entity.identity.RoleIdentity;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, RoleIdentity> {
    List<RoleEntity> findByOrganizationIdAndGroupIdAndSubGroupId(String organizationId, String groupId, String subGroupId);
}
