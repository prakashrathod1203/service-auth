package om.auth.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import om.auth.helper.DynamicSpecificationBuilder;
import om.auth.helper.RoleHelper;
import om.auth.library.exception.ResourceAlreadyExistsException;
import om.auth.library.exception.ResourceNotFoundException;
import om.auth.library.model.dto.request.filter.GenericFilterRequest;
import om.auth.library.model.dto.request.role.RoleRequest;
import om.auth.library.model.dto.response.role.RoleResponse;
import om.auth.library.util.CommonUtils;
import om.auth.model.entity.PermissionEntity;
import om.auth.model.entity.RoleEntity;
import om.auth.repository.RoleRepository;
import om.auth.repository.RoleScopeRepository;
import om.auth.util.Translator;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

        private final RoleRepository roleRepository;
        private final RoleScopeRepository roleScopeRepository;
        private final RoleHelper roleHelper;

        @Override
        public RoleResponse createRole(RoleRequest request) throws ResourceAlreadyExistsException {
                log.debug("Creating Role with request: {}", request);
                var optionalRoleEntity = roleRepository.findByNameAndOrganizationId(request.name(),
                                request.organizationId());
                if (optionalRoleEntity.isPresent()) {
                        log.error("Role with name {} already exists", request.name());
                        throw new ResourceAlreadyExistsException(
                                        Translator.toLocale("operation.exists"));
                }
                var dbRoleScopeEntity = roleScopeRepository.findById(request.roleScopeId())
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                Translator.toLocale("error.resourceNotFound")));
                var roleEntity = CommonUtils.objectToPojoConverter(request, RoleEntity.class);
                roleEntity.setRoleScope(dbRoleScopeEntity);
                if (request.permissions() != null && !request.permissions().isEmpty()) {
                        List<PermissionEntity> permissionEntities =
                                        request.permissions().stream().map(tr -> {
                                                var permission = CommonUtils.objectToPojoConverter(
                                                                tr, PermissionEntity.class);
                                                permission.setRole(roleEntity);
                                                return permission;
                                        }).toList();

                        roleEntity.setPermissions(permissionEntities);
                }

                var dbRoleEntity = roleRepository.save(roleEntity);
                log.debug("Role created successfully with id: {}", dbRoleEntity.getRoleId());
                return roleHelper.convertRoleResponse(dbRoleEntity);
        }

        @Override
        public RoleResponse updateRole(Long id, RoleRequest request)
                        throws ResourceNotFoundException {
                log.debug("Updating Role with id: {} and request: {}", id, request);
                var dbRoleEntity = roleRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                Translator.toLocale("error.resourceNotFound")));
                var dbRoleScopeEntity = roleScopeRepository.findById(request.roleScopeId())
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                Translator.toLocale("error.resourceNotFound")));
                var roleEntity = CommonUtils.objectToPojoConverter(request, RoleEntity.class);
                roleEntity.setRoleId(dbRoleEntity.getRoleId());
                roleEntity.setName(dbRoleEntity.getName());
                roleEntity.setOrganizationId(dbRoleEntity.getOrganizationId());
                roleEntity.setRoleScope(dbRoleScopeEntity);
                if (request.permissions() != null && !request.permissions().isEmpty()) {
                        List<PermissionEntity> permissionEntities =
                                        request.permissions().stream().map(tr -> {
                                                var permission = CommonUtils.objectToPojoConverter(
                                                                tr, PermissionEntity.class);
                                                permission.setRole(roleEntity);
                                                return permission;
                                        }).toList();

                        roleEntity.setPermissions(permissionEntities);
                }

                var dbUpdatedRoleEntity = roleRepository.save(roleEntity);
                log.debug("Role updated successfully with id: {}", dbUpdatedRoleEntity.getRoleId());
                return roleHelper.convertRoleResponse(dbUpdatedRoleEntity);
        }

        @Override
        public void deleteRole(Long id) throws ResourceNotFoundException {
                log.debug("Deleting Role with id: {}", id);
                var roleEntity = roleRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                Translator.toLocale("error.resourceNotFound")));
                roleRepository.delete(roleEntity);
                log.debug("Role deleted successfully with id: {}", id);
        }

        @Override
        public RoleResponse fetchRoleById(Long id) throws ResourceNotFoundException {
                log.debug("Fetching Role with id: {}", id);
                var roleEntity = roleRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                Translator.toLocale("error.resourceNotFound")));
                log.debug("Role fetched successfully with id: {}", id);
                return roleHelper.convertRoleResponse(roleEntity);
        }

        @Override
        public Page<RoleResponse> fetchAllFilteredRoles(GenericFilterRequest request) {
                log.debug("Fetching roles with filters: {}, page: {}, size: {}, sort: {}",
                                request.getFilters(), request.getPage(), request.getSize(),
                                request.getSort());
                Pageable pageable = DynamicSpecificationBuilder.buildPageRequest(request.getPage(),
                                request.getSize(), request.getSort());
                Specification<RoleEntity> spec =
                                DynamicSpecificationBuilder.build(request.getFilters());
                Page<RoleEntity> page = roleRepository.findAll(spec, pageable);
                log.debug("Fetched {} role records", page.getTotalElements());
                return page.map(entity -> roleHelper.convertRoleResponse(entity));
        }
}
