package services;

import entities.Episode;
import entities.User;
import entities.Watch;
import org.junit.Test;
import services.errors.NonExistingUserException;
import services.errors.UnAuthenticatedUserException;
import services.errors.UserExistsException;

import javax.xml.bind.DatatypeConverter;

import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class WatchServiceTest extends TestWithDb {
    @Test
    public void registerUSerWatch() throws UserExistsException, NonExistingUserException, UnAuthenticatedUserException {
        WatchService watchService = new WatchService(getSessionFactory());
        String userToken = registerAndLoginUser();
        Watch w = watchService.registerUserWatch(userToken, 1100, 6, 1);
        assertEquals(1100, (int) w.getEpisode().getShowId());
        assertEquals(6, (int) w.getEpisode().getSeasonNumber());
        assertEquals(1, (int) w.getEpisode().getEpisodeId());
    }

    @Test
    public void listWatchedEpisodes() throws UserExistsException, NonExistingUserException, UnAuthenticatedUserException  {
        WatchService watchService = new WatchService(getSessionFactory());
        String userToken = registerAndLoginUser();

        Watch w1 = watchService.registerUserWatch(userToken, 1100, 6, 1);
        Watch w2 = watchService.registerUserWatch(userToken, 1200, 5, 2);

        List<Episode> watchedEpisodes = watchService.listWatchedEpisodes(userToken);
        assertTrue(watchedEpisodes.contains(w1.getEpisode()));
        assertTrue(watchedEpisodes.contains(w2.getEpisode()));
        assertEquals(2, watchedEpisodes.size());
    }

    private String registerAndLoginUser() throws UserExistsException, NonExistingUserException {
        UsersService usersService = new UsersService(getSessionFactory());
        usersService.addUser("u", "p");
        return usersService.login("u", DatatypeConverter.printHexBinary(UsersService.hashPassWord("p")));
    }
}
