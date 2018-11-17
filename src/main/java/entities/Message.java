package entities;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

@Entity
public class Message {
    @Id
    @Column()
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    private Date date;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Integer showId;

    @Column(nullable = false)
    private Double spoilerProbability = -1.0;

    @OneToOne()
    @JoinColumn(nullable = false)
    private User user;

    public Message() { }

    public Message(User user, Integer showId, String content) {
        this.date = Calendar.getInstance().getTime();
        this.content = content;
        this.user = user;
        this.showId = showId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getShowId() {
        return showId;
    }

    public void setShowId(Integer showId) {
        this.showId = showId;
    }

    public Double getSpoilerProbability() {
        return spoilerProbability;
    }

    public void setSpoilerProbability(Double spoilerProbability) {
        if (spoilerProbability > 1.0) {
            spoilerProbability = 1.0;
        } else if (spoilerProbability < 0) {
            spoilerProbability = 0.0;
        }
        this.spoilerProbability = spoilerProbability;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Message)) return false;
        Message message = (Message) o;
        return Objects.equals(id, message.id) &&
                Objects.equals(date, message.date) &&
                Objects.equals(content, message.content) &&
                Objects.equals(showId, message.showId) &&
                Objects.equals(spoilerProbability, message.spoilerProbability) &&
                Objects.equals(user, message.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, content, showId, spoilerProbability, user);
    }
}
