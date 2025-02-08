package com.bookstore.dto;

import com.bookstore.entity.User;

public class AuthResponse {
    private String token;
    private User.Role role;

    // Constructor
    public AuthResponse(String token, User.Role role) {
        this.token = token;
        this.role = role;
    }

    // Getters and Setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User.Role getRole() {
        return role;
    }

    public void setRole(User.Role role) {
        this.role = role;
    }
}
