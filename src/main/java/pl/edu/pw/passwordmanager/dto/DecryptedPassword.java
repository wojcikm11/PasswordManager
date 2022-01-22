package pl.edu.pw.passwordmanager.dto;

public class DecryptedPassword {
    public String password;
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
