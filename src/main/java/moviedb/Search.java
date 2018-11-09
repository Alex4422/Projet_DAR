package moviedb;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

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
            JSONObject processed = new JSONObject();
            processed.put("name", show.getString("name"));
            processed.put("poster_path", show.get("poster_path"));
            processed.put("id", show.getInt("id"));
            processedShows.put(processed);
        }
        return processedShows;
    }

    public static JSONObject showDetails(String showId) {
        String r = getTarget("/tv/" + showId)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get(String.class);
        return processShowDetails(new JSONObject(r));
    }

    private static JSONObject processShowDetails(JSONObject o) {
        JSONObject result = new JSONObject();
        result.put("name", o.getString("name"));
        result.put("backdrop_path", o.getString("backdrop_path"));
        result.put("overview", o.getString("overview"));
        result.put("id", o.getInt("id"));
        return result;
    }

    private static WebTarget getTarget(String endPoint) {
        return ClientBuilder.newClient()
                .target(ApiValues.API_URL)
                .path(endPoint)
                .queryParam("api_key", ApiValues.API_KEY);
    }
}
