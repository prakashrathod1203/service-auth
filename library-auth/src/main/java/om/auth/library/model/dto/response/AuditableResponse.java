package om.auth.library.model.dto.response;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.ToString;
import om.auth.library.util.DateTimeUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public abstract class AuditableResponse {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateTimeUtils.DATE_TIME_FORMAT)
    private LocalDateTime createdDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateTimeUtils.DATE_TIME_FORMAT)
    private LocalDateTime modifiedDate;

    private Long createdById;
    private Long modifiedById;
    private String createdBy;
    private String modifiedBy;
    private Boolean isDeleted;
}
