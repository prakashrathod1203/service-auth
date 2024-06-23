package tech.sarthee.auth.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import tech.sarthee.auth.model.entity.identity.GroupIdentity;

import java.util.List;


/**
 * @author Prakash Rathod
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Entity
@Table(name = "ca_group")
@ToString
@IdClass(GroupIdentity.class)
public class GroupEntity extends Auditable<Long> {

    @Id
    @Column(name = "organization_id")
    private String organizationId;

    @Id
    @Column(name = "group_id")
    private String groupId;

    @Column(name = "display_name")
    private String displayName;

    @Column
    private String description;

    @ManyToOne
    @JoinColumn(name = "organization_id", insertable = false, updatable = false)
    @JsonIgnore
    private OrganizationEntity organization;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SubGroupEntity> subGroups;
}
