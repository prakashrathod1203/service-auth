package in.om.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataFilter {
	private Integer firstResult;
	private Integer maxResults;
	private String sortField;
	private String sortDirection;
	private String searchValue;
}
