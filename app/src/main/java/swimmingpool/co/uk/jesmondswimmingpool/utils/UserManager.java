package swimmingpool.co.uk.jesmondswimmingpool.utils;

/**
 * Created by cody on 2017/11/14.
 */

public class UserManager {
    private static final UserManager ourInstance = new UserManager();

    public  static UserManager getInstance() {
        return ourInstance;
    }

    private UserManager() {
    }
    private Long id;

    private String name;

    private String username;

    private String password;

    private String note;

    private String address;

    private String phonenumber;

    private Integer power;

    @Override
    public String toString() {
        return "Tutor [id=" + id + ", name=" + name + ", username=" + username + ", password=" + password + ", note="
                + note + ", address=" + address + ", phonenumber=" + phonenumber + ", power=" + power + "]";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber == null ? null : phonenumber.trim();
    }

    public Integer getPower() {
        return power;
    }

    public void setPower(Integer power) {
        this.power = power;
    }

    public void clean() {
        setPhonenumber(null);
        setNote(null);
        setName(null);
        setId(null);
        setPower(null);
        setUsername(null);
        setPassword(null);
        setAddress(null);
    }
}
