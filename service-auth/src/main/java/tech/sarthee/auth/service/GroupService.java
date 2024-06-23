package tech.sarthee.auth.service;

import tech.sarthee.auth.library.exception.ResourceAlreadyExistsException;
import tech.sarthee.auth.library.exception.ResourceNotFoundException;
import tech.sarthee.auth.library.model.dto.request.GroupRequest;
import tech.sarthee.auth.library.model.dto.response.GroupResponse;
import tech.sarthee.auth.model.entity.identity.GroupIdentity;

import java.util.List;

public interface GroupService {
    GroupResponse createGroup(GroupRequest request) throws ResourceAlreadyExistsException;
    GroupResponse updateGroup(GroupIdentity id, GroupRequest request) throws ResourceNotFoundException;
    void deleteGroup(GroupIdentity id) throws ResourceNotFoundException;
    GroupResponse fetchGroupById(GroupIdentity id) throws ResourceNotFoundException;
    List<GroupResponse> fetchAllGroups(String organizationId);
}