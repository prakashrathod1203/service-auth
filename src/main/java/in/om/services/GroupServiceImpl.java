package in.om.services;

import in.om.component.IdGenerator;
import in.om.component.Translator;
import in.om.constants.CacheableCacheKey;
import in.om.constants.CacheableCacheName;
import in.om.dtos.GroupDTO;
import in.om.entities.Group;
import in.om.entities.GroupIdentity;
import in.om.exceptions.RecordNotFoundException;
import in.om.helper.GroupHelper;
import in.om.repositories.GroupRepository;
import in.om.utility.CommonUtils;
import in.om.vos.GroupVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Prakash Rathod
 */
@Service
@Log4j2
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final IdGenerator idGenerator;
    private final GroupHelper groupHelper;

    @Override
    @Caching(put = @CachePut(cacheNames = CacheableCacheName.GROUP, key= CacheableCacheKey.GROUP),
            evict = @CacheEvict(cacheNames = CacheableCacheName.GROUPS, key= CacheableCacheKey.GROUPS , allEntries = true))
    public GroupVO create(String organizationId, GroupDTO groupDTO) {
        log.debug("groupDTO : {}", groupDTO);
        Group group = CommonUtils.objectToPojoConverter(groupDTO, Group.class);

        // Composite Primary Key
        String id = idGenerator.getGroupId(group.getName());
        log.debug("organizationId : {}, id : {}", organizationId, id);
        group.setId(id);
        group.setOrganizationId(organizationId);

        group = groupRepository.save(group);
        return groupHelper.getGroupVO(group);
    }

    @Override
    @Caching(put = @CachePut(cacheNames = CacheableCacheName.GROUP, key = CacheableCacheKey.GROUP),
            evict = @CacheEvict(cacheNames = CacheableCacheName.GROUPS, key = CacheableCacheKey.GROUPS, allEntries = true))
    public GroupVO update(String organizationId, String id, GroupDTO groupDTO) throws RecordNotFoundException {
        GroupIdentity groupIdentity = new GroupIdentity(organizationId, id);
        log.debug("groupIdentity : {}, groupDTO : {}", groupIdentity, groupDTO);
        Group dbGroup = groupRepository.findById(groupIdentity).orElseThrow(() -> new RecordNotFoundException(Translator.toLocale("record.not-exist", groupIdentity)));
        Group group = CommonUtils.objectToPojoConverter(groupDTO, Group.class);
        group.setId(dbGroup.getId());
        group.setOrganizationId(dbGroup.getOrganizationId());
        group = groupRepository.save(group);
        return groupHelper.getGroupVO(group);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = CacheableCacheName.GROUP, key = CacheableCacheKey.GROUP),
            @CacheEvict(cacheNames = CacheableCacheName.GROUPS, key = CacheableCacheKey.GROUPS, allEntries = true) })
    public GroupVO delete(String organizationId, String id) throws RecordNotFoundException {
        GroupIdentity groupIdentity = new GroupIdentity(organizationId, id);
        log.debug("groupIdentity : {}", groupIdentity);
        Group group = groupRepository.findById(groupIdentity).orElseThrow(() -> new RecordNotFoundException(Translator.toLocale("record.not-exist", groupIdentity)));
        groupRepository.delete(group);
        return groupHelper.getGroupVO(group);
    }

    @Override
    @Cacheable(value = CacheableCacheName.GROUPS, key = CacheableCacheKey.GROUPS)
    public List<GroupVO> fetchGroups(String organizationId) {
        log.debug("organizationId : {}", organizationId);
        List<Group> groups = groupRepository.findByOrganizationId(organizationId);
        return groupHelper.getGroupVOList(groups);
    }

    @Override
    @Cacheable(value = CacheableCacheName.GROUP, key = CacheableCacheKey.GROUP)
    public GroupVO fetchGroup(String organizationId, String id) throws RecordNotFoundException {
        GroupIdentity groupIdentity = new GroupIdentity(organizationId, id);
        log.debug("groupIdentity : {}", groupIdentity);
        Group group = groupRepository.findById(groupIdentity).orElseThrow(() -> new RecordNotFoundException(Translator.toLocale("record.not-exist", groupIdentity)));
        return groupHelper.getGroupVO(group);
    }
}
