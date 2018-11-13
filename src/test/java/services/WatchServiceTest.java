package services;

import entities.Episode;
import entities.User;
import entities.UserSession;
import org.junit.Test;
import services.errors.*;

import javax.xml.bind.DatatypeConverter;

import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class WatchServiceTest extends TestWithDb {
    @Test
    public void registerUSerWatchWithNewEpisode() throws UserExistsException, NonExistingUserException, UnAuthenticatedUserException {
        WatchService watchService = new WatchService(getSessionFactory());
        UserSession userSession = registerAndLoginUser();
        User user = userSession.getUser();
        watchService.registerUserWatch(userSession.getUuid(), 1100, 6, 1);

        assertEquals(1, user.getEpisodes().size());

        Episode episode = user.getEpisodes().iterator().next();
        assertEquals(1100, (int) episode.getShowId());
        assertEquals(6, (int) episode.getSeasonNumber());
        assertEquals(1, (int) episode.getEpisodeId());
    }

    @Test
    public void unregisterWatch() throws UserExistsException, NonExistingUserException, UnAuthenticatedUserException,
            UnregisteredEpisodeException, NonExistingUserWatchException {
        WatchService watchService = new WatchService(getSessionFactory());
        UserSession userSession = registerAndLoginUser();
        User user = userSession.getUser();
        EpisodesService episodesService = new EpisodesService(getSessionFactory());

        Episode e1 = episodesService.addEpisodeIfNotExists(1100, 6, 1);
        Episode e2 = episodesService.addEpisodeIfNotExists(1200, 5, 2);
        watchService.registerUserWatch(userSession.getUuid(), e1.getShowId(), e1.getSeasonNumber(), e1.getEpisodeId());
        watchService.registerUserWatch(userSession.getUuid(), e2.getShowId(), e2.getSeasonNumber(), e2.getEpisodeId());

        watchService.unregisterUserWatch(userSession.getUuid(), e2.getShowId(),
                                                                e2.getSeasonNumber(),
                                                                e2.getEpisodeId());
        assertTrue(user.getEpisodes().contains(e1));
        assertEquals(1, user.getEpisodes().size());
    }

    @Test(expected = UnregisteredEpisodeException.class)
    public void unregisterWatchWithUnregisteredEpisode() throws UserExistsException,
            NonExistingUserException, NonExistingUserWatchException, UnAuthenticatedUserException, UnregisteredEpisodeException
    {
        WatchService watchService = new WatchService(getSessionFactory());
        UserSession userSession = registerAndLoginUser();

        watchService.unregisterUserWatch(userSession.getUuid(), 1100, 6, 1);
    }

    private UserSession registerAndLoginUser() throws UserExistsException, NonExistingUserException {
        UsersService usersService = new UsersService(getSessionFactory());
        usersService.addUser("u", "p");
        return usersService.login("u", DatatypeConverter.printHexBinary(UsersService.hashPassWord("p")));
    }
}
