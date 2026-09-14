package com.msgDissapear.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterRequest {

    @NotBlank(message = "Display name is required")
    @Size(min = 2, max = 50, message = "Display name must be 2–50 characters")
    private String displayName;

    @NotBlank(message = "Username is required")
    @Size(min = 2, max = 30, message = "Username must be 2–30 characters")
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Enter a valid email")
    @Size(max = 120)
    private String email;

    @Size(max = 160, message = "Bio must be at most 160 characters")
    private String bio;

    @NotBlank(message = "Password is required")
    @Size(min = 4, message = "Password must be at least 4 characters")
    private String password;

    @NotBlank(message = "Please confirm your password")
    private String confirmPassword;

    public String getDisplayName() { return displayName; }
    public void setDisplayName(String displayName) { this.displayName = displayName; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getConfirmPassword() { return confirmPassword; }
    public void setConfirmPassword(String confirmPassword) { this.confirmPassword = confirmPassword; }
}
