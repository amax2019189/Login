package com.alejandromax.login.dto;

public class LoginResponse {
    public String message;
    public Long userId;
    public String name;
    public String email;

    public LoginResponse(String message, Long userId, String name, String email) {
        this.message = message;
        this.userId = userId;
        this.name = name;
        this.email = email;
    }
}