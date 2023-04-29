package in.om.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

import in.om.model.User;
import in.om.payload.DataFilter;
import in.om.payload.FileResponse;
import in.om.payload.ResultFilterResponce;

public interface UserService extends UserDetailsService {
	User update(User user);
	void delete(User user);
	Optional<User> findByUserName(String userName);
	Optional<User> findByPhone(String phone);
	Optional<User> findADByUserName(String userName, String phone);
	Optional<User> findByEmail(String email);
	Optional<User> getLoginUser();
	FileResponse updateProfilePicture(MultipartFile file, Long userId) throws FileNotFoundException, IOException;
	FileResponse getProfilePicture(Long userId);
	Boolean removeProfilePicture(Long userId);
	List<User> findAll(String status); //Status : 'A','I','AL'
	Optional<User> findADById(Long userId);
	ResultFilterResponce findAll(DataFilter resultFilter, String status, Boolean isAdmin);
}
