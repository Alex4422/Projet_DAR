package servlets;

import entities.User;
import entities.UserSession;
import launch.Main;
import org.json.JSONObject;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import services.UsersService;
import services.errors.NonExistingUserException;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.DatatypeConverter;

import static junit.framework.TestCase.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Users extends ServletTest {
    public static String USERNAME = "username";
    public static String PASSWORD = "password";

    @Test
    public void a_registerUser() throws NonExistingUserException {
        registerTestUser();
        User u = new UsersService(Main.getFactory()).getUser(USERNAME, UsersService.hashPassWord(PASSWORD));
        assertEquals(USERNAME, u.getUsername());
    }

    @Test
    public void b_loginUser() throws NonExistingUserException {
        loginTestUser();
        User u = new UsersService(Main.getFactory()).getUser(USERNAME, UsersService.hashPassWord(PASSWORD));
        assertNotNull(u.getSession());
    }

    public static void registerTestUser() {
        ClientBuilder.newClient()
                .target("http://localhost:8080")
                .path("/api/v1/users")
                .queryParam("username", USERNAME)
                .queryParam("password", DatatypeConverter.printHexBinary(UsersService.hashPassWord(PASSWORD)))
                .request()
                .post(null);
    }

    public static JSONObject loginTestUser() {
        String response = ClientBuilder.newClient()
                .target("http://localhost:8080")
                .path("/api/v1/login")
                .queryParam("username", USERNAME)
                .queryParam("password", DatatypeConverter.printHexBinary(UsersService.hashPassWord(PASSWORD)))
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get(String.class);
        return new JSONObject(response);
    }
}
