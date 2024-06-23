package tech.sarthee.auth.model.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import tech.sarthee.auth.model.entity.identity.UserRoleIdentity;

@Data
@NoArgsConstructor
@Entity
@Table(name = "ca_user_role")
@ToString
@IdClass(UserRoleIdentity.class)
public class UserRoleEntity {
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
    @Column(name = "user_id")
    private String userId;

    @Id
    @Column(name = "role_id")
    private String roleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "organization_id", referencedColumnName = "organization_id", insertable = false, updatable = false),
            @JoinColumn(name = "group_id", referencedColumnName = "group_id", insertable = false, updatable = false),
            @JoinColumn(name = "sub_group_id", referencedColumnName = "sub_group_id", insertable = false, updatable = false),
            @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    })
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "organization_id", referencedColumnName = "organization_id", insertable = false, updatable = false),
            @JoinColumn(name = "group_id", referencedColumnName = "group_id", insertable = false, updatable = false),
            @JoinColumn(name = "sub_group_id", referencedColumnName = "sub_group_id", insertable = false, updatable = false),
            @JoinColumn(name = "role_id", referencedColumnName = "role_id", insertable = false, updatable = false)
    })
    private RoleEntity role;
}
