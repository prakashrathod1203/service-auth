package om.auth.library.model.dto.request.role;

import jakarta.validation.constraints.NotNull;
import om.auth.library.enums.RoleScopeEnum;

public record RoleScopeRequest(@NotNull(message = "{role.scope.type}") RoleScopeEnum roleScopeType,
        Boolean isDeleted) {
    public RoleScopeRequest(RoleScopeEnum roleScopeType) {
        this(roleScopeType, Boolean.FALSE);
    }
}
