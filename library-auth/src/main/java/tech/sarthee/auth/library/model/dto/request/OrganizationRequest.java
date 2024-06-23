package tech.sarthee.auth.library.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.ToString;


@Data
@ToString
public class OrganizationRequest {
    @JsonProperty("organization_id")
    @NotBlank(message = "Organization Id is required")
    private String organizationId;
    @JsonProperty("display_name")
    private String displayName;
    private String description;
    private String address;
    private String email;
    @JsonProperty("contact_number")
    private String contactNumber;

    public String getOrganizationId() {
        return organizationId != null ? organizationId.toUpperCase() : null;
    }
}
