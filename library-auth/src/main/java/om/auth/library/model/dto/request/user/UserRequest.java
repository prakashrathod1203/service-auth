package om.auth.library.model.dto.request.user;

import java.util.List;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record UserRequest(@NotEmpty(message = "{user.loginId.required}") String loginId,
        @NotNull(message = "{user.organizationId.required}") Integer organizationId,
        String externalUserId, @NotEmpty(message = "{user.phone.required}") String phone,
        String email, @NotEmpty(message = "{user.password.required}") String password,
        @NotEmpty(message = "{user.firstName.required}") String firstName,
        @NotEmpty(message = "{user.lastName.required}") String lastName, String resource,
        List<Long> roleIds, List<Integer> tileIds, Boolean isDeleted) {
    public UserRequest(String loginId, Integer organizationId, String externalUserId, String phone,
            String email, String password, String firstName, String lastName, String resource,
            List<Long> roleIds, List<Integer> tileIds) {
        this(loginId, organizationId, externalUserId, phone, email, password, firstName, lastName,
                resource, roleIds, tileIds, Boolean.FALSE);
    }
}
