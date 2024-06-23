package tech.sarthee.auth.library.model.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.ToString;


@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class FileMasterResponse {

    private Long id;

    private String downloadUrl;

    private String entityName;
    private String entityId;

    private String fileName;
    private String fileType;
    private String fileStorePath;

}
