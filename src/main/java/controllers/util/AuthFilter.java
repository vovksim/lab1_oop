package controllers.util;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@WebFilter("/*")
public class AuthFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(AuthFilter.class);

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String path = request.getRequestURI();
        HttpSession session = request.getSession(false);

        boolean loggedIn = session != null && session.getAttribute("user") != null;
        boolean loginRequest = path.endsWith("TourSys/login") || path.endsWith("/register");

        logger.debug("Intercepted request: {}", path);
        logger.debug("Session exists: {}, User logged in: {}", session != null, loggedIn);
        logger.debug("Is login or register request: {}", loginRequest);

        if (loggedIn || loginRequest) {
            logger.info("Access granted for path: {}", path);
            chain.doFilter(req, res);
        } else {
            logger.warn("Access denied. Redirecting to login. Path: {}", path);
            response.sendRedirect("TourSys/login");
        }
    }
}
