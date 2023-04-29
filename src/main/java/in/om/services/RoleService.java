package in.om.services;

import java.util.List;
import java.util.Optional;

import in.om.model.Role;

public interface RoleService {
	Role update(Role user);
	void delete(Role user);
	Optional<Role> findByRoleName(String roleName);
	Optional<Role> findById(Short userId);
	List<Role> findAll();
}
