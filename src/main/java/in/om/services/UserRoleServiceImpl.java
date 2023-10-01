package in.om.services;

import in.om.component.Translator;
import in.om.constants.CacheableCacheName;
import in.om.entities.UserRole;
import in.om.entities.identity.UserRoleIdentity;
import in.om.exceptions.RecordNotFoundException;
import in.om.repositories.UserRoleRepository;
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
public class UserRoleServiceImpl implements UserRoleService {
	
	private final UserRoleRepository userRoleRepository;

	@Override
	@Caching(put = @CachePut(cacheNames = CacheableCacheName.USER_ROLES, key= "#userRole.loginId", unless = "#result == null"),
			evict = @CacheEvict(cacheNames = CacheableCacheName.USER_ROLES, key= "#userRole.loginId" , condition = "#result != null", allEntries = true))
	public UserRole create(UserRole userRole) {
		log.debug("userRole : {}", userRole);
		userRole = userRoleRepository.save(userRole);
		return userRole;
	}

	@Override
	@Caching(evict = {
			@CacheEvict(cacheNames = CacheableCacheName.USER_ROLES, key = "#roleIdentity.loginId", condition = "#result != null"),
			@CacheEvict(cacheNames = CacheableCacheName.USER_ROLES, key = "#roleIdentity.loginId", condition = "#result != null", allEntries = true) })
	public UserRole delete(UserRoleIdentity userRoleIdentity) throws RecordNotFoundException {
		log.debug("userRoleIdentity : {}", userRoleIdentity);
		UserRole userRole = userRoleRepository.findById(userRoleIdentity).orElseThrow(() -> new RecordNotFoundException(Translator.toLocale("record.not-exist", userRoleIdentity)));
		userRoleRepository.delete(userRole);
		return userRole;
	}

	@Override
	@Cacheable(value = CacheableCacheName.USER_ROLES, key = "#loginId", unless = "#result == null")
	public List<UserRole> fetchUserRole(String loginId) throws RecordNotFoundException {
		log.debug("loginId : {}", loginId);
		return userRoleRepository.findByLoginId(loginId);
	}
}
