package makarov.springsecurity.repository;

import makarov.springsecurity.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Set<Role> findAllByName(String role);

    Set<Role> findAllByNameIn(Set<String> roles);
}
