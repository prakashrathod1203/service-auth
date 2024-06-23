package tech.sarthee.auth.library.model.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class RoleResponse {
    private String organizationId;
    private String groupId;
    private String subGroupId;
    private String roleId;
    private String displayName;
    private String description;
}
