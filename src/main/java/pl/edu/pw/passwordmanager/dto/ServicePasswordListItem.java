package pl.edu.pw.passwordmanager.dto;

import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ServicePasswordListItem {

    @NotNull
    private Long id;

    @URL
    @NotBlank
    private String url;

    public ServicePasswordListItem(Long id, String url) {
        this.id = id;
        this.url = url;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
