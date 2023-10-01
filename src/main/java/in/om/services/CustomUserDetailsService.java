package in.om.services;

import java.util.ArrayList;

import in.om.component.Translator;
import in.om.entities.User;
import in.om.exceptions.RecordNotFoundException;
import in.om.repositories.UserRepository;
import in.om.security.UserPrincipal;
import in.om.utility.CommonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("customUserDetailsService")
@Log4j2
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByLoginId(username).orElseThrow(() -> new RecordNotFoundException(Translator.toLocale("user.not.found", username)));

		ArrayList<GrantedAuthority> authorities = new ArrayList<>();
//		user.getRoles().forEach(role -> {
//			authorities.add(new SimpleGrantedAuthority(role.getName()));
//		});
		UserDetails userDetails = new UserPrincipal(user.getLoginId(), user.getPassword(),  CommonUtils.getFullName(user), user.getResource(), authorities);
		log.debug("loadUserByUsername() in UserServiceImpl, Response {}", userDetails);
		return userDetails;
	}
}