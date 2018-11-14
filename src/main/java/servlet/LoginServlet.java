package servlet;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import launch.Main;
import org.json.JSONObject;
import services.UsersService;
import services.errors.NonExistingUserException;
import static servlet.Util.failWith;


@WebServlet(
        name = "Login",
        urlPatterns =  {"/api/v1/login"}
)
public class LoginServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        JSONObject jsonResponse = new JSONObject();

        if (username.isEmpty()) {
            jsonResponse.put("error", "Empty username");
            failWith(response, jsonResponse);
            return;
        }
        if (password.isEmpty()) {
            jsonResponse.put("error", "Empty password");
            failWith(response, jsonResponse);
            return;
        }

        try {
            new UsersService(Main.getFactory()).login(username,password);
            response.setStatus(200);
            response.getOutputStream().write("OK! the login has been found ! " .getBytes());
        } catch (NonExistingUserException e) {
            e.printStackTrace();
            jsonResponse.put("error", "A user with username <" + username + "> do not exist");
            failWith(response, jsonResponse);
            return;
        }

        response.getOutputStream().flush();
        response.getOutputStream().close();

    }


}
