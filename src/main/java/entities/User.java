package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;

@Entity
@Table(name = "people")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "pseudo", nullable = false, unique = true)
    private String username;

    @Basic
    @Column(name = "password", nullable = false)
    private byte[] password;

    public User() { }

    public User(String username, byte[] password) {
        id = null;
        this.username = username;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public byte[] getPassword() {
        return password;
    }

    public void setPassword(byte[] password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof User)) {
            return false;
        }

        User rhs = (User) other;

        return rhs.username.equals(username) && Arrays.equals(rhs.password, password);
    }
}
