package pl.edu.pw.passwordmanager.security.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pw.passwordmanager.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);


}
