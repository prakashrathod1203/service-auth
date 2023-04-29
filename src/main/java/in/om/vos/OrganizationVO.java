package in.om.vos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Prakash Rathod
 */
@ApiModel(description = "Organization Value Object")
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class OrganizationVO {
    @ApiModelProperty(notes = "id", example = "OM")
    private String id;

    @ApiModelProperty(notes = "name", example = "OnsetMatrix")
    private String name;
}
