package om.auth.service;

import org.springframework.data.domain.Page;
import om.auth.library.exception.ResourceAlreadyExistsException;
import om.auth.library.exception.ResourceNotFoundException;
import om.auth.library.model.dto.request.filter.GenericFilterRequest;
import om.auth.library.model.dto.request.role.RoleScopeRequest;
import om.auth.library.model.dto.response.role.RoleScopeResponse;

public interface RoleScopeService {

    RoleScopeResponse createRoleScope(RoleScopeRequest request)
            throws ResourceAlreadyExistsException;

    RoleScopeResponse updateRoleScope(Integer id, RoleScopeRequest request)
            throws ResourceNotFoundException;

    void deleteRoleScope(Integer id) throws ResourceNotFoundException;

    RoleScopeResponse fetchRoleScopeById(Integer id) throws ResourceNotFoundException;

    Page<RoleScopeResponse> fetchAllFilteredRoleScopes(GenericFilterRequest request);
}
