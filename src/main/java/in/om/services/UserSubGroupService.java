package in.om.services;

import in.om.entities.UserSubGroup;
import in.om.entities.identity.UserSubGroupIdentity;
import in.om.exceptions.RecordNotFoundException;

import java.util.List;

/**
 * @author Prakash Rathod
 */
public interface UserSubGroupService {
	UserSubGroup create(UserSubGroup userSubGroup);
	UserSubGroup delete(UserSubGroupIdentity userSubGroupIdentity) throws RecordNotFoundException;

	List<UserSubGroup> fetchUserSubGroup(String loginId);
}
