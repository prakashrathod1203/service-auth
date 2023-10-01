package in.om.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * User Login request model.
 */
@ApiModel(description = "User Login Request")
@Data
public class UserLoginRequest {
    @JsonProperty("password")
    @ApiModelProperty(notes = "Password", example = "")
    private String password;

    @JsonProperty("username")
    @ApiModelProperty(notes = "User Name", example = "mis.ap")
    private String username;
}
