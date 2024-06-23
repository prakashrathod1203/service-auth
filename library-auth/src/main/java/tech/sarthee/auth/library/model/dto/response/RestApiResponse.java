package tech.sarthee.auth.library.model.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
@ToString
public class RestApiResponse {
    private String message;
    private Object result;
    private Boolean success;
}
