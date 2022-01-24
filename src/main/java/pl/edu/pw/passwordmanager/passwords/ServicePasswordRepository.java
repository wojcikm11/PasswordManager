package pl.edu.pw.passwordmanager.passwords;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pw.passwordmanager.model.ServicePassword;

import java.util.List;

@Repository
public interface ServicePasswordRepository extends JpaRepository<ServicePassword, Long> {
    List<ServicePassword> findAllByUserId(Long userId);
}
