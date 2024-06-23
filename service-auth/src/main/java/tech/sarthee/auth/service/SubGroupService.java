package tech.sarthee.auth.service;

import tech.sarthee.auth.library.exception.ResourceAlreadyExistsException;
import tech.sarthee.auth.library.exception.ResourceNotFoundException;
import tech.sarthee.auth.library.model.dto.request.SubGroupRequest;
import tech.sarthee.auth.library.model.dto.response.SubGroupResponse;
import tech.sarthee.auth.model.entity.identity.SubGroupIdentity;

import java.util.List;

public interface SubGroupService {
    SubGroupResponse createSubGroup(SubGroupRequest request) throws ResourceAlreadyExistsException;
    SubGroupResponse updateSubGroup(SubGroupIdentity id, SubGroupRequest request) throws ResourceNotFoundException;
    void deleteSubGroup(SubGroupIdentity id) throws ResourceNotFoundException;
    SubGroupResponse fetchSubGroupById(SubGroupIdentity id) throws ResourceNotFoundException;
    List<SubGroupResponse> fetchAllSubGroups(String organizationId, String subGroupId);
}