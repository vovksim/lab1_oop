package dao.impl;

import dao.AuthTokenDAO;
import model.AuthToken;
import db.DBUtil;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Optional;

public class JdbcAuthTokenDAOImpl implements AuthTokenDAO {
    @Override
    public Optional<AuthToken> findByToken(String token) {
        String sql = "SELECT * FROM auth_tokens WHERE token = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, token);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return Optional.of(map(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return Optional.empty();
    }

    @Override
    public void save(AuthToken token) {
        String sql = "INSERT INTO auth_tokens (user_id, token, created_at) VALUES (?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, token.getUserId());
            ps.setString(2, token.getToken());
            ps.setTimestamp(3, Timestamp.valueOf(token.getCreatedAt()));
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    @Override
    public void deleteByToken(String token) {
        String sql = "DELETE FROM auth_tokens WHERE token = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, token);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    @Override
    public void deleteExpiredTokens() {
        String sql = "DELETE FROM auth_tokens WHERE created_at < ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            LocalDateTime threshold = LocalDateTime.now().minusSeconds(AuthToken.TOKEN_TIME_TO_LIVE);
            ps.setTimestamp(1, Timestamp.valueOf(threshold));
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    private AuthToken map(ResultSet rs) throws SQLException {
        return new AuthToken(
                rs.getInt("user_id"),
                rs.getString("token"),
                rs.getTimestamp("created_at").toLocalDateTime()
        );
    }
}
