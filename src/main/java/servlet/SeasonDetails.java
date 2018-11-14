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
import static servlet.Util.successWith;

@WebServlet (
        name = "SeasonDetails",
        urlPatterns = {"/api/v1/seasonDetails"}
)
public class SeasonDetails extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String showId = req.getParameter("showId");
        String seasonNumber = req.getParameter("seasonNumber");
        String userToken = req.getParameter("userToken");
        JSONObject jsonResponse = new JSONObject();

        if (showId == null || seasonNumber == null) {
            jsonResponse.put("error", "Missing parameters");
            failWith(res, jsonResponse);
            return;
        }

        if (showId.isEmpty()) {
            jsonResponse.put("error", "Empty show id.");
            failWith(res, jsonResponse);
            return;
        }

        try {
            Integer.parseInt(seasonNumber);
        } catch (NumberFormatException e) {
            jsonResponse.put("error", "Invalid season number");
            failWith(res, jsonResponse);
            return;
        }

        try {
            JSONObject result;
            if (userToken != null && !userToken.isEmpty()) {
                UserSessionsService sessionsService = new UserSessionsService(Main.getFactory());
                sessionsService.refreshSession(userToken);
                User user = sessionsService.retrieveUser(userToken);
                result = Search.seasonDetails(showId, seasonNumber, user);
            } else {
                result = Search.seasonDetails(showId, seasonNumber);
            }
            successWith(res, result);
        } catch (ClientErrorException e) {
            res.setStatus(500);
            res.getOutputStream().write("Internal server error".getBytes());
        } catch (UnAuthenticatedUserException e) {
            jsonResponse.put("error", e.getMessage());
            failWith(res, jsonResponse);
        }
    }
}
