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

    @Column(name = "user_session_uuid", nullable = false)
    private String uuid = "";

    @Column (name = "date", nullable = false)
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
}
