package tech.sarthee.auth.library.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.ToString;


@Data
@ToString
public class RoleRequest {
    @JsonProperty("organization_id")
    @NotBlank(message = "Organization Id is required")
    private String organizationId;
    @JsonProperty("group_id")
    @NotBlank(message = "Group Id is required")
    private String groupId;
    @JsonProperty("sub_group_id")
    @NotBlank(message = "Sub Group Id is required")
    private String subGroupId;
    @JsonProperty("role_id")
    @NotBlank(message = "Role Id is required")
    private String roleId;

    @JsonProperty("display_name")
    private String displayName;
    private String description;

    public String getOrganizationId() {
        return organizationId != null ? organizationId.toUpperCase() : null;
    }
    public String getGroupId() {
        return groupId != null ? groupId.toUpperCase() : null;
    }
    public String getSubGroupId() {
        return subGroupId != null ? subGroupId.toUpperCase() : null;
    }
    public String getRoleId() {
        return roleId != null ? roleId.toUpperCase() : null;
    }
}
