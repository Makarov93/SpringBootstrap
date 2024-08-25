package makarov.springsecurity.service;

import makarov.springsecurity.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Set;


public interface UserService extends UserDetailsService {
    List<User> getUsers(int count);

    void saveUser(User user, Set<String> roles);

    void deleteUser(Long id);

    User getUserById(Long id);

    User findByEmail(String name);

    Set<String> getAllRolesNames();

    void initializeDB();
}