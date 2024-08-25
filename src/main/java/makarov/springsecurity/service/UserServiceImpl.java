package makarov.springsecurity.service;

import makarov.springsecurity.model.Role;
import makarov.springsecurity.model.User;
import makarov.springsecurity.repository.RoleRepository;
import makarov.springsecurity.repository.UserRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository, @Lazy PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Set<String> getAllRolesNames() {
        return roleRepository
                .findAll()
                .stream()
                .map(Role::getName)
                .collect(Collectors.toSet());
    }

    @Transactional
    @Override
    public void initializeDB() {
        Role adminRole = new Role("ADMIN");
        Role userRole = new Role("USER");

        User user = new User("admin", "admin", (byte) 35, "admin@mail.com", "admin", Set.of(adminRole,userRole));
        User admin = new User("user", "user", (byte) 30, "user@mail.com", "user", Set.of(userRole));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));

        userRepository.save(user);
        userRepository.save(admin);
    }

    @Override
    public List<User> getUsers(int count) {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public void saveUser(User user, Set<String> roles) {
        if (roles == null) {
            user.setRoles(roleRepository.findAllByName("USER"));
        } else {
            user.setRoles(roleRepository.findAllByNameIn(roles));
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
