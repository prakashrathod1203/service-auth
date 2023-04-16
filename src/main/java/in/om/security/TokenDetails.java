package in.om.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Token details model.
 */
@Getter
@AllArgsConstructor
public class TokenDetails {

    private Integer id;

    private Long systemClientId;

    private String userName;
}
