package app;

public class User {
    private String username;
    private String password;
    private Integer id = -1;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username, String password, int id) {
        this.username = username;
        this.password = password;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object other) {
        if (! (other instanceof User)) {
            return false;
        }

        User rhs = (User) other;

        return rhs.username.equals(username) && rhs.password.equals(password);
    }
}
