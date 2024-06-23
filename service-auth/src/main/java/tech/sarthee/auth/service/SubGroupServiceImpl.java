package tech.sarthee.auth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.sarthee.auth.library.exception.ResourceAlreadyExistsException;
import tech.sarthee.auth.library.exception.ResourceNotFoundException;
import tech.sarthee.auth.library.model.dto.request.SubGroupRequest;
import tech.sarthee.auth.library.model.dto.response.SubGroupResponse;
import tech.sarthee.auth.library.util.CommonUtils;
import tech.sarthee.auth.model.entity.SubGroupEntity;
import tech.sarthee.auth.model.entity.identity.SubGroupIdentity;
import tech.sarthee.auth.repository.SubGroupRepository;
import tech.sarthee.auth.util.Translator;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubGroupServiceImpl implements SubGroupService {

    private final SubGroupRepository subGroupRepository;

    @Override
    public SubGroupResponse createSubGroup(SubGroupRequest request) throws ResourceAlreadyExistsException {
        log.debug("Creating SubGroup with request: {}", request);
        var subGroupIdentity = new SubGroupIdentity(request.getOrganizationId(), request.getGroupId(), request.getSubGroupId());
        var optionalSubGroupEntity = subGroupRepository.findById(subGroupIdentity);
        if(optionalSubGroupEntity.isPresent()) {
            log.error("SubGroup with id {} already exists", subGroupIdentity);
            throw new ResourceAlreadyExistsException(Translator.toLocale("operation.exists"));
        }

        var subGroupEntity = CommonUtils.objectToPojoConverter(request, SubGroupEntity.class);
        subGroupEntity = subGroupRepository.save(Objects.requireNonNull(subGroupEntity));
        log.debug("SubGroup created successfully with id: {}", subGroupIdentity);
        return CommonUtils.objectToPojoConverter(subGroupEntity, SubGroupResponse.class);
    }

    @Override
    public SubGroupResponse updateSubGroup(SubGroupIdentity id, SubGroupRequest request)  throws ResourceNotFoundException  {
        log.debug("Updating SubGroup with id: {} and request: {}", id, request);
        var dbSubGroupEntity = subGroupRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Translator.toLocale("error.resourceNotFound")));
        var subGroupEntity = CommonUtils.objectToPojoConverter(request, SubGroupEntity.class);
        Objects.requireNonNull(subGroupEntity).setOrganizationId(dbSubGroupEntity.getOrganizationId());
        Objects.requireNonNull(subGroupEntity).setGroupId(dbSubGroupEntity.getGroupId());
        Objects.requireNonNull(subGroupEntity).setSubGroupId(dbSubGroupEntity.getSubGroupId());
        subGroupEntity = subGroupRepository.save(subGroupEntity);
        log.debug("SubGroup updated successfully with id: {}", id);
        return CommonUtils.objectToPojoConverter(subGroupEntity, SubGroupResponse.class);
    }

    @Override
    public void deleteSubGroup(SubGroupIdentity id) throws ResourceNotFoundException {
        log.debug("Deleting SubGroup with id: {}", id);
        var subGroupEntity = subGroupRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Translator.toLocale("error.resourceNotFound")));
        subGroupRepository.delete(subGroupEntity);
        log.debug("SubGroup deleted successfully with id: {}", id);
    }

    @Override
    public SubGroupResponse fetchSubGroupById(SubGroupIdentity id) throws ResourceNotFoundException {
        log.debug("Fetching SubGroup with id: {}", id);
        var subGroupEntity = subGroupRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Translator.toLocale("error.resourceNotFound")));
        log.debug("SubGroup fetched successfully with id: {}", id);
        return CommonUtils.objectToPojoConverter(subGroupEntity, SubGroupResponse.class);
    }

    @Override
    public List<SubGroupResponse> fetchAllSubGroups(String organizationId, String subGroupId) {
        log.debug("Received request to get all SubGroups with organizationId: {}, subGroupId: {}", organizationId, subGroupId);
        var SubGroups = subGroupRepository.findByOrganizationIdAndGroupId(organizationId, subGroupId);
        log.debug("SubGroups fetched successfully with organizationId: {}, subGroupId: {}", organizationId, subGroupId);
        return CommonUtils.objectToPojoConverter(SubGroups, SubGroupResponse.class);
    }
}
