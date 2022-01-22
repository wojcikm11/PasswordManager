package pl.edu.pw.passwordmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.edu.pw.passwordmanager.add_password.ServicePasswordRepository;
import pl.edu.pw.passwordmanager.dto.AddServicePassword;
import pl.edu.pw.passwordmanager.dto.DecryptedPassword;
import pl.edu.pw.passwordmanager.dto.ServicePasswordListItem;
import pl.edu.pw.passwordmanager.dto.ShowPasswordRequest;
import pl.edu.pw.passwordmanager.exception.IncorrectMasterPasswordException;
import pl.edu.pw.passwordmanager.exception.InvalidUserException;
import pl.edu.pw.passwordmanager.model.ServicePassword;
import pl.edu.pw.passwordmanager.model.User;
import pl.edu.pw.passwordmanager.security.AESEncoder;
import pl.edu.pw.passwordmanager.security.auth.ApplicationUser;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.transaction.Transactional;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ServicePasswordService implements IServicePasswordService {

    private final ServicePasswordRepository servicePasswordRepository;
    private final PasswordEncoder passwordEncoder;
    private final AESEncoder aesEncoder;
    private byte[] encryptIv;
    private String encryptSalt;

    @Autowired
    public ServicePasswordService(ServicePasswordRepository servicePasswordRepository, PasswordEncoder passwordEncoder,
                                  AESEncoder aesEncoder) {
        this.servicePasswordRepository = servicePasswordRepository;
        this.passwordEncoder = passwordEncoder;
        this.aesEncoder = aesEncoder;
        this.encryptIv = new byte[16];
    }

    @Override
    public List<ServicePasswordListItem> getUserPasswords(Long userId) {
        return servicePasswordRepository.findAllByUserId(userId).stream().map(this::map).collect(Collectors.toList());
    }

    @Override
    public ServicePassword addPassword(AddServicePassword addPassword) {
        if (masterPasswordIncorrect(addPassword.getMasterPassword())) {
            throw new IncorrectMasterPasswordException("Incorrect master password");
        }
        try {
            String encryptServicePassword = getEncryptServicePassword(addPassword);
            addPassword.setPassword(encryptServicePassword);
        } catch (Exception e) {
            throw new RuntimeException("There was an error with password encryption.");
        }
        return servicePasswordRepository.save(map(addPassword, encryptIv, encryptSalt));
    }


    private String getEncryptServicePassword(AddServicePassword addPassword) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
        String salt = aesEncoder.generateRandomSalt();
        encryptSalt = salt;
        SecretKey vaultKey = aesEncoder.getVaultKeyFromPassword(addPassword.getMasterPassword(), salt);

        IvParameterSpec randomIv = aesEncoder.generateRandomIv();
        encryptIv = randomIv.getIV();
        String encryptedPassword = aesEncoder.encrypt(addPassword.getPassword(), vaultKey, randomIv);
        return encryptedPassword;
    }

    @Override
    public DecryptedPassword getDecryptedPassword(ShowPasswordRequest showPasswordRequest) {
        Optional<ServicePassword> servicePassword = servicePasswordRepository.findById(showPasswordRequest.getServicePasswordId());

        if (!validUser(showPasswordRequest, servicePassword)) {
            throw new InvalidUserException("You do not have permissions for this resource");
        }

        if (masterPasswordIncorrect(showPasswordRequest.getMasterPassword())) {
            throw new IncorrectMasterPasswordException("Incorrect master password");
        }

        return servicePassword.map(
                (servicePasswordValue -> {
                    try {
                        SecretKey vaultKey = aesEncoder.getVaultKeyFromPassword(showPasswordRequest.getMasterPassword(), servicePasswordValue.getSalt());
                        String encryptedPassword = servicePasswordValue.getPassword();
                        String decryptedPassword = aesEncoder.decrypt(encryptedPassword, vaultKey, new IvParameterSpec(servicePasswordValue.getIv()));
                        return new DecryptedPassword(decryptedPassword, showPasswordRequest.getUrl());
                    } catch (Exception e) {
                        throw new RuntimeException(e.getMessage());
                    }
                })).orElseThrow(() ->  new RuntimeException("error"));
    }

    private boolean validUser(ShowPasswordRequest showPasswordRequest, Optional<ServicePassword> servicePassword) {
        ApplicationUser applicationUser = getApplicationUser();
        System.out.println(applicationUser.getUser().getId());
        if (servicePassword.isEmpty()) {
            return true;
        }
        return servicePassword.map(
                (servicePasswordValue) -> {
                    Long ownerId = servicePasswordValue.getUser().getId();
                    Long requestingUserId = applicationUser.getUser().getId();
                    System.out.println(ownerId);
                    return ownerId.equals(requestingUserId);
                }).get();
    }

    private boolean masterPasswordIncorrect(String masterPasswordToCheck) {
        ApplicationUser applicationUser = getApplicationUser();
        User user = applicationUser.getUser();
        String userMasterPassword = user.getMasterPassword();
        return !passwordEncoder.matches(masterPasswordToCheck, userMasterPassword);
    }

    private ApplicationUser getApplicationUser() {
        return (ApplicationUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    private ServicePassword map(AddServicePassword addServicePassword, byte[] iv, String salt) {
        return new ServicePassword (
            addServicePassword.getUrl(),
            addServicePassword.getPassword(),
            getApplicationUser().getUser(),
            iv,
            salt
        );
    }

    private ServicePasswordListItem map(ServicePassword servicePassword) {
        return new ServicePasswordListItem (
                servicePassword.getId(),
                servicePassword.getUrl()
        );
    }
}
