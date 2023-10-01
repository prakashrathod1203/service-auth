package in.om.vos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import in.om.entities.record.UserResource;
import in.om.enums.StatusEnum;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Data;

/**
 * @author Prakash Rathod
 */
@ApiModel(description = "User Login Value Object")
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Builder
public class LoginVO {

    private String token;
    private UserResource resource;
}
