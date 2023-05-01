package in.om.helper;

import in.om.entities.Group;
import in.om.utility.CommonUtils;
import in.om.vos.GroupVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Prakash Rathod
 */
@Component
@RequiredArgsConstructor
public class GroupHelper {

    public GroupVO getGroupVO(Group group) {
        return CommonUtils.objectToPojoConverter(group, GroupVO.class);
    }
    public List<GroupVO> getGroupVOList(List<Group> groups) {
        return CommonUtils.objectToPojoConverter(groups, GroupVO.class);
    }
}
