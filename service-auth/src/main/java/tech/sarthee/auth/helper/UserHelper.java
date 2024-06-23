package tech.sarthee.auth.helper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import tech.sarthee.auth.library.model.dto.request.UserRequest;
import tech.sarthee.auth.model.entity.UserEntity;


@Component
@Slf4j
public class UserHelper {

    public String getLoginId(UserEntity userEntity) {
        StringBuilder loginIdSB = new StringBuilder();
        userEntity.getResource().getOrganizations().forEach(org -> {
            org.getGroups().forEach(group -> {
                group.getSubGroups().forEach(subGroup -> {
//                    if(GroupEnum.DUSM.name().equalsIgnoreCase(group.getId())) {
//                        loginIdSB.append(subGroup.getId().toLowerCase()).append(".").append(userDTO.getExternalUserId().toLowerCase());
//                    } else {
//                        loginIdSB.append(subGroup.getId().toLowerCase()).append(".").append(StringUtils.right(userDTO.getPhone(), 4));
//                    }
                });
            });
        });
        return loginIdSB.toString();
    }

}
