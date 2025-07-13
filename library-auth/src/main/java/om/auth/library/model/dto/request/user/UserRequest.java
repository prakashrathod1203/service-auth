package om.auth.library.model.dto.request.user;

import java.util.List;

public record UserRequest(String loginId, Integer organizationId, String externalUserId,
        String phone, String email, String password, String firstName, String lastName,
        String resource, List<Long> roleIds, Boolean isDeleted) {
    public UserRequest(String loginId, Integer organizationId, String externalUserId, String phone,
            String email, String password, String firstName, String lastName, List<Long> roleIds) {
        this(loginId, organizationId, externalUserId, phone, email, password, firstName, lastName,
                "{}", roleIds, Boolean.FALSE);
    }
}
