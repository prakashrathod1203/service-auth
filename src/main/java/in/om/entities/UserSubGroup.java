package in.om.entities;

import in.om.entities.identity.UserSubGroupIdentity;
import lombok.Data;

import javax.persistence.*;

/**
 * @author Prakash Rathod
 */
@Entity
@Table(name = "ca_user_sub_group")
@Data
@IdClass(UserSubGroupIdentity.class)
public class UserSubGroup {

    @Id @Column(name = "sub_group_id")
    private String subGroupId;
    @Id @Column(name = "login_id")
    private String loginId;
}
