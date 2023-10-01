package in.om.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import in.om.entities.record.UserResource;
import in.om.entities.record.UserResourceJpaConverterJson;
import in.om.enums.StatusEnum;
import lombok.Data;

import javax.persistence.*;

/**
 * @author Prakash Rathod
 */
@Entity
@Table(name = "ca_user")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    @Id
    @Column(name = "login_id")
    private String loginId;

    @Column(name = "external_user_id")
    private String externalUserId;
    @Column
    private String phone;
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
    @Enumerated(EnumType.STRING)
    private StatusEnum isActive;
}
