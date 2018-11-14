package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "people")
public class User implements Serializable {
    @Id
    @Column()
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer userId;

    @Column(nullable = false, unique = true)
    private String username;

    @Basic
    @Column(nullable = false)
    private byte[] password;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private UserSession session;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "User_Episodes",
            joinColumns = { @ JoinColumn(name = "userId") },
            inverseJoinColumns = { @JoinColumn(name = "id") }
    )
    Set<Episode> episodes = new HashSet<>();

    public User() { }

    public User(String username, byte[] password) {
        userId = null;
        this.username = username;
        this.password = password;
    }

    public Integer getId() {
        return userId;
    }

    public void setId(Integer id) {
        this.userId = id;
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

    private void setSession(UserSession session) {
        this.session = session;
    }

    public UserSession getSession() {
        return session;
    }

    public Set<Episode> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(Set<Episode> episodes) {
        this.episodes = episodes;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof User)) {
            return false;
        }

        User rhs = (User) other;

        return rhs.getUsername().equals(getUsername()) && Arrays.equals(rhs.getPassword(), getPassword());
    }
}
