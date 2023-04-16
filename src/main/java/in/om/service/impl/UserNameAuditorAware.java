package in.om.service.impl;

import in.om.security.UserPrincipal;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class UserNameAuditorAware implements AuditorAware<String> {

	@Override
	public Optional<String> getCurrentAuditor() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    if (authentication == null || !authentication.isAuthenticated()) {
	    	return Optional.of("System");
	    }
	    UserPrincipal principal = (UserPrincipal)authentication.getPrincipal();
	    return Optional.of(principal.getUsername());
	}
}
