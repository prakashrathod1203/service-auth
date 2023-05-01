package in.om.helper;

import in.om.entities.SubGroup;
import in.om.utility.CommonUtils;
import in.om.vos.SubGroupVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Prakash Rathod
 */
@Component
@RequiredArgsConstructor
public class SubGroupHelper {

    public SubGroupVO getSubGroupVO(SubGroup subGroup) {
        return CommonUtils.objectToPojoConverter(subGroup, SubGroupVO.class);
    }
    public List<SubGroupVO> getSubGroupVOList(List<SubGroup> subGroups) {
        return CommonUtils.objectToPojoConverter(subGroups, SubGroupVO.class);
    }
}
