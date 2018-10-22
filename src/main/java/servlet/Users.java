package servlet;

import services.UsersService;
import org.hibernate.exception.ConstraintViolationException;

import java.io.IOException;

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
        String uuid = "";
        try {
            uuid = UsersService.addUser(username, password);
        } catch (ConstraintViolationException e) {
            res.setStatus(400);
            res.getOutputStream().write("User already exists".getBytes());
            return;
        }

        res.getOutputStream().write(String.format("{ userToken: \"%s\" }", uuid).getBytes());

        res.getOutputStream().flush();
        res.getOutputStream().close();
    }
}
