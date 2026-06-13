package com.abcfoodzone.abc_food_zone.dto;

public class LoginResponse {
    private String token;
    private String message;
    private String email;
    private String role;

    // Constructors
    public LoginResponse() {}

    public LoginResponse(String token, String message, String email, String role) {
        this.token = token;
        this.message = message;
        this.email = email;
        this.role = role;
    }

    // Getters and Setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}