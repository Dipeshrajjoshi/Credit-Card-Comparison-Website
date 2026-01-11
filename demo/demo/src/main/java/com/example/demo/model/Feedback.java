package com.example.demo.model;

import jakarta.validation.constraints.*;

public class Feedback {

    @NotBlank(message = "Email is required")
    @Pattern(regexp = "^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$", message = "Invalid email format")
    private String email;

    @NotBlank(message = "Mobile is required")
    @Pattern(regexp = "^\\+\\d{10,15}$", message = "Invalid mobile number")
    private String mobile;

    @NotBlank(message = "Complaint is required")
    @Size(min = 10, message = "Complaint must be at least 10 characters")
    private String complaint;

    // Default constructor
    public Feedback() {
    }

    // Getters
    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }

    public String getComplaint() {
        return complaint;
    }

    // Setters
    public void setEmail(String email) {
        this.email = email;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setComplaint(String complaint) {
        this.complaint = complaint;
    }
}
