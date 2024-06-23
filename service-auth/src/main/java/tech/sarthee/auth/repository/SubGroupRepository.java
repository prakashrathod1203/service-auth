package tech.sarthee.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.sarthee.auth.model.entity.GroupEntity;
import tech.sarthee.auth.model.entity.SubGroupEntity;
import tech.sarthee.auth.model.entity.identity.SubGroupIdentity;

import java.util.List;

@Repository
public interface SubGroupRepository extends JpaRepository<SubGroupEntity, SubGroupIdentity> {
    List<SubGroupEntity> findByOrganizationIdAndGroupId(String organizationId, String groupId);
}
