package servlet;

import org.json.JSONObject;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// TODO: finish servlets simplification

public abstract class ServletBase extends HttpServlet {
    private HttpServletRequest request;
    private HttpServletResponse response;

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        callServletMethod(req, res, this::processGet);
    }

    protected JSONObject processGet() throws Exception {
        return null;
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        callServletMethod(req, res, this::processPost);
    }

    protected JSONObject processPost() throws Exception {
        return null;
    }

    @Override
    public void doDelete(HttpServletRequest req, HttpServletResponse res) throws IOException {
        callServletMethod(req, res, this::processDelete);
    }

    protected JSONObject processDelete() throws Exception {
        return null;
    }

    protected Integer getIntegerParameter(String parameterName) throws Exception {
        String paramStr = getRequest().getParameter(parameterName);
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

    protected String getStringParameter(String parameterName) throws Exception {
        String param = getRequest().getParameter(parameterName);
        if (param == null) {
            throw new Exception("Missing parameter: " + parameterName);
        }
        return param;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    private void callServletMethod(HttpServletRequest req, HttpServletResponse res, ServletMethod method) throws IOException {
        request = req;
        response = res;
        try {
            JSONObject result = method.processRequest();
            res.setStatus(200);
            if (result != null) {
                res.getOutputStream().write(result.toString().getBytes());
            }
        } catch (Exception e) {
            JSONObject errorBody = new JSONObject();
            errorBody.put("error", e.getMessage());
            res.setStatus(400);
            res.getOutputStream().write(errorBody.toString().getBytes());
        }
    }
}
