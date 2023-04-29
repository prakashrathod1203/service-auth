package in.om.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author Prakash Rathod
 */
@ApiModel(description = "Organization Data Transfer Object")
@Data
@ToString
public class OrganizationDTO {
    @JsonProperty("name")
    @ApiModelProperty(notes = "name", example = "OnsetMatrix")
    private String name;
}
