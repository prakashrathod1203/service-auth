package in.om.helper;

import in.om.entities.Role;
import in.om.utility.CommonUtils;
import in.om.vos.RoleVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Prakash Rathod
 */
@Component
@RequiredArgsConstructor
public class RoleHelper {

    public RoleVO getRoleVO(Role role) {
        return CommonUtils.objectToPojoConverter(role, RoleVO.class);
    }
    public List<RoleVO> getRoleVOList(List<Role> roles) {
        return CommonUtils.objectToPojoConverter(roles, RoleVO.class);
    }
}
