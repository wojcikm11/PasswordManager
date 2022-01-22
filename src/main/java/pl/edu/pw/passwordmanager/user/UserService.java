package pl.edu.pw.passwordmanager.user;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.edu.pw.passwordmanager.dto.UserRegistration;
import pl.edu.pw.passwordmanager.exception.UserAlreadyExistsException;
import pl.edu.pw.passwordmanager.model.User;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Optional;

@Service
@Transactional
public class UserService implements IUserService {
    public static final int MAX_FAILED_LOGIN_ATTEMPTS = 5;
    private static final long LOCK_TIME_DURATION = 24 * 60 * 60 * 1000;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(UserRegistration userRegistration) {
        if (usernameExists(userRegistration.getUsername())) {
            throw new UserAlreadyExistsException("There is an account with that username: " + userRegistration.getUsername());
        }

        User user = map(userRegistration);
        user.setAccountNonLocked(true);
        return userRepository.save(user);
    }

    @Override
    public void unlockUser(User user) {
        user.setAccountNonLocked(true);
        user.setLockTime(null);
        user.setFailedLoginAttempts(0);
        userRepository.save(user);
    }

    @Override
    public boolean lockTimeExpired(User user) {
        long lockTime = user.getLockTime().getTime();
        long currentTime = System.currentTimeMillis();
        return lockTime + LOCK_TIME_DURATION < currentTime;
    }

    @Override
    public void lockUser(User user) {
        user.setAccountNonLocked(false);
        user.setLockTime(new Date());
        userRepository.save(user);
    }

    @Override
    public void increaseFailedAttempts(User user) {
        int increasedFailedAttempts = user.getFailedLoginAttempts() + 1;
        userRepository.updateFailedAttempts(increasedFailedAttempts, user.getId());
    }

    @Override
    public Optional<User> getByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void resetFailedAttempts(User user) {
        int resetFailedAttempts = 0;
        userRepository.updateFailedAttempts(resetFailedAttempts, user.getId());
    }

    private boolean usernameExists(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    private User map(UserRegistration userRegistration) {
        return new User (
            userRegistration.getUsername(),
            passwordEncoder.encode(userRegistration.getPassword()),
            passwordEncoder.encode(userRegistration.getMasterPassword())
        );
    }
}
