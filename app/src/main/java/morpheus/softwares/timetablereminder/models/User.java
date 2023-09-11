package morpheus.softwares.timetablereminder.models;

public class User {
    private int id;
    private String username, password, status;

    public User(int id, String username, String password, String status) {
        setId(id);
        setUsername(username);
        setPassword(password);
        setStatus(status);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}