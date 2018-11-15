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
    public void processGet(HttpServletRequest req, HttpServletResponse res) throws Exception {
        Integer showId = getIntegerParameter(req, "showId");
        JSONObject jsonResponse = new JSONObject();

        try {
            List<JSONObject> messages = new MessagesService(Main.getFactory()).getJSONShowMessages(showId);
            jsonResponse.put("messages", messages);
            successWith(res, jsonResponse);
            return;
        } catch (NumberFormatException e) {
            jsonResponse.put("error", "Invalid showId format");
            failWith(res, jsonResponse);
            return;
        }
    }
}
