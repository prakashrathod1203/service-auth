package tech.sarthee.auth.model.entity.identity;

import com.fasterxml.jackson.annotation.JsonIgnoreType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Prakash Rathod
 */
@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreType
public class SubGroupIdentity implements Serializable {
    private String organizationId;
    private String groupId;
    private String subGroupId;

    @Override
    public String toString() {
        return "{ organizationId: " + organizationId
                + ", groupId: " + groupId
                + ", subGroupId: " + subGroupId
                + " }";
    }
}
