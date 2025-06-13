package com.jasso.finance.finance.rest;

public class AuthResponse {
    private String token;
    private String username;
    public void setToken(String token) {
        this.token = token;
    }

    // Getters
    public String getToken() { return token; }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}