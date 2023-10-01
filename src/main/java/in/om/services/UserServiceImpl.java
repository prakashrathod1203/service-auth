package in.om.services;

import in.om.component.IdGenerator;
import in.om.component.Notification;
import in.om.component.Translator;
import in.om.dtos.UserDTO;
import in.om.entities.Organization;
import in.om.entities.User;
import in.om.entities.UserSubGroup;
import in.om.entities.record.Group;
import in.om.enums.StatusEnum;
import in.om.exceptions.RecordNotFoundException;
import in.om.helper.UserHelper;
import in.om.repositories.UserRepository;
import in.om.security.UserPrincipal;
import in.om.utility.CommonUtils;
import in.om.vos.GroupVO;
import in.om.vos.SubGroupVO;
import in.om.vos.UserVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * @author Prakash Rathod
 */
@Service
@Log4j2
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserHelper userHelper;
    private final IdGenerator idGenerator;
    private final PasswordEncoder passwordEncoder;
//    private final Notification notification;
//    private final UserRoleService userRoleService;

    @Override
//    @Caching(put = @CachePut(cacheNames = CacheableCacheName.ORGANIZATION,  key = CacheableCacheKey.ORGANIZATION_RES, unless = "#result == null"),
//            evict = @CacheEvict(cacheNames = CacheableCacheName.ORGANIZATIONS, condition = "#result != null", allEntries = true))
    public UserVO create(UserDTO userDTO) throws RecordNotFoundException {
        log.debug("userDTO : {}", userDTO);
        User user = CommonUtils.objectToPojoConverter(userDTO, User.class);

        String loginId = idGenerator.getLoginId(userDTO);
        log.debug("loginId : {}", loginId);
        user.setLoginId(loginId);
        user.setPassword(passwordEncoder.encode(loginId));
        user.setIsActive(StatusEnum.Y);
        user = userRepository.save(user);

        // User roles
//        if(null != userDTO.getResource()) {
//            userDTO.getResource().getOrganizations().forEach(org -> {
//                for(Group group : org.getGroups()) {
//
//                }
//                UserSubGroup userSubGroup = new UserSubGroup();
//                userSubGroup.setLoginId(loginId);
//                userSubGroup.setSubGroupId(org.getGroups());
//            });
//        }

        // Send notification
        //notification.sendNewRegistrationMail();

        return userHelper.getUserVO(user);
    }

//    @Override
//    @Caching(put = @CachePut(cacheNames = CacheableCacheName.ORGANIZATION, key = CacheableCacheKey.ORGANIZATION, unless = "#result == null"),
//            evict = @CacheEvict(cacheNames = CacheableCacheName.ORGANIZATIONS, condition = "#result != null", allEntries = true))
//    public OrganizationVO update(String id, OrganizationDTO organizationDTO) throws RecordNotFoundException {
//        log.debug("id : {}, organizationDTO : {}", id, organizationDTO);
//        Organization dbOrganization = organizationRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(Translator.toLocale("record.not-exist", id)));
//        Organization organization = CommonUtils.objectToPojoConverter(organizationDTO, Organization.class);
//        organization.setId(dbOrganization.getId());
//        organization = organizationRepository.save(organization);
//        return organizationHelper.getOrganizationVO(organization);
//    }
//
//    @Override
//    @Caching(evict = {
//            @CacheEvict(cacheNames = CacheableCacheName.ORGANIZATION, key = CacheableCacheKey.ORGANIZATION, condition = "#result != null"),
//            @CacheEvict(cacheNames = CacheableCacheName.ORGANIZATIONS, condition = "#result != null", allEntries = true) })
//    public OrganizationVO delete(String id) throws RecordNotFoundException {
//        log.debug("id : {}", id);
//        Organization organization = organizationRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(Translator.toLocale("record.not-exist", id)));
//        organizationRepository.delete(organization);
//        return organizationHelper.getOrganizationVO(organization);
//    }
//
//    @Override
//    @Cacheable(value = CacheableCacheName.ORGANIZATIONS, unless = "#result == null")
//    public List<OrganizationVO> fetchOrganizations() {
//        List<Organization> organizations = organizationRepository.findAll();
//        return organizationHelper.getOrganizationVOList(organizations);
//    }
//
//    @Override
//    @Cacheable(value = CacheableCacheName.ORGANIZATION, key = CacheableCacheKey.ORGANIZATION, unless = "#result == null")
//    public OrganizationVO fetchOrganization(String id) throws RecordNotFoundException {
//        log.debug("id : {}", id);
//        Organization organization = organizationRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(Translator.toLocale("record.not-exist", id)));
//        return organizationHelper.getOrganizationVO(organization);
//    }
}
