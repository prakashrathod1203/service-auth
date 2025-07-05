package om.auth.library.model.dto.response.role;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode(callSuper = false)
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class PermissionResponse {
    private Long permissionId;
    private String name;
    private String resourceName;
    private String title;
    private String description;
}
