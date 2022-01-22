package pl.edu.pw.passwordmanager.add_password;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.edu.pw.passwordmanager.model.ServicePassword;
import pl.edu.pw.passwordmanager.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServicePasswordRepository extends JpaRepository<ServicePassword, Long> {
    List<ServicePassword> findAllByUserId(Long userId);
}
