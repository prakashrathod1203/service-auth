package in.om.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.transaction.Transactional;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"phone"})
})
@Getter
@Setter
@DynamicUpdate
@Transactional
public class User extends Auditable<String> {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id")
    private Long userId;
	
    @Column(name = "user_name", length=100)
    @Email(message="{user.validated.email.username}")
    private String username;

    @Length(max=150, message="{user.validated.len.password}")
	@Column(name = "password", length = 150)
    private String password;
    
    @Column(name = "first_name",  length = 30)
	@Length(max=30, min=3, message="{user.validated.len.first.name}")
	@NotBlank(message="{user.validated.notblank.first.name}")
    private String firstName;
    
    @Column(name = "middle_name", length = 30)
	@Length(max=30, message="{user.validated.len.middle.name}")
	protected String middleName;
    
    @Column(name = "last_name", length = 30)
	@Length(max=30, message="{user.validated.len.last.name}")
	@NotBlank(message="{user.validated.notblank.last.name}")
    private String lastName;
    
    @Column(name = "phone", length = 13, unique = true)
    @Pattern(regexp = "^$|((\\+)?1?[\\s-]?[0-9]{3}[\\s-]?[0-9]{3}[\\s-]?[0-9]{4})", message="{user.validated.regex.phone}")
    private String phone;

	@ApiModelProperty(hidden = true)
	@Column(name = "last_login_date_time")
	protected Date lastLoginDateTime;

	@ApiModelProperty(hidden = true)
	@Column(name = "login_attempts", columnDefinition = "BIGINT")
	protected Short loginAttempts = 0;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_to_role", 
		joinColumns = { @JoinColumn(name = "user_id") }, 
		inverseJoinColumns = { @JoinColumn(name = "role_id") 
	})
	@BatchSize(size = 100)
	protected Set<Role> roles;
	
	@Column(name = "active", columnDefinition = "BIT")
	protected Boolean active = Boolean.TRUE;
    
    @ApiModelProperty(hidden = true)
    @Transient protected String fullName;
    
    @ApiModelProperty(hidden = true)
	@Transient protected String shortName;
	
	@ApiModelProperty(hidden = true)
	@Transient protected Boolean isAdmin;
	
	// == helpers == //

	public String getFullName() {
		fullName = getLastName() + " " + getFirstName() + " " + (getMiddleName() == null ? "" : getMiddleName()) ;
		return fullName;
	}

	public String getShortName() {
		shortName = getLastName() + " " + getFirstName();
		return shortName;
	}
	
	
	public User(){}
	public User(String lastName, Long userId) {
		this.lastName = lastName;
		this.userId = userId;
	}
	
}
