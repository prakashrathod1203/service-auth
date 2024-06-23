package tech.sarthee.auth.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import tech.sarthee.auth.library.model.dto.resource.UserResource;
import tech.sarthee.auth.library.model.dto.resource.UserResourceJpaConverterJson;
import tech.sarthee.auth.model.entity.identity.UserIdentity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Entity
@Table(name = "ca_user")
@ToString
@IdClass(UserIdentity.class)
public class UserEntity extends Auditable<Long> {

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

    @Column(name = "external_user_id")
    private String externalUserId;

    @Column(name = "mobile_no")
    private String mobileNo;

    @Column(name = "alt_mobile")
    private String alternateMobile;

    @Column
    private String email;

    @Column
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name")
    private String lastName;

    @Column(columnDefinition = "json", name = "resource")
    @Convert(converter = UserResourceJpaConverterJson.class)
    private UserResource resource;

    @Column(name = "is_active")
    private Boolean isActive;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "organization_id", referencedColumnName = "organization_id", insertable = false, updatable = false),
            @JoinColumn(name = "group_id", referencedColumnName = "group_id", insertable = false, updatable = false),
            @JoinColumn(name = "sub_group_id", referencedColumnName = "sub_group_id", insertable = false, updatable = false)
    })
    private SubGroupEntity subGroup;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserRoleEntity> userRoles = new HashSet<>();
}
