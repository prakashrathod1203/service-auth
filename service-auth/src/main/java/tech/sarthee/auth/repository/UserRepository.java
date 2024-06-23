package tech.sarthee.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.sarthee.auth.model.entity.UserEntity;
import tech.sarthee.auth.model.entity.identity.UserIdentity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UserIdentity> {
}
