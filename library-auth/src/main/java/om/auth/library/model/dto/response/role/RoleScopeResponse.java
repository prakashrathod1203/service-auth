package om.auth.library.model.dto.response.role;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import om.auth.library.enums.RoleScopeEnum;
import om.auth.library.model.dto.response.AuditableResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode(callSuper = false)
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class RoleScopeResponse extends AuditableResponse {
    private Integer roleScopeId;
    private RoleScopeEnum roleScopeType;
}
