package in.om.payload;

import in.om.utility.ApplicationConstants;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponse {
    private String accessToken;
    private String tokenType = ApplicationConstants.TOKEN_PREFIX;

    public AuthResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}
