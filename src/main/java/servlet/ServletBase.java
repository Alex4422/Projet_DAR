package servlet;

import org.json.JSONObject;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// TODO: finish servlets simplification

public abstract class ServletBase extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        callServletMethod(req, res, this::processGet);
    }

    protected JSONObject processGet(HttpServletRequest req) throws Exception {
        return null;
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        callServletMethod(req, res, this::processPost);
    }

    protected JSONObject processPost(HttpServletRequest req) throws Exception {
        return null;
    }

    @Override
    public void doDelete(HttpServletRequest req, HttpServletResponse res) throws IOException {
        callServletMethod(req, res, this::processDelete);
    }

    protected JSONObject processDelete(HttpServletRequest req) throws Exception {
        return null;
    }

    protected Integer getIntegerParameter(HttpServletRequest req, String parameterName) throws Exception {
        String paramStr = req.getParameter(parameterName);
        if (paramStr == null) {
            throw new Exception("Missing parameter: " + parameterName);
        }
        try {
            Integer param = Integer.parseInt(paramStr);
            return param;
        } catch (NumberFormatException e) {
            throw new Exception("Invalid format for parameter: " + parameterName);
        }
    }

    private void callServletMethod(HttpServletRequest req, HttpServletResponse res, ServletMethod method) throws IOException {
        try {
            JSONObject result = method.processRequest(req);
            res.setStatus(200);
            res.getOutputStream().write(result.toString().getBytes());
        } catch (Exception e) {
            JSONObject errorBody = new JSONObject();
            errorBody.put("error", e.getMessage());
            res.setStatus(400);
            res.getOutputStream().write(errorBody.toString().getBytes());
        }
    }
}
