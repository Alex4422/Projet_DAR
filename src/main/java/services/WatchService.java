package services;

import entities.Episode;
import entities.User;
import entities.Watch;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import services.errors.NonExistingUserException;
import services.errors.UnAuthenticatedUserException;
import services.errors.UnregisteredEpisodeException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class WatchService extends ServiceBase {
    public WatchService(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public void clear() {
        super.clearTable("Watch");
    }

    public Watch getWatch(Integer userId, Integer showId, Integer seasonNumber, Integer episodeId)
            throws NonExistingUserException, UnregisteredEpisodeException
    {
        User user = new UsersService(getSessionFactory()).getUser(userId);
        Episode episode = new EpisodesService(getSessionFactory()).getEpisode(showId, seasonNumber, episodeId);
        return getWatch(user, episode);
    }

    public Watch getWatch(User user, Episode episode) {
        beginTransaction();
        Criteria critera = getSession().createCriteria(Watch.class)
                .add(Restrictions.eq("user", user))
                .add(Restrictions.eq("episode", episode));
        List<Watch> result = critera.list();
        getSession().getTransaction().commit();
        return first(result);
    }

    public Watch registerUserWatch(Integer userId, Integer showId, Integer seasonNumber, Integer episodeId)
            throws NonExistingUserException
    {
        User user = new UsersService(getSessionFactory()).getUser(userId);
        try {
            Watch previouswatch = getWatch(user.getId(), showId, seasonNumber, episodeId);
            if (previouswatch != null) {
                return previouswatch;
            }
        } catch (UnregisteredEpisodeException e) { /*do nothing*/ }

        Episode episode = new EpisodesService(getSessionFactory())
                                .addEpisodeIfNotExists(showId, seasonNumber, episodeId);
        Watch userWatch = new Watch(user, episode);
        add(userWatch);
        return userWatch;
    }

    public void unregisterUserWatch(Watch w) {
        delete(w);
    }

    public List<Episode> listWatchedEpisodes(User user) {
        beginTransaction();
        Criteria criteria = getSession().createCriteria(Watch.class)
                .add(Restrictions.eq("user.id", user.getId()));
        List<Watch> result = criteria.list();
        getSession().getTransaction().commit();
        return result.stream()
                .map(Watch::getEpisode)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
