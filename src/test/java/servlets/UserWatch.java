package servlets;

import entities.Episode;
import entities.User;
import launch.Main;
import org.json.JSONObject;
import org.junit.Test;
import services.EpisodesService;
import services.UsersService;
import services.WatchService;
import services.errors.NonExistingUserException;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

import static junit.framework.TestCase.assertTrue;
import static servlets.Users.PASSWORD;
import static servlets.Users.USERNAME;

public class UserWatch extends ServletTest {
    @Test
    public void registerWatch() throws NonExistingUserException {
        Users.registerTestUser();
        JSONObject loginReeponse = Users.loginTestUser();

        ClientBuilder.newClient()
                .target("http://localhost:8080")
                .path("/api/v1/auth/userWatch")
                .queryParam("userToken", loginReeponse.getString("userToken"))
                .queryParam("showId", 1100)
                .queryParam("seasonNumber", 6)
                .queryParam("episodeId", 1)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(null);

        Episode episode = new EpisodesService(Main.getFactory()).addEpisodeIfNotExists(1100, 6, 1);
        User u = new UsersService(Main.getFactory()).getUser(USERNAME, UsersService.hashPassWord(PASSWORD));
        assertTrue(u.getEpisodes().contains(episode));
    }
}
