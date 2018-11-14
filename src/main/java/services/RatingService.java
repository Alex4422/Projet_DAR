package services;

import entities.Rating;
import entities.User;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import services.errors.InvalidRatingException;
import services.errors.UnAuthenticatedUserException;

import java.util.List;

public class RatingService extends ServiceBase {
    public RatingService(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public void clear() {
        super.clearTable("Rating");
    }

    public Rating rateShow(String userToken, Integer showId, Integer rating) throws UnAuthenticatedUserException, InvalidRatingException {
        User user = new UserSessionsService(getSessionFactory()).retrieveUser(userToken);
        Rating previousRating = getRating(user, showId);
        if (previousRating != null) {
            previousRating.setRating(rating);
            update(previousRating);
            return previousRating;
        }
        Rating newRating = new Rating(user, showId, rating);
        add(newRating);
        return newRating;
    }

    public Rating getRating(User user, Integer showId) {
        beginTransaction();
        Criteria criteria = getSession().createCriteria(Rating.class);
        criteria.add(Restrictions.eq("user", user));
        criteria.add(Restrictions.eq("showId", showId));
        List<Rating> result = criteria.list();
        getSession().getTransaction().commit();
        return first(result);
    }

    public Double getAverageRating(Integer showId) {
        beginTransaction();
        Criteria criteria = getSession().createCriteria(Rating.class);
        criteria.add(Restrictions.eq("showId", showId));
        List<Rating> result = criteria.list();
        getSession().getTransaction().commit();

        if (result.isEmpty()) {
            return null;
        }

        Double total = 0.0;
        for (Rating r: result) {
            total += r.getRating();
        }
        return total / result.size();
    }
}
