package controllers;

import command.CommandFactory;
import command.CommandInterface;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@WebServlet("/TourSys/*")
public class FrontController extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(FrontController.class);

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String method = request.getMethod();
        String uri = request.getRequestURI();
        String query = request.getQueryString();

        logger.info("Incoming request: {} {}?{}", method, uri, query != null ? query : "");

        String action = extractAction(request);
        String id = extractId(request);

        logger.debug("Resolved action: {}", action);
        if (id != null) {
            logger.debug("Resolved id: {}", id);
            request.setAttribute("id", id);
        }

        CommandInterface command = CommandFactory.getCommand(action);
        if (command == null) {
            logger.warn("No command found for action: {}", action);
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Unknown action: " + action);
            return;
        }

        try {
            switch (method) {
                case "GET":
                    logger.info("Dispatching to GET handler for action: {}", action);
                    command.doGet(request, response);
                    break;
                case "POST":
                    logger.info("Dispatching to POST handler for action: {}", action);
                    command.doPost(request, response);
                    break;
                default:
                    logger.error("Unsupported HTTP method: {}", method);
                    response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "HTTP method not supported");
            }
        } catch (Exception e) {
            logger.error("Exception while handling action '{}'", action, e);
            throw new ServletException(e);
        }
    }

    /**
     * Extract action from query param or REST path.
     */
    private String extractAction(HttpServletRequest request) {
        String action = request.getParameter("action");
        if (action != null && !action.isEmpty()) {
            return action;
        }

        String path = request.getPathInfo();
        if (path != null && !path.equals("/")) {
            String[] parts = path.split("/");
            if (parts.length > 1 && parts[1] != null && !parts[1].isEmpty()) {
                return parts[1];
            }
        }

        logger.debug("No action found in request, defaulting to 'login'");
        return "login";
    }

    /**
     * Extract id from query param or REST path.
     */
    private String extractId(HttpServletRequest request) {
        String id = request.getParameter("id");
        if (id != null && !id.isEmpty()) {
            return id;
        }

        String path = request.getPathInfo();
        if (path != null && !path.equals("/")) {
            String[] parts = path.split("/");
            if (parts.length > 2 && parts[2] != null && !parts[2].isEmpty()) {
                return parts[2];
            }
        }

        return null;
    }
}
