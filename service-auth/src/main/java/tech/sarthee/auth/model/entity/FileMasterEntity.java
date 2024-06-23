package tech.sarthee.auth.model.entity;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import tech.sarthee.auth.library.enums.EntityNameEnum;
import jakarta.persistence.*;
import lombok.Data;


@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "file_master")
@Data
@ToString
public class FileMasterEntity extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "entity_name", nullable = false)
    @Enumerated(EnumType.STRING)
    private EntityNameEnum entityName;

    @Basic
    @Column(name = "entity_id", nullable = false)
    private String entityId;

    @Column(name = "file_store_path", nullable = false)
    private String fileStorePath;

    @Column(name = "file_name", nullable = false)
    private String fileName;
    @Column(name = "file_type", nullable = false)
    private String fileType;

}