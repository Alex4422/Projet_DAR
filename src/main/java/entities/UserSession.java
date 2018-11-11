package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "user_sessions")
public class UserSession implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String uuid = "";

    @Column (nullable = false)
    private Date date;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    User user;

    public UserSession() {}

    public UserSession(User user) {
        this.user = user;
        this.uuid = generateUuid();
        this.date = Calendar.getInstance().getTime();
    }

    private static String generateUuid() {
        return UUID.randomUUID().toString();
    }

    public String getUuid() {
        return uuid;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
