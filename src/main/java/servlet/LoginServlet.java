package servlet;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entities.UserSession;
import launch.Main;
import org.json.JSONObject;
import services.UsersService;
import services.errors.NonExistingUserException;
import static servlet.Util.failWith;
import static servlet.Util.successWith;


@WebServlet(
        name = "Login",
        urlPatterns =  {"/api/v1/login"}
)
public class LoginServlet extends ServletBase {
    @Override
    public JSONObject processGet() throws Exception {
        String username = getStringParameter("username");
        String password = getStringParameter("password");
        JSONObject jsonResponse = new JSONObject();
        UserSession newSession = new UsersService(Main.getFactory()).login(username,password);
        jsonResponse.put("userToken", newSession.getUuid());
        return jsonResponse;
    }
}