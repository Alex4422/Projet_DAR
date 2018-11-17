package servlet;

import moviedb.Search;
import org.json.JSONObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.ClientErrorException;
import java.io.IOException;

import static servlet.Util.failWith;
import static servlet.Util.successWith;

@WebServlet(
        name = "SearchShow",
        urlPatterns =  {"/api/v1/search/show"}
)
public class SearchShow extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException {
        String searchValue = req.getParameter("searchValue");
        JSONObject jsonResponse = new JSONObject();

        if (searchValue.isEmpty()) {
            jsonResponse.put("error", "Empty search query");
            failWith(res, jsonResponse);
            return;
        }

        try {
            JSONObject o = Search.tvShow(searchValue);
            successWith(res, o);
        } catch (ClientErrorException e) {
            res.setStatus(500);
            res.getOutputStream().write("Internal server error".getBytes());
        }
    }
}
