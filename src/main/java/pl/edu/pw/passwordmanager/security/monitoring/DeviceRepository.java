package pl.edu.pw.passwordmanager.security.monitoring;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.edu.pw.passwordmanager.model.Device;


import java.util.List;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {
    List<Device> findAllByUserId(Long userId);
    @Modifying
    @Query("UPDATE Device d SET d.verified = true WHERE d.id = ?1")
    void setDeviceAsVerified(Long deviceId);
}
