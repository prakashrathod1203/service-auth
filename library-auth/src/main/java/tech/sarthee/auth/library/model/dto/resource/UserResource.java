package tech.sarthee.auth.library.model.dto.resource;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author Prakash Rathod
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResource implements Serializable {

    private List<Organization> organizations;
}
