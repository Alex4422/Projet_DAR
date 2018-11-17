package servlet;
import launch.Main;
import org.json.JSONObject;
import services.UsersService;

import javax.servlet.annotation.WebServlet;

@WebServlet(
        name = "UsersServlet",
        urlPatterns = {"/api/v1/users"}
)
public class Users extends ServletBase {
    @Override
    public JSONObject processPost() throws Exception {
        String username = getStringParameter("username");
        String password = getStringParameter("password");
        new UsersService(Main.getFactory()).addUser(username, password);
        return null;
    }
}
