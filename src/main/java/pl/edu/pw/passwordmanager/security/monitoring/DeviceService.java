package pl.edu.pw.passwordmanager.security.monitoring;


import com.blueconic.browscap.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pw.passwordmanager.dto.UnverifiedDevice;
import pl.edu.pw.passwordmanager.model.Device;
import pl.edu.pw.passwordmanager.security.auth.ApplicationUser;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class DeviceService implements IDeviceService {

    private DeviceRepository deviceRepository;
    private UserAgentParser parser;

    @Autowired
    public DeviceService(DeviceRepository deviceRepository) throws IOException, ParseException {
        this.deviceRepository = deviceRepository;
        this.parser =  new UserAgentService().loadParser(Arrays.asList(BrowsCapField.BROWSER, BrowsCapField.BROWSER_TYPE,
                BrowsCapField.BROWSER_MAJOR_VERSION,
                BrowsCapField.DEVICE_TYPE, BrowsCapField.PLATFORM, BrowsCapField.PLATFORM_VERSION,
                BrowsCapField.RENDERING_ENGINE_VERSION, BrowsCapField.RENDERING_ENGINE_NAME,
                BrowsCapField.PLATFORM_MAKER, BrowsCapField.RENDERING_ENGINE_MAKER));
    }

    @Override
    public void verifyDevice(ApplicationUser applicationUser, HttpServletRequest request) {
        String deviceDetails = getDeviceDetails(request.getHeader("user-agent"));
        Device device = findExistingDevice(applicationUser.getUser().getId(), deviceDetails);
        if (device == null) {
            Device newDevice = new Device();
            newDevice.setUser(applicationUser.getUser());
            newDevice.setDetails(deviceDetails);
            newDevice.setLastLoggedIn(new Date());
            newDevice.setVerified(false);
            deviceRepository.save(newDevice);
        } else {
            device.setLastLoggedIn(new Date());
            deviceRepository.save(device);
        }
    }

    @Override
    public void setDeviceAsVerified(Long deviceId) {
        deviceRepository.setDeviceAsVerified(deviceId);
    }

    @Override
    public List<UnverifiedDevice> findAllUnverifiedDevices(Long userId) {
        return deviceRepository.findAllByUserId(userId).stream().filter(device -> !device.isVerified())
                                                                .map(this::map).collect(Collectors.toList());
    }

    private String getDeviceDetails(String header) {
        Capabilities capabilities = parser.parse(header);
        if (capabilities == null) {
            return null;
        }
        return capabilities.getBrowser() + " " + capabilities.getBrowserType() + " " + capabilities.getBrowserMajorVersion() + " - "
                + capabilities.getDeviceType() + " - " + capabilities.getPlatform() + " " + capabilities.getPlatformVersion();
    }

    private Device findExistingDevice(Long userId, String deviceDetails) {
        List<Device> knownDevices = deviceRepository.findAllByUserId(userId);
        for (Device existingDevice : knownDevices) {
            if (existingDevice.getDetails().equals(deviceDetails)) {
                return existingDevice;
            }
        }
        return null;
    }

    private UnverifiedDevice map(Device device) {
        return new UnverifiedDevice(
          device.getId(),
          device.getDetails(),
          device.getLastLoggedIn().toString()
        );
    }
}
