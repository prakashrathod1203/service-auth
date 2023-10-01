package in.om.entities;

import in.om.entities.identity.GroupIdentity;
import lombok.Data;

import javax.persistence.*;

/**
 * @author Prakash Rathod
 */
@Entity
@Table(name = "ca_group")
@Data
@IdClass(GroupIdentity.class)
public class Group {

    @Id @Column
    private String id;

    @Id @Column(name = "org_id")
    private String organizationId;

    @Column
    private String name;
}
