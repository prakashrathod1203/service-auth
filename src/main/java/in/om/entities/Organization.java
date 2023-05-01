package in.om.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Prakash Rathod
 */
@Entity
@Table(name = "ca_organization")
@Data
public class Organization {
    @Id
    private String id;

    @Column
    private String name;
}
