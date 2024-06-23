package tech.sarthee.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.sarthee.auth.model.entity.SubGroupEntity;
import tech.sarthee.auth.model.entity.identity.SubGroupIdentity;

@Repository
public interface SubGroupRepository extends JpaRepository<SubGroupEntity, SubGroupIdentity> {
}
