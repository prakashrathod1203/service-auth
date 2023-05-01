package in.om.component;

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
