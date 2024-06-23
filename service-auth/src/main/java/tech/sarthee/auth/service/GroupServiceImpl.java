package tech.sarthee.auth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.sarthee.auth.library.exception.ResourceAlreadyExistsException;
import tech.sarthee.auth.library.exception.ResourceNotFoundException;
import tech.sarthee.auth.library.model.dto.request.GroupRequest;
import tech.sarthee.auth.library.model.dto.response.GroupResponse;
import tech.sarthee.auth.library.util.CommonUtils;
import tech.sarthee.auth.model.entity.GroupEntity;
import tech.sarthee.auth.model.entity.identity.GroupIdentity;
import tech.sarthee.auth.repository.GroupRepository;
import tech.sarthee.auth.util.Translator;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;

    @Override
    public GroupResponse createGroup(GroupRequest request) throws ResourceAlreadyExistsException {
        log.debug("Creating Group with request: {}", request);
        var groupIdentity = new GroupIdentity(request.getOrganizationId(), request.getGroupId());
        var optionalGroupEntity = groupRepository.findById(groupIdentity);
        if(optionalGroupEntity.isPresent()) {
            log.error("Group with id {} already exists", groupIdentity);
            throw new ResourceAlreadyExistsException(Translator.toLocale("operation.exists"));
        }

        var groupEntity = CommonUtils.objectToPojoConverter(request, GroupEntity.class);
        groupEntity = groupRepository.save(Objects.requireNonNull(groupEntity));
        log.debug("Group created successfully with id: {}", groupIdentity);
        return CommonUtils.objectToPojoConverter(groupEntity, GroupResponse.class);
    }

    @Override
    public GroupResponse updateGroup(GroupIdentity id, GroupRequest request)  throws ResourceNotFoundException  {
        log.debug("Updating Group with id: {} and request: {}", id, request);
        var dbGroupEntity = groupRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Translator.toLocale("error.resourceNotFound")));
        var groupEntity = CommonUtils.objectToPojoConverter(request, GroupEntity.class);
        Objects.requireNonNull(groupEntity).setOrganizationId(dbGroupEntity.getOrganizationId());
        Objects.requireNonNull(groupEntity).setGroupId(dbGroupEntity.getGroupId());
        groupEntity = groupRepository.save(groupEntity);
        log.debug("Group updated successfully with id: {}", id);
        return CommonUtils.objectToPojoConverter(groupEntity, GroupResponse.class);
    }

    @Override
    public void deleteGroup(GroupIdentity id) throws ResourceNotFoundException {
        log.debug("Deleting group with id: {}", id);
        var groupEntity = groupRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Translator.toLocale("error.resourceNotFound")));
        groupRepository.delete(groupEntity);
        log.debug("Group deleted successfully with id: {}", id);
    }

    @Override
    public GroupResponse fetchGroupById(GroupIdentity id) throws ResourceNotFoundException {
        log.debug("Fetching Group with id: {}", id);
        var groupEntity = groupRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Translator.toLocale("error.resourceNotFound")));
        log.debug("Group fetched successfully with id: {}", id);
        return CommonUtils.objectToPojoConverter(groupEntity, GroupResponse.class);
    }

    @Override
    public List<GroupResponse> fetchAllGroups(String organizationId) {
        log.debug("Received request to get all groups with organizationId: {}", organizationId);
        var groups = groupRepository.findByOrganizationId(organizationId);
        log.debug("Groups fetched successfully with organizationId: {}", organizationId);
        return CommonUtils.objectToPojoConverter(groups, GroupResponse.class);
    }
}
