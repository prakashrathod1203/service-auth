package om.auth.helper;

import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;
import om.auth.library.model.dto.response.user.UserResponse;
import om.auth.library.util.CommonUtils;
import om.auth.model.entity.UserEntity;


@Component
@Slf4j
public class UserHelper {

    public UserResponse convertUserResponse(UserEntity dbUserEntity) {
        var response = CommonUtils.objectToPojoConverter(dbUserEntity, UserResponse.class);
        return response;
    }

}
