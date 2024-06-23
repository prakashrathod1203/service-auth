package tech.sarthee.auth.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;


/**
 * @author Prakash Rathod
 */

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Entity
@Table(name = "ca_organization")
@ToString
public class OrganizationEntity extends Auditable<Long> {

    @Id
    @Column(name = "organization_id")
    private String organizationId;

    @Column(name = "display_name")
    private String displayName;

    @Column
    private String description;

    @Column
    private String address;

    @Column
    private String email;

    @Column(name = "contact_number")
    private String contactNumber;

    @OneToMany(mappedBy = "organization", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GroupEntity> groups;
}
