package pl.edu.pw.passwordmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.edu.pw.passwordmanager.dto.ServicePasswordListItem;
import pl.edu.pw.passwordmanager.dto.ShowPasswordRequest;
import pl.edu.pw.passwordmanager.dto.UnverifiedDevice;
import pl.edu.pw.passwordmanager.security.auth.ApplicationUser;
import pl.edu.pw.passwordmanager.security.monitoring.IDeviceService;
import pl.edu.pw.passwordmanager.passwords.IServicePasswordService;

import java.util.List;

@Controller
public class DashboardController {

    private final IServicePasswordService passwordService;
    private final IDeviceService deviceService;

    @Autowired
    public DashboardController(IServicePasswordService passwordService, IDeviceService deviceService) {
        this.passwordService = passwordService;
        this.deviceService = deviceService;
    }

    @GetMapping("/dashboard")
    public String showUserPasswordsList(Model model) {
        ApplicationUser applicationUser = (ApplicationUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<ServicePasswordListItem> servicePasswordList = passwordService.getUserPasswords(applicationUser.getUser().getId());
        model.addAttribute("servicePasswordList", servicePasswordList);
        ShowPasswordRequest showPasswordRequest = new ShowPasswordRequest();
        model.addAttribute("showPasswordRequest", showPasswordRequest);
        String showPasswordErrorMessage = (String) model.asMap().get("showPasswordErrorMessage");
        model.addAttribute("showPasswordErrorMessage", showPasswordErrorMessage);
        List<UnverifiedDevice> unverifiedDevices = deviceService.findAllUnverifiedDevices(applicationUser.getUser().getId());
        model.addAttribute("unverifiedDevices", unverifiedDevices);
        return "dashboard";
    }
}
