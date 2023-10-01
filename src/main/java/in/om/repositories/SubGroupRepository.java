package in.om.repositories;

import in.om.entities.SubGroup;
import in.om.entities.identity.SubGroupIdentity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Prakash Rathod
 */
public interface SubGroupRepository extends JpaRepository<SubGroup, SubGroupIdentity> {
    List<SubGroup> findByGroupId(String groupId);
}
