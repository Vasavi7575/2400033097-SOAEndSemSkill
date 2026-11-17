package com.klu.library.dto;

public class LoginResponse {
    private final String token;
    private final String username;
    private final String role;
    private final String tokenType = "Bearer";

    public LoginResponse(String token, String username, String role) {
        this.token = token;
        this.username = username;
        this.role = role;
    }

    public String getToken() { return token; }
    public String getUsername() { return username; }
    public String getRole() { return role; }
    public String getTokenType() { return tokenType; }
}
