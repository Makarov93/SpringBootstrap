package makarov.springsecurity.security;

import makarov.springsecurity.model.Role;
import makarov.springsecurity.model.User;
import makarov.springsecurity.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceSecurity implements UserDetailsService {
    private final UserService userService;

    public UserDetailsServiceSecurity(UserService userService) {
        this.userService = userService;
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("There is no %s user", email));
        }
        return new org.springframework.security.core.userdetails.User
                (user.getEmail(), user.getPassword(), mapRolesToAuthority(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthority(Collection<Role> roles) {
        return roles
                .stream()
                .map(el -> new SimpleGrantedAuthority("ROLE_" + el.getName()))
                .collect(Collectors.toList());
    }
}
