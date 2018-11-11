package entities;

import javax.persistence.*;

@Entity
public class Watch {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    private Episode episode;

    public Watch() { }

    public Watch(User user, Episode episode) {
        this.user = user;
        this.episode = episode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Episode getEpisode() {
        return episode;
    }

    public void setEpisode(Episode episode) {
        this.episode = episode;
    }
}
