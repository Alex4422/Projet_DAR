package servlet;

import moviedb.Search;
import org.json.JSONObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.ClientErrorException;
import java.io.IOException;

@WebServlet(
        name = "ShowDetails",
        urlPatterns = {"/api/v1/show"}
)
public class ShowDetails extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException {
        String id = req.getParameter("id");
        try {
            JSONObject o = Search.showDetails(id);
            res.getOutputStream().write(o.toString().getBytes());
        } catch (ClientErrorException e) {
            res.setStatus(500);
            res.getOutputStream().write("Internal server error".getBytes());
        }

        res.getOutputStream().flush();
        res.getOutputStream().close();
    }
}
