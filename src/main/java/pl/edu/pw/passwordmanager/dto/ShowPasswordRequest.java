package pl.edu.pw.passwordmanager.dto;

import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ShowPasswordRequest {

    @NotNull
    private Long servicePasswordId;

    @NotBlank
    @NotEmpty
    private String masterPassword;

    @NotBlank
    @NotEmpty
    @URL
    @Max(value = 2083, message = "Too long URL address")
    private String url;

    public ShowPasswordRequest(Long servicePasswordId, String masterPassword, String url) {
        this.servicePasswordId = servicePasswordId;
        this.masterPassword = masterPassword;
        this.url = url;
    }

    public ShowPasswordRequest() {

    }

    public Long getServicePasswordId() {
        return servicePasswordId;
    }

    public void setServicePasswordId(Long servicePasswordId) {
        this.servicePasswordId = servicePasswordId;
    }

    public String getMasterPassword() {
        return masterPassword;
    }

    public void setMasterPassword(String masterPassword) {
        this.masterPassword = masterPassword;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
