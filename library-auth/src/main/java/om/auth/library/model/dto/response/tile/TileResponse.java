package om.auth.library.model.dto.response.tile;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import om.auth.library.model.dto.response.AuditableResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;


@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode(callSuper = false)
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class TileResponse extends AuditableResponse {
    private Integer tileId;
    private String name;
    private String title;
}
