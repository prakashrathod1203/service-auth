package tech.sarthee.auth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import tech.sarthee.auth.helper.UserHelper;
import tech.sarthee.auth.library.exception.ResourceNotFoundException;
import tech.sarthee.auth.library.model.dto.request.UserRequest;
import tech.sarthee.auth.library.model.dto.response.UserResponse;
import tech.sarthee.auth.repository.UserRepository;
import tech.sarthee.auth.model.entity.UserEntity;
import tech.sarthee.auth.library.util.CommonUtils;
import tech.sarthee.auth.util.Translator;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserHelper userHelper;

    @Override
    public UserResponse create(UserRequest userRequest) {
        log.debug("Starting user creation process with request: {}", userRequest);

        UserEntity userEntity = CommonUtils.objectToPojoConverter(userRequest, UserEntity.class);
        String loginId = userHelper.getLoginId(userEntity);
        log.debug("loginId : {}", loginId);
//        if(userRepository.findByLoginId(loginId).isPresent()) {
//            throw new ResourceNotFoundException(Translator.toLocale("operation.exists"));
//        }

        userEntity = userRepository.save(Objects.requireNonNull(userEntity));
        log.debug("Saved UserEntity to the repository: {}", userEntity);
        return CommonUtils.objectToPojoConverter(userEntity, UserResponse.class);
    }

    @Override
    public UserResponse update(String id, UserRequest userRequest) throws ResourceNotFoundException {
        return null;
    }

    @Override
    public UserResponse fetch(String mobileNo) throws ResourceNotFoundException {
//        log.debug("Fetching user with mobileNo: {}", mobileNo);
//        UserEntity userEntity = userRepository.findById(mobileNo)
//                .orElseThrow(() -> {
//                    log.error("User not found with mobileNo: {}", mobileNo);
//                    return new ResourceNotFoundException(Translator.toLocale("error.resourceNotFound"));
//                });
//
//        UserResponse userResponse = CommonUtils.objectToPojoConverter(userEntity, UserResponse.class);
//        log.debug("User fetched successfully with mobileNo: {}", mobileNo);
//        return userResponse;
        return null;
    }
}
