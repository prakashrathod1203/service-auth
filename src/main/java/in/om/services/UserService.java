package in.om.services;

import in.om.dtos.UserDTO;
import in.om.exceptions.RecordNotFoundException;
import in.om.vos.UserVO;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

/**
 * @author Prakash Rathod
 */
public interface UserService {
    UserVO create(UserDTO userDTO) throws RecordNotFoundException;
//    UserVO update(String id, UserDTO userDTO) throws RecordNotFoundException;
//    UserVO delete(String id) throws RecordNotFoundException;
//
//    List<UserVO> fetchOrganizations();
//    UserVO fetchOrganization(String id) throws RecordNotFoundException;
}
