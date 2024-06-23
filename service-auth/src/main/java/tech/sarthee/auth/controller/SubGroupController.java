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
import tech.sarthee.auth.library.model.dto.request.SubGroupRequest;
import tech.sarthee.auth.library.model.dto.response.RestApiResponse;
import tech.sarthee.auth.model.entity.identity.SubGroupIdentity;
import tech.sarthee.auth.service.SubGroupService;
import tech.sarthee.auth.util.Translator;


@Slf4j
@RestController
@RequestMapping(ResourceEndpoint.SUB_GROUP)
@RequiredArgsConstructor
public class SubGroupController {

    private final SubGroupService subGroupService;

    /**
     * Create a new SubGroup.
     */
    @Operation(
            summary = SwaggerConstants.CREATE_ORG_GROUP_SUB_GROUP_SUMMARY,
            description = SwaggerConstants.CREATE_ORG_GROUP_SUB_GROUP_DESCRIPTION,
            tags = {SwaggerConstants.MASTER_ORG_GROUP_SUB_GROUP_TAG}
    )
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> create(
            @Valid @RequestBody SubGroupRequest request
    ) throws ResourceAlreadyExistsException {
        log.info("Received request to create SubGroup: {}", request);
        var subGroupResponse = subGroupService.createSubGroup(request);
        var response = new RestApiResponse(Translator.toLocale("operation.insert"), subGroupResponse, true);
        log.info("SubGroup created successfully: {}", response);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * Update a new SubGroup.
     */
    @Operation(
            summary = SwaggerConstants.UPDATE_ORG_GROUP_SUB_GROUP_SUMMARY,
            description = SwaggerConstants.UPDATE_ORG_GROUP_SUB_GROUP_DESCRIPTION,
            tags = {SwaggerConstants.MASTER_ORG_GROUP_SUB_GROUP_TAG}
    )
    @PutMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> update(
            @Valid @RequestBody SubGroupRequest request
    ) throws ResourceNotFoundException {
        log.debug("Received request to update SubGroup with request: {}", request);
        var subGroupIdentity = new SubGroupIdentity(request.getOrganizationId(), request.getGroupId(), request.getSubGroupId());
        var subGroupResponse = subGroupService.updateSubGroup(subGroupIdentity, request);
        var response = new RestApiResponse(Translator.toLocale("operation.update"), subGroupResponse, true);
        log.info("SubGroup update successfully: {}", response);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * Delete SubGroup.
     */
    @Operation(
            summary = SwaggerConstants.DELETE_ORG_GROUP_SUB_GROUP_SUMMARY,
            description = SwaggerConstants.DELETE_ORG_GROUP_SUB_GROUP_DESCRIPTION,
            tags = {SwaggerConstants.MASTER_ORG_GROUP_SUB_GROUP_TAG}
    )
    @DeleteMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> delete(
            @RequestParam(value = "organization_id") String organizationId,
            @RequestParam(value = "group_id") String groupId,
            @RequestParam(value = "sub_group_id") String subGroupId
    ) throws ResourceNotFoundException {
        var subGroupIdentity = new SubGroupIdentity(organizationId, groupId, subGroupId);
        log.debug("Received request to delete SubGroup with id: {}", subGroupIdentity);
        subGroupService.deleteSubGroup(subGroupIdentity);
        var response = new RestApiResponse(Translator.toLocale("operation.delete"), null, true);
        log.info("SubGroup delete successfully: {}", subGroupIdentity);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * Fetch SubGroup by id.
     */
    @Operation(
            summary = SwaggerConstants.FETCH_ORG_GROUP_SUB_GROUP_BY_ID_SUMMARY,
            description = SwaggerConstants.FETCH_ORG_GROUP_SUB_GROUP_BY_ID_DESCRIPTION,
            tags = {SwaggerConstants.MASTER_ORG_GROUP_SUB_GROUP_TAG}
    )
    @GetMapping(
            path = "/{organization_id}/{group_id}/{sub_group_id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> fetch(
            @PathVariable(name = "organization_id") String organizationId,
            @PathVariable(name = "group_id") String groupId,
            @PathVariable(name = "sub_group_id") String subGroupId
    ) throws ResourceNotFoundException {
        var subGroupIdentity = new SubGroupIdentity(organizationId, groupId, subGroupId);
        log.info("Received request to fetch SubGroup with id: {}", subGroupIdentity);
        var subGroupResponse = subGroupService.fetchSubGroupById(subGroupIdentity);
        var response = new RestApiResponse(Translator.toLocale("operation.get"), subGroupResponse, true);
        log.info("SubGroup fetched successfully with id: {}", subGroupIdentity);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * Fetch All SubGroup.
     */
    @Operation(
            summary = SwaggerConstants.FETCH_ORG_GROUP_SUB_GROUP_SUMMARY,
            description = SwaggerConstants.FETCH_ORG_GROUP_SUB_GROUP_DESCRIPTION,
            tags = {SwaggerConstants.MASTER_ORG_GROUP_SUB_GROUP_TAG}
    )
    @GetMapping(
            path = "/{organization_id}/{group_id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> fetchAll(
            @PathVariable(name = "organization_id") String organizationId,
            @PathVariable(name = "group_id") String groupId
    ){
        log.debug("Received request to fetch all SubGroups. organizationId: {}, groupId: {}", organizationId, groupId);
        var subGroupsResponse = subGroupService.fetchAllSubGroups(organizationId, groupId);
        var response = new RestApiResponse(Translator.toLocale("operation.getList"), subGroupsResponse, true);
        log.info("SubGroups fetched successfully. organizationId: {}, groupId: {}", organizationId, groupId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
