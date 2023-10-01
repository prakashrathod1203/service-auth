package in.om.repositories;

import in.om.entities.User;
import in.om.entities.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author Prakash Rathod
 */
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByLoginId(String loginId);

}
