package in.om.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreType;
import in.om.utility.CommonUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * @author Prakash Rathod
 */
@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreType
public class GroupIdentity implements Serializable {
    private String organizationId;
    private String id;

    @Override
    public String toString() {
        return CommonUtils.objectToJsonString(this);
    }
}
