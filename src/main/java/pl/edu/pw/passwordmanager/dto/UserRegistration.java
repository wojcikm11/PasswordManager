package pl.edu.pw.passwordmanager.dto;

import pl.edu.pw.passwordmanager.security.validation.FieldMatch;
import pl.edu.pw.passwordmanager.security.validation.ValidPassword;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@FieldMatch.List({
    @FieldMatch(first = "password", second = "confirmationPassword", message = "The password fields must match"),
    @FieldMatch(first = "masterPassword", second = "confirmationMasterPassword", message = "The master password fields must match")
})
public class UserRegistration {
    @NotNull
    @NotEmpty
    private String username;

    @ValidPassword(message = "Password must be at least 8 digits long, must contain 1 digit, " +
            "1 upper case letter, 1 lower case letter and 1 special character")
    @NotNull
    private String password;

    @NotNull
    private String confirmationPassword;

    @ValidPassword(message = "Master password must be at least 8 digits long, must contain 1 digit, " +
            "1 upper case letter, 1 lower case letter and 1 special character")
    @NotNull
    private String masterPassword;

    @NotNull
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
