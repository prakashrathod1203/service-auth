package in.om.services;

import in.om.component.Translator;
import in.om.constants.CacheableCacheName;
import in.om.entities.UserSubGroup;
import in.om.entities.identity.UserSubGroupIdentity;
import in.om.exceptions.RecordNotFoundException;
import in.om.repositories.UserSubGroupRepository;
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
public class UserSubGroupServiceImpl implements UserSubGroupService {
	
	private final UserSubGroupRepository userSubGroupRepository;

	@Override
	@Caching(put = @CachePut(cacheNames = CacheableCacheName.USER_SUB_GROUPS, key= "#userSubGroup.loginId", unless = "#result == null"),
			evict = @CacheEvict(cacheNames = CacheableCacheName.USER_SUB_GROUPS, key= "#userSubGroup.loginId" , condition = "#result != null", allEntries = true))
	public UserSubGroup create(UserSubGroup userSubGroup) {
		log.debug("userSubGroup : {}", userSubGroup);
		userSubGroup = userSubGroupRepository.save(userSubGroup);
		return userSubGroup;
	}

	@Override
	@Caching(evict = {
			@CacheEvict(cacheNames = CacheableCacheName.USER_SUB_GROUPS, key = "#userSubGroupIdentity.loginId", condition = "#result != null"),
			@CacheEvict(cacheNames = CacheableCacheName.USER_SUB_GROUPS, key = "#userSubGroupIdentity.loginId", condition = "#result != null", allEntries = true) })
	public UserSubGroup delete(UserSubGroupIdentity userSubGroupIdentity) throws RecordNotFoundException {
		log.debug("userSubGroupIdentity : {}", userSubGroupIdentity);
		UserSubGroup userSubGroup = userSubGroupRepository.findById(userSubGroupIdentity).orElseThrow(() -> new RecordNotFoundException(Translator.toLocale("record.not-exist", userSubGroupIdentity)));
		userSubGroupRepository.delete(userSubGroup);
		return userSubGroup;
	}

	@Override
	@Cacheable(value = CacheableCacheName.USER_SUB_GROUPS, key = "#loginId", unless = "#result == null")
	public List<UserSubGroup> fetchUserSubGroup(String loginId) throws RecordNotFoundException {
		log.debug("loginId : {}", loginId);
		return userSubGroupRepository.findByLoginId(loginId);
	}
}
