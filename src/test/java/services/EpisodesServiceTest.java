package services;

import entities.Episode;
import org.junit.Test;
import services.errors.UnregisteredEpisodeException;

import static junit.framework.TestCase.assertEquals;

public class EpisodesServiceTest extends TestWithDb {
    @Test
    public void addEpisode() {
        EpisodesService e = new EpisodesService(getSessionFactory());
        Episode result = e.addEpisodeIfNotExists(1100, 6, 1);
        assertEquals(1100, (int) result.getShowId());
        assertEquals(6, (int) result.getSeasonNumber());
        assertEquals(1, (int) result.getEpisodeId());
    }

    @Test
    public void getEpisode() throws UnregisteredEpisodeException {
        EpisodesService e = new EpisodesService(getSessionFactory());
        e.addEpisodeIfNotExists(1100, 6, 1);
        Episode episode = e.getEpisode(1100, 6, 1);
        assertEquals(1100, (int) episode.getShowId());
        assertEquals(6, (int) episode.getSeasonNumber());
        assertEquals(1, (int) episode.getEpisodeId());
    }

    @Test(expected = UnregisteredEpisodeException.class)
    public void getUnregisteredEpisode() throws UnregisteredEpisodeException {
        EpisodesService e = new EpisodesService(getSessionFactory());
        Episode episode = e.getEpisode(1100, 6, 1);
    }
}
