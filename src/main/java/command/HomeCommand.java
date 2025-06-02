package command;

import dao.TourDAO;
import dao.impl.JdbcTourDAOImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Tour;

import java.io.IOException;
import java.util.List;

public class HomeCommand implements CommandInterface {

    private final TourDAO tourDAO = new JdbcTourDAOImpl();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Tour> tours = tourDAO.findAll();
        request.setAttribute("tours", tours);
        request.getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("home");
    }
}
