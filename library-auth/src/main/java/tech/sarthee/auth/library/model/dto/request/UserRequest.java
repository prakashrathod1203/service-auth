package tech.sarthee.auth.library.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import tech.sarthee.auth.library.model.dto.resource.UserResource;

@Data
@AllArgsConstructor
@ToString
public class UserRequest {

    @JsonProperty("external_user_id")
    private String externalUserId;

    @JsonProperty("mobile_no")
    private String mobileNo;

    @JsonProperty("alternate_mobile")
    private String alternateMobile;

    @JsonProperty("email")
    private String email;

    @JsonProperty("password")
    private String password;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("middle_name")
    private String middleName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("resource")
    private UserResource resource;

    @JsonProperty("is_active")
    private Boolean isActive;
}
