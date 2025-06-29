package om.auth.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import om.auth.library.enums.RoleScopeEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Prakash Rathod
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Entity
@Table(name = "role_scope")
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoleScopeEntity extends Auditable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_scope_id")
    private Integer roleScopeId;

    @Column(name = "role_scope_type", nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private RoleScopeEnum roleScopeType;

}
