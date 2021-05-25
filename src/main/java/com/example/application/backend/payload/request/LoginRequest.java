package com.example.application.backend.payload.request;

public class LoginRequest {

   // private String username;
   private String nif;
    private String password;

    public LoginRequest() {
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
