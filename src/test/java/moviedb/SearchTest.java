package moviedb;

import org.json.JSONObject;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class SearchTest {
    @Test
    public void searchShow() {
        JSONObject r = Search.tvShow("game");
        System.out.println(r);
    }

    @Test
    public void seasonDetails() {
        JSONObject result = Search.seasonDetails("1100", "6");
        assertEquals(result.getString("air_date"), "2010-09-20");
    }
}
