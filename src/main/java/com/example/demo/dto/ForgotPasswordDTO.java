package com.example.demo.dto;

public class ForgotPasswordDTO {
    private String email;
    private String newPassword;

    

    public ForgotPasswordDTO() {

    }

    public ForgotPasswordDTO(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    
}
