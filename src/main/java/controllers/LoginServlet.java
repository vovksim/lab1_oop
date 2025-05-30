package controllers;

import dao.AuthTokenDAO;
import dao.UserDAO;
import dao.impl.JdbcAuthTokenDAOImpl;
import dao.impl.JdbcUserDAOImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.AuthToken;
import model.User;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private UserDAO userDAO;
    private AuthTokenDAO authTokenDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new JdbcUserDAOImpl();
        authTokenDAO = new JdbcAuthTokenDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        Optional<User> userOpt = userDAO.findByUsername(username);

        if (userOpt.isPresent() && userOpt.get().getPassword().equals(password)) {
            User user = userOpt.get();

            // Generate a new token
            String token = UUID.randomUUID().toString();

            // Create auth token instance
            AuthToken authToken = new AuthToken();
            authToken.setUserId(user.getId());
            authToken.setToken(token);
            authToken.setCreatedAt(LocalDateTime.now());

            // Store token in DB
            authTokenDAO.save(authToken);

            // Set token in cookie
            Cookie cookie = new Cookie("auth_token", token);
            cookie.setMaxAge((int) AuthToken.TOKEN_TIME_TO_LIVE); // Use token TTL constant
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            response.addCookie(cookie);

            response.sendRedirect("home"); // Redirect to main page controller
        } else {
            request.setAttribute("error", "Invalid username or password");
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
        }
    }
}
