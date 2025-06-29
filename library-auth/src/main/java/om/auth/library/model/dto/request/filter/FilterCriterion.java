package om.auth.library.model.dto.request.filter;

import java.util.List;
import lombok.Data;
import om.auth.library.enums.LogicEnum;
import om.auth.library.enums.OperatorEnum;

@Data
public class FilterCriterion {
    private String field;
    private List<String> values;
    private OperatorEnum operator = OperatorEnum.EQUALS;
    private LogicEnum logic = LogicEnum.AND;
}
