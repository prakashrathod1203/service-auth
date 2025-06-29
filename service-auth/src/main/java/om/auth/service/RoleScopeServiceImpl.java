package om.auth.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import om.auth.helper.DynamicSpecificationBuilder;
import om.auth.library.exception.ResourceAlreadyExistsException;
import om.auth.library.exception.ResourceNotFoundException;
import om.auth.library.model.dto.request.filter.GenericFilterRequest;
import om.auth.library.model.dto.request.role.RoleScopeRequest;
import om.auth.library.model.dto.response.role.RoleScopeResponse;
import om.auth.library.util.CommonUtils;
import om.auth.model.entity.RoleScopeEntity;
import om.auth.repository.RoleScopeRepository;
import om.auth.util.Translator;


@Slf4j
@Service
@RequiredArgsConstructor
public class RoleScopeServiceImpl implements RoleScopeService {

        private final RoleScopeRepository roleScopeRepository;

        @Override
        public RoleScopeResponse createRoleScope(RoleScopeRequest request)
                        throws ResourceAlreadyExistsException {
                log.debug("Creating RoleScope with request: {}", request);
                var roleScopeEntity =
                                CommonUtils.objectToPojoConverter(request, RoleScopeEntity.class);
                roleScopeEntity = roleScopeRepository.save(roleScopeEntity);
                log.debug("RoleScope created successfully with id: {}",
                                roleScopeEntity.getRoleScopeId());
                return CommonUtils.objectToPojoConverter(request, RoleScopeResponse.class);
        }

        @Override
        public RoleScopeResponse updateRoleScope(Integer id, RoleScopeRequest request)
                        throws ResourceNotFoundException {
                log.debug("Updating RoleScope with id: {} and request: {}", id, request);
                var dbRoleScopeEntity = roleScopeRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                Translator.toLocale("error.resourceNotFound")));
                var roleScopeEntity =
                                CommonUtils.objectToPojoConverter(request, RoleScopeEntity.class);
                roleScopeEntity.setRoleScopeId(dbRoleScopeEntity.getRoleScopeId());
                roleScopeEntity = roleScopeRepository.save(roleScopeEntity);
                log.debug("RoleScope updated successfully with id: {}",
                                roleScopeEntity.getRoleScopeId());
                return CommonUtils.objectToPojoConverter(roleScopeEntity, RoleScopeResponse.class);
        }

        @Override
        public void deleteRoleScope(Integer id) throws ResourceNotFoundException {
                log.debug("Deleting RoleScope with id: {}", id);
                var roleScopeEntity = roleScopeRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                Translator.toLocale("error.resourceNotFound")));
                roleScopeRepository.delete(roleScopeEntity);
                log.debug("RoleScope deleted successfully with id: {}", id);
        }

        @Override
        public RoleScopeResponse fetchRoleScopeById(Integer id) throws ResourceNotFoundException {
                log.debug("Fetching RoleScope with id: {}", id);
                var roleScopeEntity = roleScopeRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                Translator.toLocale("error.resourceNotFound")));
                log.debug("RoleScope fetched successfully with id: {}", id);
                return CommonUtils.objectToPojoConverter(roleScopeEntity, RoleScopeResponse.class);
        }

        @Override
        public Page<RoleScopeResponse> fetchAllFilteredRoleScopes(GenericFilterRequest request) {
                log.debug("Fetching languages with filters: {}, page: {}, size: {}, sort: {}",
                                request.getFilters(), request.getPage(), request.getSize(),
                                request.getSort());
                Pageable pageable = DynamicSpecificationBuilder.buildPageRequest(request.getPage(),
                                request.getSize(), request.getSort());
                Specification<RoleScopeEntity> spec =
                                DynamicSpecificationBuilder.build(request.getFilters());
                Page<RoleScopeEntity> page = roleScopeRepository.findAll(spec, pageable);
                log.debug("Fetched {} language records", page.getTotalElements());
                return page.map(entity -> CommonUtils.objectToPojoConverter(entity,
                                RoleScopeResponse.class));
        }
}
