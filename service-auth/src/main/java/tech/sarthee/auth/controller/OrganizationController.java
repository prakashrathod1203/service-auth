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
import tech.sarthee.auth.library.model.dto.request.OrganizationRequest;
import tech.sarthee.auth.library.model.dto.response.RestApiResponse;
import tech.sarthee.auth.service.OrganizationService;
import tech.sarthee.auth.util.Translator;


@Slf4j
@RestController
@RequestMapping(ResourceEndpoint.ORGANIZATION)
@RequiredArgsConstructor
public class OrganizationController {

    private final OrganizationService organizationService;

    /**
     * Create a new Organization.
     */
    @Operation(
            summary = SwaggerConstants.CREATE_ORG_SUMMARY,
            description = SwaggerConstants.CREATE_ORG_DESCRIPTION,
            tags = {SwaggerConstants.MASTER_TAG}
    )
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> create(
            @Valid @RequestBody OrganizationRequest request
    ) throws ResourceAlreadyExistsException {
        log.info("Received request to create organization: {}", request);
        var organizationResponse = organizationService.createOrganization(request);
        var response = new RestApiResponse(Translator.toLocale("operation.insert"), organizationResponse, true);
        log.info("Organization created successfully: {}", response);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * Update a new Organization.
     */
    @Operation(
            summary = SwaggerConstants.UPDATE_ORG_SUMMARY,
            description = SwaggerConstants.UPDATE_ORG_DESCRIPTION,
            tags = {SwaggerConstants.MASTER_TAG}
    )
    @PutMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> update(
            @RequestParam(value = "organization_id", required = true) String organizationId,
            @Valid @RequestBody OrganizationRequest request
    ) throws ResourceNotFoundException {
        log.debug("Received request to update organization with id: {} and request: {}", organizationId, request);
        var organizationResponse = organizationService.updateOrganization(organizationId, request);
        var response = new RestApiResponse(Translator.toLocale("operation.update"), organizationResponse, true);
        log.info("Organization update successfully: {}", response);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * Delete Organization.
     */
    @Operation(
            summary = SwaggerConstants.DELETE_ORG_SUMMARY,
            description = SwaggerConstants.DELETE_ORG_DESCRIPTION,
            tags = {SwaggerConstants.MASTER_TAG}
    )
    @DeleteMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> delete(
            @RequestParam(value = "organization_id", required = true) String organizationId
    ) throws ResourceNotFoundException {
        log.debug("Received request to delete organization with id: {}", organizationId);
        organizationService.deleteOrganization(organizationId);
        var response = new RestApiResponse(Translator.toLocale("operation.delete"), null, true);
        log.info("Organization delete successfully: {}", organizationId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * Fetch Organization by id.
     */
    @Operation(
            summary = SwaggerConstants.FETCH_ORG_BY_ID_SUMMARY,
            description = SwaggerConstants.FETCH_ORG_BY_ID_DESCRIPTION,
            tags = {SwaggerConstants.MASTER_TAG}
    )
    @GetMapping(
            path = "/{organization_id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> fetch(
            @PathVariable(name = "organization_id") String organizationId
    ) throws ResourceNotFoundException {
        log.info("Received request to fetch organization with id: {}", organizationId);

        var organizationResponse = organizationService.fetchOrganizationById(organizationId);
        RestApiResponse response = new RestApiResponse(Translator.toLocale("operation.get"), organizationResponse, true);
        log.info("Organization fetched successfully with id: {}", organizationId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * Fetch All Organization.
     */
    @Operation(
            summary = SwaggerConstants.FETCH_ORG_SUMMARY,
            description = SwaggerConstants.FETCH_ORG_DESCRIPTION,
            tags = {SwaggerConstants.MASTER_TAG}
    )
    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> fetchAll(
    ){
        log.debug("Received request to fetch all organizations");
        var organizationsResponse = organizationService.fetchAllOrganizations();
        RestApiResponse response = new RestApiResponse(Translator.toLocale("operation.getList"), organizationsResponse, true);
        log.info("Organizations fetched successfully: {}", response);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
