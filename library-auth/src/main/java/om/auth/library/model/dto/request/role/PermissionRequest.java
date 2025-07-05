package om.auth.library.model.dto.request.role;

import jakarta.validation.constraints.NotEmpty;

public record PermissionRequest(@NotEmpty(message = "{role.permission.name.required}") String name,
        @NotEmpty(message = "{role.permission.resourceName.required}") String resourceName,
        @NotEmpty(message = "{role.permission.title.required}") String title, String description) {
}
