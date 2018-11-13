package services;

import entities.Episode;
import entities.User;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import services.errors.NonExistingUserWatchException;
import services.errors.UnAuthenticatedUserException;
import services.errors.UnregisteredEpisodeException;

public class WatchService extends ServiceBase {
    public WatchService(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public void registerUserWatch(String userToken, Integer showId, Integer seasonNumber, Integer episodeId)
            throws UnAuthenticatedUserException
    {
        User user = new UserSessionsService(getSessionFactory())
                .retrieveUser(userToken);
        Episode episode = new EpisodesService(getSessionFactory())
                                .addEpisodeIfNotExists(showId, seasonNumber, episodeId);
        user.getEpisodes().add(episode);
        update(user);
    }

    public void unregisterUserWatch(String userToken, Integer showId, Integer seasonNumber, Integer episodeId)
            throws UnAuthenticatedUserException, UnregisteredEpisodeException, NonExistingUserWatchException
    {
        User u = new UserSessionsService(getSessionFactory()).retrieveUser(userToken);
        Episode episode = new EpisodesService(getSessionFactory())
                .getEpisode(showId, seasonNumber, episodeId);
        u.getEpisodes().remove(episode);
        try {
            update(u);
        } catch (ConstraintViolationException e) {
            throw new NonExistingUserWatchException();
        }
    }
}
