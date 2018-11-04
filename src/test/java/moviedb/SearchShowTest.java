package moviedb;

import org.json.JSONObject;
import org.junit.Test;

public class SearchShowTest {
    @Test
    public void searchShow() {
        JSONObject r = Search.tvShow("game");
        System.out.println(r);
    }
}
