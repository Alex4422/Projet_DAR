package servlet;

import org.json.JSONObject;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Util {
    public static void failWith(HttpServletResponse response, JSONObject responseBody) throws IOException {
        response.setStatus(400);
        response.getOutputStream().write(responseBody.toString().getBytes());
    }

    public static void successWith(HttpServletResponse response, JSONObject responseBody) throws IOException {
        response.setStatus(200);
        response.getOutputStream().write(responseBody.toString().getBytes());
    }
}
