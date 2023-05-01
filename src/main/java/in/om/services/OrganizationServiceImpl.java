package in.om.services;

import in.om.component.IdGenerator;
import in.om.component.Translator;
import in.om.constants.CacheableCacheKey;
import in.om.constants.CacheableCacheName;
import in.om.dtos.OrganizationDTO;
import in.om.entities.Organization;
import in.om.exceptions.RecordNotFoundException;
import in.om.helper.OrganizationHelper;
import in.om.repositories.OrganizationRepository;
import in.om.utility.CommonUtils;
import in.om.vos.OrganizationVO;
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
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final OrganizationHelper organizationHelper;
    private final IdGenerator idGenerator;

    @Override
    @Caching(put = @CachePut(cacheNames = CacheableCacheName.ORGANIZATION,  key = CacheableCacheKey.ORGANIZATION_RES, unless = "#result == null"),
            evict = @CacheEvict(cacheNames = CacheableCacheName.ORGANIZATIONS, condition = "#result != null", allEntries = true))
    public OrganizationVO create(OrganizationDTO organizationDTO) {
        log.debug("organizationDTO : {}", organizationDTO);
        Organization organization = CommonUtils.objectToPojoConverter(organizationDTO, Organization.class);
        String id = idGenerator.getOrganizationId(organizationDTO.getName());
        log.debug("id : {}", id);
        organization.setId(id);
        organization = organizationRepository.save(organization);
        return organizationHelper.getOrganizationVO(organization);
    }

    @Override
    @Caching(put = @CachePut(cacheNames = CacheableCacheName.ORGANIZATION, key = CacheableCacheKey.ORGANIZATION, unless = "#result == null"),
            evict = @CacheEvict(cacheNames = CacheableCacheName.ORGANIZATIONS, condition = "#result != null", allEntries = true))
    public OrganizationVO update(String id, OrganizationDTO organizationDTO) throws RecordNotFoundException {
        log.debug("id : {}, organizationDTO : {}", id, organizationDTO);
        Organization dbOrganization = organizationRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(Translator.toLocale("record.not-exist", id)));
        Organization organization = CommonUtils.objectToPojoConverter(organizationDTO, Organization.class);
        organization.setId(dbOrganization.getId());
        organization = organizationRepository.save(organization);
        return organizationHelper.getOrganizationVO(organization);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = CacheableCacheName.ORGANIZATION, key = CacheableCacheKey.ORGANIZATION, condition = "#result != null"),
            @CacheEvict(cacheNames = CacheableCacheName.ORGANIZATIONS, condition = "#result != null", allEntries = true) })
    public OrganizationVO delete(String id) throws RecordNotFoundException {
        log.debug("id : {}", id);
        Organization organization = organizationRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(Translator.toLocale("record.not-exist", id)));
        organizationRepository.delete(organization);
        return organizationHelper.getOrganizationVO(organization);
    }

    @Override
    @Cacheable(value = CacheableCacheName.ORGANIZATIONS, unless = "#result == null")
    public List<OrganizationVO> fetchOrganizations() {
        List<Organization> organizations = organizationRepository.findAll();
        return organizationHelper.getOrganizationVOList(organizations);
    }

    @Override
    @Cacheable(value = CacheableCacheName.ORGANIZATION, key = CacheableCacheKey.ORGANIZATION, unless = "#result == null")
    public OrganizationVO fetchOrganization(String id) throws RecordNotFoundException {
        log.debug("id : {}", id);
        Organization organization = organizationRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(Translator.toLocale("record.not-exist", id)));
        return organizationHelper.getOrganizationVO(organization);
    }
}
