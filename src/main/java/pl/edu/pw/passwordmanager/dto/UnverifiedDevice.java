package pl.edu.pw.passwordmanager.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class UnverifiedDevice {

    @NotNull
    private Long id;

    @NotBlank
    private String details;

    @NotBlank
    private String lastLoggedIn;

    public UnverifiedDevice(Long id, String details, String lastLoggedIn) {
        this.id = id;
        this.details = details;
        this.lastLoggedIn = lastLoggedIn;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getLastLoggedIn() {
        return lastLoggedIn;
    }

    public void setLastLoggedIn(String lastLoggedIn) {
        this.lastLoggedIn = lastLoggedIn;
    }
}
