package in.om.services;

import in.om.component.IdGenerator;
import in.om.component.Translator;
import in.om.constants.CacheableCacheKey;
import in.om.constants.CacheableCacheName;
import in.om.dtos.SubGroupDTO;
import in.om.entities.SubGroup;
import in.om.entities.identity.SubGroupIdentity;
import in.om.exceptions.RecordNotFoundException;
import in.om.helper.SubGroupHelper;
import in.om.repositories.SubGroupRepository;
import in.om.utility.CommonUtils;
import in.om.vos.SubGroupVO;
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
public class SubGroupServiceImpl implements SubGroupService {

    private final SubGroupRepository subGroupRepository;
    private final IdGenerator idGenerator;
    private final SubGroupHelper subGroupHelper;

    @Override
    @Caching(put = @CachePut(cacheNames = CacheableCacheName.SUB_GROUP, key= CacheableCacheKey.SUB_GROUP_RES, unless = "#result == null"),
            evict = @CacheEvict(cacheNames = CacheableCacheName.SUB_GROUPS, key= CacheableCacheKey.SUB_GROUPS , condition = "#result != null", allEntries = true))
    public SubGroupVO create(String groupId, SubGroupDTO subGroupDTO) {
        log.debug("subGroupDTO : {}", subGroupDTO);
        SubGroup subGroup = CommonUtils.objectToPojoConverter(subGroupDTO, SubGroup.class);

        // Composite Primary Key
        String id = idGenerator.getSubGroupId(subGroup.getName());
        log.debug("groupId : {}, id : {}", groupId, id);
        subGroup.setId(id);
        subGroup.setGroupId(groupId);

        subGroup = subGroupRepository.save(subGroup);
        return subGroupHelper.getSubGroupVO(subGroup);
    }

    @Override
    @Caching(put = @CachePut(cacheNames = CacheableCacheName.SUB_GROUP, key = CacheableCacheKey.SUB_GROUP, unless = "#result == null"),
            evict = @CacheEvict(cacheNames = CacheableCacheName.SUB_GROUPS, key = CacheableCacheKey.SUB_GROUPS, condition = "#result != null", allEntries = true))
    public SubGroupVO update(String groupId, String id, SubGroupDTO subGroupDTO) throws RecordNotFoundException {
        SubGroupIdentity subGroupIdentity = new SubGroupIdentity(groupId, id);
        log.debug("subGroupIdentity : {}, subGroupDTO : {}", subGroupIdentity, subGroupDTO);
        SubGroup dbSubGroup = subGroupRepository.findById(subGroupIdentity).orElseThrow(() -> new RecordNotFoundException(Translator.toLocale("record.not-exist", subGroupIdentity)));
        SubGroup subGroup = CommonUtils.objectToPojoConverter(subGroupDTO, SubGroup.class);
        subGroup.setId(dbSubGroup.getId());
        subGroup.setGroupId(dbSubGroup.getGroupId());
        subGroup = subGroupRepository.save(subGroup);
        return subGroupHelper.getSubGroupVO(subGroup);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = CacheableCacheName.SUB_GROUP, key = CacheableCacheKey.SUB_GROUP, condition = "#result != null"),
            @CacheEvict(cacheNames = CacheableCacheName.SUB_GROUPS, key = CacheableCacheKey.SUB_GROUPS, condition = "#result != null", allEntries = true) })
    public SubGroupVO delete(String groupId, String id) throws RecordNotFoundException {
        SubGroupIdentity subGroupIdentity = new SubGroupIdentity(groupId, id);
        log.debug("subGroupIdentity : {}", subGroupIdentity);
        SubGroup subGroup = subGroupRepository.findById(subGroupIdentity).orElseThrow(() -> new RecordNotFoundException(Translator.toLocale("record.not-exist", subGroupIdentity)));
        subGroupRepository.delete(subGroup);
        return subGroupHelper.getSubGroupVO(subGroup);
    }

    @Override
    @Cacheable(value = CacheableCacheName.SUB_GROUPS, key = CacheableCacheKey.SUB_GROUPS,  unless = "#result == null")
    public List<SubGroupVO> fetchSubGroups(String groupId) {
        log.debug("groupId : {}", groupId);
        List<SubGroup> subGroups = subGroupRepository.findByGroupId(groupId);
        return subGroupHelper.getSubGroupVOList(subGroups);
    }

    @Override
    @Cacheable(value = CacheableCacheName.SUB_GROUP, key = CacheableCacheKey.SUB_GROUP, unless = "#result == null")
    public SubGroupVO fetchSubGroup(String groupId, String id) throws RecordNotFoundException {
        SubGroupIdentity subGroupIdentity = new SubGroupIdentity(groupId, id);
        log.debug("subGroupIdentity : {}", subGroupIdentity);
        SubGroup subGroup = subGroupRepository.findById(subGroupIdentity).orElseThrow(() -> new RecordNotFoundException(Translator.toLocale("record.not-exist", subGroupIdentity)));
        return subGroupHelper.getSubGroupVO(subGroup);
    }
}
