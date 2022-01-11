package pl.edu.pw.passwordmanager.service;

import pl.edu.pw.passwordmanager.dto.UserRegistration;
import pl.edu.pw.passwordmanager.model.User;

public interface IUserService {
    User register(UserRegistration user);
}
