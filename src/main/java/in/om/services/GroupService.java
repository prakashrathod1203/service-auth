package in.om.services;

import in.om.dtos.GroupDTO;
import in.om.exceptions.RecordNotFoundException;
import in.om.vos.GroupVO;

import java.util.List;

/**
 * @author Prakash Rathod
 */
public interface GroupService {
    GroupVO create(String organizationId, GroupDTO groupDTO);
    GroupVO update(String organizationId, String id, GroupDTO groupDTO) throws RecordNotFoundException;
    GroupVO delete(String organizationId, String id) throws RecordNotFoundException;

    List<GroupVO> fetchGroups(String organizationId);
    GroupVO fetchGroup(String organizationId, String id) throws RecordNotFoundException;
}
