package pl.edu.pw.passwordmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.edu.pw.passwordmanager.dto.AddServicePassword;
import pl.edu.pw.passwordmanager.dto.DecryptedPassword;
import pl.edu.pw.passwordmanager.dto.ShowPasswordRequest;
import pl.edu.pw.passwordmanager.exception.IncorrectMasterPasswordException;
import pl.edu.pw.passwordmanager.passwords.IServicePasswordService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;

@Controller
public class ServicePasswordController {

    private final IServicePasswordService passwordService;

    @Autowired
    public ServicePasswordController(IServicePasswordService passwordService) {
        this.passwordService = passwordService;
    }

    @GetMapping("/add_password")
    public String showAddServicePasswordForm(Model model) {
        AddServicePassword servicePassword = new AddServicePassword();
        model.addAttribute("servicePassword", servicePassword);
        return "add-password-form";
    }

    @PostMapping("/add_password")
    public String addServicePassword(@ModelAttribute("servicePassword") @Valid AddServicePassword addServicePassword, BindingResult result,
                                      Model model, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return "add-password-form";
        }

        try {
            passwordService.addPassword(addServicePassword);
            attributes.addFlashAttribute("passwordAddSuccessMessage", "New password has been successfully added.");
        } catch (IncorrectMasterPasswordException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "add-password-form";
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "add-password-form";
        }

        return "redirect:/dashboard";
    }

    @PostMapping("/show_password")
    public String sendMasterPassword(@ModelAttribute("showPasswordRequest") @Valid ShowPasswordRequest showPasswordRequest, BindingResult result, Model model,
                                     RedirectAttributes attributes) {
        if (result.hasErrors()) {
            attributes.addFlashAttribute("showPasswordErrorMessage", "Input data is incorrect");
            return "redirect:/dashboard";
        }

        DecryptedPassword decryptedPassword;
        try {
            decryptedPassword = passwordService.getDecryptedPassword(showPasswordRequest);
            attributes.addFlashAttribute("decryptedPassword", decryptedPassword);
        } catch (RuntimeException e) {
            attributes.addFlashAttribute("showPasswordErrorMessage", e.getMessage());
            return "redirect:/dashboard";
        }

        return "redirect:/show_password";
    }

    @GetMapping("/show_password")
    public String showGetPasswordForm(Model model) {
        DecryptedPassword decryptedPassword = (DecryptedPassword) model.asMap().get("decryptedPassword");
        if (decryptedPassword == null) {
            return "redirect:/dashboard";
        }
        model.addAttribute("decryptedPassword", decryptedPassword);
        return "show-password";
    }
}
