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
import java.util.List;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {

    private TourDAO tourDAO;

    @Override
    public void init() throws ServletException {
        // Initialize your DAO here (consider dependency injection for production)
        tourDAO = new JdbcTourDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Fetch all tours from DB
        List<Tour> tours = tourDAO.findAll();

        req.setAttribute("tours", tours);
        req.getRequestDispatcher("/WEB-INF/views/home.jsp").forward(req, resp);
    }
}
