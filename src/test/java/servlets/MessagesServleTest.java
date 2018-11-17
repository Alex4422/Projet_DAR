package servlets;

import entities.Message;
import launch.Main;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import services.MessagesService;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

import static junit.framework.TestCase.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MessagesServleTest extends ServletTest {
    private static JSONObject loginResponse;

    @Test
    public void a_postMessages() {
        Users.registerTestUser();
        loginResponse = Users.loginTestUser();

        ClientBuilder.newClient()
                .target("http://localhost:8080")
                .path("/api/v1/auth/messages")
                .queryParam("userToken", loginResponse.get("userToken"))
                .queryParam("showId", 1100)
                .queryParam("content", "Hello World")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(null);

        Message m = new MessagesService(Main.getFactory()).getShowMessages("1100").get(0);
        assertEquals(Users.USERNAME, m.getUser().getUsername());
        assertEquals("Hello World", m.getContent());
    }

    @Test
    public void b_getShowMessages() {
        String messagesStr = ClientBuilder.newClient()
                .target("http://localhost:8080")
                .path("api/v1/auth/messages")
                .queryParam("userToken", loginResponse.get("userToken"))
                .queryParam("showId", 1100)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get(String.class);
        JSONArray messages = new JSONObject(messagesStr).getJSONArray("messages");
        assertEquals(1, messages.length());

        JSONObject msg = messages.getJSONObject(0);
        assertEquals("Hello World", msg.getString("content"));
        assertEquals(Users.USERNAME, msg.getString("username"));
    }
}
