package pl.edu.pw.passwordmanager.security.monitoring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import pl.edu.pw.passwordmanager.model.User;
import pl.edu.pw.passwordmanager.security.auth.ApplicationUser;
import pl.edu.pw.passwordmanager.user.IUserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component("appAuthenticationSuccessHandler")
public class AppAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private IDeviceService deviceService;
    private RedirectStrategy redirectStrategy;
    private IUserService userService;

    @Autowired
    public AppAuthenticationSuccessHandler(IDeviceService deviceService, IUserService userService) {
        this.deviceService = deviceService;
        this.userService = userService;
        this.redirectStrategy = new DefaultRedirectStrategy();
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        loginNotification(authentication, request);
        resetFailedAttempts(authentication);
        redirect(request, response);
    }

    private void resetFailedAttempts(Authentication authentication) {
        ApplicationUser applicationUser = (ApplicationUser)authentication.getPrincipal();
        User user = applicationUser.getUser();
        userService.resetFailedAttempts(user);
    }

    private void loginNotification(Authentication authentication, HttpServletRequest request) {
        try {
            deviceService.verifyDevice(((ApplicationUser)authentication.getPrincipal()), request);
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void redirect(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String targetUrl = "/dashboard";

        if (response.isCommitted()) {
            System.out.println(
                    "Response has already been committed. Unable to redirect to "
                            + targetUrl);
            return;
        }

        redirectStrategy.sendRedirect(request, response, targetUrl);
    }
}
