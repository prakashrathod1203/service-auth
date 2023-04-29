package in.om.services;

import in.om.dtos.OrganizationDTO;
import in.om.exceptions.RecordNotFoundException;
import in.om.repositories.OrganizationRepository;
import in.om.vos.OrganizationVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
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

    @Override
    public OrganizationVO create(OrganizationDTO organizationDTO) {
        log.debug("organizationDTO : {}", organizationDTO);
        return null;
    }

    @Override
    public OrganizationVO update(String id, OrganizationDTO organizationDTO) throws RecordNotFoundException {
        log.debug("id : {}, organizationDTO : {}", id, organizationDTO);
        return null;
    }

    @Override
    public List<OrganizationVO> fetchOrganizations() {
        return null;
    }

    @Override
    public OrganizationVO fetchOrganization(String id) throws RecordNotFoundException {
        log.debug("id : {}", id);
        return null;
    }
}
