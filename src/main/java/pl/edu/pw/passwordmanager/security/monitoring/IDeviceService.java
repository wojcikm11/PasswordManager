package pl.edu.pw.passwordmanager.security.monitoring;

import pl.edu.pw.passwordmanager.dto.UnverifiedDevice;
import pl.edu.pw.passwordmanager.model.Device;
import pl.edu.pw.passwordmanager.security.auth.ApplicationUser;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface IDeviceService {
    void verifyDevice(ApplicationUser applicationUser, HttpServletRequest request);
    void setDeviceAsVerified(Long deviceId);
    List<UnverifiedDevice> findAllUnverifiedDevices(Long userId);
}
