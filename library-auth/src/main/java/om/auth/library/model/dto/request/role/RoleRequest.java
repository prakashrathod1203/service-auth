package om.auth.library.model.dto.request.role;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record RoleRequest(@NotNull(message = "{role.roleScopeId.required}") Integer roleScopeId,
        @NotNull(message = "{role.organizationId.required}") Integer organizationId,
        @NotEmpty(message = "{role.name.required}") String name,
        @NotEmpty(message = "{role.title.required}") String title, String description,
        Boolean isDeleted) {
    public RoleRequest(Integer roleScopeId, Integer organizationId, String name, String title) {
        this(roleScopeId, organizationId, name, title, null, Boolean.FALSE);
    }
}
