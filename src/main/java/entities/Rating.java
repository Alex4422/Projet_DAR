package entities;

import services.errors.InvalidRatingException;

import javax.persistence.*;

@Entity
public class Rating {
    @Id
    @Column()
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    Integer showId;

    @Column(nullable = false)
    Integer rating;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false)
    private User user;

    public Rating() { }

    public Rating(Integer showId, Integer rating, User user) {
        this.showId = showId;
        this.rating = rating;
        this.user = user;
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
}
