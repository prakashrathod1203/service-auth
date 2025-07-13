package om.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import om.auth.constant.SwaggerConstants;
import om.auth.library.constant.ResourceEndpoint;
import om.auth.library.exception.ResourceAlreadyExistsException;
import om.auth.library.exception.ResourceNotFoundException;
import om.auth.library.model.dto.request.filter.GenericFilterRequest;
import om.auth.library.model.dto.request.tile.TileRequest;
import om.auth.library.model.dto.response.RestApiResponse;
import om.auth.service.TileService;
import om.auth.util.Translator;

@Slf4j
@RestController
@RequestMapping(ResourceEndpoint.TILE)
@RequiredArgsConstructor
public class TileController {

        private final TileService tileService;

        /**
         * Create a new Tile.
         */
        @Operation(summary = SwaggerConstants.CREATE_TILE_SUMMARY,
                        description = SwaggerConstants.CREATE_TILE_DESCRIPTION,
                        tags = {SwaggerConstants.TILE_TAG})
        @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
                        produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<?> create(@Valid @RequestBody TileRequest request)
                        throws ResourceAlreadyExistsException {
                log.debug("Received request to create tile : {}", request);
                var tileResponse = tileService.createTile(request);
                var response = new RestApiResponse(Translator.toLocale("operation.insert"),
                                tileResponse, true);
                log.debug("Tile created successfully: {}", response);
                return ResponseEntity.status(HttpStatus.OK).body(response);
        }

        /**
         * Update a new Tile.
         */
        @Operation(summary = SwaggerConstants.UPDATE_TILE_SUMMARY,
                        description = SwaggerConstants.UPDATE_TILE_DESCRIPTION,
                        tags = {SwaggerConstants.TILE_TAG})
        @PutMapping(path = ResourceEndpoint.PATH_TILE_ID,
                        consumes = MediaType.APPLICATION_JSON_VALUE,
                        produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<?> update(@PathVariable Integer tileId,
                        @Valid @RequestBody TileRequest request) throws ResourceNotFoundException {
                log.debug("Received request to update tile with request: {}", request);
                var tileResponse = tileService.updateTile(tileId, request);
                var response = new RestApiResponse(Translator.toLocale("operation.update"),
                                tileResponse, true);
                log.debug("Tile update successfully: {}", response);
                return ResponseEntity.status(HttpStatus.OK).body(response);
        }

        /**
         * Delete Tile.
         */
        @Operation(summary = SwaggerConstants.DELETE_TILE_SUMMARY,
                        description = SwaggerConstants.DELETE_TILE_DESCRIPTION,
                        tags = {SwaggerConstants.TILE_TAG})
        @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<?> delete(@RequestParam Integer tileId)
                        throws ResourceNotFoundException {
                log.debug("Received request to delete tile with id: {}", tileId);
                tileService.deleteTile(tileId);
                var response = new RestApiResponse(Translator.toLocale("operation.delete"), null,
                                true);
                log.debug("Tile delete successfully: {}", tileId);
                return ResponseEntity.status(HttpStatus.OK).body(response);
        }

        /**
         * Fetch Tile by id.
         */
        @Operation(summary = SwaggerConstants.FETCH_TILE_BY_ID_SUMMARY,
                        description = SwaggerConstants.FETCH_TILE_BY_ID_DESCRIPTION,
                        tags = {SwaggerConstants.TILE_TAG})
        @GetMapping(path = ResourceEndpoint.PATH_TILE_ID,
                        produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<?> fetch(@PathVariable Integer tileId)
                        throws ResourceNotFoundException {
                log.debug("Received request to fetch tile with id: {}", tileId);
                var tileResponse = tileService.fetchTileById(tileId);
                var response = new RestApiResponse(Translator.toLocale("operation.get"),
                                tileResponse, true);
                log.debug("Tile fetched successfully with id: {}", tileId);
                return ResponseEntity.status(HttpStatus.OK).body(response);
        }

        /**
         * Fetch All Filtered Tile.
         */
        @Operation(summary = SwaggerConstants.FETCH_TILE_SUMMARY,
                        description = SwaggerConstants.FETCH_TILE_DESCRIPTION,
                        tags = {SwaggerConstants.TILE_TAG})
        @PostMapping(path = ResourceEndpoint.TILE_FILTER,
                        produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<?> fetchAll(@RequestBody GenericFilterRequest request) {
                log.debug("Received request to fetch all Tile");
                var languagesResponse = tileService.fetchAllFilteredTiles(request);
                var response = new RestApiResponse(Translator.toLocale("operation.getList"),
                                languagesResponse, true);
                log.debug("Tile fetched successfully");
                return ResponseEntity.status(HttpStatus.OK).body(response);
        }
}
