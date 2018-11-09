package servlet;

import org.json.JSONObject;
import services.UsersService;
import org.hibernate.exception.ConstraintViolationException;
import services.errors.UserExistsException;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static servlet.Util.failWith;

@WebServlet(
        name = "UsersServlet",
        urlPatterns = {"/api/v1/users"}
)
public class Users extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
    throws IOException{

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        JSONObject jsonResponse = new JSONObject();

        if (username.isEmpty()) {
            jsonResponse.put("error", "Empty username");
            failWith(res, jsonResponse);
            return;
        }
        if (password.isEmpty()) {
            jsonResponse.put("error", "Empty password");
            failWith(res, jsonResponse);
            return;
        }

        try {
            UsersService.addUser(username, password);
            res.setStatus(200);
            res.getOutputStream().write("OK".getBytes());
        } catch (UserExistsException e) {
            jsonResponse.put("error", "A user with username <" + username + "> already exists");
            failWith(res, jsonResponse);
            return;
        }

        res.getOutputStream().flush();
        res.getOutputStream().close();
    }
}
