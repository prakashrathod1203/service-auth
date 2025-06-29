package om.auth.service;

import org.springframework.data.domain.Page;
import om.auth.library.exception.ResourceAlreadyExistsException;
import om.auth.library.exception.ResourceNotFoundException;
import om.auth.library.model.dto.request.filter.GenericFilterRequest;
import om.auth.library.model.dto.request.role.RoleRequest;
import om.auth.library.model.dto.response.role.RoleResponse;

public interface RoleService {

        RoleResponse createRole(RoleRequest request) throws ResourceAlreadyExistsException;

        RoleResponse updateRole(Long id, RoleRequest request) throws ResourceNotFoundException;

        void deleteRole(Long id) throws ResourceNotFoundException;

        RoleResponse fetchRoleById(Long id) throws ResourceNotFoundException;

        Page<RoleResponse> fetchAllFilteredRoles(GenericFilterRequest request);
}
