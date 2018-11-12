package services;

import entities.Episode;
import entities.UserSession;
import entities.Watch;
import org.junit.Test;
import services.errors.NonExistingUserException;
import services.errors.UnregisteredEpisodeException;
import services.errors.UserExistsException;

import javax.xml.bind.DatatypeConverter;

import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class WatchServiceTest extends TestWithDb {
    @Test
    public void registerUSerWatch() throws UserExistsException, NonExistingUserException, UnregisteredEpisodeException {
        WatchService watchService = new WatchService(getSessionFactory());
        UserSession userSession = registerAndLoginUser();
        Watch w = watchService.registerUserWatch(userSession.getUser().getId(), 1100, 6, 1);
        assertEquals(1100, (int) w.getEpisode().getShowId());
        assertEquals(6, (int) w.getEpisode().getSeasonNumber());
        assertEquals(1, (int) w.getEpisode().getEpisodeId());
    }

    @Test
    public void listWatchedEpisodes() throws UserExistsException, NonExistingUserException, UnregisteredEpisodeException {
        WatchService watchService = new WatchService(getSessionFactory());
        UserSession userSession = registerAndLoginUser();

        Watch w1 = watchService.registerUserWatch(userSession.getUser().getId(), 1100, 6, 1);
        Watch w2 = watchService.registerUserWatch(userSession.getUser().getId(), 1200, 5, 2);

        List<Episode> watchedEpisodes = watchService.listWatchedEpisodes(userSession.getUser());
        assertTrue(watchedEpisodes.contains(w1.getEpisode()));
        assertTrue(watchedEpisodes.contains(w2.getEpisode()));
        assertEquals(2, watchedEpisodes.size());
    }

    @Test
    public void unregisterWatch() throws UserExistsException, NonExistingUserException {
        WatchService watchService = new WatchService(getSessionFactory());
        UserSession userSession = registerAndLoginUser();

        Watch w1 = watchService.registerUserWatch(userSession.getUser().getId(), 1100, 6, 1);
        Watch w2 = watchService.registerUserWatch(userSession.getUser().getId(), 1200, 5, 2);

        watchService.unregisterUserWatch(w2);

        List<Episode> watchedEpisodes = watchService.listWatchedEpisodes(userSession.getUser());
        assertTrue(watchedEpisodes.contains(w1.getEpisode()));
        assertEquals(1, watchedEpisodes.size());
    }

    private UserSession registerAndLoginUser() throws UserExistsException, NonExistingUserException {
        UsersService usersService = new UsersService(getSessionFactory());
        usersService.addUser("u", "p");
        return usersService.login("u", DatatypeConverter.printHexBinary(UsersService.hashPassWord("p")));
    }
}
