package moviedb;

import entities.UserSession;
import launch.Main;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import services.UsersService;
import services.WatchService;
import services.errors.NonExistingUserException;
import services.errors.UnAuthenticatedUserException;
import services.errors.UserExistsException;

import javax.xml.bind.DatatypeConverter;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class SearchTest {
    @Test
    public void searchShow() {
        JSONObject r = Search.tvShow("game");
        System.out.println(r);
    }

    @Test
    public void seasonDetails() {
        JSONObject result = Search.seasonDetails("1100", "6");
        assertEquals(6, result.getInt("season_number"));
    }

    @Test
    public void searchSeasonWithWatchedEpisode() throws UserExistsException, NonExistingUserException,
            UnAuthenticatedUserException
    {
        UsersService s = new UsersService(Main.getFactory());
        s.addUser("u", "p");
        UserSession userSession = s.login("u", DatatypeConverter.printHexBinary(UsersService.hashPassWord("p")));
        new WatchService(Main.getFactory()).registerUserWatch(userSession.getUuid(), 1100, 6, 62868);
        JSONObject HIMYMSeason6 = Search.seasonDetails("1100", "6", userSession.getUser());
        JSONArray episodes = HIMYMSeason6.getJSONArray("episodes");
        JSONObject episode1 = (JSONObject) episodes.get(0);
        assertTrue(episode1.getBoolean("watched"));
    }
}
