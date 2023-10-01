package in.om.services;

import in.om.entities.UserRole;
import in.om.entities.identity.UserRoleIdentity;
import in.om.exceptions.RecordNotFoundException;

import java.util.List;

/**
 * @author Prakash Rathod
 */
public interface UserRoleService {
	UserRole create(UserRole userRole);
	UserRole delete(UserRoleIdentity userRoleIdentity) throws RecordNotFoundException;

	List<UserRole> fetchUserRole(String loginId);
}
