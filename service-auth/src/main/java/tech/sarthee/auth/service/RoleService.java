package tech.sarthee.auth.service;

import tech.sarthee.auth.library.exception.ResourceAlreadyExistsException;
import tech.sarthee.auth.library.exception.ResourceNotFoundException;
import tech.sarthee.auth.library.model.dto.request.RoleRequest;
import tech.sarthee.auth.library.model.dto.response.RoleResponse;
import tech.sarthee.auth.model.entity.identity.RoleIdentity;

import java.util.List;

public interface RoleService {
    RoleResponse createRole(RoleRequest request) throws ResourceAlreadyExistsException;
    RoleResponse updateRole(RoleIdentity id, RoleRequest request) throws ResourceNotFoundException;
    void deleteRole(RoleIdentity id) throws ResourceNotFoundException;
    RoleResponse fetchRoleById(RoleIdentity id) throws ResourceNotFoundException;
    List<RoleResponse> fetchAllRoles(String organizationId, String groupId, String subGroupId);
}