package in.om.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import in.om.entities.record.UserResource;
import in.om.utility.CommonUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Prakash Rathod
 */
@ApiModel(description = "User Data Transfer Object")
@Data
public class UserDTO {

    @JsonProperty("externalUserId")
    @ApiModelProperty(notes = "externalUserId", example = "1001")
    private String externalUserId;

    @JsonProperty("phone")
    @ApiModelProperty(notes = "phone", example = "1234567890")
    private String phone;

    @JsonProperty("email")
    @ApiModelProperty(notes = "email", example = "test@gmail.com")
    private String email;

    @JsonProperty("firstName")
    @ApiModelProperty(notes = "firstName", example = "firstName")
    private String firstName;

    @JsonProperty("middleName")
    @ApiModelProperty(notes = "middleName", example = "middleName")
    private String middleName;

    @JsonProperty("lastName")
    @ApiModelProperty(notes = "lastName", example = "lastName")
    private String lastName;

    @JsonProperty("resource")
    @ApiModelProperty(notes = "resource")
    private UserResource resource;

    @Override
    public String toString() {
        return CommonUtils.objectToJsonString(this);
    }
}
