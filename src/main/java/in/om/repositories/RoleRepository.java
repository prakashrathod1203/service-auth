package in.om.repositories;

import in.om.entities.Role;
import in.om.entities.identity.RoleIdentity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, RoleIdentity> {
	List<Role> findByGroupId(String groupId);
	long countByGroupId(String groupId);
}
