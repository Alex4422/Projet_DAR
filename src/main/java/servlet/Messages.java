package servlet;

import launch.Main;
import org.json.JSONObject;
import services.MessagesService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static servlet.Util.failWith;
import static servlet.Util.successWith;

@WebServlet(
        name = "MessagesServlet",
        urlPatterns = {"/api/v1/auth/messages"}
)
public class Messages extends ServletBase {
    @Override
    public JSONObject processGet() throws Exception {
        Integer showId = getIntegerParameter("showId");
        JSONObject jsonResponse = new JSONObject();
        List<JSONObject> messages = new MessagesService(Main.getFactory()).getJSONShowMessages(showId.toString());
        jsonResponse.put("messages", messages);
        return jsonResponse;
    }

    @Override
    public JSONObject processPost() throws Exception {
        String userToken = getStringParameter("userToken");
        Integer showId = getIntegerParameter("showId");
        String content = getStringParameter("content");
        new MessagesService(Main.getFactory()).postMessage(userToken, content, showId.toString());
        return null;
    }
}
