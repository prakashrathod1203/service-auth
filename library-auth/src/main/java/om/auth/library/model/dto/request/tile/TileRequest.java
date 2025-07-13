package om.auth.library.model.dto.request.tile;

import jakarta.validation.constraints.NotEmpty;

public record TileRequest(@NotEmpty(message = "{tile.name.required}") String name,
        @NotEmpty(message = "{tile.title.required}") String title, Boolean isDeleted) {
    public TileRequest(String name, String title) {
        this(name, title, Boolean.FALSE);
    }
}
