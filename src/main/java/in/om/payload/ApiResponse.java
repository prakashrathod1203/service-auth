package in.om.payload;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class ApiResponse {
    private boolean success;
    private String message;
    private String description;
	private int status;
	private Object data;
}
