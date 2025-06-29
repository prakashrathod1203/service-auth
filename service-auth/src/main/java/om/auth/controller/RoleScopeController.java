package om.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import om.auth.constant.SwaggerConstants;
import om.auth.library.constant.ResourceEndpoint;
import om.auth.library.exception.ResourceAlreadyExistsException;
import om.auth.library.exception.ResourceNotFoundException;
import om.auth.library.model.dto.request.filter.GenericFilterRequest;
import om.auth.library.model.dto.request.role.RoleScopeRequest;
import om.auth.library.model.dto.response.RestApiResponse;
import om.auth.service.RoleScopeService;
import om.auth.util.Translator;

@Slf4j
@RestController
@RequestMapping(ResourceEndpoint.ROLE_SCOPE)
@RequiredArgsConstructor
public class RoleScopeController {

        private final RoleScopeService roleScopeService;

        /**
         * Create a new RoleScope.
         */
        @Operation(summary = SwaggerConstants.CREATE_ROLE_SCOPE_SUMMARY,
                        description = SwaggerConstants.CREATE_ROLE_SCOPE_DESCRIPTION,
                        tags = {SwaggerConstants.ROLE_SCOPE_TAG})
        @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
                        produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<?> create(@Valid @RequestBody RoleScopeRequest request)
                        throws ResourceAlreadyExistsException {
                log.debug("Received request to create role scope : {}", request);
                var roleScopeResponse = roleScopeService.createRoleScope(request);
                var response = new RestApiResponse(Translator.toLocale("operation.insert"),
                                roleScopeResponse, true);
                log.debug("Role Scope created successfully: {}", response);
                return ResponseEntity.status(HttpStatus.OK).body(response);
        }

        /**
         * Update a new RoleScope.
         */
        @Operation(summary = SwaggerConstants.UPDATE_ROLE_SCOPE_SUMMARY,
                        description = SwaggerConstants.UPDATE_ROLE_SCOPE_DESCRIPTION,
                        tags = {SwaggerConstants.ROLE_SCOPE_TAG})
        @PutMapping(path = ResourceEndpoint.PATH_ROLE_SCOPE_ID,
                        consumes = MediaType.APPLICATION_JSON_VALUE,
                        produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<?> update(@PathVariable Integer roleScopeId,
                        @Valid @RequestBody RoleScopeRequest request)
                        throws ResourceNotFoundException {
                log.debug("Received request to update role scope with request: {}", request);
                var roleScopeResponse = roleScopeService.updateRoleScope(roleScopeId, request);
                var response = new RestApiResponse(Translator.toLocale("operation.update"),
                                roleScopeResponse, true);
                log.debug("Role Scope update successfully: {}", response);
                return ResponseEntity.status(HttpStatus.OK).body(response);
        }

        /**
         * Delete RoleScope.
         */
        @Operation(summary = SwaggerConstants.DELETE_ROLE_SCOPE_SUMMARY,
                        description = SwaggerConstants.DELETE_ROLE_SCOPE_DESCRIPTION,
                        tags = {SwaggerConstants.ROLE_SCOPE_TAG})
        @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<?> delete(@RequestParam Integer roleScopeId)
                        throws ResourceNotFoundException {
                log.debug("Received request to delete role scope with id: {}", roleScopeId);
                roleScopeService.deleteRoleScope(roleScopeId);
                var response = new RestApiResponse(Translator.toLocale("operation.delete"), null,
                                true);
                log.debug("Role Scope delete successfully: {}", roleScopeId);
                return ResponseEntity.status(HttpStatus.OK).body(response);
        }

        /**
         * Fetch RoleScope by id.
         */
        @Operation(summary = SwaggerConstants.FETCH_ROLE_SCOPE_BY_ID_SUMMARY,
                        description = SwaggerConstants.FETCH_ROLE_SCOPE_BY_ID_DESCRIPTION,
                        tags = {SwaggerConstants.ROLE_SCOPE_TAG})
        @GetMapping(path = ResourceEndpoint.PATH_ROLE_SCOPE_ID,
                        produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<?> fetch(@PathVariable Integer roleScopeId)
                        throws ResourceNotFoundException {
                log.debug("Received request to fetch role scope with id: {}", roleScopeId);
                var roleScopeResponse = roleScopeService.fetchRoleScopeById(roleScopeId);
                var response = new RestApiResponse(Translator.toLocale("operation.get"),
                                roleScopeResponse, true);
                log.debug("Rol scope fetched successfully with id: {}", roleScopeId);
                return ResponseEntity.status(HttpStatus.OK).body(response);
        }

        /**
         * Fetch All Filtered RoleScope.
         */
        @Operation(summary = SwaggerConstants.FETCH_ROLE_SCOPE_SUMMARY,
                        description = SwaggerConstants.FETCH_ROLE_SCOPE_DESCRIPTION,
                        tags = {SwaggerConstants.ROLE_SCOPE_TAG})
        @PostMapping(path = ResourceEndpoint.ROLE_SCOPE_FILTER,
                        produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<?> fetchAll(@RequestBody GenericFilterRequest request) {
                log.debug("Received request to fetch all RoleScope");
                var languagesResponse = roleScopeService.fetchAllFilteredRoleScopes(request);
                var response = new RestApiResponse(Translator.toLocale("operation.getList"),
                                languagesResponse, true);
                log.debug("RoleScope fetched successfully");
                return ResponseEntity.status(HttpStatus.OK).body(response);
        }
}
