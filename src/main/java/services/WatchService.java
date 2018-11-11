package services;

import entities.Episode;
import entities.User;
import entities.Watch;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import services.errors.UnAuthenticatedUserException;

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

    public Watch registerUserWatch(String userToken, Integer showId, Integer seasonNumber, Integer episodeId)
            throws UnAuthenticatedUserException {
        // TODO: check that the user hasn't alrteady watched the episode
        User user = new UserSessionsService(getSessionFactory())
                        .retrieveUser(userToken);
        Episode episode = new EpisodesService(getSessionFactory())
                                .addEpisodeIfNotExists(showId, seasonNumber, episodeId);
        Watch userWatch = new Watch(user, episode);
        add(userWatch);
        return userWatch;
    }

    public List<Episode> listWatchedEpisodes(String userToken) throws UnAuthenticatedUserException {
        User user = new UserSessionsService(getSessionFactory())
                        .retrieveUser(userToken);
        beginTransaction();
        Criteria criteria = getSession().createCriteria(Watch.class)
                                .add(Restrictions.eq("user.id", user.getId()));
        List<Watch> result = criteria.list();
        getSession().getTransaction().commit();
        return result.stream()
                .map(Watch::getEpisode)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    // TODO: Unwatch Episode
}
