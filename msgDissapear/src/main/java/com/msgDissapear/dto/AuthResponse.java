package com.msgDissapear.dto;

public class AuthResponse {

    private String username;
    private String message;
    private boolean success;

    public AuthResponse(String username, String message, boolean success) {
        this.username = username;
        this.message = message;
        this.success = success;
    }

    public String getUsername() { return username; }
    public String getMessage() { return message; }
    public boolean isSuccess() { return success; }
}
