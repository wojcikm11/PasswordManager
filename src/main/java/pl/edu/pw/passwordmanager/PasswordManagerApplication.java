package pl.edu.pw.passwordmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import pl.edu.pw.passwordmanager.add_password.ServicePasswordRepository;
import pl.edu.pw.passwordmanager.security.auth.UserRepository;
import pl.edu.pw.passwordmanager.security.monitoring.DeviceRepository;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = { UserRepository.class, ServicePasswordRepository.class, DeviceRepository.class})
public class PasswordManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(PasswordManagerApplication.class, args);
    }

}
