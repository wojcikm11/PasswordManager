package pl.edu.pw.passwordmanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping("/dashboard")
    public String showUserPasswordsList() {
        return "dashboard";
    }
}
