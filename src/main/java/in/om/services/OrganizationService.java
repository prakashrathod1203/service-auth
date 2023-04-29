package in.om.services;

import in.om.dtos.OrganizationDTO;
import in.om.exceptions.RecordNotFoundException;
import in.om.vos.OrganizationVO;

import java.util.List;

/**
 * @author Prakash Rathod
 */
public interface OrganizationService {
    OrganizationVO create(OrganizationDTO request);
    OrganizationVO update(String id, OrganizationDTO organizationDTO) throws RecordNotFoundException;

    List<OrganizationVO> fetchOrganizations();
    OrganizationVO fetchOrganization(String id) throws RecordNotFoundException;
}
