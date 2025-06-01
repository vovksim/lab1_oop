package controllers;

import command.CommandFactory;
import command.CommandInterface;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/TourSys/*")
public class FrontController extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = extractAction(request);
        String id = extractId(request);

        if (id != null) {
            request.setAttribute("id", id);
        }

        CommandInterface command = CommandFactory.getCommand(action);

        switch (request.getMethod()) {
            case "GET":
                command.doGet(request, response);
                break;
            case "POST":
                command.doPost(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "HTTP method not supported");
        }
    }

    /**
     * Extract action from query param or REST path.
     */
    private String extractAction(HttpServletRequest request) {
        // 1. Try query parameter
        String action = request.getParameter("action");
        if (action != null && !action.isEmpty()) {
            return action;
        }

        // 2. Try REST path, e.g. /detail/42 or /login
        String path = request.getPathInfo(); // "/detail/42"
        if (path != null && !path.equals("/")) {
            String[] parts = path.split("/");
            if (parts.length > 1 && parts[1] != null && !parts[1].isEmpty()) {
                return parts[1];
            }
        }

        // 3. Default fallback - use "login" here, not "default"
        return "login";
    }

    /**
     * Extract id from query param or REST path.
     */
    private String extractId(HttpServletRequest request) {
        // 1. Try query parameter
        String id = request.getParameter("id");
        if (id != null && !id.isEmpty()) {
            return id;
        }

        // 2. Try REST path
        String path = request.getPathInfo();
        if (path != null && !path.equals("/")) {
            String[] parts = path.split("/");
            if (parts.length > 2 && parts[2] != null && !parts[2].isEmpty()) {
                return parts[2];
            }
        }

        // 3. No id found
        return null;
    }
}
