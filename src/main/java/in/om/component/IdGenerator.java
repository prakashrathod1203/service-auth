package in.om.component;

import in.om.dtos.UserDTO;
import in.om.enums.GroupEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Prakash Rathod
 */
@Component
public class IdGenerator {

    public String getOrganizationId(String name) {
        return getWordFirstLetter(name);
    }

    public String getGroupId(String name) {
        return getWordFirstLetter(name);
    }

    public String getSubGroupId(String name) {
        return getWordFirstLetter(name);
    }

    public String getRoleId(String groupId, long count) {
        return String.format("%s|%d",groupId.toUpperCase(), count+1);
    }

    public String getLoginId(UserDTO userDTO) {
        StringBuilder loginIdSB = new StringBuilder();
        userDTO.getResource().getOrganizations().forEach(org -> {
            org.getGroups().forEach(group -> {
                group.getSubGroups().forEach(subGroup -> {
                    if(GroupEnum.DUSM.name().equalsIgnoreCase(group.getId())) {
                        loginIdSB.append(subGroup.getId().toLowerCase()).append(".").append(userDTO.getExternalUserId().toLowerCase());
                    } else {
                        loginIdSB.append(subGroup.getId().toLowerCase()).append(".").append(StringUtils.right(userDTO.getPhone(), 4));
                    }
                });
            });
        });
        return loginIdSB.toString();
    }

    private String getWordFirstLetter(String str) {
        Pattern pattern = Pattern.compile("\\b[a-zA-Z]");
        Matcher matcher = pattern.matcher(str);
        StringBuilder builder = new StringBuilder();
        while (matcher.find()) {
            builder.append(matcher.group());
        }
        return builder.toString().trim().toUpperCase();
    }
}
