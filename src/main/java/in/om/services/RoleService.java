package in.om.services;

import in.om.dtos.RoleDTO;
import in.om.exceptions.RecordNotFoundException;
import in.om.vos.RoleVO;

import java.util.List;

/**
 * @author Prakash Rathod
 */
public interface RoleService {
	RoleVO create(String groupId, RoleDTO roleDTO);
	RoleVO update(String groupId, String id, RoleDTO roleDTO) throws RecordNotFoundException;
	RoleVO delete(String groupId, String id) throws RecordNotFoundException;

	List<RoleVO> fetchRoles(String groupId);
	RoleVO fetchRole(String groupId, String id) throws RecordNotFoundException;
}
