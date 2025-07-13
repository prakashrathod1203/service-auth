package om.auth.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import om.auth.helper.DynamicSpecificationBuilder;
import om.auth.helper.TileHelper;
import om.auth.library.exception.ResourceAlreadyExistsException;
import om.auth.library.exception.ResourceNotFoundException;
import om.auth.library.model.dto.request.filter.GenericFilterRequest;
import om.auth.library.model.dto.request.tile.TileRequest;
import om.auth.library.model.dto.response.tile.TileResponse;
import om.auth.library.util.CommonUtils;
import om.auth.model.entity.TileEntity;
import om.auth.repository.TileRepository;
import om.auth.util.Translator;

@Slf4j
@Service
@RequiredArgsConstructor
public class TileServiceImpl implements TileService {

        private final TileRepository tileRepository;
        private final TileHelper tileHelper;

        @Override
        public TileResponse createTile(TileRequest request) throws ResourceAlreadyExistsException {
                log.debug("Creating Tile with request: {}", request);
                var optionalTileEntity = tileRepository.findByName(request.name());
                if (optionalTileEntity.isPresent()) {
                        log.error("Tile with name {} already exists", request.name());
                        throw new ResourceAlreadyExistsException(
                                        Translator.toLocale("operation.exists"));
                }
                var tileEntity = CommonUtils.objectToPojoConverter(request, TileEntity.class);
                tileEntity = tileRepository.save(tileEntity);
                log.debug("Tile created successfully with id: {}", tileEntity.getTileId());
                return tileHelper.convertTileResponse(tileEntity);
        }

        @Override
        public TileResponse updateTile(Integer id, TileRequest request)
                        throws ResourceNotFoundException {
                log.debug("Updating Tile with id: {} and request: {}", id, request);
                var dbTileEntity = tileRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                Translator.toLocale("error.resourceNotFound")));
                var tileEntity = CommonUtils.objectToPojoConverter(request, TileEntity.class);
                tileEntity.setTileId(dbTileEntity.getTileId());
                tileEntity.setName(dbTileEntity.getName());
                tileEntity = tileRepository.save(tileEntity);
                log.debug("Tile updated successfully with id: {}", tileEntity.getTileId());
                return tileHelper.convertTileResponse(tileEntity);
        }

        @Override
        public void deleteTile(Integer id) throws ResourceNotFoundException {
                log.debug("Deleting Tile with id: {}", id);
                var tileEntity = tileRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                Translator.toLocale("error.resourceNotFound")));
                tileRepository.delete(tileEntity);
                log.debug("Tile deleted successfully with id: {}", id);
        }

        @Override
        public TileResponse fetchTileById(Integer id) throws ResourceNotFoundException {
                log.debug("Fetching Tile with id: {}", id);
                var tileEntity = tileRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                Translator.toLocale("error.resourceNotFound")));
                log.debug("Tile fetched successfully with id: {}", id);
                return tileHelper.convertTileResponse(tileEntity);
        }

        @Override
        public Page<TileResponse> fetchAllFilteredTiles(GenericFilterRequest request) {
                log.debug("Fetching tiles with filters: {}, page: {}, size: {}, sort: {}",
                                request.getFilters(), request.getPage(), request.getSize(),
                                request.getSort());
                Pageable pageable = DynamicSpecificationBuilder.buildPageRequest(request.getPage(),
                                request.getSize(), request.getSort());
                Specification<TileEntity> spec =
                                DynamicSpecificationBuilder.build(request.getFilters());
                Page<TileEntity> page = tileRepository.findAll(spec, pageable);
                log.debug("Fetched {} tile records", page.getTotalElements());
                return page.map(entity -> tileHelper.convertTileResponse(entity));
        }
}
