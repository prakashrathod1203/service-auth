package in.om.security;

import in.om.entities.record.UserResource;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Token details model.
 */
@Getter
@AllArgsConstructor
public class TokenDetails {

    private String loginId;

    private UserResource resource;
}
