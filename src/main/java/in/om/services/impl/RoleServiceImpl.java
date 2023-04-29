package in.om.services.impl;

import java.util.List;
import java.util.Optional;

import in.om.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.om.model.Role;
import in.om.services.RoleService;

@Service
public class RoleServiceImpl implements RoleService {
	
	private final RoleRepository roleRepository;
	
	@Autowired
	public RoleServiceImpl(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}

	@Override
	public Role update(Role role) {
		return roleRepository.save(role);
	}

	@Override
	public Optional<Role> findByRoleName(String roleName) {
		return roleRepository.findByName(roleName);
	}

	@Override
	public Optional<Role> findById(Short roleId) {
		return roleRepository.findById(roleId);
	}

	@Override
	public void delete(Role role) {
		roleRepository.delete(role);
	}

	@Override
	public List<Role> findAll() {
		return roleRepository.findAll();
	}
}	
