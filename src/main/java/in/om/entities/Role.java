package in.om.entities;

import in.om.entities.identity.RoleIdentity;
import lombok.Data;

import javax.persistence.*;

/**
 * @author Prakash Rathod
 */
@Entity
@Table(name = "ca_role")
@Data
@IdClass(RoleIdentity.class)
public class Role {

    @Id @Column
    private String id;
    @Id @Column(name = "group_id")
    private String groupId;
    @Column
    private String name;
    @Column
    private String description;
}
