package pl.edu.pw.passwordmanager.dto;

import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class AddServicePassword {

    @NotBlank
    @NotEmpty
    @URL
    private String url;

    @NotBlank
    @NotEmpty
    private String password;

    @NotBlank
    @NotEmpty
    private String masterPassword;

    public AddServicePassword() {
    }

    public AddServicePassword(String url, String password) {
        this.url = url;
        this.password = password;
    }

    public AddServicePassword(String url, String password, String masterPassword) {
        this.url = url;
        this.password = password;
        this.masterPassword = masterPassword;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
}
