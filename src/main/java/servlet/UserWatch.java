package servlet;

import launch.Main;
import org.json.JSONObject;
import services.WatchService;
import services.errors.NonExistingUserException;
import services.errors.UnAuthenticatedUserException;
import services.errors.UnregisteredEpisodeException;

import javax.persistence.ManyToOne;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static servlet.Util.failWith;

@WebServlet (
        name = "UserWatch",
        urlPatterns = {"/api/v1/auth/userWatch"}
)
public class UserWatch extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String userToken = req.getParameter("userToken");
        String showIdStr = req.getParameter("showId");
        String seasonNumberStr = req.getParameter("seasonNumber");
        String episodeIdStr = req.getParameter("episodeId");
        Integer showId;
        Integer seasonNumber;
        Integer episodeId;
        JSONObject jsonResponse = new JSONObject();

        if (showIdStr == null || seasonNumberStr == null || episodeIdStr == null || userToken == null) {
            jsonResponse.put("error", "Missing parameter");
            failWith(res, jsonResponse);
            return;
        }

        try {
            showId = Integer.parseInt(showIdStr);
            seasonNumber = Integer.parseInt(seasonNumberStr);
            episodeId = Integer.parseInt(episodeIdStr);
        } catch (NumberFormatException e) {
            jsonResponse.put("error", "Invalid argument format");
            failWith(res, jsonResponse);
            return;
        }

        try {
            new WatchService(Main.getFactory()).registerUserWatch(userToken, showId, seasonNumber, episodeId);
        } catch (NonExistingUserException e) {
            jsonResponse.put("error", "NonExisting user");
            failWith(res, jsonResponse);
            return;
        } catch (UnAuthenticatedUserException e) {
            jsonResponse.put("error", "Unauthenticated user");
            failWith(res, jsonResponse);
            return;
        }
    }

    protected void doDelete(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String userToken = req.getParameter("userToken");
        String showIdStr = req.getParameter("showId");
        String seasonNumberStr = req.getParameter("seasonNumber");
        String episodeIdStr = req.getParameter("episodeId");
        Integer showId;
        Integer seasonNumber;
        Integer episodeId;
        JSONObject jsonResponse = new JSONObject();

        if (showIdStr == null || seasonNumberStr == null || episodeIdStr == null || userToken == null) {
            jsonResponse.put("error", "Missing parameter");
            failWith(res, jsonResponse);
            return;
        }
        try {
            showId = Integer.parseInt(showIdStr);
            seasonNumber = Integer.parseInt(seasonNumberStr);
            episodeId = Integer.parseInt(episodeIdStr);
        } catch (NumberFormatException e) {
            jsonResponse.put("error", "Invalid argument format");
            failWith(res, jsonResponse);
            return;
        }

        try {
            new WatchService(Main.getFactory()).unregisterUserWatch(userToken, showId, seasonNumber, episodeId);
        } catch (UnregisteredEpisodeException e) {
            jsonResponse.put("error", "Unregistered episode");
            failWith(res, jsonResponse);
        } catch (NonExistingUserException e) {
            jsonResponse.put("error", "Non existsing user");
            failWith(res, jsonResponse);
        } catch (UnAuthenticatedUserException e) {
            jsonResponse.put("error", "Unauthenticated user");
            failWith(res, jsonResponse);
        }

    }
}
