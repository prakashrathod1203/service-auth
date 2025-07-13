package om.auth.helper;

import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;
import om.auth.library.model.dto.response.tile.TileResponse;
import om.auth.library.util.CommonUtils;
import om.auth.model.entity.TileEntity;


@Component
@Slf4j
public class TileHelper {

    public TileResponse convertTileResponse(TileEntity dbTileEntity) {
        var response = CommonUtils.objectToPojoConverter(dbTileEntity, TileResponse.class);
        return response;
    }

}
