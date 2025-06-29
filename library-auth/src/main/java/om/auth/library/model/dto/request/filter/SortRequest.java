package om.auth.library.model.dto.request.filter;

import lombok.Data;

@Data
public class SortRequest {
    private String field;
    private String direction = "asc";
}
