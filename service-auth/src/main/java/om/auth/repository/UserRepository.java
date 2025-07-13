package om.auth.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import om.auth.model.entity.UserEntity;

@Repository
public interface UserRepository
                extends JpaRepository<UserEntity, Long>, JpaSpecificationExecutor<UserEntity> {
        Optional<UserEntity> findByLoginId(String loginId);
}
