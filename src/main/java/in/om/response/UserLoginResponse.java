package in.om.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * User login response model.
 */
@ApiModel(description = "User Login Response")
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@AllArgsConstructor
public class UserLoginResponse {

    @ApiModelProperty(notes = "roles", example = "ADMIN")
    private String roles;

    @ApiModelProperty(notes = "Token", example = "slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6")
    private String token;

    @ApiModelProperty(notes = "userId", example = "01")
    private Long userId;
    @ApiModelProperty(notes = "userName", example = "test.test")
    private String userName;
}
