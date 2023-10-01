package in.om.entities.identity;

import com.fasterxml.jackson.annotation.JsonIgnoreType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * @author Prakash Rathod
 */
@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreType
public class UserRoleIdentity implements Serializable {

    private String roleId;
    private String loginId;

    @Override
    public String toString() {
        return "[ roleId='" + roleId + '\'' + ", loginId='" + loginId + '\'' + ']';
    }
}
