package in.om.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FileResponse {
	private String name;
    private String downloadUri;
    private String type;
    private long size;
}
