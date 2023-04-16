package in.om.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HistoryFilter {
    private Long startDate;
    private Long endDate;
	private Long userId;
}
