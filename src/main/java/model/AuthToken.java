package model;

import java.time.LocalDateTime;

public class AuthToken {
    private int id;
    private int userId;
    private String token;
    private LocalDateTime createdAt;
    public static final int TOKEN_TIME_TO_LIVE = 30; //30s for demo

    public AuthToken() {}

    public AuthToken(int userId, String token, LocalDateTime createdAt) {
        this.userId = userId;
        this.token = token;
        this.createdAt = createdAt;
    }

    public int getUserId() { return userId; }

    public void setUserId(int userId) { this.userId = userId; }

    public String getToken() { return token; }

    public void setToken(String token) { this.token = token; }

    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public static int getTtlSeconds() { return TOKEN_TIME_TO_LIVE; }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(createdAt.plusSeconds(TOKEN_TIME_TO_LIVE));
    }
}
