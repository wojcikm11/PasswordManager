package pl.edu.pw.passwordmanager.service;

import org.springframework.stereotype.Service;
import pl.edu.pw.passwordmanager.dto.AddServicePassword;
import pl.edu.pw.passwordmanager.dto.DecryptedPassword;
import pl.edu.pw.passwordmanager.dto.ServicePasswordListItem;
import pl.edu.pw.passwordmanager.dto.ShowPasswordRequest;
import pl.edu.pw.passwordmanager.model.ServicePassword;

import java.util.List;

public interface IServicePasswordService {
    ServicePassword addPassword(AddServicePassword password);
    List<ServicePasswordListItem> getUserPasswords(Long userId);
    DecryptedPassword getDecryptedPassword(ShowPasswordRequest showPasswordRequest);
}
