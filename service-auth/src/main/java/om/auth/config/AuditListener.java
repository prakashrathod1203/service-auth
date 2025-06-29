package om.auth.config;

import java.time.LocalDateTime;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import om.auth.model.entity.Auditable;

public class AuditListener {
    @PrePersist
    public void setCreated(Object entity) {
        if (entity instanceof Auditable<?> auditable) {
            auditable.setCreatedDate(LocalDateTime.now());
            auditable.setCreatedById(UserContext.getCreatedById());
            auditable.setCreatedBy(UserContext.getCreatedBy());
            auditable.setModifiedDate(LocalDateTime.now());
            auditable.setModifiedById(UserContext.getModifiedById());
            auditable.setModifiedBy(UserContext.getModifiedBy());
        }
    }

    @PreUpdate
    public void setUpdated(Object entity) {
        if (entity instanceof Auditable<?> auditable) {
            auditable.setModifiedDate(LocalDateTime.now());
            auditable.setModifiedById(UserContext.getModifiedById());
            auditable.setModifiedBy(UserContext.getModifiedBy());
        }
    }
}
