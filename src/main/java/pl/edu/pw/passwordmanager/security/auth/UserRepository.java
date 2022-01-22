package pl.edu.pw.passwordmanager.security.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.edu.pw.passwordmanager.model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    @Query("UPDATE User u SET u.failedLoginAttempts = ?1 WHERE u.id = ?2")
    @Modifying
    public void updateFailedAttempts(int failedLoginAttempts, Long userId);
}
