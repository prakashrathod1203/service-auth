package in.om.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="role")
@Getter
@Setter
@DynamicUpdate
public class Role implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="role_id")
	private Short roleId;
	
	@Column(name="name", length=35)
	@NotBlank(message="{role.validated.notblank.name}")
	@Length(max=35, message="{role.validated.len.name}")
	private String name;
	
	@Column(name="description", length=255)
	@NotBlank(message="{role.validated.notblank.description}")
	@Length(max=255, message="{role.validated.len.description}")
	private String description;
}