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
import tech.sarthee.auth.library.exception.ResourceNotFoundException;
import tech.sarthee.auth.library.model.dto.request.UserRequest;
import tech.sarthee.auth.library.model.dto.response.RestApiResponse;
import tech.sarthee.auth.library.model.dto.response.UserResponse;
import tech.sarthee.auth.service.UserService;
import tech.sarthee.auth.util.Translator;


@Slf4j
@RestController
@RequestMapping(ResourceEndpoint.USER)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * Create a new user.
     */
    @Operation(
            summary = SwaggerConstants.CREATE_USER_SUMMARY,
            description = SwaggerConstants.CREATE_USER_DESCRIPTION,
            tags = {SwaggerConstants.QR_MAPPING_AND_DETAIL_TAG}
    )
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> create(
            @Valid @RequestBody UserRequest userRequest
    ) {
        log.info("Received request to create user: {}", userRequest);
        UserResponse userResponse = userService.create(userRequest);
        RestApiResponse response = new RestApiResponse(Translator.toLocale("operation.insert"), userResponse, true);
        log.info("User created successfully: {}", response);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * Fetch user details by mobile number.
     */
    @Operation(
            summary = SwaggerConstants.FETCH_USER_DETAIL_BY_MOBILE_NO_SUMMARY,
            description = SwaggerConstants.FETCH_USER_DETAIL_BY_MOBILE_NO_DESCRIPTION,
            tags = {SwaggerConstants.QR_MAPPING_AND_DETAIL_TAG}
    )
    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> fetchUser(
            @RequestParam(value = "mobile_no", required = true) String mobileNo
    ) throws ResourceNotFoundException {
        log.info("Received request to fetch user with mobile number: {}", mobileNo);

        UserResponse userResponse = userService.fetch(mobileNo);
        RestApiResponse response = new RestApiResponse(Translator.toLocale("operation.get"), userResponse, true);
        log.info("User fetched successfully: {}", response);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
