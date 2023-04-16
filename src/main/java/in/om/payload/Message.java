package in.om.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Message {
    private String toUserName;
    private String fromUserName;
    private Object content;
    private String subject;
}
