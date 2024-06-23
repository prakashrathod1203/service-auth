package tech.sarthee.auth.service;

import tech.sarthee.auth.library.exception.ResourceNotFoundException;
import tech.sarthee.auth.library.model.dto.request.UserRequest;
import tech.sarthee.auth.library.model.dto.response.UserResponse;

public interface UserService {
    UserResponse create(UserRequest userRequest);
    UserResponse update(String id, UserRequest userRequest) throws ResourceNotFoundException;

    UserResponse fetch(String mobileNo) throws ResourceNotFoundException;
}
