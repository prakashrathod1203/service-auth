package in.om.vos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import in.om.entities.record.UserResource;
import in.om.enums.StatusEnum;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author Prakash Rathod
 */
@ApiModel(description = "User Value Object")
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class UserVO {

    private String loginId;
    private String externalUserId;
    private String phone;
    private String email;
    private String firstName;
    private String middleName;
    private String lastName;
    private UserResource resource;
    private StatusEnum isActive;
}
