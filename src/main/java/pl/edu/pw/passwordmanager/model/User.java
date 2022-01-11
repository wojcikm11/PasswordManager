package pl.edu.pw.passwordmanager.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    @Column(name = "master_password")
    private String masterPassword;

    @OneToMany(mappedBy = "user",
                cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    private List<ServicePassword> servicePasswordList;

    @OneToMany(mappedBy = "user",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    private List<UserDevice> userDevices;

    public User(String username, String password, String masterPassword) {
        this.username = username;
        this.password = password;
        this.masterPassword = masterPassword;
    }

    public User() {

    }

    public void addServicePassword(ServicePassword servicePassword) {
        if (servicePasswordList == null) {
            servicePasswordList = new ArrayList<>();
        }
        servicePasswordList.add(servicePassword);
        servicePassword.setUser(this);
    }

    public void addDevice(UserDevice userDevice) {
        if (userDevice == null) {
            userDevices = new ArrayList<>();
        }
        userDevices.add(userDevice);
        userDevice.setUser(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public List<ServicePassword> getServicePasswordList() {
        return servicePasswordList;
    }

    public void setServicePasswordList(List<ServicePassword> servicePasswordList) {
        this.servicePasswordList = servicePasswordList;
    }

    public List<UserDevice> getUserDevices() {
        return userDevices;
    }

    public void setUserDevices(List<UserDevice> userDevices) {
        this.userDevices = userDevices;
    }
}
