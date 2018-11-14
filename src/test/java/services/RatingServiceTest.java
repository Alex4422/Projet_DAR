package services;

import entities.Rating;
import entities.UserSession;
import org.junit.Test;
import services.errors.InvalidRatingException;
import services.errors.NonExistingUserException;
import services.errors.UnAuthenticatedUserException;
import services.errors.UserExistsException;

import static junit.framework.TestCase.assertEquals;

public class RatingServiceTest extends TestWithDb {
    @Test
    public void rateShow() throws UserExistsException, NonExistingUserException, InvalidRatingException, UnAuthenticatedUserException {
        UserSession userSession = registerAndLoginUser();
        RatingService ratingService = new RatingService(getSessionFactory());
        Rating rating = ratingService.rateShow(userSession.getUuid(), 1100, 9);

        assertEquals(userSession.getUser(), rating.getUser());
        assertEquals(1100, (int) rating.getShowId());
        assertEquals(rating, ratingService.getRating(userSession.getUser(), 1100));
    }

    @Test
    public void rerateRatedShow() throws UserExistsException, NonExistingUserException, InvalidRatingException, UnAuthenticatedUserException {
        UserSession userSession = registerAndLoginUser();
        RatingService ratingService = new RatingService(getSessionFactory());
        ratingService.rateShow(userSession.getUuid(), 1100, 9);
        ratingService.rateShow(userSession.getUuid(), 1100, 2);

        Rating finalRating = ratingService.getRating(userSession.getUser(), 1100);
        assertEquals(2, (int) finalRating.getRating());
    }

    @Test
    public void averageShowRating() throws UserExistsException, NonExistingUserException, InvalidRatingException, UnAuthenticatedUserException {
        UserSession u1 = registerAndLoginUser("u1", "p");
        UserSession u2 = registerAndLoginUser("u2", "p");
        RatingService ratingService = new RatingService(getSessionFactory());

        ratingService.rateShow(u1.getUuid(), 1100, 10);
        ratingService.rateShow(u2.getUuid(), 1100, 5);

        Double averageRating = ratingService.getAverageRating(1100);

        assertEquals(7.5, averageRating);
    }
}
