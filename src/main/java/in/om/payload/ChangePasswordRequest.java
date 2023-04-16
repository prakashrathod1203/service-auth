package in.om.payload;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordRequest {

    private String oldPassword;
	
    @NotBlank
    private String newPassword;
    
    @NotBlank
    private String confirmPassword;
}
