package in.om.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import in.om.model.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	@Query("SELECT u FROM User u WHERE u.username = ?1 or u.phone = ?1 AND u.active = true")
	Optional<User> findByusername(String username);
	
	@Query("SELECT u FROM User u WHERE u.username = ?1")
	Optional<User> findADByusername(String userName);
	
	@Query("SELECT u FROM User u WHERE u.phone = ?1")
	Optional<User> findADByPhone(String phone);
	
	@Query("SELECT u FROM User u WHERE u.username = ?1")
	Optional<User> findByEmail(String email);
	
	@Query("SELECT u FROM User u WHERE u.username = ?1")
	Optional<User> findActiveInActiveByusername(String username);
	
	@Query("SELECT u FROM User u WHERE u.active = ?1")
	List<User> findByStatus(Boolean status);
	
	@Query("SELECT u FROM User u WHERE u.userId = ?1")
	Optional<User> findADById(Long userId);

}
