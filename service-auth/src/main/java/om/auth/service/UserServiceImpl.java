package om.auth.service;

import java.util.Collections;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import om.auth.helper.DynamicSpecificationBuilder;
import om.auth.helper.UserHelper;
import om.auth.library.exception.ResourceAlreadyExistsException;
import om.auth.library.exception.ResourceNotFoundException;
import om.auth.library.model.dto.request.filter.GenericFilterRequest;
import om.auth.library.model.dto.request.user.UserRequest;
import om.auth.library.model.dto.response.user.UserResponse;
import om.auth.library.util.CommonUtils;
import om.auth.model.entity.RoleEntity;
import om.auth.model.entity.UserEntity;
import om.auth.repository.RoleRepository;
import om.auth.repository.UserRepository;
import om.auth.util.Translator;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

        private final UserRepository userRepository;
        private final RoleRepository roleRepository;
        private final UserHelper userHelper;

        @Override
        public UserResponse createUser(UserRequest request) throws ResourceAlreadyExistsException {
                log.debug("Creating User with request: {}", request);
                var optionalUserEntity = userRepository.findByLoginId(request.loginId());
                if (optionalUserEntity.isPresent()) {
                        log.error("User with loginId {} already exists", request.loginId());
                        throw new ResourceAlreadyExistsException(
                                        Translator.toLocale("operation.exists"));
                }
                var userEntity = CommonUtils.objectToPojoConverter(request, UserEntity.class);
                userEntity.setRoles(fetchRolesByIds(request.roleIds()));
                userEntity.setResource("{}");

                userEntity = userRepository.save(userEntity);
                log.debug("User created successfully with id: {}", userEntity.getUserId());
                return userHelper.convertUserResponse(userEntity);
        }

        @Override
        public UserResponse updateUser(Long id, UserRequest request)
                        throws ResourceNotFoundException {
                log.debug("Updating User with id: {} and request: {}", id, request);
                var dbUserEntity = userRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                Translator.toLocale("error.resourceNotFound")));
                var userEntity = CommonUtils.objectToPojoConverter(request, UserEntity.class);
                userEntity.setUserId(dbUserEntity.getUserId());
                userEntity.setLoginId(dbUserEntity.getLoginId());
                if (request.roleIds() != null && !request.roleIds().isEmpty()) {
                        List<RoleEntity> roleEntities = request.roleIds().stream().map(roleId -> {
                                var roleEntity = roleRepository.findById(roleId);
                                if (roleEntity.isPresent()) {
                                        return roleEntity.get();
                                } else {
                                        log.warn("Role with id {} not found, skipping", roleId);
                                        return null;
                                }
                        }).filter(role -> role != null).toList();
                        userEntity.setRoles(roleEntities);
                }

                var dbUpdatedUserEntity = userRepository.save(userEntity);
                log.debug("User updated successfully with id: {}", dbUpdatedUserEntity.getUserId());
                return userHelper.convertUserResponse(dbUpdatedUserEntity);
        }

        @Override
        public void deleteUser(Long id) throws ResourceNotFoundException {
                log.debug("Deleting User with id: {}", id);
                var userEntity = userRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                Translator.toLocale("error.resourceNotFound")));
                userRepository.delete(userEntity);
                log.debug("User deleted successfully with id: {}", id);
        }

        @Override
        public UserResponse fetchUserById(Long id) throws ResourceNotFoundException {
                log.debug("Fetching User with id: {}", id);
                var userEntity = userRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                Translator.toLocale("error.resourceNotFound")));
                log.debug("User fetched successfully with id: {}", id);
                return userHelper.convertUserResponse(userEntity);
        }

        @Override
        public Page<UserResponse> fetchAllFilteredUsers(GenericFilterRequest request) {
                log.debug("Fetching Users with filters: {}, page: {}, size: {}, sort: {}",
                                request.getFilters(), request.getPage(), request.getSize(),
                                request.getSort());
                Pageable pageable = DynamicSpecificationBuilder.buildPageRequest(request.getPage(),
                                request.getSize(), request.getSort());
                Specification<UserEntity> spec =
                                DynamicSpecificationBuilder.build(request.getFilters());
                Page<UserEntity> page = userRepository.findAll(spec, pageable);
                log.debug("Fetched {} User records", page.getTotalElements());
                return page.map(entity -> userHelper.convertUserResponse(entity));
        }

        private List<RoleEntity> fetchRolesByIds(List<Long> roleIds) {
                if (roleIds == null || roleIds.isEmpty())
                        return Collections.emptyList();
                return roleRepository.findAllById(roleIds);
        }


}
