package services;

import entities.Episode;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import services.errors.UnregisteredEpisodeException;

import java.util.List;

public class EpisodesService extends ServiceBase {

    public EpisodesService(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public void clear() {
        super.clearTable("Episode");
    }

    public boolean addEpisodeIfNotExists(Integer showId, Integer seasonNumber, Integer episodeId) {
        Episode newEpisode = new Episode(showId, seasonNumber, episodeId);
        try {
            add(newEpisode);
            return true;
        } catch (ConstraintViolationException e) {
            return false;
        }
    }

    public Episode getEpisode(Integer showId, Integer seasonNumber, Integer episodeId) throws UnregisteredEpisodeException {
        String queryString = "FROM Episode E WHERE E.showId = :showId and " +
                                                  "E.seasonNumber = :seasonNumber and " +
                                                  "E.episodeId = :episodeId";
        beginTransaction();
        Query query = getSession().createQuery(queryString);
        query.setParameter("showId", showId);
        query.setParameter("seasonNumber", seasonNumber);
        query.setParameter("episodeId", episodeId);
        List result = query.list();
        getSession().getTransaction().commit();
        if (result.isEmpty()) {
            throw new UnregisteredEpisodeException();
        }
        return (Episode) result.get(0);
    }
}
