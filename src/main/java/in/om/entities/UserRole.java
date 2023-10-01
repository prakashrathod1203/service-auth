package in.om.entities;

import in.om.entities.identity.UserRoleIdentity;
import lombok.Data;

import javax.persistence.*;

/**
 * @author Prakash Rathod
 */
@Entity
@Table(name = "ca_user_role")
@Data
@IdClass(UserRoleIdentity.class)
public class UserRole {

    @Id @Column(name = "role_id")
    private String roleId;
    @Id @Column(name = "login_id")
    private String loginId;
}
