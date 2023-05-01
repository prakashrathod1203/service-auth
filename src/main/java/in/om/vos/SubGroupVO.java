package in.om.vos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Prakash Rathod
 */
@ApiModel(description = "SubGroup Value Object")
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class SubGroupVO {

    @ApiModelProperty(notes = "id", example = "NPDUSM")
    private String id;

    @ApiModelProperty(notes = "groupId", example = "DUSM")
    private String groupId;

    @ApiModelProperty(notes = "name", example = "Nani Paniyali Dudh Utpadak Sahakari Mandali")
    private String name;
}
