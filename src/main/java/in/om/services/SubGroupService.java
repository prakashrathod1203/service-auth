package in.om.services;

import in.om.dtos.SubGroupDTO;
import in.om.exceptions.RecordNotFoundException;
import in.om.vos.SubGroupVO;

import java.util.List;

/**
 * @author Prakash Rathod
 */
public interface SubGroupService {
    SubGroupVO create(String groupId, SubGroupDTO subGroupDTO);
    SubGroupVO update(String groupId, String id, SubGroupDTO subGroupDTO) throws RecordNotFoundException;
    SubGroupVO delete(String groupId, String id) throws RecordNotFoundException;

    List<SubGroupVO> fetchSubGroups(String groupId);
    SubGroupVO fetchSubGroup(String groupId, String id) throws RecordNotFoundException;
}
