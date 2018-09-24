package servlet;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(
        name = "DemoServlet",
        urlPatterns = {"/api/users/signup"}
)
public class Users extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
    throws IOException{
        req.getParameter("login");

        res.getOutputStream().write("{ userToken: 12ft35rdeba }".getBytes());
        res.getOutputStream().flush();
        res.getOutputStream().close();
    }
}
