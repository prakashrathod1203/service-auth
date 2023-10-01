package in.om.security;

import in.om.entities.User;
import in.om.entities.record.UserResource;
import in.om.utility.CommonUtils;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class UserPrincipal implements OAuth2User, UserDetails {
    private static final long serialVersionUID = 1L;
    @Getter
    private String loginId;
    @Getter
    private String password;
    @Getter
    private String fullName;
    @Getter
    private UserResource resource;

    private Map<String, Object> attributes;
    private Collection<? extends GrantedAuthority> authorities;

    public UserPrincipal(String loginId, String password, String fullName, UserResource resource, Collection<? extends GrantedAuthority> authorities) {
        this.loginId = loginId;
        this.password = password;
        this.fullName = fullName;
        this.resource = resource;
        this.authorities = authorities;
    }

    public static UserPrincipal create(User user) {
        ArrayList<GrantedAuthority> authorities = new ArrayList<>();
//        for(Role types : user.getRoles()) {
//            authorities.add(new SimpleGrantedAuthority(types.getName()));
//        }
        return new UserPrincipal(
                user.getLoginId(),
                user.getPassword(),
                CommonUtils.getFullName(user),
                user.getResource(),
                authorities
        );
    }

    public static UserPrincipal create(User user, Map<String, Object> attributes) {
        UserPrincipal userPrincipal = UserPrincipal.create(user);
        userPrincipal.setAttributes(attributes);
        return userPrincipal;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return loginId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getName() {
        return loginId;
    }


}
