package om.auth.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Prakash Rathod
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Entity
@Table(name = "tile")
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class TileEntity extends Auditable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tile_id")
    private Integer tileId;

    @Column(name = "name", nullable = false, unique = true, length = 255)
    private String name;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

}
