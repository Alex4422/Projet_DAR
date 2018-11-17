package servlet;

import launch.Main;
import org.json.JSONObject;
import services.UserSessionsService;

import javax.servlet.annotation.WebServlet;

@WebServlet(
        name = "Logout",
        urlPatterns =  {"/api/v1/auth/logout"}
)
public class Logout extends ServletBase{
    @Override
    public JSONObject processPost() throws Exception {
        String userToken = getStringParameter("userToken");
        UserSessionsService userSessionsService = new UserSessionsService(Main.getFactory());
        userSessionsService.endSession(userToken);
        return null;
    }
}
