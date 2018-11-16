package servlet.filters;

import launch.Main;
import org.json.JSONObject;
import services.UserSessionsService;
import services.errors.UnAuthenticatedUserException;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static servlet.Util.failWith;

@WebFilter("/api/v1/auth/*")
public class AuthenticationFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // do nothing
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException
    {
        if (!((HttpServletRequest) servletRequest).getMethod().equals("OPTIONS")) {
            String userToken = servletRequest.getParameter("userToken");
            JSONObject jsonResponse = new JSONObject();

            if (userToken == null || userToken.isEmpty()) {
                jsonResponse.put("error", "You must provide a user token.");
                failWith((HttpServletResponse) servletResponse, jsonResponse);
                return;
            }

            try {
                UserSessionsService sessionsService = new UserSessionsService(Main.getFactory());
                sessionsService.refreshSession(userToken);
            } catch (UnAuthenticatedUserException e) {
                jsonResponse.put("error", "Unauthenticated user.");
                failWith((HttpServletResponse) servletResponse, jsonResponse);
                return;
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        // do nothing
    }
}
