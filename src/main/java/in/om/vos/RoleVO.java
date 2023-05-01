package in.om.vos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Prakash Rathod
 */
@ApiModel(description = "Role Value Object")
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class RoleVO {

    @ApiModelProperty(notes = "id", example = "DUSM|1")
    private String id;

    @ApiModelProperty(notes = "groupId", example = "DUSM")
    private String groupId;

    @ApiModelProperty(notes = "name", example = "Supervisor")
    private String name;

    @ApiModelProperty(notes = "description", example = "Supervisor")
    private String description;
}
