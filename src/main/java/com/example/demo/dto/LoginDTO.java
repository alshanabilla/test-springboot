package com.example.demo.dto;

import java.io.Serializable;

public class LoginDTO implements Serializable{
    private static final long serialVersionUID = 5926468583005150707L;
    
    private String email;
    private String password;    

    public LoginDTO() {

    }

    public LoginDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    
}
