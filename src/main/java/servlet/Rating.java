package servlet;

import launch.Main;
import org.json.JSONObject;
import services.RatingService;
import services.errors.InvalidRatingException;
import services.errors.UnAuthenticatedUserException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static servlet.Util.failWith;

@WebServlet(
        name = "ratingServlet",
        urlPatterns = {"/api/v1/auth/rating"}
)
public class Rating extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String userToken = req.getParameter("userToken");
        String showIdStr = req.getParameter("showId");
        String ratingStr = req.getParameter("rating");
        JSONObject jsonResponse = new JSONObject();

        if (userToken == null || showIdStr == null || ratingStr == null) {
            jsonResponse.put("error", "Missing request parameter");
            failWith(res, jsonResponse);
            return;
        }

        Integer showId;
        try {
            showId = Integer.parseInt(showIdStr);
        } catch (NumberFormatException e) {
            jsonResponse.put("error", "Invalid showID format");
            failWith(res, jsonResponse);
            return;
        }

        Integer rating;
        try {
            rating = Integer.parseInt(ratingStr);
        } catch (NumberFormatException e) {
            jsonResponse.put("error", "Invalid rating format");
            failWith(res, jsonResponse);
            return;
        }

        try {
            new RatingService(Main.getFactory()).rateShow(userToken, showId, rating);
            res.setStatus(200);
            res.getOutputStream().write("OK".getBytes());
        } catch (UnAuthenticatedUserException e) {
            jsonResponse.put("error", "Unauthenticated user");
            failWith(res, jsonResponse);
            return;
        } catch (InvalidRatingException e) {
            jsonResponse.put("error", "Rating must be in [0, 10]");
            failWith(res, jsonResponse);
            return;
        }
    }
}
