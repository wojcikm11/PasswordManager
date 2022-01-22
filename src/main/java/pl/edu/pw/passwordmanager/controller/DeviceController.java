package pl.edu.pw.passwordmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.edu.pw.passwordmanager.dto.ShowPasswordRequest;
import pl.edu.pw.passwordmanager.dto.UnverifiedDevice;
import pl.edu.pw.passwordmanager.security.monitoring.IDeviceService;

import javax.validation.Valid;

@Controller
public class DeviceController {
    private final IDeviceService deviceService;

    @Autowired
    public DeviceController(IDeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @PostMapping("/verify_device")
    public String setDeviceAsVerified(@ModelAttribute("unverifiedDevice") UnverifiedDevice unverifiedDevice) {
        deviceService.setDeviceAsVerified(unverifiedDevice.getId());
        return "redirect:/dashboard";
    }
}
