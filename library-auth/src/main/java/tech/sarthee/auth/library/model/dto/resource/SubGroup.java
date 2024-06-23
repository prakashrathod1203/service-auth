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
public class SubGroup implements Serializable {
    private String id;
    private String name;
    private List<String> roles;
}
