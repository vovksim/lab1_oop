package command;

import dao.TourDAO;
import dao.impl.JdbcTourDAOImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Tour;

import java.io.IOException;
import java.util.Optional;

public class DetailCommand implements CommandInterface {

    private final TourDAO tourDAO = new JdbcTourDAOImpl();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Extract "id" from query or attribute (FrontController may set it)
        String idParam = request.getParameter("id");
        if (idParam == null) {
            Object idAttr = request.getAttribute("id");
            if (idAttr != null) {
                idParam = idAttr.toString();
            }
        }

        if (idParam == null || idParam.isEmpty()) {
            response.sendRedirect("home");
            return;
        }

        try {
            int tourId = Integer.parseInt(idParam);
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

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("home"); // or send an error
    }
}
