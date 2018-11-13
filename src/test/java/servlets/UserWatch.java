package servlets;

import entities.User;
import launch.Main;
import org.junit.Test;
import services.UsersService;
import services.errors.NonExistingUserException;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

import static servlets.Users.PASSWORD;
import static servlets.Users.USERNAME;

public class UserWatch extends ServletTest {
    @Test
    public void registerWatch() throws NonExistingUserException {
        Users.registerTestUser();
        Users.loginTestUser();

        ClientBuilder.newClient()
                .target("http://localhost:8080")
                .path("/api/v1/users")
                .queryParam("username", "username")
                .queryParam("password", "password")
                .request(MediaType.APPLICATION_JSON_TYPE);

        User u = new UsersService(Main.getFactory()).getUser(USERNAME, UsersService.hashPassWord(PASSWORD));

    }
}
