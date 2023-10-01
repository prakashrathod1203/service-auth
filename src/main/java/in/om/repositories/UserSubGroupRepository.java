package in.om.repositories;

import in.om.entities.UserSubGroup;
import in.om.entities.identity.UserSubGroupIdentity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Prakash Rathod
 */
public interface UserSubGroupRepository extends JpaRepository<UserSubGroup, UserSubGroupIdentity> {
    List<UserSubGroup> findByLoginId(String loginId);
}
