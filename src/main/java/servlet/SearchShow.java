package servlet;

import moviedb.Search;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.ClientErrorException;
import java.io.IOException;

@WebServlet(
        name = "SearchShow",
        urlPatterns =  {"/api/v1/search/show"}
)
public class SearchShow extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException {
        String searchValue = req.getParameter("searchValue");
        try {
            JSONObject o = Search.tvShow(searchValue);
            res.getOutputStream().write(o.toString().getBytes());
        } catch (ClientErrorException e) {
            res.setStatus(500);
            res.getOutputStream().write("Internal server error".getBytes());
        }

        res.getOutputStream().flush();
        res.getOutputStream().close();
    }
}
