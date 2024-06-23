package tech.sarthee.auth.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import tech.sarthee.auth.model.entity.identity.SubGroupIdentity;

import java.util.List;


/**
 * @author Prakash Rathod
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Entity
@Table(name = "ca_sub_group")
@ToString
@IdClass(SubGroupIdentity.class)
public class SubGroupEntity extends Auditable<Long>  {

    @Id
    @Column(name = "organization_id")
    private String organizationId;

    @Id
    @Column(name = "group_id")
    private String groupId;

    @Id
    @Column(name = "sub_group_id")
    private String subGroupId;

    @Column(name = "display_name")
    private String displayName;

    @Column(name = "identity_code")
    private String identityCode;

    @Column
    private String description;

    @Column
    private String address;

    @Column
    private String email;

    @Column(name = "contact_number")
    private String contactNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "organization_id", referencedColumnName = "organization_id", insertable = false, updatable = false),
            @JoinColumn(name = "group_id", referencedColumnName = "group_id", insertable = false, updatable = false)
    })
    @JsonIgnore
    private GroupEntity group;

    @OneToMany(mappedBy = "subGroup", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RoleEntity> roles;

    @JsonIgnore
    @OneToMany(mappedBy = "subGroup", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserEntity> users;
}
