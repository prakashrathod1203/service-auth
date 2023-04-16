package in.om.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
public class ResponseBody {
    @ApiModelProperty(notes = "Response message")
    private String message;

    @ApiModelProperty(notes = "Result.")
    private Object result;

    @ApiModelProperty(notes = "Success or Fail.", example = "true")
    private Boolean success;

}
