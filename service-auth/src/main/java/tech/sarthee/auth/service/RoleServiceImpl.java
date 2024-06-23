package tech.sarthee.auth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.sarthee.auth.library.exception.ResourceAlreadyExistsException;
import tech.sarthee.auth.library.exception.ResourceNotFoundException;
import tech.sarthee.auth.library.model.dto.request.RoleRequest;
import tech.sarthee.auth.library.model.dto.response.RoleResponse;
import tech.sarthee.auth.library.util.CommonUtils;
import tech.sarthee.auth.model.entity.RoleEntity;
import tech.sarthee.auth.model.entity.identity.RoleIdentity;
import tech.sarthee.auth.repository.RoleRepository;
import tech.sarthee.auth.util.Translator;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public RoleResponse createRole(RoleRequest request) throws ResourceAlreadyExistsException {
        log.debug("Creating Role with request: {}", request);
        var roleIdentity = new RoleIdentity(request.getOrganizationId(), request.getGroupId(), request.getSubGroupId(), request.getRoleId());
        var optionalRoleEntity = roleRepository.findById(roleIdentity);
        if(optionalRoleEntity.isPresent()) {
            log.error("Role with id {} already exists", roleIdentity);
            throw new ResourceAlreadyExistsException(Translator.toLocale("operation.exists"));
        }

        var roleEntity = CommonUtils.objectToPojoConverter(request, RoleEntity.class);
        roleEntity = roleRepository.save(Objects.requireNonNull(roleEntity));
        log.debug("Role created successfully with id: {}", roleIdentity);
        return CommonUtils.objectToPojoConverter(roleEntity, RoleResponse.class);
    }

    @Override
    public RoleResponse updateRole(RoleIdentity id, RoleRequest request)  throws ResourceNotFoundException  {
        log.debug("Updating Role with id: {} and request: {}", id, request);
        var dbRoleEntity = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Translator.toLocale("error.resourceNotFound")));
        var roleEntity = CommonUtils.objectToPojoConverter(request, RoleEntity.class);
        Objects.requireNonNull(roleEntity).setOrganizationId(dbRoleEntity.getOrganizationId());
        Objects.requireNonNull(roleEntity).setGroupId(dbRoleEntity.getGroupId());
        Objects.requireNonNull(roleEntity).setSubGroupId(dbRoleEntity.getSubGroupId());
        Objects.requireNonNull(roleEntity).setRoleId(dbRoleEntity.getRoleId());
        roleEntity = roleRepository.save(roleEntity);
        log.debug("Role updated successfully with id: {}", id);
        return CommonUtils.objectToPojoConverter(roleEntity, RoleResponse.class);
    }

    @Override
    public void deleteRole(RoleIdentity id) throws ResourceNotFoundException {
        log.debug("Deleting Role with id: {}", id);
        var roleEntity = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Translator.toLocale("error.resourceNotFound")));
        roleRepository.delete(roleEntity);
        log.debug("Role deleted successfully with id: {}", id);
    }

    @Override
    public RoleResponse fetchRoleById(RoleIdentity id) throws ResourceNotFoundException {
        log.debug("Fetching Role with id: {}", id);
        var roleEntity = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Translator.toLocale("error.resourceNotFound")));
        log.debug("Role fetched successfully with id: {}", id);
        return CommonUtils.objectToPojoConverter(roleEntity, RoleResponse.class);
    }

    @Override
    public List<RoleResponse> fetchAllRoles(String organizationId, String groupId, String roleId) {
        log.debug("Received request to get all Roles with organizationId: {}, groupId: {}, roleId: {}", organizationId, groupId, roleId);
        var roles = roleRepository.findByOrganizationIdAndGroupIdAndSubGroupId(organizationId, groupId, roleId);
        log.debug("Roles fetched successfully with organizationId: {}, groupId: {}, roleId: {}", organizationId, groupId, roleId);
        return CommonUtils.objectToPojoConverter(roles, RoleResponse.class);
    }
}
