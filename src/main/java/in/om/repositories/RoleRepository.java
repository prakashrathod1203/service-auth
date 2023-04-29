package in.om.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import in.om.model.Role;

public interface RoleRepository extends JpaRepository<Role, Short> {
	Optional<Role> findByName(String roleName);
}
