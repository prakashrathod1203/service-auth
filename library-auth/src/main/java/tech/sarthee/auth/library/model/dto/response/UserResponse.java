package tech.sarthee.auth.library.model.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class UserResponse {

    private String mobileNo;
    private String titlePrefix;
    private String firstName;
    private String lastName;
    private String designation;
    private String alternateMobile;
    private String email;
    private String company;
    private String website;
    private Boolean isCorporate;
    private Long optLogId;
}
