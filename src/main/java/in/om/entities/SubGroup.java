package in.om.entities;

import in.om.entities.identity.SubGroupIdentity;
import lombok.Data;

import javax.persistence.*;

/**
 * @author Prakash Rathod
 */
@Entity
@Table(name = "ca_sub_group")
@Data
@IdClass(SubGroupIdentity.class)
public class SubGroup {

    @Id @Column
    private String id;

    @Id @Column(name = "group_id")
    private String groupId;

    @Column
    private String name;
}
