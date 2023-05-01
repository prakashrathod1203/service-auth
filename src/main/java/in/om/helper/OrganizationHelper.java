package in.om.helper;

import in.om.entities.Organization;
import in.om.utility.CommonUtils;
import in.om.vos.OrganizationVO;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Prakash Rathod
 */
@Component
public class OrganizationHelper {

    public OrganizationVO getOrganizationVO(Organization organization) {
        return CommonUtils.objectToPojoConverter(organization, OrganizationVO.class);
    }

    public List<OrganizationVO> getOrganizationVOList(List<Organization> organizations) {
        return CommonUtils.objectToPojoConverter(organizations, OrganizationVO.class);
    }

}
