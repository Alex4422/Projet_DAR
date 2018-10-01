package servlet;

import app.User;
import app.UserService;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
        User newUser = new User(username, password);

        try {
            new UserService().save(newUser);
            res.setStatus(200);
            res.getOutputStream().write("User successfully registered".getBytes());
        } catch (SQLException e) {
            res.setStatus(400);
            res.getOutputStream().write(e.getMessage().getBytes());
        }

        res.getOutputStream().flush();
        res.getOutputStream().close();
    }
}
