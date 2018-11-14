package servlet;

import entities.User;
import entities.UserSession;
import launch.Main;
import moviedb.Search;
import org.json.JSONObject;
import services.UserSessionsService;
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
public class ShowDetails extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException {
        String idStr = req.getParameter("id");
        String userToken = req.getParameter("userToken");
        JSONObject jsonResponse = new JSONObject();

        if (idStr == null) {
            jsonResponse.put("error", "missing showId parameter");
            failWith(res, jsonResponse);
            return;
        }

        Integer id;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            jsonResponse.put("error", "invalid showId format");
            failWith(res, jsonResponse);
            return;
        }

        try {
            JSONObject result;
            if (userToken == null || userToken.isEmpty()) {
                result = Search.showDetails(id.toString());
            } else {
                UserSessionsService userSessionsService = new UserSessionsService(Main.getFactory());
                userSessionsService.refreshSession(userToken);
                User u = userSessionsService.retrieveUser(userToken);
                result = Search.showDetails(id.toString(), u);
            }
            res.getOutputStream().write(result.toString().getBytes());
        } catch (ClientErrorException e) {
            res.setStatus(500);
            res.getOutputStream().write("Internal server error".getBytes());
        } catch (UnAuthenticatedUserException e) {
            jsonResponse.put("error", e.getMessage());
            failWith(res, jsonResponse);
        }

        res.getOutputStream().flush();
        res.getOutputStream().close();
    }
}
