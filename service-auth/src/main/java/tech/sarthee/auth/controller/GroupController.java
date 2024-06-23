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
import tech.sarthee.auth.library.model.dto.request.GroupRequest;
import tech.sarthee.auth.library.model.dto.response.RestApiResponse;
import tech.sarthee.auth.model.entity.identity.GroupIdentity;
import tech.sarthee.auth.service.GroupService;
import tech.sarthee.auth.util.Translator;


@Slf4j
@RestController
@RequestMapping(ResourceEndpoint.GROUP)
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

    /**
     * Create a new Group.
     */
    @Operation(
            summary = SwaggerConstants.CREATE_ORG_GROUP_SUMMARY,
            description = SwaggerConstants.CREATE_ORG_GROUP_DESCRIPTION,
            tags = {SwaggerConstants.MASTER_ORG_GROUP_TAG}
    )
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> create(
            @Valid @RequestBody GroupRequest request
    ) throws ResourceAlreadyExistsException {
        log.info("Received request to create Group: {}", request);
        var groupResponse = groupService.createGroup(request);
        var response = new RestApiResponse(Translator.toLocale("operation.insert"), groupResponse, true);
        log.info("Group created successfully: {}", response);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * Update a new Group.
     */
    @Operation(
            summary = SwaggerConstants.UPDATE_ORG_GROUP_SUMMARY,
            description = SwaggerConstants.UPDATE_ORG_GROUP_DESCRIPTION,
            tags = {SwaggerConstants.MASTER_ORG_GROUP_TAG}
    )
    @PutMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> update(
            @Valid @RequestBody GroupRequest request
    ) throws ResourceNotFoundException {
        log.debug("Received request to update Group with request: {}", request);
        var groupIdentity = new GroupIdentity(request.getOrganizationId(), request.getGroupId());
        var groupResponse = groupService.updateGroup(groupIdentity, request);
        var response = new RestApiResponse(Translator.toLocale("operation.update"), groupResponse, true);
        log.info("Group update successfully: {}", response);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * Delete Group.
     */
    @Operation(
            summary = SwaggerConstants.DELETE_ORG_GROUP_SUMMARY,
            description = SwaggerConstants.DELETE_ORG_GROUP_DESCRIPTION,
            tags = {SwaggerConstants.MASTER_ORG_GROUP_TAG}
    )
    @DeleteMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> delete(
            @RequestParam(value = "organization_id") String organizationId,
            @RequestParam(value = "group_id") String groupId
    ) throws ResourceNotFoundException {
        var groupIdentity = new GroupIdentity(organizationId, groupId);
        log.debug("Received request to delete Group with id: {}", groupIdentity);
        groupService.deleteGroup(groupIdentity);
        var response = new RestApiResponse(Translator.toLocale("operation.delete"), null, true);
        log.info("Group delete successfully: {}", groupIdentity);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * Fetch Group by id.
     */
    @Operation(
            summary = SwaggerConstants.FETCH_ORG_GROUP_BY_ID_SUMMARY,
            description = SwaggerConstants.FETCH_ORG_GROUP_BY_ID_DESCRIPTION,
            tags = {SwaggerConstants.MASTER_ORG_GROUP_TAG}
    )
    @GetMapping(
            path = "/{organization_id}/{group_id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> fetch(
            @PathVariable(name = "organization_id") String organizationId,
            @PathVariable(name = "group_id") String groupId
    ) throws ResourceNotFoundException {
        var groupIdentity = new GroupIdentity(organizationId, groupId);
        log.info("Received request to fetch Group with id: {}", groupIdentity);
        var groupResponse = groupService.fetchGroupById(groupIdentity);
        var response = new RestApiResponse(Translator.toLocale("operation.get"), groupResponse, true);
        log.info("Group fetched successfully with id: {}", groupIdentity);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * Fetch All Group.
     */
    @Operation(
            summary = SwaggerConstants.FETCH_ORG_GROUP_SUMMARY,
            description = SwaggerConstants.FETCH_ORG_GROUP_DESCRIPTION,
            tags = {SwaggerConstants.MASTER_ORG_GROUP_TAG}
    )
    @GetMapping(
            path = "/{organization_id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> fetchAll(
            @PathVariable(name = "organization_id") String organizationId
    ){
        log.debug("Received request to fetch all Groups. organizationId: {}", organizationId);
        var groupsResponse = groupService.fetchAllGroups(organizationId);
        var response = new RestApiResponse(Translator.toLocale("operation.getList"), groupsResponse, true);
        log.info("Groups fetched successfully. organizationId: {}", organizationId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
