package om.auth.model.entity;

import java.time.LocalDateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import om.auth.config.AuditListener;

@MappedSuperclass
@EntityListeners(AuditListener.class)
@Getter
@Setter
public class Auditable<U> {
    @CreatedDate
    @Column(name = "created_date", updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;

    @Column(name = "created_by_id", nullable = true, updatable = false)
    private Long createdById;

    @Column(name = "modified_by_id", nullable = true)
    private Long modifiedById;

    @Column(name = "created_by", nullable = true, length = 255, updatable = false)
    private String createdBy;

    @Column(name = "modified_by", nullable = true, length = 255)
    private String modifiedBy;

    @Column(name = "is_deleted")
    private Boolean isDeleted = false;
}
