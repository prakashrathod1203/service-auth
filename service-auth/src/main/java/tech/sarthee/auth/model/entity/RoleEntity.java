package tech.sarthee.auth.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import tech.sarthee.auth.model.entity.identity.RoleIdentity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * @author Prakash Rathod
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Entity
@Table(name = "ca_role")
@ToString
@IdClass(RoleIdentity.class)
public class RoleEntity extends Auditable<Long> {

    @Id
    @Column(name = "organization_id")
    private String organizationId;

    @Id
    @Column(name = "group_id")
    private String groupId;

    @Id
    @Column(name = "sub_group_id")
    private String subGroupId;

    @Id
    @Column(name = "role_id")
    private String roleId;

    @Column(name = "display_name")
    private String displayName;

    @Column
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "organization_id", referencedColumnName = "organization_id", insertable = false, updatable = false),
            @JoinColumn(name = "group_id", referencedColumnName = "group_id", insertable = false, updatable = false),
            @JoinColumn(name = "sub_group_id", referencedColumnName = "sub_group_id", insertable = false, updatable = false)
    })
    private SubGroupEntity subGroup;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserRoleEntity> userRoles = new HashSet<>();
}
