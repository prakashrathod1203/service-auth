package in.om.helper;

import in.om.entities.User;
import in.om.utility.CommonUtils;
import in.om.vos.UserVO;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Prakash Rathod
 */
@Component
public class UserHelper {

    public UserVO getUserVO(User user) {
        return CommonUtils.objectToPojoConverter(user, UserVO.class);
    }

    public List<UserVO> getUserVOList(List<User> users) {
        return CommonUtils.objectToPojoConverter(users, UserVO.class);
    }
}
