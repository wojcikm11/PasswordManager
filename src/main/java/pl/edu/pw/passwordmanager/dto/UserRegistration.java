package pl.edu.pw.passwordmanager.dto;

import pl.edu.pw.passwordmanager.security.validation.FieldMatch;
import pl.edu.pw.passwordmanager.security.validation.ValidPassword;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@FieldMatch.List({
    @FieldMatch(first = "password", second = "confirmationPassword", message = "The password fields must match"),
    @FieldMatch(first = "masterPassword", second = "confirmationMasterPassword", message = "The master password fields must match")
})
public class UserRegistration {
    @NotBlank
    @NotEmpty
    @Pattern(message="Username can only contain alphanumeric characters", regexp = "[a-zA-Z0-9 ]+")
    @Size(min = 3, max = 40, message = "Username must be from 3 to 40 characters long")
    private String username;

    @ValidPassword(message = "Password must from 10 to 30 characters long, must contain 1 digit, " +
            "1 upper case letter, 1 lower case letter and 1 special character")
    @NotBlank
    private String password;

    @NotBlank
    @NotEmpty
    private String confirmationPassword;

    @ValidPassword(message = "Password must be from 10 to 30 characters long, must contain 1 digit, " +
            "1 upper case letter, 1 lower case letter and 1 special character")
    @NotBlank
    private String masterPassword;

    @NotBlank
    @NotEmpty
    private String confirmationMasterPassword;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMasterPassword() {
        return masterPassword;
    }

    public void setMasterPassword(String masterPassword) {
        this.masterPassword = masterPassword;
    }

    public String getConfirmationPassword() {
        return confirmationPassword;
    }

    public void setConfirmationPassword(String confirmationPassword) {
        this.confirmationPassword = confirmationPassword;
    }

    public String getConfirmationMasterPassword() {
        return confirmationMasterPassword;
    }

    public void setConfirmationMasterPassword(String confirmationMasterPassword) {
        this.confirmationMasterPassword = confirmationMasterPassword;
    }
}
