package om.auth.library.model.dto.request.filter;

import java.util.List;
import lombok.Data;

@Data
public class GenericFilterRequest {
    private List<FilterCriterion> filters;
    private int page = 0;
    private int size = 10;
    private List<SortRequest> sort;
}
