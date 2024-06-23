package tech.sarthee.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.sarthee.auth.model.entity.GroupEntity;
import tech.sarthee.auth.model.entity.identity.GroupIdentity;

@Repository
public interface GroupRepository extends JpaRepository<GroupEntity, GroupIdentity> {
}
