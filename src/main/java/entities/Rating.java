package entities;

import services.errors.InvalidRatingException;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Rating {
    @Id
    @Column()
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    private Integer showId;

    @Column(nullable = false)
    private Integer rating;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false)
    private User user;

    public Rating() { }

    public Rating(User user, Integer showId, Integer rating) throws InvalidRatingException {
        this.showId = showId;
        this.user = user;
        setRating(rating);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getShowId() {
        return showId;
    }

    public void setShowId(Integer showId) {
        this.showId = showId;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(int rating) throws InvalidRatingException {
        if (rating < 0 || rating > 10) {
            throw new InvalidRatingException();
        }
        this.rating = rating;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Rating)) return false;
        Rating rating1 = (Rating) o;
        return Objects.equals(showId, rating1.showId) &&
                Objects.equals(rating, rating1.rating) &&
                Objects.equals(user, rating1.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(showId, rating, user);
    }
}
