package servlet;

import launch.Main;
import org.json.JSONObject;
import services.WatchService;
import services.errors.NonExistingUserException;
import services.errors.NonExistingUserWatchException;
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
public class UserWatch extends ServletBase {
    @Override
    public JSONObject processPost() throws Exception {
        String userToken = getStringParameter("userToken");
        Integer showId = getIntegerParameter("showId");
        Integer seasonNumber = getIntegerParameter("seasonNumber");
        Integer episodeId = getIntegerParameter("episodeId");
        new WatchService(Main.getFactory()).registerUserWatch(userToken, showId, seasonNumber, episodeId);
        return null;
    }

    @Override
    public JSONObject processDelete() throws Exception {
        String userToken = getStringParameter("userToken");
        Integer showId = getIntegerParameter("showId");
        Integer seasonNumber = getIntegerParameter("seasonNumber");
        Integer episodeId = getIntegerParameter("episodeId");
        new WatchService(Main.getFactory()).unregisterUserWatch(userToken, showId, seasonNumber, episodeId);
        return null;
    }
}
