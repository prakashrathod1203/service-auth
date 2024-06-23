package tech.sarthee.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.sarthee.auth.constant.SwaggerConstants;
import tech.sarthee.auth.library.constant.ResourceEndpoint;
import tech.sarthee.auth.library.exception.ResourceAlreadyExistsException;
import tech.sarthee.auth.library.exception.ResourceNotFoundException;
import tech.sarthee.auth.library.model.dto.request.RoleRequest;
import tech.sarthee.auth.library.model.dto.response.RestApiResponse;
import tech.sarthee.auth.model.entity.identity.RoleIdentity;
import tech.sarthee.auth.service.RoleService;
import tech.sarthee.auth.util.Translator;


@Slf4j
@RestController
@RequestMapping(ResourceEndpoint.ROLE)
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    /**
     * Create a new Role.
     */
    @Operation(
            summary = SwaggerConstants.CREATE_ORG_GROUP_SUB_GROUP_ROLE_SUMMARY,
            description = SwaggerConstants.CREATE_ORG_GROUP_SUB_GROUP_ROLE_DESCRIPTION,
            tags = {SwaggerConstants.MASTER_ORG_GROUP_SUB_GROUP_ROLE_TAG}
    )
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> create(
            @Valid @RequestBody RoleRequest request
    ) throws ResourceAlreadyExistsException {
        log.info("Received request to create Role: {}", request);
        var roleResponse = roleService.createRole(request);
        var response = new RestApiResponse(Translator.toLocale("operation.insert"), roleResponse, true);
        log.info("Role created successfully: {}", response);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * Update a new Role.
     */
    @Operation(
            summary = SwaggerConstants.UPDATE_ORG_GROUP_SUB_GROUP_ROLE_SUMMARY,
            description = SwaggerConstants.UPDATE_ORG_GROUP_SUB_GROUP_ROLE_DESCRIPTION,
            tags = {SwaggerConstants.MASTER_ORG_GROUP_SUB_GROUP_ROLE_TAG}
    )
    @PutMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> update(
            @Valid @RequestBody RoleRequest request
    ) throws ResourceNotFoundException {
        log.debug("Received request to update Role with request: {}", request);
        var roleIdentity = new RoleIdentity(request.getOrganizationId(), request.getGroupId(), request.getSubGroupId(), request.getRoleId());
        var roleResponse = roleService.updateRole(roleIdentity, request);
        var response = new RestApiResponse(Translator.toLocale("operation.update"), roleResponse, true);
        log.info("Role update successfully: {}", response);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * Delete Role.
     */
    @Operation(
            summary = SwaggerConstants.DELETE_ORG_GROUP_SUB_GROUP_ROLE_SUMMARY,
            description = SwaggerConstants.DELETE_ORG_GROUP_SUB_GROUP_ROLE_DESCRIPTION,
            tags = {SwaggerConstants.MASTER_ORG_GROUP_SUB_GROUP_ROLE_TAG}
    )
    @DeleteMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> delete(
            @RequestParam(value = "organization_id") String organizationId,
            @RequestParam(value = "group_id") String groupId,
            @RequestParam(value = "sub_group_id") String subGroupId,
            @RequestParam(value = "role_id") String roleId
    ) throws ResourceNotFoundException {
        var roleIdentity = new RoleIdentity(organizationId, groupId, subGroupId, roleId);
        log.debug("Received request to delete Role with id: {}", roleIdentity);
        roleService.deleteRole(roleIdentity);
        var response = new RestApiResponse(Translator.toLocale("operation.delete"), null, true);
        log.info("Role delete successfully: {}", roleIdentity);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * Fetch Role by id.
     */
    @Operation(
            summary = SwaggerConstants.FETCH_ORG_GROUP_SUB_GROUP_ROLE_BY_ID_SUMMARY,
            description = SwaggerConstants.FETCH_ORG_GROUP_SUB_GROUP_ROLE_BY_ID_DESCRIPTION,
            tags = {SwaggerConstants.MASTER_ORG_GROUP_SUB_GROUP_ROLE_TAG}
    )
    @GetMapping(
            path = "/{organization_id}/{group_id}/{sub_group_id}/{role_id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> fetch(
            @PathVariable(name = "organization_id") String organizationId,
            @PathVariable(name = "group_id") String groupId,
            @PathVariable(name = "sub_group_id") String subGroupId,
            @PathVariable(name = "role_id") String roleId
    ) throws ResourceNotFoundException {
        var roleIdentity = new RoleIdentity(organizationId, groupId, subGroupId, roleId);
        log.info("Received request to fetch Role with id: {}", roleIdentity);
        var roleResponse = roleService.fetchRoleById(roleIdentity);
        var response = new RestApiResponse(Translator.toLocale("operation.get"), roleResponse, true);
        log.info("Role fetched successfully with id: {}", roleIdentity);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * Fetch All Role.
     */
    @Operation(
            summary = SwaggerConstants.FETCH_ORG_GROUP_SUB_GROUP_ROLE_SUMMARY,
            description = SwaggerConstants.FETCH_ORG_GROUP_SUB_GROUP_ROLE_DESCRIPTION,
            tags = {SwaggerConstants.MASTER_ORG_GROUP_SUB_GROUP_ROLE_TAG}
    )
    @GetMapping(
            path = "/{organization_id}/{group_id}/{sub_group_id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> fetchAll(
            @PathVariable(name = "organization_id") String organizationId,
            @PathVariable(name = "group_id") String groupId,
            @PathVariable(name = "sub_group_id") String subGroupId
    ){
        log.debug("Received request to fetch all Roles. organizationId: {}, groupId: {}, subGroupId: {}", organizationId, groupId, subGroupId);
        var RolesResponse = roleService.fetchAllRoles(organizationId, groupId, subGroupId);
        var response = new RestApiResponse(Translator.toLocale("operation.getList"), RolesResponse, true);
        log.info("Roles fetched successfully. organizationId: {}, groupId: {}, subGroupId: {}", organizationId, groupId, subGroupId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
