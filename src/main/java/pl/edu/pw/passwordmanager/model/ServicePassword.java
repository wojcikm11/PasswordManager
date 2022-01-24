package pl.edu.pw.passwordmanager.model;

import javax.persistence.*;

@Entity
@Table(name = "service_password")
public class ServicePassword {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;

    private String password;

    private byte[] iv;

    private String salt;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="user_id")
    private User user;

    public ServicePassword() {
    }

    public ServicePassword(String url, String password) {
        this.url = url;
        this.password = password;
    }

    public ServicePassword(String url, String password, User user) {
        this.url = url;
        this.password = password;
        this.user = user;
    }

    public ServicePassword(String url, String password, User user, byte[] iv, String salt) {
        this.url = url;
        this.password = password;
        this.user = user;
        this.iv = iv;
        this.salt = salt;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public byte[] getIv() {
        return iv;
    }

    public void setIv(byte[] iv) {
        this.iv = iv;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}
