package servlet;

import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface ServletMethod {
    JSONObject processRequest() throws Exception;
}
