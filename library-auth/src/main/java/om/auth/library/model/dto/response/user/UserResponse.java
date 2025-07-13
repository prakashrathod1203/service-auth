package om.auth.library.model.dto.response.user;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import om.auth.library.model.dto.response.AuditableResponse;
import om.auth.library.model.dto.response.role.RoleResponse;
import om.auth.library.model.dto.response.tile.TileResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode(callSuper = false)
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class UserResponse extends AuditableResponse {
    Long userId;
    String loginId;
    Integer organizationId;
    String externalUserId;
    String phone;
    String email;
    String firstName;
    String lastName;
    String resource;
    List<Long> roleIds;
    LocalDateTime lastLoginAt;
    List<RoleResponse> roles;
    List<TileResponse> tiles;
}
