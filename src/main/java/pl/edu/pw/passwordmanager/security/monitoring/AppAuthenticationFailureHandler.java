package pl.edu.pw.passwordmanager.security.monitoring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import pl.edu.pw.passwordmanager.model.User;
import pl.edu.pw.passwordmanager.user.IUserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static pl.edu.pw.passwordmanager.user.UserService.MAX_FAILED_LOGIN_ATTEMPTS;

@Component
public class AppAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    private static final String LOGIN_FAILED_URL = "/login?error";
    private IUserService userService;

    @Autowired
    public AppAuthenticationFailureHandler(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String username = request.getParameter("username");
        Optional<User> foundUser = userService.getByUsername(username);
        if (foundUser.isPresent()) {
            User user = foundUser.get();
            if (user.isAccountNonLocked()) {
                if (user.getFailedLoginAttempts() < MAX_FAILED_LOGIN_ATTEMPTS - 1) {
                    userService.increaseFailedAttempts(user);
                    exception = new BadCredentialsException("Incorrect username or password, try again.");
                } else {
                    userService.lockUser(user);
                    exception = new LockedException("Your account has been locked due to " + MAX_FAILED_LOGIN_ATTEMPTS + " failed attempts."
                            + " It will be unlocked after 24 hours.");
                }
            } else if (!user.isAccountNonLocked()) {
                if (userService.lockTimeExpired(user)) {
                    userService.unlockUser(user);
                    exception = new LockedException("Your account has been unlocked. Please try to login again.");
                }
            }
        } else {
            exception = new BadCredentialsException("Incorrect username or password, try again.");
        }
        super.setDefaultFailureUrl(LOGIN_FAILED_URL);
        super.onAuthenticationFailure(request, response, exception);
    }
}
