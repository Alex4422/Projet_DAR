package servlets;

import org.json.JSONObject;
import org.junit.Test;

public class MessagesServleTest extends ServletTest {
    @Test
    public void getShowMessages() {
        Users.registerTestUser();
        JSONObject loginResponse = Users.loginTestUser();


    }
}
