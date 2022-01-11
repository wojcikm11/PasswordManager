package pl.edu.pw.passwordmanager.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.edu.pw.passwordmanager.security.auth.UserRepository;
import pl.edu.pw.passwordmanager.dto.UserRegistration;
import pl.edu.pw.passwordmanager.exception.UserAlreadyExistsException;
import pl.edu.pw.passwordmanager.model.User;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserService implements IUserService {

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
        return userRepository.save(user);
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
