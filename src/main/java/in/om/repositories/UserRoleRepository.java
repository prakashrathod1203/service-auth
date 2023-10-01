package in.om.repositories;

import in.om.entities.UserRole;
import in.om.entities.identity.UserRoleIdentity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Prakash Rathod
 */
public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleIdentity> {
    List<UserRole> findByLoginId(String loginId);
}
