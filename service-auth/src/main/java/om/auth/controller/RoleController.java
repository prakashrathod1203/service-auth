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
import om.auth.library.model.dto.request.role.RoleRequest;
import om.auth.library.model.dto.response.RestApiResponse;
import om.auth.service.RoleService;
import om.auth.util.Translator;

@Slf4j
@RestController
@RequestMapping(ResourceEndpoint.ROLE)
@RequiredArgsConstructor
public class RoleController {

        private final RoleService roleService;

        /**
         * Create a new Role.
         */
        @Operation(summary = SwaggerConstants.CREATE_ROLE_SUMMARY,
                        description = SwaggerConstants.CREATE_ROLE_DESCRIPTION,
                        tags = {SwaggerConstants.ROLE_TAG})
        @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
                        produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<?> create(@Valid @RequestBody RoleRequest request)
                        throws ResourceAlreadyExistsException {
                log.debug("Received request to create role : {}", request);
                var roleResponse = roleService.createRole(request);
                var response = new RestApiResponse(Translator.toLocale("operation.insert"),
                                roleResponse, true);
                log.debug("Role created successfully: {}", response);
                return ResponseEntity.status(HttpStatus.OK).body(response);
        }

        /**
         * Update a new Role.
         */
        @Operation(summary = SwaggerConstants.UPDATE_ROLE_SUMMARY,
                        description = SwaggerConstants.UPDATE_ROLE_DESCRIPTION,
                        tags = {SwaggerConstants.ROLE_TAG})
        @PutMapping(path = ResourceEndpoint.PATH_ROLE_ID,
                        consumes = MediaType.APPLICATION_JSON_VALUE,
                        produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<?> update(@PathVariable Long roleId,
                        @Valid @RequestBody RoleRequest request) throws ResourceNotFoundException {
                log.debug("Received request to update role with request: {}", request);
                var roleResponse = roleService.updateRole(roleId, request);
                var response = new RestApiResponse(Translator.toLocale("operation.update"),
                                roleResponse, true);
                log.debug("Role update successfully: {}", response);
                return ResponseEntity.status(HttpStatus.OK).body(response);
        }

        /**
         * Delete Role.
         */
        @Operation(summary = SwaggerConstants.DELETE_ROLE_SUMMARY,
                        description = SwaggerConstants.DELETE_ROLE_DESCRIPTION,
                        tags = {SwaggerConstants.ROLE_TAG})
        @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<?> delete(@RequestParam Long roleId)
                        throws ResourceNotFoundException {
                log.debug("Received request to delete role with id: {}", roleId);
                roleService.deleteRole(roleId);
                var response = new RestApiResponse(Translator.toLocale("operation.delete"), null,
                                true);
                log.debug("Role delete successfully: {}", roleId);
                return ResponseEntity.status(HttpStatus.OK).body(response);
        }

        /**
         * Fetch Role by id.
         */
        @Operation(summary = SwaggerConstants.FETCH_ROLE_BY_ID_SUMMARY,
                        description = SwaggerConstants.FETCH_ROLE_BY_ID_DESCRIPTION,
                        tags = {SwaggerConstants.ROLE_TAG})
        @GetMapping(path = ResourceEndpoint.PATH_ROLE_ID,
                        produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<?> fetch(@PathVariable Long roleId) throws ResourceNotFoundException {
                log.debug("Received request to fetch role with id: {}", roleId);
                var roleResponse = roleService.fetchRoleById(roleId);
                var response = new RestApiResponse(Translator.toLocale("operation.get"),
                                roleResponse, true);
                log.debug("Role fetched successfully with id: {}", roleId);
                return ResponseEntity.status(HttpStatus.OK).body(response);
        }

        /**
         * Fetch All Filtered Role.
         */
        @Operation(summary = SwaggerConstants.FETCH_ROLE_SUMMARY,
                        description = SwaggerConstants.FETCH_ROLE_DESCRIPTION,
                        tags = {SwaggerConstants.ROLE_TAG})
        @PostMapping(path = ResourceEndpoint.ROLE_FILTER,
                        produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<?> fetchAll(@RequestBody GenericFilterRequest request) {
                log.debug("Received request to fetch all Role");
                var languagesResponse = roleService.fetchAllFilteredRoles(request);
                var response = new RestApiResponse(Translator.toLocale("operation.getList"),
                                languagesResponse, true);
                log.debug("Role fetched successfully");
                return ResponseEntity.status(HttpStatus.OK).body(response);
        }
}
