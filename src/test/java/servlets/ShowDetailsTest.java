package servlets;

import launch.Main;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import services.RatingService;
import services.errors.InvalidRatingException;
import services.errors.UnAuthenticatedUserException;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

import static junit.framework.TestCase.assertEquals;

public class ShowDetailsTest extends ServletTest {
    @Test(expected = JSONException.class)
    public void showDetails() {
        String responseStr = ClientBuilder.newClient()
                .target("http://localhost:8080")
                .path("/api/v1/show")
                .queryParam("id","1100")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get(String.class);
        JSONObject showDetails = new JSONObject(responseStr);
        assertEquals("How I Met Your Mother", showDetails.getString("name"));
        showDetails.get("rating");
    }

    @Test
    public void showDetailsWithUserInfo() throws InvalidRatingException, UnAuthenticatedUserException {
        Users.registerTestUser();
        JSONObject loginResponse = Users.loginTestUser();

        new RatingService(Main.getFactory()).rateShow(loginResponse.getString("userToken"), 1100, 9);

        String responseStr = ClientBuilder.newClient()
                .target("http://localhost:8080")
                .path("/api/v1/show")
                .queryParam("id","1100")
                .queryParam("userToken", loginResponse.getString("userToken"))
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get(String.class);
        JSONObject showDetails = new JSONObject(responseStr);
        assertEquals(9, showDetails.getInt("rating"));
        assertEquals(9.0, showDetails.getDouble("averageRating"));
    }
}
