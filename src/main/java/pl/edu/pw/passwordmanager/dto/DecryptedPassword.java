package pl.edu.pw.passwordmanager.dto;

import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;

public class DecryptedPassword {

    @NotBlank
    public String password;

    @URL
    @NotBlank
    public String url;

    public DecryptedPassword(String password, String url) {
        this.password = password;
        this.url = url;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
