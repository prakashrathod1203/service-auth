package om.auth.service;

import org.springframework.data.domain.Page;
import om.auth.library.exception.ResourceAlreadyExistsException;
import om.auth.library.exception.ResourceNotFoundException;
import om.auth.library.model.dto.request.filter.GenericFilterRequest;
import om.auth.library.model.dto.request.user.UserRequest;
import om.auth.library.model.dto.response.user.UserResponse;

public interface UserService {

        UserResponse createUser(UserRequest request) throws ResourceAlreadyExistsException;

        UserResponse updateUser(Long id, UserRequest request) throws ResourceNotFoundException;

        void deleteUser(Long id) throws ResourceNotFoundException;

        UserResponse fetchUserById(Long id) throws ResourceNotFoundException;

        Page<UserResponse> fetchAllFilteredUsers(GenericFilterRequest request);
}
