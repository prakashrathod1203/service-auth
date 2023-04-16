package in.om.payload;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Mail {
    private String to;
    private String subject;
    private String templateName;
    private Map<String, Object> model;
}
