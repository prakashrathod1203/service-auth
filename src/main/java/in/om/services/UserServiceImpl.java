package in.om.services;

import in.om.component.Translator;
import in.om.exceptions.RecordNotFoundException;
import in.om.model.Role;
import in.om.model.User;
import in.om.payload.DataFilter;
import in.om.payload.FileResponse;
import in.om.payload.ResultFilterResponce;
import in.om.repositories.UserRepository;
import in.om.security.UserPrincipal;
import in.om.services.UserService;
import in.om.utility.ApplicationConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.activation.MimetypesFileTypeMap;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Log4j2
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final EntityManager entityManager;

	@Value("${user.upload.profile}")
	private String uploadProfilePath;

	@Value("${user.donwload.profile}")
	private String downloadProfilePath;

	@Override
	public User update(User user) {
		return userRepository.save(user);
	}

	@Override
	public void delete(User user) {
		userRepository.delete(user);
	}
	
	@Override
	public Optional<User> findADById(Long userId) {
		return userRepository.findADById(userId);
	}

	@Override
	public Optional<User> findByUserName(String userName) {
		return userRepository.findByusername(userName);
	}

	@Override
	public List<User> findAll(String status)  { // Status : 'A','I','AL'
		if (status.equals(ApplicationConstants.STATUS_ACTIVE)) {
			return userRepository.findByStatus(true);
		} else if (status.equals(ApplicationConstants.STATUS_INACTIVE)) {
			return userRepository.findByStatus(false);
		} else {
			return userRepository.findAll();
		}
	}
	@Override
	public ResultFilterResponce findAll(DataFilter resultFilter, String status, Boolean isAdmin) {
	   CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
	   CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
	   Root<User> user = criteriaQuery.from(User.class);
	   Join<User, Role> userRole = user.join("roles", JoinType.INNER);
	   
	   Predicate activePredicate = criteriaBuilder.equal(user.get("active"), status.equals(ApplicationConstants.STATUS_ACTIVE) ? true : false);
	   Predicate firstPredicate = criteriaBuilder.like(user.get("firstName"), "%" + resultFilter.getSearchValue() + "%");
	   Predicate lastNamePredicate = criteriaBuilder.like(user.get("lastName"), "%" + resultFilter.getSearchValue() + "%");
	   Predicate rolePredicate = null;
	   if(isAdmin){
		   rolePredicate = criteriaBuilder.notEqual(userRole.get("name"), ApplicationConstants.ROLE_CUSTOMER);
	   } else{
		   rolePredicate = criteriaBuilder.equal(userRole.get("name"), ApplicationConstants.ROLE_CUSTOMER);
	   }
	   Predicate finalPredicate = criteriaBuilder.or(firstPredicate, lastNamePredicate);
	   if(resultFilter.getSortField() != null) {
		   if(resultFilter.getSortDirection().toLowerCase().toLowerCase().equals(ApplicationConstants.ASC))
			   criteriaQuery.orderBy(criteriaBuilder.asc(user.get(resultFilter.getSortField())));
		   else if(resultFilter.getSortDirection().toLowerCase().toLowerCase().equals(ApplicationConstants.DESC))
			   criteriaQuery.orderBy(criteriaBuilder.desc(user.get(resultFilter.getSortField())));
	   }	
	
	   if(resultFilter.getSearchValue() != null){
			if(!status.equals(ApplicationConstants.STATUS_ALL))
				criteriaQuery.where(activePredicate, finalPredicate, rolePredicate);
			else
				criteriaQuery.where(finalPredicate, rolePredicate);
		}else if(!status.equals(ApplicationConstants.STATUS_ALL)){
			criteriaQuery.where(activePredicate, rolePredicate);
		} else {
			criteriaQuery.where(rolePredicate);
		}
	   criteriaQuery.groupBy(user.get("userId"));
	   
	   
	   TypedQuery<User> query = entityManager.createQuery(criteriaQuery);
	   ResultFilterResponce filterResponce = new ResultFilterResponce();
	   filterResponce.setTotalRows(query.getResultList().size());
	   query.setFirstResult(resultFilter.getFirstResult());
	   query.setMaxResults(resultFilter.getMaxResults());
	   filterResponce.setResult(query.getResultList());
       return filterResponce;
	}
	
	@Override
	public Optional<User> findADByUserName(String userName , String phone) {
		Optional<User> optional = userRepository.findADByusername(userName != "" ? userName : phone);
		if(!optional.isPresent()){
			optional = userRepository.findADByPhone(phone);
		}
		return optional;
	}
	@Override
	public Optional<User> getLoginUser() {
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
//		String currentUserName;
//		if(userPrincipal.getEmail().isEmpty()){
//			currentUserName = userPrincipal.getPhone();
//		} else {
//			currentUserName = userPrincipal.getEmail();
//		}
//		Optional<User> user = userRepository.findByusername(currentUserName);
//		return user;
		return null;
	}
	
	@Override
	public FileResponse updateProfilePicture(MultipartFile file, Long userId) throws FileNotFoundException, IOException {
		String pictureFilename = "user_"+userId+".jpg";
		File contactsFolder = new File(uploadProfilePath);
		if (!contactsFolder.exists()) {
			contactsFolder.mkdirs();
		}
		File savedFile = new File(uploadProfilePath + pictureFilename);
		savedFile.createNewFile();
			
		BufferedInputStream bufferedInputStream = new BufferedInputStream(file.getInputStream());
		BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(savedFile));

		IOUtils.copy(bufferedInputStream, bufferedOutputStream);

		bufferedOutputStream.close();
		file.getInputStream().close();
		
		String downloadUrl = downloadProfilePath + pictureFilename;
		
		return new FileResponse(savedFile.getName(), downloadUrl.trim(), file.getContentType(), file.getSize());
	}
	
	@Override
	public FileResponse getProfilePicture(Long userId) {
		String pictureFilename = "user_"+userId+".jpg";
		File file = new File(uploadProfilePath + pictureFilename);
		String downloadUrl = downloadProfilePath + file.getName(); 
		return new FileResponse(file.getName(), downloadUrl, new MimetypesFileTypeMap().getContentType(file), file.getTotalSpace());
	}
	
	@Override
	public Boolean removeProfilePicture(Long userId) {
		String pictureFilename = "user_"+userId+".jpg";
		File file = new File(uploadProfilePath + pictureFilename);
		if (file.exists()) {
			return file.delete();
		}
		return false;
	}

	@Override
	public Optional<User> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public Optional<User> findByPhone(String phone) {
		return userRepository.findADByPhone(phone);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.debug("loadUserByUsername() in UserServiceImpl, Request {}", username);
		User user = userRepository.findByusername(username).orElseThrow(() -> new RecordNotFoundException(Translator.toLocale("user.not.found", username)));

		ArrayList<GrantedAuthority> authorities = new ArrayList<>();
		user.getRoles().forEach(role -> {
			authorities.add(new SimpleGrantedAuthority(role.getName()));
		});
		UserDetails userDetails = new UserPrincipal(user.getUserId(), 0L, 0L, user.getUsername(), "", user.getPassword(), authorities);
		log.debug("loadUserByUsername() in UserServiceImpl, Response {}", userDetails);
		return userDetails;
	}
}
