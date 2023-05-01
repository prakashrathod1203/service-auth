package in.om.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Prakash Rathod
 */
//@Entity
//@Table(name = "co_user")
//@Data
public class User {
    @Id
    private String id;

    @Column(name = "sub_group_id")
    private String subGroupId;
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
    @Column(name = "resources")
    private String resources;
}
