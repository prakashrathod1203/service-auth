package in.om.security;

import in.om.model.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class UserPrincipal implements OAuth2User, UserDetails {
    private Map<String, Object> attributes;
    private Collection<? extends GrantedAuthority> authorities;
    private Long branchId;
    private Long clientSystemId;
    private String fullName;
    private Long id;
    private String password;
    private static final long serialVersionUID = 1L;
    private String userName;

    public UserPrincipal(Long id, Long branchId, Long clientSystemId, String userName, String fullName, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.branchId = branchId;
        this.clientSystemId = clientSystemId;
        this.userName = userName;
        this.fullName = fullName;
        this.password = password;
        this.authorities = authorities;
    }

    public static UserPrincipal create(User user) {
        ArrayList<GrantedAuthority> authorities = new ArrayList<>();
//        for(Role types : user.getRoles()) {
//            authorities.add(new SimpleGrantedAuthority(types.getName()));
//        }
        return new UserPrincipal(
                user.getUserId(),
                0L,
                0L,
                user.getUsername(),
                "",
                user.getPassword(),
                authorities
        );
    }

    public static UserPrincipal create(User user, Map<String, Object> attributes) {
        UserPrincipal userPrincipal = UserPrincipal.create(user);
        userPrincipal.setAttributes(attributes);
        return userPrincipal;
    }

    public Long getId() {
        return id;
    }

    public Long getBranchId() {
        return branchId;
    }
    public Long getClientSystemId() {
        return clientSystemId;
    }

    public String getFullName() {
        return fullName;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
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
        return String.valueOf(id);
    }

    private static String getFullName(User user) {
        String firstName = StringUtils.isEmpty(user.getFirstName()) ? "" : user.getFirstName();
        String lastName = StringUtils.isEmpty(user.getFirstName()) ? "" : user.getLastName();
        return String.format("%s %s", firstName, lastName);
    }
}
