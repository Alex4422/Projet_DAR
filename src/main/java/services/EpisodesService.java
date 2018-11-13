package services;

import entities.Episode;
import org.hibernate.Criteria;
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

    public Episode addEpisodeIfNotExists(Integer showId, Integer seasonNumber, Integer episodeId) {
        Episode newEpisode = new Episode(showId, seasonNumber, episodeId);
        try {
            add(newEpisode);
            return newEpisode;
        } catch (ConstraintViolationException e) {
            try {
                return getEpisode(showId, seasonNumber, episodeId);
            } catch (UnregisteredEpisodeException e1) {
                System.out.println("Error while fetching an existing that existed...");
                System.exit(1);
            }
        }
        return null;
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
