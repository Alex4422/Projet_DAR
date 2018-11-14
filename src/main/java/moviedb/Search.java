package moviedb;

import entities.Episode;
import entities.Rating;
import entities.User;
import launch.Main;
import org.json.JSONArray;
import org.json.JSONObject;
import services.RatingService;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Search {

    public static JSONObject tvShow(String searchValue) {
        String r = getTarget("/search/tv")
                .queryParam("query", searchValue)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get(String.class);
        return processTvShowsResponse(new JSONObject(r));
    }

    public static JSONObject processTvShowsResponse(JSONObject response) {
        JSONObject processedResponse = new JSONObject();
        processedResponse.put("page", response.getInt("page"));
        processedResponse.put("total_pages", response.getInt("total_pages"));
        processedResponse.put("results", processShows(response.getJSONArray("results")));
        return processedResponse;
    }

    private static JSONArray processShows(JSONArray shows) {
        JSONArray processedShows = new JSONArray();
        for (int i = 0; i < shows.length(); i++) {
            JSONObject show = shows.getJSONObject(i);
            JSONObject processed = filterFields(Arrays.asList("name", "poster_path", "id"), show);
            processedShows.put(processed);
        }
        return processedShows;
    }

    public static JSONObject showDetails(String showId, User user) {
        JSONObject showDetails = showDetails(showId);
        Rating rating = new RatingService(Main.getFactory()).getRating(user, Integer.parseInt(showId));
        if (rating != null) {
            showDetails.put("rating", rating.getRating());
        }
        return showDetails;
    }

    public static JSONObject showDetails(String showId) {
        String r = getTarget("/tv/" + showId)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get(String.class);
        JSONObject result = new JSONObject(r);
        Double rating = new RatingService(Main.getFactory()).getAverageRating(Integer.parseInt(showId));
        if (rating != null) {
            result.put("averageRating", rating);
        }
        return processShowDetails(new JSONObject(r));
    }

    private static JSONObject processShowDetails(JSONObject o) {
        JSONObject result = filterFields(Arrays.asList("name", "backdrop_path", "overview", "id"), o);
        List<Integer> seasons = o.getJSONArray("seasons").toList().stream()
                .map(s -> Integer.parseInt(((HashMap)s).get("season_number").toString()))
                .collect(Collectors.toList());
        result.put("seasons", seasons);
        return result;
    }

    public static JSONObject seasonDetails(String showId, String seasonNumber, User user) {
        JSONObject result = seasonDetails(showId, seasonNumber);
        JSONArray episodes = new JSONArray();
        for (Object e: result.getJSONArray("episodes")) {
            if (e instanceof JSONObject) {
                JSONObject jsonEpisode = (JSONObject) e;
                Episode episode = new Episode(jsonEpisode.getInt("show_id"),
                                        jsonEpisode.getInt("season_number"),
                                        jsonEpisode.getInt("id"));
                jsonEpisode.put("watched", user.getEpisodes().contains(episode));
                episodes.put(jsonEpisode);
            }
        }
        result.put("episodes", episodes);
        return result;
    }

    public static JSONObject seasonDetails(String showId, String seasonNumber) {
        String endpoint = String.format("/tv/%s/season/%s", showId, seasonNumber);
        String r = getTarget(endpoint)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get(String.class);
        return processSeasonDetails(new JSONObject(r));
    }

    private static JSONObject processSeasonDetails(JSONObject season) {
        JSONObject result = filterFields(Arrays.asList("season_number", "name"), season);
        JSONArray episodes = new JSONArray();
        for (Object e: season.getJSONArray("episodes")) {
            if (e instanceof JSONObject) {
                episodes.put(processEpisode((JSONObject) e));
            }
        }
        result.put("episodes", episodes);
        return result;
    }

    private static JSONObject processEpisode(JSONObject episode) {
        JSONObject result = filterFields(
                Arrays.asList("name", "id", "overview","episode_number", "season_number", "show_id"),
                episode);
        result.put("watched", false);
        return result;
    }

    private static JSONObject filterFields(List<String> fields, JSONObject o) {
        JSONObject result = new JSONObject();
        for (String field: fields) {
            result.put(field, o.get(field));
        }
        return result;
    }

    private static WebTarget getTarget(String endPoint) {
        return ClientBuilder.newClient()
                .target(ApiValues.API_URL)
                .path(endPoint)
                .queryParam("api_key", ApiValues.API_KEY);
    }
}
