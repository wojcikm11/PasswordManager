package pl.edu.pw.passwordmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.edu.pw.passwordmanager.dto.UserRegistration;
import pl.edu.pw.passwordmanager.exception.UserAlreadyExistsException;
import pl.edu.pw.passwordmanager.user.IUserService;
import pl.edu.pw.passwordmanager.user.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

@Controller
public class UserController {

    private final IUserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String getLoginView(@RequestParam Optional<String> error, Model model) {
        return "login";
    }

    @GetMapping("/register")
    public String getRegistrationView(Model model) {
        UserRegistration userRegistration = new UserRegistration();
        model.addAttribute("user", userRegistration);
        return "register-form";
    }

    @PostMapping("/register")
    public String registerUserAccount(@ModelAttribute("user") @Valid UserRegistration userRegistration, BindingResult result,
                                      HttpServletRequest request, Model model) {
        if (result.hasErrors()) {
            return "register-form";
        }

        try {
            userService.register(userRegistration);
            if (SecurityContextHolder.getContext().getAuthentication() != null) {
                SecurityContextHolder.getContext().setAuthentication(null);
            }
            request.login(userRegistration.getUsername(), userRegistration.getPassword());
        } catch (UserAlreadyExistsException | ServletException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "register-form";
        }

        return "redirect:/dashboard";
    }
}
