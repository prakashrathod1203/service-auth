package om.auth.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import om.auth.model.entity.TileEntity;

@Repository
public interface TileRepository
                extends JpaRepository<TileEntity, Integer>, JpaSpecificationExecutor<TileEntity> {
        Optional<TileEntity> findByName(String name);
}
