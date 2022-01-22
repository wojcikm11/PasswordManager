package pl.edu.pw.passwordmanager.user;

import org.springframework.security.core.AuthenticationException;
import pl.edu.pw.passwordmanager.dto.UserRegistration;
import pl.edu.pw.passwordmanager.model.User;

import java.util.Optional;

public interface IUserService {
    User register(UserRegistration user);
    Optional<User> getByUsername(String username);
    void resetFailedAttempts(User user);
    void unlockUser(User user);
    boolean lockTimeExpired(User user);
    void lockUser(User user);
    void increaseFailedAttempts(User user);
}
