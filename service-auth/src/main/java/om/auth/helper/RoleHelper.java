package om.auth.helper;

import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;
import om.auth.library.model.dto.response.role.RoleResponse;
import om.auth.library.model.dto.response.role.RoleScopeResponse;
import om.auth.library.util.CommonUtils;
import om.auth.model.entity.RoleEntity;


@Component
@Slf4j
public class RoleHelper {

    public RoleResponse convertRoleResponse(RoleEntity dbRoleEntity) {
        var response = CommonUtils.objectToPojoConverter(dbRoleEntity, RoleResponse.class);
        if (null != dbRoleEntity.getRoleScope()) {
            response.setRoleScope(CommonUtils.objectToPojoConverter(dbRoleEntity.getRoleScope(),
                    RoleScopeResponse.class));
        }
        return response;
    }

}
