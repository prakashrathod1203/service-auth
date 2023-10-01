package in.om.services;

import in.om.component.IdGenerator;
import in.om.component.Translator;
import in.om.constants.CacheableCacheKey;
import in.om.constants.CacheableCacheName;
import in.om.dtos.RoleDTO;
import in.om.entities.Role;
import in.om.entities.identity.RoleIdentity;
import in.om.exceptions.RecordNotFoundException;
import in.om.helper.RoleHelper;
import in.om.repositories.RoleRepository;
import in.om.utility.CommonUtils;
import in.om.vos.RoleVO;
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
public class RoleServiceImpl implements RoleService {
	
	private final RoleRepository roleRepository;
	private final RoleHelper roleHelper;
	private final IdGenerator idGenerator;

	@Override
	@Caching(put = @CachePut(cacheNames = CacheableCacheName.ROLE, key= CacheableCacheKey.ROLE_RES, unless = "#result == null"),
			evict = @CacheEvict(cacheNames = CacheableCacheName.ROLES, key= CacheableCacheKey.ROLES , condition = "#result != null", allEntries = true))
	public RoleVO create(String groupId, RoleDTO roleDTO) {
		log.debug("roleDTO : {}", roleDTO);
		Role role = CommonUtils.objectToPojoConverter(roleDTO, Role.class);

		// Primary Key
		long count = roleRepository.countByGroupId(groupId);
		String id = idGenerator.getRoleId(groupId, count);
		log.debug("groupId : {}, id : {}", groupId, id);
		role.setId(id);
		role.setGroupId(groupId);

		role = roleRepository.save(role);
		return roleHelper.getRoleVO(role);
	}

	@Override
	@Caching(put = @CachePut(cacheNames = CacheableCacheName.ROLE, key= CacheableCacheKey.ROLE, unless = "#result == null"),
			evict = @CacheEvict(cacheNames = CacheableCacheName.ROLES, key= CacheableCacheKey.ROLES , condition = "#result != null", allEntries = true))
	public RoleVO update(String groupId, String id, RoleDTO roleDTO) throws RecordNotFoundException {
		RoleIdentity roleIdentity = new RoleIdentity(groupId, id);
		log.debug("roleIdentity : {}, roleDTO : {}", roleIdentity, roleDTO);
		Role dbRole = roleRepository.findById(roleIdentity).orElseThrow(() -> new RecordNotFoundException(Translator.toLocale("record.not-exist", roleIdentity)));
		Role role = CommonUtils.objectToPojoConverter(roleDTO, Role.class);
		role.setId(dbRole.getId());
		role.setGroupId(dbRole.getGroupId());
		role = roleRepository.save(role);
		return roleHelper.getRoleVO(role);
	}

	@Override
	@Caching(evict = {
			@CacheEvict(cacheNames = CacheableCacheName.ROLE, key = CacheableCacheKey.ROLE, condition = "#result != null"),
			@CacheEvict(cacheNames = CacheableCacheName.ROLES, key = CacheableCacheKey.ROLES, condition = "#result != null", allEntries = true) })
	public RoleVO delete(String groupId, String id) throws RecordNotFoundException {
		RoleIdentity roleIdentity = new RoleIdentity(groupId, id);
		log.debug("roleIdentity : {}", roleIdentity);
		Role role = roleRepository.findById(roleIdentity).orElseThrow(() -> new RecordNotFoundException(Translator.toLocale("record.not-exist", roleIdentity)));
		roleRepository.delete(role);
		return roleHelper.getRoleVO(role);
	}

	@Override
	@Cacheable(value = CacheableCacheName.ROLES, key = CacheableCacheKey.ROLES, unless = "#result == null")
	public List<RoleVO> fetchRoles(String groupId) {
		log.debug("groupId : {}", groupId);
		List<Role> roles = roleRepository.findByGroupId(groupId);
		return roleHelper.getRoleVOList(roles);
	}

	@Override
	@Cacheable(value = CacheableCacheName.ROLE, key = CacheableCacheKey.ROLE, unless = "#result == null")
	public RoleVO fetchRole(String groupId, String id) throws RecordNotFoundException {
		RoleIdentity roleIdentity = new RoleIdentity(groupId, id);
		log.debug("groupIdentity : {}", roleIdentity);
		Role role = roleRepository.findById(roleIdentity).orElseThrow(() -> new RecordNotFoundException(Translator.toLocale("record.not-exist", roleIdentity)));
		return roleHelper.getRoleVO(role);
	}
}
