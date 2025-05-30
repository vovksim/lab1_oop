package dao.impl;

import dao.PurchaseDAO;
import model.Purchase;
import db.DBUtil;

import java.sql.*;
import java.util.*;

public class JdbcPurchaseDAOImpl implements PurchaseDAO {
    @Override
    public Optional<Purchase> findById(int id) {
        String sql = "SELECT * FROM purchases WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return Optional.of(map(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return Optional.empty();
    }

    @Override
    public List<Purchase> findByUserId(int userId) {
        List<Purchase> list = new ArrayList<>();
        String sql = "SELECT * FROM purchases WHERE user_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(map(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    @Override
    public void save(Purchase p) {
        String sql = "INSERT INTO purchases (user_id, tour_id, purchase_time) VALUES (?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, p.getUserId());
            ps.setInt(2, p.getTourId());
            ps.setTimestamp(3, Timestamp.valueOf(p.getPurchaseDate()));
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    @Override
    public void delete(int id) {
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM purchases WHERE id = ?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    private Purchase map(ResultSet rs) throws SQLException {
        return new Purchase(
                rs.getInt("id"),
                rs.getInt("user_id"),
                rs.getInt("tour_id"),
                rs.getTimestamp("purchase_time").toLocalDateTime()
        );
    }
}
