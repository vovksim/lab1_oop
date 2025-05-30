package controllers;

import dao.TourDAO;
import dao.impl.JdbcTourDAOImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Tour;

import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "DetailServlet", urlPatterns = {"/detail"})
public class DetailServlet extends HttpServlet {

    private TourDAO tourDAO;

    @Override
    public void init() throws ServletException {
        tourDAO = new JdbcTourDAOImpl();  // Initialize DAO, can be improved with DI
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String tourIdParam = request.getParameter("id");

        if (tourIdParam == null || tourIdParam.isEmpty()) {
            response.sendRedirect("home");
            return;
        }

        try {
            int tourId = Integer.parseInt(tourIdParam);
            Optional<Tour> tourOpt = tourDAO.findById(tourId);

            if (tourOpt.isPresent()) {
                request.setAttribute("tour", tourOpt.get());
                request.getRequestDispatcher("/WEB-INF/views/detail.jsp").forward(request, response);
            } else {
                response.sendRedirect("home");
            }

        } catch (NumberFormatException e) {
            response.sendRedirect("home");
        }
    }
}
