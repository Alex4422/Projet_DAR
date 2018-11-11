package entities;

import javax.persistence.*;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"showID", "seasonNumber", "episodeId"})
})
public class Episode {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    @Column(nullable = false)
    private Integer showId;

    @Column(nullable = false)
    private Integer seasonNumber;

    @Column(nullable = false)
    private Integer episodeId;

    public Episode() {}

    public Episode(Integer showId, Integer seasonNumber, Integer episodeId) {
        this.showId = showId;
        this.seasonNumber = seasonNumber;
        this.episodeId = episodeId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getShowId() {
        return showId;
    }

    public void setShowId(Integer showId) {
        this.showId = showId;
    }

    public Integer getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(Integer seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public Integer getEpisodeId() {
        return episodeId;
    }

    public void setEpisodeId(Integer episodeId) {
        this.episodeId = episodeId;
    }

    @Override
    public boolean equals(Object other) {
        if (! (other instanceof Episode)) {
            return false;
        }
        Episode rhs = (Episode) other;
        return id.equals(rhs.id) && showId.equals(rhs.showId) && seasonNumber.equals(rhs.seasonNumber) &&
                episodeId.equals(rhs.episodeId);
    }
}
