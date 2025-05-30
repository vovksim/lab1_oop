package dao.impl;

import dao.TourDAO;
import model.Tour;
import db.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcTourDAOImpl implements TourDAO {

    private static final String SELECT_ALL = "SELECT * FROM tours";
    private static final String SELECT_BY_ID = "SELECT * FROM tours WHERE id = ?";
    private static final String INSERT_TOUR = "INSERT INTO tours (name, description, image_url, location, duration, price, slots) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_TOUR = "UPDATE tours SET name=?, description=?, image_url=?, location=?, duration=?, price=?, slots=? WHERE id=?";
    private static final String DELETE_TOUR = "DELETE FROM tours WHERE id=?";

    @Override
    public Optional<Tour> findById(int id) {
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ID)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRowToTour(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Tour> findAll() {
        List<Tour> tours = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_ALL);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                tours.add(mapRowToTour(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tours;
    }

    @Override
    public void save(Tour tour) {
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_TOUR, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, tour.getName());
            stmt.setString(2, tour.getDescription());
            stmt.setString(3, tour.getImageUrl());
            stmt.setString(4, tour.getLocation());
            stmt.setInt(5, tour.getDuration());
            stmt.setDouble(6, tour.getPrice());
            stmt.setInt(7, tour.getSlots());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating tour failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    tour.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating tour failed, no ID obtained.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Tour tour) {
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_TOUR)) {

            stmt.setString(1, tour.getName());
            stmt.setString(2, tour.getDescription());
            stmt.setString(3, tour.getImageUrl());
            stmt.setString(4, tour.getLocation());
            stmt.setInt(5, tour.getDuration());
            stmt.setDouble(6, tour.getPrice());
            stmt.setInt(7, tour.getSlots());
            stmt.setInt(8, tour.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE_TOUR)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Tour mapRowToTour(ResultSet rs) throws SQLException {
        Tour tour = new Tour();
        tour.setId(rs.getInt("id"));
        tour.setName(rs.getString("name"));
        tour.setDescription(rs.getString("description"));
        tour.setImageUrl(rs.getString("image_url"));
        tour.setLocation(rs.getString("location"));
        tour.setDuration(rs.getInt("duration"));
        tour.setPrice(rs.getDouble("price"));
        tour.setSlots(rs.getInt("slots"));
        return tour;
    }
}
