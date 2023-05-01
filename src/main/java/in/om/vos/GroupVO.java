package in.om.vos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Prakash Rathod
 */
@ApiModel(description = "Group Value Object")
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class GroupVO {

    @ApiModelProperty(notes = "id", example = "TT")
    private String id;

    @ApiModelProperty(notes = "organizationId", example = "OM")
    private String organizationId;

    @ApiModelProperty(notes = "name", example = "Marble & Granites")
    private String name;
}
