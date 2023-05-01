package in.om.repositories;

import in.om.entities.Group;
import in.om.entities.GroupIdentity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Prakash Rathod
 */
public interface GroupRepository extends JpaRepository<Group, GroupIdentity> {
    List<Group> findByOrganizationId(String organizationId);
}
