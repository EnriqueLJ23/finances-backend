package com.jasso.finance.finance.rest;

public class AuthResponse {
    private String token;
    private String username;
    private long id;
    private long expiresAt;

    public void setToken(String token) {
        this.token = token;
    }

    // Getters
    public String getToken() { return token; }

    public String getUsername() {
        return username;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(long expiresAt) {
        this.expiresAt = expiresAt;
    }
}