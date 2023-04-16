package in.om.payload;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@XmlRootElement(name="error")
@Setter
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class ErrorResponse {
	private String message;
	private Object errors;
	private int status;
}
