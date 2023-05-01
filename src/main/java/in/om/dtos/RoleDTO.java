package in.om.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import in.om.utility.CommonUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Prakash Rathod
 */
@ApiModel(description = "Role Data Transfer Object")
@Data
public class RoleDTO {

    @JsonProperty("name")
    @ApiModelProperty(notes = "name", example = "Supervisor")
    private String name;
    @JsonProperty("description")
    @ApiModelProperty(notes = "description", example = "Supervisor")
    private String description;

    @Override
    public String toString() {
        return CommonUtils.objectToJsonString(this);
    }
}
