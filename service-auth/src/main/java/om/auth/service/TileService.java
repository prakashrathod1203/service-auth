package om.auth.service;

import org.springframework.data.domain.Page;
import om.auth.library.exception.ResourceAlreadyExistsException;
import om.auth.library.exception.ResourceNotFoundException;
import om.auth.library.model.dto.request.filter.GenericFilterRequest;
import om.auth.library.model.dto.request.tile.TileRequest;
import om.auth.library.model.dto.response.tile.TileResponse;

public interface TileService {

        TileResponse createTile(TileRequest request) throws ResourceAlreadyExistsException;

        TileResponse updateTile(Integer id, TileRequest request) throws ResourceNotFoundException;

        void deleteTile(Integer id) throws ResourceNotFoundException;

        TileResponse fetchTileById(Integer id) throws ResourceNotFoundException;

        Page<TileResponse> fetchAllFilteredTiles(GenericFilterRequest request);
}
