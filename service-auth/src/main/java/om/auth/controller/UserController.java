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
import om.auth.library.model.dto.request.user.UserRequest;
import om.auth.library.model.dto.response.RestApiResponse;
import om.auth.service.UserService;
import om.auth.util.Translator;

@Slf4j
@RestController
@RequestMapping(ResourceEndpoint.USER)
@RequiredArgsConstructor
public class UserController {

        private final UserService userService;

        /**
         * Create a new User.
         */
        @Operation(summary = SwaggerConstants.CREATE_USER_SUMMARY,
                        description = SwaggerConstants.CREATE_USER_DESCRIPTION,
                        tags = {SwaggerConstants.USER_TAG})
        @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
                        produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<?> create(@Valid @RequestBody UserRequest request)
                        throws ResourceAlreadyExistsException {
                log.debug("Received request to create user : {}", request);
                var userResponse = userService.createUser(request);
                var response = new RestApiResponse(Translator.toLocale("operation.insert"),
                                userResponse, true);
                log.debug("User created successfully: {}", response);
                return ResponseEntity.status(HttpStatus.OK).body(response);
        }

        /**
         * Update a new User.
         */
        @Operation(summary = SwaggerConstants.UPDATE_USER_SUMMARY,
                        description = SwaggerConstants.UPDATE_USER_DESCRIPTION,
                        tags = {SwaggerConstants.USER_TAG})
        @PutMapping(path = ResourceEndpoint.PATH_USER_ID,
                        consumes = MediaType.APPLICATION_JSON_VALUE,
                        produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<?> update(@PathVariable Long userId,
                        @Valid @RequestBody UserRequest request) throws ResourceNotFoundException {
                log.debug("Received request to update user with request: {}", request);
                var userResponse = userService.updateUser(userId, request);
                var response = new RestApiResponse(Translator.toLocale("operation.update"),
                                userResponse, true);
                log.debug("User update successfully: {}", response);
                return ResponseEntity.status(HttpStatus.OK).body(response);
        }

        /**
         * Delete User.
         */
        @Operation(summary = SwaggerConstants.DELETE_USER_SUMMARY,
                        description = SwaggerConstants.DELETE_USER_DESCRIPTION,
                        tags = {SwaggerConstants.USER_TAG})
        @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<?> delete(@RequestParam Long userId)
                        throws ResourceNotFoundException {
                log.debug("Received request to delete user with id: {}", userId);
                userService.deleteUser(userId);
                var response = new RestApiResponse(Translator.toLocale("operation.delete"), null,
                                true);
                log.debug("User delete successfully: {}", userId);
                return ResponseEntity.status(HttpStatus.OK).body(response);
        }

        /**
         * Fetch User by id.
         */
        @Operation(summary = SwaggerConstants.FETCH_USER_BY_ID_SUMMARY,
                        description = SwaggerConstants.FETCH_USER_BY_ID_DESCRIPTION,
                        tags = {SwaggerConstants.USER_TAG})
        @GetMapping(path = ResourceEndpoint.PATH_USER_ID,
                        produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<?> fetch(@PathVariable Long userId) throws ResourceNotFoundException {
                log.debug("Received request to fetch user with id: {}", userId);
                var userResponse = userService.fetchUserById(userId);
                var response = new RestApiResponse(Translator.toLocale("operation.get"),
                                userResponse, true);
                log.debug("User fetched successfully with id: {}", userId);
                return ResponseEntity.status(HttpStatus.OK).body(response);
        }

        /**
         * Fetch All Filtered User.
         */
        @Operation(summary = SwaggerConstants.FETCH_USER_SUMMARY,
                        description = SwaggerConstants.FETCH_USER_DESCRIPTION,
                        tags = {SwaggerConstants.USER_TAG})
        @PostMapping(path = ResourceEndpoint.USER_FILTER,
                        produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<?> fetchAll(@RequestBody GenericFilterRequest request) {
                log.debug("Received request to fetch all User");
                var languagesResponse = userService.fetchAllFilteredUsers(request);
                var response = new RestApiResponse(Translator.toLocale("operation.getList"),
                                languagesResponse, true);
                log.debug("User fetched successfully");
                return ResponseEntity.status(HttpStatus.OK).body(response);
        }
}
