package tech.sarthee.auth.library.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.ToString;


@Data
@ToString
public class SubGroupRequest {
    @JsonProperty("organization_id")
    @NotBlank(message = "Organization Id is required")
    private String organizationId;
    @JsonProperty("group_id")
    @NotBlank(message = "Group Id is required")
    private String groupId;
    @JsonProperty("sub_group_id")
    @NotBlank(message = "Sub Group Id is required")
    private String subGroupId;

    @JsonProperty("display_name")
    private String displayName;
    private String description;
    @JsonProperty("identity_code")
    private String identityCode;
    private String address;
    private String email;
    @JsonProperty("contact_number")
    private String contactNumber;

    public String getOrganizationId() {
        return organizationId != null ? organizationId.toUpperCase() : null;
    }

    public String getGroupId() {
        return groupId != null ? groupId.toUpperCase() : null;
    }
    public String getSubGroupId() {
        return subGroupId != null ? subGroupId.toUpperCase() : null;
    }
}
