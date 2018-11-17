package servlet;

import entities.User;
import entities.UserSession;
import launch.Main;
import moviedb.Search;
import org.json.JSONObject;
import services.UserSessionsService;
import services.UsersService;
import services.errors.UnAuthenticatedUserException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.ClientErrorException;
import java.io.IOException;

import static servlet.Util.failWith;

@WebServlet(
        name = "ShowDetails",
        urlPatterns = {"/api/v1/show"}
)
public class ShowDetails extends ServletBase {
    @Override
    public JSONObject processGet() throws Exception {
        Integer id = getIntegerParameter("id");
        if (getRequest().getParameter("userToken") != null) {
            String userToken = getStringParameter("userToken");
            UserSessionsService userSessionsService = new UserSessionsService(Main.getFactory());
            userSessionsService.refreshSession(userToken);
            User user = userSessionsService.retrieveUser(userToken);
            return Search.showDetails(id.toString(), user);
        } else {
            return Search.showDetails(id.toString());
        }
    }
}
