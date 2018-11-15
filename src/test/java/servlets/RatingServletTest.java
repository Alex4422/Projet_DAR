package servlets;

import entities.Rating;
import entities.User;
import launch.Main;
import org.json.JSONObject;
import org.junit.Test;
import services.RatingService;
import services.UserSessionsService;
import services.errors.UnAuthenticatedUserException;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

import static junit.framework.TestCase.assertEquals;

public class RatingServletTest extends ServletTest {
    @Test
    public void rateShow() throws UnAuthenticatedUserException {
        Users.registerTestUser();
        JSONObject response = Users.loginTestUser();

        ClientBuilder.newClient()
                .target("http://localhost:8080")
                .path("/api/v1/auth/rating")
                .queryParam("userToken", response.getString("userToken"))
                .queryParam("showId", 1100)
                .queryParam("rating", 9)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(null);


        RatingService ratingService = new RatingService(Main.getFactory());
        User user = new UserSessionsService(Main.getFactory()).retrieveUser(response.getString("userToken"));
        Rating rating = ratingService.getRating(user, 1100);

        assertEquals(9, (int) rating.getRating());
    }
}
