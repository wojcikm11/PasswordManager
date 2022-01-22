package pl.edu.pw.passwordmanager.dto;

import java.util.Date;

public class UnverifiedDevice {
    private Long id;
    private String details;
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
